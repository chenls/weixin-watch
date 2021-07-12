/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.pdf417.encoder;

import com.google.zxing.WriterException;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.pdf417.encoder.Compaction;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Arrays;

final class PDF417HighLevelEncoder {
    private static final int BYTE_COMPACTION = 1;
    private static final Charset DEFAULT_ENCODING;
    private static final int ECI_CHARSET = 927;
    private static final int ECI_GENERAL_PURPOSE = 926;
    private static final int ECI_USER_DEFINED = 925;
    private static final int LATCH_TO_BYTE = 924;
    private static final int LATCH_TO_BYTE_PADDED = 901;
    private static final int LATCH_TO_NUMERIC = 902;
    private static final int LATCH_TO_TEXT = 900;
    private static final byte[] MIXED;
    private static final int NUMERIC_COMPACTION = 2;
    private static final byte[] PUNCTUATION;
    private static final int SHIFT_TO_BYTE = 913;
    private static final int SUBMODE_ALPHA = 0;
    private static final int SUBMODE_LOWER = 1;
    private static final int SUBMODE_MIXED = 2;
    private static final int SUBMODE_PUNCTUATION = 3;
    private static final int TEXT_COMPACTION = 0;
    private static final byte[] TEXT_MIXED_RAW;
    private static final byte[] TEXT_PUNCTUATION_RAW;

