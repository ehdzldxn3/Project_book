package com.example.finalproject.Response;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class WriteReviewResponse extends AsyncHttpResponseHandler {
    Activity activity;

    public WriteReviewResponse(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String response = new String(responseBody);
        Log.e("[TEST]",response);
        try {
            JSONObject json = new JSONObject(response);
            String rt = json.getString("rt");
            if(rt.equals("OK")){
                Toast.makeText(activity,"서버 저장 성공!",Toast.LENGTH_SHORT).show();
                activity.finish();
            }else {
                Toast.makeText(activity,"서버 저장 실패!",Toast.LENGTH_SHORT).show();
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
