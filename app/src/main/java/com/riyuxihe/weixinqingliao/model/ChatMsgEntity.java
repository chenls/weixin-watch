/*
 * Decompiled with CFR 0.151.
 */
package com.riyuxihe.weixinqingliao.model;

public class ChatMsgEntity {
    private static final String TAG = ChatMsgEntity.class.getSimpleName();
    private String date;
    private boolean isComMeg = true;
    private String memberNickName;
    private String memberUserName;
    private String nickName;
    private String text;
    private String time;
    private String userName;

    public ChatMsgEntity() {
    }

    public ChatMsgEntity(String string2, String string3, String string4, String string5, boolean bl2) {
        this.userName = string2;
        this.nickName = string3;
        this.date = string4;
        this.text = string5;
        this.isComMeg = bl2;
    }

    public String getDate() {
        return this.date;
    }

    public String getMemberNickName() {
        return this.memberNickName;
    }

    public String getMemberUserName() {
        return this.memberUserName;
    }

    public boolean getMsgType() {
        return this.isComMeg;
    }

    public String getNickName() {
        return this.nickName;
    }

    public String getText() {
        return this.text;
    }

    public String getTime() {
        return this.time;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setDate(String string2) {
        this.date = string2;
    }

    public void setMemberNickName(String string2) {
        this.memberNickName = string2;
    }

    public void setMemberUserName(String string2) {
        this.memberUserName = string2;
    }

    public void setMsgType(boolean bl2) {
        this.isComMeg = bl2;
    }

    public void setNickName(String string2) {
        this.nickName = string2;
    }

    public void setText(String string2) {
        this.text = string2;
    }

    public void setTime(String string2) {
        this.time = string2;
    }

    public void setUserName(String string2) {
        this.userName = string2;
    }
}

