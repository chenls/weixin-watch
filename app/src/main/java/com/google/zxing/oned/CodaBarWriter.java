/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned;

import com.google.zxing.oned.CodaBarReader;
import com.google.zxing.oned.OneDimensionalCodeWriter;

public final class CodaBarWriter
extends OneDimensionalCodeWriter {
    private static final char[] ALT_START_END_CHARS;
    private static final char[] CHARS_WHICH_ARE_TEN_LENGTH_EACH_AFTER_DECODED;
    private static final char DEFAULT_GUARD;
    private static final char[] START_END_CHARS;

    static {
        START_END_CHARS = new char[]{'A', 'B', 'C', 'D'};
        ALT_START_END_CHARS = new char[]{'T', 'N', '*', 'E'};
        CHARS_WHICH_ARE_TEN_LENGTH_EACH_AFTER_DECODED = new char[]{'/', ':', '+', '.'};
        DEFAULT_GUARD = START_END_CHARS[0];
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean[] encode(String object) {
        int n2;
        boolean bl2;
        Object object2;
        if (((String)object).length() < 2) {
            object2 = DEFAULT_GUARD + (String)object + DEFAULT_GUARD;
        } else {
            char c2 = Character.toUpperCase(((String)object).charAt(0));
            char c3 = Character.toUpperCase(((String)object).charAt(((String)object).length() - 1));
            bl2 = CodaBarReader.arrayContains(START_END_CHARS, c2);
            boolean bl3 = CodaBarReader.arrayContains(START_END_CHARS, c3);
            boolean bl4 = CodaBarReader.arrayContains(ALT_START_END_CHARS, c2);
            boolean bl5 = CodaBarReader.arrayContains(ALT_START_END_CHARS, c3);
            if (bl2) {
                object2 = object;
                if (!bl3) {
                    throw new IllegalArgumentException("Invalid start/end guards: " + (String)object);
                }
            } else if (bl4) {
                object2 = object;
                if (!bl5) {
                    throw new IllegalArgumentException("Invalid start/end guards: " + (String)object);
                }
            } else {
                if (bl3 || bl5) {
                    throw new IllegalArgumentException("Invalid start/end guards: " + (String)object);
                }
                object2 = DEFAULT_GUARD + (String)object + DEFAULT_GUARD;
            }
        }
        int n3 = 20;
        for (n2 = 1; n2 < ((String)object2).length() - 1; ++n2) {
            if (Character.isDigit(((String)object2).charAt(n2)) || ((String)object2).charAt(n2) == '-' || ((String)object2).charAt(n2) == '$') {
                n3 += 9;
                continue;
            }
            if (!CodaBarReader.arrayContains(CHARS_WHICH_ARE_TEN_LENGTH_EACH_AFTER_DECODED, ((String)object2).charAt(n2))) {
                throw new IllegalArgumentException("Cannot encode : '" + ((String)object2).charAt(n2) + '\'');
            }
            n3 += 10;
        }
        object = new boolean[n3 + (((String)object2).length() - 1)];
        n2 = 0;
        int n4 = 0;
        while (true) {
            int n5;
            block33: {
                block32: {
                    if (n4 >= ((String)object2).length()) {
                        return object;
                    }
                    n5 = Character.toUpperCase(((String)object2).charAt(n4));
                    if (n4 == 0) break block32;
                    n3 = n5;
                    if (n4 != ((String)object2).length() - 1) break block33;
                }
                switch (n5) {
                    default: {
                        n3 = n5;
                        break;
                    }
                    case 84: {
                        n3 = 65;
                        break;
                    }
                    case 78: {
                        n3 = 66;
                        break;
                    }
                    case 42: {
                        n3 = 67;
                        break;
                    }
                    case 69: {
                        n3 = 68;
                    }
                }
            }
            int n6 = 0;
            int n7 = 0;
            while (true) {
                block35: {
                    block34: {
                        n5 = n6;
                        if (n7 >= CodaBarReader.ALPHABET.length) break block34;
                        if (n3 != CodaBarReader.ALPHABET[n7]) break block35;
                        n5 = CodaBarReader.CHARACTER_ENCODINGS[n7];
                    }
                    bl2 = true;
                    n3 = 0;
                    n7 = 0;
                    break;
                }
                ++n7;
            }
            while (n7 < 7) {
                object[n2] = bl2;
                ++n2;
                if ((n5 >> 6 - n7 & 1) == 0 || n3 == 1) {
                    bl2 = !bl2;
                    ++n7;
                    n3 = 0;
                    continue;
                }
                ++n3;
            }
            n3 = n2;
            if (n4 < ((String)object2).length() - 1) {
                object[n2] = false;
                n3 = n2 + 1;
            }
            ++n4;
            n2 = n3;
        }
    }
}

