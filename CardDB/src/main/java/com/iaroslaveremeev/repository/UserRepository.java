package com.iaroslaveremeev.repository;

import com.iaroslaveremeev.model.User;
import com.iaroslaveremeev.util.Constants;

import java.sql.*;

public class UserRepository implements AutoCloseable {
    private Connection conn;
    public UserRepository() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.conn = DriverManager.getConnection(Constants.DB_URL,
                Constants.USERNAME, Constants.PASSWORD);
    }
    
    public boolean add(User user){
        PreparedStatement statement = null;
        statement.setTimestamp(1, new Timestamp(user.getRegDate().getTime()));
    }

    public void close() throws Exception {
        if (this.conn != null)
            this.conn.close();
    }
}
