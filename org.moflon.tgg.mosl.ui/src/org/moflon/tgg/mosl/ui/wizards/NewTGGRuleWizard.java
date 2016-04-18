package org.moflon.tgg.mosl.ui.wizards;

import static org.moflon.core.utilities.WorkspaceHelper.addAllFoldersAndFile;
import static org.moflon.core.utilities.WorkspaceHelper.createSubmonitorWith1Tick;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.INewWizard;
import org.moflon.ide.ui.admin.wizards.metamodel.AbstractMoflonWizard;
import org.moflon.tgg.mosl.defaults.DefaultFilesHelper;

public class NewTGGRuleWizard extends AbstractMoflonWizard implements INewWizard {

	public static final String NEW_TGG_RULE_WIZARD_ID = "org.moflon.tgg.mosl.newTGGRule";
	
	protected NewTGGRuleProjectInfoPage projectInfo;

	@Override
	public void addPages() {
		projectInfo = new NewTGGRuleProjectInfoPage();
		addPage(projectInfo);
	}

	@Override
	protected void doFinish(IProgressMonitor monitor) throws CoreException {
		String ruleContent = DefaultFilesHelper.generateDefaultRule(projectInfo.getSchema(), projectInfo.getRuleName());
		addAllFoldersAndFile(projectInfo.getRuleLocation().getProject(), 
							 projectInfo.getRuleLocation().getProjectRelativePath().append(projectInfo.getRuleName()).addFileExtension("tgg"), 
							 ruleContent, 
							 createSubmonitorWith1Tick(monitor));
	}

}
