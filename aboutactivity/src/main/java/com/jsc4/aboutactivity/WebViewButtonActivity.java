package com.jsc4.aboutactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.webkit.CookieManager;
import android.webkit.GeolocationPermissions;
import android.webkit.HttpAuthHandler;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SafeBrowsingResponse;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebHistoryItem;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.HashMap;

public class WebViewButtonActivity extends AppCompatActivity {

    private WebView mWebView;

    public class TestJSEvent{

        @JavascriptInterface
        public void showToast(String toast){
            Toast.makeText(WebViewButtonActivity.this, toast, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_button);

        mWebView = findViewById(R.id.activity_webview);

        // 设置为调试状态 仅支持Android4.4以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        mWebView.loadUrl("http://www.baidu.com");
//        mWebView.loadUrl("file:///android_asset/test.html");//读取本地html文件的标准格式
//        HashMap<String, String> map = new HashMap<>();
//        map.put("token", "xxxxx");
//        map.put("myhead", "header");
//        mWebView.loadUrl("http://www.baidu.com",map);//改变header，传递token

        mWebView.getSettings().setJavaScriptEnabled(true);//允许和JavaScript交互，可以实现页面内的跳转
        mWebView.getSettings().setDomStorageEnabled(true);//允许android调用javascript

        // JS调用原生App
        mWebView.addJavascriptInterface(new TestJSEvent(), "TestApp");

        // 原生App调用JS
        mWebView.loadUrl("javascript:javaCallJS('')");




        // 常用设置2：WebViewClient
        mWebView.setWebViewClient(new TestWebViewClient());

        // 常用设置3：WebChromeClient
        mWebView.setWebChromeClient(new TestWebChromeClient());
    }


    // 自定义一个内部类用于设置WebChromeClient
    public class TestWebViewClient extends WebViewClient{
        public TestWebViewClient(){
            super();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 是否重新加载（拦截界面）
//                if(url.contains("404")){
//                    // 可以跳转至登录界面
//                }

//                view.loadUrl("https://www.zhihu.com/signin?next=%2F");

//                return super.shouldOverrideUrlLoading(view, url);//偶尔闪退，因为有js定义的scheme无法被处理

            if (url == null) return false;

            try{
                if(!url.startsWith("http://") && !url.startsWith("https://")){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
            }catch (Exception e){//防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                return true;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
            }

            // TODO Auto-generated method stub
            //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
            view.loadUrl(url);
            return true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // 页面开始，显示loading动画
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            // 页面结束，隐藏loading动画
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            // url 替换
            if(url.contains("log.img")){
                // 替换成更清晰的图片，省流量的方法
            }
            super.onLoadResource(view, url);
        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);
        }

        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

            // 拦截的是url
            return super.shouldInterceptRequest(view, url);
        }

        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            //拦截的是资源，如点赞，其实不会跳转新的界面，但也是对服务器的一种请求，请求点赞数加1
            // hybrid 离线网页
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg) {
            super.onTooManyRedirects(view, cancelMsg, continueMsg);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
        }

        @Override
        public void onFormResubmission(WebView view, Message dontResend, Message resend) {
            super.onFormResubmission(view, dontResend, resend);
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            super.doUpdateVisitedHistory(view, url, isReload);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
        }

        @Override
        public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
            super.onReceivedClientCertRequest(view, request);
        }

        @Override
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }

        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return super.shouldOverrideKeyEvent(view, event);
        }

        @Override
        public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
            super.onUnhandledKeyEvent(view, event);
        }

        @Override
        public void onScaleChanged(WebView view, float oldScale, float newScale) {
            super.onScaleChanged(view, oldScale, newScale);
        }

        @Override
        public void onReceivedLoginRequest(WebView view, String realm, @Nullable String account, String args) {
            super.onReceivedLoginRequest(view, realm, account, args);
        }

        @Override
        public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
            return super.onRenderProcessGone(view, detail);
        }

        @Override
        public void onSafeBrowsingHit(WebView view, WebResourceRequest request, int threatType, SafeBrowsingResponse callback) {
            super.onSafeBrowsingHit(view, request, threatType, callback);
        }


    }

    // 自定义一个内部类用于设置WebChromeClient
    public class TestWebChromeClient extends WebChromeClient{
        public TestWebChromeClient(){
            super();
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            // 获取网页加载的进度 可制作进度条
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            // 获取网页标题
            super.onReceivedTitle(view, title);
        }

        @Override
        public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
            super.onReceivedTouchIconUrl(view, url, precomposed);
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);
        }

        @Override
        public void onCloseWindow(WebView window) {
            super.onCloseWindow(window);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            return super.onJsConfirm(view, url, message, result);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

        @Override
        public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
            return super.onJsBeforeUnload(view, url, message, result);
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }

        @Override
        public void onGeolocationPermissionsHidePrompt() {
            super.onGeolocationPermissionsHidePrompt();
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        }

    }
    // 导航---》后退时返回上一页
    @Override
    public void onBackPressed() {
        if(mWebView != null && mWebView.canGoBack()){
            // 历史记录
//            WebBackForwardList webBackForwardList = mWebView.copyBackForwardList();
//            WebHistoryItem historyItem = webBackForwardList.getItemAtIndex(0);
//            String historyUrl = historyItem.getUrl();

            mWebView.goBack();
//            mWebView.goForward();
//            mWebView.goBackOrForward(+2);//前进2步
//            mWebView.goBackOrForward(-1);//后退1步
//            mWebView.reload();

        }else{
            super.onBackPressed();
        }
    }
}