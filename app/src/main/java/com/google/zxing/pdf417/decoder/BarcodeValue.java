/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.pdf417.decoder;

import com.google.zxing.pdf417.PDF417Common;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

final class BarcodeValue {
    private final Map<Integer, Integer> values = new HashMap<Integer, Integer>();

    BarcodeValue() {
    }

    public Integer getConfidence(int n2) {
        return this.values.get(n2);
    }

    int[] getValue() {
        int n2 = -1;
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (Map.Entry<Integer, Integer> entry : this.values.entrySet()) {
            if (entry.getValue() > n2) {
                n2 = entry.getValue();
                arrayList.clear();
                arrayList.add(entry.getKey());
                continue;
            }
            if (entry.getValue() != n2) continue;
            arrayList.add(entry.getKey());
        }
        return PDF417Common.toIntArray(arrayList);
    }

    void setValue(int n2) {
        Integer n3;
        Integer n4 = n3 = this.values.get(n2);
        if (n3 == null) {
            n4 = 0;
        }
        int n5 = n4;
        this.values.put(n2, n5 + 1);
    }
}

