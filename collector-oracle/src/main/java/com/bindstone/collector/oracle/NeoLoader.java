package com.bindstone.collector.oracle;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;

public class NeoLoader implements AutoCloseable {
    private final Driver driver;

    public NeoLoader(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }

    public void clear() {
        try (Session session = driver.session()) {
            String greeting = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    System.out.println("Clear Neo4J");
                    StatementResult result = tx.run("MATCH (n) DETACH DELETE n");
                    return new String();
                }
            });
        }
    }

    public void allObjects() {
        System.out.println("Import Neo4J");
        String query = "LOAD CSV WITH HEADERS FROM \"http://127.0.0.1:8000/csv/all_objects.csv\" AS row\n" +
                "      CREATE (col:OBJECT  {\n" +
                "      owner: row.OWNER,\n" +
                "      type: row.OBJECT_TYPE,\n" +
                "      name: row.OBJECT_NAME\n" +
                "      })\n" +
                "      MERGE (own:OWNER{name: row.OWNER})\n" +
                "      MERGE ((own) -[r1:OWNS]-> (col))";
        try (Session session = driver.session()) {
            String greeting = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    StatementResult result = tx.run(query);
                    return new String();
                }
            });
        }
    }
}