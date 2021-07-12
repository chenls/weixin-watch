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

public class ListViewAdapter extends BaseAdapter {
    private static final String TAG = "ListViewAdapter";
    private String cookie;
    private ImageLoader imageLoader;
    private Activity mContext;
    private List<HashMap<String, Object>> mData;
    private RequestQueue mQueue = VolleySingleton.getInstance().getRequestQueue();

    static class ViewHolder {
        public NetworkImageView imageView;
        public TextView info;
        public TextView time;
        public TextView title;

        ViewHolder() {
        }
    }

    public ListViewAdapter(Activity context, String cookie2, List<HashMap<String, Object>> data) {
        this.mContext = context;
        this.cookie = cookie2;
        this.mData = data;
        this.imageLoader = VolleySingleton.getInstance().getImageLoader(cookie2);
    }

    public int getCount() {
        return this.mData.size();
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View rowView = convertView;
        if (rowView == null) {
            rowView = this.mContext.getLayoutInflater().inflate(R.layout.friend_list_item, (ViewGroup) null);
            holder = new ViewHolder();
            holder.title = (TextView) rowView.findViewById(R.id.title);
            holder.time = (TextView) rowView.findViewById(R.id.time);
            holder.info = (TextView) rowView.findViewById(R.id.info);
            holder.imageView = (NetworkImageView) rowView.findViewById(R.id.img);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }
        holder.title.setText(StringUtil.filterHtml(this.mData.get(position).get("title").toString()));
        holder.time.setText(this.mData.get(position).get("time").toString());
        holder.info.setText(this.mData.get(position).get("info").toString());
        holder.imageView.setImageUrl(this.mData.get(position).get("img").toString(), this.imageLoader);
        return rowView;
    }

    public void showInfo(int position) {
    }
}
