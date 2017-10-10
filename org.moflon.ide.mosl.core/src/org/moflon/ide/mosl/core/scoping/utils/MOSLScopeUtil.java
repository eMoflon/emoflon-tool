package org.moflon.ide.mosl.core.scoping.utils;

import java.io.IOException;
import java.util.Collections;
import java.util.Stack;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

public class MOSLScopeUtil
{
   private static MOSLScopeUtil instance;
   
   private MOSLScopeUtil(){}
   
   public static MOSLScopeUtil getInstance()
   {
      if(instance == null)
         instance = new MOSLScopeUtil();
      return instance;
   }
   
   public <R extends EObject> R getRootObject(EObject context, Class<R> clazz)
   {
      Stack<EObject> stack = new Stack<EObject>();
      stack.push(context);
      while(!stack.isEmpty())
      {
         EObject element = stack.pop();
         if(element == null)
         {
            return null;
         }
         else if(clazz.isInstance(element))
            return clazz.cast(element);
         stack.push(element.eContainer());
      }
      return null;
   }
   
   public <E extends EObject> E getObjectFromResourceSet(URI uri, ResourceSet resourceSet, Class<E> clazz)
   {
      try
      {
         Resource res = resourceSet.getResource(uri, false);
         if (res == null)
         {
            res = resourceSet.createResource(uri);
            res.load(Collections.EMPTY_MAP);
         }
         E scopingRoot = clazz.cast(res.getContents().get(0));
         return scopingRoot;
      } catch (IOException e)
      {
        return null;
      }
   }
}
