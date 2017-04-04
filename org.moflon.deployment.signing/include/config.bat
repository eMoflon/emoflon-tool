:: Custom properties

set "original_directory=%cd%"

:: location to store the Certificates
set "key_store=%original_directory%\eMoflon.keystore"

:: Generic properties - they need not be adjusted, normally.

:: Find the Java JRE Home
for /f "tokens=* USEBACKQ" %%i in (`java -jar .\dependencies\homefinder.jar`) do set "java_jre_home=%%i"

:: Find the Java JDK Home
for /d %%d in ("%java_jre_home%\..\jdk*") do set "java_jdk_home=%%d"

:: Set jarsigner variable
set "jarsigner=%java_jdk_home%\bin\jarsigner.exe"

:: Set keytool variable
set "keytool=%java_jdk_home%\bin\keytool.exe"

:: Find the Java JRE Home
for /f "tokens=* USEBACKQ" %%i in (`java -jar .\dependencies\homefinder.jar`) do set "java_jre_home=%%i"

:: the Alias
set "alias=eMoflon"

:: Timestampserver
set "timestamp=http://timestamp.comodoca.com/rfc3161"


::set /p deployment_directory="Please provide deployment directory: "

:: Read %store_pass% variable
set /p store_pass="Please enter eMoflon store password: "

:: Hide password a little...
cls

echo JRE home '%java_jre_home%'
::echo Deployment directory: '%deployment_directory%'


