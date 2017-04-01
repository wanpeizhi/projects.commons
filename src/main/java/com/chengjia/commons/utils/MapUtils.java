package com.chengjia.commons.utils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

public class MapUtils {

	/**
	 * 实现对map按照value升序排序
	 * 
	 * @param h
	 * @return
	 */
	public static Map.Entry[] getSortedHashtableByValue(Map h) {
		Set set = h.entrySet();
		Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set.size()]);
		Arrays.sort(entries, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				Double key1 = Double.valueOf(((Map.Entry) arg0).getValue().toString());
				Double key2 = Double.valueOf(((Map.Entry) arg1).getValue().toString());
				return key1.compareTo(key2);
			}
		});
		return entries;
	}

	/**
	 * 实现对map按照key升序排序
	 * 
	 * @param h
	 * @return
	 */
	public static Map.Entry[] getSortedHashtableByKey(Map h) {
		Set set = h.entrySet();
		Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set.size()]);
		Arrays.sort(entries, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				Object key1 = ((Map.Entry) arg0).getKey();
				Object key2 = ((Map.Entry) arg1).getKey();
				return ((Comparable) key1).compareTo(key2);
			}
		});
		return entries;
	}

}
