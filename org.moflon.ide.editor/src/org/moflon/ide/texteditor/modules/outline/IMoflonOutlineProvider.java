package org.moflon.ide.texteditor.modules.outline;

import MocaTree.Node;

public interface IMoflonOutlineProvider
{

   public abstract Boolean showInOutline(Node node);
   
   public abstract String getOutlineImagePath(Node node);
   
   public abstract String getOutlineLabel(Node node);
   
}
