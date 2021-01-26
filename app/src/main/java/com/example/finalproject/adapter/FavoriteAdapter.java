package com.example.finalproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.finalproject.BookViewActivity;
import com.example.finalproject.R;
import com.example.finalproject.model.Favorite;
import com.example.finalproject.model.Member;

import java.util.List;

public class FavoriteAdapter extends ArrayAdapter {
    int resource;
    Activity activity;
    Member member;
    public FavoriteAdapter(@NonNull Context context, int resource, @NonNull List objects, Member member) {
        super(context, resource, objects);
        activity=(Activity)context;
        this.resource=resource;
        this.member = member;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView=activity.getLayoutInflater().inflate(resource,null);
        }
        final Favorite item = (Favorite) this.getItem(position);
        ImageView imageView = convertView.findViewById(R.id.imageView);
        Glide.with(activity).load(item.getBook_img()).into(imageView);
        TextView textView1 = convertView.findViewById(R.id.textView1);
        TextView textView2 = convertView.findViewById(R.id.textView2);
        TextView textView3 = convertView.findViewById(R.id.textView3);
        //북뷰로 넘어가기
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, BookViewActivity.class);
                intent.putExtra("book_id",item.getBook_id());
                intent.putExtra("member",member);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intent);
            }
        });

        textView1.setText(item.getBook_name());
        textView2.setText("지은이 : "+item.getWriter());
        textView3.setText("출판사 : "+item.getPublisher());
        return convertView;
   }
}
