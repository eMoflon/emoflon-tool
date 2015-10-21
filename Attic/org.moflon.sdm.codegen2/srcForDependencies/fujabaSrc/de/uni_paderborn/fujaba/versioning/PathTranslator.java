/**
 * 
 */
package de.uni_paderborn.fujaba.versioning;

import java.io.File;
import java.io.IOException;

/**
 * @author Oleg
 *
 */
public class PathTranslator
{
   private final static String UPWARD_DIR = "../";
   private final static String DIR_SEPARATOR = "/";
   
   
   /**
    * Translate the given relative, OS-dependent path
    * to an absolute, OS-dependent path, e.g.
    * for the purpose of loading a project.
    * 
    * @param base the base folder to which the relative path is assumed to be relative to
    * @param relativePath the path to be translated
    * @return the translated absolute path
    */
   public static String translateToAbsolutePath(String base, String relativePath)
   {
      File baseFile = new File(base);
      if(baseFile.isFile())
      {
         baseFile = baseFile.getParentFile();
      }
      
      File absoluteFile = new File(baseFile, relativePath);
      try
      {
         absoluteFile = absoluteFile.getCanonicalFile();
      }
      catch (IOException e)
      {
         e.printStackTrace();
         throw new RuntimeException(e);
      }
      return absoluteFile.toString();
   }


   /**
    * Translate the given absolute, OS-dependent
    * path to a relative, OS-dependent path, e.g.
    * for the purpose of saving a project.
    * 
    * @param base the base folder to which the returned path will be relative to
    * @param absolutePath the path to be translated
    * @return the translated path relative to the given base
    */
   public static String translateToRelativePath(String base, String absolutePath)
   {
      String a = null, b = null;
      File bFile = null;
      try
      {
         a = new File(base).getCanonicalFile().toURI().getPath();
         bFile = new File(absolutePath).getCanonicalFile();
         b = bFile.toURI().getPath();
      }
      catch (IOException e)
      {
         e.printStackTrace();
         throw new RuntimeException(e);
      }
      
      String[] basePaths = a.split(DIR_SEPARATOR);
      String[] otherPaths = b.split(DIR_SEPARATOR);
      int n = 0;
      for(; n < basePaths.length && n < otherPaths.length; n ++)
      {
         if( basePaths[n].equals(otherPaths[n]) == false )
            break;
      }

      StringBuffer tmp = new StringBuffer("");
      for(int m = n; m < basePaths.length; m ++)
         tmp.append(UPWARD_DIR);
      for(int m = n; m < otherPaths.length - 1; m ++)
      {
         tmp.append(otherPaths[m]);
         tmp.append(DIR_SEPARATOR);
      }
      tmp.append(otherPaths[otherPaths.length - 1]);
      if(bFile.isDirectory())
      {
         tmp.append(DIR_SEPARATOR);
      }

      return tmp.toString();
   }
}
