/*
 * The FUJABA ToolSuite project:
 *
 *   FUJABA is the acronym for 'From Uml to Java And Back Again'
 *   and originally aims to provide an environment for round-trip
 *   engineering using UML as visual programming language. During
 *   the last years, the environment has become a base for several
 *   research activities, e.g. distributed software, database
 *   systems, modelling mechanical and electrical systems and
 *   their simulation. Thus, the environment has become a project,
 *   where this source code is part of. Further details are avail-
 *   able via http://www.fujaba.de
 *
 *      Copyright (C) Fujaba Development Group
 *
 *   This library is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU Lesser General Public
 *   License as published by the Free Software Foundation; either
 *   version 2.1 of the License, or (at your option) any later version.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the Free
 *   Software Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 *   MA 02111-1307, USA or download the license under
 *   http://www.gnu.org/copyleft/lesser.html
 *
 * WARRANTY:
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 * Contact address:
 *
 *   Fujaba Management Board
 *   Software Engineering Group
 *   University of Paderborn
 *   Warburgerstr. 100
 *   D-33098 Paderborn
 *   Germany
 *
 *   URL  : http://www.fujaba.de
 *   email: info@fujaba.de
 *
 */
package de.uni_paderborn.fujaba.preferences;

import java.io.File;

/**
 * This interface defines all preference keys needed for the Fujaba core.
 *
 * @author Oleg Travkin
 */
public interface FujabaCorePreferenceKeys
{
   public final static String PREFIX = "de.fujaba.general.";
   public final static String WORKSPACE_FOLDER = PREFIX + "WorkspaceFolder";
   public final static String TEMPORARY_FOLDER = PREFIX + "TemporaryFolder";

   //NOT USED
   public final static String VIEW_FILTER_FOLDER = PREFIX + "ViewFilterFolder";
   public final static String JDK_FOLDER = PREFIX + "JDKFolder";

   /**
    * Property for ignoring wrong/missing JDK Folder
    */
   public final static String JDK_FOLDER_IGNORE = PREFIX + "JDKFolderIgnore";
   public final static String USE_EXT_EDITOR = PREFIX + "UseExtEditor";
   public final static String EXTERNAL_EDITOR = PREFIX + "ExternalEditor";

   /**
    * versioning
    */
   public final static String REPOSITORY_ACTIVATED = PREFIX + "RepositoryActivated";

   /**
    * recover
    */
   public final static String RECOVER_ACTIVATED = PREFIX + "RecoverActivated";   
   public final static String AUTOGENERATE_ASSOCS = PREFIX + "AutogenerateAssocs";  
   public final static String AUTOGENERATE_ATTR = PREFIX + "AutogenerateAttr"; 
   public final static String AUTOGENERATE_CLASSES = PREFIX + "AutogenerateClasses";

   /**
    * fpr (buttons in gui)
    */
   //   NOT USED
   public final static String FPR_ACTIVATED = PREFIX + "FPRActivated";
   public final static String CREATE_PROJECT_BACKUP = PREFIX + "CreateProjectBackup";
   public final static String EXPORT_TO_WORKSPACE_FOLDER = PREFIX + "export.WorkspaceFolder";
   public final static String EXPORT_TO_PROJECT_FOLDER = PREFIX + "export.ProjectFolder";
   public final static String EXPORT_TO_TEMPORARY_FOLDER = PREFIX + "export.TemporaryFolder";
   public final static String EXPORT_TO_CUSTOM_FOLDER = PREFIX + "export.CustomFolder";
   public final static String COMMAND_LINE_PARSING = PREFIX + "CommandLineParsing";
   public final static String PROCESS_HISTORY = PREFIX + "ProcessHistory";
   public final static String FILE_HISTORY = PREFIX + "FileHistory";
   public final static String LOOK_AND_FEEL = PREFIX + "LookAndFeel";
   
