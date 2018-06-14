package org.gooru.nucleus.insights.handlers.processors.repositories.cassandra.model;

import org.gooru.nucleus.insights.handlers.constants.ParameterConstants;
import org.gooru.nucleus.insights.handlers.constants.SchemaConstants;

import io.vertx.core.json.JsonObject;

public class Event extends CassandraModel {

  private static final String COLUMN_FAMILY = "event_info";

  private JsonObject data;

  public Event(JsonObject data) {
    this.data = new JsonObject();
    this.data.put(SchemaConstants.EVENT_ID, data.getString(ParameterConstants.EVENT_ID));
    this.data.put(SchemaConstants.TYPE,
            data.getJsonObject(ParameterConstants.CONTEXT).containsKey(ParameterConstants.TYPE)
                    ? data.getJsonObject(ParameterConstants.CONTEXT).getString(ParameterConstants.TYPE)
                    : "start");
    this.data.put(SchemaConstants.FIELD, data.toString());
  }

  public static JsonObject find(String key, String keyValue, String cassandraClusterVersion) {
    return find(COLUMN_FAMILY, key, keyValue, cassandraClusterVersion);
  }

  public static void delete(String key, String keyValue, String cassandraClusterVersion) {
    delete(COLUMN_FAMILY, key, keyValue, cassandraClusterVersion);
  }

  @Override
  public JsonObject getData() {
    return data;
  }

  @Override
  public String getColumnFamily() {
    return COLUMN_FAMILY;
  }
}
