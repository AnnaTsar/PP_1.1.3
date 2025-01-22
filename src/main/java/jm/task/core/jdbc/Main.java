package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {

    private final static UserService userService = new UserServiceImpl();

    private static final User user1 = new User("Иван", "Иванов", (byte) 10);
    private static final User user2 = new User("Петр", "Петров", (byte) 15);
    private static final User user3 = new User("Федор", "Федоров", (byte) 20);
    private static final User user4 = new User("Андрей", "Андреев", (byte) 25);

    public static void main(String[] args) {
        userService.createUsersTable();

        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());

        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());

        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());

        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }

        // реализуйте алгоритм здесь
}

