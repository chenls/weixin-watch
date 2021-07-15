package com.riyuxihe.weixinqingliao;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.riyuxihe.weixinqingliao.model.Token;
import com.riyuxihe.weixinqingliao.util.Prefs;
import com.riyuxihe.weixinqingliao.util.WxHome;

public class HomeJobService extends JobService {
    private static final String TAG = "HomeJobService";
    private static final int notificationId = 10086;
    /* access modifiers changed from: private */
    public String deviceId = "";
    /* access modifiers changed from: private */
    public String syncKey = "";
    /* access modifiers changed from: private */
    public Token token;
    HomeActivity mHomeActivity;
    private NotificationStatus notificationStatus = NotificationStatus.normal;
    private int unSyncTimes = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        this.deviceId = intent.getStringExtra("deviceId");
        this.token = new Token();
        this.token.fromBundle(intent.getBundleExtra(Prefs.Key.TOKEN));
        this.syncKey = intent.getStringExtra("syncKey");
        startForeground();
        Messenger callback = intent.getParcelableExtra("messenger");
        Message m = Message.obtain();
        m.what = 20;
        m.obj = this;
        try {
            callback.send(m);
            return 2;
        } catch (RemoteException e) {
            Log.e(TAG, "onStartCommand:Error passing service object back to activity.");
            return 2;
        }
    }

    private void startForeground() {
        Log.d(TAG, "startForeground: ");
//        startForeground(notificationId, generateNotification(getString(R.string.notification_normal)));
    }

    public void updateNotification(String content, NotificationStatus notificationStatus2) {
        if (notificationStatus2 != this.notificationStatus) {
            ((NotificationManager) getSystemService("notification")).notify(notificationId, generateNotification(content));
            this.notificationStatus = notificationStatus2;
        }
    }

    private Notification generateNotification(String content) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.chat)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(content);
        return mBuilder.build();
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob: ");
        new SyncCheckTask(this).execute(params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob: ");
        return false;
    }

    public void setUiCallback(HomeActivity activity) {
        this.mHomeActivity = activity;
    }

    public String getSyncKey() {
        return this.syncKey;
    }

    public void setSyncKey(String syncKey2) {
        this.syncKey = syncKey2;
    }

    public boolean unSync() {
        Log.d(TAG, "unSync:times=" + this.unSyncTimes);
        int i = this.unSyncTimes + 1;
        this.unSyncTimes = i;
        return i >= 2;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }

    public enum NotificationStatus {
        normal,
        mobileData,
        badNetwork,
        permissionRational,
        permissionDenied
    }

    @SuppressLint("StaticFieldLeak")
    private class SyncCheckTask extends AsyncTask<JobParameters, Void, JobParameters> {
        private final HomeJobService homeJobService;

        public SyncCheckTask(HomeJobService homeJobService2) {
            this.homeJobService = homeJobService2;
        }

        /* access modifiers changed from: protected */
        public JobParameters doInBackground(JobParameters... params) {
            Log.d(TAG, "doInBackground: ");
            if (!(HomeJobService.this.token == null || HomeJobService.this.mHomeActivity == null
                    || !HomeJobService.this.mHomeActivity.checkMobileDataConnection(HomeJobService.this.mHomeActivity))) {
                HomeJobService.this.mHomeActivity.onSyncChecked(WxHome.syncCheck(HomeJobService.this.token,
                        HomeJobService.this.deviceId, HomeJobService.this.syncKey));
            }
            return params[0];
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(JobParameters jobParameters) {
            if (jobParameters != null)
                this.homeJobService.jobFinished(jobParameters, false);
        }
    }
}
