/*
 * generated by Fujaba - CodeGen2
 */
package de.uni_kassel.fujaba.codegen.engine;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import de.uni_kassel.features.ReferenceHandler; // requires Fujaba5/libs/features.jar in classpath
import de.uni_kassel.features.annotation.util.Property; // requires Fujaba5/libs/features.jar in classpath
import de.uni_kassel.fujaba.codegen.engine.message.MessageHandler;
import de.uni_paderborn.fujaba.app.FrameMain;
import de.uni_paderborn.fujaba.messages.ErrorMessage;
import de.uni_paderborn.fujaba.messages.Message;
import de.uni_paderborn.fujaba.messages.MessageView;
import de.uni_paderborn.fujaba.messages.Warning;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.upb.tools.fca.FEmptyIterator;
import de.upb.tools.fca.FHashSet; // requires Fujaba5/libs/RuntimeTools.jar in classpath
import de.upb.tools.sdm.JavaSDM; // requires Fujaba5/libs/RuntimeTools.jar in classpath
import de.upb.tools.sdm.JavaSDMException;


public abstract class Engine implements MessageHandler
{


   /**
    * <pre>
    *           0..n     engines     0..1
    * Engine ------------------------- CodeGeneration
    *           engines               codeGeneration
    * </pre>
    */
   public static final String PROPERTY_CODE_GENERATION = "codeGeneration";

   @Property( name = PROPERTY_CODE_GENERATION, partner = CodeGeneration.PROPERTY_ENGINES, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         adornment = ReferenceHandler.Adornment.NONE)
   private CodeGeneration codeGeneration;

   @Property( name = PROPERTY_CODE_GENERATION )
   public boolean setCodeGeneration (CodeGeneration value)
   {
      boolean changed = false;

      if (this.codeGeneration != value)
      {
      
         CodeGeneration oldValue = this.codeGeneration;
         Engine source = this;
         if (this.codeGeneration != null)
         {
            this.codeGeneration = null;
            oldValue.removeFromEngines (this);
         }
         this.codeGeneration = value;

         if (value != null)
         {
            value.addToEngines (this);
         }
         changed = true;
      
      }
      return changed;
   }

   @Property( name = PROPERTY_CODE_GENERATION )
   public Engine withCodeGeneration (CodeGeneration value)
   {
      setCodeGeneration (value);
      return this;
   }

   public CodeGeneration getCodeGeneration ()
   {
      return this.codeGeneration;
   }

