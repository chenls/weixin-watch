/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.ResolveInfo
 *  android.os.Bundle
 *  android.util.Log
 *  org.xmlpull.v1.XmlPullParser
 */
package ticwear.design.preference;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import ticwear.design.preference.Preference;
import ticwear.design.preference.PreferenceFragment;
import ticwear.design.preference.PreferenceInflater;
import ticwear.design.preference.PreferenceScreen;

public class PreferenceManager {
    public static final String KEY_HAS_SET_DEFAULT_VALUES = "_has_set_default_values";
    public static final String METADATA_KEY_PREFERENCES = "android.preference";
    private static final String TAG = "PreferenceManager";
    private Activity mActivity;
    private List<OnActivityDestroyListener> mActivityDestroyListeners;
    private List<OnActivityResultListener> mActivityResultListeners;
    private List<OnActivityStopListener> mActivityStopListeners;
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private PreferenceFragment mFragment;
    private long mNextId = 0L;
    private int mNextRequestCode;
    private boolean mNoCommit;
    private OnPreferenceTreeClickListener mOnPreferenceTreeClickListener;
    private PreferenceScreen mPreferenceScreen;
    private List<DialogInterface> mPreferencesScreens;
    private SharedPreferences mSharedPreferences;
    private int mSharedPreferencesMode;
    private String mSharedPreferencesName;

    public PreferenceManager(Activity activity, int n2) {
        this.mActivity = activity;
        this.mNextRequestCode = n2;
        this.init((Context)activity);
    }

    PreferenceManager(Context context) {
        this.init(context);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void dismissAllScreens() {
        ArrayList<DialogInterface> arrayList;
        synchronized (this) {
            if (this.mPreferencesScreens == null) {
                return;
            }
            arrayList = new ArrayList<DialogInterface>(this.mPreferencesScreens);
            this.mPreferencesScreens.clear();
        }
        int n2 = arrayList.size() - 1;
        while (n2 >= 0) {
            arrayList.get(n2).dismiss();
            --n2;
        }
        return;
    }

    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        return context.getSharedPreferences(PreferenceManager.getDefaultSharedPreferencesName(context), PreferenceManager.getDefaultSharedPreferencesMode());
    }

    private static int getDefaultSharedPreferencesMode() {
        return 0;
    }

    private static String getDefaultSharedPreferencesName(Context context) {
        return context.getPackageName() + "_preferences";
    }

    private void init(Context context) {
        this.mContext = context;
        this.setSharedPreferencesName(PreferenceManager.getDefaultSharedPreferencesName(context));
    }

    private List<ResolveInfo> queryIntentActivities(Intent intent) {
        return this.mContext.getPackageManager().queryIntentActivities(intent, 128);
    }

