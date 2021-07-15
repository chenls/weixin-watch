package com.riyuxihe.weixinqingliao.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.riyuxihe.weixinqingliao.model.Token;

public class Prefs {
    private static final long EXPIRE = 10800000;
    private static final String PREFERENCE_FILE_KEY = "com.riyuxihe.weixinqingliao.PREFERENCE_FILE_KEY";
    private static final String TAG = "Prefs";
    private static Prefs sInstance;
    private final Context mCtx;
    SharedPreferences.OnSharedPreferenceChangeListener mListener = null;

    private Prefs(Context ctx) {
        this.mCtx = ctx.getApplicationContext();
    }

    public static Prefs getInstance(Context ctx) {
        if (sInstance == null) {
            synchronized (Prefs.class) {
                if (sInstance == null) {
                    sInstance = new Prefs(ctx);
                }
            }
        }
        return sInstance;
    }

    private static SharedPreferences getPrefs(Context ctx) {
        return ctx.getSharedPreferences(PREFERENCE_FILE_KEY, 0);
    }

    public Token getToken() {
        String encodedToken = getPrefs(this.mCtx).getString(Key.TOKEN, "");
        Token token = new Token();
        if (!TextUtils.isEmpty(encodedToken)) {
            return JSON.parseObject(encodedToken, Token.class);
        }
        return token;
    }

    public void setToken(Token token) {
        SharedPreferences.Editor editor = getPrefs(this.mCtx).edit();
        editor.putString(Key.TOKEN, JSON.toJSONString(token));
        editor.putLong(Key.EXPIRE_AT, System.currentTimeMillis() + EXPIRE);
        editor.apply();
    }

    public void clear() {
        SharedPreferences.Editor editor = getPrefs(this.mCtx).edit();
        editor.clear();
        editor.apply();
    }

    public Long getExpireAt() {
        return Long.valueOf(getPrefs(this.mCtx).getLong(Key.EXPIRE_AT, -1));
    }

    public String getAvatar() {
        return getPrefs(this.mCtx).getString(Key.AVATAR, "");
    }

    public void setAvatar(String avatar) {
        SharedPreferences.Editor editor = getPrefs(this.mCtx).edit();
        editor.putString(Key.AVATAR, avatar);
        editor.apply();
    }

    public interface Key {
        String AVATAR = "avatar";
        String EXPIRE_AT = "expire";
        String TOKEN = "token";
    }
}
