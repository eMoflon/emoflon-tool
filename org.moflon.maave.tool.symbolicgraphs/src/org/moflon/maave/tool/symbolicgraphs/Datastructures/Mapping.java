package org.moflon.maave.tool.symbolicgraphs.Datastructures;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class Mapping<T> {
   protected HashMap<T,T> map;


   protected Mapping(HashMap<T,T> hashmap){
      map=new HashMap<T, T>(hashmap);
      
   }
   public Mapping(int initialSize){
      this.map=new HashMap<T, T>(initialSize);
   }
   public Mapping(int initialSize,float factor){
      this.map=new HashMap<T, T>(initialSize,factor);
   }
   public Mapping()
   {
      this.map = new HashMap<T,T>(30);
   }
   public Mapping(Mapping<T> map)
   {
      this(map.map);
   }
   public T imageOf(T t) {

      return map.get(t);
   }
   public int size(){
	   return map.size();
	   
   }
   public Set<Entry<T, T>> getEntrySet(){
      return map.entrySet();
   }
   public T removeMapping(T preImage){
	   return map.remove(preImage);
   }
   public Mapping<T> getInverseMapping(){

      HashMap<T, T> reverseMap=new HashMap<T,T>(map.size());
      for (Entry<T,T> entry : map.entrySet())
      {
         reverseMap.put(entry.getValue(), entry.getKey());
      }
      return new Mapping<T>(reverseMap);
   }

   public void addMapping(T preImage , T image){
      
//      System.out.println("addMapping Size"+map.size()/16d);
//      long x=System.nanoTime();
      map.put(preImage, image);
//      System.out.println(System.nanoTime()-x);
   }
   public Collection<T> getImage(){
      return map.values();
   }

   public boolean isInImage (T element){
      return map.containsValue(element);
   }

   public void addAll(Mapping<? extends T> mapping){
      map.putAll(mapping.map);
   }
   public Mapping<T> compose(Mapping<T> after) {
      Mapping<T> newMap =  new Mapping<T>();

      for (T elem : map.keySet()) {
         T composedImage=after.imageOf(this.imageOf(elem));
         if(composedImage!=null){
            newMap.addMapping(elem,composedImage );
         }
      }
      return newMap;
   }
   public Set<T> getPreimage(){
      return map.keySet();
   }

   @Override
   public String toString() {
      StringBuilder stringBuilder=new StringBuilder();
      map.entrySet().forEach(entry->stringBuilder.append("["+entry.getKey().toString()+","+entry.getValue().toString()+"] \n"));
      return stringBuilder.toString();
   }
}
