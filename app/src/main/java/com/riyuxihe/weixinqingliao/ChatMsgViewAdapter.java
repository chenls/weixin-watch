/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.media.MediaPlayer
 *  android.media.MediaPlayer$OnCompletionListener
 *  android.os.Environment
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.TextView
 */
package com.riyuxihe.weixinqingliao;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.riyuxihe.weixinqingliao.model.ChatMsgEntity;
import com.riyuxihe.weixinqingliao.model.Token;
import com.riyuxihe.weixinqingliao.net.VolleySingleton;
import com.riyuxihe.weixinqingliao.util.StringUtil;
import com.riyuxihe.weixinqingliao.util.WxHome;
import java.util.List;

public class ChatMsgViewAdapter
extends BaseAdapter {
    private static final String TAG = ChatMsgViewAdapter.class.getSimpleName();
    private List<ChatMsgEntity> coll;
    private Context ctx;
    private ImageLoader imageLoader;
    private LayoutInflater mInflater;
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    private RequestQueue mQueue;
    private Token token;

    public ChatMsgViewAdapter(Context context, List<ChatMsgEntity> list, Token token) {
        this.token = token;
        this.ctx = context;
        this.coll = list;
        this.mQueue = VolleySingleton.getInstance().getRequestQueue();
        this.imageLoader = VolleySingleton.getInstance().getImageLoader(token.cookie);
        this.mInflater = LayoutInflater.from((Context)context);
    }

    private void playMusic(String string2) {
        try {
            if (this.mMediaPlayer.isPlaying()) {
                this.mMediaPlayer.stop();
            }
            this.mMediaPlayer.reset();
            this.mMediaPlayer.setDataSource(string2);
            this.mMediaPlayer.prepare();
            this.mMediaPlayer.start();
            this.mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){

                public void onCompletion(MediaPlayer mediaPlayer) {
                }
            });
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    private void stop() {
    }

    public int getCount() {
        return this.coll.size();
    }

    public Object getItem(int n2) {
        return this.coll.get(n2);
    }

    public long getItemId(int n2) {
        return n2;
    }

    public int getItemViewType(int n2) {
        if (this.coll.get(n2).getMsgType()) {
            return 0;
        }
        return 1;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public View getView(int n2, View view, ViewGroup object) {
        void var3_5;
        Object object2 = this.coll.get(n2);
        boolean bl2 = ((ChatMsgEntity)object2).getMsgType();
        if (view == null) {
            view = bl2 ? this.mInflater.inflate(2130968607, null) : this.mInflater.inflate(2130968608, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tvSendTime = (TextView)view.findViewById(2131689626);
            viewHolder.tvUserName = (TextView)view.findViewById(2131689630);
            viewHolder.tvContent = (TextView)view.findViewById(2131689628);
            viewHolder.tvTime = (TextView)view.findViewById(2131689629);
            viewHolder.ivUserhead = (NetworkImageView)view.findViewById(2131689627);
            viewHolder.isComMsg = bl2;
            view.setTag((Object)viewHolder);
        } else {
            ViewHolder viewHolder = (ViewHolder)view.getTag();
        }
        var3_5.tvSendTime.setText((CharSequence)((ChatMsgEntity)object2).getDate());
        if (((ChatMsgEntity)object2).getTime() != null && !((ChatMsgEntity)object2).getTime().isEmpty()) {
            var3_5.tvContent.setText((CharSequence)"");
            var3_5.tvContent.setCompoundDrawablesWithIntrinsicBounds(0, 0, 2130837610, 0);
            var3_5.tvTime.setText((CharSequence)((ChatMsgEntity)object2).getTime());
        } else {
            var3_5.tvContent.setText((CharSequence)((ChatMsgEntity)object2).getText());
            var3_5.tvContent.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            var3_5.tvTime.setText((CharSequence)"");
        }
        var3_5.tvContent.setOnClickListener(new View.OnClickListener((ChatMsgEntity)object2){
            final /* synthetic */ ChatMsgEntity val$entity;
            {
                this.val$entity = chatMsgEntity;
            }

            public void onClick(View view) {
                if (this.val$entity.getTime() != null && !this.val$entity.getTime().isEmpty()) {
                    ChatMsgViewAdapter.this.playMusic(Environment.getExternalStorageDirectory() + "/weixinQingliao/" + this.val$entity.getText());
                }
            }
        });
        if (WxHome.isGroupUserName(((ChatMsgEntity)object2).getUserName())) {
            var3_5.tvUserName.setText((CharSequence)StringUtil.filterHtml(((ChatMsgEntity)object2).getMemberNickName()));
            object2 = WxHome.getIconUrlByUsername(this.token, ((ChatMsgEntity)object2).getMemberUserName());
        } else {
            var3_5.tvUserName.setText((CharSequence)StringUtil.filterHtml(((ChatMsgEntity)object2).getNickName()));
            object2 = WxHome.getIconUrlByUsername(this.token, ((ChatMsgEntity)object2).getUserName());
        }
        var3_5.ivUserhead.setImageUrl((String)object2, this.imageLoader);
        return view;
    }

    public int getViewTypeCount() {
        return 2;
    }

    public static interface IMsgViewType {
        public static final int IMVT_COM_MSG = 0;
        public static final int IMVT_TO_MSG = 1;
    }

    static class ViewHolder {
        public boolean isComMsg = true;
        public NetworkImageView ivUserhead;
        public TextView tvContent;
        public TextView tvSendTime;
        public TextView tvTime;
        public TextView tvUserName;

        ViewHolder() {
        }
    }
}

