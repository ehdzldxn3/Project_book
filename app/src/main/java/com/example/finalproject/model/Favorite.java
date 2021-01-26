package com.example.finalproject.model;

public class Favorite {

    private int book_id;    //책 ID
    private String book_name;    //책제목
    private String writer;      //작가
    private String publisher;      //출판사
    private float grade;      //평점
    private String book_img;      //책 사진
    private String book_summary;      //책 스토리 요약
    private String book_link;      //책 구매링크
    private String member_id;

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getBook_img() {
        return book_img;
    }

    public void setBook_img(String book_img) {
        this.book_img = book_img;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public String getBook_summary() {
        return book_summary;
    }

    public void setBook_summary(String book_summary) {
        this.book_summary = book_summary;
    }

    public String getBook_link() {
        return book_link;
    }

    public void setBook_link(String book_link) {
        this.book_link = book_link;
    }
}
