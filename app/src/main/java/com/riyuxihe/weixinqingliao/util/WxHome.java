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

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class WxHome {
    public static final String DEFAULT_HEAD_IMG = "https://res.wx2.qq.com/a/wx_fed/webwx/res/static/img/2KriyDK.png";
    public static final String RETCODE = "retcode";
    public static final String SELECTOR = "selector";
    public static final String SYNC_CHECK_KEY = "window.synccheck";
    private static final String DIGITS = "0123456789";
    private static final String INIT_URI = "/cgi-bin/mmwebwx-bin/webwxinit?r=%d&&lang=zh_CN&pass_ticket=%s";
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
    private static final SecureRandom secureRandom = new SecureRandom();
    private static String deviceId;
    private static String wxBaseUri = "https://wx2.qq.com";
    private static String wxHost = "wx2.qq.com";
    private static String wxSchema = "https";

    public static void setWxBaseUri(String schema, String host) {
        wxSchema = schema;
        wxHost = host;
        wxBaseUri = schema + "://" + host;
//        Log.d(TAG, "setWxBaseUri:base uri=" + wxBaseUri);
    }

    public static Map<String, String> formSyncParams(Token token, String deviceId2, String syncKey) {
        Map<String, String> params = new HashMap<>();
        params.put("_", String.valueOf(System.currentTimeMillis()));
        params.put(Token.SKEY, token.getSkey());
        params.put("sid", token.getWxsid());
        params.put("uin", token.getWxuin());
        params.put("deviceid", deviceId2);
        params.put("synckey", syncKey);
        params.put("r", String.valueOf(System.currentTimeMillis()));
        return params;
    }

    public static String getInitUrl(Token token) {
        return String.format(wxBaseUri + INIT_URI, Long.valueOf(System.currentTimeMillis()), token.getPassTicket());
    }

    public static String getSendUrl(Token token) {
        return String.format(wxBaseUri + WX_SEND_URI, token.getPassTicket());
    }

    public static String getContactUrl(Token token, int seq) {
        return String.format(wxBaseUri + WX_CONTACT_URI, Integer.valueOf(seq), token.getPassTicket());
    }

    public static String getBatchContactUrl(Token token) {
        return String.format(wxBaseUri + WX_CONTACT_EX, token.getPassTicket());
    }

    public static String getWxStatusNotifyUrl(Token token) {
        return String.format(wxBaseUri + WX_STATUS_NOTIFY, token.getPassTicket());
    }

    public static String getMsgSyncUrl(Token token) {
        return String.format(wxBaseUri + WX_MSG_SYNC, token.getWxsid(), token.getSkey());
    }

    public static String getVoiceUrl(Token token, String msgId) {
        return String.format(wxBaseUri + WX_GET_VOICE, token.getSkey(), msgId);
    }

    public static String getIconUrlByUsername(Token token, String username) {
        return String.format(wxBaseUri + WX_GET_ICON, username, token.getSkey());
    }

    public static String getHeadUrlByUsername(Token token, String username) {
        return String.format(wxBaseUri + WX_GET_HEAD, username, token.getSkey());
    }

    public static boolean isGroupUserName(String userName) {
        return userName != null && userName.startsWith("@@");
    }

    public static String getHeadImgUrl(String headImgUrl) {
        if (TextUtils.isEmpty(headImgUrl)) {
            return DEFAULT_HEAD_IMG;
        }
        return wxBaseUri + headImgUrl;
    }

    public static InitRequest formInitRequest(Token token) {
        InitRequest initRequest = new InitRequest();
        initRequest.BaseRequest = formBaseRequest(token);
        return initRequest;
    }

    public static MsgRequest formMsgRequest(Token token, Msg msg) {
        MsgRequest msgRequest = new MsgRequest();
        msgRequest.BaseRequest = formBaseRequest(token);
        msgRequest.Msg = msg;
        return msgRequest;
    }

    public static MsgSyncRequest formMsgSyncRequest(Token token, SyncKey syncKey) {
        MsgSyncRequest msgSyncRequest = new MsgSyncRequest();
        msgSyncRequest.BaseRequest = formBaseRequest(token);
        msgSyncRequest.SyncKey = syncKey;
        return msgSyncRequest;
    }

    private static BaseRequest formBaseRequest(Token token) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.Skey = token.getSkey();
        baseRequest.Sid = token.getWxsid();
        baseRequest.Uin = token.getWxuin();
        baseRequest.DeviceID = randomDeviceId();
        return baseRequest;
    }

    public static BatchContactRequest formBatchContactRequest(Token token, List<String> chatSet) {
        BatchContactRequest batchContactRequest = new BatchContactRequest();
        batchContactRequest.BaseRequest = formBaseRequest(token);
        List<Contact> groupContact = new ArrayList<>();
        batchContactRequest.Count = 0;
        for (String chat : chatSet) {
            if (!"filehelper".equals(chat)) {
                Contact contact = new Contact();
                contact.UserName = chat;
                groupContact.add(contact);
                batchContactRequest.Count++;
            }
        }
        batchContactRequest.List = groupContact;
        return batchContactRequest;
    }

    public static StatusNotifyRequest formStatusNotifyRequest(Token token, String userName) {
        StatusNotifyRequest request = new StatusNotifyRequest();
        request.BaseRequest = formBaseRequest(token);
        request.FromUserName = userName;
        request.ToUserName = userName;
        request.ClientMsgId = randomClientMsgId();
        return request;
    }

    public static String randomDeviceId() {
        if (StringUtil.isNullOrEmpty(deviceId)) {
            StringBuilder sb = new StringBuilder(16);
            sb.append("e");
            for (int i = 0; i < 15; i++) {
                sb.append(DIGITS.charAt(secureRandom.nextInt(DIGITS.length())));
            }
            deviceId = sb.toString();
        }
        return deviceId;
    }

    public static long randomClientMsgId() {
        return System.currentTimeMillis() << 3766;
    }

    public static Properties syncCheck(Token token, String deviceId2, String syncKey) {
        Map<String, String> params = formSyncParams(token, deviceId2, syncKey);
        try {
            Uri.Builder builtUri = Uri.parse(String.format(WX_SYNC_URL_FORMAT, wxSchema, wxHost)).buildUpon();
            for (String key : params.keySet()) {
                builtUri.appendQueryParameter(key, params.get(key));
            }
            String url = builtUri.build().toString();
//            Log.d(TAG, "syncCheck:url=" + url);
            String text = NetUtil.getHttpsResponse(url, 30000, token.cookie);
            Log.d(TAG, "syncCheck:result=" + text);
            return WxLogin.parseText(text);
        } catch (IOException e) {
            Log.w(TAG, "syncCheck:exception", e);
            return null;
        }
    }
}
