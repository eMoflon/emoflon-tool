package org.moflon.csp.codegenerator;

import java.util.Locale;

import org.stringtemplate.v4.AttributeRenderer;



public class MyBasicFormatRenderer implements AttributeRenderer
{
   public String toString(Object o)
   {
      return o.toString();
   }

   public String toString(Object o, String formatName, Locale l)
   {
	  l = Locale.getDefault();
	  if ( formatName==null ) return o.toString();
      if (formatName.equals("firstToUpper"))
      {
         return firstToUpper(o.toString());
      } else if (formatName.equals("firstToLower"))
      {
         return firstToLower(o.toString());
      } else if (formatName.equals("toLower"))
      {
         return o.toString().toLowerCase();
      } else
      {
         throw new IllegalArgumentException("Unsupported format name");
      }
   }

   public static String firstToUpper(String s)
   {
      return s.substring(0, 1).toUpperCase() + s.substring(1);
   }

   public static String firstToLower(String s)
   {
      return s.substring(0, 1).toLowerCase() + s.substring(1);
   }



}