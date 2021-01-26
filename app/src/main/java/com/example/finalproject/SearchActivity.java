package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.Response.SearchSetResponse;
import com.example.finalproject.Response.SearchTextListResponse;
import com.example.finalproject.adapter.RecyclerViewAdapter;
import com.example.finalproject.adapter.SearchAdapter;
import com.example.finalproject.model.Book;
import com.example.finalproject.model.Member;
import com.example.finalproject.model.Search;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity
        implements View.OnClickListener {
    TextView textView1,textView2,textView3,textView4,textView5,textView6,textView7,textView8;

    List<Search> list;    //검색항 항목들 저장할 리스트
    Button button;          //검색하러갈 버튼
    EditText editSearch;    //검색할 항목들 적을 에딧텍스트
    ListView listView;  //검색한 항목들 리스트
    SearchAdapter adapter; //검색한 항목 어댑터

    Member member;
    //String member_id; //아이디
    String keyword; //검색한 항목

    AsyncHttpClient client;                 //요청하는 클라이언트
    SearchSetResponse searchSetResponse;    //검색어 저장 리스폰스
    SearchTextListResponse searchTextListResponse; //검색했던 리스트 리스폰스

    String urlSearhTextList = "http://"+MainActivity.IP_SET+":8080/ProjectBook/searchList.do";  //검색 리스트
    String urlSetText = "http://"+MainActivity.IP_SET+":8080/ProjectBook/searchInsert.do";    //검색한 항목 저장

    ImageView image_back;//뒤로가기 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        client = new AsyncHttpClient();

        listView = findViewById(R.id.listView);
        button = findViewById(R.id.button);
        editSearch = findViewById(R.id.editSearch);
        image_back = findViewById(R.id.image_back);


        member = (Member) getIntent().getSerializableExtra("member");
        list = new ArrayList<>();
        adapter = new SearchAdapter(this, R.layout.search_str_item, list,editSearch,member);

        image_back.setOnClickListener(this);
        button.setOnClickListener(this);

        //태그 검색버튼
        textView1=findViewById(R.id.textView_tag2);
        textView2=findViewById(R.id.textView_tag3);
        textView3=findViewById(R.id.textView_tag4);
        textView4=findViewById(R.id.textView_tag5);
        textView5=findViewById(R.id.textView_tag6);
        textView6=findViewById(R.id.textView_tag7);
        textView7=findViewById(R.id.textView_tag8);
        textView8=findViewById(R.id.textView_tag9);
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        textView4.setOnClickListener(this);
        textView5.setOnClickListener(this);
        textView6.setOnClickListener(this);
        textView7.setOnClickListener(this);
        textView8.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        adapter.clear();
        loginSearch();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button){
            keyword = editSearch.getText().toString().trim();
            searchSet(keyword, member.getMember_id());
            search(keyword);
        }else if(v.getId()==R.id.image_back){
            this.finish();
        }else if(v.getId()==R.id.textView_tag2){
            Intent intent = new Intent(this, TagSearchResultActivity.class);
            intent.putExtra("tag","genre_poet");
            intent.putExtra("member",member);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else if(v.getId()==R.id.textView_tag3){
            Intent intent = new Intent(this, TagSearchResultActivity.class);
            intent.putExtra("tag","genre_essay");
            intent.putExtra("member",member);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else if(v.getId()==R.id.textView_tag4){
            Intent intent = new Intent(this, TagSearchResultActivity.class);
            intent.putExtra("tag","genre_selfDev");
            intent.putExtra("member",member);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else if(v.getId()==R.id.textView_tag5){
            Intent intent = new Intent(this, TagSearchResultActivity.class);
            intent.putExtra("tag","genre_history");
            intent.putExtra("member",member);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else if(v.getId()==R.id.textView_tag6){
            Intent intent = new Intent(this, TagSearchResultActivity.class);
            intent.putExtra("tag","genre_science");
            intent.putExtra("member",member);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else if(v.getId()==R.id.textView_tag7){
            Intent intent = new Intent(this, TagSearchResultActivity.class);
            intent.putExtra("tag","genre_novel");
            intent.putExtra("member",member);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else if(v.getId()==R.id.textView_tag8){
            Intent intent = new Intent(this, TagSearchResultActivity.class);
            intent.putExtra("tag","genre_comics");
            intent.putExtra("member",member);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else if(v.getId()==R.id.textView_tag9){
            Intent intent = new Intent(this, TagSearchResultActivity.class);
            intent.putExtra("tag","genre_art");
            intent.putExtra("member",member);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }


    //검색히스토리 띄우기
    public void loginSearch() {
        RequestParams params = new RequestParams();
        params.put("member_id",member.getMember_id());
        searchTextListResponse = new SearchTextListResponse(this, adapter);
        client.post(urlSearhTextList, params, searchTextListResponse);
        listView.setAdapter(adapter);
    }

    //검색하는 함수
    public void search(String keyword) {
        if (!keyword.equals("")) {
            Intent intent = new Intent(this, SearchResultActivity.class);
            intent.putExtra("book_name", keyword);
            intent.putExtra("member",member);
            startActivity(intent);
        } else {
            Toast.makeText(this, "검색어를 입력해라 시파새캬", Toast.LENGTH_SHORT).show();
        }
    }

    //검색어 저장하는 함수
    public void searchSet(String keyword, String member_id) {
        searchSetResponse = new SearchSetResponse(this, member_id, keyword);
        RequestParams params = new RequestParams();
        params.put("member_id", member.getMember_id());
        params.put("search", keyword);
        client.post(urlSetText, params, searchSetResponse);
    }

}