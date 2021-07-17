package com.chenls.weixin.protocol;

import com.chenls.weixin.model.Contact;
import com.chenls.weixin.model.SyncKey;
import com.chenls.weixin.model.User;

import java.util.ArrayList;

public class InitResponse {
    public BaseResponse BaseResponse;
    public String ChatSet;
    public ArrayList<Contact> ContactList;
    public int Count;
    public int GrayScale;
    public String Skey;
    public SyncKey SyncKey;
    public long SystemTime;
    public User User;
}
