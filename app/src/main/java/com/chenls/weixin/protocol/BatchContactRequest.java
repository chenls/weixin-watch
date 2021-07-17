package com.chenls.weixin.protocol;

import com.chenls.weixin.model.Contact;

import java.util.List;

public class BatchContactRequest {
    public BaseRequest BaseRequest;
    public int Count;
    public List<Contact> List;
}
