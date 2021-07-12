/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.datamatrix.encoder;

import com.google.zxing.Dimension;
import com.google.zxing.datamatrix.encoder.ASCIIEncoder;
import com.google.zxing.datamatrix.encoder.Base256Encoder;
import com.google.zxing.datamatrix.encoder.C40Encoder;
import com.google.zxing.datamatrix.encoder.EdifactEncoder;
import com.google.zxing.datamatrix.encoder.Encoder;
import com.google.zxing.datamatrix.encoder.EncoderContext;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;
import com.google.zxing.datamatrix.encoder.TextEncoder;
import com.google.zxing.datamatrix.encoder.X12Encoder;
import java.util.Arrays;

public final class HighLevelEncoder {
    static final int ASCII_ENCODATION = 0;
    static final int BASE256_ENCODATION = 5;
    static final int C40_ENCODATION = 1;
    static final char C40_UNLATCH = '\u00fe';
    static final int EDIFACT_ENCODATION = 4;
    static final char LATCH_TO_ANSIX12 = '\u00ee';
    static final char LATCH_TO_BASE256 = '\u00e7';
    static final char LATCH_TO_C40 = '\u00e6';
    static final char LATCH_TO_EDIFACT = '\u00f0';
    static final char LATCH_TO_TEXT = '\u00ef';
    private static final char MACRO_05 = '\u00ec';
    private static final String MACRO_05_HEADER = "[)>\u001e05\u001d";
    private static final char MACRO_06 = '\u00ed';
    private static final String MACRO_06_HEADER = "[)>\u001e06\u001d";
    private static final String MACRO_TRAILER = "\u001e\u0004";
    private static final char PAD = '\u0081';
    static final int TEXT_ENCODATION = 2;
    static final char UPPER_SHIFT = '\u00eb';
    static final int X12_ENCODATION = 3;
    static final char X12_UNLATCH = '\u00fe';

    private HighLevelEncoder() {
    }

