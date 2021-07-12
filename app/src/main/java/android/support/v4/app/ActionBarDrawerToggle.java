/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.graphics.drawable.InsetDrawable
 *  android.os.Build$VERSION
 *  android.view.MenuItem
 *  android.view.View
 */
package android.support.v4.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActionBarDrawerToggleHoneycomb;
import android.support.v4.app.ActionBarDrawerToggleJellybeanMR2;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;

@Deprecated
public class ActionBarDrawerToggle
implements DrawerLayout.DrawerListener {
    private static final int ID_HOME = 16908332;
    private static final ActionBarDrawerToggleImpl IMPL;
    private static final float TOGGLE_DRAWABLE_OFFSET = 0.33333334f;
    final Activity mActivity;
    private final Delegate mActivityImpl;
    private final int mCloseDrawerContentDescRes;
    private Drawable mDrawerImage;
    private final int mDrawerImageResource;
    private boolean mDrawerIndicatorEnabled;
    private final DrawerLayout mDrawerLayout;
    private boolean mHasCustomUpIndicator;
    private Drawable mHomeAsUpIndicator;
    private final int mOpenDrawerContentDescRes;
    private Object mSetIndicatorInfo;
    private SlideDrawable mSlider;

    static {
        int n2 = Build.VERSION.SDK_INT;
        IMPL = n2 >= 18 ? new ActionBarDrawerToggleImplJellybeanMR2() : (n2 >= 11 ? new ActionBarDrawerToggleImplHC() : new ActionBarDrawerToggleImplBase());
    }

    /*
     * Enabled aggressive block sorting
     */
    public ActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, @DrawableRes int n2, @StringRes int n3, @StringRes int n4) {
        boolean bl2 = !ActionBarDrawerToggle.assumeMaterial((Context)activity);
        this(activity, drawerLayout, bl2, n2, n3, n4);
    }

    /*
     * Enabled aggressive block sorting
     */
    public ActionBarDrawerToggle(Activity object, DrawerLayout drawerLayout, boolean bl2, @DrawableRes int n2, @StringRes int n3, @StringRes int n4) {
        this.mDrawerIndicatorEnabled = true;
        this.mActivity = object;
        this.mActivityImpl = object instanceof DelegateProvider ? ((DelegateProvider)object).getDrawerToggleDelegate() : null;
        this.mDrawerLayout = drawerLayout;
        this.mDrawerImageResource = n2;
        this.mOpenDrawerContentDescRes = n3;
        this.mCloseDrawerContentDescRes = n4;
        this.mHomeAsUpIndicator = this.getThemeUpIndicator();
        this.mDrawerImage = ContextCompat.getDrawable((Context)object, n2);
        this.mSlider = new SlideDrawable(this, this.mDrawerImage);
        object = this.mSlider;
        float f2 = bl2 ? 0.33333334f : 0.0f;
        ((SlideDrawable)((Object)object)).setOffset(f2);
    }

    private static boolean assumeMaterial(Context context) {
        return context.getApplicationInfo().targetSdkVersion >= 21 && Build.VERSION.SDK_INT >= 21;
    }

    Drawable getThemeUpIndicator() {
        if (this.mActivityImpl != null) {
            return this.mActivityImpl.getThemeUpIndicator();
        }
        return IMPL.getThemeUpIndicator(this.mActivity);
    }

    public boolean isDrawerIndicatorEnabled() {
        return this.mDrawerIndicatorEnabled;
    }

    public void onConfigurationChanged(Configuration configuration) {
        if (!this.mHasCustomUpIndicator) {
            this.mHomeAsUpIndicator = this.getThemeUpIndicator();
        }
        this.mDrawerImage = ContextCompat.getDrawable((Context)this.mActivity, this.mDrawerImageResource);
        this.syncState();
    }

    @Override
    public void onDrawerClosed(View view) {
        this.mSlider.setPosition(0.0f);
        if (this.mDrawerIndicatorEnabled) {
            this.setActionBarDescription(this.mOpenDrawerContentDescRes);
        }
    }

    @Override
    public void onDrawerOpened(View view) {
        this.mSlider.setPosition(1.0f);
        if (this.mDrawerIndicatorEnabled) {
            this.setActionBarDescription(this.mCloseDrawerContentDescRes);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onDrawerSlide(View view, float f2) {
        float f3 = this.mSlider.getPosition();
        f2 = f2 > 0.5f ? Math.max(f3, Math.max(0.0f, f2 - 0.5f) * 2.0f) : Math.min(f3, f2 * 2.0f);
        this.mSlider.setPosition(f2);
    }

    @Override
    public void onDrawerStateChanged(int n2) {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem == null || menuItem.getItemId() != 16908332 || !this.mDrawerIndicatorEnabled) return false;
        if (this.mDrawerLayout.isDrawerVisible(0x800003)) {
            this.mDrawerLayout.closeDrawer(0x800003);
            return true;
        }
        this.mDrawerLayout.openDrawer(0x800003);
        return true;
    }

    void setActionBarDescription(int n2) {
        if (this.mActivityImpl != null) {
            this.mActivityImpl.setActionBarDescription(n2);
            return;
        }
        this.mSetIndicatorInfo = IMPL.setActionBarDescription(this.mSetIndicatorInfo, this.mActivity, n2);
    }

    void setActionBarUpIndicator(Drawable drawable2, int n2) {
        if (this.mActivityImpl != null) {
            this.mActivityImpl.setActionBarUpIndicator(drawable2, n2);
            return;
        }
        this.mSetIndicatorInfo = IMPL.setActionBarUpIndicator(this.mSetIndicatorInfo, this.mActivity, drawable2, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setDrawerIndicatorEnabled(boolean bl2) {
        if (bl2 != this.mDrawerIndicatorEnabled) {
            if (bl2) {
                SlideDrawable slideDrawable = this.mSlider;
                int n2 = this.mDrawerLayout.isDrawerOpen(0x800003) ? this.mCloseDrawerContentDescRes : this.mOpenDrawerContentDescRes;
                this.setActionBarUpIndicator((Drawable)slideDrawable, n2);
            } else {
                this.setActionBarUpIndicator(this.mHomeAsUpIndicator, 0);
            }
            this.mDrawerIndicatorEnabled = bl2;
        }
    }

    public void setHomeAsUpIndicator(int n2) {
        Drawable drawable2 = null;
        if (n2 != 0) {
            drawable2 = ContextCompat.getDrawable((Context)this.mActivity, n2);
        }
        this.setHomeAsUpIndicator(drawable2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setHomeAsUpIndicator(Drawable drawable2) {
        if (drawable2 == null) {
            this.mHomeAsUpIndicator = this.getThemeUpIndicator();
            this.mHasCustomUpIndicator = false;
        } else {
            this.mHomeAsUpIndicator = drawable2;
            this.mHasCustomUpIndicator = true;
        }
        if (!this.mDrawerIndicatorEnabled) {
            this.setActionBarUpIndicator(this.mHomeAsUpIndicator, 0);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void syncState() {
        if (this.mDrawerLayout.isDrawerOpen(0x800003)) {
            this.mSlider.setPosition(1.0f);
        } else {
            this.mSlider.setPosition(0.0f);
        }
        if (this.mDrawerIndicatorEnabled) {
            SlideDrawable slideDrawable = this.mSlider;
            int n2 = this.mDrawerLayout.isDrawerOpen(0x800003) ? this.mCloseDrawerContentDescRes : this.mOpenDrawerContentDescRes;
            this.setActionBarUpIndicator((Drawable)slideDrawable, n2);
        }
    }

    private static interface ActionBarDrawerToggleImpl {
        public Drawable getThemeUpIndicator(Activity var1);

        public Object setActionBarDescription(Object var1, Activity var2, int var3);

        public Object setActionBarUpIndicator(Object var1, Activity var2, Drawable var3, int var4);
    }

    private static class ActionBarDrawerToggleImplBase
    implements ActionBarDrawerToggleImpl {
        ActionBarDrawerToggleImplBase() {
        }

        @Override
        public Drawable getThemeUpIndicator(Activity activity) {
            return null;
        }

        @Override
        public Object setActionBarDescription(Object object, Activity activity, int n2) {
            return object;
        }

        @Override
        public Object setActionBarUpIndicator(Object object, Activity activity, Drawable drawable2, int n2) {
            return object;
        }
    }

    private static class ActionBarDrawerToggleImplHC
    implements ActionBarDrawerToggleImpl {
        ActionBarDrawerToggleImplHC() {
        }

        @Override
        public Drawable getThemeUpIndicator(Activity activity) {
            return ActionBarDrawerToggleHoneycomb.getThemeUpIndicator(activity);
        }

        @Override
        public Object setActionBarDescription(Object object, Activity activity, int n2) {
            return ActionBarDrawerToggleHoneycomb.setActionBarDescription(object, activity, n2);
        }

        @Override
        public Object setActionBarUpIndicator(Object object, Activity activity, Drawable drawable2, int n2) {
            return ActionBarDrawerToggleHoneycomb.setActionBarUpIndicator(object, activity, drawable2, n2);
        }
    }

    private static class ActionBarDrawerToggleImplJellybeanMR2
    implements ActionBarDrawerToggleImpl {
        ActionBarDrawerToggleImplJellybeanMR2() {
        }

        @Override
        public Drawable getThemeUpIndicator(Activity activity) {
            return ActionBarDrawerToggleJellybeanMR2.getThemeUpIndicator(activity);
        }

        @Override
        public Object setActionBarDescription(Object object, Activity activity, int n2) {
            return ActionBarDrawerToggleJellybeanMR2.setActionBarDescription(object, activity, n2);
        }

        @Override
        public Object setActionBarUpIndicator(Object object, Activity activity, Drawable drawable2, int n2) {
            return ActionBarDrawerToggleJellybeanMR2.setActionBarUpIndicator(object, activity, drawable2, n2);
        }
    }

    public static interface Delegate {
        @Nullable
        public Drawable getThemeUpIndicator();

        public void setActionBarDescription(@StringRes int var1);

        public void setActionBarUpIndicator(Drawable var1, @StringRes int var2);
    }

    public static interface DelegateProvider {
        @Nullable
        public Delegate getDrawerToggleDelegate();
    }

    private class SlideDrawable
    extends InsetDrawable
    implements Drawable.Callback {
        private final boolean mHasMirroring;
        private float mOffset;
        private float mPosition;
        private final Rect mTmpRect;
        final /* synthetic */ ActionBarDrawerToggle this$0;

        SlideDrawable(ActionBarDrawerToggle actionBarDrawerToggle, Drawable drawable2) {
            boolean bl2 = false;
            this.this$0 = actionBarDrawerToggle;
            super(drawable2, 0);
            if (Build.VERSION.SDK_INT > 18) {
                bl2 = true;
            }
            this.mHasMirroring = bl2;
            this.mTmpRect = new Rect();
        }

        /*
         * Enabled aggressive block sorting
         */
        public void draw(Canvas canvas) {
            int n2 = 1;
            this.copyBounds(this.mTmpRect);
            canvas.save();
            boolean bl2 = ViewCompat.getLayoutDirection(this.this$0.mActivity.getWindow().getDecorView()) == 1;
            if (bl2) {
                n2 = -1;
            }
            int n3 = this.mTmpRect.width();
            canvas.translate(-this.mOffset * (float)n3 * this.mPosition * (float)n2, 0.0f);
            if (bl2 && !this.mHasMirroring) {
                canvas.translate((float)n3, 0.0f);
                canvas.scale(-1.0f, 1.0f);
            }
            super.draw(canvas);
            canvas.restore();
        }

        public float getPosition() {
            return this.mPosition;
        }

        public void setOffset(float f2) {
            this.mOffset = f2;
            this.invalidateSelf();
        }

        public void setPosition(float f2) {
            this.mPosition = f2;
            this.invalidateSelf();
        }
    }
}

