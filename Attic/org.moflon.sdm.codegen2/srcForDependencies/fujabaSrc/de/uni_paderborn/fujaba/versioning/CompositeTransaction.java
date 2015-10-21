package de.uni_paderborn.fujaba.versioning;

import de.uni_kassel.coobra.transactions.Transaction;
import de.uni_kassel.util.ConcurrentHashSet;
import de.uni_kassel.util.EmptyIterator;
import de.uni_paderborn.fujaba.app.FrameMain;

import java.util.Iterator;
import java.util.Set;

/**
 * @author christian.schneider@uni-kassel.de
 */
public class CompositeTransaction
{

   /**
    * getter for field name
    *
    * @return current value of field name
    */
   public String getName()
   {
      return this.name;
   }

   /**
    * store the value for field name
    */
   private final String name;

   CompositeTransaction( String name )
   {
      this.name = name;
   }

   private Set<Transaction> transactions;

   boolean addToTransactions( Transaction value )
   {
      boolean changed = false;
      if ( value != null )
      {
         if ( this.transactions == null )
         {
            this.transactions = new ConcurrentHashSet<Transaction>();
         }
         changed = this.transactions.add( value );
      }
      return changed;
   }

   public boolean hasInTransactions( Transaction value )
   {
      return ( ( this.transactions != null ) &&
            ( value != null ) &&
            this.transactions.contains( value ) );
   }

   public Iterator<Transaction> iteratorOfTransactions()
   {
      if ( this.transactions == null )
      {
         return EmptyIterator.get();
      } else
      {
         return this.transactions.iterator();
      }
   }

   boolean removeFromTransactions( Transaction value )
   {
      boolean changed = false;
      if ( ( this.transactions != null ) && ( value != null ) )
      {
         changed = this.transactions.remove( value );
      }
      return changed;
   }

   public int sizeOfTransactions()
   {
      return ( ( this.transactions == null )
            ? 0
            : this.transactions.size() );
   }

   /**
    * Commit (finish) all transactions.
    */
   public void commit()
   {
      if ( sizeOfTransactions() > 0 || !isUndoable() )
      {
         for ( Iterator<Transaction> it = iteratorOfTransactions(); it.hasNext(); )
         {
            Transaction transaction = it.next();
            if ( transaction.getRepository().getPersistencyModule().isOpened() )
            {
               try
               {
                  transaction.commit();
               } catch (IllegalStateException e)
               {
                  // transaction is broken, but we still want to close it
                  e.printStackTrace();
               }
            }
            else
            {
               // project closed
            }
            //todo: externalize
//            removeFromTransactions( transaction );
//            addToTransactions( transaction.externalize() );
         }
         setPrevious( Versioning.get().getLastTransaction() );
         Versioning.get().setLastTransaction( this );
      }
      if ( ActionTransactionListener.get().getActiveTransaction() == this )
      {
         ActionTransactionListener.get().setActiveTransaction( null );
      }
   }

   /**
    * Abort all transactions.
    */
   public void abort()
   {
      try
      {
         for ( Iterator<Transaction> it = iteratorOfTransactions(); it.hasNext(); )
         {
            Transaction transaction = it.next();
            transaction.rollback();
         }
      }
      finally
      {
         if ( ActionTransactionListener.get().getActiveTransaction() == this )
         {
            ActionTransactionListener.get().setActiveTransaction( null );
         }
      }
   }

   /**
    * Redo all transactions.
    */
   public void redo()
   {
      if ( Versioning.get().getNextTransaction() != this )
      {
         throw new IllegalStateException( "Only the next transaction can be redone!" );
      }
      for ( Iterator<Transaction> it = iteratorOfTransactions(); it.hasNext(); )
      {
         Transaction transaction = it.next();
         transaction.recommit();
      }
      Versioning.get().setLastTransaction( this );
   }

   /**
    * Undo transactions.
    * @return true on success, false if not possible
    */
   public boolean undo()
   {
      if (Versioning.get().getLastTransaction() != this)
      {
         throw new IllegalStateException(
               "Only the previous transaction can be undone!");
      }
      if (isUndoable())
      {
         for (Iterator<Transaction> it = iteratorOfTransactions(); it.hasNext();)
         {
            Transaction transaction = it.next();
            transaction.rollback();
         }
         Versioning.get().setLastTransaction(getPrevious());
         return true;
      }
      else
      {
         // FIXME !!! remove UI access
         FrameMain.get().showError(
               "Cannot undo action '" + getName() + "'!.",
               null);
         return false;
      }
   }

   /**
    * store value for field next
    */
   private CompositeTransaction next;

   /**
    * @return current value of the field next
    */
   public CompositeTransaction getNext()
   {
      return this.next;
   }

   /**
    * @param value new value for field next
    * @return true if next was changed
    */
   public boolean setNext( final CompositeTransaction value )
   {
      final CompositeTransaction oldValue = this.next;
      boolean changed = false;
      if ( oldValue != value )
      {
         if ( oldValue != null )
         {
            this.next = null;
            oldValue.setPrevious( null );
         }
         this.next = value;
         if ( value != null )
         {
            value.setPrevious( this );
         }
         changed = true;
      }
      return changed;
   }

   /**
    * store value for field previous
    */
   private CompositeTransaction previous;

   /**
    * @return current value of the field previous
    */
   public CompositeTransaction getPrevious()
   {
      return this.previous;
   }

   /**
    * @param value new value for field previous
    * @return true if previous was changed
    */
   public boolean setPrevious( final CompositeTransaction value )
   {
      final CompositeTransaction oldValue = this.previous;
      boolean changed = false;
      if ( oldValue != value )
      {
         if ( oldValue != null )
         {
            this.previous = null;
            oldValue.setNext( null );
         }
         this.previous = value;
         if ( value != null )
         {
            value.setNext( this );
         }
         changed = true;
      }
      return changed;
   }

   /**
    * @return a string representation of the object.
    */
   @Override
   public String toString()
   {
      return getName();
   }

   /**
    * @see #isUndoable()
    */
   private boolean undoable = true;

   /**
    * @see #isUndoable()
    * @param allowed false to disallow undo of this transaction
    */
   public void setUndoable( boolean allowed )
   {
      undoable = allowed;
   }

   /**
    * @return true if this composite transaction is allowed to be undone
    */
   public boolean isUndoable()
   {
      return undoable;
   }
}
