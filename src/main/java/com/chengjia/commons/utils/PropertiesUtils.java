package com.chengjia.commons.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.chengjia.commons.exception.CustomException;

public class PropertiesUtils {

	public static void setProperty(String filePath, String fileName, String propertyName, String propertyValue)
			throws CustomException {
		try {
			Properties p = loadProperty(filePath, fileName);
			p.setProperty(propertyName, propertyValue);
			String comment = "Update '" + propertyName + "' value";
			storeProperty(filePath, fileName, p, comment);
		} catch (Exception e) {
			throw new CustomException("属性设置失败!" + e.getMessage(), e);
		}
	}

	public static void setProperty(String filePath, String fileName, String[] propertyArray) throws CustomException {
		if ((propertyArray == null) || (propertyArray.length % 2 != 0))
			throw new CustomException("make sure 'propertyArray' argument is 'ket/value' pairs");
		try {
			Properties p = loadProperty(filePath, fileName);
			for (int i = 0; i < propertyArray.length / 2; i++) {
				p.setProperty(propertyArray[(i * 2)], propertyArray[(i * 2 + 1)]);
			}
			String comment = "Update '" + propertyArray[0] + "..." + "' value";
			storeProperty(filePath, fileName, p, comment);
		} catch (Exception e) {
			throw new CustomException("属性设置失败!" + e.getMessage(), e);
		}
	}

	public static void setProperty(String filePath, String fileName, String propertyName,
			List<String> propertyValueList) throws CustomException {
		try {
			Properties p = loadProperty(filePath, fileName);
			StringBuilder propertyValue = new StringBuilder();
			if ((propertyValueList != null) && (propertyValueList.size() > 0)) {
				for (String value : propertyValueList) {
					propertyValue.append(
							value.replaceAll("(\\\\)+$", "").replaceAll("\\\\", "\\\\\\\\").replaceAll(";", "\\\\;")
									+ ";");
				}
			}
			p.setProperty(propertyName, propertyValue.toString());
			String comment = "Update '" + propertyName + "' value";
			storeProperty(filePath, fileName, p, comment);
		} catch (Exception e) {
			throw new CustomException("属性设置失败!" + e.getMessage(), e);
		}
	}

	public static void setProperty(String filePath, String fileName, Map<String, String> propertyMap)
			throws CustomException {
		try {
			Properties p = loadProperty(filePath, fileName);
			for (String name : propertyMap.keySet()) {
				p.setProperty(name, (String) propertyMap.get(name));
			}
			String comment = "Update '" + propertyMap.keySet().toString() + "' value";
			storeProperty(filePath, fileName, p, comment);
		} catch (Exception e) {
			throw new CustomException("属性移除失败!" + e.getMessage(), e);
		}
	}

	public static void setProperty(String filePath, String fileName, String propertyName,
			Map<String, String> propertyValueMap) throws CustomException {
		try {
			Properties p = loadProperty(filePath, fileName);
			StringBuilder propertyValue = new StringBuilder();
			if ((propertyValueMap != null) && (propertyValueMap.size() > 0)) {
				for (String key : propertyValueMap.keySet()) {
					propertyValue.append(key.replaceAll("\\\\", "\\\\\\\\").replaceAll("(\\\\)+$", "")
							.replaceAll("\\,", "\\\\,").replaceAll(";", "\\\\;")
							+ ","
							+ ((String) propertyValueMap.get(key)).replaceAll("(\\\\)+$", "")
									.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\,", "\\\\,").replaceAll(";", "\\\\;")
							+ ";");
				}
			}
			p.setProperty(propertyName, propertyValue.toString());
			String comment = "Update '" + propertyName + "' value";
			storeProperty(filePath, fileName, p, comment);
		} catch (Exception e) {
			throw new CustomException("属性设置失败!" + e.getMessage(), e);
		}
	}

