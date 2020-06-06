package com.gavin.app.repository;

import com.gavin.app.domain.Authority;

import org.neo4j.springframework.data.repository.ReactiveNeo4jRepository;
import reactor.core.publisher.Flux;

/**
 * Spring Data Neo4j RX repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends ReactiveNeo4jRepository<Authority, String> {
        Flux<Authority> findAll();
}
