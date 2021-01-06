package com.jsc4.aboutactivity.aidl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Toast;

import com.jsc4.aboutactivity.IMyAidlInterface;
import com.jsc4.aboutactivity.MainActivity;
import com.jsc4.aboutactivity.R;
import com.jsc4.aboutactivity.messenger.MessengerService;

public class AIDLActivity extends Activity {

    private IMyAidlInterface mIMyAidlInterface;

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_aidl);
        bindService(new Intent(this, AIDLService.class), mServiceConnection, Context.BIND_AUTO_CREATE);

        findViewById(R.id.button_aidl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIMyAidlInterface != null){
                    try {
                        String name = mIMyAidlInterface.getName("nick_know");
                        Toast.makeText(AIDLActivity.this,name + "", Toast.LENGTH_SHORT).show();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}