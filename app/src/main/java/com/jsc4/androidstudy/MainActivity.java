package com.jsc4.androidstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private Button btn_sendMessage;
    private TextView tv_getMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);  // 注册

        btn_sendMessage = findViewById(R.id.btn_sendMessage);
        tv_getMessage = findViewById(R.id.tv_getMessage);
        btn_sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyEvent myEvent = new MyEvent();
                myEvent.setType("1");
                myEvent.setContent("1号的内容");
                EventBus.getDefault().post(myEvent);  // 发布
            }
        });

    }

//    private void postData(){
//        String str = "i am a message.";
//        EventBus.getDefault().post(str);  // 发布
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MyEvent myEvent){
        if(myEvent.getType().equals("0")){
            tv_getMessage.setText(myEvent.getContent());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MyEvent myEvent){
        if(myEvent.getType().equals("1")){
            tv_getMessage.setText(myEvent.getContent());
        }
    }

//    public void onEventPostThread(String str){
//
//    }
//
//    public void onEventBackgroundThread(String str){
//
//    }
//
//    public void onEventAsync(String str){
//
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册
    }
}