package com.example.finalproject.Response;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.example.finalproject.DiaryActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SetGoalResponse extends AsyncHttpResponseHandler {
    DiaryActivity activity;

    public SetGoalResponse(DiaryActivity activity) {
        this.activity = activity;
    }


    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

        String response = new String(responseBody);

        try {
            JSONObject json = new JSONObject(response);
            String rt = json.getString("rt");
            if(rt.equals("OK")){
                Toast.makeText(activity,"목표 권수 업데이트 성공!",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(activity,"목표 권수 업데이트 실패!",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            activity.setCircle();
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Toast.makeText(activity,"통신 오류입니다",Toast.LENGTH_SHORT).show();
        String str = new String(responseBody);
        Log.e("[TEST]",str);
    }
}
