package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Util {
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";

    private Util() {
    }

    private static SessionFactory sessionFactory;

    // Соединение через JDBC
    public static Connection openJDBCSession() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USERNAME_KEY),
                    PropertiesUtil.get(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Соединение через Hibernate
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Настройки Hibernate через код (без xml)
                configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
                configuration.setProperty("hibernate.connection.url", PropertiesUtil.get(URL_KEY));
                configuration.setProperty("hibernate.connection.username", PropertiesUtil.get(USERNAME_KEY));
                configuration.setProperty("hibernate.connection.password", PropertiesUtil.get(PASSWORD_KEY));
                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
                configuration.setProperty("hibernate.show_sql", "true");
                configuration.setProperty("hibernate.format_sql", "true");

                 configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                throw new RuntimeException("There was an issue building the SessionFactory", e);
            }
        }
        return sessionFactory;
    }

    public static Session openHibernateSession() {
        return getSessionFactory().openSession();
    }
}
