/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.common.images;

public final class Size {
    private final int zzoG;
    private final int zzoH;

    public Size(int n2, int n3) {
        this.zzoG = n2;
        this.zzoH = n3;
    }

    public static Size parseSize(String string2) throws NumberFormatException {
        int n2;
        if (string2 == null) {
            throw new IllegalArgumentException("string must not be null");
        }
        int n3 = n2 = string2.indexOf(42);
        if (n2 < 0) {
            n3 = string2.indexOf(120);
        }
        if (n3 < 0) {
            throw Size.zzcC(string2);
        }
        try {
            Size size = new Size(Integer.parseInt(string2.substring(0, n3)), Integer.parseInt(string2.substring(n3 + 1)));
            return size;
        }
        catch (NumberFormatException numberFormatException) {
            throw Size.zzcC(string2);
        }
    }

    private static NumberFormatException zzcC(String string2) {
        throw new NumberFormatException("Invalid Size: \"" + string2 + "\"");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        boolean bl2 = true;
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (!(object instanceof Size)) return false;
        object = (Size)object;
        if (this.zzoG != ((Size)object).zzoG) return false;
        if (this.zzoH != ((Size)object).zzoH) return false;
        return bl2;
    }

    public int getHeight() {
        return this.zzoH;
    }

    public int getWidth() {
        return this.zzoG;
    }

    public int hashCode() {
        return this.zzoH ^ (this.zzoG << 16 | this.zzoG >>> 16);
    }

    public String toString() {
        return this.zzoG + "x" + this.zzoH;
    }
}

