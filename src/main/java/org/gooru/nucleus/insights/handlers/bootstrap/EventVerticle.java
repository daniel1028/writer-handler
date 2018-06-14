package org.gooru.nucleus.insights.handlers.bootstrap;

import org.gooru.nucleus.insights.handlers.constants.CommandConstants;
import org.gooru.nucleus.insights.handlers.constants.MessageConstants;
import org.gooru.nucleus.insights.handlers.constants.MessagebusEndpoints;
import org.gooru.nucleus.insights.handlers.processors.ProcessorBuilder;
import org.gooru.nucleus.insights.handlers.processors.command.executor.MessageResponse;
import org.gooru.nucleus.insights.handlers.processors.messageProcessor.ProcessorHandlerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

public class EventVerticle extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(EventVerticle.class);

  @Override
  public void start(Future<Void> voidFuture) throws Exception {
    final EventBus eb = vertx.eventBus();
    eb.consumer(MessagebusEndpoints.MBEP_EVENT, message -> {
      LOG.debug("Received message: " + message.body());
      vertx.executeBlocking(future -> {
        MessageResponse result =
                new ProcessorBuilder(ProcessorHandlerType.EVENT, new JsonObject().put(MessageConstants.MSG_HEADER_OP, CommandConstants.CREATE_EVENT)
                        .put(MessageConstants.MSG_HTTP_BODY, new JsonObject(message.body().toString()))).build().process();
        future.complete(result);
      }, res -> {
        MessageResponse result = (MessageResponse) res.result();
        message.reply(result.reply(), result.deliveryOptions());
      });

    }).completionHandler(result -> {

      if (result.succeeded()) {
        LOG.info("Events end point ready to listen");
        voidFuture.complete();
      } else {
        LOG.error("Error registering the Events handler. Halting the authentication machinery");
        voidFuture.fail(result.cause());
      }
    });
  }

}
