package com.gavin.app.repository;

import com.gavin.app.domain.PersistentAuditEvent;
import org.springframework.data.domain.Pageable;
import org.neo4j.springframework.data.repository.ReactiveNeo4jRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

/**
 * Spring Data Neo4j repository for the {@link PersistentAuditEvent} entity.
 */
public interface PersistenceAuditEventRepository extends ReactiveNeo4jRepository<PersistentAuditEvent, String> {

    Flux<PersistentAuditEvent> findByPrincipal(String principal);

    Flux<PersistentAuditEvent> findAllByAuditEventDateBetween(Instant fromDate, Instant toDate, Pageable pageable);

    Flux<PersistentAuditEvent> findByAuditEventDateBefore(Instant before);

    Flux<PersistentAuditEvent> findAllBy(Pageable pageable);

    Mono<Long> countByAuditEventDateBetween(Instant fromDate, Instant toDate);
}
