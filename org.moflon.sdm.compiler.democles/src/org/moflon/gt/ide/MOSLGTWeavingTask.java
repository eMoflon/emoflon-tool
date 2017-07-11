package org.moflon.gt.ide;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.gervarro.eclipse.task.ITask;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.compiler.sdm.democles.DemoclesMethodBodyHandler;
import org.moflon.compiler.sdm.democles.ScopeValidationConfigurator;
import org.moflon.compiler.sdm.democles.eclipse.AdapterResource;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.gt.mosl.codeadapter.CodeadapterTrafo;
import org.moflon.gt.mosl.moslgt.GraphTransformationFile;

/**
 * This task weaves the control flow and pattern matching models stored in textual syntax with the plain metamodel
 * 
 * The task is configured with the top-level {@link EPackage} of the metamodel.
 * 
 * @author Roland Kluge - Initial implementation
 * 
 * @see #run(IProgressMonitor)
 */
public class MOSLGTWeavingTask implements ITask
{
   /**
    * The top-level {@link EPackage} of the ongoing build process
    */
   private EPackage ePackage;

   /**
    * The common {@link ResourceSet} of the ongoing build process
    */
   private ResourceSet resourceSet;


   /**
    * Preconfigures this task with the top-level {@link EPackage} of the metamodel to be processed
    * 
    * @param ePackage
    * @param scopeValidatorConfiguration
    */
   public MOSLGTWeavingTask(final EPackage ePackage, ScopeValidationConfigurator scopeValidatorConfiguration)
   {
      this.ePackage = ePackage;
      this.resourceSet = ePackage.eResource().getResourceSet();
      CodeadapterTrafo.getInstance().setSearchplanGenerators(scopeValidatorConfiguration.getSearchPlanGenerators());

      DemoclesMethodBodyHandler.initResourceSetForDemocles(resourceSet);
   }

   /**
    * Entry point of this task
    * 
    * It iterates through all {@link EClass}es in the metamodel and invokes
    * {@link #weaveEClass(EClass, MultiStatus, IProgressMonitor)} for each one.
    * 
    * @param monitor
    *           the progress monitor
    * @return the status of the entire task
    */
   @Override
   public IStatus run(IProgressMonitor monitor)
   {
      // This status collects all information about the weaving process for 'ePackage'
      final MultiStatus weavingMultiStatus = new MultiStatus(CodeGeneratorPlugin.getModuleID(), 0, getTaskName() + " failed", null);
      final List<EClass> eClasses = CodeGeneratorPlugin.getEClasses(this.ePackage);

      final SubMonitor subMon = SubMonitor.convert(monitor, getTaskName() + " in " + ePackage.getName(), eClasses.size());
      loadMGTFiles(subMon);
      return weavingMultiStatus.isOK() ? Status.OK_STATUS : weavingMultiStatus;
   }

