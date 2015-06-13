# http://powershell.com/cs/blogs/tobias/archive/2012/05/09/managing-child-processes.aspx
function Find-ChildProcess {
	param($ID=$PID)

	$CustomColumnID = @{
		Name = 'Id'
		Expression = { [Int[]]$_.ProcessID }
	}
	
	$result = Get-WmiObject -Class Win32_Process -Filter "ParentProcessID=$ID" |
	Select-Object -Property ProcessName, $CustomColumnID, CommandLine
	
	$result
	$result | Where-Object { $_.ID -ne $null } | ForEach-Object {
		Find-ChildProcess -id $_.Id
	}
}


$outputDirectory="$HOME\emoflon_autotest_tmp_dir"
$filename = "$outputDirectory\pids.txt"
foreach ($id in [System.IO.File]::ReadLines($filename)) {
   $child = Find-ChildProcess $id
	(Get-Process -Id $child.Id).CloseMainWindow()
}