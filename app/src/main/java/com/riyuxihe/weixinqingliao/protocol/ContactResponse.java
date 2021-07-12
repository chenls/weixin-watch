package com.riyuxihe.weixinqingliao.protocol;

import com.riyuxihe.weixinqingliao.model.Contact;
import java.util.ArrayList;

public class ContactResponse {
    public BaseResponse BaseResponse;
    public int MemberCount;
    public ArrayList<Contact> MemberList;
    public int Seq;
}
