/*
 * Decompiled with CFR 0.151.
 */
package com.mobvoi.android.common.api;

public class CommonStatusCodes {
    public static final int ASSET_UNAVAILABLE = 4005;
    public static final int AUTH_API_ACCESS_FORBIDDEN = 3001;
    public static final int AUTH_API_CLIENT_ERROR = 3002;
    public static final int AUTH_API_INVALID_CREDENTIALS = 3000;
    public static final int AUTH_API_SERVER_ERROR = 3003;
    public static final int AUTH_TOKEN_ERROR = 3004;
    public static final int AUTH_URL_RESOLUTION = 3005;
    public static final int CANCEL = 16;
    public static final int DATA_ITEM_TOO_LARGE = 4003;
    public static final int DEVELOPER_ERROR = 10;
    public static final int DUPLICATE_LISTENER = 4001;
    public static final int INTERNAL_ERROR = 8;
    public static final int INTERRUPT = 12;
    public static final int INVALID_ACCOUNT = 5;
    public static final int INVALID_TARGET_NODE = 4004;
    public static final int LICENSE_CHECK_FAILED = 11;
    public static final int NETWORK_ERROR = 7;
    public static final int RESOLUTION_REQUIRED = 6;
    public static final int SERVICE_DISABLED = 3;
    public static final int SERVICE_INVALID = 9;
    public static final int SERVICE_MISSING = 1;
    public static final int SERVICE_VERSION_UPDATE_REQUIRED = 2;
    public static final int SIGN_IN_REQUIRED = 4;
    public static final int SUCCESS = 0;
    public static final int SUCCESS_CACHE = -1;
    public static final int TARGET_NODE_NOT_CONNECTED = 4000;
    public static final int TIMEOUT = 13;
    public static final int UNKNOWN_LISTENER = 4002;

    public static String getStatusCodeString(int n2) {
        if (n2 == -1) {
            return "SUCCESS_CACHE";
        }
        if (n2 == 0) {
            return "SUCCESS";
        }
        if (n2 == 1) {
            return "SERVICE_MISSING";
        }
        if (n2 == 2) {
            return "SERVICE_VERSION_UPDATE_REQUIRED";
        }
        if (n2 == 3) {
            return "SERVICE_DISABLED";
        }
        if (n2 == 4) {
            return "SIGN_IN_REQUIRED";
        }
        if (n2 == 5) {
            return "INVALID_ACCOUNT";
        }
        if (n2 == 6) {
            return "RESOLUTION_REQUIRED";
        }
        if (n2 == 7) {
            return "NETWORK_ERROR";
        }
        if (n2 == 8) {
            return "INTERNAL_ERROR";
        }
        if (n2 == 9) {
            return "SERVICE_INVALID";
        }
        if (n2 == 10) {
            return "DEVELOPER_ERROR";
        }
        if (n2 == 11) {
            return "LICENSE_CHECK_FAILED";
        }
        if (n2 == 12) {
            return "INTERRUPT";
        }
        if (n2 == 3000) {
            return "AUTH_API_INVALID_CREDENTIALS";
        }
        if (n2 == 3001) {
            return "AUTH_API_ACCESS_FORBIDDEN";
        }
        if (n2 == 3002) {
            return "AUTH_API_CLIENT_ERROR";
        }
        if (n2 == 3003) {
            return "AUTH_API_SERVER_ERROR";
        }
        if (n2 == 3004) {
            return "AUTH_TOKEN_ERROR";
        }
        if (n2 == 3005) {
            return "AUTH_URL_RESOLUTION";
        }
        return "unknown status code: " + n2;
    }
}

