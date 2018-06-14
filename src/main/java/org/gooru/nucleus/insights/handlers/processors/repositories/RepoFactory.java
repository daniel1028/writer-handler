package org.gooru.nucleus.insights.handlers.processors.repositories;

import org.gooru.nucleus.insights.handlers.processors.messageProcessor.MessageContext;
import org.gooru.nucleus.insights.handlers.processors.repositories.cassandra.CassandraRepoFactory;
import org.gooru.nucleus.insights.handlers.processors.repositories.cassandra.EventTimeLineRepo;

public final class RepoFactory {
	
    public static EventTimeLineRepo getEventRepo(MessageContext messageContext) {
        return CassandraRepoFactory.getEventTimeLineRepo(messageContext);
    }

    private RepoFactory() {
        throw new AssertionError();
    }
}
