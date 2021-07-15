package com.riyuxihe.weixinqingliao.protocol;

import com.riyuxihe.weixinqingliao.model.Contact;

import java.util.List;

public class BatchContactResponse {
    public BaseResponse BaseResponse;
    public List<Contact> ContactList;
    public int Count;
}
