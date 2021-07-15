package com.riyuxihe.weixinqingliao;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.riyuxihe.weixinqingliao.dao.MessageManager;
import com.riyuxihe.weixinqingliao.model.Contact;
import com.riyuxihe.weixinqingliao.model.Msg;
import com.riyuxihe.weixinqingliao.model.SyncKey;
import com.riyuxihe.weixinqingliao.model.Token;
import com.riyuxihe.weixinqingliao.model.User;
import com.riyuxihe.weixinqingliao.net.CookieRequest;
import com.riyuxihe.weixinqingliao.net.VolleySingleton;
import com.riyuxihe.weixinqingliao.protocol.BatchContactRequest;
import com.riyuxihe.weixinqingliao.protocol.BatchContactResponse;
import com.riyuxihe.weixinqingliao.protocol.ContactResponse;
import com.riyuxihe.weixinqingliao.protocol.InitRequest;
import com.riyuxihe.weixinqingliao.protocol.InitResponse;
import com.riyuxihe.weixinqingliao.protocol.MsgSyncRequest;
import com.riyuxihe.weixinqingliao.protocol.MsgSyncResponse;
import com.riyuxihe.weixinqingliao.protocol.StatusNotifyRequest;
import com.riyuxihe.weixinqingliao.util.Constants;
import com.riyuxihe.weixinqingliao.util.FileUtil;
import com.riyuxihe.weixinqingliao.util.NetUtil;
import com.riyuxihe.weixinqingliao.util.Prefs;
import com.riyuxihe.weixinqingliao.util.StreamUtil;
import com.riyuxihe.weixinqingliao.util.StringUtil;
import com.riyuxihe.weixinqingliao.util.TimeUtil;
import com.riyuxihe.weixinqingliao.util.WxHome;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

