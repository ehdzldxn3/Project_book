package com.example.finalproject.OnClicklistener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.finalproject.DiaryActivity;
import com.example.finalproject.HomeActivity;
import com.example.finalproject.MainActivity;
import com.example.finalproject.MypageActivity;
import com.example.finalproject.R;
import com.example.finalproject.model.Member;

import java.io.Serializable;

public class FotterOnClicklistener implements View.OnClickListener {

    Activity activity;
    Member member;

    public FotterOnClicklistener(Activity activity, Member member) {
        this.activity = activity;
        this.member = member;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.button_fotter_home){//홈버튼
            Intent intent = new Intent(activity, HomeActivity.class);
            intent.putExtra("member",(Serializable)member);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
        }else if (v.getId()==R.id.button_fotter_rec) {//추천버튼
            Intent intent = new Intent(activity, MainActivity.class);
            intent.putExtra("member",(Serializable)member);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
        } else if (v.getId() == R.id.button_fotter_diary) {// 내기록
            Intent intent = new Intent(activity, DiaryActivity.class);
            intent.putExtra("member",(Serializable)member);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);

        } else if (v.getId() == R.id.button_fotter_my) {//마이페이지
            Intent intent;
            intent = new Intent(activity, MypageActivity.class);
            intent.putExtra("member",member);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
        }
    }
}
