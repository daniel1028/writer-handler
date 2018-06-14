package org.gooru.nucleus.insights.handlers.processors.messageProcessor;

import org.gooru.nucleus.insights.handlers.processors.command.executor.MessageResponse;

public interface Processor {
    MessageResponse process();
}
