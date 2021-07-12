/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.graphics.ColorFilter
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffColorFilter
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.graphics.drawable.LayerDrawable
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.util.TypedValue
 *  android.util.Xml
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.LruCache;
import android.support.v7.appcompat.R;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.ThemeUtils;
import android.support.v7.widget.TintInfo;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.util.Xml;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class AppCompatDrawableManager {
    private static final int[] COLORFILTER_COLOR_BACKGROUND_MULTIPLY;
    private static final int[] COLORFILTER_COLOR_CONTROL_ACTIVATED;
    private static final int[] COLORFILTER_TINT_COLOR_CONTROL_NORMAL;
    private static final ColorFilterLruCache COLOR_FILTER_CACHE;
    private static final boolean DEBUG = false;
    private static final PorterDuff.Mode DEFAULT_MODE;
    private static AppCompatDrawableManager INSTANCE;
    private static final String PLATFORM_VD_CLAZZ = "android.graphics.drawable.VectorDrawable";
    private static final String SKIP_DRAWABLE_TAG = "appcompat_skip_skip";
    private static final String TAG = "AppCompatDrawableManager";
    private static final int[] TINT_CHECKABLE_BUTTON_LIST;
    private static final int[] TINT_COLOR_CONTROL_NORMAL;
    private static final int[] TINT_COLOR_CONTROL_STATE_LIST;
    private ArrayMap<String, InflateDelegate> mDelegates;
    private final Object mDrawableCacheLock = new Object();
    private final WeakHashMap<Context, LongSparseArray<WeakReference<Drawable.ConstantState>>> mDrawableCaches = new WeakHashMap(0);
    private boolean mHasCheckedVectorDrawableSetup;
    private SparseArray<String> mKnownDrawableIdTags;
    private WeakHashMap<Context, SparseArray<ColorStateList>> mTintLists;
    private TypedValue mTypedValue;

    static {
        DEFAULT_MODE = PorterDuff.Mode.SRC_IN;
        COLOR_FILTER_CACHE = new ColorFilterLruCache(6);
        COLORFILTER_TINT_COLOR_CONTROL_NORMAL = new int[]{R.drawable.abc_textfield_search_default_mtrl_alpha, R.drawable.abc_textfield_default_mtrl_alpha, R.drawable.abc_ab_share_pack_mtrl_alpha};
        TINT_COLOR_CONTROL_NORMAL = new int[]{R.drawable.abc_ic_ab_back_mtrl_am_alpha, R.drawable.abc_ic_go_search_api_mtrl_alpha, R.drawable.abc_ic_search_api_mtrl_alpha, R.drawable.abc_ic_commit_search_api_mtrl_alpha, R.drawable.abc_ic_clear_mtrl_alpha, R.drawable.abc_ic_menu_share_mtrl_alpha, R.drawable.abc_ic_menu_copy_mtrl_am_alpha, R.drawable.abc_ic_menu_cut_mtrl_alpha, R.drawable.abc_ic_menu_selectall_mtrl_alpha, R.drawable.abc_ic_menu_paste_mtrl_am_alpha, R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha, R.drawable.abc_ic_voice_search_api_mtrl_alpha};
        COLORFILTER_COLOR_CONTROL_ACTIVATED = new int[]{R.drawable.abc_textfield_activated_mtrl_alpha, R.drawable.abc_textfield_search_activated_mtrl_alpha, R.drawable.abc_cab_background_top_mtrl_alpha, R.drawable.abc_text_cursor_material};
        COLORFILTER_COLOR_BACKGROUND_MULTIPLY = new int[]{R.drawable.abc_popup_background_mtrl_mult, R.drawable.abc_cab_background_internal_bg, R.drawable.abc_menu_hardkey_panel_mtrl_mult};
        TINT_COLOR_CONTROL_STATE_LIST = new int[]{R.drawable.abc_edit_text_material, R.drawable.abc_tab_indicator_material, R.drawable.abc_textfield_search_material, R.drawable.abc_spinner_mtrl_am_alpha, R.drawable.abc_spinner_textfield_background_material, R.drawable.abc_ratingbar_full_material, R.drawable.abc_switch_track_mtrl_alpha, R.drawable.abc_switch_thumb_material, R.drawable.abc_btn_default_mtrl_shape, R.drawable.abc_btn_borderless_material};
        TINT_CHECKABLE_BUTTON_LIST = new int[]{R.drawable.abc_btn_check_material, R.drawable.abc_btn_radio_material};
    }

    private void addDelegate(@NonNull String string2, @NonNull InflateDelegate inflateDelegate) {
        if (this.mDelegates == null) {
            this.mDelegates = new ArrayMap();
        }
        this.mDelegates.put(string2, inflateDelegate);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean addDrawableToCache(@NonNull Context context, long l2, @NonNull Drawable object) {
        Drawable.ConstantState constantState = object.getConstantState();
        if (constantState == null) {
            return false;
        }
        Object object2 = this.mDrawableCacheLock;
        synchronized (object2) {
            LongSparseArray<WeakReference<Drawable.ConstantState>> longSparseArray = this.mDrawableCaches.get(context);
            object = longSparseArray;
            if (longSparseArray == null) {
                object = new LongSparseArray();
                this.mDrawableCaches.put(context, (LongSparseArray<WeakReference<Drawable.ConstantState>>)object);
            }
            object.put(l2, new WeakReference<Drawable.ConstantState>(constantState));
            return true;
        }
    }

    private void addTintListToCache(@NonNull Context context, @DrawableRes int n2, @NonNull ColorStateList colorStateList) {
        SparseArray sparseArray;
        if (this.mTintLists == null) {
            this.mTintLists = new WeakHashMap();
        }
        SparseArray sparseArray2 = sparseArray = this.mTintLists.get(context);
        if (sparseArray == null) {
            sparseArray2 = new SparseArray();
            this.mTintLists.put(context, (SparseArray<ColorStateList>)sparseArray2);
        }
        sparseArray2.append(n2, (Object)colorStateList);
    }

    private static boolean arrayContains(int[] nArray, int n2) {
        int n3 = nArray.length;
        for (int i2 = 0; i2 < n3; ++i2) {
            if (nArray[i2] != n2) continue;
            return true;
        }
        return false;
    }

    private ColorStateList createBorderlessButtonColorStateList(Context context) {
        return this.createButtonColorStateList(context, 0);
    }

    private ColorStateList createButtonColorStateList(Context context, @ColorInt int n2) {
        int[][] nArrayArray = new int[4][];
        int[] nArray = new int[4];
        int n3 = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlHighlight);
        nArrayArray[0] = ThemeUtils.DISABLED_STATE_SET;
        nArray[0] = ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorButtonNormal);
        int n4 = 0 + 1;
        nArrayArray[n4] = ThemeUtils.PRESSED_STATE_SET;
        nArray[n4] = ColorUtils.compositeColors(n3, n2);
        nArrayArray[++n4] = ThemeUtils.FOCUSED_STATE_SET;
        nArray[n4] = ColorUtils.compositeColors(n3, n2);
        n3 = n4 + 1;
        nArrayArray[n3] = ThemeUtils.EMPTY_STATE_SET;
        nArray[n3] = n2;
        return new ColorStateList((int[][])nArrayArray, nArray);
    }

    private static long createCacheKey(TypedValue typedValue) {
        return (long)typedValue.assetCookie << 32 | (long)typedValue.data;
    }

    private ColorStateList createCheckableButtonColorStateList(Context context) {
        int[][] nArrayArray = new int[3][];
        int[] nArray = new int[3];
        nArrayArray[0] = ThemeUtils.DISABLED_STATE_SET;
        nArray[0] = ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorControlNormal);
        int n2 = 0 + 1;
        nArrayArray[n2] = ThemeUtils.CHECKED_STATE_SET;
        nArray[n2] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
        nArrayArray[++n2] = ThemeUtils.EMPTY_STATE_SET;
        nArray[n2] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlNormal);
        return new ColorStateList((int[][])nArrayArray, nArray);
    }

    private ColorStateList createColoredButtonColorStateList(Context context) {
        return this.createButtonColorStateList(context, ThemeUtils.getThemeAttrColor(context, R.attr.colorAccent));
    }

    private ColorStateList createDefaultButtonColorStateList(Context context) {
        return this.createButtonColorStateList(context, ThemeUtils.getThemeAttrColor(context, R.attr.colorButtonNormal));
    }

    private ColorStateList createDefaultColorStateList(Context context) {
        int n2 = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlNormal);
        int n3 = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
        int[][] nArrayArray = new int[7][];
        int[] nArray = new int[7];
        nArrayArray[0] = ThemeUtils.DISABLED_STATE_SET;
        nArray[0] = ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorControlNormal);
        int n4 = 0 + 1;
        nArrayArray[n4] = ThemeUtils.FOCUSED_STATE_SET;
        nArray[n4] = n3;
        nArrayArray[++n4] = ThemeUtils.ACTIVATED_STATE_SET;
        nArray[n4] = n3;
        nArrayArray[++n4] = ThemeUtils.PRESSED_STATE_SET;
        nArray[n4] = n3;
        nArrayArray[++n4] = ThemeUtils.CHECKED_STATE_SET;
        nArray[n4] = n3;
        nArrayArray[++n4] = ThemeUtils.SELECTED_STATE_SET;
        nArray[n4] = n3;
        n3 = n4 + 1;
        nArrayArray[n3] = ThemeUtils.EMPTY_STATE_SET;
        nArray[n3] = n2;
        return new ColorStateList((int[][])nArrayArray, nArray);
    }

    private Drawable createDrawableIfNeeded(@NonNull Context context, @DrawableRes int n2) {
        if (this.mTypedValue == null) {
            this.mTypedValue = new TypedValue();
        }
        TypedValue typedValue = this.mTypedValue;
        context.getResources().getValue(n2, typedValue, true);
        long l2 = AppCompatDrawableManager.createCacheKey(typedValue);
        Drawable drawable2 = this.getCachedDrawable(context, l2);
        if (drawable2 != null) {
            return drawable2;
        }
        if (n2 == R.drawable.abc_cab_background_top_material) {
            drawable2 = new LayerDrawable(new Drawable[]{this.getDrawable(context, R.drawable.abc_cab_background_internal_bg), this.getDrawable(context, R.drawable.abc_cab_background_top_mtrl_alpha)});
        }
        if (drawable2 != null) {
            drawable2.setChangingConfigurations(typedValue.changingConfigurations);
            this.addDrawableToCache(context, l2, drawable2);
        }
        return drawable2;
    }

    private ColorStateList createEditTextColorStateList(Context context) {
        int[][] nArrayArray = new int[3][];
        int[] nArray = new int[3];
        nArrayArray[0] = ThemeUtils.DISABLED_STATE_SET;
        nArray[0] = ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorControlNormal);
        int n2 = 0 + 1;
        nArrayArray[n2] = ThemeUtils.NOT_PRESSED_OR_FOCUSED_STATE_SET;
        nArray[n2] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlNormal);
        nArrayArray[++n2] = ThemeUtils.EMPTY_STATE_SET;
        nArray[n2] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
        return new ColorStateList((int[][])nArrayArray, nArray);
    }

    private ColorStateList createSeekbarThumbColorStateList(Context context) {
        int[][] nArrayArray = new int[2][];
        int[] nArray = new int[2];
        nArrayArray[0] = ThemeUtils.DISABLED_STATE_SET;
        nArray[0] = ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorControlActivated);
        int n2 = 0 + 1;
        nArrayArray[n2] = ThemeUtils.EMPTY_STATE_SET;
        nArray[n2] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
        return new ColorStateList((int[][])nArrayArray, nArray);
    }

    private ColorStateList createSpinnerColorStateList(Context context) {
        int[][] nArrayArray = new int[3][];
        int[] nArray = new int[3];
        nArrayArray[0] = ThemeUtils.DISABLED_STATE_SET;
        nArray[0] = ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorControlNormal);
        int n2 = 0 + 1;
        nArrayArray[n2] = ThemeUtils.NOT_PRESSED_OR_FOCUSED_STATE_SET;
        nArray[n2] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlNormal);
        nArrayArray[++n2] = ThemeUtils.EMPTY_STATE_SET;
        nArray[n2] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
        return new ColorStateList((int[][])nArrayArray, nArray);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private ColorStateList createSwitchThumbColorStateList(Context context) {
        int[][] nArrayArray = new int[3][];
        int[] nArray = new int[3];
        ColorStateList colorStateList = ThemeUtils.getThemeAttrColorStateList(context, R.attr.colorSwitchThumbNormal);
        if (colorStateList != null && colorStateList.isStateful()) {
            nArrayArray[0] = ThemeUtils.DISABLED_STATE_SET;
            nArray[0] = colorStateList.getColorForState(nArrayArray[0], 0);
            int n2 = 0 + 1;
            nArrayArray[n2] = ThemeUtils.CHECKED_STATE_SET;
            nArray[n2] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
            nArrayArray[++n2] = ThemeUtils.EMPTY_STATE_SET;
            nArray[n2] = colorStateList.getDefaultColor();
            return new ColorStateList((int[][])nArrayArray, nArray);
        }
        nArrayArray[0] = ThemeUtils.DISABLED_STATE_SET;
        nArray[0] = ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorSwitchThumbNormal);
        int n3 = 0 + 1;
        nArrayArray[n3] = ThemeUtils.CHECKED_STATE_SET;
        nArray[n3] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
        nArrayArray[++n3] = ThemeUtils.EMPTY_STATE_SET;
        nArray[n3] = ThemeUtils.getThemeAttrColor(context, R.attr.colorSwitchThumbNormal);
        return new ColorStateList((int[][])nArrayArray, nArray);
    }

    private ColorStateList createSwitchTrackColorStateList(Context context) {
        int[][] nArrayArray = new int[3][];
        int[] nArray = new int[3];
        nArrayArray[0] = ThemeUtils.DISABLED_STATE_SET;
        nArray[0] = ThemeUtils.getThemeAttrColor(context, 0x1010030, 0.1f);
        int n2 = 0 + 1;
        nArrayArray[n2] = ThemeUtils.CHECKED_STATE_SET;
        nArray[n2] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated, 0.3f);
        nArrayArray[++n2] = ThemeUtils.EMPTY_STATE_SET;
        nArray[n2] = ThemeUtils.getThemeAttrColor(context, 0x1010030, 0.3f);
        return new ColorStateList((int[][])nArrayArray, nArray);
    }

    private static PorterDuffColorFilter createTintFilter(ColorStateList colorStateList, PorterDuff.Mode mode, int[] nArray) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return AppCompatDrawableManager.getPorterDuffColorFilter(colorStateList.getColorForState(nArray, 0), mode);
    }

    public static AppCompatDrawableManager get() {
        if (INSTANCE == null) {
            INSTANCE = new AppCompatDrawableManager();
            AppCompatDrawableManager.installDefaultInflateDelegates(INSTANCE);
        }
        return INSTANCE;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Drawable getCachedDrawable(@NonNull Context context, long l2) {
        Object object = this.mDrawableCacheLock;
        synchronized (object) {
            LongSparseArray<WeakReference<Drawable.ConstantState>> longSparseArray = this.mDrawableCaches.get(context);
            if (longSparseArray == null) {
                return null;
            }
            Drawable.ConstantState constantState = longSparseArray.get(l2);
            if (constantState == null) return null;
            if ((constantState = (Drawable.ConstantState)constantState.get()) != null) {
                return constantState.newDrawable(context.getResources());
            }
            longSparseArray.delete(l2);
            return null;
        }
    }

    public static PorterDuffColorFilter getPorterDuffColorFilter(int n2, PorterDuff.Mode mode) {
        PorterDuffColorFilter porterDuffColorFilter;
        PorterDuffColorFilter porterDuffColorFilter2 = porterDuffColorFilter = COLOR_FILTER_CACHE.get(n2, mode);
        if (porterDuffColorFilter == null) {
            porterDuffColorFilter2 = new PorterDuffColorFilter(n2, mode);
            COLOR_FILTER_CACHE.put(n2, mode, porterDuffColorFilter2);
        }
        return porterDuffColorFilter2;
    }

    private ColorStateList getTintListFromCache(@NonNull Context sparseArray, @DrawableRes int n2) {
        ColorStateList colorStateList;
        ColorStateList colorStateList2 = colorStateList = null;
        if (this.mTintLists != null) {
            sparseArray = this.mTintLists.get(sparseArray);
            colorStateList2 = colorStateList;
            if (sparseArray != null) {
                colorStateList2 = (ColorStateList)sparseArray.get(n2);
            }
        }
        return colorStateList2;
    }

    private static void installDefaultInflateDelegates(@NonNull AppCompatDrawableManager appCompatDrawableManager) {
        int n2 = Build.VERSION.SDK_INT;
        if (n2 < 23) {
            appCompatDrawableManager.addDelegate("vector", new VdcInflateDelegate());
            if (n2 >= 11) {
                appCompatDrawableManager.addDelegate("animated-vector", new AvdcInflateDelegate());
            }
        }
    }

    private static boolean isVectorDrawable(@NonNull Drawable drawable2) {
        return drawable2 instanceof VectorDrawableCompat || PLATFORM_VD_CLAZZ.equals(drawable2.getClass().getName());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Drawable loadDrawableFromDelegates(@NonNull Context context, @DrawableRes int n2) {
        String string2;
        String string3;
        block13: {
            String string4;
            if (this.mDelegates == null) return null;
            if (this.mDelegates.isEmpty()) return null;
            if (this.mKnownDrawableIdTags != null) {
                string4 = (String)this.mKnownDrawableIdTags.get(n2);
                if (SKIP_DRAWABLE_TAG.equals(string4)) return null;
                if (string4 != null && this.mDelegates.get(string4) == null) {
                    return null;
                }
            } else {
                this.mKnownDrawableIdTags = new SparseArray();
            }
            if (this.mTypedValue == null) {
                this.mTypedValue = new TypedValue();
            }
            TypedValue typedValue = this.mTypedValue;
            Resources resources = context.getResources();
            resources.getValue(n2, typedValue, true);
            long l2 = AppCompatDrawableManager.createCacheKey(typedValue);
            string3 = string4 = this.getCachedDrawable(context, l2);
            if (string4 != null) return string3;
            string2 = string4;
            if (typedValue.string != null) {
                string2 = string4;
                if (typedValue.string.toString().endsWith(".xml")) {
                    string2 = string4;
                    try {
                        int n3;
                        resources = resources.getXml(n2);
                        string2 = string4;
                        AttributeSet attributeSet = Xml.asAttributeSet((XmlPullParser)resources);
                        do {
                            string2 = string4;
                        } while ((n3 = resources.next()) != 2 && n3 != 1);
                        if (n3 != 2) {
                            string2 = string4;
                            throw new XmlPullParserException("No start tag found");
                        }
                        string2 = string4;
                        string3 = resources.getName();
                        string2 = string4;
                        this.mKnownDrawableIdTags.append(n2, (Object)string3);
                        string2 = string4;
                        InflateDelegate inflateDelegate = (InflateDelegate)this.mDelegates.get(string3);
                        string3 = string4;
                        if (inflateDelegate != null) {
                            string2 = string4;
                            string3 = inflateDelegate.createFromXmlInner(context, (XmlPullParser)resources, attributeSet, context.getTheme());
                        }
                        string2 = string3;
                        if (string3 == null) break block13;
                        string2 = string3;
                        string3.setChangingConfigurations(typedValue.changingConfigurations);
                        string2 = string3;
                        boolean bl2 = this.addDrawableToCache(context, l2, (Drawable)string3);
                        string2 = string3;
                        if (bl2) {
                            string2 = string3;
                        }
                    }
                    catch (Exception exception) {
                        Log.e((String)TAG, (String)"Exception while inflating drawable", (Throwable)exception);
                    }
                }
            }
        }
        string3 = string2;
        if (string2 != null) return string3;
        this.mKnownDrawableIdTags.append(n2, (Object)SKIP_DRAWABLE_TAG);
        return string2;
    }

    private void removeDelegate(@NonNull String string2, @NonNull InflateDelegate inflateDelegate) {
        if (this.mDelegates != null && this.mDelegates.get(string2) == inflateDelegate) {
            this.mDelegates.remove(string2);
        }
    }

    private static void setPorterDuffColorFilter(Drawable drawable2, int n2, PorterDuff.Mode mode) {
        Drawable drawable3 = drawable2;
        if (DrawableUtils.canSafelyMutateDrawable(drawable2)) {
            drawable3 = drawable2.mutate();
        }
        drawable2 = mode;
        if (mode == null) {
            drawable2 = DEFAULT_MODE;
        }
        drawable3.setColorFilter((ColorFilter)AppCompatDrawableManager.getPorterDuffColorFilter(n2, (PorterDuff.Mode)drawable2));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Drawable tintDrawable(@NonNull Context context, @DrawableRes int n2, boolean bl2, @NonNull Drawable drawable2) {
        ColorStateList colorStateList = this.getTintList(context, n2);
        if (colorStateList != null) {
            context = drawable2;
            if (DrawableUtils.canSafelyMutateDrawable(drawable2)) {
                context = drawable2.mutate();
            }
            context = DrawableCompat.wrap((Drawable)context);
            DrawableCompat.setTintList((Drawable)context, colorStateList);
            drawable2 = this.getTintMode(n2);
            colorStateList = context;
            if (drawable2 == null) return colorStateList;
            DrawableCompat.setTintMode((Drawable)context, (PorterDuff.Mode)drawable2);
            return context;
        }
        if (n2 == R.drawable.abc_seekbar_track_material) {
            colorStateList = (LayerDrawable)drawable2;
            AppCompatDrawableManager.setPorterDuffColorFilter(colorStateList.findDrawableByLayerId(0x1020000), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlNormal), DEFAULT_MODE);
            AppCompatDrawableManager.setPorterDuffColorFilter(colorStateList.findDrawableByLayerId(16908303), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlNormal), DEFAULT_MODE);
            AppCompatDrawableManager.setPorterDuffColorFilter(colorStateList.findDrawableByLayerId(16908301), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated), DEFAULT_MODE);
            return drawable2;
        }
        if (n2 == R.drawable.abc_ratingbar_indicator_material || n2 == R.drawable.abc_ratingbar_small_material) {
            colorStateList = (LayerDrawable)drawable2;
            AppCompatDrawableManager.setPorterDuffColorFilter(colorStateList.findDrawableByLayerId(0x1020000), ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorControlNormal), DEFAULT_MODE);
            AppCompatDrawableManager.setPorterDuffColorFilter(colorStateList.findDrawableByLayerId(16908303), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated), DEFAULT_MODE);
            AppCompatDrawableManager.setPorterDuffColorFilter(colorStateList.findDrawableByLayerId(16908301), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated), DEFAULT_MODE);
            return drawable2;
        }
        colorStateList = drawable2;
        if (AppCompatDrawableManager.tintDrawableUsingColorFilter(context, n2, drawable2)) return colorStateList;
        colorStateList = drawable2;
        if (!bl2) return colorStateList;
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void tintDrawable(Drawable drawable2, TintInfo tintInfo, int[] nArray) {
        if (DrawableUtils.canSafelyMutateDrawable(drawable2) && drawable2.mutate() != drawable2) {
            Log.d((String)TAG, (String)"Mutated drawable is not the same instance as the input.");
            return;
        } else {
            if (tintInfo.mHasTintList || tintInfo.mHasTintMode) {
                ColorStateList colorStateList = tintInfo.mHasTintList ? tintInfo.mTintList : null;
                tintInfo = tintInfo.mHasTintMode ? tintInfo.mTintMode : DEFAULT_MODE;
                drawable2.setColorFilter((ColorFilter)AppCompatDrawableManager.createTintFilter(colorStateList, (PorterDuff.Mode)tintInfo, nArray));
            } else {
                drawable2.clearColorFilter();
            }
            if (Build.VERSION.SDK_INT > 23) return;
            drawable2.invalidateSelf();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    static boolean tintDrawableUsingColorFilter(@NonNull Context context, @DrawableRes int n2, @NonNull Drawable drawable2) {
        PorterDuff.Mode mode;
        PorterDuff.Mode mode2 = DEFAULT_MODE;
        boolean bl2 = false;
        int n3 = 0;
        int n4 = -1;
        if (AppCompatDrawableManager.arrayContains(COLORFILTER_TINT_COLOR_CONTROL_NORMAL, n2)) {
            n3 = R.attr.colorControlNormal;
            bl2 = true;
            mode = mode2;
        } else if (AppCompatDrawableManager.arrayContains(COLORFILTER_COLOR_CONTROL_ACTIVATED, n2)) {
            n3 = R.attr.colorControlActivated;
            bl2 = true;
            mode = mode2;
        } else if (AppCompatDrawableManager.arrayContains(COLORFILTER_COLOR_BACKGROUND_MULTIPLY, n2)) {
            n3 = 0x1010031;
            bl2 = true;
            mode = PorterDuff.Mode.MULTIPLY;
        } else {
            mode = mode2;
            if (n2 == R.drawable.abc_list_divider_mtrl_alpha) {
                n3 = 0x1010030;
                bl2 = true;
                n4 = Math.round(40.8f);
                mode = mode2;
            }
        }
        if (!bl2) {
            return false;
        }
        mode2 = drawable2;
        if (DrawableUtils.canSafelyMutateDrawable(drawable2)) {
            mode2 = drawable2.mutate();
        }
        mode2.setColorFilter((ColorFilter)AppCompatDrawableManager.getPorterDuffColorFilter(ThemeUtils.getThemeAttrColor(context, n3), mode));
        if (n4 != -1) {
            mode2.setAlpha(n4);
        }
        return true;
    }

    public Drawable getDrawable(@NonNull Context context, @DrawableRes int n2) {
        return this.getDrawable(context, n2, false);
    }

    public Drawable getDrawable(@NonNull Context context, @DrawableRes int n2, boolean bl2) {
        Drawable drawable2;
        Drawable drawable3 = drawable2 = this.loadDrawableFromDelegates(context, n2);
        if (drawable2 == null) {
            drawable3 = this.createDrawableIfNeeded(context, n2);
        }
        drawable2 = drawable3;
        if (drawable3 == null) {
            drawable2 = ContextCompat.getDrawable(context, n2);
        }
        drawable3 = drawable2;
        if (drawable2 != null) {
            drawable3 = this.tintDrawable(context, n2, bl2, drawable2);
        }
        if (drawable3 != null) {
            DrawableUtils.fixDrawable(drawable3);
        }
        return drawable3;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final ColorStateList getTintList(@NonNull Context context, @DrawableRes int n2) {
        ColorStateList colorStateList;
        ColorStateList colorStateList2 = colorStateList = this.getTintListFromCache(context, n2);
        if (colorStateList != null) return colorStateList2;
        if (n2 == R.drawable.abc_edit_text_material) {
            colorStateList = this.createEditTextColorStateList(context);
        } else if (n2 == R.drawable.abc_switch_track_mtrl_alpha) {
            colorStateList = this.createSwitchTrackColorStateList(context);
        } else if (n2 == R.drawable.abc_switch_thumb_material) {
            colorStateList = this.createSwitchThumbColorStateList(context);
        } else if (n2 == R.drawable.abc_btn_default_mtrl_shape) {
            colorStateList = this.createDefaultButtonColorStateList(context);
        } else if (n2 == R.drawable.abc_btn_borderless_material) {
            colorStateList = this.createBorderlessButtonColorStateList(context);
        } else if (n2 == R.drawable.abc_btn_colored_material) {
            colorStateList = this.createColoredButtonColorStateList(context);
        } else if (n2 == R.drawable.abc_spinner_mtrl_am_alpha || n2 == R.drawable.abc_spinner_textfield_background_material) {
            colorStateList = this.createSpinnerColorStateList(context);
        } else if (AppCompatDrawableManager.arrayContains(TINT_COLOR_CONTROL_NORMAL, n2)) {
            colorStateList = ThemeUtils.getThemeAttrColorStateList(context, R.attr.colorControlNormal);
        } else if (AppCompatDrawableManager.arrayContains(TINT_COLOR_CONTROL_STATE_LIST, n2)) {
            colorStateList = this.createDefaultColorStateList(context);
        } else if (AppCompatDrawableManager.arrayContains(TINT_CHECKABLE_BUTTON_LIST, n2)) {
            colorStateList = this.createCheckableButtonColorStateList(context);
        } else if (n2 == R.drawable.abc_seekbar_thumb_material) {
            colorStateList = this.createSeekbarThumbColorStateList(context);
        }
        colorStateList2 = colorStateList;
        if (colorStateList == null) return colorStateList2;
        this.addTintListToCache(context, n2, colorStateList);
        return colorStateList;
    }

    final PorterDuff.Mode getTintMode(int n2) {
        PorterDuff.Mode mode = null;
        if (n2 == R.drawable.abc_switch_thumb_material) {
            mode = PorterDuff.Mode.MULTIPLY;
        }
        return mode;
    }

    private static class AvdcInflateDelegate
    implements InflateDelegate {
        private AvdcInflateDelegate() {
        }

        @Override
        public Drawable createFromXmlInner(@NonNull Context object, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) {
            try {
                object = AnimatedVectorDrawableCompat.createFromXmlInner(object, object.getResources(), xmlPullParser, attributeSet, theme);
                return object;
            }
            catch (Exception exception) {
                Log.e((String)"AvdcInflateDelegate", (String)"Exception while inflating <animated-vector>", (Throwable)exception);
                return null;
            }
        }
    }

    private static class ColorFilterLruCache
    extends LruCache<Integer, PorterDuffColorFilter> {
        public ColorFilterLruCache(int n2) {
            super(n2);
        }

        private static int generateCacheKey(int n2, PorterDuff.Mode mode) {
            return (n2 + 31) * 31 + mode.hashCode();
        }

        PorterDuffColorFilter get(int n2, PorterDuff.Mode mode) {
            return (PorterDuffColorFilter)this.get(ColorFilterLruCache.generateCacheKey(n2, mode));
        }

        PorterDuffColorFilter put(int n2, PorterDuff.Mode mode, PorterDuffColorFilter porterDuffColorFilter) {
            return this.put(ColorFilterLruCache.generateCacheKey(n2, mode), porterDuffColorFilter);
        }
    }

    private static interface InflateDelegate {
        public Drawable createFromXmlInner(@NonNull Context var1, @NonNull XmlPullParser var2, @NonNull AttributeSet var3, @Nullable Resources.Theme var4);
    }

    private static class VdcInflateDelegate
    implements InflateDelegate {
        private VdcInflateDelegate() {
        }

        @Override
        public Drawable createFromXmlInner(@NonNull Context object, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) {
            try {
                object = VectorDrawableCompat.createFromXmlInner(object.getResources(), xmlPullParser, attributeSet, theme);
                return object;
            }
            catch (Exception exception) {
                Log.e((String)"VdcInflateDelegate", (String)"Exception while inflating <vector>", (Throwable)exception);
                return null;
            }
        }
    }
}

