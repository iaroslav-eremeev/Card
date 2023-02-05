package com.iaroslaveremeev.model;

import java.util.Date;

public class Card {

    private int id;
    private String question;
    private String answer;
    private int categoryId;
    private Date creationDate;

    /**

     * Связи между таблицами базы данных реализовать в виде:
     * User – Category: 1 ко многим, Category – Card: 1 ко многим
     */
}
