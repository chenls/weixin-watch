/*
 * Decompiled with CFR 0.151.
 */
package com.android.volley;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class InternalUtils {
    private static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();

    InternalUtils() {
    }

    private static String convertToHex(byte[] byArray) {
        char[] cArray = new char[byArray.length * 2];
        for (int i2 = 0; i2 < byArray.length; ++i2) {
            int n2 = byArray[i2] & 0xFF;
            cArray[i2 * 2] = HEX_CHARS[n2 >>> 4];
            cArray[i2 * 2 + 1] = HEX_CHARS[n2 & 0xF];
        }
        return new String(cArray);
    }

    public static String sha1Hash(String object) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            object = ((String)object).getBytes("UTF-8");
            messageDigest.update((byte[])object, 0, ((Object)object).length);
            object = InternalUtils.convertToHex(messageDigest.digest());
            return object;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
            return null;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
            return null;
        }
    }
}

