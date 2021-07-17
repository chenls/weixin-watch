package com.chenls.weixin.model;

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

    public ChatMsgEntity(String userName2, String nickName2, String date2, String text2, boolean isComMsg) {
        this.userName = userName2;
        this.nickName = nickName2;
        this.date = date2;
        this.text = text2;
        this.isComMeg = isComMsg;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time2) {
        this.time = time2;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date2) {
        this.date = date2;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text2) {
        this.text = text2;
    }

    public boolean getMsgType() {
        return this.isComMeg;
    }

    public void setMsgType(boolean isComMsg) {
        this.isComMeg = isComMsg;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName2) {
        this.userName = userName2;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName2) {
        this.nickName = nickName2;
    }

    public String getMemberUserName() {
        return this.memberUserName;
    }

    public void setMemberUserName(String memberUserName2) {
        this.memberUserName = memberUserName2;
    }

    public String getMemberNickName() {
        return this.memberNickName;
    }

    public void setMemberNickName(String memberNickName2) {
        this.memberNickName = memberNickName2;
    }
}
