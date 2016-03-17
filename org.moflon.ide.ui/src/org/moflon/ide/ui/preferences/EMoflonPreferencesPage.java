package org.moflon.ide.ui.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class EMoflonPreferencesPage extends PreferencePage implements IWorkbenchPreferencePage
{

   private static final Color DEFAULT_BACKGROUND_COLOR = null;

   private Text updateSiteProjectTextBox;

   private Text validationTimeoutTextBox;

   public EMoflonPreferencesPage()
   {
      // Required by OSGi framework
   }

   public EMoflonPreferencesPage(final String title)
   {
      super(title);
   }

   public EMoflonPreferencesPage(final String title, final ImageDescriptor image)
   {
      super(title, image);
   }

   @Override
   public void init(final IWorkbench workbench)
   {

   }

   @Override
   protected Control createContents(final Composite parent)
   {
      final FormToolkit toolkit = new FormToolkit(parent.getDisplay());
      {
         final Label pageDescriptionLabel = toolkit.createLabel(parent, "eMoflon Preferences");
         pageDescriptionLabel.setBackground(DEFAULT_BACKGROUND_COLOR);
         final GridData gd1 = new GridData(GridData.FILL_HORIZONTAL);
         pageDescriptionLabel.setLayoutData(gd1);
      }

      {
         final Composite updateSiteComponent = toolkit.createComposite(parent);
         updateSiteComponent.setBackground(DEFAULT_BACKGROUND_COLOR);
         final GridData gd2 = new GridData(GridData.FILL_HORIZONTAL);
         updateSiteComponent.setLayoutData(gd2);

         updateSiteComponent.setLayout(new GridLayout(2, false));
         final Label updateProjectSiteLabel = toolkit.createLabel(updateSiteComponent, "Update site project name: ");
         updateProjectSiteLabel.setBackground(DEFAULT_BACKGROUND_COLOR);

         updateSiteProjectTextBox = toolkit.createText(updateSiteComponent, "");
         final GridData gd3 = new GridData(GridData.FILL_HORIZONTAL);
         updateSiteProjectTextBox.setLayoutData(gd3);
      }

      {
         final Composite validationTimeoutComponent = toolkit.createComposite(parent);
         validationTimeoutComponent.setBackground(DEFAULT_BACKGROUND_COLOR);
         final GridData gd4 = new GridData(GridData.FILL_HORIZONTAL);
         validationTimeoutComponent.setLayoutData(gd4);

         validationTimeoutComponent.setLayout(new GridLayout(2, false));
         final Label validationTimeoutLabel = toolkit.createLabel(validationTimeoutComponent, "Validation timeout in seconds: ");
         validationTimeoutLabel.setBackground(DEFAULT_BACKGROUND_COLOR);

         validationTimeoutTextBox = toolkit.createText(validationTimeoutComponent, "");
         final GridData gd5 = new GridData(GridData.FILL_HORIZONTAL);
         validationTimeoutTextBox.setLayoutData(gd5);
         validationTimeoutTextBox.setToolTipText("The maximum time that the validation may take. Choose '0' to wait undefinitely long.");
      }

      initializeValues();

      return new Composite(parent, SWT.NULL);
   }

   @Override
   protected IPreferenceStore doGetPreferenceStore()
   {
      return EMoflonPreferenceInitializer.getPreferencesStore();
   }

   @Override
   protected void performDefaults()
   {
      super.performDefaults();
      EMoflonPreferenceInitializer.restoreDefaults();
      initializeValues();
   }

   @Override
   protected void performApply()
   {
      super.performApply();
      storeValues();
   }

   @Override
   public boolean performOk()
   {
      if (!checkValues())
      {
         return false;
      }
      storeValues();
      return super.performOk();
   }

   private boolean checkValues()
   {
      try
      {
         Integer.parseInt(validationTimeoutTextBox.getText());
         return true;
      } catch (NumberFormatException e)
      {
         return false;
      }
   }

   /**
    * Store current values shown on the page
    */
   private void storeValues()
   {
      EMoflonPreferenceInitializer.setUpdateSiteProject(updateSiteProjectTextBox.getText());
      EMoflonPreferenceInitializer.setValidationTimeoutMillis(Integer.parseInt(validationTimeoutTextBox.getText()) * 1000);
   }

   private void initializeValues()
   {
      updateSiteProjectTextBox.setText(EMoflonPreferenceInitializer.getUpdateSiteProject());
      validationTimeoutTextBox.setText(Integer.toString(EMoflonPreferenceInitializer.getValidationTimeoutMillis() / 1000));
   }

}
