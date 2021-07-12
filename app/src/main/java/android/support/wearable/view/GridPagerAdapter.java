/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.database.DataSetObservable
 *  android.database.DataSetObserver
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Point
 *  android.graphics.drawable.Drawable
 *  android.os.Parcelable
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.wearable.view;

import android.annotation.TargetApi;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

@TargetApi(value=20)
public abstract class GridPagerAdapter {
    public static final Drawable BACKGROUND_NONE = new NoOpDrawable();
    public static final int OPTION_DISABLE_PARALLAX = 1;
    public static final int PAGE_DEFAULT_OPTIONS = 0;
    public static final Point POSITION_NONE = new Point(-1, -1);
    public static final Point POSITION_UNCHANGED = new Point(-2, -2);
    private DataSetObservable mObservable = new DataSetObservable();
    private OnBackgroundChangeListener mOnBackgroundChangeListener;

    protected void applyItemPosition(Object object, Point point) {
    }

    public abstract void destroyItem(ViewGroup var1, int var2, int var3, Object var4);

    public void finishUpdate(ViewGroup viewGroup) {
    }

    public Drawable getBackgroundForPage(int n2, int n3) {
        return BACKGROUND_NONE;
    }

    public Drawable getBackgroundForRow(int n2) {
        return BACKGROUND_NONE;
    }

    public abstract int getColumnCount(int var1);

    public int getCurrentColumnForRow(int n2, int n3) {
        return 0;
    }

    public Point getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public int getOptionsForPage(int n2, int n3) {
        return 0;
    }

    public abstract int getRowCount();

    public abstract Object instantiateItem(ViewGroup var1, int var2, int var3);

    public abstract boolean isViewFromObject(View var1, Object var2);

    public void notifyDataSetChanged() {
        this.mObservable.notifyChanged();
    }

    public void notifyPageBackgroundChanged(int n2, int n3) {
        if (this.mOnBackgroundChangeListener != null) {
            this.mOnBackgroundChangeListener.onPageBackgroundChanged(n2, n3);
        }
    }

    public void notifyRowBackgroundChanged(int n2) {
        if (this.mOnBackgroundChangeListener != null) {
            this.mOnBackgroundChangeListener.onRowBackgroundChanged(n2);
        }
    }

    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        this.mObservable.registerObserver((Object)dataSetObserver);
    }

    public void restoreState(Parcelable parcelable, ClassLoader classLoader) {
    }

    public Parcelable saveState() {
        return null;
    }

    public void setCurrentColumnForRow(int n2, int n3) {
    }

    void setOnBackgroundChangeListener(OnBackgroundChangeListener onBackgroundChangeListener) {
        this.mOnBackgroundChangeListener = onBackgroundChangeListener;
    }

    public void startUpdate(ViewGroup viewGroup) {
    }

    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        this.mObservable.unregisterObserver((Object)dataSetObserver);
    }

    private static final class NoOpDrawable
    extends Drawable {
        private NoOpDrawable() {
        }

        public void draw(Canvas canvas) {
        }

        public int getOpacity() {
            return 0;
        }

        public void setAlpha(int n2) {
        }

        public void setColorFilter(ColorFilter colorFilter) {
        }
    }

    static interface OnBackgroundChangeListener {
        public void onPageBackgroundChanged(int var1, int var2);

        public void onRowBackgroundChanged(int var1);
    }
}

