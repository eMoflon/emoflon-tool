package org.moflon.maave.tool.symbolicgraphs.Datastructures;

import java.util.Set;

public class IdentityMapping<T> extends Mapping<T>
{

   
  

   @Override
   public T imageOf(T t)
   {
      return t;
   }

   @Override
   public void addMapping(T preImage, T image)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean isInImage(T element)
   {
      
     throw new UnsupportedOperationException();
   }

   @Override
   public Mapping<T> compose(Mapping<T> after)
   {
      return new Mapping<T>(after);
   }

   @Override
   public Set<T> getPreimage()
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public String toString()
   {
      
      return "Identity mapping";
   }

}
