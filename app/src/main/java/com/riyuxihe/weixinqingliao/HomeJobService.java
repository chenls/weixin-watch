/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.app.job.JobParameters
 *  android.app.job.JobService
 *  android.content.Context
 *  android.content.Intent
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.os.Message
 *  android.os.Messenger
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.riyuxihe.weixinqingliao;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import com.riyuxihe.weixinqingliao.HomeActivity;
import com.riyuxihe.weixinqingliao.SettingActivity;
import com.riyuxihe.weixinqingliao.model.Token;
import com.riyuxihe.weixinqingliao.util.WxHome;
import java.util.Properties;

public class HomeJobService
extends JobService {
    private static final String TAG = "HomeJobService";
    private static final int notificationId = 10086;
    private String deviceId = "";
    HomeActivity mHomeActivity;
    private NotificationStatus notificationStatus = NotificationStatus.normal;
    private String syncKey = "";
    private Token token;
    private int unSyncTimes = 0;

    private Notification generateNotification(String string2) {
        PendingIntent pendingIntent = PendingIntent.getActivity((Context)this, (int)0, (Intent)new Intent((Context)this, HomeActivity.class), (int)0);
        Intent intent = new Intent();
        intent.setAction("com.riyuxihe.weixinqingliao.CLOSEAPP");
        intent = PendingIntent.getBroadcast((Context)this, (int)0, (Intent)intent, (int)0);
        PendingIntent pendingIntent2 = PendingIntent.getActivity((Context)this, (int)0, (Intent)new Intent((Context)this, SettingActivity.class), (int)0);
        return new NotificationCompat.Builder((Context)this).setSmallIcon(2130903040).setContentTitle(this.getString(2131230783)).setContentText(string2).addAction(2130837665, "\u6253\u5f00\u5e94\u7528", pendingIntent).addAction(2130837666, "\u8bbe\u7f6e", pendingIntent2).addAction(2130837662, "\u9000\u51fa\u767b\u5f55", (PendingIntent)intent).build();
    }

    private void startForeground() {
        this.startForeground(10086, this.generateNotification(this.getString(2131230806)));
    }

    public String getSyncKey() {
        return this.syncKey;
    }

    public void onDestroy() {
        super.onDestroy();
        this.stopForeground(true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int onStartCommand(Intent intent, int n2, int n3) {
        this.deviceId = intent.getStringExtra("deviceId");
        this.token = new Token();
        Bundle bundle = intent.getBundleExtra("token");
        this.token.fromBundle(bundle);
        this.syncKey = intent.getStringExtra("syncKey");
        this.startForeground();
        intent = (Messenger)intent.getParcelableExtra("messenger");
        bundle = Message.obtain();
        bundle.what = 20;
        bundle.obj = this;
        try {
            intent.send((Message)bundle);
            return 2;
        }
        catch (RemoteException remoteException) {
            Log.e((String)TAG, (String)"onStartCommand:Error passing service object back to activity.");
            return 2;
        }
    }

    public boolean onStartJob(JobParameters jobParameters) {
        new SyncCheckTask(this).execute(new JobParameters[]{jobParameters});
        return true;
    }

    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    public void setSyncKey(String string2) {
        this.syncKey = string2;
    }

    public void setUiCallback(HomeActivity homeActivity) {
        this.mHomeActivity = homeActivity;
    }

    public boolean unSync() {
        int n2;
        Log.d((String)TAG, (String)("unSync:times=" + this.unSyncTimes));
        this.unSyncTimes = n2 = this.unSyncTimes + 1;
        return n2 >= 2;
    }

    public void updateNotification(String string2, NotificationStatus notificationStatus) {
        if (notificationStatus == this.notificationStatus) {
            return;
        }
        string2 = this.generateNotification(string2);
        ((NotificationManager)this.getSystemService("notification")).notify(10086, (Notification)string2);
        this.notificationStatus = notificationStatus;
    }

    public static enum NotificationStatus {
        normal,
        mobileData,
        badNetwork,
        permissionRational,
        permissionDenied;

    }

    private class SyncCheckTask
    extends AsyncTask<JobParameters, Void, JobParameters> {
        private final HomeJobService homeJobService;

        public SyncCheckTask(HomeJobService homeJobService2) {
            this.homeJobService = homeJobService2;
        }

        protected JobParameters doInBackground(JobParameters ... jobParametersArray) {
            if (HomeJobService.this.token != null && HomeJobService.this.mHomeActivity != null && HomeJobService.this.mHomeActivity.checkMobileDataConnection((Context)HomeJobService.this.mHomeActivity)) {
                Properties properties = WxHome.syncCheck(HomeJobService.this.token, HomeJobService.this.deviceId, HomeJobService.this.syncKey);
                HomeJobService.this.mHomeActivity.onSyncChecked(properties);
            }
            return jobParametersArray[0];
        }

        protected void onPostExecute(JobParameters jobParameters) {
            this.homeJobService.jobFinished(jobParameters, false);
        }
    }
}

