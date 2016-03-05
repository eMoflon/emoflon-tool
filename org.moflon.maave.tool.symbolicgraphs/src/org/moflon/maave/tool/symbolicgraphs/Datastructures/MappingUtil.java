package org.moflon.maave.tool.symbolicgraphs.Datastructures;

import java.util.Collection;
import java.util.HashSet;

public class MappingUtil {

	public static <T> boolean isInjective(Mapping<T> mapping) {
	   
		HashSet<T> imageSet=new HashSet<T>(mapping.getImage().size()+10,0.99f);
		for (T elem : mapping.getImage())
      {
         if(imageSet.add(elem)==false)
         {
            return false;
         }
      }
		return true;
	}
	public static <T> boolean isSurjective(Mapping<T>mapping, Collection<T> coDom)
	{
		HashSet<T> imageSet=new HashSet<T>(mapping.getImage());
		return imageSet.containsAll(coDom);
	}
	public static <T> boolean isBijective(Mapping<T> mapping,Collection<T> coDom) {
		HashSet<T> imageSet=new HashSet<T>(mapping.getImage());
		if(imageSet.size()==mapping.getImage().size()&&imageSet.containsAll(coDom)){
			return true;
		}
		return false;
	}
	public static <T> boolean isTotal(Mapping<T> mapping, Collection<T> dom)
   {
      return mapping.map.keySet().containsAll(dom);
   }
}
