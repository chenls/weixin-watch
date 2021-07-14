package com.riyuxihe.weixinqingliao;

import android.app.Activity;

public class BaseActivity extends Activity {
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}