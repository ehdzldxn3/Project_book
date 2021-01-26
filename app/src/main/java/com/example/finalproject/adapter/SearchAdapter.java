package com.example.finalproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finalproject.R;
import com.example.finalproject.SearchResultActivity;
import com.example.finalproject.model.Member;
import com.example.finalproject.model.Search;

import java.util.List;

public class SearchAdapter extends ArrayAdapter<Search> {

    Activity activity;
    int resource;
    EditText editText;
    Member member;

    public SearchAdapter(@NonNull Context context, int resource, @NonNull List<Search> objects,EditText editText,Member member) {
        super(context, resource, objects);
        activity = (Activity)context;
        this.resource = resource;
        this.editText=editText;
        this.member = member;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(resource, null);
        }

        Search item = getItem(position);

        if (item != null) {
            TextView textView = convertView.findViewById(R.id.textView);
            textView.setText(item.getSearch_text());
            ImageView imageView1 = convertView.findViewById(R.id.imageView1);
            ImageView imageView2 = convertView.findViewById(R.id.imageView2);

            CustomOnItemClicklistener onItemClicklistener= new CustomOnItemClicklistener(item.getSearch_text(),editText);
            imageView1.setOnClickListener(onItemClicklistener);
            imageView2.setOnClickListener(onItemClicklistener);
        }
        return convertView;
    }

    class CustomOnItemClicklistener implements View.OnClickListener{

        String keyword;
        EditText editText;

        public CustomOnItemClicklistener(String keyword,EditText editText) {
            this.keyword = keyword;
            this.editText=editText;
        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.imageView2){
                editText.setText(keyword);
            }else if(v.getId()==R.id.imageView1){
                Intent intent = new Intent(activity, SearchResultActivity.class);
                intent.putExtra("book_name",keyword);
                intent.putExtra("member",member);
                activity.startActivity(intent);
            }
        }
    }

}
