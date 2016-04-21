package org.moflon.ide.workspaceinstaller.psf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EMoflonStandardWorkspaces
{
   public static final String TEST_WORKSPACE_TRANSFORMATION_ZOO_1_NAME = "TransformationZoo_1";

   public static final String TEST_WORKSPACE_TRANSFORMATION_ZOO_0_NAME = "TransformationZoo_0";

   public static final String TEST_WORKSPACE_TGG_1_NAME = "TestWorkspace_TGG_1";

   public static final String TEST_WORKSPACE_TGG_0_NAME = "TestWorkspace_TGG_0";

   public static final String TEST_WORKSPACE_MISC_NAME = "TestWorkspace_Misc";

   public static final String TEST_WORKSPACE_DEMOCLES_0_NAME = "TestWorkspace_Democles_0";

   public static final String DEVELOPER_WORKSPACE_NAME = "DeveloperWorkspace";

   public static final String HANDBOOK_WORKSPACE_NAME = "eMoflonHandbook";

   public static final String DEMO_WORKSPACE_NAME = "eMoflonDemoWorkspace";

   public static final String PSFS_ONLY_NAME = "PSFs only";
   
   public static final String HANDBOOK_EXAMPLE_FINAL = "HandbookFinal";
   
   public static final String HANDBOOK_EXAMPLE_GUI = "HandbookGUI";
   
   public static final String HANDBOOK_EXAMPLE_PART3 = "HandbookPart3";
      
   public static final String HANDBOOK_EXAMPLE_PART4 = "HandbookPart4";

   public static final String PATH_TO_PSFS_ONLY = "/resources/PSFs/psfsOnlyWorkspace.psf";
   
   public static final String PATH_TO_DEVELOPER_PSF = "/resources/PSFs/developerWorkspace.psf";

   public static final String PATH_TO_TESTWORKSPACE_MISC_PSF = "/resources/PSFs/testsuiteWorkspace.psf";

   public static final String PATH_TO_TESTWORKSPACE_TGG_0_PSF = "/resources/PSFs/tggTestSuiteWorkspace0.psf";

   public static final String PATH_TO_TESTWORKSPACE_TGG_1_PSF = "/resources/PSFs/tggTestSuiteWorkspace1.psf";

   public static final String PATH_TO_TRANSFORMATIONZOO_0_PSF = "/resources/PSFs/transformationZoo0.psf";

   public static final String PATH_TO_TRANSFORMATIONZOO_1_PSF = "/resources/PSFs/transformationZoo1.psf";

   public static final String PATH_TO_DEMOCLES_0_PSF = "/resources/PSFs/democles0.psf";

   public static final String PATH_TO_HANDBOOK_PSF = "/resources/PSFs/eMoflonDocumentation.psf";

   private static final String PATH_TO_DEMO_WORKSPACE = "/resources/PSFs/eMoflonDemoWorkspace.psf";
   
   private static final String PATH_TO_HANDBOOK_EXAMPLE_FINAL = "/resources/PSFs/handbookExample_FinalSolution.psf";
   
   private static final String PATH_TO_HANDBOOK_EXAMPLE_GUI = "/resources/PSFs/handbookExample_LeitnersBoxGui.psf";
   
   private static final String PATH_TO_HANDBOOK_EXAMPLE_PART3 = "/resources/PSFs/handbookExample_Part3.psf";
   
   private static final String PATH_TO_HANDBOOK_EXAMPLE_PART4 = "/resources/PSFs/handbookExample_Part4_FreshStart.psf";

   public static List<String> getAllTestWorkspaceNames()
   {
      return Arrays.asList(
            TEST_WORKSPACE_DEMOCLES_0_NAME, 
            TEST_WORKSPACE_MISC_NAME, 
            TEST_WORKSPACE_TGG_0_NAME,
            TEST_WORKSPACE_TGG_1_NAME, 
            TEST_WORKSPACE_TRANSFORMATION_ZOO_0_NAME, 
            TEST_WORKSPACE_TRANSFORMATION_ZOO_1_NAME
      );
   }
   
   public static List<String> getAllMoflonWorkspaces() {
      List<String> workspaceNames = new ArrayList<>();
      workspaceNames.add(DEVELOPER_WORKSPACE_NAME);
      workspaceNames.addAll(getAllTestWorkspaceNames());
      return workspaceNames;
   }

   public static String getPathToPsfFileForWorkspace(final String workspaceName)
   {
      final String path;
      switch (workspaceName)
      {
      case DEVELOPER_WORKSPACE_NAME:
         path = PATH_TO_DEVELOPER_PSF;
         break;
   
      case TEST_WORKSPACE_MISC_NAME:
         path = PATH_TO_TESTWORKSPACE_MISC_PSF;
         break;
   
      case TEST_WORKSPACE_TGG_0_NAME:
   
         path = PATH_TO_TESTWORKSPACE_TGG_0_PSF;
         break;
   
      case TEST_WORKSPACE_TGG_1_NAME:
         path = PATH_TO_TESTWORKSPACE_TGG_1_PSF;
         break;
   
      case TEST_WORKSPACE_TRANSFORMATION_ZOO_0_NAME:
         path = PATH_TO_TRANSFORMATIONZOO_0_PSF;
         break;
   
      case TEST_WORKSPACE_TRANSFORMATION_ZOO_1_NAME:
         path = PATH_TO_TRANSFORMATIONZOO_1_PSF;
         break;
   
      case TEST_WORKSPACE_DEMOCLES_0_NAME:
         path = PATH_TO_DEMOCLES_0_PSF;
         break;
   
      case HANDBOOK_WORKSPACE_NAME:
         path = PATH_TO_HANDBOOK_PSF;
         break;
         
      case PSFS_ONLY_NAME:
         path = PATH_TO_PSFS_ONLY;
         break;
         
      case DEMO_WORKSPACE_NAME: 
         path = PATH_TO_DEMO_WORKSPACE;
         break;
         
      case HANDBOOK_EXAMPLE_FINAL:
         path = PATH_TO_HANDBOOK_EXAMPLE_FINAL;
         break;
      
      case HANDBOOK_EXAMPLE_GUI:
         path = PATH_TO_HANDBOOK_EXAMPLE_GUI;
         break;
      
      case HANDBOOK_EXAMPLE_PART3:
         path = PATH_TO_HANDBOOK_EXAMPLE_PART3;
         break;
         
      case HANDBOOK_EXAMPLE_PART4:
         path = PATH_TO_HANDBOOK_EXAMPLE_PART4;
         break;
         
      default:
         path = null;
         break;
      }
      return path;
   }

}