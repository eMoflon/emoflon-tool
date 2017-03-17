include ./MakefileConfig

#JAR_FILES := $(shell /usr/bin/find $(DEPLOYMENT_DIRECTORY) \( -name "*org.moflon*.jar" -and ! -name "*org.moflon.sdm.democles.patternmatcher*.jar" \))
JAR_FILES := $(shell /usr/bin/find $(DEPLOYMENT_DIRECTORY) \( -name "*org.moflon*.jar" -or -name "*MocaTree*.jar" -or -name "*SDMLanguage*.jar" \))
SIGNED_JAR_FILES := $(JAR_FILES:%.jar=%.signedjar)

signall: $(SIGNED_JAR_FILES)
	rm $(SIGNED_JAR_FILES)
	echo "Done"
	 
%.signedjar : %.jar
	$(JAR_SIGNER) -keystore $(KEYSTORE_LOCATION) -storepass $(KEYSTORE_PASSWORD) -tsa $(TIMESTAMP_SERVER_URL)-verbose $< $(KEYSTORE_ALIAS)
	cp $< $@
	 
	 
#.PHONY: signall
 