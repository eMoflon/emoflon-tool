package org.moflon.moca.inject.util;

import java.util.List;

import org.moflon.core.utilities.UtilityClassNotInstantiableException;

/**
 * This class contains the relevant constants and helper methods for producing the
 * so-called guarded regions for members code and user imports. 
 *
 */
public final class InjectionRegions
{
   private static final String IMPORT_KEYWORD = "import ";
   private static final String NL = "\n";
   public static final String MEMBERS_END = "// [user code injected with eMoflon] -->";
   public static final String MEMBERS_BEGIN = "// <-- [user code injected with eMoflon]";
   public static final String USER_IMPORTS_END = "// [user defined imports] -->";
   public static final String USER_IMPORTS_BEGIN = "// <-- [user defined imports]";
   
   private InjectionRegions() {
      throw new UtilityClassNotInstantiableException();
   }
   
   /**
    * Builds a members block that is ready to be injected. It gets surrounded by whitespace and the comments to mark the
    * block.
    */
   public static String buildMembersBlock(final String code)
   {
      final StringBuffer block = new StringBuffer();
      block.append("\t");
      block.append(MEMBERS_BEGIN);
      block.append("\n\t");
      block.append(code);
      block.append(NL);
      block.append("\t");
      block.append(MEMBERS_END);
      return block.toString();
   }
   
   /**
    * Builds an imports block that is ready to be injected. This block contains whitespace and the marks to identify the
    * block later on.
    */
   public static String buildImportsBlock(final List<String> qualifiedImports)
   {
      final StringBuffer block = new StringBuffer();
      block.append(NL);
      block.append(USER_IMPORTS_BEGIN);
      block.append(NL);
      for (final String importExpression : qualifiedImports)
      {
         block.append(IMPORT_KEYWORD);
         block.append(' ');
         block.append(importExpression);
         block.append(";");
         block.append(NL);
      }
      block.append(NL);
      block.append(USER_IMPORTS_END);
      return block.toString();
   }
   
}
