package com.example.finalproject.model;

public class Review {
	private int rn;
	private String book_name;
	private String book_img;
	private String writer;
	private String publisher;
	private int review_id;
	private String review_content;
	private int review_likes;
	private String logtime;
	private String member_nick;
	private String member_photo;
	private int book_good;
	private int book_bad;
	private int book_neut;
	private  String member_id;
	private int book_id;

	public int getBook_id() {
		return book_id;
	}

	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public int getRn() {
		return rn;
	}
	public void setRn(int rn) {
		this.rn = rn;
	}
	public String getBook_name() {
		return book_name;
	}
	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}
	public String getBook_img() {
		return book_img;
	}
	public void setBook_img(String book_img) {
		this.book_img = book_img;
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
	public int getReview_id() {
		return review_id;
	}
	public void setReview_id(int review_id) {
		this.review_id = review_id;
	}
	public String getReview_content() {
		return review_content;
	}
	public void setReview_content(String review_content) {
		this.review_content = review_content;
	}
	public int getReview_likes() {
		return review_likes;
	}
	public void setReview_likes(int review_likes) {
		this.review_likes = review_likes;
	}
	public String getLogtime() {
		return logtime;
	}
	public void setLogtime(String logtime) {
		this.logtime = logtime;
	}
	public String getMember_nick() {
		return member_nick;
	}
	public void setMember_nick(String member_nick) {
		this.member_nick = member_nick;
	}
	public String getMember_photo() {
		return member_photo;
	}
	public void setMember_photo(String member_photo) {
		this.member_photo = member_photo;
	}
	public int getBook_good() {
		return book_good;
	}
	public void setBook_good(int book_good) {
		this.book_good = book_good;
	}
	public int getBook_bad() {
		return book_bad;
	}
	public void setBook_bad(int book_bad) {
		this.book_bad = book_bad;
	}
	public int getBook_neut() {
		return book_neut;
	}
	public void setBook_neut(int book_neut) {
		this.book_neut = book_neut;
	}
	
	
}
