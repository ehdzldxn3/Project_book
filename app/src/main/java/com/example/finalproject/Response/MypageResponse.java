package com.example.finalproject.Response;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.example.finalproject.MypageActivity;
import com.example.finalproject.adapter.FavoriteAdapter;
import com.example.finalproject.model.Favorite;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MypageResponse extends AsyncHttpResponseHandler {
    Activity activity;
    FavoriteAdapter favoriteAdapter;

    public MypageResponse(Activity activity, FavoriteAdapter favoriteAdapter) {
        this.activity = activity;
        this.favoriteAdapter = favoriteAdapter;
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
                    String book_img = temp.getString("book_img");
                    String book_name = temp.getString("book_name");
                    String publisher = temp.getString("publisher");
                    String writer = temp.getString("writer");
                    Favorite favorite = new Favorite();
                    favorite.setBook_img(book_img);
                    favorite.setBook_name(book_name);
                    favorite.setPublisher(publisher);
                    favorite.setWriter(writer);
                    favoriteAdapter.add(favorite);
                }
                //Log.e("[TEST]","리스폰스 끝?");
            }
            MypageActivity myActivity = (MypageActivity) this.activity;
            myActivity.setFavorite(favoriteAdapter.getCount());
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
