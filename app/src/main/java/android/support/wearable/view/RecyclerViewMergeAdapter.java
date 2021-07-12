/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  android.util.SparseIntArray
 *  android.view.ViewGroup
 */
package android.support.wearable.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewMergeAdapter
extends RecyclerView.Adapter {
    private static final long CHILD_ID_RANGE = 10000000000000000L;
    private static final int MAX_ADAPTER_ID = 922;
    private static final String TAG = "MergeAdapter";
    private List<AdapterHolder> mAdapters = new ArrayList<AdapterHolder>();
    private int mItemCount;
    private int mNextAdapterId;
    private int mNextViewTypeId;
    private RecyclerView mRecyclerView;

    private long createItemId(int n2, long l2) {
        return 10000000000000000L * (long)n2 + l2;
    }

    @Nullable
    private AdapterHolder findAdapterHolderForViewType(int n2) {
        if ((n2 = this.getAdapterIndexForViewType(n2)) == -1) {
            return null;
        }
        return this.mAdapters.get(n2);
    }

    @NonNull
    private AdapterHolder getAdapterHolderForPosition(int n2) {
        n2 = this.getAdapterIndexForPosition(n2);
        return this.mAdapters.get(n2);
    }

    private int getAdapterId(long l2) {
        return (int)(l2 / 10000000000000000L);
    }

    private int getAdapterIndexForPosition(int n2) {
        int n3 = this.mAdapters.size();
        for (int i2 = 0; i2 < n3; ++i2) {
            AdapterHolder adapterHolder = this.mAdapters.get(i2);
            int n4 = adapterHolder.itemPositionOffset;
            int n5 = adapterHolder.adapter.getItemCount();
            if (n2 < n4 || n2 >= n4 + n5) continue;
            return i2;
        }
        throw new IllegalStateException("No adapter appears to own position " + n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private int getAdapterIndexForViewType(int n2) {
        int n3 = this.mAdapters.size();
        int n4 = 0;
        while (n4 < n3) {
            AdapterHolder adapterHolder = this.mAdapters.get(n4);
            if (adapterHolder.viewTypes != null && adapterHolder.viewTypes.indexOfKey(n2) >= 0) {
                return n4;
            }
            ++n4;
        }
        return -1;
    }

    private long getChildItemId(long l2) {
        return l2 % 10000000000000000L;
    }

    private void updateItemPositionOffsets(int n2) {
        AdapterHolder adapterHolder;
        int n3 = 0;
        if (n2 > 0) {
            adapterHolder = this.mAdapters.get(n2 - 1);
            n3 = adapterHolder.itemPositionOffset + adapterHolder.adapter.getItemCount();
        }
        int n4 = this.mAdapters.size();
        while (n2 < n4) {
            adapterHolder = this.mAdapters.get(n2);
            adapterHolder.itemPositionOffset = n3;
            adapterHolder.adapterPosition = n2++;
            n3 += adapterHolder.adapter.getItemCount();
        }
        this.mItemCount = n3;
    }

    public void addAdapter(int n2, RecyclerView.Adapter<?> adapter) {
        if (this.mNextAdapterId == 922) {
            throw new IllegalStateException("addAdapter cannot be called more than 922 times");
        }
        if (this.hasStableIds() && !adapter.hasStableIds()) {
            throw new IllegalStateException("All child adapters must have stable IDs when hasStableIds=true");
        }
        AdapterHolder adapterHolder = new AdapterHolder(this.mNextAdapterId, adapter);
        ++this.mNextAdapterId;
        adapterHolder.observer = new ForwardingDataSetObserver(this, adapterHolder);
        adapterHolder.adapterPosition = n2;
        this.mAdapters.add(n2, adapterHolder);
        this.updateItemPositionOffsets(n2);
        adapter.registerAdapterDataObserver(adapterHolder.observer);
        this.notifyItemRangeInserted(adapterHolder.itemPositionOffset, adapterHolder.adapter.getItemCount());
    }

    public void addAdapter(RecyclerView.Adapter adapter) {
        this.addAdapter(this.mAdapters.size(), adapter);
    }

    @NonNull
    public <VH extends RecyclerView.ViewHolder> RecyclerView.Adapter<VH> getAdapterForPosition(int n2) {
        return this.getAdapterHolderForPosition((int)n2).adapter;
    }

    public int getAdapterPosition(@Nullable RecyclerView.Adapter<?> adapter) {
        if (adapter != null) {
            int n2 = this.mAdapters.size();
            for (int i2 = 0; i2 < n2; ++i2) {
                if (this.mAdapters.get((int)i2).adapter != adapter) continue;
                return i2;
            }
        }
        return -1;
    }

    public long getChildItemId(int n2) {
        AdapterHolder adapterHolder = this.getAdapterHolderForPosition(n2);
        int n3 = adapterHolder.itemPositionOffset;
        return adapterHolder.adapter.getItemId(n2 - n3);
    }

    public int getChildPosition(int n2) {
        return n2 - this.getAdapterHolderForPosition((int)n2).itemPositionOffset;
    }

    @Override
    public int getItemCount() {
        return this.mItemCount;
    }

    @Override
    public long getItemId(int n2) {
        if (!this.hasStableIds()) {
            return -1L;
        }
        int n3 = this.getAdapterIndexForPosition(n2);
        return this.createItemId(this.mAdapters.get((int)n3).adapterId, this.getChildItemId(n2));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int getItemViewType(int n2) {
        AdapterHolder adapterHolder = this.getAdapterHolderForPosition(n2);
        int n3 = adapterHolder.itemPositionOffset;
        n2 = adapterHolder.adapter.getItemViewType(n2 - n3);
        if (adapterHolder.viewTypes == null) {
            adapterHolder.viewTypes = new SparseIntArray(1);
        } else {
            n3 = adapterHolder.viewTypes.indexOfValue(n2);
            if (n3 != -1) {
                return adapterHolder.viewTypes.keyAt(n3);
            }
        }
        n3 = this.mNextViewTypeId++;
        adapterHolder.viewTypes.put(n3, n2);
        return n3;
    }

    public int getParentPosition(RecyclerView.Adapter adapter, int n2) {
        for (int i2 = 0; i2 < this.mAdapters.size(); ++i2) {
            if (this.mAdapters.get((int)i2).adapter != adapter) continue;
            return this.mAdapters.get((int)i2).itemPositionOffset + n2;
        }
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void moveAdapter(int n2, RecyclerView.Adapter<?> object) {
        if (n2 < 0) {
            throw new IllegalArgumentException("newPosition cannot be < 0");
        }
        if (this.getAdapterPosition((RecyclerView.Adapter<?>)object) < 0) {
            throw new IllegalStateException("adapter must already be added");
        }
        int n3 = this.getAdapterPosition((RecyclerView.Adapter<?>)object);
        if (n3 != n2) {
            object = this.mAdapters.remove(n3);
            this.notifyItemRangeRemoved(((AdapterHolder)object).itemPositionOffset, ((AdapterHolder)object).adapter.getItemCount());
            this.mAdapters.add(n2, (AdapterHolder)object);
            if (n3 < n2) {
                this.updateItemPositionOffsets(n3);
            } else {
                this.updateItemPositionOffsets(n2);
            }
            this.notifyItemRangeInserted(((AdapterHolder)object).itemPositionOffset, ((AdapterHolder)object).adapter.getItemCount());
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mRecyclerView = recyclerView;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int n2) {
        AdapterHolder adapterHolder = this.getAdapterHolderForPosition(n2);
        int n3 = adapterHolder.itemPositionOffset;
        adapterHolder.adapter.onBindViewHolder(viewHolder, n2 - n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int n2) {
        int n3 = this.mAdapters.size();
        int n4 = 0;
        while (true) {
            int n5;
            if (n4 >= n3) {
                Log.w((String)TAG, (String)("onCreateViewHolder: No child adapters handle viewType: " + n2));
                return null;
            }
            AdapterHolder adapterHolder = this.mAdapters.get(n4);
            if (adapterHolder.viewTypes != null && (n5 = adapterHolder.viewTypes.get(n2, -1)) != -1) {
                return adapterHolder.adapter.onCreateViewHolder(viewGroup, n5);
            }
            ++n4;
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.mRecyclerView = null;
    }

    public boolean onFailedToRecycleView(RecyclerView.ViewHolder viewHolder) {
        AdapterHolder adapterHolder;
        int n2 = viewHolder.getItemViewType();
        if (n2 != -1 && (adapterHolder = this.findAdapterHolderForViewType(n2)) != null) {
            return adapterHolder.adapter.onFailedToRecycleView(viewHolder);
        }
        return true;
    }

    public void onViewAttachedToWindow(RecyclerView.ViewHolder viewHolder) {
        AdapterHolder adapterHolder;
        int n2 = viewHolder.getItemViewType();
        if (n2 != -1 && (adapterHolder = this.findAdapterHolderForViewType(n2)) != null) {
            adapterHolder.adapter.onViewAttachedToWindow(viewHolder);
        }
    }

    public void onViewDetachedFromWindow(RecyclerView.ViewHolder viewHolder) {
        AdapterHolder adapterHolder;
        int n2 = viewHolder.getItemViewType();
        if (n2 != -1 && (adapterHolder = this.findAdapterHolderForViewType(n2)) != null) {
            adapterHolder.adapter.onViewDetachedFromWindow(viewHolder);
        }
    }

    public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
        AdapterHolder adapterHolder;
        int n2 = viewHolder.getItemViewType();
        if (n2 != -1 && (adapterHolder = this.findAdapterHolderForViewType(n2)) != null) {
            adapterHolder.adapter.onViewRecycled(viewHolder);
        }
    }

    public void removeAdapter(int n2) {
        if (n2 < 0 || n2 >= this.mAdapters.size()) {
            Log.w((String)TAG, (String)("removeAdapter(" + n2 + "): position out of range!"));
            return;
        }
        AdapterHolder adapterHolder = this.mAdapters.remove(n2);
        this.updateItemPositionOffsets(n2);
        adapterHolder.adapter.unregisterAdapterDataObserver(adapterHolder.observer);
        if (this.mRecyclerView != null && adapterHolder.viewTypes != null) {
            int n3 = adapterHolder.viewTypes.size();
            for (n2 = 0; n2 < n3; ++n2) {
                int n4 = adapterHolder.viewTypes.keyAt(n2);
                this.mRecyclerView.getRecycledViewPool().setMaxRecycledViews(n4, 0);
            }
        }
        this.notifyItemRangeRemoved(adapterHolder.itemPositionOffset, adapterHolder.adapter.getItemCount());
    }

    public void removeAdapter(RecyclerView.Adapter<?> adapter) {
        int n2 = this.getAdapterPosition(adapter);
        if (n2 >= 0) {
            this.removeAdapter(n2);
        }
    }

    @Override
    public void setHasStableIds(boolean bl2) {
        if (bl2) {
            int n2 = this.mAdapters.size();
            for (int i2 = 0; i2 < n2; ++i2) {
                if (this.mAdapters.get((int)i2).adapter.hasStableIds()) continue;
                throw new IllegalStateException("All child adapters must have stable IDs when hasStableIds=true");
            }
        }
        super.setHasStableIds(bl2);
    }

    private static class AdapterHolder {
        final RecyclerView.Adapter adapter;
        final int adapterId;
        int adapterPosition;
        int itemPositionOffset;
        ForwardingDataSetObserver observer;
        SparseIntArray viewTypes;

        public AdapterHolder(int n2, RecyclerView.Adapter adapter) {
            this.adapter = adapter;
            this.adapterId = n2;
        }
    }

    private static class ForwardingDataSetObserver
    extends RecyclerView.AdapterDataObserver {
        private final AdapterHolder mAdapterHolder;
        private final RecyclerViewMergeAdapter mMergedAdapter;

        public ForwardingDataSetObserver(RecyclerViewMergeAdapter recyclerViewMergeAdapter, AdapterHolder adapterHolder) {
            this.mAdapterHolder = adapterHolder;
            this.mMergedAdapter = recyclerViewMergeAdapter;
        }

        @Override
        public void onChanged() {
            this.mMergedAdapter.updateItemPositionOffsets(0);
            this.mMergedAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int n2, int n3) {
            this.mMergedAdapter.notifyItemRangeChanged(this.mAdapterHolder.itemPositionOffset + n2, n3);
        }

        @Override
        public void onItemRangeInserted(int n2, int n3) {
            this.mMergedAdapter.updateItemPositionOffsets(this.mAdapterHolder.adapterPosition);
            this.mMergedAdapter.notifyItemRangeInserted(this.mAdapterHolder.itemPositionOffset + n2, n3);
        }

        @Override
        public void onItemRangeRemoved(int n2, int n3) {
            this.mMergedAdapter.updateItemPositionOffsets(this.mAdapterHolder.adapterPosition);
            this.mMergedAdapter.notifyItemRangeRemoved(this.mAdapterHolder.itemPositionOffset + n2, n3);
        }
    }
}

