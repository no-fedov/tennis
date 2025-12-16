package org.tennis.adapter.out.persistence.config.datasource;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceConfiguration;
import org.tennis.application.entity.MatchEntity;
import org.tennis.application.entity.PlayerEntity;

import static jakarta.persistence.PersistenceConfiguration.JDBC_DRIVER;
import static jakarta.persistence.PersistenceConfiguration.JDBC_PASSWORD;
import static jakarta.persistence.PersistenceConfiguration.JDBC_URL;
import static jakarta.persistence.PersistenceConfiguration.JDBC_USER;
import static org.hibernate.cfg.JdbcSettings.SHOW_SQL;

public class DataSourceConfig {

    private final EntityManagerFactory emf;

    // TODO: вынести в environment variables
    private DataSourceConfig() {
        final PersistenceConfiguration cfg = new PersistenceConfiguration("emf")
                .property(JDBC_URL, "jdbc:h2:mem:db1")
                .property(JDBC_USER, "sa")
                .property(JDBC_PASSWORD, "")
                .property(SHOW_SQL, "true")
                .property(JDBC_DRIVER, "org.h2.Driver")
                .property("hibernate.hbm2ddl.auto", "drop-and-create")
                .managedClass(PlayerEntity.class)
                .managedClass(MatchEntity.class);
        emf = cfg.createEntityManagerFactory();
    }

    private static class SingletonHolder {
        public static final DataSourceConfig HOLDER_INSTANCE = new DataSourceConfig();
    }

    public static DataSourceConfig getConfig() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}
