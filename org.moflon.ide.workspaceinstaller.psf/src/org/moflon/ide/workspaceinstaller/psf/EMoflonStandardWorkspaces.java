package org.moflon.ide.workspaceinstaller.psf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class EMoflonStandardWorkspaces
{
   /*
    * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! !!!!!!!!!!!!!!!!!
    * 
    * ATTENTION: The following names are also used in the plugin.xml and in the autotest script to load the correct
    * workspaces.
    * 
    * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! !!!!!!!!!!!!!!!!!
    */
   public static final String PSF_FILES_ROOT = "resources/psf/";

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

   public static final String HANDBOOK_EXAMPLE_PART3_START = "HandbookPart3Start";

   public static final String HANDBOOK_EXAMPLE_PART3_FINAL = "HandbookPart3Final";

   public static final String HANDBOOK_EXAMPLE_PART4_START = "HandbookPart4Start";

   public static final String MODULE_ALL = "ModuleAll";

   public static final String MODULE_ALL_INCL_MOSL = "ModuleAllInclMOSL";

   public static final String MODULE_CORE_SDM = "ModuleCoreSDM";

   public static final String MODULE_TGG = "ModuleTGG";

   public static final String MODULE_TGG_CORE = "ModuleTGGCore";

   public static final String MODULE_TGG_MOSL = "ModuleTGGMOSL";

   public static final String MODULE_COEVOLUTION = "ModuleCoEvolution";

   public static final String MODULE_VISUALIZATION = "ModuleVisualization";

   private static final String MODULE_IDE = "ModuleIDE";

   private static final String MODULE_META = "ModuleMeta";

   /**
    * This is a mapping from module names (see above) to {@link #PSF_FILES_ROOT}-relative paths.
    */
   private static final Map<String, List<String>> PATH_LOOKUP = new HashMap<>();

   static
   {
      // Basic test workspace modules
      PATH_LOOKUP.put(TEST_WORKSPACE_TRANSFORMATION_ZOO_0_NAME, Arrays.asList("tests/TestSuiteZoo0.psf"));
      PATH_LOOKUP.put(TEST_WORKSPACE_TRANSFORMATION_ZOO_1_NAME, Arrays.asList("tests/TestSuiteZoo1.psf"));
      PATH_LOOKUP.put(TEST_WORKSPACE_TGG_0_NAME, Arrays.asList("tests/TestSuiteTGG0.psf"));
      PATH_LOOKUP.put(TEST_WORKSPACE_TGG_1_NAME, Arrays.asList("tests/TestSuiteTGG1.psf"));
      PATH_LOOKUP.put(TEST_WORKSPACE_MISC_NAME, Arrays.asList("tests/TestSuiteMisc.psf"));
      PATH_LOOKUP.put(TEST_WORKSPACE_DEMOCLES_0_NAME, Arrays.asList("tests/TestSuiteDemocles0.psf"));

      // Basic developer workspace modules
      PATH_LOOKUP.put(MODULE_CORE_SDM, Arrays.asList("development/Core_SDM_Modules.psf", "development/TGG_Core_Modules.psf"));
      PATH_LOOKUP.put(MODULE_META, Arrays.asList("development/Meta_Modules.psf"));
      PATH_LOOKUP.put(MODULE_IDE, Arrays.asList("development/IDE_Rest_Modules.psf"));
      PATH_LOOKUP.put(MODULE_TGG_CORE, Arrays.asList("development/TGG_Core_Modules.psf"));
      PATH_LOOKUP.put(MODULE_TGG_MOSL, Arrays.asList("development/TGG_MOSL_Modules.psf"));
      PATH_LOOKUP.put(MODULE_VISUALIZATION, Arrays.asList("development/IDE_Vis_Modules.psf"));
      PATH_LOOKUP.put(MODULE_COEVOLUTION, Arrays.asList("development/IDE_CoEvolution_Modules.psf"));
      PATH_LOOKUP.put(MODULE_DOCUMENTATION, Arrays.asList("development/Documentation.psf"));

      PATH_LOOKUP.put(MODULE_TGG, joinLists(MODULE_TGG_CORE, MODULE_TGG_MOSL));
      PATH_LOOKUP.put(MODULE_ALL, joinLists(MODULE_META, MODULE_IDE, MODULE_CORE_SDM, MODULE_TGG_CORE, MODULE_VISUALIZATION, MODULE_COEVOLUTION)); // "development/All_Modules.psf"
      PATH_LOOKUP.put(MODULE_ALL_INCL_MOSL, joinLists(MODULE_ALL, MODULE_TGG_MOSL)); // "development/All_Modules_incl_MOSL.psf"


      // Handbook workspace modules
      PATH_LOOKUP.put(DEMO_WORKSPACE_NAME, Arrays.asList("examples/DemoWorkspace.psf"));
      PATH_LOOKUP.put(HANDBOOK_EXAMPLE_FINAL, Arrays.asList("examples/HandbookExample_FinalSolution.psf"));
      PATH_LOOKUP.put(HANDBOOK_EXAMPLE_GUI, Arrays.asList("examples/HandbookExample_LeitnersBoxGui.psf"));
      PATH_LOOKUP.put(HANDBOOK_EXAMPLE_PART3_START, Arrays.asList("examples/HandbookExample_Part3_Start.psf"));
      PATH_LOOKUP.put(HANDBOOK_EXAMPLE_PART3_FINAL, Arrays.asList("examples/HandbookExample_Part3_Final.psf"));
      PATH_LOOKUP.put(HANDBOOK_EXAMPLE_PART4_START, Arrays.asList("examples/HandbookExample_Part4_FreshStart.psf"));
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
      {
         final List<String> projectRelativePsfPaths = psfPaths.stream().map(p -> PSF_FILES_ROOT + p).collect(Collectors.toList());
         return projectRelativePsfPaths;
      }
   }

   private static List<String> joinLists(final String... moduleNames)
   {
      return Arrays.asList(moduleNames).stream().map(m -> PATH_LOOKUP.get(m)).reduce(new ArrayList<String>(), createListJoiner());
   }

   private static BinaryOperator<List<String>> createListJoiner()
   {
      return (u, v) -> {
         u.addAll(v);
         return u;
      };
   }

}