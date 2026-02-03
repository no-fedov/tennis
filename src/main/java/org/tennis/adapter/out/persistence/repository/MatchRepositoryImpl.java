package org.tennis.adapter.out.persistence.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.tennis.adapter.out.persistence.entity.MatchEntity;
import org.tennis.adapter.out.persistence.entity.MatchEntity_;
import org.tennis.adapter.out.persistence.entity.PlayerEntity_;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class MatchRepositoryImpl implements MatchRepository {

    private final EntityManagerFactory emf;

    public void save(MatchEntity match) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(match);
            transaction.commit();
        } catch (ConstraintViolationException e) {
            log.warn(e.getMessage(), e);
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
        }
    }

    @Override
    public Optional<MatchEntity> findById(Long id) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            return Optional.ofNullable(entityManager.find(MatchEntity.class, id));
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
