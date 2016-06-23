package com.alexshabanov.sample.cassandra.hello.service;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public final class HelloCassandraAppRunner implements Runnable {

  @Override
  public void run() {
    System.out.println("In HelloCassandraAppRunner");

    // Connect to the cluster and keyspace "demo"
    final Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
    final Session session = cluster.connect("demo");

    final ResultSet rs = session.execute("SELECT * FROM users");

    for (final Row row : rs.all()) {
      System.out.format("%s %d\n", row.getString("name"), row.getInt("age"));
    }
  }
}
