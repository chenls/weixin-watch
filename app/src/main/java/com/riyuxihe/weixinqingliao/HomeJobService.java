package com.riyuxihe.weixinqingliao;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.riyuxihe.weixinqingliao.model.Token;
import com.riyuxihe.weixinqingliao.util.Constants;
import com.riyuxihe.weixinqingliao.util.Prefs;
import com.riyuxihe.weixinqingliao.util.WxHome;

public class HomeJobService extends JobService {
    private static final String TAG = "HomeJobService";
    private static final int notificationId = 10086;
    /* access modifiers changed from: private */
    public String deviceId = "";
    HomeActivity mHomeActivity;
    private NotificationStatus notificationStatus = NotificationStatus.normal;
    /* access modifiers changed from: private */
    public String syncKey = "";
    /* access modifiers changed from: private */
    public Token token;
    private int unSyncTimes = 0;

    public enum NotificationStatus {
        normal,
        mobileData,
        badNetwork,
        permissionRational,
        permissionDenied
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        this.deviceId = intent.getStringExtra("deviceId");
        this.token = new Token();
        this.token.fromBundle(intent.getBundleExtra(Prefs.Key.TOKEN));
        this.syncKey = intent.getStringExtra("syncKey");
        startForeground();
        Messenger callback = (Messenger) intent.getParcelableExtra("messenger");
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
        startForeground(notificationId, generateNotification(getString(R.string.notification_normal)));
    }

    public void updateNotification(String content, NotificationStatus notificationStatus2) {
        if (notificationStatus2 != this.notificationStatus) {
            ((NotificationManager) getSystemService("notification")).notify(notificationId, generateNotification(content));
            this.notificationStatus = notificationStatus2;
        }
    }

    private Notification generateNotification(String content) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, HomeActivity.class), 0);
        Intent closeIntent = new Intent();
        closeIntent.setAction(Constants.Action.CLOSE_APP);
        return new NotificationCompat.Builder(this).
                setSmallIcon(R.mipmap.chat).setContentTitle(getString(R.string.app_name)).
                setContentText(content).addAction(R.drawable.ic_open_star, "打开应用",
                pendingIntent).addAction(R.drawable.ic_setting_normal, "设置",
                PendingIntent.getActivity(this, 0,
                        new Intent(this, SettingActivity.class), 0)).
                addAction(R.drawable.ic_full_cancel, "退出登录",
                        PendingIntent.getBroadcast(this,
                                0, closeIntent, 0)).build();
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob: ");
        new SyncCheckTask(this).execute(new JobParameters[]{params});
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob: ");
        return false;
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

    public void setUiCallback(HomeActivity activity) {
        this.mHomeActivity = activity;
    }

    public void setSyncKey(String syncKey2) {
        this.syncKey = syncKey2;
    }

    public String getSyncKey() {
        return this.syncKey;
    }

    public boolean unSync() {
        Log.d(TAG, "unSync:times=" + this.unSyncTimes);
        int i = this.unSyncTimes + 1;
        this.unSyncTimes = i;
        if (i >= 2) {
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }
}
