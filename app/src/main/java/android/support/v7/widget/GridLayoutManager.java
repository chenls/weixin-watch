/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseIntArray
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 */
package android.support.v7.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import java.util.Arrays;

public class GridLayoutManager
extends LinearLayoutManager {
    private static final boolean DEBUG = false;
    public static final int DEFAULT_SPAN_COUNT = -1;
    private static final String TAG = "GridLayoutManager";
    int[] mCachedBorders;
    final Rect mDecorInsets;
    boolean mPendingSpanCountChange = false;
    final SparseIntArray mPreLayoutSpanIndexCache;
    final SparseIntArray mPreLayoutSpanSizeCache = new SparseIntArray();
    View[] mSet;
    int mSpanCount = -1;
    SpanSizeLookup mSpanSizeLookup;

    public GridLayoutManager(Context context, int n2) {
        super(context);
        this.mPreLayoutSpanIndexCache = new SparseIntArray();
        this.mSpanSizeLookup = new DefaultSpanSizeLookup();
        this.mDecorInsets = new Rect();
        this.setSpanCount(n2);
    }

    public GridLayoutManager(Context context, int n2, int n3, boolean bl2) {
        super(context, n3, bl2);
        this.mPreLayoutSpanIndexCache = new SparseIntArray();
        this.mSpanSizeLookup = new DefaultSpanSizeLookup();
        this.mDecorInsets = new Rect();
        this.setSpanCount(n2);
    }

    public GridLayoutManager(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2, n3);
        this.mPreLayoutSpanIndexCache = new SparseIntArray();
        this.mSpanSizeLookup = new DefaultSpanSizeLookup();
        this.mDecorInsets = new Rect();
        this.setSpanCount(GridLayoutManager.getProperties((Context)context, (AttributeSet)attributeSet, (int)n2, (int)n3).spanCount);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void assignSpans(RecyclerView.Recycler recycler, RecyclerView.State state, int n2, int n3, boolean bl2) {
        int n4;
        int n5;
        int n6;
        if (bl2) {
            n3 = 0;
            n6 = n2;
            n5 = 1;
            n2 = n3;
        } else {
            --n2;
            n6 = -1;
            n5 = -1;
        }
        if (this.mOrientation == 1 && this.isLayoutRTL()) {
            n3 = this.mSpanCount - 1;
            n4 = -1;
        } else {
            n3 = 0;
            n4 = 1;
        }
        while (n2 != n6) {
            View view = this.mSet[n2];
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            LayoutParams.access$102(layoutParams, this.getSpanSize(recycler, state, this.getPosition(view)));
            if (n4 == -1 && layoutParams.mSpanSize > 1) {
                LayoutParams.access$002(layoutParams, n3 - (layoutParams.mSpanSize - 1));
            } else {
                LayoutParams.access$002(layoutParams, n3);
            }
            n3 += layoutParams.mSpanSize * n4;
            n2 += n5;
        }
        return;
    }

    private void cachePreLayoutSpanMapping() {
        int n2 = this.getChildCount();
        for (int i2 = 0; i2 < n2; ++i2) {
            LayoutParams layoutParams = (LayoutParams)this.getChildAt(i2).getLayoutParams();
            int n3 = layoutParams.getViewLayoutPosition();
            this.mPreLayoutSpanSizeCache.put(n3, layoutParams.getSpanSize());
            this.mPreLayoutSpanIndexCache.put(n3, layoutParams.getSpanIndex());
        }
    }

    private void calculateItemBorders(int n2) {
        this.mCachedBorders = GridLayoutManager.calculateItemBorders(this.mCachedBorders, this.mSpanCount, n2);
    }

    static int[] calculateItemBorders(int[] nArray, int n2, int n3) {
        int[] nArray2;
        block7: {
            block6: {
                if (nArray == null || nArray.length != n2 + 1) break block6;
                nArray2 = nArray;
                if (nArray[nArray.length - 1] == n3) break block7;
            }
            nArray2 = new int[n2 + 1];
        }
        nArray2[0] = 0;
        int n4 = n3 / n2;
        int n5 = n3 % n2;
        int n6 = 0;
        n3 = 0;
        for (int i2 = 1; i2 <= n2; ++i2) {
            int n7;
            int n8 = n4;
            n3 = n7 = n3 + n5;
            int n9 = n8;
            if (n7 > 0) {
                n3 = n7;
                n9 = n8;
                if (n2 - n7 < n5) {
                    n9 = n8 + 1;
                    n3 = n7 - n2;
                }
            }
            nArray2[i2] = n6 += n9;
        }
        return nArray2;
    }

    private void clearPreLayoutSpanMappingCache() {
        this.mPreLayoutSpanSizeCache.clear();
        this.mPreLayoutSpanIndexCache.clear();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void ensureAnchorIsInCorrectSpan(RecyclerView.Recycler recycler, RecyclerView.State state, LinearLayoutManager.AnchorInfo anchorInfo, int n2) {
        int n3 = 1;
        if (n2 != 1) {
            n3 = 0;
        }
        n2 = this.getSpanIndex(recycler, state, anchorInfo.mPosition);
        if (n3 != 0) {
            while (n2 > 0 && anchorInfo.mPosition > 0) {
                --anchorInfo.mPosition;
                n2 = this.getSpanIndex(recycler, state, anchorInfo.mPosition);
            }
            return;
        } else {
            int n4;
            int n5 = state.getItemCount();
            for (n3 = anchorInfo.mPosition; n3 < n5 - 1 && (n4 = this.getSpanIndex(recycler, state, n3 + 1)) > n2; ++n3) {
                n2 = n4;
            }
            anchorInfo.mPosition = n3;
        }
    }

    private void ensureViewSet() {
        if (this.mSet == null || this.mSet.length != this.mSpanCount) {
            this.mSet = new View[this.mSpanCount];
        }
    }

    private int getSpanGroupIndex(RecyclerView.Recycler recycler, RecyclerView.State state, int n2) {
        if (!state.isPreLayout()) {
            return this.mSpanSizeLookup.getSpanGroupIndex(n2, this.mSpanCount);
        }
        int n3 = recycler.convertPreLayoutPositionToPostLayout(n2);
        if (n3 == -1) {
            Log.w((String)TAG, (String)("Cannot find span size for pre layout position. " + n2));
            return 0;
        }
        return this.mSpanSizeLookup.getSpanGroupIndex(n3, this.mSpanCount);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int getSpanIndex(RecyclerView.Recycler recycler, RecyclerView.State state, int n2) {
        int n3;
        if (!state.isPreLayout()) {
            return this.mSpanSizeLookup.getCachedSpanIndex(n2, this.mSpanCount);
        }
        int n4 = n3 = this.mPreLayoutSpanIndexCache.get(n2, -1);
        if (n3 != -1) return n4;
        n4 = recycler.convertPreLayoutPositionToPostLayout(n2);
        if (n4 != -1) return this.mSpanSizeLookup.getCachedSpanIndex(n4, this.mSpanCount);
        Log.w((String)TAG, (String)("Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + n2));
        return 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int getSpanSize(RecyclerView.Recycler recycler, RecyclerView.State state, int n2) {
        int n3;
        if (!state.isPreLayout()) {
            return this.mSpanSizeLookup.getSpanSize(n2);
        }
        int n4 = n3 = this.mPreLayoutSpanSizeCache.get(n2, -1);
        if (n3 != -1) return n4;
        n4 = recycler.convertPreLayoutPositionToPostLayout(n2);
        if (n4 != -1) return this.mSpanSizeLookup.getSpanSize(n4);
        Log.w((String)TAG, (String)("Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + n2));
        return 1;
    }

    private void guessMeasurement(float f2, int n2) {
        this.calculateItemBorders(Math.max(Math.round((float)this.mSpanCount * f2), n2));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void measureChildWithDecorationsAndMargin(View view, int n2, int n3, boolean bl2, boolean bl3) {
        int n4;
        RecyclerView.LayoutParams layoutParams;
        block10: {
            block9: {
                block8: {
                    block7: {
                        this.calculateItemDecorationsForChild(view, this.mDecorInsets);
                        layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
                        if (bl2) break block7;
                        n4 = n2;
                        if (this.mOrientation != 1) break block8;
                    }
                    n4 = this.updateSpecWithExtra(n2, layoutParams.leftMargin + this.mDecorInsets.left, layoutParams.rightMargin + this.mDecorInsets.right);
                }
                if (bl2) break block9;
                n2 = n3;
                if (this.mOrientation != 0) break block10;
            }
            n2 = this.updateSpecWithExtra(n3, layoutParams.topMargin + this.mDecorInsets.top, layoutParams.bottomMargin + this.mDecorInsets.bottom);
        }
        if (bl2 = bl3 ? this.shouldReMeasureChild(view, n4, n2, layoutParams) : this.shouldMeasureChild(view, n4, n2, layoutParams)) {
            view.measure(n4, n2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateMeasurements() {
        int n2 = this.getOrientation() == 1 ? this.getWidth() - this.getPaddingRight() - this.getPaddingLeft() : this.getHeight() - this.getPaddingBottom() - this.getPaddingTop();
        this.calculateItemBorders(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private int updateSpecWithExtra(int n2, int n3, int n4) {
        int n5;
        if (n3 == 0 && n4 == 0 || (n5 = View.MeasureSpec.getMode((int)n2)) != Integer.MIN_VALUE && n5 != 0x40000000) {
            return n2;
        }
        return View.MeasureSpec.makeMeasureSpec((int)Math.max(0, View.MeasureSpec.getSize((int)n2) - n3 - n4), (int)n5);
    }

    @Override
    public boolean checkLayoutParams(RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    View findReferenceChild(RecyclerView.Recycler recycler, RecyclerView.State state, int n2, int n3, int n4) {
        this.ensureLayoutState();
        View view = null;
        View view2 = null;
        int n5 = this.mOrientationHelper.getStartAfterPadding();
        int n6 = this.mOrientationHelper.getEndAfterPadding();
        int n7 = n3 > n2 ? 1 : -1;
        while (n2 != n3) {
            View view3 = this.getChildAt(n2);
            int n8 = this.getPosition(view3);
            View view4 = view;
            View view5 = view2;
            if (n8 >= 0) {
                view4 = view;
                view5 = view2;
                if (n8 < n4) {
                    if (this.getSpanIndex(recycler, state, n8) != 0) {
                        view5 = view2;
                        view4 = view;
                    } else if (((RecyclerView.LayoutParams)view3.getLayoutParams()).isItemRemoved()) {
                        view4 = view;
                        view5 = view2;
                        if (view == null) {
                            view4 = view3;
                            view5 = view2;
                        }
                    } else {
                        if (this.mOrientationHelper.getDecoratedStart(view3) < n6) {
                            view4 = view3;
                            if (this.mOrientationHelper.getDecoratedEnd(view3) >= n5) return view4;
                        }
                        view4 = view;
                        view5 = view2;
                        if (view2 == null) {
                            view4 = view;
                            view5 = view3;
                        }
                    }
                }
            }
            n2 += n7;
            view = view4;
            view2 = view5;
        }
        if (view2 != null) return view2;
        view2 = view;
        return view2;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        if (this.mOrientation == 0) {
            return new LayoutParams(-2, -1);
        }
        return new LayoutParams(-1, -2);
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    @Override
    public int getColumnCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation == 1) {
            return this.mSpanCount;
        }
        if (state.getItemCount() < 1) {
            return 0;
        }
        return this.getSpanGroupIndex(recycler, state, state.getItemCount() - 1) + 1;
    }

    @Override
    public int getRowCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation == 0) {
            return this.mSpanCount;
        }
        if (state.getItemCount() < 1) {
            return 0;
        }
        return this.getSpanGroupIndex(recycler, state, state.getItemCount() - 1) + 1;
    }

    public int getSpanCount() {
        return this.mSpanCount;
    }

    public SpanSizeLookup getSpanSizeLookup() {
        return this.mSpanSizeLookup;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    void layoutChunk(RecyclerView.Recycler recycler, RecyclerView.State object, LinearLayoutManager.LayoutState layoutState, LinearLayoutManager.LayoutChunkResult layoutChunkResult) {
        int n2 = this.mOrientationHelper.getModeInOther();
        int n3 = n2 != 0x40000000 ? 1 : 0;
        int n4 = this.getChildCount() > 0 ? this.mCachedBorders[this.mSpanCount] : 0;
        if (n3 != 0) {
            this.updateMeasurements();
        }
        boolean bl2 = layoutState.mItemDirection == 1;
        int n5 = 0;
        int n6 = 0;
        int n7 = this.mSpanCount;
        int n8 = n5;
        int n9 = n6;
        if (!bl2) {
            n7 = this.getSpanIndex(recycler, (RecyclerView.State)object, layoutState.mCurrentPosition) + this.getSpanSize(recycler, (RecyclerView.State)object, layoutState.mCurrentPosition);
            n9 = n6;
            n8 = n5;
        }
        while (n8 < this.mSpanCount && layoutState.hasMore((RecyclerView.State)object) && n7 > 0) {
            View view;
            n6 = layoutState.mCurrentPosition;
            n5 = this.getSpanSize(recycler, (RecyclerView.State)object, n6);
            if (n5 > this.mSpanCount) {
                throw new IllegalArgumentException("Item at position " + n6 + " requires " + n5 + " spans but GridLayoutManager has only " + this.mSpanCount + " spans.");
            }
            if ((n7 -= n5) < 0 || (view = layoutState.next(recycler)) == null) break;
            n9 += n5;
            this.mSet[n8] = view;
            ++n8;
        }
        if (n8 == 0) {
            layoutChunkResult.mFinished = true;
            return;
        }
        n7 = 0;
        float f2 = 0.0f;
        this.assignSpans(recycler, (RecyclerView.State)object, n8, n9, bl2);
        for (n9 = 0; n9 < n8; ++n9) {
            boolean bl3;
            recycler = this.mSet[n9];
            if (layoutState.mScrapList == null) {
                if (bl2) {
                    this.addView((View)recycler);
                } else {
                    this.addView((View)recycler, 0);
                }
            } else if (bl2) {
                this.addDisappearingView((View)recycler);
            } else {
                this.addDisappearingView((View)recycler, 0);
            }
            object = (LayoutParams)recycler.getLayoutParams();
            n6 = this.mCachedBorders[((LayoutParams)((Object)object)).mSpanIndex + ((LayoutParams)((Object)object)).mSpanSize];
            int n10 = this.mCachedBorders[((LayoutParams)((Object)object)).mSpanIndex];
            n5 = this.mOrientation == 0 ? ((LayoutParams)object).height : ((LayoutParams)object).width;
            n6 = GridLayoutManager.getChildMeasureSpec(n6 - n10, n2, 0, n5, false);
            n10 = this.mOrientationHelper.getTotalSpace();
            int n11 = this.mOrientationHelper.getMode();
            n5 = this.mOrientation == 1 ? ((LayoutParams)object).height : ((LayoutParams)object).width;
            n5 = GridLayoutManager.getChildMeasureSpec(n10, n11, 0, n5, true);
            if (this.mOrientation == 1) {
                bl3 = ((LayoutParams)object).height == -1;
                this.measureChildWithDecorationsAndMargin((View)recycler, n6, n5, bl3, false);
            } else {
                bl3 = ((LayoutParams)object).width == -1;
                this.measureChildWithDecorationsAndMargin((View)recycler, n5, n6, bl3, false);
            }
            n6 = this.mOrientationHelper.getDecoratedMeasurement((View)recycler);
            n5 = n7;
            if (n6 > n7) {
                n5 = n6;
            }
            float f3 = 1.0f * (float)this.mOrientationHelper.getDecoratedMeasurementInOther((View)recycler) / (float)((LayoutParams)((Object)object)).mSpanSize;
            float f4 = f2;
            if (f3 > f2) {
                f4 = f3;
            }
            n7 = n5;
            f2 = f4;
        }
        n9 = n7;
        if (n3 != 0) {
            this.guessMeasurement(f2, n4);
            n7 = 0;
            n3 = 0;
            while (true) {
                n9 = n7;
                if (n3 >= n8) break;
                recycler = this.mSet[n3];
                object = (LayoutParams)recycler.getLayoutParams();
                n9 = this.mCachedBorders[((LayoutParams)((Object)object)).mSpanIndex + ((LayoutParams)((Object)object)).mSpanSize];
                n5 = this.mCachedBorders[((LayoutParams)((Object)object)).mSpanIndex];
                n4 = this.mOrientation == 0 ? ((LayoutParams)object).height : ((LayoutParams)object).width;
                n9 = GridLayoutManager.getChildMeasureSpec(n9 - n5, 0x40000000, 0, n4, false);
                n5 = this.mOrientationHelper.getTotalSpace();
                n6 = this.mOrientationHelper.getMode();
                n4 = this.mOrientation == 1 ? ((LayoutParams)object).height : ((LayoutParams)object).width;
                n4 = GridLayoutManager.getChildMeasureSpec(n5, n6, 0, n4, true);
                if (this.mOrientation == 1) {
                    this.measureChildWithDecorationsAndMargin((View)recycler, n9, n4, false, true);
                } else {
                    this.measureChildWithDecorationsAndMargin((View)recycler, n4, n9, false, true);
                }
                n9 = this.mOrientationHelper.getDecoratedMeasurement((View)recycler);
                n4 = n7;
                if (n9 > n7) {
                    n4 = n9;
                }
                ++n3;
                n7 = n4;
            }
        }
        n4 = View.MeasureSpec.makeMeasureSpec((int)n9, (int)0x40000000);
        for (n7 = 0; n7 < n8; ++n7) {
            recycler = this.mSet[n7];
            if (this.mOrientationHelper.getDecoratedMeasurement((View)recycler) == n9) continue;
            object = (LayoutParams)recycler.getLayoutParams();
            n5 = this.mCachedBorders[((LayoutParams)((Object)object)).mSpanIndex + ((LayoutParams)((Object)object)).mSpanSize];
            n6 = this.mCachedBorders[((LayoutParams)((Object)object)).mSpanIndex];
            n3 = this.mOrientation == 0 ? ((LayoutParams)object).height : ((LayoutParams)object).width;
            n3 = GridLayoutManager.getChildMeasureSpec(n5 - n6, 0x40000000, 0, n3, false);
            if (this.mOrientation == 1) {
                this.measureChildWithDecorationsAndMargin((View)recycler, n3, n4, true, true);
                continue;
            }
            this.measureChildWithDecorationsAndMargin((View)recycler, n4, n3, true, true);
        }
        layoutChunkResult.mConsumed = n9;
        n3 = 0;
        n4 = 0;
        n5 = 0;
        n7 = 0;
        if (this.mOrientation == 1) {
            if (layoutState.mLayoutDirection == -1) {
                n7 = layoutState.mOffset;
                n9 = n7 - n9;
            } else {
                n5 = layoutState.mOffset;
                n7 = n5 + n9;
                n9 = n5;
            }
        } else if (layoutState.mLayoutDirection == -1) {
            n4 = layoutState.mOffset;
            n3 = n4 - n9;
            n9 = n5;
        } else {
            n3 = layoutState.mOffset;
            n4 = n3 + n9;
            n9 = n5;
        }
        n5 = 0;
        n6 = n4;
        n2 = n3;
        n4 = n5;
        n5 = n7;
        while (true) {
            if (n4 >= n8) {
                Arrays.fill(this.mSet, null);
                return;
            }
            recycler = this.mSet[n4];
            object = (LayoutParams)recycler.getLayoutParams();
            if (this.mOrientation == 1) {
                if (this.isLayoutRTL()) {
                    n3 = this.getPaddingLeft() + this.mCachedBorders[((LayoutParams)((Object)object)).mSpanIndex + ((LayoutParams)((Object)object)).mSpanSize];
                    n7 = n3 - this.mOrientationHelper.getDecoratedMeasurementInOther((View)recycler);
                } else {
                    n7 = this.getPaddingLeft() + this.mCachedBorders[((LayoutParams)((Object)object)).mSpanIndex];
                    n3 = n7 + this.mOrientationHelper.getDecoratedMeasurementInOther((View)recycler);
                }
            } else {
                n9 = this.getPaddingTop() + this.mCachedBorders[((LayoutParams)((Object)object)).mSpanIndex];
                n5 = n9 + this.mOrientationHelper.getDecoratedMeasurementInOther((View)recycler);
                n7 = n2;
                n3 = n6;
            }
            this.layoutDecorated((View)recycler, n7 + ((LayoutParams)object).leftMargin, n9 + ((LayoutParams)object).topMargin, n3 - ((LayoutParams)object).rightMargin, n5 - ((LayoutParams)object).bottomMargin);
            if (((RecyclerView.LayoutParams)((Object)object)).isItemRemoved() || ((RecyclerView.LayoutParams)((Object)object)).isItemChanged()) {
                layoutChunkResult.mIgnoreConsumed = true;
            }
            layoutChunkResult.mFocusable |= recycler.isFocusable();
            ++n4;
            n2 = n7;
            n6 = n3;
        }
    }

    @Override
    void onAnchorReady(RecyclerView.Recycler recycler, RecyclerView.State state, LinearLayoutManager.AnchorInfo anchorInfo, int n2) {
        super.onAnchorReady(recycler, state, anchorInfo, n2);
        this.updateMeasurements();
        if (state.getItemCount() > 0 && !state.isPreLayout()) {
            this.ensureAnchorIsInCorrectSpan(recycler, state, anchorInfo, n2);
        }
        this.ensureViewSet();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public View onFocusSearchFailed(View view, int n2, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int n3;
        int n4;
        View view2 = this.findContainingItemView(view);
        if (view2 == null) {
            return null;
        }
        LayoutParams layoutParams = (LayoutParams)view2.getLayoutParams();
        int n5 = layoutParams.mSpanIndex;
        int n6 = layoutParams.mSpanIndex + layoutParams.mSpanSize;
        if (super.onFocusSearchFailed(view, n2, recycler, state) == null) {
            return null;
        }
        boolean bl2 = this.convertFocusDirectionToLayoutDirection(n2) == 1;
        if ((n2 = bl2 != this.mShouldReverseLayout ? 1 : 0) != 0) {
            n2 = this.getChildCount() - 1;
            n4 = -1;
            n3 = -1;
        } else {
            n2 = 0;
            n4 = 1;
            n3 = this.getChildCount();
        }
        int n7 = this.mOrientation == 1 && this.isLayoutRTL() ? 1 : 0;
        state = null;
        int n8 = -1;
        int n9 = 0;
        int n10 = n2;
        while (n10 != n3) {
            view = this.getChildAt(n10);
            if (view == view2) {
                return state;
            }
            if (view.isFocusable()) {
                layoutParams = (LayoutParams)view.getLayoutParams();
                int n11 = layoutParams.mSpanIndex;
                int n12 = layoutParams.mSpanIndex + layoutParams.mSpanSize;
                if (n11 == n5) {
                    recycler = view;
                    if (n12 == n6) return recycler;
                }
                int n13 = 0;
                if (state == null) {
                    n2 = 1;
                } else {
                    n2 = Math.max(n11, n5);
                    int n14 = Math.min(n12, n6) - n2;
                    if (n14 > n9) {
                        n2 = 1;
                    } else {
                        n2 = n13;
                        if (n14 == n9) {
                            n14 = n11 > n8 ? 1 : 0;
                            n2 = n13;
                            if (n7 == n14) {
                                n2 = 1;
                            }
                        }
                    }
                }
                if (n2 != 0) {
                    n8 = layoutParams.mSpanIndex;
                    n9 = Math.min(n12, n6) - Math.max(n11, n5);
                    state = view;
                }
            }
            n10 += n4;
        }
        return state;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View object, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        ViewGroup.LayoutParams layoutParams = object.getLayoutParams();
        if (!(layoutParams instanceof LayoutParams)) {
            super.onInitializeAccessibilityNodeInfoForItem((View)object, accessibilityNodeInfoCompat);
            return;
        }
        object = (LayoutParams)layoutParams;
        int n2 = this.getSpanGroupIndex(recycler, state, ((RecyclerView.LayoutParams)((Object)object)).getViewLayoutPosition());
        if (this.mOrientation == 0) {
            int n3 = ((LayoutParams)((Object)object)).getSpanIndex();
            int n4 = ((LayoutParams)((Object)object)).getSpanSize();
            boolean bl2 = this.mSpanCount > 1 && ((LayoutParams)((Object)object)).getSpanSize() == this.mSpanCount;
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(n3, n4, n2, 1, bl2, false));
            return;
        }
        int n5 = ((LayoutParams)((Object)object)).getSpanIndex();
        int n6 = ((LayoutParams)((Object)object)).getSpanSize();
        boolean bl3 = this.mSpanCount > 1 && ((LayoutParams)((Object)object)).getSpanSize() == this.mSpanCount;
        accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(n2, 1, n5, n6, bl3, false));
    }

    @Override
    public void onItemsAdded(RecyclerView recyclerView, int n2, int n3) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    @Override
    public void onItemsChanged(RecyclerView recyclerView) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    @Override
    public void onItemsMoved(RecyclerView recyclerView, int n2, int n3, int n4) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    @Override
    public void onItemsRemoved(RecyclerView recyclerView, int n2, int n3) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    @Override
    public void onItemsUpdated(RecyclerView recyclerView, int n2, int n3, Object object) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) {
            this.cachePreLayoutSpanMapping();
        }
        super.onLayoutChildren(recycler, state);
        this.clearPreLayoutSpanMappingCache();
        if (!state.isPreLayout()) {
            this.mPendingSpanCountChange = false;
        }
    }

    @Override
    public int scrollHorizontallyBy(int n2, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.updateMeasurements();
        this.ensureViewSet();
        return super.scrollHorizontallyBy(n2, recycler, state);
    }

    @Override
    public int scrollVerticallyBy(int n2, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.updateMeasurements();
        this.ensureViewSet();
        return super.scrollVerticallyBy(n2, recycler, state);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setMeasuredDimension(Rect rect, int n2, int n3) {
        if (this.mCachedBorders == null) {
            super.setMeasuredDimension(rect, n2, n3);
        }
        int n4 = this.getPaddingLeft() + this.getPaddingRight();
        int n5 = this.getPaddingTop() + this.getPaddingBottom();
        if (this.mOrientation == 1) {
            int n6 = GridLayoutManager.chooseSize(n3, rect.height() + n5, this.getMinimumHeight());
            n3 = GridLayoutManager.chooseSize(n2, this.mCachedBorders[this.mCachedBorders.length - 1] + n4, this.getMinimumWidth());
            n2 = n6;
        } else {
            int n7 = GridLayoutManager.chooseSize(n2, rect.width() + n4, this.getMinimumWidth());
            n2 = GridLayoutManager.chooseSize(n3, this.mCachedBorders[this.mCachedBorders.length - 1] + n5, this.getMinimumHeight());
            n3 = n7;
        }
        this.setMeasuredDimension(n3, n2);
    }

    public void setSpanCount(int n2) {
        if (n2 == this.mSpanCount) {
            return;
        }
        this.mPendingSpanCountChange = true;
        if (n2 < 1) {
            throw new IllegalArgumentException("Span count should be at least 1. Provided " + n2);
        }
        this.mSpanCount = n2;
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup;
    }

    @Override
    public void setStackFromEnd(boolean bl2) {
        if (bl2) {
            throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
        }
        super.setStackFromEnd(false);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return this.mPendingSavedState == null && !this.mPendingSpanCountChange;
    }

    public static final class DefaultSpanSizeLookup
    extends SpanSizeLookup {
        @Override
        public int getSpanIndex(int n2, int n3) {
            return n2 % n3;
        }

        @Override
        public int getSpanSize(int n2) {
            return 1;
        }
    }

    public static class LayoutParams
    extends RecyclerView.LayoutParams {
        public static final int INVALID_SPAN_ID = -1;
        private int mSpanIndex = -1;
        private int mSpanSize = 0;

        public LayoutParams(int n2, int n3) {
            super(n2, n3);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(RecyclerView.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        static /* synthetic */ int access$002(LayoutParams layoutParams, int n2) {
            layoutParams.mSpanIndex = n2;
            return n2;
        }

        static /* synthetic */ int access$102(LayoutParams layoutParams, int n2) {
            layoutParams.mSpanSize = n2;
            return n2;
        }

        public int getSpanIndex() {
            return this.mSpanIndex;
        }

        public int getSpanSize() {
            return this.mSpanSize;
        }
    }

    public static abstract class SpanSizeLookup {
        private boolean mCacheSpanIndices = false;
        final SparseIntArray mSpanIndexCache = new SparseIntArray();

        int findReferenceIndexFromCache(int n2) {
            int n3 = 0;
            int n4 = this.mSpanIndexCache.size() - 1;
            while (n3 <= n4) {
                int n5 = n3 + n4 >>> 1;
                if (this.mSpanIndexCache.keyAt(n5) < n2) {
                    n3 = n5 + 1;
                    continue;
                }
                n4 = n5 - 1;
            }
            n2 = n3 - 1;
            if (n2 >= 0 && n2 < this.mSpanIndexCache.size()) {
                return this.mSpanIndexCache.keyAt(n2);
            }
            return -1;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        int getCachedSpanIndex(int n2, int n3) {
            int n4;
            if (!this.mCacheSpanIndices) {
                return this.getSpanIndex(n2, n3);
            }
            int n5 = n4 = this.mSpanIndexCache.get(n2, -1);
            if (n4 != -1) return n5;
            n3 = this.getSpanIndex(n2, n3);
            this.mSpanIndexCache.put(n2, n3);
            return n3;
        }

        /*
         * Enabled aggressive block sorting
         */
        public int getSpanGroupIndex(int n2, int n3) {
            int n4 = 0;
            int n5 = 0;
            int n6 = this.getSpanSize(n2);
            for (int i2 = 0; i2 < n2; ++i2) {
                int n7;
                int n8 = this.getSpanSize(i2);
                int n9 = n4 + n8;
                if (n9 == n3) {
                    n4 = 0;
                    n7 = n5 + 1;
                } else {
                    n7 = n5;
                    n4 = n9;
                    if (n9 > n3) {
                        n4 = n8;
                        n7 = n5 + 1;
                    }
                }
                n5 = n7;
            }
            n2 = n5;
            if (n4 + n6 <= n3) return n2;
            return n5 + 1;
        }

        /*
         * Enabled aggressive block sorting
         */
        public int getSpanIndex(int n2, int n3) {
            int n4 = this.getSpanSize(n2);
            if (n4 == n3) {
                return 0;
            }
            int n5 = 0;
            int n6 = 0;
            int n7 = n5;
            int n8 = n6;
            if (this.mCacheSpanIndices) {
                n7 = n5;
                n8 = n6;
                if (this.mSpanIndexCache.size() > 0) {
                    int n9 = this.findReferenceIndexFromCache(n2);
                    n7 = n5;
                    n8 = n6;
                    if (n9 >= 0) {
                        n7 = this.mSpanIndexCache.get(n9) + this.getSpanSize(n9);
                        n8 = n9 + 1;
                    }
                }
            }
            while (n8 < n2) {
                n5 = this.getSpanSize(n8);
                n6 = n7 + n5;
                if (n6 == n3) {
                    n7 = 0;
                } else {
                    n7 = n6;
                    if (n6 > n3) {
                        n7 = n5;
                    }
                }
                ++n8;
            }
            n2 = n7;
            if (n7 + n4 <= n3) return n2;
            return 0;
        }

        public abstract int getSpanSize(int var1);

        public void invalidateSpanIndexCache() {
            this.mSpanIndexCache.clear();
        }

        public boolean isSpanIndexCacheEnabled() {
            return this.mCacheSpanIndices;
        }

        public void setSpanIndexCacheEnabled(boolean bl2) {
            this.mCacheSpanIndices = bl2;
        }
    }
}

