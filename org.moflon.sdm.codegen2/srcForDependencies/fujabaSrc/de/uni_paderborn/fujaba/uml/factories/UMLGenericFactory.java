package de.uni_paderborn.fujaba.uml.factories;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;


/**
 * Generic factory to create any model element specified by class.
 * 
 * @author cschneid
 * @author Last editor: $Author$
 * @version $Revision$ $Date$
 */
public class UMLGenericFactory<I extends FElement> extends
      UMLHeavyweightFactory<I>
{
   private final Class<I> interfaceClass;

   private final Class<? extends I> concreteClass;

   private Constructor<? extends I> constructor;


   public UMLGenericFactory(FProject project, Class<I> concreteClass)
   {
      this(project, concreteClass, concreteClass);
   }


   public UMLGenericFactory(FProject project, Class<I> interfaceClass,
         Class<? extends I> concreteClass)
   {
      super(project);
      this.interfaceClass = interfaceClass;
      this.concreteClass = concreteClass;
   }


   /**
    */
   protected Constructor<? extends I> getConstructor()
   {
      if (this.constructor == null)
      {
         try
         {
            this.constructor = this
                  .getConstructor(FProject.class, Boolean.TYPE);
            this.constructor.setAccessible(true);
         }
         catch (NoSuchMethodException e)
         {
            try
            {
               Class<? extends FProject> projectClass = getProject().getClass();
               this.constructor = this.getConstructor(projectClass,
                     Boolean.TYPE);
               this.constructor.setAccessible(true);
            }
            catch (NoSuchMethodException e2)
            {
               throw new IllegalArgumentException(concreteClass
                     + " has neither a Constructor(FProject, boolean)"
                     + " nor a Constructor(UMLProject, boolean).");
            }
         }
      }
      return this.constructor;
   }


   protected Constructor<? extends I> getConstructor(Class<?>... classes)
         throws NoSuchMethodException
   {
      this.constructor = this.concreteClass.getDeclaredConstructor(classes);
      this.constructor.setAccessible(true);
      return this.constructor;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#getInterfaceClass()
    */
   public Class<I> getInterfaceClass()
   {
      return this.interfaceClass;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#getConcreteClass()
    */
   public Class<? extends I> getConcreteClass()
   {
      return this.concreteClass;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#create(boolean)
    */
   public I create(boolean persistent)
   {
      try
      {
         I newInstance = getConstructor().newInstance(getProject(), persistent);
         addToProducts(newInstance);
         return newInstance;
      }
      catch (InstantiationException e)
      {
         throw new RuntimeException(e);
      }
      catch (IllegalAccessException e)
      {
         throw new RuntimeException(e);
      }
      catch (InvocationTargetException e)
      {
         throw new RuntimeException(e);
      }
   }

}
