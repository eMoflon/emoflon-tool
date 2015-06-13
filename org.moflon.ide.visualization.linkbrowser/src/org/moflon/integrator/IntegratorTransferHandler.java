package org.moflon.integrator;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.TransferHandler;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.moflon.integrator.builder.IntegratorBuilder;

import TGGRuntime.CorrespondenceModel;

public class IntegratorTransferHandler extends TransferHandler
{
   private static final long serialVersionUID = 1L;

   private IntegratorApp integratorApp;

   public IntegratorTransferHandler(IntegratorApp integratorApp)
   {
      this.integratorApp = integratorApp;
   }

   @Override
   public boolean canImport(final TransferHandler.TransferSupport support)
   {
      ISelection selection = LocalSelectionTransfer.getTransfer().getSelection();
      if (selection instanceof TreeSelection)
      {
         TreeSelection treeSelection = (TreeSelection) selection;
         for (Object obj : treeSelection.toList())
         {
            System.out.println(obj);
            if (obj instanceof IAdaptable)
            {
               IAdaptable adapter = (IAdaptable) obj;
               return adapter.getAdapter(CorrespondenceModel.class) != null;
            } else if (!(obj instanceof CorrespondenceModel))
            {
               return false;
            }
         }
      }
      support.setDropAction(COPY);
      return true;
   }

   @Override
   public boolean importData(TransferSupport support)
   {
      ISelection selection = LocalSelectionTransfer.getTransfer().getSelection();
      if (selection instanceof TreeSelection)
      {
         TreeSelection treeSelection = (TreeSelection) selection;
         if (treeSelection.size() > 1)
         {
            System.err.println("Drag and drop of more than one element is currently not supported.");
            throw new RuntimeException("Drag and drop of more than one element is currently not supported.");
         }

         for (Object obj : treeSelection.toList())
         {
            CorrespondenceModel corr = null;
            if (obj instanceof CorrespondenceModel)
            {
               corr = (CorrespondenceModel) obj;
            }
            if (obj instanceof IAdaptable)
            {
               Object obj2 = ((IAdaptable) obj).getAdapter(CorrespondenceModel.class);
               if (obj2 instanceof CorrespondenceModel)
               {
                  corr = (CorrespondenceModel) obj2;
               }
            }
            if (corr != null)
            {
               try
               {
                  IntegratorBuilder builder = new IntegratorBuilder(corr);
                  Container parent = integratorApp.getParent();

                  integratorApp.dispose();
                  parent.remove(integratorApp);
                  integratorApp = new IntegratorApp(builder);
                  integratorApp.setVisible(true);
                  integratorApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                  ((javax.swing.plaf.basic.BasicInternalFrameUI) integratorApp.getUI()).setNorthPane(null);

                  integratorApp.setBorder(null);
                  parent.add(integratorApp);
               } catch (Exception e)
               {
                  e.printStackTrace();
               }
            } else
            {
               System.err.println("Drag and Drop of an yet unhandled selection type '" + obj.getClass().getName() + "'.");
               throw new RuntimeException("Drag and Drop of an yet unhandled selection type '" + obj.getClass().getName() + "'.");
            }
         }
      } else
      {
         System.err.println("Drag and Drop of an yet unhandled selection type '" + selection.getClass().getName() + "'.");
         throw new RuntimeException("Drag and Drop of an yet unhandled selection type '" + selection.getClass().getName() + "'.");
      }
      return selection == null ? false : true;
   }
}
