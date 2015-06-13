package org.moflon.ide.deployment.ui;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
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
import org.moflon.ide.ui.UIActivator;

public class DeploymentDialog extends Dialog
{
   private String deploymentDirectory;

   private String versionNumber;

   private Text deploymentDirectoryTextBox;

   private Text versionNumberTextBox;

   private List<String> ignoredPluginIdPatterns;

   private Text ignoredPluginIdPatternsTextBox;

   public DeploymentDialog(final Shell parentShell)
   {
      super(parentShell);
      this.setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
   }

   public void setDeploymentDirectory(final String selectedDirectory)
   {
      this.deploymentDirectory = selectedDirectory;
   }

   public void setVersionNumber(final String versionNumber)
   {
      this.versionNumber = versionNumber;
   }

   public String getSelectedDirectory()
   {
      return deploymentDirectory;
   }

   public String getVersionNumber()
   {
      return this.versionNumber;
   }

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

      addRowForBetaAndRelease(shell);

      addRowForVersionNumber(shell);

      addRowForIgnoredPluginIdPatterns(shell);

      addRowWithButtons(shell);

      updateDeploymentDirectoryTextBox();
      updateVersionNumberTextBox();
      updateIgnoredPluginIdPatternsTextBox();

      return parent;
   }

   private void updateIgnoredPluginIdPatternsTextBox()
   {
      this.ignoredPluginIdPatternsTextBox.setText(this.ignoredPluginIdPatterns.stream().collect(Collectors.joining(",")));
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

   private void addRowForVersionNumber(final Shell shell)
   {
      // Show the message
      Label label = new Label(shell, SWT.NONE);
      label.setText("New version number:");
      // data.horizontalSpan = 2;
      label.setLayoutData(new GridData());

      // Display the input box
      this.versionNumberTextBox = new Text(shell, SWT.BORDER);
      // data.horizontalSpan = 2;
      this.versionNumberTextBox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      this.versionNumberTextBox.addModifyListener(new ModifyListener() {

         @Override
         public void modifyText(final ModifyEvent e)
         {
            if (!versionNumberTextBox.getText().matches("\\s*"))
            {
               versionNumber = versionNumberTextBox.getText();
            } else
            {
               versionNumber = null;
            }
         }
      });
      this.versionNumberTextBox.setToolTipText("The new version number for all plugins and features to be deployed. If empty, no changes will be performed.");

      Label dummy = new Label(shell, SWT.NONE);
      dummy.setText("");
      GridData dummyData = new GridData();
      dummyData.horizontalSpan = 2;
      dummy.setLayoutData(dummyData);

   }

   private void addRowForIgnoredPluginIdPatterns(final Shell shell)
   {
      // Show the message
      Label label = new Label(shell, SWT.NONE);
      label.setText("Ignored plugin ID patterns:");
      // data.horizontalSpan = 2;
      label.setLayoutData(new GridData());

      // Display the input box
      this.ignoredPluginIdPatternsTextBox = new Text(shell, SWT.BORDER);
      // data.horizontalSpan = 2;
      this.ignoredPluginIdPatternsTextBox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      this.ignoredPluginIdPatternsTextBox.addFocusListener(new FocusListener() {

         @Override
         public void focusLost(final FocusEvent e)
         {
            if (!ignoredPluginIdPatternsTextBox.getText().matches("\\s*"))
            {
               boolean allPatternsCorrect = true;
               List<String> newPatternList = Arrays.asList(ignoredPluginIdPatternsTextBox.getText().split(","));
               for (final String newPattern : newPatternList)
               {
                  try
                  {
                     Pattern.compile(newPattern);
                  } catch (PatternSyntaxException e1)
                  {
                     ErrorDialog.openError(getShell(), "Invalid pattern", "The pattern ' " + newPattern + "' is invalid.", new Status(IStatus.ERROR,
                           UIActivator.getModuleID(), e1.getMessage()));
                     allPatternsCorrect = false;
                     break;
                  }
               }
               if (allPatternsCorrect)
                  ignoredPluginIdPatterns = newPatternList;

            } else
            {
               ignoredPluginIdPatterns = null;
            }
         }

         @Override
         public void focusGained(final FocusEvent e)
         {
         }
      });
      this.ignoredPluginIdPatternsTextBox.setToolTipText("Comma-separated list of patterns that specify which plugins will *not* be touched during a version number update");

      Label dummy = new Label(shell, SWT.NONE);
      dummy.setText("");
      GridData dummyData = new GridData();
      dummyData.horizontalSpan = 2;
      dummy.setLayoutData(dummyData);

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

   private void updateVersionNumberTextBox()
   {
      this.versionNumberTextBox.setText(this.versionNumber);
   }

   private void updateDeploymentDirectoryTextBox()
   {
      this.deploymentDirectoryTextBox.setText(this.deploymentDirectory);
   }

   public void setIgnoredPluginIDPatterns(final List<String> ignoredPluginIdPatterns)
   {
      this.ignoredPluginIdPatterns = ignoredPluginIdPatterns;
   }
}
