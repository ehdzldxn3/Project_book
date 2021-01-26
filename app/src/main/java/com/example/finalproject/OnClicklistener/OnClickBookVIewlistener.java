package com.example.finalproject.OnClicklistener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.finalproject.BookViewActivity;
import com.example.finalproject.model.Member;

public class OnClickBookVIewlistener implements View.OnClickListener {
    Activity activity;
    int book_id;
    Member member;

    public OnClickBookVIewlistener(Activity activity, int book_id, Member member) {
        this.activity = activity;
        this.book_id = book_id;
        this.member = member;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity, BookViewActivity.class);
        intent.putExtra("book_id",book_id);
        intent.putExtra("member",member);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public void setBook_id(int book_id){
        this.book_id=book_id;
    }
}
