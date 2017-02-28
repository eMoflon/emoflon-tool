package org.moflon.gt.mosl.codeadapter.codeadapter;




import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.moflon.gt.mosl.moslgt.EClassDef;
import org.moflon.gt.mosl.moslgt.GraphTransformationFile;
import org.moflon.gt.mosl.moslgt.MethodDec;
import org.moflon.gt.mosl.moslgt.MethodParameter;
import org.moflon.gt.mosl.moslgt.Statement;
import org.moflon.sdm.compiler.democles.validation.controlflow.ControlflowFactory;
import org.moflon.sdm.compiler.democles.validation.controlflow.MoflonOperation;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.Scope;


public class CodeadapterTrafo {	
	
	private static CodeadapterTrafo instance;
	
	private StatementAdapter statementTrafo;
	
	private CodeadapterTrafo(){
		statementTrafo = StatementAdapter.getInstance();
	}
	
	public static CodeadapterTrafo getInstance(){
		if(instance == null)
			instance = new CodeadapterTrafo();
		return instance;
	}
	
	
	public EPackage transform (final EPackage contextEPackage, final GraphTransformationFile gtf){
		EPackage cpyContextEPackage =EcoreUtil.copy(contextEPackage);
		String name = gtf.getName();
		String[] domain=name.split(Pattern.quote( "." ));
		
		if(contextEPackage.getName().compareToIgnoreCase(gtf.getName())==0 || domain.length > 0 && contextEPackage.getName().compareToIgnoreCase(domain[domain.length-1])==0){
			for(EClassDef classDef : gtf.getEClasses()){
				EClass cpyContextEClass = (EClass)cpyContextEPackage.getEClassifier(classDef.getName().getName());
				EClass eClassContext = classDef.getName();
				transformMethodsToEOperations(eClassContext, classDef, cpyContextEClass);
			}
		}		
		
		return cpyContextEPackage;
	}
	
	private void transformMethodsToEOperations(final EClass contextEClass, final EClassDef eclassDef, EClass changeableContext){
		for(final MethodDec methodDec :  eclassDef.getOperations()){
			// this is a closure which will test if an EOperation with its EParameters already exist
			Predicate<? super EOperation> eOpTest=(
					eo -> eo.getName().equals(methodDec.getName()) 
					&& methodDec.getParameters().stream().allMatch(
							param -> eo.getEParameters().stream().anyMatch(
									eParam -> eParam.getEType().getName().equals(param.getType().getName()))));
			Optional<EOperation> opt = changeableContext.getEOperations().stream().filter(eOpTest).findFirst();
			
			if(opt.isPresent()){
				changeableContext.getEOperations().remove(opt.get());
			}
			
			MoflonOperation mofOp = ControlflowFactory.eINSTANCE.createMoflonOperation();
			changeableContext.getEOperations().add(mofOp);
			mofOp.setName(methodDec.getName());
			mofOp.getEParameters().addAll(createEParameters(methodDec.getParameters()));
			mofOp.setEType(methodDec.getType());
			transformMethodStructure(methodDec, mofOp);
		}
	}

	/*
	 * If we provide List as Parameters this Function must be changed
	 * 
	 */
	private Collection<? extends EParameter> createEParameters(final EList<MethodParameter> parameters) {
		List<EParameter> paramLst= new ArrayList<>();
		for(MethodParameter mParam : parameters){
			EParameter eParam = EcoreFactory.eINSTANCE.createEParameter();
			eParam.setName(mParam.getName());
			eParam.setEType(mParam.getType());
			eParam.setLowerBound(0);
			eParam.setUpperBound(1);
			eParam.setUnique(true);
			eParam.setOrdered(true);
			paramLst.add(eParam);
		}
		return paramLst;
	}
	
	private void transformMethodStructure(final MethodDec methodDec, MoflonOperation mofOp){
		
		Scope rootScope = DemoclesFactory.eINSTANCE.createScope();
		mofOp.setRootScope(rootScope);
		
		statementTrafo.loadCurrentMethod(methodDec);
		Statement startStatement = methodDec.getStartStatement();
		statementTrafo.transformStatement(startStatement, rootScope);
		
	}
	
	
	
	

}
