package com.example.finalproject.OnClicklistener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.finalproject.MainActivity;
import com.example.finalproject.SearchActivity;
import com.example.finalproject.model.Member;

public class SearchOnClicklistener implements View.OnClickListener {
    Activity activity;

    public SearchOnClicklistener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Member member = new Member();
        Intent intent = new Intent(activity, SearchActivity.class);
        String member_id = member.getMember_id();   //로그인 화면에서 아이디 받아오기
        intent.putExtra("member_id", member_id);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //Log.e("[Main]", member_id);
        activity.startActivity(intent);
    }
}
