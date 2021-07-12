/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Notification
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.app.job.JobInfo$Builder
 *  android.app.job.JobScheduler
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.ServiceConnection
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo$DetailedState
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.os.Environment
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Message
 *  android.os.Messenger
 *  android.os.Parcelable
 *  android.text.TextUtils
 *  android.util.Log
 *  android.widget.ProgressBar
 *  org.json.JSONObject
 */
package com.riyuxihe.weixinqingliao;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ProgressBar;
import com.alibaba.fastjson.JSON;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mobvoi.wear.app.PermissionCompat;
import com.riyuxihe.weixinqingliao.ChatActivity;
import com.riyuxihe.weixinqingliao.ContactFragment;
import com.riyuxihe.weixinqingliao.HomeJobService;
import com.riyuxihe.weixinqingliao.HomeService;
import com.riyuxihe.weixinqingliao.InitFragment;
import com.riyuxihe.weixinqingliao.SwipeActivity;
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
import com.riyuxihe.weixinqingliao.util.FileUtil;
import com.riyuxihe.weixinqingliao.util.NetUtil;
import com.riyuxihe.weixinqingliao.util.StreamUtil;
import com.riyuxihe.weixinqingliao.util.StringUtil;
import com.riyuxihe.weixinqingliao.util.TimeUtil;
import com.riyuxihe.weixinqingliao.util.WxHome;
import com.umeng.analytics.MobclickAgent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.json.JSONObject;

