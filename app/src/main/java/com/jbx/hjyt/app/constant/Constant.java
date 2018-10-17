package com.jbx.hjyt.app.constant;

/**
 * Created by 郑谊庄
 * on 2018/7/2.
 */
public class Constant {

    /**
     * oss 图片路径
     */
    public static String IMG_URL = "http://midai88-bucket-test.oss-cn-shanghai.aliyuncs.com/";

    /**
     * 短信类型
     */
    // 用户注册
    public static int SMS_TYPE_REGISTER = 1;
    // 修改密码
    public static int SMS_TYPE_PASSWORD_EDIT = 2;
    // 忘记密码
    public static int SMS_TYPE_PASSWORD_FORGET = 3;

    /**
     * 修改密码
     */
    // 修改密码
    public static int MODIFY_PWD_TYPE_MODIFY = 1;
    // 忘记密码
    public static int MODIFY_PWD_TYPE_FORGET = 2;

    /**
     * 账号类型
     */
    // 微信
    public static final int ACCOUNT_TYPE_WEIXIN = 2;
    // 支付宝
    public static final int ACCOUNT_TYPE_ALIPAY = 1;
    // 中国工商银行
    public static final int ACCOUNT_TYPE_ICBC = 3;
    // 中国银行卡
    public static final int ACCOUNT_TYPE_BOC = 4;
    // 中国建设银行
    public static final int ACCOUNT_TYPE_CCB = 5;
    // 中国民生银行
    public static final int ACCOUNT_TYPE_CMBC = 6;

}
