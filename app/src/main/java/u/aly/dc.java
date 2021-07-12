/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import u.aly.dd;

public abstract class dc {
    public abstract int a(byte[] var1, int var2, int var3) throws dd;

    public void a(int n2) {
    }

    public abstract boolean a();

    public abstract void b() throws dd;

    public void b(byte[] byArray) throws dd {
        this.b(byArray, 0, byArray.length);
    }

    public abstract void b(byte[] var1, int var2, int var3) throws dd;

    public abstract void c();

    public int d(byte[] byArray, int n2, int n3) throws dd {
        int n4;
        int n5;
        for (n4 = 0; n4 < n3; n4 += n5) {
            n5 = this.a(byArray, n2 + n4, n3 - n4);
            if (n5 > 0) continue;
            throw new dd("Cannot read. Remote side has closed. Tried to read " + n3 + " bytes, but only got " + n4 + " bytes. (This is often indicative of an internal error on the server side. Please check your server logs.)");
        }
        return n4;
    }

    public void d() throws dd {
    }

    public byte[] f() {
        return null;
    }

    public int g() {
        return 0;
    }

    public int h() {
        return -1;
    }

    public boolean i() {
        return this.a();
    }
}

