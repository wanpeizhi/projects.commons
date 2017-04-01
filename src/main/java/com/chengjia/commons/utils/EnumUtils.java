package com.chengjia.commons.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date Jan 16, 2017 10:58:15 AM
 */
public class EnumUtils {
	
	/**
	 * 根据类型获取枚举项目
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String,String>> getEnumString(Class<? extends Enum<?>> clazz) throws Exception{
		Method method = clazz.getMethod("values");
		Enum<?> [] enums = (Enum[]) method.invoke(null);
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for(int i=0;i<enums.length;i++){
			Map<String,String> map = new HashMap<String,String>();
			map.put("name", enums[i].toString());
			map.put("value", enums[i].name());
			list.add(map);
		}
		return list;
	}
}
