## Important note on building the update site

* It is absolutely crucial to reset the *site.xml* file after every build of the update site
    * When opening *site.xml*, the entries per category should 
       * look like this: "org.moflon.ide.feature (2.28.0.qualifier)"
       * NOT like this: "features/org.moflon.ide.feature_2.28.0.201702211758.jar"
	   * If this rule is not obeyed to, the generated features are not properly moved into the appropriate categories 