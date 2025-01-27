package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {

    private static final Connection conn = Util.getConnection();
    private static final Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    public UserDaoJDBCImpl() {
    }

    //создать таблицу пользователей
    public void createUsersTable() {
        try (PreparedStatement prst = conn.prepareStatement(
                "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(50), " +
                "lastName VARCHAR(50), " +
                "age INT)")) {
            prst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//удалить таблицу пользователей
    public void dropUsersTable() {
        try (PreparedStatement prst = conn.prepareStatement ("DROP TABLE IF EXISTS users")) {
            prst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//сохранить пользователя в таблицу
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

            logger.info("User с именем – " + name + " добавлен в базу данных");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//удалить по id
    public void removeUserById(long id) {
        String sql = "DELETE FROM users where id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            logger.info("User удален");
        } catch (SQLException e) {
            e.printStackTrace();
    }
}

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql1 = "SELECT * FROM users";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql1)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String sql2 = "TRUNCATE TABLE users";
        try (PreparedStatement prst = conn.prepareStatement(sql2)) {
            prst.executeUpdate();
            logger.info("Таблица очищена");
        } catch (SQLException e) {
            logger.warning("Не удалось очистить");
            e.printStackTrace();

        }
    }
}
