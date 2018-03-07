package org.moflon.ide.texteditor.builders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.moflon.core.utilities.LogUtils;

public class TextEditorBuilder extends IncrementalProjectBuilder {
	private static final Logger logger = Logger.getLogger(TextEditorBuilder.class);

	public static Collection<TextEditorBuilderHelper> activeEditors = new ArrayList<>();

	@Override
	protected IProject[] build(final int kind, final Map<String, String> args, final IProgressMonitor monitor)
			throws CoreException {

		final SubMonitor subMon = SubMonitor.convert(monitor, "Building text editor changes", activeEditors.size());
		for (TextEditorBuilderHelper editor : activeEditors) {
			try {
				IResourceDelta delta = getDelta(getProject());
				Vector<IResourceDelta> changedDeltas = getChangedDeltaLeaves(delta, editor);

				// Default behavior
				if (delta != null && changedDeltas.size() == 0) {
					for (IResourceDelta change : getAllDeltaLeaves(delta, editor))
						editor.onSave(change.getResource().getLocation().toString());
				}

				for (IResourceDelta change : changedDeltas) {
					IResource resource = change.getResource();
					IPath path = resource.getLocation();

					IProject project = resource.getProject();
					String projectPath = project.getLocation().toString();

					String modelPath = getPathOfModelToSync(path, editor);
					String textPath = getPathOfTextToSync(path, editor);
					String pathToRefresh = null;
					if (modelPath != null) {
						editor.onSave(path.toString());
						pathToRefresh = modelPath.substring(projectPath.length() + 1);
					} else if (textPath != null) {
						editor.syncText(path.toString());
						pathToRefresh = textPath.substring(projectPath.length() + 1);
					}

					IFile fileToRefresh = project.getFile(pathToRefresh);
					// IFile textFile = project.getFile(path);

					fileToRefresh.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());

					focusEditor(fileToRefresh, 0);
				}

				editor.getProblems();
			} catch (Exception e) {
				LogUtils.error(logger, e);
			}

			subMon.worked(1);
		}

		return null;
	}

	private Collection<IResourceDelta> getAllDeltaLeaves(final IResourceDelta delta,
			final TextEditorBuilderHelper editor) {
		Vector<IResourceDelta> result = new Vector<IResourceDelta>();

		if (delta != null && delta.getResource() != null) {
			result.add(delta);
			for (IResourceDelta child : delta.getAffectedChildren(IResourceDelta.CHANGED))
				result.addAll(getAllDeltaLeaves(child, editor));
		}

		return result;
	}

	private Vector<IResourceDelta> getChangedDeltaLeaves(final IResourceDelta delta,
			final TextEditorBuilderHelper editor) {
		Vector<IResourceDelta> result = new Vector<IResourceDelta>();

		if (delta != null && delta.getResource() != null) {
			IPath deltaPath = delta.getResource().getLocation();
			if (getPathOfTextToSync(deltaPath, editor) != null || getPathOfModelToSync(deltaPath, editor) != null)
				result.add(delta);

			for (IResourceDelta child : delta.getAffectedChildren(IResourceDelta.CHANGED)) {
				result.addAll(getChangedDeltaLeaves(child, editor));
			}
		}

		return result;
	}

	private String getPathOfTextToSync(final IPath modelPath, final TextEditorBuilderHelper editor) {
		String pathStg = modelPath.toString();
		HashMap<String, String> modelToTextPathes = editor.getModelPathsToTextPaths();

		if (modelToTextPathes.containsKey(pathStg))
			return modelToTextPathes.get(pathStg);

		return null;
	}

	private String getPathOfModelToSync(final IPath textPath, final TextEditorBuilderHelper editor) {
		String pathStg = textPath.toString();
		HashMap<String, String> modelToTextPathes = editor.getModelPathsToTextPaths();

		boolean valueFound = false;

		if (modelToTextPathes.containsValue(pathStg)) {
			valueFound = true;
		}

		if (valueFound) {
			for (String modelPath : modelToTextPathes.keySet()) {
				if (modelToTextPathes.get(modelPath).equals(pathStg))
					return modelPath;
			}
		}
		return null;
	}

	private void focusEditor(final IFile fileToRefresh, final int sleepMs) {
		for (IWorkbenchWindow window : PlatformUI.getWorkbench().getWorkbenchWindows()) {
			for (IWorkbenchPage page : window.getPages()) {
				for (final IEditorReference editorRef : page.getEditorReferences()) {
					if (editorRef.getName().equals(fileToRefresh.getName())) {
						Display.getDefault().asyncExec(new Runnable() {
							@Override
							public void run() {

								editorRef.getEditor(true).setFocus();

								try {
									Thread.sleep(sleepMs);
								} catch (InterruptedException e) {
									LogUtils.error(logger, e);
								}
							}
						});
					}
				}
			}
		}
	}

}
