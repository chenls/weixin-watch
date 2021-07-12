/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.mobvoi.android.wearable;

import android.net.Uri;
import android.support.annotation.NonNull;
import com.mobvoi.android.wearable.Asset;
import com.mobvoi.android.wearable.DataItem;
import com.mobvoi.android.wearable.DataMap;
import mobvoiapi.bp;

public class DataMapItem {
    public static final String ASSET_KEYS = "assetKeys";
    public static final String ASSET_VALUES = "assetValues";
    public static final String DATA = "data";
    private final Uri a;
    private final DataMap b;

    private DataMapItem(DataItem dataItem) {
        this.a = dataItem.getUri();
        this.b = this.a((DataItem)dataItem.freeze());
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @NonNull
    private DataMap a(DataItem var1_1) {
        var4_3 /* !! */  = var1_1.getData();
        if (var4_3 /* !! */  == null && var1_1.getAssets().size() > 0) {
            throw new IllegalArgumentException("Cannot create DataMapItem from an empty DataItem.");
        }
        var5_4 = DataMap.fromByteArray(var4_3 /* !! */ );
        if (var5_4 == null) {
            return new DataMap();
        }
        try {
            if (var1_1.getAssets() == null) return var5_4;
lbl9:
            // 2 sources

            for (byte[] var4_3 : var1_1.getAssets().keySet()) {
                var7_8 = var1_1.getAssets().get(var4_3 /* !! */ );
                var8_9 = var4_3 /* !! */ .split("_@_");
                var2_6 = 0;
                var4_3 /* !! */  = var5_4;
lbl14:
                // 2 sources

                while (var2_6 < var8_9.length - 1) {
                    var9_10 = var8_9[var2_6 + 1].split("_#_");
                    if (var9_10.length == 1) {
                        var4_3 /* !! */  = var4_3 /* !! */ .getDataMap(var8_9[var2_6]);
                        break block8;
                    }
                    var3_7 = Integer.parseInt(var9_10[0]);
                    var4_3 /* !! */  = var4_3 /* !! */ .getDataMapArrayList(var8_9[var2_6]).get(var3_7);
                    ** break block9
                }
            }
        }
        catch (Exception var1_2) {
            bp.b("DataMapItem", "parse a DataItem failed.", var1_2);
            throw new IllegalStateException("parse a DataItem failed.", var1_2);
        }
lbl-1000:
        // 1 sources

        {
            block8: {
                var8_9[var2_6 + 1] = var9_10[1];
            }
            ++var2_6;
            ** GOTO lbl14
            return var5_4;
        }
        {
            var4_3 /* !! */ .putAsset(var8_9[var8_9.length - 1], Asset.createFromRef(var7_8.getId()));
            ** GOTO lbl9
        }
    }

    @NonNull
    public static DataMapItem fromDataItem(DataItem dataItem) {
        if (dataItem == null) {
            throw new IllegalStateException("unexpected null dataItem.");
        }
        return new DataMapItem(dataItem);
    }

    @NonNull
    public DataMap getDataMap() {
        return this.b;
    }

    public Uri getUri() {
        return this.a;
    }
}

