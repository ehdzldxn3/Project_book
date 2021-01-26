package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalproject.Response.WriteReviewResponse;
import com.example.finalproject.model.Book;
import com.example.finalproject.model.BookandGrade;
import com.example.finalproject.model.Grade;
import com.example.finalproject.model.Member;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

public class WriteReviewActivity extends AppCompatActivity implements View.OnClickListener {
    //책이미지 가져올 앞주소
    String book_img_url = "http://"+MainActivity.IP_SET+":8080/ProjectBook/storage/";
    Member member;
    BookandGrade bookandGrade;
    //회원관련
    ImageView img_profile;
    TextView textView_nick;
    //책관련
    ImageView img_book;
    TextView textView_bookname,textView_writer,textView_publisher;
    //점수관련
    TextView textView_good,textView_neut,textView_bad,textView_total;
    //입력완료버튼
    TextView button_submit;
    //내용
    EditText editText_content;
    //통신용
    AsyncHttpClient client;
    WriteReviewResponse response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);
        member = (Member) getIntent().getSerializableExtra("member");
        bookandGrade = (BookandGrade) getIntent().getSerializableExtra("bookandGrade");
        //회원
        img_profile = findViewById(R.id.img_profile);
        textView_nick = findViewById(R.id.textView_nick);
        //책
        img_book=findViewById(R.id.imageView_book);
        textView_bookname =findViewById(R.id.textView_book_name);
        textView_writer=findViewById(R.id.textView_writer);
        textView_publisher=findViewById(R.id.textView_publisher);
        //점수
        textView_good = findViewById(R.id.textView_good);
        textView_neut = findViewById(R.id.textView_neut);
        textView_bad = findViewById(R.id.textView_bad);
        textView_total = findViewById(R.id.textView_total);
        //내용
        editText_content = findViewById(R.id.editText_content);
        //완료버튼
        button_submit = findViewById(R.id.textView26);
        button_submit.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //회원
        GradientDrawable drawable = (GradientDrawable)getDrawable(R.drawable.background_rounding);
        img_profile.setBackground(drawable);
        img_profile.setClipToOutline(true);
        Glide.with(this).load(member.getMember_photo()).into(img_profile);
        textView_nick.setText(member.getMember_nick());
        //책
        Glide.with(this).load(book_img_url+bookandGrade.getBook_img()).into(img_book);
        textView_bookname.setText(bookandGrade.getBook_name());
        textView_writer.setText("작가 : "+bookandGrade.getWriter());
        textView_publisher.setText("출판사 : "+bookandGrade.getPublisher());
        //점수
        textView_good.setText("강추해요 "+bookandGrade.getBook_good());
        textView_neut.setText("볼만해요 "+bookandGrade.getBook_neut());
        textView_bad.setText("비추해요 "+bookandGrade.getBook_bad());
        textView_total.setText(String.valueOf(bookandGrade.getBook_good()-bookandGrade.getBook_bad()));
    }

    @Override
    public void onClick(View v) {
        if(editText_content.equals("")){
            Toast.makeText(this,"내용을 입력해 주세요",Toast.LENGTH_SHORT).show();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(bookandGrade.getBook_name()+" 작성 완료하셨나요?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    submit();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    public void submit(){
        //리스폰스 구현
        client = new AsyncHttpClient();
        response = new WriteReviewResponse(this);
        RequestParams params = new RequestParams();
        params.put("member_id",member.getMember_id());
        params.put("book_id",bookandGrade.getBook_id());
        String review_content = editText_content.getText().toString().trim();
        params.put("review_content",review_content);
        client.post("http://"+MainActivity.IP_SET+":8080/ProjectBook/write_review.do",params,response);
    }
}