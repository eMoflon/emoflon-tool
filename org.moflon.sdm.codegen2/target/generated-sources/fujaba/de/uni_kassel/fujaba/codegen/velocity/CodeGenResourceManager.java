/*
 * generated by Fujaba - CodeGen2
 */
package de.uni_kassel.fujaba.codegen.velocity;

import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.ResourceManagerImpl;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

import de.uni_kassel.features.ReferenceHandler; // requires Fujaba5/libs/features.jar in classpath
import de.uni_kassel.features.annotation.util.Property; // requires Fujaba5/libs/features.jar in classpath
import de.uni_kassel.fujaba.codegen.engine.TemplateLoader;
import de.upb.tools.fca.FEmptyIterator;
import de.upb.tools.fca.FHashMap; // requires Fujaba5/libs/RuntimeTools.jar in classpath
import de.upb.tools.sdm.JavaSDM; // requires Fujaba5/libs/RuntimeTools.jar in classpath
import de.upb.tools.sdm.JavaSDMException;


public class CodeGenResourceManager extends ResourceManagerImpl
{


   public static final String PROPERTY_ADDITIONA_L_RESOURC_E_LOADER = "ADDITIONAL_RESOURCE_LOADER";

   @Property( name = PROPERTY_ADDITIONA_L_RESOURC_E_LOADER, kind = ReferenceHandler.ReferenceKind.ATTRIBUTE )
   public static final String ADDITIONAL_RESOURCE_LOADER = "additional." + RuntimeConstants.RESOURCE_LOADER;


