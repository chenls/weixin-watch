/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.maxicode.decoder;

import com.google.zxing.common.DecoderResult;
import java.text.DecimalFormat;
import java.text.NumberFormat;

final class DecodedBitStreamParser {
    private static final char ECI = '\ufffa';
    private static final char FS = '\u001c';
    private static final char GS = '\u001d';
    private static final char LATCHA = '\ufff7';
    private static final char LATCHB = '\ufff8';
    private static final char LOCK = '\ufff9';
    private static final NumberFormat NINE_DIGITS = new DecimalFormat("000000000");
    private static final char NS = '\ufffb';
    private static final char PAD = '\ufffc';
    private static final char RS = '\u001e';
    private static final String[] SETS;
    private static final char SHIFTA = '\ufff0';
    private static final char SHIFTB = '\ufff1';
    private static final char SHIFTC = '\ufff2';
    private static final char SHIFTD = '\ufff3';
    private static final char SHIFTE = '\ufff4';
    private static final char THREESHIFTA = '\ufff6';
    private static final NumberFormat THREE_DIGITS;
    private static final char TWOSHIFTA = '\ufff5';

    static {
        THREE_DIGITS = new DecimalFormat("000");
        SETS = new String[]{"\nABCDEFGHIJKLMNOPQRSTUVWXYZ\ufffa\u001c\u001d\u001e\ufffb \ufffc\"#$%&'()*+,-./0123456789:\ufff1\ufff2\ufff3\ufff4\ufff8", "`abcdefghijklmnopqrstuvwxyz\ufffa\u001c\u001d\u001e\ufffb{\ufffc}~\u007f;<=>?[\\]^_ ,./:@!|\ufffc\ufff5\ufff6\ufffc\ufff0\ufff2\ufff3\ufff4\ufff7", "\u00c0\u00c1\u00c2\u00c3\u00c4\u00c5\u00c6\u00c7\u00c8\u00c9\u00ca\u00cb\u00cc\u00cd\u00ce\u00cf\u00d0\u00d1\u00d2\u00d3\u00d4\u00d5\u00d6\u00d7\u00d8\u00d9\u00da\ufffa\u001c\u001d\u001e\u00db\u00dc\u00dd\u00de\u00df\u00aa\u00ac\u00b1\u00b2\u00b3\u00b5\u00b9\u00ba\u00bc\u00bd\u00be\u0080\u0081\u0082\u0083\u0084\u0085\u0086\u0087\u0088\u0089\ufff7 \ufff9\ufff3\ufff4\ufff8", "\u00e0\u00e1\u00e2\u00e3\u00e4\u00e5\u00e6\u00e7\u00e8\u00e9\u00ea\u00eb\u00ec\u00ed\u00ee\u00ef\u00f0\u00f1\u00f2\u00f3\u00f4\u00f5\u00f6\u00f7\u00f8\u00f9\u00fa\ufffa\u001c\u001d\u001e\ufffb\u00fb\u00fc\u00fd\u00fe\u00ff\u00a1\u00a8\u00ab\u00af\u00b0\u00b4\u00b7\u00b8\u00bb\u00bf\u008a\u008b\u008c\u008d\u008e\u008f\u0090\u0091\u0092\u0093\u0094\ufff7 \ufff2\ufff9\ufff4\ufff8", "\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\ufffa\ufffc\ufffc\u001b\ufffb\u001c\u001d\u001e\u001f\u009f\u00a0\u00a2\u00a3\u00a4\u00a5\u00a6\u00a7\u00a9\u00ad\u00ae\u00b6\u0095\u0096\u0097\u0098\u0099\u009a\u009b\u009c\u009d\u009e\ufff7 \ufff2\ufff3\ufff9\ufff8", "\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f !\"#$%&'()*+,-./0123456789:;<=>?"};
    }

    private DecodedBitStreamParser() {
    }

    /*
     * Enabled aggressive block sorting
     */
    static DecoderResult decode(byte[] byArray, int n2) {
        StringBuilder stringBuilder = new StringBuilder(144);
        switch (n2) {
            case 2: 
            case 3: {
                String string2;
                if (n2 == 2) {
                    int n3 = DecodedBitStreamParser.getPostCode2(byArray);
                    string2 = new DecimalFormat("0000000000".substring(0, DecodedBitStreamParser.getPostCode2Length(byArray))).format(n3);
                } else {
                    string2 = DecodedBitStreamParser.getPostCode3(byArray);
                }
                String string3 = THREE_DIGITS.format(DecodedBitStreamParser.getCountry(byArray));
                String string4 = THREE_DIGITS.format(DecodedBitStreamParser.getServiceClass(byArray));
                stringBuilder.append(DecodedBitStreamParser.getMessage(byArray, 10, 84));
                if (stringBuilder.toString().startsWith("[)>\u001e01\u001d")) {
                    stringBuilder.insert(9, string2 + '\u001d' + string3 + '\u001d' + string4 + '\u001d');
                    return new DecoderResult(byArray, stringBuilder.toString(), null, String.valueOf(n2));
                }
                stringBuilder.insert(0, string2 + '\u001d' + string3 + '\u001d' + string4 + '\u001d');
                return new DecoderResult(byArray, stringBuilder.toString(), null, String.valueOf(n2));
            }
            case 4: {
                stringBuilder.append(DecodedBitStreamParser.getMessage(byArray, 1, 93));
                return new DecoderResult(byArray, stringBuilder.toString(), null, String.valueOf(n2));
            }
            case 5: {
                stringBuilder.append(DecodedBitStreamParser.getMessage(byArray, 1, 77));
                return new DecoderResult(byArray, stringBuilder.toString(), null, String.valueOf(n2));
            }
        }
        return new DecoderResult(byArray, stringBuilder.toString(), null, String.valueOf(n2));
    }

