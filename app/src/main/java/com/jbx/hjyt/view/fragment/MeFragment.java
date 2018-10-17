package com.jbx.hjyt.view.fragment;

import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jbx.hjyt.R;
import com.jbx.hjyt.app.constant.Global;
import com.jbx.hjyt.app.constant.SharePref;
import com.jbx.hjyt.model.entity.res.GetUserRes;
import com.jbx.hjyt.util.network.Api;
import com.jbx.hjyt.util.network.RxHelper;
import com.jbx.hjyt.util.network.RxSubscriber;
import com.jbx.hjyt.view.fragment.base.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class MeFragment extends BaseFragment {

    @BindView(R.id.tv_username)
    TextView tv_username;

//    @BindView(R.id.image_userlog)
//    SimpleDraweeView image_userlog;

    @BindView(R.id.ll_Exit_logon)
    LinearLayout ll_Exit_logon;

    private String token;
    private String username;
    private String id;
    @Override
    protected int setContentLayout() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {
        token = Global.getToken();
//        Log.i("我的token" , "me--"+token);
        id = Global.getId();
        getuserinformation();
    }

    public void getuserinformation(){
        Api.getDefault().getuser(token, id)
                .compose(RxHelper.handleResult())
                .subscribe(new RxSubscriber<GetUserRes>(mActivity) {
                    @Override
                    protected void _onNext(GetUserRes userRes) {
                        tv_username.setText(userRes.getUsername());
                    }
                    @Override
                    protected void _onError(String msg) {

                    }
                });
    }

    @OnClick({R.id.ll_me_recharge , R.id.ll_Exit_logon})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_me_recharge:
                setpopup();
                break;
            case R.id.ll_Exit_logon:
                Exitlogon();
                break;
        }
    }

    public void setpopup(){
        View parent = ((ViewGroup)mActivity.findViewById(android.R.id.content)).getChildAt(0);
        View popView = View.inflate(getContext(), R.layout.popup_conversation, null);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        final PopupWindow popWindow = new PopupWindow(popView,width,height);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);// 设置同意在外点击消失
        TextView tv_Determine = popView.findViewById(R.id.tv_Determine);
        tv_Determine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindow.dismiss();
            }
        });
        ColorDrawable dw = new ColorDrawable(0x30000000);
        popWindow.setBackgroundDrawable(dw);
        popWindow.showAtLocation(parent , Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public void Exitlogon(){
        if (token == null){
            Toast.makeText(mActivity, "您还没有登录哦！", Toast.LENGTH_SHORT).show();
        }else {
            Api.getDefault().exitlogon(token)
                    .compose(RxHelper.handleResult())
                    .subscribe(new RxSubscriber<List<String>>(mActivity) {
                        @Override
                        protected void _onNext(List<String> s) {
                            SharePref.getInstance().clear();
                            Toast.makeText(mActivity, "退出登录成功", Toast.LENGTH_SHORT).show();
                            ll_Exit_logon.setVisibility(View.GONE);
                            tv_username.setVisibility(View.GONE);
                            refreshfragment.onexitClick( );
                        }
                        @Override
                        protected void _onError(String msg) {
                            Toast.makeText(mActivity, "退出登录失败", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    //传递到mainactivity
    public interface Refreshfragment{
        void onexitClick();
    }

    private Refreshfragment refreshfragment;

    public void setClickListener(Refreshfragment listener){
        this.refreshfragment = listener;
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onResume() {
        super.onResume();
//        token = sp.getString("token" , null);
        if (null != token){
            ll_Exit_logon.setVisibility(View.VISIBLE);
        }
    }

}
