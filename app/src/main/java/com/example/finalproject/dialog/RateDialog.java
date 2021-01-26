package com.example.finalproject.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.finalproject.BookViewActivity;
import com.example.finalproject.R;

public class RateDialog implements View.OnClickListener {
    private Activity activity;
    String result = "";
    Dialog dig;
    public RateDialog(Activity activity) {
        this.activity = activity;
    }

    public void callDialog(String book_name){

        dig = new Dialog(activity);
        // 액티비티의 타이틀바를 숨긴다.
        dig.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dig.setContentView(R.layout.rate_dialog);
        //사이즈 설정
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dig.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dig.show();

        Window window = dig.getWindow();
        window.setAttributes(lp);


        TextView textView_title = dig.findViewById(R.id.textView21);
        textView_title.setText(book_name+"은 어떠셨나요?");
        LinearLayout button_good = dig.findViewById(R.id.button_good);
        LinearLayout button_neut = dig.findViewById(R.id.button_neut);
        LinearLayout button_bad = dig.findViewById(R.id.button_bad);
        TextView textView_cancel = dig.findViewById(R.id.textView25);
        button_good.setOnClickListener(this);
        button_neut.setOnClickListener(this);
        button_bad.setOnClickListener(this);
        textView_cancel.setOnClickListener(this);

    }

    public String getResult() {
        return result;
    }

    @Override
    public void onClick(View v) {
        BookViewActivity bookViewActivity = (BookViewActivity)activity;
        if(v.getId()==R.id.button_good){
            result="good";
            bookViewActivity.setBookGradeMember(result);
            dig.dismiss();
        }else if(v.getId()==R.id.button_neut){
            result="neut";
            bookViewActivity.setBookGradeMember(result);
            dig.dismiss();
        }else if(v.getId()==R.id.button_bad){
            result="bad";
            bookViewActivity.setBookGradeMember(result);
            dig.dismiss();
        }else if(v.getId()==R.id.textView25){
            dig.dismiss();
        }
    }
}
