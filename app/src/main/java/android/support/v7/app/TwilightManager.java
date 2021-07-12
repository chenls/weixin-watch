/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.location.Location
 *  android.location.LocationManager
 *  android.util.Log
 */
package android.support.v7.app;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.TwilightCalculator;
import android.util.Log;
import java.util.Calendar;

class TwilightManager {
    private static final int SUNRISE = 6;
    private static final int SUNSET = 22;
    private static final String TAG = "TwilightManager";
    private static final TwilightState sTwilightState = new TwilightState();
    private final Context mContext;
    private final LocationManager mLocationManager;

    TwilightManager(Context context) {
        this.mContext = context;
        this.mLocationManager = (LocationManager)context.getSystemService("location");
    }

    /*
     * Enabled aggressive block sorting
     */
    private Location getLastKnownLocation() {
        Location location = null;
        Location location2 = null;
        if (PermissionChecker.checkSelfPermission(this.mContext, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            location = this.getLastKnownLocationForProvider("network");
        }
        if (PermissionChecker.checkSelfPermission(this.mContext, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            location2 = this.getLastKnownLocationForProvider("gps");
        }
        if (location2 != null && location != null) {
            if (location2.getTime() > location.getTime()) return location2;
            return location;
        }
        if (location2 == null) return location;
        return location2;
    }

    private Location getLastKnownLocationForProvider(String string2) {
        if (this.mLocationManager != null) {
            try {
                if (this.mLocationManager.isProviderEnabled(string2)) {
                    string2 = this.mLocationManager.getLastKnownLocation(string2);
                    return string2;
                }
            }
            catch (Exception exception) {
                Log.d((String)TAG, (String)"Failed to get last known location", (Throwable)exception);
            }
        }
        return null;
    }

    private boolean isStateValid(TwilightState twilightState) {
        return twilightState != null && twilightState.nextUpdate > System.currentTimeMillis();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateState(@NonNull Location location) {
        TwilightState twilightState = sTwilightState;
        long l2 = System.currentTimeMillis();
        TwilightCalculator twilightCalculator = TwilightCalculator.getInstance();
        twilightCalculator.calculateTwilight(l2 - 86400000L, location.getLatitude(), location.getLongitude());
        long l3 = twilightCalculator.sunset;
        twilightCalculator.calculateTwilight(l2, location.getLatitude(), location.getLongitude());
        boolean bl2 = twilightCalculator.state == 1;
        long l4 = twilightCalculator.sunrise;
        long l5 = twilightCalculator.sunset;
        twilightCalculator.calculateTwilight(86400000L + l2, location.getLatitude(), location.getLongitude());
        long l6 = twilightCalculator.sunrise;
        if (l4 == -1L || l5 == -1L) {
            l2 += 43200000L;
        } else {
            l2 = l2 > l5 ? 0L + l6 : (l2 > l4 ? 0L + l5 : 0L + l4);
            l2 += 60000L;
        }
        twilightState.isNight = bl2;
        twilightState.yesterdaySunset = l3;
        twilightState.todaySunrise = l4;
        twilightState.todaySunset = l5;
        twilightState.tomorrowSunrise = l6;
        twilightState.nextUpdate = l2;
    }

    boolean isNight() {
        TwilightState twilightState = sTwilightState;
        if (this.isStateValid(twilightState)) {
            return twilightState.isNight;
        }
        Location location = this.getLastKnownLocation();
        if (location != null) {
            this.updateState(location);
            return twilightState.isNight;
        }
        Log.i((String)TAG, (String)"Could not get last known location. This is probably because the app does not have any location permissions. Falling back to hardcoded sunrise/sunset values.");
        int n2 = Calendar.getInstance().get(11);
        return n2 < 6 || n2 >= 22;
    }

    private static class TwilightState {
        boolean isNight;
        long nextUpdate;
        long todaySunrise;
        long todaySunset;
        long tomorrowSunrise;
        long yesterdaySunset;

        private TwilightState() {
        }
    }
}

