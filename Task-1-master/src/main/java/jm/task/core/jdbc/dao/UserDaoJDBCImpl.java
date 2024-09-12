package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//DAO stands for Data Access Object
public class UserDaoJDBCImpl implements UserDao {

    //Singleton
    private static UserDaoJDBCImpl USER_DAO_JDBC;

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

    public static final String SAVE_USER_SQL = """
            INSERT INTO users (name, last_name, age)
            VALUES (?, ?, ?)
            """;

    public static final String DELETE_USER_SQL = """
            DELETE FROM users
            WHERE id = ?
            """;

    public static final String DROP_USERS_TABLE = """
            DROP TABLE users
            """;

    public static final String FIND_ALL_USERS = """
            SELECT id,
                name,
                last_name,
                age
            FROM users
            """;

    public static final String TRUNCATE_USERS_TABLE = """
            TRUNCATE TABLE users
            """;


    public UserDaoJDBCImpl() {

    }

    //Singleton getter.
    public static UserDaoJDBCImpl getInstance() {
        if (USER_DAO_JDBC == null) {
            USER_DAO_JDBC = new UserDaoJDBCImpl();
        }
        return USER_DAO_JDBC;
    }


    public void createUsersTable() {
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USERS_TABLE)) {


            preparedStatement.executeUpdate();
            System.out.println("Created table users");
        } catch (SQLException e) {
            System.out.println("Failed to create table users. The table may already exist.");
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_USERS_TABLE)) {

            preparedStatement.executeUpdate();
            System.out.println("Dropped table users");
        } catch (SQLException e) {
            System.out.println("Error dropping table users. The table may not exist.");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER_SQL)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            System.out.println("Created user " + name + lastName + " with age " + age);
        } catch (SQLException e) {
        }

    }

    public void removeUserById(long id) {
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("User with id " + id + " removed");
        } catch (SQLException e) {
            System.out.println("User with id " + id + " not found");
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_USERS)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                users.add(buildUserFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            System.out.println("Error getting all users. The table may be empty or not exist.");
        }

        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_USERS_TABLE)) {

            preparedStatement.executeUpdate();
            System.out.println("Dropped table users");
        } catch (SQLException e) {
            System.out.println("Error cleaning table users. The table may be empty or not exist.");
        }

    }

    private User buildUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setAge(resultSet.getByte("age"));

        return user;
    }
}