   private String getResolvedName (String name ) throws ResourceNotFoundException
   {
      boolean fujaba__Success = false;
      String location = null;
      URLResourceLoader loader = null;

      // story pattern storypatternwiththis
      try 
      {
         fujaba__Success = false; 

         // search to-one link resolved names from this to location
         location = this.getFromString (name);

         // check object location is really bound
         JavaSDM.ensure ( location != null );


         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      if ( fujaba__Success )
      {
         return location;

      }
      // story pattern storypatternwiththis
      try 
      {
         fujaba__Success = false; 

         // search to-one link resource loader from this to loader
         loader = this.getURLResourceLoader ();

         // check object loader is really bound
         JavaSDM.ensure ( loader != null );


         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      // story pattern successor
      try 
      {
         fujaba__Success = false; 

         location = loader.getResolvedName( name );

         // check object location is really bound
         JavaSDM.ensure ( location != null );
         // create link resolved names from this to location
         this.addToString (name, location);

         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      return location;
   }

   public Resource getResource (String resourceName , int resourceType , String encoding ) throws ParseErrorException, ResourceNotFoundException, Exception
   {
      boolean fujaba__Success = false;
      String resolvedName = null;
      Resource resource = null;
      ResourceLoader resourceLoader = null;
      String s = null;

      // story pattern 
      try 
      {
         fujaba__Success = false; 

         // collabStat call
         resolvedName = getResolvedName( resourceName );
         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      // story pattern 
      try 
      {
         fujaba__Success = false; 

         resource = globalCache.get( resolvedName );

         // check object resource is really bound
         JavaSDM.ensure ( resource != null );
         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      if ( !( fujaba__Success ) )
      {
         // children
         // story pattern 
         try 
         {
            fujaba__Success = false; 

            resource = loadResource( resolvedName, resourceType, encoding );

            // check object resource is really bound
            JavaSDM.ensure ( resource != null );
            // check object globalCache is really bound
            JavaSDM.ensure ( globalCache != null );
            // search to-one link resourceLoader from resource to resourceLoader
            resourceLoader = resource.getResourceLoader ();

            // check object resourceLoader is really bound
            JavaSDM.ensure ( resourceLoader != null );

            // attribute condition cachingOn == true
            JavaSDM.ensure ( resourceLoader.isCachingOn () == true );


            // collabStat call
            globalCache.put( resolvedName, resource );
            fujaba__Success = true;
         }
         catch ( JavaSDMException fujaba__InternalException )
         {
            fujaba__Success = false;
         }


         // exception
         catch( ResourceNotFoundException e1 )
         {
            // story pattern 
            try 
            {
               fujaba__Success = false; 

               // check object rsvc is really bound
               JavaSDM.ensure ( rsvc != null );
               // collabStat call
               rsvc.error( "ResourceManager : unable to find resource '" + resourceName + "' in any resource loader.");;
               fujaba__Success = true;
            }
            catch ( JavaSDMException fujaba__InternalException )
            {
               fujaba__Success = false;
            }

            throw e1;

         }
         catch( ParseErrorException pee  )
         {
            // story pattern 
            try 
            {
               fujaba__Success = false; 

               // check object rsvc is really bound
               JavaSDM.ensure ( rsvc != null );
               // collabStat call
               rsvc.error( "ResourceManager.getResource() exception: " + pee);;
               fujaba__Success = true;
            }
            catch ( JavaSDMException fujaba__InternalException )
            {
               fujaba__Success = false;
            }

            throw pee;

         }
         catch( Exception eee )
         {
            // story pattern 
            try 
            {
               fujaba__Success = false; 

               // check object rsvc is really bound
               JavaSDM.ensure ( rsvc != null );
               // collabStat call
               rsvc.error( "ResourceManager.getResource() exception: " + eee);;
               fujaba__Success = true;
            }
            catch ( JavaSDMException fujaba__InternalException )
            {
               fujaba__Success = false;
            }

            throw eee;

         }

         return resource;

      }
      // children
      // story pattern 
      try 
      {
         fujaba__Success = false; 

         // collabStat call
         this.refreshResource( resource, encoding );
         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }


      // exception
      catch( ResourceNotFoundException e1 )
      {
         // story pattern 
         try 
         {
            fujaba__Success = false; 

            // check object globalCache is really bound
            JavaSDM.ensure ( globalCache != null );
            // search to-one link resolved names from this to s
            s = this.getFromString (resolvedName);

            // check object s is really bound
            JavaSDM.ensure ( s != null );


            // destroy link resolved names from this to s
            this.removeFromString (resolvedName, s);
            // collabStat call
            globalCache.remove( resolvedName );
            fujaba__Success = true;
         }
         catch ( JavaSDMException fujaba__InternalException )
         {
            fujaba__Success = false;
         }

         return getResource( resourceName, resourceType, encoding );

      }
      catch( Exception ex )
      {
         // story pattern 
         try 
         {
            fujaba__Success = false; 

            // check object rsvc is really bound
            JavaSDM.ensure ( rsvc != null );
            // collabStat call
            rsvc.error( "ResourceManager.getResource() exception: " + ex);;
            fujaba__Success = true;
         }
         catch ( JavaSDMException fujaba__InternalException )
         {
            fujaba__Success = false;
         }

         throw ex;

      }

      return resource;
   }

   public void initialize (RuntimeServices runtimeServices ) throws Exception
   {
      boolean fujaba__Success = false;
      Vector additionalLoaderNames = null;
      int i = 0;
      String loaderID = null;
      ExtendedProperties loaderConfiguration = null;
      String loaderClass = null;
      ResourceLoader otherLoader = null;
      Object _TmpObject = null;
      TemplateLoader loader = null;
      URLResourceLoader resourceLoader = null;

      // story pattern 
      try 
      {
         fujaba__Success = false; 

         // collabStat call
         super.initialize( runtimeServices );
         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      // story pattern successor
      try 
      {
         fujaba__Success = false; 

         additionalLoaderNames = rsvc.getConfiguration().getVector(ADDITIONAL_RESOURCE_LOADER);;

         // check object additionalLoaderNames is really bound
         JavaSDM.ensure ( additionalLoaderNames != null );
         // collabStat call
         i = -1;
         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      if ( additionalLoaderNames.size() > 0 )
      {
         do
         {
            // story pattern successor
            try 
            {
               fujaba__Success = false; 

               // collabStat call
               i = i + 1;
               // collabStat call
               loaderID = additionalLoaderNames.get(i) + "." + RuntimeConstants.RESOURCE_LOADER;
               fujaba__Success = true;
            }
            catch ( JavaSDMException fujaba__InternalException )
            {
               fujaba__Success = false;
            }

            // story pattern successor
            try 
            {
               fujaba__Success = false; 

               loaderConfiguration = rsvc.getConfiguration().subset(loaderID);

               // check object loaderConfiguration is really bound
               JavaSDM.ensure ( loaderConfiguration != null );
               fujaba__Success = true;
            }
            catch ( JavaSDMException fujaba__InternalException )
            {
               fujaba__Success = false;
            }

            if ( fujaba__Success )
            {
               // story pattern successor
               try 
               {
                  fujaba__Success = false; 

                  // check object loaderConfiguration is really bound
                  JavaSDM.ensure ( loaderConfiguration != null );
                  // collabStat call
                  loaderConfiguration.setProperty("_RESOURCE_LOADER_IDENTIFIER_", additionalLoaderNames.get(i));
                  // collabStat call
                  loaderClass = loaderConfiguration.getString( "class" );
                  fujaba__Success = true;
               }
               catch ( JavaSDMException fujaba__InternalException )
               {
                  fujaba__Success = false;
               }

               if ( loaderClass == null )
               {
                  // story pattern successor
                  try 
                  {
                     fujaba__Success = false; 

                     // check object rsvc is really bound
                     JavaSDM.ensure ( rsvc != null );
                     // collabStat call
                     rsvc.error( "Unable to find '" + loaderConfiguration.getString( "_RESOURCE_LOADER_IDENTIFIER_" ) + ".resource.loader.class' specification in configuration. This is a critical value. Please adjust configuration.");
                     fujaba__Success = true;
                  }
                  catch ( JavaSDMException fujaba__InternalException )
                  {
                     fujaba__Success = false;
                  }


               }
               else
               {
                  // story pattern successor
                  try 
                  {
                     fujaba__Success = false; 

                     otherLoader = AdaptableResourceLoaderFactory.getLoader(rsvc, loaderClass);;

                     // check object otherLoader is really bound
                     JavaSDM.ensure ( otherLoader != null );
                     // check object resourceLoaders is really bound
                     JavaSDM.ensure ( resourceLoaders != null );
                     // collabStat call
                     otherLoader.commonInit(rsvc, loaderConfiguration);;
                     // collabStat call
                     otherLoader.init(loaderConfiguration);;
                     // collabStat call
                     resourceLoaders.add( otherLoader );
                     fujaba__Success = true;
                  }
                  catch ( JavaSDMException fujaba__InternalException )
                  {
                     fujaba__Success = false;
                  }


               }

            }
            else
            {
               // story pattern 
               try 
               {
                  fujaba__Success = false; 

                  // check object rsvc is really bound
                  JavaSDM.ensure ( rsvc != null );
                  // collabStat call
                  rsvc.warn( "ResourceManager : No configuration information for resource loader named '" + additionalLoaderNames.get(i) + "'. Skipping.");
                  fujaba__Success = true;
               }
               catch ( JavaSDMException fujaba__InternalException )
               {
                  fujaba__Success = false;
               }


            }

         }
         while ( additionalLoaderNames.size() > i + 1 );
      }
      // story pattern storypatternwiththis
      try 
      {
         fujaba__Success = false; 

         _TmpObject = runtimeServices.getProperty("resource.manager." + TemplateLoader.PROPERTY_KEY);

         // ensure correct type and really bound of object loader
         JavaSDM.ensure ( _TmpObject instanceof TemplateLoader );
         loader = (TemplateLoader) _TmpObject;

         // search to-one link loader from loader to resourceLoader
         resourceLoader = loader.getLoader ();

         // check object resourceLoader is really bound
         JavaSDM.ensure ( resourceLoader != null );


         // create link resource loader from this to resourceLoader
         this.setURLResourceLoader (resourceLoader);

         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      return ;
   }

   /**
    * <pre>
    *           0..1     resolved names     0..1
    * CodeGenResourceManager ------------------------> String
    *           codeGenResourceManager               string
    * </pre>
    */
   public static final String PROPERTY_STRING = "string";

   @Property( name = PROPERTY_STRING, kind = ReferenceHandler.ReferenceKind.QUALIFIED_TO_ONE,
         adornment = ReferenceHandler.Adornment.NONE)
   private FHashMap<String, String> string;

   @Property( name = PROPERTY_STRING )

   public boolean removeFromString (String value)
   {
      boolean changed = false;

      if (this.string != null)
      {
         Iterator iter = this.entriesOfString ();
         Map.Entry entry;
         while (iter.hasNext ())
         {
            entry = (Map.Entry) iter.next ();
            if (entry.getValue () == value)
            {
            
               if (this.removeFromString ((String) entry.getKey (), value))
               {
                  changed = true;
               }
            
            }
         }
      }
      return changed;
   }

   @Property( name = PROPERTY_STRING )
   public void removeAllFromString (){
      if (this.string != null && this.string.size () > 0)
      {
      
         this.string.clear();
      
      }
   }

   @Property( name = PROPERTY_STRING )
   public boolean hasInString (String value)
   {
      return ((this.string != null) &&
              this.string.containsValue (value));
   }

   @Property( name = PROPERTY_STRING )
   public Iterator<? extends String> iteratorOfString ()
   {
      return ((this.string == null)
              ? FEmptyIterator.<String>get ()
              : this.string.values ().iterator ());
   }

   @Property( name = PROPERTY_STRING )
   public int sizeOfString ()
   {
      return ((this.string == null)
              ? 0
              : this.string.size ());
   }

   @Property( name = PROPERTY_STRING )
   public boolean hasInString (String key, String value)
   {
      return ((this.string != null) &&
              (value != null || this.string.containsKey (key)) && 
              (this.string.get (key) == value));
   }

   @Property( name = PROPERTY_STRING )
   public boolean hasKeyInString (String key)
   {
      return ((this.string != null) &&
              this.string.containsKey (key));
   }

   @Property( name = PROPERTY_STRING )
   public Iterator<String> keysOfString ()
   {
      return ((this.string == null)
              ? FEmptyIterator.<String>get ()
              : this.string.keySet ().iterator ());
   }

   @Property( name = PROPERTY_STRING )
   public Iterator entriesOfString ()
   {
      return ((this.string == null)
              ? FEmptyIterator.get ()
              : this.string.entrySet ().iterator ());
   }

   @Property( name = PROPERTY_STRING )
   public boolean addToString (String key, String value)
   {
      boolean changed = false;

      if (this.string == null)
      {
         this.string = new FHashMap<String, String> ();
      }
   
      String oldValue = (String) this.string.put (key, value);
      if (oldValue != value)
      {
         changed = true;
      }
   
      return changed;
   }

   @Property( name = PROPERTY_STRING )
   public boolean addToString (Map.Entry entry)
   {
      return addToString ((String) entry.getKey (), (String) entry.getValue ());
   }

   @Property( name = PROPERTY_STRING )
   public boolean removeFromString (String key, String value)
   {
      boolean changed = false;

      if (this.string != null)
      {
         String oldValue = (String) this.string.get (key);
         if (oldValue == value && 
             (oldValue != null || this.string.containsKey (key)))
         {
         
            this.string.remove (key);
            changed = true;
         
         }
      }
      return changed;
   }

   @Property( name = PROPERTY_STRING )
   public boolean removeKeyFromString (String key)
   {
      boolean changed = false;

      if (this.string != null)
      {
         changed = this.string.containsKey (key);
         if (changed)
         {
         
            String tmpValue = (String) this.string.remove (key);
         
         }
      }
      return changed;
   }
   @Property( name = PROPERTY_STRING )
   public String getFromString (String key)
   {
      return ((this.string == null)
              ? null
              : (String) this.string.get (key));
   }

   /**
    * <pre>
    *           0..1     resource loader     0..1
    * CodeGenResourceManager ------------------------> URLResourceLoader
    *           codeGenResourceCache               uRLResourceLoader
    * </pre>
    */
   public static final String PROPERTY_URL_RESOURCE_LOADER = "uRLResourceLoader";

   @Property( name = PROPERTY_URL_RESOURCE_LOADER, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         adornment = ReferenceHandler.Adornment.NONE)
   private URLResourceLoader uRLResourceLoader;

   @Property( name = PROPERTY_URL_RESOURCE_LOADER )
   public boolean setURLResourceLoader (URLResourceLoader value)
   {
      boolean changed = false;

      if (this.uRLResourceLoader != value)
      {
      
         URLResourceLoader oldValue = this.uRLResourceLoader;
         this.uRLResourceLoader = value;
         changed = true;
      
      }
      return changed;
   }

   @Property( name = PROPERTY_URL_RESOURCE_LOADER )
   public CodeGenResourceManager withURLResourceLoader (URLResourceLoader value)
   {
      setURLResourceLoader (value);
      return this;
   }

   public URLResourceLoader getURLResourceLoader ()
   {
      return this.uRLResourceLoader;
   }

   public void removeYou()
   {
      this.removeAllFromString ();
      this.setURLResourceLoader (null);
   }
}


