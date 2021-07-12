/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnClickListener
 *  android.content.DialogInterface$OnDismissListener
 *  android.content.DialogInterface$OnKeyListener
 *  android.content.DialogInterface$OnMultiChoiceClickListener
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.database.Cursor
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.StateListDrawable
 *  android.os.Handler
 *  android.os.Message
 *  android.text.TextUtils
 *  android.util.TypedValue
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnApplyWindowInsetsListener
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.Window
 *  android.view.WindowInsets
 *  android.widget.Button
 *  android.widget.FrameLayout
 *  android.widget.ImageView
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.Space
 *  android.widget.TextView
 */
package ticwear.design.internal.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import ticwear.design.R;
import ticwear.design.widget.CheckedTextView;
import ticwear.design.widget.CoordinatorLayout;
import ticwear.design.widget.CursorRecyclerViewAdapter;
import ticwear.design.widget.FloatingActionButton;
import ticwear.design.widget.FocusableLinearLayoutManager;
import ticwear.design.widget.SubscribedScrollView;
import ticwear.design.widget.TicklableRecyclerView;
import ticwear.design.widget.TrackSelectionAdapterWrapper;

public class AlertController {
    static final int[] DISABLED_STATE_SET;
    static final int[] EMPTY_STATE_SET;
    static final int[] ENABLED_STATE_SET;
    private Runnable buttonRestoreRunnable;
    private TrackSelectionAdapterWrapper mAdapter;
    private int mAlertDialogLayout;
    private final ButtonBundle mButtonBundleNegative;
    private final ButtonBundle mButtonBundleNeutral;
    private final ButtonBundle mButtonBundlePositive;
    private final View.OnClickListener mButtonHandler = new View.OnClickListener(){

        public void onClick(View view) {
            Message message;
            Message message2 = message = AlertController.this.mButtonBundlePositive.messageForButton(view);
            if (message == null) {
                message2 = AlertController.this.mButtonBundleNegative.messageForButton(view);
            }
            message = message2;
            if (message2 == null) {
                message = AlertController.this.mButtonBundleNeutral.messageForButton(view);
            }
            if (message != null) {
                message.sendToTarget();
            }
            AlertController.this.mHandler.obtainMessage(1, (Object)AlertController.this.mDialogInterface).sendToTarget();
        }
    };
    private int mButtonPanelLayoutHint = 0;
    private int mButtonPanelSideLayout;
    private final Context mContext;
    private View mCustomTitleView;
    private DelayConfirmRequest mDelayConfirmRequest;
    private final DialogInterface mDialogInterface;
    private boolean mForceInverseBackground;
    private Handler mHandler;
    private Drawable mIcon;
    private int mIconId = 0;
    private ImageView mIconView;
    private int mListItemLayout;
    private int mListLayout;
    private TicklableRecyclerView mListView;
    private CharSequence mMessage;
    private TextView mMessageView;
    private int mMultiChoiceItemLayout;
    private SubscribedScrollView mScrollView;
    private int mSingleChoiceItemLayout;
    private CharSequence mTitle;
    private TextView mTitleView;
    private View mView;
    private int mViewLayoutResId;
    private int mViewSpacingBottom;
    private int mViewSpacingLeft;
    private int mViewSpacingRight;
    private boolean mViewSpacingSpecified = false;
    private int mViewSpacingTop;
    private final Window mWindow;

    static {
        ENABLED_STATE_SET = new int[]{16842910};
        DISABLED_STATE_SET = new int[]{-16842910};
        EMPTY_STATE_SET = new int[0];
    }

    public AlertController(Context context, DialogInterface dialogInterface, Window window) {
        this.buttonRestoreRunnable = new Runnable(){

            @Override
            public void run() {
                AlertController.this.showButtons();
            }
        };
        this.mContext = context;
        this.mDialogInterface = dialogInterface;
        this.mWindow = window;
        this.mHandler = new ButtonHandler(dialogInterface);
        context = context.obtainStyledAttributes(null, R.styleable.AlertDialog, 16842845, 0);
        this.mAlertDialogLayout = R.layout.alert_dialog_ticwear;
        this.mButtonPanelSideLayout = context.getResourceId(R.styleable.AlertDialog_android_buttonPanelSideLayout, 0);
        this.mListLayout = context.getResourceId(R.styleable.AlertDialog_tic_listLayout, R.layout.select_dialog_ticwear);
        this.mMultiChoiceItemLayout = context.getResourceId(R.styleable.AlertDialog_tic_multiChoiceItemLayout, 17367059);
        this.mSingleChoiceItemLayout = context.getResourceId(R.styleable.AlertDialog_tic_singleChoiceItemLayout, 17367058);
        this.mListItemLayout = context.getResourceId(R.styleable.AlertDialog_tic_listItemLayout, 0x1090011);
        context.recycle();
        this.mButtonBundlePositive = new ButtonBundle();
        this.mButtonBundleNegative = new ButtonBundle();
        this.mButtonBundleNeutral = new ButtonBundle();
    }

    static /* synthetic */ TicklableRecyclerView access$1502(AlertController alertController, TicklableRecyclerView ticklableRecyclerView) {
        alertController.mListView = ticklableRecyclerView;
        return ticklableRecyclerView;
    }

    static /* synthetic */ TrackSelectionAdapterWrapper access$2102(AlertController alertController, TrackSelectionAdapterWrapper trackSelectionAdapterWrapper) {
        alertController.mAdapter = trackSelectionAdapterWrapper;
        return trackSelectionAdapterWrapper;
    }

