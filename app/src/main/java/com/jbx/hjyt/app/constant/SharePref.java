package com.jbx.hjyt.app.constant;

import android.content.Context;

import com.blankj.utilcode.utils.SPUtils;

/**
 *
 * @author 付杰
 * @date 2017/5/23
 * 公共存储类
 */

public class SharePref {

    private static SPUtils spUtils;

    public static SPUtils getInstance(){
        return spUtils;
    }

    public static void init(Context context) {
        if (spUtils == null) {
            spUtils = new SPUtils(context, "CoderWorld");
        }
    }
    /**
     * Token
     */
    public static final String TOKEN = "Token";

    /**
     * ID
     */
    public static final String ID = "id";
    /**
     * username
     */
    public static final String USERNAME = "username";

}
