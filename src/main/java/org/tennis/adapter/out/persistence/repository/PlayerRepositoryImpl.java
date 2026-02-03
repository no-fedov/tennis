package org.tennis.adapter.out.persistence.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.tennis.adapter.out.persistence.entity.PlayerEntity;
import org.tennis.adapter.out.persistence.entity.PlayerEntity_;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class PlayerRepositoryImpl implements PlayerRepository {

    private static final String FIND_BY_NAME_QUERY = """
                SELECT p
                FROM PlayerEntity p
                WHERE p.name = :name
            """;

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public PlayerEntity save(PlayerEntity player) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(player);
            transaction.commit();
            return player;
        } catch (ConstraintViolationException e) {
            log.warn(e.getMessage(), e);
            return findByName(player.getName()).orElseThrow();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
        }
    }

    @Override
    public Optional<PlayerEntity> findById(Long id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            PlayerEntity player = entityManager.find(PlayerEntity.class, id);
            return Optional.ofNullable(player);
        }
    }

    @Override
    public Optional<PlayerEntity> findByName(String name) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            PlayerEntity player = entityManager
                    .createQuery(FIND_BY_NAME_QUERY, PlayerEntity.class)
                    .setParameter(PlayerEntity_.NAME, name)
                    .getSingleResultOrNull();
            return Optional.ofNullable(player);
        }
    }
}
