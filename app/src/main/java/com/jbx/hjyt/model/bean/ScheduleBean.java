package com.jbx.hjyt.model.bean;

/**
 * Created by YYJ on 2018/5/30.
 */

public class ScheduleBean {

    private int bankIcon;
    private String bankName;

    public ScheduleBean(int bankIcon, String bankName) {
        this.bankIcon = bankIcon;
        this.bankName = bankName;
    }

    public int getBankIcon() {
        return bankIcon;
    }

    public void setBankIcon(int bankIcon) {
        this.bankIcon = bankIcon;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
