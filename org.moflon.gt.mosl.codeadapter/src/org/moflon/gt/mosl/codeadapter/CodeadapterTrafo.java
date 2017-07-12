package org.moflon.gt.mosl.codeadapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.moflon.compiler.sdm.democles.DemoclesMethodBodyHandler;
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

   private static CodeadapterTrafo instance;

   public static CodeadapterTrafo getInstance()
   {
      if (instance == null)
         instance = new CodeadapterTrafo();
      return instance;
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

   public EPackage transform(final EPackage contextEPackage, final GraphTransformationFile gtf, final ResourceSet resourceSet, final TransformationConfiguration transformationConfiguration)
   {
      transformationConfiguration.getContextController().setEPackage(contextEPackage);
      transformationConfiguration.getContextController().setResourceSet(resourceSet);

      String name = gtf.getName();
      String[] domain = name.split(java.util.regex.Pattern.quote("."));

      if (contextEPackage.getName().compareToIgnoreCase(gtf.getName()) == 0
            || domain.length > 0 && contextEPackage.getName().compareToIgnoreCase(domain[domain.length - 1]) == 0)
      {
         for (EClassDef classDef : gtf.getEClasses())
         {
            EClass contextEClass = (EClass) contextEPackage.getEClassifier(classDef.getName().getName());
            EClass eClassContext = classDef.getName();
            transformMethodsToEOperations(eClassContext, classDef, contextEClass, transformationConfiguration);
         }
      }

      return contextEPackage;
   }

   private void transformMethodsToEOperations(final EClass contextEClass, final EClassDef eclassDef, EClass changeableContext, TransformationConfiguration transformationConfiguration)
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

   private MoflonOperation createAndAddMoflonOperation(EClass changeableContext, final MethodDec methodDec, TransformationConfiguration transformationConfiguration)
   {
      final MoflonOperation eOperation = ControlflowFactory.eINSTANCE.createMoflonOperation();
      eOperation.setName(methodDec.getName());
      eOperation.getEParameters().addAll(createEParameters(methodDec.getParameters(), transformationConfiguration));
      eOperation.setEType(methodDec.getType());
      changeableContext.getEOperations().add(eOperation);
      return eOperation;
   }

   private Collection<? extends EParameter> createEParameters(final EList<MethodParameter> parameters, TransformationConfiguration transformationConfiguration)
   {
      List<EParameter> paramLst = new ArrayList<>();
      for (MethodParameter mParam : parameters)
      {
         EParameter eParam = EcoreFactory.eINSTANCE.createEParameter();
         eParam.setName(mParam.getName());
         eParam.setEType(transformationConfiguration.getContextController().getTypeContext(mParam.getType()));
         eParam.setLowerBound(0);
         eParam.setUpperBound(1);
         eParam.setUnique(true);
         eParam.setOrdered(true);
         paramLst.add(eParam);
      }
      return paramLst;
   }

   private void transformFirstStatement(final MethodDec methodDec, final MoflonOperation eOperation, TransformationConfiguration transformationConfiguration)
   {
      final Statement startStatement = methodDec.getStartStatement();
      final Scope rootScope = DemoclesFactory.eINSTANCE.createScope();
      eOperation.setRootScope(rootScope);
      if (startStatement != null)
      {
         transformationConfiguration.getStatementCreationController().loadCurrentMethod(methodDec);
         transformationConfiguration.getStatementCreationController().transformStatement(startStatement, rootScope, null, transformationConfiguration);
      }
      saveAsRegisteredAdapter(rootScope, eOperation, DemoclesMethodBodyHandler.CONTROL_FLOW_FILE_EXTENSION, transformationConfiguration.getContextController().getResourceSet());
   }

   public void saveAsRegisteredAdapter(EObject objectToSave, EObject adaptedObject, String type, ResourceSet resourceSet)
   {
      //TODO@rkluge: This could be a hack that circumvents proper Resources handling. 
      // Such things typically make debugging really hard because you cannot rely on the fact that a Resource will never "change"
      cleanResourceSet(adaptedObject.eResource(), resourceSet);

      final Resource adapterResource = (Resource) EcoreUtil.getRegisteredAdapter(adaptedObject, type);
      if (adapterResource != null)
      {
         //         try
         //         {
            cleanResourceSet(adapterResource, resourceSet);
            adapterResource.getContents().add(objectToSave);
            //adapterResource.save(Collections.EMPTY_MAP);
//         } catch (IOException e)
//         {
//            e.printStackTrace();
//         }
      }
   }

   private void cleanResourceSet(Resource res, ResourceSet resourceSet)
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
