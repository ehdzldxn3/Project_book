package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.example.finalproject.OnClicklistener.FotterOnClicklistener;
import com.example.finalproject.Response.GetRecordResponse;
import com.example.finalproject.Response.SetGoalResponse;
import com.example.finalproject.Response.GetMonthDataResponse;
import com.example.finalproject.adapter.GridViewAdapter;
import com.example.finalproject.adapter.BookShelfAdapter;
import com.example.finalproject.model.Member;
import com.example.finalproject.model.Record;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DiaryActivity extends AppCompatActivity implements View.OnClickListener {
    //목표설정 버튼
    Button button_goal;
    //원형그래프
    CircleProgressBar circleProgressBar;
    int goal_thisMonth;
    int read_thisMonth;
    //월간 목표 표기
    TextView textViewProgress;
    //이번달 읽은 책들 담은 리스트
    List<Record> list;
    //통신용
    AsyncHttpClient client;
    GetMonthDataResponse response;

    //넘어온 데이터 받기.
    Member member;
    //이번달
    int month;
    int year;
    //캘린더변수
    Calendar mCal;
    ArrayList<String> dayList;
    GridViewAdapter gridAdapter;
    Button button_last_month,button_next_month;
    int DAYS_OF_MONTH[] ={31,28,31,30,31,30,31,31,30,31,30,31};

    //독서통계페이지 / 내서재페이지 전환용
    Button button1,button2;
    LinearLayout layout_bookshelf;
    ScrollView layout_history;
    List<Record> list_bookShelf;
    BookShelfAdapter listViewadapter;
    ListView listView;

    //하단바
    ImageButton button_footer1,button_footer2,button_footer4,button_footer5;

    public void setList(List<Record> list) {
        this.list = list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        circleProgressBar = findViewById(R.id.circleBar);
        textViewProgress = findViewById(R.id.textViewProgress);

        //데이터 넘어온거 받기
        Intent intent = getIntent();
        member= (Member) intent.getSerializableExtra("member");
        goal_thisMonth = member.getMember_goal();
        Log.e("독서목표", String.valueOf(goal_thisMonth));

        //이번달 구하기
        long now =System.currentTimeMillis();
        Date date = new Date(now);
        month = date.getMonth()+1;
        year = 2020;

        getMonthData();

        //목표설정버튼
        button_goal= findViewById(R.id.button_goal);
        button_goal.setOnClickListener(this);

        //달력
        dayList = new ArrayList<String>();
        gridAdapter = new GridViewAdapter(this,R.layout.calendar_item,dayList);
       //setCalendar(year,month);
        button_last_month=findViewById(R.id.button_last_month);
        button_next_month=findViewById(R.id.button_next_month);

        button_last_month.setOnClickListener(this);
        button_next_month.setOnClickListener(this);

        //독서통계페이지 / 내서재페이지 전환용
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        layout_bookshelf = findViewById(R.id.layout_bookShelf);
        layout_history = findViewById(R.id.layout_history);
        layout_bookshelf.setVisibility(View.GONE);
        layout_history.setVisibility(View.VISIBLE);

        TextView textView = findViewById(R.id.title_goal2);
        textView.setText(member.getMember_nick()+"님의 서재");

        list_bookShelf = new ArrayList<>();
        listView = findViewById(R.id.listView);
        listViewadapter = new BookShelfAdapter(this,R.layout.bookshelf_item,list_bookShelf,member);
        listView.setAdapter(listViewadapter);

        AsyncHttpClient client = new AsyncHttpClient();
        GetRecordResponse response = new GetRecordResponse(this,listViewadapter,"DiaryActivity");
        RequestParams params = new RequestParams();
        params.put("member_id",member.getMember_id());
        client.post("http://"+MainActivity.IP_SET+":8080/ProjectBook/getBookRecord.do",params,response);

        //여기서부터 하단바
        button_footer1=findViewById(R.id.button_fotter_home);
        button_footer2=findViewById(R.id.button_fotter_rec);
        button_footer4=findViewById(R.id.button_fotter_diary);
        button_footer5=findViewById(R.id.button_fotter_my);

        FotterOnClicklistener fotterOnClicklistener = new FotterOnClicklistener(this,member);
        button_footer1.setOnClickListener(fotterOnClicklistener);
        button_footer2.setOnClickListener(fotterOnClicklistener);
        button_footer4.setEnabled(false);
        button_footer5.setOnClickListener(fotterOnClicklistener);
    }

    //getMonthData >> response에서 setCircle(),readHistory()실행
    //readHistory >> adapter에 해당 월 읽은 기록 넘겨주고 setCalendar 동작한다.

    //이번달 데이터 가져오기(어떤책 언제부터 언제까지 읽었는지) 얘만불러오면 순서대로 다불러와짐
    //불러오기전에 어댑터랑 리스트 초기화 필수
    public void getMonthData() {
        //통신
        client =new AsyncHttpClient();
        response = new GetMonthDataResponse(this);
        RequestParams params = new RequestParams();
        params.put("member_id",member.getMember_id());
        params.put("month",String.format("%02d",month));
        //월에 몇일있는지 맞춰 들어감
        params.put("lastDay",DAYS_OF_MONTH[month-1]);
        params.put("year",year);
        client.post("http://"+MainActivity.IP_SET+":8080/ProjectBook/getMonthData.do",params,response);
    }

    //원형그래프 세팅
    public void setCircle(){
        circleProgressBar.setMax(100);
        read_thisMonth=0;
        for(int i=0;i<list.size();i++){
            Record item = list.get(i);
            if(item.getIsreading()==0){
                read_thisMonth++;
            }
        }
        int percent;
        if(goal_thisMonth==0){
            percent = 100;
        }else{
            percent = (int)((float)read_thisMonth/(float)goal_thisMonth*100.0);
        }
        circleProgressBar.setProgress(percent);
        circleProgressBar.setProgressFormatter(new CircleProgressBar.ProgressFormatter() {
            @Override
            public CharSequence format(int progress, int max) {
                return String.format("%d%%",progress);
            }
        });
        textViewProgress.setText(read_thisMonth+"권 / "+goal_thisMonth+"권");
    }

    //책읽은 기록 가져와서 달력에 표시하기위해 세팅
    public void readHistory(){

        int totalday = DAYS_OF_MONTH[month-1];
        String[] month_history = new String[totalday+1];
        for(int x = 0;x<month_history.length;x++){
            month_history[x]="";
        }

        for(int i=0;i<list.size();i++) {
            Record item = list.get(i);
            //각각 날짜만 저장
            int lastIndexOfS = item.getStart_time().lastIndexOf("-");
            int startDay = Integer.parseInt(item.getStart_time().substring(lastIndexOfS + 1, lastIndexOfS + 3));
            int endDay;

            Log.e("startDay",String.valueOf(startDay));
            if(item.getIsreading()==0){//책 다읽었으면.
                int lastIndexOfE = item.getEnd_time().lastIndexOf("-");
                endDay = Integer.parseInt(item.getEnd_time().substring(lastIndexOfE + 1, lastIndexOfE + 3));
                //Log.e("endDay",String.valueOf(endDay));
            }else{//책 읽는 상태면

                int firstIndexOfS = item.getStart_time().indexOf("-");
                int startMonth = Integer.parseInt(item.getStart_time().substring(firstIndexOfS+1,firstIndexOfS+3));

                Date date = new Date(System.currentTimeMillis());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
                String today =dateFormat.format(date);
                String thismonth = monthFormat.format(date);

                if(startMonth>month){//시작일 전의달일경우
                    startDay=0;
                    endDay=0;
                }else if(startMonth==month&&Integer.parseInt(thismonth)>month){//시작일이포함된 지난달일경우
                    endDay = DAYS_OF_MONTH[month-1];
                }else if(startMonth==month&&Integer.parseInt(thismonth)==month){//시작일 종료일 모두 이번달일경우
                    endDay=Integer.parseInt(today);
                }else if(startMonth<month&&Integer.parseInt(thismonth)==month){//시작일 이후의 달이고 이번달일경우
                    startDay=1;
                    endDay=Integer.parseInt(today);
                }else if(startMonth<month&&Integer.parseInt(thismonth)>month){
                    startDay=1;
                    endDay = DAYS_OF_MONTH[month-1];
                }else{
                    startDay=0;
                    endDay=0;
                }
                Log.d("셋팅", String.valueOf(startDay)+"/"+endDay);
            }
            String book_name = item.getBook_name();
            for(int j=1;j<month_history.length;j++){
                if(startDay<=j && j<=endDay){
                    if(month_history[j].equals("")){
                        month_history[j] += book_name;
                    }else{
                        month_history[j] = month_history[j]+"/"+book_name;
                    }
                }
            }
        }
        gridAdapter.setMonth_history(month_history);
        setCalendar(year,month);
    }

    //달력세팅해주는거
    public void setCalendar(int year,int month){
        TextView textView_month;
        TextView textView_date;
        GridView gridView;
        textView_date = (TextView)findViewById(R.id.textView_date);
        textView_month = findViewById(R.id.textView_month);
        gridView = (GridView)findViewById(R.id.gridview);

        //현재 날짜 텍스트뷰에 뿌려줌
        textView_month.setText(String.format("%02d",month));
        textView_date.setText(String.format("%04d",year));

        //gridview 요일 표시
        gridAdapter.add("SUN");
        gridAdapter.add("MON");
        gridAdapter.add("TUE");
        gridAdapter.add("WED");
        gridAdapter.add("THU");
        gridAdapter.add("FRI");
        gridAdapter.add("SAT");

        mCal = Calendar.getInstance();
        //이번달 1일 무슨요일인지 판단 mCal.set(Year,Month,Day)
        mCal.set(year, month - 1, 1);

        int dayNum = mCal.get(Calendar.DAY_OF_WEEK);
        //1일 - 요일 매칭 시키기 위해 공백 add
        for (int i = 1; i < dayNum; i++) {
            gridAdapter.add("");
        }
        int thisMonth = mCal.get(Calendar.MONTH) + 1;
        mCal.set(Calendar.MONTH, thisMonth - 1);
        for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            gridAdapter.add("" + (i + 1));
        }
        gridView.setAdapter(gridAdapter);
    }

    //달력 좌우버튼 및 목표설정버튼 온클릭리스너
    //내 서재 추가
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button_last_month){
            list.clear();
            dayList.clear();
            gridAdapter.clear();
            gridAdapter.notifyDataSetChanged();
            if(month>1){
                month--;
            }else{
                year--;
                month=12;
            }
            getMonthData();
        }else if(v.getId()==R.id.button_next_month) {
            list.clear();
            dayList.clear();
            gridAdapter.clear();
            gridAdapter.notifyDataSetChanged();
            if (month < 12) {
                month++;
            } else {
                year++;
                month = 1;
            }
            getMonthData();
        }else if(v.getId()==R.id.button_goal){
            String member_id = member.getMember_id();
            //다이얼로그를 이용해 이번달 독서목표 받고
            //client.post 로 서버에 전달하고 이 액티비티의 goal_thisMonth 새로고침
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("이번달 목표 권수");
            final EditText editText = new EditText(this);
            editText.setHint("이번달 목표 권수를 입력하세요");
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            dialog.setView(editText);
            dialog.setPositiveButton("OK",new DialogClick(this,editText,member_id));
            dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }else if(v.getId()==R.id.button1){
            layout_history.setVisibility(View.VISIBLE);
            layout_bookshelf.setVisibility(View.GONE);
        }else if(v.getId()==R.id.button2){
            layout_history.setVisibility(View.GONE);
            layout_bookshelf.setVisibility(View.VISIBLE);
        }
    }

    //이번달 목표 권수 정하는 다이어로그 확인버튼의 온클릭리스너
    class DialogClick implements DialogInterface.OnClickListener{
        Activity activity;
        int goal=0;
        EditText editText;
        String member_id;

        public DialogClick(Activity activity, EditText editText, String member_id) {
            this.activity = activity;
            this.editText = editText;
            this.member_id = member_id;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            String result = editText.getText().toString().trim();
            goal = Integer.parseInt(result);

            goal_thisMonth = goal;
            member.setMember_goal(goal);

            AsyncHttpClient client = new AsyncHttpClient();
            SetGoalResponse setGoalResponse = new SetGoalResponse(( DiaryActivity)activity);

            RequestParams params = new RequestParams();
            params.put("member_id",member_id);
            params.put("goal",goal);
            client.post("http://"+MainActivity.IP_SET+":8080/ProjectBook/setGoal.do",params,setGoalResponse);
        }
    }

}
