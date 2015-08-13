package org.moflon.mosl.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ExtendedMapResolver <I, K , V>
{
   public  class Pair<F, S>{
      private  F first;
      private  S second;
      
      public Pair(F k, S v){
         first = k;
         second =v;
      }
      
      public F getFirst()
      {
         return first;
      }
      public void setFirst(F first)
      {
         this.first = first;
      }
      public S getSecond()
      {
         return second;
      }
      public void setSecond(S second)
      {
         this.second = second;
      }
      
      public String toString(){
    	  return "(" + first.toString() + "; " + second.toString() + ")";
      }

   }
   
   private Map<I, Pair<K, V>> content;
  
   public ExtendedMapResolver(){
      content = new HashMap<>();
   }
   
   public void put(I id, K key, V value){
      content.put(id, new Pair<K,V>(key,value));
   }
   
   public K getKey(I id){
      Pair<K,V> tmp = content.get(id);
      if(tmp == null)
         return null;
      else
         return tmp.first;
   }
   
   public V getValue(I id){
      Pair<K,V> tmp = content.get(id);
      if(tmp == null)
         return null;
      else
         return tmp.second;
   }
   
   public Pair<K,V> getPair(I id){
      return content.get(id);
   }
   
   public Collection<Pair<K,V>> getPairs(){
      return content.values();
   }
   
   public Collection<I> getIDs(){
      return content.keySet();
   }
   
   /**
    * Gets all IDs which have the same Key or Value
    * @param e selection to compare if e=0 Key will be compared, if e=1 the Value will be compared 
    * @param arg the argument which will be compared
    * @return all IDs which match to the compared argument
    */
   public Collection<I> getIDs(int e, Object arg)
   {
      Collection<Map.Entry<I, Pair<K, V>>> entries = content.entrySet();
      HashSet<I> ids = new HashSet<>();
      switch (e)
      {
      case 0:
         for (Map.Entry<I, Pair<K, V>> entry : entries)
         {
            if (entry.getValue().first.equals(arg))
               ids.add(entry.getKey());
         }
         break;
      case 1:
         for (Map.Entry<I, Pair<K, V>> entry : entries)
         {
            if (entry.getValue().second.equals(arg))
               ids.add(entry.getKey());
         }
         break;
      default:
         return null;
      }
      return ids;
   }
   
   public Collection<V> getValues(K key){
      Collection<Pair<K,V>> pairs = getPairs();
      HashSet<V> values = new HashSet<>();
      for(Pair<K,V> pair : pairs){
         if(pair.first.equals(key))
            values.add(pair.second);
      }      
      return values;
   }
   
   
   public Collection<K> getKeys(V value){
      Collection<Pair<K,V>> pairs = getPairs();
      HashSet<K> keys = new HashSet<>();
      for(Pair<K,V> pair : pairs){
         if(pair.second.equals(value))
            keys.add(pair.first);
      }      
      return keys;
   }
   
   public String toString(){
	   return content.toString();
   }


}
