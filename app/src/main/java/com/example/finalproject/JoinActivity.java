package com.example.finalproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.finalproject.Response.ChkIdResponse;
import com.example.finalproject.Response.JoinResponse;
import com.example.finalproject.helper.FileUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView_profile;
    Button button_profile, button_id, button_join, button;
    EditText editText_nick, editText_id, editText_pw, editText_phone, editText_email;
    CheckBox checkBox_art, checkBox_essay, checkBox_selfDev, checkBox_history, checkBox_science, checkBox_novel, checkBox_comics, checkBox_poet;
    AsyncHttpClient client;
    ChkIdResponse chkIdResponse;
    JoinResponse joinResponse;

    //아이디검사
    String urlChkId = "http://"+MainActivity.IP_SET+":8080/ProjectBook/chk_id.do";
    //회원가입
    String urlAddMember = "http://"+MainActivity.IP_SET+":8080/ProjectBook/addMember.do";
    //사진경로
    String filePath = null;

    //중복검사 확인용
    Boolean chkExistId = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //초기화
        searchID();
    }

    //초기화
    private void searchID() {

        client = new AsyncHttpClient();

        imageView_profile = findViewById(R.id.imageView_profile);
        button_profile = findViewById(R.id.button_profile);
        button_id = findViewById(R.id.button_id);
        button_join = findViewById(R.id.button_join);
        button = findViewById(R.id.button);
        editText_id = findViewById(R.id.editText_id);
        editText_pw = findViewById(R.id.editText_pw);
        editText_nick = findViewById(R.id.editText_nick);
        editText_phone = findViewById(R.id.editText_phone);
        editText_email = findViewById(R.id.editText_email);
        checkBox_art = findViewById(R.id.checkBox_art);
        checkBox_essay = findViewById(R.id.checkBox_essay);
        checkBox_selfDev = findViewById(R.id.checkBox_selfDev);
        checkBox_history = findViewById(R.id.checkBox_history);
        checkBox_science = findViewById(R.id.checkBox_science);
        checkBox_novel = findViewById(R.id.checkBox_novel);
        checkBox_comics = findViewById(R.id.checkBox_comics);
        checkBox_poet = findViewById(R.id.checkBox_poet);

        button_profile.setOnClickListener(this);
        button_id.setOnClickListener(this);
        button_join.setOnClickListener(this);
        button.setOnClickListener(this);

        //프사 동그랗게 바꾸기
        Glide.with(this).load(R.drawable.profile).circleCrop().into(imageView_profile);

    }
    //퍼미션체크
    private void permissionCheck() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        }
    }
    //사진첩 열기
    private void showSelect() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*"); //모든 이미지 표시
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(intent,100); //선택된 파일을 돌려받아야함

    }
    //프로필 사진등록
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            if(100==requestCode) {
                String uri = data.getData().toString();
                String fileName = uri.substring(uri.lastIndexOf("/")+1);
                filePath = FileUtils.getPath(this, data.getData());
                //프사 동그랗게 바꾸기
                Glide.with(this).load(filePath).circleCrop().into(imageView_profile);
            }
        }
    }
    //아이디중복검사
    private void chkid() {
        chkExistId=true;
        String member_id = editText_id.getText().toString().trim();
        chkIdResponse = new ChkIdResponse(this);
        RequestParams params = new RequestParams();
        params.put("member_id", member_id);
        client.post(urlChkId, params, chkIdResponse);
    }
    //장르 체크박스 읽기
    private String checkBox() {
        String str ="";
        if (checkBox_poet.isChecked()) {str += "genre_poet,";}
        if(checkBox_essay.isChecked()) {str += "genre_essay,";}
        if(checkBox_selfDev.isChecked()) {str += "genre_selfDev,";}
        if(checkBox_history.isChecked()) { str += "genre_history,"; }
        if(checkBox_science.isChecked()) {str += "genre_science,"; }
        if(checkBox_novel.isChecked()) {str += "genre_nover,";}
        if(checkBox_comics.isChecked()) {str += "genre_comics,";}
        if(checkBox_art.isChecked()) {str += "genre_art,";}

        int str_length = str.length();
        String member_genre = str.substring(0,str_length-1);
        return member_genre;
    }
    //회원가입
    private void joinMember() throws FileNotFoundException {
        if (chkExistId == false) {
            Toast.makeText(this,"아이디 중복검사를 해주세요",Toast.LENGTH_SHORT).show();
        }else{
            String member_nick = editText_nick.getText().toString().trim();
            String member_id = editText_id.getText().toString().trim();
            String member_pw = editText_pw.getText().toString().trim();
            String member_phone = editText_phone.getText().toString().trim();
            String member_genre = checkBox();
            String member_email = editText_email.getText().toString().trim();

            if(member_nick.equals("")) {
                Toast.makeText(this,"닉네임을 입력해주세요",Toast.LENGTH_SHORT).show();
            } else if (member_id.equals("")) {
                Toast.makeText(this,"아이디를 입력해주세요",Toast.LENGTH_SHORT).show();
            } else if (member_pw.equals("")) {
                Toast.makeText(this,"비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show();
            } else if (member_email.equals("")) {
                Toast.makeText(this,"이메일을 입력해주세요",Toast.LENGTH_SHORT).show();
            } else {
                JoinResponse joinResponse = new JoinResponse(this);
                RequestParams params = new RequestParams();
                params.put("member_nick",member_nick);
                params.put("member_id", member_id);
                params.put("member_pw", member_pw);
                params.put("member_phone", member_phone);
                params.put("member_genre", member_genre);
                if(filePath == null) {
                    params.put("photo", filePath);
                } else {
                    params.put("photo", new File(filePath));
                }
                params.put("member_email", member_email);
                params.put("member_goal", 0);
                client.post(urlAddMember, params, joinResponse);
            }
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_profile :  //사진등록
                permissionCheck();
                showSelect();
                break;
            case R.id.button_id :   //아이디 중복검사
                chkid();
                break;

            case R.id.button_join :     //회원가입하기
                try {
                    joinMember();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.button :      //취소
                finish();
                break;
        }
    }
}