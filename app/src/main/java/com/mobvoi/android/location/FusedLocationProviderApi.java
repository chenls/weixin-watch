/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.location.Location
 *  android.os.Looper
 */
package com.mobvoi.android.location;

import android.app.PendingIntent;
import android.location.Location;
import android.os.Looper;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.common.api.PendingResult;
import com.mobvoi.android.common.api.Status;
import com.mobvoi.android.location.LocationListener;
import com.mobvoi.android.location.LocationRequest;

public interface FusedLocationProviderApi {
    public static final String KEY_LOCATION_CHANGED = "com.google.android.location.LOCATION";
    public static final String KEY_MOCK_LOCATION = "mockLocation";

    public Location getLastLocation(MobvoiApiClient var1);

    public PendingResult<Status> removeLocationUpdates(MobvoiApiClient var1, PendingIntent var2);

    public PendingResult<Status> removeLocationUpdates(MobvoiApiClient var1, LocationListener var2);

    public PendingResult<Status> requestLocationUpdates(MobvoiApiClient var1, LocationRequest var2, PendingIntent var3);

    public PendingResult<Status> requestLocationUpdates(MobvoiApiClient var1, LocationRequest var2, LocationListener var3);

    public PendingResult<Status> requestLocationUpdates(MobvoiApiClient var1, LocationRequest var2, LocationListener var3, Looper var4);
}

