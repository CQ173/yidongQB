package com.jbx.hjyt.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.StringUtils;
import com.jbx.hjyt.R;
import com.jbx.hjyt.app.constant.Global;
import com.jbx.hjyt.model.entity.res.TypeofloanRes;
import com.jbx.hjyt.model.entity.res.WholeInfoRes;
import com.jbx.hjyt.util.StartActivityUtil;
import com.jbx.hjyt.util.network.Api;
import com.jbx.hjyt.util.network.RxHelper;
import com.jbx.hjyt.util.network.RxSubscriber;
import com.jbx.hjyt.view.activity.LoginActivity;
import com.jbx.hjyt.view.activity.WebviewActivity;
import com.jbx.hjyt.view.adapter.HomeFragAdapter;
import com.jbx.hjyt.view.adapter.SpinerAAdapter;
import com.jbx.hjyt.view.adapter.SpinerAdapter;
import com.jbx.hjyt.view.fragment.base.BaseFragment;
import com.jbx.hjyt.view.widget.SpinerPopAWindow;
import com.jbx.hjyt.view.widget.SpinerPopWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment implements View.OnClickListener, SpinerAdapter.IOnItemSelectListener ,SpinerAAdapter.IOnItemSelectListener{

    private List<String> mListmoney = new ArrayList<String>();
    private List<String> mListType = new ArrayList<String>();
    //类型列表
    int i;
    @BindView(R.id.tv_value)
    TextView mTView;
    @BindView(R.id.tv_value_a)
    TextView mTView_a;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.layout_a)
    LinearLayout layout_a;
    @BindView(R.id.rv_loan_list)
    RecyclerView rvLoanList;
    private SpinerAdapter mAdapter;
    private SpinerAAdapter mAAdapter;
    private SpinerPopWindow mSpinerPopWindow;
    private SpinerPopAWindow mSpinerPopAWindow;

    private String token;
    private String id;

    private  List<TypeofloanRes> data = new ArrayList<>();
    private static final ThreadLocal re = new ThreadLocal(); //自行补上泛型Object

    private HomeFragAdapter homeFragAdapter;

    @Override
    protected int setContentLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        token = Global.getToken();
        id = Global.getId();

        //初始化数据
        mListmoney.add("全部金额");
        mListmoney.add("1000-5000元");
        mListmoney.add("2000-10000元");
        mListmoney.add("5000-20000元");
        mListmoney.add("10000-50000元");
        mAdapter = new SpinerAdapter(getContext(), mListmoney);
        mAdapter.refreshData(mListmoney, 0);

        //初始化PopWindow
        mSpinerPopWindow = new SpinerPopWindow(getContext());
        mSpinerPopWindow.setAdatper(mAdapter);
        mSpinerPopWindow.setItemListener(this);

        mAAdapter = new SpinerAAdapter(getContext(), mListType);
        mAAdapter.refreshData(mListType, 0);

        mSpinerPopAWindow = new SpinerPopAWindow(getContext());
        mSpinerPopAWindow.setAdatper(mAAdapter);
        mSpinerPopAWindow.setItemListener(this);
        if (StringUtils.isEmpty(token)){

        }else {
            Typeofloan();
        }
        if (token != null)
            itemjiage(null , null );
    }

    //设置PopWindow
    private void showSpinWindow() {
        //设置mSpinerPopWindow显示的宽度
        mSpinerPopWindow.setWidth(layout.getWidth()+layout_a.getWidth());
        //设置显示的位置在哪个控件的下方
        mSpinerPopWindow.showAsDropDown(layout );
    }

    //设置PopWindow
    private void showSpinAWindow() {
        //设置mSpinerPopWindow显示的宽度
        mSpinerPopAWindow.setWidth(layout.getWidth()+layout_a.getWidth());
        //设置显示的位置在哪个控件的下方
        mSpinerPopAWindow.showAsDropDown(layout_a );
    }
    @OnClick({ R.id.layout , R.id.layout_a })
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout:
                showSpinWindow();//显示SpinerPopWindow
                break;
            case R.id.layout_a:
                showSpinAWindow();//显示SpinerPopAWindow
                break;
        }
    }

    /**
     * 获取贷款类型列表
     */
    public void Typeofloan(){
        Api.getDefault().typeofloan( token ,  id )
                .compose(RxHelper.handleResult())
                .subscribe(new RxSubscriber<List<TypeofloanRes>>(mActivity) {
                    @Override
                    protected void _onNext(List<TypeofloanRes> list) {
                        data = list;
                        re.set(list);
                        for (int i1 = 0; i1 < data.size(); i1++) {
                            mListType.add(data.get(i1).getName());
                        }
                    }
                    @Override
                    protected void _onError(String msg) {

                    }
                });
        obtainData();
    }

    public void holler(){
        rvLoanList.addItemDecoration(new DividerItemDecoration(getContext() , DividerItemDecoration.VERTICAL));
        homeFragAdapter.setClickListener(new HomeFragAdapter.OnItemClickListener() {
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

    /**
     * 获取全部产品
     */
    private void getHomeaa(){
        Api.getDefault().getallhome()
                .compose(RxHelper.handleResult())
                .subscribe(new RxSubscriber<List<WholeInfoRes>>(mActivity) {
                    @Override
                    protected void _onNext(List<WholeInfoRes> list) {
                        homeFragAdapter = new HomeFragAdapter(getContext(), list);
                        rvLoanList.setLayoutManager(new LinearLayoutManager(getContext()));
                        rvLoanList.setAdapter(homeFragAdapter);
                        rvLoanList.setNestedScrollingEnabled(false);
                        homeFragAdapter.notifyDataSetChanged();
                        holler();
                    }

                    @Override
                    protected void _onError(String msg) {

                    }
                });
    }

    @Override
    protected void obtainData() {

    }

    /**
     * 按条件查询
     * @param d_id
     * @param a_id
     */
    public void itemjiage( String d_id , String a_id){
        Api.getDefault().classificationpage(id , token ,d_id , a_id )
                .compose(RxHelper.handleResult())
                .subscribe(new RxSubscriber<List<WholeInfoRes>>(mActivity) {
                    @Override
                    protected void _onNext(List<WholeInfoRes> list) {
                        homeFragAdapter = new HomeFragAdapter(getContext(), list);
                        rvLoanList.setLayoutManager(new LinearLayoutManager(getContext()));
                        rvLoanList.setAdapter(homeFragAdapter);
                        rvLoanList.setNestedScrollingEnabled(false);
                        homeFragAdapter.notifyDataSetChanged();
                        holler();
                    }
                    @Override
                    protected void _onError(String msg) {

                    }
                });
    }

    @Override
    protected void initEvent() {

    }
    /**     * SpinerPopWindow中的条目点击监听     * @param pos     */
    @Override
    public void onItemClick(int pos) {
        String value = mListmoney.get(pos);
        if (token != null){
            if (0 == pos) {
                getHomeaa();
            } else if (1 == pos) {
                itemjiage("1", null);
            } else if (2 == pos) {
                itemjiage("2", null);
            } else if (3 == pos) {
                itemjiage("3", null);
            } else if (4 == pos) {
                itemjiage("4", null);
            }
        }
    }
    @Override
    public void onItemAClick(int pos) {
        String value = mListType.get(pos);
        if (token != null) {
            if (0 == pos) {
                getHomeaa();
            }else if (1 == pos) {
                itemjiage(null, "1");
            } else if (2 == pos) {
                itemjiage(null, "2");
            } else if (3 == pos) {
                itemjiage(null, "3");
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
