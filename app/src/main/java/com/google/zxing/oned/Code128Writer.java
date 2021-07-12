/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Reader;
import com.google.zxing.oned.OneDimensionalCodeWriter;
import java.util.ArrayList;
import java.util.Map;

public final class Code128Writer
extends OneDimensionalCodeWriter {
    private static final int CODE_CODE_B = 100;
    private static final int CODE_CODE_C = 99;
    private static final int CODE_FNC_1 = 102;
    private static final int CODE_FNC_2 = 97;
    private static final int CODE_FNC_3 = 96;
    private static final int CODE_FNC_4_B = 100;
    private static final int CODE_START_B = 104;
    private static final int CODE_START_C = 105;
    private static final int CODE_STOP = 106;
    private static final char ESCAPE_FNC_1 = '\u00f1';
    private static final char ESCAPE_FNC_2 = '\u00f2';
    private static final char ESCAPE_FNC_3 = '\u00f3';
    private static final char ESCAPE_FNC_4 = '\u00f4';

    /*
     * Enabled aggressive block sorting
     */
    private static boolean isDigits(CharSequence charSequence, int n2, int n3) {
        n3 = n2 + n3;
        int n4 = charSequence.length();
        while (n2 < n3 && n2 < n4) {
            int n5;
            block6: {
                char c2;
                block5: {
                    c2 = charSequence.charAt(n2);
                    if (c2 < '0') break block5;
                    n5 = n3;
                    if (c2 <= '9') break block6;
                }
                if (c2 != '\u00f1') return false;
                n5 = n3 + 1;
            }
            ++n2;
            n3 = n5;
        }
        if (n3 <= n4) return true;
        return false;
    }

    @Override
    public BitMatrix encode(String string2, BarcodeFormat barcodeFormat, int n2, int n3, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat != BarcodeFormat.CODE_128) {
            throw new IllegalArgumentException("Can only encode CODE_128, but got " + (Object)((Object)barcodeFormat));
        }
        return super.encode(string2, barcodeFormat, n2, n3, map);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean[] encode(String object) {
        int n2;
        int n3 = ((String)object).length();
        if (n3 < 1 || n3 > 80) {
            throw new IllegalArgumentException("Contents length should be between 1 and 80 characters, but got " + n3);
        }
        for (n2 = 0; n2 < n3; ++n2) {
            char c2 = ((String)object).charAt(n2);
            if (c2 >= ' ' && c2 <= '~') continue;
            switch (c2) {
                default: {
                    throw new IllegalArgumentException("Bad character in input: " + c2);
                }
                case '\u00f1': 
                case '\u00f2': 
                case '\u00f3': 
                case '\u00f4': 
            }
        }
        Object object2 = new ArrayList<int[]>();
        int n4 = 0;
        int n5 = 1;
        int n6 = 0;
        int n7 = 0;
        while (n7 < n3) {
            int n8;
            int n9;
            block18: {
                block16: {
                    block17: {
                        n2 = n6 == 99 ? 2 : 4;
                        if ((n2 = Code128Writer.isDigits((CharSequence)object, n7, n2) ? 99 : 100) != n6) break block16;
                        switch (((String)object).charAt(n7)) {
                            default: {
                                if (n6 != 100) break;
                                n2 = ((String)object).charAt(n7) - 32;
                                break block17;
                            }
                            case '\u00f1': {
                                n2 = 102;
                                break block17;
                            }
                            case '\u00f2': {
                                n2 = 97;
                                break block17;
                            }
                            case '\u00f3': {
                                n2 = 96;
                                break block17;
                            }
                            case '\u00f4': {
                                n2 = 100;
                                break block17;
                            }
                        }
                        n2 = Integer.parseInt(((String)object).substring(n7, n7 + 2));
                        ++n7;
                    }
                    n9 = n7 + 1;
                    n8 = n2;
                    n2 = n6;
                    break block18;
                }
                n6 = n6 == 0 ? (n2 == 100 ? 104 : 105) : n2;
                n8 = n6;
                n9 = n7;
            }
            object2.add(Code128Reader.CODE_PATTERNS[n8]);
            n4 = n8 = n4 + n8 * n5;
            n6 = n2;
            n7 = n9;
            if (n9 == 0) continue;
            ++n5;
            n4 = n8;
            n6 = n2;
            n7 = n9;
        }
        object2.add(Code128Reader.CODE_PATTERNS[n4 % 103]);
        object2.add(Code128Reader.CODE_PATTERNS[106]);
        n2 = 0;
        object = object2.iterator();
        block11: while (object.hasNext()) {
            int[] nArray = (int[])object.next();
            n4 = nArray.length;
            n7 = 0;
            n6 = n2;
            while (true) {
                n2 = n6;
                if (n7 >= n4) continue block11;
                n6 += nArray[n7];
                ++n7;
            }
        }
        object = new boolean[n2];
        n2 = 0;
        object2 = object2.iterator();
        while (object2.hasNext()) {
            n2 += Code128Writer.appendPattern((boolean[])object, n2, (int[])object2.next(), true);
        }
        return object;
    }
}

