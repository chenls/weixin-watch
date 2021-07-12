/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package ticwear.design.preference;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.XmlRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ticwear.design.R;
import ticwear.design.preference.Preference;
import ticwear.design.preference.PreferenceManager;
import ticwear.design.preference.PreferenceScreen;
import ticwear.design.widget.FocusableLinearLayoutManager;
import ticwear.design.widget.TicklableRecyclerView;

public abstract class PreferenceFragment
extends Fragment
implements PreferenceManager.OnPreferenceTreeClickListener {
    private static final int FIRST_REQUEST_CODE = 100;
    private static final int MSG_BIND_PREFERENCES = 1;
    private static final String PREFERENCES_TAG = "android:preferences";
    private Handler mHandler;
    private boolean mHavePrefs;
    private boolean mInitDone;
    private int mLayoutResId = R.layout.preference_list_fragment;
    private TicklableRecyclerView mList;
    private PreferenceManager mPreferenceManager;
    private final Runnable mRequestFocus;
    private TextView mTitleView;

    public PreferenceFragment() {
        this.mHandler = new Handler(){

            public void handleMessage(Message message) {
                switch (message.what) {
                    default: {
                        return;
                    }
                    case 1: 
                }
                PreferenceFragment.this.bindPreferences();
            }
        };
        this.mRequestFocus = new Runnable(){

            @Override
            public void run() {
                PreferenceFragment.this.mList.focusableViewAvailable((View)PreferenceFragment.this.mList);
            }
        };
    }

    private void bindPreferences() {
        PreferenceScreen preferenceScreen = this.getPreferenceScreen();
        if (preferenceScreen != null) {
            preferenceScreen.bind(this.getListView());
            preferenceScreen.bindTitle(this.mTitleView);
        }
        this.onBindPreferences();
    }

    private void ensureList() {
        if (this.mList != null) {
            return;
        }
        View view = this.getView();
        if (view == null) {
            throw new IllegalStateException("Content view not yet created");
        }
        View view2 = view.findViewById(16908298);
        if (view2 == null) {
            throw new RuntimeException("Your content must have a TicklableRecyclerView whose id attribute is 'android.R.id.list'");
        }
        if (!(view2 instanceof TicklableRecyclerView)) {
            throw new RuntimeException("Content has view with id attribute 'android.R.id.list' that is not a TicklableRecyclerView class");
        }
        this.mList = (TicklableRecyclerView)view2;
        this.mList.setLayoutManager(new FocusableLinearLayoutManager((Context)this.getActivity()));
        this.mTitleView = (TextView)view.findViewById(16908310);
        this.mHandler.post(this.mRequestFocus);
    }

    private void postBindPreferences() {
        if (this.mHandler.hasMessages(1)) {
            return;
        }
        this.mHandler.obtainMessage(1).sendToTarget();
    }

    private void requirePreferenceManager() {
        if (this.mPreferenceManager == null) {
            throw new RuntimeException("This should be called after super.onCreate.");
        }
    }

    public void addPreferencesFromIntent(Intent intent) {
        this.requirePreferenceManager();
        this.setPreferenceScreen(this.mPreferenceManager.inflateFromIntent(intent, this.getPreferenceScreen()));
    }

    public void addPreferencesFromResource(@XmlRes int n2) {
        this.requirePreferenceManager();
        this.setPreferenceScreen(this.mPreferenceManager.inflateFromResource((Context)this.getActivity(), n2, this.getPreferenceScreen()));
    }

    public Preference findPreference(CharSequence charSequence) {
        if (this.mPreferenceManager == null) {
            return null;
        }
        return this.mPreferenceManager.findPreference(charSequence);
    }

    public TicklableRecyclerView getListView() {
        this.ensureList();
        return this.mList;
    }

    public PreferenceManager getPreferenceManager() {
        return this.mPreferenceManager;
    }

    public PreferenceScreen getPreferenceScreen() {
        return this.mPreferenceManager.getPreferenceScreen();
    }

    public boolean hasListView() {
        if (this.mList != null) {
            return true;
        }
        View view = this.getView();
        if (view == null) {
            return false;
        }
        if (!((view = view.findViewById(16908298)) instanceof TicklableRecyclerView)) {
            return false;
        }
        this.mList = (TicklableRecyclerView)view;
        return true;
    }

    public void onActivityCreated(@Nullable Bundle bundle) {
        PreferenceScreen preferenceScreen;
        super.onActivityCreated(bundle);
        if (this.mHavePrefs) {
            this.bindPreferences();
        }
        this.mInitDone = true;
        if (bundle != null && (bundle = bundle.getBundle(PREFERENCES_TAG)) != null && (preferenceScreen = this.getPreferenceScreen()) != null) {
            preferenceScreen.restoreHierarchyState(bundle);
        }
    }

    public void onActivityResult(int n2, int n3, Intent intent) {
        super.onActivityResult(n2, n3, intent);
        this.mPreferenceManager.dispatchActivityResult(n2, n3, intent);
    }

    protected void onBindPreferences() {
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.mPreferenceManager = new PreferenceManager(this.getActivity(), 100);
        this.mPreferenceManager.setFragment(this);
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        bundle = this.getActivity().obtainStyledAttributes(null, R.styleable.PreferenceFragment, R.attr.tic_preferenceFragmentStyle, 0);
        this.mLayoutResId = bundle.getResourceId(R.styleable.PreferenceFragment_android_layout, this.mLayoutResId);
        bundle.recycle();
        return layoutInflater.inflate(this.mLayoutResId, viewGroup, false);
    }

    public void onDestroy() {
        super.onDestroy();
        this.mPreferenceManager.dispatchActivityDestroy();
    }

    public void onDestroyView() {
        this.mList = null;
        this.mTitleView = null;
        this.mHandler.removeCallbacks(this.mRequestFocus);
        this.mHandler.removeMessages(1);
        super.onDestroyView();
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference.getFragment() != null && this.getActivity() instanceof OnPreferenceStartFragmentCallback) {
            return ((OnPreferenceStartFragmentCallback)this.getActivity()).onPreferenceStartFragment(this, preference);
        }
        return false;
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        PreferenceScreen preferenceScreen = this.getPreferenceScreen();
        if (preferenceScreen != null) {
            Bundle bundle2 = new Bundle();
            preferenceScreen.saveHierarchyState(bundle2);
            bundle.putBundle(PREFERENCES_TAG, bundle2);
        }
    }

    public void onStart() {
        super.onStart();
        this.mPreferenceManager.setOnPreferenceTreeClickListener(this);
    }

    public void onStop() {
        super.onStop();
        this.mPreferenceManager.dispatchActivityStop();
        this.mPreferenceManager.setOnPreferenceTreeClickListener(null);
    }

    protected void onUnbindPreferences() {
    }

    public void setPreferenceScreen(PreferenceScreen preferenceScreen) {
        if (this.mPreferenceManager.setPreferences(preferenceScreen) && preferenceScreen != null) {
            this.onUnbindPreferences();
            this.mHavePrefs = true;
            if (this.mInitDone) {
                this.postBindPreferences();
            }
        }
    }

    public static interface OnPreferenceStartFragmentCallback {
        public boolean onPreferenceStartFragment(PreferenceFragment var1, Preference var2);
    }
}

