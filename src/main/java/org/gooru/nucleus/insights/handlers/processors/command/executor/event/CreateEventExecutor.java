package org.gooru.nucleus.insights.handlers.processors.command.executor.event;

import org.gooru.nucleus.insights.handlers.processors.command.executor.DBExecutor;
import org.gooru.nucleus.insights.handlers.processors.command.executor.MessageResponse;
import org.gooru.nucleus.insights.handlers.processors.messageProcessor.MessageContext;
import org.gooru.nucleus.insights.handlers.processors.repositories.cassandra.model.Event;
import org.gooru.nucleus.insights.handlers.processors.repositories.cassandra.model.EventTimeLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.json.JsonObject;

public final class CreateEventExecutor implements DBExecutor {
  private static final Logger LOG = LoggerFactory.getLogger(CreateEventExecutor.class);

  private final MessageContext messageContext;

  private JsonObject data = null;
  JsonObject valuesObject = new JsonObject();

  public CreateEventExecutor(MessageContext messageContext) {
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
    LOG.info("Data : {}", data);
    EventTimeLine eventTimeLine = new EventTimeLine(data);
    eventTimeLine.save(data.getJsonObject("version").getString("logApi"));
    Event event = new Event(data);
    event.save(data.getJsonObject("version").getString("logApi"));
    return new MessageResponse.Builder().setContentTypeJson().setStatusCreated().successful().build();
  }

  @Override
  public boolean handlerReadOnly() {
    return false;
  }
}
