package org.moflon.ide.deployment.ui;

import java.io.File;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class DeploymentDialog extends Dialog
{
   private String deploymentDirectory;

   private String versionNumber;

   private Text deploymentDirectoryTextBox;

   private List<String> ignoredPluginIdPatterns;

   public DeploymentDialog(final Shell parentShell)
   {
      super(parentShell);
      this.setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
   }

   public void setDeploymentDirectory(final String selectedDirectory)
   {
      this.deploymentDirectory = selectedDirectory;
   }

   @Deprecated
   public void setVersionNumber(final String versionNumber)
   {
      this.versionNumber = versionNumber;
   }

   public String getSelectedDirectory()
   {
      return deploymentDirectory;
   }

   @Deprecated
   public String getVersionNumber()
   {
      return this.versionNumber;
   }

   @Deprecated
   public List<String> getIgnoredPluginIdPatternsAsList()
   {
      return this.ignoredPluginIdPatterns;
   }

   @Override
   protected Control createContents(final Composite parent)
   {
      final Shell shell = parent.getShell();
      shell.setLayout(new GridLayout(4, false));

      addRowForDeploymentPath(shell);

      // Most of the time, we do not deploy directly but re-use the locally deployed eMoflon.
      // addRowForBetaAndRelease(shell);

      addRowWithButtons(shell);

      updateDeploymentDirectoryTextBox();

      return parent;
   }

   public void addRowForDeploymentPath(final Shell shell)
   {
      // Show the message
      Label deploymentPathLabel = new Label(shell, SWT.NONE);
      deploymentPathLabel.setText("Deployment directory:");
      GridData data = new GridData();
      deploymentPathLabel.setLayoutData(data);

      // Display the input box
      this.deploymentDirectoryTextBox = new Text(shell, SWT.BORDER);
      data = new GridData(GridData.FILL_HORIZONTAL);
      // data.horizontalSpan = 2;
      this.deploymentDirectoryTextBox.setLayoutData(data);
      this.deploymentDirectoryTextBox.addModifyListener(new ModifyListener() {

         @Override
         public void modifyText(final ModifyEvent e)
         {
            deploymentDirectory = deploymentDirectoryTextBox.getText();
         }
      });
      this.deploymentDirectoryTextBox.setToolTipText("Root directory of the deployment.");

      Button browseDeploymentDirectory = new Button(shell, SWT.PUSH);
      browseDeploymentDirectory.setText("Browse...");
      data = new GridData(GridData.FILL_HORIZONTAL);
      data.horizontalSpan = 2;
      browseDeploymentDirectory.setLayoutData(data);
      browseDeploymentDirectory.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(final SelectionEvent event)
         {
            DirectoryDialog dialog = new DirectoryDialog(shell, SWT.SAVE);
            dialog.setText("Select the deployment destination");
            File initialDeploymentDirectory = new File(deploymentDirectory);
            if(!initialDeploymentDirectory.exists()){
               initialDeploymentDirectory = initialDeploymentDirectory.getParentFile();
            }
            dialog.setFilterPath(initialDeploymentDirectory.getAbsolutePath());

            String destinationDirectory = dialog.open();

            if (destinationDirectory != null)
            {
               deploymentDirectory = destinationDirectory;
               updateDeploymentDirectoryTextBox();
            }
         }

      });
   }

   @SuppressWarnings("unused")
   private void addRowForBetaAndRelease(final Shell shell)
   {
      // Show the message
      Label dummyLabel = new Label(shell, SWT.NONE);
      dummyLabel.setText("");
      GridData dummyData = new GridData();
      dummyData.horizontalSpan = 2;
      dummyLabel.setLayoutData(dummyData);

      Button betaButton = new Button(shell, SWT.PUSH);
      betaButton.setText("Beta");
      GridData controlButtonData = new GridData(GridData.FILL_HORIZONTAL);
      controlButtonData.widthHint = 80;
      betaButton.setLayoutData(controlButtonData);
      betaButton.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(final SelectionEvent event)
         {
            deploymentDirectory = "file://fg/es/mitarbeiter/eclipse-plugin/beta";
            updateDeploymentDirectoryTextBox();
         }
      });
      betaButton.setToolTipText("Use default beta deployment location");

      Button releaseButton = new Button(shell, SWT.PUSH);
      releaseButton.setText("Release");
      releaseButton.setLayoutData(controlButtonData);
      releaseButton.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(final SelectionEvent event)
         {
            deploymentDirectory = "file://fg/es/mitarbeiter/eclipse-plugin";
            updateDeploymentDirectoryTextBox();
         }
      });
      releaseButton.setToolTipText("Use default release deployment location");

   }

   public void addRowWithButtons(final Shell shell)
   {
      // Show the message
      Label dummyLabel = new Label(shell, SWT.NONE);
      dummyLabel.setText("");
      GridData dummyData = new GridData();
      dummyData.horizontalSpan = 2;
      dummyLabel.setLayoutData(dummyData);

      Button ok = new Button(shell, SWT.PUSH);
      ok.setText("OK");
      GridData controlButtonData = new GridData(GridData.FILL_HORIZONTAL);
      controlButtonData.widthHint = 60;
      ok.setLayoutData(controlButtonData);
      ok.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(final SelectionEvent event)
         {
            shell.close();
            setReturnCode(OK);
         }
      });

      Button cancel = new Button(shell, SWT.PUSH);
      cancel.setText("Cancel");
      cancel.setLayoutData(controlButtonData);
      cancel.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(final SelectionEvent event)
         {
            shell.close();
            setReturnCode(CANCEL);
         }
      });

      shell.setDefaultButton(ok);
   }

   private void updateDeploymentDirectoryTextBox()
   {
      this.deploymentDirectoryTextBox.setText(this.deploymentDirectory);
   }

   @Deprecated
   public void setIgnoredPluginIDPatterns(final List<String> ignoredPluginIdPatterns)
   {
      this.ignoredPluginIdPatterns = ignoredPluginIdPatterns;
   }
}
