package com.iaroslaveremeev.repository;

import com.iaroslaveremeev.dto.ResponseResult;
import com.iaroslaveremeev.model.User;
import com.iaroslaveremeev.util.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserRepository implements AutoCloseable {
    private Connection conn;
    public UserRepository() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.conn = DriverManager.getConnection(Constants.DB_URL,
                Constants.USERNAME, Constants.PASSWORD);
    }

    // Extracted method to create user from info obtained from SQL request
    private User createFromRequest(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt(1));
        user.setLogin(resultSet.getString(2));
        user.setPassword(resultSet.getString(3).toCharArray());
        user.setName(resultSet.getString(4));
        user.setRegDate(resultSet.getDate(5));
        return user;
    }

    public User get(int id) {
        String sql = "select * from users where users.id=?";
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                return null;
            return createFromRequest(resultSet);
        } catch (SQLException e) {
            ResponseResult<User> result = new ResponseResult<>(e.getMessage());
            return result.getData();
        }
    }

    public List<User> getAll() throws SQLException {
        String sql = "select * from users";
        ArrayList<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(createFromRequest(resultSet));
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        return users;
    }

    public boolean add(User user){
        String sql = "insert into users(login, password, name, regDate) values (?,?,?,?)";
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, Arrays.toString(user.getPassword()));
            preparedStatement.setString(3, user.getName());
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

    public boolean delete(int id) {
        String sql = "delete from users where users.id=?";
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException ignored) {}
        return false;
    }

    public void close() throws Exception {
        if (this.conn != null)
            this.conn.close();
    }

}
