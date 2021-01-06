package com.jsc4.aboutactivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ThreadActivity extends AppCompatActivity{

    public static final String DJSTUDY = "djstudy";
    TextView mTextView;
    Button mDownloadButton;
    ProgressBar mProgressBar;
    private static final String APK_URL = "https://download.sj.qq.com/upload/connAssitantDownload/upload/MobileAssistant_1.apk";
    private static final String TAG = ThreadActivity.class.getSimpleName();
    private Handler mHandler = new DownloadHandler(this);

    // 点住alt + insert 自动生成下面两个方法
    public TextView getTextView() {
        return mTextView;
    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        mTextView = findViewById(R.id.text_view);
        mDownloadButton = findViewById(R.id.download_button);
        mProgressBar = findViewById(R.id.progressBar);
        mDownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: 点击了下载按钮");
                // 下载是个耗时操作，要另开一个线程处理
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        download(APK_URL);
                    }
                }).start();//最后一定要让线程启动
            }
        });
    }

    /**
     * 下载指定的文件
     * @param apkUrl 资源路径
     */
    private void download(String apkUrl){
        try {
            URL url = new URL(apkUrl);// 将资源路径字符串转换成url
            URLConnection urlConnection = url.openConnection();// 打开连接通路，使用URLConnection通信
            InputStream inputStream = urlConnection.getInputStream();// 拿到输入流

            int contentLength = urlConnection.getContentLength();//要下载的文件的大小
            Log.i(TAG, "the download file's content length:"+contentLength);

            // 建立文件保存路径
//            String downloadFoldersName = Environment.getExternalStorageDirectory() + File.separator + DJSTUDY + File.separator; // 不可用，无法读取文件夹
            String downloadFoldersName = getExternalCacheDir() + File.separator + DJSTUDY + File.separator;// getExternalCacheDir()得到的是:手机内部存储/Android/data/com.jsc4.aboutactivity/cache/djstudy
            Log.i(TAG, "download: downloadFoldersName="+downloadFoldersName);

            // 创建文件路径，就是文件夹们，如果没有就创建
            File file = new File(downloadFoldersName);
            if(!file.exists()){
                file.mkdir();
            }

            // 创建包含文件名的文件路径，如果文件已存在就清除
            String fileName = downloadFoldersName + "test.apk";
            File apkFile = new File(fileName);
            if(apkFile.exists()){
                apkFile.delete();
            }

            int downloadSize = 0;//已下载大小
            byte[] bytes = new byte[1024];//字节数组大小1024
            int length = 0;//单次收到的字节长度
            OutputStream outputStream = new FileOutputStream(fileName);//创建输出流，输出目的地是fileName
            while((length = inputStream.read(bytes)) != -1){ // 从输入流读入信息，读入的信息存放在bytes数组中；如果收取的长度不为-1，说明确实收到了内容
                outputStream.write(bytes, 0, length);//将bytes数组的内容写到输出流，写入的长度是length，收到多少写入多少，因为未必每次都收满1024
                downloadSize += length;//已下载的字节数加上这次又下载的length
                int progress = downloadSize * 100 / contentLength;//进度 = 已下载的字节数/总字节数 * 100%，得到的是0-100之间的整数
                Log.i(TAG, "download progress:"+progress);

                // 更新UI
                Message message = mHandler.obtainMessage();
                message.obj = progress;
                message.what = 0;
                mHandler.sendMessage(message);

                mProgressBar.setProgress(progress);//设置进度条
            }
            Log.i(TAG, "download success");
            outputStream.close(); // 一定要把资源还给系统
            inputStream.close(); // 同上，结束时要关闭输入输出流
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.i(TAG, "download fail");

        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "download fail");
        }
    }


    // 静态内部类
    public static class DownloadHandler extends Handler {
        public final WeakReference<ThreadActivity> mActivity;

        // 这里要对mActivity进行初始化
        public DownloadHandler(ThreadActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        // 处理消息
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            ThreadActivity activity = mActivity.get();
            switch (msg.what){
                case 0:
                    int progress = (int) msg.obj;
                    activity.getProgressBar().setProgress(progress);
                    activity.getTextView().setText("progress:" + progress);
                    if(progress == 100){
                        Toast.makeText(activity, "download success", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }
}