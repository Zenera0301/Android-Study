package com.jsc4.aboutactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jsc4.aboutactivity.aidl.AIDLActivity;
import com.jsc4.aboutactivity.aidl.AIDLService;

public class SplashActivity extends AppCompatActivity {

    public static int RESULT_CODE = 1234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Button button1 = findViewById(R.id.button1);
        Button test_draw_view_button = findViewById(R.id.test_draw_view_button);
        Button fragment_button = findViewById(R.id.fragment_button);
        Button handler_button = findViewById(R.id.handler_button);
        Button music_button_service = findViewById(R.id.music_button_service);
        Button broadcast_button = findViewById(R.id.broadcast_button);
        Button webview_button = findViewById(R.id.webview_button);
        Button database_button = findViewById(R.id.database_button);
        Button network_button = findViewById(R.id.network_button);
        Button thread_button = findViewById(R.id.thread_button);
        Button aidl_button = findViewById(R.id.aidl_button);
        Button sensor_button = findViewById(R.id.sensor_button);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtra("title","我是来自SplashActivity的消息");
                setResult(RESULT_CODE, intent);
                finish();
            }
        });
        test_draw_view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, TestViewButtonActivity.class);
                startActivity(intent);
            }
        });

        fragment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, TestFragmentActivity.class);
                startActivity(intent);
            }
        });

        handler_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, HandlerButtonActivity.class);
                startActivity(intent);
            }
        });

        music_button_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, MusicButtonActivity.class);
                startActivity(intent);
            }
        });

        broadcast_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, SendBroadcastActivity.class);
                startActivity(intent);
            }
        });


        webview_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, WebViewButtonActivity.class);
                startActivity(intent);
            }
        });

        database_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, DatabaseButtonActivity.class);
                startActivity(intent);
            }
        });

        network_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, DatabaseButtonActivity.class);
                startActivity(intent);
            }
        });

        thread_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, ThreadActivity.class);
                startActivity(intent);
            }
        });

        aidl_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, AIDLActivity.class);
                startActivity(intent);
            }
        });

        sensor_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, SensorManagerActivity.class);
                startActivity(intent);
            }
        });


        Intent intent = getIntent();
        if(intent != null){
            String title = intent.getStringExtra("title");
            UserInfo userInfo = (UserInfo) intent.getSerializableExtra("userInfo");
            setTitle("姓名：" + userInfo.getUserName());
        }
    }
}