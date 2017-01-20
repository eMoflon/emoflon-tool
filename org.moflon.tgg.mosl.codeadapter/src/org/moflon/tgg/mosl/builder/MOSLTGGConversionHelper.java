package org.moflon.tgg.mosl.builder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.tgg.algorithm.configuration.PGSavingConfigurator;
import org.moflon.tgg.language.TripleGraphGrammar;
import org.moflon.tgg.mosl.codeadapter.org.moflon.tie.CodeadapterPostProcessBackwardHelper;
import org.moflon.tgg.mosl.codeadapter.org.moflon.tie.CodeadapterPostProcessForwardHelper;
import org.moflon.tgg.mosl.codeadapter.org.moflon.tie.CodeadapterTrafo;
import org.moflon.tgg.mosl.defaults.AttrCondDefLibraryProvider;
import org.moflon.tgg.mosl.tgg.AttrCond;
import org.moflon.tgg.mosl.tgg.AttrCondDef;
import org.moflon.tgg.mosl.tgg.Rule;
import org.moflon.tgg.mosl.tgg.TripleGraphGrammarFile;
import org.moflon.tgg.tggproject.TGGProject;
import org.moflon.tgg.tggproject.TggprojectFactory;
import org.moflon.util.plugins.manifest.PluginURIToResourceURIRemapper;

public class MOSLTGGConversionHelper extends AbstractHandler
{
   private static Logger logger = Logger.getLogger(MOSLTGGConversionHelper.class);

   /**
    * The purpose of this {@link URIHandlerImpl} is to handle "pre.ecore" files in the same way as "ecore" files.
    */
   public class PreEcoreHidingURIHandler extends URIHandlerImpl
   {
      @Override
      public URI deresolve(URI uri)
      {
         if (hasLastButOnePrefix(uri, "pre"))
         {
            // Trim ".pre .ecore" and add ".ecore"
            return uri.trimFileExtension().trimFileExtension().appendFileExtension("ecore");
         }
         
         return uri;
      }

      /**
       * Returns true if the last but one suffix equals lastButOneSuffix
       * @param uri the URI to check
       * @param lastButOneSuffix the expected suffix
       * @return
       */
      private boolean hasLastButOnePrefix(URI uri, String lastButOneSuffix)
      {
         return uri.trimFileExtension().fileExtension() != null && uri.trimFileExtension().fileExtension().equals(lastButOneSuffix);
      }
   }

   Resource generateTGGModel(IResource resource)
   {
      try
      {
         final IProject project = resource.getProject();

         final IFolder moslFolder = IFolder.class.cast(resource);
         final XtextResourceSet resourceSet = new XtextResourceSet();

         AttrCondDefLibraryProvider.syncAttrCondDefLibrary(project);

         TripleGraphGrammarFile xtextParsedTGG = createTGGFileAndLoadSchema(resourceSet, moslFolder);
         if (xtextParsedTGG != null)
         {
            loadAllRulesToTGGFile(xtextParsedTGG, resourceSet, moslFolder);
            addAttrCondDefLibraryReferencesToSchema(xtextParsedTGG);

            // Save intermediate result of XText parsing
            Map<Object, Object> options = new HashMap<Object, Object>();
            options.put(XMLResource.OPTION_URI_HANDLER, new PreEcoreHidingURIHandler());

            // Invoke TGG forward transformation to produce TGG model
            String pathToThisPlugin = MoflonUtilitiesActivator.getPathRelToPlugIn("/", WorkspaceHelper.getPluginId(getClass())).getFile();

            CodeadapterTrafo helper = new CodeadapterTrafo(pathToThisPlugin);
            helper.getResourceSet().getResources().add(xtextParsedTGG.eResource());
            helper.setSrc(xtextParsedTGG);
            helper.setVerbose(false);
            helper.integrateForward();

            CodeadapterPostProcessForwardHelper postProcessHelper = new CodeadapterPostProcessForwardHelper();
            postProcessHelper.postProcessForward(helper);

            TGGProject tggProject = (TGGProject) helper.getTrg();
            if (tggProject != null)
            {
               return saveInternalTGGModelToXMI(tggProject, resourceSet, options, project.getName());
            }
         }
      } catch (Exception e)
      {
         LogUtils.error(logger, e);
      }
      return null;
   }

   private void addAttrCondDefLibraryReferencesToSchema(TripleGraphGrammarFile xtextParsedTGG)
   {
      EList<AttrCondDef> usedAttrCondDefs = new BasicEList<AttrCondDef>();
      for (Rule rule : xtextParsedTGG.getRules())
      {
         for (AttrCond attrCond : rule.getAttrConditions())
         {
            if (!usedAttrCondDefs.contains(attrCond.getName()) && !attrCond.getName().isUserDefined())
            {
               usedAttrCondDefs.add(attrCond.getName());
            }
         }
      }
      xtextParsedTGG.getSchema().getAttributeCondDefs().addAll(usedAttrCondDefs);
   }

