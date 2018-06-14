package org.gooru.nucleus.insights.handlers.processors.messageProcessor;

import io.vertx.core.json.JsonObject;

public interface MessageContext {

    JsonObject requestBody();
    
    String command();
}
