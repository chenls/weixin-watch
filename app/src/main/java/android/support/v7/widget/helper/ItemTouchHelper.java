/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.GestureDetector$SimpleOnGestureListener
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.ViewConfiguration
 *  android.view.animation.Interpolator
 */
package android.support.v7.widget.helper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.animation.AnimatorCompatHelper;
import android.support.v4.animation.AnimatorListenerCompat;
import android.support.v4.animation.AnimatorUpdateListenerCompat;
import android.support.v4.animation.ValueAnimatorCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.recyclerview.R;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchUIUtil;
import android.support.v7.widget.helper.ItemTouchUIUtilImpl;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import java.util.ArrayList;
import java.util.List;

public class ItemTouchHelper
extends RecyclerView.ItemDecoration
implements RecyclerView.OnChildAttachStateChangeListener {
    private static final int ACTION_MODE_DRAG_MASK = 0xFF0000;
    private static final int ACTION_MODE_IDLE_MASK = 255;
    private static final int ACTION_MODE_SWIPE_MASK = 65280;
    public static final int ACTION_STATE_DRAG = 2;
    public static final int ACTION_STATE_IDLE = 0;
    public static final int ACTION_STATE_SWIPE = 1;
    private static final int ACTIVE_POINTER_ID_NONE = -1;
    public static final int ANIMATION_TYPE_DRAG = 8;
    public static final int ANIMATION_TYPE_SWIPE_CANCEL = 4;
    public static final int ANIMATION_TYPE_SWIPE_SUCCESS = 2;
    private static final boolean DEBUG = false;
    private static final int DIRECTION_FLAG_COUNT = 8;
    public static final int DOWN = 2;
    public static final int END = 32;
    public static final int LEFT = 4;
    private static final int PIXELS_PER_SECOND = 1000;
    public static final int RIGHT = 8;
    public static final int START = 16;
    private static final String TAG = "ItemTouchHelper";
    public static final int UP = 1;
    int mActionState = 0;
    int mActivePointerId = -1;
    Callback mCallback;
    private RecyclerView.ChildDrawingOrderCallback mChildDrawingOrderCallback = null;
    private List<Integer> mDistances;
    private long mDragScrollStartTimeInMs;
    float mDx;
    float mDy;
    private GestureDetectorCompat mGestureDetector;
    float mInitialTouchX;
    float mInitialTouchY;
    float mMaxSwipeVelocity;
    private final RecyclerView.OnItemTouchListener mOnItemTouchListener;
    private View mOverdrawChild = null;
    private int mOverdrawChildPosition = -1;
    final List<View> mPendingCleanup = new ArrayList<View>();
    List<RecoverAnimation> mRecoverAnimations;
    private RecyclerView mRecyclerView;
    private final Runnable mScrollRunnable;
    RecyclerView.ViewHolder mSelected = null;
    int mSelectedFlags;
    float mSelectedStartX;
    float mSelectedStartY;
    private int mSlop;
    private List<RecyclerView.ViewHolder> mSwapTargets;
    float mSwipeEscapeVelocity;
    private final float[] mTmpPosition = new float[2];
    private Rect mTmpRect;
    private VelocityTracker mVelocityTracker;

    public ItemTouchHelper(Callback callback) {
        this.mRecoverAnimations = new ArrayList<RecoverAnimation>();
        this.mScrollRunnable = new Runnable(){

            @Override
            public void run() {
                if (ItemTouchHelper.this.mSelected != null && ItemTouchHelper.this.scrollIfNecessary()) {
                    if (ItemTouchHelper.this.mSelected != null) {
                        ItemTouchHelper.this.moveIfNecessary(ItemTouchHelper.this.mSelected);
                    }
                    ItemTouchHelper.this.mRecyclerView.removeCallbacks(ItemTouchHelper.this.mScrollRunnable);
                    ViewCompat.postOnAnimation((View)ItemTouchHelper.this.mRecyclerView, this);
                }
            }
        };
        this.mOnItemTouchListener = new RecyclerView.OnItemTouchListener(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public boolean onInterceptTouchEvent(RecyclerView object, MotionEvent motionEvent) {
                int n2;
                ItemTouchHelper.this.mGestureDetector.onTouchEvent(motionEvent);
                int n3 = MotionEventCompat.getActionMasked(motionEvent);
                if (n3 == 0) {
                    ItemTouchHelper.this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, 0);
                    ItemTouchHelper.this.mInitialTouchX = motionEvent.getX();
                    ItemTouchHelper.this.mInitialTouchY = motionEvent.getY();
                    ItemTouchHelper.this.obtainVelocityTracker();
                    if (ItemTouchHelper.this.mSelected == null && (object = ItemTouchHelper.this.findAnimation(motionEvent)) != null) {
                        ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
                        itemTouchHelper.mInitialTouchX -= ((RecoverAnimation)object).mX;
                        itemTouchHelper = ItemTouchHelper.this;
                        itemTouchHelper.mInitialTouchY -= ((RecoverAnimation)object).mY;
                        ItemTouchHelper.this.endRecoverAnimation(((RecoverAnimation)object).mViewHolder, true);
                        if (ItemTouchHelper.this.mPendingCleanup.remove(object.mViewHolder.itemView)) {
                            ItemTouchHelper.this.mCallback.clearView(ItemTouchHelper.this.mRecyclerView, ((RecoverAnimation)object).mViewHolder);
                        }
                        ItemTouchHelper.this.select(((RecoverAnimation)object).mViewHolder, ((RecoverAnimation)object).mActionState);
                        ItemTouchHelper.this.updateDxDy(motionEvent, ItemTouchHelper.this.mSelectedFlags, 0);
                    }
                } else if (n3 == 3 || n3 == 1) {
                    ItemTouchHelper.this.mActivePointerId = -1;
                    ItemTouchHelper.this.select(null, 0);
                } else if (ItemTouchHelper.this.mActivePointerId != -1 && (n2 = MotionEventCompat.findPointerIndex(motionEvent, ItemTouchHelper.this.mActivePointerId)) >= 0) {
                    ItemTouchHelper.this.checkSelectForSwipe(n3, motionEvent, n2);
                }
                if (ItemTouchHelper.this.mVelocityTracker != null) {
                    ItemTouchHelper.this.mVelocityTracker.addMovement(motionEvent);
                }
                return ItemTouchHelper.this.mSelected != null;
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean bl2) {
                if (!bl2) {
                    return;
                }
                ItemTouchHelper.this.select(null, 0);
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void onTouchEvent(RecyclerView object, MotionEvent motionEvent) {
                int n2;
                int n3;
                block11: {
                    block10: {
                        n3 = 0;
                        ItemTouchHelper.this.mGestureDetector.onTouchEvent(motionEvent);
                        if (ItemTouchHelper.this.mVelocityTracker != null) {
                            ItemTouchHelper.this.mVelocityTracker.addMovement(motionEvent);
                        }
                        if (ItemTouchHelper.this.mActivePointerId == -1) break block10;
                        n2 = MotionEventCompat.getActionMasked(motionEvent);
                        int n4 = MotionEventCompat.findPointerIndex(motionEvent, ItemTouchHelper.this.mActivePointerId);
                        if (n4 >= 0) {
                            ItemTouchHelper.this.checkSelectForSwipe(n2, motionEvent, n4);
                        }
                        if ((object = ItemTouchHelper.this.mSelected) == null) break block10;
                        switch (n2) {
                            default: {
                                return;
                            }
                            case 2: {
                                if (n4 < 0) break;
                                ItemTouchHelper.this.updateDxDy(motionEvent, ItemTouchHelper.this.mSelectedFlags, n4);
                                ItemTouchHelper.this.moveIfNecessary((RecyclerView.ViewHolder)object);
                                ItemTouchHelper.this.mRecyclerView.removeCallbacks(ItemTouchHelper.this.mScrollRunnable);
                                ItemTouchHelper.this.mScrollRunnable.run();
                                ItemTouchHelper.this.mRecyclerView.invalidate();
                                return;
                            }
                            case 3: {
                                if (ItemTouchHelper.this.mVelocityTracker != null) {
                                    ItemTouchHelper.this.mVelocityTracker.clear();
                                }
                            }
                            case 1: {
                                ItemTouchHelper.this.select(null, 0);
                                ItemTouchHelper.this.mActivePointerId = -1;
                                return;
                            }
                            case 6: {
                                n2 = MotionEventCompat.getActionIndex(motionEvent);
                                if (MotionEventCompat.getPointerId(motionEvent, n2) == ItemTouchHelper.this.mActivePointerId) break block11;
                            }
                        }
                    }
                    return;
                }
                if (n2 == 0) {
                    n3 = 1;
                }
                ItemTouchHelper.this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, n3);
                ItemTouchHelper.this.updateDxDy(motionEvent, ItemTouchHelper.this.mSelectedFlags, n2);
            }
        };
        this.mCallback = callback;
    }

    static /* synthetic */ int access$2302(ItemTouchHelper itemTouchHelper, int n2) {
        itemTouchHelper.mOverdrawChildPosition = n2;
        return n2;
    }

    private void addChildDrawingOrderCallback() {
        if (Build.VERSION.SDK_INT >= 21) {
            return;
        }
        if (this.mChildDrawingOrderCallback == null) {
            this.mChildDrawingOrderCallback = new RecyclerView.ChildDrawingOrderCallback(){

                /*
                 * Enabled aggressive block sorting
                 */
                @Override
                public int onGetChildDrawingOrder(int n2, int n3) {
                    block6: {
                        block5: {
                            int n4;
                            if (ItemTouchHelper.this.mOverdrawChild == null) break block5;
                            int n5 = n4 = ItemTouchHelper.this.mOverdrawChildPosition;
                            if (n4 == -1) {
                                n5 = ItemTouchHelper.this.mRecyclerView.indexOfChild(ItemTouchHelper.this.mOverdrawChild);
                                ItemTouchHelper.access$2302(ItemTouchHelper.this, n5);
                            }
                            if (n3 == n2 - 1) {
                                return n5;
                            }
                            if (n3 >= n5) break block6;
                        }
                        return n3;
                    }
                    return n3 + 1;
                }
            };
        }
        this.mRecyclerView.setChildDrawingOrderCallback(this.mChildDrawingOrderCallback);
    }

    /*
     * Enabled aggressive block sorting
     */
    private int checkHorizontalSwipe(RecyclerView.ViewHolder viewHolder, int n2) {
        if ((n2 & 0xC) != 0) {
            float f2;
            float f3;
            int n3 = this.mDx > 0.0f ? 8 : 4;
            if (this.mVelocityTracker != null && this.mActivePointerId > -1) {
                this.mVelocityTracker.computeCurrentVelocity(1000, this.mCallback.getSwipeVelocityThreshold(this.mMaxSwipeVelocity));
                f3 = VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mActivePointerId);
                f2 = VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId);
                int n4 = f3 > 0.0f ? 8 : 4;
                f3 = Math.abs(f3);
                if ((n4 & n2) != 0 && n3 == n4 && f3 >= this.mCallback.getSwipeEscapeVelocity(this.mSwipeEscapeVelocity) && f3 > Math.abs(f2)) {
                    return n4;
                }
            }
            f2 = this.mRecyclerView.getWidth();
            f3 = this.mCallback.getSwipeThreshold(viewHolder);
            if ((n2 & n3) != 0 && Math.abs(this.mDx) > f2 * f3) {
                return n3;
            }
        }
        return 0;
    }

    private boolean checkSelectForSwipe(int n2, MotionEvent motionEvent, int n3) {
        if (this.mSelected != null || n2 != 2 || this.mActionState == 2 || !this.mCallback.isItemViewSwipeEnabled()) {
            return false;
        }
        if (this.mRecyclerView.getScrollState() == 1) {
            return false;
        }
        RecyclerView.ViewHolder viewHolder = this.findSwipedView(motionEvent);
        if (viewHolder == null) {
            return false;
        }
        n2 = (0xFF00 & this.mCallback.getAbsoluteMovementFlags(this.mRecyclerView, viewHolder)) >> 8;
        if (n2 == 0) {
            return false;
        }
        float f2 = MotionEventCompat.getX(motionEvent, n3);
        float f3 = MotionEventCompat.getY(motionEvent, n3);
        float f4 = Math.abs(f2 -= this.mInitialTouchX);
        float f5 = Math.abs(f3 -= this.mInitialTouchY);
        if (f4 < (float)this.mSlop && f5 < (float)this.mSlop) {
            return false;
        }
        if (f4 > f5) {
            if (f2 < 0.0f && (n2 & 4) == 0) {
                return false;
            }
            if (f2 > 0.0f && (n2 & 8) == 0) {
                return false;
            }
        } else {
            if (f3 < 0.0f && (n2 & 1) == 0) {
                return false;
            }
            if (f3 > 0.0f && (n2 & 2) == 0) {
                return false;
            }
        }
        this.mDy = 0.0f;
        this.mDx = 0.0f;
        this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, 0);
        this.select(viewHolder, 1);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int checkVerticalSwipe(RecyclerView.ViewHolder viewHolder, int n2) {
        if ((n2 & 3) != 0) {
            float f2;
            float f3;
            int n3 = this.mDy > 0.0f ? 2 : 1;
            if (this.mVelocityTracker != null && this.mActivePointerId > -1) {
                this.mVelocityTracker.computeCurrentVelocity(1000, this.mCallback.getSwipeVelocityThreshold(this.mMaxSwipeVelocity));
                f3 = VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mActivePointerId);
                f2 = VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId);
                int n4 = f2 > 0.0f ? 2 : 1;
                f2 = Math.abs(f2);
                if ((n4 & n2) != 0 && n4 == n3 && f2 >= this.mCallback.getSwipeEscapeVelocity(this.mSwipeEscapeVelocity) && f2 > Math.abs(f3)) {
                    return n4;
                }
            }
            f3 = this.mRecyclerView.getHeight();
            f2 = this.mCallback.getSwipeThreshold(viewHolder);
            if ((n2 & n3) != 0 && Math.abs(this.mDy) > f3 * f2) {
                return n3;
            }
        }
        return 0;
    }

    private void destroyCallbacks() {
        this.mRecyclerView.removeItemDecoration(this);
        this.mRecyclerView.removeOnItemTouchListener(this.mOnItemTouchListener);
        this.mRecyclerView.removeOnChildAttachStateChangeListener(this);
        for (int i2 = this.mRecoverAnimations.size() - 1; i2 >= 0; --i2) {
            RecoverAnimation recoverAnimation = this.mRecoverAnimations.get(0);
            this.mCallback.clearView(this.mRecyclerView, recoverAnimation.mViewHolder);
        }
        this.mRecoverAnimations.clear();
        this.mOverdrawChild = null;
        this.mOverdrawChildPosition = -1;
        this.releaseVelocityTracker();
    }

    private int endRecoverAnimation(RecyclerView.ViewHolder viewHolder, boolean bl2) {
        for (int i2 = this.mRecoverAnimations.size() - 1; i2 >= 0; --i2) {
            RecoverAnimation recoverAnimation = this.mRecoverAnimations.get(i2);
            if (recoverAnimation.mViewHolder != viewHolder) continue;
            recoverAnimation.mOverridden |= bl2;
            if (!recoverAnimation.mEnded) {
                recoverAnimation.cancel();
            }
            this.mRecoverAnimations.remove(i2);
            return recoverAnimation.mAnimationType;
        }
        return 0;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private RecoverAnimation findAnimation(MotionEvent object) {
        void var1_3;
        if (this.mRecoverAnimations.isEmpty()) {
            return var1_3;
        }
        View view = this.findChildView((MotionEvent)object);
        int n2 = this.mRecoverAnimations.size() - 1;
        while (n2 >= 0) {
            RecoverAnimation recoverAnimation;
            RecoverAnimation recoverAnimation2 = recoverAnimation = this.mRecoverAnimations.get(n2);
            if (recoverAnimation.mViewHolder.itemView == view) {
                return var1_3;
            }
            --n2;
        }
        return null;
    }

    private View findChildView(MotionEvent object) {
        float f2 = object.getX();
        float f3 = object.getY();
        if (this.mSelected != null && ItemTouchHelper.hitTest((View)(object = this.mSelected.itemView), f2, f3, this.mSelectedStartX + this.mDx, this.mSelectedStartY + this.mDy)) {
            return object;
        }
        for (int i2 = this.mRecoverAnimations.size() - 1; i2 >= 0; --i2) {
            object = this.mRecoverAnimations.get(i2);
            View view = object.mViewHolder.itemView;
            if (!ItemTouchHelper.hitTest(view, f2, f3, object.mX, object.mY)) continue;
            return view;
        }
        return this.mRecyclerView.findChildViewUnder(f2, f3);
    }

    /*
     * Enabled aggressive block sorting
     */
    private List<RecyclerView.ViewHolder> findSwapTargets(RecyclerView.ViewHolder viewHolder) {
        if (this.mSwapTargets == null) {
            this.mSwapTargets = new ArrayList<RecyclerView.ViewHolder>();
            this.mDistances = new ArrayList<Integer>();
        } else {
            this.mSwapTargets.clear();
            this.mDistances.clear();
        }
        int n2 = this.mCallback.getBoundingBoxMargin();
        int n3 = Math.round(this.mSelectedStartX + this.mDx) - n2;
        int n4 = Math.round(this.mSelectedStartY + this.mDy) - n2;
        int n5 = viewHolder.itemView.getWidth() + n3 + n2 * 2;
        int n6 = viewHolder.itemView.getHeight() + n4 + n2 * 2;
        int n7 = (n3 + n5) / 2;
        int n8 = (n4 + n6) / 2;
        RecyclerView.LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
        int n9 = layoutManager.getChildCount();
        n2 = 0;
        while (n2 < n9) {
            RecyclerView.ViewHolder viewHolder2;
            View view = layoutManager.getChildAt(n2);
            if (view != viewHolder.itemView && view.getBottom() >= n4 && view.getTop() <= n6 && view.getRight() >= n3 && view.getLeft() <= n5 && this.mCallback.canDropOver(this.mRecyclerView, this.mSelected, viewHolder2 = this.mRecyclerView.getChildViewHolder(view))) {
                int n10 = Math.abs(n7 - (view.getLeft() + view.getRight()) / 2);
                int n11 = Math.abs(n8 - (view.getTop() + view.getBottom()) / 2);
                int n12 = n10 * n10 + n11 * n11;
                n11 = 0;
                int n13 = this.mSwapTargets.size();
                for (n10 = 0; n10 < n13 && n12 > this.mDistances.get(n10); ++n11, ++n10) {
                }
                this.mSwapTargets.add(n11, viewHolder2);
                this.mDistances.add(n11, n12);
            }
            ++n2;
        }
        return this.mSwapTargets;
    }

    /*
     * Enabled aggressive block sorting
     */
    private RecyclerView.ViewHolder findSwipedView(MotionEvent motionEvent) {
        block3: {
            block2: {
                RecyclerView.LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
                if (this.mActivePointerId == -1) break block2;
                int n2 = MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId);
                float f2 = MotionEventCompat.getX(motionEvent, n2);
                float f3 = this.mInitialTouchX;
                float f4 = MotionEventCompat.getY(motionEvent, n2);
                float f5 = this.mInitialTouchY;
                f2 = Math.abs(f2 - f3);
                f4 = Math.abs(f4 - f5);
                if (!(f2 < (float)this.mSlop && f4 < (float)this.mSlop || f2 > f4 && layoutManager.canScrollHorizontally() || f4 > f2 && layoutManager.canScrollVertically()) && (motionEvent = this.findChildView(motionEvent)) != null) break block3;
            }
            return null;
        }
        return this.mRecyclerView.getChildViewHolder((View)motionEvent);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void getSelectedDxDy(float[] fArray) {
        fArray[0] = (this.mSelectedFlags & 0xC) != 0 ? this.mSelectedStartX + this.mDx - (float)this.mSelected.itemView.getLeft() : ViewCompat.getTranslationX(this.mSelected.itemView);
        if ((this.mSelectedFlags & 3) != 0) {
            fArray[1] = this.mSelectedStartY + this.mDy - (float)this.mSelected.itemView.getTop();
            return;
        }
        fArray[1] = ViewCompat.getTranslationY(this.mSelected.itemView);
    }

    private boolean hasRunningRecoverAnim() {
        int n2 = this.mRecoverAnimations.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            if (this.mRecoverAnimations.get(i2).mEnded) continue;
            return true;
        }
        return false;
    }

    private static boolean hitTest(View view, float f2, float f3, float f4, float f5) {
        return f2 >= f4 && f2 <= (float)view.getWidth() + f4 && f3 >= f5 && f3 <= (float)view.getHeight() + f5;
    }

    private void initGestureDetector() {
        if (this.mGestureDetector != null) {
            return;
        }
        this.mGestureDetector = new GestureDetectorCompat(this.mRecyclerView.getContext(), (GestureDetector.OnGestureListener)new ItemTouchHelperGestureListener());
    }

    /*
     * Enabled aggressive block sorting
     */
    private void moveIfNecessary(RecyclerView.ViewHolder viewHolder) {
        int n2;
        int n3;
        Object object;
        int n4;
        int n5;
        block5: {
            block4: {
                if (this.mRecyclerView.isLayoutRequested() || this.mActionState != 2) break block4;
                float f2 = this.mCallback.getMoveThreshold(viewHolder);
                n5 = (int)(this.mSelectedStartX + this.mDx);
                n4 = (int)(this.mSelectedStartY + this.mDy);
                if ((float)Math.abs(n4 - viewHolder.itemView.getTop()) < (float)viewHolder.itemView.getHeight() * f2 && (float)Math.abs(n5 - viewHolder.itemView.getLeft()) < (float)viewHolder.itemView.getWidth() * f2 || (object = this.findSwapTargets(viewHolder)).size() == 0) break block4;
                if ((object = this.mCallback.chooseDropTarget(viewHolder, (List<RecyclerView.ViewHolder>)object, n5, n4)) == null) {
                    this.mSwapTargets.clear();
                    this.mDistances.clear();
                    return;
                }
                n3 = ((RecyclerView.ViewHolder)object).getAdapterPosition();
                n2 = viewHolder.getAdapterPosition();
                if (this.mCallback.onMove(this.mRecyclerView, viewHolder, (RecyclerView.ViewHolder)object)) break block5;
            }
            return;
        }
        this.mCallback.onMoved(this.mRecyclerView, viewHolder, n2, (RecyclerView.ViewHolder)object, n3, n5, n4);
    }

    private void obtainVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
        }
        this.mVelocityTracker = VelocityTracker.obtain();
    }

    private void postDispatchSwipe(final RecoverAnimation recoverAnimation, final int n2) {
        this.mRecyclerView.post(new Runnable(){

            @Override
            public void run() {
                block3: {
                    block2: {
                        if (ItemTouchHelper.this.mRecyclerView == null || !ItemTouchHelper.this.mRecyclerView.isAttachedToWindow() || recoverAnimation.mOverridden || recoverAnimation.mViewHolder.getAdapterPosition() == -1) break block2;
                        RecyclerView.ItemAnimator itemAnimator = ItemTouchHelper.this.mRecyclerView.getItemAnimator();
                        if (itemAnimator != null && itemAnimator.isRunning(null) || ItemTouchHelper.this.hasRunningRecoverAnim()) break block3;
                        ItemTouchHelper.this.mCallback.onSwiped(recoverAnimation.mViewHolder, n2);
                    }
                    return;
                }
                ItemTouchHelper.this.mRecyclerView.post(this);
            }
        });
    }

    private void releaseVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private void removeChildDrawingOrderCallbackIfNecessary(View view) {
        if (view == this.mOverdrawChild) {
            this.mOverdrawChild = null;
            if (this.mChildDrawingOrderCallback != null) {
                this.mRecyclerView.setChildDrawingOrderCallback(null);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean scrollIfNecessary() {
        int n2;
        if (this.mSelected == null) {
            this.mDragScrollStartTimeInMs = Long.MIN_VALUE;
            return false;
        }
        long l2 = System.currentTimeMillis();
        long l3 = this.mDragScrollStartTimeInMs == Long.MIN_VALUE ? 0L : l2 - this.mDragScrollStartTimeInMs;
        RecyclerView.LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
        if (this.mTmpRect == null) {
            this.mTmpRect = new Rect();
        }
        int n3 = 0;
        int n4 = 0;
        layoutManager.calculateItemDecorationsForChild(this.mSelected.itemView, this.mTmpRect);
        int n5 = n3;
        if (layoutManager.canScrollHorizontally()) {
            n2 = (int)(this.mSelectedStartX + this.mDx);
            n5 = n2 - this.mTmpRect.left - this.mRecyclerView.getPaddingLeft();
            if (!(this.mDx < 0.0f) || n5 >= 0) {
                n5 = n3;
                if (this.mDx > 0.0f) {
                    n2 = this.mSelected.itemView.getWidth() + n2 + this.mTmpRect.right - (this.mRecyclerView.getWidth() - this.mRecyclerView.getPaddingRight());
                    n5 = n3;
                    if (n2 > 0) {
                        n5 = n2;
                    }
                }
            }
        }
        n3 = n4;
        if (layoutManager.canScrollVertically()) {
            n2 = (int)(this.mSelectedStartY + this.mDy);
            n3 = n2 - this.mTmpRect.top - this.mRecyclerView.getPaddingTop();
            if (!(this.mDy < 0.0f) || n3 >= 0) {
                n3 = n4;
                if (this.mDy > 0.0f) {
                    n2 = this.mSelected.itemView.getHeight() + n2 + this.mTmpRect.bottom - (this.mRecyclerView.getHeight() - this.mRecyclerView.getPaddingBottom());
                    n3 = n4;
                    if (n2 > 0) {
                        n3 = n2;
                    }
                }
            }
        }
        n4 = n5;
        if (n5 != 0) {
            n4 = this.mCallback.interpolateOutOfBoundsScroll(this.mRecyclerView, this.mSelected.itemView.getWidth(), n5, this.mRecyclerView.getWidth(), l3);
        }
        n5 = n3;
        if (n3 != 0) {
            n5 = this.mCallback.interpolateOutOfBoundsScroll(this.mRecyclerView, this.mSelected.itemView.getHeight(), n3, this.mRecyclerView.getHeight(), l3);
        }
        if (n4 == 0 && n5 == 0) {
            this.mDragScrollStartTimeInMs = Long.MIN_VALUE;
            return false;
        }
        if (this.mDragScrollStartTimeInMs == Long.MIN_VALUE) {
            this.mDragScrollStartTimeInMs = l2;
        }
        this.mRecyclerView.scrollBy(n4, n5);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void select(RecyclerView.ViewHolder viewHolder, int n2) {
        if (viewHolder == this.mSelected && n2 == this.mActionState) {
            return;
        }
        this.mDragScrollStartTimeInMs = Long.MIN_VALUE;
        int n3 = this.mActionState;
        this.endRecoverAnimation(viewHolder, true);
        this.mActionState = n2;
        if (n2 == 2) {
            this.mOverdrawChild = viewHolder.itemView;
            this.addChildDrawingOrderCallback();
        }
        int n4 = 0;
        final int n5 = 0;
        if (this.mSelected != null) {
            Object object = this.mSelected;
            if (((RecyclerView.ViewHolder)object).itemView.getParent() != null) {
                float f2;
                float f3;
                n5 = n3 == 2 ? 0 : this.swipeIfNecessary((RecyclerView.ViewHolder)object);
                this.releaseVelocityTracker();
                switch (n5) {
                    default: {
                        f3 = 0.0f;
                        f2 = 0.0f;
                        break;
                    }
                    case 4: 
                    case 8: 
                    case 16: 
                    case 32: {
                        f2 = 0.0f;
                        f3 = Math.signum(this.mDx) * (float)this.mRecyclerView.getWidth();
                        break;
                    }
                    case 1: 
                    case 2: {
                        f3 = 0.0f;
                        f2 = Math.signum(this.mDy) * (float)this.mRecyclerView.getHeight();
                    }
                }
                n4 = n3 == 2 ? 8 : (n5 > 0 ? 2 : 4);
                this.getSelectedDxDy(this.mTmpPosition);
                float f4 = this.mTmpPosition[0];
                float f5 = this.mTmpPosition[1];
                object = new RecoverAnimation((RecyclerView.ViewHolder)object, n4, n3, f4, f5, f3, f2, (RecyclerView.ViewHolder)object){
                    final /* synthetic */ RecyclerView.ViewHolder val$prevSelected;
                    {
                        this.val$prevSelected = viewHolder2;
                        super(viewHolder, n2, n3, f2, f3, f4, f5);
                    }

                    /*
                     * Enabled aggressive block sorting
                     */
                    @Override
                    public void onAnimationEnd(ValueAnimatorCompat valueAnimatorCompat) {
                        block7: {
                            block6: {
                                super.onAnimationEnd(valueAnimatorCompat);
                                if (this.mOverridden) break block6;
                                if (n5 <= 0) {
                                    ItemTouchHelper.this.mCallback.clearView(ItemTouchHelper.this.mRecyclerView, this.val$prevSelected);
                                } else {
                                    ItemTouchHelper.this.mPendingCleanup.add(this.val$prevSelected.itemView);
                                    this.mIsPendingCleanup = true;
                                    if (n5 > 0) {
                                        ItemTouchHelper.this.postDispatchSwipe(this, n5);
                                    }
                                }
                                if (ItemTouchHelper.this.mOverdrawChild == this.val$prevSelected.itemView) break block7;
                            }
                            return;
                        }
                        ItemTouchHelper.this.removeChildDrawingOrderCallbackIfNecessary(this.val$prevSelected.itemView);
                    }
                };
                ((RecoverAnimation)object).setDuration(this.mCallback.getAnimationDuration(this.mRecyclerView, n4, f3 - f4, f2 - f5));
                this.mRecoverAnimations.add((RecoverAnimation)object);
                ((RecoverAnimation)object).start();
                n4 = 1;
            } else {
                this.removeChildDrawingOrderCallbackIfNecessary(((RecyclerView.ViewHolder)object).itemView);
                this.mCallback.clearView(this.mRecyclerView, (RecyclerView.ViewHolder)object);
                n4 = n5;
            }
            this.mSelected = null;
        }
        if (viewHolder != null) {
            this.mSelectedFlags = (this.mCallback.getAbsoluteMovementFlags(this.mRecyclerView, viewHolder) & (1 << n2 * 8 + 8) - 1) >> this.mActionState * 8;
            this.mSelectedStartX = viewHolder.itemView.getLeft();
            this.mSelectedStartY = viewHolder.itemView.getTop();
            this.mSelected = viewHolder;
            if (n2 == 2) {
                this.mSelected.itemView.performHapticFeedback(0);
            }
        }
        if ((viewHolder = this.mRecyclerView.getParent()) != null) {
            boolean bl2 = this.mSelected != null;
            viewHolder.requestDisallowInterceptTouchEvent(bl2);
        }
        if (n4 == 0) {
            this.mRecyclerView.getLayoutManager().requestSimpleAnimationsInNextLayout();
        }
        this.mCallback.onSelectedChanged(this.mSelected, this.mActionState);
        this.mRecyclerView.invalidate();
    }

    private void setupCallbacks() {
        this.mSlop = ViewConfiguration.get((Context)this.mRecyclerView.getContext()).getScaledTouchSlop();
        this.mRecyclerView.addItemDecoration(this);
        this.mRecyclerView.addOnItemTouchListener(this.mOnItemTouchListener);
        this.mRecyclerView.addOnChildAttachStateChangeListener(this);
        this.initGestureDetector();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int swipeIfNecessary(RecyclerView.ViewHolder viewHolder) {
        int n2;
        if (this.mActionState == 2) {
            return 0;
        }
        int n3 = this.mCallback.getMovementFlags(this.mRecyclerView, viewHolder);
        int n4 = (this.mCallback.convertToAbsoluteDirection(n3, ViewCompat.getLayoutDirection((View)this.mRecyclerView)) & 0xFF00) >> 8;
        if (n4 == 0) {
            return 0;
        }
        int n5 = (n3 & 0xFF00) >> 8;
        if (Math.abs(this.mDx) > Math.abs(this.mDy)) {
            int n6 = this.checkHorizontalSwipe(viewHolder, n4);
            if (n6 > 0) {
                n3 = n6;
                if ((n5 & n6) != 0) return n3;
                return Callback.convertToRelativeDirection(n6, ViewCompat.getLayoutDirection((View)this.mRecyclerView));
            }
            n3 = n6 = this.checkVerticalSwipe(viewHolder, n4);
            if (n6 > 0) return n3;
            return 0;
        }
        n3 = n2 = this.checkVerticalSwipe(viewHolder, n4);
        if (n2 > 0) return n3;
        n2 = this.checkHorizontalSwipe(viewHolder, n4);
        if (n2 <= 0) return 0;
        n3 = n2;
        if ((n5 & n2) != 0) return n3;
        return Callback.convertToRelativeDirection(n2, ViewCompat.getLayoutDirection((View)this.mRecyclerView));
    }

    private void updateDxDy(MotionEvent motionEvent, int n2, int n3) {
        float f2 = MotionEventCompat.getX(motionEvent, n3);
        float f3 = MotionEventCompat.getY(motionEvent, n3);
        this.mDx = f2 - this.mInitialTouchX;
        this.mDy = f3 - this.mInitialTouchY;
        if ((n2 & 4) == 0) {
            this.mDx = Math.max(0.0f, this.mDx);
        }
        if ((n2 & 8) == 0) {
            this.mDx = Math.min(0.0f, this.mDx);
        }
        if ((n2 & 1) == 0) {
            this.mDy = Math.max(0.0f, this.mDy);
        }
        if ((n2 & 2) == 0) {
            this.mDy = Math.min(0.0f, this.mDy);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) {
        block5: {
            block4: {
                if (this.mRecyclerView == recyclerView) break block4;
                if (this.mRecyclerView != null) {
                    this.destroyCallbacks();
                }
                this.mRecyclerView = recyclerView;
                if (this.mRecyclerView != null) break block5;
            }
            return;
        }
        recyclerView = recyclerView.getResources();
        this.mSwipeEscapeVelocity = recyclerView.getDimension(R.dimen.item_touch_helper_swipe_escape_velocity);
        this.mMaxSwipeVelocity = recyclerView.getDimension(R.dimen.item_touch_helper_swipe_escape_max_velocity);
        this.setupCallbacks();
    }

    @Override
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        rect.setEmpty();
    }

    @Override
    public void onChildViewAttachedToWindow(View view) {
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onChildViewDetachedFromWindow(View object) {
        RecyclerView.ViewHolder viewHolder;
        block5: {
            block4: {
                this.removeChildDrawingOrderCallbackIfNecessary((View)object);
                viewHolder = this.mRecyclerView.getChildViewHolder((View)object);
                if (viewHolder == null) break block4;
                if (this.mSelected != null && viewHolder == this.mSelected) {
                    this.select(null, 0);
                    return;
                }
                this.endRecoverAnimation(viewHolder, false);
                if (this.mPendingCleanup.remove(viewHolder.itemView)) break block5;
            }
            return;
        }
        this.mCallback.clearView(this.mRecyclerView, viewHolder);
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        this.mOverdrawChildPosition = -1;
        float f2 = 0.0f;
        float f3 = 0.0f;
        if (this.mSelected != null) {
            this.getSelectedDxDy(this.mTmpPosition);
            f2 = this.mTmpPosition[0];
            f3 = this.mTmpPosition[1];
        }
        this.mCallback.onDraw(canvas, recyclerView, this.mSelected, this.mRecoverAnimations, this.mActionState, f2, f3);
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        float f2 = 0.0f;
        float f3 = 0.0f;
        if (this.mSelected != null) {
            this.getSelectedDxDy(this.mTmpPosition);
            f2 = this.mTmpPosition[0];
            f3 = this.mTmpPosition[1];
        }
        this.mCallback.onDrawOver(canvas, recyclerView, this.mSelected, this.mRecoverAnimations, this.mActionState, f2, f3);
    }

    public void startDrag(RecyclerView.ViewHolder viewHolder) {
        if (!this.mCallback.hasDragFlag(this.mRecyclerView, viewHolder)) {
            Log.e((String)TAG, (String)"Start drag has been called but swiping is not enabled");
            return;
        }
        if (viewHolder.itemView.getParent() != this.mRecyclerView) {
            Log.e((String)TAG, (String)"Start drag has been called with a view holder which is not a child of the RecyclerView which is controlled by this ItemTouchHelper.");
            return;
        }
        this.obtainVelocityTracker();
        this.mDy = 0.0f;
        this.mDx = 0.0f;
        this.select(viewHolder, 2);
    }

    public void startSwipe(RecyclerView.ViewHolder viewHolder) {
        if (!this.mCallback.hasSwipeFlag(this.mRecyclerView, viewHolder)) {
            Log.e((String)TAG, (String)"Start swipe has been called but dragging is not enabled");
            return;
        }
        if (viewHolder.itemView.getParent() != this.mRecyclerView) {
            Log.e((String)TAG, (String)"Start swipe has been called with a view holder which is not a child of the RecyclerView controlled by this ItemTouchHelper.");
            return;
        }
        this.obtainVelocityTracker();
        this.mDy = 0.0f;
        this.mDx = 0.0f;
        this.select(viewHolder, 1);
    }

    public static abstract class Callback {
        private static final int ABS_HORIZONTAL_DIR_FLAGS = 789516;
        public static final int DEFAULT_DRAG_ANIMATION_DURATION = 200;
        public static final int DEFAULT_SWIPE_ANIMATION_DURATION = 250;
        private static final long DRAG_SCROLL_ACCELERATION_LIMIT_TIME_MS = 2000L;
        static final int RELATIVE_DIR_FLAGS = 0x303030;
        private static final Interpolator sDragScrollInterpolator = new Interpolator(){

            public float getInterpolation(float f2) {
                return f2 * f2 * f2 * f2 * f2;
            }
        };
        private static final Interpolator sDragViewScrollCapInterpolator = new Interpolator(){

            public float getInterpolation(float f2) {
                return (f2 -= 1.0f) * f2 * f2 * f2 * f2 + 1.0f;
            }
        };
        private static final ItemTouchUIUtil sUICallback = Build.VERSION.SDK_INT >= 21 ? new ItemTouchUIUtilImpl.Lollipop() : (Build.VERSION.SDK_INT >= 11 ? new ItemTouchUIUtilImpl.Honeycomb() : new ItemTouchUIUtilImpl.Gingerbread());
        private int mCachedMaxScrollSpeed = -1;

        public static int convertToRelativeDirection(int n2, int n3) {
            int n4 = n2 & 0xC0C0C;
            if (n4 == 0) {
                return n2;
            }
            n2 &= ~n4;
            if (n3 == 0) {
                return n2 | n4 << 2;
            }
            return n2 | n4 << 1 & 0xFFF3F3F3 | (n4 << 1 & 0xC0C0C) << 2;
        }

        public static ItemTouchUIUtil getDefaultUIUtil() {
            return sUICallback;
        }

        private int getMaxDragScroll(RecyclerView recyclerView) {
            if (this.mCachedMaxScrollSpeed == -1) {
                this.mCachedMaxScrollSpeed = recyclerView.getResources().getDimensionPixelSize(R.dimen.item_touch_helper_max_drag_scroll_per_frame);
            }
            return this.mCachedMaxScrollSpeed;
        }

        private boolean hasDragFlag(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return (0xFF0000 & this.getAbsoluteMovementFlags(recyclerView, viewHolder)) != 0;
        }

        private boolean hasSwipeFlag(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return (0xFF00 & this.getAbsoluteMovementFlags(recyclerView, viewHolder)) != 0;
        }

        public static int makeFlag(int n2, int n3) {
            return n3 << n2 * 8;
        }

        public static int makeMovementFlags(int n2, int n3) {
            return Callback.makeFlag(0, n3 | n2) | Callback.makeFlag(1, n3) | Callback.makeFlag(2, n2);
        }

        private void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, List<RecoverAnimation> list, int n2, float f2, float f3) {
            int n3;
            int n4 = list.size();
            for (n3 = 0; n3 < n4; ++n3) {
                RecoverAnimation recoverAnimation = list.get(n3);
                recoverAnimation.update();
                int n5 = canvas.save();
                this.onChildDraw(canvas, recyclerView, recoverAnimation.mViewHolder, recoverAnimation.mX, recoverAnimation.mY, recoverAnimation.mActionState, false);
                canvas.restoreToCount(n5);
            }
            if (viewHolder != null) {
                n3 = canvas.save();
                this.onChildDraw(canvas, recyclerView, viewHolder, f2, f3, n2, true);
                canvas.restoreToCount(n3);
            }
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        private void onDrawOver(Canvas object, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, List<RecoverAnimation> list, int n2, float f2, float f3) {
            int n3;
            void var3_5;
            void var2_4;
            int n4;
            void var4_6;
            int n5 = var4_6.size();
            for (n4 = 0; n4 < n5; ++n4) {
                RecoverAnimation recoverAnimation = (RecoverAnimation)var4_6.get(n4);
                int n6 = object.save();
                this.onChildDrawOver((Canvas)object, (RecyclerView)var2_4, recoverAnimation.mViewHolder, recoverAnimation.mX, recoverAnimation.mY, recoverAnimation.mActionState, false);
                object.restoreToCount(n6);
            }
            if (var3_5 != null) {
                void var7_9;
                void var6_8;
                n4 = object.save();
                this.onChildDrawOver((Canvas)object, (RecyclerView)var2_4, (RecyclerView.ViewHolder)var3_5, (float)var6_8, (float)var7_9, n3, true);
                object.restoreToCount(n4);
            }
            n4 = 0;
            for (n3 = n5 - 1; n3 >= 0; --n3) {
                RecoverAnimation recoverAnimation = (RecoverAnimation)var4_6.get(n3);
                if (recoverAnimation.mEnded && !recoverAnimation.mIsPendingCleanup) {
                    var4_6.remove(n3);
                    continue;
                }
                if (recoverAnimation.mEnded) continue;
                n4 = 1;
            }
            if (n4 != 0) {
                var2_4.invalidate();
            }
        }

        public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
            return true;
        }

        public RecyclerView.ViewHolder chooseDropTarget(RecyclerView.ViewHolder viewHolder, List<RecyclerView.ViewHolder> list, int n2, int n3) {
            int n4 = viewHolder.itemView.getWidth();
            int n5 = viewHolder.itemView.getHeight();
            RecyclerView.ViewHolder viewHolder2 = null;
            int n6 = -1;
            int n7 = n2 - viewHolder.itemView.getLeft();
            int n8 = n3 - viewHolder.itemView.getTop();
            int n9 = list.size();
            for (int i2 = 0; i2 < n9; ++i2) {
                int n10;
                RecyclerView.ViewHolder viewHolder3 = list.get(i2);
                RecyclerView.ViewHolder viewHolder4 = viewHolder2;
                int n11 = n6;
                if (n7 > 0) {
                    n10 = viewHolder3.itemView.getRight() - (n2 + n4);
                    viewHolder4 = viewHolder2;
                    n11 = n6;
                    if (n10 < 0) {
                        viewHolder4 = viewHolder2;
                        n11 = n6;
                        if (viewHolder3.itemView.getRight() > viewHolder.itemView.getRight()) {
                            n10 = Math.abs(n10);
                            viewHolder4 = viewHolder2;
                            n11 = n6;
                            if (n10 > n6) {
                                n11 = n10;
                                viewHolder4 = viewHolder3;
                            }
                        }
                    }
                }
                viewHolder2 = viewHolder4;
                n6 = n11;
                if (n7 < 0) {
                    n10 = viewHolder3.itemView.getLeft() - n2;
                    viewHolder2 = viewHolder4;
                    n6 = n11;
                    if (n10 > 0) {
                        viewHolder2 = viewHolder4;
                        n6 = n11;
                        if (viewHolder3.itemView.getLeft() < viewHolder.itemView.getLeft()) {
                            n10 = Math.abs(n10);
                            viewHolder2 = viewHolder4;
                            n6 = n11;
                            if (n10 > n11) {
                                n6 = n10;
                                viewHolder2 = viewHolder3;
                            }
                        }
                    }
                }
                viewHolder4 = viewHolder2;
                n11 = n6;
                if (n8 < 0) {
                    n10 = viewHolder3.itemView.getTop() - n3;
                    viewHolder4 = viewHolder2;
                    n11 = n6;
                    if (n10 > 0) {
                        viewHolder4 = viewHolder2;
                        n11 = n6;
                        if (viewHolder3.itemView.getTop() < viewHolder.itemView.getTop()) {
                            n10 = Math.abs(n10);
                            viewHolder4 = viewHolder2;
                            n11 = n6;
                            if (n10 > n6) {
                                n11 = n10;
                                viewHolder4 = viewHolder3;
                            }
                        }
                    }
                }
                viewHolder2 = viewHolder4;
                n6 = n11;
                if (n8 <= 0) continue;
                n10 = viewHolder3.itemView.getBottom() - (n3 + n5);
                viewHolder2 = viewHolder4;
                n6 = n11;
                if (n10 >= 0) continue;
                viewHolder2 = viewHolder4;
                n6 = n11;
                if (viewHolder3.itemView.getBottom() <= viewHolder.itemView.getBottom()) continue;
                n10 = Math.abs(n10);
                viewHolder2 = viewHolder4;
                n6 = n11;
                if (n10 <= n11) continue;
                n6 = n10;
                viewHolder2 = viewHolder3;
            }
            return viewHolder2;
        }

        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            sUICallback.clearView(viewHolder.itemView);
        }

        public int convertToAbsoluteDirection(int n2, int n3) {
            int n4 = n2 & 0x303030;
            if (n4 == 0) {
                return n2;
            }
            n2 &= ~n4;
            if (n3 == 0) {
                return n2 | n4 >> 2;
            }
            return n2 | n4 >> 1 & 0xFFCFCFCF | (n4 >> 1 & 0x303030) >> 2;
        }

        final int getAbsoluteMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return this.convertToAbsoluteDirection(this.getMovementFlags(recyclerView, viewHolder), ViewCompat.getLayoutDirection((View)recyclerView));
        }

        public long getAnimationDuration(RecyclerView object, int n2, float f2, float f3) {
            if ((object = ((RecyclerView)object).getItemAnimator()) == null) {
                if (n2 == 8) {
                    return 200L;
                }
                return 250L;
            }
            if (n2 == 8) {
                return ((RecyclerView.ItemAnimator)object).getMoveDuration();
            }
            return ((RecyclerView.ItemAnimator)object).getRemoveDuration();
        }

        public int getBoundingBoxMargin() {
            return 0;
        }

        public float getMoveThreshold(RecyclerView.ViewHolder viewHolder) {
            return 0.5f;
        }

        public abstract int getMovementFlags(RecyclerView var1, RecyclerView.ViewHolder var2);

        public float getSwipeEscapeVelocity(float f2) {
            return f2;
        }

        public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
            return 0.5f;
        }

        public float getSwipeVelocityThreshold(float f2) {
            return f2;
        }

        /*
         * Enabled aggressive block sorting
         */
        public int interpolateOutOfBoundsScroll(RecyclerView recyclerView, int n2, int n3, int n4, long l2) {
            n4 = this.getMaxDragScroll(recyclerView);
            int n5 = Math.abs(n3);
            int n6 = (int)Math.signum(n3);
            float f2 = Math.min(1.0f, 1.0f * (float)n5 / (float)n2);
            n2 = (int)((float)(n6 * n4) * sDragViewScrollCapInterpolator.getInterpolation(f2));
            f2 = l2 > 2000L ? 1.0f : (float)l2 / 2000.0f;
            if ((n2 = (int)((float)n2 * sDragScrollInterpolator.getInterpolation(f2))) != 0) {
                return n2;
            }
            if (n3 > 0) {
                return 1;
            }
            return -1;
        }

        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        public boolean isLongPressDragEnabled() {
            return true;
        }

        public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f2, float f3, int n2, boolean bl2) {
            sUICallback.onDraw(canvas, recyclerView, viewHolder.itemView, f2, f3, n2, bl2);
        }

        public void onChildDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f2, float f3, int n2, boolean bl2) {
            sUICallback.onDrawOver(canvas, recyclerView, viewHolder.itemView, f2, f3, n2, bl2);
        }

        public abstract boolean onMove(RecyclerView var1, RecyclerView.ViewHolder var2, RecyclerView.ViewHolder var3);

        /*
         * Enabled aggressive block sorting
         */
        public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int n2, RecyclerView.ViewHolder viewHolder2, int n3, int n4, int n5) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof ViewDropHandler) {
                ((ViewDropHandler)((Object)layoutManager)).prepareForDrop(viewHolder.itemView, viewHolder2.itemView, n4, n5);
                return;
            } else {
                if (layoutManager.canScrollHorizontally()) {
                    if (layoutManager.getDecoratedLeft(viewHolder2.itemView) <= recyclerView.getPaddingLeft()) {
                        recyclerView.scrollToPosition(n3);
                    }
                    if (layoutManager.getDecoratedRight(viewHolder2.itemView) >= recyclerView.getWidth() - recyclerView.getPaddingRight()) {
                        recyclerView.scrollToPosition(n3);
                    }
                }
                if (!layoutManager.canScrollVertically()) return;
                if (layoutManager.getDecoratedTop(viewHolder2.itemView) <= recyclerView.getPaddingTop()) {
                    recyclerView.scrollToPosition(n3);
                }
                if (layoutManager.getDecoratedBottom(viewHolder2.itemView) < recyclerView.getHeight() - recyclerView.getPaddingBottom()) return;
                recyclerView.scrollToPosition(n3);
                return;
            }
        }

        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int n2) {
            if (viewHolder != null) {
                sUICallback.onSelected(viewHolder.itemView);
            }
        }

        public abstract void onSwiped(RecyclerView.ViewHolder var1, int var2);
    }

    private class ItemTouchHelperGestureListener
    extends GestureDetector.SimpleOnGestureListener {
        private ItemTouchHelperGestureListener() {
        }

        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onLongPress(MotionEvent object) {
            RecyclerView.ViewHolder viewHolder;
            block3: {
                block2: {
                    View view = ItemTouchHelper.this.findChildView(object);
                    if (view == null || (viewHolder = ItemTouchHelper.this.mRecyclerView.getChildViewHolder(view)) == null || !ItemTouchHelper.this.mCallback.hasDragFlag(ItemTouchHelper.this.mRecyclerView, viewHolder) || MotionEventCompat.getPointerId(object, 0) != ItemTouchHelper.this.mActivePointerId) break block2;
                    int n2 = MotionEventCompat.findPointerIndex(object, ItemTouchHelper.this.mActivePointerId);
                    float f2 = MotionEventCompat.getX(object, n2);
                    float f3 = MotionEventCompat.getY(object, n2);
                    ItemTouchHelper.this.mInitialTouchX = f2;
                    ItemTouchHelper.this.mInitialTouchY = f3;
                    ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
                    ItemTouchHelper.this.mDy = 0.0f;
                    itemTouchHelper.mDx = 0.0f;
                    if (ItemTouchHelper.this.mCallback.isLongPressDragEnabled()) break block3;
                }
                return;
            }
            ItemTouchHelper.this.select(viewHolder, 2);
        }
    }

    private class RecoverAnimation
    implements AnimatorListenerCompat {
        final int mActionState;
        private final int mAnimationType;
        private boolean mEnded = false;
        private float mFraction;
        public boolean mIsPendingCleanup;
        boolean mOverridden = false;
        final float mStartDx;
        final float mStartDy;
        final float mTargetX;
        final float mTargetY;
        private final ValueAnimatorCompat mValueAnimator;
        final RecyclerView.ViewHolder mViewHolder;
        float mX;
        float mY;

        public RecoverAnimation(RecyclerView.ViewHolder viewHolder, int n2, int n3, float f2, float f3, float f4, float f5) {
            this.mActionState = n3;
            this.mAnimationType = n2;
            this.mViewHolder = viewHolder;
            this.mStartDx = f2;
            this.mStartDy = f3;
            this.mTargetX = f4;
            this.mTargetY = f5;
            this.mValueAnimator = AnimatorCompatHelper.emptyValueAnimator();
            this.mValueAnimator.addUpdateListener(new AnimatorUpdateListenerCompat(){

                @Override
                public void onAnimationUpdate(ValueAnimatorCompat valueAnimatorCompat) {
                    RecoverAnimation.this.setFraction(valueAnimatorCompat.getAnimatedFraction());
                }
            });
            this.mValueAnimator.setTarget(viewHolder.itemView);
            this.mValueAnimator.addListener(this);
            this.setFraction(0.0f);
        }

        public void cancel() {
            this.mValueAnimator.cancel();
        }

        @Override
        public void onAnimationCancel(ValueAnimatorCompat valueAnimatorCompat) {
            this.setFraction(1.0f);
        }

        @Override
        public void onAnimationEnd(ValueAnimatorCompat valueAnimatorCompat) {
            if (!this.mEnded) {
                this.mViewHolder.setIsRecyclable(true);
            }
            this.mEnded = true;
        }

        @Override
        public void onAnimationRepeat(ValueAnimatorCompat valueAnimatorCompat) {
        }

        @Override
        public void onAnimationStart(ValueAnimatorCompat valueAnimatorCompat) {
        }

        public void setDuration(long l2) {
            this.mValueAnimator.setDuration(l2);
        }

        public void setFraction(float f2) {
            this.mFraction = f2;
        }

        public void start() {
            this.mViewHolder.setIsRecyclable(false);
            this.mValueAnimator.start();
        }

        /*
         * Enabled aggressive block sorting
         */
        public void update() {
            this.mX = this.mStartDx == this.mTargetX ? ViewCompat.getTranslationX(this.mViewHolder.itemView) : this.mStartDx + this.mFraction * (this.mTargetX - this.mStartDx);
            if (this.mStartDy == this.mTargetY) {
                this.mY = ViewCompat.getTranslationY(this.mViewHolder.itemView);
                return;
            }
            this.mY = this.mStartDy + this.mFraction * (this.mTargetY - this.mStartDy);
        }
    }

    public static abstract class SimpleCallback
    extends Callback {
        private int mDefaultDragDirs;
        private int mDefaultSwipeDirs;

        public SimpleCallback(int n2, int n3) {
            this.mDefaultSwipeDirs = n3;
            this.mDefaultDragDirs = n2;
        }

        public int getDragDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return this.mDefaultDragDirs;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return SimpleCallback.makeMovementFlags(this.getDragDirs(recyclerView, viewHolder), this.getSwipeDirs(recyclerView, viewHolder));
        }

        public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return this.mDefaultSwipeDirs;
        }

        public void setDefaultDragDirs(int n2) {
            this.mDefaultDragDirs = n2;
        }

        public void setDefaultSwipeDirs(int n2) {
            this.mDefaultSwipeDirs = n2;
        }
    }

    public static interface ViewDropHandler {
        public void prepareForDrop(View var1, View var2, int var3, int var4);
    }
}

