package org.moflon.visualization.ui;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;
import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.ide.ui.admin.handlers.AbstractCommandHandler;
import org.moflon.ide.visualization.dot.sdm.SDMVisualizationPlugin;
import org.moflon.ide.visualization.dot.tgg.VisualizationPlugin;
import org.moflon.ide.visualization.dot.tgg.runtime.DotTggRuntimePlugin;
import org.moflon.ide.visualization.dot.tgg.runtime.RuntimePackage;
import org.moflon.moca.MocaTreeSorter;
import org.moflon.moca.ModelToTreeConverter;
import org.moflon.moca.dot.unparser.DotUnparserAdapter;
import org.moflon.moca.dot.unparser.SimpleDotUnparserAdapter;
import org.moflon.tgg.algorithm.synchronization.SynchronizationHelper;
import org.moflon.util.eMoflonSDMUtil;

import DotToSDMLanguageTGG.DotToSDMLanguageTGGPackage;
import DotToTGGTGG.DotToTGGTGGPackage;
import Moca.CodeAdapter;
import Moca.MocaFactory;
import Moca.unparser.Unparser;
import MocaTree.File;
import MocaTree.Folder;
import MocaTree.MocaTreeFactory;
import MocaTree.impl.MocaTreeFactoryImpl;
import SDMLanguage.activities.Activity;
import SDMLanguage.activities.ActivityNode;
import SDMLanguage.activities.StartNode;
import org.moflon.tgg.language.TripleGraphGrammar;
import org.moflon.tgg.runtime.PrecedenceStructure;

public class ConvertToDotHandler extends AbstractCommandHandler
{

   private static final String RELATIVE_PATH_TO_DOT = "/resources/bin/dot.exe";

   @Override
   public Object execute(final ExecutionEvent event) throws ExecutionException
   {
      return execute(HandlerUtil.getCurrentSelectionChecked(event));
   }

   private Object execute(final ISelection selection)
   {
      logger.debug("Creating dot representation.");

      if (selection instanceof StructuredSelection)
      {
         final StructuredSelection structuredSelection = (StructuredSelection) selection;
         final Object element = structuredSelection.getFirstElement();
         final IFile model = castToIFile(element);

         if (null != model)
         {
            modelToDot(model);

            try
            {
               model.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
            } catch (final CoreException e)
            {
               e.printStackTrace();
            }
         }
      }
      return null;
   }

   /**
    * Returns whether dot is on the path.
    */
   public boolean isDotInstalled()
   {
      final String cmd = getAbsolutePathToDotExe() + " -h";
      boolean isDotInstalled = false;
      try
      {
         Runtime.getRuntime().exec(cmd);
         isDotInstalled = true;
      } catch (final IOException e)
      {
         e.printStackTrace();
         logger.error("Could not find dot. Please check if it has been installed correctly.");
         MessageDialog.openError(Display.getCurrent().getActiveShell(), "Missing dot.exe",
               "The visualization needs dot to be installed. Please download it at http://graphviz.org/ and put in on your path.");

      }
      return isDotInstalled;
   }

   private String getAbsolutePathToDotExe()
   {
      final URL pathToExe = MoflonUtilitiesActivator.getPathRelToPlugIn(RELATIVE_PATH_TO_DOT, VisualizationPlugin.getDefault().getPluginId());
      final String absolutePathToDot = new java.io.File(pathToExe.getPath()).getAbsolutePath();
      return "\"" + absolutePathToDot + "\"";
   }

   private IFile castToIFile(final Object element)
   {
      IFile model = null;
      if (element instanceof IFile)
      {
         model = (IFile) element;
      }
      return model;
   }

   /**
    * loads a model and creates .dot-files
    * 
    * @param model
    */
   private void modelToDot(final IFile model)
   {
      if (!isDotInstalled())
      {
         return;
      }

      // Load model
      try
      {
         final EObject root = eMoflonEMFUtil.loadModelWithDependenciesAndCrossReferencer(URI.createPlatformResourceURI(model.getFullPath().toString(), true),
               eMoflonEMFUtil.createDefaultResourceSet());
         logger.debug("Loaded: " + root);

         final String visualizationFolderPath = model.getProject().getLocation().toOSString() + java.io.File.separator + "visualisation";

         if (root instanceof TripleGraphGrammar)
         {
            final TripleGraphGrammar tgg = (TripleGraphGrammar) root;

            logger.debug("Visualizing TGG '" + tgg.getName() + "'...");
            treeToDot(new DotUnparserAdapter(), visualizationFolderPath, modelToTree_TGG(tgg, model));
         } else if (root instanceof EPackage)
         {
            final EPackage ePackage = (EPackage) root;

            logger.debug("Visualizing meta-model '" + ePackage.getName() + "'...");
            treeToDot(new DotUnparserAdapter(), visualizationFolderPath, modelToTree_Ecore(ePackage, model));
         } else if(root instanceof org.moflon.tgg.runtime.PrecedenceStructure){
           final org.moflon.tgg.runtime.PrecedenceStructure ps = (org.moflon.tgg.runtime.PrecedenceStructure) root;
           
           logger.debug("Visualizing precedence structure ...");
           treeToDot(new DotUnparserAdapter(), visualizationFolderPath, modelToTree_PS(ps, model));
         } else
         {
            logger.debug("Unknown type of model. Trying to visualize its abstract syntax as a simple object diagram...");
            final Folder folder = MocaTreeFactory.eINSTANCE.createFolder();
            final File file = new ModelToTreeConverter().modelToTree(root, true);
            folder.getFile().add(file); 
            folder.setName(file.getName());
            file.setName(file.getName() + ".dot");
            treeToDot(new SimpleDotUnparserAdapter(), visualizationFolderPath, folder);
         }
         logger.debug("Completed generation of dot files. Please wait a moment until rendering is finished.");

      } catch (final Exception e)
      {
         logger.error(e.getClass().getName() + " during visualization: " + e.getMessage());
         logger.debug(Arrays.asList(e.getStackTrace()).stream().map(StackTraceElement::toString).collect(Collectors.joining("\n")));
      }

   }


