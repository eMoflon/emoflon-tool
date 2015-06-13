package org.moflon.ide.texteditor.modules.outline;

import java.io.File;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
//import org.eclipse.swt.graphics.Device;
//import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Image;
//import org.eclipse.swt.printing.Printer;
//import org.eclipse.swt.widgets.Display;
import org.moflon.ide.texteditor.config.MoflonTextEditorConfigIntern;

import MocaTree.Node;

public class MocaTreeLabelProvider implements ILabelProvider
{

   MoflonTextEditorConfigIntern config;

   MocaTreeLabelProvider(MoflonTextEditorConfigIntern config)
   {
      this.config = config;
   }

   @Override
   public void addListener(ILabelProviderListener listener)
   {
   }

   @Override
   public void dispose()
   {
   }

   @Override
   public boolean isLabelProperty(Object element, String property)
   {
      return false;
   }

   @Override
   public void removeListener(ILabelProviderListener listener)
   {
   }

   @Override
   public Image getImage(Object element)
   {
      if (element instanceof Node)
      {
         Node node = (Node) element;
         String imagePath = this.config.getOutlineImagePath(node);
         File file = new File(imagePath);
         if (file.exists())
         {
        	Image image = null; // new Image(new MoflonDevice(), file.getAbsolutePath());
            return image;
         }
      }
      return null;
   }

   @Override
   public String getText(Object element)
   {
      if (element instanceof Node)
      {
         Node node = (Node) element;
         return this.config.getOutlineLabel(node);
      }
      return "";
   }
//
//   
//   
//   
//   public class MoflonDevice extends org.eclipse.swt.graphics.Device
//   {
//
//      @Override
//      public long internal_new_GC(GCData data)
//      {
//         return 0;
//      }
//
//      @Override
//      public void internal_dispose_GC(long hDC, GCData data)
//      {
//         
//      }
//
//   }

}
