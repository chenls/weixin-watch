/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.graphics.Bitmap
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.Looper
 *  android.os.Message
 *  android.text.TextUtils
 *  android.util.Log
 *  android.util.Pair
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.Button
 *  android.widget.ImageView
 *  android.widget.ProgressBar
 *  android.widget.TextView
 */
package com.riyuxihe.weixinqingliao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.mobvoi.wear.app.PermissionCompat;
import com.riyuxihe.weixinqingliao.HomeActivity;
import com.riyuxihe.weixinqingliao.VerticalViewPager;
import com.riyuxihe.weixinqingliao.model.Token;
import com.riyuxihe.weixinqingliao.util.NetUtil;
import com.riyuxihe.weixinqingliao.util.Prefs;
import com.riyuxihe.weixinqingliao.util.WxLogin;
import com.umeng.analytics.MobclickAgent;
import java.util.Hashtable;
import java.util.Properties;

public class LoginActivity
extends Activity {
    private static final int BG_MSG_CHECK_LOGIN_STATUS = 1;
    private static final int MSG_LOGIN = 4;
    private static final int MSG_QRCODE = 2;
    private static final int MSG_SCANNED = 3;
    private static final int MSG_TIPS = 1;
    private static final int PERMISSIONS_REQUEST_INTERNET = 0;
    private static final String TAG = "LoginActivity";
    private PagerAdapter mAdapter;
    private BgHandler mBgHandler;
    private Button mChangeBtn;
    private FgHandler mFgHandler;
    private ImageView mImageView;
    private TextView mNotice;
    private ProgressBar mProgressBar;
    private VerticalViewPager mViewPager;
    private HandlerThread mhandlerThread;

    private void changeAccount() {
        Prefs.getInstance(this.getApplicationContext()).clear();
        this.showLoading();
        this.mBgHandler.removeCallbacksAndMessages(null);
        new QRCodeTask().execute(new String[0]);
    }

    private void init() {
        this.initUmeng();
        String string2 = Prefs.getInstance(this.getApplicationContext()).getAvatar();
        if (TextUtils.isEmpty((CharSequence)string2)) {
            new QRCodeTask().execute(new String[0]);
            return;
        }
        long l2 = Prefs.getInstance(this.getApplicationContext()).getExpireAt();
        Token token = Prefs.getInstance(this.getApplicationContext()).getToken();
        if (System.currentTimeMillis() < l2 && token != null && !TextUtils.isEmpty((CharSequence)token.getWxuin())) {
            this.setAvatar(WxLogin.getBase64Image(string2));
            new FastLoginTask().execute(new String[]{token.getWxuin(), token.cookie});
            return;
        }
        new QRCodeTask().execute(new String[0]);
    }

    private void initUmeng() {
        MobclickAgent.setScenarioType((Context)this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    private void jumpToMain(Token token) {
        Intent intent = new Intent((Context)this, HomeActivity.class);
        intent.putExtras(token.toBundle());
        this.startActivity(intent);
        this.finish();
    }

    private void requestInternetPermission(Activity activity) {
        block3: {
            block2: {
                if (PermissionCompat.checkSelfPermission((Context)activity, "android.permission.INTERNET") == 0) break block2;
                if (!PermissionCompat.shouldShowRequestPermissionRationale(activity, "android.permission.INTERNET")) break block3;
                this.mProgressBar.setVisibility(8);
                this.mNotice.setText(2131230800);
            }
            return;
        }
        PermissionCompat.requestPermissions(activity, new String[]{"android.permission.INTERNET"}, 0);
    }

    private void setAvatar(Bitmap bitmap) {
        this.mProgressBar.setVisibility(0);
        this.mImageView.setImageBitmap(bitmap);
        this.mChangeBtn.setVisibility(0);
        this.mChangeBtn.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Log.i((String)LoginActivity.TAG, (String)"mChangeBtn onClick");
                LoginActivity.this.changeAccount();
            }
        });
    }

    private void showLoading() {
        this.mChangeBtn.setVisibility(8);
        this.mImageView.setImageBitmap(null);
        this.mProgressBar.setVisibility(0);
        this.mNotice.setText((CharSequence)this.getString(2131230831));
    }

    private void showNotice(String string2) {
        this.mProgressBar.setVisibility(8);
        this.mChangeBtn.setVisibility(8);
        this.mNotice.setText((CharSequence)string2);
    }

    private void showQrcode(Bitmap bitmap) {
        this.mChangeBtn.setVisibility(8);
        this.mImageView.setImageBitmap(bitmap);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968603);
        this.mViewPager = (VerticalViewPager)this.findViewById(2131689613);
        this.mAdapter = new PagerAdapter(new int[]{2130968669, 2130968633}){
            final /* synthetic */ int[] val$layouts;
            {
                this.val$layouts = nArray;
            }

            @Override
            public void destroyItem(ViewGroup viewGroup, int n2, Object object) {
                viewGroup.removeView((View)object);
            }

            @Override
            public int getCount() {
                return this.val$layouts.length;
            }

            @Override
            public Object instantiateItem(ViewGroup viewGroup, int n2) {
                ViewGroup viewGroup2 = (ViewGroup)LayoutInflater.from((Context)LoginActivity.this).inflate(this.val$layouts[n2], viewGroup, false);
                viewGroup.addView((View)viewGroup2);
                if (n2 == 1) {
                    LoginActivity.this.onLoginViewInflated();
                }
                return viewGroup2;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };
        this.mViewPager.setAdapter(this.mAdapter);
    }

    protected void onDestroy() {
        if (this.mBgHandler != null) {
            this.mBgHandler.stop();
            this.mBgHandler.removeCallbacksAndMessages(null);
        }
        if (this.mFgHandler != null) {
            this.mFgHandler.removeCallbacksAndMessages(null);
        }
        if (this.mhandlerThread != null) {
            this.mhandlerThread.quitSafely();
        }
        super.onDestroy();
    }

    public void onLoginViewInflated() {
        this.mNotice = (TextView)this.findViewById(2131689662);
        this.mProgressBar = (ProgressBar)this.findViewById(2131689612);
        this.mImageView = (ImageView)this.findViewById(2131689663);
        this.mChangeBtn = (Button)this.findViewById(2131689664);
        this.mFgHandler = new FgHandler(Looper.getMainLooper());
        this.mhandlerThread = new HandlerThread("login");
        this.mhandlerThread.start();
        this.mBgHandler = new BgHandler(this.mhandlerThread.getLooper());
        Pair<Boolean, Boolean> pair = NetUtil.checkNet((Context)this);
        if (((Boolean)pair.first).booleanValue()) {
            this.init();
            return;
        }
        if (((Boolean)pair.second).booleanValue()) {
            this.requestInternetPermission(this);
            return;
        }
        this.showNotice(this.getString(2131230787));
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause((Context)this);
    }

    public void onRequestPermissionsResult(int n2, String[] stringArray, int[] nArray) {
        switch (n2) {
            default: {
                return;
            }
            case 0: 
        }
        if (nArray.length > 0 && nArray[0] == 0) {
            this.init();
            return;
        }
        this.mProgressBar.setVisibility(8);
        this.mNotice.setText(2131230799);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume((Context)this);
    }

    private class BgHandler
    extends Handler {
        private boolean isStopped;
        private long refreshTime;
        private String uuid;

        public BgHandler(Looper looper) {
            super(looper);
        }

        private boolean isValid(String string2, long l2) {
            if (TextUtils.isEmpty((CharSequence)string2)) {
                return false;
            }
            if (TextUtils.isEmpty((CharSequence)this.uuid)) {
                return true;
            }
            return TextUtils.equals((CharSequence)this.uuid, (CharSequence)string2);
        }

        /*
         * Enabled aggressive block sorting
         */
        public void handleMessage(Message object) {
            block16: {
                String string2;
                block14: {
                    String string3;
                    block15: {
                        block13: {
                            block12: {
                                if (this.isStopped) break block12;
                                switch (((Message)object).what) {
                                    default: {
                                        return;
                                    }
                                    case 1: 
                                }
                                string2 = (String)((Message)object).obj;
                                if (!this.isValid(string2, this.refreshTime)) break block12;
                                object = WxLogin.checkLoginStatus(string2);
                                if (!this.isStopped) break block13;
                            }
                            return;
                        }
                        if (object == null || ((Hashtable)object).isEmpty()) {
                            this.sendMessageDelayed(this.obtainMessage(1, string2), 5000L);
                            return;
                        }
                        string3 = ((Properties)object).getProperty("window.code");
                        if ("408".equals(string3)) break block14;
                        if (!"201".equals(string3)) break block15;
                        string3 = ((Properties)object).getProperty("window.userAvatar");
                        object = string3;
                        if (TextUtils.isEmpty((CharSequence)string3)) {
                            Log.w((String)LoginActivity.TAG, (String)"BgHandler::avatarUrl is empty");
                            object = "data:img/jpg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDABQODxIPDRQSEBIXFRQYHjIhHhwcHj0sLiQySUBMS0dARkVQWnNiUFVtVkVGZIhlbXd7gYKBTmCNl4x9lnN+gXz/wAALCACEAIQBAREA/8QAGQABAQEBAQEAAAAAAAAAAAAAAAMCAQQG/8QAIhABAAIBBAEFAQAAAAAAAAAAAAECEQMEITEFEhMyUWFB/9oACAEBAAA/APpa1rSsVpEVrEYiI6h0AAAAHk3Pi9lu9X3dxt6al8Y9UvWAAAAAAAAAAAAA7ETPTXtz9k6csdAAAAC0RiMOjF4zGUwAAAdr8oWByekQAAAdrxaFgcniEQAAAFazmGhjUn+JgAAAO0nFlnJ4hHsAAAAiM9KVpjmWxO1PpgAAAbrT7biIjp0ByaxPadqTH7DIAApSvGWwABPUjE5hgABWkx6YaAAE9SeoYAAAAAAAAAAAAAAAAAAT22pOrttLUtiJvSLTj9hQAAAAHzXnvObzYeQnQ0JpFIpE81zL/9k=";
                        }
                        Prefs.getInstance(LoginActivity.this.getApplicationContext()).setAvatar((String)object);
                        object = WxLogin.getBase64Image((String)object);
                        LoginActivity.this.mFgHandler.obtainMessage(3, object).sendToTarget();
                        break block14;
                    }
                    if (!"200".equals(string3)) {
                        Log.w((String)LoginActivity.TAG, (String)("BgHandler::unknown error, code=" + string3));
                        return;
                    }
                    if ((object = WxLogin.getToken(((Properties)object).getProperty("window.redirect_uri"))) != null && ((Token)object).ret == 0) break block16;
                }
                this.obtainMessage(1, string2).sendToTarget();
                return;
            }
            LoginActivity.this.mFgHandler.obtainMessage(4, object).sendToTarget();
        }

        public boolean isStopped() {
            return this.isStopped;
        }

        public void stop() {
            this.isStopped = true;
        }

        public void updateUuid(String string2) {
            synchronized (this) {
                this.uuid = string2;
                this.refreshTime = System.currentTimeMillis();
                return;
            }
        }
    }

    private class FastLoginTask
    extends AsyncTask<String, Void, String> {
        private FastLoginTask() {
        }

        protected String doInBackground(String ... stringArray) {
            return WxLogin.pushLogin(stringArray[0], stringArray[1]);
        }

        protected void onPostExecute(String string2) {
            if (!TextUtils.isEmpty((CharSequence)string2)) {
                LoginActivity.this.mBgHandler.updateUuid(string2);
                if (!LoginActivity.this.mBgHandler.isStopped()) {
                    LoginActivity.this.mBgHandler.obtainMessage(1, string2).sendToTarget();
                }
                return;
            }
            LoginActivity.this.changeAccount();
        }
    }

    private class FgHandler
    extends Handler {
        public FgHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message object) {
            switch (object.what) {
                default: {
                    super.handleMessage(object);
                    return;
                }
                case 1: {
                    LoginActivity.this.showNotice((String)object.obj);
                    return;
                }
                case 2: {
                    LoginActivity.this.showQrcode((Bitmap)object.obj);
                    return;
                }
                case 3: {
                    LoginActivity.this.setAvatar((Bitmap)object.obj);
                    return;
                }
                case 4: 
            }
            object = (Token)object.obj;
            Prefs.getInstance(LoginActivity.this.getApplicationContext()).setToken((Token)object);
            LoginActivity.this.jumpToMain((Token)object);
        }
    }

    private class QRCodeTask
    extends AsyncTask<String, Void, String> {
        private QRCodeTask() {
        }

        protected String doInBackground(String ... object) {
            Log.d((String)LoginActivity.TAG, (String)"QRCodeTask::on loading");
            object = WxLogin.getUUid();
            Log.d((String)LoginActivity.TAG, (String)("QRCodeTask::uuid=" + (String)object));
            if (TextUtils.isEmpty((CharSequence)object)) {
                LoginActivity.this.mFgHandler.obtainMessage(1, LoginActivity.this.getString(2131230802)).sendToTarget();
                return null;
            }
            if ("UNKNOWN_HOST".equals(object)) {
                MobclickAgent.reportError((Context)LoginActivity.this, "unknown host error when scan QRCode");
                LoginActivity.this.mFgHandler.obtainMessage(1, LoginActivity.this.getString(2131230829)).sendToTarget();
                return null;
            }
            Bitmap bitmap = WxLogin.getURLImage(WxLogin.formatQRUrl((String)object));
            if (bitmap == null) {
                LoginActivity.this.mFgHandler.obtainMessage(1, LoginActivity.this.getString(2131230802)).sendToTarget();
                return null;
            }
            LoginActivity.this.mFgHandler.obtainMessage(2, bitmap).sendToTarget();
            return object;
        }

        protected void onPostExecute(String string2) {
            if (!TextUtils.isEmpty((CharSequence)string2)) {
                LoginActivity.this.mBgHandler.updateUuid(string2);
                if (!LoginActivity.this.mBgHandler.isStopped()) {
                    LoginActivity.this.mBgHandler.obtainMessage(1, string2).sendToTarget();
                }
            }
        }
    }
}

