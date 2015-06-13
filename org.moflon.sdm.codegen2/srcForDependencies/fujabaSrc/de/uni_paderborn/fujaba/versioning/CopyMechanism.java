/*Copyright*/
package de.uni_paderborn.fujaba.versioning;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import de.uni_kassel.coobra.Change;
import de.uni_kassel.coobra.MutableChange;
import de.uni_kassel.coobra.identifiers.ID;
import de.uni_kassel.coobra.persistency.MemoryPersistencyModule;
import de.uni_kassel.coobra.persistency.PersistencyModule;
import de.uni_kassel.coobra.transactions.TransactionEntry;
import de.uni_kassel.coobra.util.CopyManager;
import de.uni_kassel.features.ClassHandler;
import de.uni_kassel.features.ClassHandlerFactory;
import de.uni_kassel.features.FieldHandler;
import de.uni_kassel.features.ReferenceHandler;
import de.uni_paderborn.fujaba.asg.ASGElement;
import de.uni_paderborn.fujaba.asg.ASGInformation;
import de.uni_paderborn.fujaba.asg.ASGUnparseInformation;
import de.uni_paderborn.fujaba.fsa.FSAObject;
import de.uni_paderborn.fujaba.fsa.unparse.UnparseManager;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.uml.behavior.UMLActivityDiagram;
import de.uni_paderborn.fujaba.uml.behavior.UMLAttrExprPair;
import de.uni_paderborn.fujaba.uml.behavior.UMLCollabStat;
import de.uni_paderborn.fujaba.uml.behavior.UMLComplexState;
import de.uni_paderborn.fujaba.uml.behavior.UMLLink;
import de.uni_paderborn.fujaba.uml.behavior.UMLMultiLink;
import de.uni_paderborn.fujaba.uml.behavior.UMLNopActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLObject;
import de.uni_paderborn.fujaba.uml.behavior.UMLPath;
import de.uni_paderborn.fujaba.uml.behavior.UMLStartActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLStatement;
import de.uni_paderborn.fujaba.uml.behavior.UMLStatementActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLStopActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLStoryActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLStoryPattern;
import de.uni_paderborn.fujaba.uml.behavior.UMLTransition;
import de.uni_paderborn.fujaba.uml.behavior.UMLTransitionGuard;
import de.uni_paderborn.fujaba.uml.common.UMLCommentary;
import de.uni_paderborn.fujaba.uml.common.UMLConstraint;
import de.uni_paderborn.fujaba.uml.common.UMLTag;
import de.uni_paderborn.fujaba.uml.structure.UMLAssoc;
import de.uni_paderborn.fujaba.uml.structure.UMLAttr;
import de.uni_paderborn.fujaba.uml.structure.UMLClass;
import de.uni_paderborn.fujaba.uml.structure.UMLGeneralization;
import de.uni_paderborn.fujaba.uml.structure.UMLMethod;
import de.uni_paderborn.fujaba.uml.structure.UMLParam;
import de.uni_paderborn.fujaba.uml.structure.UMLRole;

/**
 * Singleton with methods for copy & paste support.
 *
 * @see #addToSupportedClasses
 * @see #findCommonContext
 * @see #copy
 * @see #paste
 */
public class CopyMechanism
{
   private static CopyMechanism instance;
   private Logger LOG = Logger.getLogger(CopyMechanism.class.getName());

   private CopyMechanism()
   {
      addToSupportedClasses(ASGUnparseInformation.class);
      addToSupportedClasses(ASGInformation.class);
      addToSupportedClasses(UMLClass.class);
      addToSupportedClasses(UMLGeneralization.class);

      addToSupportedClasses(UMLAssoc.class);
      addToSupportedClasses(UMLRole.class);

      addToSupportedClasses(UMLAttr.class);
      
      addToSupportedClasses(UMLMethod.class);
      addToSupportedClasses(UMLParam.class);

      addToSupportedClasses(UMLCommentary.class);
      addToSupportedClasses(UMLTag.class);

      addToSupportedClasses(UMLObject.class);
      addToSupportedClasses(UMLLink.class);
      addToSupportedClasses(UMLPath.class);
      addToSupportedClasses(UMLCollabStat.class);
      addToSupportedClasses(UMLMultiLink.class);
      addToSupportedClasses(UMLAttrExprPair.class);
      addToSupportedClasses(UMLStoryPattern.class);
      addToSupportedClasses(UMLConstraint.class);

      addToSupportedClasses(UMLComplexState.class);
      addToSupportedClasses(UMLNopActivity.class);
      addToSupportedClasses(UMLStartActivity.class);
      addToSupportedClasses(UMLStatementActivity.class);
      addToSupportedClasses(UMLStopActivity.class);
      addToSupportedClasses(UMLStoryActivity.class);
      addToSupportedClasses(UMLTransition.class);
      addToSupportedClasses(UMLActivityDiagram.class);
      addToSupportedClasses(UMLStatement.class);
      addToSupportedClasses(UMLTransitionGuard.class);
   }

