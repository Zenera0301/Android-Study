package com.jsc4.aboutactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GridViewDemoActivity extends AppCompatActivity {

    private GridView mPhoneBookGridView;
    private List<UserInfo> mUserInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view_demo);
        mPhoneBookGridView = findViewById(R.id.grid_view);//ListView

        mUserInfos = new ArrayList<>();
        mUserInfos.add(new UserInfo("aaa", 21));
        mUserInfos.add(new UserInfo("bbb", 21));
        mUserInfos.add(new UserInfo("ccc", 21));
        mUserInfos.add(new UserInfo("ddd", 21));
        mUserInfos.add(new UserInfo("eee", 21));
        mUserInfos.add(new UserInfo("fff", 21));
        mUserInfos.add(new UserInfo("ggg", 21));
        mUserInfos.add(new UserInfo("hhh", 21));

        final GridViewAdapter gridViewAdapter = new GridViewAdapter(GridViewDemoActivity.this, mUserInfos);//Adapter（数据放到柜子里）
        mPhoneBookGridView.setAdapter(gridViewAdapter);//绑定两者 （柜子放到房子里）

        // 更新ListView数据  （数据是存放在Adapter中，如果Adapter中进行数据更新了，需要通知ListView进行数据刷新）
        gridViewAdapter.notifyDataSetChanged();

        // Item的点击事件
        mPhoneBookGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                    gridViewAdapter.refreshData(mUserInfos);
                }

                Toast.makeText(GridViewDemoActivity.this, mUserInfos.get(position).getUserName() + "被我点击了，怎么办？", Toast.LENGTH_LONG).show();
            }
        });

        // Item的长按事件
        mPhoneBookGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(GridViewDemoActivity.this, mUserInfos.get(position).getUserName() + "被我长按了，怎么办？", Toast.LENGTH_LONG).show();
                return false; // 这句话保留
            }
        });
    }
}