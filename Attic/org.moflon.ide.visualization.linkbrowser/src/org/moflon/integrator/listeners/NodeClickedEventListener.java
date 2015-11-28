package org.moflon.integrator.listeners;

import org.apache.log4j.Logger;

import de.fhg.iao.matrixbrowser.VisibleNodeEvent;
import de.fhg.iao.matrixbrowser.VisibleNodeEventListener;

public class NodeClickedEventListener implements VisibleNodeEventListener
{
   private static final Logger logger = Logger.getLogger(NodeClickedEventListener.class);

   @Override
   public void onNodeClicked(VisibleNodeEvent e)
   {
      logger.debug("node clicked: " + e.getNode().getNestedNode().getName());
   }

   @Override
   public void onNodeOver(VisibleNodeEvent e)
   {
      logger.debug("node hover: " + e.getNode().getNestedNode().getName());
   }

   @Override
   public void onNodeEdit(VisibleNodeEvent e)
   {
      logger.debug("node edit: " + e.getNode().getNestedNode().getName());
   }

   @Override
   public void onNodeMenu(VisibleNodeEvent e)
   {
      logger.debug("node menu: " + e.getNode().getNestedNode().getName());
   }

}
