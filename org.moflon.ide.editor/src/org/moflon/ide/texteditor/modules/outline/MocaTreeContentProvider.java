package org.moflon.ide.texteditor.modules.outline;

import java.util.ArrayList;

import org.eclipse.jface.viewers.Viewer;
import org.moflon.ide.texteditor.config.MoflonTextEditorConfigIntern;

import MocaTree.Node;
import MocaTree.Text;

public class MocaTreeContentProvider extends AbstractMocaTreeContentProvider
{

   MoflonTextEditorConfigIntern config;

   public MocaTreeContentProvider(MoflonTextEditorConfigIntern config)
   {
      this.config = config;
   }

   @Override
   public void dispose()
   {
   }

   @Override
   public Node[] getElements(Node inputElement)
   {
      return getChildren(inputElement);
   }

   @Override
   public Node[] getChildren(Node parentElement)
   {
      ArrayList<Node> childNodes = new ArrayList<Node>();
      for (Text Node : parentElement.getChildren())
      {
         if (Node instanceof Node)
         {
            if (this.config.showInOutline((MocaTree.Node) Node))
               childNodes.add((MocaTree.Node) Node);
            else
               childNodes.addAll(arrayToList((MocaTree.Node[]) getChildren(Node)));
         }
      }
      return (Node[]) childNodes.toArray(new Node[childNodes.size()]);
   }

   @Override
   public Node getParent(Node element)
   {
      return element.getParentNode();
   }

   @Override
   public boolean hasChildren(Node element)
   {
      return getChildren(element).length > 0;
   }

   @Override
   public void inputChanged(Viewer viewer, Node oldInput, Node newInput)
   {
   }

   private ArrayList<Node> arrayToList(Node[] nodes)
   {
      ArrayList<Node> list = new ArrayList<Node>();
      for (Node node : nodes)
      {
         list.add(node);
      }
      return list;
   }

}
