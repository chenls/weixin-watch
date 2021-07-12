/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing;

public final class Dimension {
    private final int height;
    private final int width;

    public Dimension(int n2, int n3) {
        if (n2 < 0 || n3 < 0) {
            throw new IllegalArgumentException();
        }
        this.width = n2;
        this.height = n3;
    }

    public boolean equals(Object object) {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (object instanceof Dimension) {
            object = (Dimension)object;
            bl3 = bl2;
            if (this.width == ((Dimension)object).width) {
                bl3 = bl2;
                if (this.height == ((Dimension)object).height) {
                    bl3 = true;
                }
            }
        }
        return bl3;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int hashCode() {
        return this.width * 32713 + this.height;
    }

    public String toString() {
        return this.width + "x" + this.height;
    }
}

