package org.gooru.nucleus.insights.handlers.processors;

import org.gooru.nucleus.insights.handlers.processors.messageProcessor.MessageProcessFactory;
import org.gooru.nucleus.insights.handlers.processors.messageProcessor.MessageProcessor;
import org.gooru.nucleus.insights.handlers.processors.messageProcessor.Processor;
import org.gooru.nucleus.insights.handlers.processors.messageProcessor.ProcessorHandlerType;

import io.vertx.core.json.JsonObject;

public class ProcessorBuilder {

  private final JsonObject event;

  private final ProcessorHandlerType handlerType;

  public ProcessorBuilder(ProcessorHandlerType handlerType, JsonObject event) {
    this.event = event;
    this.handlerType = handlerType;
  }

  public Processor build() {
    return new MessageProcessor(MessageProcessFactory.getInstance(handlerType), event);
  }

}
