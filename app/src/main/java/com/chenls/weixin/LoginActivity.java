package com.chenls.weixin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import androidx.viewpager.widget.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chenls.weixin.model.Token;
import com.chenls.weixin.util.Constants;
import com.chenls.weixin.util.NetUtil;
import com.chenls.weixin.util.Prefs;
import com.chenls.weixin.util.WxLogin;

import java.util.Properties;

public class LoginActivity extends BaseActivity {
    private static final int BG_MSG_CHECK_LOGIN_STATUS = 1;
    private static final int MSG_LOGIN = 4;
    private static final int MSG_QRCODE = 2;
    private static final int MSG_SCANNED = 3;
    private static final int MSG_TIPS = 1;
    private static final int PERMISSIONS_REQUEST_INTERNET = 0;
    private static final String TAG = "LoginActivity";
    /* access modifiers changed from: private */
    public BgHandler mBgHandler;
    /* access modifiers changed from: private */
    public FgHandler mFgHandler;
    private PagerAdapter mAdapter;
    private Button mChangeBtn;
    private ImageView mImageView;
    private TextView mNotice;
    private ProgressBar mProgressBar;
    private HandlerThread mhandlerThread;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);
        onLoginViewInflated();
    }

    public void onLoginViewInflated() {
        Log.d(TAG, "onLoginViewInflated");
        this.mNotice = findViewById(R.id.notice);
        this.mProgressBar = findViewById(R.id.progressBar);
        this.mImageView = findViewById(R.id.img_wx_login_qr);
        this.mChangeBtn = findViewById(R.id.btn_change_account);
        this.mFgHandler = new FgHandler(Looper.getMainLooper());
        this.mhandlerThread = new HandlerThread("login");
        this.mhandlerThread.start();
        this.mBgHandler = new BgHandler(this.mhandlerThread.getLooper());
        Pair<Boolean, Boolean> connectedBlocked = NetUtil.checkNet(this);
        if (connectedBlocked.first.booleanValue()) {
            init();
        } else if (connectedBlocked.second.booleanValue()) {
            requestInternetPermission(this);
        } else {
            showNotice(getString(R.string.bad_net_notice));
        }
    }

    private void init() {
        Log.d(TAG, "init");
        String avatarUrl = Prefs.getInstance(getApplicationContext()).getAvatar();
        if (TextUtils.isEmpty(avatarUrl)) {
            new QRCodeTask().execute();
            return;
        }
        long expireAt = Prefs.getInstance(getApplicationContext()).getExpireAt().longValue();
        Token prefToken = Prefs.getInstance(getApplicationContext()).getToken();
        if (System.currentTimeMillis() >= expireAt || prefToken == null || TextUtils.isEmpty(prefToken.getWxuin())) {
            new QRCodeTask().execute();
            return;
        }
        setAvatar(WxLogin.getBase64Image(avatarUrl));
        new FastLoginTask().execute(prefToken.getWxuin(), prefToken.cookie);
    }

    private void showLoading() {
        Log.d(TAG, "showLoading");
        this.mChangeBtn.setVisibility(8);
        this.mImageView.setImageBitmap(null);
        this.mProgressBar.setVisibility(0);
        this.mNotice.setText(getString(R.string.waiting_notice));
    }

    /* access modifiers changed from: private */
    public void showNotice(String tips) {
        this.mProgressBar.setVisibility(8);
        this.mChangeBtn.setVisibility(8);
        this.mNotice.setText(tips);
    }

    /* access modifiers changed from: private */
    public void showQrcode(Bitmap bitmap) {
        Log.d(TAG, "showQrcode");
        this.mChangeBtn.setVisibility(8);
        this.mImageView.setImageBitmap(bitmap);
        showNotice(getString(R.string.content_plus_1));
    }

    /* access modifiers changed from: private */
    public void setAvatar(Bitmap bitmap) {
        this.mProgressBar.setVisibility(0);
        this.mImageView.setImageBitmap(bitmap);
        showNotice(getString(R.string.content_plus_2));
        this.mChangeBtn.setVisibility(0);
        this.mChangeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(LoginActivity.TAG, "mChangeBtn onClick");
                LoginActivity.this.changeAccount();
            }
        });
    }

    /* access modifiers changed from: private */
    public void changeAccount() {
        Prefs.getInstance(getApplicationContext()).clear();
        showLoading();
        this.mBgHandler.removeCallbacksAndMessages(null);
        new QRCodeTask().execute();
    }

    /* access modifiers changed from: private */
    public void jumpToMain(Token token) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtras(token.toBundle());
        startActivity(intent);
        finish();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
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

    private void requestInternetPermission(Activity activity) {
//        if (PermissionCompat.checkSelfPermission(activity, "android.permission.INTERNET") == 0) {
//            return;
//        }
//        if (PermissionCompat.shouldShowRequestPermissionRationale(activity, "android.permission.INTERNET")) {
//            this.mProgressBar.setVisibility(8);
//            this.mNotice.setText(R.string.internet_permission_rationale);
//            return;
//        }
//        PermissionCompat.requestPermissions(activity, new String[]{"android.permission.INTERNET"}, 0);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults.length <= 0 || grantResults[0] != 0) {
                    this.mProgressBar.setVisibility(8);
                    this.mNotice.setText(R.string.internet_permission_denied);
                    return;
                }
                init();
                return;
            default:
                return;
        }
    }

    private class FastLoginTask extends AsyncTask<String, Void, String> {
        private FastLoginTask() {
        }

        /* access modifiers changed from: protected */
        public String doInBackground(String... strings) {
            return WxLogin.pushLogin(strings[0], strings[1]);
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String uuid) {
            if (!TextUtils.isEmpty(uuid)) {
                LoginActivity.this.mBgHandler.updateUuid(uuid);
                if (!LoginActivity.this.mBgHandler.isStopped()) {
                    LoginActivity.this.mBgHandler.obtainMessage(1, uuid).sendToTarget();
                    return;
                }
                return;
            }
            LoginActivity.this.changeAccount();
        }
    }

    private class QRCodeTask extends AsyncTask<String, Void, String> {
        private QRCodeTask() {
        }

        /* access modifiers changed from: protected */
        public String doInBackground(String... strings) {
            Log.d(LoginActivity.TAG, "QRCodeTask::on loading");
            String uuid = WxLogin.getUUid();
            Log.d(LoginActivity.TAG, "QRCodeTask::uuid=" + uuid);
            if (TextUtils.isEmpty(uuid)) {
                LoginActivity.this.mFgHandler.obtainMessage(1, LoginActivity.this.getString(R.string.net_error_notice)).sendToTarget();
                return null;
            } else if (WxLogin.UNKNOWN_HOST.equals(uuid)) {
//                MobclickAgent.reportError((Context) LoginActivity.this, "unknown host error when scan QRCode");
                LoginActivity.this.mFgHandler.obtainMessage(1, LoginActivity.this.getString(R.string.unknown_host_notice)).sendToTarget();
                return null;
            } else {
                Bitmap qrCode = WxLogin.getURLImage(WxLogin.formatQRUrl(uuid));
                if (qrCode == null) {
                    LoginActivity.this.mFgHandler.obtainMessage(1, LoginActivity.this.getString(R.string.net_error_notice)).sendToTarget();
                    return null;
                }
                LoginActivity.this.mFgHandler.obtainMessage(2, qrCode).sendToTarget();
                return uuid;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String uuid) {
            if (!TextUtils.isEmpty(uuid)) {
                LoginActivity.this.mBgHandler.updateUuid(uuid);
                if (!LoginActivity.this.mBgHandler.isStopped()) {
                    LoginActivity.this.mBgHandler.obtainMessage(1, uuid).sendToTarget();
                }
            }
        }
    }

    private class FgHandler extends Handler {
        public FgHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message inputMessage) {
            switch (inputMessage.what) {
                case 1:
                    LoginActivity.this.showNotice((String) inputMessage.obj);
                    return;
                case 2:
                    LoginActivity.this.showQrcode((Bitmap) inputMessage.obj);
                    return;
                case 3:
                    LoginActivity.this.setAvatar((Bitmap) inputMessage.obj);
                    return;
                case 4:
                    Token token = (Token) inputMessage.obj;
                    Prefs.getInstance(LoginActivity.this.getApplicationContext()).setToken(token);
                    LoginActivity.this.jumpToMain(token);
                    return;
                default:
                    super.handleMessage(inputMessage);
                    return;
            }
        }
    }

    private class BgHandler extends Handler {
        private boolean isStopped;
        private long refreshTime;
        private String uuid;

        public BgHandler(Looper looper) {
            super(looper);
        }

        public synchronized void updateUuid(String uuid2) {
            this.uuid = uuid2;
            this.refreshTime = System.currentTimeMillis();
        }

        public void stop() {
            this.isStopped = true;
        }

        public boolean isStopped() {
            return this.isStopped;
        }

        public void handleMessage(Message msg) {
            if (!this.isStopped) {
                switch (msg.what) {
                    case 1:
                        String uuid2 = (String) msg.obj;
                        if (isValid(uuid2, this.refreshTime)) {
                            Properties prop = WxLogin.checkLoginStatus(uuid2);
                            if (this.isStopped) {
                                return;
                            }
                            if (prop == null || prop.isEmpty()) {
                                sendMessageDelayed(obtainMessage(1, uuid2), 5000);
                                return;
                            }
                            String code = prop.getProperty(WxLogin.LOGIN_CODE_KEY);
                            if (!Constants.LoginCode.INIT.equals(code)) {
                                if (Constants.LoginCode.SCANNED.equals(code)) {
                                    String avatarUrl = prop.getProperty(WxLogin.AVATAR_KEY);
                                    if (TextUtils.isEmpty(avatarUrl)) {
                                        Log.w(LoginActivity.TAG, "BgHandler::avatarUrl is empty");
                                        avatarUrl = WxLogin.DEFAULT_AVATAR;
                                    }
                                    Prefs.getInstance(LoginActivity.this.getApplicationContext()).setAvatar(avatarUrl);
                                    LoginActivity.this.mFgHandler.obtainMessage(3, WxLogin.getBase64Image(avatarUrl)).sendToTarget();
                                } else if (Constants.LoginCode.LOGIN.equals(code)) {
                                    Token token = WxLogin.getToken(prop.getProperty(WxLogin.REDIRECT_KEY));
                                    if (token != null && token.ret == 0) {
                                        LoginActivity.this.mFgHandler.obtainMessage(4, token).sendToTarget();
                                        return;
                                    }
                                } else {
                                    Log.w(LoginActivity.TAG, "BgHandler::unknown error, code=" + code);
                                    return;
                                }
                            }
                            obtainMessage(1, uuid2).sendToTarget();
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }

        private boolean isValid(String uuid2, long refreshTime2) {
            if (TextUtils.isEmpty(uuid2)) {
                return false;
            }
            if (TextUtils.isEmpty(this.uuid)) {
                return true;
            }
            return TextUtils.equals(this.uuid, uuid2);
        }
    }
}
