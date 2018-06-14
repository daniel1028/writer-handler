package org.gooru.nucleus.insights.handlers.processors.command.executor.event;

import org.gooru.nucleus.insights.handlers.processors.command.executor.DBExecutor;
import org.gooru.nucleus.insights.handlers.processors.command.executor.MessageResponse;
import org.gooru.nucleus.insights.handlers.processors.messageProcessor.MessageContext;
import org.gooru.nucleus.insights.handlers.processors.repositories.cassandra.model.Event;
import org.gooru.nucleus.insights.handlers.processors.repositories.cassandra.model.EventTimeLine;

import io.vertx.core.json.JsonObject;

public final class FindEventExecutor implements DBExecutor {

	private MessageContext messageContext;
	private JsonObject data = null ;
	
	public FindEventExecutor(MessageContext messageContext) {
		this.messageContext = messageContext;
	}

	@Override
	public void checkSanity() {
		data = messageContext.requestBody();

	}

	@Override
	public void validateRequest() {

	}

	@Override
	public MessageResponse executeRequest() {
		EventTimeLine eventTimeLine = new EventTimeLine(data);
		JsonObject result = Event.find("event_time", eventTimeLine.getData().getString("event_time"),data.getJsonObject("version").getString("logApi"));
		return new MessageResponse.Builder().setContentTypeJson().setStatusOkay().setResponseBody(result).successful().build();
	}

	@Override
	public boolean handlerReadOnly() {
		return false;
	}
}
