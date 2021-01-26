package com.example.finalproject.Response;

import android.util.Log;
import android.widget.Toast;

import com.example.finalproject.MemberFindActivity;
import com.example.finalproject.model.Member;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MemberFindPwResponse extends AsyncHttpResponseHandler {

    MemberFindActivity activity;

    public MemberFindPwResponse(MemberFindActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String str = new String(responseBody);
        try {
            JSONObject json = new JSONObject(str);
            String rt = json.getString("rt");
            if (rt.equals("OK")) {
                final Member member = new Member();
                member.setMember_email(json.getString("member_email"));
                member.setMember_pw(json.getString("member_pw"));
                member.setMember_id(json.getString("member_id"));
                new Thread(){
                    @Override
                    public void run() {
                        activity.send_findPw(member);
                    }
                }.start();
            } else if(rt.equals("FAIL")){
                Toast.makeText(activity, "가입된 아이디가 없습니다. ",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Log.e("MemberFindIdResponse", "통신실패" );
    }
}
