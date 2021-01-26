package com.example.finalproject.model;

import java.io.Serializable;

public class Grade implements Serializable {

    private int book_id;
    private int book_good;
    private int book_neut;
    private int book_bad;

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getBook_neut() {
        return book_neut;
    }

    public void setBook_neut(int book_neut) {
        this.book_neut = book_neut;
    }

    public int getBook_bad() {
        return book_bad;
    }

    public void setBook_bad(int book_bad) {
        this.book_bad = book_bad;
    }

    public int getBook_good() {
        return book_good;
    }

    public void setBook_good(int book_good) {
        this.book_good = book_good;
    }
}