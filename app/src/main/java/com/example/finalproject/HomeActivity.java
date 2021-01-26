package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalproject.OnClicklistener.CustomOnClicklistener;
import com.example.finalproject.OnClicklistener.FotterOnClicklistener;
import com.example.finalproject.Response.HomeResponse;
import com.example.finalproject.Response.WriteReviewListResponse;
import com.example.finalproject.adapter.HomeAdapter;
import com.example.finalproject.adapter.WriteReviewAdapter;
import com.example.finalproject.model.BookandGrade;
import com.example.finalproject.model.Member;
import com.example.finalproject.model.Review;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements AbsListView.OnScrollListener, View.OnClickListener, AdapterView.OnItemClickListener {
    //로그인 회원정보
    Member member;
    //상단바 검색버튼
    ImageButton button_top_search;
    //회원 프사
    ImageButton myPage;
    //리뷰쓰기버튼
    TextView button_write_review;
    LinearLayout layout_bookShelf;
    ListView listView_write;
    List<BookandGrade> write_list;
    WriteReviewAdapter writeReviewAdapter;
    //SNS글목록 들어갈 리스트뷰
    ListView listView_home;
    //하단바
    ImageButton button_footer1,button_footer2,button_footer4,button_footer5;

    List<Review> list;
    HomeAdapter adapter;
    //통신
    AsyncHttpClient client;
    HomeResponse response;


    //스크롤이 최상단일때 리뷰쓰기버튼 보이게하기
    boolean firstItemVisibleFlag = true;
    TextView textView_write_review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        member= (Member) intent.getSerializableExtra("member");

        //검색버튼
        button_top_search = findViewById(R.id.button_top_search);
        button_top_search.setOnClickListener(this);

        //프사 동그랗게 바꾸기
        myPage=findViewById(R.id.myPage);
        CustomOnClicklistener listener = new CustomOnClicklistener("myPage",this,member);
        myPage.setOnClickListener(listener);
        GradientDrawable drawable = (GradientDrawable)getDrawable(R.drawable.background_rounding);
        myPage.setBackground(drawable);
        myPage.setClipToOutline(true);
        Glide.with(this).load(member.getMember_photo()).into(myPage);

        //리뷰쓰기버튼
        button_write_review = findViewById(R.id.textView7);
        button_write_review.setOnClickListener(this);
        layout_bookShelf = findViewById(R.id.layout_bookShelf);
        listView_write =findViewById(R.id.listView_write);
        write_list = new ArrayList<BookandGrade>();
        writeReviewAdapter = new WriteReviewAdapter(this,R.layout.favorite_item,write_list);
        listView_write.setAdapter(writeReviewAdapter);
        listView_write.setOnItemClickListener(this);

        //여기서부터 하단바
        button_footer1=findViewById(R.id.button_fotter_home);
        button_footer2=findViewById(R.id.button_fotter_rec);
        button_footer4=findViewById(R.id.button_fotter_diary);
        button_footer5=findViewById(R.id.button_fotter_my);

        FotterOnClicklistener fotterOnClicklistener = new FotterOnClicklistener(this,member);
        button_footer1.setEnabled(false);
        button_footer2.setOnClickListener(fotterOnClicklistener);
        button_footer4.setOnClickListener(fotterOnClicklistener);
        button_footer5.setOnClickListener(fotterOnClicklistener);

        //리스트뷰 관련
        listView_home = findViewById(R.id.listVIew_home);

        list= new ArrayList<Review>();
        adapter = new HomeAdapter(this,R.layout.review_item,list,member);

        //리뷰쓰기버튼 보이기 관련
        textView_write_review = findViewById(R.id.textView7);
        listView_home.setOnScrollListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        adapter.clear();
        client = new AsyncHttpClient();
        response = new HomeResponse(this,adapter,listView_home);
        RequestParams params = new RequestParams();
        params.add("page", String.valueOf(1));
        params.add("size", String.valueOf(5));
        client.post("http://"+MainActivity.IP_SET+":8080/ProjectBook/reviewList.do",params,response);

        //리뷰쓰기버튼용 리스트뷰
        write_list.clear();
        writeReviewAdapter.clear();
        AsyncHttpClient client2 = new AsyncHttpClient();
        WriteReviewListResponse response2 = new WriteReviewListResponse(this,writeReviewAdapter);
        RequestParams params2 = new RequestParams();
        params2.put("member_id",member.getMember_id());
        client2.post("http://"+MainActivity.IP_SET+":8080/ProjectBook/get_bookshelf.do",params2,response2);

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState==SCROLL_STATE_IDLE&&firstItemVisibleFlag){
            //여기에 리뷰보기버튼 보이게
            textView_write_review.setVisibility(View.VISIBLE);
        }else{
            textView_write_review.setVisibility(View.GONE);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(firstVisibleItem!=0){
            firstItemVisibleFlag=false;
        }else{
            firstItemVisibleFlag=true;
            textView_write_review.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.textView7){
            //리뷰쓰기버튼 구현
            if(layout_bookShelf.getVisibility()==View.VISIBLE){
                layout_bookShelf.setVisibility(View.GONE);
                button_write_review.setText("리뷰 쓰기");
            }else{
                layout_bookShelf.setVisibility(View.VISIBLE);
                button_write_review.setText("내 서재");
            }
        }else if(v.getId()==R.id.button_top_search){
            Intent intent = new Intent(this,SearchActivity.class);
            intent.putExtra("member",member);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,WriteReviewActivity.class);
        intent.putExtra("member",member);
        BookandGrade bookandGrade = (BookandGrade) writeReviewAdapter.getItem(position);
        intent.putExtra("bookandGrade",bookandGrade);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}