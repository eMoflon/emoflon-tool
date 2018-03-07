package org.moflon.ide.texteditor.builders;

import java.util.HashMap;

public interface TextEditorBuilderHelper {

	public HashMap<String, String> getModelPathsToTextPaths();

	public void onSave(String texFilePath);

	public void syncText(String modelFilePath);

	public void getProblems();

}
