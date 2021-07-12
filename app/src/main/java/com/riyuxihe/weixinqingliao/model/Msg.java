/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.riyuxihe.weixinqingliao.model;

import android.os.Bundle;

public class Msg {
    private static final String CONTENT = "Content";
    private static final String CREATE_TIME = "CreateTime";
    private static final String FROM_MEMBER_NICK_NAME = "fromMemberNickName";
    private static final String FROM_MEMBER_USER_NAME = "fromMemberUserName";
    private static final String FROM_NICK_NAME = "fromNickName";
    private static final String FROM_USER_NAME = "FromUserName";
    private static final String MSG_ID = "MsgId";
    private static final String Msg_TYPE = "MsgType";
    private static final String TO_NICK_NAME = "toNickName";
    private static final String TO_USER_NAME = "ToUserName";
    private static final String TYPE = "Type";
    private static final String VOICE_LENGTH = "VoiceLength";
    public long ClientMsgId;
    public String Content;
    public long CreateTime;
    public String FromUserName;
    public long LocalID;
    public String MsgId;
    public int MsgType;
    public String StatusNotifyUserName;
    public String ToUserName;
    public int Type;
    public long VoiceLength;
    public String fromMemberNickName = "";
    public String fromMemberUserName = "";
    public String fromNickName;
    public String toNickName;

    public void fromBundle(Bundle bundle) {
        this.Type = bundle.getInt(TYPE);
        this.Content = bundle.getString(CONTENT);
        this.FromUserName = bundle.getString(FROM_USER_NAME);
        this.ToUserName = bundle.getString(TO_USER_NAME);
        this.MsgId = bundle.getString(MSG_ID);
        this.MsgType = bundle.getInt(Msg_TYPE);
        this.fromNickName = bundle.getString(FROM_NICK_NAME);
        this.toNickName = bundle.getString(TO_NICK_NAME);
        this.fromMemberUserName = bundle.getString(FROM_MEMBER_USER_NAME);
        this.fromMemberNickName = bundle.getString(FROM_MEMBER_NICK_NAME);
        this.VoiceLength = bundle.getLong(VOICE_LENGTH);
        this.CreateTime = bundle.getLong(CREATE_TIME);
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, this.Type);
        bundle.putString(CONTENT, this.Content);
        bundle.putString(FROM_USER_NAME, this.FromUserName);
        bundle.putString(TO_USER_NAME, this.ToUserName);
        bundle.putString(MSG_ID, this.MsgId);
        bundle.putInt(Msg_TYPE, this.MsgType);
        bundle.putString(FROM_NICK_NAME, this.fromNickName);
        bundle.putString(TO_NICK_NAME, this.toNickName);
        bundle.putString(FROM_MEMBER_USER_NAME, this.fromMemberUserName);
        bundle.putString(FROM_MEMBER_NICK_NAME, this.fromMemberNickName);
        bundle.putLong(VOICE_LENGTH, this.VoiceLength);
        bundle.putLong(CREATE_TIME, this.CreateTime);
        return bundle;
    }
}

