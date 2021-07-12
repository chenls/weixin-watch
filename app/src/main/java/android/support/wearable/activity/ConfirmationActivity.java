/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.animation.StateListAnimator
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.graphics.drawable.Animatable
 *  android.graphics.drawable.AnimationDrawable
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.Handler
 *  android.view.View
 *  android.widget.TextView
 */
package android.support.wearable.activity;

import android.animation.StateListAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.R;
import android.support.wearable.view.ActionLabel;
import android.support.wearable.view.ActionPage;
import android.view.View;
import android.widget.TextView;

@TargetApi(value=21)
public class ConfirmationActivity
extends Activity {
    private static final int CONFIRMATION_ANIMATION_DURATION_MS = 1666;
    public static final String EXTRA_ANIMATION_TYPE = "animation_type";
    public static final String EXTRA_MESSAGE = "message";
    public static final int FAILURE_ANIMATION = 3;
    public static final int OPEN_ON_PHONE_ANIMATION = 2;
    private static final int OPEN_ON_PHONE_ANIMATION_DURATION_MS = 1666;
    public static final int SUCCESS_ANIMATION = 1;
    private static final int TEXT_FADE_OFFSET_TIME_MS = 50;
    private ActionPage mActionPage;
    private final Handler mHandler = new Handler();

    private static long getAnimationDuration(AnimationDrawable animationDrawable) {
        int n2 = animationDrawable.getNumberOfFrames();
        long l2 = 0L;
        for (int i2 = 0; i2 < n2; ++i2) {
            l2 += (long)animationDrawable.getDuration(i2);
        }
        return l2;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public void onCreate(Bundle object) {
        long l2;
        super.onCreate(object);
        Intent intent = this.getIntent();
        int n2 = intent.getIntExtra(EXTRA_ANIMATION_TYPE, 1);
        String string2 = intent.getStringExtra(EXTRA_MESSAGE);
        this.mActionPage = new ActionPage((Context)this);
        if (n2 == 3) {
            this.setContentView(R.layout.error_layout);
            ((TextView)this.findViewById(R.id.message)).setText((CharSequence)string2);
            l2 = 2000L;
        } else {
            void var1_5;
            this.mActionPage.setColor(0);
            this.mActionPage.setStateListAnimator(new StateListAnimator());
            this.mActionPage.setImageScaleMode(ActionPage.SCALE_MODE_CENTER);
            this.setContentView((View)this.mActionPage);
            if (string2 != null) {
                this.mActionPage.setText(string2);
            }
            switch (n2) {
                default: {
                    throw new IllegalArgumentException("Unknown type of animation: " + n2);
                }
                case 2: {
                    Drawable drawable2 = this.getDrawable(R.drawable.open_on_phone_animation);
                    break;
                }
                case 1: {
                    Drawable drawable3 = this.getDrawable(R.drawable.generic_confirmation_animation);
                }
            }
            l2 = 1666L;
            this.mActionPage.setImageDrawable((Drawable)var1_5);
            final ActionLabel actionLabel = this.mActionPage.getLabel();
            final long l3 = Math.max(0L, 1666L - 2L * (50L + actionLabel.animate().getDuration()));
            ((Animatable)var1_5).start();
            actionLabel.setAlpha(0.0f);
            actionLabel.animate().alpha(1.0f).setStartDelay(50L).withEndAction(new Runnable(){

                @Override
                public void run() {
                    actionLabel.animate().alpha(0.0f).setStartDelay(l3);
                }
            });
        }
        this.mActionPage.setKeepScreenOn(true);
        this.mHandler.postDelayed(new Runnable(){

            @Override
            public void run() {
                ConfirmationActivity.this.finish();
                ConfirmationActivity.this.overridePendingTransition(0, 0x10A0001);
            }
        }, l2);
    }
}

