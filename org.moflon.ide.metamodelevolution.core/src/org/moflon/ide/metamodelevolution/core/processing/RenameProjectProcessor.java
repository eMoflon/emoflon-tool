package org.moflon.ide.metamodelevolution.core.processing;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.moflon.core.mocatomoflon.MocaToMoflonUtils;
import org.moflon.ide.metamodelevolution.core.ChangeSequence;
import org.moflon.ide.metamodelevolution.core.EModelElementChange;
import org.moflon.ide.metamodelevolution.core.MetamodelCoevolutionPlugin;
import org.moflon.ide.metamodelevolution.core.RenameChange;
import org.moflon.ide.metamodelevolution.core.impl.RenameChangeImpl;
import org.moflon.ide.metamodelevolution.core.processing.refactoring.RenameProjectRefactoring;
import org.moflon.ide.metamodelevolution.core.processing.refactoring.RenameRefactoring;

public class RenameProjectProcessor implements MetamodelDeltaProcessor {

	@Override
	public IStatus processDelta(IProject project, ChangeSequence delta) {
		
		for (EModelElementChange change : delta.getEModelElementChange())
	    {
	         if (change instanceof RenameChangeImpl)
	         {
	            RenameChange renaming = (RenameChange) change;
	            if(renaming.getElementType().equals(MocaToMoflonUtils.TLPACKAGE_NODE_NAME) && renaming.arePreviousAndCurrentValueDifferent()) 
	            {
	            	RenameRefactoring renameProjectRefactoring = new RenameProjectRefactoring();
	            	renameProjectRefactoring.refactor(project, renaming);
	            }
	         }
	    }
      return new Status(IStatus.OK, MetamodelCoevolutionPlugin.getDefault().getPluginId(), "Project refactoring done");
	}

}
