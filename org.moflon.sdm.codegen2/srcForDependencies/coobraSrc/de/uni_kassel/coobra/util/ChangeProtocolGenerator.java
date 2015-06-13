/*
 * Copyright (c) 2004-2006 Software Engineering Kassel, various authors,
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'CoObRA' nor 'CoObRA2' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package de.uni_kassel.coobra.util;

import de.uni_kassel.coobra.Change;
import de.uni_kassel.coobra.Repository;
import de.uni_kassel.features.ClassHandler;
import de.uni_kassel.features.FieldHandler;
import de.uni_kassel.features.PlainFieldHandler;
import de.uni_kassel.features.PrimitiveClassHandler;
import de.uni_kassel.features.visitor.Visitor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author schneider
 */
public abstract class ChangeProtocolGenerator implements Visitor
{
   private final Repository repository;

   public Repository getRepository()
   {
      return repository;
   }

   private static final UnsupportedOperationException UNSUPPORTED_OPERATION_EXCEPTION = new UnsupportedOperationException();

   public ChangeProtocolGenerator(Repository repository)
   {
      this.repository = repository;
   }

   public Boolean visit(Object instance, ClassHandler type)
   {
      creation(instance);
      return null;
   }

   private Set<Object> createdObjects = new HashSet<Object>();

   private void creation(Object instance)
   {
      if ( !createdObjects.contains(instance) && generateCreate(instance))
      {
         try
         {
            ClassHandler classHandler = getRepository().getFeatureAccessModule().getClassHandler(instance);
            if ( classHandler instanceof PrimitiveClassHandler || String.class.getName().equals(classHandler.getName()) )
            {
               return; // no creation for primitives
            }
            createdObjects.add( instance );
            Change change = repository.getChangeFactory().changeCreateObject(instance, getCreationKey(instance));
            repository.acknowledgeChange(change);
         } catch (ClassNotFoundException e)
         {
            throw new RuntimeException("Classloader problems?", e);
         }
      }
   }

   public boolean foundNeighbour(Object instance, ClassHandler type, FieldHandler field, Object neighbour)
   {
      boolean isTransient = Boolean.TRUE.equals(field.isTransient());
      if (!isTransient)
      {

         Object initialValue = null;
         if (field instanceof PlainFieldHandler)
         {
            PlainFieldHandler plainFieldHandler = (PlainFieldHandler) field;
            try
            {
               initialValue = getInitialValue(type, plainFieldHandler);
               if (initialValue != null && neighbour != null && initialValue.equals(neighbour))
               {
                  return true;
               }
               if (initialValue == null && neighbour == null)
               {
                  return true;
               }
            } catch (UnsupportedOperationException e)
            {
               // ok, initial value not available
            }
         }

         Change change;
         if (neighbour instanceof Map.Entry)
         {
            Map.Entry entry = (Map.Entry) neighbour;
            if ( entry.getKey() != null ) creation( entry.getKey() );
            if ( entry.getValue() != null ) creation( entry.getValue() );
            change = repository.getChangeFactory().changeAlterField(
                  instance, field, entry.getKey(), initialValue, entry.getValue());
         } else
         {
            if ( neighbour != null ) creation( neighbour );
            change = repository.getChangeFactory().changeAlterField(
                  instance, field, initialValue, neighbour);
         }
         repository.acknowledgeChange(change);
      }
      return true;
   }

   private final Map<ClassHandler, Map<FieldHandler, Object>> initialValues = new HashMap<ClassHandler, Map<FieldHandler, Object>>();

   private Object getInitialValue(ClassHandler instanceType, PlainFieldHandler field)
   {
      Map<FieldHandler, Object> map = initialValues.get(instanceType);
      if (map == null)
      {
         map = new HashMap<FieldHandler, Object>();
         initialValues.put(instanceType, map);
      }
      if (map.containsKey(field))
      {
         return map.get(field);
      } else
      {
         Object value = determineInitialFieldValue(instanceType, field);
         map.put(field, value);
         return value;
      }
   }

   protected Object determineInitialFieldValue(ClassHandler instanceType, PlainFieldHandler field)
   {
      throw UNSUPPORTED_OPERATION_EXCEPTION;
   }

   protected Object getCreationKey(Object instance)
   {
      return null;
   }

   protected abstract boolean generateCreate(Object value);
}

/*
 * $log$
 */