   /**
    * unparses the data of the folder with the given unparser and calls for svg creation
    * 
    * @param unparser
    * @param targetFolder
    */
   private void treeToDot(final Unparser unparser, final String visualizationFolderPath, final Folder targetFolder)
   {
      final CodeAdapter codeAdapter = MocaFactory.eINSTANCE.createCodeAdapter();
      codeAdapter.getUnparser().add(unparser);
      codeAdapter.unparse(visualizationFolderPath, targetFolder);
      createSVG(visualizationFolderPath, targetFolder);
   }

   
   private Folder modelToTree_PS(final PrecedenceStructure ps, final IFile model)
   {
      Folder folder = null;
      
      final URL pathToPlugin = MoflonUtilitiesActivator.getPathRelToPlugIn("/", DotTggRuntimePlugin.getDefault().getPluginId());
      SynchronizationHelper helper = new SynchronizationHelper(RuntimePackage.eINSTANCE, pathToPlugin.getFile(), ps.eResource().getResourceSet());
      helper.setSrc(ps);
      helper.integrateForward();
      
      folder = (Folder) helper.getTrg();
      folder.setName(getFileName(model));
      return folder;
   }

   /**
    * handles the creation of a dot structure contained in a MocaTree that creates a dot visualisation for each tgg rule
    * 
    * @param tgg
    * @return
    */
   private Folder modelToTree_TGG(final TripleGraphGrammar tgg, final IFile model)
   {
      Folder folder = null;
      try
      {
         final URL pathToPlugin = MoflonUtilitiesActivator.getPathRelToPlugIn("/", VisualizationPlugin.getDefault().getPluginId());
         SynchronizationHelper helper = new SynchronizationHelper(DotToTGGTGGPackage.eINSTANCE, pathToPlugin.getFile(), tgg.eResource().getResourceSet());
         helper.setTrg(tgg);
         
         logger.debug("Loaded rules");

         helper.integrateBackward();
         logger.debug("TGG backward transformation completed");

         folder = (Folder) helper.getSrc();
         folder.setName(getFileName(model));
         MocaTreeSorter.sort(folder);
      } catch (final Exception e)
      {
         e.printStackTrace();
         logger.debug(Arrays.toString(e.getStackTrace()));
      }

      return folder;
   }

   /**
    * handles the creation of a dot structure contained in a MocaTree that creates a dot visualisation for every
    * operation of every class
    * 
    * @param epackage
    * @param model
    * @return
    */
   private Folder modelToTree_Ecore(final EPackage epackage, final IFile model)
   {
      final Folder rootFolder = MocaTreeFactoryImpl.eINSTANCE.createFolder();
      rootFolder.setName(getFileName(model));

      return modelToTree_Ecore(epackage, rootFolder);
   }

   private Folder modelToTree_Ecore(final EPackage epackage, final Folder folder)
   {
      // translate all supported content of a package
      final Iterator<EClassifier> classifierIterator = epackage.getEClassifiers().iterator();
      while (classifierIterator.hasNext())
      {
         final EClassifier eclassifier = classifierIterator.next();
         if (eclassifier instanceof EClass)
         {
            Folder subFolder = MocaTreeFactoryImpl.eINSTANCE.createFolder();
            subFolder = modelToTree_Ecore((EClass) eclassifier, subFolder);
            if (!subFolder.getFile().isEmpty())
            {
               subFolder.setName(eclassifier.getName());
               folder.getSubFolder().add(subFolder);
            }
         }
      }

      // recursive call to translate all subfolders
      final Iterator<EPackage> folderIterator = epackage.getESubpackages().iterator();
      while (folderIterator.hasNext())
      {
         final EPackage subpackage = folderIterator.next();
         Folder subFolder = MocaTreeFactoryImpl.eINSTANCE.createFolder();
         subFolder = modelToTree_Ecore(subpackage, subFolder);
         if (!subFolder.getFile().isEmpty() || !subFolder.getSubFolder().isEmpty())
         {
            subFolder.setName(subpackage.getName());
            folder.getSubFolder().add(subFolder);
         }
      }

      return folder;
   }

