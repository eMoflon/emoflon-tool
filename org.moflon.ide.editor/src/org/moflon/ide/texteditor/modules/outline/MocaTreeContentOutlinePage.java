package org.moflon.ide.texteditor.modules.outline;

import org.apache.log4j.Logger;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.moflon.ide.texteditor.editors.MoflonTextEditor;
import org.moflon.ide.texteditor.helpers.MocaNodeIndexUtil;

import MocaTree.Node;

public class MocaTreeContentOutlinePage extends ContentOutlinePage
{
   private static final Logger logger = Logger.getLogger(MocaTreeContentOutlinePage.class);
   
   private MoflonTextEditor moflonTextEditor;

   public MocaTreeContentOutlinePage(MoflonTextEditor moflonTextEditor)
   {
      this.moflonTextEditor = moflonTextEditor;
   }

   @Override
   public void createControl(Composite parent)
   {
      super.createControl(parent);
      
      try
      {
         TreeViewer contentOutlineViewer = getTreeViewer();
         contentOutlineViewer.addSelectionChangedListener(this);

         // Set up the tree viewer.
         contentOutlineViewer.setContentProvider(new MocaTreeContentProvider(moflonTextEditor.getMoflonTextEditorConfiguration()));
         contentOutlineViewer.setLabelProvider(new MocaTreeLabelProvider(moflonTextEditor.getMoflonTextEditorConfiguration()));

         this.moflonTextEditor.refreshTreeModules();
      } catch (ClassCastException e)
      {
         logger.error("Unable to create outline");
      }
   }

   public void refreshOutlineView(Node mocaTree)
   {
      // Check if the tree is currently alive 
      TreeViewer t = this.getTreeViewer();
      if (t == null)
         return;
      
      Control c = t.getControl();
      if (c == null || c.isDisposed())
         return;
      
      this.getTreeViewer().setInput(mocaTree);
   }

   @Override
   public void selectionChanged(SelectionChangedEvent event)
   {
      super.selectionChanged(event);

      if (event.getSelection() instanceof TreeSelection)
      {
         TreeSelection treeSelection = (TreeSelection) event.getSelection();
         if (treeSelection.getFirstElement() instanceof Node)
         {
            Node node = (Node) treeSelection.getFirstElement();
            markSelectedNode(node);
         }
      }

   }

   private void markSelectedNode(Node node)
   {
         Position indexes = MocaNodeIndexUtil.getIndexAlgorithm(node);
         int startIndex = (int) indexes.getOffset();
         int stopIndex = (int) indexes.getOffset() + indexes.getLength();
      if (startIndex != Integer.MAX_VALUE && stopIndex != Integer.MIN_VALUE && stopIndex > 0)
      {
         this.moflonTextEditor.selectAndReveal(startIndex, stopIndex - startIndex + 1);
      }
   }
}
