/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnClickListener
 *  android.content.DialogInterface$OnDismissListener
 *  android.content.DialogInterface$OnKeyListener
 *  android.content.DialogInterface$OnMultiChoiceClickListener
 *  android.database.Cursor
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.Message
 *  android.text.TextUtils
 *  android.util.TypedValue
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.ViewStub
 *  android.view.Window
 *  android.widget.AbsListView
 *  android.widget.AbsListView$OnScrollListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.ArrayAdapter
 *  android.widget.Button
 *  android.widget.CheckedTextView
 *  android.widget.CursorAdapter
 *  android.widget.FrameLayout
 *  android.widget.ImageView
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.SimpleCursorAdapter
 *  android.widget.TextView
 */
package android.support.v7.app;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.appcompat.R;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import java.lang.ref.WeakReference;

class AlertController {
    private ListAdapter mAdapter;
    private int mAlertDialogLayout;
    private final View.OnClickListener mButtonHandler = new View.OnClickListener(){

        /*
         * Enabled aggressive block sorting
         */
        public void onClick(View object) {
            if ((object = object == AlertController.this.mButtonPositive && AlertController.this.mButtonPositiveMessage != null ? Message.obtain((Message)AlertController.this.mButtonPositiveMessage) : (object == AlertController.this.mButtonNegative && AlertController.this.mButtonNegativeMessage != null ? Message.obtain((Message)AlertController.this.mButtonNegativeMessage) : (object == AlertController.this.mButtonNeutral && AlertController.this.mButtonNeutralMessage != null ? Message.obtain((Message)AlertController.this.mButtonNeutralMessage) : null))) != null) {
                object.sendToTarget();
            }
            AlertController.this.mHandler.obtainMessage(1, (Object)AlertController.this.mDialog).sendToTarget();
        }
    };
    private Button mButtonNegative;
    private Message mButtonNegativeMessage;
    private CharSequence mButtonNegativeText;
    private Button mButtonNeutral;
    private Message mButtonNeutralMessage;
    private CharSequence mButtonNeutralText;
    private int mButtonPanelLayoutHint = 0;
    private int mButtonPanelSideLayout;
    private Button mButtonPositive;
    private Message mButtonPositiveMessage;
    private CharSequence mButtonPositiveText;
    private int mCheckedItem = -1;
    private final Context mContext;
    private View mCustomTitleView;
    private final AppCompatDialog mDialog;
    private Handler mHandler;
    private Drawable mIcon;
    private int mIconId = 0;
    private ImageView mIconView;
    private int mListItemLayout;
    private int mListLayout;
    private ListView mListView;
    private CharSequence mMessage;
    private TextView mMessageView;
    private int mMultiChoiceItemLayout;
    private NestedScrollView mScrollView;
    private int mSingleChoiceItemLayout;
    private CharSequence mTitle;
    private TextView mTitleView;
    private View mView;
    private int mViewLayoutResId;
    private int mViewSpacingBottom;
    private int mViewSpacingLeft;
    private int mViewSpacingRight;
    private boolean mViewSpacingSpecified = false;
    private int mViewSpacingTop;
    private final Window mWindow;

    public AlertController(Context context, AppCompatDialog appCompatDialog, Window window) {
        this.mContext = context;
        this.mDialog = appCompatDialog;
        this.mWindow = window;
        this.mHandler = new ButtonHandler((DialogInterface)appCompatDialog);
        context = context.obtainStyledAttributes(null, R.styleable.AlertDialog, R.attr.alertDialogStyle, 0);
        this.mAlertDialogLayout = context.getResourceId(R.styleable.AlertDialog_android_layout, 0);
        this.mButtonPanelSideLayout = context.getResourceId(R.styleable.AlertDialog_buttonPanelSideLayout, 0);
        this.mListLayout = context.getResourceId(R.styleable.AlertDialog_listLayout, 0);
        this.mMultiChoiceItemLayout = context.getResourceId(R.styleable.AlertDialog_multiChoiceItemLayout, 0);
        this.mSingleChoiceItemLayout = context.getResourceId(R.styleable.AlertDialog_singleChoiceItemLayout, 0);
        this.mListItemLayout = context.getResourceId(R.styleable.AlertDialog_listItemLayout, 0);
        context.recycle();
        appCompatDialog.supportRequestWindowFeature(1);
    }

