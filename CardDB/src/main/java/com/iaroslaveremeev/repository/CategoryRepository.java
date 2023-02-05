package com.iaroslaveremeev.repository;

import com.iaroslaveremeev.util.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CategoryRepository implements AutoCloseable {

    private Connection conn;

    public CategoryRepository() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.conn = DriverManager.getConnection(Constants.DB_URL,
                Constants.USERNAME, Constants.PASSWORD);
    }

    public void close() throws Exception {
        if (this.conn != null)
            this.conn.close();
    }
}
