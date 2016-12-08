##Requirements
- eMoflon.keystore (in this folder)
- Internet connection (for time stamp)

##How to use it

- Run eMoflon-Deployment_signer.bat (if your PC is not very fast take a coffee)
- The script will sign all jars contained in the provided deployment directory (e.g., update-site2) in-place and recursively.
- If no Errors are shown, you're done.

## Additional scripts
- *config.bat* contains general configuration properties
- *eMoflon-Deployment_createKeystore.bat* can be used to create a new key store
- *eMoflon-Deployment_listKeystore.bat* lists the certificates in the key store
- *dependencies/homefinder.jar* extracts the *java.home* property of the active Java runtime environment

##Troubleshooting

###JDK cannot be found
This happens if you have installed multiple JDK instances (i.e. JDK 1.7 and JDK 1.8).
So you must change 'for /d %%d in ("%java_jre_home%\..\jdk*") do set "java_jdk_home=%%d"' to 'set "java_jdk_home=YourJDKPath"' in the batch files. 
