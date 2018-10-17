package com.jbx.hjyt.util.network;

import com.jbx.hjyt.model.entity.res.GetHomePageRes;
import com.jbx.hjyt.model.entity.res.GetHomebannerRes;
import com.jbx.hjyt.model.entity.res.GetUserRes;
import com.jbx.hjyt.model.entity.res.Param;
import com.jbx.hjyt.model.entity.res.TypeofloanRes;
import com.jbx.hjyt.model.entity.res.UserinfoRes;
import com.jbx.hjyt.model.entity.res.WholeInfoRes;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;


public interface ApiService {

    /**
     * 获取banner
     */
    @GET(UrlUtil.BANNER)
    Observable<BaseModel<List<GetHomebannerRes>>> getHomeInfo(

    );
    /**
     * 首页产品分类
     */
    @GET(UrlUtil.HOME_PAGE)
    Observable<BaseModel<List<GetHomePageRes>>> getHomepage(

    );
    /**
     * 获取全部产品
     */
    @GET(UrlUtil.ALL_PRODUCTTS)
    Observable<BaseModel<List<WholeInfoRes>>> getallhome(

    );

    /**
     * 获取全部产品
     */
    @GET(UrlUtil.ALL_PRODUCTTS)
    Observable<BaseModel<List<WholeInfoRes>>> getlargeamount(
            @Query("article_id") Integer article_id
    );

    /**
     * 获取短信验证码
     */
    @FormUrlEncoded
    @POST(UrlUtil.GET_CODE)
    Observable<BaseModel<Param>> getiphonecode(
            @Field("mobile") String mobile,
            @Field("sign") String sign
    );

    /**
     * 用户登录
     */
    @FormUrlEncoded
    @POST(UrlUtil.LOGIN_USER)
    Observable<BaseModel<UserinfoRes>> reloadlogin(
            @Field("mobile") String mobile,
            @Field("sign") String sign,
            @Field("verifyCode") String verifyCode
    );

    /**
     * 退出登录
     */
    @GET(UrlUtil.EXIT_LOGON)
    Observable<BaseModel<List<String>>> exitlogon(
            @Query("token") String token
    );

    /**
     * 用户中心获取用户个人信息
     */
    @FormUrlEncoded
    @POST(UrlUtil.GET_USER)
    Observable<BaseModel<GetUserRes>> getuser(
            @Field("token") String token,
            @Field("uid") String uid
    );

    /**
     * 分类页贷款类型
     */
    @FormUrlEncoded
    @POST(UrlUtil.TYPE_OF_LOAN)
    Observable<BaseModel<List<TypeofloanRes>>> typeofloan(
            @Field("token") String token,
            @Field("uid") String uid
    );

    /**
     *  分类页贷款金额产品/贷款类型产品
     */
    @FormUrlEncoded
    @POST(UrlUtil.CLASSIFICATION_PAGE)
    Observable<BaseModel<List<WholeInfoRes>>> classificationpage(
            @Field("uid") String uid,
            @Field("token") String token,
            @Field("d_id") String d_id,
            @Field("a_id") String a_id
    );
}
