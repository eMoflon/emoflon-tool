###
# CUSTOMIZATION INSTRUCTIONS
###

# 0.  Copy eMoflonTestRunner.sample.ps1 to a folder of your choice (test workspaces will be checked out here!)
# 1.  Correct the variable $eclipseHome in the script to fit to your Eclipse installation, i.e., the folder containing "eclipse.exe"
# 2.  Set the variable $outputDirectory to a suitable temporary directory.
# 3.  Select the test workspaces to run by (un)commenting the corresponding lines (e.g., [void]$workspaces.add("TestWorkspace_Democles_0")) 
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
$outputDirectory="[set output directory]"

# Path to eclipse - must contain eclipse.exe
$eclipseHome = "[set Eclipse home]"

# Time between two executions of Eclipse
$sleepTimeBetweenStartsInSeconds = 120

# Array of all test workspaces (see EMoflonStandardWorkspaces)
# (N.B. The cast to [void] avoids that the current size is printed.)
[System.Collections.ArrayList]$workspaces = @()
[void]$workspaces.add("TestWorkspace_Democles_0") # small
[void]$workspaces.add("TestWorkspace_DemoAndHandbook") # small
[void]$workspaces.add("TestWorkspace_Misc")
[void]$workspaces.add("TestWorkspace_TGG_0")
[void]$workspaces.add("TestWorkspace_TGG_1")
[void]$workspaces.add("TransformationZoo_0")
[void]$workspaces.add("TransformationZoo_1")
[void]$workspaces.add("ModuleAllInclMOSL")

# Using the following line, you can checkout a workspace using a specific branch ('rkluge-dev')
# This may not work if the corresponding repository already exists.
# Therefore, start in a clean state!
#[void]$workspaces.add("ModuleAllInclMOSL@rkluge-dev")
 

# Whether to spawn a new console for each Eclipse instance, showing standard output/standard error messages
$shallUseConsole = $FALSE # either $TRUE or $FALSE


# Number of trials to clean the output directory
$numberOfAttemptsForCleaningOutputDirectory = 5

echo "outputDirectory:         $outputDirectory"
echo "eclipseHome:             $eclipseHome"
echo "Use console?              $shallUseConsole"
echo "Interval between starts:  $sleepTimeBetweenStartsInSeconds"
echo "Feature version:          $(Get-ChildItem -Name "$eclipseHome/features/org.moflon.*")"
echo "Workspaces to be run:     $workspaces" 
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
while(Test-Path $outputDirectory) {
	
	Write-Host -NoNewline "Trial $i.."
	
	Remove-Item -Recurse -Force "$outputDirectory"
	$i = $i + 1
	if($i -gt $numberOfAttemptsForCleaningOutputDirectory) {
		echo "Maximum number of trials reached. Please try to clean the output directory, manually."
		echo "Will now stop."
		exit
	}
}  	
echo ""
echo "Cleaning output directory done."

echo "Creating output directory"
mkdir $outputDirectory | out-null

echo "Starting Eclipse instances..."
# Start Eclipse for all workspaces
$firstIteration = $TRUE
foreach ($workspace in $workspaces) {
    if (!($workspace -eq $workspaces[0]) ) {
        $firstIteration = $FALSE
        echo "    Sleeping for $sleepTimeBetweenStartsInSeconds seconds..."
        Start-Sleep -s $sleepTimeBetweenStartsInSeconds
    }
    
    [System.Collections.ArrayList]$argumentList = '-data',$workspace,'-application','org.moflon.testapplication','-showLocation','-perspective','org.moflon.ide.ui.perspective'
    if($shallUseConsole) {
        [void]$argumentList.Add('-console')
        [void]$argumentList.Add('-consoleLog')
    }
    
  	$eclipse = Start-Process -WorkingDirectory $outputDirectory -FilePath $eclipseHome\eclipse.exe -ArgumentList $argumentList   -PassThru
    $eclipsePid = $eclipse.Id
  	echo "    [$($eclipsePid)] Workspace '$workspace'"
  	$eclipsePid >> "$outputDirectory\pids.txt"
    
    # The following lines may be enabled to reduce the process priority of the launched Eclipse instance (64=Idle, 16384=Low, 32=Normal, 32768=High, 128=Higher, 256=Real-time)
    # For more information see: http://blogs.technet.com/b/heyscriptingguy/archive/2010/04/12/hey-scripting-guy-april-12-2010.aspx
    #[void]([wmi]"win32_process.handle='$eclipsePid'").setPriority(16384)
    #echo "    [$($eclipsePid)] Priority: $(([wmi]"win32_process.handle='$eclipsePid'").Priority)"
  	
}
