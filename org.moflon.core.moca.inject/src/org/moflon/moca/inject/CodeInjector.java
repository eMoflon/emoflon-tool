package org.moflon.moca.inject;

import java.util.List;

/**
 * This class injects the members code and user imports into existing java code.
 *
 */
public interface CodeInjector
{

   /**
    * Injects a members block into a file.
    * 
    * @param relativePath
    *           The relative path to the file, starting from project root. <br>
    *           Example: "/gen/org/moflon/ide/core/CoreAdvisor.java"
    * @param code
    *           Members code to inject.
    */
   public abstract void injectMembersCode(String relativePath, String code);

   /**
    * Injects an imports block into a file.
    * 
    * @param relativePath
    *           The relative path to the file, starting from project root. <br>
    *           Example: "/gen/org/moflon/ide/core/CoreAdvisor.java"
    * @param imports
    *           The imports to inject.
    */
   public abstract void injectImports(String relativePath, List<String> imports);

}