	public static String getProperty(String filePath, String fileName, String propertyName) throws CustomException {
		try {
			Properties p = loadProperty(filePath, fileName);
			return p.getProperty(propertyName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getProperty(String filePath, String fileName, String propertyName, String defaultValue)
			throws CustomException {
		try {
			Properties p = loadProperty(filePath, fileName);
			return p.getProperty(propertyName, defaultValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean getProperty(String filePath, String fileName, Map<String, String> propertyMap)
			throws CustomException {
		try {
			Properties p = loadProperty(filePath, fileName);
			for (String name : propertyMap.keySet()) {
				propertyMap.put(name, p.getProperty(name, (String) propertyMap.get(name)));
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static List<String> getPropertyList(String filePath, String fileName, String propertyName,
			String defaultValue) throws CustomException {
		try {
			Properties p = loadProperty(filePath, fileName);
			String v = p.getProperty(propertyName, defaultValue);
			String[] iA = v.split("(?<!\\\\);");
			for (int i = 0; i < iA.length; i++) {
				iA[i] = iA[i].replaceAll("(\\\\)+$", "").replaceAll("\\\\;", ";").replaceAll("\\\\\\\\", "\\\\");
			}
			return Arrays.asList(iA);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, String> getPropertyMap(String filePath, String fileName, String propertyName,
			String defaultValue) throws CustomException {
		try {
			Properties p = loadProperty(filePath, fileName);
			String v = p.getProperty(propertyName, defaultValue);

			Map<String, String> retMap = new HashMap<String, String>();
			String[] iA = v.split("(?<!\\\\);");
			for (String i : iA) {
				String[] jA = i.split("(?<!\\\\),");
				if (jA.length == 2) {
					retMap.put(
							jA[0].replaceAll("(\\\\)+$", "").replaceAll("\\\\\\,", "\\,").replaceAll("\\\\;", ";")
									.replaceAll("\\\\\\\\", "\\\\"),
							jA[1].replaceAll("(\\\\)+$", "").replaceAll("\\\\\\,", "\\,").replaceAll("\\\\;", ";")
									.replaceAll("\\\\\\\\", "\\\\"));
				}
			}
			return retMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void appendProperty(String filePath, String fileName, String propertyName,
			List<String> propertyValueList) throws CustomException {
		try {
			Properties p = loadProperty(filePath, fileName);
			StringBuilder propertyValue = new StringBuilder();
			for (String value : propertyValueList) {
				propertyValue.append(
						value.replaceAll("(\\\\)+$", "").replaceAll("\\\\", "\\\\\\\\").replaceAll(";", "\\\\;") + ";");
			}
			p.setProperty(propertyName, p.getProperty(propertyName) + propertyValue.substring(1));
			String comment = "Update '" + propertyName + "' value";
			storeProperty(filePath, fileName, p, comment);
		} catch (Exception e) {
			throw new CustomException("属性追加失败!" + e.getMessage(), e);
		}
	}

	public static void appendProperty(String filePath, String fileName, String propertyName,
			Map<String, String> propertyValueMap) throws CustomException {
		try {
			Map<String, String> combinePropertyValueMap = getPropertyMap(filePath, fileName, propertyName, "");
			combinePropertyValueMap.putAll(propertyValueMap);
			setProperty(filePath, fileName, propertyName, combinePropertyValueMap);
		} catch (Exception e) {
			throw new CustomException("属性追加失败!" + e.getMessage(), e);
		}
	}

	public static void appendProperty(String filePath, String fileName, String propertyName, String propertyValue)
			throws CustomException {
		try {
			Properties p = loadProperty(filePath, fileName);
			p.setProperty(propertyName, p.getProperty(propertyName, "")
					+ propertyValue.replaceAll("(\\\\)+$", "").replaceAll("\\\\", "\\\\\\\\").replaceAll(";", "\\\\;")
					+ ";");
			String comment = "Update '" + propertyName + "' value";
			storeProperty(filePath, fileName, p, comment);
		} catch (Exception e) {
			throw new CustomException("属性追加失败!" + e.getMessage(), e);
		}
	}

	public static void appendProperty(String filePath, String fileName, String propertyName, String propertyKey,
			String propertyValue) throws CustomException {
		try {
			Properties p = loadProperty(filePath, fileName);
			p.setProperty(propertyName,
					p.getProperty(propertyName, "")
							+ propertyKey.replaceAll("(\\\\)+$", "").replaceAll("\\\\", "\\\\\\\\")
									.replaceAll("\\,", "\\\\,").replaceAll(";", "\\\\;")
							+ "," + propertyValue.replaceAll("(\\\\)+$", "").replaceAll("\\\\", "\\\\\\\\")
									.replaceAll("\\,", "\\\\,").replaceAll(";", "\\\\;")
							+ ";");
			String comment = "Update '" + propertyName + "." + propertyKey + "' value";
			storeProperty(filePath, fileName, p, comment);
		} catch (Exception e) {
			throw new CustomException("属性追加失败!" + e.getMessage(), e);
		}
	}

	public static Properties loadProperty(String filePath, String fileName) throws CustomException {
		try {
			File d = new File(filePath);
			if (!d.exists()) {
				d.mkdirs();
			}
			File f = new File(d, fileName);
			if (!f.exists()) {
				f.createNewFile();
			}
			Properties p = new Properties();
			InputStream is = new FileInputStream(f);
			p.load(is);
			is.close();
			return p;
		} catch (Exception e) {
			throw new CustomException("属性读取失败!" + e.getMessage(), e);
		}
	}

	public static void storeProperty(String filePath, String fileName, Properties p, String comment)
			throws CustomException {
		try {
			File d = new File(filePath);
			if (!d.exists()) {
				d.mkdirs();
			}
			File f = new File(d, fileName);
			if (!f.exists()) {
				f.createNewFile();
			}
			OutputStream os = new FileOutputStream(f);
			p.store(os, comment);
			os.close();
		} catch (Exception e) {
			throw new CustomException("属性存储失败!" + e.getMessage(), e);
		}
	}

	public static void clearProperty(String filePath, String fileName, String propertyName) throws CustomException {
		try {
			Properties p = loadProperty(filePath, fileName);
			p.setProperty(propertyName, "");
			String comment = propertyName;
			storeProperty(filePath, fileName, p, comment);
		} catch (Exception e) {
			throw new CustomException("属性清除失败!" + e.getMessage(), e);
		}
	}

	public static void removeProperty(String filePath, String fileName, String propertyName) throws CustomException {
		try {
			Properties p = loadProperty(filePath, fileName);
			p.remove(propertyName);
			String comment = propertyName;
			storeProperty(filePath, fileName, p, comment);
		} catch (Exception e) {
			throw new CustomException("属性移除失败!" + e.getMessage(), e);
		}
	}
}
