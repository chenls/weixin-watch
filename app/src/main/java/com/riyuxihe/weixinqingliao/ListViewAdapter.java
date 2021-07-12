/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.TextView
 */
package com.riyuxihe.weixinqingliao;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.riyuxihe.weixinqingliao.net.VolleySingleton;
import com.riyuxihe.weixinqingliao.util.StringUtil;
import java.util.HashMap;
import java.util.List;

public class ListViewAdapter
extends BaseAdapter {
    private static final String TAG = "ListViewAdapter";
    private String cookie;
    private ImageLoader imageLoader;
    private Activity mContext;
    private List<HashMap<String, Object>> mData;
    private RequestQueue mQueue;

    public ListViewAdapter(Activity activity, String string2, List<HashMap<String, Object>> list) {
        this.mContext = activity;
        this.cookie = string2;
        this.mData = list;
        this.mQueue = VolleySingleton.getInstance().getRequestQueue();
        this.imageLoader = VolleySingleton.getInstance().getImageLoader(string2);
    }

    public int getCount() {
        return this.mData.size();
    }

    public Object getItem(int n2) {
        return null;
    }

    public long getItemId(int n2) {
        return 0L;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public View getView(int n2, View object, ViewGroup viewGroup) {
        void var2_4;
        Object object2 = object;
        if (object2 == null) {
            object2 = this.mContext.getLayoutInflater().inflate(2130968631, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.title = (TextView)object2.findViewById(2131689560);
            viewHolder.time = (TextView)object2.findViewById(2131689659);
            viewHolder.info = (TextView)object2.findViewById(2131689660);
            viewHolder.imageView = (NetworkImageView)object2.findViewById(2131689658);
            object2.setTag((Object)viewHolder);
        } else {
            ViewHolder viewHolder = (ViewHolder)object2.getTag();
        }
        var2_4.title.setText((CharSequence)StringUtil.filterHtml(this.mData.get(n2).get("title").toString()));
        var2_4.time.setText((CharSequence)this.mData.get(n2).get("time").toString());
        var2_4.info.setText((CharSequence)this.mData.get(n2).get("info").toString());
        String string2 = this.mData.get(n2).get("img").toString();
        var2_4.imageView.setImageUrl(string2, this.imageLoader);
        return object2;
    }

    public void showInfo(int n2) {
    }

    static class ViewHolder {
        public NetworkImageView imageView;
        public TextView info;
        public TextView time;
        public TextView title;

        ViewHolder() {
        }
    }
}