    static /* synthetic */ ListView access$1002(AlertController alertController, ListView listView) {
        alertController.mListView = listView;
        return listView;
    }

    static /* synthetic */ ListAdapter access$1502(AlertController alertController, ListAdapter listAdapter) {
        alertController.mAdapter = listAdapter;
        return listAdapter;
    }

    static /* synthetic */ int access$1602(AlertController alertController, int n2) {
        alertController.mCheckedItem = n2;
        return n2;
    }

    static boolean canTextInput(View view) {
        if (view.onCheckIsTextEditor()) {
            return true;
        }
        if (!(view instanceof ViewGroup)) {
            return false;
        }
        view = (ViewGroup)view;
        int n2 = view.getChildCount();
        while (n2 > 0) {
            int n3;
            n2 = n3 = n2 - 1;
            if (!AlertController.canTextInput(view.getChildAt(n3))) continue;
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void manageScrollIndicators(View view, View view2, View view3) {
        int n2;
        int n3 = 0;
        if (view2 != null) {
            n2 = ViewCompat.canScrollVertically(view, -1) ? 0 : 4;
            view2.setVisibility(n2);
        }
        if (view3 != null) {
            n2 = ViewCompat.canScrollVertically(view, 1) ? n3 : 4;
            view3.setVisibility(n2);
        }
    }

    @Nullable
    private ViewGroup resolvePanel(@Nullable View view, @Nullable View view2) {
        ViewParent viewParent;
        if (view == null) {
            view = view2;
            if (view2 instanceof ViewStub) {
                view = ((ViewStub)view2).inflate();
            }
            return (ViewGroup)view;
        }
        if (view2 != null && (viewParent = view2.getParent()) instanceof ViewGroup) {
            ((ViewGroup)viewParent).removeView(view2);
        }
        view2 = view;
        if (view instanceof ViewStub) {
            view2 = ((ViewStub)view).inflate();
        }
        return (ViewGroup)view2;
    }

    private int selectContentView() {
        if (this.mButtonPanelSideLayout == 0) {
            return this.mAlertDialogLayout;
        }
        if (this.mButtonPanelLayoutHint == 1) {
            return this.mButtonPanelSideLayout;
        }
        return this.mAlertDialogLayout;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setScrollIndicators(ViewGroup viewGroup, final View view, int n2, int n3) {
        final View view2 = this.mWindow.findViewById(R.id.scrollIndicatorUp);
        View view3 = this.mWindow.findViewById(R.id.scrollIndicatorDown);
        if (Build.VERSION.SDK_INT >= 23) {
            ViewCompat.setScrollIndicators(view, n2, n3);
            if (view2 != null) {
                viewGroup.removeView(view2);
            }
            if (view3 == null) return;
            viewGroup.removeView(view3);
            return;
        }
        view = view2;
        if (view2 != null) {
            view = view2;
            if ((n2 & 1) == 0) {
                viewGroup.removeView(view2);
                view = null;
            }
        }
        view2 = view3;
        if (view3 != null) {
            view2 = view3;
            if ((n2 & 2) == 0) {
                viewGroup.removeView(view3);
                view2 = null;
            }
        }
        if (view == null && view2 == null) return;
        if (this.mMessage != null) {
            this.mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener(){

                @Override
                public void onScrollChange(NestedScrollView nestedScrollView, int n2, int n3, int n4, int n5) {
                    AlertController.manageScrollIndicators((View)nestedScrollView, view, view2);
                }
            });
            this.mScrollView.post(new Runnable(){

                @Override
                public void run() {
                    AlertController.manageScrollIndicators((View)AlertController.this.mScrollView, view, view2);
                }
            });
            return;
        }
        if (this.mListView != null) {
            this.mListView.setOnScrollListener(new AbsListView.OnScrollListener(){

                public void onScroll(AbsListView absListView, int n2, int n3, int n4) {
                    AlertController.manageScrollIndicators((View)absListView, view, view2);
                }

                public void onScrollStateChanged(AbsListView absListView, int n2) {
                }
            });
            this.mListView.post(new Runnable(){

                @Override
                public void run() {
                    AlertController.manageScrollIndicators((View)AlertController.this.mListView, view, view2);
                }
            });
            return;
        }
        if (view != null) {
            viewGroup.removeView(view);
        }
        if (view2 == null) return;
        viewGroup.removeView(view2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setupButtons(ViewGroup viewGroup) {
        boolean bl2 = false;
        int n2 = 0;
        this.mButtonPositive = (Button)viewGroup.findViewById(16908313);
        this.mButtonPositive.setOnClickListener(this.mButtonHandler);
        if (TextUtils.isEmpty((CharSequence)this.mButtonPositiveText)) {
            this.mButtonPositive.setVisibility(8);
        } else {
            this.mButtonPositive.setText(this.mButtonPositiveText);
            this.mButtonPositive.setVisibility(0);
            n2 = 0 | 1;
        }
        this.mButtonNegative = (Button)viewGroup.findViewById(16908314);
        this.mButtonNegative.setOnClickListener(this.mButtonHandler);
        if (TextUtils.isEmpty((CharSequence)this.mButtonNegativeText)) {
            this.mButtonNegative.setVisibility(8);
        } else {
            this.mButtonNegative.setText(this.mButtonNegativeText);
            this.mButtonNegative.setVisibility(0);
            n2 |= 2;
        }
        this.mButtonNeutral = (Button)viewGroup.findViewById(16908315);
        this.mButtonNeutral.setOnClickListener(this.mButtonHandler);
        if (TextUtils.isEmpty((CharSequence)this.mButtonNeutralText)) {
            this.mButtonNeutral.setVisibility(8);
        } else {
            this.mButtonNeutral.setText(this.mButtonNeutralText);
            this.mButtonNeutral.setVisibility(0);
            n2 |= 4;
        }
        if (n2 != 0) {
            return;
        }
        if (bl2) return;
        viewGroup.setVisibility(8);
    }

    private void setupContent(ViewGroup viewGroup) {
        this.mScrollView = (NestedScrollView)this.mWindow.findViewById(R.id.scrollView);
        this.mScrollView.setFocusable(false);
        this.mScrollView.setNestedScrollingEnabled(false);
        this.mMessageView = (TextView)viewGroup.findViewById(16908299);
        if (this.mMessageView == null) {
            return;
        }
        if (this.mMessage != null) {
            this.mMessageView.setText(this.mMessage);
            return;
        }
        this.mMessageView.setVisibility(8);
        this.mScrollView.removeView((View)this.mMessageView);
        if (this.mListView != null) {
            viewGroup = (ViewGroup)this.mScrollView.getParent();
            int n2 = viewGroup.indexOfChild((View)this.mScrollView);
            viewGroup.removeViewAt(n2);
            viewGroup.addView((View)this.mListView, n2, new ViewGroup.LayoutParams(-1, -1));
            return;
        }
        viewGroup.setVisibility(8);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setupCustomContent(ViewGroup viewGroup) {
        boolean bl2 = false;
        Object object = this.mView != null ? this.mView : (this.mViewLayoutResId != 0 ? LayoutInflater.from((Context)this.mContext).inflate(this.mViewLayoutResId, viewGroup, false) : null);
        if (object != null) {
            bl2 = true;
        }
        if (!bl2 || !AlertController.canTextInput(object)) {
            this.mWindow.setFlags(131072, 131072);
        }
        if (!bl2) {
            viewGroup.setVisibility(8);
            return;
        }
        FrameLayout frameLayout = (FrameLayout)this.mWindow.findViewById(R.id.custom);
        frameLayout.addView(object, new ViewGroup.LayoutParams(-1, -1));
        if (this.mViewSpacingSpecified) {
            frameLayout.setPadding(this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
        }
        if (this.mListView != null) {
            ((LinearLayout.LayoutParams)viewGroup.getLayoutParams()).weight = 0.0f;
        }
    }

    private void setupTitle(ViewGroup viewGroup) {
        boolean bl2 = false;
        if (this.mCustomTitleView != null) {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -2);
            viewGroup.addView(this.mCustomTitleView, 0, layoutParams);
            this.mWindow.findViewById(R.id.title_template).setVisibility(8);
            return;
        }
        this.mIconView = (ImageView)this.mWindow.findViewById(16908294);
        if (!TextUtils.isEmpty((CharSequence)this.mTitle)) {
            bl2 = true;
        }
        if (bl2) {
            this.mTitleView = (TextView)this.mWindow.findViewById(R.id.alertTitle);
            this.mTitleView.setText(this.mTitle);
            if (this.mIconId != 0) {
                this.mIconView.setImageResource(this.mIconId);
                return;
            }
            if (this.mIcon != null) {
                this.mIconView.setImageDrawable(this.mIcon);
                return;
            }
            this.mTitleView.setPadding(this.mIconView.getPaddingLeft(), this.mIconView.getPaddingTop(), this.mIconView.getPaddingRight(), this.mIconView.getPaddingBottom());
            this.mIconView.setVisibility(8);
            return;
        }
        this.mWindow.findViewById(R.id.title_template).setVisibility(8);
        this.mIconView.setVisibility(8);
        viewGroup.setVisibility(8);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private void setupView() {
        ListView listView;
        View view;
        View view2 = this.mWindow.findViewById(R.id.parentPanel);
        View view3 = view2.findViewById(R.id.topPanel);
        View view4 = view2.findViewById(R.id.contentPanel);
        View view5 = view2.findViewById(R.id.buttonPanel);
        view2 = (ViewGroup)view2.findViewById(R.id.customPanel);
        this.setupCustomContent((ViewGroup)view2);
        View view6 = view2.findViewById(R.id.topPanel);
        View view7 = view2.findViewById(R.id.contentPanel);
        View view8 = view2.findViewById(R.id.buttonPanel);
        view3 = this.resolvePanel(view6, view3);
        view4 = this.resolvePanel(view7, view4);
        ViewGroup viewGroup = this.resolvePanel(view8, view5);
        this.setupContent((ViewGroup)view4);
        this.setupButtons(viewGroup);
        this.setupTitle((ViewGroup)view3);
        int n2 = view2 != null && view2.getVisibility() != 8 ? 1 : 0;
        boolean bl2 = view3 != null && view3.getVisibility() != 8;
        int n3 = viewGroup != null && viewGroup.getVisibility() != 8 ? 1 : 0;
        if (n3 == 0 && view4 != null && (view = view4.findViewById(R.id.textSpacerNoButtons)) != null) {
            view.setVisibility(0);
        }
        if (bl2 && this.mScrollView != null) {
            this.mScrollView.setClipToPadding(true);
        }
        if (n2 == 0) {
            void var4_9;
            if (this.mListView != null) {
                ListView listView2 = this.mListView;
            } else {
                NestedScrollView nestedScrollView = this.mScrollView;
            }
            if (var4_9 != null) {
                n2 = bl2 ? 1 : 0;
                n3 = n3 != 0 ? 2 : 0;
                this.setScrollIndicators((ViewGroup)view4, (View)var4_9, n2 | n3, 3);
            }
        }
        if ((listView = this.mListView) != null && this.mAdapter != null) {
            listView.setAdapter(this.mAdapter);
            n2 = this.mCheckedItem;
            if (n2 > -1) {
                listView.setItemChecked(n2, true);
                listView.setSelection(n2);
            }
        }
    }

    public Button getButton(int n2) {
        switch (n2) {
            default: {
                return null;
            }
            case -1: {
                return this.mButtonPositive;
            }
            case -2: {
                return this.mButtonNegative;
            }
            case -3: 
        }
        return this.mButtonNeutral;
    }

    public int getIconAttributeResId(int n2) {
        TypedValue typedValue = new TypedValue();
        this.mContext.getTheme().resolveAttribute(n2, typedValue, true);
        return typedValue.resourceId;
    }

    public ListView getListView() {
        return this.mListView;
    }

    public void installContent() {
        int n2 = this.selectContentView();
        this.mDialog.setContentView(n2);
        this.setupView();
    }

    public boolean onKeyDown(int n2, KeyEvent keyEvent) {
        return this.mScrollView != null && this.mScrollView.executeKeyEvent(keyEvent);
    }

    public boolean onKeyUp(int n2, KeyEvent keyEvent) {
        return this.mScrollView != null && this.mScrollView.executeKeyEvent(keyEvent);
    }

    public void setButton(int n2, CharSequence charSequence, DialogInterface.OnClickListener onClickListener, Message message) {
        Message message2 = message;
        if (message == null) {
            message2 = message;
            if (onClickListener != null) {
                message2 = this.mHandler.obtainMessage(n2, (Object)onClickListener);
            }
        }
        switch (n2) {
            default: {
                throw new IllegalArgumentException("Button does not exist");
            }
            case -1: {
                this.mButtonPositiveText = charSequence;
                this.mButtonPositiveMessage = message2;
                return;
            }
            case -2: {
                this.mButtonNegativeText = charSequence;
                this.mButtonNegativeMessage = message2;
                return;
            }
            case -3: 
        }
        this.mButtonNeutralText = charSequence;
        this.mButtonNeutralMessage = message2;
    }

    public void setButtonPanelLayoutHint(int n2) {
        this.mButtonPanelLayoutHint = n2;
    }

    public void setCustomTitle(View view) {
        this.mCustomTitleView = view;
    }

    public void setIcon(int n2) {
        block3: {
            block2: {
                this.mIcon = null;
                this.mIconId = n2;
                if (this.mIconView == null) break block2;
                if (n2 == 0) break block3;
                this.mIconView.setVisibility(0);
                this.mIconView.setImageResource(this.mIconId);
            }
            return;
        }
        this.mIconView.setVisibility(8);
    }

    public void setIcon(Drawable drawable2) {
        block3: {
            block2: {
                this.mIcon = drawable2;
                this.mIconId = 0;
                if (this.mIconView == null) break block2;
                if (drawable2 == null) break block3;
                this.mIconView.setVisibility(0);
                this.mIconView.setImageDrawable(drawable2);
            }
            return;
        }
        this.mIconView.setVisibility(8);
    }

    public void setMessage(CharSequence charSequence) {
        this.mMessage = charSequence;
        if (this.mMessageView != null) {
            this.mMessageView.setText(charSequence);
        }
    }

    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        if (this.mTitleView != null) {
            this.mTitleView.setText(charSequence);
        }
    }

    public void setView(int n2) {
        this.mView = null;
        this.mViewLayoutResId = n2;
        this.mViewSpacingSpecified = false;
    }

    public void setView(View view) {
        this.mView = view;
        this.mViewLayoutResId = 0;
        this.mViewSpacingSpecified = false;
    }

    public void setView(View view, int n2, int n3, int n4, int n5) {
        this.mView = view;
        this.mViewLayoutResId = 0;
        this.mViewSpacingSpecified = true;
        this.mViewSpacingLeft = n2;
        this.mViewSpacingTop = n3;
        this.mViewSpacingRight = n4;
        this.mViewSpacingBottom = n5;
    }

    public static class AlertParams {
        public ListAdapter mAdapter;
        public boolean mCancelable;
        public int mCheckedItem = -1;
        public boolean[] mCheckedItems;
        public final Context mContext;
        public Cursor mCursor;
        public View mCustomTitleView;
        public boolean mForceInverseBackground;
        public Drawable mIcon;
        public int mIconAttrId = 0;
        public int mIconId = 0;
        public final LayoutInflater mInflater;
        public String mIsCheckedColumn;
        public boolean mIsMultiChoice;
        public boolean mIsSingleChoice;
        public CharSequence[] mItems;
        public String mLabelColumn;
        public CharSequence mMessage;
        public DialogInterface.OnClickListener mNegativeButtonListener;
        public CharSequence mNegativeButtonText;
        public DialogInterface.OnClickListener mNeutralButtonListener;
        public CharSequence mNeutralButtonText;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnMultiChoiceClickListener mOnCheckboxClickListener;
        public DialogInterface.OnClickListener mOnClickListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public AdapterView.OnItemSelectedListener mOnItemSelectedListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        public OnPrepareListViewListener mOnPrepareListViewListener;
        public DialogInterface.OnClickListener mPositiveButtonListener;
        public CharSequence mPositiveButtonText;
        public boolean mRecycleOnMeasure = true;
        public CharSequence mTitle;
        public View mView;
        public int mViewLayoutResId;
        public int mViewSpacingBottom;
        public int mViewSpacingLeft;
        public int mViewSpacingRight;
        public boolean mViewSpacingSpecified = false;
        public int mViewSpacingTop;

        public AlertParams(Context context) {
            this.mContext = context;
            this.mCancelable = true;
            this.mInflater = (LayoutInflater)context.getSystemService("layout_inflater");
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        private void createListView(final AlertController alertController) {
            void var3_4;
            final ListView listView = (ListView)this.mInflater.inflate(alertController.mListLayout, null);
            if (this.mIsMultiChoice) {
                if (this.mCursor == null) {
                    ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<CharSequence>(this.mContext, alertController.mMultiChoiceItemLayout, 16908308, this.mItems){

                        public View getView(int n2, View view, ViewGroup viewGroup) {
                            view = super.getView(n2, view, viewGroup);
                            if (AlertParams.this.mCheckedItems != null && AlertParams.this.mCheckedItems[n2]) {
                                listView.setItemChecked(n2, true);
                            }
                            return view;
                        }
                    };
                } else {
                    CursorAdapter cursorAdapter = new CursorAdapter(this.mContext, this.mCursor, false){
                        private final int mIsCheckedIndex;
                        private final int mLabelIndex;
                        {
                            super(context, cursor, bl2);
                            AlertParams.this = this.getCursor();
                            this.mLabelIndex = AlertParams.this.getColumnIndexOrThrow(AlertParams.this.mLabelColumn);
                            this.mIsCheckedIndex = AlertParams.this.getColumnIndexOrThrow(AlertParams.this.mIsCheckedColumn);
                        }

                        /*
                         * Enabled aggressive block sorting
                         */
                        public void bindView(View view, Context context, Cursor cursor) {
                            boolean bl2 = true;
                            ((CheckedTextView)view.findViewById(16908308)).setText((CharSequence)cursor.getString(this.mLabelIndex));
                            view = listView;
                            int n2 = cursor.getPosition();
                            if (cursor.getInt(this.mIsCheckedIndex) != 1) {
                                bl2 = false;
                            }
                            view.setItemChecked(n2, bl2);
                        }

                        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
                            return AlertParams.this.mInflater.inflate(alertController.mMultiChoiceItemLayout, viewGroup, false);
                        }
                    };
                }
            } else {
                int n2 = this.mIsSingleChoice ? alertController.mSingleChoiceItemLayout : alertController.mListItemLayout;
                if (this.mCursor != null) {
                    SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this.mContext, n2, this.mCursor, new String[]{this.mLabelColumn}, new int[]{16908308});
                } else if (this.mAdapter != null) {
                    ListAdapter listAdapter = this.mAdapter;
                } else {
                    CheckedItemAdapter checkedItemAdapter = new CheckedItemAdapter(this.mContext, n2, 16908308, this.mItems);
                }
            }
            if (this.mOnPrepareListViewListener != null) {
                this.mOnPrepareListViewListener.onPrepareListView(listView);
            }
            AlertController.access$1502(alertController, (ListAdapter)var3_4);
            AlertController.access$1602(alertController, this.mCheckedItem);
            if (this.mOnClickListener != null) {
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    public void onItemClick(AdapterView<?> adapterView, View view, int n2, long l2) {
                        AlertParams.this.mOnClickListener.onClick((DialogInterface)alertController.mDialog, n2);
                        if (!AlertParams.this.mIsSingleChoice) {
                            alertController.mDialog.dismiss();
                        }
                    }
                });
            } else if (this.mOnCheckboxClickListener != null) {
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    public void onItemClick(AdapterView<?> adapterView, View view, int n2, long l2) {
                        if (AlertParams.this.mCheckedItems != null) {
                            AlertParams.this.mCheckedItems[n2] = listView.isItemChecked(n2);
                        }
                        AlertParams.this.mOnCheckboxClickListener.onClick((DialogInterface)alertController.mDialog, n2, listView.isItemChecked(n2));
                    }
                });
            }
            if (this.mOnItemSelectedListener != null) {
                listView.setOnItemSelectedListener(this.mOnItemSelectedListener);
            }
            if (this.mIsSingleChoice) {
                listView.setChoiceMode(1);
            } else if (this.mIsMultiChoice) {
                listView.setChoiceMode(2);
            }
            AlertController.access$1002(alertController, listView);
        }

        /*
         * Enabled aggressive block sorting
         */
        public void apply(AlertController alertController) {
            if (this.mCustomTitleView != null) {
                alertController.setCustomTitle(this.mCustomTitleView);
            } else {
                if (this.mTitle != null) {
                    alertController.setTitle(this.mTitle);
                }
                if (this.mIcon != null) {
                    alertController.setIcon(this.mIcon);
                }
                if (this.mIconId != 0) {
                    alertController.setIcon(this.mIconId);
                }
                if (this.mIconAttrId != 0) {
                    alertController.setIcon(alertController.getIconAttributeResId(this.mIconAttrId));
                }
            }
            if (this.mMessage != null) {
                alertController.setMessage(this.mMessage);
            }
            if (this.mPositiveButtonText != null) {
                alertController.setButton(-1, this.mPositiveButtonText, this.mPositiveButtonListener, null);
            }
            if (this.mNegativeButtonText != null) {
                alertController.setButton(-2, this.mNegativeButtonText, this.mNegativeButtonListener, null);
            }
            if (this.mNeutralButtonText != null) {
                alertController.setButton(-3, this.mNeutralButtonText, this.mNeutralButtonListener, null);
            }
            if (this.mItems != null || this.mCursor != null || this.mAdapter != null) {
                this.createListView(alertController);
            }
            if (this.mView != null) {
                if (!this.mViewSpacingSpecified) {
                    alertController.setView(this.mView);
                    return;
                }
                alertController.setView(this.mView, this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
                return;
            } else {
                if (this.mViewLayoutResId == 0) return;
                alertController.setView(this.mViewLayoutResId);
                return;
            }
        }

        public static interface OnPrepareListViewListener {
            public void onPrepareListView(ListView var1);
        }
    }

    private static final class ButtonHandler
    extends Handler {
        private static final int MSG_DISMISS_DIALOG = 1;
        private WeakReference<DialogInterface> mDialog;

        public ButtonHandler(DialogInterface dialogInterface) {
            this.mDialog = new WeakReference<DialogInterface>(dialogInterface);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                default: {
                    return;
                }
                case -3: 
                case -2: 
                case -1: {
                    ((DialogInterface.OnClickListener)message.obj).onClick((DialogInterface)this.mDialog.get(), message.what);
                    return;
                }
                case 1: 
            }
            ((DialogInterface)message.obj).dismiss();
        }
    }

    private static class CheckedItemAdapter
    extends ArrayAdapter<CharSequence> {
        public CheckedItemAdapter(Context context, int n2, int n3, CharSequence[] charSequenceArray) {
            super(context, n2, n3, (Object[])charSequenceArray);
        }

        public long getItemId(int n2) {
            return n2;
        }

        public boolean hasStableIds() {
            return true;
        }
    }
}

