/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.datamatrix.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.BitSource;
import com.google.zxing.common.DecoderResult;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

final class DecodedBitStreamParser {
    private static final char[] C40_BASIC_SET_CHARS = new char[]{'*', '*', '*', ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final char[] C40_SHIFT2_SET_CHARS = new char[]{'!', '\"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_'};
    private static final char[] TEXT_BASIC_SET_CHARS = new char[]{'*', '*', '*', ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final char[] TEXT_SHIFT2_SET_CHARS = C40_SHIFT2_SET_CHARS;
    private static final char[] TEXT_SHIFT3_SET_CHARS = new char[]{'`', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '{', '|', '}', '~', '\u007f'};

    private DecodedBitStreamParser() {
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    static DecoderResult decode(byte[] byArray) throws FormatException {
        void var1_11;
        void var1_8;
        Object object = new BitSource(byArray);
        StringBuilder stringBuilder = new StringBuilder(100);
        StringBuilder stringBuilder2 = new StringBuilder(0);
        ArrayList<byte[]> arrayList = new ArrayList<byte[]>(1);
        Mode mode = Mode.ASCII_ENCODE;
        do {
            void var1_6;
            if (var1_6 == Mode.ASCII_ENCODE) {
                Mode mode2 = DecodedBitStreamParser.decodeAsciiSegment((BitSource)object, stringBuilder, stringBuilder2);
                continue;
            }
            switch (1.$SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode[var1_6.ordinal()]) {
                default: {
                    throw FormatException.getFormatInstance();
                }
                case 1: {
                    DecodedBitStreamParser.decodeC40Segment((BitSource)object, stringBuilder);
                    break;
                }
                case 2: {
                    DecodedBitStreamParser.decodeTextSegment((BitSource)object, stringBuilder);
                    break;
                }
                case 3: {
                    DecodedBitStreamParser.decodeAnsiX12Segment((BitSource)object, stringBuilder);
                    break;
                }
                case 4: {
                    DecodedBitStreamParser.decodeEdifactSegment((BitSource)object, stringBuilder);
                    break;
                }
                case 5: {
                    DecodedBitStreamParser.decodeBase256Segment((BitSource)object, stringBuilder, arrayList);
                }
            }
            Mode mode3 = Mode.ASCII_ENCODE;
        } while (var1_8 != Mode.PAD_ENCODE && ((BitSource)object).available() > 0);
        if (stringBuilder2.length() > 0) {
            stringBuilder.append((CharSequence)stringBuilder2);
        }
        object = stringBuilder.toString();
        ArrayList<byte[]> arrayList2 = arrayList;
        if (arrayList.isEmpty()) {
            return new DecoderResult(byArray, (String)object, (List<byte[]>)var1_11, null);
        }
        return new DecoderResult(byArray, (String)object, (List<byte[]>)var1_11, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void decodeAnsiX12Segment(BitSource bitSource, StringBuilder stringBuilder) throws FormatException {
        int[] nArray = new int[3];
        do {
            int n2;
            if (bitSource.available() == 8 || (n2 = bitSource.readBits(8)) == 254) {
                return;
            }
            DecodedBitStreamParser.parseTwoBytes(n2, bitSource.readBits(8), nArray);
            for (n2 = 0; n2 < 3; ++n2) {
                int n3 = nArray[n2];
                if (n3 == 0) {
                    stringBuilder.append('\r');
                    continue;
                }
                if (n3 == 1) {
                    stringBuilder.append('*');
                    continue;
                }
                if (n3 == 2) {
                    stringBuilder.append('>');
                    continue;
                }
                if (n3 == 3) {
                    stringBuilder.append(' ');
                    continue;
                }
                if (n3 < 14) {
                    stringBuilder.append((char)(n3 + 44));
                    continue;
                }
                if (n3 >= 40) {
                    throw FormatException.getFormatInstance();
                }
                stringBuilder.append((char)(n3 + 51));
            }
        } while (bitSource.available() > 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Mode decodeAsciiSegment(BitSource bitSource, StringBuilder stringBuilder, StringBuilder stringBuilder2) throws FormatException {
        int n2 = 0;
        do {
            int n3;
            block15: {
                block20: {
                    int n4;
                    block19: {
                        block18: {
                            block17: {
                                block16: {
                                    block14: {
                                        if ((n4 = bitSource.readBits(8)) == 0) {
                                            throw FormatException.getFormatInstance();
                                        }
                                        if (n4 <= 128) {
                                            n3 = n4;
                                            if (n2 != 0) {
                                                n3 = n4 + 128;
                                            }
                                            stringBuilder.append((char)(n3 - 1));
                                            return Mode.ASCII_ENCODE;
                                        }
                                        if (n4 == 129) {
                                            return Mode.PAD_ENCODE;
                                        }
                                        if (n4 > 229) break block14;
                                        n3 = n4 - 130;
                                        if (n3 < 10) {
                                            stringBuilder.append('0');
                                        }
                                        stringBuilder.append(n3);
                                        n3 = n2;
                                        break block15;
                                    }
                                    if (n4 == 230) {
                                        return Mode.C40_ENCODE;
                                    }
                                    if (n4 == 231) {
                                        return Mode.BASE256_ENCODE;
                                    }
                                    if (n4 != 232) break block16;
                                    stringBuilder.append('\u001d');
                                    n3 = n2;
                                    break block15;
                                }
                                n3 = n2;
                                if (n4 == 233) break block15;
                                n3 = n2;
                                if (n4 == 234) break block15;
                                if (n4 != 235) break block17;
                                n3 = 1;
                                break block15;
                            }
                            if (n4 != 236) break block18;
                            stringBuilder.append("[)>\u001e05\u001d");
                            stringBuilder2.insert(0, "\u001e\u0004");
                            n3 = n2;
                            break block15;
                        }
                        if (n4 != 237) break block19;
                        stringBuilder.append("[)>\u001e06\u001d");
                        stringBuilder2.insert(0, "\u001e\u0004");
                        n3 = n2;
                        break block15;
                    }
                    if (n4 == 238) {
                        return Mode.ANSIX12_ENCODE;
                    }
                    if (n4 == 239) {
                        return Mode.TEXT_ENCODE;
                    }
                    if (n4 == 240) {
                        return Mode.EDIFACT_ENCODE;
                    }
                    n3 = n2;
                    if (n4 == 241) break block15;
                    n3 = n2;
                    if (n4 < 242) break block15;
                    if (n4 != 254) break block20;
                    n3 = n2;
                    if (bitSource.available() == 0) break block15;
                }
                throw FormatException.getFormatInstance();
            }
            n2 = n3;
        } while (bitSource.available() > 0);
        return Mode.ASCII_ENCODE;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void decodeBase256Segment(BitSource bitSource, StringBuilder stringBuilder, Collection<byte[]> collection) throws FormatException {
        int n2 = bitSource.getByteOffset() + 1;
        int n3 = bitSource.readBits(8);
        int n4 = n2 + 1;
        if ((n3 = DecodedBitStreamParser.unrandomize255State(n3, n2)) == 0) {
            n2 = bitSource.available() / 8;
        } else if (n3 < 250) {
            n2 = n3;
        } else {
            int n5 = bitSource.readBits(8);
            n2 = n4 + 1;
            n3 = (n3 - 249) * 250 + DecodedBitStreamParser.unrandomize255State(n5, n4);
            n4 = n2;
            n2 = n3;
        }
        if (n2 < 0) {
            throw FormatException.getFormatInstance();
        }
        byte[] byArray = new byte[n2];
        for (n3 = 0; n3 < n2; ++n3, ++n4) {
            if (bitSource.available() < 8) {
                throw FormatException.getFormatInstance();
            }
            byArray[n3] = (byte)DecodedBitStreamParser.unrandomize255State(bitSource.readBits(8), n4);
        }
        collection.add(byArray);
        try {
            stringBuilder.append(new String(byArray, "ISO8859_1"));
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new IllegalStateException("Platform does not support required encoding: " + unsupportedEncodingException);
        }
    }

    /*
     * Unable to fully structure code
     */
    private static void decodeC40Segment(BitSource var0, StringBuilder var1_1) throws FormatException {
        var4_2 = false;
        var7_3 = new int[3];
        var3_4 = 0;
        do {
            if (var0.available() == 8) {
                return;
            }
            if ((var5_6 = var0.readBits(8)) == 254) ** continue;
            DecodedBitStreamParser.parseTwoBytes(var5_6, var0.readBits(8), var7_3);
            block8: for (var5_6 = 0; var5_6 < 3; ++var5_6) {
                var6_7 = var7_3[var5_6];
                switch (var3_4) {
                    default: {
                        throw FormatException.getFormatInstance();
                    }
                    case 0: {
                        if (var6_7 < 3) {
                            var3_4 = var6_7 + 1;
lbl18:
                            // 6 sources

                            continue block8;
                        }
                        if (var6_7 >= DecodedBitStreamParser.C40_BASIC_SET_CHARS.length) ** GOTO lbl30
                        var2_5 = DecodedBitStreamParser.C40_BASIC_SET_CHARS[var6_7];
                        if (!var4_2) ** GOTO lbl27
                        var1_1.append((char)(var2_5 + 128));
                        var4_2 = false;
                        ** GOTO lbl18
lbl27:
                        // 1 sources

                        var1_1.append(var2_5);
                        ** GOTO lbl18
lbl30:
                        // 1 sources

                        throw FormatException.getFormatInstance();
                    }
                    case 1: {
                        if (!var4_2) ** GOTO lbl39
                        var1_1.append((char)(var6_7 + 128));
                        var4_2 = false;
lbl36:
                        // 2 sources

                        while (true) {
                            var3_4 = 0;
                            ** GOTO lbl18
                            break;
                        }
lbl39:
                        // 1 sources

                        var1_1.append((char)var6_7);
                        ** continue;
                    }
                    case 2: {
                        if (var6_7 >= DecodedBitStreamParser.C40_SHIFT2_SET_CHARS.length) ** GOTO lbl55
                        var2_5 = DecodedBitStreamParser.C40_SHIFT2_SET_CHARS[var6_7];
                        if (!var4_2) ** GOTO lbl52
                        var1_1.append((char)(var2_5 + 128));
                        var4_2 = false;
lbl49:
                        // 4 sources

                        while (true) {
                            var3_4 = 0;
                            ** GOTO lbl18
                            break;
                        }
lbl52:
                        // 1 sources

                        var1_1.append(var2_5);
                        ** GOTO lbl49
lbl55:
                        // 1 sources

                        if (var6_7 != 27) ** GOTO lbl59
                        var1_1.append('\u001d');
                        ** GOTO lbl49
lbl59:
                        // 1 sources

                        if (var6_7 == 30) {
                            var4_2 = true;
                            ** continue;
                        }
                        throw FormatException.getFormatInstance();
                    }
                    case 3: 
                }
                if (var4_2) {
                    var1_1.append((char)(var6_7 + 224));
                    var4_2 = false;
lbl68:
                    // 2 sources

                    while (true) {
                        var3_4 = 0;
                        ** continue;
                        break;
                    }
                }
                var1_1.append((char)(var6_7 + 96));
                ** continue;
            }
        } while (var0.available() > 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void decodeEdifactSegment(BitSource bitSource, StringBuilder stringBuilder) {
        int n2;
        block4: {
            block0: while (bitSource.available() > 16) {
                for (n2 = 0; n2 < 4; ++n2) {
                    int n3 = bitSource.readBits(6);
                    if (n3 == 31) {
                        n2 = 8 - bitSource.getBitOffset();
                        if (n2 == 8) break block0;
                        break block4;
                    }
                    int n4 = n3;
                    if ((n3 & 0x20) == 0) {
                        n4 = n3 | 0x40;
                    }
                    stringBuilder.append((char)n4);
                }
                if (bitSource.available() > 0) continue;
                return;
            }
            return;
        }
        bitSource.readBits(n2);
    }

    /*
     * Unable to fully structure code
     */
    private static void decodeTextSegment(BitSource var0, StringBuilder var1_1) throws FormatException {
        var4_2 = false;
        var7_3 = new int[3];
        var3_4 = 0;
        do {
            if (var0.available() == 8) {
                return;
            }
            if ((var5_6 = var0.readBits(8)) == 254) ** continue;
            DecodedBitStreamParser.parseTwoBytes(var5_6, var0.readBits(8), var7_3);
            block8: for (var5_6 = 0; var5_6 < 3; ++var5_6) {
                var6_7 = var7_3[var5_6];
                switch (var3_4) {
                    default: {
                        throw FormatException.getFormatInstance();
                    }
                    case 0: {
                        if (var6_7 < 3) {
                            var3_4 = var6_7 + 1;
lbl18:
                            // 6 sources

                            continue block8;
                        }
                        if (var6_7 >= DecodedBitStreamParser.TEXT_BASIC_SET_CHARS.length) ** GOTO lbl30
                        var2_5 = DecodedBitStreamParser.TEXT_BASIC_SET_CHARS[var6_7];
                        if (!var4_2) ** GOTO lbl27
                        var1_1.append((char)(var2_5 + 128));
                        var4_2 = false;
                        ** GOTO lbl18
lbl27:
                        // 1 sources

                        var1_1.append(var2_5);
                        ** GOTO lbl18
lbl30:
                        // 1 sources

                        throw FormatException.getFormatInstance();
                    }
                    case 1: {
                        if (!var4_2) ** GOTO lbl39
                        var1_1.append((char)(var6_7 + 128));
                        var4_2 = false;
lbl36:
                        // 2 sources

                        while (true) {
                            var3_4 = 0;
                            ** GOTO lbl18
                            break;
                        }
lbl39:
                        // 1 sources

                        var1_1.append((char)var6_7);
                        ** continue;
                    }
                    case 2: {
                        if (var6_7 >= DecodedBitStreamParser.TEXT_SHIFT2_SET_CHARS.length) ** GOTO lbl55
                        var2_5 = DecodedBitStreamParser.TEXT_SHIFT2_SET_CHARS[var6_7];
                        if (!var4_2) ** GOTO lbl52
                        var1_1.append((char)(var2_5 + 128));
                        var4_2 = false;
lbl49:
                        // 4 sources

                        while (true) {
                            var3_4 = 0;
                            ** GOTO lbl18
                            break;
                        }
lbl52:
                        // 1 sources

                        var1_1.append(var2_5);
                        ** GOTO lbl49
lbl55:
                        // 1 sources

                        if (var6_7 != 27) ** GOTO lbl59
                        var1_1.append('\u001d');
                        ** GOTO lbl49
lbl59:
                        // 1 sources

                        if (var6_7 == 30) {
                            var4_2 = true;
                            ** continue;
                        }
                        throw FormatException.getFormatInstance();
                    }
                    case 3: 
                }
                if (var6_7 < DecodedBitStreamParser.TEXT_SHIFT3_SET_CHARS.length) {
                    var2_5 = DecodedBitStreamParser.TEXT_SHIFT3_SET_CHARS[var6_7];
                    if (var4_2) {
                        var1_1.append((char)(var2_5 + 128));
                        var4_2 = false;
lbl70:
                        // 2 sources

                        while (true) {
                            var3_4 = 0;
                            ** continue;
                            break;
                        }
                    }
                    var1_1.append(var2_5);
                    ** continue;
                }
                throw FormatException.getFormatInstance();
            }
        } while (var0.available() > 0);
    }

    private static void parseTwoBytes(int n2, int n3, int[] nArray) {
        n2 = (n2 << 8) + n3 - 1;
        nArray[0] = n3 = n2 / 1600;
        n2 -= n3 * 1600;
        nArray[1] = n3 = n2 / 40;
        nArray[2] = n2 - n3 * 40;
    }

    private static int unrandomize255State(int n2, int n3) {
        if ((n2 -= n3 * 149 % 255 + 1) >= 0) {
            return n2;
        }
        return n2 + 256;
    }

    private static enum Mode {
        PAD_ENCODE,
        ASCII_ENCODE,
        C40_ENCODE,
        TEXT_ENCODE,
        ANSIX12_ENCODE,
        EDIFACT_ENCODE,
        BASE256_ENCODE;

    }
}

