package com.chenls.weixin.protocol;

import com.chenls.weixin.model.Contact;
import com.chenls.weixin.model.Msg;
import com.chenls.weixin.model.SyncKey;

import java.util.List;

public class MsgSyncResponse {
    public int AddMsgCount;
    public List<Msg> AddMsgList;
    public BaseResponse BaseResponse;
    public int ModContactCount;
    public List<Contact> ModContactList;
    public SyncKey SyncKey;
}
