package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.example.finalproject.OnClicklistener.CustomOnClicklistener;
import com.example.finalproject.Response.SearchResultResponse;
import com.example.finalproject.Response.TagSearchResponse;
import com.example.finalproject.adapter.BookAdapter;
import com.example.finalproject.model.Book;
import com.example.finalproject.model.Member;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class TagSearchResultActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    List<Book> list; //책검색 결과 리스트
    BookAdapter adapter; // 검색화면 어댑터
    AsyncHttpClient client; //요청하는 클라이언트
    TagSearchResponse tagSearchResponse; // 태그로 검색한 책 리스폰스
    //ListView listView;
    GridView gridView;
    ImageButton button_top_search;
    String tag="";

    String urlSearchTagResult = "http://"+MainActivity.IP_SET+":8080/ProjectBook/tag_select.do";  //검색한 책리스트

    Intent intent;  //인텐트
    Member member;

    //회원 프사
    ImageButton myPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_search_result);

        //인텐트로 회원정보와 어떤태그인지 넘겨받는다.
        tag = getIntent().getStringExtra("tag");
        member = (Member) getIntent().getSerializableExtra("member");
        //받아온 태그 텍스 골라주기
        tag = tagText(tag);

        member = (Member) getIntent().getSerializableExtra("member");
        list = new ArrayList<>();
        adapter = new BookAdapter(this, R.layout.search_result_item, list);
        client = new AsyncHttpClient();


        gridView = findViewById(R.id.gridView);
        button_top_search = findViewById(R.id.button_top_search);
        button_top_search.setOnClickListener(this);

        tagSearchResponse = new TagSearchResponse(this, adapter);
        RequestParams params = new RequestParams();
        params.put("tag", tag);
        client.post(urlSearchTagResult, params, tagSearchResponse);
        Log.e("태그서치리절트", tag);

        //프사 동그랗게 바꾸기
        myPage=findViewById(R.id.myPage);
        CustomOnClicklistener listener = new CustomOnClicklistener("myPage",this,member);
        myPage.setOnClickListener(listener);
        GradientDrawable drawable = (GradientDrawable)getDrawable(R.drawable.background_rounding);
        myPage.setBackground(drawable);
        myPage.setClipToOutline(true);
        Glide.with(this).load(member.getMember_photo()).into(myPage);

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    //태그 텍스트 바꿔주기
    private String tagText(String tag) {

        if(tag.equals("시")) {
            tag = "genre_poet";
        } else if (tag.equals("에세이")) {
            tag = "genre_essay";
        } else if (tag.equals("자기계발")) {
            tag = "genre_selfDev";
        } else if (tag.equals("역사")) {
            tag = "genre_history";
        } else if (tag.equals("과학")) {
            tag = "genre_science";
        } else if (tag.equals("소설")) {
            tag = "genre_novel";
        } else if (tag.equals("만화")) {
            tag = "genre_comics";
        } else if (tag.equals("예술")) {
            tag = "genre_art";
        }
        return tag;
    }

    @Override
    public void onClick(View v) {
        //검색버튼
        Intent intent = new Intent(this,SearchActivity.class);
        intent.putExtra("member",member);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Book item = adapter.getItem(position);
        int book_id = item.getBook_id();
        intent = new Intent(this, BookViewActivity.class);
        intent.putExtra("book_id", book_id);
        intent.putExtra("member",member);
        startActivity(intent);
    }
}