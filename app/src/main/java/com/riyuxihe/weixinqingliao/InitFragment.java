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
import com.riyuxihe.weixinqingliao.dao.MessageManager;
import com.riyuxihe.weixinqingliao.model.Contact;
import com.riyuxihe.weixinqingliao.model.Msg;
import com.riyuxihe.weixinqingliao.model.Token;
import com.riyuxihe.weixinqingliao.model.User;
import com.riyuxihe.weixinqingliao.net.VolleySingleton;
import com.riyuxihe.weixinqingliao.util.TimeUtil;
import com.riyuxihe.weixinqingliao.util.WxHome;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class InitFragment
extends Fragment {
    private static final String TAG = "InitFragment";
    private ListViewAdapter adapter;
    private ArrayList<Contact> initList;
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

    public static InitFragment newInstance(Token token, User user, ArrayList<Contact> arrayList) {
        InitFragment initFragment = new InitFragment();
        Bundle bundle = new Bundle();
        bundle.putBundle("token", token.toBundle());
        bundle.putBundle("user", user.toBundle());
        bundle.putParcelableArrayList("init", arrayList);
        initFragment.setArguments(bundle);
        return initFragment;
    }

    public void addExInitList(List<Contact> collection) {
        if (this.initList != null) {
            Object object = this.getData((List<Contact>)collection);
            collection = new HashSet();
            Object object2 = this.mData.iterator();
            while (object2.hasNext()) {
                ((HashSet)collection).add((Contact)((Object)object2.next().get("userName").toString()));
            }
            object = object.iterator();
            while (object.hasNext()) {
                object2 = (HashMap)object.next();
                if (((HashSet)collection).contains(((HashMap)object2).get("userName").toString())) continue;
                this.mData.add((HashMap<String, Object>)object2);
            }
            this.adapter.notifyDataSetChanged();
        }
    }

    /*
     * Unable to fully structure code
     */
    public void comeNewMessage(Msg var1_1) {
        block21: {
            block19: {
                block20: {
                    block17: {
                        block18: {
                            block22: {
                                if (this.token == null) {
                                    Log.d((String)"InitFragment", (String)"comeNewMessage:Initfragment not created yet");
                                    return;
                                }
                                var5_2 = "";
                                if (var1_1.MsgType != 1) break block22;
                                var5_2 = var1_1.Content;
lbl9:
                                // 3 sources

                                while (true) {
                                    var6_3 = "";
                                    if (var1_1.MsgType != 51) {
                                        var6_3 = TimeUtil.getDate();
                                    }
                                    var4_4 = false;
                                    if (!var1_1.FromUserName.equals(this.user.UserName)) break block17;
                                    var3_5 = 0;
lbl16:
                                    // 2 sources

                                    while (true) {
                                        var2_7 = var4_4;
                                        if (var3_5 < this.mData.size()) {
                                            var7_8 = this.mData.get(var3_5);
                                            if (!var1_1.ToUserName.equals(var7_8.get("userName"))) break block18;
                                            var7_8.put("info", var5_2);
                                            var7_8.put("time", var6_3);
                                            Collections.swap(this.mData, 0, var3_5);
                                            var2_7 = true;
                                        }
lbl27:
                                        // 5 sources

                                        while (true) {
                                            if (var2_7) ** GOTO lbl47
                                            var7_8 = new HashMap<K, V>();
                                            var7_8.put("time", var6_3);
                                            var7_8.put("info", var5_2);
                                            if (!var1_1.ToUserName.equals(this.user.UserName)) break block19;
                                            var7_8.put("title", var1_1.fromNickName);
                                            if (!WxHome.isGroupUserName(var1_1.FromUserName)) break block20;
                                            var7_8.put("img", WxHome.getHeadUrlByUsername(this.token, var1_1.FromUserName));
lbl40:
                                            // 2 sources

                                            while (true) {
                                                var7_8.put("userName", var1_1.FromUserName);
lbl43:
                                                // 2 sources

                                                while (true) {
                                                    if (this.mData != null) {
                                                        if (var1_1.MsgType == 51) break block21;
                                                        this.mData.add(0, var7_8);
                                                    }
lbl47:
                                                    // 5 sources

                                                    while (true) {
                                                        this.adapter.notifyDataSetChanged();
                                                        if (var1_1.MsgType == 51) ** continue;
                                                        new MessageManager((Context)this.getActivity()).insertMessage(var1_1);
                                                        return;
                                                    }
                                                    break;
                                                }
                                                break;
                                            }
                                            break;
                                        }
                                        break;
                                    }
                                    break;
                                }
                            }
                            if (var1_1.MsgType != 34) ** GOTO lbl9
                            var5_2 = "[\u8bed\u97f3]";
                            ** while (true)
                        }
                        ++var3_5;
                        ** while (true)
                    }
                    var3_6 = 0;
                    while (true) {
                        var2_7 = var4_4;
                        if (var3_6 >= this.mData.size()) ** GOTO lbl27
                        var7_8 = this.mData.get(var3_6);
                        if (var1_1.FromUserName.equals(var7_8.get("userName"))) {
                            var7_8.put("info", var5_2);
                            var7_8.put("time", var6_3);
                            if (var1_1.MsgType != 51) {
                                Collections.swap(this.mData, 0, var3_6);
                            }
                            var2_7 = true;
                            ** continue;
                        }
                        ++var3_6;
                    }
                }
                var7_8.put("img", WxHome.getIconUrlByUsername(this.token, var1_1.FromUserName));
                ** while (true)
            }
            var7_8.put("title", var1_1.toNickName);
            if (WxHome.isGroupUserName(var1_1.ToUserName)) {
                var7_8.put("img", WxHome.getHeadUrlByUsername(this.token, var1_1.ToUserName));
lbl86:
                // 2 sources

                while (true) {
                    var7_8.put("userName", var1_1.ToUserName);
                    ** continue;
                    break;
                }
            }
            var7_8.put("img", WxHome.getIconUrlByUsername(this.token, var1_1.ToUserName));
            ** while (true)
        }
        this.mData.add(var7_8);
        ** while (true)
    }

    public void initView(View view) {
        this.mData = this.getData(this.initList);
        this.adapter = new ListViewAdapter(this.getActivity(), this.token.cookie, this.mData);
        this.listView = (ListView)view.findViewById(2131689657);
        this.listView.setAdapter((ListAdapter)this.adapter);
        view = this.getActivity().getLayoutInflater().inflate(2130968628, (ViewGroup)this.listView, false);
        this.listView.addFooterView(view);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> intent, View object, int n2, long l2) {
                if (n2 >= InitFragment.this.mData.size()) {
                    return;
                }
                intent = new Intent((Context)InitFragment.this.getActivity(), ChatActivity.class);
                intent.putExtra("token", InitFragment.this.token.toBundle());
                object = new User();
                ((User)object).UserName = ((HashMap)InitFragment.this.mData.get(n2)).get("userName").toString();
                ((User)object).NickName = ((HashMap)InitFragment.this.mData.get(n2)).get("title").toString();
                ((User)object).HeadImgUrl = ((HashMap)InitFragment.this.mData.get(n2)).get("img").toString();
                intent.putExtra("to", ((User)object).toBundle());
                intent.putExtra("from", InitFragment.this.user.toBundle());
                ((HashMap)InitFragment.this.mData.get(n2)).put("info", "");
                ((HashMap)InitFragment.this.mData.get(n2)).put("time", "");
                InitFragment.this.adapter.notifyDataSetChanged();
                InitFragment.this.startActivity(intent);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (this.getArguments() != null) {
            this.token = new Token();
            this.token.fromBundle(this.getArguments().getBundle("token"));
            this.user = new User();
            this.user.fromBundle(this.getArguments().getBundle("user"));
            this.initList = this.getArguments().getParcelableArrayList("init");
        }
        Log.d((String)TAG, (String)("onCreate:token=" + JSON.toJSONString(this.token)));
        this.mQueue = VolleySingleton.getInstance().getRequestQueue();
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2130968630, viewGroup, false);
        this.initView((View)layoutInflater);
        return layoutInflater;
    }
}

