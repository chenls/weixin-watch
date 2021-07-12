/*
 * Decompiled with CFR 0.151.
 */
package com.ta.utdid2.android.utils;

import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
    public static final String TAG = "AESUtils";

    private static void appendHex(StringBuffer stringBuffer, byte by2) {
        stringBuffer.append("0123456789ABCDEF".charAt(by2 >> 4 & 0xF)).append("0123456789ABCDEF".charAt(by2 & 0xF));
    }

    public static String decrypt(String string2, String string3) {
        try {
            string2 = new String(AESUtils.decrypt(AESUtils.getRawKey(string2.getBytes()), AESUtils.toByte(string3)));
            return string2;
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static byte[] decrypt(byte[] object, byte[] byArray) throws Exception {
        object = new SecretKeySpec((byte[])object, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(2, (Key)object, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        return cipher.doFinal(byArray);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String encrypt(String object, String string2) {
        Object var2_3 = null;
        try {
            object = AESUtils.encrypt(AESUtils.getRawKey(((String)object).getBytes()), string2.getBytes());
        }
        catch (Exception exception) {
            exception.printStackTrace();
            object = var2_3;
        }
        if (object != null) {
            return AESUtils.toHex((byte[])object);
        }
        return null;
    }

    private static byte[] encrypt(byte[] object, byte[] byArray) throws Exception {
        object = new SecretKeySpec((byte[])object, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, (Key)object, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        return cipher.doFinal(byArray);
    }

    public static String fromHex(String string2) {
        return new String(AESUtils.toByte(string2));
    }

    private static byte[] getRawKey(byte[] byArray) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        secureRandom.setSeed(byArray);
        keyGenerator.init(128, secureRandom);
        return keyGenerator.generateKey().getEncoded();
    }

    public static byte[] toByte(String string2) {
        int n2 = string2.length() / 2;
        byte[] byArray = new byte[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            byArray[i2] = Integer.valueOf(string2.substring(i2 * 2, i2 * 2 + 2), 16).byteValue();
        }
        return byArray;
    }

    public static String toHex(String string2) {
        return AESUtils.toHex(string2.getBytes());
    }

    public static String toHex(byte[] byArray) {
        if (byArray == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(byArray.length * 2);
        for (int i2 = 0; i2 < byArray.length; ++i2) {
            AESUtils.appendHex(stringBuffer, byArray[i2]);
        }
        return stringBuffer.toString();
    }
}

