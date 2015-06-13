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

public class NewPatternPage extends WizardPage {

	private String patternName;
	
	public NewPatternPage(){
		super("newPattern");
	      patternName = "ExampleIntegration";

	      // Set information on the page
	      setTitle("New Pattern");
	      setDescription("This wizard creates a new Pattern for the MOSL project.");
	      setImageDescriptor(UIActivator.getImage("resources/icons/pattern.gif"));
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
	      label.setText("&Pattern name:");

	      final Text patternNameText = new Text(container, SWT.BORDER | SWT.SINGLE);
	      GridData gd = new GridData(GridData.FILL_HORIZONTAL);
	      patternNameText.setLayoutData(gd);
	      
	      // Place cursor here, initially.
	      patternNameText.setFocus();
	      
	      patternNameText.addModifyListener(new ModifyListener() {
	         @Override
            public void modifyText(final ModifyEvent e)
	         {
	            patternName = patternNameText.getText();
	         }
	      });

	      // Set controls and update
	      setControl(container);

	   }
	   
	   public String getPatternName(){
		   return this.patternName;
	   }
	
	   @Override
	   public boolean canFlipToNextPage()
	   {
	      return super.canFlipToNextPage() && getErrorMessage() == null;
	   }

}
