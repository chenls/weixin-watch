/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.DataSetObserver
 */
package ticwear.design.widget;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;

public abstract class CursorRecyclerViewAdapter<VH extends RecyclerView.ViewHolder>
extends RecyclerView.Adapter<VH> {
    private Context mContext;
    private Cursor mCursor;
    private DataSetObserver mDataSetObserver;
    private boolean mDataValid;
    private int mRowIdColumn;

    /*
     * Enabled aggressive block sorting
     */
    public CursorRecyclerViewAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
        boolean bl2 = cursor != null;
        this.mDataValid = bl2;
        int n2 = this.mDataValid ? this.mCursor.getColumnIndex("_id") : -1;
        this.mRowIdColumn = n2;
        this.mDataSetObserver = new NotifyingDataSetObserver();
        if (this.mCursor != null) {
            this.mCursor.registerDataSetObserver(this.mDataSetObserver);
        }
    }

    static /* synthetic */ boolean access$102(CursorRecyclerViewAdapter cursorRecyclerViewAdapter, boolean bl2) {
        cursorRecyclerViewAdapter.mDataValid = bl2;
        return bl2;
    }

    public void changeCursor(Cursor cursor) {
        if ((cursor = this.swapCursor(cursor)) != null) {
            cursor.close();
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public Cursor getCursor() {
        return this.mCursor;
    }

    @Override
    public int getItemCount() {
        if (this.mDataValid && this.mCursor != null) {
            return this.mCursor.getCount();
        }
        return 0;
    }

    @Override
    public long getItemId(int n2) {
        if (this.mDataValid && this.mCursor != null && this.mCursor.moveToPosition(n2)) {
            return this.mCursor.getLong(this.mRowIdColumn);
        }
        return 0L;
    }

    @Override
    public void onBindViewHolder(VH VH, int n2) {
        if (!this.mDataValid) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        }
        if (!this.mCursor.moveToPosition(n2)) {
            throw new IllegalStateException("couldn't move cursor to position " + n2);
        }
        this.onBindViewHolder(VH, this.mCursor);
    }

    public abstract void onBindViewHolder(VH var1, Cursor var2);

    @Override
    public void setHasStableIds(boolean bl2) {
        super.setHasStableIds(true);
    }

    public Cursor swapCursor(Cursor cursor) {
        if (cursor == this.mCursor) {
            return null;
        }
        Cursor cursor2 = this.mCursor;
        if (cursor2 != null && this.mDataSetObserver != null) {
            cursor2.unregisterDataSetObserver(this.mDataSetObserver);
        }
        this.mCursor = cursor;
        if (this.mCursor != null) {
            if (this.mDataSetObserver != null) {
                this.mCursor.registerDataSetObserver(this.mDataSetObserver);
            }
            this.mRowIdColumn = cursor.getColumnIndexOrThrow("_id");
            this.mDataValid = true;
            this.notifyDataSetChanged();
            return cursor2;
        }
        this.mRowIdColumn = -1;
        this.mDataValid = false;
        this.notifyDataSetChanged();
        return cursor2;
    }

    private class NotifyingDataSetObserver
    extends DataSetObserver {
        private NotifyingDataSetObserver() {
        }

        public void onChanged() {
            super.onChanged();
            CursorRecyclerViewAdapter.access$102(CursorRecyclerViewAdapter.this, true);
            CursorRecyclerViewAdapter.this.notifyDataSetChanged();
        }

        public void onInvalidated() {
            super.onInvalidated();
            CursorRecyclerViewAdapter.access$102(CursorRecyclerViewAdapter.this, false);
            CursorRecyclerViewAdapter.this.notifyDataSetChanged();
        }
    }
}

