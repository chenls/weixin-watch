/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  com.google.android.wearable.compat.WearableActivityController
 *  com.google.android.wearable.compat.WearableActivityController$AmbientCallback
 */
package android.support.wearable.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import com.google.android.wearable.compat.WearableActivityController;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Method;

@TargetApi(value=21)
public abstract class WearableActivity
extends Activity {
    public static final String EXTRA_BURN_IN_PROTECTION = "com.google.android.wearable.compat.extra.BURN_IN_PROTECTION";
    public static final String EXTRA_LOWBIT_AMBIENT = "com.google.android.wearable.compat.extra.LOWBIT_AMBIENT";
    private static final String WEARABLE_CONTROLLER_CLASS_NAME = "com.google.android.wearable.compat.WearableActivityController";
    private static volatile boolean sAmbientCallbacksVerifiedPresent;
    private final String TAG = WearableActivity.class.getSimpleName() + "[" + ((Object)((Object)this)).getClass().getSimpleName() + "]";
    private boolean mSuperCalled;
    private WearableActivityController mWearableController;

    static /* synthetic */ boolean access$102(WearableActivity wearableActivity, boolean bl2) {
        wearableActivity.mSuperCalled = bl2;
        return bl2;
    }

    private void initAmbientSupport() {
        if (Build.VERSION.SDK_INT <= 21) {
            return;
        }
        try {
            Class.forName(WEARABLE_CONTROLLER_CLASS_NAME);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new IllegalStateException("Could not find wearable shared library classes. Please add <uses-library android:name=\"com.google.android.wearable\" android:required=\"false\" /> to the application manifest");
        }
        this.mWearableController = new WearableActivityController(this.TAG, (Activity)this, (WearableActivityController.AmbientCallback)new AmbientCallback());
        WearableActivity.verifyAmbientCallbacksPresent();
    }

    private static void verifyAmbientCallbacksPresent() {
        if (sAmbientCallbacksVerifiedPresent) {
            return;
        }
        try {
            Method method = AmbientCallback.class.getDeclaredMethod("onEnterAmbient", Bundle.class);
            if (!".onEnterAmbient".equals("." + method.getName())) {
                throw new NoSuchMethodException();
            }
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new IllegalStateException("Could not find a required method for ambient support, likely due to proguard optimization. Please add com.google.android.wearable:wearable jar to the list of library jars for your project");
        }
        sAmbientCallbacksVerifiedPresent = true;
    }

    public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] stringArray) {
        if (this.mWearableController != null) {
            this.mWearableController.dump(string2, fileDescriptor, printWriter, stringArray);
        }
    }

    public final boolean isAmbient() {
        if (this.mWearableController != null) {
            return this.mWearableController.isAmbient();
        }
        return false;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.initAmbientSupport();
        if (this.mWearableController != null) {
            this.mWearableController.onCreate();
        }
    }

    protected void onDestroy() {
        if (this.mWearableController != null) {
            this.mWearableController.onDestroy();
        }
        super.onDestroy();
    }

    @CallSuper
    public void onEnterAmbient(Bundle bundle) {
        this.mSuperCalled = true;
    }

    @CallSuper
    public void onExitAmbient() {
        this.mSuperCalled = true;
    }

    protected void onPause() {
        if (this.mWearableController != null) {
            this.mWearableController.onPause();
        }
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
        if (this.mWearableController != null) {
            this.mWearableController.onResume();
        }
    }

    protected void onStop() {
        if (this.mWearableController != null) {
            this.mWearableController.onStop();
        }
        super.onStop();
    }

    @CallSuper
    public void onUpdateAmbient() {
    }

    public final void setAmbientEnabled() {
        if (this.mWearableController != null) {
            this.mWearableController.setAmbientEnabled();
        }
    }

    private class AmbientCallback
    extends WearableActivityController.AmbientCallback {
        private AmbientCallback() {
        }

        public void onEnterAmbient(Bundle bundle) {
            WearableActivity.access$102(WearableActivity.this, false);
            WearableActivity.this.onEnterAmbient(bundle);
            if (!WearableActivity.this.mSuperCalled) {
                throw new IllegalStateException("Activity " + ((Object)((Object)WearableActivity.this)).toString() + " did not call through to super.onEnterAmbient()");
            }
        }

        public void onExitAmbient() {
            WearableActivity.access$102(WearableActivity.this, false);
            WearableActivity.this.onExitAmbient();
            if (!WearableActivity.this.mSuperCalled) {
                throw new IllegalStateException("Activity " + ((Object)((Object)WearableActivity.this)).toString() + " did not call through to super.onExitAmbient()");
            }
        }

        public void onUpdateAmbient() {
            WearableActivity.this.onUpdateAmbient();
        }
    }
}

