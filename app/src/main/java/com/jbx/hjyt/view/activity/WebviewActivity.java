package com.jbx.hjyt.view.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.jbx.hjyt.R;
import com.jbx.hjyt.view.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class WebviewActivity extends BaseActivity {

    @BindView(R.id.wb_baidu)
    WebView wb_baidu;
    @BindView(R.id.iv_Return)
    ImageView iv_Return;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private String url ;

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_webview);
    }

    @Override
    protected void initView() {
        Bundle bundle = this.getIntent().getExtras();
        url = bundle.getString("url");

        //解析
        wb_baidu.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url == null) return false;
                Log.i("webviewhttp++" , url);
                view.loadUrl(url);
                return true;
            }
        });

        //设置WebChromeClient类
        wb_baidu.setWebChromeClient(new WebChromeClient() {
            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    progressBar.setProgress(newProgress);
                } else if (newProgress == 100) {
                    progressBar.setProgress(newProgress);
                }
            }
        });

        //设置WebViewClient类
        wb_baidu.setWebViewClient(new WebViewClient() {
            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                beginLoading.setText("开始加载了");

            }

            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {
//                endLoading.setText("结束加载了");
                progressBar.setVisibility(View.GONE);
            }
        });


        wb_baidu.setOnTouchListener ( new View.OnTouchListener () {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction ()) {
                    case MotionEvent.ACTION_DOWN :
                    case MotionEvent.ACTION_UP :
                        if (!v.hasFocus ()) {
                            v.requestFocus ();
                        }
                        break ;
                }
                return false ;
            }
        });
        //给webview设置点击事件
        wb_baidu.getSettings().setJavaScriptEnabled(true);

        wb_baidu.loadUrl(url);
        Log.i("webviewhttp--" , url);

    }

    @OnClick({R.id.iv_Return})
    public void onclick(View view){
        switch (view.getId()){
            case R.id.iv_Return:
                finish();
                break;
        }
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void initEvent() {

    }

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wb_baidu.canGoBack()) {
            wb_baidu.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    //销毁Webview
    @Override
    protected void onDestroy() {
        if (wb_baidu != null) {
            wb_baidu.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            wb_baidu.clearHistory();

            ((ViewGroup) wb_baidu.getParent()).removeView(wb_baidu);
            wb_baidu.destroy();
            wb_baidu = null;
        }
        super.onDestroy();
    }
}
