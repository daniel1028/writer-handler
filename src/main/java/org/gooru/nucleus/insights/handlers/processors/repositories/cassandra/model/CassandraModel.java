package org.gooru.nucleus.insights.handlers.processors.repositories.cassandra.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.gooru.nucleus.insights.handlers.infra.V1CassandraDataSourceRegistry;
import org.gooru.nucleus.insights.handlers.infra.V2CassandraDataSourceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.ColumnDefinitions.Definition;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Update.Assignments;

import io.vertx.core.json.JsonObject;

abstract class CassandraModel {

  abstract protected JsonObject getData();

  abstract protected String getColumnFamily();

  private static final Logger LOG = LoggerFactory.getLogger(CassandraModel.class);

  private final static V1CassandraDataSourceRegistry V1_DATASOURCE = V1CassandraDataSourceRegistry.getInstance();

  private final static V2CassandraDataSourceRegistry V2_DATASOURCE = V2CassandraDataSourceRegistry.getInstance();

  public void save(String cassandraClusterVersion) {
    JsonObject data = getData();
    Session session = getClusterSession(cassandraClusterVersion);
    StringBuffer query = new StringBuffer("INSERT INTO ");
    query.append(getColumnFamily());
    query.append("(");
    boolean intialized = false;
    for (Map.Entry<String, Object> entry : data.getMap().entrySet()) {
      if (intialized) {
        query.append(",");
      }
      query.append(entry.getKey());
      intialized = true;
    }
    query.append(") VALUES (");
    intialized = false;
    int count = 0;
    Object[] values = new Object[data.size()];
    for (Map.Entry<String, Object> entry : data.getMap().entrySet()) {
      if (intialized) {
        query.append(",");
      }
      query.append("?");
      intialized = true;
      values[count++] = entry.getValue();
    }
    query.append(");");
    LOG.debug("Query : {} ", query);
    PreparedStatement statement = session.prepare(query.toString());
    BoundStatement boundStatement = new BoundStatement(statement);

    session.execute(boundStatement.bind(values));
  }

  public void update(String key, Object keyValue, String cassandraClusterVersion) {
    JsonObject data = getData();
    Session session = getClusterSession(cassandraClusterVersion);
    Assignments assignments = QueryBuilder.update(getColumnFamily()).with();
    for (Map.Entry<String, Object> entry : data.getMap().entrySet()) {
      assignments.and(QueryBuilder.set(entry.getKey(), entry.getValue()));
    }
    Statement update = assignments.where((QueryBuilder.eq(key, keyValue)));
    System.out.println(update);
    session.execute(update);
  }

  protected static void delete(String columnFamily, String key, Object KeyValue, String cassandraClusterVersion) {
    Session session = getClusterSession(cassandraClusterVersion);
    Statement delete = QueryBuilder.delete().from(columnFamily).where(QueryBuilder.eq(key, KeyValue));
    session.execute(delete);
  }

  protected static JsonObject find(String columnFamily, String key, Object keyValue, String cassandraClusterVersion) {
    JsonObject resultJson = null;
    Session session = getClusterSession(cassandraClusterVersion);
    Statement select = QueryBuilder.select().all().from(columnFamily).where(QueryBuilder.eq(key, keyValue));
    ResultSet results = session.execute(select);
    if (results != null) {
      resultJson = new JsonObject();
      Row row = results.one();
      List<Definition> definitions = row.getColumnDefinitions().asList();
      for (Definition definition : definitions) {
        Object value = row.getObject(definition.getName());
        if (value instanceof Date) {
          resultJson.put(definition.getName(), ((Date) value).toInstant());
        } else {
          resultJson.put(definition.getName(), value);
        }
      }
    }
    return resultJson;
  }

  // This is to get Cassandra session using Cassandra cluster version
  private static Session getClusterSession(String cassandraClusterVersion) {
    Session session = null;
    if (cassandraClusterVersion != null && (cassandraClusterVersion.equalsIgnoreCase("0.1") || cassandraClusterVersion.equalsIgnoreCase("3.0"))) {
      session = V2_DATASOURCE.getSession();
    } else if (cassandraClusterVersion != null
            && (cassandraClusterVersion.equalsIgnoreCase("3.1") || cassandraClusterVersion.equalsIgnoreCase("4.0"))) {
      session = V1_DATASOURCE.getSession();
    } else {
      session = V2_DATASOURCE.getSession();
    }
    return session;
  }
}
