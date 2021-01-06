package com.jsc4.aboutactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class SensorManagerActivity extends AppCompatActivity implements SensorEventListener {

    ImageView mImageView;
    SensorManager mSensorManager;
    Sensor mSensor;
    private float mStartDegree = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_manager);

        // 手机转的时候图标跟着转

        mImageView = findViewById(R.id.imageView);
        /**
         * - 获取SensorManager对象
         * - 获取Sensor对象
         * - 注册Sensor对象
         * - 重写onAccuracyChanged，onSensorChanged这两个方法
         * - 注销Sensor对象
         */
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取SensorManager对象
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);//获取Sensor对象

        // 运行时 判断是否得到了可用的传感器
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null) {
            // 可用
        } else {
            // 传感器不存在
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);//注册Sensor对象  SENSOR_DELAY_UI 获取传感器数据的频率
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this); //注销Sensor对象
    }

    // 重写onAccuracyChanged，onSensorChanged这两个方法
    @Override
    public void onSensorChanged(SensorEvent event) {//当传感器的值发生变化的时候做的事情
        if(event.sensor.getType() == Sensor.TYPE_ORIENTATION){
            float degree = - event.values[0]; // 负的，反方向转

            // 旋转动画
            RotateAnimation rotateAnimation = new RotateAnimation(
                    mStartDegree, degree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
            );
            rotateAnimation.setDuration(300);
            mImageView.startAnimation(rotateAnimation);
            mStartDegree = degree;//用当前角度更新下次开始的角度

        }else if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){ // 加速度
            float x = event.values[SensorManager.DATA_X];
            float y = event.values[SensorManager.DATA_Y];
            float z = event.values[SensorManager.DATA_Z];
            if(x > 18 || y > 18 || z > 18){
                // 成功摇动手机
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {//当精度发生变化的时候做的事情

    }
}