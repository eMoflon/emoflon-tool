@echo off

call include/config.bat

"%keytool%" -genkey -alias "%alias%" ^
			-keyalg RSA ^
			-keystore "%key_store%" ^
			-keypass "%store_pass%" ^
			-storepass "%store_pass%" ^
			-validity 7300 ^
			-dname "CN=eMoflon Core Developers, OU=Real-Time Systems Lab, O=TU Darmstadt, L=Darmstadt, ST=Hesse, C=DE"

pause