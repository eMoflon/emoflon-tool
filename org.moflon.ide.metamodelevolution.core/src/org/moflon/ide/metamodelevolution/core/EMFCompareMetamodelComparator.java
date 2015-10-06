package org.moflon.ide.metamodelevolution.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.AttributeChange;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.DifferenceSource;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.diff.DefaultDiffEngine;
import org.eclipse.emf.compare.diff.DiffBuilder;
import org.eclipse.emf.compare.diff.IDiffEngine;
import org.eclipse.emf.compare.diff.IDiffProcessor;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.compare.utils.ReferenceUtil;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.WorkspaceHelper;

public class EMFCompareMetamodelComparator implements MetamodelComparator
{

   protected IProject project;
   protected ResourceSet resourceSetMMold; // TODO@settl: work with packages instead of resourcesets
   protected ResourceSet resourceSetMMnew;
   public Map<String, String> delta;

   public EMFCompareMetamodelComparator(final IProject project)
   {
      this.project = project;
      this.delta = new HashMap<String, String>();
   }

   public Map<String, String> compare()
   {
      // TODO@settl: return proper delta
      if (loadMetamodels()) 
      {
    	  compare(resourceSetMMold, resourceSetMMnew);
    	  if (!delta.isEmpty()) 
    		  return delta;
      }     
      return null;
   }

   @Override
   public EMFCompareMetamodelDelta compare(final EPackage oldMetaModel, final EPackage newMetaModel)
   {

      IComparisonScope scope = new DefaultComparisonScope(oldMetaModel, newMetaModel, null);

      // custom diffprocessor that ignores renaming - for testing purposes
      IDiffProcessor ignoreNameDiffProcessor = new DiffBuilder() {
         @Override
         public void attributeChange(final Match match, final EAttribute attribute, final Object value, final DifferenceKind kind, final DifferenceSource source)
         {
            if (attribute != EcorePackage.Literals.ENAMED_ELEMENT__NAME)
            {
               super.attributeChange(match, attribute, value, kind, source);
            }
         }
      };

      IDiffEngine diffEngine = new DefaultDiffEngine(ignoreNameDiffProcessor);
      // Uncomment this line to use your own custom Diffprocessor
      // EMFCompare metamodelComparator = EMFCompare.builder().setDiffEngine(diffEngine).build();

      EMFCompare metamodelComparator = EMFCompare.builder().build();
      Comparison comparison = metamodelComparator.compare(scope);

      createDeltaMap(comparison.getDifferences());
      printDiffs(comparison);
      return null;
   }

   public EMFCompareMetamodelDelta compare(final ResourceSet oldMetaModel, final ResourceSet newMetaModel)
   {

      IComparisonScope scope = new DefaultComparisonScope(oldMetaModel, newMetaModel, null);

      // custom diffprocessor that ignores renaming - for testing purposes
      IDiffProcessor ignoreNameDiffProcessor = new DiffBuilder() {
         @Override
         public void attributeChange(final Match match, final EAttribute attribute, final Object value, final DifferenceKind kind, final DifferenceSource source)
         {
            if (attribute != EcorePackage.Literals.ENAMED_ELEMENT__NAME)
            {
               super.attributeChange(match, attribute, value, kind, source);
            }
         }
      };

      IDiffEngine diffEngine = new DefaultDiffEngine(ignoreNameDiffProcessor);
      // Uncomment this line to use your own custom Diffprocessor
      // EMFCompare metamodelComparator = EMFCompare.builder().setDiffEngine(diffEngine).build();

      EMFCompare metamodelComparator = EMFCompare.builder().build();
      Comparison comparison = metamodelComparator.compare(scope);

      createDeltaMap(comparison.getDifferences());
      printDiffs(comparison);
      return null;
   }

