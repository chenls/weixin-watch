package com.riyuxihe.weixinqingliao.model;

public class VoiceInfo {
    private String content;
    private String name;
    private long voiceLength;

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content2) {
        this.content = content2;
    }

    public long getVoiceLength() {
        return this.voiceLength;
    }

    public void setVoiceLength(long voiceLength2) {
        this.voiceLength = voiceLength2;
    }
}
