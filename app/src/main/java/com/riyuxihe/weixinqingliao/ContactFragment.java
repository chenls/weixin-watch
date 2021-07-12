/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ListAdapter
 *  android.widget.ListView
 */
package com.riyuxihe.weixinqingliao;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.riyuxihe.weixinqingliao.ChatActivity;
import com.riyuxihe.weixinqingliao.ListViewAdapter;
import com.riyuxihe.weixinqingliao.model.Contact;
import com.riyuxihe.weixinqingliao.model.Token;
import com.riyuxihe.weixinqingliao.model.User;
import com.riyuxihe.weixinqingliao.net.VolleySingleton;
import com.riyuxihe.weixinqingliao.util.WxHome;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactFragment
extends Fragment {
    private static final String TAG = "ContactFragment";
    private ArrayList<Contact> contactList;
    private ListView listView;
    private List<HashMap<String, Object>> mData;
    private RequestQueue mQueue;
    private Token token;
    private User user;

    private List<HashMap<String, Object>> getData(List<Contact> object) {
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
        object = object.iterator();
        while (object.hasNext()) {
            Contact contact = (Contact)object.next();
            if (contact.isPublic()) continue;
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("title", contact.getShowName());
            hashMap.put("time", "");
            hashMap.put("info", "");
            hashMap.put("img", WxHome.getHeadImgUrl(contact.HeadImgUrl));
            hashMap.put("userName", contact.UserName);
            arrayList.add(hashMap);
        }
        return arrayList;
    }

    private void initView(View view) {
        this.mData = this.getData(this.contactList);
        ListViewAdapter listViewAdapter = new ListViewAdapter(this.getActivity(), this.token.cookie, this.mData);
        this.listView = (ListView)view.findViewById(2131689654);
        view = this.getActivity().getLayoutInflater().inflate(2130968628, (ViewGroup)this.listView, false);
        this.listView.addFooterView(view);
        this.listView.setAdapter((ListAdapter)listViewAdapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> intent, View object, int n2, long l2) {
                if (n2 >= ContactFragment.this.mData.size()) {
                    return;
                }
                intent = new Intent((Context)ContactFragment.this.getActivity(), ChatActivity.class);
                intent.putExtra("token", ContactFragment.this.token.toBundle());
                object = new User();
                ((User)object).UserName = ((HashMap)ContactFragment.this.mData.get(n2)).get("userName").toString();
                ((User)object).NickName = ((HashMap)ContactFragment.this.mData.get(n2)).get("title").toString();
                ((User)object).HeadImgUrl = ((HashMap)ContactFragment.this.mData.get(n2)).get("img").toString();
                intent.putExtra("to", ((User)object).toBundle());
                intent.putExtra("from", ContactFragment.this.user.toBundle());
                ContactFragment.this.startActivity(intent);
            }
        });
    }

    public static ContactFragment newInstance(Token token, User user, ArrayList<Contact> arrayList) {
        ContactFragment contactFragment = new ContactFragment();
        Bundle bundle = new Bundle();
        bundle.putBundle("token", token.toBundle());
        bundle.putBundle("user", user.toBundle());
        bundle.putParcelableArrayList("contact", arrayList);
        contactFragment.setArguments(bundle);
        return contactFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (this.getArguments() != null) {
            this.token = new Token();
            this.token.fromBundle(this.getArguments().getBundle("token"));
            this.user = new User();
            this.user.fromBundle(this.getArguments().getBundle("user"));
            this.contactList = this.getArguments().getParcelableArrayList("contact");
        }
        Log.d((String)TAG, (String)("onCreate:token=" + JSON.toJSONString(this.token)));
        this.mQueue = VolleySingleton.getInstance().getRequestQueue();
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2130968629, viewGroup, false);
        this.initView((View)layoutInflater);
        return layoutInflater;
    }
}

