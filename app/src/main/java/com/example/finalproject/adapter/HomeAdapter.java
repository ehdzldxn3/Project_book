package com.example.finalproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.finalproject.MainActivity;
import com.example.finalproject.OnClicklistener.OnClickBookVIewlistener;
import com.example.finalproject.R;
import com.example.finalproject.model.Member;
import com.example.finalproject.model.Review;

import java.util.List;

public class HomeAdapter extends ArrayAdapter<Review>{
    Activity activity;
    int resource;
    TextView textView_content;
    Member member;

    public HomeAdapter(@NonNull Context context, int resource, @NonNull List objects, Member member) {
        super(context, resource, objects);
        this.resource=resource;
        activity=(Activity)context;
        this.member = member;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView=activity.getLayoutInflater().inflate(resource,null);
        }
        Review item = getItem(position);
        ImageView profile = convertView.findViewById(R.id.img_profile);
        TextView textView_nick = convertView.findViewById(R.id.textView_nick);
        TextView textView_log = convertView.findViewById(R.id.textView_log);
        textView_content = convertView.findViewById(R.id.textView_content);
        ImageView book_img = convertView.findViewById(R.id.imageView_book);
        TextView textView_book_name = convertView.findViewById(R.id.textView_book_name);
        TextView textView_writer= convertView.findViewById(R.id.textView_writer);
        TextView textView_publisher = convertView.findViewById(R.id.textView_publisher);

        if(item.getMember_photo().equals("profile.jpg")){
            Glide.with(activity).load("http://"+ MainActivity.IP_SET +":8080/ProjectBook/storage/"+item.getMember_photo()).into(profile);

        }else{
            Glide.with(activity).load("http://"+ MainActivity.IP_SET +":8080/ProjectBook/storage/"+item.getMember_id()+"/"+item.getMember_photo()).into(profile);
        }
        //프로필 동그랗게
        GradientDrawable drawable = (GradientDrawable)activity.getDrawable(R.drawable.background_rounding);
        profile.setBackground(drawable);
        profile.setClipToOutline(true);
        // 이름 시간 내용
        textView_nick.setText(item.getMember_nick());
        textView_log.setText(item.getLogtime());
        String content = item.getReview_content();
        textView_content.setText(content);
        //책사진 작가 출판사
        Glide.with(activity).load("http://"+ MainActivity.IP_SET +":8080/ProjectBook/storage/"+item.getBook_img()).into(book_img);
        //책사진 눌렀을떄 북뷰로 이동
        OnClickBookVIewlistener listener = new OnClickBookVIewlistener(activity,item.getBook_id(),member);
        book_img.setOnClickListener(listener);

        textView_book_name.setText(item.getBook_name());
        textView_writer.setText("작가 : "+item.getWriter());
        textView_publisher.setText("출판사 : "+item.getPublisher());

        //점수
        TextView textView_good = convertView.findViewById(R.id.textView_good);
        TextView textView_bad = convertView.findViewById(R.id.textView_bad);
        TextView textView_neut = convertView.findViewById(R.id.textView_neut);
        textView_good.setText("강추해요 "+item.getBook_good());
        textView_bad.setText("비추해요 "+item.getBook_bad());
        textView_neut.setText("볼만해요 "+item.getBook_neut());
        TextView textView_total = convertView.findViewById(R.id.textView_total);
        textView_total.setText(String.valueOf(item.getBook_good()-item.getBook_bad()));

        return convertView;
    }

}
