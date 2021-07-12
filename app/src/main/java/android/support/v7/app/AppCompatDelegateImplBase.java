/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.view.KeyEvent
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.View
 *  android.view.Window
 *  android.view.Window$Callback
 */
package android.support.v7.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.appcompat.R;
import android.support.v7.view.ActionMode;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.WindowCallbackWrapper;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.TintTypedArray;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;

abstract class AppCompatDelegateImplBase
extends AppCompatDelegate {
    ActionBar mActionBar;
    final AppCompatCallback mAppCompatCallback;
    final Window.Callback mAppCompatWindowCallback;
    final Context mContext;
    boolean mHasActionBar;
    private boolean mIsDestroyed;
    boolean mIsFloating;
    MenuInflater mMenuInflater;
    final Window.Callback mOriginalWindowCallback;
    boolean mOverlayActionBar;
    boolean mOverlayActionMode;
    boolean mThemeRead;
    private CharSequence mTitle;
    final Window mWindow;
    boolean mWindowNoTitle;

    AppCompatDelegateImplBase(Context context, Window window, AppCompatCallback appCompatCallback) {
        this.mContext = context;
        this.mWindow = window;
        this.mAppCompatCallback = appCompatCallback;
        this.mOriginalWindowCallback = this.mWindow.getCallback();
        if (this.mOriginalWindowCallback instanceof AppCompatWindowCallbackBase) {
            throw new IllegalStateException("AppCompat has already installed itself into the Window");
        }
        this.mAppCompatWindowCallback = this.wrapWindowCallback(this.mOriginalWindowCallback);
        this.mWindow.setCallback(this.mAppCompatWindowCallback);
    }

    @Override
    public boolean applyDayNight() {
        return false;
    }

    abstract boolean dispatchKeyEvent(KeyEvent var1);

    final Context getActionBarThemedContext() {
        Context context = null;
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            context = actionBar.getThemedContext();
        }
        actionBar = context;
        if (context == null) {
            actionBar = this.mContext;
        }
        return actionBar;
    }

    @Override
    public final ActionBarDrawerToggle.Delegate getDrawerToggleDelegate() {
        return new ActionBarDrawableToggleImpl();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public MenuInflater getMenuInflater() {
        if (this.mMenuInflater == null) {
            this.initWindowDecorActionBar();
            Context context = this.mActionBar != null ? this.mActionBar.getThemedContext() : this.mContext;
            this.mMenuInflater = new SupportMenuInflater(context);
        }
        return this.mMenuInflater;
    }

    @Override
    public ActionBar getSupportActionBar() {
        this.initWindowDecorActionBar();
        return this.mActionBar;
    }

    final CharSequence getTitle() {
        if (this.mOriginalWindowCallback instanceof Activity) {
            return ((Activity)this.mOriginalWindowCallback).getTitle();
        }
        return this.mTitle;
    }

    final Window.Callback getWindowCallback() {
        return this.mWindow.getCallback();
    }

    abstract void initWindowDecorActionBar();

    final boolean isDestroyed() {
        return this.mIsDestroyed;
    }

    @Override
    public boolean isHandleNativeActionModesEnabled() {
        return false;
    }

    @Override
    public void onDestroy() {
        this.mIsDestroyed = true;
    }

    abstract boolean onKeyShortcut(int var1, KeyEvent var2);

    abstract boolean onMenuOpened(int var1, Menu var2);

    abstract void onPanelClosed(int var1, Menu var2);

    @Override
    public void onSaveInstanceState(Bundle bundle) {
    }

    abstract void onTitleChanged(CharSequence var1);

    final ActionBar peekSupportActionBar() {
        return this.mActionBar;
    }

    @Override
    public void setHandleNativeActionModesEnabled(boolean bl2) {
    }

    @Override
    public void setLocalNightMode(int n2) {
    }

    @Override
    public final void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        this.onTitleChanged(charSequence);
    }

    abstract ActionMode startSupportActionModeFromWindow(ActionMode.Callback var1);

    Window.Callback wrapWindowCallback(Window.Callback callback) {
        return new AppCompatWindowCallbackBase(callback);
    }

    private class ActionBarDrawableToggleImpl
    implements ActionBarDrawerToggle.Delegate {
        private ActionBarDrawableToggleImpl() {
        }

        @Override
        public Context getActionBarThemedContext() {
            return AppCompatDelegateImplBase.this.getActionBarThemedContext();
        }

        @Override
        public Drawable getThemeUpIndicator() {
            TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(this.getActionBarThemedContext(), null, new int[]{R.attr.homeAsUpIndicator});
            Drawable drawable2 = tintTypedArray.getDrawable(0);
            tintTypedArray.recycle();
            return drawable2;
        }

        @Override
        public boolean isNavigationVisible() {
            ActionBar actionBar = AppCompatDelegateImplBase.this.getSupportActionBar();
            return actionBar != null && (actionBar.getDisplayOptions() & 4) != 0;
        }

        @Override
        public void setActionBarDescription(int n2) {
            ActionBar actionBar = AppCompatDelegateImplBase.this.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setHomeActionContentDescription(n2);
            }
        }

        @Override
        public void setActionBarUpIndicator(Drawable drawable2, int n2) {
            ActionBar actionBar = AppCompatDelegateImplBase.this.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setHomeAsUpIndicator(drawable2);
                actionBar.setHomeActionContentDescription(n2);
            }
        }
    }

    class AppCompatWindowCallbackBase
    extends WindowCallbackWrapper {
        AppCompatWindowCallbackBase(Window.Callback callback) {
            super(callback);
        }

        @Override
        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return AppCompatDelegateImplBase.this.dispatchKeyEvent(keyEvent) || super.dispatchKeyEvent(keyEvent);
        }

        @Override
        public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
            return super.dispatchKeyShortcutEvent(keyEvent) || AppCompatDelegateImplBase.this.onKeyShortcut(keyEvent.getKeyCode(), keyEvent);
        }

        @Override
        public void onContentChanged() {
        }

        @Override
        public boolean onCreatePanelMenu(int n2, Menu menu) {
            if (n2 == 0 && !(menu instanceof MenuBuilder)) {
                return false;
            }
            return super.onCreatePanelMenu(n2, menu);
        }

        @Override
        public boolean onMenuOpened(int n2, Menu menu) {
            super.onMenuOpened(n2, menu);
            AppCompatDelegateImplBase.this.onMenuOpened(n2, menu);
            return true;
        }

        @Override
        public void onPanelClosed(int n2, Menu menu) {
            super.onPanelClosed(n2, menu);
            AppCompatDelegateImplBase.this.onPanelClosed(n2, menu);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean onPreparePanel(int n2, View view, Menu menu) {
            boolean bl2;
            MenuBuilder menuBuilder = menu instanceof MenuBuilder ? (MenuBuilder)menu : null;
            if (n2 == 0 && menuBuilder == null) {
                return false;
            }
            if (menuBuilder != null) {
                menuBuilder.setOverrideVisibleItems(true);
            }
            boolean bl3 = bl2 = super.onPreparePanel(n2, view, menu);
            if (menuBuilder == null) return bl3;
            menuBuilder.setOverrideVisibleItems(false);
            return bl2;
        }
    }
}

