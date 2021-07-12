/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
 *  android.view.View
 */
package ticwear.design.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import ticwear.design.R;
import ticwear.design.widget.FocusableLinearLayoutManager;
import ticwear.design.widget.TicklableRecyclerView;

public class RecyclerActivity
extends Activity {
    protected RecyclerView.Adapter mAdapter;
    private boolean mFinishedStart = false;
    private Handler mHandler = new Handler();
    protected RecyclerView mList;
    private Runnable mRequestFocus = new Runnable(){

        @Override
        public void run() {
            RecyclerActivity.this.mList.focusableViewAvailable((View)RecyclerActivity.this.mList);
        }
    };

    private void ensureList() {
        if (this.mList != null) {
            return;
        }
        this.setContentView(R.layout.list_content_ticklable);
    }

    public RecyclerView.Adapter getListAdapter() {
        return this.mAdapter;
    }

    public RecyclerView getListView() {
        this.ensureList();
        return this.mList;
    }

    public void onContentChanged() {
        super.onContentChanged();
        this.mList = (TicklableRecyclerView)this.findViewById(16908298);
        this.mList.setLayoutManager(new FocusableLinearLayoutManager((Context)this));
        if (this.mList == null) {
            throw new RuntimeException("Your content must have a RecyclerView whose id attribute is 'android.R.id.list'");
        }
        if (this.mFinishedStart) {
            this.setListAdapter(this.mAdapter);
        }
        this.mHandler.post(this.mRequestFocus);
        this.mFinishedStart = true;
    }

    protected void onDestroy() {
        this.mHandler.removeCallbacks(this.mRequestFocus);
        super.onDestroy();
    }

    protected void onRestoreInstanceState(Bundle bundle) {
        this.ensureList();
        super.onRestoreInstanceState(bundle);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setListAdapter(RecyclerView.Adapter adapter) {
        synchronized (this) {
            this.ensureList();
            this.mAdapter = adapter;
            this.mList.setAdapter(adapter);
            return;
        }
    }
}

