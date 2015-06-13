package de.uni_kassel.fujaba.codegen.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.uni_kassel.coobra.identifiers.IdentifierModule;
import de.uni_kassel.fujaba.codegen.dlr.DLRFileToken;
import de.uni_kassel.fujaba.codegen.dlr.DLRProjectToken;
import de.uni_kassel.fujaba.codegen.dlr.DLRToken;
import de.uni_kassel.fujaba.codegen.dlr.DLRTool;
import de.uni_kassel.fujaba.codegen.dlr.ElementReference;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.preferences.FujabaPreferencesManager;

public class DLRImporter extends DefaultHandler
{
   private DLRToken current;
   private DLRProjectToken projectToken;
   private IdentifierModule identifierModule;
   private FProject project;
   private static DLRImporter instance;

   private DLRImporter()
   {
   }

   public static DLRImporter get()
   {
      if (instance == null)
      {
         instance = new DLRImporter();
      }
      return instance;
   }

   @Override
   public void endElement(String uri, String localName, String name)
         throws SAXException
   {
      if ("token".equals(name))
      {
         current = (DLRToken) current.getParent();
      }
   }



   @Override
   public void startElement(String uri, String localName, String name,
         Attributes attributes) throws SAXException
   {
      if ("token".equals(name))
      {
         //<token id="0" startLine="0" endLine="0" offset="0" length="0" comment="">
         if (current == null)
         {
            projectToken = new DLRProjectToken();
            current = projectToken;
            DLRTool.get().addToProjects(project, projectToken);
         }
         else
         {
            Collection<FElement> elements = new ArrayList<FElement>();
            String fileName = attributes.getValue("fileName");
            DLRToken token;
            if (fileName != null)
            {
               DLRFileToken fileToken = new DLRFileToken();
               token = projectToken.createDLRToken(fileToken, elements);
               fileToken.setFileName(fileName);
               fileToken.setPath (attributes.getValue("path"));
            }
            else
            {
               token = projectToken.createDLRToken(elements);
            }
            token.setParent(current);
            projectToken.removeFromTokenByID(token);
            long id = Long.parseLong(attributes.getValue("id"));
            token.setId(id);
            projectToken.setNextId(Long.MAX_VALUE - 1); // prevent being kicked out of the map
            projectToken.addToTokenByID(token);
            int startLine = Integer.parseInt(attributes.getValue("startLine"));
            token.setStartLine(startLine);
            int endLine = Integer.parseInt(attributes.getValue("endLine"));
            token.setEndLine(endLine);
            int offset = Integer.parseInt(attributes.getValue("offset"));
            token.setOffset(offset);
            int length = Integer.parseInt(attributes.getValue("length"));
            token.setLength(length);
            String comment = attributes.getValue("comment");
            token.setComment(comment);
            current = token;
         }
      }
      else if ("element".equals(name))
      {
         //<element id="RrHeX#1"/>
         String id = attributes.getValue("id");
         FElement element = (FElement) identifierModule.getObject(id);
         if (element != null)
         {
            ElementReference ref = new ElementReference();
            ref.setElement(element);
            ref.setToken(current);
            projectToken.addToElementReference(ref);
         }
/*         else
         {
            if (!(current instanceof DLRFileToken))
            {
               System.err.println("ID not found " + id + ", comment: " + current.getComment());
            }
         }*/
      }
      else
      {
         System.err.println("Error");
      }
   }



   public void importDLRFile (FProject project) throws ParserConfigurationException, SAXException, IOException
   {
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser saxParser = factory.newSAXParser();
      current = null;
      identifierModule = project.getRepository().getIdentifierModule();
      this.project = project;
      String expPath = FujabaPreferencesManager.getExportFolder(project);
      String filename = expPath + "/dlr/" + project.getName() + ".dlr.xml";
      File file = new File(filename);
      if (file.exists())
      {
         saxParser.parse(file.toURI().toASCIIString(),this);
      }
      else
      {
         System.err.println("Generate code for project first.");
      }
   }

   public DLRProjectToken getProjectTokenWithImport (FProject umlproject)
   {
      DLRProjectToken project = DLRTool.get().getFromProjects (umlproject);
      if (project == null)
      {
         try
         {
            importDLRFile(umlproject);
            project = DLRTool.get().getFromProjects (umlproject);
         }
         catch (Exception e)
         {
            e.printStackTrace();
         }
      }
      return project;
   }
}