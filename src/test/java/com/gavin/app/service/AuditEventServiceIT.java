package com.gavin.app.service;

import com.gavin.app.AbstractNeo4jIT;
import com.gavin.app.domain.PersistentAuditEvent;
import com.gavin.app.repository.PersistenceAuditEventRepository;
import com.gavin.app.JhipsterApp;
import io.github.jhipster.config.JHipsterProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link AuditEventService}.
 */
@SpringBootTest(classes = JhipsterApp.class)
@ExtendWith(AbstractNeo4jIT.class)
public class AuditEventServiceIT {
    @Autowired
    private AuditEventService auditEventService;

    @Autowired
    private PersistenceAuditEventRepository persistenceAuditEventRepository;

    @Autowired
    private JHipsterProperties jHipsterProperties;

    private PersistentAuditEvent auditEventOld;

    private PersistentAuditEvent auditEventWithinRetention;

    private PersistentAuditEvent auditEventNew;

    @BeforeEach
    public void init() {
        auditEventOld = new PersistentAuditEvent();
        auditEventOld.setAuditEventDate(Instant.now().minus(jHipsterProperties.getAuditEvents().getRetentionPeriod() + 1, ChronoUnit.DAYS));
        auditEventOld.setPrincipal("test-user-old");
        auditEventOld.setAuditEventType("test-type");

        auditEventWithinRetention = new PersistentAuditEvent();
        auditEventWithinRetention.setAuditEventDate(Instant.now().minus(jHipsterProperties.getAuditEvents().getRetentionPeriod() - 1, ChronoUnit.DAYS));
        auditEventWithinRetention.setPrincipal("test-user-retention");
        auditEventWithinRetention.setAuditEventType("test-type");

        auditEventNew = new PersistentAuditEvent();
        auditEventNew.setAuditEventDate(Instant.now());
        auditEventNew.setPrincipal("test-user-new");
        auditEventNew.setAuditEventType("test-type");
    }

    @Test
    public void verifyOldAuditEventsAreDeleted() {
        persistenceAuditEventRepository.deleteAll().block();
        persistenceAuditEventRepository.save(auditEventOld).block();
        persistenceAuditEventRepository.save(auditEventWithinRetention).block();
        persistenceAuditEventRepository.save(auditEventNew).block();

        auditEventService.removeOldAuditEvents();

        assertThat(persistenceAuditEventRepository.findAll().collectList().block().size()).isEqualTo(2);
        assertThat(persistenceAuditEventRepository.findByPrincipal("test-user-old").collectList().block()).isEmpty();
        assertThat(persistenceAuditEventRepository.findByPrincipal("test-user-retention").collectList().block()).isNotEmpty();
        assertThat(persistenceAuditEventRepository.findByPrincipal("test-user-new").collectList().block()).isNotEmpty();
    }
}
