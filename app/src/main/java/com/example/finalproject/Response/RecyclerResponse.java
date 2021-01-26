package com.example.finalproject.Response;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.example.finalproject.adapter.RecyclerViewAdapter;
import com.example.finalproject.model.Book;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RecyclerResponse extends AsyncHttpResponseHandler {
    Activity activity;
    RecyclerViewAdapter adapter;

    public RecyclerResponse(Activity activity, RecyclerViewAdapter adapter) {
        this.activity = activity;
        this.adapter = adapter;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String response = new String(responseBody);
        try {
            JSONObject json = new JSONObject(response);
            String rt = json.getString("rt");
            int tot = json.getInt("total");
            if(rt.equals("FAIL")) {
                Toast.makeText(activity,"데이터 오류입니다",Toast.LENGTH_SHORT).show();
            }else{
                JSONArray items = json.getJSONArray("item");
                for(int i=0;i<tot;i++){
                    JSONObject temp = items.getJSONObject(i);
                    Book book = new Book();
                    String book_img = temp.getString("book_img");
                    int book_id = Integer.parseInt(temp.getString("book_id"));
                    book.setBook_img(book_img);
                    book.setBook_id(book_id);
                    //Log.e("[TEST]",book_img);
                    adapter.add(book);
                }
                //Log.e("[TEST]","리스폰스 끝?");
                adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Toast.makeText(activity,"통신 오류입니다",Toast.LENGTH_SHORT).show();
        String str = new String(responseBody);
        Log.e("[Connection Failure]",str);
    }

}
