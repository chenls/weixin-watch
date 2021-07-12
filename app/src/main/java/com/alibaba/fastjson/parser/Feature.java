/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser;

public enum Feature {
    AutoCloseSource,
    AllowComment,
    AllowUnQuotedFieldNames,
    AllowSingleQuotes,
    InternFieldNames,
    AllowISO8601DateFormat,
    AllowArbitraryCommas,
    UseBigDecimal,
    IgnoreNotMatch,
    SortFeidFastMatch,
    DisableASM,
    DisableCircularReferenceDetect,
    InitStringFieldAsEmpty,
    SupportArrayToBean,
    OrderedField,
    DisableSpecialKeyDetect,
    UseObjectArray;

    public final int mask = 1 << this.ordinal();

    public static int config(int n2, Feature feature, boolean bl2) {
        if (bl2) {
            return n2 | feature.mask;
        }
        return n2 & ~feature.mask;
    }

    public static boolean isEnabled(int n2, Feature feature) {
        return (feature.mask & n2) != 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int of(Feature[] featureArray) {
        int n2 = 0;
        if (featureArray == null) {
            return 0;
        }
        int n3 = 0;
        int n4 = featureArray.length;
        while (true) {
            int n5 = n3;
            if (n2 >= n4) return n5;
            n3 |= featureArray[n2].mask;
            ++n2;
        }
    }

    public final int getMask() {
        return this.mask;
    }
}

