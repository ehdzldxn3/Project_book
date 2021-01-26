package com.example.finalproject.Response;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.example.finalproject.adapter.WriteReviewAdapter;
import com.example.finalproject.model.BookandGrade;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class WriteReviewListResponse extends AsyncHttpResponseHandler {
    Activity activity;
    WriteReviewAdapter adapter;

    public WriteReviewListResponse(Activity activity, WriteReviewAdapter adapter) {
        this.activity = activity;
        this.adapter = adapter;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String response = new String(responseBody);
        Log.e("[TEST]",response);
        try {
            JSONObject json = new JSONObject(response);
            JSONArray item = json.getJSONArray("item");
            for(int i=0;i<item.length();i++){
                JSONObject object = item.getJSONObject(i);
                BookandGrade temp = new BookandGrade();
                temp.setBook_neut(object.getInt("book_neut"));
                temp.setBook_good(object.getInt("book_good"));
                temp.setBook_bad(object.getInt("book_bad"));
                temp.setBook_summary(object.getString("book_summary"));
                temp.setPublisher(object.getString("publisher"));
                temp.setWriter(object.getString("writer"));
                temp.setBook_id(object.getInt("book_id"));
                temp.setBook_link(object.getString("book_link"));
                temp.setBook_img(object.getString("book_img"));
                temp.setBook_name(object.getString("book_name"));
                adapter.add(temp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Toast.makeText(activity,"통신 오류입니다",Toast.LENGTH_SHORT).show();
        String str = new String(responseBody);
        Log.e("[TEST]",str);
    }
}
