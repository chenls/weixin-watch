package com.chenls.weixin.protocol;

import com.chenls.weixin.model.Contact;

import java.util.ArrayList;

public class ContactResponse {
    public BaseResponse BaseResponse;
    public int MemberCount;
    public ArrayList<Contact> MemberList;
    public int Seq;
}
