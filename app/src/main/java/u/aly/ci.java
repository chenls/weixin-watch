/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import u.aly.bn;
import u.aly.bv;
import u.aly.cj;
import u.aly.ck;
import u.aly.cl;
import u.aly.cm;
import u.aly.co;
import u.aly.cp;
import u.aly.cq;
import u.aly.cs;
import u.aly.ct;
import u.aly.dc;

public class ci
extends co {
    private static final ct d = new ct("");
    private static final cj e = new cj("", 0, 0);
    private static final byte[] f = new byte[16];
    private static final byte h = -126;
    private static final byte i = 1;
    private static final byte j = 31;
    private static final byte k = -32;
    private static final int l = 5;
    byte[] a;
    byte[] b;
    byte[] c;
    private bn m = new bn(15);
    private short n = 0;
    private cj o = null;
    private Boolean p = null;
    private final long q;
    private byte[] r;

    static {
        ci.f[0] = 0;
        ci.f[2] = 1;
        ci.f[3] = 3;
        ci.f[6] = 4;
        ci.f[8] = 5;
        ci.f[10] = 6;
        ci.f[4] = 7;
        ci.f[11] = 8;
        ci.f[15] = 9;
        ci.f[14] = 10;
        ci.f[13] = 11;
        ci.f[12] = 12;
    }

    public ci(dc dc2) {
        this(dc2, -1L);
    }

    public ci(dc dc2, long l2) {
        super(dc2);
        this.a = new byte[5];
        this.b = new byte[10];
        this.r = new byte[1];
        this.c = new byte[1];
        this.q = l2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     */
    private int E() throws bv {
        block3: {
            var3_1 = 0;
            var1_2 = 0;
            if (this.g.h() >= 5) break block3;
            var2_6 = 0;
            var1_2 = var3_1;
            if (true) ** GOTO lbl23
        }
        var6_3 = this.g.f();
        var4_4 = this.g.g();
        var2_5 = 0;
        var3_1 = 0;
        while (true) {
            var5_7 = var6_3[var4_4 + var1_2];
            var3_1 |= (var5_7 & 127) << var2_5;
            if ((var5_7 & 128) != 128) {
                this.g.a(var1_2 + 1);
                return var3_1;
            }
            var2_5 += 7;
            ++var1_2;
        }
        do {
            var1_2 += 7;
lbl23:
            // 2 sources

            var3_1 = this.u();
            var2_6 |= (var3_1 & 127) << var1_2;
        } while ((var3_1 & 128) == 128);
        return var2_6;
    }

    /*
     * Unable to fully structure code
     */
    private long F() throws bv {
        var1_1 = 0;
        var3_2 = 0;
        var7_4 = var5_3 = 0L;
        if (this.g.h() < 10) ** GOTO lbl19
        var9_5 = this.g.f();
        var4_6 = this.g.g();
        var2_7 = 0;
        var1_1 = var3_2;
        while (true) {
            var3_2 = var9_5[var4_6 + var1_1];
            var5_3 |= (long)(var3_2 & 127) << var2_7;
            if ((var3_2 & 128) != 128) {
                this.g.a(var1_1 + 1);
                return var5_3;
            }
            var2_7 += 7;
            ++var1_1;
        }
lbl-1000:
        // 1 sources

        {
            var1_1 += 7;
lbl19:
            // 2 sources

            var2_8 = this.u();
            var7_4 |= (long)(var2_8 & 127) << var1_1;
            ** while ((var2_8 & 128) == 128)
        }
lbl22:
        // 1 sources

        return var7_4;
    }

    private long a(byte[] byArray) {
        return ((long)byArray[7] & 0xFFL) << 56 | ((long)byArray[6] & 0xFFL) << 48 | ((long)byArray[5] & 0xFFL) << 40 | ((long)byArray[4] & 0xFFL) << 32 | ((long)byArray[3] & 0xFFL) << 24 | ((long)byArray[2] & 0xFFL) << 16 | ((long)byArray[1] & 0xFFL) << 8 | (long)byArray[0] & 0xFFL;
    }

    private void a(long l2, byte[] byArray, int n2) {
        byArray[n2 + 0] = (byte)(l2 & 0xFFL);
        byArray[n2 + 1] = (byte)(l2 >> 8 & 0xFFL);
        byArray[n2 + 2] = (byte)(l2 >> 16 & 0xFFL);
        byArray[n2 + 3] = (byte)(l2 >> 24 & 0xFFL);
        byArray[n2 + 4] = (byte)(l2 >> 32 & 0xFFL);
        byArray[n2 + 5] = (byte)(l2 >> 40 & 0xFFL);
        byArray[n2 + 6] = (byte)(l2 >> 48 & 0xFFL);
        byArray[n2 + 7] = (byte)(l2 >> 56 & 0xFFL);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void a(cj cj2, byte by2) throws bv {
        byte by3 = by2;
        if (by2 == -1) {
            by3 = this.e(cj2.b);
        }
        if (cj2.c > this.n && cj2.c - this.n <= 15) {
            this.d(cj2.c - this.n << 4 | by3);
        } else {
            this.b(by3);
            this.a(cj2.c);
        }
        this.n = cj2.c;
    }

    private void a(byte[] byArray, int n2, int n3) throws bv {
        this.b(n3);
        this.g.b(byArray, n2, n3);
    }

    private void b(byte by2) throws bv {
        this.r[0] = by2;
        this.g.b(this.r);
    }

    private void b(int n2) throws bv {
        int n3 = 0;
        int n4 = n2;
        n2 = n3;
        while (true) {
            if ((n4 & 0xFFFFFF80) == 0) {
                this.a[n2] = (byte)n4;
                this.g.b(this.a, 0, n2 + 1);
                return;
            }
            this.a[n2] = (byte)(n4 & 0x7F | 0x80);
            n4 >>>= 7;
            ++n2;
        }
    }

    private void b(long l2) throws bv {
        int n2 = 0;
        while (true) {
            if ((0xFFFFFFFFFFFFFF80L & l2) == 0L) {
                this.b[n2] = (byte)l2;
                this.g.b(this.b, 0, n2 + 1);
                return;
            }
            this.b[n2] = (byte)(0x7FL & l2 | 0x80L);
            l2 >>>= 7;
            ++n2;
        }
    }

    private int c(int n2) {
        return n2 << 1 ^ n2 >> 31;
    }

    private long c(long l2) {
        return l2 << 1 ^ l2 >> 63;
    }

    private boolean c(byte by2) {
        return (by2 = (byte)(by2 & 0xF)) == 1 || by2 == 2;
    }

    private byte d(byte by2) throws cp {
        switch ((byte)(by2 & 0xF)) {
            default: {
                throw new cp("don't know what type: " + (byte)(by2 & 0xF));
            }
            case 0: {
                return 0;
            }
            case 1: 
            case 2: {
                return 2;
            }
            case 3: {
                return 3;
            }
            case 4: {
                return 6;
            }
            case 5: {
                return 8;
            }
            case 6: {
                return 10;
            }
            case 7: {
                return 4;
            }
            case 8: {
                return 11;
            }
            case 9: {
                return 15;
            }
            case 10: {
                return 14;
            }
            case 11: {
                return 13;
            }
            case 12: 
        }
        return 12;
    }

    private long d(long l2) {
        return l2 >>> 1 ^ -(1L & l2);
    }

    private void d(int n2) throws bv {
        this.b((byte)n2);
    }

    private byte e(byte by2) {
        return f[by2];
    }

    private byte[] e(int n2) throws bv {
        if (n2 == 0) {
            return new byte[0];
        }
        byte[] byArray = new byte[n2];
        this.g.d(byArray, 0, n2);
        return byArray;
    }

    private void f(int n2) throws cp {
        if (n2 < 0) {
            throw new cp("Negative length: " + n2);
        }
        if (this.q != -1L && (long)n2 > this.q) {
            throw new cp("Length exceeded max allowed: " + n2);
        }
    }

    private int g(int n2) {
        return n2 >>> 1 ^ -(n2 & 1);
    }

    @Override
    public ByteBuffer A() throws bv {
        int n2 = this.E();
        this.f(n2);
        if (n2 == 0) {
            return ByteBuffer.wrap(new byte[0]);
        }
        byte[] byArray = new byte[n2];
        this.g.d(byArray, 0, n2);
        return ByteBuffer.wrap(byArray);
    }

    @Override
    public void B() {
        this.m.c();
        this.n = 0;
    }

    @Override
    public void a() throws bv {
    }

    @Override
    public void a(byte by2) throws bv {
        this.b(by2);
    }

    protected void a(byte by2, int n2) throws bv {
        if (n2 <= 14) {
            this.d(n2 << 4 | this.e(by2));
            return;
        }
        this.d(this.e(by2) | 0xF0);
        this.b(n2);
    }

    @Override
    public void a(double d2) throws bv {
        byte[] byArray;
        byte[] byArray2 = byArray = new byte[8];
        byArray[0] = 0;
        byArray2[1] = 0;
        byArray2[2] = 0;
        byArray2[3] = 0;
        byArray2[4] = 0;
        byArray2[5] = 0;
        byArray2[6] = 0;
        byArray2[7] = 0;
        this.a(Double.doubleToLongBits(d2), byArray, 0);
        this.g.b(byArray);
    }

    @Override
    public void a(int n2) throws bv {
        this.b(this.c(n2));
    }

    @Override
    public void a(long l2) throws bv {
        this.b(this.c(l2));
    }

    @Override
    public void a(String object) throws bv {
        try {
            object = ((String)object).getBytes("UTF-8");
            this.a((byte[])object, 0, ((Object)object).length);
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new bv("UTF-8 not supported!");
        }
    }

    @Override
    public void a(ByteBuffer byteBuffer) throws bv {
        int n2 = byteBuffer.limit();
        int n3 = byteBuffer.position();
        this.a(byteBuffer.array(), byteBuffer.position() + byteBuffer.arrayOffset(), n2 - n3);
    }

    @Override
    public void a(cj cj2) throws bv {
        if (cj2.b == 2) {
            this.o = cj2;
            return;
        }
        this.a(cj2, (byte)-1);
    }

    @Override
    public void a(ck ck2) throws bv {
        this.a(ck2.a, ck2.b);
    }

    @Override
    public void a(cl cl2) throws bv {
        if (cl2.c == 0) {
            this.d(0);
            return;
        }
        this.b(cl2.c);
        this.d(this.e(cl2.a) << 4 | this.e(cl2.b));
    }

    @Override
    public void a(cm cm2) throws bv {
        this.b((byte)-126);
        this.d(cm2.b << 5 & 0xFFFFFFE0 | 1);
        this.b(cm2.c);
        this.a(cm2.a);
    }

    @Override
    public void a(cs cs2) throws bv {
        this.a(cs2.a, cs2.b);
    }

    @Override
    public void a(ct ct2) throws bv {
        this.m.a(this.n);
        this.n = 0;
    }

    @Override
    public void a(short s2) throws bv {
        this.b(this.c(s2));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void a(boolean bl2) throws bv {
        byte by2 = 1;
        byte by3 = 1;
        if (this.o != null) {
            cj cj2 = this.o;
            if (!bl2) {
                by3 = 2;
            }
            this.a(cj2, by3);
            this.o = null;
            return;
        }
        by3 = bl2 ? by2 : (byte)2;
        this.b(by3);
    }

    @Override
    public void b() throws bv {
        this.n = this.m.a();
    }

    @Override
    public void c() throws bv {
    }

    @Override
    public void d() throws bv {
        this.b((byte)0);
    }

    @Override
    public void e() throws bv {
    }

    @Override
    public void f() throws bv {
    }

    @Override
    public void g() throws bv {
    }

    @Override
    public cm h() throws bv {
        int n2 = this.u();
        if (n2 != -126) {
            throw new cp("Expected protocol id " + Integer.toHexString(-126) + " but got " + Integer.toHexString(n2));
        }
        n2 = this.u();
        byte by2 = (byte)(n2 & 0x1F);
        if (by2 != 1) {
            throw new cp("Expected version 1 but got " + by2);
        }
        byte by3 = (byte)(n2 >> 5 & 3);
        n2 = this.E();
        return new cm(this.z(), by3, n2);
    }

    @Override
    public void i() throws bv {
    }

    @Override
    public ct j() throws bv {
        this.m.a(this.n);
        this.n = 0;
        return d;
    }

    @Override
    public void k() throws bv {
        this.n = this.m.a();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public cj l() throws bv {
        byte by2 = this.u();
        if (by2 == 0) {
            return e;
        }
        short s2 = (short)((by2 & 0xF0) >> 4);
        short s3 = s2 == 0 ? this.v() : (short)(s2 + this.n);
        cj cj2 = new cj("", this.d((byte)(by2 & 0xF)), s3);
        if (this.c(by2)) {
            Boolean bl2 = (byte)(by2 & 0xF) == 1 ? Boolean.TRUE : Boolean.FALSE;
            this.p = bl2;
        }
        this.n = cj2.c;
        return cj2;
    }

    @Override
    public void m() throws bv {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public cl n() throws bv {
        byte by2;
        int n2 = this.E();
        if (n2 == 0) {
            by2 = 0;
            return new cl(this.d((byte)(by2 >> 4)), this.d((byte)(by2 & 0xF)), n2);
        }
        by2 = this.u();
        return new cl(this.d((byte)(by2 >> 4)), this.d((byte)(by2 & 0xF)), n2);
    }

    @Override
    public void o() throws bv {
    }

    @Override
    public ck p() throws bv {
        int n2;
        byte by2 = this.u();
        int n3 = n2 = by2 >> 4 & 0xF;
        if (n2 == 15) {
            n3 = this.E();
        }
        return new ck(this.d(by2), n3);
    }

    @Override
    public void q() throws bv {
    }

    @Override
    public cs r() throws bv {
        return new cs(this.p());
    }

    @Override
    public void s() throws bv {
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean t() throws bv {
        boolean bl2 = true;
        if (this.p != null) {
            bl2 = this.p;
            this.p = null;
            return bl2;
        } else {
            if (this.u() == 1) return bl2;
            return false;
        }
    }

    @Override
    public byte u() throws bv {
        if (this.g.h() > 0) {
            byte by2 = this.g.f()[this.g.g()];
            this.g.a(1);
            return by2;
        }
        this.g.d(this.c, 0, 1);
        return this.c[0];
    }

    @Override
    public short v() throws bv {
        return (short)this.g(this.E());
    }

    @Override
    public int w() throws bv {
        return this.g(this.E());
    }

    @Override
    public long x() throws bv {
        return this.d(this.F());
    }

    @Override
    public double y() throws bv {
        byte[] byArray = new byte[8];
        this.g.d(byArray, 0, 8);
        return Double.longBitsToDouble(this.a(byArray));
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public String z() throws bv {
        int n2 = this.E();
        this.f(n2);
        if (n2 == 0) {
            return "";
        }
        try {
            if (this.g.h() < n2) return new String(this.e(n2), "UTF-8");
            String string2 = new String(this.g.f(), this.g.g(), n2, "UTF-8");
            this.g.a(n2);
            return string2;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new bv("UTF-8 not supported!");
        }
    }

    public static class a
    implements cq {
        private final long a;

        public a() {
            this.a = -1L;
        }

        public a(int n2) {
            this.a = n2;
        }

        @Override
        public co a(dc dc2) {
            return new ci(dc2, this.a);
        }
    }

    private static class b {
        public static final byte a = 1;
        public static final byte b = 2;
        public static final byte c = 3;
        public static final byte d = 4;
        public static final byte e = 5;
        public static final byte f = 6;
        public static final byte g = 7;
        public static final byte h = 8;
        public static final byte i = 9;
        public static final byte j = 10;
        public static final byte k = 11;
        public static final byte l = 12;

        private b() {
        }
    }
}

