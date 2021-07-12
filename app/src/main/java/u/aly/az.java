/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import u.aly.bt;

public enum az implements bt
{
    a(0),
    b(1),
    c(2);

    private final int d;

    private az(int n3) {
        this.d = n3;
    }

    public static az a(int n2) {
        switch (n2) {
            default: {
                return null;
            }
            case 0: {
                return a;
            }
            case 1: {
                return b;
            }
            case 2: 
        }
        return c;
    }

    @Override
    public int a() {
        return this.d;
    }
}

