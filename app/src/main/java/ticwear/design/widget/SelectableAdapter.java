/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.util.Log
 */
package ticwear.design.widget;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ticwear.design.widget.FocusableLinearLayoutManager;

public abstract class SelectableAdapter<VH extends FocusableLinearLayoutManager.ViewHolder>
extends RecyclerView.Adapter<VH> {
    public static final int MODE_MULTI = 2;
    public static final int MODE_SINGLE = 1;
    private static final String TAG = SelectableAdapter.class.getSimpleName();
    private int mode = 1;
    private ArrayList<Integer> selectedItems = new ArrayList();

    public void clearSelection() {
        Iterator<Integer> iterator = this.selectedItems.iterator();
        while (iterator.hasNext()) {
            int n2 = iterator.next();
            iterator.remove();
            Log.v((String)TAG, (String)("clearSelection notifyItemChanged on position " + n2));
            this.notifyItemChanged(n2);
        }
    }

    public int getMode() {
        return this.mode;
    }

    public int getSelectedItemCount() {
        return this.selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        return this.selectedItems;
    }

    public boolean isSelected(int n2) {
        return this.selectedItems.contains(n2);
    }

    public void onRestoreInstanceState(Bundle bundle) {
        this.selectedItems = bundle.getIntegerArrayList(TAG);
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putIntegerArrayList(TAG, this.selectedItems);
    }

    public void selectAll() {
        this.selectAll(-1);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void selectAll(int n2) {
        Log.v((String)TAG, (String)"selectAll");
        this.selectedItems = new ArrayList(this.getItemCount());
        int n3 = 0;
        while (n3 < this.getItemCount()) {
            if (this.getItemViewType(n3) != n2) {
                this.selectedItems.add(n3);
                Log.v((String)TAG, (String)("selectAll notifyItemChanged on position " + n3));
                this.notifyItemChanged(n3);
            }
            ++n3;
        }
        return;
    }

    public void setMode(int n2) {
        this.mode = n2;
    }

    public void toggleSelection(int n2) {
        this.toggleSelection(n2, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void toggleSelection(int n2, boolean bl2) {
        int n3;
        if (n2 < 0) {
            return;
        }
        if (this.mode == 1) {
            this.clearSelection();
        }
        if ((n3 = this.selectedItems.indexOf(n2)) != -1) {
            Log.v((String)TAG, (String)("toggleSelection removing selection on position " + n2));
            this.selectedItems.remove(n3);
        } else {
            Log.v((String)TAG, (String)("toggleSelection adding selection on position " + n2));
            this.selectedItems.add(n2);
        }
        if (bl2) {
            Log.v((String)TAG, (String)("toggleSelection notifyItemChanged on position " + n2));
            this.notifyItemChanged(n2);
        }
        Log.v((String)TAG, (String)("toggleSelection current selection " + this.selectedItems));
    }
}

