/*
 * Decompiled with CFR 0.151.
 */
package mobvoiapi;

import com.google.android.gms.wearable.DataItemAsset;

public class l
implements DataItemAsset {
    private String a;
    private String b;

    public l(String string2, String string3) {
        this.a = string2;
        this.b = string3;
    }

    public DataItemAsset a() {
        return this;
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.a();
    }

    @Override
    public String getDataItemKey() {
        return this.b;
    }

    @Override
    public String getId() {
        return this.a;
    }

    @Override
    public boolean isDataValid() {
        return true;
    }
}

