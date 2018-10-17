package com.jbx.hjyt.view.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.StringUtils;
import com.jbx.hjyt.R;
import com.jbx.hjyt.app.constant.Global;
import com.jbx.hjyt.model.entity.res.GetHomePageRes;
import com.jbx.hjyt.model.entity.res.GetHomebannerRes;
import com.jbx.hjyt.model.entity.res.WholeInfoRes;
import com.jbx.hjyt.util.StartActivityUtil;
import com.jbx.hjyt.util.network.Api;
import com.jbx.hjyt.util.network.RxHelper;
import com.jbx.hjyt.util.network.RxSubscriber;
import com.jbx.hjyt.view.activity.LoginActivity;
import com.jbx.hjyt.view.activity.WebviewActivity;
import com.jbx.hjyt.view.adapter.LoanHomepageAdapter;
import com.jbx.hjyt.view.fragment.base.BaseFragment;
import com.jbx.hjyt.view.widget.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class LoanFragment extends BaseFragment {

    @BindView(R.id.loan_banner)
    Banner LoanBanner;
    @BindView(R.id.rv_red_bag)
    RecyclerView rvLoanList;
    @BindView(R.id.ll_One)
    LinearLayout ll_One;
    @BindView(R.id.tv_rapidly)
    TextView tv_rapidly;
    @BindView(R.id.ll_Two)
    LinearLayout ll_Two;
    @BindView(R.id.tv_credit)
    TextView tv_credit;
    @BindView(R.id.ll_Three)
    LinearLayout ll_Three;
    @BindView(R.id.tv_Mouth)
    TextView tv_Mouth;
    @BindView(R.id.ll_Four)
    LinearLayout ll_Four;
    @BindView(R.id.tv_large_amount)
    TextView tv_large_amount;

    private LoanHomepageAdapter loanHomepageAdapter;
    private List<String> data = new ArrayList<>();

    private String token;
    @Override
    protected int setContentLayout() {
        return R.layout.fragment_load;
    }

    @Override
    protected void initView() {
        token = Global.getToken();
        Log.i("我的token" , "loan--"+token);
        onResume();

        tv_rapidly.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD)); //字体加粗
        tv_credit.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD)); //字体加粗
        tv_Mouth.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD)); //字体加粗
        tv_large_amount.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD)); //字体加粗
    }

    public void holler(){
        rvLoanList.addItemDecoration(new DividerItemDecoration(getContext() , DividerItemDecoration.VERTICAL));
        loanHomepageAdapter.setClickListener(new LoanHomepageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String url) {
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                if (StringUtils.isEmpty(token)){
                    StartActivityUtil.start(mActivity, LoginActivity.class);
                }else {
                    StartActivityUtil.start(mActivity, WebviewActivity.class, bundle);
                }
            }
        });
    }

    @OnClick({R.id.ll_One ,R.id.ll_Two ,R.id.ll_Three ,R.id.ll_Four})
    public void onclick(View view){
        switch (view.getId()){
            case R.id.ll_One:
                getHomeaa();
                break;
            case R.id.ll_Two:
                int a = 1;
                gettwo(a);
                break;
            case R.id.ll_Three:
                int b = 2;
                gettwo(b);
                break;
            case R.id.ll_Four:
                int c = 3;
                gettwo(c);
                break;
        }
    }

    @Override
    protected void obtainData() {
        getHomebanner();
        getHomeaa();
        getHomepageall();
    }

    @Override
    protected void initEvent() {

    }

    /**
     * 获取首页banner
     */
    private void getHomebanner(){
        Api.getDefault().getHomeInfo()
                .compose(RxHelper.handleResult())
                .subscribe(new RxSubscriber<List<GetHomebannerRes>>(mActivity) {
                    @Override
                    protected void _onNext(List<GetHomebannerRes> list) {
                        for (int i = 0;i<list.size();i++){
                            data.add(list.get(i).getPic());
                        }
                        //设置图片加载器
                        LoanBanner.setImageLoader(new GlideImageLoader());
                        //设置图片集合
                        LoanBanner.setImages(data);
                        //banner设置方法全部调用完毕时最后调用
                        LoanBanner.start();
                        onclickbanner(list);
                    }
                    @Override
                    protected void _onError(String msg) {

                    }
                });
    }

    public void onclickbanner(List<GetHomebannerRes> list){
        LoanBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (!list.get(position).getUrl().equals("")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("url", list.get(position).getUrl());
                    if (StringUtils.isEmpty(token)){
                        StartActivityUtil.start(mActivity, LoginActivity.class);
                    }else {
                        StartActivityUtil.start(mActivity, WebviewActivity.class, bundle);
                    }
                }else {

                }
            }
        });
    }

    /**
     * 获取首页数据
     */
    private void getHomeaa(){
        Api.getDefault().getallhome()
                .compose(RxHelper.handleResult())
                .subscribe(new RxSubscriber<List<WholeInfoRes>>(mActivity) {
                    @Override
                    protected void _onNext(List<WholeInfoRes> list) {
                        loanHomepageAdapter = new LoanHomepageAdapter(getContext(), list);
                        rvLoanList.setLayoutManager(new LinearLayoutManager(getContext()));
                        rvLoanList.setAdapter(loanHomepageAdapter);
                        rvLoanList.setNestedScrollingEnabled(false);
                        loanHomepageAdapter.notifyDataSetChanged();
                        holler();
                    }

                    @Override
                    protected void _onError(String msg) {

                    }
                });
    }

    public void gettwo( int a){
        Api.getDefault().getlargeamount(a)
                .compose(RxHelper.handleResult())
                .subscribe(new RxSubscriber<List<WholeInfoRes>>(mActivity) {
                    @Override
                    protected void _onNext(List<WholeInfoRes> list) {

                        loanHomepageAdapter = new LoanHomepageAdapter(getContext(), list);
                        rvLoanList.setLayoutManager(new LinearLayoutManager(getContext()));
                        rvLoanList.setAdapter(loanHomepageAdapter);
                        rvLoanList.setNestedScrollingEnabled(false);
                        loanHomepageAdapter.notifyDataSetChanged();
                        holler();
                    }

                    @Override
                    protected void _onError(String msg) {

                    }
                });
    }

    /**
     * 产品分类
     */
    private void getHomepageall(){
        Api.getDefault().getHomepage()
                .compose(RxHelper.handleResult())
                .subscribe(new RxSubscriber<List<GetHomePageRes>>(mActivity) {
                    @Override
                    protected void _onNext(List<GetHomePageRes> list) {
                        for (int i = 0;i<list.size();i++){
                            tv_rapidly.setText(list.get(0).getName());
                            tv_credit.setText(list.get(1).getName());
                            tv_Mouth.setText(list.get(2).getName());
                            tv_large_amount.setText(list.get(3).getName());
                        }
                    }
                    @Override
                    protected void _onError(String msg) {

                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        token = Global.getToken();
    }
}
