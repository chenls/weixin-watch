/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.view.AbsSavedState
 *  android.view.KeyEvent
 *  android.view.View
 *  android.view.ViewGroup
 */
package ticwear.design.preference;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.AbsSavedState;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import ticwear.design.R;
import ticwear.design.internal.CharSequences;
import ticwear.design.preference.PreferenceManager;
import ticwear.design.preference.PreferenceScreen;
import ticwear.design.preference.PreferenceViewHolder;

public class Preference
implements Comparable<Preference> {
    public static final int DEFAULT_ORDER = Integer.MAX_VALUE;
    private boolean mBaseMethodCalled;
    private Context mContext;
    private Object mDefaultValue;
    private String mDependencyKey;
    private boolean mDependencyMet = true;
    private List<Preference> mDependents;
    private boolean mEnabled = true;
    private Bundle mExtras;
    private String mFragment;
    private Drawable mIcon;
    private int mIconResId;
    private long mId;
    private Intent mIntent;
    private String mKey;
    private int mLayoutResId;
    private OnPreferenceChangeInternalListener mListener;
    private OnPreferenceChangeListener mOnChangeListener;
    private OnPreferenceClickListener mOnClickListener;
    private int mOrder = Integer.MAX_VALUE;
    private boolean mParentDependencyMet = true;
    private boolean mPersistent = true;
    private PreferenceManager mPreferenceManager;
    private boolean mRequiresKey;
    private boolean mSelectable = true;
    private boolean mShouldDisableView = true;
    private CharSequence mSummary;
    private CharSequence mTitle;
    private int mTitleRes;
    protected ViewHolderCreator mViewHolderCreator;
    private int mWidgetLayoutResId;

    public Preference(Context context) {
        this(context, null);
    }

    public Preference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842894);
    }

    public Preference(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    public Preference(Context context, AttributeSet attributeSet, int n2, int n3) {
        this.mLayoutResId = R.layout.preference_ticwear;
        this.mViewHolderCreator = new ViewHolderCreator(){

            @Override
            public ViewHolder create(@NonNull ViewGroup viewGroup, @LayoutRes int n2, @LayoutRes int n3) {
                return new ViewHolder(viewGroup, n2, n3);
            }
        };
        this.mContext = context;
        context = context.obtainStyledAttributes(attributeSet, R.styleable.Preference, n2, n3);
        n2 = context.getIndexCount() - 1;
        while (true) {
            if (n2 < 0) {
                context.recycle();
                return;
            }
            n3 = context.getIndex(n2);
            if (n3 == R.styleable.Preference_android_icon) {
                this.mIconResId = context.getResourceId(n3, 0);
            } else if (n3 == R.styleable.Preference_android_key) {
                this.mKey = context.getString(n3);
            } else if (n3 == R.styleable.Preference_android_title) {
                this.mTitleRes = context.getResourceId(n3, 0);
                this.mTitle = context.getString(n3);
            } else if (n3 == R.styleable.Preference_android_summary) {
                this.mSummary = context.getString(n3);
            } else if (n3 == R.styleable.Preference_android_order) {
                this.mOrder = context.getInt(n3, this.mOrder);
            } else if (n3 == R.styleable.Preference_android_fragment) {
                this.mFragment = context.getString(n3);
            } else if (n3 == R.styleable.Preference_android_layout) {
                this.mLayoutResId = context.getResourceId(n3, this.mLayoutResId);
            } else if (n3 == R.styleable.Preference_android_widgetLayout) {
                this.mWidgetLayoutResId = context.getResourceId(n3, this.mWidgetLayoutResId);
            } else if (n3 == R.styleable.Preference_android_enabled) {
                this.mEnabled = context.getBoolean(n3, true);
            } else if (n3 == R.styleable.Preference_android_selectable) {
                this.mSelectable = context.getBoolean(n3, true);
            } else if (n3 == R.styleable.Preference_android_persistent) {
                this.mPersistent = context.getBoolean(n3, this.mPersistent);
            } else if (n3 == R.styleable.Preference_android_dependency) {
                this.mDependencyKey = context.getString(n3);
            } else if (n3 == R.styleable.Preference_android_defaultValue) {
                this.mDefaultValue = this.onGetDefaultValue((TypedArray)context, n3);
            } else if (n3 == R.styleable.Preference_android_shouldDisableView) {
                this.mShouldDisableView = context.getBoolean(n3, this.mShouldDisableView);
            }
            --n2;
        }
    }

    static /* synthetic */ Drawable access$102(Preference preference, Drawable drawable2) {
        preference.mIcon = drawable2;
        return drawable2;
    }

    private void dispatchSetInitialValue() {
        if (!this.shouldPersist() || !this.getSharedPreferences().contains(this.mKey)) {
            if (this.mDefaultValue != null) {
                this.onSetInitialValue(false, this.mDefaultValue);
            }
            return;
        }
        this.onSetInitialValue(true, null);
    }

    private void registerDependency() {
        if (TextUtils.isEmpty((CharSequence)this.mDependencyKey)) {
            return;
        }
        Preference preference = this.findPreferenceInHierarchy(this.mDependencyKey);
        if (preference != null) {
            preference.registerDependent(this);
            return;
        }
        throw new IllegalStateException("Dependency \"" + this.mDependencyKey + "\" not found for preference \"" + this.mKey + "\" (title: \"" + this.mTitle + "\"");
    }

    private void registerDependent(Preference preference) {
        if (this.mDependents == null) {
            this.mDependents = new ArrayList<Preference>();
        }
        this.mDependents.add(preference);
        preference.onDependencyChanged(this, this.shouldDisableDependents());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void tryCommit(SharedPreferences.Editor editor) {
        if (!this.mPreferenceManager.shouldCommit()) return;
        try {
            editor.apply();
            return;
        }
        catch (AbstractMethodError abstractMethodError) {
            editor.commit();
            return;
        }
    }

    private void unregisterDependency() {
        Preference preference;
        if (this.mDependencyKey != null && (preference = this.findPreferenceInHierarchy(this.mDependencyKey)) != null) {
            preference.unregisterDependent(this);
        }
    }

    private void unregisterDependent(Preference preference) {
        if (this.mDependents != null) {
            this.mDependents.remove(preference);
        }
    }

    protected boolean callChangeListener(Object object) {
        return this.mOnChangeListener == null || this.mOnChangeListener.onPreferenceChange(this, object);
    }

    @Override
    public int compareTo(@NonNull Preference preference) {
        if (this.mOrder != preference.mOrder) {
            return this.mOrder - preference.mOrder;
        }
        if (this.mTitle == preference.mTitle) {
            return 0;
        }
        if (this.mTitle == null) {
            return 1;
        }
        if (preference.mTitle == null) {
            return -1;
        }
        return CharSequences.compareToIgnoreCase(this.mTitle, preference.mTitle);
    }

    void dispatchRestoreInstanceState(Bundle bundle) {
        if (this.hasKey() && (bundle = bundle.getParcelable(this.mKey)) != null) {
            this.mBaseMethodCalled = false;
            this.onRestoreInstanceState((Parcelable)bundle);
            if (!this.mBaseMethodCalled) {
                throw new IllegalStateException("Derived class did not call super.onRestoreInstanceState()");
            }
        }
    }

    void dispatchSaveInstanceState(Bundle bundle) {
        if (this.hasKey()) {
            this.mBaseMethodCalled = false;
            Parcelable parcelable = this.onSaveInstanceState();
            if (!this.mBaseMethodCalled) {
                throw new IllegalStateException("Derived class did not call super.onSaveInstanceState()");
            }
            if (parcelable != null) {
                bundle.putParcelable(this.mKey, parcelable);
            }
        }
    }

    protected Preference findPreferenceInHierarchy(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2) || this.mPreferenceManager == null) {
            return null;
        }
        return this.mPreferenceManager.findPreference(string2);
    }

    public Context getContext() {
        return this.mContext;
    }

    public String getDependency() {
        return this.mDependencyKey;
    }

    public SharedPreferences.Editor getEditor() {
        if (this.mPreferenceManager == null) {
            return null;
        }
        return this.mPreferenceManager.getEditor();
    }

    public Bundle getExtras() {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        return this.mExtras;
    }

    StringBuilder getFilterableStringBuilder() {
        StringBuilder stringBuilder = new StringBuilder();
        CharSequence charSequence = this.getTitle();
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            stringBuilder.append(charSequence).append(' ');
        }
        if (!TextUtils.isEmpty((CharSequence)(charSequence = this.getSummary()))) {
            stringBuilder.append(charSequence).append(' ');
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 1);
        }
        return stringBuilder;
    }

    public String getFragment() {
        return this.mFragment;
    }

    public Drawable getIcon() {
        return this.mIcon;
    }

    long getId() {
        return this.mId;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public String getKey() {
        return this.mKey;
    }

    @LayoutRes
    public int getLayoutResource() {
        return this.mLayoutResId;
    }

    public OnPreferenceChangeListener getOnPreferenceChangeListener() {
        return this.mOnChangeListener;
    }

    public OnPreferenceClickListener getOnPreferenceClickListener() {
        return this.mOnClickListener;
    }

    public int getOrder() {
        return this.mOrder;
    }

    protected boolean getPersistedBoolean(boolean bl2) {
        if (!this.shouldPersist()) {
            return bl2;
        }
        return this.mPreferenceManager.getSharedPreferences().getBoolean(this.mKey, bl2);
    }

    protected float getPersistedFloat(float f2) {
        if (!this.shouldPersist()) {
            return f2;
        }
        return this.mPreferenceManager.getSharedPreferences().getFloat(this.mKey, f2);
    }

    protected int getPersistedInt(int n2) {
        if (!this.shouldPersist()) {
            return n2;
        }
        return this.mPreferenceManager.getSharedPreferences().getInt(this.mKey, n2);
    }

    protected long getPersistedLong(long l2) {
        if (!this.shouldPersist()) {
            return l2;
        }
        return this.mPreferenceManager.getSharedPreferences().getLong(this.mKey, l2);
    }

    protected String getPersistedString(String string2) {
        if (!this.shouldPersist()) {
            return string2;
        }
        return this.mPreferenceManager.getSharedPreferences().getString(this.mKey, string2);
    }

    protected Set<String> getPersistedStringSet(Set<String> set) {
        if (!this.shouldPersist()) {
            return set;
        }
        return this.mPreferenceManager.getSharedPreferences().getStringSet(this.mKey, set);
    }

    public PreferenceManager getPreferenceManager() {
        return this.mPreferenceManager;
    }

    public SharedPreferences getSharedPreferences() {
        if (this.mPreferenceManager == null) {
            return null;
        }
        return this.mPreferenceManager.getSharedPreferences();
    }

    public boolean getShouldDisableView() {
        return this.mShouldDisableView;
    }

    public CharSequence getSummary() {
        return this.mSummary;
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    @StringRes
    public int getTitleRes() {
        return this.mTitleRes;
    }

    @LayoutRes
    public int getWidgetLayoutResource() {
        return this.mWidgetLayoutResId;
    }

    public boolean hasKey() {
        return !TextUtils.isEmpty((CharSequence)this.mKey);
    }

    public boolean isEnabled() {
        return this.mEnabled && this.mDependencyMet && this.mParentDependencyMet;
    }

    public boolean isPersistent() {
        return this.mPersistent;
    }

    public boolean isSelectable() {
        return this.mSelectable;
    }

    protected void notifyChanged() {
        if (this.mListener != null) {
            this.mListener.onPreferenceChange(this);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void notifyDependencyChange(boolean bl2) {
        List<Preference> list = this.mDependents;
        if (list != null) {
            int n2 = list.size();
            for (int i2 = 0; i2 < n2; ++i2) {
                list.get(i2).onDependencyChanged(this, bl2);
            }
        }
    }

    protected void notifyHierarchyChanged() {
        if (this.mListener != null) {
            this.mListener.onPreferenceHierarchyChange(this);
        }
    }

    protected void onAttachedToActivity() {
        this.registerDependency();
    }

    protected void onAttachedToHierarchy(PreferenceManager preferenceManager) {
        this.mPreferenceManager = preferenceManager;
        this.mId = preferenceManager.getNextId();
        this.dispatchSetInitialValue();
    }

    protected void onClick() {
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onDependencyChanged(Preference preference, boolean bl2) {
        if (this.mDependencyMet == bl2) {
            bl2 = !bl2;
            this.mDependencyMet = bl2;
            this.notifyDependencyChange(this.shouldDisableDependents());
            this.notifyChanged();
        }
    }

    protected Object onGetDefaultValue(TypedArray typedArray, int n2) {
        return null;
    }

    public boolean onKey(View view, int n2, KeyEvent keyEvent) {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onParentChanged(Preference preference, boolean bl2) {
        if (this.mParentDependencyMet == bl2) {
            bl2 = !bl2;
            this.mParentDependencyMet = bl2;
            this.notifyDependencyChange(this.shouldDisableDependents());
            this.notifyChanged();
        }
    }

    @CallSuper
    protected void onPrepareForRemoval() {
        this.unregisterDependency();
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        this.mBaseMethodCalled = true;
        if (parcelable != BaseSavedState.EMPTY_STATE && parcelable != null) {
            throw new IllegalArgumentException("Wrong state class -- expecting Preference State");
        }
    }

    protected Parcelable onSaveInstanceState() {
        this.mBaseMethodCalled = true;
        return BaseSavedState.EMPTY_STATE;
    }

    protected void onSetInitialValue(boolean bl2, Object object) {
    }

    public Bundle peekExtras() {
        return this.mExtras;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void performClick(PreferenceScreen preferenceScreen) {
        block4: {
            block2: {
                block3: {
                    if (!this.isEnabled()) break block2;
                    this.onClick();
                    if (this.mOnClickListener != null && this.mOnClickListener.onPreferenceClick(this)) break block2;
                    Object object = this.getPreferenceManager();
                    if (object == null) break block3;
                    object = ((PreferenceManager)object).getOnPreferenceTreeClickListener();
                    if (preferenceScreen != null && object != null && object.onPreferenceTreeClick(preferenceScreen, this)) break block2;
                }
                if (this.mIntent != null) break block4;
            }
            return;
        }
        this.getContext().startActivity(this.mIntent);
    }

    protected boolean persistBoolean(boolean bl2) {
        boolean bl3 = false;
        if (this.shouldPersist()) {
            if (!bl2) {
                bl3 = true;
            }
            if (bl2 == this.getPersistedBoolean(bl3)) {
                return true;
            }
            SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putBoolean(this.mKey, bl2);
            this.tryCommit(editor);
            return true;
        }
        return false;
    }

    protected boolean persistFloat(float f2) {
        if (this.shouldPersist()) {
            if (f2 == this.getPersistedFloat(Float.NaN)) {
                return true;
            }
            SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putFloat(this.mKey, f2);
            this.tryCommit(editor);
            return true;
        }
        return false;
    }

    protected boolean persistInt(int n2) {
        if (this.shouldPersist()) {
            if (n2 == this.getPersistedInt(~n2)) {
                return true;
            }
            SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putInt(this.mKey, n2);
            this.tryCommit(editor);
            return true;
        }
        return false;
    }

    protected boolean persistLong(long l2) {
        if (this.shouldPersist()) {
            if (l2 == this.getPersistedLong(0xFFFFFFFFFFFFFFFFL ^ l2)) {
                return true;
            }
            SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putLong(this.mKey, l2);
            this.tryCommit(editor);
            return true;
        }
        return false;
    }

    protected boolean persistString(String string2) {
        if (this.shouldPersist()) {
            if (TextUtils.equals((CharSequence)string2, (CharSequence)this.getPersistedString(null))) {
                return true;
            }
            SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putString(this.mKey, string2);
            this.tryCommit(editor);
            return true;
        }
        return false;
    }

    protected boolean persistStringSet(Set<String> set) {
        if (this.shouldPersist()) {
            if (set.equals(this.getPersistedStringSet(null))) {
                return true;
            }
            SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putStringSet(this.mKey, set);
            this.tryCommit(editor);
            return true;
        }
        return false;
    }

    void requireKey() {
        if (this.mKey == null) {
            throw new IllegalStateException("Preference does not have a key assigned.");
        }
        this.mRequiresKey = true;
    }

    public void restoreHierarchyState(Bundle bundle) {
        this.dispatchRestoreInstanceState(bundle);
    }

    public void saveHierarchyState(Bundle bundle) {
        this.dispatchSaveInstanceState(bundle);
    }

    public void setDefaultValue(Object object) {
        this.mDefaultValue = object;
    }

    public void setDependency(String string2) {
        this.unregisterDependency();
        this.mDependencyKey = string2;
        this.registerDependency();
    }

    public void setEnabled(boolean bl2) {
        if (this.mEnabled != bl2) {
            this.mEnabled = bl2;
            this.notifyDependencyChange(this.shouldDisableDependents());
            this.notifyChanged();
        }
    }

    public void setFragment(String string2) {
        this.mFragment = string2;
    }

    public void setIcon(@DrawableRes int n2) {
        if (this.mIconResId != n2) {
            this.mIconResId = n2;
            this.setIcon(this.mContext.getDrawable(n2));
        }
    }

    public void setIcon(Drawable drawable2) {
        if (drawable2 == null && this.mIcon != null || drawable2 != null && this.mIcon != drawable2) {
            this.mIcon = drawable2;
            this.notifyChanged();
        }
    }

    public void setIntent(Intent intent) {
        this.mIntent = intent;
    }

    public void setKey(String string2) {
        this.mKey = string2;
        if (this.mRequiresKey && !this.hasKey()) {
            this.requireKey();
        }
    }

    final void setOnPreferenceChangeInternalListener(OnPreferenceChangeInternalListener onPreferenceChangeInternalListener) {
        this.mListener = onPreferenceChangeInternalListener;
    }

    public void setOnPreferenceChangeListener(OnPreferenceChangeListener onPreferenceChangeListener) {
        this.mOnChangeListener = onPreferenceChangeListener;
    }

    public void setOnPreferenceClickListener(OnPreferenceClickListener onPreferenceClickListener) {
        this.mOnClickListener = onPreferenceClickListener;
    }

    public void setOrder(int n2) {
        if (n2 != this.mOrder) {
            this.mOrder = n2;
            this.notifyHierarchyChanged();
        }
    }

    public void setPersistent(boolean bl2) {
        this.mPersistent = bl2;
    }

    public void setSelectable(boolean bl2) {
        if (this.mSelectable != bl2) {
            this.mSelectable = bl2;
            this.notifyChanged();
        }
    }

    public void setShouldDisableView(boolean bl2) {
        this.mShouldDisableView = bl2;
        this.notifyChanged();
    }

    public void setSummary(@StringRes int n2) {
        this.setSummary(this.mContext.getString(n2));
    }

    public void setSummary(CharSequence charSequence) {
        if (charSequence == null && this.mSummary != null || charSequence != null && !charSequence.equals(this.mSummary)) {
            this.mSummary = charSequence;
            this.notifyChanged();
        }
    }

    public void setTitle(@StringRes int n2) {
        this.setTitle(this.mContext.getString(n2));
        this.mTitleRes = n2;
    }

    public void setTitle(CharSequence charSequence) {
        if (charSequence == null && this.mTitle != null || charSequence != null && !charSequence.equals(this.mTitle)) {
            this.mTitleRes = 0;
            this.mTitle = charSequence;
            this.notifyChanged();
        }
    }

    public boolean shouldCommit() {
        if (this.mPreferenceManager == null) {
            return false;
        }
        return this.mPreferenceManager.shouldCommit();
    }

    public boolean shouldDisableDependents() {
        return !this.isEnabled();
    }

    protected boolean shouldPersist() {
        return this.mPreferenceManager != null && this.isPersistent() && this.hasKey();
    }

    public String toString() {
        return this.getFilterableStringBuilder().toString();
    }

    public static class BaseSavedState
    extends AbsSavedState {
        public static final Parcelable.Creator<BaseSavedState> CREATOR = new Parcelable.Creator<BaseSavedState>(){

            public BaseSavedState createFromParcel(Parcel parcel) {
                return new BaseSavedState(parcel);
            }

            public BaseSavedState[] newArray(int n2) {
                return new BaseSavedState[n2];
            }
        };

        public BaseSavedState(Parcel parcel) {
            super(parcel);
        }

        public BaseSavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    static interface OnPreferenceChangeInternalListener {
        public void onPreferenceChange(Preference var1);

        public void onPreferenceHierarchyChange(Preference var1);
    }

    public static interface OnPreferenceChangeListener {
        public boolean onPreferenceChange(Preference var1, Object var2);
    }

    public static interface OnPreferenceClickListener {
        public boolean onPreferenceClick(Preference var1);
    }

    protected static class ViewHolder
    extends PreferenceViewHolder {
        protected PreferenceViewHolder.PreferenceData data;

        public ViewHolder(@NonNull ViewGroup viewGroup, @LayoutRes int n2, @LayoutRes int n3) {
            this(viewGroup, n2, n3, new PreferenceViewHolder.PreferenceData());
        }

        protected ViewHolder(@NonNull ViewGroup viewGroup, @LayoutRes int n2, @LayoutRes int n3, PreferenceViewHolder.PreferenceData preferenceData) {
            super(viewGroup, n2, n3);
            this.data = preferenceData;
        }

        private Drawable getIconDrawable(@NonNull Preference preference) {
            if (this.iconView != null && preference.mIconResId != 0 && preference.mIcon == null) {
                Preference.access$102(preference, preference.getContext().getDrawable(preference.mIconResId));
            }
            return preference.getIcon();
        }

        public final void bindPreference(@NonNull Preference preference) {
            this.bindPreferenceToData(preference);
            this.bind(this.data);
        }

        @CallSuper
        protected void bindPreferenceToData(@NonNull Preference preference) {
            this.data.title = preference.getTitle();
            this.data.summary = preference.getSummary();
            this.data.icon = this.getIconDrawable(preference);
        }
    }

    protected static interface ViewHolderCreator {
        public ViewHolder create(@NonNull ViewGroup var1, @LayoutRes int var2, @LayoutRes int var3);
    }
}

