package org.moflon.gt.ide;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.gervarro.eclipse.task.ITask;
import org.moflon.codegen.MethodBodyHandler;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.codegen.eclipse.MoflonCodeGeneratorPhase;
import org.moflon.compiler.sdm.democles.DemoclesMethodBodyHandler;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.gt.mosl.codeadapter.CodeadapterTrafo;
import org.moflon.gt.mosl.codeadapter.PatternBuilder;
import org.moflon.gt.mosl.codeadapter.config.TransformationConfiguration;
import org.moflon.gt.mosl.codeadapter.statementrules.ConditionStatementRule;
import org.moflon.gt.mosl.codeadapter.statementrules.DoLoopStatementRule;
import org.moflon.gt.mosl.codeadapter.statementrules.ForLoopStatementRule;
import org.moflon.gt.mosl.codeadapter.statementrules.ObjectVariableDefinitionRule;
import org.moflon.gt.mosl.codeadapter.statementrules.PatternStatementRule;
import org.moflon.gt.mosl.codeadapter.statementrules.ReturnStatementRule;
import org.moflon.gt.mosl.codeadapter.statementrules.WhileLoopStatementRule;
import org.moflon.gt.mosl.codeadapter.transformplanrules.BlackTransformPlanRule;
import org.moflon.gt.mosl.codeadapter.transformplanrules.GreenTransformPlanRule;
import org.moflon.gt.mosl.codeadapter.transformplanrules.RedTransformPlanRule;
import org.moflon.gt.mosl.codeadapter.utils.PatternKind;
import org.moflon.gt.mosl.moslgt.GraphTransformationFile;
import org.moflon.sdm.compiler.democles.validation.scope.PatternMatcher;

/**
 * This task weaves the control flow and pattern matching models stored in textual syntax with the plain metamodel
 * 
 * The task is configured with the top-level {@link EPackage} of the metamodel.
 * 
 * @author Roland Kluge - Initial implementation
 * 
 * @see #run(IProgressMonitor)
 */
public class MOSLGTWeavingTask implements ITask, MoflonCodeGeneratorPhase
{
   /**
    * The top-level {@link EPackage} of the ongoing build process
    */
   private EPackage ePackage;

   /**
    * The common {@link ResourceSet} of the ongoing build process
    */
   private ResourceSet resourceSet;

   private IProject project;
   
   private TransformationConfiguration transformationConfiguration;

   @Override
   public String getTaskName()
   {
      return "eMoflon-GT Transformation task";
   }

   @Override
   public IStatus run(final IProject project, Resource rsource, MethodBodyHandler methodBodyHandler, IProgressMonitor monitor)
   {
      this.project = project;
      this.ePackage = (EPackage) rsource.getContents().get(0);
      this.resourceSet = ePackage.eResource().getResourceSet();
      this.transformationConfiguration = new TransformationConfiguration();
      final Map<String, PatternMatcher> patternMatcherConfiguration = methodBodyHandler.getPatternMatcherConfiguration();
      this.transformationConfiguration.getPatternMatchingController().setSearchplanGenerators(patternMatcherConfiguration);

      DemoclesMethodBodyHandler.initResourceSetForDemocles(resourceSet);
      return run(monitor);
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
      final IStatus mgtLoadStatus = this.loadMgtFiles();
      if (mgtLoadStatus.matches(IStatus.ERROR))
         return mgtLoadStatus;

      // This status collects all information about the weaving process for 'ePackage'
      final MultiStatus weavingMultiStatus = new MultiStatus(CodeGeneratorPlugin.getModuleID(), 0, getTaskName() + " failed", null);
      final List<EClass> eClasses = CodeGeneratorPlugin.getEClasses(this.ePackage);

      final SubMonitor subMon = SubMonitor.convert(monitor, getTaskName() + " in " + ePackage.getName(), eClasses.size());
      processMgtFiles(subMon);
      return weavingMultiStatus.isOK() ? Status.OK_STATUS : weavingMultiStatus;
   }

   /**
    * This routine identifies and loads all .mgt files in the current project.
    * 
    * For each .mgt file, an appropriate resource is created in this generator's resource set ({@link #getResourceSet()}
    */
   private IStatus loadMgtFiles()
   {
      try
      {
         getProject().accept(new IResourceVisitor() {

            @Override
            public boolean visit(IResource resource) throws CoreException
            {
               if (resource.getName().equals("bin"))
                  return false;

               if (isMOSLGTFile(resource))
               {
                  final Resource schemaResource = (Resource) getResourceSet()
                        .createResource(URI.createPlatformResourceURI(resource.getAdapter(IFile.class).getFullPath().toString(), false));
                  try
                  {
                     schemaResource.load(null);
                  } catch (final IOException e)
                  {
                     throw new CoreException(
                           new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), "Problems while loading MOSL-GT specification", e));
                  }
               }
               return true;
            }

            private boolean isMOSLGTFile(IResource resource)
            {
               final IFile file = resource.getAdapter(IFile.class);
               return resource != null && resource.exists() && file != null && WorkspaceHelper.EMOFLON_GT_EXTENSION.equals(file.getFileExtension());
            }

         });
         EcoreUtil.resolveAll(this.getResourceSet());
      } catch (final CoreException e)
      {
         return new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), e.getMessage(), e);
      }

      return Status.OK_STATUS;
   }

   private ResourceSet getResourceSet()
   {
      return this.resourceSet;
   }

   private IProject getProject()
   {
      return project;
   }

   private IStatus processMgtFiles(final IProgressMonitor monitor)
   {
      try
      {
         CodeadapterTrafo helper = new CodeadapterTrafo();
         registerTransformationRules(this.transformationConfiguration);

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
               final EPackage enrichedEPackage = helper.transform(contextEPackage, gtf, this.resourceSet, this.transformationConfiguration);

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

   private void registerTransformationRules(TransformationConfiguration transformationConfiguration2)
   {
      PatternBuilder.getInstance().addTransformPlanRule(PatternKind.BLACK, new BlackTransformPlanRule());
      PatternBuilder.getInstance().addTransformPlanRule(PatternKind.GREEN, new GreenTransformPlanRule());
      PatternBuilder.getInstance().addTransformPlanRule(PatternKind.RED, new RedTransformPlanRule());

      //@formatter:off
      Arrays.asList(
            new ReturnStatementRule(),
            new PatternStatementRule(),
            new ConditionStatementRule(),
            new WhileLoopStatementRule(),
            new ForLoopStatementRule(),
            new DoLoopStatementRule(),
            new ObjectVariableDefinitionRule()
            ).stream().forEach(rule -> transformationConfiguration.getStatementCreationController().registerTransformationRule(rule));
      //@formatter:on
   }
}
