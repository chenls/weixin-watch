/*
 * Decompiled with CFR 0.151.
 */
package mobvoiapi;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import mobvoiapi.bp;
import mobvoiapi.z;

public class r<R1 extends com.mobvoi.android.common.api.Result, R2 extends Result>
implements ResultCallback<R2> {
    private com.mobvoi.android.common.api.ResultCallback<R1> a;

    public r(com.mobvoi.android.common.api.ResultCallback<R1> resultCallback) {
        this.a = resultCallback;
    }

    public boolean equals(Object object) {
        if (object instanceof r) {
            return this.a.equals(((r)object).a);
        }
        return false;
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    @Override
    public void onResult(R2 object) {
        bp.a("MobvoiApiManager", "ResultCallbackWrapper#onResult()");
        object = z.a(object);
        this.a.onResult(object);
    }
}

