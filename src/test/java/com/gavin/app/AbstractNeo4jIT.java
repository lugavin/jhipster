package com.gavin.app;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.Neo4jContainer;

import java.util.concurrent.atomic.AtomicBoolean;

public class AbstractNeo4jIT implements BeforeAllCallback {

    private static AtomicBoolean started = new AtomicBoolean(false);

    private static Neo4jContainer neo4jContainer = new Neo4jContainer("neo4j:4.0.4").withoutAuthentication();

    public void beforeAll(ExtensionContext extensionContext) {
        if (!started.get()) {
            neo4jContainer.start();
            System.setProperty(
                "org.neo4j.driver.uri",
                neo4jContainer.getBoltUrl()
            );
            started.set(true);
        }
    }
}
