/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.util.Base64
 *  android.util.Log
 */
package com.google.android.gms.wearable;

import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.internal.zzsi;
import com.google.android.gms.internal.zzsj;
import com.google.android.gms.internal.zzst;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import java.util.ArrayList;

public class DataMapItem {
    private final Uri mUri;
    private final DataMap zzbrd;

    private DataMapItem(DataItem dataItem) {
        this.mUri = dataItem.getUri();
        this.zzbrd = this.zza((DataItem)dataItem.freeze());
    }

    public static DataMapItem fromDataItem(DataItem dataItem) {
        if (dataItem == null) {
            throw new IllegalStateException("provided dataItem is null");
        }
        return new DataMapItem(dataItem);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private DataMap zza(DataItem var1_1) {
        if (var1_1.getData() == null && var1_1.getAssets().size() > 0) {
            throw new IllegalArgumentException("Cannot create DataMapItem from a DataItem  that wasn't made with DataMapItem.");
        }
        if (var1_1.getData() == null) {
            return new DataMap();
        }
        try {
            var4_2 = new ArrayList<Asset>();
            var3_6 = var1_1.getAssets().size();
            var2_7 = 0;
lbl9:
            // 2 sources

            while (true) {
                if (var2_7 >= var3_6) return zzsi.zza(new zzsi.zza(zzsj.zzA(var1_1.getData()), var4_2));
                var5_8 = var1_1.getAssets().get(Integer.toString(var2_7));
                if (var5_8 == null) {
                    throw new IllegalStateException("Cannot find DataItemAsset referenced in data at " + var2_7 + " for " + var1_1);
                }
                break;
            }
        }
        catch (zzst var4_3) lbl-1000:
        // 2 sources

        {
            while (true) {
                Log.w((String)"DataItem", (String)("Unable to parse datamap from dataItem. uri=" + var1_1.getUri() + ", data=" + Base64.encodeToString((byte[])var1_1.getData(), (int)0)));
                throw new IllegalStateException("Unable to parse datamap from dataItem.  uri=" + var1_1.getUri(), (Throwable)var4_4);
            }
        }
        try {
            var4_2.add(Asset.createFromRef(var5_8.getId()));
            ++var2_7;
            ** continue;
        }
        catch (NullPointerException var4_5) {
            ** continue;
        }
    }

    public DataMap getDataMap() {
        return this.zzbrd;
    }

    public Uri getUri() {
        return this.mUri;
    }
}

