Requirements

- eMoflon-Deployment_signer.bat

- eMoflon-Deployment_verifier.bat

- the dependencies folder with homefinder.jar in it

- the deployment at ./deployment Folder (it should look like .../deploment/update-site2)

- moflonCerts.keystore at the ./resources/certificate Folder

- internet connection is needed



How to use it

- run eMoflon-Deployment_signer.bat (if your PC is not very fast take a coffee)

- run eMoflon-Deployment_verifier.bat

If no Errors be shown, you're done.



TROUBLESHOOTING

- JDK cannot be found:
This happens if you have installed multiple JDK instances (i.e. JDK 1.7 and JDK 1.8).
So you must change 'for /d %%d in ("%java_jre_home%\..\jdk*") do set "java_jdk_home=%%d"' to 'set "java_jdk_home=YourJDKPath"' in the batch files. 
