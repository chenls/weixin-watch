/*
 * Decompiled with CFR 0.151.
 */
package mobvoiapi;

import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.mobvoi.android.wearable.DataApi;
import mobvoiapi.bp;
import mobvoiapi.z;

public class m
implements DataApi.DataListener {
    private DataApi.DataListener a;

    public m(DataApi.DataListener dataListener) {
        this.a = dataListener;
    }

    public boolean equals(Object object) {
        if (object instanceof m) {
            return this.a.equals(((m)object).a);
        }
        return false;
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        bp.a("MobvoiApiManager", "DataListenerWrapper#onDataChanged()");
        this.a.onDataChanged(z.a(dataEventBuffer));
    }
}

