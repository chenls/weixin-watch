/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package mobvoiapi;

import android.net.Uri;
import com.mobvoi.android.common.MobvoiApiManager;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.common.api.PendingResult;
import com.mobvoi.android.common.api.Status;
import com.mobvoi.android.wearable.Asset;
import com.mobvoi.android.wearable.DataApi;
import com.mobvoi.android.wearable.DataItemAsset;
import com.mobvoi.android.wearable.DataItemBuffer;
import com.mobvoi.android.wearable.PutDataRequest;
import mobvoiapi.ab;
import mobvoiapi.bc;
import mobvoiapi.bp;
import mobvoiapi.t;

public class y
implements DataApi,
ab {
    private DataApi a;

    public y() {
        MobvoiApiManager.getInstance().registerProxy(this);
        this.a();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void a() {
        if (MobvoiApiManager.getInstance().getGroup() == MobvoiApiManager.ApiGroup.MMS) {
            this.a = new bc();
        } else if (MobvoiApiManager.getInstance().getGroup() == MobvoiApiManager.ApiGroup.GMS) {
            this.a = new t();
        }
        bp.a("MobvoiApiManager", "load data api success.");
    }

    @Override
    public PendingResult<Status> addListener(MobvoiApiClient mobvoiApiClient, DataApi.DataListener dataListener) {
        bp.a("MobvoiApiManager", "DataApiProxy#addListener()");
        return this.a.addListener(mobvoiApiClient, dataListener);
    }

    @Override
    public PendingResult<DataApi.DeleteDataItemsResult> deleteDataItems(MobvoiApiClient mobvoiApiClient, Uri uri) {
        bp.a("MobvoiApiManager", "DataApiProxy#deleteDataItems()");
        return this.a.deleteDataItems(mobvoiApiClient, uri);
    }

    @Override
    public PendingResult<DataApi.DataItemResult> getDataItem(MobvoiApiClient mobvoiApiClient, Uri uri) {
        bp.a("MobvoiApiManager", "DataApiProxy#getDataItem()");
        return this.a.getDataItem(mobvoiApiClient, uri);
    }

    @Override
    public PendingResult<DataItemBuffer> getDataItems(MobvoiApiClient mobvoiApiClient) {
        bp.a("MobvoiApiManager", "DataApiProxy#getDataItems()");
        return this.a.getDataItems(mobvoiApiClient);
    }

    @Override
    public PendingResult<DataItemBuffer> getDataItems(MobvoiApiClient mobvoiApiClient, Uri uri) {
        bp.a("MobvoiApiManager", "DataApiProxy#getDataItems()");
        return this.a.getDataItems(mobvoiApiClient, uri);
    }

    @Override
    public PendingResult<DataApi.GetFdForAssetResult> getFdForAsset(MobvoiApiClient mobvoiApiClient, Asset asset) {
        bp.a("MobvoiApiManager", "DataApiProxy#getFdForAsset()");
        return this.a.getFdForAsset(mobvoiApiClient, asset);
    }

    @Override
    public PendingResult<DataApi.GetFdForAssetResult> getFdForAsset(MobvoiApiClient mobvoiApiClient, DataItemAsset dataItemAsset) {
        bp.a("MobvoiApiManager", "DataApiProxy#getFdForAsset()");
        return this.a.getFdForAsset(mobvoiApiClient, dataItemAsset);
    }

    @Override
    public PendingResult<DataApi.DataItemResult> putDataItem(MobvoiApiClient mobvoiApiClient, PutDataRequest putDataRequest) {
        bp.a("MobvoiApiManager", "DataApiProxy#putDataItem()");
        return this.a.putDataItem(mobvoiApiClient, putDataRequest);
    }

    @Override
    public PendingResult<Status> removeListener(MobvoiApiClient mobvoiApiClient, DataApi.DataListener dataListener) {
        bp.a("MobvoiApiManager", "DataApiProxy#removeListener()");
        return this.a.removeListener(mobvoiApiClient, dataListener);
    }
}

