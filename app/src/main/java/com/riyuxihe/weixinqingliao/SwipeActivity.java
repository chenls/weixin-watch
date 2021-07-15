package com.riyuxihe.weixinqingliao;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

public class SwipeActivity extends AppCompatActivity {
    private final boolean swipeFinished = false;
    protected boolean swipeAnyWhere = false;
    protected boolean swipeEnabled = true;
    private SwipeLayout swipeLayout;

    public static int getScreenWidth(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.swipeLayout = new SwipeLayout(this);
    }

    public boolean isSwipeAnyWhere() {
        return this.swipeAnyWhere;
    }

    public void setSwipeAnyWhere(boolean swipeAnyWhere2) {
        this.swipeAnyWhere = swipeAnyWhere2;
    }

    public boolean isSwipeEnabled() {
        return this.swipeEnabled;
    }

    public void setSwipeEnabled(boolean swipeEnabled2) {
        this.swipeEnabled = swipeEnabled2;
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.swipeLayout.replaceLayer(this);
    }

    public void finish() {
        if (this.swipeFinished) {
            super.finish();
            overridePendingTransition(0, 0);
            return;
        }
        this.swipeLayout.cancelPotentialAnimation();
        super.finish();
        overridePendingTransition(0, R.anim.slide_out_right);
    }

    /* access modifiers changed from: protected */
    public void onSwipeBack() {
        Intent startMain = new Intent("android.intent.action.MAIN");
        startMain.addCategory("android.intent.category.HOME");
        startMain.setFlags(268435456);
        startActivity(startMain);
    }

    class SwipeLayout extends FrameLayout {
        private final int duration = ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
        ObjectAnimator animator;
        boolean canSwipe = false;
        View content;
        float currentX;
        float currentY;
        float downX;
        float downY;
        boolean hasIgnoreFirstMove;
        boolean ignoreSwipe = false;
        float lastX;
        Activity mActivity;
        int screenWidth = 1080;
        int sideWidth = 72;
        int sideWidthInDP = 16;
        int touchSlop = 60;
        int touchSlopDP = 20;
        VelocityTracker tracker;
        private Drawable leftShadow;

        public SwipeLayout(Context context) {
            super(context);
        }

        public SwipeLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public void replaceLayer(Activity activity) {
            this.leftShadow = activity.getResources().getDrawable(R.drawable.left_shadow);
            this.touchSlop = (int) (((float) this.touchSlopDP) * activity.getResources().getDisplayMetrics().density);
            this.sideWidth = (int) (((float) this.sideWidthInDP) * activity.getResources().getDisplayMetrics().density);
            this.mActivity = activity;
            this.screenWidth = SwipeActivity.getScreenWidth(activity);
            setClickable(true);
            ViewGroup root = (ViewGroup) activity.getWindow().getDecorView();
            this.content = root.getChildAt(0);
            ViewGroup.LayoutParams params = this.content.getLayoutParams();
            ViewGroup.LayoutParams params2 = new ViewGroup.LayoutParams(-1, -1);
            root.removeView(this.content);
            addView(this.content, params2);
            root.addView(this, params);
        }

        /* access modifiers changed from: protected */
        public boolean drawChild(@NonNull Canvas canvas, @NonNull View child, long drawingTime) {
            boolean result = super.drawChild(canvas, child, drawingTime);
            int shadowWidth = this.leftShadow.getIntrinsicWidth();
            int left = ((int) getContentX()) - shadowWidth;
            this.leftShadow.setBounds(left, child.getTop(), left + shadowWidth, child.getBottom());
            this.leftShadow.draw(canvas);
            return result;
        }

        public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
            if (SwipeActivity.this.swipeEnabled && !this.canSwipe && !this.ignoreSwipe) {
                if (SwipeActivity.this.swipeAnyWhere) {
                    switch (ev.getAction()) {
                        case 0:
                            this.downX = ev.getX();
                            this.downY = ev.getY();
                            this.currentX = this.downX;
                            this.currentY = this.downY;
                            this.lastX = this.downX;
                            break;
                        case 2:
                            float dx = ev.getX() - this.downX;
                            float dy = ev.getY() - this.downY;
                            if ((dx * dx) + (dy * dy) > ((float) (this.touchSlop * this.touchSlop))) {
                                if (dy != 0.0f && Math.abs(dx / dy) <= 1.0f) {
                                    this.ignoreSwipe = true;
                                    break;
                                } else {
                                    this.downX = ev.getX();
                                    this.downY = ev.getY();
                                    this.currentX = this.downX;
                                    this.currentY = this.downY;
                                    this.lastX = this.downX;
                                    this.canSwipe = true;
                                    this.tracker = VelocityTracker.obtain();
                                    return true;
                                }
                            }
                            break;
                    }
                } else if (ev.getAction() == 0 && ev.getX() < ((float) this.sideWidth)) {
                    this.canSwipe = true;
                    this.tracker = VelocityTracker.obtain();
                    return true;
                }
            }
            if (ev.getAction() == 1 || ev.getAction() == 3) {
                this.ignoreSwipe = false;
            }
            return super.dispatchTouchEvent(ev);
        }

