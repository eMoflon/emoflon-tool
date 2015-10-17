package org.moflon.util.plugins;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.plugins.MoflonPluginsActivator;
import org.moflon.util.plugins.manifest.ManifestFileUpdater;
import org.moflon.util.plugins.manifest.ManifestFileUpdater.AttributeUpdatePolicy;
import org.moflon.util.plugins.manifest.PluginManifestConstants;
import org.moflon.util.plugins.xml.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Deprecated
public class FeatureUtils
{

   private static final String VERSION_ATTRIBUTE_NAME = "version";

   private static final String ID_ATTRIBUTE_NAME = "id";

   private static final String URL_ATTRIBUTE_NAME = "url";

   public static List<String> getAllFeatureIdsInSite(final IFile siteXml) throws CoreException
   {
      List<String> featureIds = new ArrayList<>();
      String content;
      try
      {
         content = readContent(siteXml);

         if (content != null)
         {
            final Document doc = XMLUtils.parseXmlDocument(content);
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("/site/feature");
            NodeList extensionPoints = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < extensionPoints.getLength(); ++i)
            {
               Node item = extensionPoints.item(i);
               if (item instanceof Element)
               {
                  final Element element = (Element) item;
                  final String id = element.getAttribute("id");
                  featureIds.add(id);
               }
            }
            return featureIds;
         } else
         {
            return null;
         }
      } catch (IOException | XPathExpressionException e)
      {
         throw new CoreException(new Status(IStatus.ERROR, MoflonPluginsActivator.getDefault().getPluginId(), "Exception while processing feature file " + siteXml, e));
      }
   }

   public static String getFeatureId(final IFile featureFile) throws CoreException
   {
      String content;
      try
      {
         content = readContent(featureFile);

         if (content != null)
         {
            final Document doc = XMLUtils.parseXmlDocument(content);
            final Element rootElement = (Element) doc.getChildNodes().item(0);
            final String id = rootElement.getAttribute("id");
            return id;
         } else
         {
            return null;
         }
      } catch (IOException e)
      {
         throw new CoreException(new Status(IStatus.ERROR, MoflonPluginsActivator.getDefault().getPluginId(), "Exception while processing feature file " + featureFile, e));
      }
   }

   public static List<String> getAllPluginsInFeature(final IFile featureFile) throws CoreException
   {
      List<String> pluginIds = new ArrayList<>();
      String content;
      try
      {
         content = readContent(featureFile);

         if (content != null)
         {
            final Document doc = XMLUtils.parseXmlDocument(content);
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("/feature/plugin");
            NodeList extensionPoints = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < extensionPoints.getLength(); ++i)
            {
               Node item = extensionPoints.item(i);
               if (item instanceof Element)
               {
                  final Element element = (Element) item;
                  final String id = element.getAttribute("id");
                  pluginIds.add(id);
               }
            }
            return pluginIds;
         } else
         {
            return null;
         }
      } catch (IOException | XPathExpressionException e)
      {
         throw new CoreException(new Status(IStatus.ERROR, MoflonPluginsActivator.getDefault().getPluginId(), "Exception while processing feature file " + featureFile, e));
      }
   }

   public static void updateVersionsOfAllPluginsInFeature(final IFile featureFile, final List<String> ignoredPluginIdPatterns, final String versionNumber,
         final IProgressMonitor monitor) throws CoreException
   {
      try
      {
         final List<String> pluginIds = getAllPluginsInFeature(featureFile);
         monitor.beginTask("Updating plugin ids of feature", pluginIds.size());

         for (final String pluginId : pluginIds)
         {
            if (!shallIgnorePluginId(ignoredPluginIdPatterns, pluginId))
            {
               IProject project = WorkspaceHelper.getProjectByPluginId(pluginId);

               if (project != null)
               {
                  new ManifestFileUpdater().processManifest(project, manifest -> {
                     final String currentVersion = manifest.getMainAttributes().getValue(PluginManifestConstants.BUNDLE_VERSION);
                     if (!currentVersion.equals(versionNumber))
                     {
                        return ManifestFileUpdater
                              .updateAttribute(manifest, PluginManifestConstants.BUNDLE_VERSION, versionNumber, AttributeUpdatePolicy.FORCE);
                     } else
                     {
                        return false;
                     }
                  });
               }
            }

            monitor.worked(1);
         }

      } catch (final IOException e)
      {
         throw new CoreException(new Status(IStatus.ERROR, MoflonPluginsActivator.getDefault().getPluginId(), "", e));
      } finally
      {
         monitor.done();
      }
   }

   private static boolean shallIgnorePluginId(final List<String> ignoredPluginIdPatterns, final String pluginId)
   {
      boolean ignorePlugin = false;
      for (final String ignoredPluginIdPattern : ignoredPluginIdPatterns)
      {
         if (Pattern.matches(ignoredPluginIdPattern, pluginId))
         {
            ignorePlugin = true;
            break;
         }
      }
      return ignorePlugin;
   }

   public static void updateVersionOfFeature(final IFile featureFile, final IFile siteFile, final String versionNumber, final IProgressMonitor monitor)
         throws CoreException
   {
      try
      {
         monitor.beginTask("Updating version of feature", 4);
         final String content = readContent(featureFile);
         monitor.worked(1);

         if (content != null)
         {
            final Document doc = XMLUtils.parseXmlDocument(content);
            final Element rootElement = (Element) doc.getElementsByTagName("feature").item(0);
            if (!versionNumber.equals(rootElement.getAttribute(VERSION_ATTRIBUTE_NAME)))
            {
               rootElement.setAttribute(VERSION_ATTRIBUTE_NAME, versionNumber);

               String output = XMLUtils.formatXmlString(doc, WorkspaceHelper.createSubmonitorWith1Tick(monitor));

               MoflonUtil.writeContentToFile(output, featureFile, WorkspaceHelper.createSubmonitorWith1Tick(monitor));

               updateVersionOfFeatureInSite(siteFile, rootElement.getAttribute(ID_ATTRIBUTE_NAME), versionNumber,
                     WorkspaceHelper.createSubmonitorWith1Tick(monitor));
            }
         }
      } catch (IOException e)
      {
         throw new CoreException(new Status(IStatus.ERROR, MoflonPluginsActivator.getDefault().getPluginId(), "Exception while processing feature file " + featureFile, e));
      } finally
      {
         monitor.done();
      }
   }

   public static void updateVersionOfFeatureInSite(final IFile siteFile, final String featureId, final String versionNumber, final IProgressMonitor monitor)
         throws CoreException
   {
      try
      {
         monitor.beginTask("Updating version of feature in " + siteFile, 3);
         final String content = readContent(siteFile);
         monitor.worked(1);

         if (content != null)
         {
            final Document doc = XMLUtils.parseXmlDocument(content);
            final Element rootElement = (Element) doc.getElementsByTagName("site").item(0);
            final NodeList featureElements = rootElement.getElementsByTagName("feature");
            boolean changed = false;
            for (int i = 0; i < featureElements.getLength(); ++i)
            {
               final Element featureElement = (Element) featureElements.item(i);
               if (featureElement.getAttribute(ID_ATTRIBUTE_NAME).equals(featureId)
                     && !versionNumber.equals(featureElement.getAttribute(VERSION_ATTRIBUTE_NAME)))
               {
                  featureElement.setAttribute(VERSION_ATTRIBUTE_NAME, versionNumber);
                  featureElement.setAttribute(URL_ATTRIBUTE_NAME, "features/" + featureId + "_" + versionNumber + ".jar");
                  changed = true;
               }
            }

            if (changed)
            {
               String output = XMLUtils.formatXmlString(doc, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
               MoflonUtil.writeContentToFile(output, siteFile, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
            }

         }
      } catch (IOException e)
      {
         throw new CoreException(new Status(IStatus.ERROR, MoflonPluginsActivator.getDefault().getPluginId(), "Exception while processing site file " + siteFile, e));
      } finally
      {
         monitor.done();
      }
   }

   private static String readContent(final IFile featureFile) throws IOException, CoreException
   {
      if (featureFile.exists())
      {
         return IOUtils.toString(featureFile.getContents());
      } else
      {
         throw new FileNotFoundException("File: " + featureFile);
      }

   }
}
