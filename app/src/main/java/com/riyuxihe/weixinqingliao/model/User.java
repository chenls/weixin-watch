/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.riyuxihe.weixinqingliao.model;

import android.os.Bundle;

public class User {
    private static final String CONTACTFLAG = "ContactFlag";
    private static final String HEADIMGURL = "HeadImgUrl";
    private static final String NICKNAME = "NickName";
    private static final String USERNAME = "UserName";
    public int ContactFlag;
    public String HeadImgUrl;
    public String NickName;
    public String UserName;

    public void fromBundle(Bundle bundle) {
        this.UserName = bundle.getString(USERNAME);
        this.NickName = bundle.getString(NICKNAME);
        this.HeadImgUrl = bundle.getString(HEADIMGURL);
        this.ContactFlag = bundle.getInt(CONTACTFLAG);
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(USERNAME, this.UserName);
        bundle.putString(NICKNAME, this.NickName);
        bundle.putString(HEADIMGURL, this.HeadImgUrl);
        bundle.putInt(CONTACTFLAG, this.ContactFlag);
        return bundle;
    }
}

