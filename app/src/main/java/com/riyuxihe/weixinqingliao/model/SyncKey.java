/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.riyuxihe.weixinqingliao.model;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SyncKey {
    public int Count;
    public List<KeyVal> List;

    public String toString() {
        ArrayList<String> arrayList = new ArrayList<String>();
        Iterator<KeyVal> iterator = this.List.iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next().toString());
        }
        return TextUtils.join((CharSequence)"|", arrayList);
    }

    public static class KeyVal {
        public int Key;
        public long Val;

        public String toString() {
            return this.Key + "_" + this.Val;
        }
    }
}

