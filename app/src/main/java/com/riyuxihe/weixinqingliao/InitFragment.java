package com.riyuxihe.weixinqingliao;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.riyuxihe.weixinqingliao.dao.MessageManager;
import com.riyuxihe.weixinqingliao.model.Contact;
import com.riyuxihe.weixinqingliao.model.Msg;
import com.riyuxihe.weixinqingliao.model.Token;
import com.riyuxihe.weixinqingliao.model.User;
import com.riyuxihe.weixinqingliao.net.VolleySingleton;
import com.riyuxihe.weixinqingliao.util.Prefs;
import com.riyuxihe.weixinqingliao.util.TimeUtil;
import com.riyuxihe.weixinqingliao.util.WxHome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class InitFragment extends Fragment {
    private static final String TAG = "InitFragment";
    /* access modifiers changed from: private */
    public ListViewAdapter adapter;
    /* access modifiers changed from: private */
    public List<HashMap<String, Object>> mData;
    /* access modifiers changed from: private */
    public Token token;
    /* access modifiers changed from: private */
    public User user;
    private ArrayList<Contact> initList;
    private ListView listView;
    private RequestQueue mQueue;

    public static InitFragment newInstance(Token token2, User user2, ArrayList<Contact> contacts) {
        InitFragment initFragment = new InitFragment();
        Bundle bundle = new Bundle();
        bundle.putBundle(Prefs.Key.TOKEN, token2.toBundle());
        bundle.putBundle("user", user2.toBundle());
        bundle.putParcelableArrayList("init", contacts);
        initFragment.setArguments(bundle);
        return initFragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_init, container, false);
        initView(view);
        return view;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.token = new Token();
            this.token.fromBundle(getArguments().getBundle(Prefs.Key.TOKEN));
            this.user = new User();
            this.user.fromBundle(getArguments().getBundle("user"));
            this.initList = getArguments().getParcelableArrayList("init");
        }
        Log.d(TAG, "onCreate:token=" + JSON.toJSONString(this.token));
        this.mQueue = VolleySingleton.getInstance().getRequestQueue();
    }

    public void addExInitList(List<Contact> exInitList) {
        if (this.initList != null) {
            List<HashMap<String, Object>> exData = getData(exInitList);
            HashSet<String> existedUsername = new HashSet<>();
            for (HashMap<String, Object> values : this.mData) {
                existedUsername.add(values.get("userName").toString());
            }
            for (HashMap<String, Object> values2 : exData) {
                if (!existedUsername.contains(values2.get("userName").toString())) {
                    this.mData.add(values2);
                }
            }
            this.adapter.notifyDataSetChanged();
        }
    }

    public void comeNewMessage(Msg msg) {
        if (this.token == null) {
            Log.d(TAG, "comeNewMessage:Initfragment not created yet");
            return;
        }
        String info = "";
        if (msg.MsgType == 1) {
            info = msg.Content;
        } else if (msg.MsgType == 34) {
            info = "[语音]";
        }
        String time = "";
        if (msg.MsgType != 51) {
            time = TimeUtil.getDate();
        }
        boolean exist = false;
        if (!msg.FromUserName.equals(this.user.UserName)) {
            int i = 0;
            while (true) {
                if (i >= this.mData.size()) {
                    break;
                }
                HashMap<String, Object> map = this.mData.get(i);
                if (msg.FromUserName.equals(map.get("userName"))) {
                    map.put("info", info);
                    map.put("time", time);
                    if (msg.MsgType != 51) {
                        Collections.swap(this.mData, 0, i);
                    }
                    exist = true;
                    break;
                } else {
                    i++;
                }
            }
        } else {
            int i2 = 0;
            while (true) {
                if (i2 >= this.mData.size()) {
                    break;
                }
                HashMap<String, Object> map2 = this.mData.get(i2);
                if (msg.ToUserName.equals(map2.get("userName"))) {
                    map2.put("info", info);
                    map2.put("time", time);
                    Collections.swap(this.mData, 0, i2);
                    exist = true;
                    break;
                }
                i2++;
            }
        }
        if (!exist) {
            HashMap<String, Object> map3 = new HashMap<>();
            map3.put("time", time);
            map3.put("info", info);
            if (msg.ToUserName.equals(this.user.UserName)) {
                map3.put("title", msg.fromNickName);
                if (WxHome.isGroupUserName(msg.FromUserName)) {
                    map3.put("img", WxHome.getHeadUrlByUsername(this.token, msg.FromUserName));
                } else {
                    map3.put("img", WxHome.getIconUrlByUsername(this.token, msg.FromUserName));
                }
                map3.put("userName", msg.FromUserName);
            } else {
                map3.put("title", msg.toNickName);
                if (WxHome.isGroupUserName(msg.ToUserName)) {
                    map3.put("img", WxHome.getHeadUrlByUsername(this.token, msg.ToUserName));
                } else {
                    map3.put("img", WxHome.getIconUrlByUsername(this.token, msg.ToUserName));
                }
                map3.put("userName", msg.ToUserName);
            }
            if (this.mData != null) {
                if (msg.MsgType != 51) {
                    this.mData.add(0, map3);
                } else {
                    this.mData.add(map3);
                }
            }
        }
        this.adapter.notifyDataSetChanged();
        if (msg.MsgType != 51) {
            new MessageManager(getActivity()).insertMessage(msg);
        }
    }

    public void initView(View view) {
        this.mData = getData(this.initList);
        this.adapter = new ListViewAdapter(getActivity(), this.token.cookie, this.mData);
        this.listView = view.findViewById(R.id.listView1);
        this.listView.setAdapter(this.adapter);
        this.listView.addFooterView(getActivity().getLayoutInflater().inflate(R.layout.footer_view, this.listView, false));
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i < InitFragment.this.mData.size()) {
                    Intent intent = new Intent(InitFragment.this.getActivity(), ChatActivity.class);
                    intent.putExtra(Prefs.Key.TOKEN, InitFragment.this.token.toBundle());
                    User toUser = new User();
                    toUser.UserName = InitFragment.this.mData.get(i).get("userName").toString();
                    toUser.NickName = InitFragment.this.mData.get(i).get("title").toString();
                    toUser.HeadImgUrl = InitFragment.this.mData.get(i).get("img").toString();
                    intent.putExtra("to", toUser.toBundle());
                    intent.putExtra("from", InitFragment.this.user.toBundle());
                    InitFragment.this.mData.get(i).put("info", "");
                    InitFragment.this.mData.get(i).put("time", "");
                    InitFragment.this.adapter.notifyDataSetChanged();
                    InitFragment.this.startActivity(intent);
                }
            }
        });
    }

    private List<HashMap<String, Object>> getData(List<Contact> contacts) {
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        for (Contact contact : contacts) {
            if (!contact.isPublic()) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("title", contact.getShowName());
                map.put("time", "");
                map.put("info", "");
                map.put("img", WxHome.getHeadImgUrl(contact.HeadImgUrl));
                map.put("userName", contact.UserName);
                list.add(map);
            }
        }
        return list;
    }
}
