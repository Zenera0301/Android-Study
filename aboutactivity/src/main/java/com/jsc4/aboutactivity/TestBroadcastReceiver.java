package com.jsc4.aboutactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

public class TestBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        // 接收广播，处理信息
        if (intent != null) {
            if (TextUtils.equals(intent.getAction(), SendBroadcastActivity.COM_JSC4_ABOUTACTIVITY_BROADCAST)) {
                //收到了该消息
                String toastString = intent.getStringExtra("toast");
                Toast.makeText(context, toastString, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