   public static CopyMechanism get()
   {
      if (instance == null)
      {
         instance = new CopyMechanism();
      }
      return instance;
   }

   public Set<Class> supportedClasses = new HashSet<Class>();

   /**
    * Copy all elements from the collection, including their children, to the clipboard.
    *
    * @param copiedElements elements to be copied
    * @param context        context elements
    * @return a transferable if copying was successful, null if copying was not possible (unsupported elements)
    */
   public Transferable copy(Collection<FElement> copiedElements, List<FElement> context)
   {
      CopyManager manager = copyImpl(copiedElements, context);
      if (manager == null)
      {
         return null;
      }
      return manager.getTransferable(Versioning.FUJABA_MODEL_NAME);
   }

   public CopyManager copyImpl(Collection<FElement> copiedElements, List<FElement> context)
   {
      if ( context.size() < 1 )
      {
         throw new IllegalArgumentException("Context must contain at least one element");
      }
      boolean allElementsSupported = checkSupported(copiedElements);
      final MyFujabaCopyManager copyManager = new MyFujabaCopyManager(context.get(0).getProject());
      if (allElementsSupported)
      {
         // copy into copyManager
         try
         {
            copyManager.copy(copiedElements.iterator(), context);
            allElementsSupported = copyManager.supported;
         } catch (UnsupportedOperationException e)
         {
            e.printStackTrace();
            allElementsSupported = false;
         }
      }
      if (allElementsSupported)
      {
         copyManager.getRepository().getFeatureAccessModule().setClassHandlerFactory(
               ContextDummy.class.getName(), new ClassHandlerFactory()
         {
            public ClassHandler getClassHandler(String className) throws ClassNotFoundException
            {
            	throw new ClassNotFoundException();
            	// throw new UnsupportedOperationException();
            }

            public ClassHandler getClassHandler(Object instance) throws ClassNotFoundException
            {
               if ( instance instanceof ContextDummy)
               {
                  ContextDummy contextDummy = (ContextDummy) instance;
                  return contextDummy.getClassHandler();
               }
               else
               {
                  throw new ClassNotFoundException();
               }
            }
         });
         PersistencyModule persistencyModule = copyManager.getRepository().getPersistencyModule();
         MemoryPersistencyModule newPersistencyModule = new MemoryPersistencyModule();
         copyManager.getRepository().setPersistencyModule(newPersistencyModule);
         TransactionEntry entry = persistencyModule.receiveFirst();
         while ( entry != null )
         {
            // all entries must be mutable changes, otherwise we can't work with'em
            MutableChange mutableChange;
            if ( entry instanceof MutableChange )
            {
               mutableChange = (MutableChange) entry;
            }
            else
            {
               mutableChange = new MutableChange((Change) entry);
            }
            FieldHandler fieldHandler = mutableChange.getField();
            if (fieldHandler instanceof ReferenceHandler)
            {
               ReferenceHandler referenceHandler = (ReferenceHandler) fieldHandler;
               if ( referenceHandler.getAdornment() == ReferenceHandler.Adornment.PARENT )
               {
                  FElement element = (FElement) mutableChange.getNewValue();
                  if ( context.contains(element) )
                  {
                     // should be referenced with context id
                     ID id = copyManager.getRepository().getIdentifierModule().getID(element);
                     ContextDummy contextDummy = new ContextDummy( element.getProject(),
                           FujabaCopyManager.CONTEXT_PREFIX + id.getPrefix(),
                           id.getClassHandler());
                     copyManager.generateCreate(contextDummy);
                     mutableChange.setNewValue(contextDummy);
                  }
               }
            }
            newPersistencyModule.send( entry, null );
            entry = persistencyModule.receiveNext(entry);
         }
         return copyManager;
      } else
      {
         return null;
      }
   }

