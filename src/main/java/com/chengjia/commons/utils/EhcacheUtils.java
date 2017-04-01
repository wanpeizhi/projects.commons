package com.chengjia.commons.utils;

import java.net.URL;

import com.chengjia.commons.common.Constants;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.store.LruPolicy;

public class EhcacheUtils {
	private static final String path = "/ehcache.xml";

	private static URL url;

	private static CacheManager manager;

	static {
		url = EhcacheUtils.class.getResource(path);
		manager = CacheManager.create(url);
	}

	public static void put(String cacheName, String key, Object value) {
		Cache cache = manager.getCache(cacheName);
		Element element = new Element(key, value);
		cache.put(element);
	}

	public static Object get(String cacheName, String key) {
		Cache cache = manager.getCache(cacheName);
		Element element = cache.get(key);
		return element == null ? null : element.getObjectValue();
	}

	public static Cache get(String cacheName) {
		Cache cache = manager.getCache(cacheName);
		if (cache == null) {
			cache = new Cache(cacheName, Constants.CACHE_DEFAULT_MAXELEMENTSINMEMORY,
					Constants.CACHE_DEFAULT_DISKPERSISTENT, Constants.CACHE_DEFAULT_ETERNAL,
					Constants.CACHE_DEFAULT_TIMETOLIVESECONDS, Constants.CACHE_DEFAULT_TIMETOIDLESECONDS);
			cache.setMemoryStoreEvictionPolicy(new LruPolicy());
			manager.addCache(cache);
		}
		return cache;
	}

	public static void remove(String cacheName, String key) {
		Cache cache = manager.getCache(cacheName);
		cache.remove(key);
	}

	/**
	 * @MethodName : setCacheKey
	 * @Description : 获得cache key的方法，cache key是Cache中一个Element的唯一标识 cache key包括
	 *              包名+类名+方法名+各个参数的具体指，如com.co.cache.service.UserServiceImpl.getAllUser
	 * @param targetName
	 *            类名
	 * @param methodName
	 *            方法名
	 * @param arguments
	 *            方法实参数组
	 * @return cachekey
	 */
	public static String setCacheKey(String targetName, String methodName, Object[] arguments) {
		StringBuffer sb = new StringBuffer();
		sb.append(targetName).append(".").append(methodName).append(JsonUtils.toJson(arguments));
		return sb.toString();
	}

}
