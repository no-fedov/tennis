package org.tennis.adapter.out.persistence.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import org.tennis.application.entity.PlayerEntity;
import org.tennis.application.port.out.persistence.PlayerRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class PlayerRepositoryImpl implements PlayerRepository {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public void save(PlayerEntity player) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(player);
            transaction.commit();
        }
    }

    @Override
    public Optional<PlayerEntity> findByName(String name) {
        PlayerEntity player = null;
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            PlayerEntity playerEntity = entityManager
                    .createQuery("SELECT p FROM PlayerEntity p WHERE p.name = :name", PlayerEntity.class)
                    .setParameter("name", name)
                    .getSingleResultOrNull();
            transaction.commit();
            player = playerEntity;
        }
        return Optional.ofNullable(player);
    }
}
