include ./MakefileConfig

JAR_FILES := $(shell /usr/bin/find $(DEPLOYMENT_DIRECTORY) -name "*org.moflon*.jar")
SIGNED_JAR_FILES := $(JAR_FILES:%.jar=%.signedjar)

signall: $(SIGNED_JAR_FILES)
	echo "Done. You will need to remove the generated *.signedjar files manually"
	#rm $(SIGNED_JAR_FILES)
	 
%.signedjar : %.jar
	$(JAR_SIGNER) -keystore $(KEYSTORE_LOCATION) -storepass $(KEYSTORE_PASSWORD) -tsa $(TIMESTAMP_SERVER_URL)-verbose $< $(KEYSTORE_ALIAS)
	cp $< $@
	 

	 
#.PHONY: signall
 