    static {
        byte by2;
        int n2;
        TEXT_MIXED_RAW = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 38, 13, 9, 44, 58, 35, 45, 46, 36, 47, 43, 37, 42, 61, 94, 0, 32, 0, 0, 0};
        TEXT_PUNCTUATION_RAW = new byte[]{59, 60, 62, 64, 91, 92, 93, 95, 96, 126, 33, 13, 9, 44, 58, 10, 45, 46, 36, 47, 34, 124, 42, 40, 41, 63, 123, 125, 39, 0};
        MIXED = new byte[128];
        PUNCTUATION = new byte[128];
        DEFAULT_ENCODING = Charset.forName("ISO-8859-1");
        Arrays.fill(MIXED, (byte)-1);
        for (n2 = 0; n2 < TEXT_MIXED_RAW.length; n2 = (int)((byte)(n2 + 1))) {
            by2 = TEXT_MIXED_RAW[n2];
            if (by2 <= 0) continue;
            PDF417HighLevelEncoder.MIXED[by2] = n2;
        }
        Arrays.fill(PUNCTUATION, (byte)-1);
        for (n2 = 0; n2 < TEXT_PUNCTUATION_RAW.length; n2 = (int)((byte)(n2 + 1))) {
            by2 = TEXT_PUNCTUATION_RAW[n2];
            if (by2 <= 0) continue;
            PDF417HighLevelEncoder.PUNCTUATION[by2] = n2;
        }
    }

    private PDF417HighLevelEncoder() {
    }

    private static int determineConsecutiveBinaryCount(CharSequence charSequence, byte[] byArray, int n2) throws WriterException {
        int n3;
        int n4 = charSequence.length();
        for (n3 = n2; n3 < n4; ++n3) {
            char c2 = charSequence.charAt(n3);
            int n5 = 0;
            while (true) {
                int n6;
                block5: {
                    block4: {
                        n6 = n5;
                        if (n5 >= 13) break block4;
                        n6 = n5++;
                        if (!PDF417HighLevelEncoder.isDigit(c2)) break block4;
                        n6 = n3 + n5;
                        if (n6 < n4) break block5;
                        n6 = n5;
                    }
                    if (n6 < 13) break;
                    return n3 - n2;
                }
                c2 = charSequence.charAt(n6);
            }
            c2 = charSequence.charAt(n3);
            if (byArray[n3] != 63 || c2 == '?') continue;
            throw new WriterException("Non-encodable character detected: " + c2 + " (Unicode: " + c2 + ')');
        }
        return n3 - n2;
    }

    private static int determineConsecutiveDigitCount(CharSequence charSequence, int n2) {
        int n3 = 0;
        int n4 = 0;
        int n5 = charSequence.length();
        if (n2 < n5) {
            char c2 = charSequence.charAt(n2);
            int n6 = n2;
            n2 = n4;
            while (true) {
                n3 = n2;
                if (!PDF417HighLevelEncoder.isDigit(c2)) break;
                n3 = n2;
                if (n6 >= n5) break;
                n3 = n2 + 1;
                n4 = n6 + 1;
                n2 = n3;
                n6 = n4;
                if (n4 >= n5) continue;
                c2 = charSequence.charAt(n4);
                n2 = n3;
                n6 = n4;
            }
        }
        return n3;
    }

    private static int determineConsecutiveTextCount(CharSequence charSequence, int n2) {
        int n3 = charSequence.length();
        int n4 = n2;
        while (true) {
            int n5;
            block7: {
                block6: {
                    n5 = n4;
                    if (n4 >= n3) break block6;
                    char c2 = charSequence.charAt(n4);
                    int n6 = 0;
                    n5 = n4;
                    while (n6 < 13 && PDF417HighLevelEncoder.isDigit(c2) && n5 < n3) {
                        int n7;
                        n4 = n6 + 1;
                        n5 = n7 = n5 + 1;
                        n6 = n4;
                        if (n7 >= n3) continue;
                        c2 = charSequence.charAt(n7);
                        n5 = n7;
                        n6 = n4;
                    }
                    if (n6 >= 13) {
                        return n5 - n2 - n6;
                    }
                    n4 = n5;
                    if (n6 > 0) continue;
                    if (PDF417HighLevelEncoder.isText(charSequence.charAt(n5))) break block7;
                }
                return n5 - n2;
            }
            n4 = n5 + 1;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void encodeBinary(byte[] byArray, int n2, int n3, int n4, StringBuilder stringBuilder) {
        if (n3 == 1 && n4 == 0) {
            stringBuilder.append('\u0391');
        } else {
            n4 = n3 % 6 == 0 ? 1 : 0;
            if (n4 != 0) {
                stringBuilder.append('\u039c');
            } else {
                stringBuilder.append('\u0385');
            }
        }
        int n5 = n4 = n2;
        if (n3 >= 6) {
            char[] cArray = new char[5];
            while (true) {
                n5 = n4;
                if (n2 + n3 - n4 < 6) break;
                long l2 = 0L;
                for (n5 = 0; n5 < 6; ++n5) {
                    l2 = (l2 << 8) + (long)(byArray[n4 + n5] & 0xFF);
                }
                for (n5 = 0; n5 < 5; l2 /= 900L, ++n5) {
                    cArray[n5] = (char)(l2 % 900L);
                }
                for (n5 = cArray.length - 1; n5 >= 0; --n5) {
                    stringBuilder.append(cArray[n5]);
                }
                n4 += 6;
            }
        }
        while (n5 < n2 + n3) {
            stringBuilder.append((char)(byArray[n5] & 0xFF));
            ++n5;
        }
        return;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    static String encodeHighLevel(String object, Compaction object2, Charset object3) throws WriterException {
        Charset charset;
        void var2_5;
        StringBuilder stringBuilder = new StringBuilder(((String)object).length());
        if (var2_5 == null) {
            charset = DEFAULT_ENCODING;
        } else {
            charset = var2_5;
            if (!DEFAULT_ENCODING.equals(var2_5)) {
                CharacterSetECI characterSetECI = CharacterSetECI.getCharacterSetECIByName(var2_5.name());
                charset = var2_5;
                if (characterSetECI != null) {
                    PDF417HighLevelEncoder.encodingECI(characterSetECI.getValue(), stringBuilder);
                    charset = var2_5;
                }
            }
        }
        int n2 = ((String)object).length();
        int n3 = 0;
        int n4 = 0;
        Object var2_6 = null;
        if (object2 == Compaction.TEXT) {
            PDF417HighLevelEncoder.encodeText((CharSequence)object, 0, n2, stringBuilder, 0);
            return stringBuilder.toString();
        }
        if (object2 == Compaction.BYTE) {
            object = ((String)object).getBytes(charset);
            PDF417HighLevelEncoder.encodeBinary((byte[])object, 0, ((Object)object).length, 1, stringBuilder);
            return stringBuilder.toString();
        }
        if (object2 == Compaction.NUMERIC) {
            stringBuilder.append('\u0386');
            PDF417HighLevelEncoder.encodeNumeric((String)object, 0, n2, stringBuilder);
            return stringBuilder.toString();
        }
        int n5 = 0;
        Object var1_2 = var2_6;
        while (n3 < n2) {
            void var2_10;
            void var1_3;
            int n6 = PDF417HighLevelEncoder.determineConsecutiveDigitCount((CharSequence)object, n3);
            if (n6 >= 13) {
                stringBuilder.append('\u0386');
                n5 = 2;
                n4 = 0;
                PDF417HighLevelEncoder.encodeNumeric((String)object, n3, n6, stringBuilder);
                n3 += n6;
                continue;
            }
            int n7 = PDF417HighLevelEncoder.determineConsecutiveTextCount((CharSequence)object, n3);
            if (n7 >= 5 || n6 == n2) {
                n6 = n5;
                if (n5 != 0) {
                    stringBuilder.append('\u0384');
                    n6 = 0;
                    n4 = 0;
                }
                n4 = PDF417HighLevelEncoder.encodeText((CharSequence)object, n3, n7, stringBuilder, n4);
                n3 += n7;
                n5 = n6;
                continue;
            }
            void var2_8 = var1_3;
            if (var1_3 == null) {
                byte[] byArray = ((String)object).getBytes(charset);
            }
            n6 = n7 = PDF417HighLevelEncoder.determineConsecutiveBinaryCount((CharSequence)object, (byte[])var2_10, n3);
            if (n7 == 0) {
                n6 = 1;
            }
            if (n6 == 1 && n5 == 0) {
                PDF417HighLevelEncoder.encodeBinary((byte[])var2_10, n3, 1, 0, stringBuilder);
            } else {
                PDF417HighLevelEncoder.encodeBinary((byte[])var2_10, n3, n6, n5, stringBuilder);
                n5 = 1;
                n4 = 0;
            }
            n3 += n6;
            void var1_4 = var2_10;
        }
        return stringBuilder.toString();
    }

    private static void encodeNumeric(String string2, int n2, int n3, StringBuilder stringBuilder) {
        int n4;
        StringBuilder stringBuilder2 = new StringBuilder(n3 / 3 + 1);
        BigInteger bigInteger = BigInteger.valueOf(900L);
        BigInteger bigInteger2 = BigInteger.valueOf(0L);
        for (int i2 = 0; i2 < n3; i2 += n4) {
            BigInteger bigInteger3;
            stringBuilder2.setLength(0);
            n4 = Math.min(44, n3 - i2);
            BigInteger bigInteger4 = new BigInteger('1' + string2.substring(n2 + i2, n2 + i2 + n4));
            do {
                stringBuilder2.append((char)bigInteger4.mod(bigInteger).intValue());
                bigInteger4 = bigInteger3 = bigInteger4.divide(bigInteger);
            } while (!bigInteger3.equals(bigInteger2));
            for (int i3 = stringBuilder2.length() - 1; i3 >= 0; --i3) {
                stringBuilder.append(stringBuilder2.charAt(i3));
            }
        }
    }

    /*
     * Unable to fully structure code
     */
    private static int encodeText(CharSequence var0, int var1_1, int var2_2, StringBuilder var3_3, int var4_4) {
        block18: {
            block17: {
                var8_5 = new StringBuilder(var2_2);
                var6_6 = 0;
                block5: while (true) {
                    var5_7 = var0.charAt(var1_1 + var6_6);
                    switch (var4_4) {
                        default: {
                            if (!PDF417HighLevelEncoder.isPunctuation(var5_7)) break;
                            var8_5.append((char)PDF417HighLevelEncoder.PUNCTUATION[var5_7]);
lbl10:
                            // 10 sources

                            while (true) {
                                var6_6 = var7_8 = var6_6 + 1;
                                if (var7_8 < var2_2) continue block5;
                                var2_2 = 0;
                                var7_8 = var8_5.length();
                                block7: for (var1_1 = 0; var1_1 < var7_8; ++var1_1) {
                                    if (var1_1 % 2 == 0) break block5;
                                    var6_6 = 1;
lbl18:
                                    // 2 sources

                                    while (var6_6 != 0) {
                                        var5_7 = (char)(var2_2 * 30 + var8_5.charAt(var1_1));
                                        var3_3.append(var5_7);
                                        var2_2 = var5_7;
lbl23:
                                        // 2 sources

                                        continue block7;
                                    }
                                    break block17;
                                }
                                break block18;
                                break;
                            }
                        }
                        case 0: {
                            if (!PDF417HighLevelEncoder.isAlphaUpper(var5_7)) ** GOTO lbl36
                            if (var5_7 != ' ') ** GOTO lbl33
                            var8_5.append('\u001a');
                            ** GOTO lbl10
lbl33:
                            // 1 sources

                            var8_5.append((char)(var5_7 - 65));
                            ** GOTO lbl10
lbl36:
                            // 1 sources

                            if (PDF417HighLevelEncoder.isAlphaLower(var5_7)) {
                                var4_4 = 1;
                                var8_5.append('\u001b');
                                continue block5;
                            }
                            if (PDF417HighLevelEncoder.isMixed(var5_7)) {
                                var4_4 = 2;
                                var8_5.append('\u001c');
                                continue block5;
                            }
                            var8_5.append('\u001d');
                            var8_5.append((char)PDF417HighLevelEncoder.PUNCTUATION[var5_7]);
                            ** GOTO lbl10
                        }
                        case 1: {
                            if (!PDF417HighLevelEncoder.isAlphaLower(var5_7)) ** GOTO lbl60
                            if (var5_7 != ' ') ** GOTO lbl57
                            var8_5.append('\u001a');
                            ** GOTO lbl10
lbl57:
                            // 1 sources

                            var8_5.append((char)(var5_7 - 97));
                            ** GOTO lbl10
lbl60:
                            // 1 sources

                            if (!PDF417HighLevelEncoder.isAlphaUpper(var5_7)) ** GOTO lbl66
                            var8_5.append('\u001b');
                            var8_5.append((char)(var5_7 - 65));
                            ** GOTO lbl10
lbl66:
                            // 1 sources

                            if (PDF417HighLevelEncoder.isMixed(var5_7)) {
                                var4_4 = 2;
                                var8_5.append('\u001c');
                                continue block5;
                            }
                            var8_5.append('\u001d');
                            var8_5.append((char)PDF417HighLevelEncoder.PUNCTUATION[var5_7]);
                            ** GOTO lbl10
                        }
                        case 2: {
                            if (!PDF417HighLevelEncoder.isMixed(var5_7)) ** GOTO lbl81
                            var8_5.append((char)PDF417HighLevelEncoder.MIXED[var5_7]);
                            ** GOTO lbl10
lbl81:
                            // 1 sources

                            if (PDF417HighLevelEncoder.isAlphaUpper(var5_7)) {
                                var4_4 = 0;
                                var8_5.append('\u001c');
                                continue block5;
                            }
                            if (PDF417HighLevelEncoder.isAlphaLower(var5_7)) {
                                var4_4 = 1;
                                var8_5.append('\u001b');
                                continue block5;
                            }
                            if (var1_1 + var6_6 + 1 < var2_2 && PDF417HighLevelEncoder.isPunctuation(var0.charAt(var1_1 + var6_6 + 1))) {
                                var4_4 = 3;
                                var8_5.append('\u0019');
                                continue block5;
                            }
                            var8_5.append('\u001d');
                            var8_5.append((char)PDF417HighLevelEncoder.PUNCTUATION[var5_7]);
                            ** continue;
                        }
                    }
                    var4_4 = 0;
                    var8_5.append('\u001d');
                }
                var6_6 = 0;
                ** GOTO lbl18
            }
            var2_2 = var8_5.charAt(var1_1);
            ** while (true)
        }
        if (var7_8 % 2 != 0) {
            var3_3.append((char)(var2_2 * 30 + 29));
        }
        return var4_4;
    }

    private static void encodingECI(int n2, StringBuilder stringBuilder) throws WriterException {
        if (n2 >= 0 && n2 < 900) {
            stringBuilder.append('\u039f');
            stringBuilder.append((char)n2);
            return;
        }
        if (n2 < 810900) {
            stringBuilder.append('\u039e');
            stringBuilder.append((char)(n2 / 900 - 1));
            stringBuilder.append((char)(n2 % 900));
            return;
        }
        if (n2 < 811800) {
            stringBuilder.append('\u039d');
            stringBuilder.append((char)(810900 - n2));
            return;
        }
        throw new WriterException("ECI number not in valid range from 0..811799, but was " + n2);
    }

    private static boolean isAlphaLower(char c2) {
        return c2 == ' ' || c2 >= 'a' && c2 <= 'z';
    }

    private static boolean isAlphaUpper(char c2) {
        return c2 == ' ' || c2 >= 'A' && c2 <= 'Z';
    }

    private static boolean isDigit(char c2) {
        return c2 >= '0' && c2 <= '9';
    }

    private static boolean isMixed(char c2) {
        return MIXED[c2] != -1;
    }

    private static boolean isPunctuation(char c2) {
        return PUNCTUATION[c2] != -1;
    }

    private static boolean isText(char c2) {
        return c2 == '\t' || c2 == '\n' || c2 == '\r' || c2 >= ' ' && c2 <= '~';
    }
}

