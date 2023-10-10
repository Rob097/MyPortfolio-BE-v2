package com.myprojects.myportfolio.core.configAndUtils;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Component
public class ScheduledMethods {

    @PersistenceContext
    private EntityManager entityManager;

    /** Evicts all second level cache hibernate entities. */
    @Scheduled(cron = "${scheduled-cron-expression.cache-eviction}")
    public void evict2ndLevelCache() {
        try {
            log.info("Evicting All Entities from 2nd level cache.");
            Session session = entityManager.unwrap(Session.class);
            SessionFactory hibernateFactory = session.getSessionFactory();
            hibernateFactory.getCache().evictAll();
        } catch (Exception e) {
            log.error("Error evicting 2nd level hibernate cache entities: ", e);
        }
    }

}
