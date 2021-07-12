/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.app.FragmentTransaction
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.util.Xml
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.Button
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.TextView
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package ticwear.design.preference;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.XmlRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import ticwear.design.R;
import ticwear.design.app.RecyclerActivity;
import ticwear.design.internal.XmlUtils;
import ticwear.design.preference.Preference;
import ticwear.design.preference.PreferenceFragment;
import ticwear.design.preference.PreferenceViewHolder;
import ticwear.design.widget.SelectableAdapter;
import ticwear.design.widget.TicklableRecyclerView;

public abstract class PreferenceActivity
extends RecyclerActivity
implements PreferenceFragment.OnPreferenceStartFragmentCallback {
    private static final String BACK_STACK_PREFS = ":android:prefs";
    private static final String CUR_HEADER_TAG = ":android:cur_header";
    public static final String EXTRA_NO_HEADERS = ":android:no_headers";
    private static final String EXTRA_PREFS_SET_BACK_TEXT = "extra_prefs_set_back_text";
    private static final String EXTRA_PREFS_SET_NEXT_TEXT = "extra_prefs_set_next_text";
    private static final String EXTRA_PREFS_SHOW_BUTTON_BAR = "extra_prefs_show_button_bar";
    private static final String EXTRA_PREFS_SHOW_SKIP = "extra_prefs_show_skip";
    public static final String EXTRA_SHOW_FRAGMENT = ":android:show_fragment";
    public static final String EXTRA_SHOW_FRAGMENT_ARGUMENTS = ":android:show_fragment_args";
    public static final String EXTRA_SHOW_FRAGMENT_SHORT_TITLE = ":android:show_fragment_short_title";
    public static final String EXTRA_SHOW_FRAGMENT_TITLE = ":android:show_fragment_title";
    private static final int FIRST_REQUEST_CODE = 100;
    private static final String HEADERS_TAG = ":android:headers";
    public static final long HEADER_ID_UNDEFINED = -1L;
    private static final int MSG_BUILD_HEADERS = 2;
    private static final String TAG = "PreferenceActivity";
    private Header mCurHeader;
    private Handler mHandler;
    private final ArrayList<Header> mHeaders = new ArrayList();
    private FrameLayout mListFooter;
    private Button mNextButton;
    HeaderAdapter.OnHeaderClickListener mOnHeaderClickListener;
    private int mPreferenceHeaderItemResId = 0;
    private boolean mPreferenceHeaderRemoveEmptyIcon = false;
    private ViewGroup mPrefsContainer;
    private boolean mSinglePane;
    private TextView mTitleView;

    public PreferenceActivity() {
        this.mHandler = new Handler(){

            /*
             * Enabled aggressive block sorting
             */
            public void handleMessage(Message object) {
                switch (object.what) {
                    default: {
                        return;
                    }
                    case 2: {
                        Header header;
                        Header header2;
                        Object object2 = new ArrayList<Header>(PreferenceActivity.this.mHeaders);
                        PreferenceActivity.this.mHeaders.clear();
                        PreferenceActivity.this.onBuildHeaders(PreferenceActivity.this.mHeaders);
                        if (PreferenceActivity.this.getListAdapter() != null) {
                            PreferenceActivity.this.getListAdapter().notifyDataSetChanged();
                        }
                        if ((header2 = PreferenceActivity.this.onGetNewHeader()) != null && header2.fragment != null) {
                            if ((object2 = PreferenceActivity.this.findBestMatchingHeader(header2, (ArrayList<Header>)object2)) != null && PreferenceActivity.this.mCurHeader == object2) return;
                            PreferenceActivity.this.switchToHeader(header2);
                            return;
                        }
                        if (PreferenceActivity.this.mCurHeader == null || (header = PreferenceActivity.this.findBestMatchingHeader(PreferenceActivity.this.mCurHeader, PreferenceActivity.this.mHeaders)) == null) return;
                        PreferenceActivity.this.setSelectedHeader(header);
                        return;
                    }
                }
            }
        };
        this.mOnHeaderClickListener = new HeaderAdapter.OnHeaderClickListener(){

            @Override
            public boolean onHeaderClick(Header header, int n2) {
                return PreferenceActivity.this.onHeaderClick(header, n2);
            }
        };
    }

    private void switchToHeaderInner(String string2, Bundle bundle) {
        this.getFragmentManager().popBackStack(BACK_STACK_PREFS, 1);
        if (!this.isValidFragment(string2)) {
            throw new IllegalArgumentException("Invalid fragment for this activity: " + string2);
        }
        string2 = Fragment.instantiate((Context)this, (String)string2, (Bundle)bundle);
        bundle = this.getFragmentManager().beginTransaction();
        bundle.setTransition(4099);
        bundle.replace(R.id.prefs, (Fragment)string2);
        bundle.commitAllowingStateLoss();
    }

    /*
     * Enabled aggressive block sorting
     */
    Header findBestMatchingHeader(Header header, ArrayList<Header> object) {
        int n2;
        Header header2;
        ArrayList<Header> arrayList = new ArrayList<Header>();
        int n3 = 0;
        while (true) {
            block18: {
                block17: {
                    if (n3 >= ((ArrayList)object).size()) break block17;
                    header2 = (Header)((ArrayList)object).get(n3);
                    if (header != header2 && (header.id == -1L || header.id != header2.id)) break block18;
                    arrayList.clear();
                    arrayList.add(header2);
                }
                if ((n2 = arrayList.size()) != 1) break;
                return (Header)arrayList.get(0);
            }
            if (header.fragment != null) {
                if (header.fragment.equals(header2.fragment)) {
                    arrayList.add(header2);
                }
            } else if (header.intent != null) {
                if (header.intent.equals(header2.intent)) {
                    arrayList.add(header2);
                }
            } else if (header.title != null && header.title.equals(header2.title)) {
                arrayList.add(header2);
            }
            ++n3;
        }
        if (n2 <= 1) return null;
        n3 = 0;
        while (n3 < n2) {
            header2 = (Header)arrayList.get(n3);
            if (header.fragmentArguments != null) {
                object = header2;
                if (header.fragmentArguments.equals(header2.fragmentArguments)) return object;
            }
            if (header.extras != null) {
                object = header2;
                if (header.extras.equals(header2.extras)) return object;
            }
            if (header.title != null) {
                object = header2;
                if (header.title.equals(header2.title)) return object;
            }
            ++n3;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void finishPreferencePanel(Fragment fragment, int n2, Intent intent) {
        if (this.mSinglePane) {
            this.setResult(n2, intent);
            this.finish();
            return;
        } else {
            this.onBackPressed();
            if (fragment == null || fragment.getTargetFragment() == null) return;
            fragment.getTargetFragment().onActivityResult(fragment.getTargetRequestCode(), n2, intent);
            return;
        }
    }

    public List<Header> getHeaders() {
        return this.mHeaders;
    }

    @Override
    public SelectableAdapter getListAdapter() {
        return (SelectableAdapter)super.getListAdapter();
    }

    @Override
    public TicklableRecyclerView getListView() {
        return (TicklableRecyclerView)super.getListView();
    }

    protected Button getNextButton() {
        return this.mNextButton;
    }

    public boolean hasHeaders() {
        return this.getListView().getVisibility() == 0;
    }

    protected boolean hasNextButton() {
        return this.mNextButton != null;
    }

    public void invalidateHeaders() {
        if (!this.mHandler.hasMessages(2)) {
            this.mHandler.sendEmptyMessage(2);
        }
    }

    public boolean isMultiPane() {
        return this.hasHeaders() && this.mPrefsContainer.getVisibility() == 0;
    }

    protected boolean isValidFragment(String string2) {
        if (this.getApplicationInfo().targetSdkVersion >= 19) {
            throw new RuntimeException("Subclasses of PreferenceActivity must override isValidFragment(String) to verify that the Fragment class is valid! " + this.getClass().getName() + " has not checked if fragment " + string2 + " is valid.");
        }
        return true;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void loadHeadersFromResource(@XmlRes int var1_1, List<Header> var2_2) {
        block30: {
            var5_6 = null;
            var8_7 = null;
            var6_8 = null;
            try {
                var6_8 = var7_9 = this.getResources().getXml(var1_1);
                var5_6 = var7_9;
                var8_7 = var7_9;
                var11_10 = Xml.asAttributeSet((XmlPullParser)var7_9);
                do {
                    var6_8 = var7_9;
                    var5_6 = var7_9;
                    var8_7 = var7_9;
                } while ((var1_1 = var7_9.next()) != 1 && var1_1 != 2);
                var6_8 = var7_9;
                var5_6 = var7_9;
                var8_7 = var7_9;
                var9_11 = var7_9.getName();
                var6_8 = var7_9;
                var5_6 = var7_9;
                var8_7 = var7_9;
                if ("preference-headers".equals(var9_11)) ** GOTO lbl-1000
                var6_8 = var7_9;
                var5_6 = var7_9;
                var8_7 = var7_9;
                throw new RuntimeException("XML document must start with <preference-headers> tag; found" + var9_11 + " at " + var7_9.getPositionDescription());
lbl-1000:
                // 1 sources

                {
                    var9_11 = null;
                    var6_8 = var7_9;
                    var5_6 = var7_9;
                    var8_7 = var7_9;
                    try {
                        var1_1 = var7_9.getDepth();
                        break block30;
                    }
                    catch (XmlPullParserException var2_3) {}
                    var5_6 = var6_8;
                    throw new RuntimeException("Error parsing headers", var2_3);
                }
            }
            catch (Throwable var2_4) {
                if (var5_6 != null) {
                    var5_6.close();
                }
                throw var2_4;
            }
        }
        while (true) {
            block33: {
                block32: {
                    block31: {
                        var6_8 = var7_9;
                        var5_6 = var7_9;
                        var8_7 = var7_9;
                        var3_12 = var7_9.next();
                        if (var3_12 == 1) ** GOTO lbl185
                        if (var3_12 != 3) break block31;
                        var6_8 = var7_9;
                        var5_6 = var7_9;
                        var8_7 = var7_9;
                        if (var7_9.getDepth() <= var1_1) ** GOTO lbl185
                    }
                    if (var3_12 == 3 || var3_12 == 4) continue;
                    var6_8 = var7_9;
                    var5_6 = var7_9;
                    var8_7 = var7_9;
                    if (!"header".equals(var7_9.getName())) ** GOTO lbl180
                    var6_8 = var7_9;
                    var5_6 = var7_9;
                    var8_7 = var7_9;
                    var12_15 = new Header();
                    var6_8 = var7_9;
                    var5_6 = var7_9;
                    var8_7 = var7_9;
                    var10_14 /* !! */  = this.obtainStyledAttributes(var11_10, R.styleable.PreferenceHeader);
                    var6_8 = var7_9;
                    var5_6 = var7_9;
                    var8_7 = var7_9;
                    var12_15.id = var10_14 /* !! */ .getResourceId(R.styleable.PreferenceHeader_android_id, -1);
                    var6_8 = var7_9;
                    var5_6 = var7_9;
                    var8_7 = var7_9;
                    var13_16 = var10_14 /* !! */ .peekValue(R.styleable.PreferenceHeader_android_title);
                    if (var13_16 == null) ** GOTO lbl95
                    var6_8 = var7_9;
                    var5_6 = var7_9;
                    var8_7 = var7_9;
                    if (var13_16.type != 3) ** GOTO lbl95
                    var6_8 = var7_9;
                    var5_6 = var7_9;
                    var8_7 = var7_9;
                    if (var13_16.resourceId == 0) break block32;
                    var6_8 = var7_9;
                    var5_6 = var7_9;
                    var8_7 = var7_9;
                    var12_15.titleRes = var13_16.resourceId;
                    ** GOTO lbl95
                }
                var6_8 = var7_9;
                var5_6 = var7_9;
                var8_7 = var7_9;
                var12_15.title = var13_16.string;
lbl95:
                // 4 sources

                var6_8 = var7_9;
                var5_6 = var7_9;
                var8_7 = var7_9;
                var13_16 = var10_14 /* !! */ .peekValue(R.styleable.PreferenceHeader_android_summary);
                if (var13_16 != null) {
                    var6_8 = var7_9;
                    var5_6 = var7_9;
                    var8_7 = var7_9;
                    if (var13_16.type == 3) {
                        var6_8 = var7_9;
                        var5_6 = var7_9;
                        var8_7 = var7_9;
                        if (var13_16.resourceId != 0) {
                            var6_8 = var7_9;
                            var5_6 = var7_9;
                            var8_7 = var7_9;
                            var12_15.summaryRes = var13_16.resourceId;
                        } else {
                            var6_8 = var7_9;
                            var5_6 = var7_9;
                            var8_7 = var7_9;
                            var12_15.summary = var13_16.string;
                        }
                    }
                }
                var6_8 = var7_9;
                var5_6 = var7_9;
                var8_7 = var7_9;
                var13_16 = var10_14 /* !! */ .peekValue(R.styleable.PreferenceHeader_android_breadCrumbTitle);
                if (var13_16 != null) {
                    var6_8 = var7_9;
                    var5_6 = var7_9;
                    var8_7 = var7_9;
                    if (var13_16.type == 3) {
                        var6_8 = var7_9;
                        var5_6 = var7_9;
                        var8_7 = var7_9;
                        if (var13_16.resourceId != 0) {
                            var6_8 = var7_9;
                            var5_6 = var7_9;
                            var8_7 = var7_9;
                            var12_15.breadCrumbTitleRes = var13_16.resourceId;
                        } else {
                            var6_8 = var7_9;
                            var5_6 = var7_9;
                            var8_7 = var7_9;
                            var12_15.breadCrumbTitle = var13_16.string;
                        }
                    }
                }
                var6_8 = var7_9;
                var5_6 = var7_9;
                var8_7 = var7_9;
                var13_16 = var10_14 /* !! */ .peekValue(R.styleable.PreferenceHeader_android_breadCrumbShortTitle);
                if (var13_16 != null) {
                    var6_8 = var7_9;
                    var5_6 = var7_9;
                    var8_7 = var7_9;
                    if (var13_16.type == 3) {
                        var6_8 = var7_9;
                        var5_6 = var7_9;
                        var8_7 = var7_9;
                        if (var13_16.resourceId != 0) {
                            var6_8 = var7_9;
                            var5_6 = var7_9;
                            var8_7 = var7_9;
                            var12_15.breadCrumbShortTitleRes = var13_16.resourceId;
                        } else {
                            var6_8 = var7_9;
                            var5_6 = var7_9;
                            var8_7 = var7_9;
                            var12_15.breadCrumbShortTitle = var13_16.string;
                        }
                    }
                }
                var6_8 = var7_9;
                var5_6 = var7_9;
                var8_7 = var7_9;
                var12_15.iconRes = var10_14 /* !! */ .getResourceId(R.styleable.PreferenceHeader_android_icon, 0);
                var6_8 = var7_9;
                var5_6 = var7_9;
                var8_7 = var7_9;
                var12_15.fragment = var10_14 /* !! */ .getString(R.styleable.PreferenceHeader_android_fragment);
                var6_8 = var7_9;
                var5_6 = var7_9;
                var8_7 = var7_9;
                var10_14 /* !! */ .recycle();
                var10_14 /* !! */  = var9_11;
                if (var9_11 == null) {
                    var6_8 = var7_9;
                    var5_6 = var7_9;
                    var8_7 = var7_9;
                    var10_14 /* !! */  = new Bundle();
                }
                break block33;
lbl180:
                // 1 sources

                var6_8 = var7_9;
                var5_6 = var7_9;
                var8_7 = var7_9;
                XmlUtils.skipCurrentTag((XmlPullParser)var7_9);
                continue;
lbl185:
                // 2 sources

                if (var7_9 != null) {
                    var7_9.close();
                }
                return;
            }
            var6_8 = var7_9;
            var5_6 = var7_9;
            var8_7 = var7_9;
            var3_12 = var7_9.getDepth();
            while (true) {
                var6_8 = var7_9;
                var5_6 = var7_9;
                var8_7 = var7_9;
                var4_13 = var7_9.next();
                if (var4_13 == 1) break;
                if (var4_13 == 3) {
                    var6_8 = var7_9;
                    var5_6 = var7_9;
                    var8_7 = var7_9;
                    if (var7_9.getDepth() <= var3_12) break;
                }
                if (var4_13 == 3 || var4_13 == 4) continue;
                var6_8 = var7_9;
                var5_6 = var7_9;
                var8_7 = var7_9;
                var9_11 = var7_9.getName();
                var6_8 = var7_9;
                var5_6 = var7_9;
                var8_7 = var7_9;
                if (var9_11.equals("extra")) {
                    var6_8 = var7_9;
                    var5_6 = var7_9;
                    var8_7 = var7_9;
                    this.getResources().parseBundleExtra("extra", var11_10, (Bundle)var10_14 /* !! */ );
                    var6_8 = var7_9;
                    var5_6 = var7_9;
                    var8_7 = var7_9;
                    XmlUtils.skipCurrentTag((XmlPullParser)var7_9);
                    continue;
                }
                var6_8 = var7_9;
                var5_6 = var7_9;
                var8_7 = var7_9;
                if (var9_11.equals("intent")) {
                    var6_8 = var7_9;
                    var5_6 = var7_9;
                    var8_7 = var7_9;
                    var12_15.intent = Intent.parseIntent((Resources)this.getResources(), (XmlPullParser)var7_9, (AttributeSet)var11_10);
                    continue;
                }
                var6_8 = var7_9;
                var5_6 = var7_9;
                var8_7 = var7_9;
                XmlUtils.skipCurrentTag((XmlPullParser)var7_9);
            }
            var6_8 = var7_9;
            var5_6 = var7_9;
            var8_7 = var7_9;
            var9_11 = var10_14 /* !! */ ;
            if (var10_14 /* !! */ .size() > 0) {
                var6_8 = var7_9;
                var5_6 = var7_9;
                var8_7 = var7_9;
                var12_15.fragmentArguments = var10_14 /* !! */ ;
                var9_11 = null;
            }
            var6_8 = var7_9;
            var5_6 = var7_9;
            var8_7 = var7_9;
            var2_2.add(var12_15);
            continue;
            break;
        }
        catch (IOException var2_5) {
            var5_6 = var8_7;
            throw new RuntimeException("Error parsing headers", var2_5);
        }
    }

    public void onBuildHeaders(List<Header> list) {
    }

    public Intent onBuildStartFragmentIntent(String string2, Bundle bundle, @StringRes int n2, int n3) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setClass((Context)this, this.getClass());
        intent.putExtra(EXTRA_SHOW_FRAGMENT, string2);
        intent.putExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS, bundle);
        intent.putExtra(EXTRA_SHOW_FRAGMENT_TITLE, n2);
        intent.putExtra(EXTRA_SHOW_FRAGMENT_SHORT_TITLE, n3);
        intent.putExtra(EXTRA_NO_HEADERS, true);
        return intent;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onCreate(@Nullable Bundle object) {
        int n2;
        String string2;
        int n3;
        block24: {
            Bundle bundle;
            block23: {
                super.onCreate((Bundle)object);
                TypedArray typedArray = this.obtainStyledAttributes(null, R.styleable.PreferenceActivity, R.attr.tic_preferenceActivityStyle, 0);
                n3 = typedArray.getResourceId(R.styleable.PreferenceActivity_android_layout, R.layout.preference_list_content);
                this.mPreferenceHeaderItemResId = typedArray.getResourceId(R.styleable.PreferenceActivity_tic_headerLayout, R.layout.preference_ticwear);
                this.mPreferenceHeaderRemoveEmptyIcon = typedArray.getBoolean(R.styleable.PreferenceActivity_tic_headerRemoveIconIfEmpty, false);
                typedArray.recycle();
                this.setContentView(n3);
                this.mTitleView = (TextView)this.findViewById(16908310);
                this.mListFooter = (FrameLayout)this.findViewById(R.id.list_footer);
                this.mPrefsContainer = (ViewGroup)this.findViewById(R.id.prefs_frame);
                boolean bl2 = this.onIsHidingHeaders() || !this.onIsMultiPane();
                this.mSinglePane = bl2;
                string2 = this.getIntent().getStringExtra(EXTRA_SHOW_FRAGMENT);
                bundle = this.getIntent().getBundleExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS);
                n3 = this.getIntent().getIntExtra(EXTRA_SHOW_FRAGMENT_TITLE, 0);
                n2 = this.getIntent().getIntExtra(EXTRA_SHOW_FRAGMENT_SHORT_TITLE, 0);
                if (object == null) break block23;
                ArrayList arrayList = object.getParcelableArrayList(HEADERS_TAG);
                if (arrayList != null) {
                    this.mHeaders.addAll(arrayList);
                    int n4 = object.getInt(CUR_HEADER_TAG, -1);
                    if (n4 >= 0 && n4 < this.mHeaders.size()) {
                        this.setSelectedHeader(this.mHeaders.get(n4));
                    }
                }
                break block24;
            }
            if (string2 != null && this.mSinglePane) {
                this.switchToHeader(string2, bundle);
                if (n3 != 0) {
                    CharSequence charSequence = this.getText(n3);
                    object = n2 != 0 ? this.getText(n2) : null;
                    this.showBreadCrumbs(charSequence, (CharSequence)object);
                }
            } else {
                this.onBuildHeaders(this.mHeaders);
                if (this.mHeaders.size() > 0 && !this.mSinglePane) {
                    if (string2 == null) {
                        this.switchToHeader(this.onGetInitialHeader());
                    } else {
                        this.switchToHeader(string2, bundle);
                    }
                }
            }
        }
        if (string2 != null && this.mSinglePane) {
            this.findViewById(R.id.headers).setVisibility(8);
            this.mPrefsContainer.setVisibility(0);
            if (n3 != 0) {
                CharSequence charSequence = this.getText(n3);
                object = n2 != 0 ? this.getText(n2) : null;
                this.showBreadCrumbs(charSequence, (CharSequence)object);
            }
        } else if (this.mHeaders.size() > 0) {
            object = new HeaderAdapter((Context)this, this.mHeaders, this.mPreferenceHeaderItemResId, this.mPreferenceHeaderRemoveEmptyIcon);
            ((HeaderAdapter)object).setOnHeaderClickListener(this.mOnHeaderClickListener);
            this.setListAdapter((RecyclerView.Adapter)object);
            if (!this.mSinglePane) {
                ((SelectableAdapter)this.getListAdapter()).setMode(1);
                if (this.mCurHeader != null) {
                    this.setSelectedHeader(this.mCurHeader);
                }
                this.mPrefsContainer.setVisibility(0);
            }
        }
        if (this.mTitleView != null) {
            this.mTitleView.setText(this.getTitle());
        }
        if ((object = this.getIntent()).getBooleanExtra(EXTRA_PREFS_SHOW_BUTTON_BAR, false)) {
            String string3;
            this.findViewById(R.id.button_bar).setVisibility(0);
            Button button = (Button)this.findViewById(R.id.back_button);
            button.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    PreferenceActivity.this.setResult(0);
                    PreferenceActivity.this.finish();
                }
            });
            Button button2 = (Button)this.findViewById(R.id.skip_button);
            button2.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    PreferenceActivity.this.setResult(-1);
                    PreferenceActivity.this.finish();
                }
            });
            this.mNextButton = (Button)this.findViewById(R.id.next_button);
            this.mNextButton.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    PreferenceActivity.this.setResult(-1);
                    PreferenceActivity.this.finish();
                }
            });
            if (object.hasExtra(EXTRA_PREFS_SET_NEXT_TEXT)) {
                string3 = object.getStringExtra(EXTRA_PREFS_SET_NEXT_TEXT);
                if (TextUtils.isEmpty((CharSequence)string3)) {
                    this.mNextButton.setVisibility(8);
                } else {
                    this.mNextButton.setText((CharSequence)string3);
                }
            }
            if (object.hasExtra(EXTRA_PREFS_SET_BACK_TEXT)) {
                string3 = object.getStringExtra(EXTRA_PREFS_SET_BACK_TEXT);
                if (TextUtils.isEmpty((CharSequence)string3)) {
                    button.setVisibility(8);
                } else {
                    button.setText((CharSequence)string3);
                }
            }
            if (object.getBooleanExtra(EXTRA_PREFS_SHOW_SKIP, false)) {
                button2.setVisibility(0);
            }
        }
    }

    @Override
    protected void onDestroy() {
        this.mHandler.removeMessages(2);
        if (this.getListAdapter() instanceof HeaderAdapter) {
            ((HeaderAdapter)this.getListAdapter()).setOnHeaderClickListener(null);
        }
        super.onDestroy();
    }

    public Header onGetInitialHeader() {
        for (int i2 = 0; i2 < this.mHeaders.size(); ++i2) {
            Header header = this.mHeaders.get(i2);
            if (header.fragment == null) continue;
            return header;
        }
        throw new IllegalStateException("Must have at least one header with a fragment");
    }

    public Header onGetNewHeader() {
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean onHeaderClick(Header header, int n2) {
        boolean bl2 = false;
        if (header.fragment != null) {
            if (this.mSinglePane) {
                int n3 = header.breadCrumbTitleRes;
                int n4 = header.breadCrumbShortTitleRes;
                n2 = n3;
                if (n3 == 0) {
                    n2 = header.titleRes;
                    n4 = 0;
                }
                this.startWithFragment(header.fragment, header.fragmentArguments, null, 0, n2, n4);
                return true;
            }
            this.switchToHeader(header);
            return false;
        }
        if (header.intent == null) return bl2;
        this.startActivity(header.intent);
        return true;
    }

    public boolean onIsHidingHeaders() {
        return this.getIntent().getBooleanExtra(EXTRA_NO_HEADERS, false);
    }

    public boolean onIsMultiPane() {
        return this.getResources().getBoolean(R.bool.tic_preferences_prefer_dual_pane);
    }

    @Override
    public boolean onPreferenceStartFragment(PreferenceFragment preferenceFragment, Preference preference) {
        this.startPreferencePanel(preference.getFragment(), preference.getExtras(), preference.getTitleRes(), preference.getTitle(), null, 0);
        return true;
    }

    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
    }

    protected void onResume() {
        super.onResume();
        if (this.getListAdapter() instanceof HeaderAdapter) {
            ((HeaderAdapter)this.getListAdapter()).resetClickEntering();
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.mHeaders.size() > 0) {
            int n2;
            bundle.putParcelableArrayList(HEADERS_TAG, this.mHeaders);
            if (this.mCurHeader != null && (n2 = this.mHeaders.indexOf(this.mCurHeader)) >= 0) {
                bundle.putInt(CUR_HEADER_TAG, n2);
            }
        }
    }

    public void setListFooter(View view) {
        this.mListFooter.removeAllViews();
        this.mListFooter.addView(view, (ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-1, -2));
    }

    public void setParentTitle(CharSequence charSequence, CharSequence charSequence2, View.OnClickListener onClickListener) {
    }

    /*
     * Enabled aggressive block sorting
     */
    void setSelectedHeader(Header header) {
        this.mCurHeader = header;
        if (this.getListAdapter() != null) {
            int n2 = this.mHeaders.indexOf(header);
            if (n2 >= 0) {
                if (((SelectableAdapter)this.getListAdapter()).getSelectedItemCount() != 1 || ((SelectableAdapter)this.getListAdapter()).getSelectedItems().get(0) != n2) {
                    ((SelectableAdapter)this.getListAdapter()).clearSelection();
                    ((SelectableAdapter)this.getListAdapter()).toggleSelection(n2);
                }
            } else {
                ((SelectableAdapter)this.getListAdapter()).clearSelection();
            }
        }
        this.showBreadCrumbs(header);
    }

    public void showBreadCrumbs(CharSequence charSequence, CharSequence charSequence2) {
    }

    void showBreadCrumbs(Header header) {
        if (header != null) {
            CharSequence charSequence;
            CharSequence charSequence2 = charSequence = header.getBreadCrumbTitle(this.getResources());
            if (charSequence == null) {
                charSequence2 = header.getTitle(this.getResources());
            }
            charSequence = charSequence2;
            if (charSequence2 == null) {
                charSequence = this.getTitle();
            }
            this.showBreadCrumbs(charSequence, header.getBreadCrumbShortTitle(this.getResources()));
            return;
        }
        this.showBreadCrumbs(this.getTitle(), null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void startPreferenceFragment(Fragment fragment, boolean bl2) {
        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.prefs, fragment);
        if (bl2) {
            fragmentTransaction.setTransition(4097);
            fragmentTransaction.addToBackStack(BACK_STACK_PREFS);
        } else {
            fragmentTransaction.setTransition(4099);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void startPreferencePanel(String string2, Bundle bundle, @StringRes int n2, CharSequence charSequence, Fragment fragment, int n3) {
        if (this.mSinglePane) {
            this.startWithFragment(string2, bundle, fragment, n3, n2, 0);
            return;
        }
        string2 = Fragment.instantiate((Context)this, (String)string2, (Bundle)bundle);
        if (fragment != null) {
            string2.setTargetFragment(fragment, n3);
        }
        bundle = this.getFragmentManager().beginTransaction();
        bundle.replace(R.id.prefs, (Fragment)string2);
        if (n2 != 0) {
            bundle.setBreadCrumbTitle(n2);
        } else if (charSequence != null) {
            bundle.setBreadCrumbTitle(charSequence);
        }
        bundle.setTransition(4097);
        bundle.addToBackStack(BACK_STACK_PREFS);
        bundle.commitAllowingStateLoss();
    }

    public void startWithFragment(String string2, Bundle bundle, Fragment fragment, int n2) {
        this.startWithFragment(string2, bundle, fragment, n2, 0, 0);
    }

    public void startWithFragment(String string2, Bundle bundle, Fragment fragment, int n2, @StringRes int n3, @StringRes int n4) {
        string2 = this.onBuildStartFragmentIntent(string2, bundle, n3, n4);
        if (fragment == null) {
            this.startActivity((Intent)string2);
            return;
        }
        fragment.startActivityForResult((Intent)string2, n2);
    }

    public void switchToHeader(String string2, Bundle bundle) {
        Header header = null;
        int n2 = 0;
        while (true) {
            block4: {
                Header header2;
                block3: {
                    header2 = header;
                    if (n2 >= this.mHeaders.size()) break block3;
                    if (!string2.equals(this.mHeaders.get((int)n2).fragment)) break block4;
                    header2 = this.mHeaders.get(n2);
                }
                this.setSelectedHeader(header2);
                this.switchToHeaderInner(string2, bundle);
                return;
            }
            ++n2;
        }
    }

    public void switchToHeader(Header header) {
        if (this.mCurHeader == header) {
            this.getFragmentManager().popBackStack(BACK_STACK_PREFS, 1);
            return;
        }
        if (header.fragment == null) {
            throw new IllegalStateException("can't switch to header that has no fragment");
        }
        this.switchToHeaderInner(header.fragment, header.fragmentArguments);
        this.setSelectedHeader(header);
    }

    public static final class Header
    implements Parcelable {
        public static final Parcelable.Creator<Header> CREATOR = new Parcelable.Creator<Header>(){

            public Header createFromParcel(Parcel parcel) {
                return new Header(parcel);
            }

            public Header[] newArray(int n2) {
                return new Header[n2];
            }
        };
        public CharSequence breadCrumbShortTitle;
        @StringRes
        public int breadCrumbShortTitleRes;
        public CharSequence breadCrumbTitle;
        @StringRes
        public int breadCrumbTitleRes;
        public Bundle extras;
        public String fragment;
        public Bundle fragmentArguments;
        public int iconRes;
        public long id = -1L;
        public Intent intent;
        public CharSequence summary;
        @StringRes
        public int summaryRes;
        public CharSequence title;
        @StringRes
        public int titleRes;

        public Header() {
        }

        Header(Parcel parcel) {
            this.readFromParcel(parcel);
        }

        public int describeContents() {
            return 0;
        }

        public CharSequence getBreadCrumbShortTitle(Resources resources) {
            if (this.breadCrumbShortTitleRes != 0) {
                return resources.getText(this.breadCrumbShortTitleRes);
            }
            return this.breadCrumbShortTitle;
        }

        public CharSequence getBreadCrumbTitle(Resources resources) {
            if (this.breadCrumbTitleRes != 0) {
                return resources.getText(this.breadCrumbTitleRes);
            }
            return this.breadCrumbTitle;
        }

        public CharSequence getSummary(Resources resources) {
            if (this.summaryRes != 0) {
                return resources.getText(this.summaryRes);
            }
            return this.summary;
        }

        public CharSequence getTitle(Resources resources) {
            if (this.titleRes != 0) {
                return resources.getText(this.titleRes);
            }
            return this.title;
        }

        public void readFromParcel(Parcel parcel) {
            this.id = parcel.readLong();
            this.titleRes = parcel.readInt();
            this.title = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.summaryRes = parcel.readInt();
            this.summary = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.breadCrumbTitleRes = parcel.readInt();
            this.breadCrumbTitle = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.breadCrumbShortTitleRes = parcel.readInt();
            this.breadCrumbShortTitle = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.iconRes = parcel.readInt();
            this.fragment = parcel.readString();
            this.fragmentArguments = parcel.readBundle();
            if (parcel.readInt() != 0) {
                this.intent = (Intent)Intent.CREATOR.createFromParcel(parcel);
            }
            this.extras = parcel.readBundle();
        }

        /*
         * Enabled aggressive block sorting
         */
        public void writeToParcel(Parcel parcel, int n2) {
            parcel.writeLong(this.id);
            parcel.writeInt(this.titleRes);
            TextUtils.writeToParcel((CharSequence)this.title, (Parcel)parcel, (int)n2);
            parcel.writeInt(this.summaryRes);
            TextUtils.writeToParcel((CharSequence)this.summary, (Parcel)parcel, (int)n2);
            parcel.writeInt(this.breadCrumbTitleRes);
            TextUtils.writeToParcel((CharSequence)this.breadCrumbTitle, (Parcel)parcel, (int)n2);
            parcel.writeInt(this.breadCrumbShortTitleRes);
            TextUtils.writeToParcel((CharSequence)this.breadCrumbShortTitle, (Parcel)parcel, (int)n2);
            parcel.writeInt(this.iconRes);
            parcel.writeString(this.fragment);
            parcel.writeBundle(this.fragmentArguments);
            if (this.intent != null) {
                parcel.writeInt(1);
                this.intent.writeToParcel(parcel, n2);
            } else {
                parcel.writeInt(0);
            }
            parcel.writeBundle(this.extras);
        }
    }

    private static class HeaderAdapter
    extends SelectableAdapter<ViewHolder> {
        private boolean mClickEntering = false;
        private final Context mContext;
        private final List<Header> mHeaders;
        private final int mLayoutResId;
        private OnHeaderClickListener mOnHeaderClickListener;
        private final boolean mRemoveIconIfEmpty;

        public HeaderAdapter(Context context, List<Header> list, int n2, boolean bl2) {
            this.mContext = context;
            this.mLayoutResId = n2;
            this.mRemoveIconIfEmpty = bl2;
            this.mHeaders = list;
            this.setHasStableIds(true);
            this.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver(){

                private void onDataChanged() {
                    HeaderAdapter.this.resetClickEntering();
                }

                @Override
                public void onChanged() {
                    this.onDataChanged();
                }

                @Override
                public void onItemRangeChanged(int n2, int n3) {
                    this.onDataChanged();
                }

                @Override
                public void onItemRangeInserted(int n2, int n3) {
                    this.onDataChanged();
                }

                @Override
                public void onItemRangeMoved(int n2, int n3, int n4) {
                    this.onDataChanged();
                }

                @Override
                public void onItemRangeRemoved(int n2, int n3) {
                    this.onDataChanged();
                }
            });
        }

        static /* synthetic */ boolean access$302(HeaderAdapter headerAdapter, boolean bl2) {
            headerAdapter.mClickEntering = bl2;
            return bl2;
        }

        public Header getItem(int n2) {
            return this.mHeaders.get(n2);
        }

        @Override
        public int getItemCount() {
            return this.mHeaders.size();
        }

        @Override
        public long getItemId(int n2) {
            return n2;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int n2) {
            Header header = this.getItem(n2);
            PreferenceViewHolder.PreferenceData preferenceData = new PreferenceViewHolder.PreferenceData();
            preferenceData.title = header.getTitle(this.mContext.getResources());
            preferenceData.summary = header.getSummary(this.mContext.getResources());
            header = header.iconRes == 0 ? null : this.mContext.getDrawable(header.iconRes);
            preferenceData.icon = header;
            preferenceData.removeIconIfEmpty = this.mRemoveIconIfEmpty;
            viewHolder.bind(preferenceData);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int n2) {
            return new ViewHolder(viewGroup, this.mLayoutResId);
        }

        public void resetClickEntering() {
            this.mClickEntering = false;
        }

        public void setOnHeaderClickListener(OnHeaderClickListener onHeaderClickListener) {
            this.mOnHeaderClickListener = onHeaderClickListener;
        }

        protected static interface OnHeaderClickListener {
            public boolean onHeaderClick(Header var1, int var2);
        }

        protected class ViewHolder
        extends PreferenceViewHolder
        implements View.OnClickListener {
            public ViewHolder(@LayoutRes ViewGroup viewGroup, int n2) {
                super(viewGroup, n2);
                this.itemView.setOnClickListener((View.OnClickListener)this);
            }

            public void onClick(View object) {
                if (HeaderAdapter.this.mOnHeaderClickListener != null && !HeaderAdapter.this.mClickEntering) {
                    int n2 = this.getAdapterPosition();
                    object = HeaderAdapter.this.getItem(n2);
                    if (HeaderAdapter.this.mOnHeaderClickListener.onHeaderClick((Header)object, n2)) {
                        HeaderAdapter.access$302(HeaderAdapter.this, true);
                    }
                }
            }
        }
    }
}

