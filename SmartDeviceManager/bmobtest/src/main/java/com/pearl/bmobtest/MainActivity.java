package com.pearl.bmobtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity {

    Button test_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "1f1f8a4eac5575b2b1bf9bde5c2ad719");


        test_btn = (Button)findViewById(R.id.test_btn);
        test_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signup();

            }
        });

    }


    public void signup(){
        // 获取用户输入的用户名和密码


        // 使用BmobSDK提供的注册功能
        BmobUser user = new BmobUser();
        user.setUsername("abcsdas");
        user.setPassword("fdffs");
        user.setEmail("764457941@qq.com");
        user.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(MainActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
