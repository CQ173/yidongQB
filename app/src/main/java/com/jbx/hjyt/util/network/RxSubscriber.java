package com.jbx.hjyt.util.network;

import android.content.Context;

import com.blankj.utilcode.utils.NetworkUtils;
import com.blankj.utilcode.utils.StringUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.jbx.hjyt.R;
import com.jbx.hjyt.view.widget.ProgressDialog;

import rx.Subscriber;

/**
 * Created by 郑谊庄 on 2017/4/17.
 */

public abstract class RxSubscriber<T> extends Subscriber<T> {
    private Context mContext;
    private String msg;
    private ProgressDialog mProgressDialog;
    private boolean isShow = true;
    public RxSubscriber(Context context, String msg) {
        this.mContext = context;
        this.msg = msg;
    }

    public RxSubscriber(Context context) {
        this(context, context.getString(R.string.loading));
    }
    public RxSubscriber(Context context, boolean isShowDialog) {
        this(context, context.getString(R.string.loading));
        isShow = isShowDialog;
    }
    protected boolean showDialog(){
        return isShow;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (showDialog()) showLoadingDialog();
    }

    @Override
    public void onCompleted() {
        if (showDialog()) dismissLoadingDialog();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();

        if (showDialog()) dismissLoadingDialog();

        String err;
        if (e instanceof ServerException) {
            err = e.getMessage();
        } else if (!NetworkUtils.isAvailableByPing(mContext)) {
            err = mContext.getString(R.string.loadFail_net);
        } else {
            err = "请求失败,请稍后再试！";
        }
        _onError(err);
        if (!StringUtils.isEmpty(err))
            ToastUtils.showShortToast(mContext, err);
    }


    /**
     * 显示圆形进度对话框
     */
    public void showLoadingDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
        }
        mProgressDialog.showDialog();
    }

    /**
     * 显示圆形进度对话框（不可关闭）
     */
    public void showNoCancelLoadingDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
        }
        mProgressDialog.showDialog();
        mProgressDialog.setCancelable(false);
    }

    /**
     * 关闭进度对话框
     */
    public void dismissLoadingDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
        }
        mProgressDialog.dismissDialog();
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String msg);
}
