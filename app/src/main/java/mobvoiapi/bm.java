/*
 * Decompiled with CFR 0.151.
 */
package mobvoiapi;

import com.mobvoi.android.common.api.Result;
import com.mobvoi.android.wearable.Wearable;
import mobvoiapi.bj;
import mobvoiapi.h;

public abstract class bm<R extends Result>
extends h.b<R, bj> {
    protected bm() {
        super(Wearable.CLIENT_KEY);
    }
}

