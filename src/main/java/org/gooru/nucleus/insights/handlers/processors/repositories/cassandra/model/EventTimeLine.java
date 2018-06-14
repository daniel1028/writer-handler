package org.gooru.nucleus.insights.handlers.processors.repositories.cassandra.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.gooru.nucleus.insights.handlers.constants.ParameterConstants;
import org.gooru.nucleus.insights.handlers.constants.SchemaConstants;

import io.vertx.core.json.JsonObject;

public class EventTimeLine extends CassandraModel {

  private static final String COLUMN_FAMILY = "events_timeline_info";

  private JsonObject data;
  private SimpleDateFormat minuteDateFormatter;

  public EventTimeLine(JsonObject data) {
    this.minuteDateFormatter = new SimpleDateFormat("yyyyMMddkkmm");
    this.data = new JsonObject();
    Date eventDateTime = new Date(data.getLong(ParameterConstants.END_TIME));
    String eventRowKey = minuteDateFormatter.format(eventDateTime);
    this.data.put(SchemaConstants.EVENT_TIME, eventRowKey);
    this.data.put(SchemaConstants.TYPE,
            data.getJsonObject(ParameterConstants.CONTEXT).containsKey(ParameterConstants.TYPE)
                    ? data.getJsonObject(ParameterConstants.CONTEXT).getString(ParameterConstants.TYPE)
                    : "start");
    this.data.put(SchemaConstants.EVENT_ID, data.getString(ParameterConstants.EVENT_ID));
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
