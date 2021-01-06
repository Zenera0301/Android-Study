package com.jsc4.aboutactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

public class TestFragmentActivity extends AppCompatActivity {

    private static final String TAG = TestFragmentActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fragment);

        FragmentManager fragmentManager = getSupportFragmentManager();  // 找到校长
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();  // 找到秘书

        // 创建一个fragment实例
//        TestFragment testFragment = new TestFragment();
        TestFragment testFragment = TestFragment.newInstance("通过fragment参数传递的消息", null);

        // 将该实例添加到ViewGroup
        fragmentTransaction.add(R.id.fragment_view, testFragment).commit();  // 全局只需commit一次
//        fragmentTransaction.add(R.id.fragment_view, testFragment, "test_fragment_view");//通过tag的方式添加实例

        // 将该实例移除
//        fragmentTransaction.remove(testFragment).commit();  // 最后要commit，向校长汇报

        // 找到fragment
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_test1);  // 找到fragment
//        Fragment fragment1 = fragmentManager.findFragmentByTag("test_fragment_view");  // 通过tag找到fragment

        if(fragment instanceof TestFragment){
            // 如果是TestFragment类型
            Log.i(TAG, "onCreate:fragment类型是TestFragment");
        }else{
            throw new IllegalStateException("this is not TestFragment");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}