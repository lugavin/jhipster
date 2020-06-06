package com.gavin.app.config;

import io.github.jhipster.config.JHipsterConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.neo4j.springframework.data.repository.config.EnableReactiveNeo4jRepositories;


@Configuration
@EnableReactiveNeo4jRepositories("com.gavin.app.repository")
public class DatabaseConfiguration {

    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);
}
