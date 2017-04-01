package com.chengjia.commons.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chengjia.commons.exception.JsonException;
import com.chengjia.commons.utils.JsonUtils;

public class ResponseUtils {
	private static Logger logger = LogManager.getLogger(ResponseUtils.class.getName());

	public String responseToJson(HttpServletResponse response, Object object) {
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(JsonUtils.toJson(object));
			out.flush();
		} catch (IOException e) {
			logger.error(e);
		} catch (JsonException e) {
			logger.error("json对象转换错误!" + e.getMessage(), e);
		}
		return null;
	}

}
