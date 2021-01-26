package com.example.finalproject.Response;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.example.finalproject.adapter.SearchAdapter;
import com.example.finalproject.model.Search;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SearchTextListResponse extends AsyncHttpResponseHandler {

    Activity activity;
    SearchAdapter adapter;


    public SearchTextListResponse(Activity activity, SearchAdapter adapter) {
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
                Search dto = new Search();
                dto.setMember_id(temp.getString("member_id"));
                dto.setSearch_text(temp.getString("search"));
                adapter.add(dto);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Log.e("SearchTextListResponse" ,"통신실패");
    }
}
