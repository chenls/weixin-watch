package com.riyuxihe.weixinqingliao.model;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

public class SyncKey {
    public int Count;
    public List<KeyVal> List;

    public static class KeyVal {
        public int Key;
        public long Val;

        public String toString() {
            return this.Key + "_" + this.Val;
        }
    }

    public String toString() {
        List<String> list = new ArrayList<>();
        for (KeyVal keyVal : this.List) {
            list.add(keyVal.toString());
        }
        return TextUtils.join("|", list);
    }
}
