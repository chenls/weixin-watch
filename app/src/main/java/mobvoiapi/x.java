/*
 * Decompiled with CFR 0.151.
 */
package mobvoiapi;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import java.util.concurrent.TimeUnit;
import mobvoiapi.bp;
import mobvoiapi.z;

public class x<R1 extends com.mobvoi.android.common.api.Result, R2 extends Result>
implements com.mobvoi.android.common.api.PendingResult<R1> {
    private PendingResult<R2> a;

    public x(PendingResult<R2> pendingResult) {
        this.a = pendingResult;
    }

    @Override
    public R1 await() {
        bp.a("MobvoiApiManager", "PendingResultGoogleImpl#await()");
        return z.a(this.a.await());
    }

    @Override
    public R1 await(long l2, TimeUnit timeUnit) {
        bp.a("MobvoiApiManager", "PendingResultGoogleImpl#await()");
        return z.a(this.a.await(l2, timeUnit));
    }

    @Override
    public boolean isCanceled() {
        bp.a("MobvoiApiManager", "PendingResultGoogleImpl#isCanceled()");
        return this.a.isCanceled();
    }

    @Override
    public void setResultCallback(com.mobvoi.android.common.api.ResultCallback<R1> resultCallback) {
        bp.a("MobvoiApiManager", "PendingResultGoogleImpl#setResultCallback()");
        resultCallback = z.a(resultCallback);
        this.a.setResultCallback((ResultCallback<R2>)((Object)resultCallback));
    }

    @Override
    public void setResultCallback(com.mobvoi.android.common.api.ResultCallback<R1> resultCallback, long l2, TimeUnit timeUnit) {
        bp.a("MobvoiApiManager", "PendingResultGoogleImpl#setResultCallback()");
        resultCallback = z.a(resultCallback);
        this.a.setResultCallback((ResultCallback<R2>)((Object)resultCallback), l2, timeUnit);
    }
}

