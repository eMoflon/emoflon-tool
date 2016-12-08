@echo off

call config.bat

cd "%deployment_directory%"

date /T
time /T

:: Generates a Certificate
for /r %%f in (*.jar) do "%jarsigner%" -keystore "%key_store%" -storepass "%store_pass%" -tsa %timestamp%  -verbose %%f %alias%

date /T
time /T

:: Verify a Certificate
for /r %%f in (*.jar) do "%jarsigner%" -keystore "%key_store%" -storepass "%store_pass%" -verify -verbose -certs %%f

date /T
time /T

cd %original_directory%

echo all Jars are signed
pause