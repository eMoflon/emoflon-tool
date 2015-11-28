package org.moflon.integrator;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.TransferHandler;

import org.moflon.integrator.builder.IntegratorBuilder;

import de.fhg.iao.matrixbrowser.MatrixBrowserPanel;
import de.fhg.iao.matrixbrowser.model.transport.TransportException;

public class IntegratorApp extends JInternalFrame
{
   private static final long serialVersionUID = 1L;

   // This is the main window showing the "matrix"
   private final MatrixBrowserPanel browserPanel;

   public IntegratorApp() throws TransportException
   {
      super("eMoflon Integrator");

      browserPanel = new MatrixBrowserPanel();

      // Put matrix in a frame
      this.add(browserPanel);

      addDragAndDropSupport();
   }

   public IntegratorApp(final IntegratorBuilder builder) throws TransportException
   {
      this();

      builder.populateMatrixView(browserPanel);
      builder.registerEventListeners(browserPanel);
      addDragAndDropSupport();
   }

   private void addDragAndDropSupport()
   {
      final TransferHandler handler = new IntegratorTransferHandler(this);

      browserPanel.getHorizontalTreeView().getTree().setTransferHandler(handler);
      browserPanel.getVerticalTreeView().getTree().setTransferHandler(handler);
      browserPanel.setTransferHandler(handler);
      for (final Component comp : browserPanel.getComponents())
      {
         if (comp instanceof JComponent)
         {
            ((JComponent) comp).setTransferHandler(handler);
         }
      }
   }
}
