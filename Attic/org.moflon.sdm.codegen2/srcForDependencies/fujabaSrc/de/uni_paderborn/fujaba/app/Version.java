/*
 * The FUJABA ToolSuite project:
 *
 *   FUJABA is the acronym for 'From Uml to Java And Back Again'
 *   and originally aims to provide an environment for round-trip
 *   engineering using UML as visual programming language. During
 *   the last years, the environment has become a base for several
 *   research activities, e.g. distributed software, database
 *   systems, modelling mechanical and electrical systems and
 *   their simulation. Thus, the environment has become a project,
 *   where this source code is part of. Further details are avail-
 *   able via http://www.fujaba.de
 *
 *      Copyright (C) Fujaba Development Group
 *
 *   This library is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU Lesser General Public
 *   License as published by the Free Software Foundation; either
 *   version 2.1 of the License, or (at your option) any later version.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the Free
 *   Software Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 *   MA 02111-1307, USA or download the license under
 *   http://www.gnu.org/copyleft/lesser.html
 *
 * WARRANTY:
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 * Contact address:
 *
 *   Fujaba Management Board
 *   Software Engineering Group
 *   University of Paderborn
 *   Warburgerstr. 100
 *   D-33098 Paderborn
 *   Germany
 *
 *   URL  : http://www.fujaba.de
 *   email: info@fujaba.de
 *
 */
package de.uni_paderborn.fujaba.app;


import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

import de.uni_paderborn.lib.util.CharacterHandlingSaxHandler;


/**
 * @author $Author$
 * @version $Revision$
 */
public class Version
{
   // TODO !!! separate Version class from FujabaApp and UI related packages
   
   /**
    * log4j logging
    */
   final static transient Logger log = Logger.getLogger(Version.class);

   private int major = 1;

   private int minor = 0;

   private int revision = 0;

   private int build = 0;

   private String release = "";

   private String appName = "Fujaba Tool Suite";

   private int appMajor = 0;

   private int appMinor = 0;

   private int appBuild = 0;

   private String appRelease = "";

   private String appIcon = "images/Fujaba.gif";

   private String appSplash = "de/uni_paderborn/fujaba/app/images/Splashscreen.gif";

   private static volatile Version instance;


   /**
    * Constructor for class Version
    */
   private Version()
   {
      try
      {
         // parse the Version.xml document and set the major,
         // minor and build number and release string
         VersionParser parser = new VersionParser();
         parser.parse("version.xml");
      }
      catch (Exception ex)
      {
         log.error("Could not parse version.xml.");
         ex.printStackTrace();
      }

   }


   public static Version get()
   {
      if (instance == null)
      {
         instance = new Version();
      }
      return instance;
   }


   /**
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      if (isAppPresent())
      {
         return getAppVersion() + " " + appRelease + " Build " + appBuild
               + " (based on Fujaba " + getVersion() + ")";
      }
      else
      {
         return getVersion() + " " + release + "  Build " + build;
      }
   }


   public boolean isAppPresent()
   {
      return (appMajor + appMinor) > 0;
   }


   /**
    * Get the application version.
    * <p>
    * The application version is returned in the following format: "appMajor.appMinor"
    * 
    * @return The application version.
    */
   public String getAppVersion()
   {
      return appMajor + "." + appMinor;
   }


   /**
    * Get the version.
    * <p>
    * The version is returned in the following format: "major.minor.revision"
    * 
    * @return The version.
    */
   public String getVersion()
   {
      return major + "." + minor + "." + revision;
   }


   /**
    * @return the build version number
    */
   public int getBuild()
   {
      return build;
   }


   void setBuild(int build)
   {
      this.build = build;
   }


   /**
    * @return the major version number
    */
   public int getMajor()
   {
      return major;
   }


   void setMajor(int major)
   {
      this.major = major;
   }


   /**
    * @return the minor version number
    */
   public int getMinor()
   {
      return minor;
   }


   void setMinor(int minor)
   {
      this.minor = minor;
   }


   /**
    * @return the revision number
    */
   public int getRevision()
   {
      return revision;
   }


   void setRevision(int revision)
   {
      this.revision = revision;
   }


   /**
    * @return the release string
    */
   public String getRelease()
   {
      return release;
   }


   void setRelease(String release)
   {
      this.release = release;
   }


   /**
    * @return the application string
    */
   public String getAppName()
   {
      return appName;
   }


   void setAppName(String appName)
   {
      this.appName = appName;
   }


