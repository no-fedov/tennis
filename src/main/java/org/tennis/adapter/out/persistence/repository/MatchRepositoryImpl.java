package org.tennis.adapter.out.persistence.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.tennis.application.entity.MatchEntity;
import org.tennis.application.entity.MatchEntity_;
import org.tennis.application.entity.PlayerEntity_;
import org.tennis.application.port.out.persistence.MatchRepository;

import java.util.List;
import java.util.Objects;

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
    public List<MatchEntity> findComplete(Integer pageSize, Integer pageNumber, String playerName) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            CriteriaBuilder builder = emf.getCriteriaBuilder();
            CriteriaQuery<MatchEntity> query = builder.createQuery(MatchEntity.class);
            Root<MatchEntity> root = query.from(MatchEntity.class);
            query.select(root);
            if (Objects.nonNull(playerName)) {
                query.where(
                        builder.equal(root.join(MatchEntity_.WINNER).get(PlayerEntity_.NAME), playerName)
                );
            }
            return entityManager
                    .createQuery(query)
                    .setFirstResult((pageNumber - 1) * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
        }
    }

    @Override
    public Long countComplete(String playerName) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            CriteriaBuilder builder = emf.getCriteriaBuilder();
            CriteriaQuery<Long> query = builder.createQuery(Long.class);
            Root<MatchEntity> root = query.from(MatchEntity.class);
            query.select(builder.count(root));
            if (Objects.nonNull(playerName)) {
                query.where(
                        builder.equal(root.join(MatchEntity_.WINNER).get(PlayerEntity_.NAME), playerName)
                );
            }
            return entityManager
                    .createQuery(query)
                    .getSingleResult();
        }
    }
}
