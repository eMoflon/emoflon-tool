package org.moflon.moca.inject;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EOperation;
import org.moflon.moca.inject.extractors.InjectionExtractor;
import org.moflon.moca.inject.util.UnsupportedOperationCodeInjector;
import org.moflon.moca.inject.validation.InjectionValidationMessage;

/**
 * This class manages the extraction of injection code.
 * 
 * Definition of terms:
 * 
 * - The **model code** for an operation is the method body of that operation.
 * 
 * - The **members code** for a class is a piece of arbitrary code.
 * 
 * - The **imports code** is a sequence of qualified or wildcard import expresions.
 */
public class InjectionManager
{
   private final InjectionExtractor userInjectionExtractor;

   private final InjectionExtractor compilerInjectionExtractor;

   private CodeInjector codeInjector;

   /**
    * Produces an injection coordination instance that uses the given injection extractor to answer queries for
    * injections.
    * 
    * This class is not able to inject code directly. Any attempt will cause an Exception.
    * 
    * @param userInjectionExtractor
    * @param compilerInjectionExtractor
    */
   public InjectionManager(final InjectionExtractor userInjectionExtractor, final InjectionExtractor compilerInjectionExtractor)
   {
      this(userInjectionExtractor, compilerInjectionExtractor, new UnsupportedOperationCodeInjector());
   }

   /**
    * Produces an injection coordination instance that uses the given injection extractor to answer queries for
    * injections and the given code injector to inject members and imports.
    * 
    * @param injectionExtractor
    * @param compilerInjectionExtractor
    * @throws CoreException
    */
   public InjectionManager(final InjectionExtractor injectionExtractor, final InjectionExtractor compilerInjectionExtractor, final CodeInjector codeInjector)
   {
      this.userInjectionExtractor = injectionExtractor;
      this.compilerInjectionExtractor = compilerInjectionExtractor;
      this.codeInjector = codeInjector;
   }

   public void setCodeInjector(final CodeInjector codeInjector)
   {
      this.codeInjector = codeInjector;
   }

   /**
    * Returns whether this component holds model code for the given operation.
    */
   public boolean hasModelCode(final EOperation eOperation)
   {
      return userInjectionExtractor.hasModelCode(eOperation) || compilerInjectionExtractor.hasModelCode(eOperation);
   }

   /**
    * Get the model code for given method.
    * 
    * @param eOperation
    *           The EOperation that identifies the method
    * @return Code for the method
    */
   public String getModelCode(final EOperation eOperation)
   {
      if (compilerInjectionExtractor.hasModelCode(eOperation) && !userInjectionExtractor.hasModelCode(eOperation))
         return compilerInjectionExtractor.getModelCode(eOperation);

      return userInjectionExtractor.getModelCode(eOperation);
   }

   public void injectMembersAndImports()
   {
      injectImports();
      injectMembers();
   }

   public String getMembersCodeByClassName(final String className)
   {

      return this.userInjectionExtractor.getMembersCodeByClassName(className);
   }

   /**
    * Retrieves the members code for given file.
    * 
    * This path is relative to the project and uses java.io.File.separatorChar. Example:
    * "\gen\DoubleLinkedList\DoubleLinkedList.java"
    * 
    * @param path
    *           Path to the file, that will receive the members code.
    * @return Members code
    */
   public String getMembersCode(final String path)
   {
      return userInjectionExtractor.getMembersCode(path);
   }

   /**
    * Retrieves the imports for given file.
    * 
    * This path is relative to the project and uses java.io.File.separatorChar. Example:
    * "\gen\DoubleLinkedList\DoubleLinkedList.java"
    * 
    * @param path
    *           Path to the file, that will receive the members code.
    * @return Imports for the file. This are just the raw imports without the keyword 'import' or semicolons.
    */
   public List<String> getImports(final String path)
   {
      List<String> result = new ArrayList<String>();
      result.addAll(userInjectionExtractor.getImports(path));
      result.addAll(compilerInjectionExtractor.getImports(path));
      return result;
   }

   /**
    * Returns the list of all imports of all processed injection files.
    */
   public List<String> getAllImports()
   {
      return this.userInjectionExtractor.getAllImports();
   }

   /**
    * Injects the imports
    */
   public void injectImports()
   {
      for (final String relativePath : userInjectionExtractor.getImportsPaths())
      {
         final List<String> imports = userInjectionExtractor.getImports(relativePath);
         codeInjector.injectImports(relativePath, imports);
      }

   }

   private void injectMembers()
   {
      for (final String relativePath : userInjectionExtractor.getMembersPaths())
      {
         final String membersCode = userInjectionExtractor.getMembersCode(relativePath);
         codeInjector.injectMembersCode(relativePath, membersCode);
      }
   }

   /**
    * Triggers {@link InjectionExtractor#extractInjections()} on all its injection extractors
    * 
    * @return
    */
   public IStatus extractInjections()
   {
      this.userInjectionExtractor.extractInjections();
      this.compilerInjectionExtractor.extractInjections();

      List<InjectionValidationMessage> errors = this.userInjectionExtractor.getErrors();
      if (errors.size() > 0)
      {
         final MultiStatus validationStatus = new MultiStatus(CodeInjectionPlugin.getModuleID(), 0, "Extraction of injections with warnings/errors.", null);
         for (final InjectionValidationMessage error : errors)
         {
            validationStatus.add(error.convertToStatus());
         }

         return validationStatus;
      } else
         return new Status(IStatus.OK, CodeInjectionPlugin.getModuleID(), "Extraction of injections successful.");
   }
}
