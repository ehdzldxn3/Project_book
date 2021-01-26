package com.example.finalproject.Response;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.finalproject.DiaryActivity;
import com.example.finalproject.MypageActivity;
import com.example.finalproject.model.Member;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class GetMemberResponse extends AsyncHttpResponseHandler {
    Activity activity;
    Member member;

    public GetMemberResponse(Activity activity,String type) {
        this.activity = activity;
    }



    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String response = new String(responseBody);
        Log.e("[TEST]",response);
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
                    member = new Member();
                    member.setMember_id(temp.getString("member_id"));
                    member.setMember_phone(temp.getString("member_phone"));
                    member.setMember_nick(temp.getString("member_nick"));
                    member.setMember_genre(temp.getString("member_genre"));
                    member.setMember_photo(temp.getString("member_photo"));
                    member.setMember_pw(temp.getString("member_pw"));
                }
                Log.e("[TEST]","리스폰스 끝?");
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
