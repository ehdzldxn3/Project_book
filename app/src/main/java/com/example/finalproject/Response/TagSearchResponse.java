package com.example.finalproject.Response;

import android.app.Activity;
import android.util.Log;

import com.example.finalproject.MainActivity;
import com.example.finalproject.adapter.BookAdapter;
import com.example.finalproject.model.Book;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TagSearchResponse extends AsyncHttpResponseHandler {

    Activity activity;
    BookAdapter adapter;

    public TagSearchResponse(Activity activity, BookAdapter adapter) {
        this.activity = activity;
        this.adapter = adapter;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

        String str = new String(responseBody);
        try {
            JSONObject json = new JSONObject(str);
            String rt = json.getString("rt");
            int total = json.getInt("total");
            JSONArray item = json.getJSONArray("item");

            for (int i = 0; i < item.length(); i++) {
                JSONObject temp = item.getJSONObject(i);
                Book dto = new Book();
                dto.setBook_id(temp.getInt("book_id"));
                dto.setBook_name(temp.getString("book_name"));
                dto.setWriter(temp.getString("writer"));
                dto.setPublisher(temp.getString("publisher"));
                dto.setBook_summary(temp.getString("book_summary"));
                //북이미지앞에 아이피주소 붙여주기
                String book_img_url = "http://"+ MainActivity.IP_SET+":8080/ProjectBook/storage/"+temp.getString("book_img");
                dto.setBook_img(book_img_url);
                adapter.add(dto);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Log.e("TagSearchResponse", "통신실패");
    }
}
