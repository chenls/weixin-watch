/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.os.Environment
 *  android.os.Handler
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.animation.Animation
 *  android.view.animation.AnimationUtils
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.RelativeLayout
 *  android.widget.TextView
 *  android.widget.Toast
 *  org.json.JSONObject
 */
package com.riyuxihe.weixinqingliao;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mobvoi.android.speech.SpeechRecognitionApi;
import com.riyuxihe.weixinqingliao.ChatMsgViewAdapter;
import com.riyuxihe.weixinqingliao.SoundMeter;
import com.riyuxihe.weixinqingliao.dao.MessageManager;
import com.riyuxihe.weixinqingliao.model.ChatMsgEntity;
import com.riyuxihe.weixinqingliao.model.Msg;
import com.riyuxihe.weixinqingliao.model.Token;
import com.riyuxihe.weixinqingliao.model.User;
import com.riyuxihe.weixinqingliao.model.VoiceInfo;
import com.riyuxihe.weixinqingliao.net.CookieRequest;
import com.riyuxihe.weixinqingliao.net.VolleySingleton;
import com.riyuxihe.weixinqingliao.util.StreamUtil;
import com.riyuxihe.weixinqingliao.util.StringUtil;
import com.riyuxihe.weixinqingliao.util.TimeUtil;
import com.riyuxihe.weixinqingliao.util.WxHome;
import com.umeng.analytics.MobclickAgent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import ticwear.design.app.AlertDialog;

