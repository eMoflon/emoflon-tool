package org.moflon.codegen;

import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.moflon.dependency.Dependency;
import org.moflon.dependency.PackageRemappingDependency;

public class MetamodelLoader {
   private final ResourceSet resourceSet;

   public MetamodelLoader(ResourceSet resourceSet) {
      this.resourceSet = resourceSet;
      this.resourceSet.setPackageRegistry(new EPackageRegistryImpl(EPackage.Registry.INSTANCE));
      this.resourceSet.setResourceFactoryRegistry(new ResourceFactoryRegistryImpl());
   }

   public Iterable<Dependency> getEcoreResourceDependencies() {
      return Collections.emptyList();
   }
   
   public void initializeRegistry() {
//	   resourceSet.setPackageRegistry(new EPackageRegistryImpl(EPackage.Registry.INSTANCE));
//	   resourceSet.getPackageRegistry().put("http://www.eclipse.org/emf/2002/Ecore",
//			   new PluginPackageDescriptor("org.eclipse.emf.ecore", "org.eclipse.emf.ecore.EcorePackage"));
//	   resourceSet.getPackageRegistry().put("http://www.moflon.org.SDMLanguage",
//			   new PluginPackageDescriptor("org.moflon.SDMLanguage", "SDMLanguage.SDMLanguagePackage"));
//	   resourceSet.getPackageRegistry().put("http://www.moflon.org.SDMLanguage.activities",
//			   new PluginPackageDescriptor("org.moflon.SDMLanguage", "SDMLanguage.activities.ActivitiesPackage"));
//	   resourceSet.getPackageRegistry().put("http://www.moflon.org.SDMLanguage.calls",
//			   new PluginPackageDescriptor("org.moflon.SDMLanguage", "SDMLanguage.calls.CallsPackage"));
//	   resourceSet.getPackageRegistry().put("http://www.moflon.org.SDMLanguage.calls.callExpressions",
//			   new PluginPackageDescriptor("org.moflon.SDMLanguage", "SDMLanguage.calls.callExpressions.CallExpressionsPackage"));
//	   resourceSet.getPackageRegistry().put("http://www.moflon.org.SDMLanguage.expressions",
//			   new PluginPackageDescriptor("org.moflon.SDMLanguage", "SDMLanguage.expressions.ExpressionsPackage"));
//	   resourceSet.getPackageRegistry().put("http://www.moflon.org.SDMLanguage.patterns",
//			   new PluginPackageDescriptor("org.moflon.SDMLanguage", "SDMLanguage.patterns.PatternsPackage"));
//	   resourceSet.getPackageRegistry().put("http://www.moflon.org.SDMLanguage.patterns.AttributeConstraints",
//			   new PluginPackageDescriptor("org.moflon.SDMLanguage", "SDMLanguage.patterns.AttributeConstraints.AttributeConstraintsPackage"));
//	   resourceSet.getPackageRegistry().put("http://www.moflon.org.SDMLanguage.patterns.patternExpressions",
//			   new PluginPackageDescriptor("org.moflon.SDMLanguage", "SDMLanguage.patterns.patternExpressions.PatternExpressionsPackage"));
//	   resourceSet.getPackageRegistry().put("http://www.moflon.org.SDMLanguage.precompiler",
//			   new PluginPackageDescriptor("org.moflon.SDMLanguage", "SDMLanguage.precompiler.PrecompilerPackage"));
   }
   
   public void loadDependencies(Iterable<Dependency> resourcesToLoad) {
      // Handle Ecore dependencies
      for (Dependency dependency : resourcesToLoad) {
         dependency.getResource(resourceSet, false);
      }
   }

   public final Resource loadResource(Dependency resourceLoader, boolean loadContent) {
      return resourceLoader.getResource(resourceSet, loadContent);
   }
   
   public Resource loadMetamodel(URI ecoreURI) {
      // Load Ecore model (i.e., the metamodel)
      PackageRemappingDependency resourceHandler =
            new PackageRemappingDependency(ecoreURI, true, false);
      return loadResource(resourceHandler, false);
   }
}
