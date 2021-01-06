package com.jsc4.aboutactivity;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class TestIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public TestIntentService(String name) {
        super(name);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        // do more work is wrong UI线程  > 10s时，ANR（应用程序无响应）
        // 把耗时操作放到子线程中
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        thread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // intent队列  排队，像MessageQueue  同步操作：排队领书，处理Intent数据
        // stopSelf();  // 停止本身，此处不必使用
        if(intent != null){

        }
    }
}
