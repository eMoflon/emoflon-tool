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
 * Used by {@link NewEPackageWizard}. Contains all GUI controls for the wizard page.
 */
public class NewEPackagePage extends WizardPage
{
   private String packageName;
   
   public NewEPackagePage()
   {
      super("NewEPackage");
      packageName = "unspecified";

      // Set information on the page
      setTitle("New EPackage");
      setDescription("This wizard creates a new package for the MOSL project.");
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
      label.setText("&Package name:");

      final Text packageNameText = new Text(container, SWT.BORDER | SWT.SINGLE);
      GridData gd = new GridData(GridData.FILL_HORIZONTAL);
      packageNameText.setLayoutData(gd);
      
      // Place cursor here, initially.
      packageNameText.setFocus();
      
      packageNameText.addModifyListener(new ModifyListener() {
         @Override
         public void modifyText(final ModifyEvent e)
         {
            packageName = packageNameText.getText();
            dialogChanged();
         }
      });

      // Set controls and update
      setControl(container);
      dialogChanged();
   }

   public String getPackageName()
   {
      return packageName;
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
