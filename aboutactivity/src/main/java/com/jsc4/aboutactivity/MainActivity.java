package com.jsc4.aboutactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.renderscript.ScriptGroup;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jsc4.aboutactivity.json.UserData;
import com.jsc4.aboutactivity.xml.SAXParseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class MainActivity extends AppCompatActivity {

    Handler mhandler = new Handler();
    public static final int REQUEST_CODE = 9999;
    private static final String TAG = MainActivity.class.getSimpleName();
    TextView textView;
    Button list_view_button, grid_view_button;
    private LayoutInflater mLayoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获得LayoutInflater实例
        mLayoutInflater = getLayoutInflater();
        mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        mLayoutInflater = LayoutInflater.from(MainActivity.this);

        View view = mLayoutInflater.inflate(R.layout.activity_main, null);

        Log.i(TAG, "onCreate");
        textView = findViewById(R.id.title_text_view);
        list_view_button = findViewById(R.id.list_view_button);
        grid_view_button = findViewById(R.id.grid_view_button);
        list_view_button.setOnClickListener(new list_view_button_Onclick());
        grid_view_button.setOnClickListener(new grid_view_buttonOnclick());
        final String title = textView.getText().toString();
        mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                UserInfo userInfo = new UserInfo("小明",12);
                // 执行的内容
                Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("userInfo", userInfo);
                startActivityForResult(intent, REQUEST_CODE);
            }
        }, 1000);  // 延时1s后执行

//        testFileDemo();
//        testAssets();

//        try {
//            testSAXParse();
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    /**
     * 测试文件
     */
    private void testFileDemo() {
        // 在内部存储中创建新文件test.txt
        File file = new File(getFilesDir(), "test.txt");
        Log.i(TAG, "testFileDemo:getFilesDir():"+ getFilesDir().getAbsolutePath());
        Log.i(TAG, "testFileDemo:file path:"+ file.getAbsolutePath());
        // getFilesDir():/data/user/0/com.jsc4.aboutactivity/files
        // file path:/data/user/0/com.jsc4.aboutactivity/files/test.txt

        try {
            boolean isSuccess = file.createNewFile();
        } catch (IOException e) {
            Log.i(TAG, "test.txt create error: "+ e.toString());
            e.printStackTrace();
        }

        String str = "随便写点什么";
        try {
            FileOutputStream fileOutputStream = openFileOutput("test2.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(str.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 检查sd卡是否可用
        String state = Environment.getExternalStorageState();
        if(TextUtils.equals(state, Environment.MEDIA_MOUNTED)){

        }
    }

    void testAssets(){
        // 直接读路径
        WebView webView = new WebView(this);
        webView.loadUrl("file:///android_asset/test.html");

        // 直接读入流
        try {
            // open的只能是文件，不能是文件夹
            InputStream inputStream = getResources().getAssets().open("test.html");
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "文件读取异常", Toast.LENGTH_LONG).show();
        }

        // 读取文件名列表
        try {
            String[] fileNames = getAssets().list("images/");
            Log.i(TAG, "testAssets: "+ Arrays.toString(fileNames));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 读一张图片并显示
        try {
            InputStream inputStream = getAssets().open("images/square32green.png");
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 读音频
        try {
            Log.i(TAG, "testAssets: 播放音乐");
            AssetFileDescriptor assetFileDescriptor = getAssets().openFd("lovelove.mps");
            MediaPlayer player = new MediaPlayer();
            player.reset();
            player.setDataSource(
                    assetFileDescriptor.getFileDescriptor(),
                    assetFileDescriptor.getStartOffset(),
                    assetFileDescriptor.getLength());
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void testResFile(){
        InputStream inputStream = getResources().openRawResource(R.raw.lovelove);

        getResources().getColor(R.color.colorAccent);
        getResources().getString(R.string.age);
        getResources().getDrawable(R.drawable.ic_launcher_background);
    }

    void testSDCard(){
        File file = new File("/sdcard/test/a.txt");//这里的SD卡路径未必正确
        File tempFile = Environment.getExternalStorageDirectory().getAbsoluteFile();//SD卡路径一般通过这个方法去获取

        Environment.getDataDirectory();// 获取Android中的data数据目录
        Environment.getDownloadCacheDirectory();
        Environment.getExternalStorageDirectory();

    }


    void testSAXParse() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();

        SAXParseHandler saxParseHandler = new SAXParseHandler();
        xmlReader.setContentHandler(saxParseHandler);

        InputStream inputStream = getResources().openRawResource(R.raw.test);

        InputSource inputSource = new InputSource(inputStream);//把流给它

        xmlReader.parse(inputSource);

        saxParseHandler.getXMLList();

        // 以上的简单写法
        XMLReader xmlReaderTest = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        xmlReaderTest.setContentHandler(new SAXParseHandler());
        xmlReaderTest.parse(new InputSource(getResources().openRawResource(R.raw.test)));



        // pull解析  xml里面才能读到
        XmlResourceParser xmlResourceParser = getResources().getXml(R.xml.test);

        try {
            while(xmlResourceParser.getEventType() != XmlResourceParser.END_DOCUMENT){
                if(xmlResourceParser.getEventType() == XmlResourceParser.START_TAG){
                    String tagName = xmlResourceParser.getName();
                    if(TextUtils.equals(tagName, "item")){
                        String id = xmlResourceParser.getAttributeNamespace(0);
                    }
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        // DOM

        // json
        InputStream is = getResources().openRawResource(R.raw.json);// 获得输入流
        String jsonString = getStringByInputStream();//从输入流获得字符串
        try {
            JSONObject jsonObject = new JSONObject(jsonString);//new一个json对象，把字符串传入
            String title = jsonObject.getString("title");//获得字符串类型的数据，标识是“title”
            JSONObject userJSONObject = jsonObject.getJSONObject("user");//获得json对象，标识是“user”
            userJSONObject.getLong("id");//获得数字，标识是“id”
            JSONArray jsonArray = jsonObject.getJSONArray("images");//获得数组，标识是“images”
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // gson 一步到位，将json数据转换为对象
        Gson gson = new Gson();
        UserData userData = gson.fromJson(jsonString, UserData.class);

    }

    // 去网上找

    /**
     * 把流转换成string
     * @return
     */
    private String getStringByInputStream() {
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: requestCode="+requestCode+"  resultCode="+ resultCode);
        if(requestCode == REQUEST_CODE && resultCode == SplashActivity.RESULT_CODE){
            if(data != null){
                String title = data.getStringExtra("title");
                textView.setText(title);
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    /**
     * 点击button跳转到ListViewDemoActivity
     */
    private class list_view_button_Onclick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, ListViewDemoActivity.class);
            startActivity(intent);
        }
    }

    private class grid_view_buttonOnclick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, GridViewDemoActivity.class);
            startActivity(intent);
        }
    }
}
