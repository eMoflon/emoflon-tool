package org.moflon.moca.inject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.moflon.compiler.sdm.MethodVisitor;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.moca.inject.util.InjectionRegions;

/**
 * This class is used to create injection files from existing code
 * 
 * @author Jerome Reinlaender
 * 
 */
public class InjectionFile
{
   private String members;

   private String imports;

   private final List<String> models;

   private final String className;

   private final String contentString;

   /**
    * Initializes the contents of a new .inject file, which can then be used via 'getFileContent()'
    * 
    * @param classFileContents
    *           Stream of the existing code.
    * @param className
    *           Name of the class
    */
   public InjectionFile(final InputStream classFileContents, final String className)
   {
      final Scanner scanner = new Scanner(classFileContents);
      scanner.useDelimiter("\\A");
      contentString = scanner.hasNext() ? scanner.next() : "";
      scanner.close();

      models = new ArrayList<String>();
      this.className = className;

      final MethodVisitor mVisit = new MethodVisitor(contentString);

      extractModelMethods(mVisit.getMethods());
      extractUserImports(contentString);
      extractUserMembers(contentString);
   }

   /**
    * Returns the content of the new .inject file
    */
   public String getFileContent()
   {
      final StringBuilder fileContent = new StringBuilder();
      fileContent.append(imports);
      fileContent.append("\n");
      fileContent.append("partial class ");
      fileContent.append(className);
      fileContent.append(" {\n\n");
      fileContent.append(members);
      fileContent.append("\n\n");
      for (final String model : models)
      {
         fileContent.append(model);
         fileContent.append("\n\n");
      }
      fileContent.append("}");
      return fileContent.toString();
   }

   public List<String> getModels()
   {
      return Collections.unmodifiableList(this.models);
   }
   
   public boolean hasModelsOrImportsOrMembersCode() {
      return !this.getModels().isEmpty() || !this.imports.isEmpty() || !this.members.isEmpty();
   }
   
   /**
    * Extracts the model methods from a list of MethodDeclarations and adds them to models
    * 
    * @param methods
    *           Methods of the model as MethodDeclarations
    */
   private void extractModelMethods(final List<MethodDeclaration> methods)
   {
      for (final MethodDeclaration method : methods)
      {
         final Block bodyBlock = method.getBody();
         if (bodyBlock != null)
         {
            final String body = getBlockBody(bodyBlock);
            if (isInjectedModel(body) && !isDefaultBody(body))
               addModel(method.getName().toString(), method.parameters(), removeModelComment(body));
         }
      }
   }

   /**
    * Checks if a body matches the default content of a generated method
    */
   private boolean isDefaultBody(final String body)
   {
      final String  bodyWithoutTabs = normalizeForDefaultBodyCheck(body);
      return bodyWithoutTabs.equals(getNormalizedDefaultInjectedBody());
   }

   private String getNormalizedDefaultInjectedBody()
   {
      return normalizeForDefaultBodyCheck(MoflonUtil.DEFAULT_METHOD_BODY);
   }

   private String normalizeForDefaultBodyCheck(final String body)
   {
      String normalizedBody = body;
      normalizedBody = body.replaceAll("//\\s*TODO:[^\\n]*", "");
      normalizedBody = normalizedBody.replaceAll("\\s+", " ");
      normalizedBody = normalizedBody.trim();
      return normalizedBody;
   }

   /**
    * Removes the the leading comment of the model method
    * 
    * @param body
    *           Body of the method
    * @return Body without the comment
    */
   private String removeModelComment(final String body)
   {
      if (body.equals(MoflonUtil.EOPERATION_MODEL_COMMENT))
         return "";
      else
         return body.substring(MoflonUtil.EOPERATION_MODEL_COMMENT.length() + 1);
   }

   /**
    * Extracts the hand-written imports from given String and saves them to imports
    * 
    * @param fileContent
    *           Content of the existing .java file
    */
   private void extractUserImports(final String fileContent)
   {
      int startIndex, endIndex;
      startIndex = fileContent.indexOf(InjectionRegions.USER_IMPORTS_BEGIN) + InjectionRegions.USER_IMPORTS_BEGIN.length();
      endIndex = fileContent.indexOf(InjectionRegions.USER_IMPORTS_END);

      if (startIndex >= 0 && endIndex >= 0)
      {
         final String extractedImports = fileContent.substring(startIndex, endIndex);

         if (!extractedImports.trim().equals(""))
         {
            imports = extractedImports;
            return;
         }
      }

      imports = "";
   }

   /**
    * Extracts the hand-written members code from given String and saves them to members
    * 
    * @param fileContent
    *           Content of the existing .java file
    */
   private void extractUserMembers(final String fileContent)
   {
      int startIndex, endIndex;
      startIndex = fileContent.indexOf(InjectionRegions.MEMBERS_BEGIN) + InjectionRegions.MEMBERS_BEGIN.length();
      endIndex = fileContent.indexOf(InjectionRegions.MEMBERS_END);

      if (startIndex >= 0 && endIndex >= 0)
      {
         final String extractedMembers = fileContent.substring(startIndex, endIndex);

         if (!extractedMembers.trim().equals(""))
         {
            setMembers(extractedMembers);
            return;
         }
      }

      setMembers("");
   }

   /**
    * Adds a new model method to models
    * 
    * @param name
    *           Name of the function
    * @param parameters
    *           The parameters as a list of SingleVariableDeclarations
    * @param body
    *           The body of the method
    */
   @SuppressWarnings("rawtypes")
   private void addModel(final String name, final List parameters, final String body)
   {
      final StringBuilder model = new StringBuilder();
      model.append("@model ");
      model.append(name);
      model.append(" (");
      // parameters
      for (final Object parameterObj : parameters)
      {
         final SingleVariableDeclaration parameter = (SingleVariableDeclaration) parameterObj;
         model.append(parameter.getType().toString());
         model.append(" ");
         model.append(parameter.getName().toString());
         if (!parameterObj.equals(parameters.get(parameters.size() - 1)))
            model.append(", ");
      }
      model.append(") <--\n");
      model.append(body);
      model.append("\n-->");
      models.add(model.toString());
   }

   /**
    * Sets the members field using the given code. It will be surrounded by "@members <-- ... -->"
    * 
    * @param code
    *           Code of the members section
    */
   private void setMembers(final String code)
   {
      if (code.equals(""))
         members = "";
      else
      {
         final StringBuilder membersSB = new StringBuilder();
         membersSB.append("@members <--\n");
         membersSB.append(code);
         membersSB.append("\n-->");
         members = membersSB.toString();
      }
   }

   /**
    * Checks if a method body belongs to an injected model method
    * 
    * @param methodBody
    *           Body of the method
    * @return True, if the method is an injected model method
    */
   private boolean isInjectedModel(final String methodBody)
   {
      return methodBody.trim().startsWith(MoflonUtil.EOPERATION_MODEL_COMMENT);
   }

   /**
    * Extracts the given block of code from the contentString and returns it as a String
    */
   private String getBlockBody(final Block block)
   {
      return contentString.substring(block.getStartPosition() + 1, block.getLength() + block.getStartPosition() - 1).trim();
   }

}
