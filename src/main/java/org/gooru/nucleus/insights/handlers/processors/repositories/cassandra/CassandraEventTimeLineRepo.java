package org.gooru.nucleus.insights.handlers.processors.repositories.cassandra;

import org.gooru.nucleus.insights.handlers.processors.command.executor.MessageResponse;
import org.gooru.nucleus.insights.handlers.processors.command.executor.event.EventExecutorFactory;
import org.gooru.nucleus.insights.handlers.processors.messageProcessor.MessageContext;
import org.gooru.nucleus.insights.handlers.processors.repositories.cassandra.transactions.CassandraTransactionExecutor;

public class CassandraEventTimeLineRepo implements EventTimeLineRepo {
	private final MessageContext messageContext;

	public CassandraEventTimeLineRepo(MessageContext messageContext) {
		this.messageContext = messageContext;
	}

	@Override
	public MessageResponse createEvent() {
		return CassandraTransactionExecutor.executeTransaction(EventExecutorFactory.createEvent(messageContext));
	}

	@Override
	public MessageResponse updateEvent() {
		return CassandraTransactionExecutor.executeTransaction(EventExecutorFactory.updateEvent(messageContext));
	}

	@Override
	public MessageResponse findEvent() {
		return CassandraTransactionExecutor.executeTransaction(EventExecutorFactory.findEvent(messageContext));
	}

	@Override
	public MessageResponse deleteEvent() {
		return CassandraTransactionExecutor.executeTransaction(EventExecutorFactory.deleteEvent(messageContext));
	}
}
