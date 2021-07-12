/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Configuration
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.view.KeyEvent
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.ViewGroup$LayoutParams
 */
package android.support.v7.app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.KeyEventCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class AppCompatActivity
extends FragmentActivity
implements AppCompatCallback,
TaskStackBuilder.SupportParentable,
ActionBarDrawerToggle.DelegateProvider {
    private AppCompatDelegate mDelegate;
    private boolean mEatKeyUpEvent;
    private int mThemeId = 0;

    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.getDelegate().addContentView(view, layoutParams);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (KeyEventCompat.hasModifiers(keyEvent, 4096) && keyEvent.getUnicodeChar(keyEvent.getMetaState() & 0xFFFF8FFF) == 60) {
            int n2 = keyEvent.getAction();
            if (n2 == 0) {
                ActionBar actionBar = this.getSupportActionBar();
                if (actionBar != null && actionBar.isShowing() && actionBar.requestFocus()) {
                    this.mEatKeyUpEvent = true;
                    return true;
                }
            } else if (n2 == 1 && this.mEatKeyUpEvent) {
                this.mEatKeyUpEvent = false;
                return true;
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Nullable
    public View findViewById(@IdRes int n2) {
        return this.getDelegate().findViewById(n2);
    }

    @NonNull
    public AppCompatDelegate getDelegate() {
        if (this.mDelegate == null) {
            this.mDelegate = AppCompatDelegate.create(this, (AppCompatCallback)this);
        }
        return this.mDelegate;
    }

    @Override
    @Nullable
    public ActionBarDrawerToggle.Delegate getDrawerToggleDelegate() {
        return this.getDelegate().getDrawerToggleDelegate();
    }

    public MenuInflater getMenuInflater() {
        return this.getDelegate().getMenuInflater();
    }

    @Nullable
    public ActionBar getSupportActionBar() {
        return this.getDelegate().getSupportActionBar();
    }

    @Override
    @Nullable
    public Intent getSupportParentActivityIntent() {
        return NavUtils.getParentActivityIntent(this);
    }

    public void invalidateOptionsMenu() {
        this.getDelegate().invalidateOptionsMenu();
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.getDelegate().onConfigurationChanged(configuration);
    }

    public void onContentChanged() {
        this.onSupportContentChanged();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        AppCompatDelegate appCompatDelegate = this.getDelegate();
        appCompatDelegate.installViewFactory();
        appCompatDelegate.onCreate(bundle);
        if (appCompatDelegate.applyDayNight() && this.mThemeId != 0) {
            if (Build.VERSION.SDK_INT >= 23) {
                this.onApplyThemeResource(this.getTheme(), this.mThemeId, false);
            } else {
                this.setTheme(this.mThemeId);
            }
        }
        super.onCreate(bundle);
    }

    public void onCreateSupportNavigateUpTaskStack(@NonNull TaskStackBuilder taskStackBuilder) {
        taskStackBuilder.addParentStack(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.getDelegate().onDestroy();
    }

    @Override
    public final boolean onMenuItemSelected(int n2, MenuItem menuItem) {
        if (super.onMenuItemSelected(n2, menuItem)) {
            return true;
        }
        ActionBar actionBar = this.getSupportActionBar();
        if (menuItem.getItemId() == 16908332 && actionBar != null && (actionBar.getDisplayOptions() & 4) != 0) {
            return this.onSupportNavigateUp();
        }
        return false;
    }

    public boolean onMenuOpened(int n2, Menu menu) {
        return super.onMenuOpened(n2, menu);
    }

    @Override
    public void onPanelClosed(int n2, Menu menu) {
        super.onPanelClosed(n2, menu);
    }

    protected void onPostCreate(@Nullable Bundle bundle) {
        super.onPostCreate(bundle);
        this.getDelegate().onPostCreate(bundle);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        this.getDelegate().onPostResume();
    }

    public void onPrepareSupportNavigateUpTaskStack(@NonNull TaskStackBuilder taskStackBuilder) {
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.getDelegate().onSaveInstanceState(bundle);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.getDelegate().onStop();
    }

    @Override
    @CallSuper
    public void onSupportActionModeFinished(@NonNull ActionMode actionMode) {
    }

    @Override
    @CallSuper
    public void onSupportActionModeStarted(@NonNull ActionMode actionMode) {
    }

    @Deprecated
    public void onSupportContentChanged() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean onSupportNavigateUp() {
        Object object = this.getSupportParentActivityIntent();
        if (object == null) return false;
        if (this.supportShouldUpRecreateTask((Intent)object)) {
            object = TaskStackBuilder.create((Context)this);
            this.onCreateSupportNavigateUpTaskStack((TaskStackBuilder)object);
            this.onPrepareSupportNavigateUpTaskStack((TaskStackBuilder)object);
            ((TaskStackBuilder)object).startActivities();
            try {
                ActivityCompat.finishAffinity(this);
            }
            catch (IllegalStateException illegalStateException) {
                this.finish();
                return true;
            }
            return true;
        }
        this.supportNavigateUpTo((Intent)object);
        return true;
    }

    protected void onTitleChanged(CharSequence charSequence, int n2) {
        super.onTitleChanged(charSequence, n2);
        this.getDelegate().setTitle(charSequence);
    }

    @Override
    @Nullable
    public ActionMode onWindowStartingSupportActionMode(@NonNull ActionMode.Callback callback) {
        return null;
    }

    public void setContentView(@LayoutRes int n2) {
        this.getDelegate().setContentView(n2);
    }

    public void setContentView(View view) {
        this.getDelegate().setContentView(view);
    }

    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.getDelegate().setContentView(view, layoutParams);
    }

    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        this.getDelegate().setSupportActionBar(toolbar);
    }

    @Deprecated
    public void setSupportProgress(int n2) {
    }

    @Deprecated
    public void setSupportProgressBarIndeterminate(boolean bl2) {
    }

    @Deprecated
    public void setSupportProgressBarIndeterminateVisibility(boolean bl2) {
    }

    @Deprecated
    public void setSupportProgressBarVisibility(boolean bl2) {
    }

    public void setTheme(@StyleRes int n2) {
        super.setTheme(n2);
        this.mThemeId = n2;
    }

    @Nullable
    public ActionMode startSupportActionMode(@NonNull ActionMode.Callback callback) {
        return this.getDelegate().startSupportActionMode(callback);
    }

    @Override
    public void supportInvalidateOptionsMenu() {
        this.getDelegate().invalidateOptionsMenu();
    }

    public void supportNavigateUpTo(@NonNull Intent intent) {
        NavUtils.navigateUpTo(this, intent);
    }

    public boolean supportRequestWindowFeature(int n2) {
        return this.getDelegate().requestWindowFeature(n2);
    }

    public boolean supportShouldUpRecreateTask(@NonNull Intent intent) {
        return NavUtils.shouldUpRecreateTask(this, intent);
    }
}

