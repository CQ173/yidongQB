package com.jbx.hjyt.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.jbx.hjyt.MainActivity;
import com.jbx.hjyt.R;
import com.jbx.hjyt.app.constant.Global;
import com.jbx.hjyt.model.entity.res.Param;
import com.jbx.hjyt.model.entity.res.UserinfoRes;
import com.jbx.hjyt.util.Constants;
import com.jbx.hjyt.util.CountdownUtil;
import com.jbx.hjyt.util.MD5Util;
import com.jbx.hjyt.util.ToastUtil;
import com.jbx.hjyt.util.network.Api;
import com.jbx.hjyt.util.network.RxHelper;
import com.jbx.hjyt.util.network.RxSubscriber;
import com.jbx.hjyt.view.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.tv_getcode)
    TextView tv_getcode;
    @BindView(R.id.tv_login)
    TextView tv_login;

    private String phone;
    private String phonecode;
    //获取验证码
    private String sign = "^^YdDc^^+~!@#$$#@!~.{(||)}";
    //登录
    private String login = "**yDdC**yUCq\"|{(.NIUBI.)}|\"";

    private CountdownUtil countdownUtil;
    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initView() {
        countdownUtil = new CountdownUtil( tv_getcode , 60000 , 1000);

    }
    @OnClick({R.id.tv_getcode , R.id.tv_login})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_getcode:
                if ("".equals(et_phone.getText().toString())){
                    ToastUtil.show("请输入手机号码!");
                }else if (et_phone.getText().toString().trim().length() != 11) {
                    ToastUtil.show("账号格式错误!");
                }else {
                    getcode();
                }
                break;
            case R.id.tv_login:
                userlogin(et_phone.getText().toString() , MD5Util.md5Password(et_code.getText().toString() + phone+login) ,et_code.getText().toString());
//                Log.i("userloog--" , "login--"+  MD5Util.md5Password(et_code.getText().toString() + phone+login) );
                break;
        }
    }

    /**
     * 获取验证码
     */
    public void getcode(){
        phone = et_phone.getText().toString();
//        Log.i("userloog--" ,"sign--"+ MD5Util.md5Password(phone+sign) );
        Api.getDefault().getiphonecode(phone, MD5Util.md5Password(phone+sign))
                .compose(RxHelper.handleResult())
                .subscribe(new RxSubscriber<Param>(this) {
                    @Override
                    protected void _onNext(Param s) {
//                        Log.i("code:", s.getCode());
                        countdownUtil.start();
                    }

                    @Override
                    protected void _onError(String msg) {

                    }
                });
    }

    /**
     * 登录
     * @param mobile
     * @param sign
     * @param verifyCode
     */
    public void userlogin(String mobile , String sign  ,String verifyCode){
        Api.getDefault().reloadlogin(mobile, sign , verifyCode )
                .compose(RxHelper.handleResult())
                .subscribe(new RxSubscriber<UserinfoRes>(this) {
                    @Override
                    protected void _onNext(UserinfoRes list) {
                        //Token
                        Global.saveToken(list.getToken());
                        //用户id
                        Global.saveID(list.getId());
                        //username
                        Global.saveUsername(list.getUsername());
//                        SharedPreferences sp = getSharedPreferences("loginToken", 0);
//                        SharedPreferences.Editor editor = sp.edit();
//                        editor.putString("token" , list.getToken());
//                        editor.putString("id" ,list.getId());
//                        editor.putString("username" ,list.getUsername());
//
//                        Log.i("gettoken" , list.getToken().toString());
//                        Log.i("用户账号" , list.getUsername().toString());
//                        Log.i("用户ID" , list.getId().toString());
//                        editor.commit();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        ToastUtil.show("登录成功!");
                        finish();
                    }

                    @Override
                    protected void _onError(String msg) {
                        ToastUtil.show("登录失败!");
                    }
                });
    }


    @Override
    protected void obtainData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){
            return;
        }
        switch (requestCode) {
            case Constants.CODE_LOGPWD:
                setResult(RESULT_OK);
                finish();
                break;
        }
    }
}
