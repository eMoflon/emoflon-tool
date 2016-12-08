@echo off

call config.bat

"%keytool%" -list ^
			-keystore "%key_store%" ^
			-storepass "%store_pass%" 

pause