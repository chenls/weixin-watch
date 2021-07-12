/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ObjectAnimator
 *  android.animation.PropertyValuesHolder
 *  android.animation.TimeInterpolator
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Message
 *  android.text.TextUtils
 *  android.util.Property
 *  android.view.View
 *  android.view.View$OnApplyWindowInsetsListener
 *  android.view.View$OnClickListener
 *  android.view.View$OnLayoutChangeListener
 *  android.view.ViewGroup
 *  android.view.WindowInsets
 *  android.view.animation.AnimationUtils
 *  android.view.animation.Interpolator
 *  android.widget.Button
 *  android.widget.TextView
 */
package android.support.wearable.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.wearable.R;
import android.support.wearable.view.ObservableScrollView;
import android.text.TextUtils;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.TextView;

@TargetApi(value=21)
public class WearableDialogActivity
extends Activity
implements Handler.Callback,
View.OnLayoutChangeListener,
ObservableScrollView.OnScrollListener,
View.OnClickListener,
View.OnApplyWindowInsetsListener {
    private static final long ANIM_DURATION = 500L;
    private static final long HIDE_ANIM_DELAY = 1500L;
    private static final int MSG_HIDE_BUTTON_BAR = 1001;
    private ViewGroup mAnimatedWrapperContainer;
    private Button mButtonNegative;
    private Button mButtonNeutral;
    private ViewGroup mButtonPanel;
    private ObjectAnimator mButtonPanelAnimator;
    private float mButtonPanelFloatHeight;
    private int mButtonPanelShadeHeight;
    private Button mButtonPositive;
    private Handler mHandler;
    private boolean mHiddenBefore;
    private Interpolator mInterpolator;
    private TextView mMessageView;
    private ObservableScrollView mParentPanel;
    private TextView mTitleView;
    private PropertyValuesHolder mTranslationValuesHolder;

    private int getButtonBarFloatingBottomTranslation() {
        return Math.min(this.getButtonBarOffsetFromBottom(), 0);
    }

    private int getButtonBarFloatingTopTranslation() {
        return this.getButtonBarOffsetFromBottom() - Math.min(this.mButtonPanel.getHeight(), this.mButtonPanelShadeHeight);
    }

    private int getButtonBarOffsetFromBottom() {
        return -this.mButtonPanel.getTop() + Math.max(this.mParentPanel.getScrollY(), 0) + this.mParentPanel.getHeight();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void hideButtonBar() {
        block5: {
            block6: {
                int n2;
                block4: {
                    if (this.mHiddenBefore && this.mButtonPanelAnimator != null) break block4;
                    if (this.mButtonPanelAnimator != null) {
                        this.mButtonPanelAnimator.cancel();
                    }
                    this.mTranslationValuesHolder = PropertyValuesHolder.ofFloat((Property)View.TRANSLATION_Y, (float[])new float[]{this.getButtonBarFloatingTopTranslation(), this.getButtonBarFloatingBottomTranslation()});
                    this.mButtonPanelAnimator = ObjectAnimator.ofPropertyValuesHolder((Object)this.mButtonPanel, (PropertyValuesHolder[])new PropertyValuesHolder[]{this.mTranslationValuesHolder, PropertyValuesHolder.ofFloat((Property)View.TRANSLATION_Z, (float[])new float[]{this.mButtonPanelFloatHeight, 0.0f})});
                    this.mButtonPanelAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                        public void onAnimationEnd(Animator animator2) {
                            WearableDialogActivity.this.mParentPanel.setOnScrollListener(null);
                            WearableDialogActivity.this.mButtonPanel.setTranslationY(0.0f);
                            WearableDialogActivity.this.mButtonPanel.setTranslationZ(0.0f);
                        }
                    });
                    this.mButtonPanelAnimator.setDuration(500L);
                    this.mButtonPanelAnimator.setInterpolator((TimeInterpolator)this.mInterpolator);
                    this.mButtonPanelAnimator.start();
                    break block5;
                }
                if (!this.mButtonPanelAnimator.isRunning()) break block6;
                int n3 = this.getButtonBarFloatingTopTranslation();
                if (n3 < (n2 = this.getButtonBarFloatingBottomTranslation())) {
                    this.mTranslationValuesHolder.setFloatValues(new float[]{n3, n2});
                    if (this.mButtonPanel.getTranslationY() < (float)n3) {
                        this.mButtonPanel.setTranslationY((float)n3);
                    }
                    break block5;
                } else {
                    this.mButtonPanelAnimator.cancel();
                    this.mButtonPanel.setTranslationY(0.0f);
                    this.mButtonPanel.setTranslationZ(0.0f);
                }
                break block5;
            }
            this.mButtonPanel.setTranslationY(0.0f);
            this.mButtonPanel.setTranslationZ(0.0f);
        }
        this.mHiddenBefore = true;
    }

    private boolean setButton(Button button, CharSequence charSequence, Drawable drawable2) {
        if (TextUtils.isEmpty((CharSequence)charSequence)) {
            button.setVisibility(8);
            return false;
        }
        button.setText(charSequence);
        if (drawable2 != null) {
            button.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);
        }
        button.setVisibility(0);
        return true;
    }

    public CharSequence getAlertTitle() {
        return null;
    }

    public CharSequence getMessage() {
        return null;
    }

    public Drawable getNegativeButtonDrawable() {
        return null;
    }

    public CharSequence getNegativeButtonText() {
        return null;
    }

    public Drawable getNeutralButtonDrawable() {
        return null;
    }

    public CharSequence getNeutralButtonText() {
        return null;
    }

    public Drawable getPositiveButtonDrawable() {
        return null;
    }

    public CharSequence getPositiveButtonText() {
        return null;
    }

    public boolean handleMessage(Message message) {
        switch (message.what) {
            default: {
                return false;
            }
            case 1001: 
        }
        this.hideButtonBar();
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        Resources resources = this.getResources();
        if (windowInsets.isRound()) {
            this.mButtonPanelShadeHeight = resources.getDimensionPixelSize(R.dimen.diag_shade_height_round);
            this.mTitleView.setPadding(resources.getDimensionPixelSize(R.dimen.diag_content_side_padding_round), resources.getDimensionPixelSize(R.dimen.diag_content_top_padding_round), resources.getDimensionPixelSize(R.dimen.diag_content_side_padding_round), 0);
            this.mTitleView.setGravity(17);
            this.mMessageView.setPadding(resources.getDimensionPixelSize(R.dimen.diag_content_side_padding_round), 0, resources.getDimensionPixelSize(R.dimen.diag_content_side_padding_round), resources.getDimensionPixelSize(R.dimen.diag_content_bottom_padding));
            this.mMessageView.setGravity(17);
            this.mButtonPanel.setPadding(resources.getDimensionPixelSize(R.dimen.diag_content_side_padding_round), 0, resources.getDimensionPixelSize(R.dimen.diag_button_side_padding_right_round), resources.getDimensionPixelSize(R.dimen.diag_button_bottom_padding_round));
            return view.onApplyWindowInsets(windowInsets);
        }
        this.mButtonPanelShadeHeight = this.getResources().getDimensionPixelSize(R.dimen.diag_shade_height_rect);
        return view.onApplyWindowInsets(windowInsets);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            default: {
                return;
            }
            case 16908313: {
                this.onPositiveButtonClick();
                return;
            }
            case 16908314: {
                this.onNegativeButtonClick();
                return;
            }
            case 16908315: 
        }
        this.onNeutralButtonClick();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setTheme(R.style.Theme_WearDiag);
        this.setContentView(R.layout.alert_dialog_wearable);
        this.mAnimatedWrapperContainer = (ViewGroup)this.findViewById(R.id.animatedWrapperContainer);
        this.mTitleView = (TextView)this.mAnimatedWrapperContainer.findViewById(R.id.alertTitle);
        this.mMessageView = (TextView)this.mAnimatedWrapperContainer.findViewById(16908299);
        this.mButtonPanel = (ViewGroup)this.mAnimatedWrapperContainer.findViewById(R.id.buttonPanel);
        this.mButtonPositive = (Button)this.mButtonPanel.findViewById(16908313);
        this.mButtonPositive.setOnClickListener((View.OnClickListener)this);
        this.mButtonNegative = (Button)this.mButtonPanel.findViewById(16908314);
        this.mButtonNegative.setOnClickListener((View.OnClickListener)this);
        this.mButtonNeutral = (Button)this.mButtonPanel.findViewById(16908315);
        this.mButtonNeutral.setOnClickListener((View.OnClickListener)this);
        this.setupLayout();
        this.mHandler = new Handler((Handler.Callback)this);
        this.mInterpolator = AnimationUtils.loadInterpolator((Context)this, (int)17563661);
        this.mButtonPanelFloatHeight = this.getResources().getDimension(R.dimen.diag_floating_height);
        this.mParentPanel = (ObservableScrollView)this.findViewById(R.id.parentPanel);
        this.mParentPanel.addOnLayoutChangeListener(this);
        this.mParentPanel.setOnScrollListener(this);
        this.mParentPanel.setOnApplyWindowInsetsListener(this);
    }

    public void onLayoutChange(View view, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        if (this.mButtonPanelAnimator != null) {
            this.mButtonPanelAnimator.cancel();
        }
        this.mHandler.removeMessages(1001);
        this.mHiddenBefore = false;
        if (this.mAnimatedWrapperContainer.getHeight() > this.mParentPanel.getHeight()) {
            this.mButtonPanel.setTranslationZ(this.mButtonPanelFloatHeight);
            this.mHandler.sendEmptyMessageDelayed(1001, 1500L);
            this.mButtonPanelAnimator = ObjectAnimator.ofPropertyValuesHolder((Object)this.mButtonPanel, (PropertyValuesHolder[])new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat((Property)View.TRANSLATION_Y, (float[])new float[]{this.getButtonBarFloatingBottomTranslation(), this.getButtonBarFloatingTopTranslation()}), PropertyValuesHolder.ofFloat((Property)View.TRANSLATION_Z, (float[])new float[]{0.0f, this.mButtonPanelFloatHeight})});
            this.mButtonPanelAnimator.setDuration(500L);
            this.mButtonPanelAnimator.setInterpolator((TimeInterpolator)this.mInterpolator);
            this.mButtonPanelAnimator.start();
            return;
        }
        this.mButtonPanel.setTranslationY(0.0f);
        this.mButtonPanel.setTranslationZ(0.0f);
        this.mButtonPanel.offsetTopAndBottom(this.mParentPanel.getHeight() - this.mAnimatedWrapperContainer.getHeight());
        this.mAnimatedWrapperContainer.setBottom(this.mParentPanel.getHeight());
    }

    public void onNegativeButtonClick() {
        this.finish();
    }

    public void onNeutralButtonClick() {
        this.finish();
    }

    public void onPositiveButtonClick() {
        this.finish();
    }

    @Override
    public void onScroll(float f2) {
        this.mHandler.removeMessages(1001);
        this.hideButtonBar();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void setupLayout() {
        int n2 = 0;
        CharSequence charSequence = this.getAlertTitle();
        if (TextUtils.isEmpty((CharSequence)charSequence)) {
            this.mTitleView.setVisibility(8);
        } else {
            this.mMessageView.setVisibility(0);
            this.mTitleView.setText(charSequence);
        }
        if (TextUtils.isEmpty((CharSequence)(charSequence = this.getMessage()))) {
            this.mMessageView.setVisibility(8);
        } else {
            this.mMessageView.setVisibility(0);
            this.mMessageView.setText(charSequence);
        }
        boolean bl2 = this.setButton(this.mButtonPositive, this.getPositiveButtonText(), this.getPositiveButtonDrawable());
        int n3 = this.setButton(this.mButtonNegative, this.getNegativeButtonText(), this.getNegativeButtonDrawable()) || bl2 ? 1 : 0;
        n3 = this.setButton(this.mButtonNeutral, this.getNeutralButtonText(), this.getNeutralButtonDrawable()) || n3 != 0 ? 1 : 0;
        charSequence = this.mButtonPanel;
        n3 = n3 != 0 ? n2 : 8;
        charSequence.setVisibility(n3);
    }
}

