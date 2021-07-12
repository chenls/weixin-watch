/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.SearchManager
 *  android.app.SearchableInfo
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.database.Cursor
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.text.SpannableString
 *  android.text.TextUtils
 *  android.text.style.TextAppearanceSpan
 *  android.util.Log
 *  android.util.TypedValue
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package android.support.v7.widget;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ResourceCursorAdapter;
import android.support.v7.appcompat.R;
import android.support.v7.widget.SearchView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.WeakHashMap;

class SuggestionsAdapter
extends ResourceCursorAdapter
implements View.OnClickListener {
    private static final boolean DBG = false;
    static final int INVALID_INDEX = -1;
    private static final String LOG_TAG = "SuggestionsAdapter";
    private static final int QUERY_LIMIT = 50;
    static final int REFINE_ALL = 2;
    static final int REFINE_BY_ENTRY = 1;
    static final int REFINE_NONE = 0;
    private boolean mClosed = false;
    private final int mCommitIconResId;
    private int mFlagsCol = -1;
    private int mIconName1Col = -1;
    private int mIconName2Col = -1;
    private final WeakHashMap<String, Drawable.ConstantState> mOutsideDrawablesCache;
    private final Context mProviderContext;
    private int mQueryRefinement = 1;
    private final SearchManager mSearchManager = (SearchManager)this.mContext.getSystemService("search");
    private final SearchView mSearchView;
    private final SearchableInfo mSearchable;
    private int mText1Col = -1;
    private int mText2Col = -1;
    private int mText2UrlCol = -1;
    private ColorStateList mUrlColor;

    public SuggestionsAdapter(Context context, SearchView searchView, SearchableInfo searchableInfo, WeakHashMap<String, Drawable.ConstantState> weakHashMap) {
        super(context, searchView.getSuggestionRowLayout(), null, true);
        this.mSearchView = searchView;
        this.mSearchable = searchableInfo;
        this.mCommitIconResId = searchView.getSuggestionCommitIconResId();
        this.mProviderContext = context;
        this.mOutsideDrawablesCache = weakHashMap;
    }

    private Drawable checkIconCache(String string2) {
        if ((string2 = this.mOutsideDrawablesCache.get(string2)) == null) {
            return null;
        }
        return string2.newDrawable();
    }

    private CharSequence formatUrl(CharSequence charSequence) {
        TypedValue typedValue;
        if (this.mUrlColor == null) {
            typedValue = new TypedValue();
            this.mContext.getTheme().resolveAttribute(R.attr.textColorSearchUrl, typedValue, true);
            this.mUrlColor = this.mContext.getResources().getColorStateList(typedValue.resourceId);
        }
        typedValue = new SpannableString(charSequence);
        typedValue.setSpan((Object)new TextAppearanceSpan(null, 0, 0, this.mUrlColor, null), 0, charSequence.length(), 33);
        return typedValue;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Drawable getActivityIcon(ComponentName componentName) {
        ActivityInfo activityInfo;
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            activityInfo = packageManager.getActivityInfo(componentName, 128);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.w((String)LOG_TAG, (String)nameNotFoundException.toString());
            return null;
        }
        int n2 = activityInfo.getIconResource();
        if (n2 == 0) {
            return null;
        }
        activityInfo = packageManager.getDrawable(componentName.getPackageName(), n2, activityInfo.applicationInfo);
        packageManager = activityInfo;
        if (activityInfo != null) return packageManager;
        Log.w((String)LOG_TAG, (String)("Invalid icon resource " + n2 + " for " + componentName.flattenToShortString()));
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    private Drawable getActivityIconWithCache(ComponentName object) {
        String string2 = object.flattenToShortString();
        if (this.mOutsideDrawablesCache.containsKey(string2)) {
            object = this.mOutsideDrawablesCache.get(string2);
            if (object == null) {
                return null;
            }
            return object.newDrawable(this.mProviderContext.getResources());
        }
        Drawable drawable2 = this.getActivityIcon((ComponentName)object);
        object = drawable2 == null ? null : drawable2.getConstantState();
        this.mOutsideDrawablesCache.put(string2, (Drawable.ConstantState)object);
        return drawable2;
    }

    public static String getColumnString(Cursor cursor, String string2) {
        return SuggestionsAdapter.getStringOrNull(cursor, cursor.getColumnIndex(string2));
    }

    private Drawable getDefaultIcon1(Cursor cursor) {
        cursor = this.getActivityIconWithCache(this.mSearchable.getSearchActivity());
        if (cursor != null) {
            return cursor;
        }
        return this.mContext.getPackageManager().getDefaultActivityIcon();
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Drawable getDrawable(Uri uri) {
        InputStream inputStream;
        block13: {
            try {
                boolean bl2 = "android.resource".equals(uri.getScheme());
                if (bl2) {
                    try {
                        return this.getDrawableFromResourceUri(uri);
                    }
                    catch (Resources.NotFoundException notFoundException) {
                        throw new FileNotFoundException("Resource does not exist: " + uri);
                    }
                }
                inputStream = this.mProviderContext.getContentResolver().openInputStream(uri);
                if (inputStream != null) break block13;
                throw new FileNotFoundException("Failed to open " + uri);
            }
            catch (FileNotFoundException fileNotFoundException) {
                Log.w((String)LOG_TAG, (String)("Icon not found: " + uri + ", " + fileNotFoundException.getMessage()));
                return null;
            }
        }
        Drawable drawable2 = Drawable.createFromStream((InputStream)inputStream, null);
        {
            catch (Throwable throwable) {
                try {
                    inputStream.close();
                }
                catch (IOException iOException) {
                    Log.e((String)LOG_TAG, (String)("Error closing icon stream for " + uri), (Throwable)iOException);
                    throw throwable;
                }
                throw throwable;
            }
        }
        try {
            inputStream.close();
            return drawable2;
        }
        catch (IOException iOException) {
            Log.e((String)LOG_TAG, (String)("Error closing icon stream for " + uri), (Throwable)iOException);
            return drawable2;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Drawable getDrawableFromResourceValue(String string2) {
        Drawable drawable2;
        if (string2 == null) return null;
        if (string2.length() == 0) return null;
        if ("0".equals(string2)) {
            return null;
        }
        int n2 = Integer.parseInt(string2);
        String string3 = "android.resource://" + this.mProviderContext.getPackageName() + "/" + n2;
        Drawable drawable3 = drawable2 = this.checkIconCache(string3);
        if (drawable2 != null) return drawable3;
        try {
            drawable3 = ContextCompat.getDrawable(this.mProviderContext, n2);
            this.storeInIconCache(string3, drawable3);
            return drawable3;
        }
        catch (NumberFormatException numberFormatException) {
            drawable3 = drawable2 = this.checkIconCache(string2);
            if (drawable2 != null) return drawable3;
            drawable3 = this.getDrawable(Uri.parse((String)string2));
            this.storeInIconCache(string2, drawable3);
            return drawable3;
        }
        catch (Resources.NotFoundException notFoundException) {
            Log.w((String)LOG_TAG, (String)("Icon resource not found: " + string2));
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Drawable getIcon1(Cursor cursor) {
        Drawable drawable2;
        if (this.mIconName1Col == -1) {
            return null;
        }
        Drawable drawable3 = drawable2 = this.getDrawableFromResourceValue(cursor.getString(this.mIconName1Col));
        if (drawable2 != null) return drawable3;
        return this.getDefaultIcon1(cursor);
    }

    private Drawable getIcon2(Cursor cursor) {
        if (this.mIconName2Col == -1) {
            return null;
        }
        return this.getDrawableFromResourceValue(cursor.getString(this.mIconName2Col));
    }

    private static String getStringOrNull(Cursor object, int n2) {
        if (n2 == -1) {
            return null;
        }
        try {
            object = object.getString(n2);
            return object;
        }
        catch (Exception exception) {
            Log.e((String)LOG_TAG, (String)"unexpected error retrieving valid column from cursor, did the remote process die?", (Throwable)exception);
            return null;
        }
    }

    private void setViewDrawable(ImageView imageView, Drawable drawable2, int n2) {
        imageView.setImageDrawable(drawable2);
        if (drawable2 == null) {
            imageView.setVisibility(n2);
            return;
        }
        imageView.setVisibility(0);
        drawable2.setVisible(false, false);
        drawable2.setVisible(true, false);
    }

    private void setViewText(TextView textView, CharSequence charSequence) {
        textView.setText(charSequence);
        if (TextUtils.isEmpty((CharSequence)charSequence)) {
            textView.setVisibility(8);
            return;
        }
        textView.setVisibility(0);
    }

    private void storeInIconCache(String string2, Drawable drawable2) {
        if (drawable2 != null) {
            this.mOutsideDrawablesCache.put(string2, drawable2.getConstantState());
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void updateSpinnerState(Cursor cursor) {
        if (cursor == null) return;
        cursor = cursor.getExtras();
        if (cursor == null) return;
        if (!cursor.getBoolean("in_progress")) return;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public void bindView(View object, Context object2, Cursor cursor) {
        void var3_10;
        ChildViewCache childViewCache = (ChildViewCache)object.getTag();
        int n2 = 0;
        if (this.mFlagsCol != -1) {
            n2 = var3_10.getInt(this.mFlagsCol);
        }
        if (childViewCache.mText1 != null) {
            String string2 = SuggestionsAdapter.getStringOrNull((Cursor)var3_10, this.mText1Col);
            this.setViewText(childViewCache.mText1, string2);
        }
        if (childViewCache.mText2 != null) {
            void var1_6;
            String string3 = SuggestionsAdapter.getStringOrNull((Cursor)var3_10, this.mText2UrlCol);
            if (string3 != null) {
                CharSequence charSequence = this.formatUrl(string3);
            } else {
                String string4 = SuggestionsAdapter.getStringOrNull((Cursor)var3_10, this.mText2Col);
            }
            if (TextUtils.isEmpty((CharSequence)var1_6)) {
                if (childViewCache.mText1 != null) {
                    childViewCache.mText1.setSingleLine(false);
                    childViewCache.mText1.setMaxLines(2);
                }
            } else if (childViewCache.mText1 != null) {
                childViewCache.mText1.setSingleLine(true);
                childViewCache.mText1.setMaxLines(1);
            }
            this.setViewText(childViewCache.mText2, (CharSequence)var1_6);
        }
        if (childViewCache.mIcon1 != null) {
            this.setViewDrawable(childViewCache.mIcon1, this.getIcon1((Cursor)var3_10), 4);
        }
        if (childViewCache.mIcon2 != null) {
            this.setViewDrawable(childViewCache.mIcon2, this.getIcon2((Cursor)var3_10), 8);
        }
        if (this.mQueryRefinement == 2 || this.mQueryRefinement == 1 && (n2 & 1) != 0) {
            childViewCache.mIconRefine.setVisibility(0);
            childViewCache.mIconRefine.setTag((Object)childViewCache.mText1.getText());
            childViewCache.mIconRefine.setOnClickListener((View.OnClickListener)this);
            return;
        }
        childViewCache.mIconRefine.setVisibility(8);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void changeCursor(Cursor cursor) {
        if (this.mClosed) {
            Log.w((String)LOG_TAG, (String)"Tried to change cursor after adapter was closed.");
            if (cursor == null) return;
            cursor.close();
            return;
        }
        try {
            super.changeCursor(cursor);
            if (cursor == null) return;
            this.mText1Col = cursor.getColumnIndex("suggest_text_1");
            this.mText2Col = cursor.getColumnIndex("suggest_text_2");
            this.mText2UrlCol = cursor.getColumnIndex("suggest_text_2_url");
            this.mIconName1Col = cursor.getColumnIndex("suggest_icon_1");
            this.mIconName2Col = cursor.getColumnIndex("suggest_icon_2");
            this.mFlagsCol = cursor.getColumnIndex("suggest_flags");
            return;
        }
        catch (Exception exception) {
            Log.e((String)LOG_TAG, (String)"error changing cursor and caching columns", (Throwable)exception);
            return;
        }
    }

    public void close() {
        this.changeCursor(null);
        this.mClosed = true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public CharSequence convertToString(Cursor object) {
        String string2;
        if (object == null) {
            return null;
        }
        String string3 = string2 = SuggestionsAdapter.getColumnString(object, "suggest_intent_query");
        if (string2 != null) return string3;
        if (this.mSearchable.shouldRewriteQueryFromData() && (string3 = SuggestionsAdapter.getColumnString(object, "suggest_intent_data")) != null) {
            return string3;
        }
        if (!this.mSearchable.shouldRewriteQueryFromText()) return null;
        String string4 = SuggestionsAdapter.getColumnString(object, "suggest_text_1");
        if (string4 == null) return null;
        return string4;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    Drawable getDrawableFromResourceUri(Uri uri) throws FileNotFoundException {
        Resources resources;
        String string2 = uri.getAuthority();
        if (TextUtils.isEmpty((CharSequence)string2)) {
            throw new FileNotFoundException("No authority: " + uri);
        }
        try {
            resources = this.mContext.getPackageManager().getResourcesForApplication(string2);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            throw new FileNotFoundException("No package found for authority: " + uri);
        }
        List list = uri.getPathSegments();
        if (list == null) {
            throw new FileNotFoundException("No path: " + uri);
        }
        int n2 = list.size();
        if (n2 == 1) {
            try {
                n2 = Integer.parseInt((String)list.get(0));
            }
            catch (NumberFormatException numberFormatException) {
                throw new FileNotFoundException("Single path segment is not a resource ID: " + uri);
            }
        } else {
            if (n2 != 2) {
                throw new FileNotFoundException("More than two path segments: " + uri);
            }
            n2 = resources.getIdentifier((String)list.get(1), (String)list.get(0), string2);
        }
        if (n2 == 0) {
            throw new FileNotFoundException("No resource found for: " + uri);
        }
        return resources.getDrawable(n2);
    }

    public int getQueryRefinement() {
        return this.mQueryRefinement;
    }

    /*
     * Enabled aggressive block sorting
     */
    Cursor getSearchManagerSuggestions(SearchableInfo stringArray, String string2, int n2) {
        String string3;
        if (stringArray == null || (string3 = stringArray.getSuggestAuthority()) == null) {
            return null;
        }
        string3 = new Uri.Builder().scheme("content").authority(string3).query("").fragment("");
        String string4 = stringArray.getSuggestPath();
        if (string4 != null) {
            string3.appendEncodedPath(string4);
        }
        string3.appendPath("search_suggest_query");
        string4 = stringArray.getSuggestSelection();
        stringArray = null;
        if (string4 != null) {
            stringArray = new String[]{string2};
        } else {
            string3.appendPath(string2);
        }
        if (n2 > 0) {
            string3.appendQueryParameter("limit", String.valueOf(n2));
        }
        string2 = string3.build();
        return this.mContext.getContentResolver().query((Uri)string2, null, string4, stringArray, null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public View getView(int n2, View view, ViewGroup viewGroup) {
        try {
            return super.getView(n2, view, viewGroup);
        }
        catch (RuntimeException runtimeException) {
            Log.w((String)LOG_TAG, (String)"Search suggestions cursor threw exception.", (Throwable)runtimeException);
            viewGroup = this.newView(this.mContext, this.mCursor, viewGroup);
            view = viewGroup;
            if (viewGroup == null) return view;
            ((ChildViewCache)viewGroup.getTag()).mText1.setText((CharSequence)runtimeException.toString());
            return viewGroup;
        }
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        context = super.newView(context, cursor, viewGroup);
        context.setTag((Object)new ChildViewCache((View)context));
        ((ImageView)context.findViewById(R.id.edit_query)).setImageResource(this.mCommitIconResId);
        return context;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        this.updateSpinnerState(this.getCursor());
    }

    public void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
        this.updateSpinnerState(this.getCursor());
    }

    public void onClick(View object) {
        if ((object = object.getTag()) instanceof CharSequence) {
            this.mSearchView.onQueryRefine((CharSequence)object);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence charSequence) {
        charSequence = charSequence == null ? "" : charSequence.toString();
        if (this.mSearchView.getVisibility() != 0 || this.mSearchView.getWindowVisibility() != 0) return null;
        try {
            if ((charSequence = this.getSearchManagerSuggestions(this.mSearchable, (String)charSequence, 50)) == null) return null;
            charSequence.getCount();
            return charSequence;
        }
        catch (RuntimeException runtimeException) {
            Log.w((String)LOG_TAG, (String)"Search suggestions query threw an exception.", (Throwable)runtimeException);
            return null;
        }
    }

    public void setQueryRefinement(int n2) {
        this.mQueryRefinement = n2;
    }

    private static final class ChildViewCache {
        public final ImageView mIcon1;
        public final ImageView mIcon2;
        public final ImageView mIconRefine;
        public final TextView mText1;
        public final TextView mText2;

        public ChildViewCache(View view) {
            this.mText1 = (TextView)view.findViewById(16908308);
            this.mText2 = (TextView)view.findViewById(16908309);
            this.mIcon1 = (ImageView)view.findViewById(16908295);
            this.mIcon2 = (ImageView)view.findViewById(16908296);
            this.mIconRefine = (ImageView)view.findViewById(R.id.edit_query);
        }
    }
}

