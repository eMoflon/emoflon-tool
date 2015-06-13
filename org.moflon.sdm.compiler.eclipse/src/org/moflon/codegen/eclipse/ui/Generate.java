package org.moflon.codegen.eclipse.ui;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.codegen.eclipse.MoflonCodeGenerator;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.eclipse.job.ProgressMonitoringJob;

public class Generate extends AbstractHandler {

	@Override
   public Object execute(final ExecutionEvent event) throws ExecutionException {
		return execute(HandlerUtil.getCurrentSelectionChecked(event));
	}

	public Object execute(final ISelection selection) throws ExecutionException {
		if (selection instanceof TreeSelection) {
			final TreeSelection treeSelection = (TreeSelection) selection;
			for (final Iterator<?> selectionIterator = treeSelection.iterator(); selectionIterator.hasNext();) {
				final Object element = selectionIterator.next();
				if (element instanceof IFile) {
					final IFile file = (IFile) element;
					final ResourceSet resourceSet = CodeGeneratorPlugin.createDefaultResourceSet();
					eMoflonEMFUtil.installCrossReferencers(resourceSet);
					final MoflonCodeGenerator codeGenerationTask =
							new MoflonCodeGenerator(file, resourceSet);
					final Job job =
							new ProgressMonitoringJob(CodeGeneratorPlugin.getModuleID(), codeGenerationTask);
					final boolean runInBackground = PlatformUI.getPreferenceStore().getBoolean("RUN_IN_BACKGROUND");
					if (!runInBackground) {
						// Run in foreground
						PlatformUI.getWorkbench().getProgressService().showInDialog(null, job);
					}
					job.schedule();
				}
			}
		}
		return null;
	}

	@Override
   public boolean isEnabled() {
		return super.isEnabled();
	}
}
