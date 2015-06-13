/*Copyright*/
package de.uni_kassel.fujaba.codegen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import de.uni_kassel.fujaba.codegen.engine.CodeGeneration;
import de.uni_kassel.fujaba.codegen.engine.Engine;
import de.uni_paderborn.fujaba.basic.RuntimeExceptionWithContext;
import de.uni_paderborn.fujaba.messages.Message;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;

class CodeGen2 extends de.fujaba.codegen.CodeGeneration
{
   public StringBuffer generateFElement(FElement elem, boolean save, String targetName)
   {
      if (targetName == null) targetName = "java";
      CodeGeneration codeGeneration = CodeGeneration.get();
      removeMessages (codeGeneration, elem);
      Engine engineFor = codeGeneration.getEngineFor (elem, targetName);
      if (engineFor == null)
      {
         System.err.println ("No engine found for element of type " + elem.getClass().getName() + " and target " + targetName + ".");
         return null;
      }
      Map map = engineFor.generateCode (elem);
      String code;
      if (map != null)
      {
         code = (String) map.values().iterator().next();
      }
      else
      {
         throw new RuntimeExceptionWithContext("Failed to generate code for element of type " + elem.getClass(), elem);
      }

//      showMessages (codeGeneration);

      return new StringBuffer(code);
   }


   /**
       * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param codeGeneration  No description provided
    */
   protected void showMessages (CodeGeneration codeGeneration)
   {
      //todo: better feedback, jump to context, list, etc.
      for (Iterator it = codeGeneration.iteratorOfEngines(); it.hasNext(); )
      {
         Engine engine = (Engine) it.next();
         for (Iterator it2 = engine.iteratorOfMessages(); it2.hasNext(); )
         {
            Message message = (Message) it2.next();
            System.err.println (message.getText());
         }
      }

   }


   /**
    * Remove all messages with a given element or it's children in the context.
    *
    * @param codeGeneration  code generation
    * @param generatedElement element that is currently processed
    */
   protected void removeMessages(CodeGeneration codeGeneration, FElement generatedElement)
   {
      for (Iterator it = codeGeneration.iteratorOfEngines(); it.hasNext(); )
      {
         Engine engine = (Engine) it.next();
         Collection<Message> removedMessages = new ArrayList<Message>();
         final boolean deleteAll = generatedElement == null || generatedElement instanceof FProject;
         for (Iterator it2 = engine.iteratorOfMessages(); it2.hasNext();)
         {
            Message message = (Message) it2.next();
            if (deleteAll)
            {
               message.removeYou();
            } else
            {
               for (Iterator<? extends FElement> it3 = message.iteratorOfContext(); it3.hasNext();)
               {
                  FElement context = it3.next();
                  do
                  {
                     if (context == generatedElement)
                     {
                        message.removeYou();
                        removedMessages.add(message);
                     }
                     try
                     {
                        context = context.getParentElement();
                     } catch (UnsupportedOperationException e)
                     {
                        context = null;
                     }
                  } while (context != null);
               }
            }
         }
         if (deleteAll)
         {
            engine.removeAllFromMessages();
         } else
         {
            for (Message removedMessage : removedMessages)
            {
               engine.removeFromMessages(removedMessage);
            }
         }
      }

   }


   /**
    * @see de.fujaba.codegen.CodeGeneration#getCodegenTargets()
    */
   @Override
   public Iterator<String> getCodegenTargets()
   {
      ArrayList<String> targets = new ArrayList<String>();
      Iterator i = CodeGeneration.get().iteratorOfEngines();
      while(i.hasNext())
      {
         Engine engine = (Engine)i.next();
         targets.add(engine.getTargetLanguage());
      }
      return targets.iterator();
   }
}

/*
 * $Log$
 */

