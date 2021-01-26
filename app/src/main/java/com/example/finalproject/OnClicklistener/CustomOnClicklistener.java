package com.example.finalproject.OnClicklistener;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.finalproject.LoginActivity;
import com.example.finalproject.MainActivity;
import com.example.finalproject.MypageActivity;
import com.example.finalproject.SearchActivity;
import com.example.finalproject.SearchResultActivity;
import com.example.finalproject.TagSearchResultActivity;
import com.example.finalproject.model.Member;

public class CustomOnClicklistener implements View.OnClickListener{
    Activity activity;
    //키워드를 받아와서(장르) 그에따른 검색페이지로 안내해주기위해 커스텀 버튼 리스너 만들었음
    String keyword;
    Member member;
    public CustomOnClicklistener(String keyword,Activity activity,Member member) {
        this.keyword=keyword;
        this.activity=activity;
        this.member = member;
    }

    @Override
    public void onClick(View v) {
        Log.e("[TEST]","키워드는"+keyword);
        if(keyword.equals("myPage")){
            Log.e("[TEST]","진입테스트1");
            //마이페이지 넘어가는거
            Intent intent = new Intent(activity, MypageActivity.class);
            intent.putExtra("member",member);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
        }else {
            Log.e("[TEST]","진입테스트1");
            Intent intent = new Intent(activity, TagSearchResultActivity.class);
            intent.putExtra("tag",keyword);
            intent.putExtra("member",member);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
        }
    }
}