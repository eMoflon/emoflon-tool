package org.moflon.ide.texteditor.helpers;

import java.util.ArrayList;

import org.eclipse.jface.text.Position;

import MocaTree.Node;
import MocaTree.Text;

public class MocaNodeIndexUtil
{
   public static Position getIndexAlgorithm(Node node)
   {
      int startIndex = Integer.MAX_VALUE;
      int stopIndex = Integer.MIN_VALUE;
      ArrayList<Node> nodes = new ArrayList<Node>();
      nodes.add(node);
      for (int i = 0; i < nodes.size(); i++)
      {
         Node curNode = nodes.get(i);
         for (Text child : curNode.getChildren())
         {
            if (child instanceof Node)
            {
               Node actNode = (Node) child;
               int tempStart = actNode.getStartIndex();
               int tempStop = actNode.getStopIndex();

               if (tempStart <= startIndex && tempStart != -1 && tempStop > 0)
                  startIndex = tempStart;

               if (tempStop >= stopIndex && tempStop > 0)
                  stopIndex = tempStop;

               nodes.add(actNode);
            }
         }
      }
      
      if(startIndex != Integer.MAX_VALUE && stopIndex != Integer.MIN_VALUE)
      {
         if(node.getStartIndex() < startIndex && node.getStopIndex() > 0 )
            startIndex = node.getStartIndex();
         return new Position(startIndex, stopIndex - startIndex);
      
      }
      startIndex = node.getStartIndex();
      stopIndex = node.getStopIndex();

      if (stopIndex > 0)
      {
         return new Position(startIndex, stopIndex - startIndex);
      }
      return new Position(0,0);
   }
}
