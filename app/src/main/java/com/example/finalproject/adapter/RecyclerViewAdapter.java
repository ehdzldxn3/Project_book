package com.example.finalproject.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.BookViewActivity;
import com.example.finalproject.R;
import com.example.finalproject.model.Book;
import com.example.finalproject.model.Member;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.HorizontalViewHolder> {

    Activity activity;
    Member member;

    public RecyclerViewAdapter(Activity activity,Member member) {
        this.activity = activity;
        this.member = member;
    }

    private ArrayList<Book> dataList;

    public void setData(ArrayList<Book> list) {
        dataList = list;
    }

    public void add(Book item){
        dataList.add(item);
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 사용할 아이템의 뷰를 생성해준다.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);

        HorizontalViewHolder holder = new HorizontalViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(HorizontalViewHolder holder, int position) {
        final Book item = dataList.get(position);
        Glide.with(activity).load(item.getBook_img()).error(R.mipmap.ic_launcher).into(holder.icon);
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, BookViewActivity.class);
                intent.putExtra("book_id",item.getBook_id());
                intent.putExtra("member",member);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class HorizontalViewHolder extends RecyclerView.ViewHolder {

        public ImageView icon;

        public HorizontalViewHolder(View itemView) {
            super(itemView);

            icon = (ImageView) itemView.findViewById(R.id.horizon_icon);
        }
    }

    public void clear(){
        int size = dataList.size();
        dataList.clear();
        notifyDataSetChanged();
    }
}