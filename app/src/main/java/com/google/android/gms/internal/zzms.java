/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.database.CharArrayBuffer
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.database.CharArrayBuffer;
import android.text.TextUtils;

public final class zzms {
    /*
     * Enabled aggressive block sorting
     */
    public static void zzb(String string2, CharArrayBuffer charArrayBuffer) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            charArrayBuffer.sizeCopied = 0;
        } else if (charArrayBuffer.data == null || charArrayBuffer.data.length < string2.length()) {
            charArrayBuffer.data = string2.toCharArray();
        } else {
            string2.getChars(0, string2.length(), charArrayBuffer.data, 0);
        }
        charArrayBuffer.sizeCopied = string2.length();
    }
}

