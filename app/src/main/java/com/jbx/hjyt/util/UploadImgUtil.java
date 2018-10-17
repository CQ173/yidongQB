package com.jbx.hjyt.util;

import android.app.Activity;
import android.content.Context;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.utils.BinaryUtil;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.blankj.utilcode.utils.ToastUtils;
import com.jbx.hjyt.app.MyApplication;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;


import java.io.File;
import java.io.IOException;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


/**
 * Created by 郑谊庄 on 2017/6/29.
 */

public abstract class UploadImgUtil {

    private static String BUCKET_NAME = "midai88-bucket-test";

    private LoadingDialog dialog;
    private Context mContext;

    public UploadImgUtil(Context mContext, String key, String path) {
        this.mContext = mContext;
        uploadImg(key, path);
    }

    public void uploadImg(String key, String path) {
        Luban.with(mContext)
                .load(new File(path))
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        initDialog("正在上传...");
                    }

                    @Override
                    public void onSuccess(File file) {
                        doUploadPic(key, file.getPath());
                    }

                    @Override
                    public void onError(Throwable e) {
                        hintDialog();
                        ToastUtils.showShortToast(mContext, "上传失败");
                    }
                }).launch();
    }

    /**
     * 上传文件至oss
     * @param key
     * @param path
     */
    private void doUploadPic(String key, String path) {
        PutObjectRequest put = new PutObjectRequest(BUCKET_NAME, key, path);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("application/octet-stream");
        try {
            // 设置Md5以便校验
            metadata.setContentMD5(BinaryUtil.calculateBase64Md5("<uploadFilePath>")); // 如果是从文件上传
            // metadata.setContentMD5(BinaryUtil.calculateBase64Md5(byte[])); // 如果是上传二进制数据
        } catch (IOException e) {
            e.printStackTrace();
        }
        put.setMetadata(metadata);
        OSSAsyncTask task = MyApplication.getInstance().getOss()
                .asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                    @Override
                    public void onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult) {
                        ((Activity) mContext).runOnUiThread(() -> {
                            hintDialog();
                            ToastUtils.showShortToast(mContext, "上传成功");
                            _onSuccess(putObjectRequest, putObjectResult);
                        });
                    }

                    @Override
                    public void onFailure(PutObjectRequest putObjectRequest, ClientException e, ServiceException e1) {
                        ((Activity) mContext).runOnUiThread(() -> hintDialog());
                        _onFailure(putObjectRequest, e, e1);
                    }
                });
    }

    public void initDialog(String msg){
        dialog = new LoadingDialog(mContext);
        dialog.setLoadingText(msg)
                .setInterceptBack(false);
        dialog.show();
    }

    public void hintDialog(){
        if (null != dialog) {
            dialog.close();
        }
    }

    public abstract void _onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult);

    public abstract void _onFailure(PutObjectRequest putObjectRequest, ClientException e, ServiceException e1);

}
