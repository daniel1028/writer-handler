package org.gooru.nucleus.insights.handlers.processors.repositories.cassandra;

import org.gooru.nucleus.insights.handlers.processors.messageProcessor.MessageContext;

public final class CassandraRepoFactory {

	public static EventTimeLineRepo getEventTimeLineRepo(MessageContext messageContext) {
		return new CassandraEventTimeLineRepo(messageContext);
	}
	
	private CassandraRepoFactory() {
		throw new AssertionError();
	}
}
