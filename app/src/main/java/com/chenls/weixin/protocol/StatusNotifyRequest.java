package com.chenls.weixin.protocol;

public class StatusNotifyRequest {
    public BaseRequest BaseRequest;
    public long ClientMsgId;
    public int Code = 3;
    public String FromUserName;
    public String ToUserName;
}
