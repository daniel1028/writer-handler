package org.gooru.nucleus.insights.handlers.processors.messageProcessor;

import org.gooru.nucleus.insights.handlers.processors.command.executor.MessageResponse;
import org.gooru.nucleus.insights.handlers.processors.exceptions.InvalidRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.json.JsonObject;

public class MessageProcessor implements Processor {

    private static final Logger LOG = LoggerFactory.getLogger(MessageProcessorHandler.class);

    private final JsonObject message;

    private final MessageProcessorHandler handler;

    public MessageProcessor(MessageProcessorHandler handler, JsonObject message) {
        this.message = message;
        this.handler = handler;
    }

    @Override
    public MessageResponse process() {
        MessageResponse result = null;
        try {
            if (message == null || message.isEmpty()) {
                LOG.error("Invalid message received, either null or body of message is not JsonObject ");
                throw new InvalidRequestException();
            }
            MessageContext messageContext = new MessageContextHolder(message);
            result = handler.process(messageContext);
            
            return result;
        } catch (Throwable throwable) {
            LOG.warn("Caught unexpected exception here", throwable);
            return new MessageResponse.Builder().setThrowable(throwable).build();
        }
    }

}
