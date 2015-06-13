package org.moflon.ide.ui.admin.wizards.mosl;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.moflon.ide.ui.UIActivator;

/**
 * Used by {@link NewEClassWizard}. Contains all GUI controls for the wizard page.
 * 
 * @author anjorin
 * @author (last editor) $Author: aanjorin $
 * @version $Revision: 1252 $ $Date: 2012-10-06 21:39:04 +0200 (Sa, 06 Okt 2012) $
 */
public class NewEClassPage extends WizardPage
{
   private String className;
   
   public NewEClassPage()
   {
      super("NewEClass");
      className = "unspecified";

      // Set information on the page
      setTitle("New EClass");
      setDescription("This wizard creates a new eclass for the MOSL project.");
      setImageDescriptor(UIActivator.getImage("resources/icons/metamodelProjectWizard.gif"));
   }

   @Override
   public void createControl(final Composite parent)
   {
      // Create root container
      Composite container = new Composite(parent, SWT.NULL);
      GridLayout layout = new GridLayout();
      container.setLayout(layout);
      layout.numColumns = 2;

      // Create control for entering project name
      Label label = new Label(container, SWT.NULL);
      label.setText("&Class name:");

      final Text classNameText = new Text(container, SWT.BORDER | SWT.SINGLE);
      GridData gd = new GridData(GridData.FILL_HORIZONTAL);
      classNameText.setLayoutData(gd);
      
      // Place cursor here, initially.
      classNameText.setFocus();
      
      classNameText.addModifyListener(new ModifyListener() {
         @Override
         public void modifyText(final ModifyEvent e)
         {
            className = classNameText.getText();
            dialogChanged();
         }
      });

      // Set controls and update
      setControl(container);
      dialogChanged();
   }

   public String getClassName()
   {
      return className;
   }
   
   @Override
   public boolean canFlipToNextPage()
   {
      return super.canFlipToNextPage() && getErrorMessage() == null;
   }

   private void dialogChanged()
   {
   }

}
