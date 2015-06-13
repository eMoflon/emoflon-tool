package org.moflon.util.plugins.manifest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.Manifest;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.plugins.MoflonPluginsActivator;

/**
 * This class provides functionality to modify manifest files.
 *
 */
public class ManifestFileUpdater
{

   public enum AttributeUpdatePolicy {
      FORCE, KEEP;
   }

   /**
    * This means that the dependency is not available as a plugin -> the user must manipulate the projects buildpath
    * manually!
    **/
   public static final String IGNORE_PLUGIN_ID = "__ignore__";

   /**
    * Delegates to {@link #processManifest(IProject, Function, IProgressMonitor)} using a {@link NullProgressMonitor}
    */
   public void processManifest(final IProject project, final Function<Manifest, Boolean> consumer) throws CoreException, IOException
   {
      this.processManifest(project, consumer, new NullProgressMonitor());
   }

   /**
    * Modifies the manifest of the given project.
    * 
    * The method reads the manifest, applies the given function, and, if the function returns true, saves the manifest
    * again.
    * 
    * @param consumer
    *           A function that returns whether it has modified the manifest.
    * @throws CoreException
    * @throws IOException
    */
   public void processManifest(final IProject project, final Function<Manifest, Boolean> consumer, final IProgressMonitor monitor) throws CoreException,
         IOException
   {
      try
      {
         monitor.beginTask("Processing manifest of project " + project.getName(), 100);
         IFile manifestFile = WorkspaceHelper.getManifestFile(project);
         Manifest manifest = new Manifest();

         if (manifestFile.exists())
         {
            readManifestFile(manifestFile, manifest);
         }
         monitor.worked(10);

         final boolean hasManifestChanged = consumer.apply(manifest);
         monitor.worked(80);

         if (hasManifestChanged)
         {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            new ManifestWriter().write(manifest, stream);
            String formattedManifestString = prettyPrintManifest(stream.toString());
            if (!manifestFile.exists())
            {
               WorkspaceHelper.addAllFoldersAndFile(project, manifestFile.getProjectRelativePath(), formattedManifestString,
                     WorkspaceHelper.createSubMonitor(monitor, 10));
            } else
            {
               final ByteArrayInputStream fileOutputStream = new ByteArrayInputStream(formattedManifestString.getBytes());
               manifestFile.setContents(fileOutputStream, IFile.FORCE, WorkspaceHelper.createSubMonitor(monitor, 10));
               stream.close();
            }
         } else
         {
            monitor.worked(10);
         }
      } finally
      {
         monitor.done();
      }
   }

   /**
    * Updates the given attribute in the manifest.
    * 
    * @return whether the value of the attribute changed
    */
   public static boolean updateAttribute(final Manifest manifest, final Name attribute, final String value, final AttributeUpdatePolicy updatePolicy)
   {
      Attributes attributes = manifest.getMainAttributes();
      if ((!attributes.containsKey(attribute) || attributes.get(attribute) == null //
      || attributes.get(attribute).equals("null")) //
            || (attributes.containsKey(attribute) && updatePolicy == AttributeUpdatePolicy.FORCE))
      {
         Object previousValue = attributes.get(attributes);
         if (!value.equals(previousValue))
         {
            attributes.put(attribute, value);
            return true;
         } else
         {
            return false;
         }
      } else
      {
         return false;
      }
   }

   /**
    * Updates the manifest (if necessary) to contain the given dependencies.
    * 
    * @param newDependencies
    *           the dependencies to be added (if not present yet)
    * @return whether the manifest was changed
    */
   public static boolean updateDependencies(final Manifest manifest, final List<String> newDependencies)
   {
      final List<String> currentDependencies = extractDependencies(manifest);

      final List<String> missingNewDependencies = calculateMissingDependencies(currentDependencies, newDependencies);

      if (!missingNewDependencies.isEmpty())
      {
         for (final String newDependency : missingNewDependencies)
         {
            currentDependencies.add(newDependency);
         }

         addDependenciesToManifest(manifest, currentDependencies);
         return true;
      } else
      {
         return false;
      }
   }

