package com.chenls.weixin.protocol;

import com.chenls.weixin.model.Contact;

import java.util.List;

public class BatchContactResponse {
    public BaseResponse BaseResponse;
    public List<Contact> ContactList;
    public int Count;
}
