/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.res.Resources
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.TintResources;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

class TintContextWrapper
extends ContextWrapper {
    private static final ArrayList<WeakReference<TintContextWrapper>> sCache = new ArrayList();
    private Resources mResources;

    private TintContextWrapper(Context context) {
        super(context);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static Context wrap(@NonNull Context object) {
        if (object instanceof TintContextWrapper) {
            return object;
        }
        int n2 = 0;
        int n3 = sCache.size();
        while (true) {
            void var3_4;
            if (n2 >= n3) {
                TintContextWrapper tintContextWrapper = new TintContextWrapper((Context)object);
                sCache.add(new WeakReference<TintContextWrapper>(tintContextWrapper));
                return tintContextWrapper;
            }
            WeakReference<TintContextWrapper> weakReference = sCache.get(n2);
            if (weakReference != null) {
                TintContextWrapper tintContextWrapper = (TintContextWrapper)((Object)weakReference.get());
            } else {
                Object var3_7 = null;
            }
            if (var3_4 != null && var3_4.getBaseContext() == object) {
                return var3_4;
            }
            ++n2;
        }
    }

    public Resources getResources() {
        if (this.mResources == null) {
            this.mResources = new TintResources((Context)this, super.getResources());
        }
        return this.mResources;
    }
}

