package org.moflon.tgg.algorithm.datastructures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class CollectionProvider {

	public static boolean useHashSets = false;

	public static <M> Collection<M> getCollection() {
		if (useHashSets)
			return new HashSet<M>();
		return new ArrayList<M>();
	}

	public static <M> Collection<M> getCollection(int size) {
		if (useHashSets)
			return new HashSet<M>(size);
		return new ArrayList<M>(size);
	}

}
