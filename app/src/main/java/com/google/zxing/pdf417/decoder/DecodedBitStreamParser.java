/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.pdf417.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.pdf417.PDF417ResultMetadata;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Arrays;

final class DecodedBitStreamParser {
    private static final int AL = 28;
    private static final int AS = 27;
    private static final int BEGIN_MACRO_PDF417_CONTROL_BLOCK = 928;
    private static final int BEGIN_MACRO_PDF417_OPTIONAL_FIELD = 923;
    private static final int BYTE_COMPACTION_MODE_LATCH = 901;
    private static final int BYTE_COMPACTION_MODE_LATCH_6 = 924;
    private static final Charset DEFAULT_ENCODING;
    private static final int ECI_CHARSET = 927;
    private static final int ECI_GENERAL_PURPOSE = 926;
    private static final int ECI_USER_DEFINED = 925;
    private static final BigInteger[] EXP900;
    private static final int LL = 27;
    private static final int MACRO_PDF417_TERMINATOR = 922;
    private static final int MAX_NUMERIC_CODEWORDS = 15;
    private static final char[] MIXED_CHARS;
    private static final int ML = 28;
    private static final int MODE_SHIFT_TO_BYTE_COMPACTION_MODE = 913;
    private static final int NUMBER_OF_SEQUENCE_CODEWORDS = 2;
    private static final int NUMERIC_COMPACTION_MODE_LATCH = 902;
    private static final int PAL = 29;
    private static final int PL = 25;
    private static final int PS = 29;
    private static final char[] PUNCT_CHARS;
    private static final int TEXT_COMPACTION_MODE_LATCH = 900;

