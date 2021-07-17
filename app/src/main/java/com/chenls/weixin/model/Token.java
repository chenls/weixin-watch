package com.chenls.weixin.model;

import android.os.Bundle;

public class Token {
    public static final String COOKIE = "Cookie";
    public static final String IS_GRAY_SCALE = "isgrayscale";
    public static final String MESSAGE = "message";
    public static final String PASS_TICKET = "pass_ticket";
    public static final String RET = "ret";
    public static final String SKEY = "skey";
    public static final String WXSID = "wxsid";
    public static final String WXUIN = "wxuin";
    public String cookie;
    public String message;
    public int ret;
    private String isGrayScale;
    private String passTicket;
    private String skey;
    private String wxsid;
    private String wxuin;

    public String getSkey() {
        return this.skey;
    }

    public void setSkey(String skey2) {
        this.skey = skey2;
    }

    public String getWxsid() {
        return this.wxsid;
    }

    public void setWxsid(String wxsid2) {
        this.wxsid = wxsid2;
    }

    public String getWxuin() {
        return this.wxuin;
    }

    public void setWxuin(String wxuin2) {
        this.wxuin = wxuin2;
    }

    public String getPassTicket() {
        return this.passTicket;
    }

    public void setPassTicket(String passTicket2) {
        this.passTicket = passTicket2;
    }

    public String getIsGrayScale() {
        return this.isGrayScale;
    }

    public void setIsGrayScale(String isGrayScale2) {
        this.isGrayScale = isGrayScale2;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(SKEY, this.skey);
        bundle.putString(WXSID, this.wxsid);
        bundle.putString(WXUIN, this.wxuin);
        bundle.putString(PASS_TICKET, this.passTicket);
        bundle.putString(IS_GRAY_SCALE, this.isGrayScale);
        bundle.putString(COOKIE, this.cookie);
        return bundle;
    }

    public void fromBundle(Bundle bundle) {
        if (bundle != null) {
            this.skey = bundle.getString(SKEY);
            this.wxsid = bundle.getString(WXSID);
            this.wxuin = bundle.getString(WXUIN);
            this.passTicket = bundle.getString(PASS_TICKET);
            this.isGrayScale = bundle.getString(IS_GRAY_SCALE);
            this.cookie = bundle.getString(COOKIE);
        }
    }
}
