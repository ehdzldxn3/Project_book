package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.finalproject.OnClicklistener.FotterOnClicklistener;
import com.example.finalproject.Response.DeleteMemberResponse;
import com.example.finalproject.Response.GetRecordResponse;
import com.example.finalproject.Response.HomeResponse;
import com.example.finalproject.Response.MypageResponse;
import com.example.finalproject.adapter.BookShelfAdapter;
import com.example.finalproject.adapter.FavoriteAdapter;
import com.example.finalproject.adapter.HomeAdapter;
import com.example.finalproject.model.Favorite;
import com.example.finalproject.model.Member;
import com.example.finalproject.model.Record;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class MypageActivity extends AppCompatActivity implements View.OnClickListener {
    //member가 인텐트로 넘어옴
    Member member;
    ImageView image_back,image_profile;
    ImageButton button_option;
    LinearLayout layout_option;
    TextView textView_nick;
    //목록 전환할 버튼들
    Button button_review,button_bookshelf,button_favorite;
    LinearLayout layout_review,layout_bookshelf,layout_favorite;

    //내책장용 변수들
    List<Record> list_bookShelf;
    ListView listView_bookShelf;
    BookShelfAdapter bookShelfAdapter;
    //찜목록용 변수들
    List<Favorite> list_favorite;
    ListView listView_favorite;
    FavoriteAdapter favoriteAdapter;
    //평가/리뷰창 변수들
    List<Record> list_review;
    ListView listView_review;
    HomeAdapter reviewAdapter;

    //하단바
    ImageButton button_footer1,button_footer2,button_footer4,button_footer5;
    //로그아웃버튼
    TextView textView_logout,textView_deleteMember;
    //설정버튼
    TextView textView_option;
    //총 컨테이너
    ConstraintLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        image_back=findViewById(R.id.image_back);
        image_profile=findViewById(R.id.image_profile);
        button_option = findViewById(R.id.button_option);
        textView_nick = findViewById(R.id.textView_Name);

        Intent intent = getIntent();
        member= (Member) intent.getSerializableExtra("member");
        setMyProfile();

        button_review=findViewById(R.id.button5);
        button_bookshelf=findViewById(R.id.button6);
        button_favorite=findViewById(R.id.button7);
        layout_review=findViewById(R.id.layout_review);
        layout_bookshelf=findViewById(R.id.layout_bookShelf);
        layout_favorite=findViewById(R.id.layout_favorite);
        button_review.setTextColor(Color.parseColor("#FFFFFFFF"));
        button_bookshelf.setTextColor(Color.parseColor("#77FFFFFF"));
        button_favorite.setTextColor(Color.parseColor("#77FFFFFF"));
        layout_review.setVisibility(View.VISIBLE);
        layout_bookshelf.setVisibility(View.GONE);
        layout_favorite.setVisibility(View.GONE);
        button_review.setOnClickListener(this);
        button_bookshelf.setOnClickListener(this);
        button_favorite.setOnClickListener(this);
        //옵션버튼
        button_option.setOnClickListener(this);
        layout_option=findViewById(R.id.layout_option);
        textView_option = findViewById(R.id.textView27);
        textView_option.setOnClickListener(this);
        container = findViewById(R.id.container);
        container.setOnClickListener(this);

        list_bookShelf = new ArrayList<>();
        listView_bookShelf = findViewById(R.id.listView_bookShelf);
        bookShelfAdapter = new BookShelfAdapter(this,R.layout.bookshelf_item,list_bookShelf,member);
        listView_bookShelf.setAdapter(bookShelfAdapter);

        AsyncHttpClient client1 = new AsyncHttpClient();
        GetRecordResponse response1 = new GetRecordResponse(this,bookShelfAdapter,"MypageActivity");
        RequestParams params1 = new RequestParams();
        params1.put("member_id",member.getMember_id());
        client1.post("http://"+MainActivity.IP_SET+":8080/ProjectBook/getBookRecord.do",params1,response1);

        //찜목록
        list_favorite =new ArrayList<>();
        listView_favorite = findViewById(R.id.listView_favorite);
        favoriteAdapter = new FavoriteAdapter(this,R.layout.favorite_item,list_favorite,member);
        listView_favorite.setAdapter(favoriteAdapter);
        AsyncHttpClient client2 =new AsyncHttpClient();
        MypageResponse response2 = new MypageResponse(this,favoriteAdapter);
        client2.post("http://"+MainActivity.IP_SET+":8080/ProjectBook/getFavorite.do",params1,response2);
        //리뷰
        list_review = new ArrayList<>();
        listView_review=findViewById(R.id.listView_review);
        reviewAdapter = new HomeAdapter(this,R.layout.review_item,list_review,member);
        listView_review.setAdapter(reviewAdapter);
        AsyncHttpClient client3 = new AsyncHttpClient();
        HomeResponse response3 = new HomeResponse(this,reviewAdapter,listView_review);
        RequestParams params3 = new RequestParams();
        params3.add("page", String.valueOf(1));
        params3.add("size", String.valueOf(5));
        params3.add("member_id",member.getMember_id());
        client3.post("http://"+MainActivity.IP_SET+":8080/ProjectBook/reviewId.do",params3,response3);



        //여기서부터 하단바
        button_footer1=findViewById(R.id.button_fotter_home);
        button_footer2=findViewById(R.id.button_fotter_rec);
        button_footer4=findViewById(R.id.button_fotter_diary);
        button_footer5=findViewById(R.id.button_fotter_my);

        FotterOnClicklistener fotterOnClicklistener = new FotterOnClicklistener(this,member);
        button_footer1.setOnClickListener(fotterOnClicklistener);
        button_footer2.setOnClickListener(fotterOnClicklistener);
        button_footer4.setOnClickListener(fotterOnClicklistener);
        button_footer5.setEnabled(false);
        //로그아웃
        textView_logout = findViewById(R.id.textView33);
        textView_logout.setOnClickListener(this);
        textView_deleteMember = findViewById(R.id.textView34);
        textView_deleteMember.setOnClickListener(this);
    }

    //상단에 권수표시
    public void setState(int book_reading,int book_done){
        Button button_reading = findViewById(R.id.button2);
        Button button_done =findViewById(R.id.button3);
        button_reading.setText("읽는중 "+book_reading);
        button_done.setText("읽음 "+book_done);
    }

    public void setFavorite(int favorite){
        Button button = findViewById(R.id.button1);
        button.setText("찜 "+favorite);
    }

    public void setMyProfile(){
        //배경사진 세팅 (블러처리)
        Glide.with(this)
                .load(member.getMember_photo())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25,3)))
                .into(image_back);
        //프로필사진 세팅
        Glide.with(this)
                .load(member.getMember_photo())
                .into(image_profile);
        textView_nick.setText(member.getMember_nick());
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button5){//리뷰창
            layout_review.setVisibility(View.VISIBLE);
            layout_bookshelf.setVisibility(View.GONE);
            layout_favorite.setVisibility(View.GONE);
            button_review.setTextColor(Color.parseColor("#FFFFFFFF"));
            button_bookshelf.setTextColor(Color.parseColor("#77FFFFFF"));
            button_favorite.setTextColor(Color.parseColor("#77FFFFFF"));
        }else if(v.getId()==R.id.button6){//내 책장
            layout_review.setVisibility(View.GONE);
            layout_bookshelf.setVisibility(View.VISIBLE);
            layout_favorite.setVisibility(View.GONE);
            button_review.setTextColor(Color.parseColor("#77FFFFFF"));
            button_bookshelf.setTextColor(Color.parseColor("#FFFFFFFF"));
            button_favorite.setTextColor(Color.parseColor("#77FFFFFF"));
        }else if(v.getId()==R.id.button7){//찜목록
            layout_review.setVisibility(View.GONE);
            layout_bookshelf.setVisibility(View.GONE);
            layout_favorite.setVisibility(View.VISIBLE);
            button_review.setTextColor(Color.parseColor("#77FFFFFF"));
            button_bookshelf.setTextColor(Color.parseColor("#77FFFFFF"));
            button_favorite.setTextColor(Color.parseColor("#FFFFFFFF"));
        }else if(v.getId()==R.id.button_option){
            if(layout_option.getVisibility()==View.VISIBLE){
                layout_option.setVisibility(View.GONE);
            }else{
                layout_option.setVisibility(View.VISIBLE);
            }
        }else if(v.getId()==R.id.textView33){//로그아웃
            Intent intent = new Intent(this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else if(v.getId() == R.id.textView34){//탈퇴하기
            final LinearLayout layout = (LinearLayout) View.inflate(this,R.layout.delete_member_dialog,null);
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("탈퇴 ㄹㅇ?");
            dialog.setView(layout);
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText editText1 = layout.findViewById(R.id.editText1);
                    EditText editText2 = layout.findViewById(R.id.editText2);
                    String member_idchk = editText1.getText().toString().trim();
                    String member_pwchk = editText2.getText().toString().trim();
                    deleteMember(member_idchk,member_pwchk);
                }
            });
            dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }else if(v.getId()==R.id.textView27||v.getId()==R.id.container){
            layout_option.setVisibility(View.GONE);
        }
    }
    public void deleteMember(String member_idchk,String member_pwchk){
        if(member_idchk.equals(member.getMember_id())&&member_pwchk.equals(member.getMember_pw())){
            //아이디 비번이 동일하므로 삭제
            //리스폰스 구현한다.
            AsyncHttpClient client =new AsyncHttpClient();
            DeleteMemberResponse response = new DeleteMemberResponse(this);
            RequestParams params = new RequestParams();
            params.put("member_id",member.getMember_id());
            client.post("http://"+MainActivity.IP_SET+":8080/ProjectBook/delete_member.do",params,response);

            //그리고 로그아웃처리
            Intent intent = new Intent(this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);


        }else{
            Toast.makeText(this,"입력한 아이디 혹은 비밀번호가 일치하지 않습니다",Toast.LENGTH_SHORT).show();
        }
    }
}