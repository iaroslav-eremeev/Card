package com.iaroslaveremeev.repository;

import com.iaroslaveremeev.model.Category;
import com.iaroslaveremeev.util.Constants;

import java.sql.*;

public class CategoryRepository implements AutoCloseable {

    private Connection conn;

    public CategoryRepository() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.conn = DriverManager.getConnection(Constants.DB_URL,
                Constants.USERNAME, Constants.PASSWORD);
    }

    /**
     * •	post – осуществляет добавление новой категории для пользователя с заданным id в базу данных
     * •	get – осуществляет получение всех категорий для заданного id пользователя, получение категории по ее id
     * •	put – осуществляет обновление категории по ее id
     * •	delete – осуществляет удаление категории и всех записей, связанных с ней
     *
     * @return
     * @throws Exception
     */
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

    public void close() throws Exception {
        if (this.conn != null)
            this.conn.close();
    }
}
