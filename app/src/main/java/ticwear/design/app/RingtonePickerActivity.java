/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.database.Cursor
 *  android.media.Ringtone
 *  android.media.RingtoneManager
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Parcelable
 *  android.provider.Settings$System
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package ticwear.design.app;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import ticwear.design.R;
import ticwear.design.internal.app.AlertActivity;
import ticwear.design.internal.app.AlertController;
import ticwear.design.widget.CheckedTextView;
import ticwear.design.widget.CursorRecyclerViewAdapter;
import ticwear.design.widget.FocusableLinearLayoutManager;
import ticwear.design.widget.TicklableRecyclerView;
import ticwear.design.widget.TrackSelectionAdapterWrapper;

public final class RingtonePickerActivity
extends AlertActivity
implements TrackSelectionAdapterWrapper.OnItemSelectedListener,
Runnable,
DialogInterface.OnClickListener,
AlertController.AlertParams.OnPrepareListViewListener {
    private static final int DELAY_MS_SELECTION_PLAYED = 300;
    private static final int POS_UNKNOWN = -1;
    private static final String SAVE_CLICKED_POS = "clicked_pos";
    private static final boolean SHOW_BUTTONS = false;
    private static final String TAG = "RingtonePickerActivity";
    private static Ringtone sPlayingRingtone;
    private int mClickedPos = -1;
    private Ringtone mCurrentRingtone;
    private Cursor mCursor;
    private Ringtone mDefaultRingtone;
    private int mDefaultRingtonePos = -1;
    private Uri mExistingUri;
    private Handler mHandler;
    private boolean mHasDefaultItem;
    private boolean mHasSilentItem;
    private DialogInterface.OnClickListener mRingtoneClickListener = new DialogInterface.OnClickListener(){

        public void onClick(DialogInterface dialogInterface, int n2) {
            RingtonePickerActivity.access$002(RingtonePickerActivity.this, n2);
            RingtonePickerActivity.this.playRingtone(n2, 0);
            RingtonePickerActivity.this.setPositiveResult();
        }
    };
    private RingtoneManager mRingtoneManager;
    private int mSampleRingtonePos = -1;
    private int mSilentPos = -1;
    private int mStaticItemCount;
    private int mType;
    private Uri mUriForDefaultItem;
    private WithHeaderCursorAdapter mWithHeaderCursorAdapter;

    static /* synthetic */ int access$002(RingtonePickerActivity ringtonePickerActivity, int n2) {
        ringtonePickerActivity.mClickedPos = n2;
        return n2;
    }

    private int addDefaultRingtoneItem(TicklableRecyclerView ticklableRecyclerView) {
        if (this.mType == 2) {
            return this.addStaticItem(ticklableRecyclerView, R.string.notification_sound_default);
        }
        if (this.mType == 4) {
            return this.addStaticItem(ticklableRecyclerView, R.string.alarm_sound_default);
        }
        return this.addStaticItem(ticklableRecyclerView, R.string.ringtone_default);
    }

    private int addSilentItem(TicklableRecyclerView ticklableRecyclerView) {
        return this.addStaticItem(ticklableRecyclerView, R.string.ringtone_silent);
    }

    private int addStaticItem(TicklableRecyclerView object, int n2) {
        object = this.getString(n2);
        ++this.mStaticItemCount;
        return this.mWithHeaderCursorAdapter.addHeader((String)object) - 1;
    }

    private int getListPosition(int n2) {
        if (n2 < 0) {
            return n2;
        }
        return n2 + this.mStaticItemCount;
    }

    private int getRingtoneManagerPosition(int n2) {
        return n2 - this.mStaticItemCount;
    }

    private void playRingtone(int n2, int n3) {
        this.mHandler.removeCallbacks((Runnable)this);
        this.mSampleRingtonePos = n2;
        this.mHandler.postDelayed((Runnable)this, (long)n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void saveAnyPlayingRingtone() {
        if (this.mDefaultRingtone != null && this.mDefaultRingtone.isPlaying()) {
            sPlayingRingtone = this.mDefaultRingtone;
            return;
        } else {
            if (this.mCurrentRingtone == null || !this.mCurrentRingtone.isPlaying()) return;
            sPlayingRingtone = this.mCurrentRingtone;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setPositiveResult() {
        Intent intent = new Intent();
        Object object = this.mClickedPos == this.mDefaultRingtonePos ? this.mUriForDefaultItem : (this.mClickedPos == this.mSilentPos ? null : this.mRingtoneManager.getRingtoneUri(this.getRingtoneManagerPosition(this.mClickedPos)));
        intent.putExtra("android.intent.extra.ringtone.PICKED_URI", (Parcelable)object);
        this.setResult(-1, intent);
    }

    private void stopAnyPlayingRingtone() {
        if (sPlayingRingtone != null && sPlayingRingtone.isPlaying()) {
            sPlayingRingtone.stop();
        }
        sPlayingRingtone = null;
        if (this.mDefaultRingtone != null && this.mDefaultRingtone.isPlaying()) {
            this.mDefaultRingtone.stop();
        }
        if (this.mRingtoneManager != null) {
            this.mRingtoneManager.stopPreviousRingtone();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onClick(DialogInterface dialogInterface, int n2) {
        n2 = n2 == -1 ? 1 : 0;
        this.mRingtoneManager.stopPreviousRingtone();
        if (n2 != 0) {
            this.setPositiveResult();
        } else {
            this.setResult(0);
        }
        this.getWindow().getDecorView().post(new Runnable(){

            @Override
            public void run() {
                RingtonePickerActivity.this.mCursor.deactivate();
            }
        });
        this.finish();
    }

    @Override
    protected void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        this.mHandler = new Handler();
        Object object2 = this.getIntent();
        this.mHasDefaultItem = object2.getBooleanExtra("android.intent.extra.ringtone.SHOW_DEFAULT", true);
        this.mUriForDefaultItem = (Uri)object2.getParcelableExtra("android.intent.extra.ringtone.DEFAULT_URI");
        if (this.mUriForDefaultItem == null) {
            this.mUriForDefaultItem = Settings.System.DEFAULT_RINGTONE_URI;
        }
        if (object != null) {
            this.mClickedPos = object.getInt(SAVE_CLICKED_POS, -1);
        }
        this.mHasSilentItem = object2.getBooleanExtra("android.intent.extra.ringtone.SHOW_SILENT", true);
        this.mRingtoneManager = new RingtoneManager((Activity)this);
        this.mType = object2.getIntExtra("android.intent.extra.ringtone.TYPE", -1);
        if (this.mType != -1) {
            this.mRingtoneManager.setType(this.mType);
        }
        this.mCursor = this.mRingtoneManager.getCursor();
        this.setVolumeControlStream(this.mRingtoneManager.inferStreamType());
        this.mExistingUri = (Uri)object2.getParcelableExtra("android.intent.extra.ringtone.EXISTING_URI");
        object = this.mAlertParams;
        object.mCursor = this.mCursor;
        object.mOnClickListener = this.mRingtoneClickListener;
        object.mLabelColumn = "title";
        object.mIsSingleChoice = true;
        object.mOnItemSelectedListener = this;
        object.mOnPrepareListViewListener = this;
        object.mTitle = object2.getCharSequenceExtra("android.intent.extra.ringtone.TITLE");
        if (object.mTitle == null) {
            object.mTitle = this.getString(R.string.ringtone_picker_title);
        }
        object2 = new WithHeaderCursorAdapter((Context)this, object.mCursor, object.mLabelColumn);
        this.mWithHeaderCursorAdapter = object2;
        object.mAdapter = object2;
        this.setupAlert();
    }

    public void onItemSelected(TrackSelectionAdapterWrapper trackSelectionAdapterWrapper, View view, int n2, long l2) {
        this.playRingtone(n2, 300);
    }

    public void onNothingSelected(TrackSelectionAdapterWrapper trackSelectionAdapterWrapper) {
    }

    protected void onPause() {
        super.onPause();
        if (!this.isChangingConfigurations()) {
            this.stopAnyPlayingRingtone();
        }
    }

    @Override
    public void onPrepareListView(TicklableRecyclerView ticklableRecyclerView) {
        if (this.mHasDefaultItem) {
            this.mDefaultRingtonePos = this.addDefaultRingtoneItem(ticklableRecyclerView);
            if (this.mClickedPos == -1 && RingtoneManager.isDefault((Uri)this.mExistingUri)) {
                this.mClickedPos = this.mDefaultRingtonePos;
            }
        }
        if (this.mHasSilentItem) {
            this.mSilentPos = this.addSilentItem(ticklableRecyclerView);
            if (this.mClickedPos == -1 && this.mExistingUri == null) {
                this.mClickedPos = this.mSilentPos;
            }
        }
        if (this.mClickedPos == -1) {
            this.mClickedPos = this.getListPosition(this.mRingtoneManager.getRingtonePosition(this.mExistingUri));
        }
        this.mAlertParams.mCheckedItem = this.mClickedPos;
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(SAVE_CLICKED_POS, this.mClickedPos);
    }

    protected void onStop() {
        super.onStop();
        if (!this.isChangingConfigurations()) {
            this.stopAnyPlayingRingtone();
            return;
        }
        this.saveAnyPlayingRingtone();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void run() {
        Ringtone ringtone;
        block8: {
            block7: {
                this.stopAnyPlayingRingtone();
                if (this.mSampleRingtonePos == this.mSilentPos) break block7;
                if (this.mSampleRingtonePos == this.mDefaultRingtonePos) {
                    if (this.mDefaultRingtone == null) {
                        this.mDefaultRingtone = RingtoneManager.getRingtone((Context)this, (Uri)this.mUriForDefaultItem);
                    }
                    if (this.mDefaultRingtone != null) {
                        this.mDefaultRingtone.setStreamType(this.mRingtoneManager.inferStreamType());
                    }
                    ringtone = this.mDefaultRingtone;
                    this.mCurrentRingtone = null;
                } else {
                    this.mCurrentRingtone = ringtone = this.mRingtoneManager.getRingtone(this.getRingtoneManagerPosition(this.mSampleRingtonePos));
                }
                if (ringtone != null) break block8;
            }
            return;
        }
        ringtone.play();
    }

    private static class CursorViewHolder
    extends FocusableLinearLayoutManager.ViewHolder {
        protected CheckedTextView text;

        public CursorViewHolder(View view) {
            super(view);
            this.text = (CheckedTextView)view.findViewById(16908308);
        }
    }

    private class WithHeaderCursorAdapter
    extends CursorRecyclerViewAdapter<CursorViewHolder> {
        private static final int TYPE_HEADER = 1;
        private static final int TYPE_NORMAL = 0;
        private final List<String> mHeaders;
        private final int mLabelIndex;

        public WithHeaderCursorAdapter(Context context, Cursor cursor, String string2) {
            super(context, cursor);
            this.mHeaders = new ArrayList<String>();
            this.mLabelIndex = cursor.getColumnIndexOrThrow(string2);
        }

        public int addHeader(String string2) {
            this.mHeaders.add(string2);
            this.notifyDataSetChanged();
            return this.mHeaders.size();
        }

        public void clearHeaders() {
            this.mHeaders.clear();
            this.notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return super.getItemCount() + this.mHeaders.size();
        }

        @Override
        public int getItemViewType(int n2) {
            if (n2 < this.mHeaders.size()) {
                return 1;
            }
            return 0;
        }

        @Override
        public void onBindViewHolder(CursorViewHolder cursorViewHolder, int n2) {
            if (this.getItemViewType(n2) == 1) {
                cursorViewHolder.text.setText(this.mHeaders.get(n2));
                return;
            }
            super.onBindViewHolder(cursorViewHolder, n2 - this.mHeaders.size());
        }

        @Override
        public void onBindViewHolder(CursorViewHolder cursorViewHolder, Cursor cursor) {
            cursorViewHolder.text.setText(cursor.getString(this.mLabelIndex));
        }

        @Override
        public CursorViewHolder onCreateViewHolder(ViewGroup viewGroup, int n2) {
            return new CursorViewHolder(LayoutInflater.from((Context)this.getContext()).inflate(R.layout.select_dialog_singlechoice_ticwear, viewGroup, false));
        }
    }
}

