package com.jsc4.socketchat;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    TextView tv_show;
    EditText ed_ip;
    EditText ed_port;
    Button bt_connect;
    EditText ed_send;
    Button bt_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_show = findViewById(R.id.tv_show);
        ed_ip = findViewById(R.id.ed_ip);
        ed_port = findViewById(R.id.ed_port);
        bt_connect = findViewById(R.id.bt_connect);
        ed_send = findViewById(R.id.ed_send);
        bt_send = findViewById(R.id.bt_send);

        bt_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect();
            }
        });

        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });

    }

    //***********************************
    Socket socket = null;
    BufferedWriter writer = null;
    BufferedReader reader = null;

//    public void connect(){
//        // 一个专门的线程：从socket中读取数据 并显示在界面中
//        AsyncTask<Void, String, Void> read = new AsyncTask<Void, String, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//
//                try {
//                    socket = new Socket(ed_ip.getText().toString(), Integer.parseInt(ed_port.getText().toString()));
//                    writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                    publishProgress("@success");//如果能走到这一步，说明上述3个变量全部创建成功，即成功建立连接
//                }catch(IOException e){
//                    //Toast.makeText(MainActivity.this, "无法建立连接", Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
//
//                try {
//                    String line;
//                    while((line = reader.readLine()) != null){
//                        publishProgress(line);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//
//            @Override
//            protected void onProgressUpdate(String... values) {
//                super.onProgressUpdate(values);
//                if(values[0].equals("@success")){
//                    Toast.makeText(MainActivity.this, "成功建立连接", Toast.LENGTH_SHORT).show();
//                }
//                tv_show.append(values[0]);
//            }
//        };
//        read.execute();
//    }


    public void connect() {
        AsyncTask<Void, String, Void> read = new AsyncTask<Void, String, Void>() {
            @Override
            protected Void doInBackground(Void... view) {
                try {
                    Socket socket = new Socket(ed_ip.getText().toString(), 8888);
                    writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    publishProgress("@success");
                } catch (UnknownHostException e) {
                    Log.i("djtest", "doInBackground: 无法建立连接");
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.i("djtest", "doInBackground: 无法建立连接");
                    e.printStackTrace();
                }

                try {
                    String line;
                    while ((line = reader.readLine()) != null)
                        publishProgress(line);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(String... values) {
                if(values[0].equals("@success")){
                    Log.i("djtest", "onProgressUpdate: 成功建立连接");
                }
                tv_show.append("别人说：" + values[0] + "\n");
                super.onProgressUpdate(values);
            }
        };
        read.execute();
    }

    public void send(){
        tv_show.append("我说："+ ed_send.getText().toString() + "\n");
//        ed_send.setText("");//发送后清空，便于下次输入
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    writer.write(ed_send.getText().toString() + "\n");
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}