    private static int getBit(int n2, byte[] byArray) {
        int n3 = 1;
        int n4 = n2 - 1;
        n2 = n3;
        if ((byArray[n4 / 6] & 1 << 5 - n4 % 6) == 0) {
            n2 = 0;
        }
        return n2;
    }

    private static int getCountry(byte[] byArray) {
        return DecodedBitStreamParser.getInt(byArray, new byte[]{53, 54, 43, 44, 45, 46, 47, 48, 37, 38});
    }

    private static int getInt(byte[] byArray, byte[] byArray2) {
        if (byArray2.length == 0) {
            throw new IllegalArgumentException();
        }
        int n2 = 0;
        for (int i2 = 0; i2 < byArray2.length; ++i2) {
            n2 += DecodedBitStreamParser.getBit(byArray2[i2], byArray) << byArray2.length - i2 - 1;
        }
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String getMessage(byte[] byArray, int n2, int n3) {
        StringBuilder stringBuilder = new StringBuilder();
        int n4 = -1;
        int n5 = 0;
        int n6 = 0;
        for (int i2 = n2; i2 < n2 + n3; ++i2) {
            int n7;
            char c2 = SETS[n5].charAt(byArray[i2]);
            switch (c2) {
                default: {
                    stringBuilder.append(c2);
                    break;
                }
                case '\ufff7': {
                    n5 = 0;
                    n4 = -1;
                    break;
                }
                case '\ufff8': {
                    n5 = 1;
                    n4 = -1;
                    break;
                }
                case '\ufff0': 
                case '\ufff1': 
                case '\ufff2': 
                case '\ufff3': 
                case '\ufff4': {
                    n7 = c2 - 65520;
                    n4 = 1;
                    n6 = n5;
                    n5 = n7;
                    break;
                }
                case '\ufff5': {
                    n7 = 0;
                    n4 = 2;
                    n6 = n5;
                    n5 = n7;
                    break;
                }
                case '\ufff6': {
                    n7 = 0;
                    n4 = 3;
                    n6 = n5;
                    n5 = n7;
                    break;
                }
                case '\ufffb': {
                    n7 = byArray[++i2];
                    byte by2 = byArray[++i2];
                    byte by3 = byArray[++i2];
                    byte by4 = byArray[++i2];
                    byte by5 = byArray[++i2];
                    stringBuilder.append(NINE_DIGITS.format((n7 << 24) + (by2 << 18) + (by3 << 12) + (by4 << 6) + by5));
                    break;
                }
                case '\ufff9': {
                    n4 = -1;
                }
            }
            n7 = n4 - 1;
            if (n4 == 0) {
                n5 = n6;
            }
            n4 = n7;
        }
        while (stringBuilder.length() > 0 && stringBuilder.charAt(stringBuilder.length() - 1) == '\ufffc') {
            stringBuilder.setLength(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    private static int getPostCode2(byte[] byArray) {
        return DecodedBitStreamParser.getInt(byArray, new byte[]{33, 34, 35, 36, 25, 26, 27, 28, 29, 30, 19, 20, 21, 22, 23, 24, 13, 14, 15, 16, 17, 18, 7, 8, 9, 10, 11, 12, 1, 2});
    }

    private static int getPostCode2Length(byte[] byArray) {
        return DecodedBitStreamParser.getInt(byArray, new byte[]{39, 40, 41, 42, 31, 32});
    }

    private static String getPostCode3(byte[] byArray) {
        return String.valueOf(new char[]{SETS[0].charAt(DecodedBitStreamParser.getInt(byArray, new byte[]{39, 40, 41, 42, 31, 32})), SETS[0].charAt(DecodedBitStreamParser.getInt(byArray, new byte[]{33, 34, 35, 36, 25, 26})), SETS[0].charAt(DecodedBitStreamParser.getInt(byArray, new byte[]{27, 28, 29, 30, 19, 20})), SETS[0].charAt(DecodedBitStreamParser.getInt(byArray, new byte[]{21, 22, 23, 24, 13, 14})), SETS[0].charAt(DecodedBitStreamParser.getInt(byArray, new byte[]{15, 16, 17, 18, 7, 8})), SETS[0].charAt(DecodedBitStreamParser.getInt(byArray, new byte[]{9, 10, 11, 12, 1, 2}))});
    }

    private static int getServiceClass(byte[] byArray) {
        return DecodedBitStreamParser.getInt(byArray, new byte[]{55, 56, 57, 58, 59, 60, 49, 50, 51, 52});
    }
}

