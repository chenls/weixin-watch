package com.riyuxihe.weixinqingliao;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.riyuxihe.weixinqingliao.util.Constants;
//import ticwear.design.app.AlertDialog;

public class SettingActivity extends BaseActivity {
    public static final String PERIOD_KEY = "sync_period";

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDialog();
    }

    public void initDialog() {
        String[] listItems = {"一分钟", "45秒", "半分钟", "15秒"};
        final long[] listItemsValue = {Constants.Period.HOME_STANDARD, 45000, 30000, 15000};
        long pref = readFromPref();
        int checkedItem = 0;
        if (pref == listItemsValue[1]) {
            checkedItem = 1;
        } else if (pref == listItemsValue[2]) {
            checkedItem = 2;
        } else if (pref == listItemsValue[3]) {
            checkedItem = 3;
        }
        new AlertDialog.Builder(this).setTitle("同步周期").setSingleChoiceItems(listItems, checkedItem, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SettingActivity.this.sendRescheduleBroadCast(listItemsValue[which]);
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                SettingActivity.this.finish();
            }
        }).create().show();
    }

    private void writeToPref(long period) {
        SharedPreferences.Editor editor = getPreferences(0).edit();
        editor.putLong(PERIOD_KEY, period);
        editor.commit();
    }

    private long readFromPref() {
        return getPreferences(0).getLong(PERIOD_KEY, Constants.Period.HOME_STANDARD);
    }

    /* access modifiers changed from: private */
    public void sendRescheduleBroadCast(long period) {
        writeToPref(period);
        Intent intent = new Intent(Constants.Action.RESCHEDULE);
        intent.putExtra("period", period);
        sendBroadcast(intent);
    }
}
