package com.riyuxihe.weixinqingliao.protocol;

import com.riyuxihe.weixinqingliao.model.Contact;
import java.util.List;

public class BatchContactRequest {
    public BaseRequest BaseRequest;
    public int Count;
    public List<Contact> List;
}
