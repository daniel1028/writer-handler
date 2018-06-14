package org.gooru.nucleus.insights.handlers.processors.repositories.cassandra;

import org.gooru.nucleus.insights.handlers.processors.command.executor.MessageResponse;

public interface EventTimeLineRepo {

	MessageResponse createEvent();

	MessageResponse updateEvent();

	MessageResponse findEvent();

	MessageResponse deleteEvent();

}