   public final static String CODEGEN_TARGETS = "de.fujaba.codegen.targets";
   public final static String CODE_GEN_TARGET_JAVA = "java";

//   public final static String COLOR_ACTIVITY_BACKGROUND = "de.fujaba.color.ActivityBackground";
//   public final static String COLOR_ASSIGNMENT = "de.fujaba.color.Assignment";
//   public final static String COLOR_CLASS_BACKGROUND = "de.fujaba.color.ClassBackground";
//   public final static String COLOR_COLLAPSE_ANCHOR = "de.fujaba.color.CollapseAnchor";
//   public final static String COLOR_COMMENT_BACKGROUND = "de.fujaba.color.CommentBackground";
//   public final static String COLOR_CREATION = "de.fujaba.color.Creation";
//   public final static String COLOR_DEFAULT_BACKGROUND = "de.fujaba.color.DefaultBackground";
//   public final static String COLOR_DEFAULT_BORDER = "de.fujaba.color.DefaultBorder";
//   public final static String COLOR_DEFAULT_FOCUS = "de.fujaba.color.DefaultFocus";
//   public final static String COLOR_DEFAULT_FOREGROUND = "de.fujaba.color.DefaultForeground";
//   public final static String COLOR_DEFAULT_SELECTED = "de.fujaba.color.DefaultSelected";
//   public final static String COLOR_DELETION = "de.fujaba.color.Deletion";
//   public final static String COLOR_DIAGRAM_BACKGROUND = "de.fujaba.color.DiagramBackground";
//   public final static String COLOR_EDITOR_BORDER = "de.fujaba.color.EditorBorder";
//   public final static String COLOR_ERROR = "de.fujaba.color.Error";
//   public final static String COLOR_MULTILINK = "de.fujaba.color.Multilink";
//   public final static String COLOR_OPTIONAL = "de.fujaba.color.Optional";
//   public final static String COLOR_PACKAGE_BACKGROUND = "de.fujaba.color.PackageBackground";
//   public final static String COLOR_SELECTED_BORDER = "de.fujaba.color.SelectedBorder";
//   public final static String COLOR_SELECTION_RECT = "de.fujaba.color.SelectionRect";
//   public final static String COLOR_STORY_BACKGROUND = "de.fujaba.color.StoryBackground";
   public final static String DEBUG_MODE = "de.fujaba.debug.Mode";
   public final static String DEBUG_SAVE_GENERATED = "de.fujaba.debug.SaveGenerated";
   public final static String DEBUG_SAVE_LOAD = "de.fujaba.debug.level.SaveLoad";
   public final static String DEBUG_CODE_GENERATION = "de.fujaba.debug.level.CodeGeneration"; //NOT USED
   public final static String DEBUG_DISPLAY = "de.fujaba.debug.level.Display"; //NOT USED
   public final static String DEBUG_ANALYZING = "de.fujaba.debug.level.Analyzing"; //NOT USED
   public final static String DEBUG_UML = "de.fujaba.debug.level.UML"; //NOT USED
   public final static String DEBUG_PARSING = "de.fujaba.debug.level.Parsing"; //NOT USED
   public final static String DEBUG_GUI = "de.fujaba.debug.level.GUI";
   public final static String DEBUG_REMOVE_YOU = "de.fujaba.debug.level.RemoveYouMethods";
   public final static String DEBUG_PLUGINS = "de.fujaba.debug.level.Plugins";
   public final static String DEBUG_DEFAULT = "de.fujaba.debug.level.Default";
//   public final static String LAYOUT_COLLAPSE_CLASSES = "de.fujaba.layout.CollapseClasses";
//   public final static String LAYOUT_HORIZONTAL_DISTANCE = "de.fujaba.layout.HorizontalDistance";
//   public final static String LAYOUT_MINIMUM_WIDTH = "de.fujaba.layout.MinimumWidth";
//   public final static String LAYOUT_NUMBER_OF_ITERATIONS = "de.fujaba.layout.NumberOfIterations";
//   public final static String LAYOUT_ACTIVITY_DIAGRAM_LAYOUT = "de.fujaba.layout.SelectedActivityDiagramLayout";
//   public final static String LAYOUT_SELECTED_LAYOUT = "de.fujaba.layout.SelectedLayout";
//   public final static String LAYOUT_SHOW_ROLENAMES = "de.fujaba.layout.ShowRoleNames";
//   public final static String LAYOUT_STRETCHFACTOR = "de.fujaba.layout.StretchFactor";
//   public final static String LAYOUT_VERTICAL_DISTANCE = "de.fujaba.layout.VerticalDistance";

//   public final static String LAYOUT_TREELAYOUT = "TreeLayout";
//   public final static String LAYOUT_SPRINGEMBEDDER = "SpringEmbedder";
//   public final static String LAYOUT_CONTROLFLOW = "Control flow Layout";
//   public final static String LAYOUT_CLASS_DIAGRAM = "Class diagram Layout";

