/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.TypedArray
 *  android.media.RingtoneManager
 *  android.net.Uri
 *  android.os.Parcelable
 *  android.text.TextUtils
 *  android.util.AttributeSet
 */
package ticwear.design.preference;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import ticwear.design.R;
import ticwear.design.app.RingtonePickerActivity;
import ticwear.design.preference.Preference;
import ticwear.design.preference.PreferenceFragment;
import ticwear.design.preference.PreferenceManager;

public class RingtonePreference
extends Preference
implements PreferenceManager.OnActivityResultListener {
    private static final String TAG = "RingtonePreference";
    private int mRequestCode;
    private int mRingtoneType;
    private boolean mShowDefault;
    private boolean mShowSilent;

    public RingtonePreference(Context context) {
        this(context, null);
    }

    public RingtonePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842899);
    }

    public RingtonePreference(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, 0);
    }

    public RingtonePreference(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2, n3);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.RingtonePreference, n2, n3);
        this.mRingtoneType = context.getInt(R.styleable.RingtonePreference_android_ringtoneType, 1);
        this.mShowDefault = context.getBoolean(R.styleable.RingtonePreference_android_showDefault, true);
        this.mShowSilent = context.getBoolean(R.styleable.RingtonePreference_android_showSilent, true);
        context.recycle();
    }

    public int getRingtoneType() {
        return this.mRingtoneType;
    }

    public boolean getShowDefault() {
        return this.mShowDefault;
    }

    public boolean getShowSilent() {
        return this.mShowSilent;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onActivityResult(int n2, int n3, Intent object) {
        if (n2 != this.mRequestCode) {
            return false;
        }
        if (object != null) {
            void var3_5;
            Uri uri = (Uri)object.getParcelableExtra("android.intent.extra.ringtone.PICKED_URI");
            if (uri != null) {
                String string2 = uri.toString();
            } else {
                String string3 = "";
            }
            if (this.callChangeListener(var3_5)) {
                this.onSaveRingtone(uri);
            }
        }
        return true;
    }

    @Override
    protected void onAttachedToHierarchy(PreferenceManager preferenceManager) {
        super.onAttachedToHierarchy(preferenceManager);
        preferenceManager.registerOnActivityResultListener(this);
        this.mRequestCode = preferenceManager.getNextRequestCode();
    }

    @Override
    protected void onClick() {
        Intent intent = new Intent(this.getContext(), RingtonePickerActivity.class);
        this.onPrepareRingtonePickerIntent(intent);
        PreferenceFragment preferenceFragment = this.getPreferenceManager().getFragment();
        if (preferenceFragment != null) {
            preferenceFragment.startActivityForResult(intent, this.mRequestCode);
            return;
        }
        this.getPreferenceManager().getActivity().startActivityForResult(intent, this.mRequestCode);
    }

    @Override
    protected Object onGetDefaultValue(TypedArray typedArray, int n2) {
        return typedArray.getString(n2);
    }

    protected void onPrepareRingtonePickerIntent(Intent intent) {
        intent.putExtra("android.intent.extra.ringtone.EXISTING_URI", (Parcelable)this.onRestoreRingtone());
        intent.putExtra("android.intent.extra.ringtone.SHOW_DEFAULT", this.mShowDefault);
        if (this.mShowDefault) {
            intent.putExtra("android.intent.extra.ringtone.DEFAULT_URI", (Parcelable)RingtoneManager.getDefaultUri((int)this.getRingtoneType()));
        }
        intent.putExtra("android.intent.extra.ringtone.SHOW_SILENT", this.mShowSilent);
        intent.putExtra("android.intent.extra.ringtone.TYPE", this.mRingtoneType);
        intent.putExtra("android.intent.extra.ringtone.TITLE", this.getTitle());
    }

    protected Uri onRestoreRingtone() {
        Uri uri = null;
        String string2 = this.getPersistedString(null);
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            uri = Uri.parse((String)string2);
        }
        return uri;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    protected void onSaveRingtone(Uri object) {
        void var1_3;
        if (object != null) {
            String string2 = object.toString();
        } else {
            String string3 = "";
        }
        this.persistString((String)var1_3);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onSetInitialValue(boolean bl2, Object object) {
        object = (String)object;
        if (bl2 || TextUtils.isEmpty((CharSequence)object)) {
            return;
        }
        this.onSaveRingtone(Uri.parse((String)object));
    }

    public void setRingtoneType(int n2) {
        this.mRingtoneType = n2;
    }

    public void setShowDefault(boolean bl2) {
        this.mShowDefault = bl2;
    }

    public void setShowSilent(boolean bl2) {
        this.mShowSilent = bl2;
    }
}