        public boolean onInterceptTouchEvent(MotionEvent ev) {
            return this.canSwipe || super.onInterceptTouchEvent(ev);
        }

        public boolean onTouchEvent(@NonNull MotionEvent event) {
            if (this.canSwipe) {
                this.tracker.addMovement(event);
                switch (event.getAction()) {
                    case 0:
                        this.downX = event.getX();
                        this.downY = event.getY();
                        this.currentX = this.downX;
                        this.currentY = this.downY;
                        this.lastX = this.downX;
                        break;
                    case 1:
                    case 3:
                        this.tracker.computeCurrentVelocity(10000);
                        this.tracker.computeCurrentVelocity(1000, 20000.0f);
                        this.canSwipe = false;
                        this.hasIgnoreFirstMove = false;
                        if (Math.abs(this.tracker.getXVelocity()) > ((float) (this.screenWidth * 3))) {
                            animateFromVelocity(this.tracker.getXVelocity());
                        } else if (getContentX() > ((float) (this.screenWidth / 3))) {
                            animateFinish(false);
                        } else {
                            animateBack(false);
                        }
                        this.tracker.recycle();
                        break;
                    case 2:
                        this.currentX = event.getX();
                        this.currentY = event.getY();
                        float dx = this.currentX - this.lastX;
                        if (dx != 0.0f && !this.hasIgnoreFirstMove) {
                            this.hasIgnoreFirstMove = true;
                            dx /= dx;
                        }
                        if (getContentX() + dx < 0.0f) {
                            setContentX(0.0f);
                        } else {
                            setContentX(getContentX() + dx);
                        }
                        this.lastX = this.currentX;
                        break;
                }
            }
            return super.onTouchEvent(event);
        }

        public void cancelPotentialAnimation() {
            if (this.animator != null) {
                this.animator.removeAllListeners();
                this.animator.cancel();
            }
        }

        public float getContentX() {
            return this.content.getX();
        }

        public void setContentX(float x) {
            this.content.setX((float) ((int) x));
            invalidate();
        }

        /* access modifiers changed from: private */
        public void animateBack(boolean withVel) {
            cancelPotentialAnimation();
            this.animator = ObjectAnimator.ofFloat(this, "contentX", getContentX(), 0.0f);
            int tmpDuration = withVel ? (int) ((200.0f * getContentX()) / ((float) this.screenWidth)) : ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
            if (tmpDuration < 100) {
                tmpDuration = 100;
            }
            this.animator.setDuration(tmpDuration);
            this.animator.setInterpolator(new DecelerateInterpolator());
            this.animator.start();
        }

        private void animateFinish(boolean withVel) {
            cancelPotentialAnimation();
            this.animator = ObjectAnimator.ofFloat(this, "contentX", getContentX(), (float) this.screenWidth);
            int tmpDuration = withVel ? (int) ((200.0f * (((float) this.screenWidth) - getContentX())) / ((float) this.screenWidth)) : ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
            if (tmpDuration < 100) {
                tmpDuration = 100;
            }
            this.animator.setDuration(tmpDuration);
            this.animator.setInterpolator(new DecelerateInterpolator());
            this.animator.addListener(new Animator.AnimatorListener() {
                public void onAnimationStart(Animator animation) {
                }

                public void onAnimationRepeat(Animator animation) {
                }

                public void onAnimationEnd(Animator animation) {
                    SwipeActivity.this.onSwipeBack();
                    SwipeLayout.this.animateBack(true);
                }

                public void onAnimationCancel(Animator animation) {
                }
            });
            this.animator.start();
        }

        private void animateFromVelocity(float v) {
            if (v > 0.0f) {
                if (getContentX() >= ((float) (this.screenWidth / 3)) || ((v * 200.0f) / 1000.0f) + getContentX() >= ((float) (this.screenWidth / 3))) {
                    animateFinish(true);
                } else {
                    animateBack(false);
                }
            } else if (getContentX() <= ((float) (this.screenWidth / 3)) || ((v * 200.0f) / 1000.0f) + getContentX() <= ((float) (this.screenWidth / 3))) {
                animateBack(true);
            } else {
                animateFinish(false);
            }
        }
    }
}
