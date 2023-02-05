package com.iaroslaveremeev.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class User {

    private int id;
    private String login;
    private char[] password;
    private String name;
    private java.sql.Date regDate;

    public User() {
    }

    // For registration
    public User(String login, char[] password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.regDate = java.sql.Date.valueOf(LocalDate.now());
    }

    public User(String login, char[] password, String name, java.sql.Date regDate) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.regDate = regDate;
    }

    public User(int id, String login, char[] password, String name, java.sql.Date regDate) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.regDate = regDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(login, user.login) && Arrays.equals(password, user.password) && Objects.equals(name, user.name) && Objects.equals(regDate, user.regDate);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, login, name, regDate);
        result = 31 * result + Arrays.hashCode(password);
        return result;
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password=" + Arrays.toString(password) +
                ", name='" + name + '\'' +
                ", regDate=" + dateFormat.format(regDate) +
                '}';
    }
}

