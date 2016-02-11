package org.moflon.ide.metamodelevolution.core.processing;

import org.eclipse.core.resources.IProject;
import org.moflon.ide.metamodelevolution.core.ChangeSequence;
import org.moflon.ide.metamodelevolution.core.EModelElementChange;
import org.moflon.ide.metamodelevolution.core.RenameChange;
import org.moflon.ide.metamodelevolution.core.impl.RenameChangeImpl;

public class RenameProjectProcessor implements MetamodelDeltaProcessor {

	@Override
	public void processDelta(IProject project, ChangeSequence delta) {
		
		for (EModelElementChange change : delta.getEModelElementChange())
	    {
	         if (change instanceof RenameChangeImpl)
	         {
	            RenameChange renaming = (RenameChange) change;
	            if(renaming.getElement().equals(TL_PACKAGE) && renaming.arePreviousAndCurrentValueDifferent()) 
	            {
	            	RenameRefactoring renameProjectRefactoring = new RenameProjectRefactoring();
	            	renameProjectRefactoring.refactor(project, renaming);
	            }
	         }
	    }
	}

}
