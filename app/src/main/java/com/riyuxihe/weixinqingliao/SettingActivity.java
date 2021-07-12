/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.DialogInterface$OnDismissListener
 *  android.content.Intent
 *  android.content.SharedPreferences$Editor
 *  android.os.Bundle
 */
package com.riyuxihe.weixinqingliao;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import ticwear.design.app.AlertDialog;

public class SettingActivity
extends Activity {
    public static final String PERIOD_KEY = "sync_period";

    private long readFromPref() {
        return this.getPreferences(0).getLong(PERIOD_KEY, 60000L);
    }

    private void sendRescheduleBroadCast(long l2) {
        this.writeToPref(l2);
        Intent intent = new Intent("com.riyuxihe.weixinqingliao.RESCHEDULE");
        intent.putExtra("period", l2);
        this.sendBroadcast(intent);
    }

    private void writeToPref(long l2) {
        SharedPreferences.Editor editor = this.getPreferences(0).edit();
        editor.putLong(PERIOD_KEY, l2);
        editor.commit();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void initDialog() {
        long[] lArray;
        long[] lArray2 = lArray = new long[4];
        lArray[0] = 60000L;
        lArray2[1] = 45000L;
        lArray2[2] = 30000L;
        lArray2[3] = 15000L;
        long l2 = this.readFromPref();
        int n2 = 0;
        if (l2 == lArray[1]) {
            n2 = 1;
        } else if (l2 == lArray[2]) {
            n2 = 2;
        } else if (l2 == lArray[3]) {
            n2 = 3;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)this).setTitle("\u540c\u6b65\u5468\u671f");
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n2) {
                SettingActivity.this.sendRescheduleBroadCast(lArray[n2]);
            }
        };
        builder.setSingleChoiceItems(new String[]{"\u4e00\u5206\u949f", "45\u79d2", "\u534a\u5206\u949f", "15\u79d2"}, n2, onClickListener).setOnDismissListener(new DialogInterface.OnDismissListener(){

            public void onDismiss(DialogInterface dialogInterface) {
                SettingActivity.this.finish();
            }
        }).create().show();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.initDialog();
    }
}

