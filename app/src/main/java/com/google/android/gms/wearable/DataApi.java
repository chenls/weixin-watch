/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.ParcelFileDescriptor
 */
package com.google.android.gms.wearable;

import android.net.Uri;
import android.os.ParcelFileDescriptor;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemAsset;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.PutDataRequest;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface DataApi {
    public static final String ACTION_DATA_CHANGED = "com.google.android.gms.wearable.DATA_CHANGED";
    public static final int FILTER_LITERAL = 0;
    public static final int FILTER_PREFIX = 1;

    public PendingResult<Status> addListener(GoogleApiClient var1, DataListener var2);

    public PendingResult<Status> addListener(GoogleApiClient var1, DataListener var2, Uri var3, int var4);

    public PendingResult<DeleteDataItemsResult> deleteDataItems(GoogleApiClient var1, Uri var2);

    public PendingResult<DeleteDataItemsResult> deleteDataItems(GoogleApiClient var1, Uri var2, int var3);

    public PendingResult<DataItemResult> getDataItem(GoogleApiClient var1, Uri var2);

    public PendingResult<DataItemBuffer> getDataItems(GoogleApiClient var1);

    public PendingResult<DataItemBuffer> getDataItems(GoogleApiClient var1, Uri var2);

    public PendingResult<DataItemBuffer> getDataItems(GoogleApiClient var1, Uri var2, int var3);

    public PendingResult<GetFdForAssetResult> getFdForAsset(GoogleApiClient var1, Asset var2);

    public PendingResult<GetFdForAssetResult> getFdForAsset(GoogleApiClient var1, DataItemAsset var2);

    public PendingResult<DataItemResult> putDataItem(GoogleApiClient var1, PutDataRequest var2);

    public PendingResult<Status> removeListener(GoogleApiClient var1, DataListener var2);

    public static interface DataItemResult
    extends Result {
        public DataItem getDataItem();
    }

    public static interface DataListener {
        public void onDataChanged(DataEventBuffer var1);
    }

    public static interface DeleteDataItemsResult
    extends Result {
        public int getNumDeleted();
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface FilterType {
    }

    public static interface GetFdForAssetResult
    extends Releasable,
    Result {
        public ParcelFileDescriptor getFd();

        public InputStream getInputStream();
    }
}