    public static int determineConsecutiveDigitCount(CharSequence charSequence, int n2) {
        int n3 = 0;
        int n4 = 0;
        int n5 = charSequence.length();
        if (n2 < n5) {
            char c2 = charSequence.charAt(n2);
            int n6 = n2;
            n2 = n4;
            while (true) {
                n3 = n2;
                if (!HighLevelEncoder.isDigit(c2)) break;
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

    public static String encodeHighLevel(String string2) {
        return HighLevelEncoder.encodeHighLevel(string2, SymbolShapeHint.FORCE_NONE, null, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String encodeHighLevel(String charSequence, SymbolShapeHint symbolShapeHint, Dimension dimension, Dimension dimension2) {
        ASCIIEncoder aSCIIEncoder = new ASCIIEncoder();
        C40Encoder c40Encoder = new C40Encoder();
        TextEncoder textEncoder = new TextEncoder();
        X12Encoder x12Encoder = new X12Encoder();
        EdifactEncoder edifactEncoder = new EdifactEncoder();
        Base256Encoder base256Encoder = new Base256Encoder();
        EncoderContext encoderContext = new EncoderContext((String)charSequence);
        encoderContext.setSymbolShape(symbolShapeHint);
        encoderContext.setSizeConstraints(dimension, dimension2);
        if (((String)charSequence).startsWith(MACRO_05_HEADER) && ((String)charSequence).endsWith(MACRO_TRAILER)) {
            encoderContext.writeCodeword('\u00ec');
            encoderContext.setSkipAtEnd(2);
            encoderContext.pos += MACRO_05_HEADER.length();
        } else if (((String)charSequence).startsWith(MACRO_06_HEADER) && ((String)charSequence).endsWith(MACRO_TRAILER)) {
            encoderContext.writeCodeword('\u00ed');
            encoderContext.setSkipAtEnd(2);
            encoderContext.pos += MACRO_06_HEADER.length();
        }
        int n2 = 0;
        while (encoderContext.hasMoreCharacters()) {
            (new Encoder[]{aSCIIEncoder, c40Encoder, textEncoder, x12Encoder, edifactEncoder, base256Encoder})[n2].encode(encoderContext);
            if (encoderContext.getNewEncoding() < 0) continue;
            n2 = encoderContext.getNewEncoding();
            encoderContext.resetEncoderSignal();
        }
        int n3 = encoderContext.getCodewordCount();
        encoderContext.updateSymbolInfo();
        int n4 = encoderContext.getSymbolInfo().getDataCapacity();
        if (n3 < n4 && n2 != 0 && n2 != 5) {
            encoderContext.writeCodeword('\u00fe');
        }
        if (((StringBuilder)(charSequence = encoderContext.getCodewords())).length() < n4) {
            ((StringBuilder)charSequence).append('\u0081');
        }
        while (((StringBuilder)charSequence).length() < n4) {
            ((StringBuilder)charSequence).append(HighLevelEncoder.randomize253State('\u0081', ((StringBuilder)charSequence).length() + 1));
        }
        return encoderContext.getCodewords().toString();
    }

    private static int findMinimums(float[] fArray, int[] nArray, int n2, byte[] byArray) {
        Arrays.fill(byArray, (byte)0);
        int n3 = 0;
        int n4 = n2;
        for (n2 = n3; n2 < 6; ++n2) {
            nArray[n2] = (int)Math.ceil(fArray[n2]);
            int n5 = nArray[n2];
            n3 = n4;
            if (n4 > n5) {
                n3 = n5;
                Arrays.fill(byArray, (byte)0);
            }
            if (n3 == n5) {
                byArray[n2] = (byte)(byArray[n2] + 1);
            }
            n4 = n3;
        }
        return n4;
    }

    private static int getMinimumCount(byte[] byArray) {
        int n2 = 0;
        for (int i2 = 0; i2 < 6; ++i2) {
            n2 += byArray[i2];
        }
        return n2;
    }

    static void illegalCharacter(char c2) {
        String string2 = Integer.toHexString(c2);
        string2 = "0000".substring(0, 4 - string2.length()) + string2;
        throw new IllegalArgumentException("Illegal character: " + c2 + " (0x" + string2 + ')');
    }

    static boolean isDigit(char c2) {
        return c2 >= '0' && c2 <= '9';
    }

    static boolean isExtendedASCII(char c2) {
        return c2 >= '\u0080' && c2 <= '\u00ff';
    }

    private static boolean isNativeC40(char c2) {
        return c2 == ' ' || c2 >= '0' && c2 <= '9' || c2 >= 'A' && c2 <= 'Z';
    }

    private static boolean isNativeEDIFACT(char c2) {
        return c2 >= ' ' && c2 <= '^';
    }

    private static boolean isNativeText(char c2) {
        return c2 == ' ' || c2 >= '0' && c2 <= '9' || c2 >= 'a' && c2 <= 'z';
    }

    private static boolean isNativeX12(char c2) {
        return HighLevelEncoder.isX12TermSep(c2) || c2 == ' ' || c2 >= '0' && c2 <= '9' || c2 >= 'A' && c2 <= 'Z';
    }

    private static boolean isSpecialB256(char c2) {
        return false;
    }

    private static boolean isX12TermSep(char c2) {
        return c2 == '\r' || c2 == '*' || c2 == '>';
    }

    /*
     * Enabled aggressive block sorting
     */
    static int lookAheadTest(CharSequence object, int n2, int n3) {
        char c2;
        float[] fArray;
        if (n2 >= object.length()) {
            return n3;
        }
        if (n3 == 0) {
            float[] fArray2 = fArray = new float[6];
            fArray[0] = 0.0f;
            fArray2[1] = 1.0f;
            fArray2[2] = 1.0f;
            fArray2[3] = 1.0f;
            fArray2[4] = 1.0f;
            fArray2[5] = 1.25f;
        } else {
            float[] fArray3 = fArray = new float[6];
            fArray[0] = 1.0f;
            fArray3[1] = 2.0f;
            fArray3[2] = 2.0f;
            fArray3[3] = 2.0f;
            fArray3[4] = 2.0f;
            fArray3[5] = 2.25f;
            fArray[n3] = 0.0f;
        }
        n3 = 0;
        while (true) {
            int[] nArray;
            if (n2 + n3 == object.length()) {
                object = new byte[6];
                nArray = new int[6];
                n2 = HighLevelEncoder.findMinimums(fArray, nArray, Integer.MAX_VALUE, (byte[])object);
                n3 = HighLevelEncoder.getMinimumCount((byte[])object);
                if (nArray[0] == n2) {
                    return 0;
                }
                if (n3 == 1 && object[5] > 0) {
                    return 5;
                }
                if (n3 == 1 && object[4] > 0) {
                    return 4;
                }
                if (n3 == 1 && object[2] > 0) {
                    return 2;
                }
                if (n3 == 1 && object[3] > 0) {
                    return 3;
                }
                return 1;
            }
            c2 = object.charAt(n2 + n3);
            int n4 = n3 + 1;
            if (HighLevelEncoder.isDigit(c2)) {
                fArray[0] = (float)((double)fArray[0] + 0.5);
            } else if (HighLevelEncoder.isExtendedASCII(c2)) {
                fArray[0] = (int)Math.ceil(fArray[0]);
                fArray[0] = fArray[0] + 2.0f;
            } else {
                fArray[0] = (int)Math.ceil(fArray[0]);
                fArray[0] = fArray[0] + 1.0f;
            }
            fArray[1] = HighLevelEncoder.isNativeC40(c2) ? fArray[1] + 0.6666667f : (HighLevelEncoder.isExtendedASCII(c2) ? fArray[1] + 2.6666667f : fArray[1] + 1.3333334f);
            fArray[2] = HighLevelEncoder.isNativeText(c2) ? fArray[2] + 0.6666667f : (HighLevelEncoder.isExtendedASCII(c2) ? fArray[2] + 2.6666667f : fArray[2] + 1.3333334f);
            fArray[3] = HighLevelEncoder.isNativeX12(c2) ? fArray[3] + 0.6666667f : (HighLevelEncoder.isExtendedASCII(c2) ? fArray[3] + 4.3333335f : fArray[3] + 3.3333333f);
            fArray[4] = HighLevelEncoder.isNativeEDIFACT(c2) ? fArray[4] + 0.75f : (HighLevelEncoder.isExtendedASCII(c2) ? fArray[4] + 4.25f : fArray[4] + 3.25f);
            fArray[5] = HighLevelEncoder.isSpecialB256(c2) ? fArray[5] + 4.0f : fArray[5] + 1.0f;
            n3 = n4;
            if (n4 < 4) continue;
            nArray = new int[6];
            byte[] byArray = new byte[6];
            HighLevelEncoder.findMinimums(fArray, nArray, Integer.MAX_VALUE, byArray);
            n3 = HighLevelEncoder.getMinimumCount(byArray);
            if (nArray[0] < nArray[5] && nArray[0] < nArray[1] && nArray[0] < nArray[2] && nArray[0] < nArray[3] && nArray[0] < nArray[4]) {
                return 0;
            }
            if (nArray[5] < nArray[0] || byArray[1] + byArray[2] + byArray[3] + byArray[4] == 0) {
                return 5;
            }
            if (n3 == 1 && byArray[4] > 0) {
                return 4;
            }
            if (n3 == 1 && byArray[2] > 0) {
                return 2;
            }
            if (n3 == 1 && byArray[3] > 0) {
                return 3;
            }
            n3 = n4;
            if (nArray[1] + 1 >= nArray[0]) continue;
            n3 = n4;
            if (nArray[1] + 1 >= nArray[5]) continue;
            n3 = n4;
            if (nArray[1] + 1 >= nArray[4]) continue;
            n3 = n4;
            if (nArray[1] + 1 >= nArray[2]) continue;
            if (nArray[1] < nArray[3]) {
                return 1;
            }
            n3 = n4;
            if (nArray[1] == nArray[3]) break;
        }
        for (n2 = n2 + n4 + 1; n2 < object.length(); ++n2) {
            c2 = object.charAt(n2);
            if (HighLevelEncoder.isX12TermSep(c2)) {
                return 3;
            }
            if (!HighLevelEncoder.isNativeX12(c2)) break;
        }
        return 1;
    }

    private static char randomize253State(char c2, int n2) {
        if ((c2 = (char)(c2 + (n2 * 149 % 253 + 1))) <= '\u00fe') {
            return c2;
        }
        return (char)(c2 - 254);
    }
}

