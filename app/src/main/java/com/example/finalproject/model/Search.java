package com.example.finalproject.model;

import java.io.Serializable;

public class Search implements Serializable {

    private String member_id;    // 아이디
    private String search_text;    //검색했던 항목

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getSearch_text() {
        return search_text;
    }

    public void setSearch_text(String search_text) {
        this.search_text = search_text;
    }
}
