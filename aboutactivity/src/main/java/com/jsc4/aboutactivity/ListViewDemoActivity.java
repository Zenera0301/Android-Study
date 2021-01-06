package com.jsc4.aboutactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListViewDemoActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String LIST_VIEW_DATA_COUNTS = "list_view_data_counts";
    public static final String PREFERENCE_NAME = "preference_name";
    public static final int DEFAULT_VALUE = 10;
    private ListView mPhoneBookListView;
    private List<UserInfo> mUserInfos;
    private int mDataCounts = 10;
    private PhoneBookAdapter mPhoneBookAdapter;
    private EditText mDataCountEditText;
    private Button mCreate_list_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_demo);
        findViews();
        setData();
        setListeners();
    }

    private void findViews() {
        mPhoneBookListView = findViewById(R.id.list_view);//ListView
        mDataCountEditText = findViewById(R.id.data_count_edittext);
        mCreate_list_button = findViewById(R.id.create_list_button);
    }

    private void setData() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        // 如果能读到键LIST_VIEW_DATA_COUNTS对应的值，就用读到的值；如果读不到，就用默认值DEFAULT_VALUE
        mDataCounts = sharedPreferences.getInt(LIST_VIEW_DATA_COUNTS, DEFAULT_VALUE);

        mDataCountEditText.setText(String.valueOf(mDataCounts));
        mUserInfos = new ArrayList<>();
        for(int i = 0; i < mDataCounts; ++i){
            mUserInfos.add(new UserInfo("aaa", 21));
        }

        //Adapter（数据放到柜子里）
        mPhoneBookAdapter = new PhoneBookAdapter(ListViewDemoActivity.this, mUserInfos);
        mPhoneBookListView.setAdapter(mPhoneBookAdapter);//绑定两者 （柜子放到房子里）
    }

    private void saveData2Preference() {
        // 系统会自动帮我们创建一个xml文件，名字是：preference_name, 地址：data/data/com.jsc4.aboutactivity
        SharedPreferences sharedPreferences = ListViewDemoActivity.this.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LIST_VIEW_DATA_COUNTS, mDataCounts);
        editor.apply();

//        // 当内容发生改变时的监听器
//        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
//            @Override
//            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//
//            }
//        });
    }

    private void setListeners() {
        // 更新ListView数据  （数据是存放在Adapter中，如果Adapter中进行数据更新了，需要通知ListView进行数据刷新）
        mPhoneBookAdapter.notifyDataSetChanged();

        // Item的点击事件
        mPhoneBookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 点击第一条时，将所有数据更新
                if(position == 0){
                    // 新建另外一批数据
                    mUserInfos.clear();
                    mUserInfos.add(new UserInfo("第1条数据",1));
                    mUserInfos.add(new UserInfo("第2条数据",2));
                    mUserInfos.add(new UserInfo("第3条数据",3));

                    // 替换掉老的数据
                    // 刷新ListView，让他更新自己的视图
                    mPhoneBookAdapter.refreshData(mUserInfos);
                }

                Toast.makeText(ListViewDemoActivity.this, mUserInfos.get(position).getUserName() + "被我点击了，怎么办？", Toast.LENGTH_LONG).show();
            }
        });

        // Item的长按事件
        mPhoneBookListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListViewDemoActivity.this, mUserInfos.get(position).getUserName() + "被我长按了，怎么办？", Toast.LENGTH_LONG).show();
                return false; // 这句话保留
            }
        });

        mCreate_list_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_list_button:
                String countString = mDataCountEditText.getText().toString();
                if(! "".equals(countString)){
                    mDataCounts = Integer.parseInt(countString);
                    refreshListView();
                    saveData2Preference();
                }else{
                    Toast.makeText(ListViewDemoActivity.this, "请输入创建item的个数", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    private void refreshListView() {
        mUserInfos.clear();
        for (int i = 0; i < mDataCounts; i++) {
            mUserInfos.add(new UserInfo("大狗辰", 25));
        }
        mPhoneBookAdapter.refreshData(mUserInfos);
        mPhoneBookAdapter.notifyDataSetChanged();
    }
}