package com.example.finalproject.Response;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.example.finalproject.MypageActivity;
import com.example.finalproject.adapter.BookShelfAdapter;
import com.example.finalproject.model.Record;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class GetRecordResponse extends AsyncHttpResponseHandler {
    Activity activity;
    BookShelfAdapter adapter;
    int book_reading,book_done;
    String type;
    public GetRecordResponse(Activity activity, BookShelfAdapter adapter,String type) {
        this.activity = activity;
        this.adapter = adapter;
        this.type = type;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        book_reading=0;
        book_done=0;
        String response = new String(responseBody);
        Log.e("[TEST]",response);
        JSONObject json = null;
        try {
            json = new JSONObject(response);
            String rt = json.getString("rt");
            int tot = json.getInt("total");
            JSONArray items = json.getJSONArray("item");
            for(int i=0;i<items.length();i++){
                Record record = new Record();
                JSONObject temp = items.getJSONObject(i);
                record.setMember_id(temp.getString("member_id"));
                record.setBook_id(temp.getInt("book_id"));
                record.setBook_name(temp.getString("book_name"));
                record.setStart_time(temp.getString("start_time"));
                int isReading = temp.getInt("isreading");
                record.setIsreading(isReading);
                if(isReading==0){
                    book_done++;
                    record.setEnd_time(temp.getString("end_time"));
                }else{
                    book_reading++;
                    record.setEnd_time("now");
                }
                record.setBook_img(temp.getString("book_img"));
                adapter.add(record);
            }
            if(type.equals("MypageActivity")){
                MypageActivity myActivity = (MypageActivity) this.activity;
                myActivity.setState(book_reading,book_done);
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
