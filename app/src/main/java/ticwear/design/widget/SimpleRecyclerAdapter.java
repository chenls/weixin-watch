/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.Checkable
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package ticwear.design.widget;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import java.util.Map;
import ticwear.design.DesignConfig;
import ticwear.design.widget.FocusableLinearLayoutManager;

public class SimpleRecyclerAdapter
extends RecyclerView.Adapter<ViewHolder> {
    static final String TAG = "SimpleRA";
    private List<? extends Map<String, ?>> mData;
    private String[] mFrom;
    private final LayoutInflater mInflater;
    private int mResource;
    private int[] mTo;
    private ViewBinder mViewBinder;
    private ViewHolderCreator mViewHolderCreator;

    public SimpleRecyclerAdapter(Context context, List<? extends Map<String, ?>> list, @LayoutRes int n2, String[] stringArray, @IdRes int[] nArray) {
        this.mData = list;
        this.mResource = n2;
        this.mFrom = stringArray;
        this.mTo = nArray;
        this.mInflater = (LayoutInflater)context.getSystemService("layout_inflater");
        this.setHasStableIds(true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void bindView(ViewHolder clazz, int n2) {
        Map<String, ?> map = this.mData.get(n2);
        if (map == null) {
            return;
        }
        if (DesignConfig.DEBUG_RECYCLER_VIEW) {
            Log.v((String)TAG, (String)(((FocusableLinearLayoutManager.ViewHolder)((Object)clazz)).getLogPrefix() + "bind to " + map + ((FocusableLinearLayoutManager.ViewHolder)((Object)clazz)).getLogSuffix()));
        }
        ViewHolder.access$002(clazz, map);
        ViewBinder viewBinder = this.mViewBinder;
        String[] stringArray = this.mFrom;
        int n3 = this.mTo.length;
        n2 = 0;
        while (n2 < n3) {
            block16: {
                String string2;
                Object obj;
                View view;
                block17: {
                    view = ((ViewHolder)clazz).views[n2];
                    if (view == null) break block16;
                    obj = map.get(stringArray[n2]);
                    CharSequence charSequence = obj == null ? "" : obj.toString();
                    string2 = charSequence;
                    if (charSequence == null) {
                        string2 = "";
                    }
                    boolean bl2 = false;
                    if (viewBinder != null) {
                        bl2 = viewBinder.setViewValue(view, obj, string2);
                    }
                    if (bl2) break block16;
                    if (!(view instanceof Checkable)) break block17;
                    if (obj instanceof Boolean) {
                        ((Checkable)view).setChecked(((Boolean)obj).booleanValue());
                        break block16;
                    } else if (view instanceof TextView) {
                        this.setViewText((TextView)view, string2);
                        break block16;
                    } else {
                        charSequence = new StringBuilder().append(view.getClass().getName()).append(" should be bound to a Boolean, not a ");
                        if (obj == null) {
                            clazz = "<unknown type>";
                            throw new IllegalStateException(((StringBuilder)charSequence).append(clazz).toString());
                        }
                        clazz = obj.getClass();
                        throw new IllegalStateException(((StringBuilder)charSequence).append(clazz).toString());
                    }
                }
                if (view instanceof TextView) {
                    if (obj instanceof Integer) {
                        this.setViewText((TextView)view, (Integer)obj);
                    } else {
                        this.setViewText((TextView)view, string2);
                    }
                } else {
                    if (!(view instanceof ImageView)) throw new IllegalStateException(view.getClass().getName() + " is not a " + " view that can be bounds by this SimpleAdapter");
                    if (obj instanceof Integer) {
                        this.setViewImage((ImageView)view, (Integer)obj);
                    } else {
                        this.setViewImage((ImageView)view, string2);
                    }
                }
            }
            ++n2;
        }
    }

    protected View createViewFromResource(LayoutInflater layoutInflater, ViewGroup viewGroup, int n2) {
        return layoutInflater.inflate(n2, viewGroup, false);
    }

    public Object getItem(int n2) {
        return this.mData.get(n2);
    }

    @Override
    public int getItemCount() {
        return this.mData.size();
    }

    @Override
    public long getItemId(int n2) {
        return n2;
    }

    public ViewBinder getViewBinder() {
        return this.mViewBinder;
    }

    public ViewHolderCreator getViewHolderCreator() {
        return this.mViewHolderCreator;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int n2) {
        this.bindView(viewHolder, n2);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int n2) {
        viewGroup = this.createViewFromResource(this.mInflater, viewGroup, this.mResource);
        if (this.mViewHolderCreator != null) {
            return this.mViewHolderCreator.create((View)viewGroup, this.mTo);
        }
        return new ViewHolder((View)viewGroup, this.mTo);
    }

    public void setViewBinder(ViewBinder viewBinder) {
        this.mViewBinder = viewBinder;
    }

    public void setViewHolderCreator(ViewHolderCreator viewHolderCreator) {
        this.mViewHolderCreator = viewHolderCreator;
    }

    public void setViewImage(ImageView imageView, int n2) {
        imageView.setImageResource(n2);
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

    public void setViewText(TextView textView, int n2) {
        textView.setText(n2);
    }

    public void setViewText(TextView textView, String string2) {
        textView.setText((CharSequence)string2);
    }

    public static interface ViewBinder {
        public boolean setViewValue(View var1, Object var2, String var3);
    }

    public static class ViewHolder
    extends FocusableLinearLayoutManager.ViewHolder {
        private Map dataSet;
        private final View[] views;

        public ViewHolder(View view, int[] nArray) {
            super(view);
            int n2 = nArray.length;
            this.views = new View[n2];
            for (int i2 = 0; i2 < n2; ++i2) {
                this.views[i2] = view.findViewById(nArray[i2]);
            }
        }

        static /* synthetic */ Map access$002(ViewHolder viewHolder, Map map) {
            viewHolder.dataSet = map;
            return map;
        }

        protected Object getBindingData(String string2) {
            return this.dataSet.get(string2);
        }
    }

    public static interface ViewHolderCreator {
        public ViewHolder create(View var1, int[] var2);
    }
}