public class ChatActivity
extends SpeechRecognitionApi.SpeechRecogActivity
implements View.OnClickListener {
    private static final int POLL_INTERVAL = 300;
    private static final String TAG = ChatActivity.class.getSimpleName();
    private boolean btn_vocie = true;
    private ImageView chatting_mode_btn;
    private LinearLayout del_re;
    private long endVoiceT;
    private int flag = 1;
    private User fromUser;
    private ImageView img1;
    private boolean isShosrt = false;
    private ChatMsgViewAdapter mAdapter;
    private RelativeLayout mBottom;
    private Button mBtnBack;
    private TextView mBtnRcd;
    private Button mBtnSend;
    private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();
    private EditText mEditTextContent;
    private Handler mHandler = new Handler();
    private ListView mListView;
    private Runnable mPollTask;
    private RequestQueue mQueue;
    private SoundMeter mSensor;
    private Runnable mSleepTask;
    private MsgReceiver msgReceiver;
    private View rcChat_popup;
    private ImageView sc_img1;
    private long startVoiceT;
    private User toUser;
    private Token token;
    private String voiceName;
    private LinearLayout voice_rcd_hint_loading;
    private LinearLayout voice_rcd_hint_rcding;
    private LinearLayout voice_rcd_hint_tooshort;
    private ImageView volume;

    public ChatActivity() {
        this.fromUser = new User();
        this.toUser = new User();
        this.mSleepTask = new Runnable(){

            @Override
            public void run() {
                ChatActivity.this.stop();
            }
        };
        this.mPollTask = new Runnable(){

            @Override
            public void run() {
                double d2 = ChatActivity.this.mSensor.getAmplitude();
                ChatActivity.this.updateDisplay(d2);
                ChatActivity.this.mHandler.postDelayed(ChatActivity.this.mPollTask, 300L);
            }
        };
    }

    static /* synthetic */ boolean access$002(ChatActivity chatActivity, boolean bl2) {
        chatActivity.btn_vocie = bl2;
        return bl2;
    }

    static /* synthetic */ boolean access$502(ChatActivity chatActivity, boolean bl2) {
        chatActivity.isShosrt = bl2;
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean recorded(MotionEvent motionEvent) {
        if (!Environment.getExternalStorageDirectory().exists()) {
            Toast.makeText((Context)this, (CharSequence)"No SDCard", (int)1).show();
            return false;
        }
        if (!this.btn_vocie) return true;
        Object object = new int[2];
        this.mBtnRcd.getLocationInWindow((int[])object);
        int n2 = object[1];
        int n3 = object[0];
        object = new int[2];
        this.del_re.getLocationInWindow((int[])object);
        n3 = object[1];
        int n4 = object[0];
        if (motionEvent.getAction() == 0 && this.flag == 1) {
            if (!Environment.getExternalStorageDirectory().exists()) {
                Toast.makeText((Context)this, (CharSequence)"No SDCard", (int)1).show();
                return false;
            }
            this.mBtnRcd.setBackgroundResource(2130837747);
            this.rcChat_popup.setVisibility(0);
            this.voice_rcd_hint_loading.setVisibility(0);
            this.voice_rcd_hint_rcding.setVisibility(8);
            this.voice_rcd_hint_tooshort.setVisibility(8);
            this.mHandler.postDelayed(new Runnable(){

                @Override
                public void run() {
                    if (!ChatActivity.this.isShosrt) {
                        ChatActivity.this.voice_rcd_hint_loading.setVisibility(8);
                        ChatActivity.this.voice_rcd_hint_rcding.setVisibility(0);
                    }
                }
            }, 300L);
            this.img1.setVisibility(0);
            this.del_re.setVisibility(8);
            this.startVoiceT = System.currentTimeMillis();
            this.voiceName = this.startVoiceT + ".amr";
            this.start(this.voiceName);
            this.flag = 2;
            return true;
        }
        if (motionEvent.getAction() != 1) return true;
        if (this.flag != 2) return true;
        this.mBtnRcd.setBackgroundResource(2130837746);
        if (motionEvent.getY() >= (float)n3 && motionEvent.getY() <= (float)(this.del_re.getHeight() + n3) && motionEvent.getX() >= (float)n4 && motionEvent.getX() <= (float)(this.del_re.getWidth() + n4)) {
            this.rcChat_popup.setVisibility(8);
            this.img1.setVisibility(0);
            this.del_re.setVisibility(8);
            this.stop();
            this.flag = 1;
            object = new File(Environment.getExternalStorageDirectory() + "/weixinQingliao/" + this.voiceName);
            if (((File)object).exists()) {
                ((File)object).delete();
            }
        } else {
            this.voice_rcd_hint_rcding.setVisibility(8);
            this.stop();
            this.endVoiceT = System.currentTimeMillis();
            this.flag = 1;
            long l2 = this.endVoiceT - this.startVoiceT;
            if (l2 < 1000L) {
                this.isShosrt = true;
                this.voice_rcd_hint_loading.setVisibility(8);
                this.voice_rcd_hint_rcding.setVisibility(8);
                this.voice_rcd_hint_tooshort.setVisibility(0);
                this.mHandler.postDelayed(new Runnable(){

                    @Override
                    public void run() {
                        ChatActivity.this.voice_rcd_hint_tooshort.setVisibility(8);
                        ChatActivity.this.rcChat_popup.setVisibility(8);
                        ChatActivity.access$502(ChatActivity.this, false);
                    }
                }, 500L);
                return false;
            }
            object = new ChatMsgEntity();
            ((ChatMsgEntity)object).setDate(TimeUtil.getDate());
            ((ChatMsgEntity)object).setUserName(this.fromUser.UserName);
            ((ChatMsgEntity)object).setMsgType(false);
            long l3 = TimeUtil.toCeilSecondsFromMillis(l2);
            ((ChatMsgEntity)object).setTime(l3 + "\"");
            ((ChatMsgEntity)object).setText(this.voiceName);
            new VoiceTask().execute(new String[]{this.voiceName, String.valueOf(l2)});
            this.mDataArrays.add((ChatMsgEntity)object);
            this.mAdapter.notifyDataSetChanged();
            this.mListView.setSelection(this.mListView.getCount() - 1);
            this.rcChat_popup.setVisibility(8);
        }
        if (motionEvent.getY() < (float)n2 && motionEvent.getAction() == 1) {
            object = AnimationUtils.loadAnimation((Context)this, (int)2131034122);
            Animation animation = AnimationUtils.loadAnimation((Context)this, (int)2131034123);
            this.img1.setVisibility(8);
            this.del_re.setVisibility(0);
            this.del_re.setBackgroundResource(2130837748);
            if (!(motionEvent.getY() >= (float)n3)) return true;
            if (!(motionEvent.getY() <= (float)(this.del_re.getHeight() + n3))) return true;
            if (!(motionEvent.getX() >= (float)n4)) return true;
            if (!(motionEvent.getX() <= (float)(this.del_re.getWidth() + n4))) return true;
            this.del_re.setBackgroundResource(2130837749);
            this.sc_img1.startAnimation((Animation)object);
            this.sc_img1.startAnimation(animation);
            return true;
        }
        this.img1.setVisibility(0);
        this.del_re.setVisibility(8);
        this.del_re.setBackgroundResource(0);
        return true;
    }

    private void send() {
        String string2 = this.mEditTextContent.getText().toString();
        if (string2.length() > 0) {
            ChatMsgEntity chatMsgEntity = new ChatMsgEntity();
            chatMsgEntity.setDate(TimeUtil.getDate());
            chatMsgEntity.setUserName(this.fromUser.UserName);
            chatMsgEntity.setNickName(this.fromUser.NickName);
            chatMsgEntity.setMsgType(false);
            chatMsgEntity.setText(string2);
            this.sendTextMsg(string2);
            this.mDataArrays.add(chatMsgEntity);
            this.mAdapter.notifyDataSetChanged();
            this.mEditTextContent.setText((CharSequence)"");
            this.mListView.setSelection(this.mListView.getCount() - 1);
        }
    }

    private void send(String string2) {
        ChatMsgEntity chatMsgEntity = new ChatMsgEntity();
        chatMsgEntity.setDate(TimeUtil.getDate());
        chatMsgEntity.setUserName(this.fromUser.UserName);
        chatMsgEntity.setNickName(this.fromUser.NickName);
        chatMsgEntity.setMsgType(false);
        chatMsgEntity.setText(string2);
        this.sendTextMsg(string2);
        this.mDataArrays.add(chatMsgEntity);
        this.mAdapter.notifyDataSetChanged();
        this.mEditTextContent.setText((CharSequence)"");
        this.mListView.setSelection(this.mListView.getCount() - 1);
    }

    private void sendMsg(Msg object) {
        object = WxHome.formMsgRequest(this.token, (Msg)object);
        object = new CookieRequest(1, WxHome.getSendUrl(this.token), JSON.toJSONString(object), new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject jSONObject) {
                Log.d((String)TAG, (String)("sendMsg:" + jSONObject.toString()));
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e((String)TAG, (String)("sendMsg:error " + volleyError.getMessage()), (Throwable)volleyError);
            }
        });
        ((CookieRequest)object).setCookie(this.token.cookie);
        this.mQueue.add(object);
    }

    private void sendTextMsg(String string2) {
        Msg msg = new Msg();
        msg.FromUserName = this.fromUser.UserName;
        msg.ToUserName = this.toUser.UserName;
        msg.Content = string2;
        msg.Type = 1;
        msg.LocalID = msg.ClientMsgId = WxHome.randomClientMsgId();
        this.sendMsg(msg);
        msg.MsgType = 1;
        msg.fromNickName = this.fromUser.NickName;
        new MessageManager((Context)this).insertMessage(msg);
    }

    private void sendVoiceMsg(String string2, String string3, long l2) {
        Msg msg = new Msg();
        msg.FromUserName = this.fromUser.UserName;
        msg.ToUserName = this.toUser.UserName;
        msg.Content = string2;
        msg.Type = 1;
        msg.LocalID = msg.ClientMsgId = WxHome.randomClientMsgId();
        this.sendMsg(msg);
        msg.MsgType = 34;
        msg.Content = string3;
        msg.VoiceLength = l2;
        msg.fromNickName = this.fromUser.NickName;
        new MessageManager((Context)this).insertMessage(msg);
    }

    private void showHeadName(String string2) {
        ((TextView)this.findViewById(2131689600)).setText((CharSequence)StringUtil.filterHtml(string2));
    }

    private void start(String string2) {
        this.mSensor.start(string2);
        this.mHandler.postDelayed(this.mPollTask, 300L);
    }

    private void stop() {
        this.mHandler.removeCallbacks(this.mSleepTask);
        this.mHandler.removeCallbacks(this.mPollTask);
        this.mSensor.stop();
        this.volume.setImageResource(2130837580);
    }

    private void updateDisplay(double d2) {
        switch ((int)d2) {
            default: {
                this.volume.setImageResource(2130837586);
                return;
            }
            case 0: 
            case 1: {
                this.volume.setImageResource(2130837580);
                return;
            }
            case 2: 
            case 3: {
                this.volume.setImageResource(2130837581);
                return;
            }
            case 4: 
            case 5: {
                this.volume.setImageResource(2130837582);
                return;
            }
            case 6: 
            case 7: {
                this.volume.setImageResource(2130837583);
                return;
            }
            case 8: 
            case 9: {
                this.volume.setImageResource(2130837584);
                return;
            }
            case 10: 
            case 11: 
        }
        this.volume.setImageResource(2130837585);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void initData() {
        Object object = new MessageManager((Context)this).getMsg(this.toUser.UserName);
        Log.d((String)TAG, (String)("initData:search messages from db messages = " + JSON.toJSONString(object)));
        object = object.iterator();
        while (true) {
            if (!object.hasNext()) {
                this.mAdapter = new ChatMsgViewAdapter((Context)this, this.mDataArrays, this.token);
                this.mListView.setAdapter((ListAdapter)this.mAdapter);
                return;
            }
            Msg msg = (Msg)object.next();
            ChatMsgEntity chatMsgEntity = new ChatMsgEntity();
            chatMsgEntity.setText(msg.Content);
            if (this.toUser.UserName.equals(msg.FromUserName)) {
                chatMsgEntity.setMsgType(true);
                chatMsgEntity.setUserName(this.toUser.UserName);
                chatMsgEntity.setNickName(this.toUser.NickName);
                if (WxHome.isGroupUserName(msg.FromUserName)) {
                    chatMsgEntity.setMemberUserName(msg.fromMemberUserName);
                    chatMsgEntity.setMemberNickName(msg.fromMemberNickName);
                }
            } else {
                chatMsgEntity.setMsgType(false);
                chatMsgEntity.setUserName(this.fromUser.UserName);
                chatMsgEntity.setNickName(this.fromUser.NickName);
            }
            chatMsgEntity.setDate(TimeUtil.timeToStr(msg.CreateTime));
            if (msg.MsgType == 34) {
                long l2 = TimeUtil.toCeilSecondsFromMillis(msg.VoiceLength);
                chatMsgEntity.setTime(l2 + "\"");
            }
            this.mDataArrays.add(chatMsgEntity);
        }
    }

    public void initView() {
        this.mListView = (ListView)this.findViewById(2131689608);
        this.mBtnSend = (Button)this.findViewById(2131689605);
        this.mBtnRcd = (TextView)this.findViewById(2131689607);
        this.mBtnSend.setOnClickListener((View.OnClickListener)this);
        this.mBtnBack = (Button)this.findViewById(0x7F0F007F);
        this.mBottom = (RelativeLayout)this.findViewById(2131689604);
        this.mBtnBack.setOnClickListener((View.OnClickListener)this);
        this.chatting_mode_btn = (ImageView)this.findViewById(2131689603);
        this.volume = (ImageView)this.findViewById(2131689700);
        this.rcChat_popup = this.findViewById(2131689609);
        this.img1 = (ImageView)this.findViewById(2131689701);
        this.sc_img1 = (ImageView)this.findViewById(2131689703);
        this.del_re = (LinearLayout)this.findViewById(2131689702);
        this.voice_rcd_hint_rcding = (LinearLayout)this.findViewById(2131689699);
        this.voice_rcd_hint_loading = (LinearLayout)this.findViewById(2131689704);
        this.voice_rcd_hint_tooshort = (LinearLayout)this.findViewById(2131689706);
        this.mSensor = new SoundMeter();
        this.mEditTextContent = (EditText)this.findViewById(2131689606);
        this.chatting_mode_btn.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (ChatActivity.this.btn_vocie) {
                    ChatActivity.this.mBtnRcd.setVisibility(8);
                    ChatActivity.this.mBottom.setVisibility(0);
                    ChatActivity.access$002(ChatActivity.this, false);
                    ChatActivity.this.chatting_mode_btn.setImageResource(2130837598);
                    return;
                }
                ChatActivity.this.mBtnRcd.setVisibility(0);
                ChatActivity.this.mBottom.setVisibility(8);
                ChatActivity.this.chatting_mode_btn.setImageResource(2130837602);
                ChatActivity.access$002(ChatActivity.this, true);
            }
        });
        this.mBtnRcd.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                ChatActivity.this.startVoiceInput();
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            default: {
                return;
            }
            case 2131689605: {
                this.send();
                return;
            }
            case 0x7F0F007F: 
        }
        this.finish();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968601);
        this.getWindow().setSoftInputMode(3);
        this.initView();
        this.mQueue = VolleySingleton.getInstance().getRequestQueue();
        bundle = this.getIntent();
        this.token = new Token();
        this.token.fromBundle(bundle.getBundleExtra("token"));
        this.fromUser.fromBundle(bundle.getBundleExtra("from"));
        this.toUser.fromBundle(bundle.getBundleExtra("to"));
        Log.d((String)TAG, (String)("onCreate:token=" + JSON.toJSONString(this.token) + " from=" + JSON.toJSONString(this.fromUser) + " to=" + JSON.toJSONString(this.toUser)));
        this.showHeadName(this.toUser.NickName);
        this.msgReceiver = new MsgReceiver();
        bundle = new IntentFilter();
        bundle.addAction("com.riyuxihe.weixinqingliao.MSG");
        this.registerReceiver(this.msgReceiver, (IntentFilter)bundle);
        this.initData();
    }

    protected void onDestroy() {
        this.unregisterReceiver(this.msgReceiver);
        super.onDestroy();
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause((Context)this);
    }

    @Override
    public void onRecognitionFailed() {
        Log.e((String)TAG, (String)"onRecognitionFailed:");
    }

    @Override
    public void onRecognitionSuccess(String string2) {
        this.showMessageDialog(string2);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume((Context)this);
    }

    public void showMessageDialog(final String string2) {
        new AlertDialog.Builder((Context)this).setMessage(string2).setDelayConfirmAction(-1, 5000L).setPositiveButtonIcon(2130837688, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n2) {
                ChatActivity.this.send(string2);
            }
        }).setNegativeButtonIcon(2130837686, null).create().show();
    }

    public class MsgReceiver
    extends BroadcastReceiver {
        /*
         * Enabled aggressive block sorting
         */
        public void onReceive(Context object, Intent object2) {
            block13: {
                block12: {
                    if (!"com.riyuxihe.weixinqingliao.MSG".equals(object2.getAction())) break block12;
                    object = new Msg();
                    ((Msg)object).fromBundle(object2.getExtras());
                    Log.d((String)TAG, (String)("MsgReceiver::receive broadcast msgType=" + ((Msg)object).MsgType));
                    if (((Msg)object).FromUserName == null) break block12;
                    Log.d((String)TAG, (String)("MsgReceiver::msg fromusername=" + ((Msg)object).FromUserName + " chat tousername=" + ((Msg)object).ToUserName));
                    if (((Msg)object).ToUserName.equals(((ChatActivity)ChatActivity.this).fromUser.UserName) && ((Msg)object).FromUserName.equals(((ChatActivity)ChatActivity.this).toUser.UserName)) {
                        object2 = new ChatMsgEntity();
                        if (((Msg)object).MsgType == 1 || ((Msg)object).MsgType == 34) {
                            ((ChatMsgEntity)object2).setUserName(((Msg)object).FromUserName);
                            ((ChatMsgEntity)object2).setNickName(((Msg)object).fromNickName);
                            ((ChatMsgEntity)object2).setMemberUserName(((Msg)object).fromMemberUserName);
                            ((ChatMsgEntity)object2).setMemberNickName(((Msg)object).fromMemberNickName);
                            ((ChatMsgEntity)object2).setDate(TimeUtil.getDate());
                            ((ChatMsgEntity)object2).setMsgType(true);
                            if (((Msg)object).MsgType == 1) {
                                ((ChatMsgEntity)object2).setText(((Msg)object).Content);
                            } else if (((Msg)object).MsgType == 34) {
                                long l2 = TimeUtil.toCeilSecondsFromMillis(((Msg)object).VoiceLength);
                                ((ChatMsgEntity)object2).setTime(l2 + "\"");
                                ((ChatMsgEntity)object2).setText(((Msg)object).MsgId + ".mp3");
                            }
                        }
                        ChatActivity.this.mDataArrays.add(object2);
                        ChatActivity.this.mAdapter.notifyDataSetChanged();
                        ChatActivity.this.mListView.setSelection(ChatActivity.this.mListView.getCount() - 1);
                        return;
                    }
                    if (((Msg)object).FromUserName.equals(((ChatActivity)ChatActivity.this).fromUser.UserName) && ((Msg)object).ToUserName.equals(((ChatActivity)ChatActivity.this).toUser.UserName)) break block13;
                }
                return;
            }
            object2 = new ChatMsgEntity();
            if (((Msg)object).MsgType == 1 || ((Msg)object).MsgType == 34) {
                ((ChatMsgEntity)object2).setUserName(((Msg)object).ToUserName);
                ((ChatMsgEntity)object2).setNickName(((Msg)object).toNickName);
                ((ChatMsgEntity)object2).setDate(TimeUtil.getDate());
                ((ChatMsgEntity)object2).setMsgType(false);
                if (((Msg)object).MsgType == 1) {
                    ((ChatMsgEntity)object2).setText(((Msg)object).Content);
                } else if (((Msg)object).MsgType == 34) {
                    long l3 = TimeUtil.toCeilSecondsFromMillis(((Msg)object).VoiceLength);
                    ((ChatMsgEntity)object2).setTime(l3 + "\"");
                    ((ChatMsgEntity)object2).setText(((Msg)object).MsgId + ".mp3");
                }
            }
            ChatActivity.this.mDataArrays.add(object2);
            ChatActivity.this.mAdapter.notifyDataSetChanged();
            ChatActivity.this.mListView.setSelection(ChatActivity.this.mListView.getCount() - 1);
        }
    }

    private class VoiceTask
    extends AsyncTask<String, Void, VoiceInfo> {
        private VoiceTask() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected VoiceInfo doInBackground(String ... stringArray) {
            try {
                Object object = Environment.getExternalStorageDirectory() + "/weixinQingliao/" + stringArray[0];
                Log.d((String)TAG, (String)("VoiceTask::amr file path=" + (String)object));
                object = new FileInputStream((String)object);
                Object object2 = (HttpURLConnection)new URL(StreamUtil.SPEECH_TO_TEXT).openConnection();
                ((HttpURLConnection)object2).setRequestMethod("POST");
                ((URLConnection)object2).setRequestProperty("Content-Type", "audio/AMR");
                ((URLConnection)object2).setConnectTimeout(6000);
                ((URLConnection)object2).setDoInput(true);
                ((URLConnection)object2).setDoOutput(true);
                ((URLConnection)object2).setUseCaches(false);
                ((HttpURLConnection)object2).setChunkedStreamingMode(StreamUtil.CHUNK_LENGTH);
                ((URLConnection)object2).connect();
                OutputStream outputStream = ((URLConnection)object2).getOutputStream();
                StreamUtil.fromInStreamToOutStream((InputStream)object, outputStream);
                outputStream.close();
                int n2 = ((HttpURLConnection)object2).getResponseCode();
                object = n2 >= 400 ? ((HttpURLConnection)object2).getErrorStream() : ((URLConnection)object2).getInputStream();
                object = StreamUtil.readFromStream((InputStream)object, "utf-8");
                Log.d((String)TAG, (String)("VoiceTask::voice2text, response=" + (String)object));
                if (n2 >= 400) {
                    object = "";
                }
                object2 = new VoiceInfo();
                ((VoiceInfo)object2).setContent((String)object);
                ((VoiceInfo)object2).setName(stringArray[0]);
                ((VoiceInfo)object2).setVoiceLength(Long.parseLong(stringArray[1]));
                return object2;
            }
            catch (Exception exception) {
                Log.w((String)TAG, (String)"VoiceTask::exception", (Throwable)exception);
                return null;
            }
        }

        protected void onPostExecute(VoiceInfo voiceInfo) {
            if (voiceInfo != null) {
                Log.d((String)TAG, (String)("VoiceTask::onPostExecute, text=" + voiceInfo.getContent() + "path=" + voiceInfo.getName()));
                ChatActivity.this.sendVoiceMsg(voiceInfo.getContent(), voiceInfo.getName(), voiceInfo.getVoiceLength());
            }
        }
    }
}

