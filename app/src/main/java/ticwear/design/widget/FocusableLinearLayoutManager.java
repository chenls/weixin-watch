/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Handler
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.animation.AccelerateDecelerateInterpolator
 *  android.view.animation.Interpolator
 */
package ticwear.design.widget;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import ticwear.design.DesignConfig;
import ticwear.design.R;
import ticwear.design.widget.AppBarScrollController;
import ticwear.design.widget.FocusLayoutHelper;
import ticwear.design.widget.TicklableLayoutManager;
import ticwear.design.widget.TicklableRecyclerView;
import ticwear.design.widget.ViewPropertiesHelper;

public class FocusableLinearLayoutManager
extends LinearLayoutManager
implements TicklableLayoutManager {
    public static final int FOCUS_STATE_CENTRAL = 1;
    public static final int FOCUS_STATE_INVALID = -1;
    public static final int FOCUS_STATE_NON_CENTRAL = 2;
    public static final int FOCUS_STATE_NORMAL = 0;
    private static final int INVALID_SCROLL_OFFSET = Integer.MAX_VALUE;
    static final String TAG = "FocusableLLM";
    private Runnable exitFocusStateRunnable;
    private final AppBarScrollController mAppBarScrollController;
    private final Context mContext;
    @Nullable
    private FocusLayoutHelper mFocusLayoutHelper;
    private final FocusStateRequest mFocusStateRequest = new FocusStateRequest();
    private final List<OnCentralPositionChangedListener> mOnCentralPositionChangedListeners;
    private int mPreviousCentral;
    private int mScrollOffset;
    private boolean mScrollResetting;
    private ScrollVelocityTracker mScrollVelocityTracker;
    @Nullable
    private TicklableRecyclerView mTicklableRecyclerView;
    private final Handler mUiHandler;

    public FocusableLinearLayoutManager(Context context) {
        super(context, 1, false);
        this.exitFocusStateRunnable = new Runnable(){

            @Override
            public void run() {
                FocusableLinearLayoutManager.this.exitFocusStateIfNeed();
            }
        };
        this.mContext = context;
        this.mUiHandler = new Handler();
        this.mOnCentralPositionChangedListeners = new ArrayList<OnCentralPositionChangedListener>();
        this.mPreviousCentral = -1;
        this.mScrollOffset = Integer.MAX_VALUE;
        this.mAppBarScrollController = new AppBarScrollController((View)this.mTicklableRecyclerView);
        this.setInFocusState(false);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void enterFocusStateIfNeed(@Nullable MotionEvent motionEvent) {
        block3: {
            block2: {
                this.getHandler().removeCallbacks(this.exitFocusStateRunnable);
                if (this.mFocusLayoutHelper != null) break block2;
                this.setInFocusState(true);
                if (motionEvent != null && this.mScrollOffset != Integer.MAX_VALUE && motionEvent.getAction() == 0) break block3;
            }
            return;
        }
        motionEvent.offsetLocation(0.0f, (float)this.mScrollOffset);
    }

    private void exitFocusStateIfNeed() {
        this.exitFocusStateIfNeed(null);
    }

    private void exitFocusStateIfNeed(@Nullable MotionEvent motionEvent) {
        this.getHandler().removeCallbacks(this.exitFocusStateRunnable);
        if (this.mFocusLayoutHelper == null) {
            return;
        }
        if (motionEvent != null && this.mScrollOffset != Integer.MAX_VALUE && motionEvent.getAction() == 0) {
            motionEvent.offsetLocation(0.0f, (float)(-this.mScrollOffset));
        }
        this.setInFocusState(false);
    }

    private float getCentralProgress(int n2, int n3, int n4, int n5) {
        int n6 = n5;
        if (n5 < n2) {
            n6 = n2;
        }
        n5 = n6;
        if (n6 > n3) {
            n5 = n3;
        }
        if (n5 > n4) {
            return (float)(n3 - n5) / (float)(n3 - n4);
        }
        return (float)(n5 - n2) / (float)(n4 - n2);
    }

    private void notifyAfterLayoutOnNextMainLoop() {
        this.mUiHandler.post(new Runnable(){

            @Override
            public void run() {
                FocusableLinearLayoutManager.this.requestNotifyChildrenStateChanged(FocusableLinearLayoutManager.this.mFocusStateRequest);
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    private void notifyChildFocusStateChanged(@NonNull TicklableRecyclerView object, int n2, boolean bl2, View view) {
        boolean bl3 = true;
        if (((ViewHolder)(object = (ViewHolder)((RecyclerView)object).getChildViewHolder(view))).prevFocusState == n2) return;
        if (n2 == 0) {
            ((ViewHolder)object).itemView.setFocusable(false);
            ((ViewHolder)object).itemView.setFocusableInTouchMode(false);
        } else {
            ((ViewHolder)object).itemView.setFocusable(true);
            ((ViewHolder)object).itemView.setFocusableInTouchMode(true);
        }
        ((ViewHolder)object).onFocusStateChanged(n2, bl2);
        ViewHolder.access$802((ViewHolder)object, n2);
        bl2 = n2 != 2 ? bl3 : false;
        view.setClickable(bl2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void notifyChildProgressUpdated(ViewHolder viewHolder, float f2, boolean bl2) {
        long l2;
        block6: {
            block5: {
                l2 = viewHolder.getDefaultAnimDuration();
                if (viewHolder.animationStartTime <= 0L) break block5;
                long l3 = System.currentTimeMillis();
                long l4 = viewHolder.animationStartTime;
                ViewHolder.access$702(viewHolder, viewHolder.animationPlayedTime + (l3 - l4));
                if (viewHolder.animationPlayedTime >= l2) {
                    ViewHolder.access$602(viewHolder, 0L);
                    ViewHolder.access$702(viewHolder, 0L);
                    if (!bl2) {
                        l2 = 0L;
                    }
                    break block6;
                } else if (!bl2) {
                    l2 -= viewHolder.animationPlayedTime;
                }
                break block6;
            }
            long l5 = bl2 ? l2 : 0L;
            l2 = l5;
            if (bl2) {
                ViewHolder.access$602(viewHolder, System.currentTimeMillis());
                ViewHolder.access$702(viewHolder, 0L);
                l2 = l5;
            }
        }
        viewHolder.onCentralProgressUpdated(f2, l2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void notifyChildrenStateChanged(@NonNull TicklableRecyclerView ticklableRecyclerView, FocusStateRequest focusStateRequest) {
        int n2 = ViewPropertiesHelper.getTop((View)ticklableRecyclerView);
        int n3 = ViewPropertiesHelper.getBottom((View)ticklableRecyclerView);
        int n4 = ViewPropertiesHelper.getCenterYPos((View)ticklableRecyclerView);
        int n5 = 0;
        while (true) {
            if (n5 >= this.getChildCount()) {
                this.notifyOnCentralPositionChanged(ticklableRecyclerView, focusStateRequest.centerIndex);
                return;
            }
            View view = this.getChildAt(n5);
            int n6 = 0;
            if (focusStateRequest.centerIndex != -1) {
                n6 = n5 == focusStateRequest.centerIndex ? 1 : 2;
            }
            boolean bl2 = view.isShown() && focusStateRequest.animate;
            this.notifyChildFocusStateChanged(ticklableRecyclerView, n6, bl2, view);
            if (n6 != 0) {
                n6 = ViewPropertiesHelper.getCenterYPos(view);
                int n7 = view.getHeight() / 2;
                float f2 = this.getCentralProgress(n2 + n7, n3 - n7, n4, n6);
                ViewHolder viewHolder = (ViewHolder)ticklableRecyclerView.getChildViewHolder(view);
                bl2 = view.isShown() && focusStateRequest.animate && !focusStateRequest.scroll;
                this.notifyChildProgressUpdated(viewHolder, f2, bl2);
            }
            ++n5;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void notifyOnCentralPositionChanged(@NonNull TicklableRecyclerView iterator, int n2) {
        int n3 = -1;
        if ((n2 = n2 == -1 ? n3 : ((RecyclerView)((Object)iterator)).getChildAdapterPosition(this.getChildAt(n2))) != this.mPreviousCentral) {
            iterator = this.mOnCentralPositionChangedListeners.iterator();
            while (iterator.hasNext()) {
                iterator.next().onCentralPositionChanged(n2);
            }
            this.mPreviousCentral = n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onScrollVerticalBy(int n2) {
        boolean bl2 = true;
        if (this.mScrollVelocityTracker == null && this.mFocusLayoutHelper != null) {
            int n3 = this.mFocusLayoutHelper.getCentralItemHeight();
            this.mScrollVelocityTracker = new ScrollVelocityTracker(this.mContext, n3);
        }
        n2 = this.mScrollVelocityTracker != null && this.mScrollVelocityTracker.addScroll(n2) ? 1 : 0;
        if (this.getChildCount() > 0) {
            if (this.mFocusLayoutHelper == null) {
                this.requestNotifyChildrenAboutExit();
                return;
            }
            if (n2 != 0) {
                bl2 = false;
            }
            this.requestNotifyChildrenAboutScroll(bl2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void requestNotifyChildrenStateChanged(final FocusStateRequest focusStateRequest) {
        boolean bl2;
        block3: {
            block2: {
                if (focusStateRequest.notifyOnNextLayout) break block2;
                bl2 = this.mTicklableRecyclerView != null && this.mTicklableRecyclerView.getItemAnimator().isRunning(new RecyclerView.ItemAnimator.ItemAnimatorFinishedListener(){

                    @Override
                    public void onAnimationsFinished() {
                        if (FocusableLinearLayoutManager.this.mTicklableRecyclerView != null) {
                            FocusableLinearLayoutManager.this.notifyChildrenStateChanged(FocusableLinearLayoutManager.this.mTicklableRecyclerView, focusStateRequest);
                        }
                    }
                });
                if (DesignConfig.DEBUG_RECYCLER_VIEW) break block3;
            }
            return;
        }
        Log.v((String)TAG, (String)("request state changed with item anim running? " + bl2));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void restoreOffset() {
        if (this.mTicklableRecyclerView == null) {
            return;
        }
        this.mScrollResetting = true;
        if (this.mFocusLayoutHelper != null) {
            this.mScrollOffset = this.mTicklableRecyclerView.getTop();
            this.mTicklableRecyclerView.offsetTopAndBottom(-this.mScrollOffset);
            this.mTicklableRecyclerView.scrollBy(0, -this.mScrollOffset);
        } else if (this.mScrollOffset != Integer.MAX_VALUE) {
            this.mTicklableRecyclerView.offsetTopAndBottom(this.mScrollOffset);
            this.mTicklableRecyclerView.scrollBy(0, this.mScrollOffset);
            this.mScrollOffset = Integer.MAX_VALUE;
        }
        this.mScrollResetting = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setInFocusState(boolean bl2) {
        block10: {
            block9: {
                boolean bl3 = this.mFocusLayoutHelper != null;
                if (bl3 == bl2) break block9;
                if (bl2 && this.mTicklableRecyclerView != null) {
                    this.mFocusLayoutHelper = new FocusLayoutHelper(this.mTicklableRecyclerView, this);
                } else if (this.mFocusLayoutHelper != null) {
                    this.mFocusLayoutHelper.destroy();
                    this.mFocusLayoutHelper = null;
                }
                this.restoreOffset();
                this.mFocusStateRequest.notifyOnNextLayout = true;
                if (this.getChildCount() > 0) {
                    if (this.mFocusLayoutHelper != null) {
                        this.requestNotifyChildrenAboutFocus();
                    } else {
                        this.requestNotifyChildrenAboutExit();
                    }
                }
                this.requestSimpleAnimationsInNextLayout();
                if (this.mTicklableRecyclerView != null && this.mTicklableRecyclerView.getAdapter() != null) break block10;
            }
            return;
        }
        this.mTicklableRecyclerView.getAdapter().notifyDataSetChanged();
    }

    public void addOnCentralPositionChangedListener(OnCentralPositionChangedListener onCentralPositionChangedListener) {
        this.mOnCentralPositionChangedListeners.add(onCentralPositionChangedListener);
    }

    @Override
    public boolean canScrollVertically() {
        return this.getChildCount() > 0;
    }

    public void clearOnCentralPositionChangedListener() {
        this.mOnCentralPositionChangedListeners.clear();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        this.exitFocusStateIfNeed(motionEvent);
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean dispatchTouchSidePanelEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0: {
                this.enterFocusStateIfNeed(motionEvent);
                break;
            }
            case 1: 
            case 3: {
                this.getHandler().postDelayed(this.exitFocusStateRunnable, (long)this.mContext.getResources().getInteger(R.integer.design_time_action_idle_timeout));
            }
        }
        return this.mFocusLayoutHelper != null && this.mFocusLayoutHelper.dispatchTouchSidePanelEvent(motionEvent);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(-1, -2);
    }

    @Override
    public boolean getClipToPadding() {
        return this.mFocusLayoutHelper == null && super.getClipToPadding();
    }

    public Handler getHandler() {
        return this.mUiHandler;
    }

    @Override
    public int getPaddingBottom() {
        if (this.mFocusLayoutHelper != null) {
            return this.mFocusLayoutHelper.getVerticalPadding();
        }
        return super.getPaddingBottom();
    }

    @Override
    public int getPaddingTop() {
        if (this.mFocusLayoutHelper != null) {
            return this.mFocusLayoutHelper.getVerticalPadding();
        }
        return super.getPaddingTop();
    }

    @Override
    public int getScrollOffset() {
        if (this.mScrollOffset == Integer.MAX_VALUE) {
            return 0;
        }
        return this.mScrollOffset;
    }

    @Override
    public boolean interceptPreScroll() {
        return this.mFocusLayoutHelper != null && this.mFocusLayoutHelper.interceptPreScroll();
    }

    @Override
    public void onAttachedToWindow(RecyclerView recyclerView) {
        super.onAttachedToWindow(recyclerView);
    }

    @Override
    public void onDetachedFromWindow(RecyclerView recyclerView, RecyclerView.Recycler recycler) {
        this.getHandler().removeCallbacks(this.exitFocusStateRunnable);
        super.onDetachedFromWindow(recyclerView, recycler);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        block3: {
            block2: {
                super.onLayoutChildren(recycler, state);
                if (!this.mFocusStateRequest.notifyOnNextLayout) break block2;
                this.mFocusStateRequest.notifyOnNextLayout = false;
                if (!this.mFocusStateRequest.animate) break block3;
                this.postOnAnimation(new Runnable(){

                    @Override
                    public void run() {
                        FocusableLinearLayoutManager.this.notifyAfterLayoutOnNextMainLoop();
                    }
                });
            }
            return;
        }
        this.requestNotifyChildrenStateChanged(this.mFocusStateRequest);
    }

    @Override
    public void onScrollStateChanged(int n2) {
        if (this.mFocusLayoutHelper != null) {
            this.mFocusLayoutHelper.onScrollStateChanged(n2);
            return;
        }
        super.onScrollStateChanged(n2);
    }

    public void removeOnCentralPositionChangedListener(OnCentralPositionChangedListener onCentralPositionChangedListener) {
        this.mOnCentralPositionChangedListeners.remove(onCentralPositionChangedListener);
    }

    void requestNotifyChildrenAboutExit() {
        if (this.mScrollResetting) {
            return;
        }
        this.mFocusStateRequest.centerIndex = -1;
        this.mFocusStateRequest.animate = true;
        this.mFocusStateRequest.scroll = false;
        this.requestNotifyChildrenStateChanged(this.mFocusStateRequest);
    }

    void requestNotifyChildrenAboutFocus() {
        if (this.mScrollResetting || this.mFocusLayoutHelper == null) {
            return;
        }
        this.mFocusStateRequest.centerIndex = this.mFocusLayoutHelper.findCenterViewIndex();
        this.mFocusStateRequest.animate = true;
        this.mFocusStateRequest.scroll = false;
        this.requestNotifyChildrenStateChanged(this.mFocusStateRequest);
    }

    void requestNotifyChildrenAboutScroll(boolean bl2) {
        if (this.mScrollResetting || this.mFocusLayoutHelper == null) {
            return;
        }
        this.mFocusStateRequest.centerIndex = this.mFocusLayoutHelper.findCenterViewIndex();
        this.mFocusStateRequest.animate = bl2;
        this.mFocusStateRequest.scroll = true;
        this.requestNotifyChildrenStateChanged(this.mFocusStateRequest);
    }

    @Override
    public int scrollVerticallyBy(int n2, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int n3 = super.scrollVerticallyBy(n2, recycler, state);
        if (n3 == n2) {
            this.onScrollVerticalBy(n2);
        }
        return n3;
    }

    @Override
    public void setTicklableRecyclerView(TicklableRecyclerView ticklableRecyclerView) {
        this.mTicklableRecyclerView = ticklableRecyclerView;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int updateScrollOffset(int n2) {
        if (this.mTicklableRecyclerView == null || this.mScrollOffset == Integer.MAX_VALUE || this.mAppBarScrollController.isAppBarChanging()) {
            this.mScrollOffset = n2;
            return 0;
        } else {
            if (this.mScrollOffset == n2) return 0;
            int n3 = -(n2 - this.mScrollOffset);
            int n4 = this.mTicklableRecyclerView.computeVerticalScrollOffset();
            this.mTicklableRecyclerView.scrollBySkipNestedScroll(0, n3);
            n3 = this.mTicklableRecyclerView.computeVerticalScrollOffset();
            this.mScrollOffset -= n3 - n4;
            return n2 - this.mScrollOffset;
        }
    }

    @Override
    public boolean useScrollAsOffset() {
        return this.mFocusLayoutHelper != null;
    }

    @Override
    public boolean validAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null && !(adapter.createViewHolder(this.mTicklableRecyclerView, adapter.getItemViewType(0)) instanceof ViewHolder)) {
            if (DesignConfig.DEBUG) {
                throw new IllegalArgumentException("adapter's ViewHolder should be instance of FocusableLinearLayoutManager.ViewHolder");
            }
            Log.w((String)TAG, (String)"adapter's ViewHolder should be instance of FocusableLinearLayoutManager.ViewHolder");
            return false;
        }
        return true;
    }

    @Documented
    @Retention(value=RetentionPolicy.SOURCE)
    @Target(value={ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.LOCAL_VARIABLE})
    public static @interface FocusState {
    }

    private static class FocusStateRequest {
        public boolean animate = false;
        public int centerIndex = -1;
        public boolean notifyOnNextLayout = false;
        public boolean scroll = false;

        public String toString() {
            return "FocusStateRequest@" + this.hashCode() + "{" + "notifyOnNext " + this.notifyOnNextLayout + ", center " + this.centerIndex + ", animate " + this.animate + ", scroll " + this.scroll + "}";
        }
    }

    public static interface OnCentralPositionChangedListener {
        public void onCentralPositionChanged(int var1);
    }

    public static interface OnCentralProgressUpdatedListener {
        public void onCentralProgressUpdated(float var1, long var2);
    }

    public static interface OnFocusStateChangedListener {
        public void onFocusStateChanged(int var1, boolean var2);
    }

    private static class ScrollVelocityTracker {
        private final float mFastScrollVelocity;
        private long mLastScrollTime = -1L;

        ScrollVelocityTracker(@NonNull Context context, int n2) {
            long l2 = context.getResources().getInteger(R.integer.design_anim_list_item_state_change);
            this.mFastScrollVelocity = 1.5f * (float)n2 / (float)l2;
        }

        public boolean addScroll(int n2) {
            boolean bl2 = false;
            long l2 = System.currentTimeMillis();
            boolean bl3 = bl2;
            if (this.mLastScrollTime > 0L) {
                long l3 = this.mLastScrollTime;
                bl3 = bl2;
                if ((float)Math.abs(n2) / (float)(l2 - l3) > this.mFastScrollVelocity) {
                    bl3 = true;
                }
            }
            this.mLastScrollTime = l2;
            return bl3;
        }
    }

    public static class ViewHolder
    extends RecyclerView.ViewHolder {
        private long animationPlayedTime = 0L;
        private long animationStartTime = 0L;
        private final long defaultAnimDuration;
        private final Interpolator focusInterpolator;
        private int prevFocusState = -1;

        public ViewHolder(View view) {
            super(view);
            this.defaultAnimDuration = view.getContext().getResources().getInteger(R.integer.design_anim_list_item_state_change);
            this.focusInterpolator = new AccelerateDecelerateInterpolator();
        }

        static /* synthetic */ long access$602(ViewHolder viewHolder, long l2) {
            viewHolder.animationStartTime = l2;
            return l2;
        }

        static /* synthetic */ long access$702(ViewHolder viewHolder, long l2) {
            viewHolder.animationPlayedTime = l2;
            return l2;
        }

        static /* synthetic */ int access$802(ViewHolder viewHolder, int n2) {
            viewHolder.prevFocusState = n2;
            return n2;
        }

        private void transform(float f2, float f3, long l2) {
            this.itemView.animate().cancel();
            if (l2 > 0L) {
                this.itemView.animate().setDuration(l2).alpha(f3).scaleX(f2).scaleY(f2).start();
                return;
            }
            this.itemView.setScaleX(f2);
            this.itemView.setScaleY(f2);
            this.itemView.setAlpha(f3);
        }

        public long getDefaultAnimDuration() {
            return this.defaultAnimDuration;
        }

        public Interpolator getFocusInterpolator() {
            return this.focusInterpolator;
        }

        protected final String getLogPrefix() {
            int n2 = this.getLayoutPosition();
            int n3 = this.getAdapterPosition();
            return String.format(Locale.getDefault(), "<%d,%d %8x,%8x>: ", n3, n2, this.hashCode(), this.itemView.hashCode());
        }

        protected final String getLogSuffix() {
            return " with " + this + ", view " + this.itemView;
        }

        protected void onCentralProgressUpdated(float f2, long l2) {
            if (this.itemView instanceof OnCentralProgressUpdatedListener) {
                ((OnCentralProgressUpdatedListener)this.itemView).onCentralProgressUpdated(f2, l2);
                return;
            }
            this.transform(1.0f + (1.1f - 1.0f) * f2, 0.6f + (1.0f - 0.6f) * this.getFocusInterpolator().getInterpolation(f2), l2);
        }

        /*
         * Enabled aggressive block sorting
         */
        protected void onFocusStateChanged(int n2, boolean bl2) {
            long l2;
            if (DesignConfig.DEBUG_RECYCLER_VIEW) {
                Log.d((String)FocusableLinearLayoutManager.TAG, (String)(this.getLogPrefix() + "focus state to " + n2 + ", animate " + bl2 + this.getLogSuffix()));
            }
            if (this.itemView instanceof OnFocusStateChangedListener) {
                ((OnFocusStateChangedListener)this.itemView).onFocusStateChanged(n2, bl2);
                return;
            } else {
                if (n2 != 0) return;
                l2 = bl2 ? this.getDefaultAnimDuration() : 0L;
            }
            this.transform(1.0f, 1.0f, l2);
        }
    }
}

