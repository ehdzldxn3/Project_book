package com.example.finalproject.Response;

import android.app.Activity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.finalproject.adapter.HomeAdapter;
import com.example.finalproject.model.Review;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class HomeResponse extends AsyncHttpResponseHandler {
    Activity activity;
    HomeAdapter adapter;
    // List<Review> list;
    ListView listView;
    public HomeResponse(Activity activity, HomeAdapter adapter,ListView listView) {
        this.activity = activity;
        this.adapter = adapter;
        //this.list = list;
        this.listView = listView;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String response = new String(responseBody);
        Log.e("[TEST]",response);
        try {
            JSONObject json = new JSONObject(response);
            String rt = json.getString("rt");
            if(rt.equals("OK")){
                JSONArray item = json.getJSONArray("item");
                for(int i=0;i<item.length();i++){
                    JSONObject temp = item.getJSONObject(i);
                    Review review = new Review();
                    review.setReview_id(temp.getInt("review_id"));
                    review.setBook_bad(temp.getInt("book_bad"));
                    review.setBook_good(temp.getInt("book_good"));
                    review.setBook_img(temp.getString("book_img"));
                    review.setBook_name(temp.getString("book_name"));
                    review.setBook_neut(temp.getInt("book_neut"));
                    review.setLogtime(temp.getString("logtime"));
                    review.setMember_nick(temp.getString("member_nick"));
                    review.setMember_photo(temp.getString("member_photo"));
                    review.setPublisher(temp.getString("publisher"));
                    review.setReview_content(temp.getString("review_content"));
                    review.setReview_likes(temp.getInt("review_likes"));
                    review.setWriter(temp.getString("writer"));
                    review.setMember_id(temp.getString("member_id"));
                    review.setBook_id(temp.getInt("book_id"));

                    adapter.add(review);

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        Toast.makeText(activity,"통신 오류입니다",Toast.LENGTH_SHORT).show();
        String str = new String(responseBody);
        Log.e("[TEST]",str);
    }
}
