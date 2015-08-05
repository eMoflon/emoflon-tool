package org.moflon.ide.ui.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
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

   private Text updateSiteProjectTextBox;

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
      final Label pageDescriptionLabel = toolkit.createLabel(parent, "eMoflon Preferences");
      GridData gd1 = new GridData(GridData.FILL_HORIZONTAL);
      pageDescriptionLabel.setLayoutData(gd1);

      final Composite updateSiteComponent = toolkit.createComposite(parent);
      GridData gd2 = new GridData(GridData.FILL_HORIZONTAL);
      updateSiteComponent.setLayoutData(gd2);

      updateSiteComponent.setLayout(new GridLayout(2, false));
      toolkit.createLabel(updateSiteComponent, "Update site project name: ");

      updateSiteProjectTextBox = toolkit.createText(updateSiteComponent, "");
      GridData gd3 = new GridData(GridData.FILL_HORIZONTAL);
      updateSiteProjectTextBox.setLayoutData(gd3);

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
      storeValues();
      return super.performOk();
   }

   /**
    * Store current values shown on the page
    */
   private void storeValues()
   {
      EMoflonPreferenceInitializer.setUpdateSiteProject(updateSiteProjectTextBox.getText());
   }

   private void initializeValues()
   {
      updateSiteProjectTextBox.setText(EMoflonPreferenceInitializer.getUpdateSiteProject());
   }

}
