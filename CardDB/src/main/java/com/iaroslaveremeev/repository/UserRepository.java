package com.iaroslaveremeev.repository;

import com.iaroslaveremeev.model.User;
import com.iaroslaveremeev.util.Constants;

import java.sql.*;
import java.util.Arrays;

public class UserRepository implements AutoCloseable {
    private Connection conn;
    public UserRepository() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.conn = DriverManager.getConnection(Constants.DB_URL,
                Constants.USERNAME, Constants.PASSWORD);
    }

    public boolean add(User user){
        String sql = "insert into users(login,password,name) values (?,?,?,?)";
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, Arrays.toString(user.getPassword()));
            preparedStatement.setString(3, user.getName());
            // Сработает ли - хз, раньше было через Timestamp - по рекомендации ментора
            // Создание даты будет не здесь, так как тут уже готовый юзер с 4-мя полями
            preparedStatement.setDate(4, user.getRegDate());
            int row = preparedStatement.executeUpdate();
            if (row <= 0)
                return false;
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next())
                    user.setId(generatedKeys.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void close() throws Exception {
        if (this.conn != null)
            this.conn.close();
    }
}
