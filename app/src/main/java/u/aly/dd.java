/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import u.aly.bv;

public class dd
extends bv {
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    public static final int d = 3;
    public static final int e = 4;
    private static final long g = 1L;
    protected int f = 0;

    public dd() {
    }

    public dd(int n2) {
        this.f = n2;
    }

    public dd(int n2, String string2) {
        super(string2);
        this.f = n2;
    }

    public dd(int n2, String string2, Throwable throwable) {
        super(string2, throwable);
        this.f = n2;
    }

    public dd(int n2, Throwable throwable) {
        super(throwable);
        this.f = n2;
    }

    public dd(String string2) {
        super(string2);
    }

    public dd(String string2, Throwable throwable) {
        super(string2, throwable);
    }

    public dd(Throwable throwable) {
        super(throwable);
    }

    public int a() {
        return this.f;
    }
}