    static {
        BigInteger bigInteger;
        PUNCT_CHARS = new char[]{';', '<', '>', '@', '[', '\\', ']', '_', '`', '~', '!', '\r', '\t', ',', ':', '\n', '-', '.', '$', '/', '\"', '|', '*', '(', ')', '?', '{', '}', '\''};
        MIXED_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '&', '\r', '\t', ',', ':', '#', '-', '.', '$', '/', '+', '%', '*', '=', '^'};
        DEFAULT_ENCODING = Charset.forName("ISO-8859-1");
        EXP900 = new BigInteger[16];
        DecodedBitStreamParser.EXP900[0] = BigInteger.ONE;
        DecodedBitStreamParser.EXP900[1] = bigInteger = BigInteger.valueOf(900L);
        for (int i2 = 2; i2 < EXP900.length; ++i2) {
            DecodedBitStreamParser.EXP900[i2] = EXP900[i2 - 1].multiply(bigInteger);
        }
    }

    private DecodedBitStreamParser() {
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int byteCompaction(int n2, int[] nArray, Charset charset, int n3, StringBuilder stringBuilder) {
        int n4;
        ByteArrayOutputStream byteArrayOutputStream;
        block16: {
            block18: {
                block17: {
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    if (n2 == 901) break block17;
                    n4 = n3;
                    if (n2 != 924) break block16;
                    break block18;
                }
                int n5 = 0;
                long l2 = 0L;
                int[] nArray2 = new int[6];
                int n6 = 0;
                n4 = nArray[n3];
                n2 = n3 + 1;
                n3 = n5;
                while (true) {
                    if (n2 < nArray[0] && n6 == 0) {
                        n5 = n3 + 1;
                        nArray2[n3] = n4;
                        l2 = 900L * l2 + (long)n4;
                        n3 = n2 + 1;
                        n4 = nArray[n2];
                        if (n4 == 900 || n4 == 901 || n4 == 902 || n4 == 924 || n4 == 928 || n4 == 923 || n4 == 922) {
                            n2 = n3 - 1;
                            n6 = 1;
                            n3 = n5;
                            continue;
                        }
                        if (n5 % 5 == 0 && n5 > 0) {
                            for (n2 = 0; n2 < 6; ++n2) {
                                byteArrayOutputStream.write((byte)(l2 >> (5 - n2) * 8));
                            }
                            l2 = 0L;
                            n5 = 0;
                            n2 = n3;
                            n3 = n5;
                            continue;
                        }
                    } else {
                        n6 = n3;
                        if (n2 == nArray[0]) {
                            n6 = n3;
                            if (n4 < 900) {
                                nArray2[n3] = n4;
                                n6 = n3 + 1;
                            }
                        }
                        n3 = 0;
                        while (true) {
                            n4 = n2;
                            if (n3 < n6) {
                                byteArrayOutputStream.write((byte)nArray2[n3]);
                                ++n3;
                                continue;
                            }
                            break block16;
                            break;
                        }
                    }
                    n2 = n3;
                    n3 = n5;
                }
            }
            n2 = 0;
            long l3 = 0L;
            int n7 = 0;
            int n8 = n3;
            n3 = n2;
            while (true) {
                long l4;
                int n9;
                n4 = n8;
                if (n8 >= nArray[0]) break;
                n4 = n8;
                if (n7 != 0) break;
                n2 = n8 + 1;
                if ((n8 = nArray[n8]) < 900) {
                    n9 = n3 + 1;
                    l4 = 900L * l3 + (long)n8;
                    n4 = n7;
                } else if (n8 == 900 || n8 == 901 || n8 == 902 || n8 == 924 || n8 == 928 || n8 == 923 || n8 == 922) {
                    --n2;
                    n4 = 1;
                    n9 = n3;
                    l4 = l3;
                } else {
                    n9 = n3;
                    n4 = n7;
                    l4 = l3;
                }
                n3 = n9;
                n7 = n4;
                l3 = l4;
                n8 = n2;
                if (n9 % 5 != 0) continue;
                n3 = n9;
                n7 = n4;
                l3 = l4;
                n8 = n2;
                if (n9 <= 0) continue;
                for (n3 = 0; n3 < 6; ++n3) {
                    byteArrayOutputStream.write((byte)(l4 >> (5 - n3) * 8));
                }
                l3 = 0L;
                n3 = 0;
                n7 = n4;
                n8 = n2;
            }
        }
        stringBuilder.append(new String(byteArrayOutputStream.toByteArray(), charset));
        return n4;
    }

    /*
     * Unable to fully structure code
     */
    static DecoderResult decode(int[] var0, String var1_1) throws FormatException {
        var7_2 = new StringBuilder(((int[])var0).length * 2);
        var5_3 = DecodedBitStreamParser.DEFAULT_ENCODING;
        var2_4 = var0[1];
        var6_5 = new PDF417ResultMetadata();
        var3_6 = 1 + 1;
        block11: while (var3_6 < var0[0]) {
            switch (var2_4) {
                default: {
                    var2_4 = DecodedBitStreamParser.textCompaction((int[])var0, var3_6 - 1, var7_2);
lbl10:
                    // 9 sources

                    while (var2_4 < ((int[])var0).length) {
                        var3_6 = var0[var2_4];
                        var4_7 = var2_4 + 1;
                        var2_4 = var3_6;
                        var3_6 = var4_7;
                        continue block11;
                    }
                    break;
                }
                case 900: {
                    var2_4 = DecodedBitStreamParser.textCompaction((int[])var0, var3_6, var7_2);
                    ** GOTO lbl10
                }
                case 901: 
                case 924: {
                    var2_4 = DecodedBitStreamParser.byteCompaction(var2_4, (int[])var0, var5_3, var3_6, var7_2);
                    ** GOTO lbl10
                }
                case 913: {
                    var7_2.append((char)var0[var3_6]);
                    var2_4 = var3_6 + 1;
                    ** GOTO lbl10
                }
                case 902: {
                    var2_4 = DecodedBitStreamParser.numericCompaction((int[])var0, var3_6, var7_2);
                    ** GOTO lbl10
                }
                case 927: {
                    var5_3 = Charset.forName(CharacterSetECI.getCharacterSetECIByValue(var0[var3_6]).name());
                    var2_4 = var3_6 + 1;
                    ** GOTO lbl10
                }
                case 926: {
                    var2_4 = var3_6 + 2;
                    ** GOTO lbl10
                }
                case 925: {
                    var2_4 = var3_6 + 1;
                    ** GOTO lbl10
                }
                case 928: {
                    var2_4 = DecodedBitStreamParser.decodeMacroBlock((int[])var0, var3_6, var6_5);
                    ** GOTO lbl10
                }
                case 922: 
                case 923: {
                    throw FormatException.getFormatInstance();
                }
            }
            throw FormatException.getFormatInstance();
        }
        if (var7_2.length() == 0) {
            throw FormatException.getFormatInstance();
        }
        var0 = new DecoderResult(null, var7_2.toString(), null, var1_1);
        var0.setOther(var6_5);
        return var0;
    }

    private static String decodeBase900toBase10(int[] object, int n2) throws FormatException {
        BigInteger bigInteger = BigInteger.ZERO;
        for (int i2 = 0; i2 < n2; ++i2) {
            bigInteger = bigInteger.add(EXP900[n2 - i2 - 1].multiply(BigInteger.valueOf((long)object[i2])));
        }
        object = bigInteger.toString();
        if (((String)object).charAt(0) != '1') {
            throw FormatException.getFormatInstance();
        }
        return ((String)object).substring(1);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int decodeMacroBlock(int[] nArray, int n2, PDF417ResultMetadata pDF417ResultMetadata) throws FormatException {
        int n3;
        if (n2 + 2 > nArray[0]) {
            throw FormatException.getFormatInstance();
        }
        int[] nArray2 = new int[2];
        for (n3 = 0; n3 < 2; ++n3, ++n2) {
            nArray2[n3] = nArray[n2];
        }
        pDF417ResultMetadata.setSegmentIndex(Integer.parseInt(DecodedBitStreamParser.decodeBase900toBase10(nArray2, 2)));
        StringBuilder stringBuilder = new StringBuilder();
        n3 = DecodedBitStreamParser.textCompaction(nArray, n2, stringBuilder);
        pDF417ResultMetadata.setFileId(stringBuilder.toString());
        if (nArray[n3] != 923) {
            n2 = n3;
            if (nArray[n3] != 922) return n2;
            pDF417ResultMetadata.setLastSegment(true);
            return n3 + 1;
        } else {
            n2 = n3 + 1;
            int[] nArray3 = new int[nArray[0] - n2];
            int n4 = 0;
            n3 = 0;
            while (n2 < nArray[0] && n3 == 0) {
                int n5 = n2 + 1;
                if ((n2 = nArray[n2]) < 900) {
                    nArray3[n4] = n2;
                    ++n4;
                    n2 = n5;
                    continue;
                }
                switch (n2) {
                    default: {
                        throw FormatException.getFormatInstance();
                    }
                    case 922: 
                }
                pDF417ResultMetadata.setLastSegment(true);
                n2 = n5 + 1;
                n3 = 1;
            }
            pDF417ResultMetadata.setOptionalData(Arrays.copyOf(nArray3, n4));
        }
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void decodeTextCompaction(int[] nArray, int[] nArray2, int n2, StringBuilder stringBuilder) {
        Mode mode = Mode.ALPHA;
        Mode mode2 = Mode.ALPHA;
        int n3 = 0;
        while (n3 < n2) {
            int n4;
            Mode mode3;
            int n5 = nArray[n3];
            int n6 = 0;
            switch (1.$SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[mode.ordinal()]) {
                default: {
                    mode3 = mode2;
                    n4 = n6;
                    break;
                }
                case 1: {
                    if (n5 < 26) {
                        n4 = (char)(n5 + 65);
                        mode3 = mode2;
                        break;
                    }
                    if (n5 == 26) {
                        n4 = 32;
                        mode3 = mode2;
                        break;
                    }
                    if (n5 == 27) {
                        mode = Mode.LOWER;
                        n4 = n6;
                        mode3 = mode2;
                        break;
                    }
                    if (n5 == 28) {
                        mode = Mode.MIXED;
                        n4 = n6;
                        mode3 = mode2;
                        break;
                    }
                    if (n5 == 29) {
                        mode2 = Mode.PUNCT_SHIFT;
                        n4 = n6;
                        mode3 = mode;
                        mode = mode2;
                        break;
                    }
                    if (n5 == 913) {
                        stringBuilder.append((char)nArray2[n3]);
                        n4 = n6;
                        mode3 = mode2;
                        break;
                    }
                    n4 = n6;
                    mode3 = mode2;
                    if (n5 != 900) break;
                    mode = Mode.ALPHA;
                    n4 = n6;
                    mode3 = mode2;
                    break;
                }
                case 2: {
                    if (n5 < 26) {
                        n4 = (char)(n5 + 97);
                        mode3 = mode2;
                        break;
                    }
                    if (n5 == 26) {
                        n4 = 32;
                        mode3 = mode2;
                        break;
                    }
                    if (n5 == 27) {
                        mode2 = Mode.ALPHA_SHIFT;
                        n4 = n6;
                        mode3 = mode;
                        mode = mode2;
                        break;
                    }
                    if (n5 == 28) {
                        mode = Mode.MIXED;
                        n4 = n6;
                        mode3 = mode2;
                        break;
                    }
                    if (n5 == 29) {
                        mode2 = Mode.PUNCT_SHIFT;
                        n4 = n6;
                        mode3 = mode;
                        mode = mode2;
                        break;
                    }
                    if (n5 == 913) {
                        stringBuilder.append((char)nArray2[n3]);
                        n4 = n6;
                        mode3 = mode2;
                        break;
                    }
                    n4 = n6;
                    mode3 = mode2;
                    if (n5 != 900) break;
                    mode = Mode.ALPHA;
                    n4 = n6;
                    mode3 = mode2;
                    break;
                }
                case 3: {
                    if (n5 < 25) {
                        n4 = MIXED_CHARS[n5];
                        mode3 = mode2;
                        break;
                    }
                    if (n5 == 25) {
                        mode = Mode.PUNCT;
                        n4 = n6;
                        mode3 = mode2;
                        break;
                    }
                    if (n5 == 26) {
                        n4 = 32;
                        mode3 = mode2;
                        break;
                    }
                    if (n5 == 27) {
                        mode = Mode.LOWER;
                        n4 = n6;
                        mode3 = mode2;
                        break;
                    }
                    if (n5 == 28) {
                        mode = Mode.ALPHA;
                        n4 = n6;
                        mode3 = mode2;
                        break;
                    }
                    if (n5 == 29) {
                        mode2 = Mode.PUNCT_SHIFT;
                        n4 = n6;
                        mode3 = mode;
                        mode = mode2;
                        break;
                    }
                    if (n5 == 913) {
                        stringBuilder.append((char)nArray2[n3]);
                        n4 = n6;
                        mode3 = mode2;
                        break;
                    }
                    n4 = n6;
                    mode3 = mode2;
                    if (n5 != 900) break;
                    mode = Mode.ALPHA;
                    n4 = n6;
                    mode3 = mode2;
                    break;
                }
                case 4: {
                    if (n5 < 29) {
                        n4 = PUNCT_CHARS[n5];
                        mode3 = mode2;
                        break;
                    }
                    if (n5 == 29) {
                        mode = Mode.ALPHA;
                        n4 = n6;
                        mode3 = mode2;
                        break;
                    }
                    if (n5 == 913) {
                        stringBuilder.append((char)nArray2[n3]);
                        n4 = n6;
                        mode3 = mode2;
                        break;
                    }
                    n4 = n6;
                    mode3 = mode2;
                    if (n5 != 900) break;
                    mode = Mode.ALPHA;
                    n4 = n6;
                    mode3 = mode2;
                    break;
                }
                case 5: {
                    mode = mode2;
                    if (n5 < 26) {
                        n4 = (char)(n5 + 65);
                        mode3 = mode2;
                        break;
                    }
                    if (n5 == 26) {
                        n4 = 32;
                        mode3 = mode2;
                        break;
                    }
                    n4 = n6;
                    mode3 = mode2;
                    if (n5 != 900) break;
                    mode = Mode.ALPHA;
                    n4 = n6;
                    mode3 = mode2;
                    break;
                }
                case 6: {
                    mode = mode2;
                    if (n5 < 29) {
                        n4 = PUNCT_CHARS[n5];
                        mode3 = mode2;
                        break;
                    }
                    if (n5 == 29) {
                        mode = Mode.ALPHA;
                        n4 = n6;
                        mode3 = mode2;
                        break;
                    }
                    if (n5 == 913) {
                        stringBuilder.append((char)nArray2[n3]);
                        n4 = n6;
                        mode3 = mode2;
                        break;
                    }
                    n4 = n6;
                    mode3 = mode2;
                    if (n5 != 900) break;
                    mode = Mode.ALPHA;
                    n4 = n6;
                    mode3 = mode2;
                }
            }
            if (n4 != 0) {
                stringBuilder.append((char)n4);
            }
            ++n3;
            mode2 = mode3;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int numericCompaction(int[] nArray, int n2, StringBuilder stringBuilder) throws FormatException {
        int n3 = 0;
        boolean bl2 = false;
        int[] nArray2 = new int[15];
        int n4 = n2;
        while (true) {
            int n5;
            boolean bl3;
            block5: {
                block6: {
                    block3: {
                        block4: {
                            if (n4 >= nArray[0] || bl2) break block3;
                            n2 = n4 + 1;
                            n4 = nArray[n4];
                            bl3 = bl2;
                            if (n2 == nArray[0]) {
                                bl3 = true;
                            }
                            if (n4 >= 900) break block4;
                            nArray2[n3] = n4;
                            n5 = n3 + 1;
                            break block5;
                        }
                        if (n4 != 900 && n4 != 901 && n4 != 924 && n4 != 928 && n4 != 923 && n4 != 922) break block6;
                        --n2;
                        bl3 = true;
                        n5 = n3;
                        break block5;
                    }
                    return n4;
                }
                n5 = n3;
            }
            if (n5 % 15 != 0 && n4 != 902) {
                n3 = n5;
                bl2 = bl3;
                n4 = n2;
                if (!bl3) continue;
            }
            n3 = n5;
            bl2 = bl3;
            n4 = n2;
            if (n5 <= 0) continue;
            stringBuilder.append(DecodedBitStreamParser.decodeBase900toBase10(nArray2, n5));
            n3 = 0;
            bl2 = bl3;
            n4 = n2;
        }
    }

    private static int textCompaction(int[] nArray, int n2, StringBuilder stringBuilder) {
        int[] nArray2 = new int[(nArray[0] - n2) * 2];
        int[] nArray3 = new int[(nArray[0] - n2) * 2];
        int n3 = 0;
        boolean bl2 = false;
        block5: while (n2 < nArray[0] && !bl2) {
            int n4 = n2 + 1;
            if ((n2 = nArray[n2]) < 900) {
                nArray2[n3] = n2 / 30;
                nArray2[n3 + 1] = n2 % 30;
                n3 += 2;
                n2 = n4;
                continue;
            }
            switch (n2) {
                default: {
                    n2 = n4;
                    continue block5;
                }
                case 900: {
                    nArray2[n3] = 900;
                    ++n3;
                    n2 = n4;
                    continue block5;
                }
                case 901: 
                case 902: 
                case 922: 
                case 923: 
                case 924: 
                case 928: {
                    n2 = n4 - 1;
                    bl2 = true;
                    continue block5;
                }
                case 913: 
            }
            nArray2[n3] = 913;
            n2 = n4 + 1;
            nArray3[n3] = nArray[n4];
            ++n3;
        }
        DecodedBitStreamParser.decodeTextCompaction(nArray2, nArray3, n3, stringBuilder);
        return n2;
    }

    private static enum Mode {
        ALPHA,
        LOWER,
        MIXED,
        PUNCT,
        ALPHA_SHIFT,
        PUNCT_SHIFT;

    }
}

