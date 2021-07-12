/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.TypedArray
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Parcelable
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.animation.AccelerateInterpolator
 *  android.view.animation.AlphaAnimation
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.view.animation.AnimationSet
 *  android.view.animation.AnimationUtils
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.view.animation.ScaleAnimation
 */
package android.support.v4.app;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.v4.app.BackStackRecord;
import android.support.v4.app.BackStackState;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentContainer;
import android.support.v4.app.FragmentController;
import android.support.v4.app.FragmentHostCallback;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManagerNonConfig;
import android.support.v4.app.FragmentManagerState;
import android.support.v4.app.FragmentState;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NoSaveStateFrameLayout;
import android.support.v4.app.SuperNotCalledException;
import android.support.v4.os.BuildCompat;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.LogWriter;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class FragmentManagerImpl
extends FragmentManager
implements LayoutInflaterFactory {
    static final Interpolator ACCELERATE_CUBIC;
    static final Interpolator ACCELERATE_QUINT;
    static final int ANIM_DUR = 220;
    public static final int ANIM_STYLE_CLOSE_ENTER = 3;
    public static final int ANIM_STYLE_CLOSE_EXIT = 4;
    public static final int ANIM_STYLE_FADE_ENTER = 5;
    public static final int ANIM_STYLE_FADE_EXIT = 6;
    public static final int ANIM_STYLE_OPEN_ENTER = 1;
    public static final int ANIM_STYLE_OPEN_EXIT = 2;
    static boolean DEBUG = false;
    static final Interpolator DECELERATE_CUBIC;
    static final Interpolator DECELERATE_QUINT;
    static final boolean HONEYCOMB;
    static final String TAG = "FragmentManager";
    static final String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
    static final String TARGET_STATE_TAG = "android:target_state";
    static final String USER_VISIBLE_HINT_TAG = "android:user_visible_hint";
    static final String VIEW_STATE_TAG = "android:view_state";
    static Field sAnimationListenerField;
    ArrayList<Fragment> mActive;
    ArrayList<Fragment> mAdded;
    ArrayList<Integer> mAvailBackStackIndices;
    ArrayList<Integer> mAvailIndices;
    ArrayList<BackStackRecord> mBackStack;
    ArrayList<FragmentManager.OnBackStackChangedListener> mBackStackChangeListeners;
    ArrayList<BackStackRecord> mBackStackIndices;
    FragmentContainer mContainer;
    FragmentController mController;
    ArrayList<Fragment> mCreatedMenus;
    int mCurState = 0;
    boolean mDestroyed;
    Runnable mExecCommit = new Runnable(){

        @Override
        public void run() {
            FragmentManagerImpl.this.execPendingActions();
        }
    };
    boolean mExecutingActions;
    boolean mHavePendingDeferredStart;
    FragmentHostCallback mHost;
    boolean mNeedMenuInvalidate;
    String mNoTransactionsBecause;
    Fragment mParent;
    ArrayList<Runnable> mPendingActions;
    SparseArray<Parcelable> mStateArray = null;
    Bundle mStateBundle = null;
    boolean mStateSaved;
    Runnable[] mTmpActions;

    static {
        boolean bl2 = false;
        DEBUG = false;
        if (Build.VERSION.SDK_INT >= 11) {
            bl2 = true;
        }
        HONEYCOMB = bl2;
        sAnimationListenerField = null;
        DECELERATE_QUINT = new DecelerateInterpolator(2.5f);
        DECELERATE_CUBIC = new DecelerateInterpolator(1.5f);
        ACCELERATE_QUINT = new AccelerateInterpolator(2.5f);
        ACCELERATE_CUBIC = new AccelerateInterpolator(1.5f);
    }

    FragmentManagerImpl() {
    }

    private void checkStateLoss() {
        if (this.mStateSaved) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
        if (this.mNoTransactionsBecause != null) {
            throw new IllegalStateException("Can not perform this action inside of " + this.mNoTransactionsBecause);
        }
    }

    static Animation makeFadeAnimation(Context context, float f2, float f3) {
        context = new AlphaAnimation(f2, f3);
        context.setInterpolator(DECELERATE_CUBIC);
        context.setDuration(220L);
        return context;
    }

    static Animation makeOpenCloseAnimation(Context context, float f2, float f3, float f4, float f5) {
        context = new AnimationSet(false);
        ScaleAnimation scaleAnimation = new ScaleAnimation(f2, f3, f2, f3, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setInterpolator(DECELERATE_QUINT);
        scaleAnimation.setDuration(220L);
        context.addAnimation((Animation)scaleAnimation);
        scaleAnimation = new AlphaAnimation(f4, f5);
        scaleAnimation.setInterpolator(DECELERATE_CUBIC);
        scaleAnimation.setDuration(220L);
        context.addAnimation((Animation)scaleAnimation);
        return context;
    }

    /*
     * Enabled aggressive block sorting
     */
    static boolean modifiesAlpha(Animation object) {
        if (object instanceof AlphaAnimation) return true;
        if (!(object instanceof AnimationSet)) return false;
        object = ((AnimationSet)object).getAnimations();
        for (int i2 = 0; i2 < object.size(); ++i2) {
            if (!(object.get(i2) instanceof AlphaAnimation)) continue;
            return true;
        }
        return false;
    }

    public static int reverseTransit(int n2) {
        switch (n2) {
            default: {
                return 0;
            }
            case 4097: {
                return 8194;
            }
            case 8194: {
                return 4097;
            }
            case 4099: 
        }
        return 4099;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void setHWLayerAnimListenerIfAlpha(View view, Animation animation) {
        if (view == null || animation == null || !FragmentManagerImpl.shouldRunOnHWLayer(view, animation)) {
            return;
        }
        Animation.AnimationListener animationListener = null;
        try {
            Animation.AnimationListener animationListener2;
            if (sAnimationListenerField == null) {
                sAnimationListenerField = Animation.class.getDeclaredField("mListener");
                sAnimationListenerField.setAccessible(true);
            }
            animationListener = animationListener2 = (Animation.AnimationListener)sAnimationListenerField.get(animation);
        }
        catch (NoSuchFieldException noSuchFieldException) {
            Log.e((String)TAG, (String)"No field with the name mListener is found in Animation class", (Throwable)noSuchFieldException);
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.e((String)TAG, (String)"Cannot access Animation's mListener field", (Throwable)illegalAccessException);
        }
        ViewCompat.setLayerType(view, 2, null);
        animation.setAnimationListener((Animation.AnimationListener)new AnimateOnHWLayerIfNeededListener(view, animation, animationListener));
    }

    static boolean shouldRunOnHWLayer(View view, Animation animation) {
        return Build.VERSION.SDK_INT >= 19 && ViewCompat.getLayerType(view) == 0 && ViewCompat.hasOverlappingRendering(view) && FragmentManagerImpl.modifiesAlpha(animation);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void throwException(RuntimeException runtimeException) {
        Log.e((String)TAG, (String)runtimeException.getMessage());
        Log.e((String)TAG, (String)"Activity state:");
        PrintWriter printWriter = new PrintWriter(new LogWriter(TAG));
        if (this.mHost != null) {
            try {
                this.mHost.onDump("  ", null, printWriter, new String[0]);
            }
            catch (Exception exception) {
                Log.e((String)TAG, (String)"Failed dumping state", (Throwable)exception);
                throw runtimeException;
            }
            throw runtimeException;
        }
        try {
            this.dump("  ", null, printWriter, new String[0]);
            throw runtimeException;
        }
        catch (Exception exception) {
            Log.e((String)TAG, (String)"Failed dumping state", (Throwable)exception);
            throw runtimeException;
        }
    }

    public static int transitToStyleIndex(int n2, boolean bl2) {
        switch (n2) {
            default: {
                return -1;
            }
            case 4097: {
                if (bl2) {
                    return 1;
                }
                return 2;
            }
            case 8194: {
                if (bl2) {
                    return 3;
                }
                return 4;
            }
            case 4099: 
        }
        if (bl2) {
            return 5;
        }
        return 6;
    }

    void addBackStackState(BackStackRecord backStackRecord) {
        if (this.mBackStack == null) {
            this.mBackStack = new ArrayList();
        }
        this.mBackStack.add(backStackRecord);
        this.reportBackStackChanged();
    }

    public void addFragment(Fragment fragment, boolean bl2) {
        if (this.mAdded == null) {
            this.mAdded = new ArrayList();
        }
        if (DEBUG) {
            Log.v((String)TAG, (String)("add: " + fragment));
        }
        this.makeActive(fragment);
        if (!fragment.mDetached) {
            if (this.mAdded.contains(fragment)) {
                throw new IllegalStateException("Fragment already added: " + fragment);
            }
            this.mAdded.add(fragment);
            fragment.mAdded = true;
            fragment.mRemoving = false;
            if (fragment.mHasMenu && fragment.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            if (bl2) {
                this.moveToState(fragment);
            }
        }
    }

    @Override
    public void addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener onBackStackChangedListener) {
        if (this.mBackStackChangeListeners == null) {
            this.mBackStackChangeListeners = new ArrayList();
        }
        this.mBackStackChangeListeners.add(onBackStackChangedListener);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int allocBackStackIndex(BackStackRecord backStackRecord) {
        synchronized (this) {
            if (this.mAvailBackStackIndices == null || this.mAvailBackStackIndices.size() <= 0) {
                if (this.mBackStackIndices == null) {
                    this.mBackStackIndices = new ArrayList();
                }
                int n2 = this.mBackStackIndices.size();
                if (DEBUG) {
                    Log.v((String)TAG, (String)("Setting back stack index " + n2 + " to " + backStackRecord));
                }
                this.mBackStackIndices.add(backStackRecord);
                return n2;
            }
            int n3 = this.mAvailBackStackIndices.remove(this.mAvailBackStackIndices.size() - 1);
            if (DEBUG) {
                Log.v((String)TAG, (String)("Adding back stack index " + n3 + " with " + backStackRecord));
            }
            this.mBackStackIndices.set(n3, backStackRecord);
            return n3;
        }
    }

    public void attachController(FragmentHostCallback fragmentHostCallback, FragmentContainer fragmentContainer, Fragment fragment) {
        if (this.mHost != null) {
            throw new IllegalStateException("Already attached");
        }
        this.mHost = fragmentHostCallback;
        this.mContainer = fragmentContainer;
        this.mParent = fragment;
    }

    public void attachFragment(Fragment fragment, int n2, int n3) {
        if (DEBUG) {
            Log.v((String)TAG, (String)("attach: " + fragment));
        }
        if (fragment.mDetached) {
            fragment.mDetached = false;
            if (!fragment.mAdded) {
                if (this.mAdded == null) {
                    this.mAdded = new ArrayList();
                }
                if (this.mAdded.contains(fragment)) {
                    throw new IllegalStateException("Fragment already added: " + fragment);
                }
                if (DEBUG) {
                    Log.v((String)TAG, (String)("add from attach: " + fragment));
                }
                this.mAdded.add(fragment);
                fragment.mAdded = true;
                if (fragment.mHasMenu && fragment.mMenuVisible) {
                    this.mNeedMenuInvalidate = true;
                }
                this.moveToState(fragment, this.mCurState, n2, n3, false);
            }
        }
    }

    @Override
    public FragmentTransaction beginTransaction() {
        return new BackStackRecord(this);
    }

    public void detachFragment(Fragment fragment, int n2, int n3) {
        if (DEBUG) {
            Log.v((String)TAG, (String)("detach: " + fragment));
        }
        if (!fragment.mDetached) {
            fragment.mDetached = true;
            if (fragment.mAdded) {
                if (this.mAdded != null) {
                    if (DEBUG) {
                        Log.v((String)TAG, (String)("remove from detach: " + fragment));
                    }
                    this.mAdded.remove(fragment);
                }
                if (fragment.mHasMenu && fragment.mMenuVisible) {
                    this.mNeedMenuInvalidate = true;
                }
                fragment.mAdded = false;
                this.moveToState(fragment, 1, n2, n3, false);
            }
        }
    }

    public void dispatchActivityCreated() {
        this.mStateSaved = false;
        this.moveToState(2, false);
    }

    public void dispatchConfigurationChanged(Configuration configuration) {
        if (this.mAdded != null) {
            for (int i2 = 0; i2 < this.mAdded.size(); ++i2) {
                Fragment fragment = this.mAdded.get(i2);
                if (fragment == null) continue;
                fragment.performConfigurationChanged(configuration);
            }
        }
    }

    public boolean dispatchContextItemSelected(MenuItem menuItem) {
        if (this.mAdded != null) {
            for (int i2 = 0; i2 < this.mAdded.size(); ++i2) {
                Fragment fragment = this.mAdded.get(i2);
                if (fragment == null || !fragment.performContextItemSelected(menuItem)) continue;
                return true;
            }
        }
        return false;
    }

    public void dispatchCreate() {
        this.mStateSaved = false;
        this.moveToState(1, false);
    }

    public boolean dispatchCreateOptionsMenu(Menu object, MenuInflater menuInflater) {
        int n2;
        boolean bl2 = false;
        boolean bl3 = false;
        ArrayList<Fragment> arrayList = null;
        ArrayList<Fragment> arrayList2 = null;
        if (this.mAdded != null) {
            n2 = 0;
            while (true) {
                arrayList = arrayList2;
                bl2 = bl3;
                if (n2 >= this.mAdded.size()) break;
                Fragment fragment = this.mAdded.get(n2);
                arrayList = arrayList2;
                bl2 = bl3;
                if (fragment != null) {
                    arrayList = arrayList2;
                    bl2 = bl3;
                    if (fragment.performCreateOptionsMenu((Menu)object, menuInflater)) {
                        bl2 = true;
                        arrayList = arrayList2;
                        if (arrayList2 == null) {
                            arrayList = new ArrayList<Fragment>();
                        }
                        arrayList.add(fragment);
                    }
                }
                ++n2;
                arrayList2 = arrayList;
                bl3 = bl2;
            }
        }
        if (this.mCreatedMenus != null) {
            for (n2 = 0; n2 < this.mCreatedMenus.size(); ++n2) {
                object = this.mCreatedMenus.get(n2);
                if (arrayList != null && arrayList.contains(object)) continue;
                ((Fragment)object).onDestroyOptionsMenu();
            }
        }
        this.mCreatedMenus = arrayList;
        return bl2;
    }

    public void dispatchDestroy() {
        this.mDestroyed = true;
        this.execPendingActions();
        this.moveToState(0, false);
        this.mHost = null;
        this.mContainer = null;
        this.mParent = null;
    }

    public void dispatchDestroyView() {
        this.moveToState(1, false);
    }

    public void dispatchLowMemory() {
        if (this.mAdded != null) {
            for (int i2 = 0; i2 < this.mAdded.size(); ++i2) {
                Fragment fragment = this.mAdded.get(i2);
                if (fragment == null) continue;
                fragment.performLowMemory();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void dispatchMultiWindowModeChanged(boolean bl2) {
        if (this.mAdded != null) {
            for (int i2 = this.mAdded.size() - 1; i2 >= 0; --i2) {
                Fragment fragment = this.mAdded.get(i2);
                if (fragment == null) continue;
                fragment.performMultiWindowModeChanged(bl2);
            }
        }
    }

    public boolean dispatchOptionsItemSelected(MenuItem menuItem) {
        if (this.mAdded != null) {
            for (int i2 = 0; i2 < this.mAdded.size(); ++i2) {
                Fragment fragment = this.mAdded.get(i2);
                if (fragment == null || !fragment.performOptionsItemSelected(menuItem)) continue;
                return true;
            }
        }
        return false;
    }

    public void dispatchOptionsMenuClosed(Menu menu) {
        if (this.mAdded != null) {
            for (int i2 = 0; i2 < this.mAdded.size(); ++i2) {
                Fragment fragment = this.mAdded.get(i2);
                if (fragment == null) continue;
                fragment.performOptionsMenuClosed(menu);
            }
        }
    }

    public void dispatchPause() {
        this.moveToState(4, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void dispatchPictureInPictureModeChanged(boolean bl2) {
        if (this.mAdded != null) {
            for (int i2 = this.mAdded.size() - 1; i2 >= 0; --i2) {
                Fragment fragment = this.mAdded.get(i2);
                if (fragment == null) continue;
                fragment.performPictureInPictureModeChanged(bl2);
            }
        }
    }

    public boolean dispatchPrepareOptionsMenu(Menu menu) {
        boolean bl2 = false;
        boolean bl3 = false;
        if (this.mAdded != null) {
            int n2 = 0;
            while (true) {
                bl2 = bl3;
                if (n2 >= this.mAdded.size()) break;
                Fragment fragment = this.mAdded.get(n2);
                bl2 = bl3;
                if (fragment != null) {
                    bl2 = bl3;
                    if (fragment.performPrepareOptionsMenu(menu)) {
                        bl2 = true;
                    }
                }
                ++n2;
                bl3 = bl2;
            }
        }
        return bl2;
    }

    public void dispatchReallyStop() {
        this.moveToState(2, false);
    }

    public void dispatchResume() {
        this.mStateSaved = false;
        this.moveToState(5, false);
    }

    public void dispatchStart() {
        this.mStateSaved = false;
        this.moveToState(4, false);
    }

    public void dispatchStop() {
        this.mStateSaved = true;
        this.moveToState(3, false);
    }

    void doPendingDeferredStart() {
        if (this.mHavePendingDeferredStart) {
            boolean bl2 = false;
            for (int i2 = 0; i2 < this.mActive.size(); ++i2) {
                Fragment fragment = this.mActive.get(i2);
                boolean bl3 = bl2;
                if (fragment != null) {
                    bl3 = bl2;
                    if (fragment.mLoaderManager != null) {
                        bl3 = bl2 | fragment.mLoaderManager.hasRunningLoaders();
                    }
                }
                bl2 = bl3;
            }
            if (!bl2) {
                this.mHavePendingDeferredStart = false;
                this.startPendingDeferredFragments();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void dump(String string2, FileDescriptor object, PrintWriter printWriter, String[] stringArray) {
        Object object2;
        int n2;
        int n3;
        String string3 = string2 + "    ";
        if (this.mActive != null && (n3 = this.mActive.size()) > 0) {
            printWriter.print(string2);
            printWriter.print("Active Fragments in ");
            printWriter.print(Integer.toHexString(System.identityHashCode(this)));
            printWriter.println(":");
            for (n2 = 0; n2 < n3; ++n2) {
                object2 = this.mActive.get(n2);
                printWriter.print(string2);
                printWriter.print("  #");
                printWriter.print(n2);
                printWriter.print(": ");
                printWriter.println(object2);
                if (object2 == null) continue;
                ((Fragment)object2).dump(string3, (FileDescriptor)object, printWriter, stringArray);
            }
        }
        if (this.mAdded != null && (n3 = this.mAdded.size()) > 0) {
            printWriter.print(string2);
            printWriter.println("Added Fragments:");
            for (n2 = 0; n2 < n3; ++n2) {
                object2 = this.mAdded.get(n2);
                printWriter.print(string2);
                printWriter.print("  #");
                printWriter.print(n2);
                printWriter.print(": ");
                printWriter.println(((Fragment)object2).toString());
            }
        }
        if (this.mCreatedMenus != null && (n3 = this.mCreatedMenus.size()) > 0) {
            printWriter.print(string2);
            printWriter.println("Fragments Created Menus:");
            for (n2 = 0; n2 < n3; ++n2) {
                object2 = this.mCreatedMenus.get(n2);
                printWriter.print(string2);
                printWriter.print("  #");
                printWriter.print(n2);
                printWriter.print(": ");
                printWriter.println(((Fragment)object2).toString());
            }
        }
        if (this.mBackStack != null && (n3 = this.mBackStack.size()) > 0) {
            printWriter.print(string2);
            printWriter.println("Back Stack:");
            for (n2 = 0; n2 < n3; ++n2) {
                object2 = this.mBackStack.get(n2);
                printWriter.print(string2);
                printWriter.print("  #");
                printWriter.print(n2);
                printWriter.print(": ");
                printWriter.println(((BackStackRecord)object2).toString());
                ((BackStackRecord)object2).dump(string3, (FileDescriptor)object, printWriter, stringArray);
            }
        }
        // MONITORENTER : this
        if (this.mBackStackIndices != null && (n3 = this.mBackStackIndices.size()) > 0) {
            printWriter.print(string2);
            printWriter.println("Back Stack Indices:");
            for (n2 = 0; n2 < n3; ++n2) {
                object = this.mBackStackIndices.get(n2);
                printWriter.print(string2);
                printWriter.print("  #");
                printWriter.print(n2);
                printWriter.print(": ");
                printWriter.println(object);
            }
        }
        if (this.mAvailBackStackIndices != null && this.mAvailBackStackIndices.size() > 0) {
            printWriter.print(string2);
            printWriter.print("mAvailBackStackIndices: ");
            printWriter.println(Arrays.toString(this.mAvailBackStackIndices.toArray()));
        }
        // MONITOREXIT : this
        if (this.mPendingActions != null && (n3 = this.mPendingActions.size()) > 0) {
            printWriter.print(string2);
            printWriter.println("Pending Actions:");
            for (n2 = 0; n2 < n3; ++n2) {
                object = this.mPendingActions.get(n2);
                printWriter.print(string2);
                printWriter.print("  #");
                printWriter.print(n2);
                printWriter.print(": ");
                printWriter.println(object);
            }
        }
        printWriter.print(string2);
        printWriter.println("FragmentManager misc state:");
        printWriter.print(string2);
        printWriter.print("  mHost=");
        printWriter.println(this.mHost);
        printWriter.print(string2);
        printWriter.print("  mContainer=");
        printWriter.println(this.mContainer);
        if (this.mParent != null) {
            printWriter.print(string2);
            printWriter.print("  mParent=");
            printWriter.println(this.mParent);
        }
        printWriter.print(string2);
        printWriter.print("  mCurState=");
        printWriter.print(this.mCurState);
        printWriter.print(" mStateSaved=");
        printWriter.print(this.mStateSaved);
        printWriter.print(" mDestroyed=");
        printWriter.println(this.mDestroyed);
        if (this.mNeedMenuInvalidate) {
            printWriter.print(string2);
            printWriter.print("  mNeedMenuInvalidate=");
            printWriter.println(this.mNeedMenuInvalidate);
        }
        if (this.mNoTransactionsBecause != null) {
            printWriter.print(string2);
            printWriter.print("  mNoTransactionsBecause=");
            printWriter.println(this.mNoTransactionsBecause);
        }
        if (this.mAvailIndices == null) return;
        if (this.mAvailIndices.size() <= 0) return;
        printWriter.print(string2);
        printWriter.print("  mAvailIndices: ");
        printWriter.println(Arrays.toString(this.mAvailIndices.toArray()));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void enqueueAction(Runnable runnable, boolean bl2) {
        if (!bl2) {
            this.checkStateLoss();
        }
        synchronized (this) {
            if (this.mDestroyed || this.mHost == null) {
                throw new IllegalStateException("Activity has been destroyed");
            }
            if (this.mPendingActions == null) {
                this.mPendingActions = new ArrayList();
            }
            this.mPendingActions.add(runnable);
            if (this.mPendingActions.size() == 1) {
                this.mHost.getHandler().removeCallbacks(this.mExecCommit);
                this.mHost.getHandler().post(this.mExecCommit);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean execPendingActions() {
        if (this.mExecutingActions) {
            throw new IllegalStateException("FragmentManager is already executing transactions");
        }
        if (Looper.myLooper() != this.mHost.getHandler().getLooper()) {
            throw new IllegalStateException("Must be called from main thread of fragment host");
        }
        boolean bl2 = false;
        while (true) {
            int n2;
            synchronized (this) {
                if (this.mPendingActions == null || this.mPendingActions.size() == 0) {
                    // MONITOREXIT @DISABLED, blocks:[3, 4, 7] lbl9 : MonitorExitStatement: MONITOREXIT : this
                    this.doPendingDeferredStart();
                    return bl2;
                }
                n2 = this.mPendingActions.size();
                if (this.mTmpActions == null || this.mTmpActions.length < n2) {
                    this.mTmpActions = new Runnable[n2];
                }
                this.mPendingActions.toArray(this.mTmpActions);
                this.mPendingActions.clear();
                this.mHost.getHandler().removeCallbacks(this.mExecCommit);
            }
            this.mExecutingActions = true;
            for (int i2 = 0; i2 < n2; ++i2) {
                this.mTmpActions[i2].run();
                this.mTmpActions[i2] = null;
            }
            this.mExecutingActions = false;
            bl2 = true;
        }
    }

    public void execSingleAction(Runnable runnable, boolean bl2) {
        if (this.mExecutingActions) {
            throw new IllegalStateException("FragmentManager is already executing transactions");
        }
        if (Looper.myLooper() != this.mHost.getHandler().getLooper()) {
            throw new IllegalStateException("Must be called from main thread of fragment host");
        }
        if (!bl2) {
            this.checkStateLoss();
        }
        this.mExecutingActions = true;
        runnable.run();
        this.mExecutingActions = false;
        this.doPendingDeferredStart();
    }

    @Override
    public boolean executePendingTransactions() {
        return this.execPendingActions();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Fragment findFragmentById(int n2) {
        Fragment fragment;
        int n3;
        if (this.mAdded != null) {
            for (n3 = this.mAdded.size() - 1; n3 >= 0; --n3) {
                fragment = this.mAdded.get(n3);
                if (fragment != null && fragment.mFragmentId == n2) return fragment;
            }
        }
        if (this.mActive == null) return null;
        for (n3 = this.mActive.size() - 1; n3 >= 0; --n3) {
            Fragment fragment2 = this.mActive.get(n3);
            if (fragment2 == null) continue;
            fragment = fragment2;
            if (fragment2.mFragmentId != n2) continue;
            return fragment;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Fragment findFragmentByTag(String string2) {
        Fragment fragment;
        int n2;
        if (this.mAdded != null && string2 != null) {
            for (n2 = this.mAdded.size() - 1; n2 >= 0; --n2) {
                fragment = this.mAdded.get(n2);
                if (fragment != null && string2.equals(fragment.mTag)) return fragment;
            }
        }
        if (this.mActive == null || string2 == null) return null;
        for (n2 = this.mActive.size() - 1; n2 >= 0; --n2) {
            Fragment fragment2 = this.mActive.get(n2);
            if (fragment2 == null) continue;
            fragment = fragment2;
            if (!string2.equals(fragment2.mTag)) continue;
            return fragment;
        }
        return null;
    }

    public Fragment findFragmentByWho(String string2) {
        if (this.mActive != null && string2 != null) {
            for (int i2 = this.mActive.size() - 1; i2 >= 0; --i2) {
                Fragment fragment = this.mActive.get(i2);
                if (fragment == null || (fragment = fragment.findFragmentByWho(string2)) == null) continue;
                return fragment;
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void freeBackStackIndex(int n2) {
        synchronized (this) {
            this.mBackStackIndices.set(n2, null);
            if (this.mAvailBackStackIndices == null) {
                this.mAvailBackStackIndices = new ArrayList();
            }
            if (DEBUG) {
                Log.v((String)TAG, (String)("Freeing back stack index " + n2));
            }
            this.mAvailBackStackIndices.add(n2);
            return;
        }
    }

    @Override
    public FragmentManager.BackStackEntry getBackStackEntryAt(int n2) {
        return this.mBackStack.get(n2);
    }

    @Override
    public int getBackStackEntryCount() {
        if (this.mBackStack != null) {
            return this.mBackStack.size();
        }
        return 0;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public Fragment getFragment(Bundle object, String string2) {
        Fragment fragment;
        void var1_3;
        void var2_5;
        int n2 = object.getInt((String)var2_5, -1);
        if (n2 == -1) {
            return var1_3;
        }
        if (n2 >= this.mActive.size()) {
            this.throwException(new IllegalStateException("Fragment no longer exists for key " + (String)var2_5 + ": index " + n2));
        }
        Fragment fragment2 = fragment = this.mActive.get(n2);
        if (fragment != null) {
            return var1_3;
        }
        this.throwException(new IllegalStateException("Fragment no longer exists for key " + (String)var2_5 + ": index " + n2));
        return fragment;
    }

    @Override
    public List<Fragment> getFragments() {
        return this.mActive;
    }

    LayoutInflaterFactory getLayoutInflaterFactory() {
        return this;
    }

    public void hideFragment(Fragment fragment, int n2, int n3) {
        if (DEBUG) {
            Log.v((String)TAG, (String)("hide: " + fragment));
        }
        if (!fragment.mHidden) {
            fragment.mHidden = true;
            if (fragment.mView != null) {
                Animation animation = this.loadAnimation(fragment, n2, false, n3);
                if (animation != null) {
                    this.setHWLayerAnimListenerIfAlpha(fragment.mView, animation);
                    fragment.mView.startAnimation(animation);
                }
                fragment.mView.setVisibility(8);
            }
            if (fragment.mAdded && fragment.mHasMenu && fragment.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            fragment.onHiddenChanged(true);
        }
    }

    @Override
    public boolean isDestroyed() {
        return this.mDestroyed;
    }

    boolean isStateAtLeast(int n2) {
        return this.mCurState >= n2;
    }

    Animation loadAnimation(Fragment fragment, int n2, boolean bl2, int n3) {
        Animation animation = fragment.onCreateAnimation(n2, bl2, fragment.mNextAnim);
        if (animation != null) {
            return animation;
        }
        if (fragment.mNextAnim != 0 && (fragment = AnimationUtils.loadAnimation((Context)this.mHost.getContext(), (int)fragment.mNextAnim)) != null) {
            return fragment;
        }
        if (n2 == 0) {
            return null;
        }
        if ((n2 = FragmentManagerImpl.transitToStyleIndex(n2, bl2)) < 0) {
            return null;
        }
        switch (n2) {
            default: {
                n2 = n3;
                if (n3 == 0) {
                    n2 = n3;
                    if (this.mHost.onHasWindowAnimations()) {
                        n2 = this.mHost.onGetWindowAnimations();
                    }
                }
                if (n2 != 0) break;
                return null;
            }
            case 1: {
                return FragmentManagerImpl.makeOpenCloseAnimation(this.mHost.getContext(), 1.125f, 1.0f, 0.0f, 1.0f);
            }
            case 2: {
                return FragmentManagerImpl.makeOpenCloseAnimation(this.mHost.getContext(), 1.0f, 0.975f, 1.0f, 0.0f);
            }
            case 3: {
                return FragmentManagerImpl.makeOpenCloseAnimation(this.mHost.getContext(), 0.975f, 1.0f, 0.0f, 1.0f);
            }
            case 4: {
                return FragmentManagerImpl.makeOpenCloseAnimation(this.mHost.getContext(), 1.0f, 1.075f, 1.0f, 0.0f);
            }
            case 5: {
                return FragmentManagerImpl.makeFadeAnimation(this.mHost.getContext(), 0.0f, 1.0f);
            }
            case 6: {
                return FragmentManagerImpl.makeFadeAnimation(this.mHost.getContext(), 1.0f, 0.0f);
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    void makeActive(Fragment fragment) {
        block7: {
            block6: {
                if (fragment.mIndex >= 0) break block6;
                if (this.mAvailIndices == null || this.mAvailIndices.size() <= 0) {
                    if (this.mActive == null) {
                        this.mActive = new ArrayList();
                    }
                    fragment.setIndex(this.mActive.size(), this.mParent);
                    this.mActive.add(fragment);
                } else {
                    fragment.setIndex(this.mAvailIndices.remove(this.mAvailIndices.size() - 1), this.mParent);
                    this.mActive.set(fragment.mIndex, fragment);
                }
                if (DEBUG) break block7;
            }
            return;
        }
        Log.v((String)TAG, (String)("Allocated fragment index " + fragment));
    }

    void makeInactive(Fragment fragment) {
        if (fragment.mIndex < 0) {
            return;
        }
        if (DEBUG) {
            Log.v((String)TAG, (String)("Freeing fragment index " + fragment));
        }
        this.mActive.set(fragment.mIndex, null);
        if (this.mAvailIndices == null) {
            this.mAvailIndices = new ArrayList();
        }
        this.mAvailIndices.add(fragment.mIndex);
        this.mHost.inactivateFragment(fragment.mWho);
        fragment.initState();
    }

    /*
     * Enabled aggressive block sorting
     */
    void moveToState(int n2, int n3, int n4, boolean bl2) {
        block9: {
            block8: {
                if (this.mHost == null && n2 != 0) {
                    throw new IllegalStateException("No host");
                }
                if (!bl2 && this.mCurState == n2) break block8;
                this.mCurState = n2;
                if (this.mActive == null) break block8;
                boolean bl3 = false;
                for (int i2 = 0; i2 < this.mActive.size(); ++i2) {
                    Fragment fragment = this.mActive.get(i2);
                    boolean bl4 = bl3;
                    if (fragment != null) {
                        this.moveToState(fragment, n2, n3, n4, false);
                        bl4 = bl3;
                        if (fragment.mLoaderManager != null) {
                            bl4 = bl3 | fragment.mLoaderManager.hasRunningLoaders();
                        }
                    }
                    bl3 = bl4;
                }
                if (!bl3) {
                    this.startPendingDeferredFragments();
                }
                if (this.mNeedMenuInvalidate && this.mHost != null && this.mCurState == 5) break block9;
            }
            return;
        }
        this.mHost.onSupportInvalidateOptionsMenu();
        this.mNeedMenuInvalidate = false;
    }

    void moveToState(int n2, boolean bl2) {
        this.moveToState(n2, 0, 0, bl2);
    }

    void moveToState(Fragment fragment) {
        this.moveToState(fragment, this.mCurState, 0, 0, false);
    }

    /*
     * Handled duff style switch with additional control
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void moveToState(final Fragment var1_1, int var2_2, int var3_3, int var4_4, boolean var5_5) {
        block90: {
            block89: {
                block85: {
                    block88: {
                        block87: {
                            block86: {
                                if (!var1_1.mAdded) break block86;
                                var6_6 = var2_2;
                                if (!var1_1.mDetached) break block87;
                            }
                            var6_6 = var2_2;
                            if (var2_2 > 1) {
                                var6_6 = 1;
                            }
                        }
                        var7_7 = var6_6;
                        if (var1_1.mRemoving) {
                            var7_7 = var6_6;
                            if (var6_6 > var1_1.mState) {
                                var7_7 = var1_1.mState;
                            }
                        }
                        var2_2 = var7_7;
                        if (var1_1.mDeferStart) {
                            var2_2 = var7_7;
                            if (var1_1.mState < 4) {
                                var2_2 = var7_7;
                                if (var7_7 > 3) {
                                    var2_2 = 3;
                                }
                            }
                        }
                        if (var1_1.mState >= var2_2) break block88;
                        if (var1_1.mFromLayout && !var1_1.mInLayout) break block89;
                        if (var1_1.mAnimatingAway != null) {
                            var1_1.mAnimatingAway = null;
                            this.moveToState(var1_1, var1_1.mStateAfterAnimating, 0, 0, true);
                        }
                        var6_6 = var2_2;
                        var8_8 = var2_2;
                        var9_9 = var2_2;
                        var7_7 = var2_2;
                        cfr_temp_0 = -2147483648;
                        block16: do {
                            switch (cfr_temp_0 == -2147483648 ? var1_1.mState : cfr_temp_0) {
                                default: {
                                    var6_6 = var2_2;
                                    break block85;
                                }
                                case 0: {
                                    if (FragmentManagerImpl.DEBUG) {
                                        Log.v((String)"FragmentManager", (String)("moveto CREATED: " + var1_1));
                                    }
                                    var7_7 = var2_2;
                                    if (var1_1.mSavedFragmentState != null) {
                                        var1_1.mSavedFragmentState.setClassLoader(this.mHost.getContext().getClassLoader());
                                        var1_1.mSavedViewState = var1_1.mSavedFragmentState.getSparseParcelableArray("android:view_state");
                                        var1_1.mTarget = this.getFragment(var1_1.mSavedFragmentState, "android:target_state");
                                        if (var1_1.mTarget != null) {
                                            var1_1.mTargetRequestCode = var1_1.mSavedFragmentState.getInt("android:target_req_state", 0);
                                        }
                                        var1_1.mUserVisibleHint = var1_1.mSavedFragmentState.getBoolean("android:user_visible_hint", true);
                                        var7_7 = var2_2;
                                        if (!var1_1.mUserVisibleHint) {
                                            var1_1.mDeferStart = true;
                                            var7_7 = var2_2;
                                            if (var2_2 > 3) {
                                                var7_7 = 3;
                                            }
                                        }
                                    }
                                    var1_1.mHost = this.mHost;
                                    var1_1.mParentFragment = this.mParent;
                                    var10_10 = this.mParent != null ? this.mParent.mChildFragmentManager : this.mHost.getFragmentManagerImpl();
                                    var1_1.mFragmentManager = var10_10;
                                    var1_1.mCalled = false;
                                    var1_1.onAttach(this.mHost.getContext());
                                    if (!var1_1.mCalled) {
                                        throw new SuperNotCalledException("Fragment " + var1_1 + " did not call through to super.onAttach()");
                                    }
                                    if (var1_1.mParentFragment == null) {
                                        this.mHost.onAttachFragment(var1_1);
                                    } else {
                                        var1_1.mParentFragment.onAttachFragment(var1_1);
                                    }
                                    if (!var1_1.mRetaining) {
                                        var1_1.performCreate(var1_1.mSavedFragmentState);
                                    } else {
                                        var1_1.restoreChildFragmentState(var1_1.mSavedFragmentState);
                                        var1_1.mState = 1;
                                    }
                                    var1_1.mRetaining = false;
                                    var6_6 = var7_7;
                                    if (!var1_1.mFromLayout) ** GOTO lbl85
                                    var1_1.mView = var1_1.performCreateView(var1_1.getLayoutInflater(var1_1.mSavedFragmentState), null, var1_1.mSavedFragmentState);
                                    if (var1_1.mView == null) ** GOTO lbl87
                                    var1_1.mInnerView = var1_1.mView;
                                    if (Build.VERSION.SDK_INT >= 11) {
                                        ViewCompat.setSaveFromParentEnabled(var1_1.mView, false);
                                    } else {
                                        var1_1.mView = NoSaveStateFrameLayout.wrap(var1_1.mView);
                                    }
                                    if (var1_1.mHidden) {
                                        var1_1.mView.setVisibility(8);
                                    }
                                    var1_1.onViewCreated(var1_1.mView, var1_1.mSavedFragmentState);
                                    var6_6 = var7_7;
                                }
lbl85:
                                // 3 sources

                                case 1: {
                                    ** GOTO lbl89
                                }
lbl87:
                                // 1 sources

                                var1_1.mInnerView = null;
                                var6_6 = var7_7;
lbl89:
                                // 2 sources

                                var8_8 = var6_6;
                                cfr_temp_0 = 2;
                                if (var6_6 <= 1) continue block16;
                                if (FragmentManagerImpl.DEBUG) {
                                    Log.v((String)"FragmentManager", (String)("moveto ACTIVITY_CREATED: " + var1_1));
                                }
                                if (!var1_1.mFromLayout) {
                                    var10_10 = null;
                                    if (var1_1.mContainerId != 0) {
                                        if (var1_1.mContainerId == -1) {
                                            this.throwException(new IllegalArgumentException("Cannot create fragment " + var1_1 + " for a container view with no id"));
                                        }
                                        var11_13 = (ViewGroup)this.mContainer.onFindViewById(var1_1.mContainerId);
                                        var10_10 = var11_13;
                                        if (var11_13 == null) {
                                            var10_10 = var11_13;
                                            if (!var1_1.mRestored) {
                                                try {
                                                    var10_10 = var1_1.getResources().getResourceName(var1_1.mContainerId);
                                                }
                                                catch (Resources.NotFoundException var10_11) {
                                                    var10_10 = "unknown";
                                                }
                                                this.throwException(new IllegalArgumentException("No view found for id 0x" + Integer.toHexString(var1_1.mContainerId) + " (" + (String)var10_10 + ") for fragment " + var1_1));
                                                var10_10 = var11_13;
                                            }
                                        }
                                    }
                                    var1_1.mContainer = var10_10;
                                    var1_1.mView = var1_1.performCreateView(var1_1.getLayoutInflater(var1_1.mSavedFragmentState), (ViewGroup)var10_10, var1_1.mSavedFragmentState);
                                    if (var1_1.mView != null) {
                                        var1_1.mInnerView = var1_1.mView;
                                        if (Build.VERSION.SDK_INT >= 11) {
                                            ViewCompat.setSaveFromParentEnabled(var1_1.mView, false);
                                        } else {
                                            var1_1.mView = NoSaveStateFrameLayout.wrap(var1_1.mView);
                                        }
                                        if (var10_10 != null) {
                                            var11_13 = this.loadAnimation(var1_1, var3_3, true, var4_4);
                                            if (var11_13 != null) {
                                                this.setHWLayerAnimListenerIfAlpha(var1_1.mView, (Animation)var11_13);
                                                var1_1.mView.startAnimation((Animation)var11_13);
                                            }
                                            var10_10.addView(var1_1.mView);
                                        }
                                        if (var1_1.mHidden) {
                                            var1_1.mView.setVisibility(8);
                                        }
                                        var1_1.onViewCreated(var1_1.mView, var1_1.mSavedFragmentState);
                                    } else {
                                        var1_1.mInnerView = null;
                                    }
                                }
                                var1_1.performActivityCreated(var1_1.mSavedFragmentState);
                                if (var1_1.mView != null) {
                                    var1_1.restoreViewState(var1_1.mSavedFragmentState);
                                }
                                var1_1.mSavedFragmentState = null;
                                cfr_temp_0 = 2;
                                var8_8 = var6_6;
                                case 2: {
                                    var9_9 = var8_8;
                                    if (var8_8 > 2) {
                                        var1_1.mState = 3;
                                        var9_9 = var8_8;
                                    }
                                }
                                case 3: {
                                    var7_7 = var9_9;
                                    if (var9_9 <= 3) break;
                                    if (FragmentManagerImpl.DEBUG) {
                                        Log.v((String)"FragmentManager", (String)("moveto STARTED: " + var1_1));
                                    }
                                    var1_1.performStart();
                                    var7_7 = var9_9;
                                }
                                case 4: 
                            }
                            break;
                        } while (true);
                        var6_6 = var7_7;
                        if (var7_7 > 4) {
                            if (FragmentManagerImpl.DEBUG) {
                                Log.v((String)"FragmentManager", (String)("moveto RESUMED: " + var1_1));
                            }
                            var1_1.performResume();
                            var1_1.mSavedFragmentState = null;
                            var1_1.mSavedViewState = null;
                            var6_6 = var7_7;
                        }
                        break block85;
                    }
                    var6_6 = var2_2;
                    if (var1_1.mState > var2_2) {
                        switch (var1_1.mState) {
                            default: {
                                var6_6 = var2_2;
                                break;
                            }
                            case 5: {
                                if (var2_2 < 5) {
                                    if (FragmentManagerImpl.DEBUG) {
                                        Log.v((String)"FragmentManager", (String)("movefrom RESUMED: " + var1_1));
                                    }
                                    var1_1.performPause();
                                }
                            }
                            case 4: {
                                if (var2_2 < 4) {
                                    if (FragmentManagerImpl.DEBUG) {
                                        Log.v((String)"FragmentManager", (String)("movefrom STARTED: " + var1_1));
                                    }
                                    var1_1.performStop();
                                }
                            }
                            case 3: {
                                if (var2_2 < 3) {
                                    if (FragmentManagerImpl.DEBUG) {
                                        Log.v((String)"FragmentManager", (String)("movefrom STOPPED: " + var1_1));
                                    }
                                    var1_1.performReallyStop();
                                }
                            }
                            case 2: {
                                if (var2_2 < 2) {
                                    if (FragmentManagerImpl.DEBUG) {
                                        Log.v((String)"FragmentManager", (String)("movefrom ACTIVITY_CREATED: " + var1_1));
                                    }
                                    if (var1_1.mView != null && this.mHost.onShouldSaveFragmentState(var1_1) && var1_1.mSavedViewState == null) {
                                        this.saveFragmentViewState(var1_1);
                                    }
                                    var1_1.performDestroyView();
                                    if (var1_1.mView != null && var1_1.mContainer != null) {
                                        var11_14 = null;
                                        var10_12 = var11_14;
                                        if (this.mCurState > 0) {
                                            var10_12 = var11_14;
                                            if (!this.mDestroyed) {
                                                var10_12 = this.loadAnimation(var1_1, var3_3, false, var4_4);
                                            }
                                        }
                                        if (var10_12 != null) {
                                            var1_1.mAnimatingAway = var1_1.mView;
                                            var1_1.mStateAfterAnimating = var2_2;
                                            var10_12.setAnimationListener((Animation.AnimationListener)new AnimateOnHWLayerIfNeededListener(var1_1.mView, (Animation)var10_12){

                                                @Override
                                                public void onAnimationEnd(Animation animation) {
                                                    super.onAnimationEnd(animation);
                                                    if (var1_1.mAnimatingAway != null) {
                                                        var1_1.mAnimatingAway = null;
                                                        FragmentManagerImpl.this.moveToState(var1_1, var1_1.mStateAfterAnimating, 0, 0, false);
                                                    }
                                                }
                                            });
                                            var1_1.mView.startAnimation((Animation)var10_12);
                                        }
                                        var1_1.mContainer.removeView(var1_1.mView);
                                    }
                                    var1_1.mContainer = null;
                                    var1_1.mView = null;
                                    var1_1.mInnerView = null;
                                }
                            }
                            case 1: {
                                var6_6 = var2_2;
                                if (var2_2 >= 1) break;
                                if (this.mDestroyed && var1_1.mAnimatingAway != null) {
                                    var10_12 = var1_1.mAnimatingAway;
                                    var1_1.mAnimatingAway = null;
                                    var10_12.clearAnimation();
                                }
                                if (var1_1.mAnimatingAway != null) {
                                    var1_1.mStateAfterAnimating = var2_2;
                                    var6_6 = 1;
                                    break;
                                }
                                if (FragmentManagerImpl.DEBUG) {
                                    Log.v((String)"FragmentManager", (String)("movefrom CREATED: " + var1_1));
                                }
                                if (!var1_1.mRetaining) {
                                    var1_1.performDestroy();
                                } else {
                                    var1_1.mState = 0;
                                }
                                var1_1.performDetach();
                                var6_6 = var2_2;
                                if (var5_5) break;
                                if (!var1_1.mRetaining) {
                                    this.makeInactive(var1_1);
                                    var6_6 = var2_2;
                                    break;
                                }
                                var1_1.mHost = null;
                                var1_1.mParentFragment = null;
                                var1_1.mFragmentManager = null;
                                var6_6 = var2_2;
                            }
                        }
                    }
                }
                if (var1_1.mState != var6_6) break block90;
            }
            return;
        }
        Log.w((String)"FragmentManager", (String)("moveToState: Fragment state for " + var1_1 + " not updated inline; " + "expected state " + var6_6 + " found " + var1_1.mState));
        var1_1.mState = var6_6;
    }

    public void noteStateNotSaved() {
        this.mStateSaved = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public View onCreateView(View object, String object2, Context context, AttributeSet attributeSet) {
        String string2;
        int n2;
        Object object3;
        block20: {
            block19: {
                if (!"fragment".equals(object2)) break block19;
                object2 = attributeSet.getAttributeValue(null, "class");
                TypedArray typedArray = context.obtainStyledAttributes(attributeSet, FragmentTag.Fragment);
                object3 = object2;
                if (object2 == null) {
                    object3 = typedArray.getString(0);
                }
                n2 = typedArray.getResourceId(1, -1);
                string2 = typedArray.getString(2);
                typedArray.recycle();
                if (Fragment.isSupportFragmentClass(this.mHost.getContext(), (String)object3)) break block20;
            }
            return null;
        }
        int n3 = object != null ? object.getId() : 0;
        if (n3 == -1 && n2 == -1 && string2 == null) {
            throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Must specify unique android:id, android:tag, or have a parent with an id for " + (String)object3);
        }
        object2 = n2 != -1 ? this.findFragmentById(n2) : null;
        object = object2;
        if (object2 == null) {
            object = object2;
            if (string2 != null) {
                object = this.findFragmentByTag(string2);
            }
        }
        object2 = object;
        if (object == null) {
            object2 = object;
            if (n3 != -1) {
                object2 = this.findFragmentById(n3);
            }
        }
        if (DEBUG) {
            Log.v((String)TAG, (String)("onCreateView: id=0x" + Integer.toHexString(n2) + " fname=" + (String)object3 + " existing=" + object2));
        }
        if (object2 == null) {
            object = Fragment.instantiate(context, (String)object3);
            ((Fragment)object).mFromLayout = true;
            int n4 = n2 != 0 ? n2 : n3;
            ((Fragment)object).mFragmentId = n4;
            ((Fragment)object).mContainerId = n3;
            ((Fragment)object).mTag = string2;
            ((Fragment)object).mInLayout = true;
            ((Fragment)object).mFragmentManager = this;
            ((Fragment)object).mHost = this.mHost;
            ((Fragment)object).onInflate(this.mHost.getContext(), attributeSet, ((Fragment)object).mSavedFragmentState);
            this.addFragment((Fragment)object, true);
        } else {
            if (((Fragment)object2).mInLayout) {
                throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Duplicate id 0x" + Integer.toHexString(n2) + ", tag " + string2 + ", or parent id 0x" + Integer.toHexString(n3) + " with another fragment for " + (String)object3);
            }
            ((Fragment)object2).mInLayout = true;
            ((Fragment)object2).mHost = this.mHost;
            object = object2;
            if (!((Fragment)object2).mRetaining) {
                ((Fragment)object2).onInflate(this.mHost.getContext(), attributeSet, ((Fragment)object2).mSavedFragmentState);
                object = object2;
            }
        }
        if (this.mCurState < 1 && ((Fragment)object).mFromLayout) {
            this.moveToState((Fragment)object, 1, 0, 0, false);
        } else {
            this.moveToState((Fragment)object);
        }
        if (((Fragment)object).mView == null) {
            throw new IllegalStateException("Fragment " + (String)object3 + " did not create a view.");
        }
        if (n2 != 0) {
            ((Fragment)object).mView.setId(n2);
        }
        if (((Fragment)object).mView.getTag() == null) {
            ((Fragment)object).mView.setTag((Object)string2);
        }
        return ((Fragment)object).mView;
    }

    public void performPendingDeferredStart(Fragment fragment) {
        block3: {
            block2: {
                if (!fragment.mDeferStart) break block2;
                if (!this.mExecutingActions) break block3;
                this.mHavePendingDeferredStart = true;
            }
            return;
        }
        fragment.mDeferStart = false;
        this.moveToState(fragment, this.mCurState, 0, 0, false);
    }

    @Override
    public void popBackStack() {
        this.enqueueAction(new Runnable(){

            @Override
            public void run() {
                FragmentManagerImpl.this.popBackStackState(FragmentManagerImpl.this.mHost.getHandler(), null, -1, 0);
            }
        }, false);
    }

    @Override
    public void popBackStack(final int n2, final int n3) {
        if (n2 < 0) {
            throw new IllegalArgumentException("Bad id: " + n2);
        }
        this.enqueueAction(new Runnable(){

            @Override
            public void run() {
                FragmentManagerImpl.this.popBackStackState(FragmentManagerImpl.this.mHost.getHandler(), null, n2, n3);
            }
        }, false);
    }

    @Override
    public void popBackStack(final String string2, final int n2) {
        this.enqueueAction(new Runnable(){

            @Override
            public void run() {
                FragmentManagerImpl.this.popBackStackState(FragmentManagerImpl.this.mHost.getHandler(), string2, -1, n2);
            }
        }, false);
    }

    @Override
    public boolean popBackStackImmediate() {
        this.checkStateLoss();
        this.executePendingTransactions();
        return this.popBackStackState(this.mHost.getHandler(), null, -1, 0);
    }

    @Override
    public boolean popBackStackImmediate(int n2, int n3) {
        this.checkStateLoss();
        this.executePendingTransactions();
        if (n2 < 0) {
            throw new IllegalArgumentException("Bad id: " + n2);
        }
        return this.popBackStackState(this.mHost.getHandler(), null, n2, n3);
    }

    @Override
    public boolean popBackStackImmediate(String string2, int n2) {
        this.checkStateLoss();
        this.executePendingTransactions();
        return this.popBackStackState(this.mHost.getHandler(), string2, -1, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean popBackStackState(Handler object, String object2, int n2, int n3) {
        if (this.mBackStack == null) {
            return false;
        }
        if (object2 == null && n2 < 0 && (n3 & 1) == 0) {
            n2 = this.mBackStack.size() - 1;
            if (n2 < 0) {
                return false;
            }
            object = this.mBackStack.remove(n2);
            object2 = new SparseArray();
            SparseArray sparseArray = new SparseArray();
            if (this.mCurState >= 1) {
                ((BackStackRecord)object).calculateBackFragments((SparseArray<Fragment>)object2, (SparseArray<Fragment>)sparseArray);
            }
            ((BackStackRecord)object).popFromBackStack(true, null, (SparseArray<Fragment>)object2, (SparseArray<Fragment>)sparseArray);
            this.reportBackStackChanged();
            return true;
        }
        int n4 = -1;
        if (object2 != null || n2 >= 0) {
            int n5;
            for (n5 = this.mBackStack.size() - 1; n5 >= 0; --n5) {
                object = this.mBackStack.get(n5);
                if (object2 != null && ((String)object2).equals(((BackStackRecord)object).getName()) || n2 >= 0 && n2 == ((BackStackRecord)object).mIndex) break;
            }
            if (n5 < 0) {
                return false;
            }
            n4 = n5;
            if ((n3 & 1) != 0) {
                n3 = n5 - 1;
                while (true) {
                    n4 = --n3;
                    if (n3 < 0) break;
                    object = this.mBackStack.get(n3);
                    if (object2 != null && ((String)object2).equals(((BackStackRecord)object).getName())) continue;
                    n4 = n3;
                    if (n2 < 0) break;
                    n4 = n3;
                    if (n2 != ((BackStackRecord)object).mIndex) break;
                }
            }
        }
        if (n4 == this.mBackStack.size() - 1) {
            return false;
        }
        object2 = new ArrayList();
        for (n2 = this.mBackStack.size() - 1; n2 > n4; --n2) {
            ((ArrayList)object2).add(this.mBackStack.remove(n2));
        }
        n3 = ((ArrayList)object2).size() - 1;
        SparseArray sparseArray = new SparseArray();
        SparseArray sparseArray2 = new SparseArray();
        if (this.mCurState >= 1) {
            for (n2 = 0; n2 <= n3; ++n2) {
                ((BackStackRecord)((ArrayList)object2).get(n2)).calculateBackFragments((SparseArray<Fragment>)sparseArray, (SparseArray<Fragment>)sparseArray2);
            }
        }
        object = null;
        n2 = 0;
        while (true) {
            if (n2 > n3) {
                this.reportBackStackChanged();
                return true;
            }
            if (DEBUG) {
                Log.v((String)TAG, (String)("Popping back stack state: " + ((ArrayList)object2).get(n2)));
            }
            BackStackRecord backStackRecord = (BackStackRecord)((ArrayList)object2).get(n2);
            boolean bl2 = n2 == n3;
            object = backStackRecord.popFromBackStack(bl2, (BackStackRecord.TransitionState)object, (SparseArray<Fragment>)sparseArray, (SparseArray<Fragment>)sparseArray2);
            ++n2;
        }
    }

    @Override
    public void putFragment(Bundle bundle, String string2, Fragment fragment) {
        if (fragment.mIndex < 0) {
            this.throwException(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        bundle.putInt(string2, fragment.mIndex);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void removeFragment(Fragment fragment, int n2, int n3) {
        if (DEBUG) {
            Log.v((String)TAG, (String)("remove: " + fragment + " nesting=" + fragment.mBackStackNesting));
        }
        int n4 = !fragment.isInBackStack() ? 1 : 0;
        if (!fragment.mDetached || n4 != 0) {
            if (this.mAdded != null) {
                this.mAdded.remove(fragment);
            }
            if (fragment.mHasMenu && fragment.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            fragment.mAdded = false;
            fragment.mRemoving = true;
            n4 = n4 != 0 ? 0 : 1;
            this.moveToState(fragment, n4, n2, n3, false);
        }
    }

    @Override
    public void removeOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener onBackStackChangedListener) {
        if (this.mBackStackChangeListeners != null) {
            this.mBackStackChangeListeners.remove(onBackStackChangedListener);
        }
    }

    void reportBackStackChanged() {
        if (this.mBackStackChangeListeners != null) {
            for (int i2 = 0; i2 < this.mBackStackChangeListeners.size(); ++i2) {
                this.mBackStackChangeListeners.get(i2).onBackStackChanged();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void restoreAllState(Parcelable object, FragmentManagerNonConfig object2) {
        if (object != null) {
            FragmentManagerState fragmentManagerState = (FragmentManagerState)object;
            if (fragmentManagerState.mActive != null) {
                FragmentState fragmentState;
                int n2;
                int n3;
                Object object3;
                List<Fragment> list;
                object = null;
                if (object2 != null) {
                    list = ((FragmentManagerNonConfig)object2).getFragments();
                    object3 = ((FragmentManagerNonConfig)object2).getChildNonConfigs();
                    n3 = list != null ? list.size() : 0;
                    n2 = 0;
                    while (true) {
                        object = object3;
                        if (n2 >= n3) break;
                        object = list.get(n2);
                        if (DEBUG) {
                            Log.v((String)TAG, (String)("restoreAllState: re-attaching retained " + object));
                        }
                        fragmentState = fragmentManagerState.mActive[((Fragment)object).mIndex];
                        fragmentState.mInstance = object;
                        ((Fragment)object).mSavedViewState = null;
                        ((Fragment)object).mBackStackNesting = 0;
                        ((Fragment)object).mInLayout = false;
                        ((Fragment)object).mAdded = false;
                        ((Fragment)object).mTarget = null;
                        if (fragmentState.mSavedFragmentState != null) {
                            fragmentState.mSavedFragmentState.setClassLoader(this.mHost.getContext().getClassLoader());
                            ((Fragment)object).mSavedViewState = fragmentState.mSavedFragmentState.getSparseParcelableArray(VIEW_STATE_TAG);
                            ((Fragment)object).mSavedFragmentState = fragmentState.mSavedFragmentState;
                        }
                        ++n2;
                    }
                }
                this.mActive = new ArrayList(fragmentManagerState.mActive.length);
                if (this.mAvailIndices != null) {
                    this.mAvailIndices.clear();
                }
                for (n3 = 0; n3 < fragmentManagerState.mActive.length; ++n3) {
                    fragmentState = fragmentManagerState.mActive[n3];
                    if (fragmentState != null) {
                        list = null;
                        object3 = list;
                        if (object != null) {
                            object3 = list;
                            if (n3 < object.size()) {
                                object3 = (FragmentManagerNonConfig)object.get(n3);
                            }
                        }
                        object3 = fragmentState.instantiate(this.mHost, this.mParent, (FragmentManagerNonConfig)object3);
                        if (DEBUG) {
                            Log.v((String)TAG, (String)("restoreAllState: active #" + n3 + ": " + object3));
                        }
                        this.mActive.add((Fragment)object3);
                        fragmentState.mInstance = null;
                        continue;
                    }
                    this.mActive.add(null);
                    if (this.mAvailIndices == null) {
                        this.mAvailIndices = new ArrayList();
                    }
                    if (DEBUG) {
                        Log.v((String)TAG, (String)("restoreAllState: avail #" + n3));
                    }
                    this.mAvailIndices.add(n3);
                }
                if (object2 != null) {
                    object = ((FragmentManagerNonConfig)object2).getFragments();
                    n3 = object != null ? object.size() : 0;
                    for (n2 = 0; n2 < n3; ++n2) {
                        object2 = (Fragment)object.get(n2);
                        if (((Fragment)object2).mTargetIndex < 0) continue;
                        if (((Fragment)object2).mTargetIndex < this.mActive.size()) {
                            ((Fragment)object2).mTarget = this.mActive.get(((Fragment)object2).mTargetIndex);
                            continue;
                        }
                        Log.w((String)TAG, (String)("Re-attaching retained fragment " + object2 + " target no longer exists: " + ((Fragment)object2).mTargetIndex));
                        ((Fragment)object2).mTarget = null;
                    }
                }
                if (fragmentManagerState.mAdded == null) {
                    this.mAdded = null;
                } else {
                    this.mAdded = new ArrayList(fragmentManagerState.mAdded.length);
                    for (n3 = 0; n3 < fragmentManagerState.mAdded.length; ++n3) {
                        object = this.mActive.get(fragmentManagerState.mAdded[n3]);
                        if (object == null) {
                            this.throwException(new IllegalStateException("No instantiated fragment for index #" + fragmentManagerState.mAdded[n3]));
                        }
                        ((Fragment)object).mAdded = true;
                        if (DEBUG) {
                            Log.v((String)TAG, (String)("restoreAllState: added #" + n3 + ": " + object));
                        }
                        if (this.mAdded.contains(object)) {
                            throw new IllegalStateException("Already added!");
                        }
                        this.mAdded.add((Fragment)object);
                    }
                }
                if (fragmentManagerState.mBackStack == null) {
                    this.mBackStack = null;
                    return;
                }
                this.mBackStack = new ArrayList(fragmentManagerState.mBackStack.length);
                for (n3 = 0; n3 < fragmentManagerState.mBackStack.length; ++n3) {
                    object = fragmentManagerState.mBackStack[n3].instantiate(this);
                    if (DEBUG) {
                        Log.v((String)TAG, (String)("restoreAllState: back stack #" + n3 + " (index " + ((BackStackRecord)object).mIndex + "): " + object));
                        ((BackStackRecord)object).dump("  ", new PrintWriter(new LogWriter(TAG)), false);
                    }
                    this.mBackStack.add((BackStackRecord)object);
                    if (((BackStackRecord)object).mIndex < 0) continue;
                    this.setBackStackIndex(((BackStackRecord)object).mIndex, (BackStackRecord)object);
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    FragmentManagerNonConfig retainNonConfig() {
        ArrayList<ArrayList<Fragment>> arrayList = null;
        ArrayList<ArrayList<Fragment>> arrayList2 = null;
        ArrayList<ArrayList<Fragment>> arrayList3 = null;
        ArrayList<ArrayList<Fragment>> arrayList4 = null;
        if (this.mActive != null) {
            int n2 = 0;
            while (true) {
                arrayList3 = arrayList4;
                arrayList = arrayList2;
                if (n2 >= this.mActive.size()) break;
                Fragment fragment = this.mActive.get(n2);
                arrayList = arrayList4;
                ArrayList<ArrayList<Fragment>> arrayList5 = arrayList2;
                if (fragment != null) {
                    int n3;
                    int n4;
                    arrayList3 = arrayList2;
                    if (fragment.mRetainInstance) {
                        arrayList = arrayList2;
                        if (arrayList2 == null) {
                            arrayList = new ArrayList<ArrayList<Fragment>>();
                        }
                        arrayList.add((ArrayList<Fragment>)((Object)fragment));
                        fragment.mRetaining = true;
                        n4 = fragment.mTarget != null ? fragment.mTarget.mIndex : -1;
                        fragment.mTargetIndex = n4;
                        arrayList3 = arrayList;
                        if (DEBUG) {
                            Log.v((String)TAG, (String)("retainNonConfig: keeping retained " + fragment));
                            arrayList3 = arrayList;
                        }
                    }
                    n4 = n3 = 0;
                    arrayList2 = arrayList4;
                    if (fragment.mChildFragmentManager != null) {
                        arrayList = fragment.mChildFragmentManager.retainNonConfig();
                        n4 = n3;
                        arrayList2 = arrayList4;
                        if (arrayList != null) {
                            arrayList2 = arrayList4;
                            if (arrayList4 == null) {
                                arrayList4 = new ArrayList();
                                n4 = 0;
                                while (true) {
                                    arrayList2 = arrayList4;
                                    if (n4 >= n2) break;
                                    arrayList4.add(null);
                                    ++n4;
                                }
                            }
                            arrayList2.add(arrayList);
                            n4 = 1;
                        }
                    }
                    arrayList = arrayList2;
                    arrayList5 = arrayList3;
                    if (arrayList2 != null) {
                        arrayList = arrayList2;
                        arrayList5 = arrayList3;
                        if (n4 == 0) {
                            arrayList2.add(null);
                            arrayList5 = arrayList3;
                            arrayList = arrayList2;
                        }
                    }
                }
                ++n2;
                arrayList4 = arrayList;
                arrayList2 = arrayList5;
            }
        }
        if (arrayList == null && arrayList3 == null) {
            return null;
        }
        return new FragmentManagerNonConfig(arrayList, arrayList3);
    }

    /*
     * Enabled aggressive block sorting
     */
    Parcelable saveAllState() {
        BackStackState[] backStackStateArray;
        Object object;
        int n2;
        int n3;
        FragmentState[] fragmentStateArray;
        block25: {
            block26: {
                block22: {
                    this.execPendingActions();
                    if (HONEYCOMB) {
                        this.mStateSaved = true;
                    }
                    if (this.mActive == null || this.mActive.size() <= 0) break block22;
                    int n4 = this.mActive.size();
                    fragmentStateArray = new FragmentState[n4];
                    n3 = 0;
                    for (n2 = 0; n2 < n4; ++n2) {
                        int n5;
                        block24: {
                            block23: {
                                object = this.mActive.get(n2);
                                if (object == null) continue;
                                if (((Fragment)object).mIndex < 0) {
                                    this.throwException(new IllegalStateException("Failure saving state: active " + object + " has cleared index: " + ((Fragment)object).mIndex));
                                }
                                n5 = 1;
                                backStackStateArray = new FragmentState((Fragment)object);
                                fragmentStateArray[n2] = backStackStateArray;
                                if (((Fragment)object).mState <= 0 || backStackStateArray.mSavedFragmentState != null) break block23;
                                backStackStateArray.mSavedFragmentState = this.saveFragmentBasicState((Fragment)object);
                                if (((Fragment)object).mTarget != null) {
                                    if (object.mTarget.mIndex < 0) {
                                        this.throwException(new IllegalStateException("Failure saving state: " + object + " has target not in fragment manager: " + ((Fragment)object).mTarget));
                                    }
                                    if (backStackStateArray.mSavedFragmentState == null) {
                                        backStackStateArray.mSavedFragmentState = new Bundle();
                                    }
                                    this.putFragment(backStackStateArray.mSavedFragmentState, TARGET_STATE_TAG, ((Fragment)object).mTarget);
                                    if (((Fragment)object).mTargetRequestCode != 0) {
                                        backStackStateArray.mSavedFragmentState.putInt(TARGET_REQUEST_CODE_STATE_TAG, ((Fragment)object).mTargetRequestCode);
                                    }
                                }
                                break block24;
                            }
                            backStackStateArray.mSavedFragmentState = ((Fragment)object).mSavedFragmentState;
                        }
                        n3 = n5;
                        if (!DEBUG) continue;
                        Log.v((String)TAG, (String)("Saved state of " + object + ": " + backStackStateArray.mSavedFragmentState));
                        n3 = n5;
                    }
                    if (n3 != 0) break block25;
                    if (DEBUG) break block26;
                }
                return null;
            }
            Log.v((String)TAG, (String)"saveAllState: no fragments!");
            return null;
        }
        backStackStateArray = null;
        BackStackState[] backStackStateArray2 = null;
        object = backStackStateArray;
        if (this.mAdded != null) {
            n3 = this.mAdded.size();
            object = backStackStateArray;
            if (n3 > 0) {
                backStackStateArray = (BackStackState[])new int[n3];
                n2 = 0;
                while (true) {
                    object = backStackStateArray;
                    if (n2 >= n3) break;
                    backStackStateArray[n2] = (BackStackState)this.mAdded.get((int)n2).mIndex;
                    if (backStackStateArray[n2] < 0) {
                        this.throwException(new IllegalStateException("Failure saving state: active " + this.mAdded.get(n2) + " has cleared index: " + (int)backStackStateArray[n2]));
                    }
                    if (DEBUG) {
                        Log.v((String)TAG, (String)("saveAllState: adding fragment #" + n2 + ": " + this.mAdded.get(n2)));
                    }
                    ++n2;
                }
            }
        }
        backStackStateArray = backStackStateArray2;
        if (this.mBackStack != null) {
            n3 = this.mBackStack.size();
            backStackStateArray = backStackStateArray2;
            if (n3 > 0) {
                backStackStateArray2 = new BackStackState[n3];
                n2 = 0;
                while (true) {
                    backStackStateArray = backStackStateArray2;
                    if (n2 >= n3) break;
                    backStackStateArray2[n2] = new BackStackState(this.mBackStack.get(n2));
                    if (DEBUG) {
                        Log.v((String)TAG, (String)("saveAllState: adding back stack #" + n2 + ": " + this.mBackStack.get(n2)));
                    }
                    ++n2;
                }
            }
        }
        backStackStateArray2 = new FragmentManagerState();
        backStackStateArray2.mActive = fragmentStateArray;
        backStackStateArray2.mAdded = (int[])object;
        backStackStateArray2.mBackStack = backStackStateArray;
        return backStackStateArray2;
    }

    Bundle saveFragmentBasicState(Fragment fragment) {
        Bundle bundle = null;
        if (this.mStateBundle == null) {
            this.mStateBundle = new Bundle();
        }
        fragment.performSaveInstanceState(this.mStateBundle);
        if (!this.mStateBundle.isEmpty()) {
            bundle = this.mStateBundle;
            this.mStateBundle = null;
        }
        if (fragment.mView != null) {
            this.saveFragmentViewState(fragment);
        }
        Bundle bundle2 = bundle;
        if (fragment.mSavedViewState != null) {
            bundle2 = bundle;
            if (bundle == null) {
                bundle2 = new Bundle();
            }
            bundle2.putSparseParcelableArray(VIEW_STATE_TAG, fragment.mSavedViewState);
        }
        bundle = bundle2;
        if (!fragment.mUserVisibleHint) {
            bundle = bundle2;
            if (bundle2 == null) {
                bundle = new Bundle();
            }
            bundle.putBoolean(USER_VISIBLE_HINT_TAG, fragment.mUserVisibleHint);
        }
        return bundle;
    }

    @Override
    public Fragment.SavedState saveFragmentInstanceState(Fragment fragment) {
        Fragment.SavedState savedState = null;
        if (fragment.mIndex < 0) {
            this.throwException(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        Fragment.SavedState savedState2 = savedState;
        if (fragment.mState > 0) {
            fragment = this.saveFragmentBasicState(fragment);
            savedState2 = savedState;
            if (fragment != null) {
                savedState2 = new Fragment.SavedState((Bundle)fragment);
            }
        }
        return savedState2;
    }

    /*
     * Enabled aggressive block sorting
     */
    void saveFragmentViewState(Fragment fragment) {
        block6: {
            block5: {
                if (fragment.mInnerView == null) break block5;
                if (this.mStateArray == null) {
                    this.mStateArray = new SparseArray();
                } else {
                    this.mStateArray.clear();
                }
                fragment.mInnerView.saveHierarchyState(this.mStateArray);
                if (this.mStateArray.size() > 0) break block6;
            }
            return;
        }
        fragment.mSavedViewState = this.mStateArray;
        this.mStateArray = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setBackStackIndex(int n2, BackStackRecord backStackRecord) {
        synchronized (this) {
            int n3;
            if (this.mBackStackIndices == null) {
                this.mBackStackIndices = new ArrayList();
            }
            if (n2 < n3) {
                if (DEBUG) {
                    Log.v((String)TAG, (String)("Setting back stack index " + n2 + " to " + backStackRecord));
                }
                this.mBackStackIndices.set(n2, backStackRecord);
            } else {
                for (int i2 = n3 = this.mBackStackIndices.size(); i2 < n2; ++i2) {
                    this.mBackStackIndices.add(null);
                    if (this.mAvailBackStackIndices == null) {
                        this.mAvailBackStackIndices = new ArrayList();
                    }
                    if (DEBUG) {
                        Log.v((String)TAG, (String)("Adding available back stack index " + i2));
                    }
                    this.mAvailBackStackIndices.add(i2);
                }
                if (DEBUG) {
                    Log.v((String)TAG, (String)("Adding back stack index " + n2 + " with " + backStackRecord));
                }
                this.mBackStackIndices.add(backStackRecord);
            }
            return;
        }
    }

    public void showFragment(Fragment fragment, int n2, int n3) {
        if (DEBUG) {
            Log.v((String)TAG, (String)("show: " + fragment));
        }
        if (fragment.mHidden) {
            fragment.mHidden = false;
            if (fragment.mView != null) {
                Animation animation = this.loadAnimation(fragment, n2, true, n3);
                if (animation != null) {
                    this.setHWLayerAnimListenerIfAlpha(fragment.mView, animation);
                    fragment.mView.startAnimation(animation);
                }
                fragment.mView.setVisibility(0);
            }
            if (fragment.mAdded && fragment.mHasMenu && fragment.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            fragment.onHiddenChanged(false);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void startPendingDeferredFragments() {
        if (this.mActive != null) {
            for (int i2 = 0; i2 < this.mActive.size(); ++i2) {
                Fragment fragment = this.mActive.get(i2);
                if (fragment == null) continue;
                this.performPendingDeferredStart(fragment);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("FragmentManager{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" in ");
        if (this.mParent != null) {
            DebugUtils.buildShortClassTag(this.mParent, stringBuilder);
        } else {
            DebugUtils.buildShortClassTag(this.mHost, stringBuilder);
        }
        stringBuilder.append("}}");
        return stringBuilder.toString();
    }

    static class AnimateOnHWLayerIfNeededListener
    implements Animation.AnimationListener {
        private Animation.AnimationListener mOriginalListener;
        private boolean mShouldRunOnHWLayer;
        View mView;

        public AnimateOnHWLayerIfNeededListener(View view, Animation animation) {
            if (view == null || animation == null) {
                return;
            }
            this.mView = view;
        }

        public AnimateOnHWLayerIfNeededListener(View view, Animation animation, Animation.AnimationListener animationListener) {
            if (view == null || animation == null) {
                return;
            }
            this.mOriginalListener = animationListener;
            this.mView = view;
            this.mShouldRunOnHWLayer = true;
        }

        /*
         * Enabled aggressive block sorting
         */
        @CallSuper
        public void onAnimationEnd(Animation animation) {
            if (this.mView != null && this.mShouldRunOnHWLayer) {
                if (ViewCompat.isAttachedToWindow(this.mView) || BuildCompat.isAtLeastN()) {
                    this.mView.post(new Runnable(){

                        @Override
                        public void run() {
                            ViewCompat.setLayerType(AnimateOnHWLayerIfNeededListener.this.mView, 0, null);
                        }
                    });
                } else {
                    ViewCompat.setLayerType(this.mView, 0, null);
                }
            }
            if (this.mOriginalListener != null) {
                this.mOriginalListener.onAnimationEnd(animation);
            }
        }

        public void onAnimationRepeat(Animation animation) {
            if (this.mOriginalListener != null) {
                this.mOriginalListener.onAnimationRepeat(animation);
            }
        }

        @CallSuper
        public void onAnimationStart(Animation animation) {
            if (this.mOriginalListener != null) {
                this.mOriginalListener.onAnimationStart(animation);
            }
        }
    }

    static class FragmentTag {
        public static final int[] Fragment = new int[]{0x1010003, 0x10100D0, 0x10100D1};
        public static final int Fragment_id = 1;
        public static final int Fragment_name = 0;
        public static final int Fragment_tag = 2;

        FragmentTag() {
        }
    }
}

