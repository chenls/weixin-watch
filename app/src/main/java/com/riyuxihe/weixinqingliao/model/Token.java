/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.riyuxihe.weixinqingliao.model;

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
    private String isGrayScale;
    public String message;
    private String passTicket;
    public int ret;
    private String skey;
    private String wxsid;
    private String wxuin;

    public void fromBundle(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        this.skey = bundle.getString(SKEY);
        this.wxsid = bundle.getString(WXSID);
        this.wxuin = bundle.getString(WXUIN);
        this.passTicket = bundle.getString(PASS_TICKET);
        this.isGrayScale = bundle.getString(IS_GRAY_SCALE);
        this.cookie = bundle.getString(COOKIE);
    }

    public String getIsGrayScale() {
        return this.isGrayScale;
    }

    public String getPassTicket() {
        return this.passTicket;
    }

    public String getSkey() {
        return this.skey;
    }

    public String getWxsid() {
        return this.wxsid;
    }

    public String getWxuin() {
        return this.wxuin;
    }

    public void setIsGrayScale(String string2) {
        this.isGrayScale = string2;
    }

    public void setPassTicket(String string2) {
        this.passTicket = string2;
    }

    public void setSkey(String string2) {
        this.skey = string2;
    }

    public void setWxsid(String string2) {
        this.wxsid = string2;
    }

    public void setWxuin(String string2) {
        this.wxuin = string2;
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
}

