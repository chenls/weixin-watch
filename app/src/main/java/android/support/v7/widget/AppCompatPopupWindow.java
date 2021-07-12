/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewTreeObserver$OnScrollChangedListener
 *  android.widget.PopupWindow
 */
package android.support.v7.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.appcompat.R;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.PopupWindow;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

public class AppCompatPopupWindow
extends PopupWindow {
    private static final boolean COMPAT_OVERLAP_ANCHOR;
    private static final String TAG = "AppCompatPopupWindow";
    private boolean mOverlapAnchor;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl2 = Build.VERSION.SDK_INT < 21;
        COMPAT_OVERLAP_ANCHOR = bl2;
    }

    public AppCompatPopupWindow(Context object, AttributeSet attributeSet, int n2) {
        super((Context)object, attributeSet, n2);
        object = TintTypedArray.obtainStyledAttributes((Context)object, attributeSet, R.styleable.PopupWindow, n2, 0);
        if (((TintTypedArray)object).hasValue(R.styleable.PopupWindow_overlapAnchor)) {
            this.setSupportOverlapAnchor(((TintTypedArray)object).getBoolean(R.styleable.PopupWindow_overlapAnchor, false));
        }
        this.setBackgroundDrawable(((TintTypedArray)object).getDrawable(R.styleable.PopupWindow_android_popupBackground));
        ((TintTypedArray)object).recycle();
        if (Build.VERSION.SDK_INT < 14) {
            AppCompatPopupWindow.wrapOnScrollChangedListener(this);
        }
    }

    private static void wrapOnScrollChangedListener(final PopupWindow popupWindow) {
        try {
            final Field field = PopupWindow.class.getDeclaredField("mAnchor");
            field.setAccessible(true);
            Field field2 = PopupWindow.class.getDeclaredField("mOnScrollChangedListener");
            field2.setAccessible(true);
            field2.set(popupWindow, new ViewTreeObserver.OnScrollChangedListener((ViewTreeObserver.OnScrollChangedListener)field2.get(popupWindow)){
                final /* synthetic */ ViewTreeObserver.OnScrollChangedListener val$originalListener;
                {
                    this.val$originalListener = onScrollChangedListener;
                }

                public void onScrollChanged() {
                    block4: {
                        WeakReference weakReference = (WeakReference)field.get(popupWindow);
                        if (weakReference == null) break block4;
                        try {
                            if (weakReference.get() == null) {
                                return;
                            }
                            this.val$originalListener.onScrollChanged();
                            return;
                        }
                        catch (IllegalAccessException illegalAccessException) {
                            // empty catch block
                        }
                    }
                }
            });
            return;
        }
        catch (Exception exception) {
            Log.d((String)TAG, (String)"Exception while installing workaround OnScrollChangedListener", (Throwable)exception);
            return;
        }
    }

    public boolean getSupportOverlapAnchor() {
        if (COMPAT_OVERLAP_ANCHOR) {
            return this.mOverlapAnchor;
        }
        return PopupWindowCompat.getOverlapAnchor(this);
    }

    public void setSupportOverlapAnchor(boolean bl2) {
        if (COMPAT_OVERLAP_ANCHOR) {
            this.mOverlapAnchor = bl2;
            return;
        }
        PopupWindowCompat.setOverlapAnchor(this, bl2);
    }

    public void showAsDropDown(View view, int n2, int n3) {
        int n4 = n3;
        if (COMPAT_OVERLAP_ANCHOR) {
            n4 = n3;
            if (this.mOverlapAnchor) {
                n4 = n3 - view.getHeight();
            }
        }
        super.showAsDropDown(view, n2, n4);
    }

    @TargetApi(value=19)
    public void showAsDropDown(View view, int n2, int n3, int n4) {
        int n5 = n3;
        if (COMPAT_OVERLAP_ANCHOR) {
            n5 = n3;
            if (this.mOverlapAnchor) {
                n5 = n3 - view.getHeight();
            }
        }
        super.showAsDropDown(view, n2, n5, n4);
    }

    public void update(View view, int n2, int n3, int n4, int n5) {
        int n6 = n3;
        if (COMPAT_OVERLAP_ANCHOR) {
            n6 = n3;
            if (this.mOverlapAnchor) {
                n6 = n3 - view.getHeight();
            }
        }
        super.update(view, n2, n6, n4, n5);
    }
}

