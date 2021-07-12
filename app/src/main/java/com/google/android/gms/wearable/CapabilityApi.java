/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.google.android.gms.wearable;

import android.net.Uri;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.CapabilityInfo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

public interface CapabilityApi {
    public static final String ACTION_CAPABILITY_CHANGED = "com.google.android.gms.wearable.CAPABILITY_CHANGED";
    public static final int FILTER_ALL = 0;
    public static final int FILTER_LITERAL = 0;
    public static final int FILTER_PREFIX = 1;
    public static final int FILTER_REACHABLE = 1;

    public PendingResult<Status> addCapabilityListener(GoogleApiClient var1, CapabilityListener var2, String var3);

    public PendingResult<Status> addListener(GoogleApiClient var1, CapabilityListener var2, Uri var3, int var4);

    public PendingResult<AddLocalCapabilityResult> addLocalCapability(GoogleApiClient var1, String var2);

    public PendingResult<GetAllCapabilitiesResult> getAllCapabilities(GoogleApiClient var1, int var2);

    public PendingResult<GetCapabilityResult> getCapability(GoogleApiClient var1, String var2, int var3);

    public PendingResult<Status> removeCapabilityListener(GoogleApiClient var1, CapabilityListener var2, String var3);

    public PendingResult<Status> removeListener(GoogleApiClient var1, CapabilityListener var2);

    public PendingResult<RemoveLocalCapabilityResult> removeLocalCapability(GoogleApiClient var1, String var2);

    public static interface AddLocalCapabilityResult
    extends Result {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CapabilityFilterType {
    }

    public static interface CapabilityListener {
        public void onCapabilityChanged(CapabilityInfo var1);
    }

    public static interface GetAllCapabilitiesResult
    extends Result {
        public Map<String, CapabilityInfo> getAllCapabilities();
    }

    public static interface GetCapabilityResult
    extends Result {
        public CapabilityInfo getCapability();
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface NodeFilterType {
    }

    public static interface RemoveLocalCapabilityResult
    extends Result {
    }
}

