/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.app.Service
 *  android.content.ComponentName
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package android.support.wearable.watchface;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@TargetApi(value=21)
public class WatchFaceStyle
implements Parcelable {
    public static final int AMBIENT_PEEK_MODE_HIDDEN = 1;
    public static final int AMBIENT_PEEK_MODE_VISIBLE = 0;
    public static final int BACKGROUND_VISIBILITY_INTERRUPTIVE = 0;
    public static final int BACKGROUND_VISIBILITY_PERSISTENT = 1;
    public static final Parcelable.Creator<WatchFaceStyle> CREATOR = new Parcelable.Creator<WatchFaceStyle>(){

        public WatchFaceStyle createFromParcel(Parcel parcel) {
            return new WatchFaceStyle(parcel.readBundle());
        }

        public WatchFaceStyle[] newArray(int n2) {
            return new WatchFaceStyle[n2];
        }
    };
    public static final String KEY_ACCEPTS_TAPS = "acceptsTapEvents";
    public static final String KEY_AMBIENT_PEEK_MODE = "ambientPeekMode";
    public static final String KEY_BACKGROUND_VISIBILITY = "backgroundVisibility";
    public static final String KEY_CARD_PEEK_MODE = "cardPeekMode";
    public static final String KEY_CARD_PROGRESS_MODE = "cardProgressMode";
    public static final String KEY_COMPONENT = "component";
    public static final String KEY_HIDE_HOTWORD_INDICATOR = "hideHotwordIndicator";
    public static final String KEY_HIDE_STATUS_BAR = "hideStatusBar";
    public static final String KEY_HOTWORD_INDICATOR_GRAVITY = "hotwordIndicatorGravity";
    public static final String KEY_PEEK_CARD_OPACITY = "peekOpacityMode";
    public static final String KEY_SHOW_SYSTEM_UI_TIME = "showSystemUiTime";
    public static final String KEY_SHOW_UNREAD_INDICATOR = "showUnreadIndicator";
    public static final String KEY_STATUS_BAR_GRAVITY = "statusBarGravity";
    public static final String KEY_VIEW_PROTECTION_MODE = "viewProtectionMode";
    public static final int PEEK_MODE_NONE = 2;
    public static final int PEEK_MODE_SHORT = 1;
    public static final int PEEK_MODE_VARIABLE = 0;
    public static final int PEEK_OPACITY_MODE_OPAQUE = 0;
    public static final int PEEK_OPACITY_MODE_TRANSLUCENT = 1;
    public static final int PROGRESS_MODE_DISPLAY = 1;
    public static final int PROGRESS_MODE_NONE = 0;
    public static final int PROTECT_HOTWORD_INDICATOR = 2;
    public static final int PROTECT_STATUS_BAR = 1;
    public static final int PROTECT_WHOLE_SCREEN = 4;
    private final boolean acceptsTapEvents;
    private final int ambientPeekMode;
    private final int backgroundVisibility;
    private final int cardPeekMode;
    private final int cardProgressMode;
    private final ComponentName component;
    private final boolean hideHotwordIndicator;
    private final boolean hideStatusBar;
    private final int hotwordIndicatorGravity;
    private final int peekOpacityMode;
    private final boolean showSystemUiTime;
    private final boolean showUnreadCountIndicator;
    private final int statusBarGravity;
    private final int viewProtectionMode;

    private WatchFaceStyle(ComponentName componentName, int n2, int n3, int n4, boolean bl2, int n5, int n6, int n7, int n8, int n9, boolean bl3, boolean bl4, boolean bl5, boolean bl6) {
        this.component = componentName;
        this.ambientPeekMode = n5;
        this.backgroundVisibility = n4;
        this.cardPeekMode = n2;
        this.cardProgressMode = n3;
        this.hotwordIndicatorGravity = n9;
        this.peekOpacityMode = n6;
        this.showSystemUiTime = bl2;
        this.showUnreadCountIndicator = bl3;
        this.statusBarGravity = n8;
        this.viewProtectionMode = n7;
        this.acceptsTapEvents = bl4;
        this.hideHotwordIndicator = bl5;
        this.hideStatusBar = bl6;
    }

    public WatchFaceStyle(Bundle bundle) {
        this.component = (ComponentName)bundle.getParcelable(KEY_COMPONENT);
        this.ambientPeekMode = bundle.getInt(KEY_AMBIENT_PEEK_MODE, 0);
        this.backgroundVisibility = bundle.getInt(KEY_BACKGROUND_VISIBILITY, 0);
        this.cardPeekMode = bundle.getInt(KEY_CARD_PEEK_MODE, 0);
        this.cardProgressMode = bundle.getInt(KEY_CARD_PROGRESS_MODE, 0);
        this.hotwordIndicatorGravity = bundle.getInt(KEY_HOTWORD_INDICATOR_GRAVITY);
        this.peekOpacityMode = bundle.getInt(KEY_PEEK_CARD_OPACITY, 0);
        this.showSystemUiTime = bundle.getBoolean(KEY_SHOW_SYSTEM_UI_TIME);
        this.showUnreadCountIndicator = bundle.getBoolean(KEY_SHOW_UNREAD_INDICATOR);
        this.statusBarGravity = bundle.getInt(KEY_STATUS_BAR_GRAVITY);
        this.viewProtectionMode = bundle.getInt(KEY_VIEW_PROTECTION_MODE);
        this.acceptsTapEvents = bundle.getBoolean(KEY_ACCEPTS_TAPS);
        this.hideHotwordIndicator = bundle.getBoolean(KEY_HIDE_HOTWORD_INDICATOR);
        this.hideStatusBar = bundle.getBoolean(KEY_HIDE_STATUS_BAR);
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block3: {
            block2: {
                if (object == null || !(object instanceof WatchFaceStyle)) break block2;
                object = (WatchFaceStyle)object;
                if (this.component.equals((Object)((WatchFaceStyle)object).component) && this.cardPeekMode == ((WatchFaceStyle)object).cardPeekMode && this.cardProgressMode == ((WatchFaceStyle)object).cardProgressMode && this.backgroundVisibility == ((WatchFaceStyle)object).backgroundVisibility && this.showSystemUiTime == ((WatchFaceStyle)object).showSystemUiTime && this.ambientPeekMode == ((WatchFaceStyle)object).ambientPeekMode && this.peekOpacityMode == ((WatchFaceStyle)object).peekOpacityMode && this.viewProtectionMode == ((WatchFaceStyle)object).viewProtectionMode && this.statusBarGravity == ((WatchFaceStyle)object).statusBarGravity && this.hotwordIndicatorGravity == ((WatchFaceStyle)object).hotwordIndicatorGravity && this.showUnreadCountIndicator == ((WatchFaceStyle)object).showUnreadCountIndicator && this.acceptsTapEvents == ((WatchFaceStyle)object).acceptsTapEvents && this.hideHotwordIndicator == ((WatchFaceStyle)object).hideHotwordIndicator && this.hideStatusBar == ((WatchFaceStyle)object).hideStatusBar) break block3;
            }
            return false;
        }
        return true;
    }

    public boolean getAcceptsTapEvents() {
        return this.acceptsTapEvents;
    }

    public int getAmbientPeekMode() {
        return this.ambientPeekMode;
    }

    public int getBackgroundVisibility() {
        return this.backgroundVisibility;
    }

    public int getCardPeekMode() {
        return this.cardPeekMode;
    }

    public int getCardProgressMode() {
        return this.cardProgressMode;
    }

    public ComponentName getComponent() {
        return this.component;
    }

    public boolean getHideHotwordIndicator() {
        return this.hideHotwordIndicator;
    }

    public boolean getHideStatusBar() {
        return this.hideStatusBar;
    }

    public int getHotwordIndicatorGravity() {
        return this.hotwordIndicatorGravity;
    }

    public int getPeekOpacityMode() {
        return this.peekOpacityMode;
    }

    public boolean getShowSystemUiTime() {
        return this.showSystemUiTime;
    }

    public boolean getShowUnreadCountIndicator() {
        return this.showUnreadCountIndicator;
    }

    public int getStatusBarGravity() {
        return this.statusBarGravity;
    }

    public int getViewProtectionMode() {
        return this.viewProtectionMode;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int n2 = 1;
        int n3 = this.component.hashCode();
        int n4 = this.cardPeekMode;
        int n5 = this.cardProgressMode;
        int n6 = this.backgroundVisibility;
        int n7 = this.showSystemUiTime ? 1 : 0;
        int n8 = this.ambientPeekMode;
        int n9 = this.peekOpacityMode;
        int n10 = this.viewProtectionMode;
        int n11 = this.statusBarGravity;
        int n12 = this.hotwordIndicatorGravity;
        int n13 = this.showUnreadCountIndicator ? 1 : 0;
        int n14 = this.acceptsTapEvents ? 1 : 0;
        int n15 = this.hideHotwordIndicator ? 1 : 0;
        if (this.hideStatusBar) {
            return (((((((((((((n3 + 31) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9) * 31 + n10) * 31 + n11) * 31 + n12) * 31 + n13) * 31 + n14) * 31 + n15) * 31 + n2;
        }
        n2 = 0;
        return (((((((((((((n3 + 31) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9) * 31 + n10) * 31 + n11) * 31 + n12) * 31 + n13) * 31 + n14) * 31 + n15) * 31 + n2;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_COMPONENT, (Parcelable)this.component);
        bundle.putInt(KEY_AMBIENT_PEEK_MODE, this.ambientPeekMode);
        bundle.putInt(KEY_BACKGROUND_VISIBILITY, this.backgroundVisibility);
        bundle.putInt(KEY_CARD_PEEK_MODE, this.cardPeekMode);
        bundle.putInt(KEY_CARD_PROGRESS_MODE, this.cardProgressMode);
        bundle.putInt(KEY_HOTWORD_INDICATOR_GRAVITY, this.hotwordIndicatorGravity);
        bundle.putInt(KEY_PEEK_CARD_OPACITY, this.peekOpacityMode);
        bundle.putBoolean(KEY_SHOW_SYSTEM_UI_TIME, this.showSystemUiTime);
        bundle.putBoolean(KEY_SHOW_UNREAD_INDICATOR, this.showUnreadCountIndicator);
        bundle.putInt(KEY_STATUS_BAR_GRAVITY, this.statusBarGravity);
        bundle.putInt(KEY_VIEW_PROTECTION_MODE, this.viewProtectionMode);
        bundle.putBoolean(KEY_ACCEPTS_TAPS, this.acceptsTapEvents);
        bundle.putBoolean(KEY_HIDE_HOTWORD_INDICATOR, this.hideHotwordIndicator);
        bundle.putBoolean(KEY_HIDE_STATUS_BAR, this.hideStatusBar);
        return bundle;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String toString() {
        String string2;
        if (this.component == null) {
            string2 = "default";
            return String.format("watch face %s (card %d/%d bg %d time %s ambientPeek %d peekOpacityMode %d viewProtectionMode %d statusBarGravity %d hotwordIndicatorGravity %d showUnreadCountIndicator %s acceptsTapEvents %s hideHotwordIndicator %s hideStatusBar %s)", string2, this.cardPeekMode, this.cardProgressMode, this.backgroundVisibility, this.showSystemUiTime, this.ambientPeekMode, this.peekOpacityMode, this.viewProtectionMode, this.statusBarGravity, this.hotwordIndicatorGravity, this.showUnreadCountIndicator, this.acceptsTapEvents, this.hideHotwordIndicator, this.hideStatusBar);
        }
        string2 = this.component.getShortClassName();
        return String.format("watch face %s (card %d/%d bg %d time %s ambientPeek %d peekOpacityMode %d viewProtectionMode %d statusBarGravity %d hotwordIndicatorGravity %d showUnreadCountIndicator %s acceptsTapEvents %s hideHotwordIndicator %s hideStatusBar %s)", string2, this.cardPeekMode, this.cardProgressMode, this.backgroundVisibility, this.showSystemUiTime, this.ambientPeekMode, this.peekOpacityMode, this.viewProtectionMode, this.statusBarGravity, this.hotwordIndicatorGravity, this.showUnreadCountIndicator, this.acceptsTapEvents, this.hideHotwordIndicator, this.hideStatusBar);
    }

    public void writeToParcel(Parcel parcel, int n2) {
        parcel.writeBundle(this.toBundle());
    }

    public static class Builder {
        private boolean mAcceptsTapEvents = false;
        private int mAmbientPeekMode = 0;
        private int mBackgroundVisibility = 0;
        private int mCardPeekMode = 0;
        private int mCardProgressMode = 0;
        private final ComponentName mComponent;
        private boolean mHideHotwordIndicator = false;
        private boolean mHideStatusBar = false;
        private int mHotwordIndicatorGravity = 0;
        private int mPeekOpacityMode = 0;
        private boolean mShowSystemUiTime = false;
        private boolean mShowUnreadCountIndicator = false;
        private int mStatusBarGravity = 0;
        private int mViewProtectionMode = 0;

        public Builder(Service service) {
            this(new ComponentName((Context)service, service.getClass()));
        }

        private Builder(ComponentName componentName) {
            this.mComponent = componentName;
        }

        public static Builder forActivity(Activity activity) {
            if (activity == null) {
                throw new IllegalArgumentException("activity must not be null.");
            }
            return new Builder(new ComponentName((Context)activity, activity.getClass()));
        }

        public static Builder forComponentName(ComponentName componentName) {
            if (componentName == null) {
                throw new IllegalArgumentException("component must not be null.");
            }
            return new Builder(componentName);
        }

        public static Builder forDefault() {
            return new Builder((ComponentName)null);
        }

        public WatchFaceStyle build() {
            return new WatchFaceStyle(this.mComponent, this.mCardPeekMode, this.mCardProgressMode, this.mBackgroundVisibility, this.mShowSystemUiTime, this.mAmbientPeekMode, this.mPeekOpacityMode, this.mViewProtectionMode, this.mStatusBarGravity, this.mHotwordIndicatorGravity, this.mShowUnreadCountIndicator, this.mAcceptsTapEvents, this.mHideHotwordIndicator, this.mHideStatusBar);
        }

        public Builder setAcceptsTapEvents(boolean bl2) {
            this.mAcceptsTapEvents = bl2;
            return this;
        }

        public Builder setAmbientPeekMode(int n2) {
            switch (n2) {
                default: {
                    throw new IllegalArgumentException("Ambient peek mode must be AMBIENT_PEEK_MODE_VISIBLE or AMBIENT_PEEK_MODE_HIDDEN");
                }
                case 0: 
                case 1: 
            }
            this.mAmbientPeekMode = n2;
            return this;
        }

        public Builder setBackgroundVisibility(int n2) {
            switch (n2) {
                default: {
                    throw new IllegalArgumentException("backgroundVisibility must be BACKGROUND_VISIBILITY_INTERRUPTIVE or BACKGROUND_VISIBILITY_PERSISTENT");
                }
                case 0: 
                case 1: 
            }
            this.mBackgroundVisibility = n2;
            return this;
        }

        public Builder setCardPeekMode(int n2) {
            switch (n2) {
                default: {
                    throw new IllegalArgumentException("peekMode must be PEEK_MODE_VARIABLE or PEEK_MODE_SHORT");
                }
                case 0: 
                case 1: 
                case 2: 
            }
            this.mCardPeekMode = n2;
            return this;
        }

        public Builder setCardProgressMode(int n2) {
            switch (n2) {
                default: {
                    throw new IllegalArgumentException("progressMode must be PROGRESS_MODE_NONE or PROGRESS_MODE_DISPLAY");
                }
                case 0: 
                case 1: 
            }
            this.mCardProgressMode = n2;
            return this;
        }

        public Builder setHideHotwordIndicator(boolean bl2) {
            this.mHideHotwordIndicator = bl2;
            return this;
        }

        public Builder setHideStatusBar(boolean bl2) {
            this.mHideStatusBar = bl2;
            return this;
        }

        public Builder setHotwordIndicatorGravity(int n2) {
            this.mHotwordIndicatorGravity = n2;
            return this;
        }

        public Builder setPeekOpacityMode(int n2) {
            switch (n2) {
                default: {
                    throw new IllegalArgumentException("Peek card opacity must be PEEK_OPACITY_MODE_OPAQUE or PEEK_OPACITY_MODE_TRANSLUCENT");
                }
                case 0: 
                case 1: 
            }
            this.mPeekOpacityMode = n2;
            return this;
        }

        public Builder setShowSystemUiTime(boolean bl2) {
            this.mShowSystemUiTime = bl2;
            return this;
        }

        public Builder setShowUnreadCountIndicator(boolean bl2) {
            this.mShowUnreadCountIndicator = bl2;
            return this;
        }

        public Builder setStatusBarGravity(int n2) {
            this.mStatusBarGravity = n2;
            return this;
        }

        @Deprecated
        public Builder setViewProtection(int n2) {
            return this.setViewProtectionMode(n2);
        }

        public Builder setViewProtectionMode(int n2) {
            if (n2 < 0 || n2 > 7) {
                throw new IllegalArgumentException("View protection must be combination PROTECT_STATUS_BAR, PROTECT_HOTWORD_INDICATOR or PROTECT_WHOLE_SCREEN");
            }
            this.mViewProtectionMode = n2;
            return this;
        }
    }
}

