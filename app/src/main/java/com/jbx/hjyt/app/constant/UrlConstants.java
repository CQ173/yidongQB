package com.jbx.hjyt.app.constant;

/**
 * author:  ljy
 * date:    2017/9/25
 * description: 网络地址常量类
 * @author fujie
 */

public interface UrlConstants {

//    String HOST_SITE_HTTPS = "https://api.douban.com/";
//    String HOST_SITE_HTTPS = "http://10.0.2.2:8080/api/";
    String HOST_SITE_HTTPS = "http://tj6j82.natappfree.cc/api/";

    String GET_PLAYING_MOVIE = "v2/movie/in_theaters";
    String GET_COMMING_MOVIE = "v2/movie/coming_soon";
    //获取首页信息
    String GET_HOME_INFO = "get_home_info";
    //获取分类的金融产品列表
    String GET_TYPE_LOAN = "get_type_loan";
    //获取信用卡列表
    String GET_CREDIT_CARD = "get_credit_card";
    //用户登录
    String FIND_LOGIN = "login";
    //上传通讯录列表
    String UPLOAD_MAIL_LIST = "upload_mail_list";

}