   /**
    * @return the appMajor int
    */
   public int getAppMajor()
   {
      return appMajor;
   }


   void setAppMajor(int appMajor)
   {
      this.appMajor = appMajor;
   }


   /**
    * @return the appMinor int
    */
   public int getAppMinor()
   {
      return appMinor;
   }


   void setAppMinor(int appMinor)
   {
      this.appMinor = appMinor;
   }


   /**
    * @return the application build version number
    */
   public int getAppBuild()
   {
      return appBuild;
   }


   void setAppBuild(int appBuild)
   {
      this.appBuild = appBuild;
   }


   /**
    * @return the application release version number
    */
   public String getAppRelease()
   {
      return appRelease;
   }


   void setAppRelease(String appRelease)
   {
      this.appRelease = appRelease;
   }


   /**
    * @return the application icon string
    */
   public String getAppIcon()
   {
      return appIcon;
   }


   void setAppIcon(String appIcon)
   {
      this.appIcon = appIcon;
   }


   /**
    * @return the application splash string
    */
   public String getAppSplash()
   {
      return appSplash;
   }


   void setAppSplash(String appSplash)
   {
      this.appSplash = appSplash;
   }


   /**
    * Class to parse the Version.xml document.
    * 
    * @author $Author$
    * @version $Revision$
    */
   class VersionParser extends CharacterHandlingSaxHandler
   {
      /**
       * URL used in Plugin definitions (XML-files) to identify the DTD. DTD given as "SYSTEM"
       * (non-public) definition.
       */
      private final static String VERSION_SYSTEM_ID = "http://www.upb.de/cs/fujaba/DTDs/Version.dtd";

      /**
       * Alternative URL used in Plugin definitions (XML-files) to identify the DTD. DTD given as
       * "SYSTEM" (non-public) definition.
       */
      private final static String VERSION_SYSTEM_ID2 = "http://www.fujaba.de/DTDs/Version.dtd";

      /**
       * Name used in Plugin definitions (XML-files) to identify the DTD. DTD given as "PUBLIC"
       * definition.
       */
      private final static String VERSION_PUBLIC_ID = "-//Fujaba//Fujaba Plugin Definition//EN//1.0";

      private final static String VERSION_DTD_RESOURCE = "DTDs/Version.dtd";

      public final static String VERSION = "Version";

      public final static String MAJOR = "Major";

      public final static String MINOR = "Minor";

      public final static String REVISION = "Revision";

      public final static String BUILD = "Build";

      public final static String RELEASE = "Release";

      public final static String APP_NAME = "AppName";

      public final static String APP_MAJOR = "AppMajor";

      public final static String APP_MINOR = "AppMinor";

      public final static String APP_BUILD = "AppBuild";

      public final static String APP_RELEASE = "AppRelease";

      public final static String APP_ICON = "AppIcon";

      public final static String APP_SPLASH = "AppSplash";

      private final static int VERSION_STATE = 0;

      private final static int MAJOR_STATE = 1;

      private final static int MINOR_STATE = 2;

      private final static int REVISION_STATE = 3;

      private final static int BUILD_STATE = 4;

      private final static int RELEASE_STATE = 5;

      private final static int APP_NAME_STATE = 6;

      private final static int APP_MAJOR_STATE = 7;

      private final static int APP_MINOR_STATE = 8;

      private final static int APP_BUILD_STATE = 9;

      private final static int APP_RELEASE_STATE = 10;

      private final static int APP_ICON_STATE = 11;

      private final static int APP_SPLASH_STATE = 12;

      private URL versionDTD;

      private int state = -1;


      public void parse(String xmlFile)
      {
         try
         {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setValidating(true);

            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            xmlReader.setContentHandler(this);
            xmlReader.setErrorHandler(this);
            xmlReader.setEntityResolver(this);
            xmlReader
                  .setFeature(
                        "http://apache.org/xml/features/nonvalidating/load-external-dtd",
                        false);


            URL url = getClass().getClassLoader().getResource(xmlFile);
            xmlReader.parse(new InputSource(url.toString()));
         }
         catch (SAXParseException e)
         {
            log.error("Parse error in line " + e.getLineNumber() + "\n"
                  + e.getMessage());
            e.printStackTrace();
         }
         catch (SAXException e)
         {
            log.error("SAXException:\n" + e.getMessage());
            e.printStackTrace();
         }
         catch (ParserConfigurationException e)
         {
            log.error("ParserConfigurationException:\n" + e.getMessage());
            e.printStackTrace();
         }
         catch (IOException e)
         {
            log.error("File " + xmlFile + " can not be opened:\n"
                  + e.getMessage());
            e.printStackTrace();
         }

      }


