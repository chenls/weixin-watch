/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.Cursor
 *  android.net.Uri
 *  android.view.View
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package android.support.v4.widget;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.ResourceCursorAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SimpleCursorAdapter
extends ResourceCursorAdapter {
    private CursorToStringConverter mCursorToStringConverter;
    protected int[] mFrom;
    String[] mOriginalFrom;
    private int mStringConversionColumn = -1;
    protected int[] mTo;
    private ViewBinder mViewBinder;

    @Deprecated
    public SimpleCursorAdapter(Context context, int n2, Cursor cursor, String[] stringArray, int[] nArray) {
        super(context, n2, cursor);
        this.mTo = nArray;
        this.mOriginalFrom = stringArray;
        this.findColumns(cursor, stringArray);
    }

    public SimpleCursorAdapter(Context context, int n2, Cursor cursor, String[] stringArray, int[] nArray, int n3) {
        super(context, n2, cursor, n3);
        this.mTo = nArray;
        this.mOriginalFrom = stringArray;
        this.findColumns(cursor, stringArray);
    }

    private void findColumns(Cursor cursor, String[] stringArray) {
        if (cursor != null) {
            int n2 = stringArray.length;
            if (this.mFrom == null || this.mFrom.length != n2) {
                this.mFrom = new int[n2];
            }
            for (int i2 = 0; i2 < n2; ++i2) {
                this.mFrom[i2] = cursor.getColumnIndexOrThrow(stringArray[i2]);
            }
        } else {
            this.mFrom = null;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public void bindView(View view, Context object, Cursor cursor) {
        ViewBinder viewBinder = this.mViewBinder;
        int n2 = this.mTo.length;
        int[] nArray = this.mFrom;
        int[] nArray2 = this.mTo;
        int n3 = 0;
        while (n3 < n2) {
            View view2 = view.findViewById(nArray2[n3]);
            if (view2 != null) {
                void var3_7;
                boolean bl2 = false;
                if (viewBinder != null) {
                    bl2 = viewBinder.setViewValue(view2, (Cursor)var3_7, nArray[n3]);
                }
                if (!bl2) {
                    void var2_6;
                    String string2;
                    String string3 = string2 = var3_7.getString(nArray[n3]);
                    if (string2 == null) {
                        String string4 = "";
                    }
                    if (view2 instanceof TextView) {
                        this.setViewText((TextView)view2, (String)var2_6);
                    } else {
                        if (!(view2 instanceof ImageView)) {
                            throw new IllegalStateException(view2.getClass().getName() + " is not a " + " view that can be bounds by this SimpleCursorAdapter");
                        }
                        this.setViewImage((ImageView)view2, (String)var2_6);
                    }
                }
            }
            ++n3;
        }
        return;
    }

    public void changeCursorAndColumns(Cursor cursor, String[] stringArray, int[] nArray) {
        this.mOriginalFrom = stringArray;
        this.mTo = nArray;
        this.findColumns(cursor, this.mOriginalFrom);
        super.changeCursor(cursor);
    }

    @Override
    public CharSequence convertToString(Cursor cursor) {
        if (this.mCursorToStringConverter != null) {
            return this.mCursorToStringConverter.convertToString(cursor);
        }
        if (this.mStringConversionColumn > -1) {
            return cursor.getString(this.mStringConversionColumn);
        }
        return super.convertToString(cursor);
    }

    public CursorToStringConverter getCursorToStringConverter() {
        return this.mCursorToStringConverter;
    }

    public int getStringConversionColumn() {
        return this.mStringConversionColumn;
    }

    public ViewBinder getViewBinder() {
        return this.mViewBinder;
    }

    public void setCursorToStringConverter(CursorToStringConverter cursorToStringConverter) {
        this.mCursorToStringConverter = cursorToStringConverter;
    }

    public void setStringConversionColumn(int n2) {
        this.mStringConversionColumn = n2;
    }

    public void setViewBinder(ViewBinder viewBinder) {
        this.mViewBinder = viewBinder;
    }

    public void setViewImage(ImageView imageView, String string2) {
        try {
            imageView.setImageResource(Integer.parseInt(string2));
            return;
        }
        catch (NumberFormatException numberFormatException) {
            imageView.setImageURI(Uri.parse((String)string2));
            return;
        }
    }

    public void setViewText(TextView textView, String string2) {
        textView.setText((CharSequence)string2);
    }

    @Override
    public Cursor swapCursor(Cursor cursor) {
        this.findColumns(cursor, this.mOriginalFrom);
        return super.swapCursor(cursor);
    }

    public static interface CursorToStringConverter {
        public CharSequence convertToString(Cursor var1);
    }

    public static interface ViewBinder {
        public boolean setViewValue(View var1, Cursor var2, int var3);
    }
}

