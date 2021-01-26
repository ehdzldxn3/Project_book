package com.example.finalproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.finalproject.MainActivity;
import com.example.finalproject.R;
import com.example.finalproject.model.BookandGrade;

import java.util.List;

public class WriteReviewAdapter extends ArrayAdapter {
    Activity activity;
    int resource;
    String book_img_url = "http://"+ MainActivity.IP_SET+":8080/ProjectBook/storage/";
    public WriteReviewAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        activity = (Activity) context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView = activity.getLayoutInflater().inflate(resource,null);
        }
        BookandGrade item = (BookandGrade) this.getItem(position);
        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textView1 = convertView.findViewById(R.id.textView1);
        TextView textView2 = convertView.findViewById(R.id.textView2);
        TextView textView3 = convertView.findViewById(R.id.textView3);
        Glide.with(activity).load(book_img_url+item.getBook_img()).into(imageView);
        textView1.setText(item.getBook_name());
        textView2.setText("지은이 : "+item.getWriter());
        textView3.setText("출판사 : "+item.getPublisher());
        return convertView;
    }
}
