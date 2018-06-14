package org.gooru.nucleus.insights.handlers.constants;

/**
 * Constant definition that are used to read configuration
 */
final public class ConfigConstants {

    public static final String PORT = "port";
    public static final String HOST = "host";
    public static final String VERTICLES_DEPLOY_LIST = "verticles.deploy.list";

    //Kafka Config Constants
    public static final String CONFIG_KAFKA_CONSUMER = "defaultKafkaConsumerSettings";
    public static final String CONFIG_KAFKA_SERVERS = "bootstrap.servers";
    public static final String CONFIG_KAFKA_GROUP = "group.id";
    public static final String CONFIG_KAFKA_TOPICS = "consumer.topics";
    public static final String CONFIG_KAFKA_TIME_OUT_IN_MS = "session.timeout.ms";
    public static final String CONFIG_KAFKA_KEY_DESERIALIZER = "key.deserializer";
    public static final String CONFIG_KAFKA_VALUE_DESERIALIZER = "value.deserializer";
    //Expected Kafka Topics
    public static final String KAFKA_EVENTLOGS_TOPIC = "EVENTLOGS";
    public static final String KAFKA_TEST_TOPIC = "test";
    public static final String KAFKA_XAPITRANSFORMER_TOPIC = "XAPITRANSFORMER";

    public static final String THREAD_POOL_SIZE ="thead.pool.size";

    public static final String HYPHEN = "-";
    public static final String COMMA = ",";
    public static final String COLON = ":";
 
    public static final String EVENT_NAME = "eventName";
    
    private ConfigConstants() {
		throw new AssertionError();
	}
}
