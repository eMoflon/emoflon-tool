package de.fujaba.preferences;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

public class MapConverter {
   
   private static final char ENTRIES_DELIM = ';';
   private static final char KEY_VALUE_DELIM = '=';
   private static final char ESCAPE_CHAR = '\\';

   /**
    * convert a preference value given as String into a Map 
    * @param value the String representation of a preference value 
    * @return a Map containing the key-value-pairs represented by 
    * the input value
    */
   public static Map<String, String> parseString(String value) {
      Map<String, String> result = new LinkedHashMap<String, String>();
      Vector<String> keyValuePairs = ConverterUtil.splitString(value, ENTRIES_DELIM, ESCAPE_CHAR, true);
      for (String keyValuePair : keyValuePairs) {
         Vector<String> keyValueVector = ConverterUtil.splitString(keyValuePair, KEY_VALUE_DELIM, ESCAPE_CHAR, false);
         assert (keyValueVector.size() == 2) : keyValuePair + " is not a valid key-value-pair";
         result.put(keyValueVector.elementAt(0), keyValueVector.elementAt(1));
      }
      return result;
   }
   
   /**
    * convert a map into a String to store it in a PreferenceStore
    * @param map the map to be stored; the values in this map must not be null
    * @return a String representation, which can be used as a property 
    * value and stored by a PreferenceStore
    */
   public static String toString(Map<String, String> map) {
      Vector<String> entries = new Vector<String>();
      for (Map.Entry<String, String> entry : map.entrySet()) {
         Vector<String> keyValueVector = new Vector<String>(2);
         keyValueVector.add(entry.getKey());
         keyValueVector.add(entry.getValue());
         entries.add(ConverterUtil.joinStrings(keyValueVector, KEY_VALUE_DELIM, ESCAPE_CHAR, false));
      }
      return ConverterUtil.joinStrings(entries, ENTRIES_DELIM, ESCAPE_CHAR, true);
   }
}
