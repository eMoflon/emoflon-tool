package org.moflon.validation.info;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tobias Raffel
 * @version 1.0 30.3.2013
 * 
 * @deprecated This class does not seem to be used anymore
 */
//TODO: remove
@Deprecated
public class Violation
{
   public static final String MESSAGE_PATTERN = ".+\\|.+\\|.+";

   private String resource = null;

   private String location = null;

   private String message = null;

   /**
    * Creates a new violation instance, depending on information of an already created violation object.
    */
   protected Violation(final String resource, final String location, final String message)
   {
      this.resource = resource;
      this.location = location;
      this.message = message;
   }
   
   /**
    * Creates a new violation instance.
    */
   public Violation(final Object resource, final String location, final String message)
   {
      this.resource = resource.getClass().getName().replace('.', '/');
      this.location = location;
      this.message = message;
   }

   public String getResource()
   {
      return resource;
   }

   public String getLocation()
   {
      return location;
   }

   public String getMessage()
   {
      return message;
   }
   
   /**
    * Transforms a all messages within 'formatedStrings' into Violation-objects.
    * @param formatedStrings A list of strings matching the pattern: Violation.MESSAGE_PATTERN.
    * @return An Array of Violation-objects.
    * @throws UnknownFormatException
    */
   public static ArrayList<Violation> transform(final List<String> formatedStrings) throws UnknownFormatException
   {
      ArrayList<Violation> violations = new ArrayList<>();


      for (String formatedString : formatedStrings)
      {
         if (!formatedString.matches(Violation.MESSAGE_PATTERN))
         {
            throw new UnknownFormatException("Value does not match the pattern: \"" + Violation.MESSAGE_PATTERN + "\"");
         }

         String[] tmp = formatedString.split("\\|");

         violations.add(new Violation(tmp[0], tmp[1], tmp[2]));
      }
      return violations;
   }
   
   /**
    * Also specifies the storage-format of a violation.
    */
   @Override
   public String toString()
   {
      StringBuffer sb = new StringBuffer();
      sb.append("gen/").append(resource).append(".java").append("|");
      sb.append(location).append("|");
      sb.append(message);

      return sb.toString();
   }
}