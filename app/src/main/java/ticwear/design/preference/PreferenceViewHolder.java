/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.text.TextUtils
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package ticwear.design.preference;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import ticwear.design.DesignConfig;
import ticwear.design.R;
import ticwear.design.widget.FocusableLinearLayoutManager;

public class PreferenceViewHolder
extends FocusableLinearLayoutManager.ViewHolder {
    static final String TAG = "PrefVH";
    protected ImageView iconBackground;
    protected ViewGroup iconFrame = (ViewGroup)this.findViewById(R.id.icon_frame);
    protected float iconScaleDown;
    protected float iconScaleUp;
    protected ImageView iconView;
    protected float itemAlphaDown;
    protected TextView summaryView;
    protected TextView titleView;
    protected ViewGroup widgetFrame;

    /*
     * Enabled aggressive block sorting
     */
    PreferenceViewHolder(@NonNull View view, @Nullable View view2) {
        super(view);
        this.iconBackground = (ImageView)this.findViewById(R.id.icon_background);
        this.iconView = (ImageView)this.findViewById(16908294);
        this.titleView = (TextView)this.findViewById(16908310);
        this.summaryView = (TextView)this.findViewById(0x1020010);
        this.widgetFrame = (ViewGroup)this.findViewById(16908312);
        if (this.widgetFrame != null) {
            if (view2 != null) {
                this.widgetFrame.addView(view2);
                this.widgetFrame.setVisibility(0);
            } else {
                this.widgetFrame.setVisibility(8);
            }
        }
        this.initItemAnimationProperties(view.getContext());
    }

    public PreferenceViewHolder(@NonNull ViewGroup viewGroup, @LayoutRes int n2) {
        this(viewGroup, n2, 0);
    }

    public PreferenceViewHolder(@NonNull ViewGroup viewGroup, @LayoutRes int n2, @LayoutRes int n3) {
        this(PreferenceViewHolder.inflateView(viewGroup, n2), PreferenceViewHolder.inflateView(viewGroup, n3));
    }

    @CheckResult
    public static View inflateView(@NonNull ViewGroup viewGroup, @LayoutRes int n2) {
        LayoutInflater layoutInflater = (LayoutInflater)viewGroup.getContext().getSystemService("layout_inflater");
        if (n2 == 0) {
            return null;
        }
        return layoutInflater.inflate(n2, viewGroup, false);
    }

    private void initItemAnimationProperties(Context context) {
        float f2 = context.getResources().getDimensionPixelSize(R.dimen.tic_list_item_icon_frame_size_large);
        float f3 = context.getResources().getDimensionPixelSize(R.dimen.tic_list_item_icon_frame_size_normal);
        float f4 = context.getResources().getDimensionPixelSize(R.dimen.tic_list_item_icon_frame_size_small);
        this.iconScaleUp = f2 / f3;
        this.iconScaleDown = f4 / f3;
        this.itemAlphaDown = 0.6f;
    }

    private void setEnabledStateOnViews(View view, boolean bl2) {
        view.setEnabled(bl2);
        if (view instanceof ViewGroup) {
            view = (ViewGroup)view;
            for (int i2 = view.getChildCount() - 1; i2 >= 0; --i2) {
                this.setEnabledStateOnViews(view.getChildAt(i2), bl2);
            }
        }
    }

    private boolean showIconAnimation() {
        return this.iconView != null && this.iconFrame != null && this.iconFrame.getVisibility() == 0;
    }

    private void transform(float f2, float f3, long l2) {
        this.itemView.animate().cancel();
        if (this.titleView != null) {
            this.titleView.animate().cancel();
        }
        if (this.summaryView != null) {
            this.summaryView.animate().cancel();
        }
        if (this.showIconAnimation()) {
            this.iconView.animate().cancel();
        }
        if (l2 > 0L) {
            this.itemView.animate().setDuration(l2).scaleX(f2).scaleY(f2).start();
            if (this.titleView != null) {
                this.titleView.animate().setDuration(l2).alpha(f3).start();
            }
            if (this.summaryView != null) {
                this.summaryView.animate().setDuration(l2).alpha(f3).start();
            }
            if (this.showIconAnimation()) {
                f2 = 1.0f / f2;
                this.iconView.animate().setDuration(l2).scaleX(f2).scaleY(f2).start();
            }
            return;
        }
        if (this.showIconAnimation()) {
            float f4 = 1.0f / f2;
            this.iconView.setScaleX(f4);
            this.iconView.setScaleY(f4);
        }
        if (this.titleView != null) {
            this.titleView.setAlpha(f3);
        }
        if (this.summaryView != null) {
            this.summaryView.setAlpha(f3);
        }
        this.itemView.setScaleX(f2);
        this.itemView.setScaleY(f2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @CallSuper
    public void bind(@NonNull PreferenceData preferenceData) {
        int n2 = 8;
        if (DesignConfig.DEBUG_RECYCLER_VIEW) {
            Log.v((String)TAG, (String)(this.getLogPrefix() + "bind to " + preferenceData.title + this.getLogSuffix()));
        }
        this.bindTextView(this.titleView, preferenceData.title);
        this.bindTextView(this.summaryView, preferenceData.summary);
        int n3 = preferenceData.removeIconIfEmpty && preferenceData.icon == null ? 1 : 0;
        if (this.iconView != null) {
            if (preferenceData.icon != null) {
                this.iconView.setImageDrawable(preferenceData.icon);
            }
            preferenceData = this.iconView;
            int n4 = n3 != 0 ? 8 : 0;
            preferenceData.setVisibility(n4);
        }
        if (this.iconFrame != null) {
            preferenceData = this.iconFrame;
            n3 = n3 != 0 ? n2 : 0;
            preferenceData.setVisibility(n3);
        }
    }

    protected void bindTextView(@Nullable TextView textView, @Nullable CharSequence charSequence) {
        block3: {
            block2: {
                if (textView == null) break block2;
                if (TextUtils.isEmpty((CharSequence)charSequence)) break block3;
                textView.setText(charSequence);
                textView.setVisibility(0);
            }
            return;
        }
        textView.setVisibility(8);
    }

    protected <V extends View> V findViewById(@IdRes int n2) {
        View view;
        View view2 = view = this.itemView.findViewById(n2);
        if (view == null) {
            view2 = null;
        }
        return (V)view2;
    }

    @Override
    protected void onCentralProgressUpdated(float f2, long l2) {
        float f3 = this.iconScaleDown;
        float f4 = this.iconScaleUp;
        float f5 = this.itemAlphaDown;
        this.transform(f3 + (f4 - f3) * f2, f5 + (1.0f - f5) * this.getFocusInterpolator().getInterpolation(f2), l2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onFocusStateChanged(int n2, boolean bl2) {
        if (n2 == 0) {
            long l2 = bl2 ? this.getDefaultAnimDuration() : 0L;
            this.transform(1.0f, 1.0f, l2);
        }
        if (DesignConfig.DEBUG_RECYCLER_VIEW) {
            Log.d((String)TAG, (String)(this.getLogPrefix() + "focus state to " + n2 + ", animate " + bl2 + ", view alpha " + this.itemView.getAlpha() + this.getLogSuffix()));
        }
    }

    public void setEnabled(boolean bl2) {
        this.setEnabledStateOnViews(this.itemView, bl2);
    }

    protected static class PreferenceData {
        @Nullable
        protected Drawable icon;
        protected boolean removeIconIfEmpty = true;
        @Nullable
        protected CharSequence summary;
        @Nullable
        protected CharSequence title;

        protected PreferenceData() {
        }

        @Nullable
        public Drawable getIcon() {
            return this.icon;
        }

        @Nullable
        public CharSequence getSummary() {
            return this.summary;
        }

        @Nullable
        public CharSequence getTitle() {
            return this.title;
        }

        public boolean isRemoveIconIfEmpty() {
            return this.removeIconIfEmpty;
        }
    }
}

