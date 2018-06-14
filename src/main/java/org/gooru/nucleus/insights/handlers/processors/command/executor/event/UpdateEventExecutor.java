package org.gooru.nucleus.insights.handlers.processors.command.executor.event;

import org.gooru.nucleus.insights.handlers.processors.command.executor.DBExecutor;
import org.gooru.nucleus.insights.handlers.processors.command.executor.MessageResponse;
import org.gooru.nucleus.insights.handlers.processors.messageProcessor.MessageContext;
import org.gooru.nucleus.insights.handlers.processors.repositories.cassandra.model.EventTimeLine;

import io.vertx.core.json.JsonObject;

public final class UpdateEventExecutor implements DBExecutor {

    private final MessageContext messageContext;
    private JsonObject data = null;

    public UpdateEventExecutor(MessageContext messageContext) {
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
    	eventTimeLine.update("event_time", messageContext.requestBody().getString("event_time"),data.getJsonObject("version").getString("logApi"));
    	return new MessageResponse.Builder().setContentTypeJson().setStatusNoOutput().successful().build();
    }

    @Override
    public boolean handlerReadOnly() {
        return true;
    }

}