public class HomeActivity extends SwipeActivity {
    public static final int HOME_SERVICE_OBJ = 20;
    public static final String PERIOD_KEY = "sync_period";
    private static final int PERMISSIONS_REQUEST_INTERNET = 1;
    private static final String TAG = "HomeActivity";
    private static final int VIEW_COUNT = 2;
    private static int kJobId = 2;
    /* access modifiers changed from: private */
    public List<String> chatSet = new ArrayList();
    /* access modifiers changed from: private */
    public ArrayList<Contact> contactList = new ArrayList<>();
    /* access modifiers changed from: private */
    public boolean contactLoaded = false;
    /* access modifiers changed from: private */
    public ArrayList<Contact> exContactList = new ArrayList<>();
    /* access modifiers changed from: private */
    public boolean exContactLoaded = false;
    /* access modifiers changed from: private */
    public ArrayList<Contact> initList;
    /* access modifiers changed from: private */
    public boolean initLoaded = false;
    /* access modifiers changed from: private */
    public List<Fragment> mFragments = new ArrayList();
    /* access modifiers changed from: private */
    public List<String> mTitles = new ArrayList();
    /* access modifiers changed from: private */
    public SyncKey syncKey;
    /* access modifiers changed from: private */
    public Token token;
    /* access modifiers changed from: private */
    public User user;
    HomeJobService mHomeJobService;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 20:
                    HomeActivity.this.mHomeJobService = (HomeJobService) msg.obj;
                    HomeActivity.this.mHomeJobService.setUiCallback(HomeActivity.this);
                    HomeActivity.this.scheduleJob(HomeActivity.this.getPreferences(0).
                            getLong(PERIOD_KEY, Constants.Period.HOME_STANDARD));
                    mHandler.sendEmptyMessage(21);
                    return;
                case 21:
                    mHomeJobService.onStartJob(null);
                    mHandler.sendEmptyMessageDelayed(21, 5000);
                default:
                    return;
            }
        }
    };
    private CloseReceiver closeReceiver;
    private ContactFragment contactFragment;
    private InitFragment initFragment;
    private FragmentPagerAdapter mAdapter;
    private boolean mIsLoaded = false;
    private ProgressBar mProgressBar;
    private TextView mNotice;
    private RequestQueue mQueue;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    /* access modifiers changed from: private */
    private NetworkStateReceiver networkStateReceiver;
    private RescheduleReceiver rescheduleReceiver;
    private long mExitTime = 0;

    static /* synthetic */ int access$3208() {
        int i = kJobId;
        kJobId = i + 1;
        return i;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        new MessageManager(this).reCreateTable();
        Intent intent = getIntent();
        this.token = new Token();
        this.token.fromBundle(intent.getExtras());
        Log.d(TAG, "onCreate: ");
//        Log.d(TAG, "onCreate:token=" + JSON.toJSONString(this.token));
        this.mQueue = VolleySingleton.getInstance().getRequestQueue();
        initViews();
        initWx();
        initContact(0);
        this.closeReceiver = new CloseReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.Action.CLOSE_APP);
        registerReceiver(this.closeReceiver, intentFilter);
        this.rescheduleReceiver = new RescheduleReceiver();
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(Constants.Action.RESCHEDULE);
        registerReceiver(this.rescheduleReceiver, intentFilter2);
    }

    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    /* access modifiers changed from: private */
    public void startHomeJobService(Token token2, String deviceId) {
        Intent startServiceIntent = new Intent(this, HomeJobService.class);
        startServiceIntent.putExtra("messenger", new Messenger(this.mHandler));
        startServiceIntent.putExtra(Prefs.Key.TOKEN, token2.toBundle());
        startServiceIntent.putExtra("deviceId", deviceId);
        startServiceIntent.putExtra("syncKey", this.syncKey.toString());
        startService(startServiceIntent);
        Log.d(TAG, "startHomeJobService: ");
    }

    private void initWx() {
        Log.d(TAG, "initWx: ");
        String url = WxHome.getInitUrl(this.token);
        InitRequest initRequest = WxHome.formInitRequest(this.token);
//        Log.d(TAG, "initWx:" + JSON.toJSONString(initRequest));
        CookieRequest cookieRequest = new CookieRequest(1, url, JSON.toJSONString(initRequest), new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse: ");
//                Log.d(HomeActivity.TAG, "initWx:" + response.toString());
                InitResponse initResponse = JSON.parseObject(response.toString(), InitResponse.class);
                User unused = HomeActivity.this.user = initResponse.User;
                ArrayList unused2 = HomeActivity.this.initList = initResponse.ContactList;
                HomeActivity.this.filterDunpFilehelper(HomeActivity.this.initList);
                SyncKey unused3 = HomeActivity.this.syncKey = initResponse.SyncKey;
                HomeActivity.this.notifyStatus();
                for (String chat : initResponse.ChatSet.split(",")) {
                    if (chat.startsWith("@") || "filehelper".equals(chat)) {
                        HomeActivity.this.chatSet.add(chat);
                    }
                }
                HomeActivity.this.initBatchContact(HomeActivity.this.chatSet);
                HomeActivity.this.startHomeJobService(HomeActivity.this.token, WxHome.randomDeviceId());
                boolean unused4 = HomeActivity.this.initLoaded = true;
                HomeActivity.this.onInitComplete();
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
//                Log.e(HomeActivity.TAG, "initWx:error " + error.getMessage(), error);
            }
        });
        cookieRequest.setCookie(this.token.cookie);
        cookieRequest.setRetryPolicy(new DefaultRetryPolicy(7000, 1, 2.0f));
        this.mQueue.add(cookieRequest);
    }

    /* access modifiers changed from: private */
    public void filterDunpFilehelper(ArrayList<Contact> initList2) {
        boolean found = false;
        for (int i = 0; i < initList2.size(); i++) {
            if (initList2.get(i).UserName.equals("filehelper")) {
                if (found) {
                    initList2.remove(i);
                } else {
                    found = true;
                }
            } else if (!initList2.get(i).UserName.startsWith("@")) {
                initList2.remove(i);
            }
        }
    }

    /* access modifiers changed from: private */
    public void initContact(int seq) {
        CookieRequest contactRequest = new CookieRequest(1, WxHome.getContactUrl(this.token, seq), new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
//                Log.d(HomeActivity.TAG, "initContact:" + response.toString());
                ContactResponse contactResponse = JSON.parseObject(response.toString(), ContactResponse.class);
                HomeActivity.this.contactList.addAll(contactResponse.MemberList);
                if (contactResponse.Seq == 0) {
                    Collections.sort(HomeActivity.this.contactList);
                    boolean unused = HomeActivity.this.contactLoaded = true;
                    HomeActivity.this.onInitComplete();
                    return;
                }
                HomeActivity.this.initContact(contactResponse.Seq);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    boolean unused = HomeActivity.this.contactLoaded = true;
                    HomeActivity.this.onInitComplete();
                    Log.w(HomeActivity.TAG, "initContact:error " + error.getMessage());
                    return;
                }
                Log.e(HomeActivity.TAG, "initContact:error " + error.getMessage(), error);
            }
        });
        contactRequest.setCookie(this.token.cookie);
        contactRequest.setRetryPolicy(new DefaultRetryPolicy(15000, 1, 2.0f));
        this.mQueue.add(contactRequest);
    }

    /* access modifiers changed from: private */
    public void scheduleJob(long period) {
        Log.d(TAG, "scheduleJob: ");
        JobInfo.Builder builder = new JobInfo.Builder(kJobId, new ComponentName(this, HomeJobService.class));
        builder.setPeriodic(period);
        ((JobScheduler) getSystemService("jobscheduler")).schedule(builder.build());
    }

    /* access modifiers changed from: private */
    public void cancelJob() {
        ((JobScheduler) getSystemService("jobscheduler")).cancel(kJobId);
    }

    /* access modifiers changed from: private */
    public void initBatchContact(List<String> chatSet2) {
        String url = WxHome.getBatchContactUrl(this.token);
        BatchContactRequest request = WxHome.formBatchContactRequest(this.token, chatSet2);
        if (request == null || request.List.isEmpty()) {
            Log.d(TAG, "intBatchContact:skipping get BatchContact");
            this.exContactLoaded = true;
            return;
        }
        Log.d(TAG, "initBatchContact: ");
//        Log.d(TAG, "initBatchContact:" + JSON.toJSONString(request));
        CookieRequest cookieRequest = new CookieRequest(1, url, JSON.toJSONString(request), new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                Log.d(HomeActivity.TAG, "initBatchContact:" + response.toString());
                HomeActivity.this.exContactList.addAll(JSON.parseObject(response.toString(), BatchContactResponse.class).ContactList);
                boolean unused = HomeActivity.this.exContactLoaded = true;
                HomeActivity.this.onExComplete();
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e(HomeActivity.TAG, "initBatchContact:error " + error.getMessage(), error);
            }
        });
        cookieRequest.setCookie(this.token.cookie);
        cookieRequest.setRetryPolicy(new DefaultRetryPolicy(8000, 1, 2.0f));
        this.mQueue.add(cookieRequest);
    }

    /* access modifiers changed from: private */
    public void notifyStatus() {
        Log.d(TAG, "notifyStatus: ");
        String url = WxHome.getWxStatusNotifyUrl(this.token);
        StatusNotifyRequest request = WxHome.formStatusNotifyRequest(this.token, this.user.UserName);
//        Log.d(TAG, "notifyStatus:" + JSON.toJSONString(request));
        CookieRequest cookieRequest = new CookieRequest(1, url, JSON.toJSONString(request), new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
//                Log.d(HomeActivity.TAG, "notifyStatus:" + response.toString());
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
//                Log.e(HomeActivity.TAG, "notifyStatus:error " + error.getMessage(), error);
            }
        });
        cookieRequest.setCookie(this.token.cookie);
        cookieRequest.setRetryPolicy(new DefaultRetryPolicy(8000, 1, 2.0f));
        this.mQueue.add(cookieRequest);
    }

    public String getShowName(String groupUserName, String userName) {
        if (this.exContactList == null) {
            Log.w(TAG, "getShowName:extra Contact list not initial");
            return "";
        } else if (StringUtil.isNullOrEmpty(groupUserName)) {
            Log.w(TAG, "getShowName:groupUserName can not be empty");
            return "";
        } else {
            Iterator<Contact> it = this.exContactList.iterator();
            while (it.hasNext()) {
                Contact contact = it.next();
                if (contact.UserName.equals(groupUserName)) {
                    for (Contact member : contact.MemberList) {
                        if (member.UserName.equals(userName)) {
                            return member.getShowName();
                        }
                    }
                    continue;
                }
            }
            return "";
        }
    }

    public String getShowName(String userName) {
        Contact contact = getContact(userName);
        if (contact == null) {
            return "";
        }
        return contact.getShowName();
    }

    public Contact getContact(String userName) {
        if (this.contactList == null) {
            Log.w(TAG, "getShowName:contact list not initial");
            return null;
        }
        Iterator<Contact> it = this.contactList.iterator();
        while (it.hasNext()) {
            Contact contact = it.next();
            if (contact.UserName.equals(userName)) {
                return contact;
            }
        }
        Iterator<Contact> it2 = this.exContactList.iterator();
        while (it2.hasNext()) {
            Contact contact2 = it2.next();
            if (contact2.UserName.equals(userName)) {
                return contact2;
            }
        }
        return null;
    }

    public boolean isMutedByUserName(String userName) {
        if (userName == null) {
            return false;
        }
        Iterator<Contact> it = this.exContactList.iterator();
        while (it.hasNext()) {
            Contact contact = it.next();
            if (userName.equals(contact.UserName)) {
                return contact.isMuted();
            }
        }
        if (this.contactList == null) {
            return false;
        }
        Iterator<Contact> it2 = this.contactList.iterator();
        while (it2.hasNext()) {
            Contact contact2 = it2.next();
            if (userName.equals(contact2.UserName)) {
                return contact2.isMuted();
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void onInitComplete() {
        if (this.initLoaded && this.contactLoaded) {
            if (this.contactList.isEmpty() && this.initList != null && !this.initList.isEmpty()) {
                this.contactList.addAll(this.initList);
                Collections.sort(this.contactList);
            }
            this.mProgressBar.setVisibility(8);
            this.mNotice.setVisibility(8);
            this.mTitles.add("消息");
            this.mTitles.add("联系人");
            this.initFragment = InitFragment.newInstance(this.token, this.user, this.initList);
            this.mFragments.add(this.initFragment);
            this.contactFragment = ContactFragment.newInstance(this.token, this.user, this.contactList);
            this.mFragments.add(this.contactFragment);
            this.mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
                public Fragment getItem(int position) {
                    return HomeActivity.this.mFragments.get(position);
                }

                public int getCount() {
                    return HomeActivity.this.mFragments.size();
                }

                public CharSequence getPageTitle(int position) {
                    return HomeActivity.this.mTitles.get(position);
                }
            };
            this.mViewPager.setAdapter(this.mAdapter);
            for (int i = 0; i < VIEW_COUNT; i++) {
                this.mTabLayout.addTab(this.mTabLayout.newTab());
            }
            this.mTabLayout.setTabMode(0);
            this.mTabLayout.setupWithViewPager(this.mViewPager);
            this.mIsLoaded = true;
            onExComplete();
        }
    }

    /* access modifiers changed from: private */
    public void onExComplete() {
        if (this.mIsLoaded && this.exContactLoaded) {
            if (this.exContactList != null) {
                this.initFragment.addExInitList(this.exContactList);
            }
            syncMsg();
            syncMsg();
        }
    }

    private void initViews() {
        this.mViewPager = findViewById(R.id.view_pager);
        this.mTabLayout = findViewById(R.id.tablayout);
        this.mProgressBar = findViewById(R.id.progressBar);
        this.mNotice = findViewById(R.id.notice);

    }

    public void onSyncChecked(Properties prop) {
        if (prop != null && !prop.isEmpty()) {
            String syncCheckStr = prop.getProperty(WxHome.SYNC_CHECK_KEY);
            Log.d(TAG, "onSyncChecked:syncCheckStr=" + syncCheckStr);
            com.alibaba.fastjson.JSONObject syncCheckObj = JSON.parseObject(syncCheckStr);
            if (Constants.SyncCheckCode.SUCCESS.equals(syncCheckObj.getString(WxHome.RETCODE))) {
                int code = Integer.parseInt(syncCheckObj.getString(WxHome.SELECTOR));
                if (code != 0) {
                    Log.d(TAG, "onSyncChecked:new message, code=" + code);
                    syncMsg();
                    return;
                }
                return;
            }
            Log.d(TAG, "onSyncChecked:sync fail");
            if (this.mHomeJobService.unSync()) {
                cancelJob();
                closeApp();
                clearFiles();
            }
        }
    }

    /* access modifiers changed from: private */
    public void closeApp() {
        runOnUiThread(new Runnable() {
            public void run() {
                HomeActivity.this.finish();
            }
        });
        ((NotificationManager) getSystemService("notification")).notify(10010, new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.chat).setContentTitle(getString(R.string.app_name)).setContentText("已退出").setAutoCancel(true).build());
    }

    /* access modifiers changed from: private */
    public void clearFiles() {
        File dir = new File(Environment.getExternalStorageDirectory() + Constants.AUDIO_DIRECTORY);
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (String file : children) {
                new File(dir, file).delete();
            }
        }
    }

    private void sendNotification(Msg msg) {
        Log.d(TAG, "sendNotification:content=" + msg.Content);
        Intent notificationIntent = new Intent(this, ChatActivity.class);
        notificationIntent.putExtra(Prefs.Key.TOKEN, this.token.toBundle());
        User toUser = new User();
        toUser.UserName = msg.FromUserName;
        toUser.NickName = msg.fromNickName;
        toUser.HeadImgUrl = "";
        notificationIntent.putExtra("to", toUser.toBundle());
        notificationIntent.putExtra("from", this.user.toBundle());
        ((NotificationManager) getSystemService("notification")).notify(msg.FromUserName.hashCode(), new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.chat).setContentTitle(msg.fromNickName).setContentText(msg.Content).setContentIntent(PendingIntent.getActivity(this, msg.FromUserName.hashCode(), notificationIntent, 134217728)).setAutoCancel(true).build());
    }

    /* access modifiers changed from: private */
    public void syncMsg() {
        Log.d(TAG, "syncMsg: ");
        if (this.mIsLoaded && this.exContactLoaded) {
            String url = WxHome.getMsgSyncUrl(this.token);
            MsgSyncRequest msgSyncRequest = WxHome.formMsgSyncRequest(this.token, this.syncKey);
//            Log.d(TAG, "syncMsg:" + JSON.toJSONString(msgSyncRequest));
            CookieRequest cookieRequest = new CookieRequest(1, url, JSON.toJSONString(msgSyncRequest), new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject response) {
//                    Log.d(HomeActivity.TAG, "syncMsg:" + response.toString());
                    MsgSyncResponse msgSyncResponse = JSON.parseObject(response.toString(), MsgSyncResponse.class);
                    if (msgSyncResponse.BaseResponse.Ret == 0 && !msgSyncResponse.SyncKey.toString().equals(HomeActivity.this.syncKey.toString())) {
                        Log.d(HomeActivity.TAG, "syncMsg:receive " + msgSyncResponse.AddMsgList.size() + " messages");
                        for (Msg msg : msgSyncResponse.AddMsgList) {
                            if (msg.MsgType == 51) {
                                HomeActivity.this.handleInitNotifyMsg(msg);
                            }
                            if (msg.MsgType == 1 || msg.MsgType == 34) {
                                Log.d(HomeActivity.TAG, "syncMsg:text=" + msg.Content + " msgId= " + msg.MsgId);
                                if (WxHome.isGroupUserName(msg.FromUserName) && !HomeActivity.this.chatSet.contains(msg.FromUserName)) {
                                    HomeActivity.this.addNewGroupThenProcessMsg(msg.FromUserName, msg);
                                } else if (!WxHome.isGroupUserName(msg.ToUserName) || HomeActivity.this.chatSet.contains(msg.ToUserName)) {
                                    HomeActivity.this.processMsg(msg);
                                } else {
                                    HomeActivity.this.addNewGroupThenProcessMsg(msg.ToUserName, msg);
                                }
                            }
                        }
                        SyncKey unused = HomeActivity.this.syncKey = msgSyncResponse.SyncKey;
                        HomeActivity.this.mHomeJobService.setSyncKey(HomeActivity.this.syncKey.toString());
                    }
                }
            }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    Log.e(HomeActivity.TAG, "syncMsg:error" + error.getMessage(), error);
                }
            });
            cookieRequest.setCookie(this.token.cookie);
            this.mQueue.add(cookieRequest);
        }
    }

    /* access modifiers changed from: private */
    public void handleInitNotifyMsg(Msg initMsg) {
        Log.d(TAG, "handleInitNotifyMsg: ");
//        Log.d(TAG, "handleInitNotifyMsg:" + JSON.toJSONString(initMsg));
        if (!TextUtils.isEmpty(initMsg.StatusNotifyUserName)) {
            for (String name : initMsg.StatusNotifyUserName.split(",")) {
                if (name.startsWith("@")) {
                    Msg msg = new Msg();
                    msg.MsgType = 51;
                    msg.FromUserName = name;
                    msg.ToUserName = this.user.UserName;
                    if (!WxHome.isGroupUserName(msg.FromUserName) || this.chatSet.contains(msg.FromUserName)) {
                        processMsg(msg);
                    } else {
                        addNewGroupThenProcessMsg(msg.FromUserName, msg);
                    }
                }
            }
        }
        Log.d(TAG, "handleInitNotifyMsg: end");
    }

    /* access modifiers changed from: private */
    public void processMsg(Msg msg) {
        String[] cols;
        if (msg.MsgType == 51) {
            Contact contact = getContact(msg.FromUserName);
            if (contact != null && !contact.isPublic()) {
                msg.fromNickName = contact.getShowName();
                msg.toNickName = this.user.NickName;
                this.initFragment.comeNewMessage(msg);
                return;
            }
            return;
        }
        if (WxHome.isGroupUserName(msg.FromUserName) && (cols = msg.Content.split(":<br/>")) != null && cols.length == 2) {
            msg.fromMemberUserName = cols[0].trim();
            msg.fromMemberNickName = getShowName(msg.FromUserName, msg.fromMemberUserName);
            msg.Content = cols[1].trim();
        }
        msg.Content = msg.Content.replaceAll("<br/>", "\n");
        if (msg.FromUserName.equals(this.user.UserName)) {
            msg.fromNickName = this.user.NickName;
            if (msg.ToUserName.equals(this.user.UserName)) {
                msg.toNickName = this.user.NickName;
            } else {
                msg.toNickName = getShowName(msg.ToUserName);
            }
        } else {
            msg.fromNickName = getShowName(msg.FromUserName);
            msg.toNickName = this.user.NickName;
        }
        Log.d(TAG, "processMsg:fromNickname=" + msg.fromNickName + " toNickName=" + msg.toNickName);
        msg.CreateTime = TimeUtil.toTimeMillis(msg.CreateTime);
        if (msg.MsgType == 34) {
            msg.Content = msg.MsgId + ".mp3";
            new VoiceTask().execute(msg.MsgId);
        }
        msg.ClientMsgId = WxHome.randomClientMsgId();
        this.initFragment.comeNewMessage(msg);
        Log.d(TAG, "processMsg:send broadcast, msgType=" + msg.MsgType);
        broadcastMsg(msg);
        if (!msg.FromUserName.equals(this.user.UserName) && !isMutedByUserName(msg.FromUserName)) {
            sendNotification(msg);
        }
    }

    /* access modifiers changed from: private */
    public void addNewGroupThenProcessMsg(String newGroupName, final Msg msg) {
        final List<String> newChatSet = new ArrayList<>();
        newChatSet.add(newGroupName);
        String url = WxHome.getBatchContactUrl(this.token);
        BatchContactRequest request = WxHome.formBatchContactRequest(this.token, newChatSet);
        if (request != null && !request.List.isEmpty()) {
            Log.d(TAG, "addNewGroupThenProcessMsg: ");
//            Log.d(TAG, "addNewGroupThenProcessMsg:" + JSON.toJSONString(request));
            CookieRequest cookieRequest = new CookieRequest(1, url, JSON.toJSONString(request), new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject response) {
                    Log.d(HomeActivity.TAG, "addNewGroupThenProcessMsg:" + response.toString());
                    HomeActivity.this.chatSet.addAll(newChatSet);
                    HomeActivity.this.exContactList.addAll(JSON.parseObject(response.toString(), BatchContactResponse.class).ContactList);
                    HomeActivity.this.processMsg(msg);
                }
            }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    Log.e(HomeActivity.TAG, "addNewGroupThenProcessMsg:error " + error.getMessage(), error);
                }
            });
            cookieRequest.setCookie(this.token.cookie);
            cookieRequest.setRetryPolicy(new DefaultRetryPolicy(2500, 1, 2.0f));
            this.mQueue.add(cookieRequest);
        }
    }

    private void broadcastMsg(Msg msg) {
        Intent intent = new Intent(Constants.Action.NEW_MSG);
        intent.putExtras(msg.toBundle());
        sendBroadcast(intent);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        if (this.closeReceiver != null) {
            unregisterReceiver(this.closeReceiver);
        }
        if (this.rescheduleReceiver != null) {
            unregisterReceiver(this.rescheduleReceiver);
        }
        if (this.networkStateReceiver != null) {
            unregisterReceiver(this.networkStateReceiver);
        }
        super.onDestroy();
        if (this.mHomeJobService != null) {
            this.mHomeJobService.stopSelf();
        }
    }

    /* access modifiers changed from: protected */
    public void onSwipeBack() {
        if (this.mIsLoaded) {
            super.onSwipeBack();
            return;
        }
        cancelJob();
        finish();
    }

    public boolean checkMobileDataConnection(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            if (!activeNetworkInfo.isConnectedOrConnecting()) {
                if (activeNetworkInfo.getDetailedState() == NetworkInfo.DetailedState.BLOCKED) {
                    Log.d(TAG, "Internet permission is blocked, request for permission.");
                } else {
                    updateForgroundNotification(getString(R.string.notification_bad_net_notice), HomeJobService.NotificationStatus.badNetwork);
                }
            } else if (activeNetworkInfo.getType() == 0) {
                updateForgroundNotification(getString(R.string.notification_using_mobile_data), HomeJobService.NotificationStatus.mobileData);
                return true;
            } else {
                updateForgroundNotification(getString(R.string.notification_normal), HomeJobService.NotificationStatus.normal);
                return true;
            }
        }
        return false;
    }

    private void updateForgroundNotification(String content, HomeJobService.NotificationStatus notificationStatus) {
        if (this.mHomeJobService != null) {
            this.mHomeJobService.updateNotification(content, notificationStatus);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length <= 0 || grantResults[0] != 0) {
                    updateForgroundNotification(getString(R.string.notification_internet_permission_denied), HomeJobService.NotificationStatus.permissionDenied);
                    return;
                }
                return;
            default:
                return;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(HomeActivity.this, "再按一次返回键退出微信!", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                mHandler.removeMessages(21);
                finish();
            }
        }
        return true;
    }

    private class VoiceTask extends AsyncTask<String, Void, String> {
        private VoiceTask() {
        }

        /* access modifiers changed from: protected */
        public String doInBackground(String... strings) {
            String url = WxHome.getVoiceUrl(HomeActivity.this.token, strings[0]);
            String outDir = Environment.getExternalStorageDirectory() + Constants.AUDIO_DIRECTORY;
            FileUtil.createDir(outDir);
            String outPutPath = outDir + strings[0] + ".mp3";
            Log.d(HomeActivity.TAG, "VoiceTask::audio output path=" + outPutPath);
            try {
                HttpsURLConnection conn = NetUtil.getHttpsConnection(url, 0);
                conn.setRequestProperty(Token.COOKIE, HomeActivity.this.token.cookie);
                conn.setChunkedStreamingMode(StreamUtil.CHUNK_LENGTH);
                conn.connect();
                FileOutputStream fileOutputStream = new FileOutputStream(outPutPath);
                StreamUtil.fromInStreamToOutStream(conn.getInputStream(), fileOutputStream);
                fileOutputStream.close();
                conn.disconnect();
                return outPutPath;
            } catch (IOException e) {
                Log.w(HomeActivity.TAG, "VoiceTask::exception", e);
                return "";
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String filePath) {
            Log.d(HomeActivity.TAG, "VoiceTask::onPostExecute:filePath=" + filePath);
        }
    }

    private class CloseReceiver extends BroadcastReceiver {
        private CloseReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (Constants.Action.CLOSE_APP.equals(intent.getAction())) {
                HomeActivity.this.cancelJob();
                HomeActivity.this.closeApp();
                HomeActivity.this.clearFiles();
            }
        }
    }

    private class RescheduleReceiver extends BroadcastReceiver {
        private RescheduleReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (Constants.Action.RESCHEDULE.equals(intent.getAction())) {
                long period = intent.getLongExtra("period", Constants.Period.HOME_STANDARD);
                Log.d("setting", "period=" + period);
                HomeActivity.this.cancelJob();
                HomeActivity.access$3208();
                HomeActivity.this.scheduleJob(period);
            }
        }
    }

    private class NetworkStateReceiver extends BroadcastReceiver {
        private NetworkStateReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                HomeActivity.this.checkMobileDataConnection(context);
            }
        }
    }
}
