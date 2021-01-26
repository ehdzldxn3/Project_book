package com.example.finalproject.Response;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.example.finalproject.adapter.RecyclerViewAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SearchSetResponse extends AsyncHttpResponseHandler {

    Activity activity;
    String member_id;
    String search_text;

    public SearchSetResponse(Activity activity, String member_id, String search_text) {
        this.activity = activity;
        this.member_id = member_id;
        this.search_text = search_text;
    }
    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String str = new String(responseBody);
        try {
            JSONObject json = new JSONObject(str);
            String rt = json.getString("rt");
            Log.e("[search]",rt);
            if (rt.equals("ok")) {
               Log.e("[SearSetResponse]", "서치셋 리스폰스 저장 성공");
            } else {
                Log.e("[SearSetResponse]", "서치셋 리스폰스 저장 실패");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Log.e("[SearSetResponse]", "서치셋 리스폰스 통신 실패");
    }
}
