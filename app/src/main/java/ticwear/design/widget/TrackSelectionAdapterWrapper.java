/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 *  android.util.LongSparseArray
 *  android.util.SparseBooleanArray
 *  android.view.ActionMode
 *  android.view.ActionMode$Callback
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnLongClickListener
 *  android.view.ViewGroup
 *  android.widget.AbsListView$MultiChoiceModeListener
 *  android.widget.Checkable
 */
package ticwear.design.widget;

import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.LongSparseArray;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Checkable;

public class TrackSelectionAdapterWrapper<VH extends RecyclerView.ViewHolder>
extends RecyclerView.Adapter<VH> {
    final RecyclerView.Adapter<VH> mAdapter;
    RecyclerView mAttachedRecyclerView;
    SparseBooleanArray mCheckStates;
    LongSparseArray<Integer> mCheckedIdStates;
    int mCheckedItemCount;
    ActionMode mChoiceActionMode;
    int mChoiceMode = 0;
    MultiChoiceModeWrapper mMultiChoiceModeCallback;
    OnItemClickListener mOnItemClickListener;
    OnItemLongClickListener mOnItemLongClickListener;
    OnItemSelectedListener mOnItemSelectedListener;

    public TrackSelectionAdapterWrapper(RecyclerView.Adapter<VH> adapter) {
        this.mAdapter = adapter;
        this.setHasStableIds(this.mAdapter.hasStableIds());
    }

    private boolean canNotifyChange() {
        return this.mAttachedRecyclerView != null && !this.mAttachedRecyclerView.isComputingLayout();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private View findItemView(int n2) {
        if (this.mAttachedRecyclerView == null) return null;
        if (Looper.getMainLooper() != Looper.myLooper()) {
            return null;
        }
        int n3 = 0;
        while (n3 < this.mAttachedRecyclerView.getChildCount()) {
            View view;
            View view2 = view = this.mAttachedRecyclerView.getChildAt(n3);
            if (this.mAttachedRecyclerView.getChildAdapterPosition(view) == n2) return view2;
            ++n3;
        }
        return null;
    }

    private void fireOnSelected(View view) {
        if (this.mOnItemSelectedListener == null) {
            return;
        }
        int n2 = this.getCheckedItemPosition();
        if (n2 >= 0) {
            this.mOnItemSelectedListener.onItemSelected(this, view, n2, this.getAdapter().getItemId(n2));
            return;
        }
        this.mOnItemSelectedListener.onNothingSelected(this);
    }

    private boolean startSelectionModeIfNeeded(View view) {
        if (this.mChoiceMode == 3 && this.mChoiceActionMode != null) {
            if (this.mMultiChoiceModeCallback == null || !this.mMultiChoiceModeCallback.hasWrappedCallback()) {
                throw new IllegalStateException("TrackSelectionAdapter: attempted to start selection mode for AbsListView.CHOICE_MODE_MULTIPLE_MODAL but no choice mode callback was supplied. Call setMultiChoiceModeListener to set a callback.");
            }
            this.mChoiceActionMode = view.startActionMode((ActionMode.Callback)this.mMultiChoiceModeCallback);
            return true;
        }
        return false;
    }

    private void updateCheckState(View view, int n2) {
        if (view instanceof Checkable) {
            ((Checkable)view).setChecked(this.mCheckStates.get(n2));
            return;
        }
        view.setActivated(this.mCheckStates.get(n2));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateCheckStateIfPossible(View view, int n2) {
        View view2 = view;
        if (view == null) {
            view2 = this.findItemView(n2);
        }
        if (view2 != null) {
            this.updateCheckState(view2, n2);
            return;
        } else {
            if (!this.canNotifyChange()) return;
            this.notifyItemChanged(n2);
            return;
        }
    }

    public void clearChoices() {
        if (this.mCheckStates != null) {
            this.mCheckStates.clear();
        }
        if (this.mCheckedIdStates != null) {
            this.mCheckedIdStates.clear();
        }
        this.mCheckedItemCount = 0;
    }

    public RecyclerView.Adapter<VH> getAdapter() {
        return this.mAdapter;
    }

    public int getCheckedItemCount() {
        return this.mCheckedItemCount;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public long[] getCheckedItemIds() {
        if (this.mChoiceMode == 0) return new long[0];
        if (this.mCheckedIdStates == null) {
            return new long[0];
        }
        LongSparseArray<Integer> longSparseArray = this.mCheckedIdStates;
        int n2 = longSparseArray.size();
        long[] lArray = new long[n2];
        int n3 = 0;
        while (true) {
            long[] lArray2 = lArray;
            if (n3 >= n2) return lArray2;
            lArray[n3] = longSparseArray.keyAt(n3);
            ++n3;
        }
    }

    public int getCheckedItemPosition() {
        if (this.mChoiceMode == 1 && this.mCheckStates != null && this.mCheckStates.size() == 1) {
            return this.mCheckStates.keyAt(0);
        }
        return -1;
    }

    public SparseBooleanArray getCheckedItemPositions() {
        if (this.mChoiceMode != 0) {
            return this.mCheckStates;
        }
        return null;
    }

    public int getChoiceMode() {
        return this.mChoiceMode;
    }

    @Override
    public int getItemCount() {
        return this.mAdapter.getItemCount();
    }

    @Override
    public long getItemId(int n2) {
        return this.mAdapter.getItemId(n2);
    }

    @Override
    public int getItemViewType(int n2) {
        return this.mAdapter.getItemViewType(n2);
    }

    public final OnItemClickListener getOnItemClickListener() {
        return this.mOnItemClickListener;
    }

    public final OnItemLongClickListener getOnItemLongClickListener() {
        return this.mOnItemLongClickListener;
    }

    public final OnItemSelectedListener getOnItemSelectedListener() {
        return this.mOnItemSelectedListener;
    }

    public boolean isItemChecked(int n2) {
        if (this.mChoiceMode != 0 && this.mCheckStates != null) {
            return this.mCheckStates.get(n2);
        }
        return false;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (this.mAttachedRecyclerView == recyclerView) {
            return;
        }
        if (this.mAttachedRecyclerView != null) {
            throw new RuntimeException("Can't attach TrackSelectionAdapterWrapper to multiple RecyclerView.");
        }
        this.mAttachedRecyclerView = recyclerView;
        if (this.mChoiceMode == 3) {
            recyclerView.setLongClickable(true);
        }
        this.startSelectionModeIfNeeded((View)recyclerView);
    }

    @Override
    public void onBindViewHolder(VH VH, int n2) {
        this.mAdapter.onBindViewHolder(VH, n2);
        if (this.mChoiceMode != 0) {
            this.updateCheckState(((RecyclerView.ViewHolder)VH).itemView, n2);
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup object, int n2) {
        object = this.mAdapter.onCreateViewHolder((ViewGroup)object, n2);
        View view = object.itemView;
        view.setOnClickListener(new View.OnClickListener((RecyclerView.ViewHolder)object){
            final /* synthetic */ RecyclerView.ViewHolder val$vh;
            {
                this.val$vh = viewHolder;
            }

            public void onClick(View view) {
                TrackSelectionAdapterWrapper.this.performItemClick(view, this.val$vh.getLayoutPosition(), this.val$vh.getItemId());
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener((RecyclerView.ViewHolder)object){
            final /* synthetic */ RecyclerView.ViewHolder val$vh;
            {
                this.val$vh = viewHolder;
            }

            public boolean onLongClick(View view) {
                return TrackSelectionAdapterWrapper.this.performLongPress(view, this.val$vh.getLayoutPosition(), this.val$vh.getItemId());
            }
        });
        return (VH)object;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        this.mAttachedRecyclerView = null;
        if (this.mChoiceActionMode != null) {
            this.mChoiceActionMode.finish();
            this.mChoiceActionMode = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean performItemClick(View view, int n2, long l2) {
        boolean bl2;
        boolean bl3 = false;
        int n3 = 1;
        int n4 = 1;
        int n5 = n3;
        if (this.mChoiceMode != 0) {
            bl2 = true;
            if (this.mChoiceMode == 2 || this.mChoiceMode == 3 && this.mChoiceActionMode != null) {
                bl3 = !this.mCheckStates.get(n2, false);
                this.mCheckStates.put(n2, bl3);
                if (this.mCheckedIdStates != null && this.hasStableIds()) {
                    if (bl3) {
                        this.mCheckedIdStates.put(this.getItemId(n2), (Object)n2);
                    } else {
                        this.mCheckedIdStates.delete(this.getItemId(n2));
                    }
                }
                this.mCheckedItemCount = bl3 ? ++this.mCheckedItemCount : --this.mCheckedItemCount;
                n5 = n4;
                if (this.mChoiceActionMode != null) {
                    this.mMultiChoiceModeCallback.onItemCheckedStateChanged(this.mChoiceActionMode, n2, l2, bl3);
                    n5 = 0;
                }
                this.updateCheckStateIfPossible(view, n2);
                this.fireOnSelected(view);
                bl3 = bl2;
            } else {
                n5 = n3;
                bl3 = bl2;
                if (this.mChoiceMode == 1) {
                    n4 = this.getCheckedItemPosition();
                    n5 = !this.mCheckStates.get(n2, false) ? 1 : 0;
                    if (n5 != 0) {
                        this.mCheckStates.clear();
                        this.mCheckStates.put(n2, true);
                        if (this.mCheckedIdStates != null && this.hasStableIds()) {
                            this.mCheckedIdStates.clear();
                            this.mCheckedIdStates.put(this.getItemId(n2), (Object)n2);
                        }
                        this.mCheckedItemCount = 1;
                    } else if (this.mCheckStates.size() == 0 || !this.mCheckStates.valueAt(0)) {
                        this.mCheckedItemCount = 0;
                    }
                    n5 = n3;
                    bl3 = bl2;
                    if (n4 != n2) {
                        if (n4 != -1) {
                            this.updateCheckStateIfPossible(null, n4);
                        }
                        this.updateCheckStateIfPossible(view, n2);
                        this.fireOnSelected(view);
                        n5 = n3;
                        bl3 = bl2;
                    }
                }
            }
        }
        bl2 = bl3;
        if (n5 == 0) return bl2;
        return bl3 | this.superPerformItemClick(view, n2, l2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    boolean performLongPress(View view, int n2, long l2) {
        boolean bl2;
        boolean bl3 = true;
        if (this.mChoiceMode == 3) {
            bl2 = bl3;
            if (this.mChoiceActionMode != null) return bl2;
            this.setItemChecked(n2, true);
            view.performHapticFeedback(0);
            return bl3;
        }
        bl3 = false;
        if (this.mOnItemLongClickListener != null) {
            bl3 = this.mOnItemLongClickListener.onItemLongClick(this, view, n2, l2);
        }
        bl2 = bl3;
        if (!bl3) return bl2;
        view.performHapticFeedback(0);
        return bl3;
    }

    public void setChoiceMode(int n2) {
        this.mChoiceMode = n2;
        if (this.mChoiceActionMode != null) {
            this.mChoiceActionMode.finish();
            this.mChoiceActionMode = null;
        }
        if (this.mChoiceMode != 0) {
            if (this.mCheckStates == null) {
                this.mCheckStates = new SparseBooleanArray(0);
            }
            if (this.mCheckedIdStates == null && this.hasStableIds()) {
                this.mCheckedIdStates = new LongSparseArray(0);
            }
            if (this.mChoiceMode == 3) {
                this.clearChoices();
                if (this.mAttachedRecyclerView != null) {
                    this.mAttachedRecyclerView.setLongClickable(true);
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setItemChecked(int n2, boolean bl2) {
        if (this.mChoiceMode == 0) return;
        if (bl2 && this.mAttachedRecyclerView != null) {
            this.startSelectionModeIfNeeded((View)this.mAttachedRecyclerView);
        }
        if (this.mChoiceMode == 2 || this.mChoiceMode == 3) {
            boolean bl3 = this.mCheckStates.get(n2);
            this.mCheckStates.put(n2, bl2);
            if (this.mCheckedIdStates != null && this.hasStableIds()) {
                if (bl2) {
                    this.mCheckedIdStates.put(this.getItemId(n2), (Object)n2);
                } else {
                    this.mCheckedIdStates.delete(this.getItemId(n2));
                }
            }
            if (bl3 != bl2) {
                this.mCheckedItemCount = bl2 ? ++this.mCheckedItemCount : --this.mCheckedItemCount;
                this.updateCheckStateIfPossible(null, n2);
                this.fireOnSelected(null);
            }
            if (this.mChoiceActionMode == null) return;
            long l2 = this.getItemId(n2);
            this.mMultiChoiceModeCallback.onItemCheckedStateChanged(this.mChoiceActionMode, n2, l2, bl2);
            return;
        }
        int n3 = this.getCheckedItemPosition();
        boolean bl4 = this.mCheckedIdStates != null && this.hasStableIds();
        if (bl2 || this.isItemChecked(n2)) {
            this.mCheckStates.clear();
            if (bl4) {
                this.mCheckedIdStates.clear();
            }
        }
        if (bl2) {
            this.mCheckStates.put(n2, true);
            if (bl4) {
                this.mCheckedIdStates.put(this.getItemId(n2), (Object)n2);
            }
            this.mCheckedItemCount = 1;
        } else if (this.mCheckStates.size() == 0 || !this.mCheckStates.valueAt(0)) {
            this.mCheckedItemCount = 0;
        }
        if (n3 == n2) {
            return;
        }
        if (n3 != -1) {
            this.updateCheckStateIfPossible(null, n3);
        }
        this.updateCheckStateIfPossible(null, n2);
        this.fireOnSelected(null);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.mOnItemSelectedListener = onItemSelectedListener;
    }

    public boolean superPerformItemClick(View view, int n2, long l2) {
        if (this.mOnItemClickListener != null) {
            this.mOnItemClickListener.onItemClick(this, view, n2, l2);
            if (view != null) {
                view.playSoundEffect(0);
                view.sendAccessibilityEvent(1);
            }
            return true;
        }
        return false;
    }

    class MultiChoiceModeWrapper
    implements AbsListView.MultiChoiceModeListener {
        private AbsListView.MultiChoiceModeListener mWrapped;

        MultiChoiceModeWrapper() {
        }

        public boolean hasWrappedCallback() {
            return this.mWrapped != null;
        }

        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.mWrapped.onActionItemClicked(actionMode, menuItem);
        }

        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            boolean bl2 = false;
            if (this.mWrapped.onCreateActionMode(actionMode, menu)) {
                if (TrackSelectionAdapterWrapper.this.mAttachedRecyclerView != null) {
                    TrackSelectionAdapterWrapper.this.mAttachedRecyclerView.setLongClickable(false);
                }
                bl2 = true;
            }
            return bl2;
        }

        public void onDestroyActionMode(ActionMode actionMode) {
            this.mWrapped.onDestroyActionMode(actionMode);
            TrackSelectionAdapterWrapper.this.mChoiceActionMode = null;
            TrackSelectionAdapterWrapper.this.clearChoices();
            TrackSelectionAdapterWrapper.this.notifyDataSetChanged();
            TrackSelectionAdapterWrapper.this.fireOnSelected(null);
            if (TrackSelectionAdapterWrapper.this.mAttachedRecyclerView != null) {
                TrackSelectionAdapterWrapper.this.mAttachedRecyclerView.setLongClickable(true);
            }
        }

        public void onItemCheckedStateChanged(ActionMode actionMode, int n2, long l2, boolean bl2) {
            this.mWrapped.onItemCheckedStateChanged(actionMode, n2, l2, bl2);
            if (TrackSelectionAdapterWrapper.this.getCheckedItemCount() == 0) {
                actionMode.finish();
            }
        }

        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return this.mWrapped.onPrepareActionMode(actionMode, menu);
        }

        public void setWrapped(AbsListView.MultiChoiceModeListener multiChoiceModeListener) {
            this.mWrapped = multiChoiceModeListener;
        }
    }

    public static interface OnItemClickListener {
        public void onItemClick(TrackSelectionAdapterWrapper<?> var1, View var2, int var3, long var4);
    }

    public static interface OnItemLongClickListener {
        public boolean onItemLongClick(TrackSelectionAdapterWrapper<?> var1, View var2, int var3, long var4);
    }

    public static interface OnItemSelectedListener {
        public void onItemSelected(TrackSelectionAdapterWrapper<?> var1, View var2, int var3, long var4);

        public void onNothingSelected(TrackSelectionAdapterWrapper<?> var1);
    }
}

