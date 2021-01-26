package com.example.finalproject.Response;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ChkIdResponse extends AsyncHttpResponseHandler {

    Activity activity;

    public ChkIdResponse(Activity activity) {
        this.activity = activity;

    }
    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String str = new String(responseBody);
        try {
            JSONObject json = new JSONObject(str);
            String rt = json.getString("rt");
            if (rt.equals("OK")) {
                Toast.makeText(activity,"같은 아이디가 존재합니다",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity,"사용 할 수 있는 아이디입니다.",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Log.e("ChkIdResponse" ,"통신실패");
    }
}
