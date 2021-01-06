package com.jsc4.aboutactivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PhoneBookAdapter extends BaseAdapter {

    private Context mContext;

    private LayoutInflater mLayoutInflater;//Adapter用这个LayoutInflater把布局读出来

    private List<UserInfo> mUserInfos = new ArrayList<>();

    public PhoneBookAdapter(Context context, List<UserInfo> userInfos){
        mContext = context;
        mUserInfos = userInfos;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//利用系统服务初始化mLayoutInflater
    }
    @Override
    public int getCount() {
        // 有多少条数据
        return mUserInfos.size();
    }

    @Override
    public Object getItem(int position) {
        // 返回某一条数据对象
        return mUserInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        //
        return position;
    }

    /**
     * Item不同时，可以用
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    /**
     * 每一行数据显示在界面上，用户能够看到时，就会调用
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 返回某一条数据的一个视图  和数据之间进行绑定
        ViewHolder viewHolder;
        if(convertView == null){  // 优化1： 仅第一次创建视图，实现convertView视图复用
            convertView = mLayoutInflater.inflate(R.layout.item_phone_book_friend, parent, false);

            viewHolder = new ViewHolder();
            // 获取控件
            viewHolder.nameTextView       = convertView.findViewById(R.id.name_text_view);
            viewHolder.ageTextView        = convertView.findViewById(R.id.age_text_view);
            viewHolder.avatarImageView   = convertView.findViewById(R.id.avatar_image_view);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 和数据进行绑定
        viewHolder.nameTextView.setText(mUserInfos.get(position).getUserName());
        viewHolder.ageTextView.setText(String.valueOf(mUserInfos.get(position).getAge()));
        viewHolder.avatarImageView.setImageResource(R.mipmap.ic_launcher);

        // 返回视图
        return convertView;
    }

    class ViewHolder{ // 优化2：通过ViewHolder将数据缓存起来，不用每次都findViewById去查找
        TextView nameTextView;
        TextView ageTextView;
        ImageView avatarImageView;
    }

    /**
     * 刷新数据
     * @param userInfos
     */
    public void refreshData(List<UserInfo> userInfos){
        mUserInfos = userInfos;
        notifyDataSetChanged();
    }
}
