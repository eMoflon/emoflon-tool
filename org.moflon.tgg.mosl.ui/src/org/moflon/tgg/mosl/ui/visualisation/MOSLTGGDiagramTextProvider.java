package org.moflon.tgg.mosl.ui.visualisation;

import java.util.HashMap;
import java.util.Optional;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.ide.visualisation.dot.language.DotUnparserAdapter;
import org.moflon.ide.visualization.dot.tgg.TGGRuleDiagramTextProvider;
import org.moflon.tgg.language.TGGRule;
import org.moflon.tgg.language.TripleGraphGrammar;

import net.sourceforge.plantuml.eclipse.utils.AbstractDiagramTextProvider;

public class MOSLTGGDiagramTextProvider extends AbstractDiagramTextProvider {
	private boolean outdated = false;
	private XtextEditor oldEditor;
	private HashMap<TGGRule, String> oldValue = new HashMap<>();
	
	private IPropertyListener listener = (o, p) -> {
		if (p == IWorkbenchPartConstants.PROP_DIRTY && !oldEditor.isDirty()) {
			Job j = new Job("set outdated") {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					try {
						IJobManager jobManager = Job.getJobManager();

						// Wait for auto build to finish if running
						jobManager.join(ResourcesPlugin.FAMILY_AUTO_BUILD, new NullProgressMonitor());
						outdated = true;
					} catch (Exception e) {
						e.printStackTrace();
					}

					return Status.OK_STATUS;
				}
			};
			
			j.setPriority(Job.LONG);
			j.schedule();
		}
	};
	
   @Override
   public String getDiagramText(IEditorPart editorPart, IEditorInput editorInput)
   {
      try
      {
         ISelection selection = editorPart.getSite().getSelectionProvider().getSelection();
         Optional<TGGRule> rule = getTGGRuleForSelection(selection);

         if (oldValue.containsKey(rule) && !outdated)
            return oldValue.get(rule);

         return rule.map(r -> {
            outdated = false;
            TGGRuleDiagramTextProvider tggTextProvider = new TGGRuleDiagramTextProvider();
            String diagram = new DotUnparserAdapter().unparse(tggTextProvider.modelToDot(r));
            oldValue.put(r, diagram);
            return diagram;
         }).orElse("@startuml @enduml");
         
      } catch (Exception e)
      {
         e.printStackTrace();
         return "@startuml @enduml";
      }
   }

	@Override
	public boolean supportsEditor(IEditorPart editorPart) {
		if(oldEditor != null && oldEditor.equals(editorPart))
			return true;
		
		if(editorPart instanceof XtextEditor){
			XtextEditor ed  = (XtextEditor)editorPart;
			if("org.moflon.tgg.mosl.TGG".equals(ed.getLanguageName())){
				oldEditor = ed;
				oldValue = new HashMap<>();
				oldEditor.addPropertyListener(listener);
				return true;
			}
		}
		
		return false;
	}

	private Optional<TGGRule> getTGGRuleForSelection(ISelection selection) { 
		IPath ruleNamePath = new Path(oldEditor.getEditorInput().getName());
		ruleNamePath = ruleNamePath.removeFileExtension();
		String ruleName = ruleNamePath.toString();
		
		String selectedRuleName = extractRuleName(selection);

		if (oldEditor != null && oldEditor.getEditorInput() instanceof FileEditorInput) {
			IFile file = FileEditorInput.class.cast(oldEditor.getEditorInput()).getFile();
			IProject project = file.getProject();
         IFile tggFile = project.getFile(MoflonUtil.getDefaultPathToFileInProject(project.getName(), ".pre.tgg.xmi"));

			if (tggFile.exists()) {
				ResourceSet rs = eMoflonEMFUtil.createDefaultResourceSet();
				URI uri = URI.createPlatformResourceURI(tggFile.getFullPath().toString(), true);
				Resource tggResource = rs.getResource(uri, true);
				TripleGraphGrammar tgg = (TripleGraphGrammar) tggResource.getContents().get(0);
				return tgg.getTggRule()
				      .stream()
				      .filter(r -> r.getName().equals(ruleName) || r.getName().equals(selectedRuleName))
				      .findAny();
			}
		}

		return Optional.empty();
	}

   private String extractRuleName(ISelection selection)
   {
      if(selection instanceof ITextSelection){
         ITextSelection textSelection = (ITextSelection)selection;
         return textSelection.getText();
      } else
         return "";

   }
}