   /**
    * Calculates all dependencies in newDependencies that are not present in existingDependencies.
    * 
    * All dependencies containing {@link ManifestFileUpdater#IGNORE_PLUGIN_ID} are ignored. If a dependency appears in
    * both lists with different metadata (e.g., bundle-version), nothing happens.
    * 
    * @param existingDependencies
    * @param newDependencies
    * @return the missing dependencies, a sublist of newDependencies
    */
   public static List<String> calculateMissingDependencies(final List<String> existingDependencies, final List<String> newDependencies)
   {
      final Collection<String> existingDependencyPluginIds = existingDependencies.stream().map(ManifestFileUpdater::extractPluginId)
            .collect(Collectors.toList());
      final List<String> missingDependencies = newDependencies.stream().filter(newDependency -> !newDependency.contains(IGNORE_PLUGIN_ID))
            .filter(newDependency -> !existingDependencyPluginIds.contains(ManifestFileUpdater.extractPluginId(newDependency))).collect(Collectors.toList());

      return missingDependencies;
   }

   /**
    * Reads the dependencies of the given manifest (i.e., values of the key Require-Bundle) and splits them at commas.
    * 
    * @param manifest
    * @return
    */
   public static List<String> extractDependencies(final Manifest manifest)
   {
      final String currentDependencies = (String) manifest.getMainAttributes().get(PluginManifestConstants.REQUIRE_BUNDLE);
      final List<String> dependencies = ManifestFileUpdater.extractDependencies(currentDependencies);
      return dependencies;
   }

   public Map<String, IProject> extractPluginIDToProjectMap(final Collection<IProject> projects)
   {
      final Map<String, IProject> idToProject = new HashMap<>();
      projects.stream().forEach(p -> {
         try
         {
            processManifest(p, manifest -> {
               idToProject.put(extractPluginId(getID(p, manifest)), p);
               return false;
            });
         } catch (Exception e)
         {
            idToProject.put(p.getName(), p);
         }
      });

      return idToProject;
   }

   private String getID(final IProject p, final Manifest manifest)
   {
      return (String) manifest.getMainAttributes().get(PluginManifestConstants.BUNDLE_SYMBOLIC_NAME);
   }

   public Collection<String> getDependenciesAsPluginIDs(final IProject project)
   {
      Collection<String> dependencies = new ArrayList<>();

      try
      {
         processManifest(project, manifest -> {
            dependencies.addAll(extractDependencies((String) manifest.getMainAttributes().get(PluginManifestConstants.REQUIRE_BUNDLE)));
            return false;
         });
      } catch (Exception e)
      {
         e.printStackTrace();
      }

      return dependencies.stream().map(dep -> extractPluginId(dep)).collect(Collectors.toList());
   }

   /**
    * Returns the plugin Id for a given dependency entry, which may contain additional metadata, e.g.
    *
    * Input: org.moflon.ide.core;bundle-version="1.0.0" Output: org.moflon.ide.core
    */
   public static String extractPluginId(final String existingDependency)
   {
      int indexOfSemicolon = existingDependency.indexOf(";");
      if (indexOfSemicolon > 0)
      {
         return existingDependency.substring(0, indexOfSemicolon);
      } else
      {
         return existingDependency;
      }
   }

   /**
    * Extracts the dependencies from the given list of properties.
    */
   public static List<String> extractDependencies(final String dependencies)
   {
      List<String> extractedDependencies = new ArrayList<>();
      if (dependencies != null && !dependencies.isEmpty())
      {
         extractedDependencies.addAll(Arrays.asList(dependencies.split(",")));
      }

      return extractedDependencies;
   }

   private String prettyPrintManifest(final String string)
   {
      return new ManifestPrettyPrinter().print(string);
   }

   private void readManifestFile(final IFile manifestFile, final Manifest manifest) throws CoreException
   {
      try
      {
         InputStream manifestFileContents = manifestFile.getContents();
         manifest.read(manifestFileContents);
         manifestFileContents.close();
      } catch (IOException e)
      {
         throw new CoreException(new Status(IStatus.ERROR, MoflonPluginsActivator.PLUGIN_ID, "Failed to read existing MANIFEST.MF: " + e.getMessage(), e));
      }
   }

   private static void addDependenciesToManifest(final Manifest manifest, final List<String> dependencies)
   {
      String dependenciesString = ManifestFileUpdater.createDependenciesString(dependencies);

      if (!dependenciesString.matches("\\s*"))
      {
         manifest.getMainAttributes().put(PluginManifestConstants.REQUIRE_BUNDLE, dependenciesString);
      }
   }

   private static String createDependenciesString(final List<String> dependencies)
   {
      return dependencies.stream().filter(dep -> !dep.equals("")).collect(Collectors.joining(","));
   }

}