public class HomeActivity
extends SwipeActivity {
    public static final int HOME_SERVICE_OBJ = 20;
    private static final int PERMISSIONS_REQUEST_INTERNET = 1;
    private static final String TAG = "HomeActivity";
    private static int VIEW_COUNT = 2;
    private static int kJobId = 2;
    private List<String> chatSet;
    private CloseReceiver closeReceiver;
    private HomeConnection conn;
    private ContactFragment contactFragment;
    private ArrayList<Contact> contactList;
    private boolean contactLoaded = false;
    private ArrayList<Contact> exContactList;
    private boolean exContactLoaded = false;
    private InitFragment initFragment;
    private ArrayList<Contact> initList;
    private boolean initLoaded = false;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    Handler mHandler;
    HomeJobService mHomeJobService;
    private boolean mIsLoaded = false;
    private ProgressBar mProgressBar;
    private RequestQueue mQueue;
    private TabLayout mTabLayout;
    private List<String> mTitles = new ArrayList<String>();
    private ViewPager mViewPager;
    private HomeService.HomeBinder myBinder;
    private NetworkStateReceiver networkStateReceiver;
    private RescheduleReceiver rescheduleReceiver;
    private SyncKey syncKey;
    private Token token;
    private User user;

    public HomeActivity() {
        this.contactList = new ArrayList();
        this.exContactList = new ArrayList();
        this.chatSet = new ArrayList<String>();
        this.mHandler = new Handler(){

            public void handleMessage(Message message) {
                switch (message.what) {
                    default: {
                        return;
                    }
                    case 20: 
                }
                HomeActivity.this.mHomeJobService = (HomeJobService)((Object)message.obj);
                HomeActivity.this.mHomeJobService.setUiCallback(HomeActivity.this);
                message = HomeActivity.this.getPreferences(0);
                HomeActivity.this.scheduleJob(message.getLong("sync_period", 60000L));
            }
        };
    }

    static /* synthetic */ boolean access$1302(HomeActivity homeActivity, boolean bl2) {
        homeActivity.initLoaded = bl2;
        return bl2;
    }

    static /* synthetic */ boolean access$1602(HomeActivity homeActivity, boolean bl2) {
        homeActivity.contactLoaded = bl2;
        return bl2;
    }

    static /* synthetic */ boolean access$1902(HomeActivity homeActivity, boolean bl2) {
        homeActivity.exContactLoaded = bl2;
        return bl2;
    }

    static /* synthetic */ HomeService.HomeBinder access$2302(HomeActivity homeActivity, HomeService.HomeBinder homeBinder) {
        homeActivity.myBinder = homeBinder;
        return homeBinder;
    }

    static /* synthetic */ int access$3208() {
        int n2 = kJobId;
        kJobId = n2 + 1;
        return n2;
    }

    static /* synthetic */ User access$402(HomeActivity homeActivity, User user) {
        homeActivity.user = user;
        return user;
    }

    static /* synthetic */ ArrayList access$502(HomeActivity homeActivity, ArrayList arrayList) {
        homeActivity.initList = arrayList;
        return arrayList;
    }

    static /* synthetic */ SyncKey access$702(HomeActivity homeActivity, SyncKey syncKey) {
        homeActivity.syncKey = syncKey;
        return syncKey;
    }

    private void addNewGroupThenProcessMsg(String object, final Msg msg) {
        final ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add((String)object);
        object = WxHome.getBatchContactUrl(this.token);
        BatchContactRequest batchContactRequest = WxHome.formBatchContactRequest(this.token, arrayList);
        if (batchContactRequest == null || batchContactRequest.List.isEmpty()) {
            return;
        }
        Log.d((String)TAG, (String)("addNewGroupThenProcessMsg:" + JSON.toJSONString(batchContactRequest)));
        object = new CookieRequest(1, (String)object, JSON.toJSONString(batchContactRequest), new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject object) {
                Log.d((String)HomeActivity.TAG, (String)("addNewGroupThenProcessMsg:" + object.toString()));
                HomeActivity.this.chatSet.addAll(arrayList);
                object = JSON.parseObject(object.toString(), BatchContactResponse.class);
                HomeActivity.this.exContactList.addAll(object.ContactList);
                HomeActivity.this.processMsg(msg);
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e((String)HomeActivity.TAG, (String)("addNewGroupThenProcessMsg:error " + volleyError.getMessage()), (Throwable)volleyError);
            }
        });
        ((CookieRequest)object).setCookie(this.token.cookie);
        ((Request)object).setRetryPolicy(new DefaultRetryPolicy(2500, 1, 2.0f));
        this.mQueue.add(object);
    }

    private void bindHomeService(Token token, String string2) {
        Intent intent = new Intent((Context)this, HomeService.class);
        intent.putExtra("token", token.toBundle());
        intent.putExtra("deviceId", string2);
        intent.putExtra("syncKey", this.syncKey.toString());
        this.conn = new HomeConnection();
        this.bindService(intent, this.conn, 1);
    }

    private void broadcastMsg(Msg msg) {
        Intent intent = new Intent("com.riyuxihe.weixinqingliao.MSG");
        intent.putExtras(msg.toBundle());
        this.sendBroadcast(intent);
    }

    private void cancelJob() {
        ((JobScheduler)this.getSystemService("jobscheduler")).cancel(kJobId);
    }

    private void clearFiles() {
        File file = new File(Environment.getExternalStorageDirectory() + "/weixinQingliao/");
        if (file.isDirectory()) {
            String[] stringArray = file.list();
            for (int i2 = 0; i2 < stringArray.length; ++i2) {
                new File(file, stringArray[i2]).delete();
            }
        }
    }

    private void closeApp() {
        this.runOnUiThread(new Runnable(){

            @Override
            public void run() {
                HomeActivity.this.finish();
            }
        });
        Notification notification = new NotificationCompat.Builder((Context)this).setSmallIcon(2130903040).setContentTitle(this.getString(2131230783)).setContentText("\u5df2\u9000\u51fa").setAutoCancel(true).build();
        ((NotificationManager)this.getSystemService("notification")).notify(10010, notification);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void filterDunpFilehelper(ArrayList<Contact> arrayList) {
        boolean bl2 = false;
        int n2 = 0;
        while (n2 < arrayList.size()) {
            boolean bl3;
            if (arrayList.get((int)n2).UserName.equals("filehelper")) {
                if (bl2) {
                    arrayList.remove(n2);
                    bl3 = bl2;
                } else {
                    bl3 = true;
                }
            } else {
                bl3 = bl2;
                if (!arrayList.get((int)n2).UserName.startsWith("@")) {
                    arrayList.remove(n2);
                    bl3 = bl2;
                }
            }
            ++n2;
            bl2 = bl3;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void handleInitNotifyMsg(Msg stringArray) {
        Log.d((String)TAG, (String)("handleInitNotifyMsg:" + JSON.toJSONString(stringArray)));
        if (!TextUtils.isEmpty((CharSequence)stringArray.StatusNotifyUserName)) {
            for (String string2 : stringArray.StatusNotifyUserName.split(",")) {
                if (!string2.startsWith("@")) continue;
                Msg msg = new Msg();
                msg.MsgType = 51;
                msg.FromUserName = string2;
                msg.ToUserName = this.user.UserName;
                if (WxHome.isGroupUserName(msg.FromUserName) && !this.chatSet.contains(msg.FromUserName)) {
                    this.addNewGroupThenProcessMsg(msg.FromUserName, msg);
                    continue;
                }
                this.processMsg(msg);
            }
        }
    }

    private void initBatchContact(List<String> object) {
        String string2 = WxHome.getBatchContactUrl(this.token);
        if ((object = WxHome.formBatchContactRequest(this.token, object)) == null || ((BatchContactRequest)object).List.isEmpty()) {
            Log.d((String)TAG, (String)"intBatchContact:skipping get BatchContact");
            this.exContactLoaded = true;
            return;
        }
        Log.d((String)TAG, (String)("initBatchContact:" + JSON.toJSONString(object)));
        object = new CookieRequest(1, string2, JSON.toJSONString(object), new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject object) {
                Log.d((String)HomeActivity.TAG, (String)("initBatchContact:" + object.toString()));
                object = JSON.parseObject(object.toString(), BatchContactResponse.class);
                HomeActivity.this.exContactList.addAll(object.ContactList);
                HomeActivity.access$1902(HomeActivity.this, true);
                HomeActivity.this.onExComplete();
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e((String)HomeActivity.TAG, (String)("initBatchContact:error " + volleyError.getMessage()), (Throwable)volleyError);
            }
        });
        ((CookieRequest)object).setCookie(this.token.cookie);
        ((Request)object).setRetryPolicy(new DefaultRetryPolicy(8000, 1, 2.0f));
        this.mQueue.add(object);
    }

    private void initContact(int n2) {
        CookieRequest cookieRequest = new CookieRequest(1, WxHome.getContactUrl(this.token, n2), new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject object) {
                Log.d((String)HomeActivity.TAG, (String)("initContact:" + object.toString()));
                object = JSON.parseObject(object.toString(), ContactResponse.class);
                HomeActivity.this.contactList.addAll(object.MemberList);
                if (object.Seq == 0) {
                    Collections.sort(HomeActivity.this.contactList);
                    HomeActivity.access$1602(HomeActivity.this, true);
                    HomeActivity.this.onInitComplete();
                    return;
                }
                HomeActivity.this.initContact(object.Seq);
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError instanceof NoConnectionError) {
                    HomeActivity.access$1602(HomeActivity.this, true);
                    HomeActivity.this.onInitComplete();
                    Log.w((String)HomeActivity.TAG, (String)("initContact:error " + volleyError.getMessage()));
                    return;
                }
                Log.e((String)HomeActivity.TAG, (String)("initContact:error " + volleyError.getMessage()), (Throwable)volleyError);
            }
        });
        cookieRequest.setCookie(this.token.cookie);
        cookieRequest.setRetryPolicy(new DefaultRetryPolicy(15000, 1, 2.0f));
        this.mQueue.add(cookieRequest);
    }

    private void initViews() {
        this.mViewPager = (ViewPager)this.findViewById(2131689611);
        this.mTabLayout = (TabLayout)this.findViewById(2131689610);
        this.mProgressBar = (ProgressBar)this.findViewById(2131689612);
    }

    private void initWx() {
        Object object = WxHome.getInitUrl(this.token);
        InitRequest initRequest = WxHome.formInitRequest(this.token);
        Log.d((String)TAG, (String)("initWx:" + JSON.toJSONString(initRequest)));
        object = new CookieRequest(1, (String)object, JSON.toJSONString(initRequest), new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject stringArray) {
                Log.d((String)HomeActivity.TAG, (String)("initWx:" + stringArray.toString()));
                stringArray = JSON.parseObject(stringArray.toString(), InitResponse.class);
                HomeActivity.access$402(HomeActivity.this, stringArray.User);
                HomeActivity.access$502(HomeActivity.this, stringArray.ContactList);
                HomeActivity.this.filterDunpFilehelper(HomeActivity.this.initList);
                HomeActivity.access$702(HomeActivity.this, stringArray.SyncKey);
                HomeActivity.this.notifyStatus();
                for (String string2 : stringArray.ChatSet.split(",")) {
                    if (!string2.startsWith("@") && !"filehelper".equals(string2)) continue;
                    HomeActivity.this.chatSet.add(string2);
                }
                HomeActivity.this.initBatchContact(HomeActivity.this.chatSet);
                HomeActivity.this.startHomeJobService(HomeActivity.this.token, WxHome.randomDeviceId());
                HomeActivity.access$1302(HomeActivity.this, true);
                HomeActivity.this.onInitComplete();
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e((String)HomeActivity.TAG, (String)("initWx:error " + volleyError.getMessage()), (Throwable)volleyError);
            }
        });
        ((CookieRequest)object).setCookie(this.token.cookie);
        ((Request)object).setRetryPolicy(new DefaultRetryPolicy(7000, 1, 2.0f));
        this.mQueue.add(object);
    }

    private void notifyStatus() {
        Object object = WxHome.getWxStatusNotifyUrl(this.token);
        StatusNotifyRequest statusNotifyRequest = WxHome.formStatusNotifyRequest(this.token, this.user.UserName);
        Log.d((String)TAG, (String)("notifyStatus:" + JSON.toJSONString(statusNotifyRequest)));
        object = new CookieRequest(1, (String)object, JSON.toJSONString(statusNotifyRequest), new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject jSONObject) {
                Log.d((String)HomeActivity.TAG, (String)("notifyStatus:" + jSONObject.toString()));
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e((String)HomeActivity.TAG, (String)("notifyStatus:error " + volleyError.getMessage()), (Throwable)volleyError);
            }
        });
        ((CookieRequest)object).setCookie(this.token.cookie);
        ((Request)object).setRetryPolicy(new DefaultRetryPolicy(8000, 1, 2.0f));
        this.mQueue.add(object);
    }

    private void onExComplete() {
        if (!this.mIsLoaded || !this.exContactLoaded) {
            return;
        }
        if (this.exContactList != null) {
            this.initFragment.addExInitList(this.exContactList);
        }
        this.syncMsg();
        this.syncMsg();
    }

    private void onInitComplete() {
        if (!this.initLoaded || !this.contactLoaded) {
            return;
        }
        if (this.contactList.isEmpty() && this.initList != null && !this.initList.isEmpty()) {
            this.contactList.addAll(this.initList);
            Collections.sort(this.contactList);
        }
        this.mProgressBar.setVisibility(8);
        this.mTitles.add("\u6d88\u606f");
        this.mTitles.add("\u8054\u7cfb\u4eba");
        this.initFragment = InitFragment.newInstance(this.token, this.user, this.initList);
        this.mFragments.add(this.initFragment);
        this.contactFragment = ContactFragment.newInstance(this.token, this.user, this.contactList);
        this.mFragments.add(this.contactFragment);
        this.mAdapter = new FragmentPagerAdapter(this.getSupportFragmentManager()){

            @Override
            public int getCount() {
                return HomeActivity.this.mFragments.size();
            }

            @Override
            public Fragment getItem(int n2) {
                return (Fragment)HomeActivity.this.mFragments.get(n2);
            }

            @Override
            public CharSequence getPageTitle(int n2) {
                return (CharSequence)HomeActivity.this.mTitles.get(n2);
            }
        };
        this.mViewPager.setAdapter(this.mAdapter);
        for (int i2 = 0; i2 < VIEW_COUNT; ++i2) {
            this.mTabLayout.addTab(this.mTabLayout.newTab());
        }
        this.mTabLayout.setTabMode(0);
        this.mTabLayout.setupWithViewPager(this.mViewPager);
        this.mIsLoaded = true;
        this.onExComplete();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void processMsg(Msg msg) {
        String[] stringArray;
        if (msg.MsgType == 51) {
            Contact contact = this.getContact(msg.FromUserName);
            if (contact == null || contact.isPublic()) return;
            msg.fromNickName = contact.getShowName();
            msg.toNickName = this.user.NickName;
            this.initFragment.comeNewMessage(msg);
            return;
        }
        if (WxHome.isGroupUserName(msg.FromUserName) && (stringArray = msg.Content.split(":<br/>")) != null && stringArray.length == 2) {
            msg.fromMemberUserName = stringArray[0].trim();
            msg.fromMemberNickName = this.getShowName(msg.FromUserName, msg.fromMemberUserName);
            msg.Content = stringArray[1].trim();
        }
        msg.Content = msg.Content.replaceAll("<br/>", "\n");
        if (msg.FromUserName.equals(this.user.UserName)) {
            msg.fromNickName = this.user.NickName;
            msg.toNickName = msg.ToUserName.equals(this.user.UserName) ? this.user.NickName : this.getShowName(msg.ToUserName);
        } else {
            msg.fromNickName = this.getShowName(msg.FromUserName);
            msg.toNickName = this.user.NickName;
        }
        Log.d((String)TAG, (String)("processMsg:fromNickname=" + msg.fromNickName + " toNickName=" + msg.toNickName));
        msg.CreateTime = TimeUtil.toTimeMillis(msg.CreateTime);
        if (msg.MsgType == 34) {
            msg.Content = msg.MsgId + ".mp3";
            new VoiceTask().execute(new String[]{msg.MsgId});
        }
        msg.ClientMsgId = WxHome.randomClientMsgId();
        this.initFragment.comeNewMessage(msg);
        Log.d((String)TAG, (String)("processMsg:send broadcast, msgType=" + msg.MsgType));
        this.broadcastMsg(msg);
        if (msg.FromUserName.equals(this.user.UserName) || this.isMutedByUserName(msg.FromUserName)) {
            return;
        }
        this.sendNotification(msg);
    }

    private void requestInternetPermission(Activity activity) {
        block3: {
            block2: {
                if (PermissionCompat.checkSelfPermission((Context)activity, "android.permission.INTERNET") == 0) break block2;
                if (!PermissionCompat.shouldShowRequestPermissionRationale(activity, "android.permission.INTERNET")) break block3;
                this.updateForgroundNotification(this.getString(2131230805), HomeJobService.NotificationStatus.permissionRational);
            }
            return;
        }
        PermissionCompat.requestPermissions(activity, new String[]{"android.permission.INTERNET"}, 1);
    }

    private void scheduleJob(long l2) {
        JobInfo.Builder builder = new JobInfo.Builder(kJobId, new ComponentName((Context)this, HomeJobService.class));
        builder.setPeriodic(l2);
        ((JobScheduler)this.getSystemService("jobscheduler")).schedule(builder.build());
    }

    private void sendNotification(Msg msg) {
        Log.d((String)TAG, (String)("sendNotification:content=" + msg.Content));
        Intent intent = new Intent((Context)this, ChatActivity.class);
        intent.putExtra("token", this.token.toBundle());
        User user = new User();
        user.UserName = msg.FromUserName;
        user.NickName = msg.fromNickName;
        user.HeadImgUrl = "";
        intent.putExtra("to", user.toBundle());
        intent.putExtra("from", this.user.toBundle());
        intent = PendingIntent.getActivity((Context)this, (int)msg.FromUserName.hashCode(), (Intent)intent, (int)0x8000000);
        intent = new NotificationCompat.Builder((Context)this).setSmallIcon(2130903040).setContentTitle(msg.fromNickName).setContentText(msg.Content).setContentIntent((PendingIntent)intent).setAutoCancel(true).build();
        ((NotificationManager)this.getSystemService("notification")).notify(msg.FromUserName.hashCode(), (Notification)intent);
    }

    private void startHomeJobService(Token token, String string2) {
        Intent intent = new Intent((Context)this, HomeJobService.class);
        intent.putExtra("messenger", (Parcelable)new Messenger(this.mHandler));
        intent.putExtra("token", token.toBundle());
        intent.putExtra("deviceId", string2);
        intent.putExtra("syncKey", this.syncKey.toString());
        this.startService(intent);
    }

    private void syncMsg() {
        if (!this.mIsLoaded || !this.exContactLoaded) {
            return;
        }
        Object object = WxHome.getMsgSyncUrl(this.token);
        MsgSyncRequest msgSyncRequest = WxHome.formMsgSyncRequest(this.token, this.syncKey);
        Log.d((String)TAG, (String)("syncMsg:" + JSON.toJSONString(msgSyncRequest)));
        object = new CookieRequest(1, (String)object, JSON.toJSONString(msgSyncRequest), new Response.Listener<JSONObject>(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void onResponse(JSONObject object) {
                Log.d((String)HomeActivity.TAG, (String)("syncMsg:" + object.toString()));
                MsgSyncResponse msgSyncResponse = JSON.parseObject(object.toString(), MsgSyncResponse.class);
                if (msgSyncResponse.BaseResponse.Ret != 0 || msgSyncResponse.SyncKey.toString().equals(HomeActivity.this.syncKey.toString())) {
                    return;
                }
                Log.d((String)HomeActivity.TAG, (String)("syncMsg:receive " + msgSyncResponse.AddMsgList.size() + " messages"));
                Iterator<Msg> iterator = msgSyncResponse.AddMsgList.iterator();
                while (true) {
                    if (!iterator.hasNext()) {
                        HomeActivity.access$702(HomeActivity.this, msgSyncResponse.SyncKey);
                        HomeActivity.this.mHomeJobService.setSyncKey(HomeActivity.this.syncKey.toString());
                        return;
                    }
                    Msg msg = iterator.next();
                    if (msg.MsgType == 51) {
                        HomeActivity.this.handleInitNotifyMsg(msg);
                    }
                    if (msg.MsgType != 1 && msg.MsgType != 34) continue;
                    Log.d((String)HomeActivity.TAG, (String)("syncMsg:text=" + msg.Content + " msgId= " + msg.MsgId));
                    if (WxHome.isGroupUserName(msg.FromUserName) && !HomeActivity.this.chatSet.contains(msg.FromUserName)) {
                        HomeActivity.this.addNewGroupThenProcessMsg(msg.FromUserName, msg);
                        continue;
                    }
                    if (WxHome.isGroupUserName(msg.ToUserName) && !HomeActivity.this.chatSet.contains(msg.ToUserName)) {
                        HomeActivity.this.addNewGroupThenProcessMsg(msg.ToUserName, msg);
                        continue;
                    }
                    HomeActivity.this.processMsg(msg);
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e((String)HomeActivity.TAG, (String)("syncMsg:error" + volleyError.getMessage()), (Throwable)volleyError);
            }
        });
        ((CookieRequest)object).setCookie(this.token.cookie);
        this.mQueue.add(object);
    }

    private void updateForgroundNotification(String string2, HomeJobService.NotificationStatus notificationStatus) {
        if (this.mHomeJobService != null) {
            this.mHomeJobService.updateNotification(string2, notificationStatus);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean checkMobileDataConnection(Context context) {
        if ((context = ((ConnectivityManager)context.getSystemService("connectivity")).getActiveNetworkInfo()) == null) return false;
        if (context.isConnectedOrConnecting()) {
            if (context.getType() == 0) {
                this.updateForgroundNotification(this.getString(2131230807), HomeJobService.NotificationStatus.mobileData);
                return true;
            }
            this.updateForgroundNotification(this.getString(2131230806), HomeJobService.NotificationStatus.normal);
            return true;
        }
        boolean bl2 = context.getDetailedState() == NetworkInfo.DetailedState.BLOCKED;
        if (bl2) {
            Log.d((String)TAG, (String)"Internet permission is blocked, request for permission.");
            this.runOnUiThread(new Runnable(){

                @Override
                public void run() {
                    HomeActivity.this.requestInternetPermission(HomeActivity.this);
                }
            });
            return false;
        }
        this.updateForgroundNotification(this.getString(2131230803), HomeJobService.NotificationStatus.badNetwork);
        return false;
    }

    public Contact getContact(String string2) {
        if (this.contactList == null) {
            Log.w((String)TAG, (String)"getShowName:contact list not initial");
            return null;
        }
        for (Contact contact : this.contactList) {
            if (!contact.UserName.equals(string2)) continue;
            return contact;
        }
        for (Contact contact : this.exContactList) {
            if (!contact.UserName.equals(string2)) continue;
            return contact;
        }
        return null;
    }

    public String getShowName(String object) {
        if ((object = this.getContact((String)object)) == null) {
            return "";
        }
        return ((Contact)object).getShowName();
    }

    public String getShowName(String string2, String string3) {
        if (this.exContactList == null) {
            Log.w((String)TAG, (String)"getShowName:extra Contact list not initial");
            return "";
        }
        if (StringUtil.isNullOrEmpty(string2)) {
            Log.w((String)TAG, (String)"getShowName:groupUserName can not be empty");
            return "";
        }
        for (Contact contact : this.exContactList) {
            if (!contact.UserName.equals(string2)) continue;
            for (Contact contact2 : contact.MemberList) {
                if (!contact2.UserName.equals(string3)) continue;
                return contact2.getShowName();
            }
        }
        return "";
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean isMutedByUserName(String string2) {
        Contact contact2;
        if (string2 == null) {
            return false;
        }
        for (Contact contact2 : this.exContactList) {
            if (!string2.equals(contact2.UserName)) continue;
            return contact2.isMuted();
        }
        if (this.contactList == null) return false;
        Iterator<Contact> iterator = this.contactList.iterator();
        do {
            if (!iterator.hasNext()) return false;
            contact2 = iterator.next();
        } while (!string2.equals(contact2.UserName));
        return contact2.isMuted();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968602);
        new MessageManager((Context)this).reCreateTable();
        bundle = this.getIntent();
        this.token = new Token();
        this.token.fromBundle(bundle.getExtras());
        Log.d((String)TAG, (String)("onCreate:token=" + JSON.toJSONString(this.token)));
        this.mQueue = VolleySingleton.getInstance().getRequestQueue();
        this.initViews();
        this.initWx();
        this.initContact(0);
        this.closeReceiver = new CloseReceiver();
        bundle = new IntentFilter();
        bundle.addAction("com.riyuxihe.weixinqingliao.CLOSEAPP");
        this.registerReceiver(this.closeReceiver, (IntentFilter)bundle);
        this.rescheduleReceiver = new RescheduleReceiver();
        bundle = new IntentFilter();
        bundle.addAction("com.riyuxihe.weixinqingliao.RESCHEDULE");
        this.registerReceiver(this.rescheduleReceiver, (IntentFilter)bundle);
    }

    @Override
    protected void onDestroy() {
        if (this.closeReceiver != null) {
            this.unregisterReceiver(this.closeReceiver);
        }
        if (this.rescheduleReceiver != null) {
            this.unregisterReceiver(this.rescheduleReceiver);
        }
        if (this.networkStateReceiver != null) {
            this.unregisterReceiver(this.networkStateReceiver);
        }
        super.onDestroy();
        if (this.conn != null) {
            this.unbindService(this.conn);
        }
        if (this.mHomeJobService != null) {
            this.mHomeJobService.stopSelf();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause((Context)this);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onRequestPermissionsResult(int n2, String[] stringArray, int[] nArray) {
        switch (n2) {
            default: {
                return;
            }
            case 1: {
                if (nArray.length > 0 && nArray[0] == 0) return;
                this.updateForgroundNotification(this.getString(2131230804), HomeJobService.NotificationStatus.permissionDenied);
                return;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume((Context)this);
    }

    @Override
    protected void onSwipeBack() {
        if (this.mIsLoaded) {
            super.onSwipeBack();
            return;
        }
        this.cancelJob();
        this.finish();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onSyncChecked(Properties object) {
        if (object == null || ((Hashtable)object).isEmpty()) return;
        object = ((Properties)object).getProperty("window.synccheck");
        Log.d((String)TAG, (String)("onSyncChecked:syncCheckStr=" + (String)object));
        object = JSON.parseObject((String)object);
        if ("0".equals(((com.alibaba.fastjson.JSONObject)object).getString("retcode"))) {
            int n2 = Integer.parseInt(((com.alibaba.fastjson.JSONObject)object).getString("selector"));
            if (n2 == 0) return;
            Log.d((String)TAG, (String)("onSyncChecked:new message, code=" + n2));
            this.syncMsg();
            return;
        }
        Log.d((String)TAG, (String)"onSyncChecked:sync fail");
        if (!this.mHomeJobService.unSync()) {
            return;
        }
        this.cancelJob();
        this.closeApp();
        this.clearFiles();
    }

    private class CloseReceiver
    extends BroadcastReceiver {
        private CloseReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (!"com.riyuxihe.weixinqingliao.CLOSEAPP".equals(intent.getAction())) {
                return;
            }
            HomeActivity.this.cancelJob();
            HomeActivity.this.closeApp();
            HomeActivity.this.clearFiles();
        }
    }

    private class HomeConnection
    implements ServiceConnection {
        private HomeConnection() {
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            HomeActivity.access$2302(HomeActivity.this, (HomeService.HomeBinder)iBinder);
            HomeActivity.this.myBinder.getService().setCallBack(new HomeService.CallBack(){

                /*
                 * Enabled aggressive block sorting
                 */
                @Override
                public void handleServiceData(Properties object) {
                    if (object == null || ((Hashtable)object).isEmpty()) return;
                    object = ((Properties)object).getProperty("window.synccheck");
                    Log.d((String)HomeActivity.TAG, (String)("CallBack::handleServiceData:syncCheckStr=" + (String)object));
                    object = JSON.parseObject((String)object);
                    String string2 = ((com.alibaba.fastjson.JSONObject)object).getString("retcode");
                    if ("0".equals(string2)) {
                        int n2 = Integer.parseInt(((com.alibaba.fastjson.JSONObject)object).getString("selector"));
                        if (n2 == 0) return;
                        Log.d((String)HomeActivity.TAG, (String)("CallBack::handleServiceData:new message, code=" + n2));
                        HomeActivity.this.syncMsg();
                        return;
                    }
                    if (!"1100".equals(string2)) {
                        return;
                    }
                    HomeActivity.this.myBinder.stopTimer();
                    HomeActivity.this.closeApp();
                    HomeActivity.this.clearFiles();
                }
            });
            HomeActivity.this.myBinder.startTimer();
        }

        public void onServiceDisconnected(ComponentName componentName) {
        }
    }

    private class NetworkStateReceiver
    extends BroadcastReceiver {
        private NetworkStateReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (!"android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                return;
            }
            HomeActivity.this.checkMobileDataConnection(context);
        }
    }

    private class RescheduleReceiver
    extends BroadcastReceiver {
        private RescheduleReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (!"com.riyuxihe.weixinqingliao.RESCHEDULE".equals(intent.getAction())) {
                return;
            }
            long l2 = intent.getLongExtra("period", 60000L);
            Log.d((String)"setting", (String)("period=" + l2));
            HomeActivity.this.cancelJob();
            HomeActivity.access$3208();
            HomeActivity.this.scheduleJob(l2);
        }
    }

    private class VoiceTask
    extends AsyncTask<String, Void, String> {
        private VoiceTask() {
        }

        protected String doInBackground(String ... object) {
            Object object2 = WxHome.getVoiceUrl(HomeActivity.this.token, object[0]);
            Object object3 = Environment.getExternalStorageDirectory() + "/weixinQingliao/";
            FileUtil.createDir((String)object3);
            object = (String)object3 + object[0] + ".mp3";
            Log.d((String)HomeActivity.TAG, (String)("VoiceTask::audio output path=" + (String)object));
            try {
                object2 = NetUtil.getHttpsConnection((String)object2, 0);
                ((URLConnection)object2).setRequestProperty("Cookie", ((HomeActivity)HomeActivity.this).token.cookie);
                ((HttpURLConnection)object2).setChunkedStreamingMode(StreamUtil.CHUNK_LENGTH);
                ((URLConnection)object2).connect();
                object3 = new FileOutputStream((String)object);
                StreamUtil.fromInStreamToOutStream(((URLConnection)object2).getInputStream(), (OutputStream)object3);
                ((FileOutputStream)object3).close();
                ((HttpURLConnection)object2).disconnect();
                return object;
            }
            catch (IOException iOException) {
                Log.w((String)HomeActivity.TAG, (String)"VoiceTask::exception", (Throwable)iOException);
                return "";
            }
        }

        protected void onPostExecute(String string2) {
            Log.d((String)HomeActivity.TAG, (String)("VoiceTask::onPostExecute:filePath=" + string2));
        }
    }
}

