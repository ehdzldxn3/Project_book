package com.example.finalproject.model;

import java.io.Serializable;

public class Member implements Serializable {
    String member_nick;
    String member_id;
    String member_pw;
    String member_genre;
    String member_photo;
    String member_phone;
    int  member_goal;
    String member_email;

    public String getMember_nick() {
        return member_nick;
    }

    public void setMember_nick(String member_nick) {
        this.member_nick = member_nick;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_pw() {
        return member_pw;
    }

    public void setMember_pw(String member_pw) {
        this.member_pw = member_pw;
    }

    public String getMember_genre() {
        return member_genre;
    }

    public void setMember_genre(String member_genre) {
        this.member_genre = member_genre;
    }

    public String getMember_photo() {
        return member_photo;
    }

    public void setMember_photo(String member_photo) {
        this.member_photo = member_photo;
    }

    public String getMember_phone() {
        return member_phone;
    }

    public void setMember_phone(String member_phone) {
        this.member_phone = member_phone;
    }

    public int getMember_goal() {
        return member_goal;
    }

    public void setMember_goal(int member_goal) {
        this.member_goal = member_goal;
    }

    public String getMember_email() {
        return member_email;
    }

    public void setMember_email(String member_email) {
        this.member_email = member_email;
    }
}
