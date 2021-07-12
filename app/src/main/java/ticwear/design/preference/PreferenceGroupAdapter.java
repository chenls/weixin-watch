/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 */
package ticwear.design.preference;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ticwear.design.preference.Preference;
import ticwear.design.preference.PreferenceGroup;

public class PreferenceGroupAdapter
extends RecyclerView.Adapter<Preference.ViewHolder>
implements Preference.OnPreferenceChangeInternalListener {
    private static final String TAG = "PreferenceGroupAdapter";
    private Handler mHandler;
    private boolean mHasReturnedViewTypeCount = false;
    private volatile boolean mIsSyncing = false;
    private OnPreferenceItemClickListener mOnPreferenceItemClickListener;
    private PreferenceGroup mPreferenceGroup;
    private ArrayList<PreferenceLayout> mPreferenceLayouts;
    private List<Preference> mPreferenceList;
    private Runnable mSyncRunnable;
    private PreferenceLayout mTempPreferenceLayout = new PreferenceLayout();

    public PreferenceGroupAdapter(PreferenceGroup preferenceGroup) {
        this.mHandler = new Handler();
        this.mSyncRunnable = new Runnable(){

            @Override
            public void run() {
                PreferenceGroupAdapter.this.syncMyPreferences();
            }
        };
        this.mPreferenceGroup = preferenceGroup;
        this.mPreferenceGroup.setOnPreferenceChangeInternalListener(this);
        this.mPreferenceList = new ArrayList<Preference>();
        this.mPreferenceLayouts = new ArrayList();
        this.syncMyPreferences();
        this.setHasStableIds(true);
    }

    private void addPreferenceClassName(Preference comparable) {
        int n2 = Collections.binarySearch(this.mPreferenceLayouts, comparable = this.createPreferenceLayout((Preference)comparable, null));
        if (n2 < 0) {
            this.mPreferenceLayouts.add(n2 * -1 - 1, (PreferenceLayout)comparable);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private PreferenceLayout createPreferenceLayout(Preference preference, PreferenceLayout preferenceLayout) {
        if (preferenceLayout == null) {
            preferenceLayout = new PreferenceLayout();
        }
        PreferenceLayout.access$202(preferenceLayout, preference.getClass().getName());
        PreferenceLayout.access$302(preferenceLayout, preference.getLayoutResource());
        PreferenceLayout.access$402(preferenceLayout, preference.getWidgetLayoutResource());
        PreferenceLayout.access$502(preferenceLayout, preference.mViewHolderCreator);
        return preferenceLayout;
    }

    private void flattenPreferenceGroup(List<Preference> list, PreferenceGroup preferenceGroup) {
        preferenceGroup.sortPreferences();
        int n2 = preferenceGroup.getPreferenceCount();
        for (int i2 = 0; i2 < n2; ++i2) {
            PreferenceGroup preferenceGroup2;
            Preference preference = preferenceGroup.getPreference(i2);
            list.add(preference);
            if (!this.mHasReturnedViewTypeCount) {
                this.addPreferenceClassName(preference);
            }
            if (preference instanceof PreferenceGroup && (preferenceGroup2 = (PreferenceGroup)preference).isOnSameScreenAsChildren()) {
                this.flattenPreferenceGroup(list, preferenceGroup2);
            }
            preference.setOnPreferenceChangeInternalListener(this);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void syncMyPreferences() {
        synchronized (this) {
            if (this.mIsSyncing) {
                return;
            }
            this.mIsSyncing = true;
        }
        ArrayList<Preference> arrayList = new ArrayList<Preference>(this.mPreferenceList.size());
        this.flattenPreferenceGroup(arrayList, this.mPreferenceGroup);
        this.mPreferenceList = arrayList;
        this.notifyDataSetChanged();
        synchronized (this) {
            this.mIsSyncing = false;
            this.notifyAll();
            return;
        }
    }

    public Preference getItem(int n2) {
        if (n2 < 0 || n2 >= this.getItemCount()) {
            return null;
        }
        return this.mPreferenceList.get(n2);
    }

    @Override
    public int getItemCount() {
        return this.mPreferenceList.size();
    }

    @Override
    public long getItemId(int n2) {
        if (n2 < 0 || n2 >= this.getItemCount()) {
            return Long.MIN_VALUE;
        }
        return this.getItem(n2).getId();
    }

    @Override
    public int getItemViewType(int n2) {
        int n3;
        if (!this.mHasReturnedViewTypeCount) {
            this.mHasReturnedViewTypeCount = true;
        }
        this.mTempPreferenceLayout = this.createPreferenceLayout(this.getItem(n2), this.mTempPreferenceLayout);
        n2 = n3 = Collections.binarySearch(this.mPreferenceLayouts, this.mTempPreferenceLayout);
        if (n3 < 0) {
            n2 = -1;
        }
        return n2;
    }

    @Override
    public void onBindViewHolder(Preference.ViewHolder viewHolder, int n2) {
        final Preference preference = this.getItem(n2);
        viewHolder.bindPreference(preference);
        if (preference.getShouldDisableView()) {
            viewHolder.setEnabled(preference.isEnabled());
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (PreferenceGroupAdapter.this.mOnPreferenceItemClickListener != null) {
                    PreferenceGroupAdapter.this.mOnPreferenceItemClickListener.onPreferenceItemClick(preference);
                }
            }
        });
    }

    @Override
    public Preference.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int n2) {
        if (n2 < 0 || n2 >= this.mPreferenceLayouts.size()) {
            throw new RuntimeException("viewType " + n2 + " should contains in synced layouts.");
        }
        PreferenceLayout preferenceLayout = this.mPreferenceLayouts.get(n2);
        if (preferenceLayout.viewHolderCreator != null) {
            return preferenceLayout.viewHolderCreator.create(viewGroup, preferenceLayout.resId, preferenceLayout.widgetResId);
        }
        return new Preference.ViewHolder(viewGroup, preferenceLayout.resId, preferenceLayout.widgetResId);
    }

    @Override
    public void onPreferenceChange(Preference preference) {
        this.notifyDataSetChanged();
    }

    @Override
    public void onPreferenceHierarchyChange(Preference preference) {
        this.mHandler.removeCallbacks(this.mSyncRunnable);
        this.mHandler.post(this.mSyncRunnable);
    }

    public void setOnPreferenceItemClickListener(OnPreferenceItemClickListener onPreferenceItemClickListener) {
        this.mOnPreferenceItemClickListener = onPreferenceItemClickListener;
    }

    public static interface OnPreferenceItemClickListener {
        public void onPreferenceItemClick(@NonNull Preference var1);
    }

    private static class PreferenceLayout
    implements Comparable<PreferenceLayout> {
        private String name;
        private int resId;
        private Preference.ViewHolderCreator viewHolderCreator;
        private int widgetResId;

        private PreferenceLayout() {
        }

        static /* synthetic */ String access$202(PreferenceLayout preferenceLayout, String string2) {
            preferenceLayout.name = string2;
            return string2;
        }

        static /* synthetic */ int access$302(PreferenceLayout preferenceLayout, int n2) {
            preferenceLayout.resId = n2;
            return n2;
        }

        static /* synthetic */ int access$402(PreferenceLayout preferenceLayout, int n2) {
            preferenceLayout.widgetResId = n2;
            return n2;
        }

        static /* synthetic */ Preference.ViewHolderCreator access$502(PreferenceLayout preferenceLayout, Preference.ViewHolderCreator viewHolderCreator) {
            preferenceLayout.viewHolderCreator = viewHolderCreator;
            return viewHolderCreator;
        }

        @Override
        public int compareTo(@NonNull PreferenceLayout preferenceLayout) {
            block3: {
                block4: {
                    int n2;
                    block2: {
                        int n3;
                        n2 = n3 = this.name.compareTo(preferenceLayout.name);
                        if (n3 != 0) break block2;
                        if (this.resId != preferenceLayout.resId) break block3;
                        if (this.widgetResId != preferenceLayout.widgetResId) break block4;
                        n2 = 0;
                    }
                    return n2;
                }
                return this.widgetResId - preferenceLayout.widgetResId;
            }
            return this.resId - preferenceLayout.resId;
        }
    }
}

