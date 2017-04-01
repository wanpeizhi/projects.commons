package com.chengjia.commons.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.collections4.map.HashedMap;

import com.chengjia.commons.exception.JsonException;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtils {
	private static final ObjectMapper objectMapper;

	/**
	 * 静态模块初始化加载
	 */
	static {
		objectMapper = new ObjectMapper();
		// 去掉默认的时间戳格式
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		// 设置为中国上海时区
		objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		// 空值不序列化
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		// 反序列化时，属性不存在的兼容处理
		objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		// 序列化时，日期的统一格式
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 单引号处理
		objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		// 设置字段可以不用双引号包括
		objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
	}

	/**
	 * 字符串转对象
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 * @throws JsonException
	 */
	public static <T> T toObject(String json, Class<T> clazz) throws JsonException {
		try {
			return objectMapper.readValue(json, clazz);
		} catch (JsonParseException e) {
			throw new JsonException(e.getMessage(), e);
		} catch (JsonMappingException e) {
			throw new JsonException(e.getMessage(), e);
		} catch (IOException e) {
			throw new JsonException(e.getMessage(), e);
		}
	}

	/**
	 * 对象转字符串
	 * 
	 * @param entity
	 * @return
	 * @throws JsonException
	 */
	public static <T> String toJson(T entity) throws JsonException {
		try {
			return objectMapper.writeValueAsString(entity);
		} catch (JsonGenerationException e) {
			throw new JsonException(e.getMessage(), e);
		} catch (JsonMappingException e) {
			throw new JsonException(e.getMessage(), e);
		} catch (IOException e) {
			throw new JsonException(e.getMessage(), e);
		}
	}

	/**
	 * json字符串转集合
	 * 
	 * @param json
	 * @param typeReference
	 * @return
	 */
	public static <T> T toCollection(String json, TypeReference<T> typeReference) throws JsonException {
		try {
			return objectMapper.readValue(json, typeReference);
		} catch (JsonParseException e) {
			throw new JsonException(e.getMessage(), e);
		} catch (JsonMappingException e) {
			throw new JsonException(e.getMessage(), e);
		} catch (IOException e) {
			throw new JsonException(e.getMessage(), e);
		}
	}
	
}
