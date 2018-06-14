package org.gooru.nucleus.insights.handlers.processors.messageProcessor;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.gooru.nucleus.insights.handlers.constants.CommandConstants;
import org.gooru.nucleus.insights.handlers.constants.ConfigConstants;
import org.gooru.nucleus.insights.handlers.constants.MessageConstants;
import org.gooru.nucleus.insights.handlers.processors.ProcessorBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.util.internal.StringUtil;
import io.vertx.core.json.JsonObject;

public class KafkaMessageConsumer implements Runnable {

  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaMessageConsumer.class);
  private KafkaConsumer<String, String> consumer = null;

  public KafkaMessageConsumer(KafkaConsumer<String, String> consumer) {
    this.consumer = consumer;

  }

  @Override
  public void run() {
    while (true) {
      ConsumerRecords<String, String> records = consumer.poll(200);
      for (ConsumerRecord<String, String> record : records) {
        switch (record.topic().split(ConfigConstants.HYPHEN)[0]) {
        case ConfigConstants.KAFKA_EVENTLOGS_TOPIC:
          sendMessage(record.value());
          break;
        case ConfigConstants.KAFKA_TEST_TOPIC:
          LOGGER.info("Test Kafka Consumer : {}", record.value());
          break;
        default:
          // FIXME: Revisit this logic.
          LOGGER.warn("Assume that message is coming from unknown topic. Send to handlers anyway");
          sendMessage(record.value());
        }
      }
    }
  }

  // Send messages to handlers via event bus
  private void sendMessage(String record) {
    if (!StringUtil.isNullOrEmpty(record)) {
      JsonObject eventObject = null;
      JsonObject message = null;
      try {
        eventObject = new JsonObject(record);
        message = new JsonObject();
        message.put(MessageConstants.MSG_HTTP_BODY, eventObject);
        message.put(MessageConstants.MSG_HEADER_OP, CommandConstants.CREATE_EVENT);
      } catch (Exception e) {
        LOGGER.warn("Kafka Message should be JsonObject");
      }
      if (eventObject != null) {
        LOGGER.info("RECEIVED EVENT OBJECT IN RAW WRITER:::: {}", eventObject);
        new ProcessorBuilder(ProcessorHandlerType.EVENT, message).build().process();
      }
    } else {
      LOGGER.warn("NULL or Empty message can not be processed...");
    }
  }
}
