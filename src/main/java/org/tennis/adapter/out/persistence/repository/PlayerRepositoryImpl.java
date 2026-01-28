package org.tennis.adapter.out.persistence.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.tennis.application.entity.PlayerEntity;
import org.tennis.application.entity.PlayerEntity_;
import org.tennis.application.port.out.persistence.PlayerRepository;

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
    public void save(PlayerEntity player) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(player);
            transaction.commit();
        } catch (ConstraintViolationException e) {
            log.warn(e.getMessage());
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
