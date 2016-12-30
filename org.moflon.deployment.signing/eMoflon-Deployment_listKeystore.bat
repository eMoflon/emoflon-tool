@echo off

call config.bat

"%keytool%" -list -v ^
			-keystore "%key_store%" ^
			-storepass "%store_pass%" 

pause