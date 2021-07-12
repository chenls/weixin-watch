/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.util.Log
 *  android.view.ActionMode
 *  android.view.ActionMode$Callback
 *  android.view.Window
 *  android.view.Window$Callback
 */
package android.support.v7.app;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegateImplBase;
import android.support.v7.app.AppCompatDelegateImplV11;
import android.support.v7.app.TwilightManager;
import android.support.v7.view.ActionMode;
import android.support.v7.view.SupportActionModeWrapper;
import android.util.Log;
import android.view.ActionMode;
import android.view.Window;

class AppCompatDelegateImplV14
extends AppCompatDelegateImplV11 {
    private static final String KEY_LOCAL_NIGHT_MODE = "appcompat:local_night_mode";
    private static TwilightManager sTwilightManager;
    private boolean mApplyDayNightCalled;
    private boolean mHandleNativeActionModes = true;
    private int mLocalNightMode = -100;

    AppCompatDelegateImplV14(Context context, Window window, AppCompatCallback appCompatCallback) {
        super(context, window, appCompatCallback);
    }

    private TwilightManager getTwilightManager() {
        if (sTwilightManager == null) {
            sTwilightManager = new TwilightManager(this.mContext.getApplicationContext());
        }
        return sTwilightManager;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean updateConfigurationForNightMode(int n2) {
        Resources resources = this.mContext.getResources();
        Configuration configuration = resources.getConfiguration();
        int n3 = configuration.uiMode;
        if ((n3 & 0x30) != (n2 = n2 == 2 ? 32 : 16)) {
            configuration.uiMode = configuration.uiMode & 0xFFFFFFCF | n2;
            resources.updateConfiguration(configuration, null);
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean applyDayNight() {
        this.mApplyDayNightCalled = true;
        int n2 = this.mLocalNightMode == -100 ? AppCompatDelegateImplV14.getDefaultNightMode() : this.mLocalNightMode;
        if ((n2 = this.mapNightMode(n2)) != -1) {
            return this.updateConfigurationForNightMode(n2);
        }
        return false;
    }

    @Override
    public boolean isHandleNativeActionModesEnabled() {
        return this.mHandleNativeActionModes;
    }

    int mapNightMode(int n2) {
        switch (n2) {
            default: {
                return n2;
            }
            case 0: {
                if (this.getTwilightManager().isNight()) {
                    return 2;
                }
                return 1;
            }
            case -100: 
        }
        return -1;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null && this.mLocalNightMode == -100) {
            this.mLocalNightMode = bundle.getInt(KEY_LOCAL_NIGHT_MODE, -100);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.mLocalNightMode != -100) {
            bundle.putInt(KEY_LOCAL_NIGHT_MODE, this.mLocalNightMode);
        }
    }

    @Override
    public void setHandleNativeActionModesEnabled(boolean bl2) {
        this.mHandleNativeActionModes = bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setLocalNightMode(int n2) {
        switch (n2) {
            default: {
                Log.d((String)"AppCompatDelegate", (String)"setLocalNightMode() called with an unknown mode");
                return;
            }
            case -1: 
            case 0: 
            case 1: 
            case 2: {
                if (this.mLocalNightMode == n2) return;
                this.mLocalNightMode = n2;
                if (!this.mApplyDayNightCalled) return;
                this.applyDayNight();
                return;
            }
        }
    }

    @Override
    Window.Callback wrapWindowCallback(Window.Callback callback) {
        return new AppCompatWindowCallbackV14(callback);
    }

    class AppCompatWindowCallbackV14
    extends AppCompatDelegateImplBase.AppCompatWindowCallbackBase {
        AppCompatWindowCallbackV14(Window.Callback callback) {
            super(callback);
        }

        @Override
        public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
            if (AppCompatDelegateImplV14.this.isHandleNativeActionModesEnabled()) {
                return this.startAsSupportActionMode(callback);
            }
            return super.onWindowStartingActionMode(callback);
        }

        final ActionMode startAsSupportActionMode(ActionMode.Callback object) {
            android.support.v7.view.ActionMode actionMode = AppCompatDelegateImplV14.this.startSupportActionMode((ActionMode.Callback)(object = new SupportActionModeWrapper.CallbackWrapper(AppCompatDelegateImplV14.this.mContext, (ActionMode.Callback)object)));
            if (actionMode != null) {
                return ((SupportActionModeWrapper.CallbackWrapper)object).getActionModeWrapper(actionMode);
            }
            return null;
        }
    }
}

