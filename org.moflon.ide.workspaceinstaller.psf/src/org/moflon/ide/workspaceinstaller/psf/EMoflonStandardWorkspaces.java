package org.moflon.ide.workspaceinstaller.psf;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EMoflonStandardWorkspaces
{
   /*
    * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    * 
    * ATTENTION: The following names are also used in the plugin.xml and in the autotest script to load the correct
    * workspaces.
    * 
    * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    */
   public static final String TEST_WORKSPACE_TRANSFORMATION_ZOO_1_NAME = "TransformationZoo_1";

   public static final String TEST_WORKSPACE_TRANSFORMATION_ZOO_0_NAME = "TransformationZoo_0";

   public static final String TEST_WORKSPACE_TGG_1_NAME = "TestWorkspace_TGG_1";

   public static final String TEST_WORKSPACE_TGG_0_NAME = "TestWorkspace_TGG_0";

   public static final String TEST_WORKSPACE_MISC_NAME = "TestWorkspace_Misc";

   public static final String TEST_WORKSPACE_DEMOCLES_0_NAME = "TestWorkspace_Democles_0";

   public static final String OLD_DEVELOPER_WORKSPACE_NAME = "DeveloperWorkspace";

   public static final String OLD_DEVELOPER_WORKSPACE_TGG_NAME = "DeveloperWorkspace (TGG)";

   public static final String MODULE_DOCUMENTATION = "ModuleDocumentation";

   public static final String DEMO_WORKSPACE_NAME = "eMoflonDemoWorkspace";

   public static final String HANDBOOK_EXAMPLE_FINAL = "HandbookFinal";

   public static final String HANDBOOK_EXAMPLE_GUI = "HandbookGUI";

   public static final String HANDBOOK_EXAMPLE_PART3 = "HandbookPart3";

   public static final String HANDBOOK_EXAMPLE_PART4 = "HandbookPart4";

   public static final String MODULE_ALL = "ModuleAll";

   public static final String MODULE_CORE_SDM = "ModuleCoreSDM";

   public static final String MODULE_TGG = "ModuleTGG";

   public static final String MODULE_COEVOLUTION = "ModuleCoEvolution";

   public static final String MODULE_VISUALIZATION = "ModuleVisualization";

   private static final String MODULE_IDE = "ModuleIDE";

   private static final String MODULE_META = "ModuleMeta";

   private static final Map<String, List<String>> PATH_LOOKUP = new HashMap<>();

   static
   {
      PATH_LOOKUP.put(TEST_WORKSPACE_TRANSFORMATION_ZOO_0_NAME, Arrays.asList("/resources/psf/tests/TestSuiteZoo0.psf"));
      PATH_LOOKUP.put(TEST_WORKSPACE_TRANSFORMATION_ZOO_1_NAME, Arrays.asList("/resources/psf/tests/TestSuiteZoo1.psf"));
      PATH_LOOKUP.put(TEST_WORKSPACE_TGG_0_NAME, Arrays.asList("/resources/psf/tests/TestSuiteTGG0.psf"));
      PATH_LOOKUP.put(TEST_WORKSPACE_TGG_1_NAME, Arrays.asList("/resources/psf/tests/TestSuiteTGG1.psf"));
      PATH_LOOKUP.put(TEST_WORKSPACE_MISC_NAME, Arrays.asList("/resources/psf/tests/TestSuiteMisc.psf"));
      PATH_LOOKUP.put(TEST_WORKSPACE_DEMOCLES_0_NAME, Arrays.asList("/resources/psf/tests/TestSuiteDemocles0.psf"));

      PATH_LOOKUP.put(OLD_DEVELOPER_WORKSPACE_NAME, Arrays.asList("/resources/psf/development/OldDeveloperWorkspace.psf"));
      PATH_LOOKUP.put(OLD_DEVELOPER_WORKSPACE_TGG_NAME, Arrays.asList("/resources/psf/development/OldDeveloperWorkspace_TGG.psf"));
      PATH_LOOKUP.put(MODULE_DOCUMENTATION, Arrays.asList("/resources/psf/development/Documentation.psf"));
      PATH_LOOKUP.put(MODULE_ALL, Arrays.asList("/resources/psf/development/All_Modules.psf"));
      PATH_LOOKUP.put(MODULE_CORE_SDM, Arrays.asList("/resources/psf/development/Core_SDM_Modules.psf"));
      PATH_LOOKUP.put(MODULE_TGG, Arrays.asList("/resources/psf/development/TGG_Modules.psf"));
      PATH_LOOKUP.put(MODULE_VISUALIZATION, Arrays.asList("/resources/psf/development/IDE_Vis_Modules.psf"));
      PATH_LOOKUP.put(MODULE_COEVOLUTION, Arrays.asList("/resources/psf/development/IDE_CoEvolution_Modules.psf"));
      PATH_LOOKUP.put(MODULE_IDE, Arrays.asList("/resources/psf/development/IDE_Rest_Modules.psf"));
      PATH_LOOKUP.put(MODULE_META, Arrays.asList("/resources/psf/development/Meta_Modules.psf"));

      PATH_LOOKUP.put(DEMO_WORKSPACE_NAME, Arrays.asList("/resources/psf/examples/DemoWorkspace.psf"));
      PATH_LOOKUP.put(HANDBOOK_EXAMPLE_FINAL, Arrays.asList("/resources/psf/examples/HandbookExample_FinalSolution.psf"));
      PATH_LOOKUP.put(HANDBOOK_EXAMPLE_GUI, Arrays.asList("/resources/psf/examples/HandbookExample_LeitnersBoxGui.psf"));
      PATH_LOOKUP.put(HANDBOOK_EXAMPLE_PART3, Arrays.asList("/resources/psf/examples/HandbookExample_Part3.psf"));
      PATH_LOOKUP.put(HANDBOOK_EXAMPLE_PART4, Arrays.asList("/resources/psf/examples/HandbookExample_Part4_FreshStart.psf"));
   }

   /**
    * Returns the project-relative path to the PSF file that corresponds to the given workspace name.
    * 
    * If no such path exists, the list is empty
    */
   public static List<String> getPathToPsfFileForWorkspace(final String workspaceName)
   {
      final List<String> psfPaths = PATH_LOOKUP.get(workspaceName);
      if (psfPaths == null)
         return Arrays.asList();
      else
         return psfPaths;
   }

}