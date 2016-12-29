package org.moflon.moca.inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EOperation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.moflon.moca.inject.extractors.InjectionExtractor;
import org.moflon.moca.inject.validation.InjectionValidationMessage;

/**
 * Unit tests for {@link InjectionManager}
 */
public class InjectionManagerTest
{

   private static final String CLASS_1 = "foo.bar.Baz";

   private static final String MEMBERS_CODE_FOR_CLASS_1 = "Members code for \\gen\\foo\\bar\\Baz.java";

   private static final List<String> IMPORTS_FOR_CLASS_1 = Arrays.asList("import.for.class.1.1", "import.for.class.1.2");

   private static final String PATH_TO_CLASS_1 = "\\gen\\foo\\bar\\Baz.java";

   @Rule
   public TemporaryFolder tempFolder = new TemporaryFolder();

   private InjectionExtractorDummy defaultInjectionExtractor;

   @Before
   public void setUp() throws Exception
   {
      defaultInjectionExtractor = new InjectionExtractorDummy();
   }

   @Test(expected = RuntimeException.class)
   public void testThatCodeInjectionFailsWithoutAGivenCodeInjector() throws Exception
   {
      final InjectionManager injectionManager = new InjectionManager(defaultInjectionExtractor, defaultInjectionExtractor);
      injectionManager.injectMembersAndImports();
   }

   @Test
   public void testThatDummyInjectionExtractorReturnsMemberProperly() throws Exception
   {
      final InjectionManager injection = new InjectionManager(defaultInjectionExtractor, defaultInjectionExtractor);

      Assert.assertEquals(MEMBERS_CODE_FOR_CLASS_1, injection.getMembersCode(PATH_TO_CLASS_1));
   }

   @Ignore("Class-to-path translation not implemented")
   @Test
   public void testThatClassNameToPathConversionWorksProperly() throws Exception
   {
      final InjectionManager injection = new InjectionManager(defaultInjectionExtractor, defaultInjectionExtractor);

      Assert.assertEquals(MEMBERS_CODE_FOR_CLASS_1, injection.getMembersCodeByClassName(CLASS_1));
   }

   private static class InjectionExtractorDummy implements InjectionExtractor
   {

      @Override
      public Collection<String> getImportsPaths()
      {
         return Arrays.asList(PATH_TO_CLASS_1);
      }

      @Override
      public List<String> getImports(final String path)
      {
         if (path.equals(PATH_TO_CLASS_1))
         {
            return IMPORTS_FOR_CLASS_1;
         } else
         {
            return null;
         }

      }

      @Override
      public String getMembersCode(final String path)
      {
         if (path.equals(PATH_TO_CLASS_1))
         {
            return MEMBERS_CODE_FOR_CLASS_1;
         } else
         {
            return null;
         }
      }

      @Override
      public List<String> getAllImports()
      {
         return null;
      }

      @Override
      public boolean hasModelCode(final EOperation eOperation)
      {
         return false;
      }

      @Override
      public String getModelCode(final EOperation eOperation)
      {
         return null;
      }

      @Override
      public Set<String> getMembersPaths()
      {
         return null;
      }

      @Override
      public String getMembersCodeByClassName(final String className)
      {
         return null;
      }

      @Override
      public void extractInjections() 
      {
      }

      @Override
      public List<InjectionValidationMessage> getErrors()
      {
         return new ArrayList<>();
      }

   }
}
