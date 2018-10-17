package com.jbx.hjyt.util.network;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 郑谊庄 on 2017/4/17.
 */

public class RxHelper {

    /**
     * 对结果进行预处理
     * @param <T>
     * @return
     */
    public static <T>Observable.Transformer<BaseModel<T>, T> handleResult() {
        return baseModel ->
                baseModel.flatMap(tBaseModel -> {
                    if (tBaseModel.isSucceed()) {
                        return createData(tBaseModel.params);
                    }
                    return Observable.error(new ServerException(tBaseModel.message));
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 创建成功的数据
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(data);
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }
}
