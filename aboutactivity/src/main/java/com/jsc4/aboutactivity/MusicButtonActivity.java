package com.jsc4.aboutactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

public class MusicButtonActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mStartButton;
    private Button mStopButton;
    private MusicService mMusicService;
    private ServiceConnection mServiceConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.LocalBinder localBinder = (MusicService.LocalBinder) service;
            mMusicService = localBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_button);

        mStartButton = findViewById(R.id.start_music_button);
        mStopButton = findViewById(R.id.stop_music_button);

        mStartButton.setOnClickListener(this);
        mStopButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.start_music_button:
                startService(new Intent(MusicButtonActivity.this, MusicService.class));
                bindService(new Intent(MusicButtonActivity.this, MusicService.class), mServiceConnection, BIND_AUTO_CREATE);
                break;

            case R.id.stop_music_button:
                unbindService(mServiceConnection);
                stopService(new Intent(MusicButtonActivity.this, MusicService.class));
                break;

            case R.id.get_music_progress_button:
                if(mMusicService != null){
                    int progress = mMusicService.getMusicPlayProgress();
                }
                break;
        }
    }
}