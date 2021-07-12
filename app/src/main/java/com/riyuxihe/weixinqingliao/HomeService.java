/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.Intent
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.util.Log
 */
package com.riyuxihe.weixinqingliao;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.riyuxihe.weixinqingliao.model.Token;
import com.riyuxihe.weixinqingliao.util.WxHome;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class HomeService
extends Service {
    private static final String TAG = "HomeService";
    private CallBack callBack = null;
    private String deviceId = "";
    private String syncKey = "";
    private Timer timer;
    private Token token;

    public CallBack getCallBack() {
        return this.callBack;
    }

    public IBinder onBind(Intent intent) {
        this.deviceId = intent.getStringExtra("deviceId");
        this.token = new Token();
        Bundle bundle = intent.getBundleExtra("token");
        this.token.fromBundle(bundle);
        this.syncKey = intent.getStringExtra("syncKey");
        Log.i((String)TAG, (String)("onBind:token=" + JSON.toJSONString(this.token) + " deviceId=" + this.deviceId + " syncKey=" + this.syncKey));
        return new HomeBinder();
    }

    public void onCreate() {
        super.onCreate();
    }

    public void onDestroy() {
        this.stopTimer();
        super.onDestroy();
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public void setSyncKey(String string2) {
        this.syncKey = string2;
    }

    public void startTimer() {
        this.timer = new Timer();
        this.timer.schedule((TimerTask)new HomeTimerTask(), 1L, 60000L);
    }

    public void stopTimer() {
        this.timer.cancel();
        this.timer.purge();
    }

    public static interface CallBack {
        public void handleServiceData(Properties var1);
    }

    public class HomeBinder
    extends Binder {
        private HomeService service;

        public HomeBinder() {
            this.service = HomeService.this;
        }

        public HomeService getService() {
            return this.service;
        }

        public void startTimer() {
            this.service.startTimer();
        }

        public void stopTimer() {
            this.service.stopTimer();
        }

        public void updateSyncKey(String string2) {
            this.service.setSyncKey(string2);
        }
    }

    class HomeTimerTask
    extends TimerTask {
        HomeTimerTask() {
        }

        @Override
        public void run() {
            Properties properties = WxHome.syncCheck(HomeService.this.token, HomeService.this.deviceId, HomeService.this.syncKey);
            if (HomeService.this.callBack != null) {
                HomeService.this.callBack.handleServiceData(properties);
            }
        }
    }
}