   public void error (String userFeedback , FElement context )
   {
      boolean fujaba__Success = false;
      FrameMain frame = null;
      MessageView messageView = null;
      ErrorMessage error = null;

      // story pattern 
      try 
      {
         fujaba__Success = false; 

         frame = FrameMain.get();

         // check object frame is really bound
         JavaSDM.ensure ( frame != null );
         // search to-one link messageView from frame to messageView
         messageView = frame.getMessageView ();

         // check object messageView is really bound
         JavaSDM.ensure ( messageView != null );


         // create object error
         error = new ErrorMessage ( );

         // assign attribute error
         error.setText (userFeedback);
         // assign attribute error
         error.setMessageCategory ("Code Generation");
         // check optional
         if (context != null)
         {
            // create link context from error to context
            error.addToContext (context);

         }// create link messages from messageView to error
         messageView.addToMessages (error);

         // create link messages from this to error
         this.addToMessages (error);

         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      return ;
   }

   public abstract Map generateCode (FElement element );

   public abstract String generateCode (FElement element , String targetName );

   public void internalError (String userFeedback , FElement context )
   {
      boolean fujaba__Success = false;
      FrameMain frame = null;
      MessageView messageView = null;
      ErrorMessage error = null;

      // story pattern 
      try 
      {
         fujaba__Success = false; 

         frame = FrameMain.get();

         // check object frame is really bound
         JavaSDM.ensure ( frame != null );
         // search to-one link messageView from frame to messageView
         messageView = frame.getMessageView ();

         // check object messageView is really bound
         JavaSDM.ensure ( messageView != null );


         // create object error
         error = new ErrorMessage ( );

         // assign attribute error
         error.setText ("Internal error: " + userFeedback);
         // assign attribute error
         error.setMessageCategory ("Internal Code Generation Error");
         // check optional
         if (context != null)
         {
            // create link context from error to context
            error.addToContext (context);

         }// create link messages from messageView to error
         messageView.addToMessages (error);

         // create link messages from this to error
         this.addToMessages (error);

         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      return ;
   }

   public boolean isResponsible (FElement element , String targetLanguage )
   {
      boolean fujaba__Success = false;

      // story pattern storypatternwiththis
      try 
      {
         fujaba__Success = false; 

         // attribute condition targetLanguage == targetLanguage
         JavaSDM.ensure ( JavaSDM.stringCompare ((String) this.getTargetLanguage (), targetLanguage) == 0 );

         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      if ( !( fujaba__Success ) )
      {
         return false;

      }
      return true;
   }

   /**
    * <pre>
    *           0..1     messages     0..n
    * Engine ------------------------> Message
    *           engine               messages
    * </pre>
    */
   public static final String PROPERTY_MESSAGES = "messages";

   @Property( name = PROPERTY_MESSAGES, kind = ReferenceHandler.ReferenceKind.TO_MANY,
         adornment = ReferenceHandler.Adornment.NONE)
   private FHashSet<Message> messages;

   @Property( name = PROPERTY_MESSAGES )
   public Set<? extends Message> getMessages()
   {
      return ((this.messages == null)
              ? Collections.EMPTY_SET
              : Collections.unmodifiableSet(this.messages));
   }

   @Property( name = PROPERTY_MESSAGES )
   public boolean addToMessages (Message value)
   {
      boolean changed = false;

      if (value != null)
      {
         if (this.messages == null)
         {
            this.messages = new FHashSet<Message> ();

         }
      
         changed = this.messages.add (value);
      
      }
      return changed;
   }

   @Property( name = PROPERTY_MESSAGES )
   public Engine withMessages (Message value)
   {
      addToMessages (value);
      return this;
   }

   public Engine withoutMessages (Message value)
   {
      removeFromMessages (value);
      return this;
   }


   public boolean removeFromMessages (Message value)
   {
      boolean changed = false;

      if ((this.messages != null) && (value != null))
      {
      
         changed = this.messages.remove (value);
      
      }
      return changed;
   }

   @Property( name = PROPERTY_MESSAGES )
   public void removeAllFromMessages (){
      if (this.messages != null && this.messages.size () > 0)
      {
      
         this.messages.clear();
      
      }
   }

   @Property( name = PROPERTY_MESSAGES )
   public boolean hasInMessages (Message value)
   {
      return ((this.messages != null) &&
              (value != null) &&
              this.messages.contains (value));
   }

   @Property( name = PROPERTY_MESSAGES )
   public Iterator<? extends Message> iteratorOfMessages ()
   {
      return ((this.messages == null)
              ? FEmptyIterator.<Message>get ()
              : this.messages.iterator ());
   }

   @Property( name = PROPERTY_MESSAGES )
   public int sizeOfMessages ()
   {
      return ((this.messages == null)
              ? 0
              : this.messages.size ());
   }

   public static final String PROPERTY_TARGET_LANGUAGE = "targetLanguage";

   @Property( name = PROPERTY_TARGET_LANGUAGE, kind = ReferenceHandler.ReferenceKind.ATTRIBUTE )
   private String targetLanguage;

   @Property( name = PROPERTY_TARGET_LANGUAGE )
   public void setTargetLanguage (String value)
   {
      this.targetLanguage = value;
   }

   public Engine withTargetLanguage (String value)
   {
      setTargetLanguage (value);
      return this;
   }

   @Property( name = PROPERTY_TARGET_LANGUAGE )
   public String getTargetLanguage ()
   {
      return this.targetLanguage;
   }

   public void warning (String userFeedback , FElement context )
   {
      boolean fujaba__Success = false;
      FrameMain frame = null;
      MessageView messageView = null;
      Warning warning = null;

      // story pattern 
      try 
      {
         fujaba__Success = false; 

         frame = FrameMain.get();

         // check object frame is really bound
         JavaSDM.ensure ( frame != null );
         // search to-one link messageView from frame to messageView
         messageView = frame.getMessageView ();

         // check object messageView is really bound
         JavaSDM.ensure ( messageView != null );


         // create object warning
         warning = new Warning ( );

         // assign attribute warning
         warning.setText (userFeedback);
         // assign attribute warning
         warning.setMessageCategory ("Code Generation Warning");
         // check optional
         if (context != null)
         {
            // create link context from warning to context
            warning.addToContext (context);

         }// create link messages from messageView to warning
         messageView.addToMessages (warning);

         // create link messages from this to warning
         this.addToMessages (warning);

         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      return ;
   }

   public void removeYou()
   {
      this.setCodeGeneration (null);
      this.removeAllFromMessages ();
   }
}


