package org.tennis.adapter.out.persistence.config.datasource;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceConfiguration;
import org.tennis.adapter.out.persistence.entity.MatchEntity;
import org.tennis.adapter.out.persistence.entity.PlayerEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static jakarta.persistence.PersistenceConfiguration.JDBC_DRIVER;
import static jakarta.persistence.PersistenceConfiguration.JDBC_PASSWORD;
import static jakarta.persistence.PersistenceConfiguration.JDBC_URL;
import static jakarta.persistence.PersistenceConfiguration.JDBC_USER;
import static org.hibernate.cfg.JdbcSettings.SHOW_SQL;

public class DataSourceConfig {

    public static final String HIBERNATE_HBM_2_DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final Properties properties = new Properties();

    static {
        try (InputStream is = DataSourceConfig.class.getClassLoader().getResourceAsStream("hibernate.properties")) {
            if (is == null) {
                throw new RuntimeException("Файл hibernate.properties не найден в classpath");
            }
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить файл hibernate.properties", e);
        }
    }

    private final EntityManagerFactory emf;

    private DataSourceConfig() {
        final PersistenceConfiguration cfg = new PersistenceConfiguration("emf")
                .property(JDBC_DRIVER, properties.getProperty(JDBC_DRIVER))
                .property(JDBC_URL, properties.getProperty(JDBC_URL))
                .property(JDBC_USER, properties.getProperty(JDBC_USER))
                .property(JDBC_PASSWORD, properties.getProperty(JDBC_PASSWORD))
                .property(SHOW_SQL, properties.getProperty(SHOW_SQL))
                .property(HIBERNATE_HBM_2_DDL_AUTO,  properties.getProperty(HIBERNATE_HBM_2_DDL_AUTO))
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
