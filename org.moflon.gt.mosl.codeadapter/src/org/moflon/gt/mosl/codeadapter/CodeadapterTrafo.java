package org.moflon.gt.mosl.codeadapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.moflon.gt.mosl.codeadapter.utils.PatternUtil;
import org.moflon.gt.mosl.moslgt.EClassDef;
import org.moflon.gt.mosl.moslgt.GraphTransformationFile;
import org.moflon.gt.mosl.moslgt.MethodDec;
import org.moflon.gt.mosl.moslgt.MethodParameter;
import org.moflon.gt.mosl.moslgt.PatternDef;
import org.moflon.gt.mosl.moslgt.Statement;
import org.moflon.sdm.compiler.democles.validation.controlflow.ControlflowFactory;
import org.moflon.sdm.compiler.democles.validation.controlflow.MoflonOperation;
import org.moflon.sdm.runtime.democles.CFNode;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.Scope;

public class CodeadapterTrafo
{

   private static CodeadapterTrafo instance;

   private StatementBuilder statementTrafo;

   private Consumer<ResourceSet> loader;

   private Function<CFNode, Function<PatternDef, Function<String, String>>> currentEOperationNameConstructor;
   
   private EPackage contextEPackage;;
   
   private ResourceSet resourceSet;

   private CodeadapterTrafo()
   {
      statementTrafo = StatementBuilder.getInstance();
      new CodeadapterAutofactory();
   }

   public static CodeadapterTrafo getInstance()
   {
      if (instance == null)
         instance = new CodeadapterTrafo();
      return instance;
   }

   public String getPatternName(CFNode node, PatternDef patternDef, String suffix)
   {
      return currentEOperationNameConstructor.apply(node).apply(patternDef).apply(suffix);
   }

   public void loadResourceSet(ResourceSet resSet)
   {
      loader.accept(resSet);
   }

   public <EC extends EClassifier> EC getTypeContext(EC eClassifier){
      @SuppressWarnings("unchecked")
      Optional<EC> contextEClassMonad=contextEPackage.getEClassifiers().stream().filter(classifier -> classifier.getName().equals(eClassifier.getName())).map(classifier -> (EC) classifier).findFirst();
      if(contextEClassMonad.isPresent())
         return contextEClassMonad.get();
      else
         return eClassifier;
   }
   
   public EReference getEReferenceContext(EReference ref, EClass contextEclass){
      Optional<EReference> contextEReferenceMonad = contextEclass.getEAllReferences().stream().filter(reference -> reference.getName().equals(ref.getName())).findFirst();
      if(contextEReferenceMonad.isPresent())
         return contextEReferenceMonad.get();
      else
         return ref;
   }
   
   public EPackage transform(EPackage contextEPackage, final GraphTransformationFile gtf, Consumer<ResourceSet> loader, final ResourceSet resourceSet)
   {
      this.resourceSet = resourceSet;
      this.loader = loader;
      this.contextEPackage = contextEPackage;
      PatternUtil.cleanNames();

      
      String name = gtf.getName();
      String[] domain = name.split(Pattern.quote("."));

      if (contextEPackage.getName().compareToIgnoreCase(gtf.getName()) == 0
            || domain.length > 0 && contextEPackage.getName().compareToIgnoreCase(domain[domain.length - 1]) == 0)
      {
         for (EClassDef classDef : gtf.getEClasses())
         {
            EClass contextEClass = (EClass) contextEPackage.getEClassifier(classDef.getName().getName());
            EClass eClassContext = classDef.getName();
            transformMethodsToEOperations(eClassContext, classDef, contextEClass);
         }
      }

      return contextEPackage;
   }

   private void transformMethodsToEOperations(final EClass contextEClass, final EClassDef eclassDef, EClass changeableContext)
   {
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

         MoflonOperation mofOp = ControlflowFactory.eINSTANCE.createMoflonOperation();
         changeableContext.getEOperations().add(mofOp);
         mofOp.setName(methodDec.getName());
         mofOp.getEParameters().addAll(createEParameters(methodDec.getParameters()));
         mofOp.setEType(methodDec.getType());

         currentEOperationNameConstructor = node -> patternDef -> suffix -> {
            final EOperation eOperation = mofOp;
            final CFNode cfNode = node;
            String storyNodeName = patternDef.getName() != null ? patternDef.getName().trim() : "";
            storyNodeName = storyNodeName.replaceAll("\\s+", "");
            final EClass eClass = eOperation.getEContainingClass();
            final int operationIndex = eClass.getEOperations().indexOf(eOperation);
            return "pattern_" + eClass.getName() + "_" + operationIndex + "_" + node.getId() + "_" + storyNodeName + "_" + suffix;
         };
         transformMethodStructure(methodDec, mofOp);
      }
   }

   /*
    * If we provide List as Parameters this Function must be changed
    * 
    */
   private Collection<? extends EParameter> createEParameters(final EList<MethodParameter> parameters)
   {
      List<EParameter> paramLst = new ArrayList<>();
      for (MethodParameter mParam : parameters)
      {
         EParameter eParam = EcoreFactory.eINSTANCE.createEParameter();
         eParam.setName(mParam.getName());
         eParam.setEType(getTypeContext(mParam.getType()));
         eParam.setLowerBound(0);
         eParam.setUpperBound(1);
         eParam.setUnique(true);
         eParam.setOrdered(true);
         paramLst.add(eParam);
      }
      return paramLst;
   }

   private void transformMethodStructure(final MethodDec methodDec, MoflonOperation mofOp)
   {
      Scope rootScope = DemoclesFactory.eINSTANCE.createScope();
      mofOp.setRootScope(rootScope);

      statementTrafo.loadCurrentMethod(methodDec);
      Statement startStatement = methodDec.getStartStatement();
      statementTrafo.transformStatement(startStatement, rootScope, null);
      
//      if(deletedOperations.containsKey(mofOp.getName()))
//         saveAsRegisteredAdapter(EcoreUtil.copy(rootScope), deletedOperations.get(mofOp.getName()), "cf");
//      else
         saveAsRegisteredAdapter(rootScope, mofOp, "cf");
   }
   
   public void saveAsRegisteredAdapter(EObject objectToSave, EObject adaptedObject, String type)
   {
      cleanResourceSet(adaptedObject.eResource());

      loadResourceSet(resourceSet);
      Resource patternResource = (Resource) EcoreUtil.getRegisteredAdapter(adaptedObject, type);
      if (patternResource != null)
      {
         try
         {
            cleanResourceSet(patternResource);
            patternResource.getContents().add(objectToSave);
            // objectToSave.eResource().getResourceSet().getResources().add(patternResource);
            patternResource.save(Collections.EMPTY_MAP);
         } catch (IOException e)
         {
            e.printStackTrace();
         }
      }
   }
   
   private void cleanResourceSet(Resource res)
   {
      if (res != null)
      {
         URI resUri = res.getURI();
         Resource contextResource = resourceSet.getResource(resUri, false);
         if (contextResource != null)
            resourceSet.getResources().remove(contextResource);

         resourceSet.getResources().add(res);
      }
   }
}
