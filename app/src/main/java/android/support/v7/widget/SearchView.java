/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.PendingIntent
 *  android.app.SearchableInfo
 *  android.content.ActivityNotFoundException
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.database.Cursor
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.net.Uri
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.ResultReceiver
 *  android.text.Editable
 *  android.text.SpannableStringBuilder
 *  android.text.TextUtils
 *  android.text.TextWatcher
 *  android.text.style.ImageSpan
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.KeyEvent
 *  android.view.KeyEvent$DispatcherState
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.View$OnFocusChangeListener
 *  android.view.View$OnKeyListener
 *  android.view.View$OnLayoutChangeListener
 *  android.view.ViewGroup
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.view.inputmethod.InputMethodManager
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.AutoCompleteTextView
 *  android.widget.ImageView
 *  android.widget.ListAdapter
 *  android.widget.TextView
 *  android.widget.TextView$OnEditorActionListener
 */
package android.support.v7.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.SearchableInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.support.v4.view.KeyEventCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.appcompat.R;
import android.support.v7.view.CollapsibleActionView;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.SuggestionsAdapter;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.ViewUtils;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

public class SearchView
extends LinearLayoutCompat
implements CollapsibleActionView {
    private static final boolean DBG = false;
    static final AutoCompleteTextViewReflector HIDDEN_METHOD_INVOKER;
    private static final String IME_OPTION_NO_MICROPHONE = "nm";
    private static final boolean IS_AT_LEAST_FROYO;
    private static final String LOG_TAG = "SearchView";
    private Bundle mAppSearchData;
    private boolean mClearingFocus;
    private final ImageView mCloseButton;
    private final ImageView mCollapsedIcon;
    private int mCollapsedImeOptions;
    private final CharSequence mDefaultQueryHint;
    private final AppCompatDrawableManager mDrawableManager;
    private final View mDropDownAnchor;
    private boolean mExpandedInActionView;
    private final ImageView mGoButton;
    private boolean mIconified;
    private boolean mIconifiedByDefault;
    private int mMaxWidth;
    private CharSequence mOldQueryText;
    private final View.OnClickListener mOnClickListener;
    private OnCloseListener mOnCloseListener;
    private final TextView.OnEditorActionListener mOnEditorActionListener;
    private final AdapterView.OnItemClickListener mOnItemClickListener;
    private final AdapterView.OnItemSelectedListener mOnItemSelectedListener;
    private OnQueryTextListener mOnQueryChangeListener;
    private View.OnFocusChangeListener mOnQueryTextFocusChangeListener;
    private View.OnClickListener mOnSearchClickListener;
    private OnSuggestionListener mOnSuggestionListener;
    private final WeakHashMap<String, Drawable.ConstantState> mOutsideDrawablesCache;
    private CharSequence mQueryHint;
    private boolean mQueryRefinement;
    private Runnable mReleaseCursorRunnable;
    private final ImageView mSearchButton;
    private final View mSearchEditFrame;
    private final Drawable mSearchHintIcon;
    private final View mSearchPlate;
    private final SearchAutoComplete mSearchSrcTextView;
    private SearchableInfo mSearchable;
    private Runnable mShowImeRunnable = new Runnable(){

        @Override
        public void run() {
            InputMethodManager inputMethodManager = (InputMethodManager)SearchView.this.getContext().getSystemService("input_method");
            if (inputMethodManager != null) {
                HIDDEN_METHOD_INVOKER.showSoftInputUnchecked(inputMethodManager, (View)SearchView.this, 0);
            }
        }
    };
    private final View mSubmitArea;
    private boolean mSubmitButtonEnabled;
    private final int mSuggestionCommitIconResId;
    private final int mSuggestionRowLayout;
    private CursorAdapter mSuggestionsAdapter;
    View.OnKeyListener mTextKeyListener;
    private TextWatcher mTextWatcher;
    private final Runnable mUpdateDrawableStateRunnable = new Runnable(){

        @Override
        public void run() {
            SearchView.this.updateFocusedState();
        }
    };
    private CharSequence mUserQuery;
    private final Intent mVoiceAppSearchIntent;
    private final ImageView mVoiceButton;
    private boolean mVoiceButtonEnabled;
    private final Intent mVoiceWebSearchIntent;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl2 = Build.VERSION.SDK_INT >= 8;
        IS_AT_LEAST_FROYO = bl2;
        HIDDEN_METHOD_INVOKER = new AutoCompleteTextViewReflector();
    }

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.searchViewStyle);
    }

    /*
     * Enabled aggressive block sorting
     */
    public SearchView(Context context, AttributeSet object, int n2) {
        super(context, (AttributeSet)object, n2);
        this.mReleaseCursorRunnable = new Runnable(){

            @Override
            public void run() {
                if (SearchView.this.mSuggestionsAdapter != null && SearchView.this.mSuggestionsAdapter instanceof SuggestionsAdapter) {
                    SearchView.this.mSuggestionsAdapter.changeCursor(null);
                }
            }
        };
        this.mOutsideDrawablesCache = new WeakHashMap();
        this.mOnClickListener = new View.OnClickListener(){

            /*
             * Enabled aggressive block sorting
             */
            public void onClick(View view) {
                if (view == SearchView.this.mSearchButton) {
                    SearchView.this.onSearchClicked();
                    return;
                } else {
                    if (view == SearchView.this.mCloseButton) {
                        SearchView.this.onCloseClicked();
                        return;
                    }
                    if (view == SearchView.this.mGoButton) {
                        SearchView.this.onSubmitQuery();
                        return;
                    }
                    if (view == SearchView.this.mVoiceButton) {
                        SearchView.this.onVoiceClicked();
                        return;
                    }
                    if (view != SearchView.this.mSearchSrcTextView) return;
                    SearchView.this.forceSuggestionQuery();
                    return;
                }
            }
        };
        this.mTextKeyListener = new View.OnKeyListener(){

            /*
             * Enabled aggressive block sorting
             */
            public boolean onKey(View view, int n2, KeyEvent keyEvent) {
                block5: {
                    block4: {
                        if (SearchView.this.mSearchable == null) break block4;
                        if (SearchView.this.mSearchSrcTextView.isPopupShowing() && SearchView.this.mSearchSrcTextView.getListSelection() != -1) {
                            return SearchView.this.onSuggestionsKey(view, n2, keyEvent);
                        }
                        if (!SearchView.this.mSearchSrcTextView.isEmpty() && KeyEventCompat.hasNoModifiers(keyEvent) && keyEvent.getAction() == 1 && n2 == 66) break block5;
                    }
                    return false;
                }
                view.cancelLongPress();
                SearchView.this.launchQuerySearch(0, null, SearchView.this.mSearchSrcTextView.getText().toString());
                return true;
            }
        };
        this.mOnEditorActionListener = new TextView.OnEditorActionListener(){

            public boolean onEditorAction(TextView textView, int n2, KeyEvent keyEvent) {
                SearchView.this.onSubmitQuery();
                return true;
            }
        };
        this.mOnItemClickListener = new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> adapterView, View view, int n2, long l2) {
                SearchView.this.onItemClicked(n2, 0, null);
            }
        };
        this.mOnItemSelectedListener = new AdapterView.OnItemSelectedListener(){

            public void onItemSelected(AdapterView<?> adapterView, View view, int n2, long l2) {
                SearchView.this.onItemSelected(n2);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };
        this.mTextWatcher = new TextWatcher(){

            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int n2, int n3, int n4) {
            }

            public void onTextChanged(CharSequence charSequence, int n2, int n3, int n4) {
                SearchView.this.onTextChanged(charSequence);
            }
        };
        this.mDrawableManager = AppCompatDrawableManager.get();
        object = TintTypedArray.obtainStyledAttributes(context, (AttributeSet)object, R.styleable.SearchView, n2, 0);
        LayoutInflater.from((Context)context).inflate(((TintTypedArray)object).getResourceId(R.styleable.SearchView_layout, R.layout.abc_search_view), (ViewGroup)this, true);
        this.mSearchSrcTextView = (SearchAutoComplete)this.findViewById(R.id.search_src_text);
        this.mSearchSrcTextView.setSearchView(this);
        this.mSearchEditFrame = this.findViewById(R.id.search_edit_frame);
        this.mSearchPlate = this.findViewById(R.id.search_plate);
        this.mSubmitArea = this.findViewById(R.id.submit_area);
        this.mSearchButton = (ImageView)this.findViewById(R.id.search_button);
        this.mGoButton = (ImageView)this.findViewById(R.id.search_go_btn);
        this.mCloseButton = (ImageView)this.findViewById(R.id.search_close_btn);
        this.mVoiceButton = (ImageView)this.findViewById(R.id.search_voice_btn);
        this.mCollapsedIcon = (ImageView)this.findViewById(R.id.search_mag_icon);
        this.mSearchPlate.setBackgroundDrawable(((TintTypedArray)object).getDrawable(R.styleable.SearchView_queryBackground));
        this.mSubmitArea.setBackgroundDrawable(((TintTypedArray)object).getDrawable(R.styleable.SearchView_submitBackground));
        this.mSearchButton.setImageDrawable(((TintTypedArray)object).getDrawable(R.styleable.SearchView_searchIcon));
        this.mGoButton.setImageDrawable(((TintTypedArray)object).getDrawable(R.styleable.SearchView_goIcon));
        this.mCloseButton.setImageDrawable(((TintTypedArray)object).getDrawable(R.styleable.SearchView_closeIcon));
        this.mVoiceButton.setImageDrawable(((TintTypedArray)object).getDrawable(R.styleable.SearchView_voiceIcon));
        this.mCollapsedIcon.setImageDrawable(((TintTypedArray)object).getDrawable(R.styleable.SearchView_searchIcon));
        this.mSearchHintIcon = ((TintTypedArray)object).getDrawable(R.styleable.SearchView_searchHintIcon);
        this.mSuggestionRowLayout = ((TintTypedArray)object).getResourceId(R.styleable.SearchView_suggestionRowLayout, R.layout.abc_search_dropdown_item_icons_2line);
        this.mSuggestionCommitIconResId = ((TintTypedArray)object).getResourceId(R.styleable.SearchView_commitIcon, 0);
        this.mSearchButton.setOnClickListener(this.mOnClickListener);
        this.mCloseButton.setOnClickListener(this.mOnClickListener);
        this.mGoButton.setOnClickListener(this.mOnClickListener);
        this.mVoiceButton.setOnClickListener(this.mOnClickListener);
        this.mSearchSrcTextView.setOnClickListener(this.mOnClickListener);
        this.mSearchSrcTextView.addTextChangedListener(this.mTextWatcher);
        this.mSearchSrcTextView.setOnEditorActionListener(this.mOnEditorActionListener);
        this.mSearchSrcTextView.setOnItemClickListener(this.mOnItemClickListener);
        this.mSearchSrcTextView.setOnItemSelectedListener(this.mOnItemSelectedListener);
        this.mSearchSrcTextView.setOnKeyListener(this.mTextKeyListener);
        this.mSearchSrcTextView.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            public void onFocusChange(View view, boolean bl2) {
                if (SearchView.this.mOnQueryTextFocusChangeListener != null) {
                    SearchView.this.mOnQueryTextFocusChangeListener.onFocusChange((View)SearchView.this, bl2);
                }
            }
        });
        this.setIconifiedByDefault(((TintTypedArray)object).getBoolean(R.styleable.SearchView_iconifiedByDefault, true));
        n2 = ((TintTypedArray)object).getDimensionPixelSize(R.styleable.SearchView_android_maxWidth, -1);
        if (n2 != -1) {
            this.setMaxWidth(n2);
        }
        this.mDefaultQueryHint = ((TintTypedArray)object).getText(R.styleable.SearchView_defaultQueryHint);
        this.mQueryHint = ((TintTypedArray)object).getText(R.styleable.SearchView_queryHint);
        n2 = ((TintTypedArray)object).getInt(R.styleable.SearchView_android_imeOptions, -1);
        if (n2 != -1) {
            this.setImeOptions(n2);
        }
        if ((n2 = ((TintTypedArray)object).getInt(R.styleable.SearchView_android_inputType, -1)) != -1) {
            this.setInputType(n2);
        }
        this.setFocusable(((TintTypedArray)object).getBoolean(R.styleable.SearchView_android_focusable, true));
        ((TintTypedArray)object).recycle();
        this.mVoiceWebSearchIntent = new Intent("android.speech.action.WEB_SEARCH");
        this.mVoiceWebSearchIntent.addFlags(0x10000000);
        this.mVoiceWebSearchIntent.putExtra("android.speech.extra.LANGUAGE_MODEL", "web_search");
        this.mVoiceAppSearchIntent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        this.mVoiceAppSearchIntent.addFlags(0x10000000);
        this.mDropDownAnchor = this.findViewById(this.mSearchSrcTextView.getDropDownAnchor());
        if (this.mDropDownAnchor != null) {
            if (Build.VERSION.SDK_INT >= 11) {
                this.addOnLayoutChangeListenerToDropDownAnchorSDK11();
            } else {
                this.addOnLayoutChangeListenerToDropDownAnchorBase();
            }
        }
        this.updateViewsVisibility(this.mIconifiedByDefault);
        this.updateQueryHint();
    }

    private void addOnLayoutChangeListenerToDropDownAnchorBase() {
        this.mDropDownAnchor.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){

            public void onGlobalLayout() {
                SearchView.this.adjustDropDownSizeAndPosition();
            }
        });
    }

    @TargetApi(value=11)
    private void addOnLayoutChangeListenerToDropDownAnchorSDK11() {
        this.mDropDownAnchor.addOnLayoutChangeListener(new View.OnLayoutChangeListener(){

            public void onLayoutChange(View view, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
                SearchView.this.adjustDropDownSizeAndPosition();
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    private void adjustDropDownSizeAndPosition() {
        if (this.mDropDownAnchor.getWidth() > 1) {
            Resources resources = this.getContext().getResources();
            int n2 = this.mSearchPlate.getPaddingLeft();
            Rect rect = new Rect();
            boolean bl2 = ViewUtils.isLayoutRtl((View)this);
            int n3 = this.mIconifiedByDefault ? resources.getDimensionPixelSize(R.dimen.abc_dropdownitem_icon_width) + resources.getDimensionPixelSize(R.dimen.abc_dropdownitem_text_padding_left) : 0;
            this.mSearchSrcTextView.getDropDownBackground().getPadding(rect);
            int n4 = bl2 ? -rect.left : n2 - (rect.left + n3);
            this.mSearchSrcTextView.setDropDownHorizontalOffset(n4);
            n4 = this.mDropDownAnchor.getWidth();
            int n5 = rect.left;
            int n6 = rect.right;
            this.mSearchSrcTextView.setDropDownWidth(n4 + n5 + n6 + n3 - n2);
        }
    }

    private Intent createIntent(String string2, Uri uri, String string3, String string4, int n2, String string5) {
        string2 = new Intent(string2);
        string2.addFlags(0x10000000);
        if (uri != null) {
            string2.setData(uri);
        }
        string2.putExtra("user_query", this.mUserQuery);
        if (string4 != null) {
            string2.putExtra("query", string4);
        }
        if (string3 != null) {
            string2.putExtra("intent_extra_data_key", string3);
        }
        if (this.mAppSearchData != null) {
            string2.putExtra("app_data", this.mAppSearchData);
        }
        if (n2 != 0) {
            string2.putExtra("action_key", n2);
            string2.putExtra("action_msg", string5);
        }
        if (IS_AT_LEAST_FROYO) {
            string2.setComponent(this.mSearchable.getSearchActivity());
        }
        return string2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Intent createIntentFromSuggestion(Cursor cursor, int n2, String string2) {
        String string3;
        String string4;
        String string5;
        block10: {
            try {
                string4 = string5 = SuggestionsAdapter.getColumnString(cursor, "suggest_intent_action");
                if (string5 != null) break block10;
                string4 = string5;
                if (Build.VERSION.SDK_INT >= 8) {
                    string4 = this.mSearchable.getSuggestIntentAction();
                }
            }
            catch (RuntimeException runtimeException) {
                try {
                    n2 = cursor.getPosition();
                }
                catch (RuntimeException runtimeException2) {
                    n2 = -1;
                }
                Log.w((String)LOG_TAG, (String)("Search suggestions cursor at row " + n2 + " returned exception."), (Throwable)runtimeException);
                return null;
            }
        }
        string5 = string4;
        if (string4 == null) {
            string5 = "android.intent.action.SEARCH";
        }
        string4 = string3 = SuggestionsAdapter.getColumnString(cursor, "suggest_intent_data");
        if (IS_AT_LEAST_FROYO) {
            string4 = string3;
            if (string3 == null) {
                string4 = this.mSearchable.getSuggestIntentData();
            }
        }
        string3 = string4;
        if (string4 != null) {
            String string6 = SuggestionsAdapter.getColumnString(cursor, "suggest_intent_data_id");
            string3 = string4;
            if (string6 != null) {
                string3 = string4 + "/" + Uri.encode((String)string6);
            }
        }
        string4 = string3 == null ? null : Uri.parse((String)string3);
        string3 = SuggestionsAdapter.getColumnString(cursor, "suggest_intent_query");
        return this.createIntent(string5, (Uri)string4, SuggestionsAdapter.getColumnString(cursor, "suggest_intent_extra_data"), string3, n2, string2);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @TargetApi(value=8)
    private Intent createVoiceAppSearchIntent(Intent object, SearchableInfo searchableInfo) {
        void var1_6;
        void var7_25;
        void var9_20;
        void var8_31;
        void var2_8;
        ComponentName componentName = var2_8.getSearchActivity();
        Intent intent = new Intent("android.intent.action.SEARCH");
        intent.setComponent(componentName);
        PendingIntent pendingIntent = PendingIntent.getActivity((Context)this.getContext(), (int)0, (Intent)intent, (int)0x40000000);
        Bundle bundle = new Bundle();
        if (this.mAppSearchData != null) {
            bundle.putParcelable("app_data", (Parcelable)this.mAppSearchData);
        }
        Intent intent2 = new Intent(object);
        String string2 = "free_form";
        Object var9_17 = null;
        Object var5_11 = null;
        Object var7_21 = null;
        String string3 = null;
        int n2 = 1;
        String string4 = string2;
        int n3 = n2;
        if (Build.VERSION.SDK_INT >= 8) {
            void var5_13;
            void var1_4;
            Resources resources = this.getResources();
            if (var2_8.getVoiceLanguageModeId() != 0) {
                String string5 = resources.getString(var2_8.getVoiceLanguageModeId());
            }
            if (var2_8.getVoicePromptTextId() != 0) {
                String string6 = resources.getString(var2_8.getVoicePromptTextId());
            }
            if (var2_8.getVoiceLanguageId() != 0) {
                string3 = resources.getString(var2_8.getVoiceLanguageId());
            }
            String string7 = string3;
            void var8_29 = var1_4;
            n3 = n2;
            void var9_18 = var5_13;
            if (var2_8.getVoiceMaxResults() != 0) {
                n3 = var2_8.getVoiceMaxResults();
                void var9_19 = var5_13;
                void var8_30 = var1_4;
                String string8 = string3;
            }
        }
        intent2.putExtra("android.speech.extra.LANGUAGE_MODEL", (String)var8_31);
        intent2.putExtra("android.speech.extra.PROMPT", (String)var9_20);
        intent2.putExtra("android.speech.extra.LANGUAGE", (String)var7_25);
        intent2.putExtra("android.speech.extra.MAX_RESULTS", n3);
        if (componentName == null) {
            Object var1_5 = null;
        } else {
            String string9 = componentName.flattenToShortString();
        }
        intent2.putExtra("calling_package", (String)var1_6);
        intent2.putExtra("android.speech.extra.RESULTS_PENDINGINTENT", (Parcelable)pendingIntent);
        intent2.putExtra("android.speech.extra.RESULTS_PENDINGINTENT_BUNDLE", bundle);
        return intent2;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @TargetApi(value=8)
    private Intent createVoiceWebSearchIntent(Intent object, SearchableInfo searchableInfo) {
        void var1_4;
        void var2_6;
        Intent intent = new Intent(object);
        ComponentName componentName = var2_6.getSearchActivity();
        if (componentName == null) {
            Object var1_3 = null;
        } else {
            String string2 = componentName.flattenToShortString();
        }
        intent.putExtra("calling_package", (String)var1_4);
        return intent;
    }

    private void dismissSuggestions() {
        this.mSearchSrcTextView.dismissDropDown();
    }

    private void forceSuggestionQuery() {
        HIDDEN_METHOD_INVOKER.doBeforeTextChanged(this.mSearchSrcTextView);
        HIDDEN_METHOD_INVOKER.doAfterTextChanged(this.mSearchSrcTextView);
    }

    private CharSequence getDecoratedHint(CharSequence charSequence) {
        if (!this.mIconifiedByDefault || this.mSearchHintIcon == null) {
            return charSequence;
        }
        int n2 = (int)((double)this.mSearchSrcTextView.getTextSize() * 1.25);
        this.mSearchHintIcon.setBounds(0, 0, n2, n2);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder((CharSequence)"   ");
        spannableStringBuilder.setSpan((Object)new ImageSpan(this.mSearchHintIcon), 1, 2, 33);
        spannableStringBuilder.append(charSequence);
        return spannableStringBuilder;
    }

    private int getPreferredWidth() {
        return this.getContext().getResources().getDimensionPixelSize(R.dimen.abc_search_view_preferred_width);
    }

    /*
     * Enabled aggressive block sorting
     */
    @TargetApi(value=8)
    private boolean hasVoiceSearch() {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (this.mSearchable == null) return bl3;
        bl3 = bl2;
        if (!this.mSearchable.getVoiceSearchEnabled()) return bl3;
        Intent intent = null;
        if (this.mSearchable.getVoiceSearchLaunchWebSearch()) {
            intent = this.mVoiceWebSearchIntent;
        } else if (this.mSearchable.getVoiceSearchLaunchRecognizer()) {
            intent = this.mVoiceAppSearchIntent;
        }
        bl3 = bl2;
        if (intent == null) return bl3;
        bl3 = bl2;
        if (this.getContext().getPackageManager().resolveActivity(intent, 65536) == null) return bl3;
        return true;
    }

    static boolean isLandscapeMode(Context context) {
        return context.getResources().getConfiguration().orientation == 2;
    }

    private boolean isSubmitAreaEnabled() {
        return (this.mSubmitButtonEnabled || this.mVoiceButtonEnabled) && !this.isIconified();
    }

    private void launchIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        try {
            this.getContext().startActivity(intent);
            return;
        }
        catch (RuntimeException runtimeException) {
            Log.e((String)LOG_TAG, (String)("Failed launch activity: " + intent), (Throwable)runtimeException);
            return;
        }
    }

    private void launchQuerySearch(int n2, String string2, String string3) {
        string2 = this.createIntent("android.intent.action.SEARCH", null, null, string3, n2, string2);
        this.getContext().startActivity((Intent)string2);
    }

    private boolean launchSuggestion(int n2, int n3, String string2) {
        Cursor cursor = this.mSuggestionsAdapter.getCursor();
        if (cursor != null && cursor.moveToPosition(n2)) {
            this.launchIntent(this.createIntentFromSuggestion(cursor, n3, string2));
            return true;
        }
        return false;
    }

    private void onCloseClicked() {
        if (TextUtils.isEmpty((CharSequence)this.mSearchSrcTextView.getText())) {
            if (this.mIconifiedByDefault && (this.mOnCloseListener == null || !this.mOnCloseListener.onClose())) {
                this.clearFocus();
                this.updateViewsVisibility(true);
            }
            return;
        }
        this.mSearchSrcTextView.setText("");
        this.mSearchSrcTextView.requestFocus();
        this.setImeVisibility(true);
    }

    private boolean onItemClicked(int n2, int n3, String string2) {
        boolean bl2 = false;
        if (this.mOnSuggestionListener == null || !this.mOnSuggestionListener.onSuggestionClick(n2)) {
            this.launchSuggestion(n2, 0, null);
            this.setImeVisibility(false);
            this.dismissSuggestions();
            bl2 = true;
        }
        return bl2;
    }

    private boolean onItemSelected(int n2) {
        if (this.mOnSuggestionListener == null || !this.mOnSuggestionListener.onSuggestionSelect(n2)) {
            this.rewriteQueryFromSuggestion(n2);
            return true;
        }
        return false;
    }

    private void onSearchClicked() {
        this.updateViewsVisibility(false);
        this.mSearchSrcTextView.requestFocus();
        this.setImeVisibility(true);
        if (this.mOnSearchClickListener != null) {
            this.mOnSearchClickListener.onClick((View)this);
        }
    }

    private void onSubmitQuery() {
        Editable editable = this.mSearchSrcTextView.getText();
        if (!(editable == null || TextUtils.getTrimmedLength((CharSequence)editable) <= 0 || this.mOnQueryChangeListener != null && this.mOnQueryChangeListener.onQueryTextSubmit(editable.toString()))) {
            if (this.mSearchable != null) {
                this.launchQuerySearch(0, null, editable.toString());
            }
            this.setImeVisibility(false);
            this.dismissSuggestions();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean onSuggestionsKey(View view, int n2, KeyEvent keyEvent) {
        block6: {
            block5: {
                if (this.mSearchable == null || this.mSuggestionsAdapter == null || keyEvent.getAction() != 0 || !KeyEventCompat.hasNoModifiers(keyEvent)) break block5;
                if (n2 == 66 || n2 == 84 || n2 == 61) {
                    return this.onItemClicked(this.mSearchSrcTextView.getListSelection(), 0, null);
                }
                if (n2 == 21 || n2 == 22) {
                    n2 = n2 == 21 ? 0 : this.mSearchSrcTextView.length();
                    this.mSearchSrcTextView.setSelection(n2);
                    this.mSearchSrcTextView.setListSelection(0);
                    this.mSearchSrcTextView.clearListSelection();
                    HIDDEN_METHOD_INVOKER.ensureImeVisible(this.mSearchSrcTextView, true);
                    return true;
                }
                if (n2 == 19 && this.mSearchSrcTextView.getListSelection() == 0) break block6;
            }
            return false;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onTextChanged(CharSequence charSequence) {
        boolean bl2 = true;
        Editable editable = this.mSearchSrcTextView.getText();
        this.mUserQuery = editable;
        boolean bl3 = !TextUtils.isEmpty((CharSequence)editable);
        this.updateSubmitButton(bl3);
        bl3 = !bl3 ? bl2 : false;
        this.updateVoiceButton(bl3);
        this.updateCloseButton();
        this.updateSubmitArea();
        if (this.mOnQueryChangeListener != null && !TextUtils.equals((CharSequence)charSequence, (CharSequence)this.mOldQueryText)) {
            this.mOnQueryChangeListener.onQueryTextChange(charSequence.toString());
        }
        this.mOldQueryText = charSequence.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @TargetApi(value=8)
    private void onVoiceClicked() {
        SearchableInfo searchableInfo;
        block6: {
            block5: {
                if (this.mSearchable != null) {
                    searchableInfo = this.mSearchable;
                    try {
                        if (searchableInfo.getVoiceSearchLaunchWebSearch()) {
                            searchableInfo = this.createVoiceWebSearchIntent(this.mVoiceWebSearchIntent, searchableInfo);
                            this.getContext().startActivity((Intent)searchableInfo);
                            return;
                        }
                        if (!searchableInfo.getVoiceSearchLaunchRecognizer()) break block5;
                        break block6;
                    }
                    catch (ActivityNotFoundException activityNotFoundException) {
                        Log.w((String)LOG_TAG, (String)"Could not find voice search activity");
                        return;
                    }
                }
            }
            return;
        }
        searchableInfo = this.createVoiceAppSearchIntent(this.mVoiceAppSearchIntent, searchableInfo);
        this.getContext().startActivity((Intent)searchableInfo);
    }

    private void postUpdateFocusedState() {
        this.post(this.mUpdateDrawableStateRunnable);
    }

    private void rewriteQueryFromSuggestion(int n2) {
        Editable editable = this.mSearchSrcTextView.getText();
        Object object = this.mSuggestionsAdapter.getCursor();
        if (object == null) {
            return;
        }
        if (object.moveToPosition(n2)) {
            if ((object = this.mSuggestionsAdapter.convertToString((Cursor)object)) != null) {
                this.setQuery((CharSequence)object);
                return;
            }
            this.setQuery((CharSequence)editable);
            return;
        }
        this.setQuery((CharSequence)editable);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setImeVisibility(boolean bl2) {
        if (bl2) {
            this.post(this.mShowImeRunnable);
            return;
        } else {
            this.removeCallbacks(this.mShowImeRunnable);
            InputMethodManager inputMethodManager = (InputMethodManager)this.getContext().getSystemService("input_method");
            if (inputMethodManager == null) return;
            inputMethodManager.hideSoftInputFromWindow(this.getWindowToken(), 0);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setQuery(CharSequence charSequence) {
        this.mSearchSrcTextView.setText(charSequence);
        SearchAutoComplete searchAutoComplete = this.mSearchSrcTextView;
        int n2 = TextUtils.isEmpty((CharSequence)charSequence) ? 0 : charSequence.length();
        searchAutoComplete.setSelection(n2);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private void updateCloseButton() {
        int n2 = 1;
        int n3 = 0;
        boolean bl2 = !TextUtils.isEmpty((CharSequence)this.mSearchSrcTextView.getText());
        int n4 = n2;
        if (!bl2) {
            n4 = this.mIconifiedByDefault && !this.mExpandedInActionView ? n2 : 0;
        }
        ImageView imageView = this.mCloseButton;
        n4 = n4 != 0 ? n3 : 8;
        imageView.setVisibility(n4);
        Drawable drawable2 = this.mCloseButton.getDrawable();
        if (drawable2 != null) {
            void var5_7;
            if (bl2) {
                int[] nArray = ENABLED_STATE_SET;
            } else {
                int[] nArray = EMPTY_STATE_SET;
            }
            drawable2.setState((int[])var5_7);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateFocusedState() {
        int[] nArray = this.mSearchSrcTextView.hasFocus() ? FOCUSED_STATE_SET : EMPTY_STATE_SET;
        Drawable drawable2 = this.mSearchPlate.getBackground();
        if (drawable2 != null) {
            drawable2.setState(nArray);
        }
        if ((drawable2 = this.mSubmitArea.getBackground()) != null) {
            drawable2.setState(nArray);
        }
        this.invalidate();
    }

    private void updateQueryHint() {
        CharSequence charSequence = this.getQueryHint();
        SearchAutoComplete searchAutoComplete = this.mSearchSrcTextView;
        CharSequence charSequence2 = charSequence;
        if (charSequence == null) {
            charSequence2 = "";
        }
        searchAutoComplete.setHint(this.getDecoratedHint(charSequence2));
    }

    @TargetApi(value=8)
    private void updateSearchAutoComplete() {
        int n2;
        int n3 = 1;
        this.mSearchSrcTextView.setThreshold(this.mSearchable.getSuggestThreshold());
        this.mSearchSrcTextView.setImeOptions(this.mSearchable.getImeOptions());
        int n4 = n2 = this.mSearchable.getInputType();
        if ((n2 & 0xF) == 1) {
            n4 = n2 &= 0xFFFEFFFF;
            if (this.mSearchable.getSuggestAuthority() != null) {
                n4 = n2 | 0x10000 | 0x80000;
            }
        }
        this.mSearchSrcTextView.setInputType(n4);
        if (this.mSuggestionsAdapter != null) {
            this.mSuggestionsAdapter.changeCursor(null);
        }
        if (this.mSearchable.getSuggestAuthority() != null) {
            this.mSuggestionsAdapter = new SuggestionsAdapter(this.getContext(), this, this.mSearchable, this.mOutsideDrawablesCache);
            this.mSearchSrcTextView.setAdapter((ListAdapter)this.mSuggestionsAdapter);
            SuggestionsAdapter suggestionsAdapter = (SuggestionsAdapter)this.mSuggestionsAdapter;
            n4 = n3;
            if (this.mQueryRefinement) {
                n4 = 2;
            }
            suggestionsAdapter.setQueryRefinement(n4);
        }
    }

    private void updateSubmitArea() {
        int n2;
        block2: {
            block3: {
                int n3;
                n2 = n3 = 8;
                if (!this.isSubmitAreaEnabled()) break block2;
                if (this.mGoButton.getVisibility() == 0) break block3;
                n2 = n3;
                if (this.mVoiceButton.getVisibility() != 0) break block2;
            }
            n2 = 0;
        }
        this.mSubmitArea.setVisibility(n2);
    }

    private void updateSubmitButton(boolean bl2) {
        int n2;
        block2: {
            block3: {
                int n3;
                n2 = n3 = 8;
                if (!this.mSubmitButtonEnabled) break block2;
                n2 = n3;
                if (!this.isSubmitAreaEnabled()) break block2;
                n2 = n3;
                if (!this.hasFocus()) break block2;
                if (bl2) break block3;
                n2 = n3;
                if (this.mVoiceButtonEnabled) break block2;
            }
            n2 = 0;
        }
        this.mGoButton.setVisibility(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateViewsVisibility(boolean bl2) {
        int n2 = 8;
        boolean bl3 = true;
        this.mIconified = bl2;
        int n3 = bl2 ? 0 : 8;
        boolean bl4 = !TextUtils.isEmpty((CharSequence)this.mSearchSrcTextView.getText());
        this.mSearchButton.setVisibility(n3);
        this.updateSubmitButton(bl4);
        View view = this.mSearchEditFrame;
        n3 = bl2 ? n2 : 0;
        view.setVisibility(n3);
        n3 = this.mCollapsedIcon.getDrawable() == null || this.mIconifiedByDefault ? 8 : 0;
        this.mCollapsedIcon.setVisibility(n3);
        this.updateCloseButton();
        bl2 = !bl4 ? bl3 : false;
        this.updateVoiceButton(bl2);
        this.updateSubmitArea();
    }

    private void updateVoiceButton(boolean bl2) {
        int n2;
        int n3 = n2 = 8;
        if (this.mVoiceButtonEnabled) {
            n3 = n2;
            if (!this.isIconified()) {
                n3 = n2;
                if (bl2) {
                    n3 = 0;
                    this.mGoButton.setVisibility(8);
                }
            }
        }
        this.mVoiceButton.setVisibility(n3);
    }

    public void clearFocus() {
        this.mClearingFocus = true;
        this.setImeVisibility(false);
        super.clearFocus();
        this.mSearchSrcTextView.clearFocus();
        this.mClearingFocus = false;
    }

    public int getImeOptions() {
        return this.mSearchSrcTextView.getImeOptions();
    }

    public int getInputType() {
        return this.mSearchSrcTextView.getInputType();
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    public CharSequence getQuery() {
        return this.mSearchSrcTextView.getText();
    }

    public CharSequence getQueryHint() {
        if (this.mQueryHint != null) {
            return this.mQueryHint;
        }
        if (IS_AT_LEAST_FROYO && this.mSearchable != null && this.mSearchable.getHintId() != 0) {
            return this.getContext().getText(this.mSearchable.getHintId());
        }
        return this.mDefaultQueryHint;
    }

    int getSuggestionCommitIconResId() {
        return this.mSuggestionCommitIconResId;
    }

    int getSuggestionRowLayout() {
        return this.mSuggestionRowLayout;
    }

    public CursorAdapter getSuggestionsAdapter() {
        return this.mSuggestionsAdapter;
    }

    public boolean isIconfiedByDefault() {
        return this.mIconifiedByDefault;
    }

    public boolean isIconified() {
        return this.mIconified;
    }

    public boolean isQueryRefinementEnabled() {
        return this.mQueryRefinement;
    }

    public boolean isSubmitButtonEnabled() {
        return this.mSubmitButtonEnabled;
    }

    @Override
    public void onActionViewCollapsed() {
        this.setQuery("", false);
        this.clearFocus();
        this.updateViewsVisibility(true);
        this.mSearchSrcTextView.setImeOptions(this.mCollapsedImeOptions);
        this.mExpandedInActionView = false;
    }

    @Override
    public void onActionViewExpanded() {
        if (this.mExpandedInActionView) {
            return;
        }
        this.mExpandedInActionView = true;
        this.mCollapsedImeOptions = this.mSearchSrcTextView.getImeOptions();
        this.mSearchSrcTextView.setImeOptions(this.mCollapsedImeOptions | 0x2000000);
        this.mSearchSrcTextView.setText("");
        this.setIconified(false);
    }

    protected void onDetachedFromWindow() {
        this.removeCallbacks(this.mUpdateDrawableStateRunnable);
        this.post(this.mReleaseCursorRunnable);
        super.onDetachedFromWindow();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onMeasure(int n2, int n3) {
        if (this.isIconified()) {
            super.onMeasure(n2, n3);
            return;
        }
        int n4 = View.MeasureSpec.getMode((int)n2);
        int n5 = View.MeasureSpec.getSize((int)n2);
        switch (n4) {
            default: {
                n2 = n5;
                break;
            }
            case -2147483648: {
                if (this.mMaxWidth > 0) {
                    n2 = Math.min(this.mMaxWidth, n5);
                    break;
                }
                n2 = Math.min(this.getPreferredWidth(), n5);
                break;
            }
            case 0x40000000: {
                n2 = n5;
                if (this.mMaxWidth <= 0) break;
                n2 = Math.min(this.mMaxWidth, n5);
                break;
            }
            case 0: {
                n2 = this.mMaxWidth > 0 ? this.mMaxWidth : this.getPreferredWidth();
            }
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec((int)n2, (int)0x40000000), n3);
    }

    void onQueryRefine(CharSequence charSequence) {
        this.setQuery(charSequence);
    }

    protected void onRestoreInstanceState(Parcelable object) {
        if (!(object instanceof SavedState)) {
            super.onRestoreInstanceState(object);
            return;
        }
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        this.updateViewsVisibility(object.isIconified);
        this.requestLayout();
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.isIconified = this.isIconified();
        return savedState;
    }

    void onTextFocusChanged() {
        this.updateViewsVisibility(this.isIconified());
        this.postUpdateFocusedState();
        if (this.mSearchSrcTextView.hasFocus()) {
            this.forceSuggestionQuery();
        }
    }

    public void onWindowFocusChanged(boolean bl2) {
        super.onWindowFocusChanged(bl2);
        this.postUpdateFocusedState();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean requestFocus(int n2, Rect rect) {
        boolean bl2;
        if (this.mClearingFocus) {
            return false;
        }
        if (!this.isFocusable()) {
            return false;
        }
        if (this.isIconified()) return super.requestFocus(n2, rect);
        boolean bl3 = bl2 = this.mSearchSrcTextView.requestFocus(n2, rect);
        if (!bl2) return bl3;
        this.updateViewsVisibility(false);
        return bl2;
    }

    public void setAppSearchData(Bundle bundle) {
        this.mAppSearchData = bundle;
    }

    public void setIconified(boolean bl2) {
        if (bl2) {
            this.onCloseClicked();
            return;
        }
        this.onSearchClicked();
    }

    public void setIconifiedByDefault(boolean bl2) {
        if (this.mIconifiedByDefault == bl2) {
            return;
        }
        this.mIconifiedByDefault = bl2;
        this.updateViewsVisibility(bl2);
        this.updateQueryHint();
    }

    public void setImeOptions(int n2) {
        this.mSearchSrcTextView.setImeOptions(n2);
    }

    public void setInputType(int n2) {
        this.mSearchSrcTextView.setInputType(n2);
    }

    public void setMaxWidth(int n2) {
        this.mMaxWidth = n2;
        this.requestLayout();
    }

    public void setOnCloseListener(OnCloseListener onCloseListener) {
        this.mOnCloseListener = onCloseListener;
    }

    public void setOnQueryTextFocusChangeListener(View.OnFocusChangeListener onFocusChangeListener) {
        this.mOnQueryTextFocusChangeListener = onFocusChangeListener;
    }

    public void setOnQueryTextListener(OnQueryTextListener onQueryTextListener) {
        this.mOnQueryChangeListener = onQueryTextListener;
    }

    public void setOnSearchClickListener(View.OnClickListener onClickListener) {
        this.mOnSearchClickListener = onClickListener;
    }

    public void setOnSuggestionListener(OnSuggestionListener onSuggestionListener) {
        this.mOnSuggestionListener = onSuggestionListener;
    }

    public void setQuery(CharSequence charSequence, boolean bl2) {
        this.mSearchSrcTextView.setText(charSequence);
        if (charSequence != null) {
            this.mSearchSrcTextView.setSelection(this.mSearchSrcTextView.length());
            this.mUserQuery = charSequence;
        }
        if (bl2 && !TextUtils.isEmpty((CharSequence)charSequence)) {
            this.onSubmitQuery();
        }
    }

    public void setQueryHint(CharSequence charSequence) {
        this.mQueryHint = charSequence;
        this.updateQueryHint();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setQueryRefinementEnabled(boolean bl2) {
        this.mQueryRefinement = bl2;
        if (this.mSuggestionsAdapter instanceof SuggestionsAdapter) {
            SuggestionsAdapter suggestionsAdapter = (SuggestionsAdapter)this.mSuggestionsAdapter;
            int n2 = bl2 ? 2 : 1;
            suggestionsAdapter.setQueryRefinement(n2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setSearchableInfo(SearchableInfo searchableInfo) {
        this.mSearchable = searchableInfo;
        if (this.mSearchable != null) {
            if (IS_AT_LEAST_FROYO) {
                this.updateSearchAutoComplete();
            }
            this.updateQueryHint();
        }
        boolean bl2 = IS_AT_LEAST_FROYO && this.hasVoiceSearch();
        this.mVoiceButtonEnabled = bl2;
        if (this.mVoiceButtonEnabled) {
            this.mSearchSrcTextView.setPrivateImeOptions(IME_OPTION_NO_MICROPHONE);
        }
        this.updateViewsVisibility(this.isIconified());
    }

    public void setSubmitButtonEnabled(boolean bl2) {
        this.mSubmitButtonEnabled = bl2;
        this.updateViewsVisibility(this.isIconified());
    }

    public void setSuggestionsAdapter(CursorAdapter cursorAdapter) {
        this.mSuggestionsAdapter = cursorAdapter;
        this.mSearchSrcTextView.setAdapter((ListAdapter)this.mSuggestionsAdapter);
    }

    private static class AutoCompleteTextViewReflector {
        private Method doAfterTextChanged;
        private Method doBeforeTextChanged;
        private Method ensureImeVisible;
        private Method showSoftInputUnchecked;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        AutoCompleteTextViewReflector() {
            try {
                this.doBeforeTextChanged = AutoCompleteTextView.class.getDeclaredMethod("doBeforeTextChanged", new Class[0]);
                this.doBeforeTextChanged.setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {}
            try {
                this.doAfterTextChanged = AutoCompleteTextView.class.getDeclaredMethod("doAfterTextChanged", new Class[0]);
                this.doAfterTextChanged.setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {}
            try {
                this.ensureImeVisible = AutoCompleteTextView.class.getMethod("ensureImeVisible", Boolean.TYPE);
                this.ensureImeVisible.setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {}
            try {
                this.showSoftInputUnchecked = InputMethodManager.class.getMethod("showSoftInputUnchecked", Integer.TYPE, ResultReceiver.class);
                this.showSoftInputUnchecked.setAccessible(true);
                return;
            }
            catch (NoSuchMethodException noSuchMethodException) {
                return;
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        void doAfterTextChanged(AutoCompleteTextView autoCompleteTextView) {
            if (this.doAfterTextChanged == null) return;
            try {
                this.doAfterTextChanged.invoke(autoCompleteTextView, new Object[0]);
                return;
            }
            catch (Exception exception) {
                return;
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        void doBeforeTextChanged(AutoCompleteTextView autoCompleteTextView) {
            if (this.doBeforeTextChanged == null) return;
            try {
                this.doBeforeTextChanged.invoke(autoCompleteTextView, new Object[0]);
                return;
            }
            catch (Exception exception) {
                return;
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        void ensureImeVisible(AutoCompleteTextView autoCompleteTextView, boolean bl2) {
            if (this.ensureImeVisible == null) return;
            try {
                this.ensureImeVisible.invoke(autoCompleteTextView, bl2);
                return;
            }
            catch (Exception exception) {
                return;
            }
        }

        void showSoftInputUnchecked(InputMethodManager inputMethodManager, View view, int n2) {
            if (this.showSoftInputUnchecked != null) {
                try {
                    this.showSoftInputUnchecked.invoke(inputMethodManager, n2, null);
                    return;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            inputMethodManager.showSoftInput(view, n2);
        }
    }

    public static interface OnCloseListener {
        public boolean onClose();
    }

    public static interface OnQueryTextListener {
        public boolean onQueryTextChange(String var1);

        public boolean onQueryTextSubmit(String var1);
    }

    public static interface OnSuggestionListener {
        public boolean onSuggestionClick(int var1);

        public boolean onSuggestionSelect(int var1);
    }

    static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n2) {
                return new SavedState[n2];
            }
        };
        boolean isIconified;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.isIconified = (Boolean)parcel.readValue(null);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "SearchView.SavedState{" + Integer.toHexString(System.identityHashCode((Object)this)) + " isIconified=" + this.isIconified + "}";
        }

        public void writeToParcel(Parcel parcel, int n2) {
            super.writeToParcel(parcel, n2);
            parcel.writeValue((Object)this.isIconified);
        }
    }

    public static class SearchAutoComplete
    extends AppCompatAutoCompleteTextView {
        private SearchView mSearchView;
        private int mThreshold = this.getThreshold();

        public SearchAutoComplete(Context context) {
            this(context, null);
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, R.attr.autoCompleteTextViewStyle);
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet, int n2) {
            super(context, attributeSet, n2);
        }

        private boolean isEmpty() {
            return TextUtils.getTrimmedLength((CharSequence)this.getText()) == 0;
        }

        public boolean enoughToFilter() {
            return this.mThreshold <= 0 || super.enoughToFilter();
        }

        protected void onFocusChanged(boolean bl2, int n2, Rect rect) {
            super.onFocusChanged(bl2, n2, rect);
            this.mSearchView.onTextFocusChanged();
        }

        public boolean onKeyPreIme(int n2, KeyEvent keyEvent) {
            if (n2 == 4) {
                if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                    KeyEvent.DispatcherState dispatcherState = this.getKeyDispatcherState();
                    if (dispatcherState != null) {
                        dispatcherState.startTracking(keyEvent, (Object)this);
                    }
                    return true;
                }
                if (keyEvent.getAction() == 1) {
                    KeyEvent.DispatcherState dispatcherState = this.getKeyDispatcherState();
                    if (dispatcherState != null) {
                        dispatcherState.handleUpEvent(keyEvent);
                    }
                    if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                        this.mSearchView.clearFocus();
                        this.mSearchView.setImeVisibility(false);
                        return true;
                    }
                }
            }
            return super.onKeyPreIme(n2, keyEvent);
        }

        public void onWindowFocusChanged(boolean bl2) {
            super.onWindowFocusChanged(bl2);
            if (bl2 && this.mSearchView.hasFocus() && this.getVisibility() == 0) {
                ((InputMethodManager)this.getContext().getSystemService("input_method")).showSoftInput((View)this, 0);
                if (SearchView.isLandscapeMode(this.getContext())) {
                    HIDDEN_METHOD_INVOKER.ensureImeVisible(this, true);
                }
            }
        }

        public void performCompletion() {
        }

        protected void replaceText(CharSequence charSequence) {
        }

        void setSearchView(SearchView searchView) {
            this.mSearchView = searchView;
        }

        public void setThreshold(int n2) {
            super.setThreshold(n2);
            this.mThreshold = n2;
        }
    }
}