   protected boolean loadMetamodels()
   {

      URI projectURI = lookupProjectURI(project);
      URI metamodelURI = URI.createURI(getEcoreFileAndHandleMissingFile().getProjectRelativePath().toString()).resolve(projectURI);

      String oldMetamodelURITempString = metamodelURI.toString();
      oldMetamodelURITempString = oldMetamodelURITempString.substring(0, oldMetamodelURITempString.lastIndexOf(".")) + ".previous.ecore";
      URI previousMMURI = URI.createURI(oldMetamodelURITempString);

      resourceSetMMold = new ResourceSetImpl();
      resourceSetMMnew = new ResourceSetImpl();

      /*Resource oldMMResource = null;
      Resource newMMResource = null;*/
      try
      {
         resourceSetMMold.getResource(previousMMURI, true);
         resourceSetMMnew.getResource(metamodelURI, true);
         return true;

      } catch (final Exception e)
      {
         e.printStackTrace();
         return false;
      }
      // TODO@settl: this returns always the same package, not the previous one, why?

      // EPackage oldMetamodelPackage = (EPackage)oldMMResource.getContents().get(0);
      // EPackage newMetamodelPackage = (EPackage)newMMResource.getContents().get(0);
   }

   protected void createDeltaMap(final List<Diff> differences)
   {
      for (Diff diffElement : differences)
      {
         if (diffElement.getKind() == DifferenceKind.CHANGE && diffElement instanceof AttributeChange)
         {

            final EAttribute attribute = ((AttributeChange) diffElement).getAttribute();
            final String attributeChangevalue = ((AttributeChange) diffElement).getValue().toString();

            DifferenceSource source = diffElement.getSource();
            if (source == DifferenceSource.LEFT)
            {
               final EObject containerRight = diffElement.getMatch().getRight();
               final String rightValue = (String) ReferenceUtil.safeEGet(containerRight, attribute);
               delta.put(attributeChangevalue, rightValue);
               delta.put("changedValue", attributeChangevalue);
            }
         }
      }
   }

   // for debugging
   protected void printDiffs(final Comparison comparison)
   {

      List<Diff> differences = comparison.getDifferences();

      for (Diff diffElement : differences)
      {
         System.out.println("diffElement.getKind(): " + diffElement.getKind());
         System.out.println("diffElement.getMatch(): " + diffElement.getMatch());

         if (diffElement.getKind() == DifferenceKind.CHANGE && diffElement instanceof AttributeChange)
         {
            // TODO@settl: sometimes renaming a class is recognized as a MOVE, why?
            final EAttribute attribute = ((AttributeChange) diffElement).getAttribute();

            final EObject containerLeft = diffElement.getMatch().getLeft();
            final EObject containerRight = diffElement.getMatch().getRight();

            final String attributeChangevalue = ((AttributeChange) diffElement).getValue().toString();
            final Object leftValue = ReferenceUtil.safeEGet(containerLeft, attribute);
            final Object rightValue = ReferenceUtil.safeEGet(containerRight, attribute);

            System.out.println("leftValue: " + leftValue); // old
            System.out.println("rightValue: " + rightValue); // new
            System.out.println("attributeChangevalue: " + attributeChangevalue); // old

            final EObject containerRightClass = diffElement.getMatch().getRight();
            DifferenceSource source = diffElement.getSource();
            if (source == DifferenceSource.LEFT)
            {
               System.out.println("source is LEFT");
               System.out.println("containerLeft: " + containerLeft);
               System.out.println("containerRight: " + containerRight);
            }
         }
      }

   }

   public static final URI lookupProjectURI(final IProject project)
   {
      IPluginModelBase pluginModel = PluginRegistry.findModel(project);
      if (pluginModel != null)
      {
         // Plugin projects in the workspace
         String pluginID = pluginModel.getBundleDescription().getSymbolicName();
         return URI.createPlatformPluginURI(pluginID + "/", true);
      } else
      {
         // Regular projects in the workspace
         return URI.createPlatformResourceURI(project.getName() + "/", true);
      }
   }

   protected IFile getEcoreFile()
   {
      return getEcoreFile(project);
   }

   private boolean doesEcoreFileExist()
   {
      return getEcoreFile().exists();
   }

   protected IFile getEcoreFileAndHandleMissingFile()
   {
      if (!doesEcoreFileExist())
         createMarkersForMissingEcoreFile();
      return getEcoreFile();
   }

   private void createMarkersForMissingEcoreFile()
   {
      IFile ecoreFile = getEcoreFile();
      // logger.error("Unable to generate code: " + ecoreFile + " does not exist in project!");
   }

   public static IFile getEcoreFile(final IProject p)
   {
      String ecoreFileName = MoflonUtil.getDefaultNameOfFileInProjectWithoutExtension(p.getName());
      return p.getFolder(WorkspaceHelper.MODEL_FOLDER).getFile(ecoreFileName + WorkspaceHelper.ECORE_FILE_EXTENSION);
   }
}
