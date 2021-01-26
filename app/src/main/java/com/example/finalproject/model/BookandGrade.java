package com.example.finalproject.model;

import java.io.Serializable;

public class BookandGrade implements Serializable {
    private int book_neut;
    private String book_summary;
    private String publisher;
    private int book_good;
    private int book_id;
    private String writer;
    private String book_link;
    private String book_img;
    private String book_name;
    private int book_bad;

    public int getBook_neut() {
        return book_neut;
    }

    public void setBook_neut(int book_neut) {
        this.book_neut = book_neut;
    }

    public String getBook_summary() {
        return book_summary;
    }

    public void setBook_summary(String book_summary) {
        this.book_summary = book_summary;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getBook_good() {
        return book_good;
    }

    public void setBook_good(int book_good) {
        this.book_good = book_good;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getBook_link() {
        return book_link;
    }

    public void setBook_link(String book_link) {
        this.book_link = book_link;
    }

    public String getBook_img() {
        return book_img;
    }

    public void setBook_img(String book_img) {
        this.book_img = book_img;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public int getBook_bad() {
        return book_bad;
    }

    public void setBook_bad(int book_bad) {
        this.book_bad = book_bad;
    }
}