      /**
       * @see org.xml.sax.helpers.DefaultHandler#resolveEntity(java.lang.String, java.lang.String)
       */
      @Override
      public InputSource resolveEntity(String publicId, String systemId)
            throws SAXException
      {
         if (VERSION_PUBLIC_ID.equals(publicId)
               || VERSION_SYSTEM_ID.equals(systemId)
               || VERSION_SYSTEM_ID2.equals(systemId))
         {
            if (versionDTD == null)
            {
               versionDTD = getClass().getClassLoader().getResource(
                     VERSION_DTD_RESOURCE);
            }
            if (versionDTD != null)
            {
               try
               {
                  return new InputSource(versionDTD.openStream());
               }
               catch (Exception e)
               {
                  System.out
                        .println("Could not solve SYSTEM or PUBLIC reference for DTD.");
                  throw new SAXException(e);
               }
            }
         }
         InputSource result = null;
         try
         {
            result = super.resolveEntity(publicId, systemId);
         }
         catch (Exception ex)
         {
         }
         return result;
      } // resolveEntity


      /**
       * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String,
       *      java.lang.String, org.xml.sax.Attributes)
       */
      @Override
      public void startElement(String uri, String name, String qName,
            Attributes atts)
      {
         resetCharacters();
         try
         {

            if (qName.equals(VERSION))
            {
               state = VERSION_STATE;
            }
            else if (qName.equals(MAJOR))
            {
               state = MAJOR_STATE;
            }
            else if (qName.equals(MINOR))
            {
               state = MINOR_STATE;
            }
            else if (qName.equals(REVISION))
            {
               state = REVISION_STATE;
            }
            else if (qName.equals(BUILD))
            {
               state = BUILD_STATE;
            }
            else if (qName.equals(RELEASE))
            {
               state = RELEASE_STATE;
            }
            else if (qName.equals(APP_NAME))
            {
               state = APP_NAME_STATE;
            }
            else if (qName.equals(APP_MAJOR))
            {
               state = APP_MAJOR_STATE;
            }
            else if (qName.equals(APP_MINOR))
            {
               state = APP_MINOR_STATE;
            }
            else if (qName.equals(APP_BUILD))
            {
               state = APP_BUILD_STATE;
            }
            else if (qName.equals(APP_RELEASE))
            {
               state = APP_RELEASE_STATE;
            }
            else if (qName.equals(APP_ICON))
            {
               state = APP_ICON_STATE;
            }
            else if (qName.equals(APP_SPLASH))
            {
               state = APP_SPLASH_STATE;
            }
         }
         catch (Exception ex)
         {
            ex.printStackTrace();
         }
      }


      @Override
      protected void characters(CharSequence characters)
      {
         if (characters.length() == 0)
         {
            return;
         }
         try
         {
            String data = characters.toString();

            switch (state)
            {
               case VERSION_STATE:
                  // do nothing
                  break;
               case MAJOR_STATE:
                  setMajor(Integer.parseInt(data));
                  break;
               case MINOR_STATE:
                  setMinor(Integer.parseInt(data));
                  break;
               case REVISION_STATE:
                  setRevision(Integer.parseInt(data));
                  break;
               case BUILD_STATE:
                  setBuild(Integer.parseInt(data));
                  break;
               case RELEASE_STATE:
                  setRelease(data);
                  break;
               case APP_NAME_STATE:
                  setAppName(data);
                  break;
               case APP_MAJOR_STATE:
                  setAppMajor(Integer.parseInt(data));
                  break;
               case APP_MINOR_STATE:
                  setAppMinor(Integer.parseInt(data));
                  break;
               case APP_BUILD_STATE:
                  setAppBuild(Integer.parseInt(data));
                  break;
               case APP_RELEASE_STATE:
                  setAppRelease(data);
                  break;
               case APP_ICON_STATE:
                  setAppIcon(data);
                  break;
               case APP_SPLASH_STATE:
                  setAppSplash(data);
                  break;
               default:
                  break;
            }
         }
         catch (Exception ex)
         {
            ex.printStackTrace();
         }
      }


      /**
       * @throws SAXException
       * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String,
       *      java.lang.String)
       */
      @Override
      public void endElement(String uri, String name, String qName)
            throws SAXException
      {
         super.endElement(uri, name, qName);
         state = -1;
      }

   }

}
