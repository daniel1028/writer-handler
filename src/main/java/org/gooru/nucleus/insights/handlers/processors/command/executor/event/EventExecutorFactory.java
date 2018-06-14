package org.gooru.nucleus.insights.handlers.processors.command.executor.event;

import org.gooru.nucleus.insights.handlers.processors.command.executor.DBExecutor;
import org.gooru.nucleus.insights.handlers.processors.messageProcessor.MessageContext;

public final class EventExecutorFactory {

	public static DBExecutor createEvent(MessageContext messageContext) {
		return new CreateEventExecutor(messageContext);
	}
	
	public static DBExecutor updateEvent(MessageContext messageContext) {
		return new UpdateEventExecutor(messageContext);
	}

	public static DBExecutor findEvent(MessageContext messageContext) {
		return new FindEventExecutor(messageContext);
	}
	
	
	public static DBExecutor deleteEvent(MessageContext messageContext) {
		return new DeleteEventExecutor(messageContext);
	}

	private EventExecutorFactory() {
		throw new AssertionError();
	}
}
