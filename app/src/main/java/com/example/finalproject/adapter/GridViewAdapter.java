package com.example.finalproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.finalproject.R;

import java.util.List;

public class GridViewAdapter extends ArrayAdapter {
        Activity activity;
        int resource;
        String[] month_history;

        public GridViewAdapter(@NonNull Context context, int resource, @NonNull List objects) {
            super(context, resource, objects);
            activity=(Activity)context;
            this.resource = resource;
        }

        public void setMonth_history(String[] month_history) {
            this.month_history = month_history;
//            for(int k=0;k<month_history.length;k++){
//                Log.e("[TEST]",k+""+month_history[k]);
//            }
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView==null){
                convertView=activity.getLayoutInflater().inflate(resource,null);
            }
            TextView textView = convertView.findViewById(R.id.tv_item_gridview);
            textView.setText(String.valueOf(this.getItem(position)));
            textView.setGravity(Gravity.LEFT);

            LinearLayout layout = convertView.findViewById(R.id.layout);
            layout.removeAllViews();
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(textView,0);
            layout.setMinimumHeight(300);

            int date = 0;

            if(position==7||position==14||position==21||position==28||position==35){
                textView.setTextColor(Color.parseColor("#E95061"));
            }else if(position==13||position==20||position==27||position==34){
                textView.setTextColor(Color.parseColor("#3D85B5"));
            }


            if(position<7){
                textView.setGravity(Gravity.CENTER);
                textView.setTextAppearance(R.style.TextAppearance_AppCompat_Medium);
                textView.setBackgroundColor(ContextCompat.getColor(activity,R.color.subcolor));
                textView.setTextColor(Color.parseColor("#FFFFFF"));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
                layout.setMinimumHeight(60);
            }else if(!getItem(position).equals("")){
                date = Integer.parseInt(String.valueOf(this.getItem(position)));
                //이걸로 일정 추가
                if (date != 0) {
                    if(!month_history[date].equals("")&&month_history[date]!=null){
                        String[] str = month_history[date].split("/");
                        for(int i=0;i<str.length;i++){
                            LinearLayout temp = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.calendar_read_history,null);
                            TextView textView1=temp.findViewById(R.id.textView1);
                            textView1.setText(str[i]);
                            textView1.setLines(1);
                            textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
                            textView1.setTextColor(Color.parseColor("#FFFFFF"));
                            if(i%2==0){
                                textView1.setBackgroundColor(ContextCompat.getColor(activity,R.color.maincolor));
                            }else if(i%2==1){
                                textView1.setBackgroundColor(ContextCompat.getColor(activity,R.color.subcolor));
                            }
                            layout.addView(temp,i+1);
                        }
                    }
                }
            }


            convertView.setBackground(activity.getDrawable(R.drawable.background_calendar_item));
            return convertView;
        }
}