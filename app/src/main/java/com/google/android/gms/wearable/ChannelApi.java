/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.wearable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.Channel;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface ChannelApi {
    public static final String ACTION_CHANNEL_EVENT = "com.google.android.gms.wearable.CHANNEL_EVENT";

    public PendingResult<Status> addListener(GoogleApiClient var1, ChannelListener var2);

    public PendingResult<OpenChannelResult> openChannel(GoogleApiClient var1, String var2, String var3);

    public PendingResult<Status> removeListener(GoogleApiClient var1, ChannelListener var2);

    public static interface ChannelListener {
        public static final int CLOSE_REASON_DISCONNECTED = 1;
        public static final int CLOSE_REASON_LOCAL_CLOSE = 3;
        public static final int CLOSE_REASON_NORMAL = 0;
        public static final int CLOSE_REASON_REMOTE_CLOSE = 2;

        public void onChannelClosed(Channel var1, int var2, int var3);

        public void onChannelOpened(Channel var1);

        public void onInputClosed(Channel var1, int var2, int var3);

        public void onOutputClosed(Channel var1, int var2, int var3);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CloseReason {
    }

    public static interface OpenChannelResult
    extends Result {
        public Channel getChannel();
    }
}

