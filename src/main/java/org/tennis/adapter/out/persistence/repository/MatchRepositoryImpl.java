package org.tennis.adapter.out.persistence.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import org.tennis.application.entity.MatchEntity;
import org.tennis.application.port.out.persistence.MatchRepository;

import java.util.List;

@RequiredArgsConstructor
public class MatchRepositoryImpl implements MatchRepository {

    private final EntityManagerFactory emf;

    public void save(MatchEntity match) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(match);
            transaction.commit();
        }
    }

    @Override
    public List<MatchEntity> findComplete() {
        try (EntityManager entityManager = emf.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            List<MatchEntity> fromMatchEntity = entityManager
                    .createQuery("SELECT m FROM MatchEntity m", MatchEntity.class)
                    .getResultList();
            transaction.commit();
            return fromMatchEntity;
        }
    }
}
