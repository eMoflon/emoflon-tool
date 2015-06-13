package de.fujaba.preferences;

import java.util.Collection;
import java.util.Vector;

/**
 * 
 * @author weisemoeller
 * This class provides two convenience methods used by the converter classes
 * to convert data structures into a format appropriate for the preference stores  
 */
public abstract class ConverterUtil {

   /**
    * Get substrings of a String separated by a delimiter. 
    * Occurences of the delimiter inside the substrings must 
    * be escaped in the input String. 
    * @param input The String to be split
    * @param delim The delimiter character
    * @param escape The escape character
    * @param closingDelim true iff the input contains a delimiter after the last entry
    * @return A Vector containing the substrings as described above
    */
   public static Vector<String> splitString(String input, char delim, char escape, boolean closingDelim) {
      Vector<String> result = new Vector<String>();
      char[] myChar = input.toCharArray();
      boolean escaped = false;
      StringBuffer nextString = new StringBuffer();
      for (int i = 0; i < myChar.length; i++) {
         char currentChar = myChar[i];
         if (escaped) {
            escaped = false;
            if (currentChar == delim || currentChar == escape) {
               nextString.append(currentChar);
            } else {
               throw new IllegalArgumentException(currentChar + "  must not be escaped");               
            }
         } else {
            if (currentChar == delim) {
               result.add(nextString.toString());
               nextString = new StringBuffer();
            } else if (currentChar == escape) {
               escaped = true;
            } else {
               nextString.append(currentChar);
            }
            
         }
      }
      if (!closingDelim) { 
         result.add(nextString.toString());
      }
      return result;
   }

   /**
    * Concatenate a Collection of Strings. The original Strings will be separated
    * by a delimiter char in the result String. Delimiters inside the substrings 
    * will be escaped.
    * @param input The Strings to be concatenated
    * @param delim The delimiter character
    * @param escape The escape character
    * @param closingDelim append a delimiter after the last element
    * @return A String containing the input Strings separated by the delimiter
    */
   public static String joinStrings(Collection<String> input, char delim, char escape , boolean closingDelim) {
      StringBuffer result = new StringBuffer();
      for (String currentString : input) {
         for (char currentChar : currentString.toCharArray()) {
            // if currentChar needs to be escaped, escape it
            if (currentChar == delim || currentChar == escape) {
               result.append(escape);
            }
            result.append(currentChar);
         }
         // end of current String
         result.append(delim);
      }
      if (!closingDelim) {
         // delete last delimiter
         result.setLength(result.length() - 1);
      }
      return result.toString();
   }

}
