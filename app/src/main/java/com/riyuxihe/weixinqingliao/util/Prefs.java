/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.SharedPreferences$OnSharedPreferenceChangeListener
 *  android.text.TextUtils
 */
package com.riyuxihe.weixinqingliao.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.riyuxihe.weixinqingliao.model.Token;

public class Prefs {
    private static final long EXPIRE = 10800000L;
    private static final String PREFERENCE_FILE_KEY = "com.riyuxihe.weixinqingliao.PREFERENCE_FILE_KEY";
    private static final String TAG = "Prefs";
    private static Prefs sInstance;
    private final Context mCtx;
    SharedPreferences.OnSharedPreferenceChangeListener mListener = null;

    private Prefs(Context context) {
        this.mCtx = context.getApplicationContext();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Prefs getInstance(Context context) {
        if (sInstance == null) {
            synchronized (Prefs.class) {
                if (sInstance == null) {
                    sInstance = new Prefs(context);
                }
            }
        }
        return sInstance;
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREFERENCE_FILE_KEY, 0);
    }

    public void clear() {
        SharedPreferences.Editor editor = Prefs.getPrefs(this.mCtx).edit();
        editor.clear();
        editor.apply();
    }

    public String getAvatar() {
        return Prefs.getPrefs(this.mCtx).getString("avatar", "");
    }

    public Long getExpireAt() {
        return Prefs.getPrefs(this.mCtx).getLong("expire", -1L);
    }

    public Token getToken() {
        String string2 = Prefs.getPrefs(this.mCtx).getString("token", "");
        Token token = new Token();
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            token = JSON.parseObject(string2, Token.class);
        }
        return token;
    }

    public void setAvatar(String string2) {
        SharedPreferences.Editor editor = Prefs.getPrefs(this.mCtx).edit();
        editor.putString("avatar", string2);
        editor.apply();
    }

    public void setToken(Token token) {
        SharedPreferences.Editor editor = Prefs.getPrefs(this.mCtx).edit();
        editor.putString("token", JSON.toJSONString(token));
        editor.putLong("expire", System.currentTimeMillis() + 10800000L);
        editor.apply();
    }

    static interface Key {
        public static final String AVATAR = "avatar";
        public static final String EXPIRE_AT = "expire";
        public static final String TOKEN = "token";
    }
}

