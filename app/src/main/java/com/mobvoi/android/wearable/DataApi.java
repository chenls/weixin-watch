/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.ParcelFileDescriptor
 */
package com.mobvoi.android.wearable;

import android.net.Uri;
import android.os.ParcelFileDescriptor;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.common.api.PendingResult;
import com.mobvoi.android.common.api.Releasable;
import com.mobvoi.android.common.api.Result;
import com.mobvoi.android.common.api.Status;
import com.mobvoi.android.wearable.Asset;
import com.mobvoi.android.wearable.DataEventBuffer;
import com.mobvoi.android.wearable.DataItem;
import com.mobvoi.android.wearable.DataItemAsset;
import com.mobvoi.android.wearable.DataItemBuffer;
import com.mobvoi.android.wearable.PutDataRequest;
import java.io.InputStream;

public interface DataApi {
    public PendingResult<Status> addListener(MobvoiApiClient var1, DataListener var2);

    public PendingResult<DeleteDataItemsResult> deleteDataItems(MobvoiApiClient var1, Uri var2);

    public PendingResult<DataItemResult> getDataItem(MobvoiApiClient var1, Uri var2);

    public PendingResult<DataItemBuffer> getDataItems(MobvoiApiClient var1);

    public PendingResult<DataItemBuffer> getDataItems(MobvoiApiClient var1, Uri var2);

    public PendingResult<GetFdForAssetResult> getFdForAsset(MobvoiApiClient var1, Asset var2);

    public PendingResult<GetFdForAssetResult> getFdForAsset(MobvoiApiClient var1, DataItemAsset var2);

    public PendingResult<DataItemResult> putDataItem(MobvoiApiClient var1, PutDataRequest var2);

    public PendingResult<Status> removeListener(MobvoiApiClient var1, DataListener var2);

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

    public static interface GetFdForAssetResult
    extends Releasable,
    Result {
        public ParcelFileDescriptor getFd();

        public InputStream getInputStream();
    }
}