   private Folder modelToTree_Ecore(final EClass eclass, final Folder folder)
   {
      final Iterator<EOperation> eOpIterator = eclass.getEOperations().iterator();
      Folder patternFolder = null;
      while (eOpIterator.hasNext())
      {
         final EOperation eOperation = eOpIterator.next();
         Activity activity = null;
         // if sdm has been found -> integrate and connect file to folder structure
         if ((activity = getActivity(eOperation)) != null)
         {  
            try
            {
               // initialize tgg and integrate
               final URL pathToPlugin = MoflonUtilitiesActivator.getPathRelToPlugIn("/", SDMVisualizationPlugin.getDefault().getPluginId());
               SynchronizationHelper helper = new SynchronizationHelper(DotToSDMLanguageTGGPackage.eINSTANCE, pathToPlugin.getFile(), eclass.eResource().getResourceSet());
               

               logger.debug("Retrieved: " + pathToPlugin + ", " + pathToPlugin.getFile() + ", " + pathToPlugin.getPath());

               for (final ActivityNode node : activity.getOwnedActivityNode())
               {
                  if (node instanceof StartNode)
                  {
                     String name = eclass.getName() + "::" + eOperation.getName() + "(";
                     for (int i = 0; i < eOperation.getEParameters().size(); i++)
                     {
                        final EParameter eParam = eOperation.getEParameters().get(i);
                        if (i != 0)
                           name += node.getName() + ", ";
                        name += eParam.getEType().getName() + " " + eParam.getName();
                     }
                     name += ")";
                     node.setName(name);
                  }
               }

               helper.setTrg(activity);
               helper.integrateBackward();

               final Folder genFolder = (Folder) helper.getSrc();

               final File file = genFolder.getFile().get(0);
               file.setName(eOperation.getName() + getParameterString(eOperation) + ".dot");
               MocaTreeSorter.sort(file);
               folder.getFile().add(file);
               if (patternFolder == null)
               {
                  patternFolder = genFolder.getSubFolder().get(0);
                  folder.getSubFolder().add(patternFolder);
               } else
               {
                  patternFolder.getFile().addAll(genFolder.getSubFolder().get(0).getFile());
               }
            } catch (final Exception e)
            {
               e.printStackTrace();
            }

         }
      }

      return folder;
   }

   private String getFileName(final IFile model)
   {
      return model.getFullPath().lastSegment();
   }

   /**
    * tries to create .svg out of .dot-files that are found in the specified location that is given by path + folder
    * 
    * @param path
    * @param folder
    */
   private void createSVG(final String path, final Folder folder)
   {
      // recursive method for all subfolders
      for (final Folder subFolder : folder.getSubFolder())
      {
         createSVG(path + java.io.File.separator + folder.getName(), subFolder);
      }

      // create svg for all .dot files
      for (final File file : folder.getFile())
      {
         final String filePath = path + java.io.File.separator + folder.getName() + java.io.File.separator + file.getName();
         final String cmd = getAbsolutePathToDotExe() + " -Tsvg \"" + filePath + "\" -o \"" + filePath.substring(0, filePath.lastIndexOf(".dot")) +  ".svg" + "\"";
         try
         {
            logger.debug("Executing: " + cmd);
            Runtime.getRuntime().exec(cmd);
         } catch (final IOException e)
         {
            e.printStackTrace();
            logger.error("Could not find dot. Please check if it has been installed correctly.");
            MessageDialog.openError(Display.getCurrent().getActiveShell(), "Missing dot.exe",
                  "The visualization needs dot to be installed. Please download it at http://graphviz.org/ and put in on your path.");
         }
      }
   }

   private String getParameterString(final EOperation eoperation)
   {
      String parameters = "(";
      for (int i = 0; i < eoperation.getEParameters().size(); i++)
      {
         final String eParamType = eoperation.getEParameters().get(i).getEType().getName();
         if (i != 0)
            parameters += ", ";

         parameters += eParamType;
      }
      return parameters + ")";
   }

   private Activity getActivity(final EOperation eoperation)
   {
      final ResourceSet resourceSet = new ResourceSetImpl();
      eMoflonEMFUtil.addToResourceSet(resourceSet, eoperation.eContainer());

      final Activity activity = eMoflonSDMUtil.getActivityFromEOperation(eoperation);

      if (activity == null)
         logger.debug("The eOperation " + eoperation.getName() + " does not contain an SDM so I have nothing to visualize!");

      return activity;
   }
}
