/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.design.widget;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class SwipeDismissBehavior<V extends View>
extends CoordinatorLayout.Behavior<V> {
    private static final float DEFAULT_ALPHA_END_DISTANCE = 0.5f;
    private static final float DEFAULT_ALPHA_START_DISTANCE = 0.0f;
    private static final float DEFAULT_DRAG_DISMISS_THRESHOLD = 0.5f;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    public static final int SWIPE_DIRECTION_ANY = 2;
    public static final int SWIPE_DIRECTION_END_TO_START = 1;
    public static final int SWIPE_DIRECTION_START_TO_END = 0;
    private float mAlphaEndSwipeDistance = 0.5f;
    private float mAlphaStartSwipeDistance = 0.0f;
    private final ViewDragHelper.Callback mDragCallback = new ViewDragHelper.Callback(){
        private static final int INVALID_POINTER_ID = -1;
        private int mActivePointerId = -1;
        private int mOriginalCapturedViewLeft;

        /*
         * Enabled aggressive block sorting
         */
        private boolean shouldDismiss(View view, float f2) {
            if (f2 != 0.0f) {
                boolean bl2 = ViewCompat.getLayoutDirection(view) == 1;
                if (SwipeDismissBehavior.this.mSwipeDirection == 2) return true;
                if (SwipeDismissBehavior.this.mSwipeDirection == 0) {
                    if (!(bl2 ? !(f2 < 0.0f) : !(f2 > 0.0f))) return true;
                    return false;
                } else {
                    if (SwipeDismissBehavior.this.mSwipeDirection != 1) {
                        return false;
                    }
                    if (!(bl2 ? !(f2 > 0.0f) : !(f2 < 0.0f))) return true;
                    return false;
                }
            }
            int n2 = view.getLeft();
            int n3 = this.mOriginalCapturedViewLeft;
            int n4 = Math.round((float)view.getWidth() * SwipeDismissBehavior.this.mDragDismissThreshold);
            if (Math.abs(n2 - n3) < n4) return false;
            return true;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public int clampViewPositionHorizontal(View view, int n2, int n3) {
            int n4;
            n3 = ViewCompat.getLayoutDirection(view) == 1 ? 1 : 0;
            if (SwipeDismissBehavior.this.mSwipeDirection == 0) {
                if (n3 != 0) {
                    n4 = this.mOriginalCapturedViewLeft - view.getWidth();
                    n3 = this.mOriginalCapturedViewLeft;
                    return SwipeDismissBehavior.clamp(n4, n2, n3);
                }
                n4 = this.mOriginalCapturedViewLeft;
                n3 = this.mOriginalCapturedViewLeft + view.getWidth();
                return SwipeDismissBehavior.clamp(n4, n2, n3);
            }
            if (SwipeDismissBehavior.this.mSwipeDirection != 1) {
                n4 = this.mOriginalCapturedViewLeft - view.getWidth();
                n3 = this.mOriginalCapturedViewLeft + view.getWidth();
                return SwipeDismissBehavior.clamp(n4, n2, n3);
            }
            if (n3 != 0) {
                n4 = this.mOriginalCapturedViewLeft;
                n3 = this.mOriginalCapturedViewLeft + view.getWidth();
                return SwipeDismissBehavior.clamp(n4, n2, n3);
            }
            n4 = this.mOriginalCapturedViewLeft - view.getWidth();
            n3 = this.mOriginalCapturedViewLeft;
            return SwipeDismissBehavior.clamp(n4, n2, n3);
        }

        @Override
        public int clampViewPositionVertical(View view, int n2, int n3) {
            return view.getTop();
        }

        @Override
        public int getViewHorizontalDragRange(View view) {
            return view.getWidth();
        }

        @Override
        public void onViewCaptured(View view, int n2) {
            this.mActivePointerId = n2;
            this.mOriginalCapturedViewLeft = view.getLeft();
            if ((view = view.getParent()) != null) {
                view.requestDisallowInterceptTouchEvent(true);
            }
        }

        @Override
        public void onViewDragStateChanged(int n2) {
            if (SwipeDismissBehavior.this.mListener != null) {
                SwipeDismissBehavior.this.mListener.onDragStateChanged(n2);
            }
        }

        @Override
        public void onViewPositionChanged(View view, int n2, int n3, int n4, int n5) {
            float f2 = (float)this.mOriginalCapturedViewLeft + (float)view.getWidth() * SwipeDismissBehavior.this.mAlphaStartSwipeDistance;
            float f3 = (float)this.mOriginalCapturedViewLeft + (float)view.getWidth() * SwipeDismissBehavior.this.mAlphaEndSwipeDistance;
            if ((float)n2 <= f2) {
                ViewCompat.setAlpha(view, 1.0f);
                return;
            }
            if ((float)n2 >= f3) {
                ViewCompat.setAlpha(view, 0.0f);
                return;
            }
            ViewCompat.setAlpha(view, SwipeDismissBehavior.clamp(0.0f, 1.0f - SwipeDismissBehavior.fraction(f2, f3, n2), 1.0f));
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onViewReleased(View view, float f2, float f3) {
            this.mActivePointerId = -1;
            int n2 = view.getWidth();
            boolean bl2 = false;
            if (this.shouldDismiss(view, f2)) {
                n2 = view.getLeft() < this.mOriginalCapturedViewLeft ? this.mOriginalCapturedViewLeft - n2 : this.mOriginalCapturedViewLeft + n2;
                bl2 = true;
            } else {
                n2 = this.mOriginalCapturedViewLeft;
            }
            if (SwipeDismissBehavior.this.mViewDragHelper.settleCapturedViewAt(n2, view.getTop())) {
                ViewCompat.postOnAnimation(view, new SettleRunnable(view, bl2));
                return;
            } else {
                if (!bl2 || SwipeDismissBehavior.this.mListener == null) return;
                SwipeDismissBehavior.this.mListener.onDismiss(view);
                return;
            }
        }

        @Override
        public boolean tryCaptureView(View view, int n2) {
            return this.mActivePointerId == -1 && SwipeDismissBehavior.this.canSwipeDismissView(view);
        }
    };
    private float mDragDismissThreshold = 0.5f;
    private boolean mIgnoreEvents;
    private OnDismissListener mListener;
    private float mSensitivity = 0.0f;
    private boolean mSensitivitySet;
    private int mSwipeDirection = 2;
    private ViewDragHelper mViewDragHelper;

    private static float clamp(float f2, float f3, float f4) {
        return Math.min(Math.max(f2, f3), f4);
    }

    private static int clamp(int n2, int n3, int n4) {
        return Math.min(Math.max(n2, n3), n4);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private void ensureViewDragHelper(ViewGroup object) {
        if (this.mViewDragHelper == null) {
            void var1_3;
            if (this.mSensitivitySet) {
                ViewDragHelper viewDragHelper = ViewDragHelper.create(object, this.mSensitivity, this.mDragCallback);
            } else {
                ViewDragHelper viewDragHelper = ViewDragHelper.create(object, this.mDragCallback);
            }
            this.mViewDragHelper = var1_3;
        }
    }

    static float fraction(float f2, float f3, float f4) {
        return (f4 - f2) / (f3 - f2);
    }

    public boolean canSwipeDismissView(@NonNull View view) {
        return true;
    }

    public int getDragState() {
        if (this.mViewDragHelper != null) {
            return this.mViewDragHelper.getViewDragState();
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v2, MotionEvent motionEvent) {
        block5: {
            boolean bl2;
            switch (MotionEventCompat.getActionMasked(motionEvent)) {
                default: {
                    bl2 = !coordinatorLayout.isPointInChildBounds((View)v2, (int)motionEvent.getX(), (int)motionEvent.getY());
                }
                case 1: 
                case 3: {
                    if (this.mIgnoreEvents) {
                        this.mIgnoreEvents = false;
                        return false;
                    }
                    break block5;
                }
            }
            this.mIgnoreEvents = bl2;
        }
        if (this.mIgnoreEvents) {
            return false;
        }
        this.ensureViewDragHelper(coordinatorLayout);
        return this.mViewDragHelper.shouldInterceptTouchEvent(motionEvent);
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v2, MotionEvent motionEvent) {
        if (this.mViewDragHelper != null) {
            this.mViewDragHelper.processTouchEvent(motionEvent);
            return true;
        }
        return false;
    }

    public void setDragDismissDistance(float f2) {
        this.mDragDismissThreshold = SwipeDismissBehavior.clamp(0.0f, f2, 1.0f);
    }

    public void setEndAlphaSwipeDistance(float f2) {
        this.mAlphaEndSwipeDistance = SwipeDismissBehavior.clamp(0.0f, f2, 1.0f);
    }

    public void setListener(OnDismissListener onDismissListener) {
        this.mListener = onDismissListener;
    }

    public void setSensitivity(float f2) {
        this.mSensitivity = f2;
        this.mSensitivitySet = true;
    }

    public void setStartAlphaSwipeDistance(float f2) {
        this.mAlphaStartSwipeDistance = SwipeDismissBehavior.clamp(0.0f, f2, 1.0f);
    }

    public void setSwipeDirection(int n2) {
        this.mSwipeDirection = n2;
    }

    public static interface OnDismissListener {
        public void onDismiss(View var1);

        public void onDragStateChanged(int var1);
    }

    private class SettleRunnable
    implements Runnable {
        private final boolean mDismiss;
        private final View mView;

        SettleRunnable(View view, boolean bl2) {
            this.mView = view;
            this.mDismiss = bl2;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void run() {
            if (SwipeDismissBehavior.this.mViewDragHelper != null && SwipeDismissBehavior.this.mViewDragHelper.continueSettling(true)) {
                ViewCompat.postOnAnimation(this.mView, this);
                return;
            } else {
                if (!this.mDismiss || SwipeDismissBehavior.this.mListener == null) return;
                SwipeDismissBehavior.this.mListener.onDismiss(this.mView);
                return;
            }
        }
    }
}

