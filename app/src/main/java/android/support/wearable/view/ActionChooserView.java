/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorSet
 *  android.animation.ObjectAnimator
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.util.Property
 *  android.util.SparseArray
 *  android.util.TypedValue
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.GestureDetector$SimpleOnGestureListener
 *  android.view.MotionEvent
 *  android.view.View
 */
package android.support.wearable.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.wearable.R;
import android.util.AttributeSet;
import android.util.Property;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@TargetApi(value=20)
public class ActionChooserView
extends View {
    private static final int ANIMATION_STATE_DISABLED = 2;
    private static final int ANIMATION_STATE_DISABLING = 1;
    private static final int ANIMATION_STATE_ENABLED = 0;
    private static final float MIN_SWIPE_OFFSET = 0.25f;
    private static Property<ActionChooserView, Float> OFFSET = new Property<ActionChooserView, Float>(Float.class, "offset"){

        public Float get(ActionChooserView actionChooserView) {
            return Float.valueOf(actionChooserView.getOffset());
        }

        public void set(ActionChooserView actionChooserView, Float f2) {
            actionChooserView.setAnimationOffset(f2.floatValue());
        }
    };
    public static final int OPTION_END = 2;
    public static final int OPTION_START = 1;
    private static Property<ActionChooserView, Float> SELECTED_MULTIPLIER = new Property<ActionChooserView, Float>(Float.class, "selected_multiplier"){

        public Float get(ActionChooserView actionChooserView) {
            return Float.valueOf(actionChooserView.getSelectedMultiplier());
        }

        public void set(ActionChooserView actionChooserView, Float f2) {
            actionChooserView.setSelectedMultiplier(f2.floatValue());
        }
    };
    private final float mAnimMaxOffset;
    private int mAnimationState = 0;
    private final float mBaseRadiusPercentage;
    private final int mBounceAnimationDuration;
    private final int mBounceDelay;
    private ObjectAnimator mCenterAnimator;
    private final Paint mCirclePaint;
    private final int mConfirmationDelay;
    private ObjectAnimator mExpandAnimator;
    private final boolean mExpandSelected;
    private final long mExpandToFullMillis;
    private GestureDetector mGestureDetector;
    private final float mIconHeightPercentage;
    private final float mIdleAnimationSpeed;
    private AnimatorSet mIdleAnimatorSet;
    private float mLastTouchOffset;
    private float mLastTouchX;
    private ArrayList<ActionChooserListener> mListeners;
    private final float mMaxRadiusPercentage;
    private final float mMinDragSelectPercent;
    private final float mMinSwipeSelectPercent;
    private float mOffset;
    private SparseArray<Option> mOptions;
    private ObjectAnimator mReturnAnimator;
    private Runnable mSelectOptionRunnable = new Runnable(){

        @Override
        public void run() {
            if (ActionChooserView.this.mListeners != null) {
                Iterator iterator = ActionChooserView.this.mListeners.iterator();
                while (iterator.hasNext()) {
                    ((ActionChooserListener)iterator.next()).onOptionChosen(ActionChooserView.this.mSelectedOption);
                }
            }
        }
    };
    private float mSelectedMultiplier = 1.0f;
    private Integer mSelectedOption;
    private float mSelectedPercent;
    private float mSpeed;
    private boolean mTouchedEnabled = true;

    public ActionChooserView(Context context) {
        this(context, null);
    }

    public ActionChooserView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ActionChooserView(Context object, AttributeSet attributeSet, int n2) {
        super((Context)object, attributeSet, n2);
        this.mCirclePaint = new Paint();
        this.mCirclePaint.setAntiAlias(true);
        this.mCirclePaint.setStyle(Paint.Style.FILL);
        object = new TypedValue();
        this.getResources().getValue(R.dimen.action_chooser_bounce_in_percent, (TypedValue)object, true);
        this.mAnimMaxOffset = object.getFloat();
        this.getResources().getValue(R.dimen.action_chooser_base_radius_percent, (TypedValue)object, true);
        this.mBaseRadiusPercentage = object.getFloat();
        this.getResources().getValue(R.dimen.action_chooser_max_radius_percent, (TypedValue)object, true);
        this.mMaxRadiusPercentage = object.getFloat();
        this.getResources().getValue(R.dimen.action_chooser_icon_height_percent, (TypedValue)object, true);
        this.mIconHeightPercentage = object.getFloat();
        this.getResources().getValue(R.dimen.action_chooser_min_drag_select_percent, (TypedValue)object, true);
        this.mMinDragSelectPercent = object.getFloat();
        this.getResources().getValue(R.dimen.action_chooser_min_swipe_select_percent, (TypedValue)object, true);
        this.mMinSwipeSelectPercent = object.getFloat();
        this.mBounceAnimationDuration = this.getResources().getInteger(R.integer.action_chooser_anim_duration);
        this.mBounceDelay = this.getResources().getInteger(R.integer.action_chooser_bounce_delay);
        this.mIdleAnimationSpeed = this.mMaxRadiusPercentage / (float)this.mBounceAnimationDuration;
        this.mConfirmationDelay = this.getResources().getInteger(R.integer.action_chooser_confirmation_duration);
        this.mExpandSelected = this.getResources().getBoolean(R.bool.action_choose_expand_selected);
        this.mExpandToFullMillis = this.getResources().getInteger(R.integer.action_choose_expand_full_duration);
        this.mOptions = new SparseArray();
        object = new ArrayList();
        ((ArrayList)object).addAll(this.generateOptionAnimation(1));
        ((ArrayList)object).addAll(this.generateOptionAnimation(2));
        this.mIdleAnimatorSet = new AnimatorSet();
        this.mIdleAnimatorSet.playSequentially((List)object);
        this.mIdleAnimatorSet.addListener(new Animator.AnimatorListener(){
            private boolean mCancelled;

            public void onAnimationCancel(Animator animator2) {
                this.mCancelled = true;
            }

            public void onAnimationEnd(Animator animator2) {
                if (!this.mCancelled && ActionChooserView.this.mAnimationState == 0) {
                    ActionChooserView.this.mIdleAnimatorSet.start();
                }
            }

            public void onAnimationRepeat(Animator animator2) {
            }

            public void onAnimationStart(Animator animator2) {
                this.mCancelled = false;
            }
        });
        this.mReturnAnimator = ObjectAnimator.ofFloat((Object)((Object)this), OFFSET, (float[])new float[]{0.0f});
        this.mReturnAnimator.addListener(new Animator.AnimatorListener(){
            private boolean mCancelled;

            public void onAnimationCancel(Animator animator2) {
                this.mCancelled = true;
            }

            public void onAnimationEnd(Animator animator2) {
                if (!this.mCancelled && ActionChooserView.this.mAnimationState == 0) {
                    ActionChooserView.this.mIdleAnimatorSet.start();
                }
            }

            public void onAnimationRepeat(Animator animator2) {
            }

            public void onAnimationStart(Animator animator2) {
                this.mCancelled = false;
            }
        });
        this.mCenterAnimator = ObjectAnimator.ofFloat((Object)((Object)this), OFFSET, (float[])new float[]{0.0f});
        this.mExpandAnimator = ObjectAnimator.ofFloat((Object)((Object)this), SELECTED_MULTIPLIER, (float[])new float[]{1.0f, (float)Math.sqrt(2.0)});
        this.mGestureDetector = new GestureDetector(this.getContext(), (GestureDetector.OnGestureListener)new GestureDetector.SimpleOnGestureListener(){

            /*
             * Enabled aggressive block sorting
             */
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
                if (!(Math.abs(motionEvent2.getX() - motionEvent.getX()) >= (float)ActionChooserView.this.getMeasuredWidth() * ActionChooserView.this.mMinSwipeSelectPercent)) {
                    return false;
                }
                int n2 = f2 < 0.0f ? 2 : 1;
                ActionChooserView.this.selectOption(n2);
                ActionChooserView.this.enableAnimations(true);
                return true;
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    private void drawOption(Canvas canvas, Option option, int n2, int n3, float f2) {
        block3: {
            block2: {
                if (option == null) break block2;
                this.mCirclePaint.setColor(option.color);
                canvas.drawCircle((float)n2, (float)n3, f2, this.mCirclePaint);
                if (option.icon != null) break block3;
            }
            return;
        }
        Rect rect = option.icon.getBounds();
        rect.offsetTo(n2 - rect.width() / 2, n3 - rect.height() / 2);
        option.icon.setBounds(rect);
        option.icon.draw(canvas);
    }

    private void enableAnimations(boolean bl2) {
        this.enableAnimations(bl2, true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void enableAnimations(boolean bl2, boolean bl3) {
        if (bl2) {
            this.mAnimationState = 0;
            if (this.mSelectedOption != null) {
                this.mIdleAnimatorSet.cancel();
                this.mCenterAnimator.cancel();
                this.mReturnAnimator.cancel();
                ObjectAnimator objectAnimator = this.mCenterAnimator;
                float f2 = this.getOffset();
                float f3 = this.getMaxOffset();
                int n2 = this.mSelectedOption == 2 ? -1 : 1;
                objectAnimator.setFloatValues(new float[]{f2, (float)n2 * f3});
                this.mCenterAnimator.setDuration((long)Math.round((Math.abs(this.getMaxOffset()) - Math.abs(this.getOffset())) / Math.max(this.mIdleAnimationSpeed, this.mSpeed)));
                this.mCenterAnimator.start();
                return;
            }
            if (this.mOffset == 0.0f) {
                this.mIdleAnimatorSet.start();
                return;
            }
            float f4 = this.getOffset();
            this.mReturnAnimator.setFloatValues(new float[]{f4, 0.0f});
            this.mReturnAnimator.setDuration((long)Math.round(Math.abs(f4 / this.mIdleAnimationSpeed)));
            this.mReturnAnimator.start();
            return;
        }
        if (bl3) {
            this.mAnimationState = 2;
            this.mIdleAnimatorSet.cancel();
            this.mCenterAnimator.cancel();
            this.mReturnAnimator.cancel();
            return;
        }
        this.mAnimationState = 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    private ArrayList<Animator> generateOptionAnimation(int n2) {
        ArrayList<Animator> arrayList = new ArrayList<Animator>();
        n2 = n2 == 1 ? 1 : -1;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat((Object)((Object)this), OFFSET, (float[])new float[]{0.0f, (float)n2 * this.mAnimMaxOffset});
        objectAnimator.setDuration((long)this.mBounceAnimationDuration);
        objectAnimator.setStartDelay((long)this.mBounceDelay);
        arrayList.add((Animator)objectAnimator);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat((Object)((Object)this), OFFSET, (float[])new float[]{(float)n2 * this.mAnimMaxOffset, 0.0f});
        objectAnimator.setDuration((long)this.mBounceAnimationDuration);
        objectAnimator.setStartDelay((long)this.mBounceDelay);
        arrayList.add((Animator)objectAnimator2);
        return arrayList;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private float getCircleRadius(int n2, float f2, float f3, boolean bl2, float f4) {
        float f5 = (f2 - this.mAnimMaxOffset) / (this.getMaxOffset() - this.mAnimMaxOffset);
        float f6 = n2;
        f2 = n2;
        f3 = Math.max(0.0f, f5 * (f3 - f6));
        if (bl2) {
            return (f2 + f3) * f4;
        }
        f4 = 1.0f;
        return (f2 + f3) * f4;
    }

    private float getMaxOffset() {
        return 0.5f + this.mBaseRadiusPercentage;
    }

    private float getOffset() {
        return this.mOffset;
    }

    private float getSelectedMultiplier() {
        return this.mSelectedMultiplier;
    }

    private boolean isSelected(int n2) {
        return this.mSelectedOption != null && this.mSelectedOption == n2;
    }

    private void layoutOption(Option option) {
        if (option == null) {
            return;
        }
        Rect rect = option.icon.getBounds();
        int n2 = Math.max(option.icon.getIntrinsicHeight(), option.icon.getIntrinsicHeight());
        float f2 = this.mIconHeightPercentage * 2.0f * this.mBaseRadiusPercentage * (float)this.getMeasuredHeight() / (float)n2;
        rect.left = 0;
        rect.top = 0;
        rect.right = Math.round((float)option.icon.getIntrinsicWidth() * f2);
        rect.bottom = Math.round((float)option.icon.getIntrinsicHeight() * f2);
    }

    private void selectOption(int n2) {
        this.mSelectedOption = n2;
        this.mTouchedEnabled = false;
    }

    private void setAnimationOffset(float f2) {
        if (this.mAnimationState != 2) {
            this.setOffset(f2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setOffset(float f2) {
        int n2 = f2 < 0.0f ? -1 : 1;
        if (this.mAnimationState == 1 && Math.abs(f2) == 0.0f) {
            this.enableAnimations(false, true);
            this.setOffsetAndNotify(0.0f);
            this.invalidate();
            return;
        }
        this.setOffsetAndNotify((float)n2 * Math.min(Math.abs(f2), this.getMaxOffset()));
        if (Math.abs(this.mOffset) >= this.getMaxOffset()) {
            n2 = n2 < 0 ? 2 : 1;
            this.mSelectedOption = n2;
            if (this.mOptions.indexOfKey(this.mSelectedOption.intValue()) > -1) {
                this.mTouchedEnabled = false;
                this.enableAnimations(false, true);
                if (this.mExpandSelected) {
                    this.mExpandAnimator.setDuration(this.mExpandToFullMillis);
                    this.mExpandAnimator.addListener(new Animator.AnimatorListener(){

                        public void onAnimationCancel(Animator animator2) {
                        }

                        public void onAnimationEnd(Animator animator2) {
                            ActionChooserView.this.removeCallbacks(ActionChooserView.this.mSelectOptionRunnable);
                            ActionChooserView.this.postDelayed(ActionChooserView.this.mSelectOptionRunnable, ActionChooserView.this.mConfirmationDelay);
                        }

                        public void onAnimationRepeat(Animator animator2) {
                        }

                        public void onAnimationStart(Animator animator2) {
                        }
                    });
                    this.mExpandAnimator.start();
                } else {
                    this.removeCallbacks(this.mSelectOptionRunnable);
                    this.postDelayed(this.mSelectOptionRunnable, this.mConfirmationDelay);
                }
            }
        }
        this.invalidate();
    }

    private void setOffsetAndNotify(float f2) {
        if (f2 != this.mOffset) {
            this.mOffset = f2;
            if (this.mSelectedPercent != (f2 = Math.max(0.0f, (Math.abs(f2) - this.mAnimMaxOffset) / (this.getMaxOffset() - this.mAnimMaxOffset)))) {
                this.mSelectedPercent = f2;
                Iterator<ActionChooserListener> iterator = this.mListeners.iterator();
                while (iterator.hasNext()) {
                    iterator.next().onOptionProgress(this.mSelectedPercent);
                }
            }
        }
    }

    private void setSelectedMultiplier(float f2) {
        this.mSelectedMultiplier = f2;
        this.invalidate();
    }

    private boolean validateOption(int n2) {
        return n2 == 1 || n2 == 2;
    }

    public void addListener(ActionChooserListener actionChooserListener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        if (!this.mListeners.contains(actionChooserListener)) {
            this.mListeners.add(actionChooserListener);
        }
    }

    public boolean canScrollHorizontally(int n2) {
        return true;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIdleAnimatorSet.start();
    }

    protected void onDetachedFromWindow() {
        this.mIdleAnimatorSet.cancel();
        super.onDetachedFromWindow();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int n2 = canvas.getHeight();
        int n3 = canvas.getWidth();
        int n4 = Math.round((float)n3 * this.mOffset);
        int n5 = Math.round((float)n2 * this.mBaseRadiusPercentage);
        float f2 = (float)n2 * this.mMaxRadiusPercentage;
        this.drawOption(canvas, (Option)this.mOptions.get(1), n4 - n5, n2 / 2, this.getCircleRadius(n5, this.mOffset, f2, this.isSelected(1), this.mSelectedMultiplier));
        this.drawOption(canvas, (Option)this.mOptions.get(2), n4 + n3 + n5, n2 / 2, this.getCircleRadius(n5, -this.mOffset, f2, this.isSelected(2), this.mSelectedMultiplier));
    }

    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        super.onLayout(bl2, n2, n3, n4, n5);
        this.layoutOption((Option)this.mOptions.get(1));
        this.layoutOption((Option)this.mOptions.get(2));
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean bl2 = true;
        if (!this.mTouchedEnabled) {
            return false;
        }
        if (this.mGestureDetector.onTouchEvent(motionEvent)) return bl2;
        switch (motionEvent.getAction() & 0xFF) {
            default: {
                return true;
            }
            case 0: {
                this.enableAnimations(false);
                this.mLastTouchX = motionEvent.getX();
                this.mLastTouchOffset = this.getOffset();
                return true;
            }
            case 2: {
                float f2 = motionEvent.getX() - this.mLastTouchX;
                this.mSpeed = Math.abs((f2 / (float)this.getWidth() - this.mLastTouchOffset) / (float)(motionEvent.getEventTime() - motionEvent.getDownTime()));
                this.setOffset(this.mLastTouchOffset + f2 / (float)this.getWidth());
                return true;
            }
            case 1: 
            case 3: 
        }
        if (Math.abs(motionEvent.getX() - this.mLastTouchX) >= (float)this.getMeasuredWidth() * this.mMinDragSelectPercent) {
            int n2 = motionEvent.getX() < this.mLastTouchX ? 2 : 1;
            this.selectOption(n2);
        }
        this.enableAnimations(true);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void performSelectOption(int n2) {
        if (!this.validateOption(n2)) {
            throw new IllegalArgumentException("unrecognized option");
        }
        if (n2 == 1) {
            this.selectOption(1);
            this.enableAnimations(true);
            return;
        } else {
            if (n2 != 2) return;
            this.selectOption(2);
            this.enableAnimations(true);
            return;
        }
    }

    public void removeListener(ActionChooserListener actionChooserListener) {
        if (this.mListeners != null) {
            this.mListeners.remove(actionChooserListener);
        }
    }

    public void setEnabled(boolean bl2) {
        boolean bl3 = this.isEnabled();
        super.setEnabled(bl2);
        if (bl3 != bl2) {
            this.mTouchedEnabled = bl2;
            this.enableAnimations(bl2, bl2);
        }
    }

    public void setOption(int n2, Drawable drawable2, int n3) {
        if (!this.validateOption(n2)) {
            throw new IllegalArgumentException("unrecognized option");
        }
        this.mOptions.put(n2, (Object)new Option(n3, drawable2));
        this.invalidate();
    }

    public static interface ActionChooserListener {
        public void onOptionChosen(int var1);

        public void onOptionProgress(float var1);
    }

    private static class Option {
        public int color;
        public Drawable icon;

        public Option(int n2, Drawable drawable2) {
            this.color = n2;
            this.icon = drawable2;
        }
    }
}

