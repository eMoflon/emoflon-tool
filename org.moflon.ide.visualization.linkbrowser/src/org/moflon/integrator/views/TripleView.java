package org.moflon.integrator.views;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;
import org.moflon.integrator.IntegratorApp;
import org.moflon.integrator.builder.IntegratorBuilder;

import TGGRuntime.CorrespondenceModel;
import de.fhg.iao.matrixbrowser.model.transport.TransportException;

public class TripleView extends ViewPart
{

   public static final String ID = "org.moflon.ide.ui.admin.views.Matrixbrowser";

   IntegratorApp integrator = null;

   public void createPartControl(Composite parent)
   {
      try
      {
         // Create frame and show
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
         integrator = new IntegratorApp();
         integrator.setVisible(true);
         integrator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         integrator.setBorder(null);
         // hide title bar
         ((javax.swing.plaf.basic.BasicInternalFrameUI) integrator.getUI()).setNorthPane(null);
      } catch (Exception e)
      {
         e.printStackTrace();
      }

      Composite swtAwtComponent = new Composite(parent, SWT.EMBEDDED | SWT.NO_BACKGROUND);
      java.awt.Frame frame = SWT_AWT.new_Frame(swtAwtComponent);
      frame.add(integrator);
      getSite().getPage().addSelectionListener(listener);
   }

   @Override
   public void setFocus()
   {
   }

   ISelectionListener listener = new ISelectionListener() {
      public void selectionChanged(IWorkbenchPart part, ISelection sel)
      {
         if (!(sel instanceof IStructuredSelection))
            return;
         IStructuredSelection ss = (IStructuredSelection) sel;
         Object o = ss.getFirstElement();
         // System.out.println("Listener from Tirpile View on " + o);
         if (o instanceof IStackFrame)
         {
            IStackFrame sf = (IStackFrame) o;
            try
            {
               if (sf.hasVariables())
               {
                  try
                  {
                     CorrespondenceModel corr = (CorrespondenceModel) sf.getVariables()[0].getValue().getAdapter(CorrespondenceModel.class);
                     if (corr != null)
                        try
                        {
                           IntegratorBuilder builder = new IntegratorBuilder(corr);
                           Container parent = integrator.getParent();
                           if (parent != null)
                           { // TODO Fix parent bug
                              parent.remove(integrator);
                              integrator.dispose();
                              integrator = new IntegratorApp(builder);
                              integrator.setVisible(true);
                              integrator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                              ((javax.swing.plaf.basic.BasicInternalFrameUI) integrator.getUI()).setNorthPane(null);

                              integrator.setBorder(null);
                              parent.add(integrator);
                              parent.doLayout();
                           }
                        } catch (TransportException e)
                        {
                           e.printStackTrace();
                        }
                  } catch (DebugException e)
                  {
                     e.printStackTrace();
                  }
               }
            } catch (DebugException e)
            {
               e.printStackTrace();
            }
         }
      }
   };

   public void dispose()
   {
      getSite().getPage().removeSelectionListener(listener);
   }
}