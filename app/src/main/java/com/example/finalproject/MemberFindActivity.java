package com.example.finalproject;

import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.Response.MemberFindIdResponse;
import com.example.finalproject.Response.MemberFindPwResponse;
import com.example.finalproject.mail.GMailSender;
import com.example.finalproject.model.Member;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

public class MemberFindActivity extends AppCompatActivity implements View.OnClickListener {

    AsyncHttpClient client;

    //관리자 이메일
    String user = "testman3256@gmail.com";    //보내는 계정 ID
    String password = "xptmxm3256"; // 보내는 계정의 pw

    //인증용 코드
    String codeId,codePw;
    //확인된 아이디 비번
    Member id_member,pw_member;

    //아이디찾기
    String urlFindId = "http://"+MainActivity.IP_SET+":8080/ProjectBook/member_find_id.do";
    //비번찾기
    String urlFindPw = "http://"+MainActivity.IP_SET+":8080/ProjectBook/member_find_pw.do";

    //button
    Button button_back;
    //비번찾기 / 아이디찾기 탭
    TextView textView40,textView42;
    LinearLayout layout_id1,layout_id2,layout_id3,
                layout_pw1,layout_pw2,layout_pw3;
    //아이디 페이지
    Button button_id,button_id2;
    EditText editText_id,editText_id2;
    //비번페이지
    Button button_pw,button_pw2,button_pw3;
    EditText editText_pw,editText_pw2,editText_pw3,editText_pw4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_find);

        //쓰레드
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        client = new AsyncHttpClient();
        //뒤로가기버튼
        button_back = findViewById(R.id.button);
        button_back.setOnClickListener(this);
        //아이디/비번찾기 탭 버튼
        textView40 = findViewById(R.id.textView40);
        textView42 = findViewById(R.id.textView42);
        textView40.setOnClickListener(this);
        textView42.setOnClickListener(this);

        layout_id1= findViewById(R.id.layout_id1);
        layout_id2= findViewById(R.id.layout_id2);
        layout_id3= findViewById(R.id.layout_id3);

        layout_pw1=findViewById(R.id.layout_pw1);
        layout_pw2=findViewById(R.id.layout_pw2);
        layout_pw3=findViewById(R.id.layout_pw3);

        layout_id1.setVisibility(View.VISIBLE);
        layout_id2.setVisibility(View.GONE);
        layout_id3.setVisibility(View.GONE);

        layout_pw1.setVisibility(View.GONE);
        layout_pw2.setVisibility(View.GONE);
        layout_pw3.setVisibility(View.GONE);

        //아이디 페이지1
        editText_id = findViewById(R.id.editText_id);
        button_id =findViewById(R.id.button_id);
        button_id.setOnClickListener(this);
        //아이디 페이지2
        editText_id2=findViewById(R.id.editText_id2);
        button_id2 = findViewById(R.id.button_id2);
        button_id2.setOnClickListener(this);
        //비번 페이지1
        editText_pw = findViewById(R.id.editText_pw);
        button_pw =findViewById(R.id.button_pw);
        button_pw.setOnClickListener(this);
        //비번페이지2
        editText_pw2 = findViewById(R.id.editText_pw2);
        button_pw2=findViewById(R.id.button_pw2);
        button_pw2.setOnClickListener(this);
        button_pw3=findViewById(R.id.button_pw3);
        button_pw3.setOnClickListener(this);
        //비번페이지3
        editText_pw3 =findViewById(R.id.editText_pw3);
        editText_pw4 = findViewById(R.id.editText_pw4);
    }


    //아이디 찾기
    private void findId(String member_email) {
        //String member_email = editText_email.getText().toString().trim();
        MemberFindIdResponse memberFindIdResponse = new MemberFindIdResponse(this);
        RequestParams params = new RequestParams();
        params.put("member_email", member_email);
        client.post(urlFindId,params,memberFindIdResponse);
    }
    //비번찾기
    private void findPw(String member_id) {
        //String member_id = editText_id.getText().toString().trim();
        MemberFindPwResponse memberFindPwResponse = new MemberFindPwResponse(this);
        RequestParams params = new RequestParams();
        params.put("member_id", member_id);
        client.post(urlFindPw,params,memberFindPwResponse);

    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button){
            finish();
        }else if(v.getId()==R.id.textView40){
            textView40.setTextColor(Color.parseColor("#3D85B5"));
            textView40.setBackground(this.getDrawable(R.drawable.background_find_id_on));
            textView42.setTextColor(Color.parseColor("#88999999"));
            textView42.setBackground(this.getDrawable(R.drawable.background_find_id));

            layout_id1.setVisibility(View.VISIBLE);
            layout_id2.setVisibility(View.GONE);
            layout_id3.setVisibility(View.GONE);

            layout_pw1.setVisibility(View.GONE);
            layout_pw2.setVisibility(View.GONE);
            layout_pw3.setVisibility(View.GONE);

        }else if(v.getId()==R.id.textView42){
            textView40.setTextColor(Color.parseColor("#88999999"));
            textView40.setBackground(this.getDrawable(R.drawable.background_find_id));
            textView42.setTextColor(Color.parseColor("#3D85B5"));
            textView42.setBackground(this.getDrawable(R.drawable.background_find_id_on));

            layout_id1.setVisibility(View.GONE);
            layout_id2.setVisibility(View.GONE);
            layout_id3.setVisibility(View.GONE);

            layout_pw1.setVisibility(View.VISIBLE);
            layout_pw2.setVisibility(View.GONE);
            layout_pw3.setVisibility(View.GONE);
        }else if(v.getId()==R.id.button_id){
            String member_id = editText_id.getText().toString().trim();
            findId(member_id);
            layout_id1.setVisibility(View.GONE);
            layout_id2.setVisibility(View.VISIBLE);
        }else if( v.getId()==R.id.button_id2){
            String inputCode = editText_id2.getText().toString().trim();
            Log.e("[TEST]",inputCode+"/"+codeId);
            if(inputCode.equals(codeId)){
                layout_id3.setVisibility(View.VISIBLE);
                layout_id2.setVisibility(View.GONE);
                TextView textView = findViewById(R.id.textView22);
                textView.setText("회원님의 아이디는 "+id_member.getMember_id()+" 입니다.");
            }else{
                Toast.makeText(this,"입력된 코드가 다릅니다.",Toast.LENGTH_SHORT).show();
            }
        }else if(v.getId()==R.id.button_pw){
            String member_id =editText_pw.getText().toString().trim();
            findPw(member_id);
            layout_pw1.setVisibility(View.GONE);
            layout_pw2.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.button_pw2) {
            String inputCode = editText_pw2.getText().toString().trim();
            if (inputCode.equals(codePw)) {
                layout_pw3.setVisibility(View.VISIBLE);
                layout_pw2.setVisibility(View.GONE);
            }else{
                Toast.makeText(this,"입력된 코드가 다릅니다.",Toast.LENGTH_SHORT).show();
            }
        } else if(v.getId()==R.id.button_pw3){
            String new_pw =editText_pw3.getText().toString().trim();
            String new_pw2= editText_pw4.getText().toString().trim();
            if(new_pw.equals(new_pw2)){
                //비번변경 리스폰스 구성
                Toast.makeText(this,"비밀번호가 재설정되었습니다..",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"비밀번호가 일치하지 않습니다..",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void send_findId(Member member){

        Log.e("[TEST]","aaaaaaaaaaaaaa");
        try {
            id_member = member;
            GMailSender gMailSender = new GMailSender(user, password);
            codeId = gMailSender.getEmailCode();
            gMailSender.sendMail("북적북적 아이디 찾기 이메일 인증","인증번호 : "+codeId,member.getMember_email());
        } catch (SendFailedException e){
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send_findPw(Member member){
        try {
            pw_member=member;
            GMailSender gMailSender = new GMailSender(user, password);
            codePw = gMailSender.getEmailCode();
            gMailSender.sendMail("북적북적 비밀번호 찾기 이메일 인증","인증번호 : "+codePw,member.getMember_email());
        } catch (SendFailedException e){
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

}