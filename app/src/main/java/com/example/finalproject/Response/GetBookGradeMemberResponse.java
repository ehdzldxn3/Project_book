package com.example.finalproject.Response;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.BookViewActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class GetBookGradeMemberResponse extends AsyncHttpResponseHandler {
    Activity activity;
    boolean isGradedFlag;
    TextView textView_graded;

    public GetBookGradeMemberResponse(Activity activity, TextView textView_graded) {
        this.activity = activity;
        this.textView_graded = textView_graded;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String str = new String(responseBody);
        Log.e("[TEST]",str+"평가완료했는지 결과");
        BookViewActivity bookViewActivity = (BookViewActivity) activity;
        try {
            JSONObject json = new JSONObject(str);
            String rt = json.getString("rt");
            if(rt.equals("OK")){
                //평가완료헀음
                bookViewActivity.setGradedFlag(true);
                textView_graded.setText("평가완료");
            }else{
                bookViewActivity.setGradedFlag(false);
                textView_graded.setText("평가하기");
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
