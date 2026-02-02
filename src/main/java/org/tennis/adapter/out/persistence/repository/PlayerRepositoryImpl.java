package org.tennis.adapter.out.persistence.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.tennis.adapter.out.persistence.entity.PlayerEntity;
import org.tennis.adapter.out.persistence.entity.PlayerEntity_;
import org.tennis.adapter.out.persistence.repository.mapper.PlayerMapper;
import org.tennis.application.dto.PlayerDto;
import org.tennis.application.port.in.service.PlayerCreate;
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
    private final PlayerMapper playerMapper;

    @Override
    public PlayerDto save(PlayerCreate dto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        PlayerEntity player = playerMapper.toEntity(dto);
        try {
            transaction.begin();
            entityManager.persist(player);
            transaction.commit();
            return playerMapper.toDto(player);
        } catch (ConstraintViolationException e) {
            log.warn(e.getMessage(), e);
            return findByName(dto.name()).orElseThrow();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
        }
    }

    @Override
    public Optional<PlayerDto> findById(Long id) {
        Optional<PlayerEntity> player = findPlayerById(id);
        return player.map(playerMapper::toDto);
    }

    public Optional<PlayerDto> findByName(String name) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            PlayerEntity player = entityManager
                    .createQuery(FIND_BY_NAME_QUERY, PlayerEntity.class)
                    .setParameter(PlayerEntity_.NAME, name)
                    .getSingleResultOrNull();
            PlayerDto playerDto = playerMapper.toDto(player);
            return Optional.ofNullable(playerDto);
        }
    }

    @Override
    public Optional<PlayerEntity> findPlayerById(Long id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            PlayerEntity player = entityManager.find(PlayerEntity.class, id);
            return Optional.ofNullable(player);
        }
    }
}
