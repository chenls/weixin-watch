package com.riyuxihe.weixinqingliao.protocol;

import com.riyuxihe.weixinqingliao.model.Contact;
import com.riyuxihe.weixinqingliao.model.SyncKey;
import com.riyuxihe.weixinqingliao.model.User;
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
