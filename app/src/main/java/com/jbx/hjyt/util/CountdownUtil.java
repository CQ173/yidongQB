package com.jbx.hjyt.util;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * @author zp
 * @class name: com.tomtop.shop.helper
 * @description: 倒计时辅助类
 * @date 2015/7/28 0028 上午 10:48
 */
public class CountdownUtil extends CountDownTimer{

    private TextView mButton;

    public CountdownUtil(TextView button, long millisInFuture, long countDownInterval) {//控件，定时总时间,间隔时间
        super(millisInFuture, countDownInterval);
        this.mButton=button;

    }

    @Override    public void onTick(long millisUntilFinished) {
        mButton.setClickable(false);//设置不可点击
        mButton.setText(millisUntilFinished/1000+"秒后可重新发送");//设置倒计时时间

    }
    @Override
    public void onFinish() {
        mButton.setClickable(true);//重新获得点击
        mButton.setText("重新获取验证码");

    }

}
