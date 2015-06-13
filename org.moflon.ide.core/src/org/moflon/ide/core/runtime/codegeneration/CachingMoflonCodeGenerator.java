package org.moflon.ide.core.runtime.codegeneration;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.codegen.eclipse.MoflonCodeGenerator;
import org.moflon.ide.core.CoreActivator;
import org.moflon.properties.MoflonPropertiesContainerHelper;
import org.moflon.util.eMoflonSDMUtil;

import MoflonPropertyContainer.BuildFilter;
import MoflonPropertyContainer.BuildFilterRule;
import MoflonPropertyContainer.MoflonPropertiesContainer;

public class CachingMoflonCodeGenerator extends MoflonCodeGenerator {
   private static final Logger logger = Logger.getLogger(CachingMoflonCodeGenerator.class);
   
   private HashMap<IFile, String> cachedGeneratedCode = new HashMap<>();

   public CachingMoflonCodeGenerator(final IFile ecoreFile, final ResourceSet resourceSet) {
	   super(ecoreFile, resourceSet);
   }
	
   private final IProject getProject() {
	   return getEcoreFile().getProject();
   }

   @Override
   public IStatus processResource(final IProgressMonitor monitor) {
	   final Resource resource = getEcoreResource();
      // filtered tgg rules should not be built
      filterTGGsFromBuildFilterProps(resource);
      
      final IStatus status = super.processResource(monitor);
      
      restoreCachedGeneratedCode();
      
      CoreActivator.getDefault().setDirty(getProject(), false);
      
      return status;
   }
   
   private void cacheGeneratedCodeForRules(final List<EClassifier> rulesToFilter)
   {
      cachedGeneratedCode.clear();

      List<EClassifier> rulesToDeactivate = new ArrayList<>();
      for (EClassifier rule : rulesToFilter)
      {
         IFile impl = getProject().getFile("gen/" + getProject().getName() + "/Rules/impl/" + rule.getName() + "Impl.java");

         try
         {
            StringWriter writer = new StringWriter();
            IOUtils.copy(impl.getContents(), writer, impl.getCharset());
            cachedGeneratedCode.put(impl, writer.toString());
         } catch (Exception e)
         {
            logger.error("Unable to chache code for: " + rule.getName() + ", deactivating corresponding filter...");
            rulesToDeactivate.add(rule);
         }
      }

      rulesToFilter.removeAll(rulesToDeactivate);
   }

   private void restoreCachedGeneratedCode()
   {
      for (IFile file : cachedGeneratedCode.keySet())
         try
         {
            file.setContents(IOUtils.toInputStream(cachedGeneratedCode.get(file)), IFile.FORCE, new NullProgressMonitor());
         } catch (CoreException e)
         {
            logger.error("Unable to restore code for: " + file.getName());
         }

      cachedGeneratedCode.clear();
   }
   
   private void filterTGGsFromBuildFilterProps(final Resource genEcoreResource)
   {
      MoflonPropertiesContainer moflonProperties = MoflonPropertiesContainerHelper.load(getProject(), new NullProgressMonitor());

      Collection<BuildFilter> filter = moflonProperties.getBuildFilter();

      EPackage rootPackage = (EPackage) genEcoreResource.getContents().get(0);
      EPackage rulesPackage = rootPackage.getESubpackages().get(0);
      List<EClassifier> rulesToFilter = new ArrayList<>();
      for (EClassifier rule : rulesPackage.getEClassifiers())
      {
         for (BuildFilter bf : filter)
         {
            if (bf.isActivated())
               for (BuildFilterRule bfr : bf.getRules())
               {
                  if (bfr.getValue().equals(rule.getName()))
                     rulesToFilter.add(rule);
               }
         }
      }

      cacheGeneratedCodeForRules(rulesToFilter);

      for (EClassifier rule : rulesToFilter)
      {
         EClass ruleClass = (EClass) rule;
         for (EOperation op : ruleClass.getEOperations())
            eMoflonSDMUtil.deleteActivity(op);
      }
   }

}
