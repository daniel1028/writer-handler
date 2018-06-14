package org.gooru.nucleus.insights.handlers.processors.command.executor.event;

import org.gooru.nucleus.insights.handlers.processors.command.executor.DBExecutor;
import org.gooru.nucleus.insights.handlers.processors.command.executor.MessageResponse;
import org.gooru.nucleus.insights.handlers.processors.messageProcessor.MessageContext;
import org.gooru.nucleus.insights.handlers.processors.repositories.cassandra.model.EventTimeLine;

import io.vertx.core.json.JsonObject;

public final class DeleteEventExecutor implements DBExecutor {

    private MessageContext messageContext;
	private JsonObject data = null ;

    public DeleteEventExecutor(MessageContext messageContext) {
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
      EventTimeLine.delete("event_time",eventTimeLine.getData().getString("startTime"),data.getJsonObject("version").getString("logApi") );
        return new MessageResponse.Builder().setContentTypeJson().setStatusNoOutput().successful().build();
    }

    @Override
    public boolean handlerReadOnly() {
        return true;
    }
}
