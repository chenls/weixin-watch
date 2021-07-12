/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources$Theme
 *  android.database.DataSetObserver
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ArrayAdapter
 *  android.widget.ListAdapter
 *  android.widget.PopupWindow$OnDismissListener
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 *  android.widget.ThemedSpinnerAdapter
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.TintableBackgroundView;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatBackgroundHelper;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.ViewUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class AppCompatSpinner
extends Spinner
implements TintableBackgroundView {
    private static final int[] ATTRS_ANDROID_SPINNERMODE;
    private static final boolean IS_AT_LEAST_JB;
    private static final boolean IS_AT_LEAST_M;
    private static final int MAX_ITEMS_MEASURED = 15;
    private static final int MODE_DIALOG = 0;
    private static final int MODE_DROPDOWN = 1;
    private static final int MODE_THEME = -1;
    private static final String TAG = "AppCompatSpinner";
    private AppCompatBackgroundHelper mBackgroundTintHelper;
    private AppCompatDrawableManager mDrawableManager;
    private int mDropDownWidth;
    private ListPopupWindow.ForwardingListener mForwardingListener;
    private DropdownPopup mPopup;
    private Context mPopupContext;
    private boolean mPopupSet;
    private SpinnerAdapter mTempAdapter;
    private final Rect mTempRect;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl2 = Build.VERSION.SDK_INT >= 23;
        IS_AT_LEAST_M = bl2;
        bl2 = Build.VERSION.SDK_INT >= 16;
        IS_AT_LEAST_JB = bl2;
        ATTRS_ANDROID_SPINNERMODE = new int[]{16843505};
    }

    public AppCompatSpinner(Context context) {
        this(context, null);
    }

    public AppCompatSpinner(Context context, int n2) {
        this(context, null, R.attr.spinnerStyle, n2);
    }

    public AppCompatSpinner(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.spinnerStyle);
    }

    public AppCompatSpinner(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, -1);
    }

    public AppCompatSpinner(Context context, AttributeSet attributeSet, int n2, int n3) {
        this(context, attributeSet, n2, n3, null);
    }

    /*
     * Unable to fully structure code
     */
    public AppCompatSpinner(Context var1_1, AttributeSet var2_3, int var3_4, int var4_5, Resources.Theme var5_6) {
        block16: {
            block19: {
                block18: {
                    super(var1_1, var2_3, var3_4);
                    this.mTempRect = new Rect();
                    var10_7 = TintTypedArray.obtainStyledAttributes(var1_1, var2_3, R.styleable.Spinner, var3_4, 0);
                    this.mDrawableManager = AppCompatDrawableManager.get();
                    this.mBackgroundTintHelper = new AppCompatBackgroundHelper((View)this, this.mDrawableManager);
                    if (var5_6 /* !! */  == null) break block18;
                    this.mPopupContext = new ContextThemeWrapper(var1_1, (Resources.Theme)var5_6 /* !! */ );
lbl8:
                    // 3 sources

                    while (true) {
                        if (this.mPopupContext == null) ** GOTO lbl41
                        var7_9 = var4_5;
                        if (var4_5 == -1) {
                            block17: {
                                if (Build.VERSION.SDK_INT < 11) break block16;
                                var8_10 = null;
                                var5_6 /* !! */  = null;
                                var9_11 = var1_1.obtainStyledAttributes(var2_3, AppCompatSpinner.ATTRS_ANDROID_SPINNERMODE, var3_4, 0);
                                var6_8 = var4_5;
                                var5_6 /* !! */  = var9_11;
                                var8_10 = var9_11;
                                if (!var9_11.hasValue(0)) break block17;
                                var5_6 /* !! */  = var9_11;
                                var8_10 = var9_11;
                                var6_8 = var9_11.getInt(0, 0);
                            }
                            var7_9 = var6_8;
                            if (var9_11 != null) {
                                var9_11.recycle();
                                var7_9 = var6_8;
                            }
                        }
lbl31:
                        // 8 sources

                        while (true) {
                            if (var7_9 == 1) {
                                var5_6 /* !! */  = new DropdownPopup(this.mPopupContext, var2_3, var3_4);
                                var8_10 = TintTypedArray.obtainStyledAttributes(this.mPopupContext, var2_3, R.styleable.Spinner, var3_4, 0);
                                this.mDropDownWidth = var8_10.getLayoutDimension(R.styleable.Spinner_android_dropDownWidth, -2);
                                var5_6 /* !! */ .setBackgroundDrawable(var8_10.getDrawable(R.styleable.Spinner_android_popupBackground));
                                var5_6 /* !! */ .setPromptText(var10_7.getString(R.styleable.Spinner_android_prompt));
                                var8_10.recycle();
                                this.mPopup = var5_6 /* !! */ ;
                                this.mForwardingListener = new ListPopupWindow.ForwardingListener((View)this, (DropdownPopup)var5_6 /* !! */ ){
                                    final /* synthetic */ DropdownPopup val$popup;
                                    {
                                        this.val$popup = dropdownPopup;
                                        super(view);
                                    }

                                    @Override
                                    public ListPopupWindow getPopup() {
                                        return this.val$popup;
                                    }

                                    @Override
                                    public boolean onForwardingStarted() {
                                        if (!AppCompatSpinner.this.mPopup.isShowing()) {
                                            AppCompatSpinner.this.mPopup.show();
                                        }
                                        return true;
                                    }
                                };
                            }
lbl41:
                            // 4 sources

                            if ((var5_6 /* !! */  = var10_7.getTextArray(R.styleable.Spinner_android_entries)) != null) {
                                var1_1 = new ArrayAdapter(var1_1, R.layout.support_simple_spinner_dropdown_item, (Object[])var5_6 /* !! */ );
                                var1_1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                                this.setAdapter((SpinnerAdapter)var1_1);
                            }
                            var10_7.recycle();
                            this.mPopupSet = true;
                            if (this.mTempAdapter != null) {
                                this.setAdapter(this.mTempAdapter);
                                this.mTempAdapter = null;
                            }
                            this.mBackgroundTintHelper.loadFromAttributes(var2_3, var3_4);
                            return;
                        }
                        break;
                    }
                }
                var6_8 = var10_7.getResourceId(R.styleable.Spinner_popupTheme, 0);
                if (var6_8 == 0) break block19;
                this.mPopupContext = new ContextThemeWrapper(var1_1, var6_8);
                ** GOTO lbl8
            }
            if (!AppCompatSpinner.IS_AT_LEAST_M) {
                var5_6 /* !! */  = var1_1;
lbl60:
                // 2 sources

                while (true) {
                    this.mPopupContext = var5_6 /* !! */ ;
                    ** continue;
                    break;
                }
            }
            var5_6 /* !! */  = null;
            ** while (true)
            catch (Exception var9_12) {
                var8_10 = var5_6 /* !! */ ;
                try {
                    Log.i((String)"AppCompatSpinner", (String)"Could not read android:spinnerMode", (Throwable)var9_12);
                    var7_9 = var4_5;
                    if (var5_6 /* !! */  == null) ** GOTO lbl31
                }
                catch (Throwable var1_2) {
                    if (var8_10 != null) {
                        var8_10.recycle();
                    }
                    throw var1_2;
                }
                var5_6 /* !! */ .recycle();
                var7_9 = var4_5;
                ** GOTO lbl31
            }
        }
        var7_9 = 1;
        ** while (true)
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int compatMeasureContentWidth(SpinnerAdapter spinnerAdapter, Drawable drawable2) {
        if (spinnerAdapter == null) {
            return 0;
        }
        int n2 = 0;
        View view = null;
        int n3 = 0;
        int n4 = View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredWidth(), (int)0);
        int n5 = View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredHeight(), (int)0);
        int n6 = Math.max(0, this.getSelectedItemPosition());
        int n7 = Math.min(spinnerAdapter.getCount(), n6 + 15);
        for (n6 = Math.max(0, n6 - (15 - (n7 - n6))); n6 < n7; ++n6) {
            int n8 = spinnerAdapter.getItemViewType(n6);
            int n9 = n3;
            if (n8 != n3) {
                n9 = n8;
                view = null;
            }
            if ((view = spinnerAdapter.getView(n6, view, (ViewGroup)this)).getLayoutParams() == null) {
                view.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            }
            view.measure(n4, n5);
            n2 = Math.max(n2, view.getMeasuredWidth());
            n3 = n9;
        }
        n6 = n2;
        if (drawable2 == null) return n6;
        drawable2.getPadding(this.mTempRect);
        return n2 + (this.mTempRect.left + this.mTempRect.right);
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.applySupportBackgroundTint();
        }
    }

    public int getDropDownHorizontalOffset() {
        if (this.mPopup != null) {
            return this.mPopup.getHorizontalOffset();
        }
        if (IS_AT_LEAST_JB) {
            return super.getDropDownHorizontalOffset();
        }
        return 0;
    }

    public int getDropDownVerticalOffset() {
        if (this.mPopup != null) {
            return this.mPopup.getVerticalOffset();
        }
        if (IS_AT_LEAST_JB) {
            return super.getDropDownVerticalOffset();
        }
        return 0;
    }

    public int getDropDownWidth() {
        if (this.mPopup != null) {
            return this.mDropDownWidth;
        }
        if (IS_AT_LEAST_JB) {
            return super.getDropDownWidth();
        }
        return 0;
    }

    public Drawable getPopupBackground() {
        if (this.mPopup != null) {
            return this.mPopup.getBackground();
        }
        if (IS_AT_LEAST_JB) {
            return super.getPopupBackground();
        }
        return null;
    }

    public Context getPopupContext() {
        if (this.mPopup != null) {
            return this.mPopupContext;
        }
        if (IS_AT_LEAST_M) {
            return super.getPopupContext();
        }
        return null;
    }

    public CharSequence getPrompt() {
        if (this.mPopup != null) {
            return this.mPopup.getHintText();
        }
        return super.getPrompt();
    }

    @Override
    @Nullable
    public ColorStateList getSupportBackgroundTintList() {
        if (this.mBackgroundTintHelper != null) {
            return this.mBackgroundTintHelper.getSupportBackgroundTintList();
        }
        return null;
    }

    @Override
    @Nullable
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        if (this.mBackgroundTintHelper != null) {
            return this.mBackgroundTintHelper.getSupportBackgroundTintMode();
        }
        return null;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mPopup != null && this.mPopup.isShowing()) {
            this.mPopup.dismiss();
        }
    }

    protected void onMeasure(int n2, int n3) {
        super.onMeasure(n2, n3);
        if (this.mPopup != null && View.MeasureSpec.getMode((int)n2) == Integer.MIN_VALUE) {
            this.setMeasuredDimension(Math.min(Math.max(this.getMeasuredWidth(), this.compatMeasureContentWidth(this.getAdapter(), this.getBackground())), View.MeasureSpec.getSize((int)n2)), this.getMeasuredHeight());
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mForwardingListener != null && this.mForwardingListener.onTouch((View)this, motionEvent)) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    public boolean performClick() {
        if (this.mPopup != null) {
            if (!this.mPopup.isShowing()) {
                this.mPopup.show();
            }
            return true;
        }
        return super.performClick();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setAdapter(SpinnerAdapter spinnerAdapter) {
        Context context;
        if (!this.mPopupSet) {
            this.mTempAdapter = spinnerAdapter;
            return;
        } else {
            super.setAdapter(spinnerAdapter);
            if (this.mPopup == null) return;
            context = this.mPopupContext == null ? this.getContext() : this.mPopupContext;
        }
        this.mPopup.setAdapter(new DropDownAdapter(spinnerAdapter, context.getTheme()));
    }

    public void setBackgroundDrawable(Drawable drawable2) {
        super.setBackgroundDrawable(drawable2);
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundDrawable(drawable2);
        }
    }

    public void setBackgroundResource(@DrawableRes int n2) {
        super.setBackgroundResource(n2);
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundResource(n2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setDropDownHorizontalOffset(int n2) {
        if (this.mPopup != null) {
            this.mPopup.setHorizontalOffset(n2);
            return;
        } else {
            if (!IS_AT_LEAST_JB) return;
            super.setDropDownHorizontalOffset(n2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setDropDownVerticalOffset(int n2) {
        if (this.mPopup != null) {
            this.mPopup.setVerticalOffset(n2);
            return;
        } else {
            if (!IS_AT_LEAST_JB) return;
            super.setDropDownVerticalOffset(n2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setDropDownWidth(int n2) {
        if (this.mPopup != null) {
            this.mDropDownWidth = n2;
            return;
        } else {
            if (!IS_AT_LEAST_JB) return;
            super.setDropDownWidth(n2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setPopupBackgroundDrawable(Drawable drawable2) {
        if (this.mPopup != null) {
            this.mPopup.setBackgroundDrawable(drawable2);
            return;
        } else {
            if (!IS_AT_LEAST_JB) return;
            super.setPopupBackgroundDrawable(drawable2);
            return;
        }
    }

    public void setPopupBackgroundResource(@DrawableRes int n2) {
        this.setPopupBackgroundDrawable(ContextCompat.getDrawable(this.getPopupContext(), n2));
    }

    public void setPrompt(CharSequence charSequence) {
        if (this.mPopup != null) {
            this.mPopup.setPromptText(charSequence);
            return;
        }
        super.setPrompt(charSequence);
    }

    @Override
    public void setSupportBackgroundTintList(@Nullable ColorStateList colorStateList) {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.setSupportBackgroundTintList(colorStateList);
        }
    }

    @Override
    public void setSupportBackgroundTintMode(@Nullable PorterDuff.Mode mode) {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.setSupportBackgroundTintMode(mode);
        }
    }

    private static class DropDownAdapter
    implements ListAdapter,
    SpinnerAdapter {
        private SpinnerAdapter mAdapter;
        private ListAdapter mListAdapter;

        /*
         * Enabled aggressive block sorting
         */
        public DropDownAdapter(@Nullable SpinnerAdapter spinnerAdapter, @Nullable Resources.Theme theme) {
            this.mAdapter = spinnerAdapter;
            if (spinnerAdapter instanceof ListAdapter) {
                this.mListAdapter = (ListAdapter)spinnerAdapter;
            }
            if (theme == null) return;
            if (IS_AT_LEAST_M && spinnerAdapter instanceof android.widget.ThemedSpinnerAdapter) {
                if ((spinnerAdapter = (android.widget.ThemedSpinnerAdapter)spinnerAdapter).getDropDownViewTheme() == theme) return;
                spinnerAdapter.setDropDownViewTheme(theme);
                return;
            }
            if (!(spinnerAdapter instanceof ThemedSpinnerAdapter) || (spinnerAdapter = (ThemedSpinnerAdapter)spinnerAdapter).getDropDownViewTheme() != null) return;
            spinnerAdapter.setDropDownViewTheme(theme);
        }

        public boolean areAllItemsEnabled() {
            ListAdapter listAdapter = this.mListAdapter;
            if (listAdapter != null) {
                return listAdapter.areAllItemsEnabled();
            }
            return true;
        }

        public int getCount() {
            if (this.mAdapter == null) {
                return 0;
            }
            return this.mAdapter.getCount();
        }

        public View getDropDownView(int n2, View view, ViewGroup viewGroup) {
            if (this.mAdapter == null) {
                return null;
            }
            return this.mAdapter.getDropDownView(n2, view, viewGroup);
        }

        public Object getItem(int n2) {
            if (this.mAdapter == null) {
                return null;
            }
            return this.mAdapter.getItem(n2);
        }

        public long getItemId(int n2) {
            if (this.mAdapter == null) {
                return -1L;
            }
            return this.mAdapter.getItemId(n2);
        }

        public int getItemViewType(int n2) {
            return 0;
        }

        public View getView(int n2, View view, ViewGroup viewGroup) {
            return this.getDropDownView(n2, view, viewGroup);
        }

        public int getViewTypeCount() {
            return 1;
        }

        public boolean hasStableIds() {
            return this.mAdapter != null && this.mAdapter.hasStableIds();
        }

        public boolean isEmpty() {
            return this.getCount() == 0;
        }

        public boolean isEnabled(int n2) {
            ListAdapter listAdapter = this.mListAdapter;
            if (listAdapter != null) {
                return listAdapter.isEnabled(n2);
            }
            return true;
        }

        public void registerDataSetObserver(DataSetObserver dataSetObserver) {
            if (this.mAdapter != null) {
                this.mAdapter.registerDataSetObserver(dataSetObserver);
            }
        }

        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            if (this.mAdapter != null) {
                this.mAdapter.unregisterDataSetObserver(dataSetObserver);
            }
        }
    }

    private class DropdownPopup
    extends ListPopupWindow {
        private ListAdapter mAdapter;
        private CharSequence mHintText;
        private final Rect mVisibleRect;

        public DropdownPopup(Context context, AttributeSet attributeSet, int n2) {
            super(context, attributeSet, n2);
            this.mVisibleRect = new Rect();
            this.setAnchorView((View)AppCompatSpinner.this);
            this.setModal(true);
            this.setPromptPosition(0);
            this.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                public void onItemClick(AdapterView<?> adapterView, View view, int n2, long l2) {
                    AppCompatSpinner.this.setSelection(n2);
                    if (AppCompatSpinner.this.getOnItemClickListener() != null) {
                        AppCompatSpinner.this.performItemClick(view, n2, DropdownPopup.this.mAdapter.getItemId(n2));
                    }
                    DropdownPopup.this.dismiss();
                }
            });
        }

        private boolean isVisibleToUser(View view) {
            return ViewCompat.isAttachedToWindow(view) && view.getGlobalVisibleRect(this.mVisibleRect);
        }

        /*
         * Enabled aggressive block sorting
         */
        void computeContentWidth() {
            Drawable drawable2 = this.getBackground();
            int n2 = 0;
            if (drawable2 != null) {
                drawable2.getPadding(AppCompatSpinner.this.mTempRect);
                n2 = ViewUtils.isLayoutRtl((View)AppCompatSpinner.this) ? ((AppCompatSpinner)AppCompatSpinner.this).mTempRect.right : -((AppCompatSpinner)AppCompatSpinner.this).mTempRect.left;
            } else {
                drawable2 = AppCompatSpinner.this.mTempRect;
                ((AppCompatSpinner)AppCompatSpinner.this).mTempRect.right = 0;
                drawable2.left = 0;
            }
            int n3 = AppCompatSpinner.this.getPaddingLeft();
            int n4 = AppCompatSpinner.this.getPaddingRight();
            int n5 = AppCompatSpinner.this.getWidth();
            if (AppCompatSpinner.this.mDropDownWidth == -2) {
                int n6 = AppCompatSpinner.this.compatMeasureContentWidth((SpinnerAdapter)this.mAdapter, this.getBackground());
                int n7 = AppCompatSpinner.this.getContext().getResources().getDisplayMetrics().widthPixels - ((AppCompatSpinner)AppCompatSpinner.this).mTempRect.left - ((AppCompatSpinner)AppCompatSpinner.this).mTempRect.right;
                int n8 = n6;
                if (n6 > n7) {
                    n8 = n7;
                }
                this.setContentWidth(Math.max(n8, n5 - n3 - n4));
            } else if (AppCompatSpinner.this.mDropDownWidth == -1) {
                this.setContentWidth(n5 - n3 - n4);
            } else {
                this.setContentWidth(AppCompatSpinner.this.mDropDownWidth);
            }
            n2 = ViewUtils.isLayoutRtl((View)AppCompatSpinner.this) ? (n2 += n5 - n4 - this.getWidth()) : (n2 += n3);
            this.setHorizontalOffset(n2);
        }

        public CharSequence getHintText() {
            return this.mHintText;
        }

        @Override
        public void setAdapter(ListAdapter listAdapter) {
            super.setAdapter(listAdapter);
            this.mAdapter = listAdapter;
        }

        public void setPromptText(CharSequence charSequence) {
            this.mHintText = charSequence;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void show() {
            ViewTreeObserver viewTreeObserver;
            boolean bl2 = this.isShowing();
            this.computeContentWidth();
            this.setInputMethodMode(2);
            super.show();
            this.getListView().setChoiceMode(1);
            this.setSelection(AppCompatSpinner.this.getSelectedItemPosition());
            if (bl2 || (viewTreeObserver = AppCompatSpinner.this.getViewTreeObserver()) == null) {
                return;
            }
            final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener(){

                public void onGlobalLayout() {
                    if (!DropdownPopup.this.isVisibleToUser((View)AppCompatSpinner.this)) {
                        DropdownPopup.this.dismiss();
                        return;
                    }
                    DropdownPopup.this.computeContentWidth();
                    DropdownPopup.super.show();
                }
            };
            viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener);
            this.setOnDismissListener(new PopupWindow.OnDismissListener(){

                public void onDismiss() {
                    ViewTreeObserver viewTreeObserver = AppCompatSpinner.this.getViewTreeObserver();
                    if (viewTreeObserver != null) {
                        viewTreeObserver.removeGlobalOnLayoutListener(onGlobalLayoutListener);
                    }
                }
            });
        }
    }
}

