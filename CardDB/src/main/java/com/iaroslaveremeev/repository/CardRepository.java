package com.iaroslaveremeev.repository;

import com.iaroslaveremeev.model.Card;
import com.iaroslaveremeev.model.Category;
import com.iaroslaveremeev.util.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardRepository implements AutoCloseable {

    private Connection conn;

    public CardRepository() throws SQLException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection(Constants.DB_URL,
                    Constants.USERNAME, Constants.PASSWORD);
        } catch (ClassNotFoundException | SQLException ignored) {}
    }

    // Add card to a category
    public boolean addCard(Card card){
        String sql = "insert into cards(question, answer, categoryId, creationDate) values (?,?,?,?)";
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, card.getQuestion());
            preparedStatement.setString(2, card.getAnswer());
            preparedStatement.setInt(3, card.getCategoryId());
            preparedStatement.setTimestamp(4, new Timestamp(card.getCreationDate().getTime()));
            int row = preparedStatement.executeUpdate();
            if (row <= 0)
                return false;
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next())
                    card.setId(generatedKeys.getInt(1));
            }
            return true;
        } catch (SQLException ignored) {}
        return false;
    }

    public Card get(int id) {
        String sql = "select * from cards where cards.id=?";
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                return null;
            Card card = new Card();
            card.setQuestion(resultSet.getString(1));
            card.setAnswer(resultSet.getString(2));
            card.setCategoryId(resultSet.getInt(3));
            card.setCreationDate(resultSet.getDate(4));
            return card;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Card getByCategoryId(int categoryId) {
        String sql = "select * from cards where cards.categoryId=?";
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                return null;
            Card card = new Card();
            card.setId(resultSet.getInt(1));
            card.setQuestion(resultSet.getString(2));
            card.setAnswer(resultSet.getString(3));
            card.setCategoryId(resultSet.getInt(4));
            card.setCreationDate(resultSet.getDate(5));
            return card;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO Проработать доступ к user (запрос SQL с join'ом)
    public Card getByUserId(int userId) {
        String sql = "select * from cards where cards.categoryId=?";
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                return null;
            Card card = new Card();
            card.setId(resultSet.getInt(1));
            card.setQuestion(resultSet.getString(2));
            card.setAnswer(resultSet.getString(3));
            card.setCategoryId(resultSet.getInt(4));
            card.setCreationDate(resultSet.getDate(5));
            return card;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
/*
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
    }*/

    // Close connection
    public void close() {
        try {
            if (this.conn != null)
                this.conn.close();
        } catch (Exception ignored) {}
    }
}
