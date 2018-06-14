package org.gooru.nucleus.insights.handlers.processors.command.executor;

public interface DBExecutor {
    void checkSanity();

    void validateRequest();

    MessageResponse executeRequest();

    boolean handlerReadOnly();
}
