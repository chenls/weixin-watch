/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.google.android.gms.wearable;

import android.net.Uri;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.MessageEvent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface MessageApi {
    public static final String ACTION_MESSAGE_RECEIVED = "com.google.android.gms.wearable.MESSAGE_RECEIVED";
    public static final int FILTER_LITERAL = 0;
    public static final int FILTER_PREFIX = 1;
    public static final int UNKNOWN_REQUEST_ID = -1;

    public PendingResult<Status> addListener(GoogleApiClient var1, MessageListener var2);

    public PendingResult<Status> addListener(GoogleApiClient var1, MessageListener var2, Uri var3, int var4);

    public PendingResult<Status> removeListener(GoogleApiClient var1, MessageListener var2);

    public PendingResult<SendMessageResult> sendMessage(GoogleApiClient var1, String var2, String var3, byte[] var4);

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface FilterType {
    }

    public static interface MessageListener {
        public void onMessageReceived(MessageEvent var1);
    }

    public static interface SendMessageResult
    extends Result {
        public int getRequestId();
    }
}

