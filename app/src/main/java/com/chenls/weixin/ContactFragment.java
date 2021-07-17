package com.chenls.weixin;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.chenls.weixin.model.Contact;
import com.chenls.weixin.model.Token;
import com.chenls.weixin.model.User;
import com.chenls.weixin.net.VolleySingleton;
import com.chenls.weixin.util.Prefs;
import com.chenls.weixin.util.WxHome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactFragment extends Fragment {
    private static final String TAG = "ContactFragment";
    /* access modifiers changed from: private */
    public List<HashMap<String, Object>> mData;
    /* access modifiers changed from: private */
    public Token token;
    /* access modifiers changed from: private */
    public User user;
    private ArrayList<Contact> contactList;
    private ListView listView;
    private RequestQueue mQueue;

    public static ContactFragment newInstance(Token token2, User user2, ArrayList<Contact> contacts) {
        ContactFragment contactFragment = new ContactFragment();
        Bundle bundle = new Bundle();
        bundle.putBundle(Prefs.Key.TOKEN, token2.toBundle());
        bundle.putBundle("user", user2.toBundle());
        bundle.putParcelableArrayList("contact", contacts);
        contactFragment.setArguments(bundle);
        return contactFragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
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
            this.contactList = getArguments().getParcelableArrayList("contact");
        }
//        Log.d(TAG, "onCreate:token=" + JSON.toJSONString(this.token));
        this.mQueue = VolleySingleton.getInstance().getRequestQueue();
    }

    private void initView(View view) {
        this.mData = getData(this.contactList);
        ListViewAdapter adapter = new ListViewAdapter(getActivity(), this.token.cookie, this.mData);
        this.listView = view.findViewById(R.id.listView2);
        this.listView.addFooterView(getActivity().getLayoutInflater().inflate(R.layout.footer_view, this.listView, false));
        this.listView.setAdapter(adapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i < ContactFragment.this.mData.size()) {
                    Intent intent = new Intent(ContactFragment.this.getActivity(), ChatActivity.class);
                    intent.putExtra(Prefs.Key.TOKEN, ContactFragment.this.token.toBundle());
                    User toUser = new User();
                    toUser.UserName = ContactFragment.this.mData.get(i).get("userName").toString();
                    toUser.NickName = ContactFragment.this.mData.get(i).get("title").toString();
                    toUser.HeadImgUrl = ContactFragment.this.mData.get(i).get("img").toString();
                    intent.putExtra("to", toUser.toBundle());
                    intent.putExtra("from", ContactFragment.this.user.toBundle());
                    ContactFragment.this.startActivity(intent);
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
