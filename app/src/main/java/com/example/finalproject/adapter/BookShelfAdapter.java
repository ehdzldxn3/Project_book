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
import com.example.finalproject.model.Member;
import com.example.finalproject.model.Record;

import org.w3c.dom.Text;

import java.util.List;

public class BookShelfAdapter extends ArrayAdapter {
    Activity activity;
    int resource;
    Member member;

    public BookShelfAdapter(@NonNull Context context, int resource, @NonNull List objects,Member member) {
        super(context, resource, objects);
        activity=(Activity)context;
        this.resource=resource;
        this.member = member;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(resource,null);
        }
        final Record item = (Record) this.getItem(position);
        ImageView imageView = convertView.findViewById(R.id.imageView);
        Glide.with(activity).load(item.getBook_img()).into(imageView);
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
        TextView book_name = convertView.findViewById(R.id.textView1);
        TextView book_start = convertView.findViewById(R.id.textView2);
        TextView book_end = convertView.findViewById(R.id.textView3);

        TextView isreading = convertView.findViewById(R.id.isreading);
        TextView donereading = convertView.findViewById(R.id.donereading);


        book_name.setText(item.getBook_name());
        book_start.setText("읽기 시작한 날짜 : "+item.getStart_time().substring(0,10));
        if(item.getIsreading()==0){
            book_end.setText("읽기 종료한 날짜 : "+item.getEnd_time().substring(0,10));
            isreading.setVisibility(View.GONE);
            donereading.setVisibility(View.VISIBLE);
        }else{
            book_end.setText("독서중");
            isreading.setVisibility(View.VISIBLE);
            donereading.setVisibility(View.GONE);
        }
        return convertView;
    }
}
