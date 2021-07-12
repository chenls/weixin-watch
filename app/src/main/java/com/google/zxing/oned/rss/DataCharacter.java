/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned.rss;

public class DataCharacter {
    private final int checksumPortion;
    private final int value;

    public DataCharacter(int n2, int n3) {
        this.value = n2;
        this.checksumPortion = n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block3: {
            block2: {
                if (!(object instanceof DataCharacter)) break block2;
                object = (DataCharacter)object;
                if (this.value == ((DataCharacter)object).value && this.checksumPortion == ((DataCharacter)object).checksumPortion) break block3;
            }
            return false;
        }
        return true;
    }

    public final int getChecksumPortion() {
        return this.checksumPortion;
    }

    public final int getValue() {
        return this.value;
    }

    public final int hashCode() {
        return this.value ^ this.checksumPortion;
    }

    public final String toString() {
        return this.value + "(" + this.checksumPortion + ')';
    }
}

