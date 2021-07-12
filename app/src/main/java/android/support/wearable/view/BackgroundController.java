/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.graphics.Point
 *  android.graphics.drawable.Drawable
 *  android.view.View
 */
package android.support.wearable.view;

import android.annotation.TargetApi;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v4.util.LruCache;
import android.support.wearable.view.CrossfadeDrawable;
import android.support.wearable.view.Func;
import android.support.wearable.view.GridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.ViewportDrawable;
import android.view.View;

@TargetApi(value=20)
class BackgroundController
implements GridViewPager.OnPageChangeListener,
GridViewPager.OnAdapterChangeListener,
GridPagerAdapter.OnBackgroundChangeListener {
    private GridPagerAdapter mAdapter;
    private final CrossfadeDrawable mBackground;
    private final ViewportDrawable mBaseLayer;
    private final Point mBaseSourcePage;
    private float mBaseXPos;
    private int mBaseXSteps;
    private float mBaseYPos;
    private int mBaseYSteps;
    private final ViewportDrawable mCrossfadeLayer;
    private float mCrossfadeXPos;
    private float mCrossfadeYPos;
    private final Point mCurrentPage;
    private Direction mDirection = Direction.NONE;
    private final Point mFadeSourcePage;
    private int mFadeXSteps;
    private int mFadeYSteps;
    private final Point mLastPageScrolled;
    private final Point mLastSelectedPage;
    private final LruCache<Integer, Drawable> mPageBackgrounds;
    private final LruCache<Integer, Drawable> mRowBackgrounds;
    private float mScrollRelativeX;
    private float mScrollRelativeY;
    private boolean mUsingCrossfadeLayer;

    public BackgroundController() {
        this.mCurrentPage = new Point();
        this.mLastSelectedPage = new Point();
        this.mRowBackgrounds = new LruCache<Integer, Drawable>(3){

            @Override
            protected Drawable create(Integer n2) {
                return BackgroundController.this.mAdapter.getBackgroundForRow(n2).mutate();
            }
        };
        this.mPageBackgrounds = new LruCache<Integer, Drawable>(5){

            @Override
            protected Drawable create(Integer n2) {
                int n3 = BackgroundController.unpackX(n2);
                int n4 = BackgroundController.unpackY(n2);
                return BackgroundController.this.mAdapter.getBackgroundForPage(n4, n3).mutate();
            }
        };
        this.mBaseLayer = new ViewportDrawable();
        this.mCrossfadeLayer = new ViewportDrawable();
        this.mBackground = new CrossfadeDrawable();
        this.mLastPageScrolled = new Point();
        this.mFadeSourcePage = new Point();
        this.mBaseSourcePage = new Point();
        this.mBackground.setFilterBitmap(true);
        this.mCrossfadeLayer.setFilterBitmap(true);
        this.mBaseLayer.setFilterBitmap(true);
    }

    private static int pack(int n2, int n3) {
        return n3 << 16 | 0xFFFF & n2;
    }

    private static int pack(Point point) {
        return BackgroundController.pack(point.x, point.y);
    }

    private void reset() {
        this.mDirection = Direction.NONE;
        this.mPageBackgrounds.evictAll();
        this.mRowBackgrounds.evictAll();
        this.mCrossfadeLayer.setDrawable(null);
        this.mBaseLayer.setDrawable(null);
    }

    private static int unpackX(int n2) {
        return 0xFFFF & n2;
    }

    private static int unpackY(int n2) {
        return n2 >>> 16;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateBackgrounds(Point point, Point point2, Direction direction, float f2, float f3) {
        if (this.mAdapter != null && this.mAdapter.getRowCount() > 0) {
            Drawable drawable2 = this.updateBaseLayer(point, f2, f3);
            boolean bl2 = (float)point.x + f2 < 0.0f || (float)point.y + f3 < 0.0f || (float)point2.x + f2 > (float)(this.mAdapter.getColumnCount(point.y) - 1) || (float)point2.y + f3 > (float)(this.mAdapter.getRowCount() - 1);
            if (this.mDirection != Direction.NONE && !bl2) {
                this.updateFadingLayer(point, point2, direction, f2, f3, drawable2);
                return;
            }
            this.mUsingCrossfadeLayer = false;
            this.mCrossfadeLayer.setDrawable(null);
            this.mBackground.setProgress(0.0f);
            return;
        }
        this.mUsingCrossfadeLayer = false;
        this.mBaseLayer.setDrawable(null);
        this.mCrossfadeLayer.setDrawable(null);
    }

    /*
     * Enabled aggressive block sorting
     */
    private Drawable updateBaseLayer(Point point, float f2, float f3) {
        Drawable drawable2 = this.mPageBackgrounds.get(BackgroundController.pack(point));
        this.mBaseSourcePage.set(point.x, point.y);
        if (drawable2 == GridPagerAdapter.BACKGROUND_NONE) {
            drawable2 = this.mRowBackgrounds.get(point.y);
            this.mBaseXSteps = this.mAdapter.getColumnCount(point.y) + 2;
            this.mBaseXPos = point.x + 1;
            point = drawable2;
        } else {
            this.mBaseXSteps = 3;
            this.mBaseXPos = 1.0f;
            point = drawable2;
        }
        this.mBaseYSteps = 3;
        this.mBaseYPos = 1.0f;
        this.mBaseLayer.setDrawable((Drawable)point);
        this.mBaseLayer.setStops(this.mBaseXSteps, this.mBaseYSteps);
        this.mBaseLayer.setPosition(this.mBaseXPos + f2, this.mBaseYPos + f3);
        this.mBackground.setBase(this.mBaseLayer);
        return point;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateFadingLayer(Point point, Point point2, Direction direction, float f2, float f3, Drawable drawable2) {
        int n2 = point2.y;
        int n3 = direction == Direction.DOWN ? 1 : 0;
        int n4 = n2 + n3;
        n2 = point2.x;
        n3 = direction == Direction.RIGHT ? 1 : 0;
        n3 = n2 + n3;
        if (n4 != this.mCurrentPage.y) {
            n3 = this.mAdapter.getCurrentColumnForRow(n4, point.x);
        }
        Drawable drawable3 = this.mPageBackgrounds.get(BackgroundController.pack(n3, n4));
        this.mFadeSourcePage.set(n3, n4);
        n2 = 0;
        point2 = drawable3;
        if (drawable3 == GridPagerAdapter.BACKGROUND_NONE) {
            point2 = this.mRowBackgrounds.get(n4);
            n2 = 1;
        }
        if (drawable2 == point2) {
            this.mUsingCrossfadeLayer = false;
            this.mCrossfadeLayer.setDrawable(null);
            this.mBackground.setFading(null);
            this.mBackground.setProgress(0.0f);
            return;
        }
        if (n2 != 0) {
            n2 = Func.clamp(n4, 0, this.mAdapter.getRowCount() - 1);
            this.mFadeXSteps = this.mAdapter.getColumnCount(n2) + 2;
            this.mCrossfadeXPos = direction.isHorizontal() ? (float)(point.x + 1) : (float)(n3 + 1);
        } else {
            this.mFadeXSteps = 3;
            this.mCrossfadeXPos = 1 - direction.x;
        }
        this.mFadeYSteps = 3;
        this.mCrossfadeYPos = 1 - direction.y;
        this.mUsingCrossfadeLayer = true;
        this.mCrossfadeLayer.setDrawable((Drawable)point2);
        this.mCrossfadeLayer.setStops(this.mFadeXSteps, this.mFadeYSteps);
        this.mCrossfadeLayer.setPosition(this.mCrossfadeXPos + f2, this.mCrossfadeYPos + f3);
        this.mBackground.setFading(this.mCrossfadeLayer);
    }

    public void attachTo(View view) {
        view.setBackground((Drawable)this.mBackground);
    }

    public Drawable getBackground() {
        return this.mBackground;
    }

    @Override
    public void onAdapterChanged(GridPagerAdapter gridPagerAdapter, GridPagerAdapter gridPagerAdapter2) {
        this.reset();
        this.mLastSelectedPage.set(0, 0);
        this.mCurrentPage.set(0, 0);
        this.mAdapter = gridPagerAdapter2;
    }

    @Override
    public void onDataSetChanged() {
        this.reset();
    }

    @Override
    public void onPageBackgroundChanged(int n2, int n3) {
        this.mPageBackgrounds.remove(BackgroundController.pack(n3, n2));
        if (this.mAdapter != null && this.mAdapter.getRowCount() > 0) {
            this.updateBackgrounds(this.mCurrentPage, this.mCurrentPage, Direction.NONE, this.mScrollRelativeX, this.mScrollRelativeY);
        }
    }

    @Override
    public void onPageScrollStateChanged(int n2) {
        if (n2 == 0) {
            this.mDirection = Direction.NONE;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onPageScrolled(int n2, int n3, float f2, float f3, int n4, int n5) {
        float f4;
        if (this.mDirection == Direction.NONE || !this.mCurrentPage.equals((Object)this.mLastSelectedPage) || !this.mLastPageScrolled.equals(n3, n2)) {
            this.mLastPageScrolled.set(n3, n2);
            this.mCurrentPage.set(this.mLastSelectedPage.x, this.mLastSelectedPage.y);
            float f5 = 0.0f;
            f4 = (float)Func.clamp(n2 - this.mCurrentPage.y, -1, 0) + f2;
            f2 = f5;
            if (f4 == 0.0f) {
                f2 = (float)Func.clamp(n3 - this.mCurrentPage.x, -1, 0) + f3;
            }
            this.mDirection = Direction.fromOffset(f2, f4);
            this.updateBackgrounds(this.mCurrentPage, this.mLastPageScrolled, this.mDirection, f2, f4);
            f3 = f4;
        } else if (this.mDirection.isVertical()) {
            f3 = 0.0f;
            f4 = (float)Func.clamp(n2 - this.mCurrentPage.y, -1, 0) + f2;
            f2 = f3;
            f3 = f4;
        } else {
            f2 = (float)Func.clamp(n3 - this.mCurrentPage.x, -1, 0) + f3;
            f3 = 0.0f;
        }
        this.mScrollRelativeX = f2;
        this.mScrollRelativeY = f3;
        this.mBaseLayer.setPosition(this.mBaseXPos + f2, this.mBaseYPos + f3);
        if (this.mUsingCrossfadeLayer) {
            f4 = this.mDirection.isVertical() ? Math.abs(f3) : Math.abs(f2);
            this.mBackground.setProgress(f4);
            this.mCrossfadeLayer.setPosition(this.mCrossfadeXPos + f2, this.mCrossfadeYPos + f3);
        }
    }

    @Override
    public void onPageSelected(int n2, int n3) {
        this.mLastSelectedPage.set(n3, n2);
    }

    @Override
    public void onRowBackgroundChanged(int n2) {
        this.mRowBackgrounds.remove(n2);
        if (this.mAdapter != null && this.mAdapter.getRowCount() > 0) {
            this.updateBackgrounds(this.mCurrentPage, this.mCurrentPage, Direction.NONE, this.mScrollRelativeX, this.mScrollRelativeY);
        }
    }

    private static enum Direction {
        LEFT(-1, 0),
        UP(0, -1),
        RIGHT(1, 0),
        DOWN(0, 1),
        NONE(0, 0);

        private final int x;
        private final int y;

        private Direction(int n3, int n4) {
            this.x = n3;
            this.y = n4;
        }

        static Direction fromOffset(float f2, float f3) {
            if (f3 != 0.0f) {
                if (f3 > 0.0f) {
                    return DOWN;
                }
                return UP;
            }
            if (f2 != 0.0f) {
                if (f2 > 0.0f) {
                    return RIGHT;
                }
                return LEFT;
            }
            return NONE;
        }

        boolean isHorizontal() {
            return this.x != 0;
        }

        boolean isVertical() {
            return this.y != 0;
        }
    }
}

