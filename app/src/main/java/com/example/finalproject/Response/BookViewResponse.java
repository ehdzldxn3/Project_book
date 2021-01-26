package com.example.finalproject.Response;

import android.app.Activity;
import android.util.Log;

import com.example.finalproject.BookViewActivity;
import com.example.finalproject.model.Book;
import com.example.finalproject.model.Grade;
import com.example.finalproject.model.Tag;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class BookViewResponse extends AsyncHttpResponseHandler {

    Activity activity;
    Book book;
    Grade grade;


    public BookViewResponse(Activity activity) {
        this.activity = activity;
    }


    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String str = new String(responseBody);
        try {
            JSONObject json = new JSONObject(str);
            String rt = json.getString("rt");
            int total = json.getInt("total");
            JSONArray item = json.getJSONArray("item");
            JSONObject temp = item.getJSONObject(0);
            book = new Book();
            book.setBook_id(temp.getInt("book_id"));
            book.setBook_img(temp.getString("book_img"));
            book.setBook_name(temp.getString("book_name"));
            book.setWriter(temp.getString("writer"));
            book.setPublisher(temp.getString("publisher"));
            book.setBook_summary(temp.getString("book_summary"));
            book.setBook_link(temp.getString("book_link"));

            //점수저장
            grade = new Grade();
            grade.setBook_good(temp.getInt("book_good"));
            grade.setBook_bad(temp.getInt("book_bad"));
            grade.setBook_neut(temp.getInt("book_neut"));
            BookViewActivity bookViewActivity = (BookViewActivity) activity;
            bookViewActivity.setGrade(this.grade);
            bookViewActivity.setBook(this.book);
            bookViewActivity.setBookProfile();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Log.e("bookViewResponse", "통신 실패입니다.");
    }
}
