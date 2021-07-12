/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcelable
 */
package com.google.android.gms.wearable;

import android.net.Uri;
import android.os.Parcelable;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.ChannelApi;
import java.io.InputStream;
import java.io.OutputStream;

public interface Channel
extends Parcelable {
    public PendingResult<Status> addListener(GoogleApiClient var1, ChannelApi.ChannelListener var2);

    public PendingResult<Status> close(GoogleApiClient var1);

    public PendingResult<Status> close(GoogleApiClient var1, int var2);

    public PendingResult<GetInputStreamResult> getInputStream(GoogleApiClient var1);

    public String getNodeId();

    public PendingResult<GetOutputStreamResult> getOutputStream(GoogleApiClient var1);

    public String getPath();

    public PendingResult<Status> receiveFile(GoogleApiClient var1, Uri var2, boolean var3);

    public PendingResult<Status> removeListener(GoogleApiClient var1, ChannelApi.ChannelListener var2);

    public PendingResult<Status> sendFile(GoogleApiClient var1, Uri var2);

    public PendingResult<Status> sendFile(GoogleApiClient var1, Uri var2, long var3, long var5);

    public static interface GetInputStreamResult
    extends Releasable,
    Result {
        public InputStream getInputStream();
    }

    public static interface GetOutputStreamResult
    extends Releasable,
    Result {
        public OutputStream getOutputStream();
    }
}

