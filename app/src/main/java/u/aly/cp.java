/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import u.aly.bv;

public class cp
extends bv {
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    public static final int d = 3;
    public static final int e = 4;
    public static final int f = 5;
    private static final long h = 1L;
    protected int g = 0;

    public cp() {
    }

    public cp(int n2) {
        this.g = n2;
    }

    public cp(int n2, String string2) {
        super(string2);
        this.g = n2;
    }

    public cp(int n2, String string2, Throwable throwable) {
        super(string2, throwable);
        this.g = n2;
    }

    public cp(int n2, Throwable throwable) {
        super(throwable);
        this.g = n2;
    }

    public cp(String string2) {
        super(string2);
    }

    public cp(String string2, Throwable throwable) {
        super(string2, throwable);
    }

    public cp(Throwable throwable) {
        super(throwable);
    }

    public int a() {
        return this.g;
    }
}

