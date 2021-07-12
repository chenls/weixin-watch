/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.ObjectAnimator
 *  android.animation.TimeInterpolator
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.graphics.Canvas
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.WindowManager
 *  android.view.animation.DecelerateInterpolator
 *  android.widget.FrameLayout
 */
package com.riyuxihe.weixinqingliao;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

public class SwipeActivity
extends AppCompatActivity {
    protected boolean swipeAnyWhere = false;
    protected boolean swipeEnabled = true;
    private boolean swipeFinished = false;
    private SwipeLayout swipeLayout;

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager)context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public void finish() {
        if (this.swipeFinished) {
            super.finish();
            this.overridePendingTransition(0, 0);
            return;
        }
        this.swipeLayout.cancelPotentialAnimation();
        super.finish();
        this.overridePendingTransition(0, 2131034140);
    }

    public boolean isSwipeAnyWhere() {
        return this.swipeAnyWhere;
    }

    public boolean isSwipeEnabled() {
        return this.swipeEnabled;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.swipeLayout = new SwipeLayout((Context)this);
    }

    @Override
    protected void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        this.swipeLayout.replaceLayer(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void onSwipeBack() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.setFlags(0x10000000);
        this.startActivity(intent);
    }

    public void setSwipeAnyWhere(boolean bl2) {
        this.swipeAnyWhere = bl2;
    }

    public void setSwipeEnabled(boolean bl2) {
        this.swipeEnabled = bl2;
    }

    class SwipeLayout
    extends FrameLayout {
        ObjectAnimator animator;
        boolean canSwipe;
        View content;
        float currentX;
        float currentY;
        float downX;
        float downY;
        private final int duration;
        boolean hasIgnoreFirstMove;
        boolean ignoreSwipe;
        float lastX;
        private Drawable leftShadow;
        Activity mActivity;
        int screenWidth;
        int sideWidth;
        int sideWidthInDP;
        int touchSlop;
        int touchSlopDP;
        VelocityTracker tracker;

        public SwipeLayout(Context context) {
            super(context);
            this.canSwipe = false;
            this.ignoreSwipe = false;
            this.sideWidthInDP = 16;
            this.sideWidth = 72;
            this.screenWidth = 1080;
            this.touchSlopDP = 20;
            this.touchSlop = 60;
            this.duration = 200;
        }

        public SwipeLayout(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.canSwipe = false;
            this.ignoreSwipe = false;
            this.sideWidthInDP = 16;
            this.sideWidth = 72;
            this.screenWidth = 1080;
            this.touchSlopDP = 20;
            this.touchSlop = 60;
            this.duration = 200;
        }

        public SwipeLayout(Context context, AttributeSet attributeSet, int n2) {
            super(context, attributeSet, n2);
            this.canSwipe = false;
            this.ignoreSwipe = false;
            this.sideWidthInDP = 16;
            this.sideWidth = 72;
            this.screenWidth = 1080;
            this.touchSlopDP = 20;
            this.touchSlop = 60;
            this.duration = 200;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void animateBack(boolean bl2) {
            this.cancelPotentialAnimation();
            this.animator = ObjectAnimator.ofFloat((Object)((Object)this), (String)"contentX", (float[])new float[]{this.getContentX(), 0.0f});
            int n2 = bl2 ? (int)(200.0f * this.getContentX() / (float)this.screenWidth) : 200;
            int n3 = n2;
            if (n2 < 100) {
                n3 = 100;
            }
            this.animator.setDuration((long)n3);
            this.animator.setInterpolator((TimeInterpolator)new DecelerateInterpolator());
            this.animator.start();
        }

        /*
         * Enabled aggressive block sorting
         */
        private void animateFinish(boolean bl2) {
            this.cancelPotentialAnimation();
            this.animator = ObjectAnimator.ofFloat((Object)((Object)this), (String)"contentX", (float[])new float[]{this.getContentX(), this.screenWidth});
            int n2 = bl2 ? (int)(200.0f * ((float)this.screenWidth - this.getContentX()) / (float)this.screenWidth) : 200;
            int n3 = n2;
            if (n2 < 100) {
                n3 = 100;
            }
            this.animator.setDuration((long)n3);
            this.animator.setInterpolator((TimeInterpolator)new DecelerateInterpolator());
            this.animator.addListener(new Animator.AnimatorListener(){

                public void onAnimationCancel(Animator animator2) {
                }

                public void onAnimationEnd(Animator animator2) {
                    SwipeActivity.this.onSwipeBack();
                    SwipeLayout.this.animateBack(true);
                }

                public void onAnimationRepeat(Animator animator2) {
                }

                public void onAnimationStart(Animator animator2) {
                }
            });
            this.animator.start();
        }

        private void animateFromVelocity(float f2) {
            if (f2 > 0.0f) {
                if (this.getContentX() < (float)(this.screenWidth / 3) && f2 * 200.0f / 1000.0f + this.getContentX() < (float)(this.screenWidth / 3)) {
                    this.animateBack(false);
                    return;
                }
                this.animateFinish(true);
                return;
            }
            if (this.getContentX() > (float)(this.screenWidth / 3) && f2 * 200.0f / 1000.0f + this.getContentX() > (float)(this.screenWidth / 3)) {
                this.animateFinish(false);
                return;
            }
            this.animateBack(true);
        }

        public void cancelPotentialAnimation() {
            if (this.animator != null) {
                this.animator.removeAllListeners();
                this.animator.cancel();
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean dispatchTouchEvent(@NonNull MotionEvent motionEvent) {
            if (SwipeActivity.this.swipeEnabled && !this.canSwipe && !this.ignoreSwipe) {
                if (SwipeActivity.this.swipeAnyWhere) {
                    switch (motionEvent.getAction()) {
                        case 0: {
                            this.downX = motionEvent.getX();
                            this.downY = motionEvent.getY();
                            this.currentX = this.downX;
                            this.currentY = this.downY;
                            this.lastX = this.downX;
                        }
                        default: {
                            break;
                        }
                        case 2: {
                            float f2;
                            float f3 = motionEvent.getX() - this.downX;
                            if (!(f3 * f3 + (f2 = motionEvent.getY() - this.downY) * f2 > (float)(this.touchSlop * this.touchSlop))) break;
                            if (f2 == 0.0f || Math.abs(f3 / f2) > 1.0f) {
                                this.downX = motionEvent.getX();
                                this.downY = motionEvent.getY();
                                this.currentX = this.downX;
                                this.currentY = this.downY;
                                this.lastX = this.downX;
                                this.canSwipe = true;
                                this.tracker = VelocityTracker.obtain();
                                return true;
                            }
                            this.ignoreSwipe = true;
                            break;
                        }
                    }
                } else if (motionEvent.getAction() == 0 && motionEvent.getX() < (float)this.sideWidth) {
                    this.canSwipe = true;
                    this.tracker = VelocityTracker.obtain();
                    return true;
                }
            }
            if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                this.ignoreSwipe = false;
            }
            return super.dispatchTouchEvent(motionEvent);
        }

        protected boolean drawChild(@NonNull Canvas canvas, @NonNull View view, long l2) {
            boolean bl2 = super.drawChild(canvas, view, l2);
            int n2 = this.leftShadow.getIntrinsicWidth();
            int n3 = (int)this.getContentX() - n2;
            this.leftShadow.setBounds(n3, view.getTop(), n3 + n2, view.getBottom());
            this.leftShadow.draw(canvas);
            return bl2;
        }

        public float getContentX() {
            return this.content.getX();
        }

        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            return this.canSwipe || super.onInterceptTouchEvent(motionEvent);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTouchEvent(@NonNull MotionEvent motionEvent) {
            if (!this.canSwipe) return super.onTouchEvent(motionEvent);
            this.tracker.addMovement(motionEvent);
            switch (motionEvent.getAction()) {
                case 0: {
                    this.downX = motionEvent.getX();
                    this.downY = motionEvent.getY();
                    this.currentX = this.downX;
                    this.currentY = this.downY;
                    this.lastX = this.downX;
                    return super.onTouchEvent(motionEvent);
                }
                case 2: {
                    float f2;
                    this.currentX = motionEvent.getX();
                    this.currentY = motionEvent.getY();
                    float f3 = f2 = this.currentX - this.lastX;
                    if (f2 != 0.0f) {
                        f3 = f2;
                        if (!this.hasIgnoreFirstMove) {
                            this.hasIgnoreFirstMove = true;
                            f3 = f2 / f2;
                        }
                    }
                    if (this.getContentX() + f3 < 0.0f) {
                        this.setContentX(0.0f);
                    } else {
                        this.setContentX(this.getContentX() + f3);
                    }
                    this.lastX = this.currentX;
                    return super.onTouchEvent(motionEvent);
                }
                case 1: 
                case 3: {
                    this.tracker.computeCurrentVelocity(10000);
                    this.tracker.computeCurrentVelocity(1000, 20000.0f);
                    this.canSwipe = false;
                    this.hasIgnoreFirstMove = false;
                    int n2 = this.screenWidth;
                    if (Math.abs(this.tracker.getXVelocity()) > (float)(n2 * 3)) {
                        this.animateFromVelocity(this.tracker.getXVelocity());
                    } else if (this.getContentX() > (float)(this.screenWidth / 3)) {
                        this.animateFinish(false);
                    } else {
                        this.animateBack(false);
                    }
                    this.tracker.recycle();
                    return super.onTouchEvent(motionEvent);
                }
            }
            return super.onTouchEvent(motionEvent);
        }

        public void replaceLayer(Activity activity) {
            this.leftShadow = activity.getResources().getDrawable(2130837667);
            this.touchSlop = (int)((float)this.touchSlopDP * activity.getResources().getDisplayMetrics().density);
            this.sideWidth = (int)((float)this.sideWidthInDP * activity.getResources().getDisplayMetrics().density);
            this.mActivity = activity;
            this.screenWidth = SwipeActivity.getScreenWidth((Context)activity);
            this.setClickable(true);
            activity = (ViewGroup)activity.getWindow().getDecorView();
            this.content = activity.getChildAt(0);
            ViewGroup.LayoutParams layoutParams = this.content.getLayoutParams();
            ViewGroup.LayoutParams layoutParams2 = new ViewGroup.LayoutParams(-1, -1);
            activity.removeView(this.content);
            this.addView(this.content, layoutParams2);
            activity.addView((View)this, layoutParams);
        }

        public void setContentX(float f2) {
            int n2 = (int)f2;
            this.content.setX((float)n2);
            this.invalidate();
        }
    }
}