   public final static String DEFAULT_PLUGIN_FOLDER = new File (
      System.getProperty ("user.dir"), "plugins").getPath(); //NOT USED
   public final static String SERVER_LIST_PROPERTY = "de.fujaba.plugins.ServerList";
   public final static String DOWNLOAD_URL_PROPERTY = "de.fujaba.plugins.DownloadURL";
   public final static String PLUGIN_FOLDERS_PROPERTY = "de.fujaba.plugins.Folders";

   public final static String COOBRA_REPOSITORIES = "de.fujaba.coobra.RepositoryHistory";

//   public final static String ICON_VISIBILITY_PREFIX = "de.fujaba.icons.visibility.";
//   public final static String PUB_METH = "PublicMethod";
//   public final static String PUB_FIELD = "PublicAttribute";
//   public final static String PROT_METH = "ProtectedMethod";
//   public final static String PROT_FIELD = "ProtectedAttribute";
//   public final static String PRIV_METH = "PrivateMethod";
//   public final static String PRIV_FIELD = "PrivateAttribute";
//   public final static String PACK_METH = "PackageMethod";
//   public final static String PACK_FIELD = "PackageAttribute";
//   public final static String UFIELD = "user attribute";
//   public final static String ICON_ATTRIBUTE_PRIVATE = ICON_VISIBILITY_PREFIX + PRIV_FIELD;
//   public final static String ICON_ATTRIBUTE_PUBLIC = ICON_VISIBILITY_PREFIX + PUB_FIELD;
//   public final static String ICON_ATTRIBUTE_PROTECTED = ICON_VISIBILITY_PREFIX + PROT_FIELD;
//   public final static String ICON_ATTRIBUTE_PACKAGE = ICON_VISIBILITY_PREFIX + PACK_FIELD;
//   public final static String ICON_METHOD_PRIVATE = ICON_VISIBILITY_PREFIX + PRIV_METH;
//   public final static String ICON_METHOD_PUBLIC = ICON_VISIBILITY_PREFIX + PUB_METH;
//   public final static String ICON_METHOD_PROTECTED = ICON_VISIBILITY_PREFIX + PROT_METH;
//   public final static String ICON_METHOD_PACKAGE = ICON_VISIBILITY_PREFIX + PACK_METH;
//   public final static String ICON_USER_FIELD = ICON_VISIBILITY_PREFIX + UFIELD;

   public final static String CUSTOM_EXPORT_FOLDER = PREFIX + "CustomExportFolder";
   public final static String PROJECT_EXPORT_FOLDER = PREFIX + "ProjectExportFolder";

   public final static String JAVA_INTERNAL_VARIABLE_PREFIX = "de.fujaba.java.InternalVariablePrefix";

//   public final static String TOOLBAR_ENABLED_PREFIX = "de.fujaba.toolbar.enabled.";
//   public final static String TOOLBAR_LOCATION_PREFIX = "de.fujaba.toolbar.location.";
//
//   public final static String TOOLBAR_LOCATION_MAIN = "MAIN_TOOLBAR_PANEL";
//   public final static String TOOLBAR_LOCATION_DIAGRAM = "DIAGRAM_TOOLBAR_PANEL";
   
   public final static String PROPERTY_EDITOR_ACTIVATED = PREFIX + "PropertyEditor.activated";

//   public final static String NO_ENTRY = "no entry";

   public final static String DIALOG_XML = "properties/dialog.xml";
   
   /**
    * codegen property keys
    * */
   public final static String PROPERTY_PREFIX = "de.fujaba.codegeneration.directory.";
   public final static String PROJECT_ID = "de.fujaba.project.id";
}
