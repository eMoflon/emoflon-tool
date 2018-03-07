package org.moflon.ide.ui.admin.wizards.moca;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.moflon.moca.AbstractFileGenerator;

/**
 * Wizard for creating a Parser and/or Unparser
 */

public class AddParserAndUnparserWizard extends Wizard implements INewWizard {
	private AddParserAndUnparserWizardPage page;

	private IProject project;

	private AbstractFileGenerator generator;

	private static Logger logger = Logger.getLogger(AddParserAndUnparserWizard.class);

	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		// Check if selection is valid (i.e., inside Package Explorer or Navigator
		Object obj = null;
		if (selection instanceof TreeSelection) {
			obj = ((TreeSelection) selection).getFirstElement();
		} else if (selection instanceof StructuredSelection) {
			obj = ((StructuredSelection) selection).getFirstElement();
		}
		// Get what the user selected in the workspace as a resource
		if (obj != null) {
			if (obj instanceof IResource) {
				final IResource resource = (IResource) obj;
				project = resource.getProject();
			} else if (obj instanceof IJavaElement) {
				final IJavaElement javaProject = (IJavaElement) obj;
				project = javaProject.getJavaProject().getProject();
			}
		} else {
			project = null;
		}
	}

	@Override
	public void addPages() {
		page = new AddParserAndUnparserWizardPage(project);
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		if (project != null) {
			generator = new ParserUnparserGenerator(page, project);
			try {
				final String message = generator.doFinish();
				if (message.length() > 0) {
					MessageDialog.openInformation(getShell(), "Creating Parser/Unparser", message);
				}
			} catch (final Exception e) {
				logger.error("error while creating parser/unparser");
				logger.error(ExceptionUtils.getStackTrace(e));
			}
		} else {
			logger.debug("no project was selected!");
		}
		return true;
	}

}
