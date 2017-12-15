#
# This Powershell script allows to list, install, and uninstall eMoflon features
#
# The term "provisioning" subsumes "installing" and "uninstalling" in this context.
# Author: Roland Kluge
# Date: 2017-10-09
#
# Thanks to http://www.lorenzobettini.it/2012/10/installing-eclipse-features-via-the-command-line/
# For more information see: http://help.eclipse.org/oxygen/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Fguide%2Fp2_director.html
#

# Eclipse home - should contain eclipse(.exe)
$eclipseHome="[INSERT_PATH_TO_ECLIPSE_HERE]"

# Path to local eMoflon repository (contains site.xml, using Eclipse path conventions, e.g., file:/C:/myRepo)
$localRepoPath="file:/C:/Users/rkluge/Documents/___tmp/eMoflonLocalUpdateSiteFromL0/updatesite/"

$eMoflonUpdateSite="https://emoflon.github.io/eclipse-plugin/beta/updatesite/"
$emoflonFeatureId="org.moflon.ide.feature.feature.group"
$emoflonVisualizationFeatureId="org.moflon.ide.visualization.feature.feature.group"
$emoflonMoslTggFeatureId="org.moflon.ide.visualization.feature.feature.group"
$emoflonMoslGtFeatureId="org.moflon.gt.mosl.feature.feature.group"

# Comma-separated list of feature IDs to be provisioned (must end with 'feature.group')
$provisioningFeatureIds="$emoflonFeatureId,$emoflonVisualizationFeatureId,$emoflonMoslTggFeatureId,$emoflonMoslGtFeatureId"
# Comma-separated list of repositories
$provisioningRepositoryPaths="$localRepoPath,http://files.idi.ntnu.no/publish/plantuml/repository/"

# Choose main operation mode of the script
# L: list bundles in the repository given by $localRepoPath
# I: install  features specified in $provisioningFeatureIds
# U: uninstall features specified in $provisioningFeatureIds`
$mode="I"

# Specifies whether to perform a dry run
# $TRUE: only simulate the changes - nothing will happen
# $FALSE: truly perform the specified changes
$dryRun=$FALSE

# Specifies whether Eclipse shall be started after a successful provisioning operation ("I", "U")
# $TRUE: Start Eclipse
# $FALSE: Do not start Eclipse
$shallLaunchEclipseAfterInstallation=$TRUE

# In the following, the argument list is built up according to the selected mode
[System.Collections.ArrayList]$argumentList="-clean","-purgeHistory","-application","org.eclipse.equinox.p2.director","-noSplash"
switch($mode) {
  # Use the 'L' mode on conjunction with the '>>' operator to save the list of bundles in a file
  "L"{
    [void]$argumentList.add("-repository")
    [void]$argumentList.add($localRepoPath)
    [void]$argumentList.add("-list")
  }
  # Use the 'I' mode to install the specified features
  "I" {
    [void]$argumentList.add("-repository")
    [void]$argumentList.add($provisioningRepositoryPaths)
    [void]$argumentList.add("-installIUs")
    [void]$argumentList.add($provisioningFeatureIds)
  }
  # Use the 'U' mode to uninstall the specified features
  "U" {
    [void]$argumentList.add("-repository")
    [void]$argumentList.add($provisioningRepositoryPaths)
    [void]$argumentList.add("-uninstallIUs")
    [void]$argumentList.add($provisioningFeatureIds)
  }
}
if($dryRun) {
  echo "Dry-run enabled. All shown changes are only simulated!"
  [void]$argumentList.add("-verifyOnly")
}

$provisioningProcess = Start-Process -WorkingDirectory "." -FilePath "$eclipseHome/eclipsec.exe" -ArgumentList $argumentList -NoNewWindow -Wait
$isProvisioning = ($mode -eq "I") -or ($mode -eq "U")
if($shallLaunchEclipseAfterInstallation -and $isProvisioning) {
  echo "Launching Eclipse after installation"
  if(!$dryRun) { 
    Start-Process -WorkingDirectory "." -FilePath "$eclipseHome/eclipse.exe"
  }
}