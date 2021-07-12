/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Rect
 *  android.media.AudioManager
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  android.util.AndroidRuntimeException
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.util.TypedValue
 *  android.view.KeyCharacterMap
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.LayoutInflater$Factory
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.Window
 *  android.view.Window$Callback
 *  android.view.WindowManager
 *  android.view.WindowManager$LayoutParams
 *  android.widget.FrameLayout
 *  android.widget.PopupWindow
 *  android.widget.TextView
 */
package android.support.v7.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegateImplBase;
import android.support.v7.app.AppCompatViewInflater;
import android.support.v7.app.ToolbarActionBar;
import android.support.v7.app.WindowDecorActionBar;
import android.support.v7.appcompat.R;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.view.StandaloneActionMode;
import android.support.v7.view.menu.ListMenuPresenter;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.ActionBarContextView;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.DecorContentParent;
import android.support.v7.widget.FitWindowsViewGroup;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewStubCompat;
import android.support.v7.widget.ViewUtils;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

class AppCompatDelegateImplV7
extends AppCompatDelegateImplBase
implements MenuBuilder.Callback,
LayoutInflaterFactory {
    private ActionMenuPresenterCallback mActionMenuPresenterCallback;
    ActionMode mActionMode;
    PopupWindow mActionModePopup;
    ActionBarContextView mActionModeView;
    private AppCompatViewInflater mAppCompatViewInflater;
    private boolean mClosingActionMenu;
    private DecorContentParent mDecorContentParent;
    private boolean mEnableDefaultActionBarUp;
    ViewPropertyAnimatorCompat mFadeAnim = null;
    private boolean mFeatureIndeterminateProgress;
    private boolean mFeatureProgress;
    private int mInvalidatePanelMenuFeatures;
    private boolean mInvalidatePanelMenuPosted;
    private final Runnable mInvalidatePanelMenuRunnable = new Runnable(){

        @Override
        public void run() {
            if ((AppCompatDelegateImplV7.this.mInvalidatePanelMenuFeatures & 1) != 0) {
                AppCompatDelegateImplV7.this.doInvalidatePanelMenu(0);
            }
            if ((AppCompatDelegateImplV7.this.mInvalidatePanelMenuFeatures & 0x1000) != 0) {
                AppCompatDelegateImplV7.this.doInvalidatePanelMenu(108);
            }
            AppCompatDelegateImplV7.access$202(AppCompatDelegateImplV7.this, false);
            AppCompatDelegateImplV7.access$002(AppCompatDelegateImplV7.this, 0);
        }
    };
    private boolean mLongPressBackDown;
    private PanelMenuPresenterCallback mPanelMenuPresenterCallback;
    private PanelFeatureState[] mPanels;
    private PanelFeatureState mPreparedPanel;
    Runnable mShowActionModePopup;
    private View mStatusGuard;
    private ViewGroup mSubDecor;
    private boolean mSubDecorInstalled;
    private Rect mTempRect1;
    private Rect mTempRect2;
    private TextView mTitleView;

    AppCompatDelegateImplV7(Context context, Window window, AppCompatCallback appCompatCallback) {
        super(context, window, appCompatCallback);
    }

    static /* synthetic */ int access$002(AppCompatDelegateImplV7 appCompatDelegateImplV7, int n2) {
        appCompatDelegateImplV7.mInvalidatePanelMenuFeatures = n2;
        return n2;
    }

    static /* synthetic */ boolean access$202(AppCompatDelegateImplV7 appCompatDelegateImplV7, boolean bl2) {
        appCompatDelegateImplV7.mInvalidatePanelMenuPosted = bl2;
        return bl2;
    }

    private void applyFixedSizeWindow() {
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout)this.mSubDecor.findViewById(0x1020002);
        View view = this.mWindow.getDecorView();
        contentFrameLayout.setDecorPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
        view = this.mContext.obtainStyledAttributes(R.styleable.AppCompatTheme);
        view.getValue(R.styleable.AppCompatTheme_windowMinWidthMajor, contentFrameLayout.getMinWidthMajor());
        view.getValue(R.styleable.AppCompatTheme_windowMinWidthMinor, contentFrameLayout.getMinWidthMinor());
        if (view.hasValue(R.styleable.AppCompatTheme_windowFixedWidthMajor)) {
            view.getValue(R.styleable.AppCompatTheme_windowFixedWidthMajor, contentFrameLayout.getFixedWidthMajor());
        }
        if (view.hasValue(R.styleable.AppCompatTheme_windowFixedWidthMinor)) {
            view.getValue(R.styleable.AppCompatTheme_windowFixedWidthMinor, contentFrameLayout.getFixedWidthMinor());
        }
        if (view.hasValue(R.styleable.AppCompatTheme_windowFixedHeightMajor)) {
            view.getValue(R.styleable.AppCompatTheme_windowFixedHeightMajor, contentFrameLayout.getFixedHeightMajor());
        }
        if (view.hasValue(R.styleable.AppCompatTheme_windowFixedHeightMinor)) {
            view.getValue(R.styleable.AppCompatTheme_windowFixedHeightMinor, contentFrameLayout.getFixedHeightMinor());
        }
        view.recycle();
        contentFrameLayout.requestLayout();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void callOnPanelClosed(int n2, PanelFeatureState panelFeatureState, Menu menu) {
        PanelFeatureState panelFeatureState2 = panelFeatureState;
        Menu menu2 = menu;
        if (menu == null) {
            PanelFeatureState panelFeatureState3 = panelFeatureState;
            if (panelFeatureState == null) {
                panelFeatureState3 = panelFeatureState;
                if (n2 >= 0) {
                    panelFeatureState3 = panelFeatureState;
                    if (n2 < this.mPanels.length) {
                        panelFeatureState3 = this.mPanels[n2];
                    }
                }
            }
            panelFeatureState2 = panelFeatureState3;
            menu2 = menu;
            if (panelFeatureState3 != null) {
                menu2 = panelFeatureState3.menu;
                panelFeatureState2 = panelFeatureState3;
            }
        }
        if (panelFeatureState2 != null && !panelFeatureState2.isOpen || this.isDestroyed()) {
            return;
        }
        this.mOriginalWindowCallback.onPanelClosed(n2, menu2);
    }

    private void checkCloseActionMenu(MenuBuilder menuBuilder) {
        if (this.mClosingActionMenu) {
            return;
        }
        this.mClosingActionMenu = true;
        this.mDecorContentParent.dismissPopups();
        Window.Callback callback = this.getWindowCallback();
        if (callback != null && !this.isDestroyed()) {
            callback.onPanelClosed(108, (Menu)menuBuilder);
        }
        this.mClosingActionMenu = false;
    }

    private void closePanel(int n2) {
        this.closePanel(this.getPanelState(n2, true), true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void closePanel(PanelFeatureState panelFeatureState, boolean bl2) {
        if (bl2 && panelFeatureState.featureId == 0 && this.mDecorContentParent != null && this.mDecorContentParent.isOverflowMenuShowing()) {
            this.checkCloseActionMenu(panelFeatureState.menu);
            return;
        } else {
            WindowManager windowManager = (WindowManager)this.mContext.getSystemService("window");
            if (windowManager != null && panelFeatureState.isOpen && panelFeatureState.decorView != null) {
                windowManager.removeView((View)panelFeatureState.decorView);
                if (bl2) {
                    this.callOnPanelClosed(panelFeatureState.featureId, panelFeatureState, null);
                }
            }
            panelFeatureState.isPrepared = false;
            panelFeatureState.isHandled = false;
            panelFeatureState.isOpen = false;
            panelFeatureState.shownPanelView = null;
            panelFeatureState.refreshDecorView = true;
            if (this.mPreparedPanel != panelFeatureState) return;
            this.mPreparedPanel = null;
            return;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private ViewGroup createSubDecor() {
        void var1_4;
        LayoutInflater layoutInflater;
        block22: {
            void var1_12;
            block20: {
                block21: {
                    TypedArray typedArray = this.mContext.obtainStyledAttributes(R.styleable.AppCompatTheme);
                    if (!typedArray.hasValue(R.styleable.AppCompatTheme_windowActionBar)) {
                        typedArray.recycle();
                        throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
                    }
                    if (typedArray.getBoolean(R.styleable.AppCompatTheme_windowNoTitle, false)) {
                        this.requestWindowFeature(1);
                    } else if (typedArray.getBoolean(R.styleable.AppCompatTheme_windowActionBar, false)) {
                        this.requestWindowFeature(108);
                    }
                    if (typedArray.getBoolean(R.styleable.AppCompatTheme_windowActionBarOverlay, false)) {
                        this.requestWindowFeature(109);
                    }
                    if (typedArray.getBoolean(R.styleable.AppCompatTheme_windowActionModeOverlay, false)) {
                        this.requestWindowFeature(10);
                    }
                    this.mIsFloating = typedArray.getBoolean(R.styleable.AppCompatTheme_android_windowIsFloating, false);
                    typedArray.recycle();
                    layoutInflater = LayoutInflater.from((Context)this.mContext);
                    Object var1_2 = null;
                    if (this.mWindowNoTitle) break block20;
                    if (!this.mIsFloating) break block21;
                    ViewGroup viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.abc_dialog_title_material, null);
                    this.mOverlayActionBar = false;
                    this.mHasActionBar = false;
                    break block22;
                }
                if (this.mHasActionBar) {
                    void var1_7;
                    TypedValue typedValue = new TypedValue();
                    this.mContext.getTheme().resolveAttribute(R.attr.actionBarTheme, typedValue, true);
                    if (typedValue.resourceId != 0) {
                        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(this.mContext, typedValue.resourceId);
                    } else {
                        Context context = this.mContext;
                    }
                    layoutInflater = (ViewGroup)LayoutInflater.from((Context)var1_7).inflate(R.layout.abc_screen_toolbar, null);
                    this.mDecorContentParent = (DecorContentParent)layoutInflater.findViewById(R.id.decor_content_parent);
                    this.mDecorContentParent.setWindowCallback(this.getWindowCallback());
                    if (this.mOverlayActionBar) {
                        this.mDecorContentParent.initFeature(109);
                    }
                    if (this.mFeatureProgress) {
                        this.mDecorContentParent.initFeature(2);
                    }
                    LayoutInflater layoutInflater2 = layoutInflater;
                    if (this.mFeatureIndeterminateProgress) {
                        this.mDecorContentParent.initFeature(5);
                        LayoutInflater layoutInflater3 = layoutInflater;
                    }
                }
                break block22;
            }
            if (this.mOverlayActionMode) {
                ViewGroup viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.abc_screen_simple_overlay_action_mode, null);
            } else {
                ViewGroup viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.abc_screen_simple, null);
            }
            if (Build.VERSION.SDK_INT >= 21) {
                ViewCompat.setOnApplyWindowInsetsListener((View)var1_12, new OnApplyWindowInsetsListener(){

                    @Override
                    public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                        int n2 = windowInsetsCompat.getSystemWindowInsetTop();
                        int n3 = AppCompatDelegateImplV7.this.updateStatusGuard(n2);
                        WindowInsetsCompat windowInsetsCompat2 = windowInsetsCompat;
                        if (n2 != n3) {
                            windowInsetsCompat2 = windowInsetsCompat.replaceSystemWindowInsets(windowInsetsCompat.getSystemWindowInsetLeft(), n3, windowInsetsCompat.getSystemWindowInsetRight(), windowInsetsCompat.getSystemWindowInsetBottom());
                        }
                        return ViewCompat.onApplyWindowInsets(view, windowInsetsCompat2);
                    }
                });
            } else {
                ((FitWindowsViewGroup)var1_12).setOnFitSystemWindowsListener(new FitWindowsViewGroup.OnFitSystemWindowsListener(){

                    @Override
                    public void onFitSystemWindows(Rect rect) {
                        rect.top = AppCompatDelegateImplV7.this.updateStatusGuard(rect.top);
                    }
                });
            }
        }
        if (var1_4 == null) {
            throw new IllegalArgumentException("AppCompat does not support the current theme features: { windowActionBar: " + this.mHasActionBar + ", windowActionBarOverlay: " + this.mOverlayActionBar + ", android:windowIsFloating: " + this.mIsFloating + ", windowActionModeOverlay: " + this.mOverlayActionMode + ", windowNoTitle: " + this.mWindowNoTitle + " }");
        }
        if (this.mDecorContentParent == null) {
            this.mTitleView = (TextView)var1_4.findViewById(R.id.title);
        }
        ViewUtils.makeOptionalFitsSystemWindows((View)var1_4);
        layoutInflater = (ViewGroup)this.mWindow.findViewById(0x1020002);
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout)var1_4.findViewById(R.id.action_bar_activity_content);
        while (layoutInflater.getChildCount() > 0) {
            View view = layoutInflater.getChildAt(0);
            layoutInflater.removeViewAt(0);
            contentFrameLayout.addView(view);
        }
        this.mWindow.setContentView((View)var1_4);
        layoutInflater.setId(-1);
        contentFrameLayout.setId(0x1020002);
        if (layoutInflater instanceof FrameLayout) {
            ((FrameLayout)layoutInflater).setForeground(null);
        }
        contentFrameLayout.setAttachListener(new ContentFrameLayout.OnAttachListener(){

            @Override
            public void onAttachedFromWindow() {
            }

            @Override
            public void onDetachedFromWindow() {
                AppCompatDelegateImplV7.this.dismissPopups();
            }
        });
        return var1_4;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void dismissPopups() {
        if (this.mDecorContentParent != null) {
            this.mDecorContentParent.dismissPopups();
        }
        if (this.mActionModePopup != null) {
            this.mWindow.getDecorView().removeCallbacks(this.mShowActionModePopup);
            if (this.mActionModePopup.isShowing()) {
                try {
                    this.mActionModePopup.dismiss();
                }
                catch (IllegalArgumentException illegalArgumentException) {}
            }
            this.mActionModePopup = null;
        }
        this.endOnGoingFadeAnimation();
        PanelFeatureState panelFeatureState = this.getPanelState(0, false);
        if (panelFeatureState != null && panelFeatureState.menu != null) {
            panelFeatureState.menu.close();
        }
    }

    private void doInvalidatePanelMenu(int n2) {
        PanelFeatureState panelFeatureState = this.getPanelState(n2, true);
        if (panelFeatureState.menu != null) {
            Bundle bundle = new Bundle();
            panelFeatureState.menu.saveActionViewStates(bundle);
            if (bundle.size() > 0) {
                panelFeatureState.frozenActionViewState = bundle;
            }
            panelFeatureState.menu.stopDispatchingItemsChanged();
            panelFeatureState.menu.clear();
        }
        panelFeatureState.refreshMenuContent = true;
        panelFeatureState.refreshDecorView = true;
        if ((n2 == 108 || n2 == 0) && this.mDecorContentParent != null && (panelFeatureState = this.getPanelState(0, false)) != null) {
            panelFeatureState.isPrepared = false;
            this.preparePanel(panelFeatureState, null);
        }
    }

    private void endOnGoingFadeAnimation() {
        if (this.mFadeAnim != null) {
            this.mFadeAnim.cancel();
        }
    }

    private void ensureSubDecor() {
        if (!this.mSubDecorInstalled) {
            this.mSubDecor = this.createSubDecor();
            Object object = this.getTitle();
            if (!TextUtils.isEmpty((CharSequence)object)) {
                this.onTitleChanged((CharSequence)object);
            }
            this.applyFixedSizeWindow();
            this.onSubDecorInstalled(this.mSubDecor);
            this.mSubDecorInstalled = true;
            object = this.getPanelState(0, false);
            if (!(this.isDestroyed() || object != null && ((PanelFeatureState)object).menu != null)) {
                this.invalidatePanelMenu(108);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private PanelFeatureState findMenuPanel(Menu menu) {
        PanelFeatureState[] panelFeatureStateArray = this.mPanels;
        if (panelFeatureStateArray == null) return null;
        int n2 = panelFeatureStateArray.length;
        int n3 = 0;
        while (n3 < n2) {
            PanelFeatureState panelFeatureState = panelFeatureStateArray[n3];
            if (panelFeatureState != null && panelFeatureState.menu == menu) {
                return panelFeatureState;
            }
            ++n3;
        }
        return null;
    }

    private PanelFeatureState getPanelState(int n2, boolean bl2) {
        Object object;
        PanelFeatureState[] panelFeatureStateArray;
        Object object2;
        block6: {
            block5: {
                object2 = this.mPanels;
                if (object2 == null) break block5;
                panelFeatureStateArray = object2;
                if (((PanelFeatureState[])object2).length > n2) break block6;
            }
            object = new PanelFeatureState[n2 + 1];
            if (object2 != null) {
                System.arraycopy(object2, 0, object, 0, ((PanelFeatureState[])object2).length);
            }
            panelFeatureStateArray = object;
            this.mPanels = object;
        }
        object = panelFeatureStateArray[n2];
        object2 = object;
        if (object == null) {
            panelFeatureStateArray[n2] = object2 = new PanelFeatureState(n2);
        }
        return object2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean initializePanelContent(PanelFeatureState panelFeatureState) {
        if (panelFeatureState.createdPanelView != null) {
            panelFeatureState.shownPanelView = panelFeatureState.createdPanelView;
            return true;
        } else {
            if (panelFeatureState.menu == null) {
                return false;
            }
            if (this.mPanelMenuPresenterCallback == null) {
                this.mPanelMenuPresenterCallback = new PanelMenuPresenterCallback();
            }
            panelFeatureState.shownPanelView = (View)panelFeatureState.getListMenuView(this.mPanelMenuPresenterCallback);
            if (panelFeatureState.shownPanelView != null) return true;
            return false;
        }
    }

    private boolean initializePanelDecor(PanelFeatureState panelFeatureState) {
        panelFeatureState.setStyle(this.getActionBarThemedContext());
        panelFeatureState.decorView = new ListMenuDecorView(panelFeatureState.listPresenterContext);
        panelFeatureState.gravity = 81;
        return true;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private boolean initializePanelMenu(PanelFeatureState panelFeatureState) {
        void var2_10;
        block10: {
            Context context;
            block9: {
                context = this.mContext;
                if (panelFeatureState.featureId == 0) break block9;
                Context context2 = context;
                if (panelFeatureState.featureId != 108) break block10;
            }
            Context context3 = context;
            if (this.mDecorContentParent != null) {
                void var2_7;
                TypedValue typedValue = new TypedValue();
                Resources.Theme theme = context.getTheme();
                theme.resolveAttribute(R.attr.actionBarTheme, typedValue, true);
                Object var2_5 = null;
                if (typedValue.resourceId != 0) {
                    Resources.Theme theme2 = context.getResources().newTheme();
                    theme2.setTo(theme);
                    theme2.applyStyle(typedValue.resourceId, true);
                    theme2.resolveAttribute(R.attr.actionBarWidgetTheme, typedValue, true);
                } else {
                    theme.resolveAttribute(R.attr.actionBarWidgetTheme, typedValue, true);
                }
                Resources.Theme theme3 = var2_7;
                if (typedValue.resourceId != 0) {
                    theme3 = var2_7;
                    if (var2_7 == null) {
                        theme3 = context.getResources().newTheme();
                        theme3.setTo(theme);
                    }
                    theme3.applyStyle(typedValue.resourceId, true);
                }
                Context context4 = context;
                if (theme3 != null) {
                    ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, 0);
                    contextThemeWrapper.getTheme().setTo(theme3);
                }
            }
        }
        MenuBuilder menuBuilder = new MenuBuilder((Context)var2_10);
        menuBuilder.setCallback(this);
        panelFeatureState.setMenu(menuBuilder);
        return true;
    }

    private void invalidatePanelMenu(int n2) {
        this.mInvalidatePanelMenuFeatures |= 1 << n2;
        if (!this.mInvalidatePanelMenuPosted) {
            ViewCompat.postOnAnimation(this.mWindow.getDecorView(), this.mInvalidatePanelMenuRunnable);
            this.mInvalidatePanelMenuPosted = true;
        }
    }

    private boolean onKeyDownPanel(int n2, KeyEvent keyEvent) {
        if (keyEvent.getRepeatCount() == 0) {
            PanelFeatureState panelFeatureState = this.getPanelState(n2, true);
            if (!panelFeatureState.isOpen) {
                return this.preparePanel(panelFeatureState, keyEvent);
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean onKeyUpPanel(int n2, KeyEvent keyEvent) {
        boolean bl2;
        boolean bl3;
        block11: {
            PanelFeatureState panelFeatureState;
            boolean bl4;
            block9: {
                block10: {
                    if (this.mActionMode != null) {
                        return false;
                    }
                    bl4 = false;
                    panelFeatureState = this.getPanelState(n2, true);
                    if (n2 != 0 || this.mDecorContentParent == null || !this.mDecorContentParent.canShowOverflowMenu() || ViewConfigurationCompat.hasPermanentMenuKey(ViewConfiguration.get((Context)this.mContext))) break block9;
                    if (this.mDecorContentParent.isOverflowMenuShowing()) break block10;
                    bl3 = bl4;
                    if (!this.isDestroyed()) {
                        bl3 = bl4;
                        if (this.preparePanel(panelFeatureState, keyEvent)) {
                            bl3 = this.mDecorContentParent.showOverflowMenu();
                        }
                    }
                    break block11;
                }
                bl3 = this.mDecorContentParent.hideOverflowMenu();
                break block11;
            }
            if (panelFeatureState.isOpen || panelFeatureState.isHandled) {
                bl3 = panelFeatureState.isOpen;
                this.closePanel(panelFeatureState, true);
            } else {
                bl3 = bl4;
                if (panelFeatureState.isPrepared) {
                    bl2 = true;
                    if (panelFeatureState.refreshMenuContent) {
                        panelFeatureState.isPrepared = false;
                        bl2 = this.preparePanel(panelFeatureState, keyEvent);
                    }
                    bl3 = bl4;
                    if (bl2) {
                        this.openPanel(panelFeatureState, keyEvent);
                        bl3 = true;
                    }
                }
            }
        }
        bl2 = bl3;
        if (!bl3) return bl2;
        keyEvent = (AudioManager)this.mContext.getSystemService("audio");
        if (keyEvent != null) {
            keyEvent.playSoundEffect(0);
            return bl3;
        }
        Log.w((String)"AppCompatDelegate", (String)"Couldn't get audio manager");
        return bl3;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void openPanel(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        WindowManager windowManager;
        int n2;
        block18: {
            int n3;
            block14: {
                Context context;
                block17: {
                    block12: {
                        block16: {
                            block15: {
                                block13: {
                                    if (panelFeatureState.isOpen || this.isDestroyed()) break block12;
                                    if (panelFeatureState.featureId != 0) break block13;
                                    context = this.mContext;
                                    n2 = (context.getResources().getConfiguration().screenLayout & 0xF) == 4 ? 1 : 0;
                                    n3 = context.getApplicationInfo().targetSdkVersion >= 11 ? 1 : 0;
                                    if (n2 != 0 && n3 != 0) break block12;
                                }
                                if ((context = this.getWindowCallback()) != null && !context.onMenuOpened(panelFeatureState.featureId, (Menu)panelFeatureState.menu)) {
                                    this.closePanel(panelFeatureState, true);
                                    return;
                                }
                                windowManager = (WindowManager)this.mContext.getSystemService("window");
                                if (windowManager == null || !this.preparePanel(panelFeatureState, keyEvent)) break block12;
                                n3 = -2;
                                if (panelFeatureState.decorView != null && !panelFeatureState.refreshDecorView) break block14;
                                if (panelFeatureState.decorView != null) break block15;
                                if (!this.initializePanelDecor(panelFeatureState) || panelFeatureState.decorView == null) break block12;
                                break block16;
                            }
                            if (panelFeatureState.refreshDecorView && panelFeatureState.decorView.getChildCount() > 0) {
                                panelFeatureState.decorView.removeAllViews();
                            }
                        }
                        if (this.initializePanelContent(panelFeatureState) && panelFeatureState.hasPanelItems()) break block17;
                    }
                    return;
                }
                context = panelFeatureState.shownPanelView.getLayoutParams();
                keyEvent = context;
                if (context == null) {
                    keyEvent = new ViewGroup.LayoutParams(-2, -2);
                }
                n2 = panelFeatureState.background;
                panelFeatureState.decorView.setBackgroundResource(n2);
                context = panelFeatureState.shownPanelView.getParent();
                if (context != null && context instanceof ViewGroup) {
                    ((ViewGroup)context).removeView(panelFeatureState.shownPanelView);
                }
                panelFeatureState.decorView.addView(panelFeatureState.shownPanelView, (ViewGroup.LayoutParams)keyEvent);
                n2 = n3;
                if (!panelFeatureState.shownPanelView.hasFocus()) {
                    panelFeatureState.shownPanelView.requestFocus();
                    n2 = n3;
                }
                break block18;
            }
            n2 = n3;
            if (panelFeatureState.createdPanelView != null) {
                keyEvent = panelFeatureState.createdPanelView.getLayoutParams();
                n2 = n3;
                if (keyEvent != null) {
                    n2 = n3;
                    if (keyEvent.width == -1) {
                        n2 = -1;
                    }
                }
            }
        }
        panelFeatureState.isHandled = false;
        keyEvent = new WindowManager.LayoutParams(n2, -2, panelFeatureState.x, panelFeatureState.y, 1002, 0x820000, -3);
        keyEvent.gravity = panelFeatureState.gravity;
        keyEvent.windowAnimations = panelFeatureState.windowAnimations;
        windowManager.addView((View)panelFeatureState.decorView, (ViewGroup.LayoutParams)keyEvent);
        panelFeatureState.isOpen = true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean performPanelShortcut(PanelFeatureState panelFeatureState, int n2, KeyEvent keyEvent, int n3) {
        boolean bl2;
        boolean bl3;
        block6: {
            block5: {
                if (keyEvent.isSystem()) {
                    return false;
                }
                bl3 = false;
                if (panelFeatureState.isPrepared) break block5;
                bl2 = bl3;
                if (!this.preparePanel(panelFeatureState, keyEvent)) break block6;
            }
            bl2 = bl3;
            if (panelFeatureState.menu != null) {
                bl2 = panelFeatureState.menu.performShortcut(n2, keyEvent, n3);
            }
        }
        bl3 = bl2;
        if (!bl2) return bl3;
        bl3 = bl2;
        if ((n3 & 1) != 0) return bl3;
        bl3 = bl2;
        if (this.mDecorContentParent != null) return bl3;
        this.closePanel(panelFeatureState, true);
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean preparePanel(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        block13: {
            int n2;
            Window.Callback callback;
            block14: {
                block15: {
                    block16: {
                        block12: {
                            if (this.isDestroyed()) break block12;
                            if (panelFeatureState.isPrepared) {
                                return true;
                            }
                            if (this.mPreparedPanel != null && this.mPreparedPanel != panelFeatureState) {
                                this.closePanel(this.mPreparedPanel, false);
                            }
                            if ((callback = this.getWindowCallback()) != null) {
                                panelFeatureState.createdPanelView = callback.onCreatePanelView(panelFeatureState.featureId);
                            }
                            if ((n2 = panelFeatureState.featureId == 0 || panelFeatureState.featureId == 108 ? 1 : 0) != 0 && this.mDecorContentParent != null) {
                                this.mDecorContentParent.setMenuPrepared();
                            }
                            if (panelFeatureState.createdPanelView != null || n2 != 0 && this.peekSupportActionBar() instanceof ToolbarActionBar) break block13;
                            if (panelFeatureState.menu != null && !panelFeatureState.refreshMenuContent) break block14;
                            if (panelFeatureState.menu == null && (!this.initializePanelMenu(panelFeatureState) || panelFeatureState.menu == null)) break block12;
                            if (n2 != 0 && this.mDecorContentParent != null) {
                                if (this.mActionMenuPresenterCallback == null) {
                                    this.mActionMenuPresenterCallback = new ActionMenuPresenterCallback();
                                }
                                this.mDecorContentParent.setMenu(panelFeatureState.menu, this.mActionMenuPresenterCallback);
                            }
                            panelFeatureState.menu.stopDispatchingItemsChanged();
                            if (callback.onCreatePanelMenu(panelFeatureState.featureId, (Menu)panelFeatureState.menu)) break block15;
                            panelFeatureState.setMenu(null);
                            if (n2 != 0 && this.mDecorContentParent != null) break block16;
                        }
                        return false;
                    }
                    this.mDecorContentParent.setMenu(null, this.mActionMenuPresenterCallback);
                    return false;
                }
                panelFeatureState.refreshMenuContent = false;
            }
            panelFeatureState.menu.stopDispatchingItemsChanged();
            if (panelFeatureState.frozenActionViewState != null) {
                panelFeatureState.menu.restoreActionViewStates(panelFeatureState.frozenActionViewState);
                panelFeatureState.frozenActionViewState = null;
            }
            if (!callback.onPreparePanel(0, panelFeatureState.createdPanelView, (Menu)panelFeatureState.menu)) {
                if (n2 != 0 && this.mDecorContentParent != null) {
                    this.mDecorContentParent.setMenu(null, this.mActionMenuPresenterCallback);
                }
                panelFeatureState.menu.startDispatchingItemsChanged();
                return false;
            }
            n2 = keyEvent != null ? keyEvent.getDeviceId() : -1;
            boolean bl2 = KeyCharacterMap.load((int)n2).getKeyboardType() != 1;
            panelFeatureState.qwertyMode = bl2;
            panelFeatureState.menu.setQwertyMode(panelFeatureState.qwertyMode);
            panelFeatureState.menu.startDispatchingItemsChanged();
        }
        panelFeatureState.isPrepared = true;
        panelFeatureState.isHandled = false;
        this.mPreparedPanel = panelFeatureState;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void reopenMenu(MenuBuilder object, boolean bl2) {
        if (this.mDecorContentParent != null && this.mDecorContentParent.canShowOverflowMenu() && (!ViewConfigurationCompat.hasPermanentMenuKey(ViewConfiguration.get((Context)this.mContext)) || this.mDecorContentParent.isOverflowMenuShowPending())) {
            object = this.getWindowCallback();
            if (!this.mDecorContentParent.isOverflowMenuShowing() || !bl2) {
                if (object == null || this.isDestroyed()) return;
                if (this.mInvalidatePanelMenuPosted && (this.mInvalidatePanelMenuFeatures & 1) != 0) {
                    this.mWindow.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable);
                    this.mInvalidatePanelMenuRunnable.run();
                }
                PanelFeatureState panelFeatureState = this.getPanelState(0, true);
                if (panelFeatureState.menu == null || panelFeatureState.refreshMenuContent || !object.onPreparePanel(0, panelFeatureState.createdPanelView, (Menu)panelFeatureState.menu)) return;
                object.onMenuOpened(108, (Menu)panelFeatureState.menu);
                this.mDecorContentParent.showOverflowMenu();
                return;
            }
            this.mDecorContentParent.hideOverflowMenu();
            if (this.isDestroyed()) return;
            object.onPanelClosed(108, (Menu)this.getPanelState((int)0, (boolean)true).menu);
            return;
        }
        object = this.getPanelState(0, true);
        ((PanelFeatureState)object).refreshDecorView = true;
        this.closePanel((PanelFeatureState)object, false);
        this.openPanel((PanelFeatureState)object, null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int sanitizeWindowFeatureId(int n2) {
        if (n2 == 8) {
            Log.i((String)"AppCompatDelegate", (String)"You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
            return 108;
        }
        int n3 = n2;
        if (n2 != 9) return n3;
        Log.i((String)"AppCompatDelegate", (String)"You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
        return 109;
    }

    private boolean shouldInheritContext(ViewParent viewParent) {
        if (viewParent == null) {
            return false;
        }
        View view = this.mWindow.getDecorView();
        while (viewParent != null) {
            if (viewParent == view || !(viewParent instanceof View) || ViewCompat.isAttachedToWindow((View)viewParent)) {
                return false;
            }
            viewParent = viewParent.getParent();
        }
        return true;
    }

    private void throwFeatureRequestIfSubDecorInstalled() {
        if (this.mSubDecorInstalled) {
            throw new AndroidRuntimeException("Window feature must be requested before adding content");
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private int updateStatusGuard(int n2) {
        View view;
        int n3;
        int n4;
        int n5;
        block10: {
            int n6;
            int n7;
            int n8;
            block12: {
                int n9;
                block11: {
                    n5 = 0;
                    n8 = 0;
                    n9 = 0;
                    n4 = n8;
                    n3 = n2;
                    if (this.mActionModeView == null) break block10;
                    n4 = n8;
                    n3 = n2;
                    if (!(this.mActionModeView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) break block10;
                    view = (ViewGroup.MarginLayoutParams)this.mActionModeView.getLayoutParams();
                    n8 = 0;
                    n3 = 0;
                    if (!this.mActionModeView.isShown()) break block11;
                    if (this.mTempRect1 == null) {
                        this.mTempRect1 = new Rect();
                        this.mTempRect2 = new Rect();
                    }
                    Rect rect = this.mTempRect1;
                    Rect rect2 = this.mTempRect2;
                    rect.set(0, n2, 0, 0);
                    ViewUtils.computeFitSystemWindows((View)this.mSubDecor, rect, rect2);
                    n8 = rect2.top == 0 ? n2 : 0;
                    if (view.topMargin != n8) {
                        n8 = 1;
                        view.topMargin = n2;
                        if (this.mStatusGuard == null) {
                            this.mStatusGuard = new View(this.mContext);
                            this.mStatusGuard.setBackgroundColor(this.mContext.getResources().getColor(R.color.abc_input_method_navigation_guard));
                            this.mSubDecor.addView(this.mStatusGuard, -1, new ViewGroup.LayoutParams(-1, n2));
                            n3 = n8;
                        } else {
                            rect = this.mStatusGuard.getLayoutParams();
                            n3 = n8;
                            if (rect.height != n2) {
                                rect.height = n2;
                                this.mStatusGuard.setLayoutParams((ViewGroup.LayoutParams)rect);
                                n3 = n8;
                            }
                        }
                    }
                    n4 = this.mStatusGuard != null ? 1 : 0;
                    n8 = n3;
                    n7 = n4;
                    n6 = n2;
                    if (!this.mOverlayActionMode) {
                        n8 = n3;
                        n7 = n4;
                        n6 = n2;
                        if (n4 != 0) {
                            n6 = 0;
                            n7 = n4;
                            n8 = n3;
                        }
                    }
                    break block12;
                }
                n7 = n9;
                n6 = n2;
                if (view.topMargin != 0) {
                    n8 = 1;
                    view.topMargin = 0;
                    n7 = n9;
                    n6 = n2;
                }
            }
            n4 = n7;
            n3 = n6;
            if (n8 != 0) {
                this.mActionModeView.setLayoutParams((ViewGroup.LayoutParams)view);
                n3 = n6;
                n4 = n7;
            }
        }
        if (this.mStatusGuard != null) {
            view = this.mStatusGuard;
            n2 = n4 != 0 ? n5 : 8;
            view.setVisibility(n2);
        }
        return n3;
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.ensureSubDecor();
        ((ViewGroup)this.mSubDecor.findViewById(0x1020002)).addView(view, layoutParams);
        this.mOriginalWindowCallback.onContentChanged();
    }

    View callActivityOnCreateView(View view, String string2, Context context, AttributeSet attributeSet) {
        if (this.mOriginalWindowCallback instanceof LayoutInflater.Factory && (view = ((LayoutInflater.Factory)this.mOriginalWindowCallback).onCreateView(string2, context, attributeSet)) != null) {
            return view;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public View createView(View view, String string2, @NonNull Context context, @NonNull AttributeSet attributeSet) {
        boolean bl2;
        boolean bl3 = Build.VERSION.SDK_INT < 21;
        if (this.mAppCompatViewInflater == null) {
            this.mAppCompatViewInflater = new AppCompatViewInflater();
        }
        if (bl3 && this.shouldInheritContext((ViewParent)view)) {
            bl2 = true;
            return this.mAppCompatViewInflater.createView(view, string2, context, attributeSet, bl2, bl3, true);
        }
        bl2 = false;
        return this.mAppCompatViewInflater.createView(view, string2, context, attributeSet, bl2, bl3, true);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 82 && this.mOriginalWindowCallback.dispatchKeyEvent(keyEvent)) {
            return true;
        }
        int n2 = keyEvent.getKeyCode();
        boolean bl2 = keyEvent.getAction() == 0;
        if (bl2) {
            return this.onKeyDown(n2, keyEvent);
        }
        return this.onKeyUp(n2, keyEvent);
    }

    @Override
    @Nullable
    public View findViewById(@IdRes int n2) {
        this.ensureSubDecor();
        return this.mWindow.findViewById(n2);
    }

    ViewGroup getSubDecor() {
        return this.mSubDecor;
    }

    @Override
    public boolean hasWindowFeature(int n2) {
        n2 = this.sanitizeWindowFeatureId(n2);
        switch (n2) {
            default: {
                return this.mWindow.hasFeature(n2);
            }
            case 108: {
                return this.mHasActionBar;
            }
            case 109: {
                return this.mOverlayActionBar;
            }
            case 10: {
                return this.mOverlayActionMode;
            }
            case 2: {
                return this.mFeatureProgress;
            }
            case 5: {
                return this.mFeatureIndeterminateProgress;
            }
            case 1: 
        }
        return this.mWindowNoTitle;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void initWindowDecorActionBar() {
        block7: {
            block6: {
                this.ensureSubDecor();
                if (!this.mHasActionBar || this.mActionBar != null) break block6;
                if (this.mOriginalWindowCallback instanceof Activity) {
                    this.mActionBar = new WindowDecorActionBar((Activity)this.mOriginalWindowCallback, this.mOverlayActionBar);
                } else if (this.mOriginalWindowCallback instanceof Dialog) {
                    this.mActionBar = new WindowDecorActionBar((Dialog)this.mOriginalWindowCallback);
                }
                if (this.mActionBar != null) break block7;
            }
            return;
        }
        this.mActionBar.setDefaultDisplayHomeAsUpEnabled(this.mEnableDefaultActionBarUp);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void installViewFactory() {
        LayoutInflater layoutInflater = LayoutInflater.from((Context)this.mContext);
        if (layoutInflater.getFactory() == null) {
            LayoutInflaterCompat.setFactory(layoutInflater, this);
            return;
        } else {
            if (LayoutInflaterCompat.getFactory(layoutInflater) instanceof AppCompatDelegateImplV7) return;
            Log.i((String)"AppCompatDelegate", (String)"The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
            return;
        }
    }

    @Override
    public void invalidateOptionsMenu() {
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null && actionBar.invalidateOptionsMenu()) {
            return;
        }
        this.invalidatePanelMenu(0);
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean onBackPressed() {
        if (this.mActionMode != null) {
            this.mActionMode.finish();
            return true;
        } else {
            ActionBar actionBar = this.getSupportActionBar();
            if (actionBar != null && actionBar.collapseActionView()) return true;
            return false;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        ActionBar actionBar;
        if (this.mHasActionBar && this.mSubDecorInstalled && (actionBar = this.getSupportActionBar()) != null) {
            actionBar.onConfigurationChanged(configuration);
        }
    }

    @Override
    public void onCreate(Bundle object) {
        block3: {
            block2: {
                if (!(this.mOriginalWindowCallback instanceof Activity) || NavUtils.getParentActivityName((Activity)this.mOriginalWindowCallback) == null) break block2;
                object = this.peekSupportActionBar();
                if (object != null) break block3;
                this.mEnableDefaultActionBarUp = true;
            }
            return;
        }
        ((ActionBar)object).setDefaultDisplayHomeAsUpEnabled(true);
    }

    @Override
    public final View onCreateView(View view, String string2, Context context, AttributeSet attributeSet) {
        View view2 = this.callActivityOnCreateView(view, string2, context, attributeSet);
        if (view2 != null) {
            return view2;
        }
        return this.createView(view, string2, context, attributeSet);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.mActionBar != null) {
            this.mActionBar.onDestroy();
            this.mActionBar = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean onKeyDown(int n2, KeyEvent keyEvent) {
        boolean bl2 = true;
        switch (n2) {
            default: {
                break;
            }
            case 82: {
                this.onKeyDownPanel(0, keyEvent);
                return true;
            }
            case 4: {
                if ((keyEvent.getFlags() & 0x80) == 0) {
                    bl2 = false;
                }
                this.mLongPressBackDown = bl2;
            }
        }
        if (Build.VERSION.SDK_INT < 11) {
            this.onKeyShortcut(n2, keyEvent);
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    boolean onKeyShortcut(int n2, KeyEvent keyEvent) {
        Object object = this.getSupportActionBar();
        if (object != null && ((ActionBar)object).onKeyShortcut(n2, keyEvent)) return true;
        if (this.mPreparedPanel != null && this.performPanelShortcut(this.mPreparedPanel, keyEvent.getKeyCode(), keyEvent, 1)) {
            if (this.mPreparedPanel == null) return true;
            this.mPreparedPanel.isHandled = true;
            return true;
        }
        if (this.mPreparedPanel != null) return false;
        object = this.getPanelState(0, true);
        this.preparePanel((PanelFeatureState)object, keyEvent);
        boolean bl2 = this.performPanelShortcut((PanelFeatureState)object, keyEvent.getKeyCode(), keyEvent, 1);
        ((PanelFeatureState)object).isPrepared = false;
        if (!bl2) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    boolean onKeyUp(int n2, KeyEvent object) {
        boolean bl2 = true;
        switch (n2) {
            default: {
                return false;
            }
            case 82: {
                this.onKeyUpPanel(0, (KeyEvent)object);
                return true;
            }
            case 4: 
        }
        boolean bl3 = this.mLongPressBackDown;
        this.mLongPressBackDown = false;
        PanelFeatureState panelFeatureState = this.getPanelState(0, false);
        if (panelFeatureState != null && panelFeatureState.isOpen) {
            if (bl3) return bl2;
            this.closePanel(panelFeatureState, true);
            return true;
        }
        if (!this.onBackPressed()) return false;
        return true;
    }

    @Override
    public boolean onMenuItemSelected(MenuBuilder object, MenuItem menuItem) {
        Window.Callback callback = this.getWindowCallback();
        if (callback != null && !this.isDestroyed() && (object = this.findMenuPanel(((MenuBuilder)object).getRootMenu())) != null) {
            return callback.onMenuItemSelected(((PanelFeatureState)object).featureId, menuItem);
        }
        return false;
    }

    @Override
    public void onMenuModeChange(MenuBuilder menuBuilder) {
        this.reopenMenu(menuBuilder, true);
    }

    @Override
    boolean onMenuOpened(int n2, Menu object) {
        if (n2 == 108) {
            object = this.getSupportActionBar();
            if (object != null) {
                ((ActionBar)object).dispatchMenuVisibilityChanged(true);
            }
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    void onPanelClosed(int n2, Menu object) {
        if (n2 == 108) {
            ActionBar actionBar = this.getSupportActionBar();
            if (actionBar == null) return;
            actionBar.dispatchMenuVisibilityChanged(false);
            return;
        }
        if (n2 != 0) return;
        PanelFeatureState panelFeatureState = this.getPanelState(n2, true);
        if (!panelFeatureState.isOpen) return;
        this.closePanel(panelFeatureState, false);
    }

    @Override
    public void onPostCreate(Bundle bundle) {
        this.ensureSubDecor();
    }

    @Override
    public void onPostResume() {
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setShowHideAnimationEnabled(true);
        }
    }

    @Override
    public void onStop() {
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setShowHideAnimationEnabled(false);
        }
    }

    void onSubDecorInstalled(ViewGroup viewGroup) {
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    void onTitleChanged(CharSequence charSequence) {
        if (this.mDecorContentParent != null) {
            this.mDecorContentParent.setWindowTitle(charSequence);
            return;
        } else {
            if (this.peekSupportActionBar() != null) {
                this.peekSupportActionBar().setWindowTitle(charSequence);
                return;
            }
            if (this.mTitleView == null) return;
            this.mTitleView.setText(charSequence);
            return;
        }
    }

    @Override
    public boolean requestWindowFeature(int n2) {
        n2 = this.sanitizeWindowFeatureId(n2);
        if (this.mWindowNoTitle && n2 == 108) {
            return false;
        }
        if (this.mHasActionBar && n2 == 1) {
            this.mHasActionBar = false;
        }
        switch (n2) {
            default: {
                return this.mWindow.requestFeature(n2);
            }
            case 108: {
                this.throwFeatureRequestIfSubDecorInstalled();
                this.mHasActionBar = true;
                return true;
            }
            case 109: {
                this.throwFeatureRequestIfSubDecorInstalled();
                this.mOverlayActionBar = true;
                return true;
            }
            case 10: {
                this.throwFeatureRequestIfSubDecorInstalled();
                this.mOverlayActionMode = true;
                return true;
            }
            case 2: {
                this.throwFeatureRequestIfSubDecorInstalled();
                this.mFeatureProgress = true;
                return true;
            }
            case 5: {
                this.throwFeatureRequestIfSubDecorInstalled();
                this.mFeatureIndeterminateProgress = true;
                return true;
            }
            case 1: 
        }
        this.throwFeatureRequestIfSubDecorInstalled();
        this.mWindowNoTitle = true;
        return true;
    }

    @Override
    public void setContentView(int n2) {
        this.ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup)this.mSubDecor.findViewById(0x1020002);
        viewGroup.removeAllViews();
        LayoutInflater.from((Context)this.mContext).inflate(n2, viewGroup);
        this.mOriginalWindowCallback.onContentChanged();
    }

    @Override
    public void setContentView(View view) {
        this.ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup)this.mSubDecor.findViewById(0x1020002);
        viewGroup.removeAllViews();
        viewGroup.addView(view);
        this.mOriginalWindowCallback.onContentChanged();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup)this.mSubDecor.findViewById(0x1020002);
        viewGroup.removeAllViews();
        viewGroup.addView(view, layoutParams);
        this.mOriginalWindowCallback.onContentChanged();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setSupportActionBar(Toolbar object) {
        if (!(this.mOriginalWindowCallback instanceof Activity)) {
            return;
        }
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar instanceof WindowDecorActionBar) {
            throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
        }
        this.mMenuInflater = null;
        if (actionBar != null) {
            actionBar.onDestroy();
        }
        if (object != null) {
            this.mActionBar = object = new ToolbarActionBar((Toolbar)((Object)object), ((Activity)this.mContext).getTitle(), this.mAppCompatWindowCallback);
            this.mWindow.setCallback(((ToolbarActionBar)object).getWrappedWindowCallback());
        } else {
            this.mActionBar = null;
            this.mWindow.setCallback(this.mAppCompatWindowCallback);
        }
        this.invalidateOptionsMenu();
    }

    @Override
    public ActionMode startSupportActionMode(ActionMode.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("ActionMode callback can not be null.");
        }
        if (this.mActionMode != null) {
            this.mActionMode.finish();
        }
        callback = new ActionModeCallbackWrapperV7(callback);
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            this.mActionMode = actionBar.startActionMode(callback);
            if (this.mActionMode != null && this.mAppCompatCallback != null) {
                this.mAppCompatCallback.onSupportActionModeStarted(this.mActionMode);
            }
        }
        if (this.mActionMode == null) {
            this.mActionMode = this.startSupportActionModeFromWindow(callback);
        }
        return this.mActionMode;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    ActionMode startSupportActionModeFromWindow(ActionMode.Callback callback) {
        void var4_10;
        Object var5_3;
        this.endOnGoingFadeAnimation();
        if (this.mActionMode != null) {
            this.mActionMode.finish();
        }
        ActionModeCallbackWrapperV7 actionModeCallbackWrapperV7 = new ActionModeCallbackWrapperV7(callback);
        Object var4_7 = var5_3 = null;
        if (this.mAppCompatCallback != null) {
            Object var4_8 = var5_3;
            if (!this.isDestroyed()) {
                try {
                    ActionMode actionMode = this.mAppCompatCallback.onWindowStartingSupportActionMode(actionModeCallbackWrapperV7);
                }
                catch (AbstractMethodError abstractMethodError) {
                    Object var4_20 = var5_3;
                }
            }
        }
        if (var4_10 != null) {
            this.mActionMode = var4_10;
        } else {
            if (this.mActionModeView == null) {
                if (this.mIsFloating) {
                    void var4_13;
                    TypedValue typedValue = new TypedValue();
                    Resources.Theme theme = this.mContext.getTheme();
                    theme.resolveAttribute(R.attr.actionBarTheme, typedValue, true);
                    if (typedValue.resourceId != 0) {
                        Resources.Theme theme2 = this.mContext.getResources().newTheme();
                        theme2.setTo(theme);
                        theme2.applyStyle(typedValue.resourceId, true);
                        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(this.mContext, 0);
                        contextThemeWrapper.getTheme().setTo(theme2);
                    } else {
                        Context context = this.mContext;
                    }
                    this.mActionModeView = new ActionBarContextView((Context)var4_13);
                    this.mActionModePopup = new PopupWindow((Context)var4_13, null, R.attr.actionModePopupWindowStyle);
                    PopupWindowCompat.setWindowLayoutType(this.mActionModePopup, 2);
                    this.mActionModePopup.setContentView((View)this.mActionModeView);
                    this.mActionModePopup.setWidth(-1);
                    var4_13.getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true);
                    int n2 = TypedValue.complexToDimensionPixelSize((int)typedValue.data, (DisplayMetrics)var4_13.getResources().getDisplayMetrics());
                    this.mActionModeView.setContentHeight(n2);
                    this.mActionModePopup.setHeight(-2);
                    this.mShowActionModePopup = new Runnable(){

                        @Override
                        public void run() {
                            AppCompatDelegateImplV7.this.mActionModePopup.showAtLocation((View)AppCompatDelegateImplV7.this.mActionModeView, 55, 0, 0);
                            AppCompatDelegateImplV7.this.endOnGoingFadeAnimation();
                            ViewCompat.setAlpha((View)AppCompatDelegateImplV7.this.mActionModeView, 0.0f);
                            AppCompatDelegateImplV7.this.mFadeAnim = ViewCompat.animate((View)AppCompatDelegateImplV7.this.mActionModeView).alpha(1.0f);
                            AppCompatDelegateImplV7.this.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter(){

                                @Override
                                public void onAnimationEnd(View view) {
                                    ViewCompat.setAlpha((View)AppCompatDelegateImplV7.this.mActionModeView, 1.0f);
                                    AppCompatDelegateImplV7.this.mFadeAnim.setListener(null);
                                    AppCompatDelegateImplV7.this.mFadeAnim = null;
                                }

                                @Override
                                public void onAnimationStart(View view) {
                                    AppCompatDelegateImplV7.this.mActionModeView.setVisibility(0);
                                }
                            });
                        }
                    };
                } else {
                    ViewStubCompat viewStubCompat = (ViewStubCompat)this.mSubDecor.findViewById(R.id.action_mode_bar_stub);
                    if (viewStubCompat != null) {
                        viewStubCompat.setLayoutInflater(LayoutInflater.from((Context)this.getActionBarThemedContext()));
                        this.mActionModeView = (ActionBarContextView)viewStubCompat.inflate();
                    }
                }
            }
            if (this.mActionModeView != null) {
                this.endOnGoingFadeAnimation();
                this.mActionModeView.killMode();
                Context context = this.mActionModeView.getContext();
                ActionBarContextView actionBarContextView = this.mActionModeView;
                boolean bl2 = this.mActionModePopup == null;
                StandaloneActionMode standaloneActionMode = new StandaloneActionMode(context, actionBarContextView, actionModeCallbackWrapperV7, bl2);
                if (callback.onCreateActionMode(standaloneActionMode, ((ActionMode)standaloneActionMode).getMenu())) {
                    ((ActionMode)standaloneActionMode).invalidate();
                    this.mActionModeView.initForMode(standaloneActionMode);
                    this.mActionMode = standaloneActionMode;
                    ViewCompat.setAlpha((View)this.mActionModeView, 0.0f);
                    this.mFadeAnim = ViewCompat.animate((View)this.mActionModeView).alpha(1.0f);
                    this.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter(){

                        @Override
                        public void onAnimationEnd(View view) {
                            ViewCompat.setAlpha((View)AppCompatDelegateImplV7.this.mActionModeView, 1.0f);
                            AppCompatDelegateImplV7.this.mFadeAnim.setListener(null);
                            AppCompatDelegateImplV7.this.mFadeAnim = null;
                        }

                        @Override
                        public void onAnimationStart(View view) {
                            AppCompatDelegateImplV7.this.mActionModeView.setVisibility(0);
                            AppCompatDelegateImplV7.this.mActionModeView.sendAccessibilityEvent(32);
                            if (AppCompatDelegateImplV7.this.mActionModeView.getParent() != null) {
                                ViewCompat.requestApplyInsets((View)AppCompatDelegateImplV7.this.mActionModeView.getParent());
                            }
                        }
                    });
                    if (this.mActionModePopup != null) {
                        this.mWindow.getDecorView().post(this.mShowActionModePopup);
                    }
                } else {
                    this.mActionMode = null;
                }
            }
        }
        if (this.mActionMode != null && this.mAppCompatCallback != null) {
            this.mAppCompatCallback.onSupportActionModeStarted(this.mActionMode);
        }
        return this.mActionMode;
    }

    private final class ActionMenuPresenterCallback
    implements MenuPresenter.Callback {
        private ActionMenuPresenterCallback() {
        }

        @Override
        public void onCloseMenu(MenuBuilder menuBuilder, boolean bl2) {
            AppCompatDelegateImplV7.this.checkCloseActionMenu(menuBuilder);
        }

        @Override
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            Window.Callback callback = AppCompatDelegateImplV7.this.getWindowCallback();
            if (callback != null) {
                callback.onMenuOpened(108, (Menu)menuBuilder);
            }
            return true;
        }
    }

    class ActionModeCallbackWrapperV7
    implements ActionMode.Callback {
        private ActionMode.Callback mWrapped;

        public ActionModeCallbackWrapperV7(ActionMode.Callback callback) {
            this.mWrapped = callback;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.mWrapped.onActionItemClicked(actionMode, menuItem);
        }

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return this.mWrapped.onCreateActionMode(actionMode, menu);
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            this.mWrapped.onDestroyActionMode(actionMode);
            if (AppCompatDelegateImplV7.this.mActionModePopup != null) {
                AppCompatDelegateImplV7.this.mWindow.getDecorView().removeCallbacks(AppCompatDelegateImplV7.this.mShowActionModePopup);
            }
            if (AppCompatDelegateImplV7.this.mActionModeView != null) {
                AppCompatDelegateImplV7.this.endOnGoingFadeAnimation();
                AppCompatDelegateImplV7.this.mFadeAnim = ViewCompat.animate((View)AppCompatDelegateImplV7.this.mActionModeView).alpha(0.0f);
                AppCompatDelegateImplV7.this.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter(){

                    /*
                     * Enabled aggressive block sorting
                     */
                    @Override
                    public void onAnimationEnd(View view) {
                        AppCompatDelegateImplV7.this.mActionModeView.setVisibility(8);
                        if (AppCompatDelegateImplV7.this.mActionModePopup != null) {
                            AppCompatDelegateImplV7.this.mActionModePopup.dismiss();
                        } else if (AppCompatDelegateImplV7.this.mActionModeView.getParent() instanceof View) {
                            ViewCompat.requestApplyInsets((View)AppCompatDelegateImplV7.this.mActionModeView.getParent());
                        }
                        AppCompatDelegateImplV7.this.mActionModeView.removeAllViews();
                        AppCompatDelegateImplV7.this.mFadeAnim.setListener(null);
                        AppCompatDelegateImplV7.this.mFadeAnim = null;
                    }
                });
            }
            if (AppCompatDelegateImplV7.this.mAppCompatCallback != null) {
                AppCompatDelegateImplV7.this.mAppCompatCallback.onSupportActionModeFinished(AppCompatDelegateImplV7.this.mActionMode);
            }
            AppCompatDelegateImplV7.this.mActionMode = null;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return this.mWrapped.onPrepareActionMode(actionMode, menu);
        }
    }

    private class ListMenuDecorView
    extends ContentFrameLayout {
        public ListMenuDecorView(Context context) {
            super(context);
        }

        private boolean isOutOfBounds(int n2, int n3) {
            return n2 < -5 || n3 < -5 || n2 > this.getWidth() + 5 || n3 > this.getHeight() + 5;
        }

        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return AppCompatDelegateImplV7.this.dispatchKeyEvent(keyEvent) || super.dispatchKeyEvent(keyEvent);
        }

        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0 && this.isOutOfBounds((int)motionEvent.getX(), (int)motionEvent.getY())) {
                AppCompatDelegateImplV7.this.closePanel(0);
                return true;
            }
            return super.onInterceptTouchEvent(motionEvent);
        }

        public void setBackgroundResource(int n2) {
            this.setBackgroundDrawable(AppCompatDrawableManager.get().getDrawable(this.getContext(), n2));
        }
    }

    private static final class PanelFeatureState {
        int background;
        View createdPanelView;
        ViewGroup decorView;
        int featureId;
        Bundle frozenActionViewState;
        Bundle frozenMenuState;
        int gravity;
        boolean isHandled;
        boolean isOpen;
        boolean isPrepared;
        ListMenuPresenter listMenuPresenter;
        Context listPresenterContext;
        MenuBuilder menu;
        public boolean qwertyMode;
        boolean refreshDecorView;
        boolean refreshMenuContent;
        View shownPanelView;
        boolean wasLastOpen;
        int windowAnimations;
        int x;
        int y;

        PanelFeatureState(int n2) {
            this.featureId = n2;
            this.refreshDecorView = false;
        }

        void applyFrozenState() {
            if (this.menu != null && this.frozenMenuState != null) {
                this.menu.restorePresenterStates(this.frozenMenuState);
                this.frozenMenuState = null;
            }
        }

        public void clearMenuPresenters() {
            if (this.menu != null) {
                this.menu.removeMenuPresenter(this.listMenuPresenter);
            }
            this.listMenuPresenter = null;
        }

        MenuView getListMenuView(MenuPresenter.Callback callback) {
            if (this.menu == null) {
                return null;
            }
            if (this.listMenuPresenter == null) {
                this.listMenuPresenter = new ListMenuPresenter(this.listPresenterContext, R.layout.abc_list_menu_item_layout);
                this.listMenuPresenter.setCallback(callback);
                this.menu.addMenuPresenter(this.listMenuPresenter);
            }
            return this.listMenuPresenter.getMenuView(this.decorView);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean hasPanelItems() {
            boolean bl2 = true;
            if (this.shownPanelView == null) {
                return false;
            }
            boolean bl3 = bl2;
            if (this.createdPanelView != null) return bl3;
            bl3 = bl2;
            if (this.listMenuPresenter.getAdapter().getCount() > 0) return bl3;
            return false;
        }

        void onRestoreInstanceState(Parcelable parcelable) {
            parcelable = (SavedState)parcelable;
            this.featureId = parcelable.featureId;
            this.wasLastOpen = parcelable.isOpen;
            this.frozenMenuState = parcelable.menuState;
            this.shownPanelView = null;
            this.decorView = null;
        }

        Parcelable onSaveInstanceState() {
            SavedState savedState = new SavedState();
            savedState.featureId = this.featureId;
            savedState.isOpen = this.isOpen;
            if (this.menu != null) {
                savedState.menuState = new Bundle();
                this.menu.savePresenterStates(savedState.menuState);
            }
            return savedState;
        }

        /*
         * Enabled aggressive block sorting
         */
        void setMenu(MenuBuilder menuBuilder) {
            block5: {
                block4: {
                    if (menuBuilder == this.menu) break block4;
                    if (this.menu != null) {
                        this.menu.removeMenuPresenter(this.listMenuPresenter);
                    }
                    this.menu = menuBuilder;
                    if (menuBuilder != null && this.listMenuPresenter != null) break block5;
                }
                return;
            }
            menuBuilder.addMenuPresenter(this.listMenuPresenter);
        }

        /*
         * Enabled aggressive block sorting
         */
        void setStyle(Context object) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = object.getResources().newTheme();
            theme.setTo(object.getTheme());
            theme.resolveAttribute(R.attr.actionBarPopupTheme, typedValue, true);
            if (typedValue.resourceId != 0) {
                theme.applyStyle(typedValue.resourceId, true);
            }
            theme.resolveAttribute(R.attr.panelMenuListTheme, typedValue, true);
            if (typedValue.resourceId != 0) {
                theme.applyStyle(typedValue.resourceId, true);
            } else {
                theme.applyStyle(R.style.Theme_AppCompat_CompactMenu, true);
            }
            ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper((Context)object, 0);
            contextThemeWrapper.getTheme().setTo(theme);
            this.listPresenterContext = contextThemeWrapper;
            TypedArray typedArray = contextThemeWrapper.obtainStyledAttributes(R.styleable.AppCompatTheme);
            this.background = typedArray.getResourceId(R.styleable.AppCompatTheme_panelBackground, 0);
            this.windowAnimations = typedArray.getResourceId(R.styleable.AppCompatTheme_android_windowAnimationStyle, 0);
            typedArray.recycle();
        }

        private static class SavedState
        implements Parcelable {
            public static final Parcelable.Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<SavedState>(){

                @Override
                public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                    return SavedState.readFromParcel(parcel, classLoader);
                }

                public SavedState[] newArray(int n2) {
                    return new SavedState[n2];
                }
            });
            int featureId;
            boolean isOpen;
            Bundle menuState;

            private SavedState() {
            }

            /*
             * Enabled aggressive block sorting
             */
            private static SavedState readFromParcel(Parcel parcel, ClassLoader classLoader) {
                boolean bl2 = true;
                SavedState savedState = new SavedState();
                savedState.featureId = parcel.readInt();
                if (parcel.readInt() != 1) {
                    bl2 = false;
                }
                savedState.isOpen = bl2;
                if (savedState.isOpen) {
                    savedState.menuState = parcel.readBundle(classLoader);
                }
                return savedState;
            }

            public int describeContents() {
                return 0;
            }

            /*
             * Enabled aggressive block sorting
             */
            public void writeToParcel(Parcel parcel, int n2) {
                parcel.writeInt(this.featureId);
                n2 = this.isOpen ? 1 : 0;
                parcel.writeInt(n2);
                if (this.isOpen) {
                    parcel.writeBundle(this.menuState);
                }
            }
        }
    }

    private final class PanelMenuPresenterCallback
    implements MenuPresenter.Callback {
        private PanelMenuPresenterCallback() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onCloseMenu(MenuBuilder object, boolean bl2) {
            MenuBuilder menuBuilder = ((MenuBuilder)object).getRootMenu();
            boolean bl3 = menuBuilder != object;
            AppCompatDelegateImplV7 appCompatDelegateImplV7 = AppCompatDelegateImplV7.this;
            if (bl3) {
                object = menuBuilder;
            }
            if ((object = appCompatDelegateImplV7.findMenuPanel((Menu)object)) != null) {
                if (!bl3) {
                    AppCompatDelegateImplV7.this.closePanel((PanelFeatureState)object, bl2);
                    return;
                }
                AppCompatDelegateImplV7.this.callOnPanelClosed(((PanelFeatureState)object).featureId, (PanelFeatureState)object, menuBuilder);
                AppCompatDelegateImplV7.this.closePanel((PanelFeatureState)object, true);
            }
        }

        @Override
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            Window.Callback callback;
            if (menuBuilder == null && AppCompatDelegateImplV7.this.mHasActionBar && (callback = AppCompatDelegateImplV7.this.getWindowCallback()) != null && !AppCompatDelegateImplV7.this.isDestroyed()) {
                callback.onMenuOpened(108, (Menu)menuBuilder);
            }
            return true;
        }
    }
}

