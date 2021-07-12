/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.FrameLayout
 *  android.widget.TextView
 */
package android.support.wearable.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.wearable.R;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

@TargetApi(value=20)
public class DismissOverlayView
extends FrameLayout {
    private static final String KEY_FIRST_RUN = "first_run";
    private static final String PREF_NAME = "android.support.wearable.DismissOverlay";
    private View mDismissButton;
    private boolean mFirstRun = true;
    private TextView mFirstRunText;
    private SharedPreferences mPrefs;

    public DismissOverlayView(Context context) {
        this(context, null, 0);
    }

    public DismissOverlayView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DismissOverlayView(final Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        LayoutInflater.from((Context)context).inflate(R.layout.dismiss_overlay, (ViewGroup)this, true);
        this.setBackgroundResource(R.color.dismiss_overlay_bg);
        this.setClickable(true);
        if (!this.isInEditMode()) {
            this.mPrefs = context.getSharedPreferences(PREF_NAME, 0);
            this.mFirstRun = this.mPrefs.getBoolean(KEY_FIRST_RUN, true);
        }
        this.mFirstRunText = (TextView)this.findViewById(R.id.dismiss_overlay_explain);
        this.mDismissButton = this.findViewById(R.id.dismiss_overlay_button);
        this.mDismissButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (context instanceof Activity) {
                    ((Activity)context).finish();
                }
            }
        });
        this.setVisibility(8);
    }

    private void hide() {
        this.animate().alpha(0.0f).setDuration(200L).withEndAction(new Runnable(){

            @Override
            public void run() {
                DismissOverlayView.this.setVisibility(8);
                DismissOverlayView.this.setAlpha(1.0f);
            }
        }).start();
        if (this.mFirstRun) {
            this.mFirstRun = false;
            this.mPrefs.edit().putBoolean(KEY_FIRST_RUN, false).apply();
        }
    }

    static void resetPrefs(Context context) {
        context.getSharedPreferences(PREF_NAME, 0).edit().clear().apply();
    }

    public boolean performClick() {
        super.performClick();
        this.hide();
        return true;
    }

    public void setIntroText(int n2) {
        this.mFirstRunText.setText(n2);
    }

    public void setIntroText(CharSequence charSequence) {
        this.mFirstRunText.setText(charSequence);
    }

    public void show() {
        this.setAlpha(0.0f);
        this.mFirstRunText.setVisibility(8);
        this.mDismissButton.setVisibility(0);
        this.setVisibility(0);
        this.animate().alpha(1.0f).setDuration(200L).start();
    }

    public void showIntroIfNecessary() {
        if (!this.mFirstRun) {
            return;
        }
        if (TextUtils.isEmpty((CharSequence)this.mFirstRunText.getText())) {
            this.mFirstRun = false;
            return;
        }
        this.mFirstRunText.setVisibility(0);
        this.mDismissButton.setVisibility(8);
        this.setVisibility(0);
        this.postDelayed(new Runnable(){

            @Override
            public void run() {
                DismissOverlayView.this.hide();
            }
        }, 3000L);
    }
}

