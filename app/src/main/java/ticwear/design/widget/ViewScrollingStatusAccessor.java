/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  android.view.View
 */
package ticwear.design.widget;

import android.util.Log;
import android.view.View;
import java.lang.reflect.Method;
import ticwear.design.DesignConfig;

public class ViewScrollingStatusAccessor {
    private static final String CLASS_NAME_VIEW = "android.view.View";
    private static final int COMPUTE_METHOD_COUNT = 6;
    private static final int INDEX_HORIZONTAL_EXTENT = 5;
    private static final int INDEX_HORIZONTAL_OFFSET = 4;
    private static final int INDEX_HORIZONTAL_RANGE = 3;
    private static final int INDEX_VERTICAL_EXTENT = 2;
    private static final int INDEX_VERTICAL_OFFSET = 1;
    private static final int INDEX_VERTICAL_RANGE = 0;
    private static final String[] METHOD_NAMES = new String[]{"computeVerticalScrollRange", "computeVerticalScrollOffset", "computeVerticalScrollExtent", "computeHorizontalScrollRange", "computeHorizontalScrollOffset", "computeHorizontalScrollExtent"};
    static final String TAG = "ViewScrollSA";
    private final Method[] mComputeScrollMethods = new Method[6];
    private View mFailedAccessView;
    private View mScrollingView;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean ensureMethods() {
        block10: {
            block9: {
                if (this.mScrollingView == null) break block9;
                if (!this.hasEmptyMethod()) {
                    return true;
                }
                if (this.mScrollingView != this.mFailedAccessView) break block10;
            }
            return false;
        }
        try {
            Class<?> clazz = Class.forName(CLASS_NAME_VIEW);
            int n2 = 0;
            while (true) {
                if (n2 >= this.mComputeScrollMethods.length) {
                    return true;
                }
                this.mComputeScrollMethods[n2] = clazz.getDeclaredMethod(METHOD_NAMES[n2], new Class[0]);
                this.mComputeScrollMethods[n2].setAccessible(true);
                ++n2;
            }
        }
        catch (Exception exception) {
            if (DesignConfig.DEBUG) {
                Log.w((String)TAG, (String)"Failed to access methods for view", (Throwable)exception);
            }
            this.mFailedAccessView = this.mScrollingView;
            return false;
        }
    }

    private boolean hasEmptyMethod() {
        boolean bl2 = false;
        Method[] methodArray = this.mComputeScrollMethods;
        int n2 = methodArray.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            if (methodArray[i2] != null) continue;
            bl2 = true;
        }
        return bl2;
    }

    private int invoke(int n2, int n3) {
        Method method = this.mComputeScrollMethods[n2];
        if (method == null) {
            return n3;
        }
        try {
            n2 = (Integer)method.invoke(this.mScrollingView, new Object[0]);
            return n2;
        }
        catch (Throwable throwable) {
            return n3;
        }
    }

    public void attach(View view) {
        if (view == this.mScrollingView) {
            return;
        }
        this.mScrollingView = view;
        this.mFailedAccessView = null;
        this.ensureMethods();
    }

    public int computeHorizontalScrollExtent() {
        this.ensureMethods();
        return this.invoke(5, this.mScrollingView.getWidth());
    }

    public int computeHorizontalScrollOffset() {
        this.ensureMethods();
        return this.invoke(4, this.mScrollingView.getScrollX());
    }

    public int computeHorizontalScrollRange() {
        this.ensureMethods();
        return this.invoke(3, this.mScrollingView.getWidth());
    }

    public int computeVerticalScrollExtent() {
        this.ensureMethods();
        return this.invoke(2, this.mScrollingView.getHeight());
    }

    public int computeVerticalScrollOffset() {
        this.ensureMethods();
        return this.invoke(1, this.mScrollingView.getScrollY());
    }

    public int computeVerticalScrollRange() {
        this.ensureMethods();
        return this.invoke(0, this.mScrollingView.getHeight());
    }

    public boolean isValid() {
        return this.ensureMethods();
    }
}

