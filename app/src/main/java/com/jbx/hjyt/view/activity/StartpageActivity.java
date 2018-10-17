package com.jbx.hjyt.view.activity;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jbx.hjyt.MainActivity;
import com.jbx.hjyt.util.StartActivityUtil;


public class StartpageActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gohome();
    }

    public void gohome(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        StartActivityUtil.start(this , MainActivity.class);
        finish();
    }
}
