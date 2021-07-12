/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.SystemClock
 */
package com.mobvoi.android.location;

import android.os.Parcel;
import android.os.SystemClock;

public class LocationRequest {
    public static final int PRIORITY_BALANCED_POWER_ACCURACY = 102;
    public static final int PRIORITY_HIGH_ACCURACY = 100;
    public static final int PRIORITY_LOW_POWER = 104;
    public static final int PRIORITY_NO_POWER = 105;
    private final int a;
    private int b = 102;
    private long c = 3600000L;
    private long d = 600000L;
    private boolean e = false;
    private long f = Long.MAX_VALUE;
    private int g = Integer.MAX_VALUE;
    private float h = 0.0f;

    public LocationRequest() {
        this.a = 1;
    }

    private static void a(float f2) {
        if (f2 < 0.0f) {
            throw new IllegalArgumentException("invalid displacement: " + f2);
        }
    }

    public static LocationRequest create() {
        return new LocationRequest();
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (!(object instanceof LocationRequest)) {
                    return false;
                }
                object = (LocationRequest)object;
                if (this.b != ((LocationRequest)object).b || this.c != ((LocationRequest)object).c || this.d != ((LocationRequest)object).d || this.e != ((LocationRequest)object).e || this.f != ((LocationRequest)object).f || this.g != ((LocationRequest)object).g || this.h != ((LocationRequest)object).h) break block5;
            }
            return true;
        }
        return false;
    }

    public long getExpirationTime() {
        return this.f;
    }

    public long getFastestInterval() {
        return this.d;
    }

    public long getInterval() {
        return this.c;
    }

    public int getNumUpdates() {
        return this.g;
    }

    public int getPriority() {
        return this.b;
    }

    public float getSmallestDisplacement() {
        return this.h;
    }

    public int getVersionCode() {
        return this.a;
    }

    /*
     * Enabled aggressive block sorting
     */
    public LocationRequest setExpirationDuration(long l2) {
        long l3 = SystemClock.elapsedRealtime();
        this.f = l2 > Long.MAX_VALUE - l3 ? Long.MAX_VALUE : l3 + l2;
        if (this.f < 0L) {
            this.f = 0L;
        }
        return this;
    }

    public LocationRequest setExpirationTime(long l2) {
        this.f = l2;
        if (this.f < 0L) {
            this.f = 0L;
        }
        return this;
    }

    public LocationRequest setFastestInterval(long l2) {
        if (l2 < 0L) {
            throw new IllegalArgumentException("invalid interval: " + l2);
        }
        this.e = true;
        this.d = l2;
        return this;
    }

    public LocationRequest setInterval(long l2) {
        if (l2 < 0L) {
            throw new IllegalArgumentException("invalid interval: " + l2);
        }
        this.c = l2;
        if (!this.e) {
            this.d = (int)((double)this.c / 6.0);
        }
        return this;
    }

    public LocationRequest setNumUpdates(int n2) {
        if (n2 <= 0) {
            throw new IllegalArgumentException("invalid numUpdates: " + n2);
        }
        this.g = n2;
        return this;
    }

    public LocationRequest setPriority(int n2) {
        this.b = n2;
        return this;
    }

    public LocationRequest setSmallestDisplacement(float f2) {
        LocationRequest.a(f2);
        this.h = f2;
        return this;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Request[").append(this.b);
        if (this.b != 105) {
            stringBuilder.append(" requested=");
            stringBuilder.append(this.c + "ms");
        }
        stringBuilder.append(" fastest=");
        stringBuilder.append(this.d + "ms");
        if (this.f != Long.MAX_VALUE) {
            long l2 = this.f;
            long l3 = SystemClock.elapsedRealtime();
            stringBuilder.append(" expireIn=");
            stringBuilder.append(l2 - l3 + "ms");
        }
        if (this.g != Integer.MAX_VALUE) {
            stringBuilder.append(" num=").append(this.g);
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
    }
}

