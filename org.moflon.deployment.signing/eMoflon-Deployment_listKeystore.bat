@echo off

call include/config.bat

"%keytool%" -list -v ^
			-keystore "%key_store%" ^
			-storepass "%store_pass%" 

pause