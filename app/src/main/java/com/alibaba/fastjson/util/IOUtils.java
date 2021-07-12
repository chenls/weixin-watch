/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSONException;
import java.io.Closeable;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.MalformedInputException;
import java.util.Arrays;

public class IOUtils {
    public static final char[] ASCII_CHARS;
    public static final char[] CA;
    public static final char[] DIGITS;
    static final char[] DigitOnes;
    static final char[] DigitTens;
    public static final int[] IA;
    public static final Charset UTF8;
    static final char[] digits;
    public static final boolean[] firstIdentifierFlags;
    public static final boolean[] identifierFlags;
    public static final char[] replaceChars;
    static final int[] sizeTable;
    public static final byte[] specicalFlags_doubleQuotes;
    public static final boolean[] specicalFlags_doubleQuotesFlags;
    public static final byte[] specicalFlags_singleQuotes;
    public static final boolean[] specicalFlags_singleQuotesFlags;

    /*
     * Enabled aggressive block sorting
     */
    static {
        int n2;
        UTF8 = Charset.forName("UTF-8");
        DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        firstIdentifierFlags = new boolean[256];
        for (n2 = 0; n2 < firstIdentifierFlags.length; n2 = (int)((char)(n2 + 1))) {
            if (n2 >= 65 && n2 <= 90) {
                IOUtils.firstIdentifierFlags[n2] = true;
                continue;
            }
            if (n2 >= 97 && n2 <= 122) {
                IOUtils.firstIdentifierFlags[n2] = true;
                continue;
            }
            if (n2 != 95) continue;
            IOUtils.firstIdentifierFlags[n2] = true;
        }
        identifierFlags = new boolean[256];
        for (n2 = 0; n2 < identifierFlags.length; n2 = (int)((char)(n2 + 1))) {
            if (n2 >= 65 && n2 <= 90) {
                IOUtils.identifierFlags[n2] = true;
                continue;
            }
            if (n2 >= 97 && n2 <= 122) {
                IOUtils.identifierFlags[n2] = true;
                continue;
            }
            if (n2 == 95) {
                IOUtils.identifierFlags[n2] = true;
                continue;
            }
            if (n2 < 48 || n2 > 57) continue;
            IOUtils.identifierFlags[n2] = true;
        }
        specicalFlags_doubleQuotes = new byte[161];
        specicalFlags_singleQuotes = new byte[161];
        specicalFlags_doubleQuotesFlags = new boolean[161];
        specicalFlags_singleQuotesFlags = new boolean[161];
        replaceChars = new char[93];
        IOUtils.specicalFlags_doubleQuotes[0] = 4;
        IOUtils.specicalFlags_doubleQuotes[1] = 4;
        IOUtils.specicalFlags_doubleQuotes[2] = 4;
        IOUtils.specicalFlags_doubleQuotes[3] = 4;
        IOUtils.specicalFlags_doubleQuotes[4] = 4;
        IOUtils.specicalFlags_doubleQuotes[5] = 4;
        IOUtils.specicalFlags_doubleQuotes[6] = 4;
        IOUtils.specicalFlags_doubleQuotes[7] = 4;
        IOUtils.specicalFlags_doubleQuotes[8] = 1;
        IOUtils.specicalFlags_doubleQuotes[9] = 1;
        IOUtils.specicalFlags_doubleQuotes[10] = 1;
        IOUtils.specicalFlags_doubleQuotes[11] = 4;
        IOUtils.specicalFlags_doubleQuotes[12] = 1;
        IOUtils.specicalFlags_doubleQuotes[13] = 1;
        IOUtils.specicalFlags_doubleQuotes[34] = 1;
        IOUtils.specicalFlags_doubleQuotes[92] = 1;
        IOUtils.specicalFlags_singleQuotes[0] = 4;
        IOUtils.specicalFlags_singleQuotes[1] = 4;
        IOUtils.specicalFlags_singleQuotes[2] = 4;
        IOUtils.specicalFlags_singleQuotes[3] = 4;
        IOUtils.specicalFlags_singleQuotes[4] = 4;
        IOUtils.specicalFlags_singleQuotes[5] = 4;
        IOUtils.specicalFlags_singleQuotes[6] = 4;
        IOUtils.specicalFlags_singleQuotes[7] = 4;
        IOUtils.specicalFlags_singleQuotes[8] = 1;
        IOUtils.specicalFlags_singleQuotes[9] = 1;
        IOUtils.specicalFlags_singleQuotes[10] = 1;
        IOUtils.specicalFlags_singleQuotes[11] = 4;
        IOUtils.specicalFlags_singleQuotes[12] = 1;
        IOUtils.specicalFlags_singleQuotes[13] = 1;
        IOUtils.specicalFlags_singleQuotes[92] = 1;
        IOUtils.specicalFlags_singleQuotes[39] = 1;
        for (n2 = 14; n2 <= 31; ++n2) {
            IOUtils.specicalFlags_doubleQuotes[n2] = 4;
            IOUtils.specicalFlags_singleQuotes[n2] = 4;
        }
        for (n2 = 127; n2 <= 160; ++n2) {
            IOUtils.specicalFlags_doubleQuotes[n2] = 4;
            IOUtils.specicalFlags_singleQuotes[n2] = 4;
        }
        for (n2 = 0; n2 < 161; ++n2) {
            boolean[] blArray = specicalFlags_doubleQuotesFlags;
            boolean bl2 = specicalFlags_doubleQuotes[n2] != 0;
            blArray[n2] = bl2;
            blArray = specicalFlags_singleQuotesFlags;
            bl2 = specicalFlags_singleQuotes[n2] != 0;
            blArray[n2] = bl2;
        }
        IOUtils.replaceChars[0] = 48;
        IOUtils.replaceChars[1] = 49;
        IOUtils.replaceChars[2] = 50;
        IOUtils.replaceChars[3] = 51;
        IOUtils.replaceChars[4] = 52;
        IOUtils.replaceChars[5] = 53;
        IOUtils.replaceChars[6] = 54;
        IOUtils.replaceChars[7] = 55;
        IOUtils.replaceChars[8] = 98;
        IOUtils.replaceChars[9] = 116;
        IOUtils.replaceChars[10] = 110;
        IOUtils.replaceChars[11] = 118;
        IOUtils.replaceChars[12] = 102;
        IOUtils.replaceChars[13] = 114;
        IOUtils.replaceChars[34] = 34;
        IOUtils.replaceChars[39] = 39;
        IOUtils.replaceChars[47] = 47;
        IOUtils.replaceChars[92] = 92;
        ASCII_CHARS = new char[]{'0', '0', '0', '1', '0', '2', '0', '3', '0', '4', '0', '5', '0', '6', '0', '7', '0', '8', '0', '9', '0', 'A', '0', 'B', '0', 'C', '0', 'D', '0', 'E', '0', 'F', '1', '0', '1', '1', '1', '2', '1', '3', '1', '4', '1', '5', '1', '6', '1', '7', '1', '8', '1', '9', '1', 'A', '1', 'B', '1', 'C', '1', 'D', '1', 'E', '1', 'F', '2', '0', '2', '1', '2', '2', '2', '3', '2', '4', '2', '5', '2', '6', '2', '7', '2', '8', '2', '9', '2', 'A', '2', 'B', '2', 'C', '2', 'D', '2', 'E', '2', 'F'};
        digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        DigitTens = new char[]{'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '3', '3', '3', '3', '3', '3', '3', '3', '3', '3', '4', '4', '4', '4', '4', '4', '4', '4', '4', '4', '5', '5', '5', '5', '5', '5', '5', '5', '5', '5', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '7', '7', '7', '7', '7', '7', '7', '7', '7', '7', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '9', '9', '9', '9', '9', '9', '9', '9', '9', '9'};
        DigitOnes = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        sizeTable = new int[]{9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE};
        CA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
        IA = new int[256];
        Arrays.fill(IA, -1);
        n2 = 0;
        int n3 = CA.length;
        while (true) {
            if (n2 >= n3) {
                IOUtils.IA[61] = 0;
                return;
            }
            IOUtils.IA[IOUtils.CA[n2]] = n2;
            ++n2;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void close(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public static void decode(CharsetDecoder object, ByteBuffer object2, CharBuffer charBuffer) {
        try {
            object2 = ((CharsetDecoder)object).decode((ByteBuffer)object2, charBuffer, true);
            if (!((CoderResult)object2).isUnderflow()) {
                ((CoderResult)object2).throwException();
            }
            if (!((CoderResult)(object = ((CharsetDecoder)object).flush(charBuffer))).isUnderflow()) {
                ((CoderResult)object).throwException();
            }
            return;
        }
        catch (CharacterCodingException characterCodingException) {
            throw new JSONException("utf8 decode error, " + characterCodingException.getMessage(), characterCodingException);
        }
    }

    /*
     * Unable to fully structure code
     */
    public static byte[] decodeBase64(String var0) {
        block16: {
            block17: {
                block14: {
                    block15: {
                        block18: {
                            var4_1 = var0.length();
                            if (var4_1 == 0) {
                                return new byte[0];
                            }
                            var1_2 = 0;
                            var2_3 = var4_1 - 1;
                            while (true) {
                                var5_4 = var2_3;
                                if (var1_2 >= var2_3) break;
                                var5_4 = var2_3;
                                if (IOUtils.IA[var0.charAt(var1_2) & 255] >= 0) break;
                                ++var1_2;
                            }
                            while (var5_4 > 0 && IOUtils.IA[var0.charAt(var5_4) & 255] < 0) {
                                --var5_4;
                            }
                            if (var0.charAt(var5_4) != '=') break block18;
                            if (var0.charAt(var5_4 - 1) == '=') {
                                var3_5 = 2;
lbl19:
                                // 3 sources

                                while (true) {
                                    var7_6 = var5_4 - var1_2 + 1;
                                    if (var4_1 <= 76) break block14;
                                    if (var0.charAt(76) != '\r') break block15;
                                    var2_3 = var7_6 / 78;
lbl24:
                                    // 2 sources

                                    while (true) {
                                        var6_7 = var2_3 << 1;
lbl26:
                                        // 2 sources

                                        while (true) {
                                            var8_8 = ((var7_6 - var6_7) * 6 >> 3) - var3_5;
                                            var13_9 = new byte[var8_8];
                                            var4_1 = 0;
                                            var9_10 = var8_8 / 3;
                                            var7_6 = 0;
                                            var2_3 = var1_2;
                                            var1_2 = var7_6;
                                            block5: while (var1_2 < var9_10 * 3) {
                                                var14_14 = IOUtils.IA;
                                                var10_11 = var2_3 + 1;
                                                var7_6 = var14_14[var0.charAt(var2_3)];
                                                var14_14 = IOUtils.IA;
                                                var2_3 = var10_11 + 1;
                                                var10_11 = var14_14[var0.charAt(var10_11)];
                                                var14_14 = IOUtils.IA;
                                                var11_12 = var2_3 + 1;
                                                var12_13 = var14_14[var0.charAt(var2_3)];
                                                var14_14 = IOUtils.IA;
                                                var2_3 = var11_12 + 1;
                                                var10_11 = var7_6 << 18 | var10_11 << 12 | var12_13 << 6 | var14_14[var0.charAt(var11_12)];
                                                var11_12 = var1_2 + 1;
                                                var13_9[var1_2] = (byte)(var10_11 >> 16);
                                                var7_6 = var11_12 + 1;
                                                var13_9[var11_12] = (byte)(var10_11 >> 8);
                                                var13_9[var7_6] = (byte)var10_11;
                                                var1_2 = var4_1++;
                                                if (var6_7 > 0) {
                                                    var1_2 = var4_1;
                                                    if (var4_1 == 19) {
                                                        var1_2 = var2_3 + 2;
                                                        var4_1 = 0;
lbl58:
                                                        // 2 sources

                                                        while (true) {
                                                            var2_3 = var1_2;
                                                            var1_2 = ++var7_6;
                                                            continue block5;
                                                            break;
                                                        }
                                                    }
                                                }
                                                break block16;
                                            }
                                            break block17;
                                            break;
                                        }
                                        break;
                                    }
                                    break;
                                }
                            }
                            var3_5 = 1;
                            ** GOTO lbl19
                        }
                        var3_5 = 0;
                        ** while (true)
                    }
                    var2_3 = 0;
                    ** while (true)
                }
                var6_7 = 0;
                ** while (true)
            }
            var6_7 = var1_2;
            var6_7 = var2_3;
            if (var1_2 < var8_8) {
                var4_1 = 0;
                var6_7 = 0;
                while (var2_3 <= var5_4 - var3_5) {
                    var4_1 |= IOUtils.IA[var0.charAt(var2_3)] << 18 - var6_7 * 6;
                    ++var6_7;
                    ++var2_3;
                }
                var3_5 = 16;
                while (true) {
                    var6_7 = var1_2;
                    var6_7 = var2_3;
                    if (var1_2 >= var8_8) break;
                    var13_9[var1_2] = (byte)(var4_1 >> var3_5);
                    var3_5 -= 8;
                    ++var1_2;
                }
            }
            return var13_9;
        }
        var4_1 = var1_2;
        var1_2 = var2_3;
        ** while (true)
    }

    /*
     * Unable to fully structure code
     */
    public static byte[] decodeBase64(String var0, int var1_1, int var2_2) {
        block16: {
            block17: {
                block14: {
                    block15: {
                        block18: {
                            if (var2_2 == 0) {
                                return new byte[0];
                            }
                            var3_3 = var1_1;
                            var4_4 = var1_1 + var2_2 - 1;
                            var1_1 = var3_3;
                            while (true) {
                                var5_5 = var4_4;
                                if (var1_1 >= var4_4) break;
                                var5_5 = var4_4;
                                if (IOUtils.IA[var0.charAt(var1_1)] >= 0) break;
                                ++var1_1;
                            }
                            while (var5_5 > 0 && IOUtils.IA[var0.charAt(var5_5)] < 0) {
                                --var5_5;
                            }
                            if (var0.charAt(var5_5) != '=') break block18;
                            if (var0.charAt(var5_5 - 1) == '=') {
                                var3_3 = 2;
lbl19:
                                // 3 sources

                                while (true) {
                                    var4_4 = var5_5 - var1_1 + 1;
                                    if (var2_2 <= 76) break block14;
                                    if (var0.charAt(76) != '\r') break block15;
                                    var2_2 = var4_4 / 78;
lbl24:
                                    // 2 sources

                                    while (true) {
                                        var6_6 = var2_2 << 1;
lbl26:
                                        // 2 sources

                                        while (true) {
                                            var8_7 = ((var4_4 - var6_6) * 6 >> 3) - var3_3;
                                            var13_8 = new byte[var8_7];
                                            var4_4 = 0;
                                            var9_9 = var8_7 / 3;
                                            var7_10 = 0;
                                            var2_2 = var1_1;
                                            var1_1 = var7_10;
                                            block5: while (var1_1 < var9_9 * 3) {
                                                var14_14 = IOUtils.IA;
                                                var10_11 = var2_2 + 1;
                                                var7_10 = var14_14[var0.charAt(var2_2)];
                                                var14_14 = IOUtils.IA;
                                                var2_2 = var10_11 + 1;
                                                var10_11 = var14_14[var0.charAt(var10_11)];
                                                var14_14 = IOUtils.IA;
                                                var11_12 = var2_2 + 1;
                                                var12_13 = var14_14[var0.charAt(var2_2)];
                                                var14_14 = IOUtils.IA;
                                                var2_2 = var11_12 + 1;
                                                var10_11 = var7_10 << 18 | var10_11 << 12 | var12_13 << 6 | var14_14[var0.charAt(var11_12)];
                                                var11_12 = var1_1 + 1;
                                                var13_8[var1_1] = (byte)(var10_11 >> 16);
                                                var7_10 = var11_12 + 1;
                                                var13_8[var11_12] = (byte)(var10_11 >> 8);
                                                var13_8[var7_10] = (byte)var10_11;
                                                var1_1 = var4_4++;
                                                if (var6_6 > 0) {
                                                    var1_1 = var4_4;
                                                    if (var4_4 == 19) {
                                                        var1_1 = var2_2 + 2;
                                                        var4_4 = 0;
lbl58:
                                                        // 2 sources

                                                        while (true) {
                                                            var2_2 = var1_1;
                                                            var1_1 = ++var7_10;
                                                            continue block5;
                                                            break;
                                                        }
                                                    }
                                                }
                                                break block16;
                                            }
                                            break block17;
                                            break;
                                        }
                                        break;
                                    }
                                    break;
                                }
                            }
                            var3_3 = 1;
                            ** GOTO lbl19
                        }
                        var3_3 = 0;
                        ** while (true)
                    }
                    var2_2 = 0;
                    ** while (true)
                }
                var6_6 = 0;
                ** while (true)
            }
            var6_6 = var1_1;
            var6_6 = var2_2;
            if (var1_1 < var8_7) {
                var4_4 = 0;
                var6_6 = 0;
                while (var2_2 <= var5_5 - var3_3) {
                    var4_4 |= IOUtils.IA[var0.charAt(var2_2)] << 18 - var6_6 * 6;
                    ++var6_6;
                    ++var2_2;
                }
                var3_3 = 16;
                while (true) {
                    var6_6 = var1_1;
                    var6_6 = var2_2;
                    if (var1_1 >= var8_7) break;
                    var13_8[var1_1] = (byte)(var4_4 >> var3_3);
                    var3_3 -= 8;
                    ++var1_1;
                }
            }
            return var13_8;
        }
        var4_4 = var1_1;
        var1_1 = var2_2;
        ** while (true)
    }

    /*
     * Unable to fully structure code
     */
    public static byte[] decodeBase64(char[] var0, int var1_1, int var2_2) {
        block16: {
            block17: {
                block14: {
                    block15: {
                        block18: {
                            if (var2_2 == 0) {
                                return new byte[0];
                            }
                            var3_3 = var1_1;
                            var4_4 = var1_1 + var2_2 - 1;
                            var1_1 = var3_3;
                            while (true) {
                                var5_5 = var4_4;
                                if (var1_1 >= var4_4) break;
                                var5_5 = var4_4;
                                if (IOUtils.IA[var0[var1_1]] >= 0) break;
                                ++var1_1;
                            }
                            while (var5_5 > 0 && IOUtils.IA[var0[var5_5]] < 0) {
                                --var5_5;
                            }
                            if (var0[var5_5] != '=') break block18;
                            if (var0[var5_5 - 1] == '=') {
                                var3_3 = 2;
lbl19:
                                // 3 sources

                                while (true) {
                                    var4_4 = var5_5 - var1_1 + 1;
                                    if (var2_2 <= 76) break block14;
                                    if (var0[76] != '\r') break block15;
                                    var2_2 = var4_4 / 78;
lbl24:
                                    // 2 sources

                                    while (true) {
                                        var6_6 = var2_2 << 1;
lbl26:
                                        // 2 sources

                                        while (true) {
                                            var8_7 = ((var4_4 - var6_6) * 6 >> 3) - var3_3;
                                            var13_8 = new byte[var8_7];
                                            var4_4 = 0;
                                            var9_9 = var8_7 / 3;
                                            var7_10 = 0;
                                            var2_2 = var1_1;
                                            var1_1 = var7_10;
                                            block5: while (var1_1 < var9_9 * 3) {
                                                var14_14 = IOUtils.IA;
                                                var10_11 = var2_2 + 1;
                                                var7_10 = var14_14[var0[var2_2]];
                                                var14_14 = IOUtils.IA;
                                                var2_2 = var10_11 + 1;
                                                var10_11 = var14_14[var0[var10_11]];
                                                var14_14 = IOUtils.IA;
                                                var11_12 = var2_2 + 1;
                                                var12_13 = var14_14[var0[var2_2]];
                                                var14_14 = IOUtils.IA;
                                                var2_2 = var11_12 + 1;
                                                var10_11 = var7_10 << 18 | var10_11 << 12 | var12_13 << 6 | var14_14[var0[var11_12]];
                                                var11_12 = var1_1 + 1;
                                                var13_8[var1_1] = (byte)(var10_11 >> 16);
                                                var7_10 = var11_12 + 1;
                                                var13_8[var11_12] = (byte)(var10_11 >> 8);
                                                var13_8[var7_10] = (byte)var10_11;
                                                var1_1 = var4_4++;
                                                if (var6_6 > 0) {
                                                    var1_1 = var4_4;
                                                    if (var4_4 == 19) {
                                                        var1_1 = var2_2 + 2;
                                                        var4_4 = 0;
lbl58:
                                                        // 2 sources

                                                        while (true) {
                                                            var2_2 = var1_1;
                                                            var1_1 = ++var7_10;
                                                            continue block5;
                                                            break;
                                                        }
                                                    }
                                                }
                                                break block16;
                                            }
                                            break block17;
                                            break;
                                        }
                                        break;
                                    }
                                    break;
                                }
                            }
                            var3_3 = 1;
                            ** GOTO lbl19
                        }
                        var3_3 = 0;
                        ** while (true)
                    }
                    var2_2 = 0;
                    ** while (true)
                }
                var6_6 = 0;
                ** while (true)
            }
            var6_6 = var1_1;
            var6_6 = var2_2;
            if (var1_1 < var8_7) {
                var4_4 = 0;
                var6_6 = 0;
                while (var2_2 <= var5_5 - var3_3) {
                    var4_4 |= IOUtils.IA[var0[var2_2]] << 18 - var6_6 * 6;
                    ++var6_6;
                    ++var2_2;
                }
                var3_3 = 16;
                while (true) {
                    var6_6 = var1_1;
                    var6_6 = var2_2;
                    if (var1_1 >= var8_7) break;
                    var13_8[var1_1] = (byte)(var4_4 >> var3_3);
                    var3_3 -= 8;
                    ++var1_1;
                }
            }
            return var13_8;
        }
        var4_4 = var1_1;
        var1_1 = var2_2;
        ** while (true)
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int decodeUTF8(byte[] var0, int var1_1, int var2_2, char[] var3_3) {
        var7_4 = var1_1 + var2_2;
        var8_5 = Math.min(var2_2, var3_3.length);
        var6_6 = 0;
        var5_7 = var1_1;
        while (true) {
            var1_1 = var6_6;
            var2_2 = var5_7;
            if (var6_6 >= var8_5) ** GOTO lbl17
            var1_1 = var6_6++;
            var2_2 = var5_7;
            if (var0[var5_7] < 0) ** GOTO lbl17
            var3_3[var6_6] = (char)var0[var5_7];
            ++var5_7;
        }
        {
            var3_3[var1_1] = (char)(var6_6 << 6 ^ var5_7 ^ 3968);
            ++var1_1;
            while (var2_2 < var7_4) {
                var5_7 = var2_2 + 1;
                var6_6 = var0[var2_2];
                if (var6_6 >= 0) {
                    var3_3[var1_1] = (char)var6_6;
                    ++var1_1;
                    var2_2 = var5_7;
                    continue;
                }
                if (var6_6 >> 5 == -2 && (var6_6 & 30) != 0) {
                    if (var5_7 >= var7_4) return -1;
                    var2_2 = var5_7 + 1;
                    if (((var5_7 = var0[var5_7]) & 192) == 128) continue block1;
                    return -1;
                }
                if (var6_6 >> 4 == -2) {
                    if (var5_7 + 1 >= var7_4) return -1;
                    var2_2 = var5_7 + 1;
                    var5_7 = var0[var5_7];
                    var8_5 = var0[var2_2];
                    if (var6_6 == -32 && (var5_7 & 224) == 128 || (var5_7 & 192) != 128 || (var8_5 & 192) != 128) {
                        return -1;
                    }
                    var4_8 = (char)(var6_6 << 12 ^ var5_7 << 6 ^ (-123008 ^ var8_5));
                    if (Character.isSurrogate(var4_8)) {
                        return -1;
                    }
                    var3_3[var1_1] = var4_8;
                    ++var1_1;
                    ++var2_2;
                    continue;
                }
                if (var6_6 >> 3 != -2) return -1;
                if (var5_7 + 2 >= var7_4) return -1;
                var2_2 = var5_7 + 1;
                var5_7 = var0[var5_7];
                var9_9 = var2_2 + 1;
                var8_5 = var0[var2_2];
                var2_2 = var9_9 + 1;
                var9_9 = var0[var9_9];
                var6_6 = var6_6 << 18 ^ var5_7 << 12 ^ var8_5 << 6 ^ (3678080 ^ var9_9);
                if ((var5_7 & 192) != 128 || (var8_5 & 192) != 128 || (var9_9 & 192) != 128 || !Character.isSupplementaryCodePoint(var6_6)) {
                    return -1;
                }
                var5_7 = var1_1 + 1;
                var3_3[var1_1] = Character.highSurrogate(var6_6);
                var1_1 = var5_7 + 1;
                var3_3[var5_7] = Character.lowSurrogate(var6_6);
            }
            break block1;
        }
        return var1_1;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int encodeUTF8(char[] cArray, int n2, int n3, byte[] byArray) {
        int n4;
        int n5;
        int n6 = n2 + n3;
        int n7 = Math.min(n3, byArray.length);
        n3 = 0;
        while (true) {
            n5 = n3;
            n4 = n2;
            if (n3 >= 0 + n7) break;
            n5 = n3++;
            n4 = n2;
            if (cArray[n2] >= '\u0080') break;
            byArray[n3] = (byte)cArray[n2];
            ++n2;
        }
        while (n4 < n6) {
            n3 = n4 + 1;
            char c2 = cArray[n4];
            if (c2 < '\u0080') {
                n2 = n5 + 1;
                byArray[n5] = (byte)c2;
            } else if (c2 < '\u0800') {
                n2 = n5 + 1;
                byArray[n5] = (byte)(c2 >> 6 | 0xC0);
                byArray[n2] = (byte)(c2 & 0x3F | 0x80);
            } else if (c2 >= '\ud800' && c2 < '\ue000') {
                n2 = n3 - 1;
                if (Character.isHighSurrogate(c2)) {
                    if (n6 - n2 < 2) {
                        n2 = -1;
                    } else {
                        char c3 = cArray[n2 + 1];
                        if (!Character.isLowSurrogate(c3)) {
                            throw new JSONException("encodeUTF8 error", new MalformedInputException(1));
                        }
                        n2 = Character.toCodePoint(c2, c3);
                    }
                } else {
                    if (Character.isLowSurrogate(c2)) {
                        throw new JSONException("encodeUTF8 error", new MalformedInputException(1));
                    }
                    n2 = c2;
                }
                if (n2 < 0) {
                    n2 = n5 + 1;
                    byArray[n5] = 63;
                } else {
                    n4 = n5 + 1;
                    byArray[n5] = (byte)(n2 >> 18 | 0xF0);
                    n5 = n4 + 1;
                    byArray[n4] = (byte)(n2 >> 12 & 0x3F | 0x80);
                    n4 = n5 + 1;
                    byArray[n5] = (byte)(n2 >> 6 & 0x3F | 0x80);
                    byArray[n4] = (byte)(n2 & 0x3F | 0x80);
                    ++n3;
                    n2 = n4 + 1;
                }
            } else {
                n2 = n5 + 1;
                byArray[n5] = (byte)(c2 >> 12 | 0xE0);
                n5 = n2 + 1;
                byArray[n2] = (byte)(c2 >> 6 & 0x3F | 0x80);
                n2 = n5 + 1;
                byArray[n5] = (byte)(c2 & 0x3F | 0x80);
            }
            n5 = ++n2;
            n4 = n3;
        }
        return n5;
    }

    public static boolean firstIdentifier(char c2) {
        return c2 < firstIdentifierFlags.length && firstIdentifierFlags[c2];
    }

    public static void getChars(byte n2, int n3, char[] cArray) {
        int n4 = n2;
        int n5 = n3;
        int n6 = 0;
        n3 = n5;
        n2 = n4;
        if (n4 < 0) {
            n6 = 45;
            n2 = -n4;
            n3 = n5;
        }
        do {
            n4 = 52429 * n2 >>> 19;
            n5 = n3 - 1;
            cArray[n5] = digits[n2 - ((n4 << 3) + (n4 << 1))];
            n3 = n5;
            n2 = n4;
        } while (n4 != 0);
        if (n6 != 0) {
            cArray[n5 - 1] = n6;
        }
    }

    public static void getChars(int n2, int n3, char[] cArray) {
        int n4 = n3;
        int n5 = 0;
        int n6 = n4;
        n3 = n2;
        if (n2 < 0) {
            n5 = 45;
            n3 = -n2;
            n6 = n4;
        }
        while (true) {
            n2 = n6;
            n4 = n3;
            if (n3 < 65536) break;
            n2 = n3 / 100;
            n4 = n3 - ((n2 << 6) + (n2 << 5) + (n2 << 2));
            n3 = n2;
            n2 = n6 - 1;
            cArray[n2] = DigitOnes[n4];
            n6 = n2 - 1;
            cArray[n6] = DigitTens[n4];
        }
        do {
            n3 = 52429 * n4 >>> 19;
            n6 = n2 - 1;
            cArray[n6] = digits[n4 - ((n3 << 3) + (n3 << 1))];
            n2 = n6;
            n4 = n3;
        } while (n3 != 0);
        if (n5 != 0) {
            cArray[n6 - 1] = n5;
        }
    }

    public static void getChars(long l2, int n2, char[] cArray) {
        int n3;
        int n4;
        int n5 = n2;
        int n6 = 0;
        n2 = n5;
        long l3 = l2;
        if (l2 < 0L) {
            n6 = 45;
            l3 = -l2;
            n2 = n5;
        }
        while (l3 > Integer.MAX_VALUE) {
            l2 = l3 / 100L;
            n5 = (int)(l3 - ((l2 << 6) + (l2 << 5) + (l2 << 2)));
            l3 = l2;
            cArray[--n2] = DigitOnes[n5];
            cArray[--n2] = DigitTens[n5];
        }
        n5 = (int)l3;
        while (true) {
            n4 = n2;
            n3 = n5;
            if (n5 < 65536) break;
            n4 = n5 / 100;
            n3 = n5 - ((n4 << 6) + (n4 << 5) + (n4 << 2));
            n5 = n4;
            cArray[--n2] = DigitOnes[n3];
            cArray[--n2] = DigitTens[n3];
        }
        do {
            n2 = 52429 * n3 >>> 19;
            n5 = n4 - 1;
            cArray[n5] = digits[n3 - ((n2 << 3) + (n2 << 1))];
            n4 = n5;
            n3 = n2;
        } while (n2 != 0);
        if (n6 != 0) {
            cArray[n5 - 1] = n6;
        }
    }

    public static boolean isIdent(char c2) {
        return c2 < identifierFlags.length && identifierFlags[c2];
    }

    public static int stringSize(int n2) {
        int n3 = 0;
        while (n2 > sizeTable[n3]) {
            ++n3;
        }
        return n3 + 1;
    }

    public static int stringSize(long l2) {
        long l3 = 10L;
        for (int i2 = 1; i2 < 19; ++i2) {
            if (l2 < l3) {
                return i2;
            }
            l3 *= 10L;
        }
        return 19;
    }
}

