package org.moflon.moca.inject.util;

import java.util.List;

import org.moflon.moca.inject.CodeInjector;

/**
 * Dummy code injector that will fail when any interface method is called
 */
public class UnsupportedOperationCodeInjector implements CodeInjector
{

   @Override
   public void injectMembersCode(String relativePath, String code)
   {
      fail();
   }


   @Override
   public void injectImports(String relativePath, List<String> imports)
   {
      fail();
   }
   
   private void fail()
   {
      throw new UnsupportedOperationException("This code injector serves as a placeholder and cannot inject code.");
   }
   
}
