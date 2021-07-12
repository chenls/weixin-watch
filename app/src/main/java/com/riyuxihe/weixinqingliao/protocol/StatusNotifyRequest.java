/*
 * Decompiled with CFR 0.151.
 */
package com.riyuxihe.weixinqingliao.protocol;

import com.riyuxihe.weixinqingliao.protocol.BaseRequest;

public class StatusNotifyRequest {
    public BaseRequest BaseRequest;
    public long ClientMsgId;
    public int Code = 3;
    public String FromUserName;
    public String ToUserName;
}