   public ASGElement mapFujabaSpecificContext(ASGElement selection)
   {
      if ( selection instanceof UMLStoryActivity )
      {
         // paste into pattern if activity is selected
         UMLStoryActivity storyActivity = (UMLStoryActivity) selection;
         return storyActivity.getStoryPattern();
      }
      // no special mapping - return selection
      return selection;
   }

   public class ContextDummy extends ASGElement
   {
      public ClassHandler getClassHandler()
      {
         return classHandler;
      }

      public ContextDummy(FProject project, String identifier, ClassHandler classHandler)
      {
         super( project, false );
         this.identifier = identifier;
         this.classHandler = classHandler;
      }


      /**
       * getter for field identifier
       *
       * @return current value of field identifier
       */
      public String getIdentifier()
      {
         return this.identifier;
      }

      /**
       * store the value for field identifier
       */
      private final String identifier;
      private final ClassHandler classHandler;
   }

   /**
    * Finds a common parent element to all elements in the list.
    *
    * @param list list of elements
    * @return common parent element of all list entries, null if no such parent could be found
    */
   public FElement findCommonContext(Collection<FElement> list)
   {
      FElement context = list.iterator().next();
      searchContext:
      while (context != null)
      {
         context = context.getParentElement();
         for (FElement element : list)
         {
            FElement parent = element;
            while (parent != null && parent.getParentElement() != context)
            {
               parent = parent.getParentElement();
            }
            if (parent == null || parent.getParentElement() != context)
            {
               continue searchContext;
            }
         }
         break; // found a common context
      }
      return context;
   }

   public void addToSupportedClasses(Class<? extends FElement> cls)
   {
      supportedClasses.add(cls);
   }

   public boolean checkSupported(FElement element)
   {
      Class<?> clazz = element.getClass();
      boolean supported = supportedClasses.contains(clazz);
      if (!supported)
      {
         System.err.println("Unsupported type for copy&paste: " + element.getClass());
      }
      return supported;
   }

   public boolean checkSupported(Collection<FElement> elements)
   {
      for (FElement element : elements)
      {
         if (!checkSupported(element))
         {
            return false;
         }
      }
      return true;
   }

   /**
    * Paste the contents of the transferable into the new context.
    *
    * @param contents transferable (created by  {@link #copy})
    * @param context  new context
    * @throws UnsupportedFlavorException if the data flavor is not supported
    * @throws IOException                when reading from the transferable causes an IO error
    */
   public static CopyManager.PasteResult paste(Transferable contents, ASGElement context)
         throws UnsupportedFlavorException, IOException
   {
      FujabaCopyManager copyManager = new FujabaCopyManager(context.getProject());
      CopyManager.PasteResult pasteResult = copyManager.paste(contents, Arrays.asList(context),
            Versioning.FUJABA_MODEL_NAME);
      java.util.List<Object> list = pasteResult.getPastedObjects();
//         System.out.println(list);
      for (Object obj : list)
      {
         if (obj instanceof ASGUnparseInformation)
         {
            // FIXME !!! Remove dependency to UnparseManager and FSAObject
            ASGUnparseInformation info = (ASGUnparseInformation) obj;
            Iterator iter = UnparseManager.get().iteratorOfFsaObjects(info.getASGElement());
            while (iter.hasNext())
            {
               FSAObject fsa = (FSAObject) iter.next();
               fsa.applyProperties();
            }
         }
      }
      return pasteResult;
   }

   public Logger getLogger()
   {
      return LOG;
   }

   private class MyFujabaCopyManager extends FujabaCopyManager
   {
      boolean supported = true;

      public MyFujabaCopyManager(FProject project)
      {
         super(project);
      }

      @Override
      protected boolean generateCreate(Object value)
      {
         boolean copied = super.generateCreate(value);
         if (copied && value instanceof FElement)
         {
            if (!checkSupported((FElement) value))
            {
               supported = false;
            }
         }
         return copied;
      }
   }
}

/*
 * $Log$
 */

