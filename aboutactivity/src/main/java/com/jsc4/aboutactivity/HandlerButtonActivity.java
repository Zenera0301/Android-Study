package com.jsc4.aboutactivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.HeaderViewListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class HandlerButtonActivity extends AppCompatActivity {
    private static final int MESSAGE_CODE = 888888;
    private TextView mTextView;
    private TestHandler mTestHandler = new TestHandler(this);

    public TextView getTextView() {
        return mTextView;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_button);
        mTextView = findViewById(R.id.handler_text_view);

//        Looper looper = getMainLooper();
        // 发送消息
        Message message = mTestHandler.obtainMessage();
        message.arg1 = 0;
        message.arg2 = 1;
        message.what = MESSAGE_CODE;
        message.obj = 10000;
        mTestHandler.sendMessageDelayed(message, 1000);

//        mTestHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        },1000); // 延时1s执行

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                mTextView.setText("change UI in thread");
//            }
//        }).start();
    }


    public class TestHandler extends Handler{

        // 弱引用
        public final WeakReference<HandlerButtonActivity>  mHandlerButtonActivityWeakReference;
        public TestHandler(HandlerButtonActivity activity){
            mHandlerButtonActivityWeakReference = new WeakReference<HandlerButtonActivity>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            HandlerButtonActivity handlerButtonActivity = mHandlerButtonActivityWeakReference.get();

            // 接收消息
            switch (msg.what){
                case MESSAGE_CODE:
                    int value = (int) msg.obj;
                    handlerButtonActivity.getTextView().setText(String.valueOf(value/1000));

                    msg = Message.obtain();
                    msg.arg1 = 0;
                    msg.arg2 = 1;
                    msg.what = MESSAGE_CODE;
                    msg.obj = value - 1000;

                    if(value > 0){
                        sendMessageDelayed(msg, 1000);
                    }
                    break;


            }
        }
    }
}