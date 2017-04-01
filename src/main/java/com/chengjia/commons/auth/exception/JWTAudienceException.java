package com.chengjia.commons.auth.exception;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;

public class JWTAudienceException extends JWTVerifyException {
	private static final long serialVersionUID = 1204992733569565707L;
	private JsonNode audienceNode;

	public JWTAudienceException(JsonNode audienceNode) {
		this.audienceNode = audienceNode;
	}

	public JWTAudienceException(String message, JsonNode audienceNode) {
		super(message);
		this.audienceNode = audienceNode;
	}

	public List<String> getAudience() {
		ArrayList<String> audience = new ArrayList<String>();
		if (audienceNode.isArray()) {
			for (JsonNode jsonNode : audienceNode) {
				audience.add(jsonNode.getTextValue());
			}
		} else if (audienceNode.isTextual()) {
			audience.add(audienceNode.getTextValue());
		}
		return audience;
	}

}
