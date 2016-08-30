package org.moflon.ide.ui.admin.wizards.moca;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.ui.UIActivator;

/**
 * generates a java class in the directory src/org/moflon/moca/
 * for setting text editor properties
 * 
 * @author Amir Naseri
 * @author (last editor) $Author$
 * @version $Revision$ $Date$
 */

public class TextEditorGenerator {


	private static Logger logger = Logger.getLogger(UIActivator.class);

	private IProject project;

	private IFolder textEditorFolder;


	private String getContentOfTextEditor() {
		String content = 
				"package org.moflon.moca;" + "\n" + "\n" +
						"import java.util.Map;"+ "\n" + "\n" +
						"import java.util.HashMap;"+ "\n" + "\n" +
						"/** please notice the following conventions:  " + "\n" +
						"* i. The Path \"org.moflon.moca\" is fixed" + "\n" +
						"* 	and name of this class is fixed and is name of the project + \"Editor\" " + "\n" +
						"* ii. Methods are public static and name of them and their outputs are fixed" + "\n" +
						"*	and they don't have any input arguments" + "\n" +
						"*/" + "\n" + "\n" +
						"public class " + project.getName() + "Editor { " + "\n" +
						"\t" + "public static Map<String, Integer[]> createKeywordColorMap() {" + "\n" +
						"\t" + "\t" + "  Map<String, Integer[]> keywords = new HashMap<String, Integer[]>();" + "\n" +
						"\t" + "\t" + "// Here you can put your keyword-color pair, for example:" + "\n" +
						"\t" + "\t" + "// keywords.put(\"red\", new Integer[]{255,0,0});" + "\n" +
						"\t" + "\t" + "return keywords;" + "\n" +
						"\t" +  "}" + "\n" + "\n" +

						"\t" + "public static String[] getTemplateNames() {" + "\n" +
						"\t" + "\t" + "// Here you give names to your templates, for example: " + "\n" +
						"\t" + "\t" + "// String[] templateNames = {\"if\", \"case\"};" + "\n" +
						"\t" + "\t" + "return new String[0];" + "\n" +
						"\t" +  "}" + "\n" + "\n" +

						"\t" + "public static String[] getTemplateDescriptions(){" + "\n" +
						"\t" + "\t" + "// Here you describe your templates, for example: " + "\n" +
						"\t" + "\t" + "// String[] TemplateDescriptions = {\"if-then-else template\", \"case-of template\"};" + "\n" +
						"\t" + "\t" + "return new String[0];" + "\n" +
						"\t" +  "}" + "\n" + "\n" +

						"\t" + "public static String[] getTemplatePatterns() {" + "\n" +
						"\t" + "\t" + "// Here you can define the content of your template by using ${VAR} as variables, for example: " + "\n" +
						"\t" + "\t" + "// String[] TemplatePatterns = {\"if ${COND}\\n  then ${THEN}\\n  else ${ELSE}\\nend_if\","+ "\n" +
						"\t" + "\t" + "//\"case ${CASETERM} of\\n  ${CONS} : ${TERM1},\\n  other : ${TERM2}\\nend_case\"};" + "\n" +
						"\t" + "\t" + "return new String[0];" + "\n" +
						"\t" +  "}" + "\n" + "\n" +

						"}";

		return content;

	}

	private IProgressMonitor monitor;

	private String message = "";

	private String packagePrefix;

	public TextEditorGenerator(IProject project)
	{
		this.project = project;
		this.monitor = new NullProgressMonitor();
		this.packagePrefix = "org.moflon.moca";
	}

	public String doFinish() throws FileNotFoundException, CoreException
	{
		prepareCodegen();
		createTextEditorClass();
		return message;
	}

	private void createTextEditorClass() throws CoreException 
	{
		// create file name prefix for the text editor
		String fileNamePrefix = textEditorFolder.getProjectRelativePath().toOSString() + "/";

		addFileAndCheckSuccess(fileNamePrefix + project.getName() + "Editor" + ".java", getContentOfTextEditor());


	}

	private void addFileAndCheckSuccess(String fileName, String content) throws CoreException
	{
		if (!addFile(fileName, content))
		{
			message += fileName + " already exists! \n" +
					"Please don't forget to edit appropriately ... \n";
		}
	}

	public IProject getProject() {
		return project;
	}

	private void prepareCodegen()
	{
		/*
		 * ensure package for text editor configuration exist
		 */
		List<String> packageFolders = Arrays.asList(packagePrefix.split("\\."));
		String currentFolder = "src/";
		for (String folder : packageFolders)
		{
			currentFolder += folder + "/";
			createFolder(currentFolder);
		}

		textEditorFolder = createFolder(currentFolder);

	}


	private IFolder createFolder(String path)
	{
		IFolder folder = project.getFolder(path);
		if (!folder.exists())
		{
			try
			{
				return WorkspaceHelper.addFolder(project, path, monitor);
			} catch (CoreException e)
			{
            LogUtils.error(logger, e, "error while creating folder: " + path);
				return null;
			}
		} else
		{
			return folder;
		}
	}

	private boolean addFile(String fileName, String content) throws CoreException
	{
		IFile file = project.getFile(fileName);
		if (!file.exists())
		{
			WorkspaceHelper.addFile(project, fileName, content, monitor);
			return true;
		} else
		{
			return false;
		}
	}
}
