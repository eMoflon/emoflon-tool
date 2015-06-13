package org.moflon.ide.ui.admin.wizards.metamodel;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.ui.UIActivator;

/**
 * Used by {@link NewMetamodelWizard}. Contains all GUI controls for the wizard page.
 * 
 * @author anjorin
 * @author (last editor) $Author$
 * @version $Revision$ $Date$
 */
public class NewMetamodelProjectInfoPage extends WizardPage
{
   private static final String BUTTON_DEMO_NAME = "Add Demo Specification";

   private static final String VISUAL_RADIO_BUTTON_TEXT = "Visual (Enterprise Architect)";

   private String projectName;

   private boolean eMoflonDemo;

   private boolean textual;

   public NewMetamodelProjectInfoPage()
   {
      super("NewMetamodelProjectInfo");
      projectName = "unspecified";

      // Set information on the page
      setTitle("New Metamodel Project");
      setDescription("This wizard creates a new project for metamodelling with eMoflon.");
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
      label.setText("&Project name:");

      final Text projectNameText = new Text(container, SWT.BORDER | SWT.SINGLE);
      GridData gd = new GridData(GridData.FILL_HORIZONTAL);
      projectNameText.setLayoutData(gd);

      projectNameText.addModifyListener(new ModifyListener() {
         @Override
         public void modifyText(final ModifyEvent e)
         {
            projectName = projectNameText.getText();
            dialogChanged();
         }
      });

      // Place cursor in textfield
      projectNameText.setFocus();

      new Label(container, SWT.NULL); // radio buttons in column 2

      // RadioButton Group - Concrete Syntax - textual or EA

      Group rbsContainer = new Group(container, SWT.NONE);
      rbsContainer.setText("Concrete Syntax");
      rbsContainer.setLayout(new GridLayout());

      final Button visualRadioButton = new Button(rbsContainer, SWT.RADIO);
      visualRadioButton.setText(VISUAL_RADIO_BUTTON_TEXT);
      visualRadioButton.setSelection(true);

      final Button textualRadioButton = new Button(rbsContainer, SWT.RADIO);
      textualRadioButton.setText("Textual (MOSL)");
      
      SelectionListener sl = new SelectionListener() {

         @Override
         public void widgetSelected(final SelectionEvent e)
         {
            if (visualRadioButton.getSelection())
               textual = false;
            else
               textual = true;
         }

         @Override
         public void widgetDefaultSelected(final SelectionEvent e)
         {
            if (visualRadioButton.getSelection())
               textual = false;
            else
               textual = true;
         }
      };

      visualRadioButton.addSelectionListener(sl);
      // textualRadioButton.addSelectionListener(sl);

      // create check button to enable moca support
      new Label(container, SWT.NULL); // empty label -> check box in column 2

      final Button eMoflonDemoButton = new Button(container, SWT.CHECK);
      eMoflonDemoButton.setText(BUTTON_DEMO_NAME);
      eMoflonDemoButton.addSelectionListener(new SelectionListener() {
         @Override
         public void widgetSelected(final SelectionEvent e)
         {
            eMoflonDemo = eMoflonDemoButton.getSelection();
         }

         @Override
         public void widgetDefaultSelected(final SelectionEvent e)
         {
            eMoflonDemo = eMoflonDemoButton.getSelection();
         }
      });

      // Set controls and update
      setControl(container);
      dialogChanged();
   }

   @Override
   public void setVisible(final boolean visible)
   {
      super.setVisible(visible);
   }

   @Override
   public boolean canFlipToNextPage()
   {
      return super.canFlipToNextPage() && getErrorMessage() == null;
   }

   public String getProjectName()
   {
      return projectName;
   }

   public boolean eMoflonDemo()
   {
      return eMoflonDemo;
   }

   public boolean textual()
   {
      return textual;
   }

   private final void updateStatus(final String message)
   {
      setErrorMessage(message);
      setPageComplete(message == null);
   }

   private void dialogChanged()
   {
      IStatus validity = WorkspaceHelper.validateProjectName(projectName, UIActivator.getModuleID());

      if (validity.isOK())
         updateStatus(null);
      else
         updateStatus(validity.getMessage());
   }

}
