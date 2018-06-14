package org.gooru.nucleus.insights.handlers.processors.messageProcessor;

import org.gooru.nucleus.insights.handlers.constants.CommandConstants;
import org.gooru.nucleus.insights.handlers.processors.command.executor.MessageResponse;
import org.gooru.nucleus.insights.handlers.processors.exceptions.InvalidRequestException;
import org.gooru.nucleus.insights.handlers.processors.repositories.RepoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class EventMessageProcessor implements MessageProcessorHandler {

	private static final Logger LOG = LoggerFactory.getLogger(EventMessageProcessor.class);

	@Override
	public MessageResponse process(MessageContext messageContext) {
		MessageResponse result = null;
		switch (messageContext.command()) {
    case CommandConstants.CREATE_EVENT:
      result = RepoFactory.getEventRepo(messageContext).createEvent();
      break;
    case CommandConstants.UPDATE_EVENT:
      //Don't use for now. It will be useful in future
      result = RepoFactory.getEventRepo(messageContext).updateEvent();
      break;
    case CommandConstants.DELETE_EVENT:
      //Don't use for now. It will be useful in future
      result = RepoFactory.getEventRepo(messageContext).deleteEvent();
      break;
    case CommandConstants.FIND_EVENT:
      //Don't use for now. It will be useful in future
      result = RepoFactory.getEventRepo(messageContext).findEvent();
      break;
    default:
      LOG.error("Invalid command type passed in, not able to handle");
      throw new InvalidRequestException();
    }
		return result;
	}

}
