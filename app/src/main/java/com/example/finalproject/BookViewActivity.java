package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalproject.Response.DeleteFavoriteResponse;
import com.example.finalproject.Response.GetBookGradeMemberResponse;
import com.example.finalproject.Response.BookRecordResponse;
import com.example.finalproject.Response.BookViewResponse;
import com.example.finalproject.Response.SetBookGradeMemberResponse;
import com.example.finalproject.Response.SetFavoriteResponse;
import com.example.finalproject.Response.SetReadingResponse;
import com.example.finalproject.Response.UpdateHistoryResponse;
import com.example.finalproject.dialog.RateDialog;
import com.example.finalproject.model.Book;
import com.example.finalproject.model.BookandGrade;
import com.example.finalproject.model.Grade;
import com.example.finalproject.model.Member;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class BookViewActivity extends AppCompatActivity
        implements View.OnClickListener{

    Member member;

    ImageView back_image, book_image;
    //리스폰스에서 정보 받아서 담을 빈클래스
    Book book;
    Grade grade;
    //책정보 보는 아이디.
    int book_id;
    TextView textView_bookname,textView_writer,textView_publisher,textView_booksummary,
            book_good,book_bad,book_neut,tot_rate,tot_parti;
    //읽은기록관련변수
    TextView texthistory;
    TextView textView_dips,textView_reading,textView_read;
    ImageView img_dips,img_reading,img_read;

    //책정보 가져오기
    String urlBookView = "http://"+MainActivity.IP_SET+":8080/ProjectBook/book_select.do";
    //책 이미지 가져올 앞주소
    String book_img_url = "http://"+MainActivity.IP_SET+":8080/ProjectBook/storage/";
    //책읽었는지 읽는중인지 상태 판별
    String urlBookHistory = "http://"+MainActivity.IP_SET+":8080/ProjectBook/is_reading.do";
    //이미 평가했는지 여부 판별
    String urlBookGraded =  "http://"+MainActivity.IP_SET+":8080/ProjectBook/get_grade_book_member.do";

    //평가하기
    String urlBookGradeMember = "http://"+MainActivity.IP_SET+":8080/ProjectBook/set_grade_book_member.do";

    //뒤로가기 , 글쓰기
    ImageView imageView_back, imageView_writeReview;

    //평가하기 버튼
    LinearLayout button_rate;
    TextView textView_graded;
    //트루면 이미 평가했음 아니면 평가안했음
    Boolean isGradedFlag = false;

    //읽기상태
    String type;
    //찜 상태
    boolean isFavoriteFlag =false;
    //찜하기 버튼
    LinearLayout button_favorite;
    //읽는중 버튼
    LinearLayout button_reading;
    //독서기록 수정버튼
    LinearLayout button_history;

    //읽기 시작날짜 / 종료날짜(날짜수정용)
    String startDate,endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_view);

        member = (Member) getIntent().getSerializableExtra("member");

        book_id = getIntent().getIntExtra("book_id",0);

        back_image = findViewById(R.id.back_image);
        book_image = findViewById(R.id.book_image);
        textView_bookname = findViewById(R.id.book_name);
        textView_writer = findViewById(R.id.book_writer);
        textView_publisher =findViewById(R.id.book_publisher);

        //추천/보통/비추점수
        book_good = findViewById(R.id.textView14);
        book_neut = findViewById(R.id.textView15);
        book_bad = findViewById(R.id.textView16);
        //총점 / 총참여자수
        tot_rate = findViewById(R.id.textView12);
        tot_parti = findViewById(R.id.textView11);
        //책소개글
        textView_booksummary= findViewById(R.id.book_summary);
        //읽은기록관련 변수
        textView_read = findViewById(R.id.textView19);
        img_read = findViewById(R.id.imageView18);

        //뒤로가기&글쓰기버튼
        imageView_back=findViewById(R.id.imageView20);
        imageView_back.setOnClickListener(this);
        imageView_writeReview=findViewById(R.id.imageView22);
        imageView_writeReview.setOnClickListener(this);
        //평가하기 버튼
        button_rate = findViewById(R.id.button_rate);
        button_rate.setOnClickListener(this);
        textView_graded =findViewById(R.id.textView9);
        //찜하기 버튼
        textView_dips =findViewById(R.id.textView17);
        img_dips =findViewById(R.id.imageView16);
        button_favorite=findViewById(R.id.button_favorite);
        button_favorite.setOnClickListener(this);
        //읽는중버튼
        textView_reading=findViewById(R.id.textView18);
        img_reading=findViewById(R.id.imageView17);
        button_reading =findViewById(R.id.button_reading);
        button_reading.setOnClickListener(this);
        //히스토리 수정버튼
        button_history = findViewById(R.id.button_history);
        button_history.setOnClickListener(this);
        texthistory = findViewById(R.id.textView20);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //책정보 가져옴
        BookViewResponse response1 = new BookViewResponse(this);
        AsyncHttpClient client1 = new AsyncHttpClient();
        RequestParams params1 = new RequestParams();
        params1.put("book_id", book_id);
        client1.post(urlBookView, params1, response1);
        
        //읽었는지 여부 판별
        BookRecordResponse  response2 = new BookRecordResponse(this);
        AsyncHttpClient client2 = new AsyncHttpClient();
        RequestParams params2 = new RequestParams();
        params2.put("book_id",book_id);
        params2.put("member_id",member.getMember_id());
        client2.post(urlBookHistory,params2,response2);

        //평가했는지여부 판별
        GetBookGradeMemberResponse response3 = new GetBookGradeMemberResponse(this,textView_graded);
        AsyncHttpClient client3 = new AsyncHttpClient();
        RequestParams params3 = new RequestParams();
        params3.put("member_id",member.getMember_id());
        params3.put("book_id",book_id);
        client3.post(urlBookGraded,params3,response3);
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
    public void setBook(Book book) {
        this.book = book;
    }

    //책정보 셋팅하는 함수
    public void setBookProfile(){
        //책프로필사진 세팅
        Glide.with(this)
                .load(book_img_url+book.getBook_img())
                .into(book_image);

        textView_bookname.setText(book.getBook_name());
        textView_writer.setText("작가 : "+book.getWriter() );
        textView_publisher.setText( "출판사 : " + book.getPublisher());

        //점수저장
        book_good.setText("강추해요 "+grade.getBook_good());
        book_neut.setText("볼만해요 "+grade.getBook_neut());
        book_bad.setText("비추해요 "+grade.getBook_bad());
        tot_parti.setText((grade.getBook_bad()+grade.getBook_good()+grade.getBook_neut())+"명 참여");
        tot_rate.setText(String.valueOf(grade.getBook_good()-grade.getBook_bad()));
        textView_booksummary.setText(book.getBook_summary());
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBookHistory(String startDate, String endDate, String type){
        if(type.equals("DONE")){//다읽었을 경우
            this.type = "DONE";
            String start = startDate.substring(0,10);
            String end = endDate.substring(0,10);
            texthistory.setText(start+" ~ "+end);
            textView_read.setTextColor(Color.parseColor("#3DADC0"));
            img_read.setImageResource(R.drawable.bookview5_on);
        }else if(type.equals("READING")){//읽는중일경우
            this.type ="READING";
            String start = startDate.substring(0,10);
            texthistory.setText(start+" ~ NOW");
            textView_reading.setTextColor(Color.parseColor("#3DADC0"));
            img_reading.setImageResource(R.drawable.bookview4_on);
        }else{//읽지않았을경우
            this.type="NONE";
            texthistory.setText("아직 읽지 않았음");
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.textView6){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(book.getBook_link()));
            startActivity(intent);
        }else if(v.getId()==R.id.imageView20){
            this.finish();;
        }else if(v.getId()==R.id.imageView22){
            Intent intent = new Intent(this,WriteReviewActivity.class);
            intent.putExtra("member",member);
            BookandGrade bookandGrade = new BookandGrade();
            bookandGrade.setBook_name(book.getBook_name());
            bookandGrade.setBook_img(book.getBook_img());
            bookandGrade.setBook_link(book.getBook_link());
            bookandGrade.setBook_id(book.getBook_id());
            bookandGrade.setWriter(book.getWriter());
            bookandGrade.setPublisher(book.getPublisher());
            bookandGrade.setBook_summary(book.getBook_summary());
            bookandGrade.setBook_neut(grade.getBook_neut());
            bookandGrade.setBook_bad(grade.getBook_neut());
            bookandGrade.setBook_good(grade.getBook_good());
            intent.putExtra("bookandGrade",bookandGrade);
            startActivity(intent);
        }else if(v.getId()==R.id.button_rate){
            if(isGradedFlag){
                Toast.makeText(this,"이미 평가완료하셨습니다!",Toast.LENGTH_SHORT).show();
            }else{
                RateDialog dialog  = new RateDialog(this);
                dialog.callDialog(book.getBook_name());
                textView_graded.setText("평가완료");
            }
        }else if(v.getId()==R.id.button_favorite){
            if(isFavoriteFlag){
                //찜 취소
                DeleteFavoriteResponse response = new DeleteFavoriteResponse(this);
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.put("member_id",member.getMember_id());
                params.put("book_id",book_id);
                client.post("http://"+MainActivity.IP_SET+":8080/ProjectBook/delete_favorite.do",params,response);
                isFavoriteFlag=false;
                setFavorite();
            }else{
                //찜 하기
                SetFavoriteResponse response = new SetFavoriteResponse(this);
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.put("member_id",member.getMember_id());
                params.put("book_id",book_id);
                client.post("http://"+MainActivity.IP_SET+":8080/ProjectBook/set_favorite.do",params,response);
                isFavoriteFlag=true;
                setFavorite();
            }
        }else if(v.getId()==R.id.button_reading){
            if(type.equals("DONE")){
                Toast.makeText(this,book.getBook_name()+"은 이미 읽기 완료하셨습니다!",Toast.LENGTH_SHORT).show();
            }else if(type.equals("READING")){
                //읽기 종료하시겠습니까?
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(book.getBook_name()+" 읽기 종료하시겠습니까?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                         endReading();
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
            }else{
                //읽기 시작하시겠습니까?
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(book.getBook_name()+" 읽기 시작하시겠습니까?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        startReading();
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
        }else if(v.getId()==R.id.button_history){
            if(type.equals("DONE")){
                String startDate = texthistory.getText().toString().substring(0,10);
                int startYear = Integer.parseInt(startDate.substring(0,4));
                int startMonth= Integer.parseInt(startDate.substring(5,7));
                int startDay = Integer.parseInt(startDate.substring(8,10));
                String endDate =texthistory.getText().toString().substring(13,23);
                int endYear = Integer.parseInt(endDate.substring(0,4));
                int endMonth= Integer.parseInt(endDate.substring(5,7));
                int endDay = Integer.parseInt(endDate.substring(8,10));

                //종료날짜 선택
                final DatePickerDialog dialog2 = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        setEndDate(year+"-"+(month+1)+"-"+dayOfMonth);
                        updatehistory();
                    }
                },endYear,endMonth,endDay);
                dialog2.setTitle("종료날짜를 선택하세요");
                dialog2.getDatePicker().setMaxDate(System.currentTimeMillis());



                //시작날짜 선택
                DatePickerDialog dialog1 = new DatePickerDialog(this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        setStartDate(year+"-"+(month+1)+"-"+dayOfMonth);
                        dialog2.show();
                    }
                },startYear,startMonth,startDay);
                dialog1.setTitle("시작날짜를 선택하세요");
                dialog1.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog1.show();

            }else{
                Toast.makeText(this,"먼저 읽기 종료한후에 날짜를 수정할 수 있습니다.",Toast.LENGTH_SHORT).show();
            }
        }
    }
    //평가하기용 플래그
    public void setGradedFlag(Boolean gradedFlag) {
        isGradedFlag = gradedFlag;
    }

    //평가하기
    public void setBookGradeMember(String graded){
        SetBookGradeMemberResponse response = new SetBookGradeMemberResponse(this);
        RequestParams params = new RequestParams();
        params.put("member_id",member.getMember_id());
        params.put("book_id",book_id);
        params.put("graded",graded);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(urlBookGradeMember,params,response);
        if(graded.equals("good")){
            book_good.setText("강추해요 "+String.valueOf(grade.getBook_good()+1));
        }else if(graded.equals("neut")){
            book_neut.setText("볼만해요 "+String.valueOf(grade.getBook_neut()+1));
        }else{
            book_bad.setText("비추해요 "+String.valueOf(grade.getBook_bad()+1));
        }
        isGradedFlag=true;
    }

    //찜하기용 플래그
    public void setFavoriteFlag(boolean favorite) {
        isFavoriteFlag = favorite;
    }
    public void setFavorite(){
        if(isFavoriteFlag){
            img_dips.setImageResource(R.drawable.bookview3_on);
            textView_dips.setTextColor(Color.parseColor("#3DADC0"));
        }else{
            img_dips.setImageResource(R.drawable.bookview3);
            textView_dips.setTextColor(Color.parseColor("#727171"));
        }
    }


    //읽기 시작
    public void startReading(){
        type = "READING";
        //리스폰스 처리
        SetReadingResponse response = new SetReadingResponse(this);
        RequestParams params = new RequestParams();
        params.put("member_id",member.getMember_id());
        params.put("book_id",book_id);
        params.put("book_name",book.getBook_name());
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://"+MainActivity.IP_SET+":8080/ProjectBook/start_reading.do",params,response);

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat date = new SimpleDateFormat("YYYY-MM-dd", Locale.getDefault());
        String start = date.format(currentTime);
        texthistory.setText(start+" ~ NOW");
        textView_reading.setTextColor(Color.parseColor("#3DADC0"));
        img_reading.setImageResource(R.drawable.bookview4_on);
    }
    //읽기 종료
    public void endReading(){
        type = "DONE";
        //리스폰스 처리
        SetReadingResponse response = new SetReadingResponse(this);
        RequestParams params = new RequestParams();
        params.put("member_id",member.getMember_id());
        params.put("book_id",book_id);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://"+MainActivity.IP_SET+":8080/ProjectBook/end_reading.do",params,response);

        String now = texthistory.getText().toString();
        String start = now.substring(0,10);
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat date = new SimpleDateFormat("YYYY-MM-dd", Locale.getDefault());
        String end = date.format(currentTime);
        texthistory.setText(start+" ~ "+end);
        textView_reading.setTextColor(Color.parseColor("#727171"));
        img_reading.setImageResource(R.drawable.bookview4);
        textView_read.setTextColor(Color.parseColor("#3DADC0"));
        img_read.setImageResource(R.drawable.bookview5_on);
    }

    //수정용 시작/종료날짜 셋팅
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    //히스토리 수정
    public void updatehistory(){
        UpdateHistoryResponse response = new UpdateHistoryResponse(this);
        RequestParams params = new RequestParams();
        params.put("member_id",member.getMember_id());
        params.put("book_id",book_id);
        params.put("start_time",startDate);
        params.put("end_time",endDate);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://"+MainActivity.IP_SET+":8080/ProjectBook/update_reading.do",params,response);
        texthistory.setText(startDate+" ~ "+endDate);
    }
}