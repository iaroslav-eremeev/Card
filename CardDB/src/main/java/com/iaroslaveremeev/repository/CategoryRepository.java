package com.iaroslaveremeev.repository;

import com.iaroslaveremeev.model.Category;
import com.iaroslaveremeev.util.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository implements AutoCloseable {

    private Connection conn;

    public CategoryRepository() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.conn = DriverManager.getConnection(Constants.DB_URL,
                Constants.USERNAME, Constants.PASSWORD);
    }
    public boolean addUserCategory(Category category){
        String sql = "insert into categories(name, userId) values (?,?)";
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.setInt(2, category.getUserId());
            int row = preparedStatement.executeUpdate();
            if (row <= 0)
                return false;
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next())
                    category.setId(generatedKeys.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Category get(int id) {
        String sql = "select * from categories where categories.id=?";
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                return null;
            Category category = new Category();
            category.setId(resultSet.getInt(1));
            category.setName(resultSet.getString(2));
            category.setUserId(resultSet.getInt(3));
            return category;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Category getByUserId(int userId) {
        String sql = "select * from categories where categories.userId=?";
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                return null;
            Category category = new Category();
            category.setId(resultSet.getInt(1));
            category.setName(resultSet.getString(2));
            category.setUserId(resultSet.getInt(3));
            return category;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Category> getAll() throws SQLException {
        String sql = "select * from categories";
        ArrayList<Category> categories = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt(1));
                category.setName(resultSet.getString(2));
                category.setUserId(resultSet.getInt(3));
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        return categories;
    }

    public boolean update(Category category) throws SQLException {
        String sql = "update categories set categories.name=?, categories.userId=? where categories.id=?";
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.setInt(2, category.getUserId());
            preparedStatement.setInt(3, category.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    //TODO добавить каскадное удаление данных
    public boolean delete(int id) {
        String sql = "delete from categories where categories.id=?";
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
