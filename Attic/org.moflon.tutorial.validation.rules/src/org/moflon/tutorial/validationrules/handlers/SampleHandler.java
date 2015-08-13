package org.moflon.tutorial.validationrules.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.handlers.HandlerUtil;

//TODO anjorin: Can we remove that? It obviously does nothing
/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SampleHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public SampleHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		//		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		ISelection selected = HandlerUtil.getShowInSelectionChecked(event);
		if(selected instanceof TreeSelection){
			TreeSelection ts = (TreeSelection)selected;
			Object s = ts.getFirstElement();
			if(s instanceof IFile){
				IFile f = (IFile)s;
				validate(f);
			}
		}
		
		return null;
	}

	private void validate(final IFile f) {
		// EPackage p =
		// (EPackage)eMoflonEMFUtil.loadModel(f.getLocation().toString());
		// Validation validator = TutorialValidationRulesFactory.eINSTANCE.createValidation();

		//MoflonConsole.printInfoMessage("Tutorial Validation", "Is metamodel valid? " + (validator.checkMetamodelStructure(p) && validator.checkFigureMove(p)));
		
	}
}