   private void loadAllRulesToTGGFile(TripleGraphGrammarFile xtextParsedTGG, XtextResourceSet resourceSet, IFolder moslFolder) throws CoreException, IOException
   {
      if (moslFolder.exists())
      {
         EList<Rule> rules = new BasicEList<Rule>();

         for (IResource iResource : moslFolder.members())
         {
            if (iResource instanceof IFile)
            {
               Collection<Rule> rulesFromFile = loadRules(iResource, resourceSet, moslFolder);
               rules.addAll(rulesFromFile);
            } else if (iResource instanceof IFolder)
            {
               loadAllRulesToTGGFile(xtextParsedTGG, resourceSet, IFolder.class.cast(iResource));
            }
         }
         xtextParsedTGG.getRules().addAll(rules);
      }

   }

   private Collection<Rule> loadRules(IResource iResource, XtextResourceSet resourceSet, IFolder moslFolder) throws IOException
   {
      IFile ruleFile = (IFile) iResource;
      if (ruleFile.getFileExtension().equals(WorkspaceHelper.MOSL_TGG_EXTENSION))
      {
         XtextResource ruleRes = (XtextResource) resourceSet.getResource(URI.createPlatformResourceURI(ruleFile.getFullPath().toString(), true), true);
         EcoreUtil.resolveAll(resourceSet);

         EObject tggFile = ruleRes.getContents().get(0);
         if (tggFile instanceof TripleGraphGrammarFile)
         {
            TripleGraphGrammarFile tggFileWithRules = (TripleGraphGrammarFile) tggFile;
            return new ArrayList<Rule>(tggFileWithRules.getRules());
         }
      }
      return Collections.<Rule> emptyList();
   }

   /**
    * Loads a TGG grammar from the given folder.
    * 
    * The method first searches for a file named 'Schema.tgg' or else uses some other file with a ".tgg" extension 
    * @param resourceSet
    * @param moslFolder
    * @return
    * @throws IOException
    * @throws CoreException
    */
   private TripleGraphGrammarFile createTGGFileAndLoadSchema(XtextResourceSet resourceSet, IFolder moslFolder) throws IOException, CoreException
   {
      final IFile schemaFile = moslFolder.getFile("Schema." + WorkspaceHelper.MOSL_TGG_EXTENSION);

      if (schemaFile.exists())
      {
         return loadTggFromFile(resourceSet, schemaFile);
      } else
      {
         for (IResource resource : moslFolder.members())
         {
            if (resource instanceof IFile)
            {
               final IFile otherSchemaFile = IFile.class.cast(resource);
               if (otherSchemaFile.getFileExtension().equals(WorkspaceHelper.MOSL_TGG_EXTENSION))
               {
                  TripleGraphGrammarFile tgg = loadTggFromFile(resourceSet, otherSchemaFile);
                  if (tgg.getSchema() != null)
                     return tgg;
               }
            }
         }
         return null;
      }
   }

   private TripleGraphGrammarFile loadTggFromFile(XtextResourceSet resourceSet, IFile schemaFile) throws IOException
   {
      XtextResource schemaResource = (XtextResource) resourceSet.createResource(URI.createPlatformResourceURI(schemaFile.getFullPath().toString(), false));
      schemaResource.load(null);
      EcoreUtil.resolveAll(resourceSet);
      TripleGraphGrammarFile tgg = (TripleGraphGrammarFile) schemaResource.getContents().get(0);
      return tgg;
   }

   /**
    * Tries to convert the first selected item from a "tgg.xmi" file to a MOSL-TGG specification
    */
   @Override
   public Object execute(ExecutionEvent event) throws ExecutionException
   {
      try
      {
         ISelection selection = HandlerUtil.getCurrentSelection(event);

         if (selection instanceof IStructuredSelection)
         {
            Object file = ((IStructuredSelection) selection).getFirstElement();
            if (file instanceof IFile)
            {
               IFile tggFile = (IFile) file;

               ResourceSet resourceSet = eMoflonEMFUtil.createDefaultResourceSet();
               PluginURIToResourceURIRemapper.createPluginToResourceMap(resourceSet);
               TGGProject tggProject = createTGGProject(tggFile, resourceSet);
               resourceSet.getResources().forEach(r -> {
                  try
                  {
                     r.load(null);
                  } catch (IOException e)
                  {
                     e.printStackTrace();
                  }
               });
               EcoreUtil.resolveAll(resourceSet);

               String pathToThisPlugin = MoflonUtilitiesActivator.getPathRelToPlugIn("/", WorkspaceHelper.getPluginId(getClass())).getFile();
               CodeadapterTrafo helper = new CodeadapterTrafo(pathToThisPlugin, resourceSet);

               helper.setVerbose(true);
               helper.setConfigurator(new PGSavingConfigurator(helper, tggFile.getProject().getLocation().toString() + "/instances/PG.xmi"));
               helper.setTrg(tggProject);
               helper.integrateBackward();

               CodeadapterPostProcessBackwardHelper postProcessHelper = new CodeadapterPostProcessBackwardHelper();
               postProcessHelper.postProcessBackward(helper);

               TripleGraphGrammarFile tggModel = (TripleGraphGrammarFile) helper.getSrc();
               String projectPath = tggFile.getProject().getFullPath().toString();

               Resource resource = resourceSet
                     .createResource(URI.createPlatformResourceURI(projectPath + "/src/org/moflon/tgg/mosl" + projectPath + ".xmi", true));
               resource.getContents().add(tggModel);
               resource.save(null);

               saveXtextTGGModelToTGGFile(tggModel, tggFile.getProject(), "/src/org/moflon/tgg/mosl" + projectPath + ".tgg");
            }
         }
      } catch (Exception e)
      {
         LogUtils.error(logger, e);
      }

      return null;
   }