    public static void setDefaultValues(Context context, int n2, boolean bl2) {
        PreferenceManager.setDefaultValues(context, PreferenceManager.getDefaultSharedPreferencesName(context), PreferenceManager.getDefaultSharedPreferencesMode(), n2, bl2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void setDefaultValues(Context context, String string2, int n2, int n3, boolean bl2) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_HAS_SET_DEFAULT_VALUES, 0);
        if (!bl2 && sharedPreferences.getBoolean(KEY_HAS_SET_DEFAULT_VALUES, false)) return;
        PreferenceManager preferenceManager = new PreferenceManager(context);
        preferenceManager.setSharedPreferencesName(string2);
        preferenceManager.setSharedPreferencesMode(n2);
        preferenceManager.inflateFromResource(context, n3, null);
        context = sharedPreferences.edit().putBoolean(KEY_HAS_SET_DEFAULT_VALUES, true);
        try {
            context.apply();
            return;
        }
        catch (AbstractMethodError abstractMethodError) {
            context.commit();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void setNoCommit(boolean bl2) {
        if (!bl2 && this.mEditor != null) {
            try {
                this.mEditor.apply();
            }
            catch (AbstractMethodError abstractMethodError) {
                this.mEditor.commit();
            }
        }
        this.mNoCommit = bl2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void addPreferencesScreen(DialogInterface dialogInterface) {
        synchronized (this) {
            if (this.mPreferencesScreens == null) {
                this.mPreferencesScreens = new ArrayList<DialogInterface>();
            }
            this.mPreferencesScreens.add(dialogInterface);
            return;
        }
    }

    public PreferenceScreen createPreferenceScreen(Context object) {
        object = new PreferenceScreen((Context)object, null);
        ((Preference)object).onAttachedToHierarchy(this);
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void dispatchActivityDestroy() {
        ArrayList<OnActivityDestroyListener> arrayList = null;
        // MONITORENTER : this
        if (this.mActivityDestroyListeners != null) {
            arrayList = new ArrayList<OnActivityDestroyListener>(this.mActivityDestroyListeners);
        }
        // MONITOREXIT : this
        if (arrayList != null) {
            int n2 = arrayList.size();
            for (int i2 = 0; i2 < n2; ++i2) {
                ((OnActivityDestroyListener)arrayList.get(i2)).onActivityDestroy();
            }
        }
        this.dismissAllScreens();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void dispatchActivityResult(int n2, int n3, Intent intent) {
        ArrayList<OnActivityResultListener> arrayList;
        synchronized (this) {
            if (this.mActivityResultListeners == null) {
                return;
            }
            arrayList = new ArrayList<OnActivityResultListener>(this.mActivityResultListeners);
        }
        int n4 = arrayList.size();
        for (int i2 = 0; i2 < n4 && !((OnActivityResultListener)arrayList.get(i2)).onActivityResult(n2, n3, intent); ++i2) {
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void dispatchActivityStop() {
        ArrayList<OnActivityStopListener> arrayList;
        synchronized (this) {
            if (this.mActivityStopListeners == null) {
                return;
            }
            arrayList = new ArrayList<OnActivityStopListener>(this.mActivityStopListeners);
        }
        int n2 = arrayList.size();
        int n3 = 0;
        while (n3 < n2) {
            ((OnActivityStopListener)arrayList.get(n3)).onActivityStop();
            ++n3;
        }
        return;
    }

    void dispatchNewIntent(Intent intent) {
        this.dismissAllScreens();
    }

    public Preference findPreference(CharSequence charSequence) {
        if (this.mPreferenceScreen == null) {
            return null;
        }
        return this.mPreferenceScreen.findPreference(charSequence);
    }

    Activity getActivity() {
        return this.mActivity;
    }

    Context getContext() {
        return this.mContext;
    }

    SharedPreferences.Editor getEditor() {
        if (this.mNoCommit) {
            if (this.mEditor == null) {
                this.mEditor = this.getSharedPreferences().edit();
            }
            return this.mEditor;
        }
        return this.getSharedPreferences().edit();
    }

    PreferenceFragment getFragment() {
        return this.mFragment;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    long getNextId() {
        synchronized (this) {
            long l2 = this.mNextId;
            this.mNextId = 1L + l2;
            return l2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    int getNextRequestCode() {
        synchronized (this) {
            int n2 = this.mNextRequestCode;
            this.mNextRequestCode = n2 + 1;
            return n2;
        }
    }

    OnPreferenceTreeClickListener getOnPreferenceTreeClickListener() {
        return this.mOnPreferenceTreeClickListener;
    }

    PreferenceScreen getPreferenceScreen() {
        return this.mPreferenceScreen;
    }

    public SharedPreferences getSharedPreferences() {
        if (this.mSharedPreferences == null) {
            this.mSharedPreferences = this.mContext.getSharedPreferences(this.mSharedPreferencesName, this.mSharedPreferencesMode);
        }
        return this.mSharedPreferences;
    }

    public int getSharedPreferencesMode() {
        return this.mSharedPreferencesMode;
    }

    public String getSharedPreferencesName() {
        return this.mSharedPreferencesName;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    PreferenceScreen inflateFromIntent(Intent object, PreferenceScreen preferenceScreen) {
        List<ResolveInfo> list = this.queryIntentActivities((Intent)object);
        HashSet<String> hashSet = new HashSet<String>();
        int n2 = list.size() - 1;
        while (true) {
            void var1_5;
            void var2_11;
            if (n2 < 0) {
                var2_11.onAttachedToHierarchy(this);
                return var2_11;
            }
            ActivityInfo activityInfo = list.get((int)n2).activityInfo;
            Bundle bundle = activityInfo.metaData;
            void var1_3 = var2_11;
            if (bundle != null) {
                if (!bundle.containsKey(METADATA_KEY_PREFERENCES)) {
                    void var1_4 = var2_11;
                } else {
                    String string2 = activityInfo.packageName + ":" + activityInfo.metaData.getInt(METADATA_KEY_PREFERENCES);
                    void var1_6 = var2_11;
                    if (!hashSet.contains(string2)) {
                        PreferenceInflater preferenceInflater;
                        Context context;
                        hashSet.add(string2);
                        try {
                            context = this.mContext.createPackageContext(activityInfo.packageName, 0);
                            preferenceInflater = new PreferenceInflater(context, this);
                        }
                        catch (PackageManager.NameNotFoundException nameNotFoundException) {
                            Log.w((String)TAG, (String)("Could not create context for " + activityInfo.packageName + ": " + Log.getStackTraceString((Throwable)nameNotFoundException)));
                            void var1_10 = var2_11;
                        }
                        activityInfo = activityInfo.loadXmlMetaData(context.getPackageManager(), METADATA_KEY_PREFERENCES);
                        PreferenceScreen preferenceScreen2 = (PreferenceScreen)preferenceInflater.inflate((XmlPullParser)activityInfo, var2_11, true);
                        activityInfo.close();
                    }
                }
            }
            --n2;
            var2_11 = var1_5;
        }
    }

    public PreferenceScreen inflateFromResource(Context object, int n2, PreferenceScreen preferenceScreen) {
        this.setNoCommit(true);
        object = (PreferenceScreen)new PreferenceInflater((Context)object, this).inflate(n2, preferenceScreen, true);
        ((Preference)object).onAttachedToHierarchy(this);
        this.setNoCommit(false);
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void registerOnActivityDestroyListener(OnActivityDestroyListener onActivityDestroyListener) {
        synchronized (this) {
            if (this.mActivityDestroyListeners == null) {
                this.mActivityDestroyListeners = new ArrayList<OnActivityDestroyListener>();
            }
            if (!this.mActivityDestroyListeners.contains(onActivityDestroyListener)) {
                this.mActivityDestroyListeners.add(onActivityDestroyListener);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void registerOnActivityResultListener(OnActivityResultListener onActivityResultListener) {
        synchronized (this) {
            if (this.mActivityResultListeners == null) {
                this.mActivityResultListeners = new ArrayList<OnActivityResultListener>();
            }
            if (!this.mActivityResultListeners.contains(onActivityResultListener)) {
                this.mActivityResultListeners.add(onActivityResultListener);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerOnActivityStopListener(OnActivityStopListener onActivityStopListener) {
        synchronized (this) {
            if (this.mActivityStopListeners == null) {
                this.mActivityStopListeners = new ArrayList<OnActivityStopListener>();
            }
            if (!this.mActivityStopListeners.contains(onActivityStopListener)) {
                this.mActivityStopListeners.add(onActivityStopListener);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void removePreferencesScreen(DialogInterface dialogInterface) {
        synchronized (this) {
            if (this.mPreferencesScreens == null) {
                return;
            }
            this.mPreferencesScreens.remove(dialogInterface);
            return;
        }
    }

    void setFragment(PreferenceFragment preferenceFragment) {
        this.mFragment = preferenceFragment;
    }

    void setOnPreferenceTreeClickListener(OnPreferenceTreeClickListener onPreferenceTreeClickListener) {
        this.mOnPreferenceTreeClickListener = onPreferenceTreeClickListener;
    }

    boolean setPreferences(PreferenceScreen preferenceScreen) {
        if (preferenceScreen != this.mPreferenceScreen) {
            this.mPreferenceScreen = preferenceScreen;
            return true;
        }
        return false;
    }

    public void setSharedPreferencesMode(int n2) {
        this.mSharedPreferencesMode = n2;
        this.mSharedPreferences = null;
    }

    public void setSharedPreferencesName(String string2) {
        this.mSharedPreferencesName = string2;
        this.mSharedPreferences = null;
    }

    boolean shouldCommit() {
        return !this.mNoCommit;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void unregisterOnActivityDestroyListener(OnActivityDestroyListener onActivityDestroyListener) {
        synchronized (this) {
            if (this.mActivityDestroyListeners != null) {
                this.mActivityDestroyListeners.remove(onActivityDestroyListener);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void unregisterOnActivityResultListener(OnActivityResultListener onActivityResultListener) {
        synchronized (this) {
            if (this.mActivityResultListeners != null) {
                this.mActivityResultListeners.remove(onActivityResultListener);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterOnActivityStopListener(OnActivityStopListener onActivityStopListener) {
        synchronized (this) {
            if (this.mActivityStopListeners != null) {
                this.mActivityStopListeners.remove(onActivityStopListener);
            }
            return;
        }
    }

    public static interface OnActivityDestroyListener {
        public void onActivityDestroy();
    }

    public static interface OnActivityResultListener {
        public boolean onActivityResult(int var1, int var2, Intent var3);
    }

    public static interface OnActivityStopListener {
        public void onActivityStop();
    }

    public static interface OnPreferenceTreeClickListener {
        public boolean onPreferenceTreeClick(PreferenceScreen var1, Preference var2);
    }
}

