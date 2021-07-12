/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewTreeObserver$OnPreDrawListener
 */
package android.support.v4.app;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManagerImpl;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentTransitionCompat21;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LogWriter;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Map;

final class BackStackRecord
extends FragmentTransaction
implements FragmentManager.BackStackEntry,
Runnable {
    static final int OP_ADD = 1;
    static final int OP_ATTACH = 7;
    static final int OP_DETACH = 6;
    static final int OP_HIDE = 4;
    static final int OP_NULL = 0;
    static final int OP_REMOVE = 3;
    static final int OP_REPLACE = 2;
    static final int OP_SHOW = 5;
    static final boolean SUPPORTS_TRANSITIONS;
    static final String TAG = "FragmentManager";
    boolean mAddToBackStack;
    boolean mAllowAddToBackStack = true;
    int mBreadCrumbShortTitleRes;
    CharSequence mBreadCrumbShortTitleText;
    int mBreadCrumbTitleRes;
    CharSequence mBreadCrumbTitleText;
    boolean mCommitted;
    int mEnterAnim;
    int mExitAnim;
    Op mHead;
    int mIndex = -1;
    final FragmentManagerImpl mManager;
    String mName;
    int mNumOp;
    int mPopEnterAnim;
    int mPopExitAnim;
    ArrayList<String> mSharedElementSourceNames;
    ArrayList<String> mSharedElementTargetNames;
    Op mTail;
    int mTransition;
    int mTransitionStyle;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl2 = Build.VERSION.SDK_INT >= 21;
        SUPPORTS_TRANSITIONS = bl2;
    }

    public BackStackRecord(FragmentManagerImpl fragmentManagerImpl) {
        this.mManager = fragmentManagerImpl;
    }

    private TransitionState beginTransition(SparseArray<Fragment> object, SparseArray<Fragment> sparseArray, boolean bl2) {
        int n2;
        TransitionState transitionState = new TransitionState();
        transitionState.nonExistentView = new View(this.mManager.mHost.getContext());
        boolean bl3 = false;
        for (n2 = 0; n2 < object.size(); ++n2) {
            if (!this.configureTransitions(object.keyAt(n2), transitionState, bl2, (SparseArray<Fragment>)object, sparseArray)) continue;
            bl3 = true;
        }
        for (n2 = 0; n2 < sparseArray.size(); ++n2) {
            int n3 = sparseArray.keyAt(n2);
            boolean bl4 = bl3;
            if (object.get(n3) == null) {
                bl4 = bl3;
                if (this.configureTransitions(n3, transitionState, bl2, (SparseArray<Fragment>)object, sparseArray)) {
                    bl4 = true;
                }
            }
            bl3 = bl4;
        }
        object = transitionState;
        if (!bl3) {
            object = null;
        }
        return object;
    }

    /*
     * Unable to fully structure code
     */
    private void calculateFragments(SparseArray<Fragment> var1_1, SparseArray<Fragment> var2_2) {
        if (!this.mManager.mContainer.onHasView()) {
            return;
        }
        var4_3 = this.mHead;
        block10: while (true) {
            if (var4_3 == null) ** continue;
            switch (var4_3.cmd) lbl-1000:
            // 7 sources

            {
                default: lbl-1000:
                // 2 sources

                {
                    while (true) {
                        var4_3 = var4_3.next;
                        continue block10;
                        break;
                    }
                }
                case 1: {
                    this.setLastIn(var1_1, var2_2, var4_3.fragment);
                    ** GOTO lbl-1000
                }
                case 2: {
                    var5_5 = var4_3.fragment;
                    if (this.mManager.mAdded == null) ** GOTO lbl32
                    block13: for (var3_4 = 0; var3_4 < this.mManager.mAdded.size(); ++var3_4) {
                        var7_7 = this.mManager.mAdded.get(var3_4);
                        if (var5_5 == null) ** GOTO lbl23
                        var6_6 = var5_5;
                        if (var7_7.mContainerId != var5_5.mContainerId) ** GOTO lbl26
lbl23:
                        // 2 sources

                        if (var7_7 != var5_5) ** GOTO lbl29
                        var6_6 = null;
                        var2_2.remove(var7_7.mContainerId);
lbl26:
                        // 3 sources

                        while (true) {
                            var5_5 = var6_6;
                            continue block13;
                            break;
                        }
lbl29:
                        // 1 sources

                        BackStackRecord.setFirstOut(var1_1, var2_2, var7_7);
                        var6_6 = var5_5;
                        ** continue;
                    }
lbl32:
                    // 2 sources

                    this.setLastIn(var1_1, var2_2, var4_3.fragment);
                    ** GOTO lbl-1000
                }
                case 3: {
                    BackStackRecord.setFirstOut(var1_1, var2_2, var4_3.fragment);
                    ** GOTO lbl-1000
                }
                case 4: {
                    BackStackRecord.setFirstOut(var1_1, var2_2, var4_3.fragment);
                    ** GOTO lbl-1000
                }
                case 5: {
                    this.setLastIn(var1_1, var2_2, var4_3.fragment);
                    ** GOTO lbl-1000
                }
                case 6: {
                    BackStackRecord.setFirstOut(var1_1, var2_2, var4_3.fragment);
                    ** GOTO lbl-1000
                }
                case 7: 
            }
            break;
        }
        this.setLastIn(var1_1, var2_2, var4_3.fragment);
        ** while (true)
    }

    private static Object captureExitingViews(Object object, Fragment fragment, ArrayList<View> arrayList, ArrayMap<String, View> arrayMap, View view) {
        Object object2 = object;
        if (object != null) {
            object2 = FragmentTransitionCompat21.captureExitingViews(object, fragment.getView(), arrayList, arrayMap, view);
        }
        return object2;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private boolean configureTransitions(int n2, TransitionState transitionState, boolean bl2, SparseArray<Fragment> object, SparseArray<Fragment> object2) {
        void var5_8;
        ArrayMap<String, View> arrayMap;
        ViewGroup viewGroup = (ViewGroup)this.mManager.mContainer.onFindViewById(n2);
        if (viewGroup == null) {
            return false;
        }
        Object object3 = (Fragment)object2.get(n2);
        Object object4 = (Fragment)object.get(n2);
        Object object5 = BackStackRecord.getEnterTransition((Fragment)object3, bl2);
        ArrayList<View> arrayList = BackStackRecord.getSharedElementTransition((Fragment)object3, (Fragment)object4, bl2);
        ArrayList<View> arrayList2 = BackStackRecord.getExitTransition((Fragment)object4, bl2);
        object = null;
        ArrayList<View> arrayList3 = new ArrayList<View>();
        ArrayList<View> arrayList4 = arrayList;
        if (arrayList != null) {
            arrayMap = this.remapSharedElements(transitionState, (Fragment)object4, bl2);
            if (arrayMap.isEmpty()) {
                Object var5_7 = null;
                object = null;
            } else {
                object = bl2 ? ((Fragment)object4).mEnterTransitionCallback : ((Fragment)object3).mEnterTransitionCallback;
                if (object != null) {
                    ((SharedElementCallback)object).onSharedElementStart(new ArrayList<String>(arrayMap.keySet()), new ArrayList<View>(arrayMap.values()), null);
                }
                this.prepareSharedElementTransition(transitionState, (View)viewGroup, arrayList, (Fragment)object3, (Fragment)object4, bl2, arrayList3, object5, arrayList2);
                ArrayList<View> arrayList5 = arrayList;
                object = arrayMap;
            }
        }
        if (object5 == null && var5_8 == null && arrayList2 == null) {
            return false;
        }
        arrayList = new ArrayList<View>();
        arrayMap = BackStackRecord.captureExitingViews(arrayList2, (Fragment)object4, arrayList, (ArrayMap<String, View>)object, transitionState.nonExistentView);
        if (this.mSharedElementTargetNames != null && object != null && (object4 = (View)((SimpleArrayMap)object).get(this.mSharedElementTargetNames.get(0))) != null) {
            if (arrayMap != null) {
                FragmentTransitionCompat21.setEpicenter(arrayMap, (View)object4);
            }
            if (var5_8 != null) {
                FragmentTransitionCompat21.setEpicenter(var5_8, (View)object4);
            }
        }
        object4 = new FragmentTransitionCompat21.ViewRetriever((Fragment)object3){
            final /* synthetic */ Fragment val$inFragment;
            {
                this.val$inFragment = fragment;
            }

            @Override
            public View getView() {
                return this.val$inFragment.getView();
            }
        };
        arrayList2 = new ArrayList<View>();
        ArrayMap<String, View> arrayMap2 = new ArrayMap<String, View>();
        boolean bl3 = true;
        if (object3 != null) {
            bl3 = bl2 ? ((Fragment)object3).getAllowReturnTransitionOverlap() : ((Fragment)object3).getAllowEnterTransitionOverlap();
        }
        if ((object3 = FragmentTransitionCompat21.mergeTransitions(object5, arrayMap, var5_8, bl3)) != null) {
            FragmentTransitionCompat21.addTransitionTargets(object5, var5_8, arrayMap, (View)viewGroup, (FragmentTransitionCompat21.ViewRetriever)object4, transitionState.nonExistentView, transitionState.enteringEpicenterView, transitionState.nameOverrides, arrayList2, arrayList, (Map<String, View>)object, arrayMap2, arrayList3);
            this.excludeHiddenFragmentsAfterEnter((View)viewGroup, transitionState, n2, object3);
            FragmentTransitionCompat21.excludeTarget(object3, transitionState.nonExistentView, true);
            this.excludeHiddenFragments(transitionState, n2, object3);
            FragmentTransitionCompat21.beginDelayedTransition(viewGroup, object3);
            FragmentTransitionCompat21.cleanupTransitions((View)viewGroup, transitionState.nonExistentView, object5, arrayList2, arrayMap, arrayList, var5_8, arrayList3, object3, transitionState.hiddenFragmentViews, arrayMap2);
        }
        return object3 != null;
    }

    private void doAddOp(int n2, Fragment fragment, String object, int n3) {
        Class<?> clazz = fragment.getClass();
        int n4 = clazz.getModifiers();
        if (clazz.isAnonymousClass() || !Modifier.isPublic(n4) || clazz.isMemberClass() && !Modifier.isStatic(n4)) {
            throw new IllegalStateException("Fragment " + clazz.getCanonicalName() + " must be a public static class to be  properly recreated from" + " instance state.");
        }
        fragment.mFragmentManager = this.mManager;
        if (object != null) {
            if (fragment.mTag != null && !((String)object).equals(fragment.mTag)) {
                throw new IllegalStateException("Can't change tag of fragment " + fragment + ": was " + fragment.mTag + " now " + (String)object);
            }
            fragment.mTag = object;
        }
        if (n2 != 0) {
            if (n2 == -1) {
                throw new IllegalArgumentException("Can't add fragment " + fragment + " with tag " + (String)object + " to container view with no id");
            }
            if (fragment.mFragmentId != 0 && fragment.mFragmentId != n2) {
                throw new IllegalStateException("Can't change container ID of fragment " + fragment + ": was " + fragment.mFragmentId + " now " + n2);
            }
            fragment.mFragmentId = n2;
            fragment.mContainerId = n2;
        }
        object = new Op();
        ((Op)object).cmd = n3;
        ((Op)object).fragment = fragment;
        this.addOp((Op)object);
    }

    private void excludeHiddenFragmentsAfterEnter(final View view, final TransitionState transitionState, final int n2, final Object object) {
        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(){

            public boolean onPreDraw() {
                view.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this);
                BackStackRecord.this.excludeHiddenFragments(transitionState, n2, object);
                return true;
            }
        });
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Object getEnterTransition(Fragment object, boolean bl2) {
        if (object == null) {
            return null;
        }
        if (bl2) {
            object = ((Fragment)object).getReenterTransition();
            return FragmentTransitionCompat21.cloneTransition(object);
        }
        object = ((Fragment)object).getEnterTransition();
        return FragmentTransitionCompat21.cloneTransition(object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Object getExitTransition(Fragment object, boolean bl2) {
        if (object == null) {
            return null;
        }
        if (bl2) {
            object = ((Fragment)object).getReturnTransition();
            return FragmentTransitionCompat21.cloneTransition(object);
        }
        object = ((Fragment)object).getExitTransition();
        return FragmentTransitionCompat21.cloneTransition(object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Object getSharedElementTransition(Fragment object, Fragment fragment, boolean bl2) {
        if (object == null || fragment == null) {
            return null;
        }
        if (bl2) {
            object = fragment.getSharedElementReturnTransition();
            return FragmentTransitionCompat21.wrapSharedElementTransition(object);
        }
        object = ((Fragment)object).getSharedElementEnterTransition();
        return FragmentTransitionCompat21.wrapSharedElementTransition(object);
    }

    private ArrayMap<String, View> mapEnteringSharedElements(TransitionState arrayMap, Fragment fragment, boolean bl2) {
        ArrayMap<String, View> arrayMap2;
        block3: {
            block2: {
                arrayMap2 = new ArrayMap<String, View>();
                fragment = fragment.getView();
                arrayMap = arrayMap2;
                if (fragment == null) break block2;
                arrayMap = arrayMap2;
                if (this.mSharedElementSourceNames == null) break block2;
                FragmentTransitionCompat21.findNamedViews(arrayMap2, (View)fragment);
                if (!bl2) break block3;
                arrayMap = BackStackRecord.remapNames(this.mSharedElementSourceNames, this.mSharedElementTargetNames, arrayMap2);
            }
            return arrayMap;
        }
        arrayMap2.retainAll(this.mSharedElementTargetNames);
        return arrayMap2;
    }

    private void prepareSharedElementTransition(final TransitionState transitionState, final View view, final Object object, final Fragment fragment, final Fragment fragment2, final boolean bl2, final ArrayList<View> arrayList, final Object object2, final Object object3) {
        if (object != null) {
            view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(){

                public boolean onPreDraw() {
                    view.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this);
                    FragmentTransitionCompat21.removeTargets(object, arrayList);
                    arrayList.remove(transitionState.nonExistentView);
                    FragmentTransitionCompat21.excludeSharedElementViews(object2, object3, object, arrayList, false);
                    arrayList.clear();
                    ArrayMap<String, View> arrayMap = BackStackRecord.this.mapSharedElementsIn(transitionState, bl2, fragment);
                    FragmentTransitionCompat21.setSharedElementTargets(object, transitionState.nonExistentView, arrayMap, arrayList);
                    BackStackRecord.this.setEpicenterIn(arrayMap, transitionState);
                    BackStackRecord.this.callSharedElementEnd(transitionState, fragment, fragment2, bl2, arrayMap);
                    FragmentTransitionCompat21.excludeSharedElementViews(object2, object3, object, arrayList, true);
                    return true;
                }
            });
        }
    }

    private static ArrayMap<String, View> remapNames(ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayMap<String, View> arrayMap) {
        if (arrayMap.isEmpty()) {
            return arrayMap;
        }
        ArrayMap<String, View> arrayMap2 = new ArrayMap<String, View>();
        int n2 = arrayList.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            View view = (View)arrayMap.get(arrayList.get(i2));
            if (view == null) continue;
            arrayMap2.put(arrayList2.get(i2), view);
        }
        return arrayMap2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private ArrayMap<String, View> remapSharedElements(TransitionState transitionState, Fragment fragment, boolean bl2) {
        ArrayMap<String, View> arrayMap;
        ArrayMap<String, View> arrayMap2 = arrayMap = new ArrayMap<String, View>();
        if (this.mSharedElementSourceNames != null) {
            FragmentTransitionCompat21.findNamedViews(arrayMap, fragment.getView());
            if (bl2) {
                arrayMap.retainAll(this.mSharedElementTargetNames);
                arrayMap2 = arrayMap;
            } else {
                arrayMap2 = BackStackRecord.remapNames(this.mSharedElementSourceNames, this.mSharedElementTargetNames, arrayMap);
            }
        }
        if (bl2) {
            if (fragment.mEnterTransitionCallback != null) {
                fragment.mEnterTransitionCallback.onMapSharedElements(this.mSharedElementTargetNames, arrayMap2);
            }
            this.setBackNameOverrides(transitionState, arrayMap2, false);
            return arrayMap2;
        }
        if (fragment.mExitTransitionCallback != null) {
            fragment.mExitTransitionCallback.onMapSharedElements(this.mSharedElementTargetNames, arrayMap2);
        }
        this.setNameOverrides(transitionState, arrayMap2, false);
        return arrayMap2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setBackNameOverrides(TransitionState transitionState, ArrayMap<String, View> arrayMap, boolean bl2) {
        if (this.mSharedElementTargetNames == null) {
            return;
        }
        int n2 = this.mSharedElementTargetNames.size();
        int n3 = 0;
        while (n3 < n2) {
            String string2 = this.mSharedElementSourceNames.get(n3);
            View view = (View)arrayMap.get(this.mSharedElementTargetNames.get(n3));
            if (view != null) {
                String string3 = FragmentTransitionCompat21.getTransitionName(view);
                if (bl2) {
                    BackStackRecord.setNameOverride(transitionState.nameOverrides, string2, string3);
                } else {
                    BackStackRecord.setNameOverride(transitionState.nameOverrides, string3, string2);
                }
            }
            ++n3;
        }
        return;
    }

    private static void setFirstOut(SparseArray<Fragment> sparseArray, SparseArray<Fragment> sparseArray2, Fragment fragment) {
        int n2;
        if (fragment != null && (n2 = fragment.mContainerId) != 0 && !fragment.isHidden()) {
            if (fragment.isAdded() && fragment.getView() != null && sparseArray.get(n2) == null) {
                sparseArray.put(n2, (Object)fragment);
            }
            if (sparseArray2.get(n2) == fragment) {
                sparseArray2.remove(n2);
            }
        }
    }

    private void setLastIn(SparseArray<Fragment> sparseArray, SparseArray<Fragment> sparseArray2, Fragment fragment) {
        if (fragment != null) {
            int n2 = fragment.mContainerId;
            if (n2 != 0) {
                if (!fragment.isAdded()) {
                    sparseArray2.put(n2, (Object)fragment);
                }
                if (sparseArray.get(n2) == fragment) {
                    sparseArray.remove(n2);
                }
            }
            if (fragment.mState < 1 && this.mManager.mCurState >= 1) {
                this.mManager.makeActive(fragment);
                this.mManager.moveToState(fragment, 1, 0, 0, false);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void setNameOverride(ArrayMap<String, String> arrayMap, String string2, String string3) {
        if (string2 == null || string3 == null) return;
        for (int i2 = 0; i2 < arrayMap.size(); ++i2) {
            if (!string2.equals(arrayMap.valueAt(i2))) continue;
            arrayMap.setValueAt(i2, string3);
            return;
        }
        arrayMap.put(string2, string3);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setNameOverrides(TransitionState transitionState, ArrayMap<String, View> arrayMap, boolean bl2) {
        int n2 = arrayMap.size();
        int n3 = 0;
        while (n3 < n2) {
            String string2 = (String)arrayMap.keyAt(n3);
            String string3 = FragmentTransitionCompat21.getTransitionName((View)arrayMap.valueAt(n3));
            if (bl2) {
                BackStackRecord.setNameOverride(transitionState.nameOverrides, string2, string3);
            } else {
                BackStackRecord.setNameOverride(transitionState.nameOverrides, string3, string2);
            }
            ++n3;
        }
        return;
    }

    private static void setNameOverrides(TransitionState transitionState, ArrayList<String> arrayList, ArrayList<String> arrayList2) {
        if (arrayList != null) {
            for (int i2 = 0; i2 < arrayList.size(); ++i2) {
                String string2 = arrayList.get(i2);
                String string3 = arrayList2.get(i2);
                BackStackRecord.setNameOverride(transitionState.nameOverrides, string2, string3);
            }
        }
    }

    @Override
    public FragmentTransaction add(int n2, Fragment fragment) {
        this.doAddOp(n2, fragment, null, 1);
        return this;
    }

    @Override
    public FragmentTransaction add(int n2, Fragment fragment, String string2) {
        this.doAddOp(n2, fragment, string2, 1);
        return this;
    }

    @Override
    public FragmentTransaction add(Fragment fragment, String string2) {
        this.doAddOp(0, fragment, string2, 1);
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    void addOp(Op op) {
        if (this.mHead == null) {
            this.mTail = op;
            this.mHead = op;
        } else {
            op.prev = this.mTail;
            this.mTail.next = op;
            this.mTail = op;
        }
        op.enterAnim = this.mEnterAnim;
        op.exitAnim = this.mExitAnim;
        op.popEnterAnim = this.mPopEnterAnim;
        op.popExitAnim = this.mPopExitAnim;
        ++this.mNumOp;
    }

    @Override
    public FragmentTransaction addSharedElement(View object, String string2) {
        if (SUPPORTS_TRANSITIONS) {
            if ((object = FragmentTransitionCompat21.getTransitionName(object)) == null) {
                throw new IllegalArgumentException("Unique transitionNames are required for all sharedElements");
            }
            if (this.mSharedElementSourceNames == null) {
                this.mSharedElementSourceNames = new ArrayList();
                this.mSharedElementTargetNames = new ArrayList();
            }
            this.mSharedElementSourceNames.add((String)object);
            this.mSharedElementTargetNames.add(string2);
        }
        return this;
    }

    @Override
    public FragmentTransaction addToBackStack(String string2) {
        if (!this.mAllowAddToBackStack) {
            throw new IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
        }
        this.mAddToBackStack = true;
        this.mName = string2;
        return this;
    }

    @Override
    public FragmentTransaction attach(Fragment fragment) {
        Op op = new Op();
        op.cmd = 7;
        op.fragment = fragment;
        this.addOp(op);
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    void bumpBackStackNesting(int n2) {
        if (this.mAddToBackStack) {
            if (FragmentManagerImpl.DEBUG) {
                Log.v((String)TAG, (String)("Bump nesting in " + this + " by " + n2));
            }
            Op op = this.mHead;
            while (op != null) {
                Fragment fragment;
                if (op.fragment != null) {
                    fragment = op.fragment;
                    fragment.mBackStackNesting += n2;
                    if (FragmentManagerImpl.DEBUG) {
                        Log.v((String)TAG, (String)("Bump nesting of " + op.fragment + " to " + op.fragment.mBackStackNesting));
                    }
                }
                if (op.removed != null) {
                    for (int i2 = op.removed.size() - 1; i2 >= 0; --i2) {
                        fragment = op.removed.get(i2);
                        fragment.mBackStackNesting += n2;
                        if (!FragmentManagerImpl.DEBUG) continue;
                        Log.v((String)TAG, (String)("Bump nesting of " + fragment + " to " + fragment.mBackStackNesting));
                    }
                }
                op = op.next;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void calculateBackFragments(SparseArray<Fragment> sparseArray, SparseArray<Fragment> sparseArray2) {
        if (this.mManager.mContainer.onHasView()) {
            Op op = this.mTail;
            while (op != null) {
                switch (op.cmd) {
                    case 1: {
                        BackStackRecord.setFirstOut(sparseArray, sparseArray2, op.fragment);
                        break;
                    }
                    case 2: {
                        if (op.removed != null) {
                            for (int i2 = op.removed.size() - 1; i2 >= 0; --i2) {
                                this.setLastIn(sparseArray, sparseArray2, op.removed.get(i2));
                            }
                        }
                        BackStackRecord.setFirstOut(sparseArray, sparseArray2, op.fragment);
                        break;
                    }
                    case 3: {
                        this.setLastIn(sparseArray, sparseArray2, op.fragment);
                        break;
                    }
                    case 4: {
                        this.setLastIn(sparseArray, sparseArray2, op.fragment);
                        break;
                    }
                    case 5: {
                        BackStackRecord.setFirstOut(sparseArray, sparseArray2, op.fragment);
                        break;
                    }
                    case 6: {
                        this.setLastIn(sparseArray, sparseArray2, op.fragment);
                        break;
                    }
                    case 7: {
                        BackStackRecord.setFirstOut(sparseArray, sparseArray2, op.fragment);
                        break;
                    }
                }
                op = op.prev;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void callSharedElementEnd(TransitionState object, Fragment fragment, Fragment fragment2, boolean bl2, ArrayMap<String, View> arrayMap) {
        object = bl2 ? fragment2.mEnterTransitionCallback : fragment.mEnterTransitionCallback;
        if (object != null) {
            ((SharedElementCallback)object).onSharedElementEnd(new ArrayList<String>(arrayMap.keySet()), new ArrayList<View>(arrayMap.values()), null);
        }
    }

    @Override
    public int commit() {
        return this.commitInternal(false);
    }

    @Override
    public int commitAllowingStateLoss() {
        return this.commitInternal(true);
    }

    /*
     * Enabled aggressive block sorting
     */
    int commitInternal(boolean bl2) {
        if (this.mCommitted) {
            throw new IllegalStateException("commit already called");
        }
        if (FragmentManagerImpl.DEBUG) {
            Log.v((String)TAG, (String)("Commit: " + this));
            this.dump("  ", null, new PrintWriter(new LogWriter(TAG)), null);
        }
        this.mCommitted = true;
        this.mIndex = this.mAddToBackStack ? this.mManager.allocBackStackIndex(this) : -1;
        this.mManager.enqueueAction(this, bl2);
        return this.mIndex;
    }

    @Override
    public void commitNow() {
        this.disallowAddToBackStack();
        this.mManager.execSingleAction(this, false);
    }

    @Override
    public void commitNowAllowingStateLoss() {
        this.disallowAddToBackStack();
        this.mManager.execSingleAction(this, true);
    }

    @Override
    public FragmentTransaction detach(Fragment fragment) {
        Op op = new Op();
        op.cmd = 6;
        op.fragment = fragment;
        this.addOp(op);
        return this;
    }

    @Override
    public FragmentTransaction disallowAddToBackStack() {
        if (this.mAddToBackStack) {
            throw new IllegalStateException("This transaction is already being added to the back stack");
        }
        this.mAllowAddToBackStack = false;
        return this;
    }

    public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] stringArray) {
        this.dump(string2, printWriter, true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void dump(String string2, PrintWriter printWriter, boolean bl2) {
        if (bl2) {
            printWriter.print(string2);
            printWriter.print("mName=");
            printWriter.print(this.mName);
            printWriter.print(" mIndex=");
            printWriter.print(this.mIndex);
            printWriter.print(" mCommitted=");
            printWriter.println(this.mCommitted);
            if (this.mTransition != 0) {
                printWriter.print(string2);
                printWriter.print("mTransition=#");
                printWriter.print(Integer.toHexString(this.mTransition));
                printWriter.print(" mTransitionStyle=#");
                printWriter.println(Integer.toHexString(this.mTransitionStyle));
            }
            if (this.mEnterAnim != 0 || this.mExitAnim != 0) {
                printWriter.print(string2);
                printWriter.print("mEnterAnim=#");
                printWriter.print(Integer.toHexString(this.mEnterAnim));
                printWriter.print(" mExitAnim=#");
                printWriter.println(Integer.toHexString(this.mExitAnim));
            }
            if (this.mPopEnterAnim != 0 || this.mPopExitAnim != 0) {
                printWriter.print(string2);
                printWriter.print("mPopEnterAnim=#");
                printWriter.print(Integer.toHexString(this.mPopEnterAnim));
                printWriter.print(" mPopExitAnim=#");
                printWriter.println(Integer.toHexString(this.mPopExitAnim));
            }
            if (this.mBreadCrumbTitleRes != 0 || this.mBreadCrumbTitleText != null) {
                printWriter.print(string2);
                printWriter.print("mBreadCrumbTitleRes=#");
                printWriter.print(Integer.toHexString(this.mBreadCrumbTitleRes));
                printWriter.print(" mBreadCrumbTitleText=");
                printWriter.println(this.mBreadCrumbTitleText);
            }
            if (this.mBreadCrumbShortTitleRes != 0 || this.mBreadCrumbShortTitleText != null) {
                printWriter.print(string2);
                printWriter.print("mBreadCrumbShortTitleRes=#");
                printWriter.print(Integer.toHexString(this.mBreadCrumbShortTitleRes));
                printWriter.print(" mBreadCrumbShortTitleText=");
                printWriter.println(this.mBreadCrumbShortTitleText);
            }
        }
        if (this.mHead != null) {
            printWriter.print(string2);
            printWriter.println("Operations:");
            String string3 = string2 + "    ";
            Op op = this.mHead;
            int n2 = 0;
            while (op != null) {
                String string4;
                switch (op.cmd) {
                    default: {
                        string4 = "cmd=" + op.cmd;
                        break;
                    }
                    case 0: {
                        string4 = "NULL";
                        break;
                    }
                    case 1: {
                        string4 = "ADD";
                        break;
                    }
                    case 2: {
                        string4 = "REPLACE";
                        break;
                    }
                    case 3: {
                        string4 = "REMOVE";
                        break;
                    }
                    case 4: {
                        string4 = "HIDE";
                        break;
                    }
                    case 5: {
                        string4 = "SHOW";
                        break;
                    }
                    case 6: {
                        string4 = "DETACH";
                        break;
                    }
                    case 7: {
                        string4 = "ATTACH";
                    }
                }
                printWriter.print(string2);
                printWriter.print("  Op #");
                printWriter.print(n2);
                printWriter.print(": ");
                printWriter.print(string4);
                printWriter.print(" ");
                printWriter.println(op.fragment);
                if (bl2) {
                    if (op.enterAnim != 0 || op.exitAnim != 0) {
                        printWriter.print(string2);
                        printWriter.print("enterAnim=#");
                        printWriter.print(Integer.toHexString(op.enterAnim));
                        printWriter.print(" exitAnim=#");
                        printWriter.println(Integer.toHexString(op.exitAnim));
                    }
                    if (op.popEnterAnim != 0 || op.popExitAnim != 0) {
                        printWriter.print(string2);
                        printWriter.print("popEnterAnim=#");
                        printWriter.print(Integer.toHexString(op.popEnterAnim));
                        printWriter.print(" popExitAnim=#");
                        printWriter.println(Integer.toHexString(op.popExitAnim));
                    }
                }
                if (op.removed != null && op.removed.size() > 0) {
                    for (int i2 = 0; i2 < op.removed.size(); ++i2) {
                        printWriter.print(string3);
                        if (op.removed.size() == 1) {
                            printWriter.print("Removed: ");
                        } else {
                            if (i2 == 0) {
                                printWriter.println("Removed:");
                            }
                            printWriter.print(string3);
                            printWriter.print("  #");
                            printWriter.print(i2);
                            printWriter.print(": ");
                        }
                        printWriter.println(op.removed.get(i2));
                    }
                }
                op = op.next;
                ++n2;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void excludeHiddenFragments(TransitionState transitionState, int n2, Object object) {
        if (this.mManager.mAdded != null) {
            for (int i2 = 0; i2 < this.mManager.mAdded.size(); ++i2) {
                Fragment fragment = this.mManager.mAdded.get(i2);
                if (fragment.mView == null || fragment.mContainer == null || fragment.mContainerId != n2) continue;
                if (fragment.mHidden) {
                    if (transitionState.hiddenFragmentViews.contains(fragment.mView)) continue;
                    FragmentTransitionCompat21.excludeTarget(object, fragment.mView, true);
                    transitionState.hiddenFragmentViews.add(fragment.mView);
                    continue;
                }
                FragmentTransitionCompat21.excludeTarget(object, fragment.mView, false);
                transitionState.hiddenFragmentViews.remove(fragment.mView);
            }
        }
    }

    @Override
    public CharSequence getBreadCrumbShortTitle() {
        if (this.mBreadCrumbShortTitleRes != 0) {
            return this.mManager.mHost.getContext().getText(this.mBreadCrumbShortTitleRes);
        }
        return this.mBreadCrumbShortTitleText;
    }

    @Override
    public int getBreadCrumbShortTitleRes() {
        return this.mBreadCrumbShortTitleRes;
    }

    @Override
    public CharSequence getBreadCrumbTitle() {
        if (this.mBreadCrumbTitleRes != 0) {
            return this.mManager.mHost.getContext().getText(this.mBreadCrumbTitleRes);
        }
        return this.mBreadCrumbTitleText;
    }

    @Override
    public int getBreadCrumbTitleRes() {
        return this.mBreadCrumbTitleRes;
    }

    @Override
    public int getId() {
        return this.mIndex;
    }

    @Override
    public String getName() {
        return this.mName;
    }

    public int getTransition() {
        return this.mTransition;
    }

    public int getTransitionStyle() {
        return this.mTransitionStyle;
    }

    @Override
    public FragmentTransaction hide(Fragment fragment) {
        Op op = new Op();
        op.cmd = 4;
        op.fragment = fragment;
        this.addOp(op);
        return this;
    }

    @Override
    public boolean isAddToBackStackAllowed() {
        return this.mAllowAddToBackStack;
    }

    @Override
    public boolean isEmpty() {
        return this.mNumOp == 0;
    }

    ArrayMap<String, View> mapSharedElementsIn(TransitionState transitionState, boolean bl2, Fragment fragment) {
        ArrayMap<String, View> arrayMap = this.mapEnteringSharedElements(transitionState, fragment, bl2);
        if (bl2) {
            if (fragment.mExitTransitionCallback != null) {
                fragment.mExitTransitionCallback.onMapSharedElements(this.mSharedElementTargetNames, arrayMap);
            }
            this.setBackNameOverrides(transitionState, arrayMap, true);
            return arrayMap;
        }
        if (fragment.mEnterTransitionCallback != null) {
            fragment.mEnterTransitionCallback.onMapSharedElements(this.mSharedElementTargetNames, arrayMap);
        }
        this.setNameOverrides(transitionState, arrayMap, true);
        return arrayMap;
    }

    /*
     * Enabled aggressive block sorting
     */
    public TransitionState popFromBackStack(boolean bl2, TransitionState object, SparseArray<Fragment> object2, SparseArray<Fragment> sparseArray) {
        Object object3;
        block18: {
            block19: {
                block20: {
                    if (FragmentManagerImpl.DEBUG) {
                        Log.v((String)TAG, (String)("popFromBackStack: " + this));
                        this.dump("  ", null, new PrintWriter(new LogWriter(TAG)), null);
                    }
                    object3 = object;
                    if (!SUPPORTS_TRANSITIONS) break block18;
                    object3 = object;
                    if (this.mManager.mCurState < 1) break block18;
                    if (object != null) break block19;
                    if (object2.size() != 0) break block20;
                    object3 = object;
                    if (sparseArray.size() == 0) break block18;
                }
                object3 = this.beginTransition((SparseArray<Fragment>)object2, sparseArray, true);
                break block18;
            }
            object3 = object;
            if (!bl2) {
                BackStackRecord.setNameOverrides((TransitionState)object, this.mSharedElementTargetNames, this.mSharedElementSourceNames);
                object3 = object;
            }
        }
        this.bumpBackStackNesting(-1);
        int n2 = object3 != null ? 0 : this.mTransitionStyle;
        int n3 = object3 != null ? 0 : this.mTransition;
        object = this.mTail;
        while (object != null) {
            int n4 = object3 != null ? 0 : ((Op)object).popEnterAnim;
            int n5 = object3 != null ? 0 : ((Op)object).popExitAnim;
            switch (((Op)object).cmd) {
                default: {
                    throw new IllegalArgumentException("Unknown cmd: " + ((Op)object).cmd);
                }
                case 1: {
                    object2 = ((Op)object).fragment;
                    object2.mNextAnim = n5;
                    this.mManager.removeFragment((Fragment)object2, FragmentManagerImpl.reverseTransit(n3), n2);
                    break;
                }
                case 2: {
                    object2 = ((Op)object).fragment;
                    if (object2 != null) {
                        object2.mNextAnim = n5;
                        this.mManager.removeFragment((Fragment)object2, FragmentManagerImpl.reverseTransit(n3), n2);
                    }
                    if (((Op)object).removed == null) break;
                    for (n5 = 0; n5 < ((Op)object).removed.size(); ++n5) {
                        object2 = ((Op)object).removed.get(n5);
                        object2.mNextAnim = n4;
                        this.mManager.addFragment((Fragment)object2, false);
                    }
                    break;
                }
                case 3: {
                    object2 = ((Op)object).fragment;
                    object2.mNextAnim = n4;
                    this.mManager.addFragment((Fragment)object2, false);
                    break;
                }
                case 4: {
                    object2 = ((Op)object).fragment;
                    object2.mNextAnim = n4;
                    this.mManager.showFragment((Fragment)object2, FragmentManagerImpl.reverseTransit(n3), n2);
                    break;
                }
                case 5: {
                    object2 = ((Op)object).fragment;
                    object2.mNextAnim = n5;
                    this.mManager.hideFragment((Fragment)object2, FragmentManagerImpl.reverseTransit(n3), n2);
                    break;
                }
                case 6: {
                    object2 = ((Op)object).fragment;
                    object2.mNextAnim = n4;
                    this.mManager.attachFragment((Fragment)object2, FragmentManagerImpl.reverseTransit(n3), n2);
                    break;
                }
                case 7: {
                    object2 = ((Op)object).fragment;
                    object2.mNextAnim = n4;
                    this.mManager.detachFragment((Fragment)object2, FragmentManagerImpl.reverseTransit(n3), n2);
                }
            }
            object = ((Op)object).prev;
        }
        if (bl2) {
            this.mManager.moveToState(this.mManager.mCurState, FragmentManagerImpl.reverseTransit(n3), n2, true);
            object3 = null;
        }
        if (this.mIndex >= 0) {
            this.mManager.freeBackStackIndex(this.mIndex);
            this.mIndex = -1;
        }
        return object3;
    }

    @Override
    public FragmentTransaction remove(Fragment fragment) {
        Op op = new Op();
        op.cmd = 3;
        op.fragment = fragment;
        this.addOp(op);
        return this;
    }

    @Override
    public FragmentTransaction replace(int n2, Fragment fragment) {
        return this.replace(n2, fragment, null);
    }

    @Override
    public FragmentTransaction replace(int n2, Fragment fragment, String string2) {
        if (n2 == 0) {
            throw new IllegalArgumentException("Must use non-zero containerViewId");
        }
        this.doAddOp(n2, fragment, string2, 2);
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void run() {
        Object object;
        if (FragmentManagerImpl.DEBUG) {
            Log.v((String)TAG, (String)("Run: " + this));
        }
        if (this.mAddToBackStack && this.mIndex < 0) {
            throw new IllegalStateException("addToBackStack() called after commit()");
        }
        this.bumpBackStackNesting(1);
        Object object2 = object = null;
        if (SUPPORTS_TRANSITIONS) {
            object2 = object;
            if (this.mManager.mCurState >= 1) {
                object = new SparseArray();
                object2 = new SparseArray();
                this.calculateFragments((SparseArray<Fragment>)object, (SparseArray<Fragment>)object2);
                object2 = this.beginTransition((SparseArray<Fragment>)object, (SparseArray<Fragment>)object2, false);
            }
        }
        int n2 = object2 != null ? 0 : this.mTransitionStyle;
        int n3 = object2 != null ? 0 : this.mTransition;
        Op op = this.mHead;
        while (op != null) {
            int n4 = object2 != null ? 0 : op.enterAnim;
            int n5 = object2 != null ? 0 : op.exitAnim;
            switch (op.cmd) {
                default: {
                    throw new IllegalArgumentException("Unknown cmd: " + op.cmd);
                }
                case 1: {
                    object = op.fragment;
                    object.mNextAnim = n4;
                    this.mManager.addFragment((Fragment)object, false);
                    break;
                }
                case 2: {
                    object = op.fragment;
                    int n6 = object.mContainerId;
                    Object object3 = object;
                    if (this.mManager.mAdded != null) {
                        int n7 = this.mManager.mAdded.size() - 1;
                        while (true) {
                            object3 = object;
                            if (n7 < 0) break;
                            Fragment fragment = this.mManager.mAdded.get(n7);
                            if (FragmentManagerImpl.DEBUG) {
                                Log.v((String)TAG, (String)("OP_REPLACE: adding=" + object + " old=" + fragment));
                            }
                            object3 = object;
                            if (fragment.mContainerId == n6) {
                                if (fragment == object) {
                                    object3 = null;
                                    op.fragment = null;
                                } else {
                                    if (op.removed == null) {
                                        op.removed = new ArrayList();
                                    }
                                    op.removed.add(fragment);
                                    fragment.mNextAnim = n5;
                                    if (this.mAddToBackStack) {
                                        ++fragment.mBackStackNesting;
                                        if (FragmentManagerImpl.DEBUG) {
                                            Log.v((String)TAG, (String)("Bump nesting of " + fragment + " to " + fragment.mBackStackNesting));
                                        }
                                    }
                                    this.mManager.removeFragment(fragment, n3, n2);
                                    object3 = object;
                                }
                            }
                            --n7;
                            object = object3;
                        }
                    }
                    if (object3 == null) break;
                    object3.mNextAnim = n4;
                    this.mManager.addFragment((Fragment)object3, false);
                    break;
                }
                case 3: {
                    object = op.fragment;
                    object.mNextAnim = n5;
                    this.mManager.removeFragment((Fragment)object, n3, n2);
                    break;
                }
                case 4: {
                    object = op.fragment;
                    object.mNextAnim = n5;
                    this.mManager.hideFragment((Fragment)object, n3, n2);
                    break;
                }
                case 5: {
                    object = op.fragment;
                    object.mNextAnim = n4;
                    this.mManager.showFragment((Fragment)object, n3, n2);
                    break;
                }
                case 6: {
                    object = op.fragment;
                    object.mNextAnim = n5;
                    this.mManager.detachFragment((Fragment)object, n3, n2);
                    break;
                }
                case 7: {
                    object = op.fragment;
                    object.mNextAnim = n4;
                    this.mManager.attachFragment((Fragment)object, n3, n2);
                }
            }
            op = op.next;
        }
        this.mManager.moveToState(this.mManager.mCurState, n3, n2, true);
        if (this.mAddToBackStack) {
            this.mManager.addBackStackState(this);
        }
    }

    @Override
    public FragmentTransaction setBreadCrumbShortTitle(int n2) {
        this.mBreadCrumbShortTitleRes = n2;
        this.mBreadCrumbShortTitleText = null;
        return this;
    }

    @Override
    public FragmentTransaction setBreadCrumbShortTitle(CharSequence charSequence) {
        this.mBreadCrumbShortTitleRes = 0;
        this.mBreadCrumbShortTitleText = charSequence;
        return this;
    }

    @Override
    public FragmentTransaction setBreadCrumbTitle(int n2) {
        this.mBreadCrumbTitleRes = n2;
        this.mBreadCrumbTitleText = null;
        return this;
    }

    @Override
    public FragmentTransaction setBreadCrumbTitle(CharSequence charSequence) {
        this.mBreadCrumbTitleRes = 0;
        this.mBreadCrumbTitleText = charSequence;
        return this;
    }

    @Override
    public FragmentTransaction setCustomAnimations(int n2, int n3) {
        return this.setCustomAnimations(n2, n3, 0, 0);
    }

    @Override
    public FragmentTransaction setCustomAnimations(int n2, int n3, int n4, int n5) {
        this.mEnterAnim = n2;
        this.mExitAnim = n3;
        this.mPopEnterAnim = n4;
        this.mPopExitAnim = n5;
        return this;
    }

    void setEpicenterIn(ArrayMap<String, View> view, TransitionState transitionState) {
        if (this.mSharedElementTargetNames != null && !view.isEmpty() && (view = (View)view.get(this.mSharedElementTargetNames.get(0))) != null) {
            transitionState.enteringEpicenterView.epicenter = view;
        }
    }

    @Override
    public FragmentTransaction setTransition(int n2) {
        this.mTransition = n2;
        return this;
    }

    @Override
    public FragmentTransaction setTransitionStyle(int n2) {
        this.mTransitionStyle = n2;
        return this;
    }

    @Override
    public FragmentTransaction show(Fragment fragment) {
        Op op = new Op();
        op.cmd = 5;
        op.fragment = fragment;
        this.addOp(op);
        return this;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("BackStackEntry{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        if (this.mIndex >= 0) {
            stringBuilder.append(" #");
            stringBuilder.append(this.mIndex);
        }
        if (this.mName != null) {
            stringBuilder.append(" ");
            stringBuilder.append(this.mName);
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    static final class Op {
        int cmd;
        int enterAnim;
        int exitAnim;
        Fragment fragment;
        Op next;
        int popEnterAnim;
        int popExitAnim;
        Op prev;
        ArrayList<Fragment> removed;

        Op() {
        }
    }

    public class TransitionState {
        public FragmentTransitionCompat21.EpicenterView enteringEpicenterView;
        public ArrayList<View> hiddenFragmentViews;
        public ArrayMap<String, String> nameOverrides = new ArrayMap();
        public View nonExistentView;

        public TransitionState() {
            this.hiddenFragmentViews = new ArrayList();
            this.enteringEpicenterView = new FragmentTransitionCompat21.EpicenterView();
        }
    }
}

