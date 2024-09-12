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
        User Winnie = new User("Winnie", "Pooh", (byte)5);
        User Piglet = new User("Piglet", "Little", (byte)4);
        User Tigger = new User("Tigger", "Jumpy", (byte)6);
        User Rabbit = new User("Rabbit", "Busy", (byte)7);

        UserServiceImpl userService = new UserServiceImpl();

//        userService.createUsersTable();
//        userService.saveUser(Winnie.getName(), Winnie.getLastName(), Winnie.getAge());
//        userService.saveUser(Piglet.getName(), Piglet.getLastName(), Piglet.getAge());
//        userService.saveUser(Tigger.getName(), Tigger.getLastName(), Tigger.getAge());
//        userService.saveUser(Rabbit.getName(), Rabbit.getLastName(), Rabbit.getAge());

        userService.cleanUsersTable();
        List<User> users = userService.getAllUsers();
        users.forEach(System.out::println);


//        users.clear();
//        users = userService.getAllUsers();
//        users.forEach(System.out::println);

//        userService.removeUserById(1);
//        userService.dropUsersTable();
    }
}
