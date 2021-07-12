/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.PendingIntent
 *  android.content.IntentSender$SendIntentException
 */
package com.mobvoi.android.common;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender;

public class ConnectionResult {
    public static final int API_UNAVAILABLE = 16;
    public static final int CANCELED = 13;
    public static final int DEVELOPER_ERROR = 10;
    public static final int INTERNAL_ERROR = 8;
    public static final int INTERRUPTED = 15;
    public static final int INVALID_ACCOUNT = 5;
    public static final int LICENSE_CHECK_FAILED = 11;
    public static final int NETWORK_ERROR = 7;
    public static final int RESOLUTION_REQUIRED = 6;
    public static final int SERVICE_DISABLED = 3;
    public static final int SERVICE_INVALID = 9;
    public static final int SERVICE_MISSING = 1;
    public static final int SERVICE_VERSION_UPDATE_REQUIRED = 2;
    public static final int SIGN_IN_REQUIRED = 4;
    public static final int SUCCESS = 0;
    public static final ConnectionResult SUCCESS_CONNECTION_RESULT = new ConnectionResult(0, null);
    public static final int TIMEOUT = 14;
    private final int a;
    private final PendingIntent b;

    public ConnectionResult(int n2, PendingIntent pendingIntent) {
        this.a = n2;
        this.b = pendingIntent;
    }

    private String a() {
        if (this.a == 0) {
            return "SUCCESS";
        }
        if (this.a == 1) {
            return "SERVICE_MISSING";
        }
        if (this.a == 2) {
            return "SERVICE_VERSION_UPDATE_REQUIRED";
        }
        if (this.a == 3) {
            return "SERVICE_DISABLED";
        }
        if (this.a == 4) {
            return "SIGN_IN_REQUIRED";
        }
        if (this.a == 5) {
            return "INVALID_ACCOUNT";
        }
        if (this.a == 6) {
            return "RESOLUTION_REQUIRED";
        }
        if (this.a == 7) {
            return "NETWORK_ERROR";
        }
        if (this.a == 9) {
            return "SERVICE_INVALID";
        }
        if (this.a == 8) {
            return "INTERNAL_ERROR";
        }
        if (this.a == 10) {
            return "DEVELOPER_ERROR";
        }
        if (this.a == 11) {
            return "LICENSE_CHECK_FAILED";
        }
        if (this.a == 13) {
            return "CANCELED";
        }
        if (this.a == 14) {
            return "TIMEOUT";
        }
        return "unknown status code " + this.a;
    }

    public int getErrorCode() {
        return this.a;
    }

    public PendingIntent getResolution() {
        return this.b;
    }

    public boolean hasResolution() {
        return this.a != 0 && this.b != null;
    }

    public boolean isSuccess() {
        return this.a == 0;
    }

    public void startResolutionForResult(Activity activity, int n2) throws IntentSender.SendIntentException {
        if (this.hasResolution()) {
            activity.startIntentSenderForResult(this.b.getIntentSender(), n2, null, 0, 0, 0);
        }
    }

    public String toString() {
        return new StringBuffer().append("statusCode: ").append(this.a()).append(", resolution: ").append(this.b).toString();
    }
}

