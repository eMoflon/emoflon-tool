package org.moflon.gt.mosl.codeadapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

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
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.specification.emf.Pattern;
import org.moflon.compiler.sdm.democles.DemoclesMethodBodyHandler;
import org.moflon.gt.mosl.moslgt.EClassDef;
import org.moflon.gt.mosl.moslgt.GraphTransformationFile;
import org.moflon.gt.mosl.moslgt.MethodDec;
import org.moflon.gt.mosl.moslgt.MethodParameter;
import org.moflon.gt.mosl.moslgt.Statement;
import org.moflon.sdm.compiler.democles.validation.controlflow.ControlflowFactory;
import org.moflon.sdm.compiler.democles.validation.controlflow.MoflonOperation;
import org.moflon.sdm.compiler.democles.validation.result.ValidationReport;
import org.moflon.sdm.compiler.democles.validation.scope.PatternMatcher;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.Scope;

public class CodeadapterTrafo
{

   private static CodeadapterTrafo instance;

   private StatementBuilder statementTrafo;

   private EPackage contextEPackage;

   private Map<String, PatternMatcher> searchPlanGenerators;

   private ResourceSet resourceSet;

   private PatternNameGenerator patternNameGenerator;

   private CodeadapterTrafo()
   {
      statementTrafo = StatementBuilder.getInstance();
   }

   public static CodeadapterTrafo getInstance()
   {
      if (instance == null)
         instance = new CodeadapterTrafo();
      return instance;
   }

   public void setSearchplanGenerators(final Map<String, PatternMatcher> searchPlanGeneratorsByPatternKind)
   {
      this.searchPlanGenerators = searchPlanGeneratorsByPatternKind;
   }

   public PatternNameGenerator getPatternNameGenerator() {
      return this.patternNameGenerator;
   }

   public <EC extends EClassifier> EC getTypeContext(EC eClassifier)
   {
      @SuppressWarnings("unchecked")
      Optional<EC> contextEClassMonad = contextEPackage.getEClassifiers().stream().filter(classifier -> classifier.getName().equals(eClassifier.getName()))
            .map(classifier -> (EC) classifier).findFirst();
      if (contextEClassMonad.isPresent())
         return contextEClassMonad.get();
      else
         return eClassifier;
   }

   public EReference getEReferenceContext(EReference ref, EClass contextEclass)
   {
      Optional<EReference> contextEReferenceMonad = contextEclass.getEAllReferences().stream().filter(reference -> reference.getName().equals(ref.getName()))
            .findFirst();
      if (contextEReferenceMonad.isPresent())
         return contextEReferenceMonad.get();
      else
         return ref;
   }

   public EPackage transform(EPackage contextEPackage, final GraphTransformationFile gtf, final ResourceSet resourceSet)
   {
      this.resourceSet = resourceSet;
      this.contextEPackage = contextEPackage;

      String name = gtf.getName();
      String[] domain = name.split(java.util.regex.Pattern.quote("."));

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
      this.patternNameGenerator = new PatternNameGenerator();
      this.patternNameGenerator.setEClass(changeableContext);
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

         final MoflonOperation eOperation = ControlflowFactory.eINSTANCE.createMoflonOperation();
         changeableContext.getEOperations().add(eOperation);
         eOperation.setName(methodDec.getName());
         eOperation.getEParameters().addAll(createEParameters(methodDec.getParameters()));
         eOperation.setEType(methodDec.getType());

         this.patternNameGenerator.setEOperation(eOperation);
         transformMethodStructure(methodDec, eOperation);
      }
   }

   /*
    * TODO@rkluge If we provide List as Parameters this Function must be changed
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

   private void transformMethodStructure(final MethodDec methodDec, MoflonOperation eOperation)
   {
      final Statement startStatement = methodDec.getStartStatement();
      final Scope rootScope = DemoclesFactory.eINSTANCE.createScope();
      eOperation.setRootScope(rootScope);
      if (startStatement != null)
      {
         statementTrafo.loadCurrentMethod(methodDec);
         statementTrafo.transformStatement(startStatement, rootScope, null);
      }
      saveAsRegisteredAdapter(rootScope, eOperation, DemoclesMethodBodyHandler.CONTROL_FLOW_FILE_EXTENSION);
   }

   public ValidationReport generateSearchPlan(Pattern pattern, Adornment adornment, boolean isMultipleMatch, String type)
   {
      return searchPlanGenerators.get(type).generateSearchPlan(pattern, adornment, isMultipleMatch);
   }

   public void saveAsRegisteredAdapter(EObject objectToSave, EObject adaptedObject, String type)
   {
      //TODO@rkluge: This could be a hack that circumvents proper Resources handling. 
      // Such things typically make debugging really hard because you cannot rely on the fact that a Resource will never "change"
      cleanResourceSet(adaptedObject.eResource());

      final Resource adapterResource = (Resource) EcoreUtil.getRegisteredAdapter(adaptedObject, type);
      if (adapterResource != null)
      {
         //         try
         //         {
            cleanResourceSet(adapterResource);
            adapterResource.getContents().add(objectToSave);
            //adapterResource.save(Collections.EMPTY_MAP);
//         } catch (IOException e)
//         {
//            e.printStackTrace();
//         }
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
