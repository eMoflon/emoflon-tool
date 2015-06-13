package org.moflon.util.plugins.manifest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.Manifest;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;

public class ExportedPackagesInManifestUpdater
{

   public static final String EXPORT_PACKAGE_KEY = "Export-Package";

   public static final Name EXPORT_PACKAGE = new Attributes.Name(EXPORT_PACKAGE_KEY);

   private IProject project;

   private GenModel genModel;

   public ExportedPackagesInManifestUpdater(final IProject project, final GenModel genModel)
   {
      this.project = project;
      this.genModel = genModel;
   }

   public void run(final IProgressMonitor monitor) throws CoreException, IOException
   {
      new ManifestFileUpdater().processManifest(project, manifest -> {
         return updateExportedPackages(manifest);
      });

      monitor.done();
   }

   private boolean updateExportedPackages(final Manifest manifest)
   {
      String exportedPackageString = (String) manifest.getMainAttributes().get(EXPORT_PACKAGE);
      List<String> exportedPackages = new ArrayList<>();
      if (exportedPackageString != null && !exportedPackageString.isEmpty())
      {
         exportedPackages.addAll(Arrays.asList(exportedPackageString.split(",")));
      }
      Set<String> missingPackages = new HashSet<>(getExportPackage());
      missingPackages.removeAll(exportedPackages);
      exportedPackages.addAll(missingPackages);

      String exportedPackagesString = exportedPackages.stream().collect(Collectors.joining(","));
      if (!exportedPackagesString.isEmpty())
      {
         manifest.getMainAttributes().put(EXPORT_PACKAGE, exportedPackagesString);
      }
      else {
         manifest.getMainAttributes().remove(EXPORT_PACKAGE);
      }

      return true;
   }

   private List<String> getExportPackage()
   {
      final List<String> exportedPackages = new ArrayList<>();
      genModel.getAllGenPackagesWithClassifiers().forEach(genPackage -> {
         exportedPackages.add(genPackage.getInterfacePackageName());
         exportedPackages.add(genPackage.getUtilitiesPackageName());
         exportedPackages.add(genPackage.getClassPackageName());
      });
      return exportedPackages;
   }
}
