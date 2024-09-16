package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь

        UserServiceImpl userServiceImpl = new UserServiceImpl();

        userServiceImpl.createUsersTable();

        userServiceImpl.saveUser("Winnie", "Pooh", (byte)5);
        userServiceImpl.saveUser("Piglet", "Little", (byte)4);
        userServiceImpl.saveUser("Tigger", "Jumpy", (byte)6);
        userServiceImpl.saveUser("Rabbit", "Busy", (byte)7);

        List<User> users = userServiceImpl.getAllUsers();
        users.forEach(System.out::println);

        userServiceImpl.cleanUsersTable();
        userServiceImpl.dropUsersTable();
    }
}
