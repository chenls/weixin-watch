/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import u.aly.dc;
import u.aly.dd;

public final class db
extends dc {
    private byte[] a;
    private int b;
    private int c;

    public db() {
    }

    public db(byte[] byArray) {
        this.a(byArray);
    }

    public db(byte[] byArray, int n2, int n3) {
        this.c(byArray, n2, n3);
    }

    @Override
    public int a(byte[] byArray, int n2, int n3) throws dd {
        int n4 = this.h();
        int n5 = n3;
        if (n3 > n4) {
            n5 = n4;
        }
        if (n5 > 0) {
            System.arraycopy(this.a, this.b, byArray, n2, n5);
            this.a(n5);
        }
        return n5;
    }

    @Override
    public void a(int n2) {
        this.b += n2;
    }

    public void a(byte[] byArray) {
        this.c(byArray, 0, byArray.length);
    }

    @Override
    public boolean a() {
        return true;
    }

    @Override
    public void b() throws dd {
    }

    @Override
    public void b(byte[] byArray, int n2, int n3) throws dd {
        throw new UnsupportedOperationException("No writing allowed!");
    }

    @Override
    public void c() {
    }

    public void c(byte[] byArray, int n2, int n3) {
        this.a = byArray;
        this.b = n2;
        this.c = n2 + n3;
    }

    public void e() {
        this.a = null;
    }

    @Override
    public byte[] f() {
        return this.a;
    }

    @Override
    public int g() {
        return this.b;
    }

    @Override
    public int h() {
        return this.c - this.b;
    }
}

