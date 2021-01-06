package com.jsc4.aboutactivity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

public class SendBroadcastActivity extends Activity {

    public static final String COM_JSC4_ABOUTACTIVITY_BROADCAST = "com.jsc4.aboutactivity.broadcast";
    private TestBroadcastReceiver mTestBroadcastReceiver = new TestBroadcastReceiver();//接收者
    private Button mSend_broadcast_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_broadcast);

        mSend_broadcast_button = findViewById(R.id.send_broadcast_button);
        mSend_broadcast_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(COM_JSC4_ABOUTACTIVITY_BROADCAST);
                intent.putExtra("toast", "this is my toast of broadcast");
                sendBroadcast(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        // 有个intentFilter，动态注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(COM_JSC4_ABOUTACTIVITY_BROADCAST);
        registerReceiver(mTestBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 反注册
        unregisterReceiver(mTestBroadcastReceiver);
    }
}
