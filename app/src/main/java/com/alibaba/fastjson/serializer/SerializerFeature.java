/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

public enum SerializerFeature {
    QuoteFieldNames,
    UseSingleQuotes,
    WriteMapNullValue,
    WriteEnumUsingToString,
    WriteEnumUsingName,
    UseISO8601DateFormat,
    WriteNullListAsEmpty,
    WriteNullStringAsEmpty,
    WriteNullNumberAsZero,
    WriteNullBooleanAsFalse,
    SkipTransientField,
    SortField,
    WriteTabAsSpecial,
    PrettyFormat,
    WriteClassName,
    DisableCircularReferenceDetect,
    WriteSlashAsSpecial,
    BrowserCompatible,
    WriteDateUseDateFormat,
    NotWriteRootClassName,
    DisableCheckSpecialChar,
    BeanToArray,
    WriteNonStringKeyAsString,
    NotWriteDefaultValue,
    BrowserSecure,
    IgnoreNonFieldGetter,
    WriteNonStringValueAsString,
    IgnoreErrorGetter;

    public static final SerializerFeature[] EMPTY;
    public final int mask = 1 << this.ordinal();

    static {
        EMPTY = new SerializerFeature[0];
    }

    public static int config(int n2, SerializerFeature serializerFeature, boolean bl2) {
        if (bl2) {
            return n2 | serializerFeature.mask;
        }
        return n2 & ~serializerFeature.mask;
    }

    public static boolean isEnabled(int n2, int n3, SerializerFeature serializerFeature) {
        int n4 = serializerFeature.mask;
        return (n2 & n4) != 0 || (n3 & n4) != 0;
    }

    public static boolean isEnabled(int n2, SerializerFeature serializerFeature) {
        return (serializerFeature.mask & n2) != 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int of(SerializerFeature[] serializerFeatureArray) {
        int n2 = 0;
        if (serializerFeatureArray == null) {
            return 0;
        }
        int n3 = 0;
        int n4 = serializerFeatureArray.length;
        while (true) {
            int n5 = n3;
            if (n2 >= n4) return n5;
            n3 |= serializerFeatureArray[n2].mask;
            ++n2;
        }
    }

    public final int getMask() {
        return this.mask;
    }
}

