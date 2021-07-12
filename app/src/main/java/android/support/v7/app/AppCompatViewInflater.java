/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.InflateException
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package android.support.v7.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

class AppCompatViewInflater {
    private static final String LOG_TAG = "AppCompatViewInflater";
    private static final String[] sClassPrefixList;
    private static final Map<String, Constructor<? extends View>> sConstructorMap;
    private static final Class<?>[] sConstructorSignature;
    private static final int[] sOnClickAttrs;
    private final Object[] mConstructorArgs = new Object[2];

    static {
        sConstructorSignature = new Class[]{Context.class, AttributeSet.class};
        sOnClickAttrs = new int[]{16843375};
        sClassPrefixList = new String[]{"android.widget.", "android.view.", "android.webkit."};
        sConstructorMap = new ArrayMap<String, Constructor<? extends View>>();
    }

    AppCompatViewInflater() {
    }

    private void checkOnClickListener(View view, AttributeSet attributeSet) {
        Object object = view.getContext();
        if (!(object instanceof ContextWrapper) || Build.VERSION.SDK_INT >= 15 && !ViewCompat.hasOnClickListeners(view)) {
            return;
        }
        if ((object = (attributeSet = object.obtainStyledAttributes(attributeSet, sOnClickAttrs)).getString(0)) != null) {
            view.setOnClickListener((View.OnClickListener)new DeclaredOnClickListener(view, (String)object));
        }
        attributeSet.recycle();
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private View createView(Context var1_1, String var2_3, String var3_4) throws ClassNotFoundException, InflateException {
        block4: {
            var5_5 = AppCompatViewInflater.sConstructorMap.get(var2_3);
            var4_6 = var5_5;
            if (var5_5 != null) ** GOTO lbl13
            var4_6 = var1_1 /* !! */ .getClassLoader();
            if (var3_4 == null) break block4;
            try {
                var1_1 /* !! */  = var3_4 + var2_3;
lbl9:
                // 2 sources

                while (true) {
                    var4_6 = var4_6.loadClass((String)var1_1 /* !! */ ).asSubclass(View.class).getConstructor(AppCompatViewInflater.sConstructorSignature);
                    AppCompatViewInflater.sConstructorMap.put(var2_3, var4_6);
lbl13:
                    // 2 sources

                    var4_6.setAccessible(true);
                    var1_1 /* !! */  = (View)var4_6.newInstance(this.mConstructorArgs);
                    return var1_1 /* !! */ ;
                }
            }
            catch (Exception var1_2) {
                return null;
            }
        }
        var1_1 /* !! */  = var2_3;
        ** while (true)
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private View createViewFromTag(Context context, String string2, AttributeSet attributeSet) {
        int n2;
        String string3 = string2;
        if (string2.equals("view")) {
            string3 = attributeSet.getAttributeValue(null, "class");
        }
        try {
            this.mConstructorArgs[0] = context;
            this.mConstructorArgs[1] = attributeSet;
            if (-1 != string3.indexOf(46)) return this.createView(context, string3, null);
            n2 = 0;
        }
        catch (Exception exception) {
            return null;
        }
        catch (Throwable throwable) {
            throw throwable;
        }
        while (true) {
            if (n2 >= sClassPrefixList.length) {
                this.mConstructorArgs[0] = null;
                this.mConstructorArgs[1] = null;
                return null;
            }
            string2 = this.createView(context, string3, sClassPrefixList[n2]);
            if (string2 != null) {
                this.mConstructorArgs[0] = null;
                this.mConstructorArgs[1] = null;
                return string2;
            }
            ++n2;
        }
    }

    private static Context themifyContext(Context context, AttributeSet object, boolean bl2, boolean bl3) {
        block7: {
            int n2;
            block8: {
                object = context.obtainStyledAttributes(object, R.styleable.View, 0, 0);
                int n3 = 0;
                if (bl2) {
                    n3 = object.getResourceId(R.styleable.View_android_theme, 0);
                }
                n2 = n3;
                if (bl3) {
                    n2 = n3;
                    if (n3 == 0) {
                        n2 = n3 = object.getResourceId(R.styleable.View_theme, 0);
                        if (n3 != 0) {
                            Log.i((String)LOG_TAG, (String)"app:theme is now deprecated. Please move to using android:theme instead.");
                            n2 = n3;
                        }
                    }
                }
                object.recycle();
                object = context;
                if (n2 == 0) break block7;
                if (!(context instanceof ContextThemeWrapper)) break block8;
                object = context;
                if (((ContextThemeWrapper)context).getThemeResId() == n2) break block7;
            }
            object = new ContextThemeWrapper(context, n2);
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final View createView(View object, String string2, @NonNull Context context, @NonNull AttributeSet attributeSet, boolean bl2, boolean bl3, boolean bl4) {
        Context context2;
        block39: {
            block38: {
                context2 = context;
                if (bl2) {
                    context2 = context;
                    if (object != null) {
                        context2 = object.getContext();
                    }
                }
                object = context2;
                if (bl3) break block38;
                context2 = object;
                if (!bl4) break block39;
            }
            context2 = AppCompatViewInflater.themifyContext((Context)object, attributeSet, bl3, bl4);
        }
        object = null;
        int n2 = -1;
        switch (string2.hashCode()) {
            case -938935918: {
                if (!string2.equals("TextView")) break;
                n2 = 0;
                break;
            }
            case 1125864064: {
                if (!string2.equals("ImageView")) break;
                n2 = 1;
                break;
            }
            case 2001146706: {
                if (!string2.equals("Button")) break;
                n2 = 2;
                break;
            }
            case 1666676343: {
                if (!string2.equals("EditText")) break;
                n2 = 3;
                break;
            }
            case -339785223: {
                if (!string2.equals("Spinner")) break;
                n2 = 4;
                break;
            }
            case -937446323: {
                if (!string2.equals("ImageButton")) break;
                n2 = 5;
                break;
            }
            case 1601505219: {
                if (!string2.equals("CheckBox")) break;
                n2 = 6;
                break;
            }
            case 776382189: {
                if (!string2.equals("RadioButton")) break;
                n2 = 7;
                break;
            }
            case -1455429095: {
                if (!string2.equals("CheckedTextView")) break;
                n2 = 8;
                break;
            }
            case 1413872058: {
                if (!string2.equals("AutoCompleteTextView")) break;
                n2 = 9;
                break;
            }
            case -1346021293: {
                if (!string2.equals("MultiAutoCompleteTextView")) break;
                n2 = 10;
                break;
            }
            case -1946472170: {
                if (!string2.equals("RatingBar")) break;
                n2 = 11;
                break;
            }
            case -658531749: {
                if (!string2.equals("SeekBar")) break;
                n2 = 12;
                break;
            }
        }
        switch (n2) {
            case 0: {
                object = new AppCompatTextView(context2, attributeSet);
                break;
            }
            case 1: {
                object = new AppCompatImageView(context2, attributeSet);
                break;
            }
            case 2: {
                object = new AppCompatButton(context2, attributeSet);
                break;
            }
            case 3: {
                object = new AppCompatEditText(context2, attributeSet);
                break;
            }
            case 4: {
                object = new AppCompatSpinner(context2, attributeSet);
                break;
            }
            case 5: {
                object = new AppCompatImageButton(context2, attributeSet);
                break;
            }
            case 6: {
                object = new AppCompatCheckBox(context2, attributeSet);
                break;
            }
            case 7: {
                object = new AppCompatRadioButton(context2, attributeSet);
                break;
            }
            case 8: {
                object = new AppCompatCheckedTextView(context2, attributeSet);
                break;
            }
            case 9: {
                object = new AppCompatAutoCompleteTextView(context2, attributeSet);
                break;
            }
            case 10: {
                object = new AppCompatMultiAutoCompleteTextView(context2, attributeSet);
                break;
            }
            case 11: {
                object = new AppCompatRatingBar(context2, attributeSet);
                break;
            }
            case 12: {
                object = new AppCompatSeekBar(context2, attributeSet);
                break;
            }
        }
        Object object2 = object;
        if (object == null) {
            object2 = object;
            if (context != context2) {
                object2 = this.createViewFromTag(context2, string2, attributeSet);
            }
        }
        if (object2 != null) {
            this.checkOnClickListener((View)object2, attributeSet);
        }
        return object2;
    }

    private static class DeclaredOnClickListener
    implements View.OnClickListener {
        private final View mHostView;
        private final String mMethodName;
        private Context mResolvedContext;
        private Method mResolvedMethod;

        public DeclaredOnClickListener(@NonNull View view, @NonNull String string2) {
            this.mHostView = view;
            this.mMethodName = string2;
        }

        /*
         * WARNING - void declaration
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @NonNull
        private void resolveMethod(@Nullable Context object, @NonNull String object2) {
            void var1_6;
            void var1_2;
            while (var1_2 != null) {
                block7: {
                    Method method;
                    if (var1_2.isRestricted() || (method = var1_2.getClass().getMethod(this.mMethodName, View.class)) == null) break block7;
                    try {
                        this.mResolvedMethod = method;
                        this.mResolvedContext = var1_2;
                        return;
                    }
                    catch (NoSuchMethodException noSuchMethodException) {
                        // empty catch block
                    }
                }
                if (var1_2 instanceof ContextWrapper) {
                    Context context = ((ContextWrapper)var1_2).getBaseContext();
                    continue;
                }
                Object var1_4 = null;
            }
            int n2 = this.mHostView.getId();
            if (n2 == -1) {
                String string2 = "";
                throw new IllegalStateException("Could not find method " + this.mMethodName + "(View) in a parent or ancestor Context for android:onClick " + "attribute defined on view " + this.mHostView.getClass() + (String)var1_6);
            }
            String string3 = " with id '" + this.mHostView.getContext().getResources().getResourceEntryName(n2) + "'";
            throw new IllegalStateException("Could not find method " + this.mMethodName + "(View) in a parent or ancestor Context for android:onClick " + "attribute defined on view " + this.mHostView.getClass() + (String)var1_6);
        }

        public void onClick(@NonNull View view) {
            if (this.mResolvedMethod == null) {
                this.resolveMethod(this.mHostView.getContext(), this.mMethodName);
            }
            try {
                this.mResolvedMethod.invoke(this.mResolvedContext, view);
                return;
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new IllegalStateException("Could not execute non-public method for android:onClick", illegalAccessException);
            }
            catch (InvocationTargetException invocationTargetException) {
                throw new IllegalStateException("Could not execute method for android:onClick", invocationTargetException);
            }
        }
    }
}