   private Resource saveInternalTGGModelToXMI(TGGProject tggProject, XtextResourceSet resourceSet, Map<Object, Object> options, String saveTargetName)
         throws IOException, CoreException
   {
      TripleGraphGrammar tgg = tggProject.getTgg();
      EPackage corrPackage = tggProject.getCorrPackage();

      String file = StringUtils.substringAfterLast(saveTargetName, ".");

      if (file.isEmpty())
      {
         file = StringUtils.capitalize(saveTargetName);
      } else
      {
         file = StringUtils.capitalize(file);
      }

      final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(saveTargetName);
      CodeGeneratorPlugin.createPluginToResourceMapping(resourceSet, project);
      URI relativePreEcoreXmiURI = URI.createURI(MoflonUtil.getDefaultPathToFileInProject(file, ".pre.ecore"));
      URI projectURI = CodeGeneratorPlugin.lookupProjectURI(project);
      URI preEcoreXmiURI = relativePreEcoreXmiURI.resolve(projectURI);
      Resource preEcoreResource = resourceSet.createResource(preEcoreXmiURI);
      preEcoreResource.getContents().add(corrPackage);
      final String prefix = MoflonUtil.allSegmentsButLast(corrPackage.getNsPrefix());
      if (prefix != null && prefix.length() > 0) {
    	  EcoreUtil.setAnnotation(corrPackage, "http://www.eclipse.org/emf/2002/GenModel", "basePackage", prefix);
      }
      preEcoreResource.save(options);

      URI pretggXmiURI = URI.createPlatformResourceURI(saveTargetName + "/" + MoflonUtil.getDefaultPathToFileInProject(file, ".pre.tgg.xmi"), false);
      Resource pretggXmiResource = resourceSet.createResource(pretggXmiURI);
      pretggXmiResource.getContents().add(tgg);
      pretggXmiResource.save(options);
      return preEcoreResource;
   }

   private void saveXtextTGGModelToTGGFile(TripleGraphGrammarFile tggModel, IProject project, String filePath) throws IOException, CoreException
   {
      URI tggFileURI = URI.createPlatformResourceURI(project.getFullPath().toString() + filePath, true);

      XtextResourceSet xtextResourceSet = new XtextResourceSet();
      XtextResource xtextResource = (XtextResource) xtextResourceSet.createResource(tggFileURI);
      AttrCondDefLibraryProvider.syncAttrCondDefLibrary(project);

      xtextResource.getContents().add(tggModel);
      EcoreUtil.resolveAll(xtextResource);

      SaveOptions.Builder options = SaveOptions.newBuilder();
      options.format();
      options.noValidation();
      xtextResource.save(options.getOptions().toOptionsMap());
   }

   private TGGProject createTGGProject(IFile tggFile, ResourceSet resourceSet) throws IOException
   {
      String tggFilePath = tggFile.getFullPath().toString();
      Resource tggEcoreResource = resourceSet.createResource(URI.createPlatformResourceURI(tggFilePath.replace(".tgg.xmi", ".ecore"), true));
      tggEcoreResource.load(null);
      Resource tggModelResource = resourceSet.createResource(URI.createPlatformResourceURI(tggFilePath, true));
      tggModelResource.load(null);
      EcoreUtil.resolveAll(resourceSet);

      TGGProject tggProject = TggprojectFactory.eINSTANCE.createTGGProject();
      tggProject.setCorrPackage((EPackage) tggEcoreResource.getContents().get(0));
      tggProject.setTgg((TripleGraphGrammar) tggModelResource.getContents().get(0));
      Resource tggProjectResource = resourceSet.createResource(URI.createURI("TGGProject"));
      tggProjectResource.getContents().add(tggProject);

      return tggProject;
   }
}
