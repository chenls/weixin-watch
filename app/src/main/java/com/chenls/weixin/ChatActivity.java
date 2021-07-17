package com.chenls.weixin;

import android.content.BroadcastReceiver;
import android.content.Context;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.chenls.weixin.dao.MessageManager;
import com.chenls.weixin.model.ChatMsgEntity;
import com.chenls.weixin.model.Msg;
import com.chenls.weixin.model.Token;
import com.chenls.weixin.model.User;
import com.chenls.weixin.model.VoiceInfo;
import com.chenls.weixin.net.CookieRequest;
import com.chenls.weixin.net.VolleySingleton;
import com.chenls.weixin.util.Constants;
import com.chenls.weixin.util.Prefs;
import com.chenls.weixin.util.StreamUtil;
import com.chenls.weixin.util.StringUtil;
import com.chenls.weixin.util.TimeUtil;
import com.chenls.weixin.util.WxHome;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends BaseActivity implements View.OnClickListener {
    /* access modifiers changed from: private */
    public static final String TAG = ChatActivity.class.getSimpleName();
    private static final int POLL_INTERVAL = 300;
    /* access modifiers changed from: private */
    public boolean btn_vocie = true;
    /* access modifiers changed from: private */
    public ImageView chatting_mode_btn;
    /* access modifiers changed from: private */
    public User fromUser = new User();
    /* access modifiers changed from: private */
    public boolean isShosrt = false;
    /* access modifiers changed from: private */
    public ChatMsgViewAdapter mAdapter;
    /* access modifiers changed from: private */
    public RelativeLayout mBottom;
    /* access modifiers changed from: private */
    public TextView mBtnRcd;
    /* access modifiers changed from: private */
    public List<ChatMsgEntity> mDataArrays = new ArrayList();
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler();
    /* access modifiers changed from: private */
    public ListView mListView;
    /* access modifiers changed from: private */
    public SoundMeter mSensor;
    /* access modifiers changed from: private */
    public View rcChat_popup;
    /* access modifiers changed from: private */
    public User toUser = new User();
    /* access modifiers changed from: private */
    public LinearLayout voice_rcd_hint_loading;
    /* access modifiers changed from: private */
    public LinearLayout voice_rcd_hint_rcding;
    /* access modifiers changed from: private */
    public LinearLayout voice_rcd_hint_tooshort;
    private LinearLayout del_re;
    private long endVoiceT;
    private int flag = 1;
    private ImageView img1;
    private Button mBtnBack;
    private Button mBtnSend;
    private EditText mEditTextContent;
    private RequestQueue mQueue;
    private MsgReceiver msgReceiver;
    private ImageView sc_img1;
    private long startVoiceT;
    private Token token;
    private String voiceName;
    private ImageView volume;
    /* access modifiers changed from: private */
    public Runnable mPollTask = new Runnable() {
        public void run() {
            ChatActivity.this.updateDisplay(ChatActivity.this.mSensor.getAmplitude());
            ChatActivity.this.mHandler.postDelayed(ChatActivity.this.mPollTask, 300);
        }
    };
    private final Runnable mSleepTask = new Runnable() {
        public void run() {
            ChatActivity.this.stop();
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getWindow().setSoftInputMode(3);
        initView();
        this.mQueue = VolleySingleton.getInstance().getRequestQueue();
        Intent intent = getIntent();
        this.token = new Token();
        this.token.fromBundle(intent.getBundleExtra(Prefs.Key.TOKEN));
        this.fromUser.fromBundle(intent.getBundleExtra("from"));
        this.toUser.fromBundle(intent.getBundleExtra("to"));
//        Log.d(TAG, "onCreate:token=" + JSON.toJSONString(this.token) + " from=" + JSON.toJSONString(this.fromUser) + " to=" + JSON.toJSONString(this.toUser));
        showHeadName(this.toUser.NickName);
        this.msgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.Action.NEW_MSG);
        registerReceiver(this.msgReceiver, intentFilter);
        initData();
    }

    public void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    private void showHeadName(String name) {
        ((TextView) findViewById(R.id.head_name)).setText(StringUtil.filterHtml(name));
    }

    public void initView() {
        this.mListView = findViewById(R.id.listview);
        this.mBtnSend = findViewById(R.id.btn_send);
        this.mBtnRcd = findViewById(R.id.btn_rcd);
        this.mBtnSend.setOnClickListener(this);
        this.mBtnBack = findViewById(R.id.btn_back);
        this.mBottom = findViewById(R.id.btn_bottom);
        this.mBtnBack.setOnClickListener(this);
        this.chatting_mode_btn = findViewById(R.id.ivPopUp);
        this.volume = findViewById(R.id.volume);
        this.rcChat_popup = findViewById(R.id.rcChat_popup);
        this.img1 = findViewById(R.id.img1);
        this.sc_img1 = findViewById(R.id.sc_img1);
        this.del_re = findViewById(R.id.del_re);
        this.voice_rcd_hint_rcding = findViewById(R.id.voice_rcd_hint_rcding);
        this.voice_rcd_hint_loading = findViewById(R.id.voice_rcd_hint_loading);
        this.voice_rcd_hint_tooshort = findViewById(R.id.voice_rcd_hint_tooshort);
        this.mSensor = new SoundMeter();
        this.mEditTextContent = findViewById(R.id.et_sendmessage);
        this.chatting_mode_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ChatActivity.this.btn_vocie) {
                    ChatActivity.this.mBtnRcd.setVisibility(8);
                    ChatActivity.this.mBottom.setVisibility(0);
                    boolean unused = ChatActivity.this.btn_vocie = false;
                    ChatActivity.this.chatting_mode_btn.setImageResource(R.drawable.chatting_setmode_msg_btn);
                    return;
                }
                ChatActivity.this.mBtnRcd.setVisibility(0);
                ChatActivity.this.mBottom.setVisibility(8);
                ChatActivity.this.chatting_mode_btn.setImageResource(R.drawable.chatting_setmode_voice_btn);
                boolean unused2 = ChatActivity.this.btn_vocie = true;
            }
        });
        this.mBtnRcd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                ChatActivity.this.startVoiceInput();
            }
        });
    }

    public void initData() {
        List<Msg> msgList = new MessageManager(this).getMsg(this.toUser.UserName);
//        Log.d(TAG, "initData:search messages from db messages = " + JSON.toJSONString(msgList));
        for (Msg msg : msgList) {
            ChatMsgEntity entity = new ChatMsgEntity();
            entity.setText(msg.Content);
            if (this.toUser.UserName.equals(msg.FromUserName)) {
                entity.setMsgType(true);
                entity.setUserName(this.toUser.UserName);
                entity.setNickName(this.toUser.NickName);
                if (WxHome.isGroupUserName(msg.FromUserName)) {
                    entity.setMemberUserName(msg.fromMemberUserName);
                    entity.setMemberNickName(msg.fromMemberNickName);
                }
            } else {
                entity.setMsgType(false);
                entity.setUserName(this.fromUser.UserName);
                entity.setNickName(this.fromUser.NickName);
            }
            entity.setDate(TimeUtil.timeToStr(msg.CreateTime));
            if (msg.MsgType == 34) {
                entity.setTime(TimeUtil.toCeilSecondsFromMillis(msg.VoiceLength) + "\"");
            }
            this.mDataArrays.add(entity);
        }
        this.mAdapter = new ChatMsgViewAdapter(this, this.mDataArrays, this.token);
        this.mListView.setAdapter(this.mAdapter);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back /*2131689599*/:
                finish();
                return;
            case R.id.btn_send /*2131689605*/:
                send();
                return;
            default:
                return;
        }
    }

    private void send() {
        String contString = this.mEditTextContent.getText().toString();
        if (contString.length() > 0) {
            ChatMsgEntity entity = new ChatMsgEntity();
            entity.setDate(TimeUtil.getDate());
            entity.setUserName(this.fromUser.UserName);
            entity.setNickName(this.fromUser.NickName);
            entity.setMsgType(false);
            entity.setText(contString);
            sendTextMsg(contString);
            this.mDataArrays.add(entity);
            this.mAdapter.notifyDataSetChanged();
            this.mEditTextContent.setText("");
            this.mListView.setSelection(this.mListView.getCount() - 1);
        }
    }

    private void sendTextMsg(String content) {
        Msg msg = new Msg();
        msg.FromUserName = this.fromUser.UserName;
        msg.ToUserName = this.toUser.UserName;
        msg.Content = content;
        msg.Type = 1;
        msg.ClientMsgId = WxHome.randomClientMsgId();
        msg.LocalID = msg.ClientMsgId;
        sendMsg(msg);
        msg.MsgType = 1;
        msg.fromNickName = this.fromUser.NickName;
        new MessageManager(this).insertMessage(msg);
    }

    /* access modifiers changed from: private */
    public void sendVoiceMsg(String content, String name, long voiceLength) {
        Msg msg = new Msg();
        msg.FromUserName = this.fromUser.UserName;
        msg.ToUserName = this.toUser.UserName;
        msg.Content = content;
        msg.Type = 1;
        msg.ClientMsgId = WxHome.randomClientMsgId();
        msg.LocalID = msg.ClientMsgId;
        sendMsg(msg);
        msg.MsgType = 34;
        msg.Content = name;
        msg.VoiceLength = voiceLength;
        msg.fromNickName = this.fromUser.NickName;
        new MessageManager(this).insertMessage(msg);
    }

    private void sendMsg(Msg msg) {
        CookieRequest cookieRequest = new CookieRequest(1, WxHome.getSendUrl(this.token), JSON.toJSONString(WxHome.formMsgRequest(this.token, msg)), new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                Log.d(ChatActivity.TAG, "sendMsg:" + response.toString());
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e(ChatActivity.TAG, "sendMsg:error " + error.getMessage(), error);
            }
        });
        cookieRequest.setCookie(this.token.cookie);
        this.mQueue.add(cookieRequest);
    }

    private boolean recorded(MotionEvent event) {
        if (!Environment.getExternalStorageDirectory().exists()) {
            Toast.makeText(this, "No SDCard", 1).show();
            return false;
        }
        if (this.btn_vocie) {
            int[] location = new int[2];
            this.mBtnRcd.getLocationInWindow(location);
            int btn_rc_Y = location[1];
            int i = location[0];
            int[] del_location = new int[2];
            this.del_re.getLocationInWindow(del_location);
            int del_Y = del_location[1];
            int del_x = del_location[0];
            if (event.getAction() == 0 && this.flag == 1) {
                if (!Environment.getExternalStorageDirectory().exists()) {
                    Toast.makeText(this, "No SDCard", 1).show();
                    return false;
                }
                this.mBtnRcd.setBackgroundResource(R.drawable.voice_rcd_btn_pressed);
                this.rcChat_popup.setVisibility(0);
                this.voice_rcd_hint_loading.setVisibility(0);
                this.voice_rcd_hint_rcding.setVisibility(8);
                this.voice_rcd_hint_tooshort.setVisibility(8);
                this.mHandler.postDelayed(new Runnable() {
                    public void run() {
                        if (!ChatActivity.this.isShosrt) {
                            ChatActivity.this.voice_rcd_hint_loading.setVisibility(8);
                            ChatActivity.this.voice_rcd_hint_rcding.setVisibility(0);
                        }
                    }
                }, 300);
                this.img1.setVisibility(0);
                this.del_re.setVisibility(8);
                this.startVoiceT = System.currentTimeMillis();
                this.voiceName = this.startVoiceT + ".amr";
                start(this.voiceName);
                this.flag = 2;
            } else if (event.getAction() == 1 && this.flag == 2) {
                this.mBtnRcd.setBackgroundResource(R.drawable.voice_rcd_btn_nor);
                if (event.getY() < ((float) del_Y) || event.getY() > ((float) (this.del_re.getHeight() + del_Y)) || event.getX() < ((float) del_x) || event.getX() > ((float) (this.del_re.getWidth() + del_x))) {
                    this.voice_rcd_hint_rcding.setVisibility(8);
                    stop();
                    this.endVoiceT = System.currentTimeMillis();
                    this.flag = 1;
                    long voiceLength = this.endVoiceT - this.startVoiceT;
                    if (voiceLength < 1000) {
                        this.isShosrt = true;
                        this.voice_rcd_hint_loading.setVisibility(8);
                        this.voice_rcd_hint_rcding.setVisibility(8);
                        this.voice_rcd_hint_tooshort.setVisibility(0);
                        this.mHandler.postDelayed(new Runnable() {
                            public void run() {
                                ChatActivity.this.voice_rcd_hint_tooshort.setVisibility(8);
                                ChatActivity.this.rcChat_popup.setVisibility(8);
                                boolean unused = ChatActivity.this.isShosrt = false;
                            }
                        }, 500);
                        return false;
                    }
                    ChatMsgEntity entity = new ChatMsgEntity();
                    entity.setDate(TimeUtil.getDate());
                    entity.setUserName(this.fromUser.UserName);
                    entity.setMsgType(false);
                    entity.setTime(TimeUtil.toCeilSecondsFromMillis(voiceLength) + "\"");
                    entity.setText(this.voiceName);
                    new VoiceTask().execute(this.voiceName, String.valueOf(voiceLength));
                    this.mDataArrays.add(entity);
                    this.mAdapter.notifyDataSetChanged();
                    this.mListView.setSelection(this.mListView.getCount() - 1);
                    this.rcChat_popup.setVisibility(8);
                } else {
                    this.rcChat_popup.setVisibility(8);
                    this.img1.setVisibility(0);
                    this.del_re.setVisibility(8);
                    stop();
                    this.flag = 1;
                    File file = new File(Environment.getExternalStorageDirectory() + Constants.AUDIO_DIRECTORY + this.voiceName);
                    if (file.exists()) {
                        file.delete();
                    }
                }
                if (event.getY() >= ((float) btn_rc_Y) || event.getAction() != 1) {
                    this.img1.setVisibility(0);
                    this.del_re.setVisibility(8);
                    this.del_re.setBackgroundResource(0);
                } else {
                    Animation mLitteAnimation = AnimationUtils.loadAnimation(this, R.anim.cancel_rc);
                    Animation mBigAnimation = AnimationUtils.loadAnimation(this, R.anim.cancel_rc2);
                    this.img1.setVisibility(8);
                    this.del_re.setVisibility(0);
                    this.del_re.setBackgroundResource(R.drawable.voice_rcd_cancel_bg);
                    if (event.getY() >= ((float) del_Y) && event.getY() <= ((float) (this.del_re.getHeight() + del_Y)) && event.getX() >= ((float) del_x) && event.getX() <= ((float) (this.del_re.getWidth() + del_x))) {
                        this.del_re.setBackgroundResource(R.drawable.voice_rcd_cancel_bg_focused);
                        this.sc_img1.startAnimation(mLitteAnimation);
                        this.sc_img1.startAnimation(mBigAnimation);
                    }
                }
            }
        }
        return true;
    }

    private void start(String name) {
        this.mSensor.start(name);
        this.mHandler.postDelayed(this.mPollTask, 300);
    }

    /* access modifiers changed from: private */
    public void stop() {
        this.mHandler.removeCallbacks(this.mSleepTask);
        this.mHandler.removeCallbacks(this.mPollTask);
        this.mSensor.stop();
        this.volume.setImageResource(R.drawable.amp1);
    }

    /* access modifiers changed from: private */
    public void updateDisplay(double signalEMA) {
        switch ((int) signalEMA) {
            case 0:
            case 1:
                this.volume.setImageResource(R.drawable.amp1);
                return;
            case 2:
            case 3:
                this.volume.setImageResource(R.drawable.amp2);
                return;
            case 4:
            case 5:
                this.volume.setImageResource(R.drawable.amp3);
                return;
            case 6:
            case 7:
                this.volume.setImageResource(R.drawable.amp4);
                return;
            case 8:
            case 9:
                this.volume.setImageResource(R.drawable.amp5);
                return;
            case 10:
            case 11:
                this.volume.setImageResource(R.drawable.amp6);
                return;
            default:
                this.volume.setImageResource(R.drawable.amp7);
                return;
        }
    }

    public void onRecognitionSuccess(String message) {
        showMessageDialog(message);
    }

    public void onRecognitionFailed() {
        Log.e(TAG, "onRecognitionFailed:");
    }

    public void showMessageDialog(final String message) {
//        TODO:chenls
//        new AlertDialog.Builder(this).setMessage((CharSequence) message).setPositiveButtonIcon(R.drawable.tic_ic_btn_ok, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                ChatActivity.this.send(message);
//            }
//        }).setNegativeButtonIcon(R.drawable.tic_ic_btn_cancel, (DialogInterface.OnClickListener) null).create().show();
    }

    /* access modifiers changed from: private */
    public void send(String msg) {
        ChatMsgEntity entity = new ChatMsgEntity();
        entity.setDate(TimeUtil.getDate());
        entity.setUserName(this.fromUser.UserName);
        entity.setNickName(this.fromUser.NickName);
        entity.setMsgType(false);
        entity.setText(msg);
        sendTextMsg(msg);
        this.mDataArrays.add(entity);
        this.mAdapter.notifyDataSetChanged();
        this.mEditTextContent.setText("");
        this.mListView.setSelection(this.mListView.getCount() - 1);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        unregisterReceiver(this.msgReceiver);
        super.onDestroy();
    }

    private class VoiceTask extends AsyncTask<String, Void, VoiceInfo> {
        private VoiceTask() {
        }

        /* access modifiers changed from: protected */
        public VoiceInfo doInBackground(String... strings) {
            InputStream inputStream;
            try {
                String filePath = Environment.getExternalStorageDirectory() + Constants.AUDIO_DIRECTORY + strings[0];
                Log.d(ChatActivity.TAG, "VoiceTask::amr file path=" + filePath);
                InputStream fileInputStream = new FileInputStream(filePath);
                HttpURLConnection conn = (HttpURLConnection) new URL(StreamUtil.SPEECH_TO_TEXT).openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "audio/AMR");
                conn.setConnectTimeout(6000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setChunkedStreamingMode(StreamUtil.CHUNK_LENGTH);
                conn.connect();
                OutputStream outputStream = conn.getOutputStream();
                StreamUtil.fromInStreamToOutStream(fileInputStream, outputStream);
                outputStream.close();
                int status = conn.getResponseCode();
                if (status >= 400) {
                    inputStream = conn.getErrorStream();
                } else {
                    inputStream = conn.getInputStream();
                }
                String text = StreamUtil.readFromStream(inputStream, "utf-8");
                Log.d(ChatActivity.TAG, "VoiceTask::voice2text, response=" + text);
                if (status >= 400) {
                    text = "";
                }
                VoiceInfo voiceInfo = new VoiceInfo();
                voiceInfo.setContent(text);
                voiceInfo.setName(strings[0]);
                voiceInfo.setVoiceLength(Long.parseLong(strings[1]));
                return voiceInfo;
            } catch (Exception e) {
                Log.w(ChatActivity.TAG, "VoiceTask::exception", e);
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(VoiceInfo info) {
            if (info != null) {
                Log.d(ChatActivity.TAG, "VoiceTask::onPostExecute, text=" + info.getContent() + "path=" + info.getName());
                ChatActivity.this.sendVoiceMsg(info.getContent(), info.getName(), info.getVoiceLength());
            }
        }
    }

    public class MsgReceiver extends BroadcastReceiver {
        public MsgReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (Constants.Action.NEW_MSG.equals(intent.getAction())) {
                Msg msg = new Msg();
                msg.fromBundle(intent.getExtras());
                Log.d(ChatActivity.TAG, "MsgReceiver::receive broadcast msgType=" + msg.MsgType);
                if (msg.FromUserName != null) {
                    Log.d(ChatActivity.TAG, "MsgReceiver::msg fromusername=" + msg.FromUserName + " chat tousername=" + msg.ToUserName);
                    if (msg.ToUserName.equals(ChatActivity.this.fromUser.UserName) && msg.FromUserName.equals(ChatActivity.this.toUser.UserName)) {
                        ChatMsgEntity entity = new ChatMsgEntity();
                        if (msg.MsgType == 1 || msg.MsgType == 34) {
                            entity.setUserName(msg.FromUserName);
                            entity.setNickName(msg.fromNickName);
                            entity.setMemberUserName(msg.fromMemberUserName);
                            entity.setMemberNickName(msg.fromMemberNickName);
                            entity.setDate(TimeUtil.getDate());
                            entity.setMsgType(true);
                            if (msg.MsgType == 1) {
                                entity.setText(msg.Content);
                            } else if (msg.MsgType == 34) {
                                entity.setTime(TimeUtil.toCeilSecondsFromMillis(msg.VoiceLength) + "\"");
                                entity.setText(msg.MsgId + ".mp3");
                            }
                        }
                        ChatActivity.this.mDataArrays.add(entity);
                        ChatActivity.this.mAdapter.notifyDataSetChanged();
                        ChatActivity.this.mListView.setSelection(ChatActivity.this.mListView.getCount() - 1);
                    } else if (msg.FromUserName.equals(ChatActivity.this.fromUser.UserName) && msg.ToUserName.equals(ChatActivity.this.toUser.UserName)) {
                        ChatMsgEntity entity2 = new ChatMsgEntity();
                        if (msg.MsgType == 1 || msg.MsgType == 34) {
                            entity2.setUserName(msg.ToUserName);
                            entity2.setNickName(msg.toNickName);
                            entity2.setDate(TimeUtil.getDate());
                            entity2.setMsgType(false);
                            if (msg.MsgType == 1) {
                                entity2.setText(msg.Content);
                            } else if (msg.MsgType == 34) {
                                entity2.setTime(TimeUtil.toCeilSecondsFromMillis(msg.VoiceLength) + "\"");
                                entity2.setText(msg.MsgId + ".mp3");
                            }
                        }
                        ChatActivity.this.mDataArrays.add(entity2);
                        ChatActivity.this.mAdapter.notifyDataSetChanged();
                        ChatActivity.this.mListView.setSelection(ChatActivity.this.mListView.getCount() - 1);
                    }
                }
            }
        }
    }
}
