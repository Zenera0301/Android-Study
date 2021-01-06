package com.jsc4.aboutactivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MusicService extends Service {

    private static final String TAG = MusicService.class.getSimpleName();

    private MediaPlayer mMediaPlayer;
    private IBinder mIBinder = new LocalBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
        mMediaPlayer = MediaPlayer.create(this, R.raw.lovelove);

//        Intent intent = new Intent(this, MusicButtonActivity.class);
//        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
//        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        Notification notification = new NotificationCompat.Builder(this)
//                .setContentTitle("这是测试通知标题")  //设置标题
//                .setContentText("这是测试通知内容") //设置内容
//                .setWhen(System.currentTimeMillis())  //设置时间
//                .setSmallIcon(R.mipmap.ic_launcher)  //设置小图标  只能使用alpha图层的图片进行设置
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))   //设置大图标
//                .setContentIntent(pi) // 点击跳转
//                .setAutoCancel(true)  // 阅后取消
//                .build();
//        manager.notify(1, notification);


        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("FCM_foreground", "FCM", NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(false);
            channel.enableVibration(false);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }


        // 通知与提醒
        Intent stateIntent = new Intent(this, MusicButtonActivity.class);
        PendingIntent statePendingIntent = PendingIntent.getActivity(this, 0, stateIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this, "FCM_foreground")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("标题")//设置内容标题
                .setContentText("内容")//设置显示的内容
                .setContentIntent(statePendingIntent)//设置意图
                .setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE)// 只通知1次
                .setSound(null) // 不发出声音
                .setPriority(Notification.PRIORITY_HIGH) // 优先级高
                .setAutoCancel(true);//点击后消失

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManagerCompat.notify((int) 123, mBuilder.build());

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        mMediaPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        mMediaPlayer.stop();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return mIBinder;
    }

    public class LocalBinder extends Binder {

        MusicService getService(){
            return MusicService.this;
        }
    }

    public int getMusicPlayProgress(){
        return 18;
    }
}
