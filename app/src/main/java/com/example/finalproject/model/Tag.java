package com.example.finalproject.model;

public class Tag {

    private int book_id;         	//책식별번호
    private String book_name;    	//책 이름
    private int genre_poet;        //시
    private int genre_essay;        //에세이
    private int genre_selfDev;      //자기계발
    private int genre_history;      //역사
    private int genre_science;    	//과학
    private int genre_novel;        //소설
    private int genre_comics;    	//만화
    private int genre_art;

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

    public int getGenre_poet() {
        return genre_poet;
    }

    public void setGenre_poet(int genre_poet) {
        this.genre_poet = genre_poet;
    }

    public int getGenre_essay() {
        return genre_essay;
    }

    public void setGenre_essay(int genre_essay) {
        this.genre_essay = genre_essay;
    }

    public int getGenre_selfDev() {
        return genre_selfDev;
    }

    public void setGenre_selfDev(int genre_selfDev) {
        this.genre_selfDev = genre_selfDev;
    }

    public int getGenre_history() {
        return genre_history;
    }

    public void setGenre_history(int genre_history) {
        this.genre_history = genre_history;
    }

    public int getGenre_science() {
        return genre_science;
    }

    public void setGenre_science(int genre_science) {
        this.genre_science = genre_science;
    }

    public int getGenre_novel() {
        return genre_novel;
    }

    public void setGenre_novel(int genre_novel) {
        this.genre_novel = genre_novel;
    }

    public int getGenre_comics() {
        return genre_comics;
    }

    public void setGenre_comics(int genre_comics) {
        this.genre_comics = genre_comics;
    }

    public int getGenre_art() {
        return genre_art;
    }

    public void setGenre_art(int genre_art) {
        this.genre_art = genre_art;
    }
}
