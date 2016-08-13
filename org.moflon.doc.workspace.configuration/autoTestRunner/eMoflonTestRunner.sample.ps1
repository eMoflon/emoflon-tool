###
# CUSTOMIZATION INSTRUCTIONS
###

# 0.  Copy eMoflonTestRunner.sample.ps1 to a folder of your choice (test workspaces will be checked out here!)
# 1.  Correct the variable $ECLIPSE_HOME in the script to fit to your Eclipse installation, i.e., the folder containing "eclipse.exe"
# 2.  Set the variable $OUTPUT_DIRECTORY to a suitable temporary directory.
# 3.  Select the test workspaces to run by (un)commenting the corresponding lines (e.g., [void]$WORKSPACES.add("TestWorkspace_Democles_0")) 
# 4.  (If necessary,) open Powershell (press Windows button, type in powershell, hit enter), and enter the following command in the shell.
#       As normal user:
#           Set-ExecutionPolicy RemoteSigned -Scope CurrentUser
#       As administrator:
#           Set-ExecutionPolicy RemoteSigned
#       Suspend the rule by pressing 'J' or 'Y' to confirm the changed settings.
# 5.  Close the shell, select and right-click eMoflonTestRunner.ps1, and choose "run in powershell".

### 
# TROUBLESHOOTING
###

### Problem 1
#   **Issue:** Eclipse crashes and the log tells you: 'java.lang.RuntimeException: Application "org.moflon.testapplication" could not be found in the registry.'
#   **Solution:** Your Moflon devtools plugin is probably too old. Update to at least version 201407141709.

### Problem 2
#   **Issue:** Eclipse starts but your workspace is empty.
#   **Solution:** Make sure that your default SVN client/interface is *SVNKit* (Window > Preferences > Team/SVN). If *JavaHL* is installed, uninstall it.

###
# SCRIPT BODY
###

#$OLD_DIR = $(get-location).Path

# Directory for temporarily storing the checked out projects
$OUTPUT_DIRECTORY="[set output directory]"

# Path to eclipse - must contain eclipse.exe
$ECLIPSE_HOME = "[set Eclipse home]"

# Time between two executions of Eclipse
$sleepTimeBetweenStartsInSeconds = 120

# Array of all test workspaces (see EMoflonStandardWorkspaces)
# (N.B. The cast to [void] avoids that the current size is printed.)
[System.Collections.ArrayList]$WORKSPACES = @()
[void]$WORKSPACES.add("TestWorkspace_Democles_0")
[void]$WORKSPACES.add("TestWorkspace_Misc")
[void]$workspaces.add("TestWorkspace_DemoAndHandbook")
# [void]$WORKSPACES.add("TestWorkspace_TGG_0")
# [void]$WORKSPACES.add("TestWorkspace_TGG_1")
# [void]$WORKSPACES.add("TransformationZoo_0")
# [void]$WORKSPACES.add("TransformationZoo_1")
#[void]$workspaces.add("ModuleAll")
# [void]$workspaces.add("ModuleAllInclMOSL")
# [void]$WORKSPACES.add("eMoflonDemoWorkspace")
# [void]$WORKSPACES.add("HandbookFinal")
# [void]$WORKSPACES.add("HandbookGUI")
# [void]$WORKSPACES.add("HandbookPart3Start")
# [void]$WORKSPACES.add("HandbookPart3Final")
# [void]$WORKSPACES.add("HandbookPart4Start")
 

# Whether to spawn a new console for each Eclipse instance, showing standard output/standard error messages
$USE_CONSOLE = $FALSE # either $TRUE or $FALSE


# Number of trials to clean the output directory
$NUM_TRIALS_FOR_CLEANING_OUTPUT_DIRECTORY = 5

echo "OUTPUT_DIRECTORY:         $OUTPUT_DIRECTORY"
echo "ECLIPSE_HOME:             $ECLIPSE_HOME"
echo "Use console?              $USE_CONSOLE"
echo "Interval between starts:  $sleepTimeBetweenStartsInSeconds"
echo "Feature version:          $(Get-ChildItem -Name "$ECLIPSE_HOME/features/org.moflon.*")"
echo "Workspaces to be run:     $WORKSPACES" 
echo ""
$confirmed = Read-Host "Continue? [Y/n]"
if($confirmed -ne "" -and ($confirmed -ne "y" -or $confirmed -ne "Y")) {
    echo "User aborted. Bye."
    exit
}

# Clean up root folder
echo "Cleaning output directory (possibly with multiple runs)..."
Write-Host -NoNewline "    "
$i=1
while(Test-Path $OUTPUT_DIRECTORY) {
	
	Write-Host -NoNewline "Trial $i.."
	
	Remove-Item -Recurse -Force "$OUTPUT_DIRECTORY"
	$i = $i + 1
	if($i -gt $NUM_TRIALS_FOR_CLEANING_OUTPUT_DIRECTORY) {
		echo "Maximum number of trials reached. Please try to clean the output directory, manually."
		echo "Will now stop."
		exit
	}
}  	
echo ""
echo "Cleaning output directory done."

echo "Creating output directory"
mkdir $OUTPUT_DIRECTORY | out-null

echo "Starting Eclipse instances..."
# Start Eclipse for all workspaces
$firstIteration = $TRUE
foreach ($WORKSPACE in $WORKSPACES) {
    if (!($WORKSPACE -eq $WORKSPACES[0]) ) {
        $firstIteration = $FALSE
        echo "    Sleeping for $sleepTimeBetweenStartsInSeconds seconds..."
        Start-Sleep -s $sleepTimeBetweenStartsInSeconds
    }
    
    [System.Collections.ArrayList]$argumentList = '-data',$WORKSPACE,'-application','org.moflon.testapplication','-showLocation','-perspective','org.moflon.ide.ui.perspective'
    if($USE_CONSOLE) {
        [void]$argumentList.Add('-console')
        [void]$argumentList.Add('-consoleLog')
    }
    
  	$eclipse = Start-Process -WorkingDirectory $OUTPUT_DIRECTORY -FilePath $ECLIPSE_HOME\eclipse.exe -ArgumentList $argumentList   -PassThru
    $eclipsePid = $eclipse.Id
  	echo "    [$($eclipsePid)] Workspace '$WORKSPACE'"
  	$eclipsePid >> "$OUTPUT_DIRECTORY\pids.txt"
    
    # The following lines may be enabled to reduce the process priority of the launched Eclipse instance (64=Idle, 16384=Low, 32=Normal, 32768=High, 128=Higher, 256=Real-time)
    # For more information see: http://blogs.technet.com/b/heyscriptingguy/archive/2010/04/12/hey-scripting-guy-april-12-2010.aspx
    #[void]([wmi]"win32_process.handle='$eclipsePid'").setPriority(16384)
    #echo "    [$($eclipsePid)] Priority: $(([wmi]"win32_process.handle='$eclipsePid'").Priority)"
  	
}