    static boolean canTextInput(View view) {
        if (view.onCheckIsTextEditor()) {
            return true;
        }
        if (!(view instanceof ViewGroup)) {
            return false;
        }
        view = (ViewGroup)view;
        int n2 = view.getChildCount();
        while (n2 > 0) {
            int n3;
            n2 = n3 = n2 - 1;
            if (!AlertController.canTextInput(view.getChildAt(n3))) continue;
            return true;
        }
        return false;
    }

    private CoordinatorLayout.LayoutParams getCoordinatorLayoutParams(View view) {
        if (view.getLayoutParams() instanceof CoordinatorLayout.LayoutParams) {
            return (CoordinatorLayout.LayoutParams)view.getLayoutParams();
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void manageScrollIndicators(View view, View view2, View view3) {
        int n2;
        int n3 = 0;
        if (view2 != null) {
            n2 = view.canScrollVertically(-1) ? 0 : 4;
            view2.setVisibility(n2);
        }
        if (view3 != null) {
            n2 = view.canScrollVertically(1) ? n3 : 4;
            view3.setVisibility(n2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void offsetIconButtons(int n2) {
        CoordinatorLayout.LayoutParams layoutParams;
        CoordinatorLayout.LayoutParams layoutParams2;
        int n3 = this.mContext.getResources().getDimensionPixelOffset(R.dimen.alert_dialog_round_padding_bottom);
        int n4 = n2 > 1 ? n3 * 2 : n3;
        int n5 = n2 == 3 ? this.mContext.getResources().getDimensionPixelOffset(R.dimen.alert_dialog_round_button_padding_horizontal_full) : (n2 == 2 ? this.mContext.getResources().getDimensionPixelOffset(R.dimen.alert_dialog_round_button_padding_horizontal_pair) : 0);
        CoordinatorLayout.LayoutParams layoutParams3 = this.getCoordinatorLayoutParams((View)this.mButtonBundlePositive.iconButton);
        if (layoutParams3 != null) {
            if (n2 == 1) {
                layoutParams3.gravity = 81;
            }
            layoutParams3.setMarginStart(n5);
            layoutParams3.setMarginEnd(n5);
            layoutParams3.bottomMargin = n4;
            this.mButtonBundlePositive.iconButton.setLayoutParams((ViewGroup.LayoutParams)layoutParams3);
        }
        if ((layoutParams2 = this.getCoordinatorLayoutParams((View)this.mButtonBundleNegative.iconButton)) != null) {
            if (n2 == 1) {
                layoutParams2.gravity = 81;
            }
            layoutParams2.setMarginStart(n5);
            layoutParams2.setMarginEnd(n5);
            layoutParams2.bottomMargin = n4;
            this.mButtonBundleNegative.iconButton.setLayoutParams((ViewGroup.LayoutParams)layoutParams2);
        }
        if ((layoutParams = this.getCoordinatorLayoutParams((View)this.mButtonBundleNeutral.iconButton)) != null) {
            if (n2 == 1) {
                layoutParams.gravity = 81;
            }
            layoutParams.bottomMargin = n4;
            this.mButtonBundleNeutral.iconButton.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        }
        n4 = this.mContext.getResources().getDimensionPixelOffset(R.dimen.alert_dialog_button_translate_vertical) + (n4 - n3);
        n5 = this.mContext.getResources().getDimensionPixelOffset(R.dimen.alert_dialog_button_translate_horizontal);
        if (n2 == 2) {
            this.mButtonBundlePositive.setMinimizeTranslation(-n5, n4);
            this.mButtonBundleNegative.setMinimizeTranslation(n5, n4);
            ButtonBundle buttonBundle = this.mButtonBundleNeutral;
            n2 = this.mButtonBundlePositive.hasIconButton() ? n5 : -n5;
            buttonBundle.setMinimizeTranslation(n2, n4);
            return;
        }
        if (n2 == 3) {
            this.mButtonBundlePositive.setMinimizeTranslation(-n5, n4);
            this.mButtonBundleNegative.setMinimizeTranslation(n5, n4);
            this.mButtonBundleNeutral.setMinimizeTranslation(0, n4);
            return;
        }
        this.mButtonBundlePositive.setMinimizeTranslation(0, n4);
        this.mButtonBundleNegative.setMinimizeTranslation(0, n4);
        this.mButtonBundleNeutral.setMinimizeTranslation(0, n4);
    }

    private int selectContentView() {
        if (this.mButtonPanelSideLayout == 0) {
            return this.mAlertDialogLayout;
        }
        if (this.mButtonPanelLayoutHint == 1) {
            return this.mButtonPanelSideLayout;
        }
        return this.mAlertDialogLayout;
    }

    private void setupButtonBundles() {
        this.mButtonBundlePositive.setup(this.mWindow, R.id.textButton1, R.id.textSpace1, R.id.iconButton1);
        this.mButtonBundleNegative.setup(this.mWindow, R.id.textButton2, R.id.textSpace2, R.id.iconButton2);
        this.mButtonBundleNeutral.setup(this.mWindow, R.id.textButton3, R.id.textSpace3, R.id.iconButton3);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean setupButtons() {
        boolean bl2 = true;
        this.setupButtonBundles();
        int n2 = this.setupTextButtons();
        int n3 = this.setupIconButtons();
        boolean bl3 = n2 > 0 || n3 > 0;
        if (n2 <= n3) {
            bl2 = false;
        }
        Object object = this.mWindow.findViewById(R.id.textButtonPanel);
        if (bl3 && bl2) {
            object.setVisibility(0);
        } else {
            object.setVisibility(8);
        }
        this.offsetIconButtons(n3);
        if (bl3 && !bl2 && this.mDelayConfirmRequest != null && (object = this.getIconButton(this.mDelayConfirmRequest.witchButton)) != null) {
            ((FloatingActionButton)((Object)object)).startDelayConfirmation(this.mDelayConfirmRequest.delayDuration, new FloatingActionButton.DelayedConfirmationListener(){

                @Override
                public void onButtonClicked(FloatingActionButton floatingActionButton) {
                }

                @Override
                public void onTimerFinished(FloatingActionButton floatingActionButton) {
                    floatingActionButton.performClick();
                }
            });
        }
        return bl3;
    }

    /*
     * Enabled aggressive block sorting
     */
    @SuppressLint(value={"NewApi"})
    private void setupContent(ViewGroup viewGroup) {
        View view;
        block11: {
            block10: {
                View view2;
                this.mScrollView = (SubscribedScrollView)this.mWindow.findViewById(R.id.scrollView);
                this.mScrollView.setFocusable(false);
                this.mMessageView = (TextView)this.mWindow.findViewById(R.id.message);
                if (this.mMessageView == null) break block10;
                if (this.mMessage != null) {
                    this.mMessageView.setText(this.mMessage);
                } else {
                    this.mMessageView.setVisibility(8);
                    this.mScrollView.removeView((View)this.mMessageView);
                    if (this.mListView != null) {
                        view2 = (ViewGroup)this.mScrollView.getParent();
                        int n2 = view2.indexOfChild((View)this.mScrollView);
                        view2.removeViewAt(n2);
                        view2.addView((View)this.mListView, n2, new ViewGroup.LayoutParams(-1, -1));
                    } else {
                        viewGroup.setVisibility(8);
                    }
                }
                view2 = this.mWindow.findViewById(R.id.scrollIndicatorUp);
                view = this.mWindow.findViewById(R.id.scrollIndicatorDown);
                if (view2 == null && view == null) break block10;
                OnViewScrollListener onViewScrollListener = new OnViewScrollListener(){
                    boolean scrollDown;
                    int scrollState;
                    {
                        this.scrollState = 0;
                        this.scrollDown = true;
                    }

                    /*
                     * Enabled aggressive block sorting
                     */
                    @Override
                    public void onViewScroll(View view3, int n2, int n3, int n4, int n5) {
                        block5: {
                            block4: {
                                AlertController.manageScrollIndicators(view3, view2, view);
                                if (n3 == n5) break block4;
                                n2 = n3 - n5 < 0 ? 1 : 0;
                                if (n2 != 0 && !this.scrollDown) {
                                    AlertController.this.showButtons();
                                    this.scrollDown = true;
                                    return;
                                }
                                if (n2 == 0 && this.scrollDown && this.scrollState != 0) break block5;
                            }
                            return;
                        }
                        AlertController.this.minimizeButtons();
                        this.scrollDown = false;
                    }

                    /*
                     * Enabled aggressive block sorting
                     */
                    @Override
                    public void onViewScrollStateChanged(View view3, int n2) {
                        this.scrollState = n2;
                        if (this.scrollState == 0) {
                            AlertController.this.showButtonsDelayed();
                            return;
                        } else {
                            if (this.scrollDown) return;
                            AlertController.this.minimizeButtons();
                            return;
                        }
                    }
                };
                if (this.mMessage != null) {
                    this.mScrollView.setOnScrollListener(onViewScrollListener);
                    this.mScrollView.post(new Runnable(){

                        @Override
                        public void run() {
                            AlertController.manageScrollIndicators((View)AlertController.this.mScrollView, view2, view);
                        }
                    });
                    return;
                }
                if (this.mListView != null) {
                    this.mListView.setOnScrollListener(onViewScrollListener);
                    this.mListView.post(new Runnable(){

                        @Override
                        public void run() {
                            AlertController.manageScrollIndicators((View)AlertController.this.mListView, view2, view);
                        }
                    });
                    return;
                }
                if (view2 != null) {
                    viewGroup.removeView(view2);
                }
                if (view != null) break block11;
            }
            return;
        }
        viewGroup.removeView(view);
    }

    private void setupDecor() {
        View view = this.mWindow.getDecorView();
        final View view2 = this.mWindow.findViewById(R.id.parentPanel);
        if (view2 != null && view != null) {
            view.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener(){

                public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                    if (windowInsets.isRound()) {
                        int n2 = AlertController.this.mContext.getResources().getDimensionPixelOffset(R.dimen.alert_dialog_round_padding);
                        view2.setPadding(n2, n2, n2, n2);
                    }
                    return windowInsets.consumeSystemWindowInsets();
                }
            });
            view.setFitsSystemWindows(true);
            view.requestApplyInsets();
        }
    }

    private int setupIconButtons() {
        int n2 = 0;
        if (this.mButtonBundlePositive.setupIconButton(this.mButtonHandler)) {
            n2 = 0 | 1;
        }
        int n3 = n2;
        if (this.mButtonBundleNegative.setupIconButton(this.mButtonHandler)) {
            n3 = n2 | 2;
        }
        n2 = n3;
        if (this.mButtonBundleNeutral.setupIconButton(this.mButtonHandler)) {
            n2 = n3 | 4;
        }
        return Integer.bitCount(n2);
    }

    private int setupTextButtons() {
        int n2 = 0;
        if (this.mButtonBundlePositive.setupTextButton(this.mButtonHandler)) {
            n2 = 0 | 1;
        }
        int n3 = n2;
        if (this.mButtonBundleNegative.setupTextButton(this.mButtonHandler)) {
            n3 = n2 | 2;
        }
        n2 = n3;
        if (this.mButtonBundleNeutral.setupTextButton(this.mButtonHandler)) {
            n2 = n3 | 4;
        }
        return Integer.bitCount(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean setupTitle(ViewGroup viewGroup) {
        if (this.mCustomTitleView != null) {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -2);
            viewGroup.addView(this.mCustomTitleView, 0, layoutParams);
            this.mWindow.findViewById(R.id.title_template).setVisibility(8);
            return true;
        }
        boolean bl2 = !TextUtils.isEmpty((CharSequence)this.mTitle);
        boolean bl3 = this.mIconId != 0 || this.mIcon != null;
        if (!bl2 && !bl3) {
            this.mWindow.findViewById(R.id.title_template).setVisibility(8);
            return false;
        }
        this.mTitleView = (TextView)this.mWindow.findViewById(16908310);
        this.mIconView = (ImageView)this.mWindow.findViewById(16908294);
        if (bl2) {
            this.mTitleView.setText(this.mTitle);
        } else {
            this.mTitleView.setVisibility(8);
        }
        if (this.mIconId != 0) {
            this.mIconView.setImageResource(this.mIconId);
            return true;
        }
        if (this.mIcon != null) {
            this.mIconView.setImageDrawable(this.mIcon);
            return true;
        }
        this.mIconView.setVisibility(8);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setupView() {
        this.setupContent((ViewGroup)this.mWindow.findViewById(R.id.contentPanel));
        boolean bl2 = this.setupButtons();
        Object object = (ViewGroup)this.mWindow.findViewById(R.id.topPanel);
        TypedArray typedArray = this.mContext.obtainStyledAttributes(null, R.styleable.AlertDialog, 16842845, 0);
        this.setupTitle((ViewGroup)object);
        if (!bl2 && (object = this.mWindow.findViewById(R.id.textSpacerNoButtons)) != null) {
            object.setVisibility(0);
        }
        FrameLayout frameLayout = (FrameLayout)this.mWindow.findViewById(R.id.customPanel);
        object = this.mView != null ? this.mView : (this.mViewLayoutResId != 0 ? LayoutInflater.from((Context)this.mContext).inflate(this.mViewLayoutResId, (ViewGroup)frameLayout, false) : null);
        boolean bl3 = object != null;
        if (!bl3 || !AlertController.canTextInput((View)object)) {
            this.mWindow.setFlags(131072, 131072);
        }
        if (bl3) {
            FrameLayout frameLayout2 = (FrameLayout)this.mWindow.findViewById(R.id.custom);
            frameLayout2.addView((View)object, new ViewGroup.LayoutParams(-1, -1));
            if (this.mViewSpacingSpecified) {
                frameLayout2.setPadding(this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
            }
            if (this.mListView != null) {
                ((LinearLayout.LayoutParams)frameLayout.getLayoutParams()).weight = 0.0f;
            }
        } else {
            frameLayout.setVisibility(8);
        }
        typedArray.recycle();
        object = this.mListView;
        if (object != null && this.mAdapter != null) {
            object.setAdapter(this.mAdapter);
        }
    }

    private static boolean shouldCenterSingleButton(Context context) {
        return true;
    }

    public Button getButton(int n2) {
        switch (n2) {
            default: {
                return null;
            }
            case -1: {
                return this.mButtonBundlePositive.textButton;
            }
            case -2: {
                return this.mButtonBundleNegative.textButton;
            }
            case -3: 
        }
        return this.mButtonBundleNeutral.textButton;
    }

    public int getIconAttributeResId(int n2) {
        TypedValue typedValue = new TypedValue();
        this.mContext.getTheme().resolveAttribute(n2, typedValue, true);
        return typedValue.resourceId;
    }

    public FloatingActionButton getIconButton(int n2) {
        switch (n2) {
            default: {
                return null;
            }
            case -1: {
                return this.mButtonBundlePositive.iconButton;
            }
            case -2: {
                return this.mButtonBundleNegative.iconButton;
            }
            case -3: 
        }
        return this.mButtonBundleNeutral.iconButton;
    }

    public TicklableRecyclerView getListView() {
        return this.mListView;
    }

    public void hideButtons() {
        this.mWindow.getDecorView().removeCallbacks(this.buttonRestoreRunnable);
        this.mButtonBundlePositive.hideButton();
        this.mButtonBundleNegative.hideButton();
        this.mButtonBundleNeutral.hideButton();
    }

    public void installContent() {
        this.mWindow.requestFeature(1);
        int n2 = this.selectContentView();
        this.mWindow.setContentView(n2);
        this.setupView();
        this.setupDecor();
    }

    public void minimizeButtons() {
        this.mWindow.getDecorView().removeCallbacks(this.buttonRestoreRunnable);
        this.mButtonBundlePositive.minimizeButton();
        this.mButtonBundleNegative.minimizeButton();
        this.mButtonBundleNeutral.minimizeButton();
    }

    public boolean onKeyDown(int n2, KeyEvent keyEvent) {
        return this.mScrollView != null && this.mScrollView.executeKeyEvent(keyEvent);
    }

    public boolean onKeyUp(int n2, KeyEvent keyEvent) {
        return this.mScrollView != null && this.mScrollView.executeKeyEvent(keyEvent);
    }

    public void setButton(int n2, CharSequence charSequence, Drawable drawable2, DialogInterface.OnClickListener onClickListener, Message message) {
        Message message2 = message;
        if (message == null) {
            message2 = message;
            if (onClickListener != null) {
                message2 = this.mHandler.obtainMessage(n2, (Object)onClickListener);
            }
        }
        switch (n2) {
            default: {
                throw new IllegalArgumentException("Button does not exist");
            }
            case -1: {
                ButtonBundle.access$602(this.mButtonBundlePositive, charSequence);
                ButtonBundle.access$702(this.mButtonBundlePositive, drawable2);
                ButtonBundle.access$802(this.mButtonBundlePositive, message2);
                return;
            }
            case -2: {
                ButtonBundle.access$602(this.mButtonBundleNegative, charSequence);
                ButtonBundle.access$702(this.mButtonBundleNegative, drawable2);
                ButtonBundle.access$802(this.mButtonBundleNegative, message2);
                return;
            }
            case -3: 
        }
        ButtonBundle.access$602(this.mButtonBundleNeutral, charSequence);
        ButtonBundle.access$702(this.mButtonBundleNeutral, drawable2);
        ButtonBundle.access$802(this.mButtonBundleNeutral, message2);
    }

    public void setButtonPanelLayoutHint(int n2) {
        this.mButtonPanelLayoutHint = n2;
    }

    public void setCustomTitle(View view) {
        this.mCustomTitleView = view;
    }

    public void setDelayConfirmAction(DelayConfirmRequest delayConfirmRequest) {
        this.mDelayConfirmRequest = delayConfirmRequest;
    }

    public void setIcon(int n2) {
        block3: {
            block2: {
                this.mIcon = null;
                this.mIconId = n2;
                if (this.mIconView == null) break block2;
                if (n2 == 0) break block3;
                this.mIconView.setImageResource(this.mIconId);
            }
            return;
        }
        this.mIconView.setVisibility(8);
    }

    public void setIcon(Drawable drawable2) {
        block3: {
            block2: {
                this.mIcon = drawable2;
                this.mIconId = 0;
                if (this.mIconView == null) break block2;
                if (drawable2 == null) break block3;
                this.mIconView.setImageDrawable(drawable2);
            }
            return;
        }
        this.mIconView.setVisibility(8);
    }

    public void setInverseBackgroundForced(boolean bl2) {
        this.mForceInverseBackground = bl2;
    }

    public void setMessage(CharSequence charSequence) {
        this.mMessage = charSequence;
        if (this.mMessageView != null) {
            this.mMessageView.setText(charSequence);
        }
    }

    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        if (this.mTitleView != null) {
            this.mTitleView.setText(charSequence);
        }
    }

    public void setView(int n2) {
        this.mView = null;
        this.mViewLayoutResId = n2;
        this.mViewSpacingSpecified = false;
    }

    public void setView(View view) {
        this.mView = view;
        this.mViewLayoutResId = 0;
        this.mViewSpacingSpecified = false;
    }

    public void setView(View view, int n2, int n3, int n4, int n5) {
        this.mView = view;
        this.mViewLayoutResId = 0;
        this.mViewSpacingSpecified = true;
        this.mViewSpacingLeft = n2;
        this.mViewSpacingTop = n3;
        this.mViewSpacingRight = n4;
        this.mViewSpacingBottom = n5;
    }

    public void showButtons() {
        this.mWindow.getDecorView().removeCallbacks(this.buttonRestoreRunnable);
        this.mButtonBundlePositive.showButton();
        this.mButtonBundleNegative.showButton();
        this.mButtonBundleNeutral.showButton();
    }

    public void showButtonsDelayed() {
        this.mWindow.getDecorView().removeCallbacks(this.buttonRestoreRunnable);
        long l2 = this.mContext.getResources().getInteger(R.integer.design_time_action_idle_timeout_short);
        this.mWindow.getDecorView().postDelayed(this.buttonRestoreRunnable, l2);
    }

    public static class AlertParams {
        public RecyclerView.Adapter mAdapter;
        public boolean mCancelable;
        public int mCheckedItem = -1;
        public boolean[] mCheckedItems;
        public final Context mContext;
        public Cursor mCursor;
        public View mCustomTitleView;
        public DelayConfirmRequest mDelayConfirmRequest;
        public boolean mForceInverseBackground;
        public Drawable mIcon;
        public int mIconAttrId = 0;
        public int mIconId = 0;
        public final LayoutInflater mInflater;
        public String mIsCheckedColumn;
        public boolean mIsMultiChoice;
        public boolean mIsSingleChoice;
        public CharSequence[] mItems;
        public String mLabelColumn;
        public CharSequence mMessage;
        public Drawable mNegativeButtonIcon;
        public DialogInterface.OnClickListener mNegativeButtonListener;
        public CharSequence mNegativeButtonText;
        public Drawable mNeutralButtonIcon;
        public DialogInterface.OnClickListener mNeutralButtonListener;
        public CharSequence mNeutralButtonText;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnMultiChoiceClickListener mOnCheckboxClickListener;
        public DialogInterface.OnClickListener mOnClickListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public TrackSelectionAdapterWrapper.OnItemSelectedListener mOnItemSelectedListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        public OnPrepareListViewListener mOnPrepareListViewListener;
        public Drawable mPositiveButtonIcon;
        public DialogInterface.OnClickListener mPositiveButtonListener;
        public CharSequence mPositiveButtonText;
        public CharSequence mTitle;
        public View mView;
        public int mViewLayoutResId;
        public int mViewSpacingBottom;
        public int mViewSpacingLeft;
        public int mViewSpacingRight;
        public boolean mViewSpacingSpecified = false;
        public int mViewSpacingTop;

        public AlertParams(Context context) {
            this.mContext = context;
            this.mCancelable = true;
            this.mInflater = (LayoutInflater)context.getSystemService("layout_inflater");
        }

        /*
         * Enabled aggressive block sorting
         */
        private void createListView(final AlertController alertController) {
            TicklableRecyclerView ticklableRecyclerView = (TicklableRecyclerView)this.mInflater.inflate(alertController.mListLayout, null);
            ticklableRecyclerView.setLayoutManager(new FocusableLinearLayoutManager(this.mContext));
            final int n2 = this.mIsSingleChoice ? alertController.mSingleChoiceItemLayout : (this.mIsMultiChoice ? alertController.mMultiChoiceItemLayout : alertController.mListItemLayout);
            RecyclerView.Adapter adapter = this.mCursor == null ? (this.mAdapter != null ? this.mAdapter : new CheckedItemAdapter(this.mContext, n2, 16908308, this.mItems)) : (this.mAdapter instanceof CursorRecyclerViewAdapter ? this.mAdapter : new CursorRecyclerViewAdapter(this.mContext, this.mCursor){
                private final int mLabelIndex;
                {
                    super(context, cursor);
                    this.mLabelIndex = this.getCursor().getColumnIndexOrThrow(AlertParams.this.mLabelColumn);
                }

                public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Cursor cursor) {
                    ((CheckedTextView)viewHolder.itemView.findViewById(16908308)).setText(cursor.getString(this.mLabelIndex));
                }

                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int n22) {
                    return new FocusableLinearLayoutManager.ViewHolder(AlertParams.this.mInflater.inflate(n2, viewGroup, false));
                }
            });
            if (this.mOnPrepareListViewListener != null) {
                this.mOnPrepareListViewListener.onPrepareListView(ticklableRecyclerView);
            }
            AlertController.access$2102(alertController, new TrackSelectionAdapterWrapper<RecyclerView.ViewHolder>(adapter){
                private final int mIsCheckedIndex;
                {
                    if (this.useCursorCheckedColumn()) {
                        this.mIsCheckedIndex = ((CursorRecyclerViewAdapter)this.getAdapter()).getCursor().getColumnIndexOrThrow(AlertParams.this.mIsCheckedColumn);
                        return;
                    }
                    this.mIsCheckedIndex = -1;
                }

                private boolean useCursorCheckedColumn() {
                    return AlertParams.this.mIsMultiChoice && this.getAdapter() instanceof CursorRecyclerViewAdapter;
                }

                /*
                 * Enabled aggressive block sorting
                 */
                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int n2) {
                    if (this.useCursorCheckedColumn() && this.mIsCheckedIndex != -1) {
                        Cursor cursor = ((CursorRecyclerViewAdapter)this.getAdapter()).getCursor();
                        int n3 = cursor.getPosition();
                        boolean bl2 = cursor.getInt(this.mIsCheckedIndex) == 1;
                        this.setItemChecked(n3, bl2);
                    } else if (AlertParams.this.mCheckedItems != null) {
                        boolean bl3 = AlertParams.this.mCheckedItems[n2];
                        if (bl3 != this.isItemChecked(n2)) {
                            this.setItemChecked(n2, bl3);
                        }
                    } else if (AlertParams.this.mCheckedItem > -1) {
                        this.setItemChecked(AlertParams.this.mCheckedItem, true);
                    }
                    super.onBindViewHolder(viewHolder, n2);
                }
            });
            if (this.mOnClickListener != null) {
                alertController.mAdapter.setOnItemClickListener(new TrackSelectionAdapterWrapper.OnItemClickListener(){

                    @Override
                    public void onItemClick(TrackSelectionAdapterWrapper<?> trackSelectionAdapterWrapper, View view, int n2, long l2) {
                        AlertParams.this.mCheckedItem = n2;
                        AlertParams.this.mOnClickListener.onClick(alertController.mDialogInterface, n2);
                        if (!AlertParams.this.mIsSingleChoice) {
                            alertController.mDialogInterface.dismiss();
                        }
                    }
                });
            } else if (this.mOnCheckboxClickListener != null) {
                alertController.mAdapter.setOnItemClickListener(new TrackSelectionAdapterWrapper.OnItemClickListener(){

                    @Override
                    public void onItemClick(TrackSelectionAdapterWrapper<?> trackSelectionAdapterWrapper, View view, int n2, long l2) {
                        if (AlertParams.this.mCheckedItems != null) {
                            AlertParams.this.mCheckedItems[n2] = alertController.mAdapter.isItemChecked(n2);
                        }
                        AlertParams.this.mOnCheckboxClickListener.onClick(alertController.mDialogInterface, n2, alertController.mAdapter.isItemChecked(n2));
                    }
                });
            }
            if (this.mOnItemSelectedListener != null) {
                alertController.mAdapter.setOnItemSelectedListener(this.mOnItemSelectedListener);
            }
            if (this.mIsSingleChoice) {
                alertController.mAdapter.setChoiceMode(1);
            } else if (this.mIsMultiChoice) {
                alertController.mAdapter.setChoiceMode(2);
            }
            AlertController.access$1502(alertController, ticklableRecyclerView);
        }

        /*
         * Enabled aggressive block sorting
         */
        public void apply(AlertController alertController) {
            if (this.mCustomTitleView != null) {
                alertController.setCustomTitle(this.mCustomTitleView);
            } else {
                if (this.mTitle != null) {
                    alertController.setTitle(this.mTitle);
                }
                if (this.mIcon != null) {
                    alertController.setIcon(this.mIcon);
                }
                if (this.mIconId != 0) {
                    alertController.setIcon(this.mIconId);
                }
                if (this.mIconAttrId != 0) {
                    alertController.setIcon(alertController.getIconAttributeResId(this.mIconAttrId));
                }
            }
            if (this.mMessage != null) {
                alertController.setMessage(this.mMessage);
            }
            if (this.mPositiveButtonText != null || this.mPositiveButtonIcon != null) {
                alertController.setButton(-1, this.mPositiveButtonText, this.mPositiveButtonIcon, this.mPositiveButtonListener, null);
            }
            if (this.mNegativeButtonText != null || this.mNegativeButtonIcon != null) {
                alertController.setButton(-2, this.mNegativeButtonText, this.mNegativeButtonIcon, this.mNegativeButtonListener, null);
            }
            if (this.mNeutralButtonText != null || this.mNeutralButtonIcon != null) {
                alertController.setButton(-3, this.mNeutralButtonText, this.mNeutralButtonIcon, this.mNeutralButtonListener, null);
            }
            if (this.mForceInverseBackground) {
                alertController.setInverseBackgroundForced(true);
            }
            if (this.mDelayConfirmRequest != null) {
                alertController.setDelayConfirmAction(this.mDelayConfirmRequest);
            }
            if (this.mItems != null || this.mCursor != null || this.mAdapter != null) {
                this.createListView(alertController);
            }
            if (this.mView != null) {
                if (!this.mViewSpacingSpecified) {
                    alertController.setView(this.mView);
                    return;
                }
                alertController.setView(this.mView, this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
                return;
            } else {
                if (this.mViewLayoutResId == 0) return;
                alertController.setView(this.mViewLayoutResId);
                return;
            }
        }

        public static interface OnPrepareListViewListener {
            public void onPrepareListView(TicklableRecyclerView var1);
        }
    }

    private static class ButtonBundle {
        private Drawable buttonIcon;
        private Message buttonMessage;
        private CharSequence buttonText;
        private FloatingActionButton iconButton;
        private Button textButton;
        private Space textSpace;

        private ButtonBundle() {
        }

        static /* synthetic */ CharSequence access$602(ButtonBundle buttonBundle, CharSequence charSequence) {
            buttonBundle.buttonText = charSequence;
            return charSequence;
        }

        static /* synthetic */ Drawable access$702(ButtonBundle buttonBundle, Drawable drawable2) {
            buttonBundle.buttonIcon = drawable2;
            return drawable2;
        }

        static /* synthetic */ Message access$802(ButtonBundle buttonBundle, Message message) {
            buttonBundle.buttonMessage = message;
            return message;
        }

        private static int getAlphaValue(@NonNull Context context) {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(0x1010033, typedValue, true);
            return (int)(255.0f * typedValue.getFloat());
        }

        private static ColorStateList getStatefulColorList(@NonNull Context context, @Nullable ColorStateList object) {
            Object object2 = object;
            if (object != null) {
                int n2 = object.getDefaultColor();
                object = ENABLED_STATE_SET;
                object2 = DISABLED_STATE_SET;
                int n3 = ButtonBundle.getAlphaValue(context);
                object2 = new ColorStateList((int[][])new int[][]{(int[])object, (int[])object2}, new int[]{n2, 0xFFFFFF & n2 | n3 << 24});
            }
            return object2;
        }

        /*
         * Enabled aggressive block sorting
         */
        private static Drawable getStatefulDrawable(@NonNull Context context, @Nullable Drawable drawable2) {
            StateListDrawable stateListDrawable = null;
            if (drawable2 != null) {
                if (drawable2 instanceof StateListDrawable) {
                    stateListDrawable = (StateListDrawable)drawable2;
                } else {
                    stateListDrawable = new StateListDrawable();
                    stateListDrawable.addState(ENABLED_STATE_SET, drawable2);
                }
                int n2 = ButtonBundle.getAlphaValue(context);
                context = drawable2.getConstantState().newDrawable().mutate();
                context.setAlpha(n2);
                stateListDrawable.addState(DISABLED_STATE_SET, (Drawable)context);
            }
            return stateListDrawable;
        }

        private boolean hasIconButton() {
            return this.buttonIcon != null || this.iconButton.getVisibility() == 0;
        }

        private void setupIconContent() {
            this.iconButton.setImageDrawable(ButtonBundle.getStatefulDrawable(this.iconButton.getContext(), this.buttonIcon));
            ColorStateList colorStateList = this.iconButton.getBackgroundTintList();
            this.iconButton.setBackgroundTintList(ButtonBundle.getStatefulColorList(this.iconButton.getContext(), colorStateList));
        }

        public void hideButton() {
            if (this.hasIconButton()) {
                this.iconButton.hide();
            }
        }

        public Message messageForButton(View view) {
            if ((view == this.textButton || view == this.iconButton) && this.buttonMessage != null) {
                return Message.obtain((Message)this.buttonMessage);
            }
            return null;
        }

        public void minimizeButton() {
            if (this.hasIconButton()) {
                this.iconButton.minimize();
            }
        }

        public void setMinimizeTranslation(int n2, int n3) {
            if (this.hasIconButton()) {
                this.iconButton.setMinimizeTranslation(n2, n3);
            }
        }

        public void setup(Window window, @IdRes int n2, @IdRes int n3, @IdRes int n4) {
            this.textButton = (Button)window.findViewById(n2);
            this.textSpace = (Space)window.findViewById(n3);
            this.iconButton = (FloatingActionButton)window.findViewById(n4);
        }

        public boolean setupIconButton(@Nullable View.OnClickListener onClickListener) {
            this.iconButton.setOnClickListener(onClickListener);
            if (this.buttonIcon == null) {
                this.iconButton.setVisibility(8);
                return false;
            }
            this.setupIconContent();
            this.iconButton.setVisibility(0);
            return true;
        }

        public boolean setupTextButton(@Nullable View.OnClickListener onClickListener) {
            this.textButton.setOnClickListener(onClickListener);
            if (TextUtils.isEmpty((CharSequence)this.buttonText)) {
                this.textButton.setVisibility(8);
                this.textSpace.setVisibility(8);
                return false;
            }
            this.textButton.setText(this.buttonText);
            this.textButton.setVisibility(0);
            this.textSpace.setVisibility(4);
            return true;
        }

        public void showButton() {
            if (this.hasIconButton()) {
                this.iconButton.show();
            }
        }
    }

    private static final class ButtonHandler
    extends Handler {
        private static final int MSG_DISMISS_DIALOG = 1;
        private WeakReference<DialogInterface> mDialog;

        public ButtonHandler(DialogInterface dialogInterface) {
            this.mDialog = new WeakReference<DialogInterface>(dialogInterface);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                default: {
                    return;
                }
                case -3: 
                case -2: 
                case -1: {
                    ((DialogInterface.OnClickListener)message.obj).onClick((DialogInterface)this.mDialog.get(), message.what);
                    return;
                }
                case 1: 
            }
            ((DialogInterface)message.obj).dismiss();
        }
    }

    private static class CheckedItemAdapter
    extends RecyclerView.Adapter<FocusableLinearLayoutManager.ViewHolder> {
        private final Context mContext;
        private final int mLayoutResource;
        private final CharSequence[] mObjects;
        private final int mTextViewResourceId;

        public CheckedItemAdapter(Context context, int n2, int n3, CharSequence[] charSequenceArray) {
            this.mContext = context;
            this.mLayoutResource = n2;
            this.mTextViewResourceId = n3;
            this.mObjects = charSequenceArray;
            this.setHasStableIds(true);
        }

        @Override
        public int getItemCount() {
            return this.mObjects.length;
        }

        @Override
        public long getItemId(int n2) {
            return n2;
        }

        @Override
        public void onBindViewHolder(FocusableLinearLayoutManager.ViewHolder viewHolder, int n2) {
            ((TextView)viewHolder.itemView.findViewById(this.mTextViewResourceId)).setText(this.mObjects[n2]);
        }

        @Override
        public FocusableLinearLayoutManager.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int n2) {
            return new FocusableLinearLayoutManager.ViewHolder(LayoutInflater.from((Context)this.mContext).inflate(this.mLayoutResource, viewGroup, false));
        }
    }

    public static class DelayConfirmRequest {
        public final long delayDuration;
        public final int witchButton;

        public DelayConfirmRequest(int n2, long l2) {
            this.witchButton = n2;
            this.delayDuration = l2;
        }
    }

    private abstract class OnViewScrollListener
    extends RecyclerView.OnScrollListener
    implements SubscribedScrollView.OnScrollListener {
        private int oldScrollX = 0;
        private int oldScrollY = 0;

        private OnViewScrollListener() {
        }

        @Override
        public void onScroll(SubscribedScrollView subscribedScrollView, int n2, int n3, int n4, int n5) {
            this.onViewScroll((View)subscribedScrollView, n2, n3, n4, n5);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int n2) {
            this.onViewScrollStateChanged((View)recyclerView, n2);
        }

        @Override
        public void onScrollStateChanged(SubscribedScrollView subscribedScrollView, int n2) {
            this.onViewScrollStateChanged((View)subscribedScrollView, n2);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int n2, int n3) {
            n2 = recyclerView.computeHorizontalScrollOffset();
            n3 = recyclerView.computeVerticalScrollOffset();
            this.onViewScroll((View)recyclerView, n2, n3, this.oldScrollX, this.oldScrollY);
            this.oldScrollX = n2;
            this.oldScrollY = n3;
        }

        public abstract void onViewScroll(View var1, int var2, int var3, int var4, int var5);

        public abstract void onViewScrollStateChanged(View var1, int var2);
    }
}

