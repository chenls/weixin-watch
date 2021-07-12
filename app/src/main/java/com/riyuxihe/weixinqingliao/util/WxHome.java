/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.riyuxihe.weixinqingliao.util;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.riyuxihe.weixinqingliao.model.Contact;
import com.riyuxihe.weixinqingliao.model.Msg;
import com.riyuxihe.weixinqingliao.model.SyncKey;
import com.riyuxihe.weixinqingliao.model.Token;
import com.riyuxihe.weixinqingliao.protocol.BaseRequest;
import com.riyuxihe.weixinqingliao.protocol.BatchContactRequest;
import com.riyuxihe.weixinqingliao.protocol.InitRequest;
import com.riyuxihe.weixinqingliao.protocol.MsgRequest;
import com.riyuxihe.weixinqingliao.protocol.MsgSyncRequest;
import com.riyuxihe.weixinqingliao.protocol.StatusNotifyRequest;
import com.riyuxihe.weixinqingliao.util.NetUtil;
import com.riyuxihe.weixinqingliao.util.StringUtil;
import com.riyuxihe.weixinqingliao.util.WxLogin;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class WxHome {
    public static final String DEFAULT_HEAD_IMG = "https://res.wx.qq.com/a/wx_fed/webwx/res/static/img/2KriyDK.png";
    private static final String DIGITS = "0123456789";
    private static final String INIT_URI = "/cgi-bin/mmwebwx-bin/webwxinit?r=%d&&lang=zh_CN&pass_ticket=%s";
    public static final String RETCODE = "retcode";
    public static final String SELECTOR = "selector";
    public static final String SYNC_CHECK_KEY = "window.synccheck";
    private static final String TAG = "WxHome";
    private static final String WX_CONTACT_EX = "/cgi-bin/mmwebwx-bin/webwxbatchgetcontact?type=ex&lang=zh_CN&pass_ticket=%s";
    private static final String WX_CONTACT_URI = "/cgi-bin/mmwebwx-bin/webwxgetcontact?seq=%d&pass_ticket=%s";
    private static final String WX_GET_HEAD = "/cgi-bin/mmwebwx-bin/webwxgetheadimg?username=%s&skey=%s";
    private static final String WX_GET_ICON = "/cgi-bin/mmwebwx-bin/webwxgeticon?username=%s&skey=%s";
    private static final String WX_GET_VOICE = "/cgi-bin/mmwebwx-bin/webwxgetvoice?skey=%s&msgid=%s";
    private static final String WX_MSG_SYNC = "/cgi-bin/mmwebwx-bin/webwxsync?sid=%s&skey=%s&lang=zh_CN";
    private static final String WX_SEND_URI = "/cgi-bin/mmwebwx-bin/webwxsendmsg?pass_ticket=%s";
    private static final String WX_STATUS_NOTIFY = "/cgi-bin/mmwebwx-bin/webwxstatusnotify?lang=zh_CN&pass_ticket=%s";
    private static final String WX_SYNC_URL_FORMAT = "%s://webpush.%s/cgi-bin/mmwebwx-bin/synccheck";
    private static String deviceId;
    private static SecureRandom secureRandom;
    private static String wxBaseUri;
    private static String wxHost;
    private static String wxSchema;

    static {
        secureRandom = new SecureRandom();
        wxBaseUri = "https://wx.qq.com";
        wxSchema = "https";
        wxHost = "wx.qq.com";
    }

    private static BaseRequest formBaseRequest(Token token) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.Skey = token.getSkey();
        baseRequest.Sid = token.getWxsid();
        baseRequest.Uin = token.getWxuin();
        baseRequest.DeviceID = WxHome.randomDeviceId();
        return baseRequest;
    }

    public static BatchContactRequest formBatchContactRequest(Token object, List<String> object2) {
        BatchContactRequest batchContactRequest = new BatchContactRequest();
        batchContactRequest.BaseRequest = WxHome.formBaseRequest((Token)object);
        object = new ArrayList();
        batchContactRequest.Count = 0;
        object2 = object2.iterator();
        while (object2.hasNext()) {
            String string2 = (String)object2.next();
            if ("filehelper".equals(string2)) continue;
            Contact contact = new Contact();
            contact.UserName = string2;
            object.add(contact);
            ++batchContactRequest.Count;
        }
        batchContactRequest.List = object;
        return batchContactRequest;
    }

    public static InitRequest formInitRequest(Token token) {
        InitRequest initRequest = new InitRequest();
        initRequest.BaseRequest = WxHome.formBaseRequest(token);
        return initRequest;
    }

    public static MsgRequest formMsgRequest(Token token, Msg msg) {
        MsgRequest msgRequest = new MsgRequest();
        msgRequest.BaseRequest = WxHome.formBaseRequest(token);
        msgRequest.Msg = msg;
        return msgRequest;
    }

    public static MsgSyncRequest formMsgSyncRequest(Token token, SyncKey syncKey) {
        MsgSyncRequest msgSyncRequest = new MsgSyncRequest();
        msgSyncRequest.BaseRequest = WxHome.formBaseRequest(token);
        msgSyncRequest.SyncKey = syncKey;
        return msgSyncRequest;
    }

    public static StatusNotifyRequest formStatusNotifyRequest(Token token, String string2) {
        StatusNotifyRequest statusNotifyRequest = new StatusNotifyRequest();
        statusNotifyRequest.BaseRequest = WxHome.formBaseRequest(token);
        statusNotifyRequest.FromUserName = string2;
        statusNotifyRequest.ToUserName = string2;
        statusNotifyRequest.ClientMsgId = WxHome.randomClientMsgId();
        return statusNotifyRequest;
    }

    public static Map<String, String> formSyncParams(Token token, String string2, String string3) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("_", String.valueOf(System.currentTimeMillis()));
        hashMap.put("skey", token.getSkey());
        hashMap.put("sid", token.getWxsid());
        hashMap.put("uin", token.getWxuin());
        hashMap.put("deviceid", string2);
        hashMap.put("synckey", string3);
        hashMap.put("r", String.valueOf(System.currentTimeMillis()));
        return hashMap;
    }

    public static String getBatchContactUrl(Token token) {
        return String.format(wxBaseUri + WX_CONTACT_EX, token.getPassTicket());
    }

    public static String getContactUrl(Token token, int n2) {
        return String.format(wxBaseUri + WX_CONTACT_URI, n2, token.getPassTicket());
    }

    public static String getHeadImgUrl(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return DEFAULT_HEAD_IMG;
        }
        return wxBaseUri + string2;
    }

    public static String getHeadUrlByUsername(Token token, String string2) {
        return String.format(wxBaseUri + WX_GET_HEAD, string2, token.getSkey());
    }

    public static String getIconUrlByUsername(Token token, String string2) {
        return String.format(wxBaseUri + WX_GET_ICON, string2, token.getSkey());
    }

    public static String getInitUrl(Token token) {
        return String.format(wxBaseUri + INIT_URI, System.currentTimeMillis(), token.getPassTicket());
    }

    public static String getMsgSyncUrl(Token token) {
        return String.format(wxBaseUri + WX_MSG_SYNC, token.getWxsid(), token.getSkey());
    }

    public static String getSendUrl(Token token) {
        return String.format(wxBaseUri + WX_SEND_URI, token.getPassTicket());
    }

    public static String getVoiceUrl(Token token, String string2) {
        return String.format(wxBaseUri + WX_GET_VOICE, token.getSkey(), string2);
    }

    public static String getWxStatusNotifyUrl(Token token) {
        return String.format(wxBaseUri + WX_STATUS_NOTIFY, token.getPassTicket());
    }

    public static boolean isGroupUserName(String string2) {
        return string2 != null && string2.startsWith("@@");
    }

    public static long randomClientMsgId() {
        return System.currentTimeMillis() << 3766;
    }

    public static String randomDeviceId() {
        if (StringUtil.isNullOrEmpty(deviceId)) {
            StringBuilder stringBuilder = new StringBuilder(16);
            stringBuilder.append("e");
            for (int i2 = 0; i2 < 15; ++i2) {
                stringBuilder.append(DIGITS.charAt(secureRandom.nextInt(DIGITS.length())));
            }
            deviceId = stringBuilder.toString();
        }
        return deviceId;
    }

    public static void setWxBaseUri(String string2, String string3) {
        wxSchema = string2;
        wxHost = string3;
        wxBaseUri = string2 + "://" + string3;
        Log.d((String)TAG, (String)("setWxBaseUri:base uri=" + wxBaseUri));
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Properties syncCheck(Token object, String object2, String string2) {
        object2 = WxHome.formSyncParams((Token)object, (String)object2, string2);
        try {
            string2 = Uri.parse((String)String.format(WX_SYNC_URL_FORMAT, wxSchema, wxHost)).buildUpon();
            for (String string3 : object2.keySet()) {
                string2.appendQueryParameter(string3, (String)object2.get(string3));
            }
        }
        catch (IOException iOException) {
            Log.w((String)TAG, (String)"syncCheck:exception", (Throwable)iOException);
            return null;
        }
        {
            object2 = string2.build().toString();
            Log.d((String)TAG, (String)("syncCheck:url=" + (String)object2));
            object = NetUtil.getHttpsResponse((String)object2, 30000, ((Token)object).cookie);
            Log.d((String)TAG, (String)("syncCheck:result=" + (String)object));
            return WxLogin.parseText((String)object);
        }
    }
}

