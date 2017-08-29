package org.moflon.gt.mosl.codeadapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.compiler.sdm.democles.DemoclesMethodBodyHandler;
import org.moflon.gt.mosl.codeadapter.config.PatternNameGenerator;
import org.moflon.gt.mosl.codeadapter.config.TransformationConfiguration;
import org.moflon.gt.mosl.moslgt.EClassDef;
import org.moflon.gt.mosl.moslgt.GraphTransformationFile;
import org.moflon.gt.mosl.moslgt.MethodDec;
import org.moflon.gt.mosl.moslgt.MethodParameter;
import org.moflon.gt.mosl.moslgt.Statement;
import org.moflon.sdm.compiler.democles.validation.controlflow.ControlflowFactory;
import org.moflon.sdm.compiler.democles.validation.controlflow.MoflonOperation;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.Scope;

public class CodeadapterTrafo
{
   private final TransformationConfiguration transformationConfiguration;
   public CodeadapterTrafo(TransformationConfiguration trafoConfig){
      this.transformationConfiguration = trafoConfig;
   }
   
   public EPackage transform(final EPackage contextEPackage, final GraphTransformationFile graphTransformationFile, final ResourceSet resourceSet)
   {
      transformationConfiguration.getContextController().setEPackage(contextEPackage);
      transformationConfiguration.getContextController().setResourceSet(resourceSet);

      final String name = graphTransformationFile.getName();
      final String[] domain = name.split(java.util.regex.Pattern.quote("."));

      if (contextEPackage.getName().compareToIgnoreCase(graphTransformationFile.getName()) == 0
            || domain.length > 0 && contextEPackage.getName().compareToIgnoreCase(domain[domain.length - 1]) == 0)
      {
         for (final EClassDef eClassSpecification : graphTransformationFile.getEClasses())
         {
            EClass contextEClass = (EClass) contextEPackage.getEClassifier(eClassSpecification.getName().getName());
            EClass eClassContext = eClassSpecification.getName();
            transformMethodsToEOperations(eClassContext, eClassSpecification, contextEClass, transformationConfiguration);
         }
      }

      return contextEPackage;
   }

   private void transformMethodsToEOperations(final EClass contextEClass, final EClassDef eclassDef, final EClass changeableContext, final TransformationConfiguration transformationConfiguration)
   {
      final PatternNameGenerator patternNameGenerator = transformationConfiguration.getPatternCreationController().getPatternNameGenerator();
      patternNameGenerator.setEClass(changeableContext);
      
      for (final MethodDec methodDec : eclassDef.getOperations())
      {
         // this is a closure which will test if an EOperation with its EParameters already exist
         Predicate<? super EOperation> eOpTest = (eo -> eo.getName().equals(methodDec.getName()) && methodDec.getParameters().stream()
               .allMatch(param -> eo.getEParameters().stream().anyMatch(eParam -> eParam.getEType().getName().equals(param.getType().getName()))));
         Optional<EOperation> opt = changeableContext.getEOperations().stream().filter(eOpTest).findFirst();

         if (opt.isPresent())
         {
            changeableContext.getEOperations().remove(opt.get());
         }

         final MoflonOperation eOperation = createAndAddMoflonOperation(changeableContext, methodDec, transformationConfiguration);

         patternNameGenerator.setEOperation(eOperation);
         transformFirstStatement(methodDec, eOperation, transformationConfiguration);
      }
      
   }

   private MoflonOperation createAndAddMoflonOperation(final EClass changeableContext, final MethodDec methodSpecification, final TransformationConfiguration transformationConfiguration)
   {
      final MoflonOperation eOperation = ControlflowFactory.eINSTANCE.createMoflonOperation();
      eOperation.setName(methodSpecification.getName());
      eOperation.getEParameters().addAll(createEParameters(methodSpecification.getParameters(), transformationConfiguration));
      eOperation.setEType(getEType(methodSpecification, changeableContext));
      changeableContext.getEOperations().add(eOperation);
      return eOperation;
   }

   private EPackage getRootPackage(EPackage ePackage){
      EPackage rootPackage = ePackage;
      while(rootPackage.getESuperPackage() != null)
         rootPackage = rootPackage.getESuperPackage();
      return rootPackage;
   }
   
   private Collection<EClassifier> getAllClassifiers(EPackage anyPackage){
      LinkedList<EPackage> packageStack = new LinkedList<>(); 
      Set<EPackage> labeled = new HashSet<>();
      Set<EClassifier> allClassifier = new HashSet<>();
      packageStack.addLast(getRootPackage(anyPackage));
      while(!packageStack.isEmpty()){
         EPackage ePackage = packageStack.removeLast();
         if(!labeled.contains(ePackage)){
            labeled.add(ePackage);
            allClassifier.addAll(ePackage.getEClassifiers());
            ePackage.getESubpackages().forEach(subPackage -> packageStack.addLast(subPackage));
         }
      }
      
      return allClassifier;
   }
   
   private EClassifier getEType(MethodDec methodSpecification, EClass changeableContext){
      Collection<EClassifier> allClassifier = getAllClassifiers(changeableContext.getEPackage());
      Optional<EClassifier> typeMonad = allClassifier.stream().filter(eClassifier -> methodSpecification.getType() != null && eClassifier.getName().equals(methodSpecification.getType().getName())).findFirst();
      if(typeMonad.isPresent())
         return typeMonad.get();
      else
         return methodSpecification.getType();
   }
   
   private Collection<? extends EParameter> createEParameters(final EList<MethodParameter> parameterListSpecification, final TransformationConfiguration transformationConfiguration)
   {
      final List<EParameter> paramLst = new ArrayList<>();
      for (MethodParameter methodParameterSpecification : parameterListSpecification)
      {
         EParameter eParam = EcoreFactory.eINSTANCE.createEParameter();
         eParam.setName(methodParameterSpecification.getName());
         eParam.setEType(transformationConfiguration.getContextController().getTypeContext(methodParameterSpecification.getType()));
         eParam.setLowerBound(0);
         eParam.setUpperBound(1);
         eParam.setUnique(true);
         eParam.setOrdered(true);
         paramLst.add(eParam);
      }
      return paramLst;
   }

   private void transformFirstStatement(final MethodDec methodDec, final MoflonOperation eOperation, final TransformationConfiguration transformationConfiguration)
   {
      final Statement startStatement = methodDec.getStartStatement();
      final Scope rootScope = DemoclesFactory.eINSTANCE.createScope();
      eOperation.setRootScope(rootScope);
      
      if (startStatement != null)
      {
         transformationConfiguration.getStatementCreationController().loadCurrentMethod(methodDec);
         transformationConfiguration.getStatementCreationController().transformStatement(startStatement, rootScope, null);
      }
      
      transformationConfiguration.getECoreAdapterController().saveAsRegisteredAdapter(rootScope, eOperation, DemoclesMethodBodyHandler.CONTROL_FLOW_FILE_EXTENSION, transformationConfiguration.getContextController().getResourceSet());
   }
}
