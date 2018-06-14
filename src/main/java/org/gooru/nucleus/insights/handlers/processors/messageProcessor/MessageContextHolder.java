package org.gooru.nucleus.insights.handlers.processors.messageProcessor;

import org.gooru.nucleus.insights.handlers.constants.MessageConstants;

import io.vertx.core.json.JsonObject;

public class MessageContextHolder implements MessageContext {

	private final JsonObject data;

	public MessageContextHolder(JsonObject data) {
		this.data = data;
	}

	@Override
	public JsonObject requestBody() {
		return data.getJsonObject(MessageConstants.MSG_HTTP_BODY);
	}

	@Override
	public String command() {
		return data.getString(MessageConstants.MSG_HEADER_OP);
	}

}
