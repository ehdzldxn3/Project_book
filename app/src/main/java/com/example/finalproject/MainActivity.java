package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalproject.OnClicklistener.CustomOnClicklistener;
import com.example.finalproject.OnClicklistener.FotterOnClicklistener;
import com.example.finalproject.Response.RecyclerResponse;
import com.example.finalproject.adapter.RecyclerViewAdapter;
import com.example.finalproject.model.Book;
import com.example.finalproject.model.Member;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static String IP_SET = "58.79.156.213";

    //목록들 갯수
    int LIST_CNT;
    //더블클릭용 변수
    long lastClickTime;
    ImageButton button_search;
    //회원 프사
    ImageButton myPage;
    //리싸이클러뷰들이 들어갈 큰 틀
    LinearLayout verticalLayout;
    //리싸이클러뷰+제목이 들어갈 작은 틀
    LinearLayout[] containers;
    TextView[] textViews; //제목
    RecyclerView[] recyclerViews;//리싸이클러뷰
    RecyclerViewAdapter[] adapters;//어댑터 
    LinearLayoutManager[] layoutManagers;//리싸이클러뷰 가로로바꿔줄 레이아웃
    ArrayList<Book>[] data;//리싸이클러뷰에 들어갈 데이터 리스트들
    //로그인해있는회원
    public static Member member;
    //통신용
    AsyncHttpClient client;
    RecyclerResponse response;
    //하단바
    ImageButton button_footer1,button_footer2,button_footer4,button_footer5;
    String[] genres;
    String[] titles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        intent.getSerializableExtra("member");
        member= (Member) intent.getSerializableExtra("member");
        //접속한 맴버가 좋아하는 장르
        genres=member.getMember_genre().split(",");
        titles= new String[genres.length];

        for(int i=0;i<genres.length;i++){
            if(genres[i].equals("genre_poet")){
                titles[i]="시문학";
            }else if(genres[i].equals("genre_essay")){
                titles[i]="에세이";
            }else if(genres[i].equals("genre_selfDev")){
                titles[i]="자기계발서";
            }else if(genres[i].equals("genre_history")){
                titles[i]="역사";
            }else if(genres[i].equals("genre_science")){
                titles[i]="과학";
            }else if(genres[i].equals("genre_novel")){
                titles[i]="소설";
            }else if(genres[i].equals("genre_comics")){
                titles[i]="만화";
            }else if(genres[i].equals("genre_art")){
                titles[i]="예술";
            }
        }

        button_search = findViewById(R.id.button_top_search);
        button_search.setOnClickListener(this);

        //프사 동그랗게 바꾸기
        myPage=findViewById(R.id.myPage);
        CustomOnClicklistener listener = new CustomOnClicklistener("myPage",this,member);
        myPage.setOnClickListener(listener);
        GradientDrawable drawable = (GradientDrawable)getDrawable(R.drawable.background_rounding);
        myPage.setBackground(drawable);
        myPage.setClipToOutline(true);
        Glide.with(this).load(member.getMember_photo()).into(myPage);


//        //더블클릭 체크용 변수
//        lastClickTime = System.currentTimeMillis();

        //장르들갯수+평점높은순
        LIST_CNT = (genres.length)+1;

        //리싸이클러뷰 유연하게 갯수 변화시킬수 있도록 리사이클러뷰관련을 배열들로 선언
        verticalLayout = findViewById(R.id.vertical_layout);
        containers = new LinearLayout[LIST_CNT];
        textViews = new TextView[LIST_CNT];
        recyclerViews = new RecyclerView[LIST_CNT];
        adapters = new RecyclerViewAdapter[LIST_CNT];
        layoutManagers = new LinearLayoutManager[LIST_CNT];
        data= new ArrayList[LIST_CNT];
        CustomOnClicklistener listeners[] = new CustomOnClicklistener[LIST_CNT];

        for(int i=0;i<LIST_CNT;i++){
            containers[i] = (LinearLayout) getLayoutInflater().inflate(R.layout.recyclerview,null);
            //여기에서 장르별 more 버튼 구현해야함 > 더보기 버튼누를시 그에맞는 검색페이지로 넘김
            ImageButton button_more = containers[i].findViewById(R.id.imageButton);
            if(i==0){
                button_more.setOnClickListener(this);
            }else{
                //접속한 맴버가 좋아하는장르 스플릿을 통해 genres에 저장한것.
                listeners[i] = new CustomOnClicklistener(genres[i-1],this,member);
                button_more.setOnClickListener(listeners[i]);
            }


            recyclerViews[i]=containers[i].findViewById(R.id.recyclerView);
            textViews[i] = containers[i].findViewById(R.id.textView1);
            layoutManagers[i] = new LinearLayoutManager(this);
            layoutManagers[i].setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerViews[i].setLayoutManager(layoutManagers[i]);
            adapters[i] = new RecyclerViewAdapter(this,member);
            verticalLayout.addView(containers[i]);

            data[i] = new ArrayList<>();
            adapters[i].setData(data[i]);
            recyclerViews[i].setAdapter(adapters[i]);
        }
        //여기서부터 하단바
        button_footer1=findViewById(R.id.button_fotter_home);
        button_footer2=findViewById(R.id.button_fotter_rec);
        button_footer4=findViewById(R.id.button_fotter_diary);
        button_footer5=findViewById(R.id.button_fotter_my);

        FotterOnClicklistener fotterOnClicklistener = new FotterOnClicklistener(this,member);
        button_footer1.setOnClickListener(fotterOnClicklistener);
        button_footer2.setEnabled(false);
        button_footer4.setOnClickListener(fotterOnClicklistener);
        button_footer5.setOnClickListener(fotterOnClicklistener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        client = new AsyncHttpClient();

        for(int i=0;i<LIST_CNT;i++){
            //위에서 선언한 제목 순서 (로그인시/비로그인시에 따라 순서 정해짐)
            adapters[i].clear();
            if (i == 0) {
                //제일위에는 평점 높은순이 무조건 들어간다.
                textViews[i].setText("평점\r\n높은순");
                response = new RecyclerResponse(this,adapters[i]);
                RequestParams params = new RequestParams();
                params.add("page","1");
                params.add("size","5");
                client.post("http://"+IP_SET+":8080/ProjectBook/top_index_list.do",params,response);
            }else {
                //2번째부터는 사용자에따라 임의로 정해진 순서가 들어갈 예정이다.
                textViews[i].setText(titles[i-1]);
                response = new RecyclerResponse(this, adapters[i]);
                RequestParams params = new RequestParams();
                params.add("page", "1");
                params.add("size", "5");
                //위에서 선언한 순서대로 장르별로들어간다.
                params.add("genre", genres[i-1]);
                client.post("http://"+IP_SET+":8080/ProjectBook/genre_index_list.do", params, response);
            }
        }
    }



    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button_top_search){
            Intent intent = new Intent(this,SearchActivity.class);
            intent.putExtra("member",member);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this,SearchResultActivity.class);
            intent.putExtra("book_name","toplist");
            intent.putExtra("member",member);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }

//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//        long currTime = System.currentTimeMillis();
//        if (currTime - lastClickTime < ViewConfiguration.getDoubleTapTimeout()) {
//            onItemDoubleClick(adapterView, view, position, l);
//        }
//        lastClickTime = currTime;
//    }

//    public void onItemDoubleClick(AdapterView<?> adapterView, View view, int position, long l) {
//        Toast.makeText(this,"더블클릭",Toast.LENGTH_SHORT).show();
//    }
}
