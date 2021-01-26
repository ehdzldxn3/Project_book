package com.example.finalproject.Response;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.example.finalproject.DiaryActivity;
import com.example.finalproject.LoginActivity;
import com.example.finalproject.model.Record;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class GetMonthDataResponse extends AsyncHttpResponseHandler {

    DiaryActivity activity;
    List<Record> list;
    public GetMonthDataResponse(DiaryActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        list = new ArrayList<>();

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
                    record.setEnd_time(temp.getString("end_time"));
                }else{
                    record.setEnd_time("");
                }
                list.add(record);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            //Log.e("리스트 사이즈는??", String.valueOf(list.size()));
            activity.setList(list);
            activity.setCircle();
            activity.readHistory();
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Toast.makeText(activity,"통신 오류입니다",Toast.LENGTH_SHORT).show();
        String str = new String(responseBody);
        Log.e("[TEST]",str);
    }
}
