/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.graphics.Point
 *  android.graphics.drawable.Drawable
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.wearable.view;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.wearable.view.GridPageOptions;
import android.support.wearable.view.GridPagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.HashMap;
import java.util.Map;

@TargetApi(value=20)
public abstract class FragmentGridPagerAdapter
extends GridPagerAdapter {
    private static final int MAX_ROWS = 65535;
    private static final GridPageOptions.BackgroundListener NOOP_BACKGROUND_OBSERVER = new GridPageOptions.BackgroundListener(){

        @Override
        public void notifyBackgroundChanged() {
        }
    };
    private FragmentTransaction mCurTransaction;
    private final FragmentManager mFragmentManager;
    private final Map<String, Point> mFragmentPositions;
    private final Map<Point, String> mFragmentTags;

    public FragmentGridPagerAdapter(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
        this.mFragmentPositions = new HashMap<String, Point>();
        this.mFragmentTags = new HashMap<Point, String>();
    }

    private static String makeFragmentName(int n2, long l2) {
        return "android:switcher:" + n2 + ":" + l2;
    }

    @Override
    protected void applyItemPosition(Object object, Point point) {
        if (point == GridPagerAdapter.POSITION_UNCHANGED) {
            return;
        }
        if ((object = (Fragment)object).getTag().equals(this.mFragmentTags.get(point))) {
            this.mFragmentTags.remove(point);
        }
        if (point == GridPagerAdapter.POSITION_NONE) {
            this.mFragmentPositions.remove(object.getTag());
            return;
        }
        this.mFragmentPositions.put(object.getTag(), point);
        this.mFragmentTags.put(point, object.getTag());
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int n2, int n3, Object object) {
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        if ((viewGroup = (Fragment)object) instanceof GridPageOptions) {
            ((GridPageOptions)viewGroup).setBackgroundListener(NOOP_BACKGROUND_OBSERVER);
        }
        this.removeFragment((Fragment)viewGroup, this.mCurTransaction);
    }

    public Fragment findExistingFragment(int n2, int n3) {
        String string2 = this.mFragmentTags.get(new Point(n3, n2));
        if (string2 != null) {
            return this.mFragmentManager.findFragmentByTag(string2);
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void finishUpdate(ViewGroup viewGroup) {
        if (this.mFragmentManager.isDestroyed()) {
            this.mCurTransaction = null;
            return;
        } else {
            if (this.mCurTransaction == null) return;
            this.mCurTransaction.commitAllowingStateLoss();
            this.mCurTransaction = null;
            this.mFragmentManager.executePendingTransactions();
            return;
        }
    }

    @Override
    public Drawable getBackgroundForPage(int n2, int n3) {
        return this.getFragmentBackground(n2, n3);
    }

    public abstract Fragment getFragment(int var1, int var2);

    public final Drawable getFragmentBackground(int n2, int n3) {
        String string2 = this.mFragmentTags.get(new Point(n3, n2));
        Fragment fragment = this.mFragmentManager.findFragmentByTag(string2);
        string2 = BACKGROUND_NONE;
        if (fragment instanceof GridPageOptions) {
            string2 = ((GridPageOptions)fragment).getBackground();
        }
        return string2;
    }

    public long getFragmentId(int n2, int n3) {
        return 65535 * n3 + n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Fragment instantiateItem(ViewGroup viewGroup, int n2, int n3) {
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        long l2 = this.getFragmentId(n2, n3);
        String string2 = FragmentGridPagerAdapter.makeFragmentName(viewGroup.getId(), l2);
        Fragment fragment = this.mFragmentManager.findFragmentByTag(string2);
        if (fragment == null) {
            fragment = this.getFragment(n2, n3);
            this.mCurTransaction.add(viewGroup.getId(), fragment, string2);
            viewGroup = fragment;
        } else {
            this.restoreFragment(fragment, this.mCurTransaction);
            viewGroup = fragment;
        }
        fragment = new Point(n3, n2);
        this.mFragmentTags.put((Point)fragment, string2);
        this.mFragmentPositions.put(string2, (Point)fragment);
        if (viewGroup instanceof GridPageOptions) {
            ((GridPageOptions)viewGroup).setBackgroundListener(new BackgroundObserver(string2));
        }
        return viewGroup;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((Fragment)object).getView();
    }

    protected void removeFragment(Fragment fragment, FragmentTransaction fragmentTransaction) {
        fragmentTransaction.remove(fragment);
    }

    protected void restoreFragment(Fragment fragment, FragmentTransaction fragmentTransaction) {
    }

    private class BackgroundObserver
    implements GridPageOptions.BackgroundListener {
        private final String mTag;

        private BackgroundObserver(String string2) {
            this.mTag = string2;
        }

        @Override
        public void notifyBackgroundChanged() {
            Point point = (Point)FragmentGridPagerAdapter.this.mFragmentPositions.get(this.mTag);
            if (point != null) {
                FragmentGridPagerAdapter.this.notifyPageBackgroundChanged(point.y, point.x);
            }
        }
    }
}

