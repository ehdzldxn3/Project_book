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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalproject.OnClicklistener.CustomOnClicklistener;
import com.example.finalproject.Response.SearchResultResponse;
import com.example.finalproject.Response.SearchTextListResponse;
import com.example.finalproject.adapter.BookAdapter;
import com.example.finalproject.adapter.GridViewAdapter;
import com.example.finalproject.adapter.SearchAdapter;
import com.example.finalproject.model.Book;
import com.example.finalproject.model.Member;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener {

    List<Book> list; //책검색 결과 리스트
    BookAdapter adapter; // 검색화면 어댑터
    //ListView listView;
    GridView gridView;

    //요청하는 클라이언트
    AsyncHttpClient client;
    //검색한 책 리스폰스
    SearchResultResponse searchResultResponse;
    //toplist일 경우 상위목록을 모두 불러줄 리스폰스 구현

    ImageButton button_top_search;

    String book_name;   //검색할 단어
    String urlSearchBookList = "http://"+MainActivity.IP_SET+":8080/ProjectBook/book_searchlist.do";  //검색한 책리스트

    Intent intent;  //인텐트
    Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        //전달받은 회원정보
        member = (Member) getIntent().getSerializableExtra("member");
        //전달받은 검색한 단어
        book_name = getIntent().getStringExtra("book_name");


        //검색버튼 추가
        button_top_search = findViewById(R.id.button_top_search);
        button_top_search.setOnClickListener(this);

        //프사설정
        //프사 동그랗게 바꾸기
        ImageView myPage=findViewById(R.id.myPage);
        CustomOnClicklistener listener = new CustomOnClicklistener("myPage",this,member);
        myPage.setOnClickListener(listener);
        GradientDrawable drawable = (GradientDrawable)getDrawable(R.drawable.background_rounding);
        myPage.setBackground(drawable);
        myPage.setClipToOutline(true);
        Glide.with(this).load(member.getMember_photo()).into(myPage);

        //리스트뷰관련
        list = new ArrayList<>();
        adapter = new BookAdapter(this, R.layout.search_result_item, list);
        gridView = findViewById(R.id.gridView);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

        //책제목이 toplist이면 상위 목록 불러오기

        if(book_name.equals("toplist")){
            client = new AsyncHttpClient();
            searchResultResponse = new SearchResultResponse(this,adapter);
            RequestParams params = new RequestParams();
            params.put("page",1);
            params.put("size",15);
            client.post("http://"+MainActivity.IP_SET+":8080/ProjectBook/top_index_list.do",params,searchResultResponse);

        }else{
            //통신
            client = new AsyncHttpClient();
            searchResultResponse = new SearchResultResponse(this, adapter);
            RequestParams params = new RequestParams();
            params.put("book_name", book_name);
            client.post(urlSearchBookList, params, searchResultResponse);
        }



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
        //북뷰액티비티로 넘어가기
        Book item = adapter.getItem(position);
        int book_id = item.getBook_id();
        intent = new Intent(this, BookViewActivity.class);
        intent.putExtra("book_id", book_id);
        intent.putExtra("member",member);
        startActivity(intent);
    }
}