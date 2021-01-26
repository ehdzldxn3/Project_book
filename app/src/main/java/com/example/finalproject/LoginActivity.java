package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.finalproject.Response.LoginResponse;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editText1, editText2;
    Button button_login;
    LinearLayout layout_join,layout_find;
    AsyncHttpClient client;
    LoginResponse response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        button_login = findViewById(R.id.button_login);
        layout_join = findViewById(R.id.layout_join);
        layout_find = findViewById(R.id.layout_find);

        button_login.setOnClickListener(this);
        layout_join.setOnClickListener(this);
        layout_find.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button_login){
            String member_id = editText1.getText().toString().trim();
            String member_pw = editText2.getText().toString().trim();
            if(member_id.equals("")){
                Toast.makeText(this,"아이디를 입력해 주세요",Toast.LENGTH_SHORT).show();
            }else if(member_pw.equals("")){
                Toast.makeText(this,"비밀번호를 입력해 주세요",Toast.LENGTH_SHORT).show();
            }else{
                client= new AsyncHttpClient();
                response = new LoginResponse(this);
                RequestParams params = new RequestParams();
                params.put("member_id",member_id);
                params.put("member_pw",member_pw);
                client.post("http://"+MainActivity.IP_SET+":8080/ProjectBook/chkLogin.do",params,response);
            }
        }else if(v.getId() == R.id.layout_join){
            Intent intent = new Intent(this,JoinActivity.class);
            startActivity(intent);
        }else if(v.getId() == R.id.layout_find){
            Intent intent = new Intent(this,MemberFindActivity.class);
            startActivity(intent);
        }
    }
}