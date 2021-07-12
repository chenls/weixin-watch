package com.riyuxihe.weixinqingliao.protocol;

import com.riyuxihe.weixinqingliao.model.Contact;
import com.riyuxihe.weixinqingliao.model.Msg;
import com.riyuxihe.weixinqingliao.model.SyncKey;
import java.util.List;

public class MsgSyncResponse {
    public int AddMsgCount;
    public List<Msg> AddMsgList;
    public BaseResponse BaseResponse;
    public int ModContactCount;
    public List<Contact> ModContactList;
    public SyncKey SyncKey;
}
