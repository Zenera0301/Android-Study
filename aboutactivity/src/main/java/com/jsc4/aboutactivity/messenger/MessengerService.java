package com.jsc4.aboutactivity.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

// 多进程+单线程，不用担心线程安全问题
public class MessengerService extends Service {

    Messenger mMessenger = new Messenger(new IncommingHandler());

    class IncommingHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            // 在这里处理消息
            switch(msg.what){
                case 0:
                    break;
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}