   private IStatus loadMGTFiles(final IProgressMonitor monitor)
   {
      try
      {
         CodeadapterTrafo helper = CodeadapterTrafo.getInstance();

         final List<Resource> mgtResources = this.resourceSet.getResources().stream()
               .filter(resource -> resource.getURI().lastSegment().endsWith('.' + WorkspaceHelper.EMOFLON_GT_EXTENSION)).collect(Collectors.toList());

         for (final Resource schemaResource : mgtResources)
         {
            final GraphTransformationFile gtf = GraphTransformationFile.class.cast(schemaResource.getContents().get(0));
            if (gtf.getImports().size() > 0)
            {
               // TODO@rkluge: Probably, we will have to translate the .mgt files "package-by-package" and load the
               // appropriate packages

               // load context
               String contextEcorePath = gtf.getImports().get(0).getName().replaceFirst("platform:/resource", "").replaceFirst("platform:/plugin", "");
               Resource ecoreRes = this.resourceSet.getResource(URI.createPlatformResourceURI(contextEcorePath, false), true);
               ecoreRes.load(null);
               // TODO@rkluge: Extend to support multiple root packages
               final EPackage contextEPackage = EcoreUtil.copy((EPackage) ecoreRes.getContents().get(0));

               // save context as raw
               //      List<String> uriParts = Arrays.asList(ePackage.eResource().getURI().toString().split("/")).subList(0, 3);
               //      projecPrefixURI = "";
               //      for (String uriPart : uriParts)
               //      {
               //         projecPrefixURI += uriPart + "/";
               //      )
               // final String rawURIString = projecPrefixURI + "/model/raw/"+
               // MoflonUtil.lastCapitalizedSegmentOf(contextEPackage.getName());
               // String contextEcoreURIString= rawURIString + ".raw" + WorkspaceHelper.ECORE_FILE_EXTENSION;
               // URI contextEcoreURI = URI.createURI(contextEcoreURIString, true);
               // Resource contextEcoreResource = this.resourceSet.getResource(contextEcoreURI, false);
               // if(contextEcoreResource == null)
               // contextEcoreResource = this.resourceSet.createResource(contextEcoreURI);
               // contextEcoreResource.getContents().clear();
               // contextEcoreResource.getContents().add(EcoreUtil.copy(contextEPackage));
               // final Map<String, String> contextSaveOptions = new HashMap<>();
               // contextSaveOptions.put(Resource.OPTION_LINE_DELIMITER,
               // WorkspaceHelper.DEFAULT_RESOURCE_LINE_DELIMITER);
               // contextEcoreResource.save(contextSaveOptions);

               // transformation
               Resource enrichedEcoreResource = ePackage.eResource(); // this.resourceSet.createResource(enrichedEcoreURI);
               String nsURI = ePackage.eResource().getURI().toString();
               enrichedEcoreResource.getContents().clear();
               enrichedEcoreResource.getContents().add(contextEPackage);
               contextEPackage.setNsURI(nsURI);
               enrichedEcoreResource.save(Collections.EMPTY_MAP);
               EcoreUtil.resolveAll(contextEPackage);
               final EPackage enrichedEPackage = helper.transform(contextEPackage, gtf, DemoclesMethodBodyHandler::initResourceSetForDemocles, this.resourceSet,
                     this::getTreeIterator);

               /*
                * Goal: For each pattern invocation, generate and store the search plan Needed: * pattern invocations (=
                * pattern+adornment) within each operation in the package * PatternMatcher
                */
               // TODO@rkluge: Add support for nested packages
               //               enrichedEPackage.getEClassifiers().stream()//
               //                     .filter(eClassifier -> eClassifier instanceof EClass)//
               //                     .map(eClassifier -> EClass.class.cast(eClassifier)).forEach(eClass -> {
               //                        eClass.getEOperations().forEach(eOperation -> {
               //                           AdapterResource controlFlowResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eOperation,
               //                                 DemoclesMethodBodyHandler.CONTROL_FLOW_FILE_EXTENSION);
               //                           controlFlowResource.getAllContents().forEachRemaining(eObject -> {
               //                              if (eObject instanceof PatternInvocation)
               //                              {
               //                                 final PatternInvocation invocation = (PatternInvocation) eObject;
               //                                 final Adornment adornment = calculateAdornment(invocation);
               //                                 final Pattern pattern = invocation.getPattern();
               //                                 final boolean isMultipleMatch = invocation.isMultipleMatch();
               //                                 scopeValidatorConfiguration.getBlackPatternMatcher().generateSearchPlan(pattern, adornment, isMultipleMatch);
               //                              }
               //                           });
               //                        });
               //                     });

               // save context
               enrichedEcoreResource.getContents().clear();
               enrichedEcoreResource.getContents().add(enrichedEPackage);
               final Map<String, String> saveOptions = new HashMap<>();
               saveOptions.put(Resource.OPTION_LINE_DELIMITER, WorkspaceHelper.DEFAULT_RESOURCE_LINE_DELIMITER);
               enrichedEcoreResource.save(saveOptions);

            }

         }
         EcoreUtil.resolveAll(this.resourceSet);
      } catch (final IOException e)
      {
         return new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), "Problems while loading MOSL-GT specification", e);
      }
      return Status.OK_STATUS;
   }

   private TreeIterator<EObject> getTreeIterator(EOperation eOperation)
   {
      AdapterResource controlFlowResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eOperation, DemoclesMethodBodyHandler.CONTROL_FLOW_FILE_EXTENSION);
      return controlFlowResource.getAllContents();
   }

   //   //TODO@rkluge: Possible code duplication
   //   private Adornment calculateAdornment(PatternInvocation invocation)
   //   {
   //      Adornment adornment = new Adornment(invocation.getParameters().size());
   //      int i = 0;
   //      for (VariableReference variableRef : invocation.getParameters())
   //      {
   //         final int value = variableRef.isFree() ? Adornment.FREE : Adornment.BOUND;
   //         adornment.set(i, value);
   //      }
   //      return adornment;
   //   }

   @Override
   public String getTaskName()
   {
      return "MOSL-GT Weaving";
   }

}
