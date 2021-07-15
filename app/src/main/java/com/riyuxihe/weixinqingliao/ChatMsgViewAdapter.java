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
import com.riyuxihe.weixinqingliao.util.Constants;
import com.riyuxihe.weixinqingliao.util.StringUtil;
import com.riyuxihe.weixinqingliao.util.WxHome;

import java.util.List;

public class ChatMsgViewAdapter extends BaseAdapter {
    private static final String TAG = ChatMsgViewAdapter.class.getSimpleName();
    private final List<ChatMsgEntity> coll;
    private final Context ctx;
    private final ImageLoader imageLoader;
    private final LayoutInflater mInflater;
    private final MediaPlayer mMediaPlayer = new MediaPlayer();
    private final RequestQueue mQueue;
    private final Token token;

    public interface IMsgViewType {
        int IMVT_COM_MSG = 0;
        int IMVT_TO_MSG = 1;
    }

    public ChatMsgViewAdapter(Context context, List<ChatMsgEntity> coll2, Token token2) {
        this.token = token2;
        this.ctx = context;
        this.coll = coll2;
        this.mQueue = VolleySingleton.getInstance().getRequestQueue();
        this.imageLoader = VolleySingleton.getInstance().getImageLoader(token2.cookie);
        this.mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return this.coll.size();
    }

    public Object getItem(int position) {
        return this.coll.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public int getItemViewType(int position) {
        if (this.coll.get(position).getMsgType()) {
            return 0;
        }
        return 1;
    }

    public int getViewTypeCount() {
        return 2;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        String imgUrl;
        final ChatMsgEntity entity = this.coll.get(position);
        boolean isComMsg = entity.getMsgType();
        if (convertView == null) {
            if (isComMsg) {
                convertView = this.mInflater.inflate(R.layout.chatting_item_msg_text_left, null);
            } else {
                convertView = this.mInflater.inflate(R.layout.chatting_item_msg_text_right, null);
            }
            viewHolder = new ViewHolder();
            viewHolder.tvSendTime = convertView.findViewById(R.id.tv_sendtime);
            viewHolder.tvUserName = convertView.findViewById(R.id.tv_username);
            viewHolder.tvContent = convertView.findViewById(R.id.tv_chatcontent);
            viewHolder.tvTime = convertView.findViewById(R.id.tv_time);
            viewHolder.ivUserhead = convertView.findViewById(R.id.iv_userhead);
            viewHolder.isComMsg = isComMsg;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvSendTime.setText(entity.getDate());
        if (entity.getTime() == null || entity.getTime().isEmpty()) {
            viewHolder.tvContent.setText(entity.getText());
            viewHolder.tvContent.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            viewHolder.tvTime.setText("");
        } else {
            viewHolder.tvContent.setText("");
            viewHolder.tvContent.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.chatto_voice_playing, 0);
            viewHolder.tvTime.setText(entity.getTime());
        }
        viewHolder.tvContent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (entity.getTime() != null && !entity.getTime().isEmpty()) {
                    ChatMsgViewAdapter.this.playMusic(Environment.getExternalStorageDirectory() + Constants.AUDIO_DIRECTORY + entity.getText());
                }
            }
        });
        if (WxHome.isGroupUserName(entity.getUserName())) {
            viewHolder.tvUserName.setText(StringUtil.filterHtml(entity.getMemberNickName()));
            imgUrl = WxHome.getIconUrlByUsername(this.token, entity.getMemberUserName());
        } else {
            viewHolder.tvUserName.setText(StringUtil.filterHtml(entity.getNickName()));
            imgUrl = WxHome.getIconUrlByUsername(this.token, entity.getUserName());
        }
        viewHolder.ivUserhead.setImageUrl(imgUrl, this.imageLoader);
        return convertView;
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

    /* access modifiers changed from: private */
    public void playMusic(String name) {
        try {
            if (this.mMediaPlayer.isPlaying()) {
                this.mMediaPlayer.stop();
            }
            this.mMediaPlayer.reset();
            this.mMediaPlayer.setDataSource(name);
            this.mMediaPlayer.prepare();
            this.mMediaPlayer.start();
            this.mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stop() {
    }
}
