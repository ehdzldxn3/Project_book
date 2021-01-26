package com.example.finalproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.model.Book;



import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    Activity activity;
    int resource;

    public BookAdapter(@NonNull Context context, int resource, @NonNull List<Book> objects) {

        super(context, resource, objects);
        activity = (Activity)context;
        this.resource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(resource, null);
        }

        Book item = getItem(position);

        if (item != null) {
            ImageView imageView = convertView.findViewById(R.id.imageView28);
            TextView textView1 = convertView.findViewById(R.id.textView35);
            TextView textView2 = convertView.findViewById(R.id.textView36);
            TextView textView3 = convertView.findViewById(R.id.textView37);


            Glide.with(convertView)                    // new를 하지 않았으니 Glide는 static으로 구성됨
                    .load(item.getBook_img())       // 단점 : 메모리 차지
                    .placeholder(R.drawable.ic_stub)// 불러올 동안 표시할 이미지 설정
                    .error(R.drawable.ic_error)     // 에러 발생시 표시할 이미지 설정
                    .fallback(R.drawable.ic_empty)  // url이 null일 때 표시할 이미지 설정
                    .into(imageView);
            textView1.setText(item.getBook_name());
            textView2.setText("지은이 : "+item.getWriter() );
            textView3.setText("출판사 : "+item.getPublisher());

        }
        return convertView;
    }
}
