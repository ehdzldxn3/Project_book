package com.example.finalproject.Response;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.example.finalproject.BookViewActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class BookRecordResponse extends AsyncHttpResponseHandler {

    Activity activity;

    public BookRecordResponse(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String str = new String(responseBody);
        Log.e("[TEST]",str);
        try {
            JSONObject json = new JSONObject(str);
            String isreading = json.getString("isreading");
            //다읽은책인경우
            String start_time = "";
            String end_time = "";
            JSONArray item = json.getJSONArray("item");
            if(isreading.equals("DONE")){
                JSONObject temp = item.getJSONObject(0);
                start_time = temp.getString("start_time");
                end_time = temp.getString("end_time");
            }else if(isreading.equals("READING")){
                JSONObject temp = item.getJSONObject(0);
                start_time =temp.getString("start_time");
            }else{
            }
            BookViewActivity bookViewActivity = (BookViewActivity) activity;
            bookViewActivity.setBookHistory(start_time,end_time,isreading);
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
