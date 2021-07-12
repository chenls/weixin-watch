/*
 * Decompiled with CFR 0.151.
 */
package ticwear.design.internal;

public class CharSequences {
    /*
     * Enabled aggressive block sorting
     */
    public static int compareToIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        int n2;
        int n3 = charSequence.length();
        int n4 = n3 < (n2 = charSequence2.length()) ? n3 : n2;
        int n5 = 0;
        int n6 = 0;
        while (n6 < n4) {
            int n7 = Character.toLowerCase(charSequence.charAt(n6)) - Character.toLowerCase(charSequence2.charAt(n5));
            if (n7 != 0) {
                return n7;
            }
            ++n5;
            ++n6;
        }
        return n3 - n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean equals(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence.length() == charSequence2.length()) {
            int n2 = charSequence.length();
            int n3 = 0;
            while (true) {
                if (n3 >= n2) {
                    return true;
                }
                if (charSequence.charAt(n3) != charSequence2.charAt(n3)) break;
                ++n3;
            }
        }
        return false;
    }

    public static CharSequence forAsciiBytes(final byte[] byArray) {
        return new CharSequence(){

            @Override
            public char charAt(int n2) {
                return (char)byArray[n2];
            }

            @Override
            public int length() {
                return byArray.length;
            }

            @Override
            public CharSequence subSequence(int n2, int n3) {
                return CharSequences.forAsciiBytes(byArray, n2, n3);
            }

            @Override
            public String toString() {
                return new String(byArray);
            }
        };
    }

    public static CharSequence forAsciiBytes(final byte[] byArray, final int n2, final int n3) {
        CharSequences.validate(n2, n3, byArray.length);
        return new CharSequence(){

            @Override
            public char charAt(int n22) {
                return (char)byArray[n2 + n22];
            }

            @Override
            public int length() {
                return n3 - n2;
            }

            @Override
            public CharSequence subSequence(int n22, int n32) {
                CharSequences.validate(n22 -= n2, n32 -= n2, this.length());
                return CharSequences.forAsciiBytes(byArray, n22, n32);
            }

            @Override
            public String toString() {
                return new String(byArray, n2, this.length());
            }
        };
    }

    static void validate(int n2, int n3, int n4) {
        if (n2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (n3 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (n3 > n4) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 > n3) {
            throw new IndexOutOfBoundsException();
        }
    }
}

