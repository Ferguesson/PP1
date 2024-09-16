package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    //Singleton
    private static final UserDaoHibernateImpl INSTANCE = new UserDaoHibernateImpl();

    //SQL finals.
    public static final String CREATE_USERS_TABLE = """
            create table users
            (
                id BIGSERIAL primary key,
                name      VARCHAR(128),
                last_name VARCHAR(128),
                age       SMALLINT
            )
            """;

    public static final String DROP_USERS_TABLE = """
            DROP TABLE users
            """;

    public static final String TRUNCATE_USERS_TABLE = """
            TRUNCATE TABLE users
            """;

    public UserDaoHibernateImpl() {
    }


    public static UserDaoHibernateImpl getInstance() {
        return INSTANCE;
    }


    @Override
    public void createUsersTable() {
        Session session = Util.openHibernateSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createSQLQuery(CREATE_USERS_TABLE).executeUpdate();
            transaction.commit();
            System.out.println("Created table users");
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Failed to create table users. The table may already exist.");
        } finally {
            session.close();
        }

    }

    @Override
    public void dropUsersTable() {
        Session session = Util.openHibernateSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createSQLQuery(DROP_USERS_TABLE).executeUpdate();
            transaction.commit();
            System.out.println("Dropped table users");
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Error dropping table users. The table may not exist.");
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.openHibernateSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("Created user " + name + lastName + " with age " + age);
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Error creating user.");
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.openHibernateSession();
        Transaction transaction = session.beginTransaction();
        try {
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                transaction.commit();
                System.out.println("User with id " + id + " removed");
            }
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("User with id " + id + " not found");
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.openHibernateSession();
        List<User> users = null;
        try {
            // Используем HQL вместо SQL
            users = session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            System.out.println("Error getting all users. The table may be empty or does not exist.");
        } finally {
            session.close();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.openHibernateSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createSQLQuery(TRUNCATE_USERS_TABLE).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Error clearing table users. The table may be empty or does not exist.");
        } finally {
            session.close();
        }
    }
}
