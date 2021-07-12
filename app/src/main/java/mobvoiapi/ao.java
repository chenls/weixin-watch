/*
 * Decompiled with CFR 0.151.
 */
package mobvoiapi;

import com.mobvoi.android.common.api.Result;
import com.mobvoi.android.location.LocationServices;
import mobvoiapi.ap;
import mobvoiapi.h;

public abstract class ao<R extends Result>
extends h.b<R, ap> {
    protected ao() {
        super(LocationServices.CLIENT_KEY);
    }
}

