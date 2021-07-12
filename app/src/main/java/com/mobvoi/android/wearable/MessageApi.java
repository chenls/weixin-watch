/*
 * Decompiled with CFR 0.151.
 */
package com.mobvoi.android.wearable;

import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.common.api.PendingResult;
import com.mobvoi.android.common.api.Result;
import com.mobvoi.android.common.api.Status;
import com.mobvoi.android.wearable.MessageEvent;

public interface MessageApi {
    public PendingResult<Status> addListener(MobvoiApiClient var1, MessageListener var2);

    public PendingResult<Status> removeListener(MobvoiApiClient var1, MessageListener var2);

    public PendingResult<SendMessageResult> sendMessage(MobvoiApiClient var1, String var2, String var3, byte[] var4);

    public static interface MessageListener {
        public void onMessageReceived(MessageEvent var1);
    }

    public static interface SendMessageResult
    extends Result {
        public static final int UNKNOWN_REQUEST_ID = -1;

        public int getRequestId();
    }
}

