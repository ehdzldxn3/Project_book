package com.example.finalproject.Response;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.example.finalproject.BookViewActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class GetFavoriteResponse extends AsyncHttpResponseHandler {
    BookViewActivity activity;

    public GetFavoriteResponse(Activity activity) {
        this.activity = (BookViewActivity)activity;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String response = new String(responseBody);
        try {
            JSONObject json = new JSONObject(response);
            String result = json.getString("result");
            if(result.equals("false")){
                //액티비티에 찜버튼 처리 &플래그처
                activity.setFavoriteFlag(false);
                activity.setFavorite();
            }else{
                activity.setFavoriteFlag(true);
                activity.setFavorite();
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
