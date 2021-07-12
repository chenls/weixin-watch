/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package mobvoiapi;

import android.net.Uri;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.Wearable;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.common.api.PendingResult;
import com.mobvoi.android.common.api.Status;
import com.mobvoi.android.wearable.Asset;
import com.mobvoi.android.wearable.DataApi;
import com.mobvoi.android.wearable.DataItemAsset;
import com.mobvoi.android.wearable.DataItemBuffer;
import com.mobvoi.android.wearable.PutDataRequest;
import mobvoiapi.bp;
import mobvoiapi.z;

public class t
implements com.mobvoi.android.wearable.DataApi {
    private DataApi a = Wearable.DataApi;

    @Override
    public PendingResult<Status> addListener(MobvoiApiClient object, DataApi.DataListener dataListener) {
        bp.a("MobvoiApiManager", "DataApiGoogleImpl#addListener()");
        object = z.a((MobvoiApiClient)object);
        return z.a(this.a.addListener((GoogleApiClient)object, z.a(dataListener)));
    }

    @Override
    public PendingResult<DataApi.DeleteDataItemsResult> deleteDataItems(MobvoiApiClient object, Uri uri) {
        bp.a("MobvoiApiManager", "DataApiGoogleImpl#deleteDataItems()");
        object = z.a((MobvoiApiClient)object);
        return z.a(this.a.deleteDataItems((GoogleApiClient)object, uri));
    }

    @Override
    public PendingResult<DataApi.DataItemResult> getDataItem(MobvoiApiClient object, Uri uri) {
        bp.a("MobvoiApiManager", "DataApiGoogleImpl#getDataItem()");
        object = z.a((MobvoiApiClient)object);
        return z.a(this.a.getDataItem((GoogleApiClient)object, uri));
    }

    @Override
    public PendingResult<DataItemBuffer> getDataItems(MobvoiApiClient object) {
        bp.a("MobvoiApiManager", "DataApiGoogleImpl#getDataItems()");
        object = z.a((MobvoiApiClient)object);
        return z.a(this.a.getDataItems((GoogleApiClient)object));
    }

    @Override
    public PendingResult<DataItemBuffer> getDataItems(MobvoiApiClient object, Uri uri) {
        bp.a("MobvoiApiManager", "DataApiGoogleImpl#getDataItems()");
        object = z.a((MobvoiApiClient)object);
        return z.a(this.a.getDataItems((GoogleApiClient)object, uri));
    }

    @Override
    public PendingResult<DataApi.GetFdForAssetResult> getFdForAsset(MobvoiApiClient object, Asset asset) {
        bp.a("MobvoiApiManager", "DataApiGoogleImpl#getFdForAsset()");
        object = z.a((MobvoiApiClient)object);
        return z.a(this.a.getFdForAsset((GoogleApiClient)object, z.a(asset)));
    }

    @Override
    public PendingResult<DataApi.GetFdForAssetResult> getFdForAsset(MobvoiApiClient object, DataItemAsset dataItemAsset) {
        bp.a("MobvoiApiManager", "DataApiGoogleImpl#getFdForAsset()");
        object = z.a((MobvoiApiClient)object);
        return z.a(this.a.getFdForAsset((GoogleApiClient)object, z.a(dataItemAsset)));
    }

    @Override
    public PendingResult<DataApi.DataItemResult> putDataItem(MobvoiApiClient object, PutDataRequest putDataRequest) {
        bp.a("MobvoiApiManager", "DataApiGoogleImpl#putDataItem()");
        object = z.a((MobvoiApiClient)object);
        return z.a(this.a.putDataItem((GoogleApiClient)object, z.a(putDataRequest)));
    }

    @Override
    public PendingResult<Status> removeListener(MobvoiApiClient object, DataApi.DataListener dataListener) {
        bp.a("MobvoiApiManager", "DataApiGoogleImpl#removeListener()");
        object = z.a((MobvoiApiClient)object);
        return z.a(this.a.removeListener((GoogleApiClient)object, z.a(dataListener)));
    }
}

