package org.moflon.ide.workspaceinstaller.psf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class EMoflonStandardWorkspaces {
	/**
	 * This string separates the name of a workspace from the name of the branch to
	 * be used If this separator is missing from a workspace name, a sensible
	 * default choice is not to modify the branch name in the PSF file.
	 *
	 * Important: the separator must be a valid character within directory names
	 * (NOT: /, \, #,...)
	 */
	public static final String BRANCH_NAME_SEPARATOR = "@";

	/*
	 * !!! Danger Zone !!! !!! Danger Zone !!! !!! Danger Zone !!! !!! Danger Zone
	 * !!! !!! Danger Zone !!!
	 *
	 * ATTENTION: The following names are also used in the plugin.xml and in the
	 * autotest script to load the correct workspaces.
	 */
	public static final String PSF_FILES_ROOT = "resources/psf/";

	public static final String TEST_WORKSPACE_TRANSFORMATION_ZOO_1_NAME = "TransformationZoo_1";

	public static final String TEST_WORKSPACE_TRANSFORMATION_ZOO_0_NAME = "TransformationZoo_0";

	public static final String TEST_WORKSPACE_TGG_1_NAME = "TestWorkspace_TGG_1";

	public static final String TEST_WORKSPACE_TGG_0_NAME = "TestWorkspace_TGG_0";

	public static final String TEST_WORKSPACE_MISC_NAME = "TestWorkspace_Misc";

	public static final String TEST_WORKSPACE_DEMOCLES_0_NAME = "TestWorkspace_Democles_0";

	public static final String TEST_DEMO_HANDBOOK = "TestWorkspace_DemoAndHandbook";

	public static final String TEST_GRAVITY = "TestWorkspace_Gravity";

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

	public static final String MODULE_VISUALIZATION = "ModuleVisualization";

	private static final String MODULE_IDE = "ModuleIDE";

	private static final String MODULE_META = "ModuleMeta";
	/*
	 * End of Danger Zone
	 */

	/**
	 * This is a mapping from module names (see above) to
	 * {@link #PSF_FILES_ROOT}-relative paths.
	 */
	private static final Map<String, List<String>> PATH_LOOKUP = new HashMap<>();

	static {

		// Basic developer workspace modules
		addPathLookup(MODULE_CORE_SDM,
				Arrays.asList("development/Core_SDM_Modules.psf", "development/TGG_Core_Modules.psf"));
		addPathLookup(MODULE_META, Arrays.asList("development/Meta_Modules.psf"));
		addPathLookup(MODULE_IDE, Arrays.asList("development/IDE_Rest_Modules.psf"));
		addPathLookup(MODULE_TGG_CORE, Arrays.asList("development/TGG_Core_Modules.psf"));
		addPathLookup(MODULE_TGG_MOSL, Arrays.asList("development/TGG_MOSL_Modules.psf"));
		addPathLookup(MODULE_VISUALIZATION, Arrays.asList("development/IDE_Vis_Modules.psf"));
		addPathLookup(MODULE_DOCUMENTATION, Arrays.asList("development/Documentation.psf"));

		addPathLookup(MODULE_TGG, joinLists(MODULE_TGG_CORE, MODULE_TGG_MOSL));
		addPathLookup(MODULE_ALL,
				joinLists(MODULE_META, MODULE_IDE, MODULE_CORE_SDM, MODULE_TGG_CORE, MODULE_VISUALIZATION)); // "development/All_Modules.psf"
		addPathLookup(MODULE_ALL_INCL_MOSL, joinLists(MODULE_ALL, MODULE_TGG_MOSL)); // "development/All_Modules_incl_MOSL.psf"

		// Handbook workspace modules
		addPathLookup(DEMO_WORKSPACE_NAME, Arrays.asList("examples/DemoWorkspace.psf"));
		addPathLookup(HANDBOOK_EXAMPLE_FINAL, Arrays.asList("examples/HandbookExample_FinalSolution.psf"));
		addPathLookup(HANDBOOK_EXAMPLE_GUI, Arrays.asList("examples/HandbookExample_LeitnersBoxGui.psf"));
		addPathLookup(HANDBOOK_EXAMPLE_PART3_START, Arrays.asList("examples/HandbookExample_Part3_Start.psf"));
		addPathLookup(HANDBOOK_EXAMPLE_PART3_FINAL, Arrays.asList("examples/HandbookExample_Part3_Final.psf"));
		addPathLookup(HANDBOOK_EXAMPLE_PART4_START, Arrays.asList("examples/HandbookExample_Part4_FreshStart.psf"));

		// Basic test workspace modules
		addPathLookup(TEST_WORKSPACE_TRANSFORMATION_ZOO_0_NAME, Arrays.asList("tests/TestSuiteZoo0.psf"));
		addPathLookup(TEST_WORKSPACE_TRANSFORMATION_ZOO_1_NAME, Arrays.asList("tests/TestSuiteZoo1.psf"));
		addPathLookup(TEST_WORKSPACE_TGG_0_NAME, Arrays.asList("tests/TestSuiteTGG0.psf"));
		addPathLookup(TEST_WORKSPACE_TGG_1_NAME, Arrays.asList("tests/TestSuiteTGG1.psf"));
		addPathLookup(TEST_WORKSPACE_MISC_NAME, Arrays.asList("tests/TestSuiteMisc.psf"));
		addPathLookup(TEST_WORKSPACE_DEMOCLES_0_NAME, Arrays.asList("tests/TestSuiteDemocles0.psf"));
		addPathLookup(TEST_DEMO_HANDBOOK, joinLists(DEMO_WORKSPACE_NAME, HANDBOOK_EXAMPLE_FINAL));
		addPathLookup(TEST_GRAVITY, Arrays.asList("tests/TestSuiteGravity.psf"));
	}

	/**
	 * Returns the project-relative path to the PSF file that corresponds to the
	 * given workspace name.
	 *
	 * If no such path exists, the list is empty
	 */
	public static List<String> getPathToPsfFileForWorkspace(final String workspaceName) {
		final String basicWorkspaceName = extractBasicWorkspaceName(workspaceName);
		final List<String> psfPaths = PATH_LOOKUP.get(basicWorkspaceName);
		if (psfPaths == null)
			return Arrays.asList();
		else {
			final List<String> projectRelativePsfPaths = psfPaths.stream().map(p -> PSF_FILES_ROOT + p)
					.collect(Collectors.toList());
			return projectRelativePsfPaths;
		}
	}

	/**
	 * Registers the given list of paths to PSF files for the given module name
	 *
	 * @param moduleName
	 * @param paths
	 */
	private static void addPathLookup(String moduleName, List<String> paths) {
		PATH_LOOKUP.put(moduleName, paths);
	}

	/**
	 * Joins the paths corresponding to the given module names to a new (flat) list.
	 *
	 * The resulting list preserves the order of the paths of the given modules (and
	 * their corresponding paths) but does not contain duplicates. Only the first
	 * path of a set of duplicates is kept.
	 */
	private static List<String> joinLists(final String... moduleNames) {
		final List<String> intermediateResult = new ArrayList<>(Arrays.asList(moduleNames).stream()
				.map(m -> PATH_LOOKUP.get(m)).reduce(new ArrayList<String>(), createListJoiner()));
		final List<String> duplicateFilteredList = new ArrayList<String>();
		for (final String path : intermediateResult) {
			if (!duplicateFilteredList.contains(path))
				duplicateFilteredList.add(path);
		}
		return duplicateFilteredList;
	}

	/**
	 * Creates a binary operator on string lists that uses
	 * {@link Collection#addAll(Collection)} to add all elements of the second
	 * operand to the first operand
	 *
	 * @return
	 */
	private static BinaryOperator<List<String>> createListJoiner() {
		return (u, v) -> {
			u.addAll(v);
			return u;
		};
	}

	/**
	 * Extracts the component of the workspace name that describes the desired
	 * branch to use
	 *
	 * Anything beyond the {@link #BRANCH_NAME_SEPARATOR} is interpreted as branch
	 * name. If {@link #BRANCH_NAME_SEPARATOR} does not appear within the
	 * workspaceName, then null is returned If {@link #BRANCH_NAME_SEPARATOR} is the
	 * last character, then the result is also null.
	 *
	 * @param workspaceName
	 *            the raw workspace name
	 * @return the branch specificator as described above
	 */
	public static String extractCustomBranchName(String workspaceName) {
		String[] segments = workspaceName.split(Pattern.quote(BRANCH_NAME_SEPARATOR), 2);
		if (segments.length == 2 && !segments[1].isEmpty())
			return segments[1];
		return null;
	}

	/**
	 * Returns the name part of the workspace name (i.e., without the branch
	 * specificator)
	 *
	 * @param workspaceName
	 *            the raw workspace name
	 * @return the name part
	 */
	public static String extractBasicWorkspaceName(final String workspaceName) {
		return workspaceName.replaceAll(Pattern.quote(BRANCH_NAME_SEPARATOR) + ".*", "");
	}

}