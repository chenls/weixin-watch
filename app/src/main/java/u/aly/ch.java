/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
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

public class ch
extends co {
    protected static final int a = -65536;
    protected static final int b = -2147418112;
    private static final ct h = new ct();
    protected boolean c = false;
    protected boolean d = true;
    protected int e;
    protected boolean f = false;
    private byte[] i = new byte[1];
    private byte[] j = new byte[2];
    private byte[] k = new byte[4];
    private byte[] l = new byte[8];
    private byte[] m = new byte[1];
    private byte[] n = new byte[2];
    private byte[] o = new byte[4];
    private byte[] p = new byte[8];

    public ch(dc dc2) {
        this(dc2, false, true);
    }

    public ch(dc dc2, boolean bl2, boolean bl3) {
        super(dc2);
        this.c = bl2;
        this.d = bl3;
    }

    private int a(byte[] byArray, int n2, int n3) throws bv {
        this.d(n3);
        return this.g.d(byArray, n2, n3);
    }

    @Override
    public ByteBuffer A() throws bv {
        int n2 = this.w();
        this.d(n2);
        if (this.g.h() >= n2) {
            ByteBuffer byteBuffer = ByteBuffer.wrap(this.g.f(), this.g.g(), n2);
            this.g.a(n2);
            return byteBuffer;
        }
        byte[] byArray = new byte[n2];
        this.g.d(byArray, 0, n2);
        return ByteBuffer.wrap(byArray);
    }

    @Override
    public void a() {
    }

    @Override
    public void a(byte by2) throws bv {
        this.i[0] = by2;
        this.g.b(this.i, 0, 1);
    }

    @Override
    public void a(double d2) throws bv {
        this.a(Double.doubleToLongBits(d2));
    }

    @Override
    public void a(int n2) throws bv {
        this.k[0] = (byte)(n2 >> 24 & 0xFF);
        this.k[1] = (byte)(n2 >> 16 & 0xFF);
        this.k[2] = (byte)(n2 >> 8 & 0xFF);
        this.k[3] = (byte)(n2 & 0xFF);
        this.g.b(this.k, 0, 4);
    }

    @Override
    public void a(long l2) throws bv {
        this.l[0] = (byte)(l2 >> 56 & 0xFFL);
        this.l[1] = (byte)(l2 >> 48 & 0xFFL);
        this.l[2] = (byte)(l2 >> 40 & 0xFFL);
        this.l[3] = (byte)(l2 >> 32 & 0xFFL);
        this.l[4] = (byte)(l2 >> 24 & 0xFFL);
        this.l[5] = (byte)(l2 >> 16 & 0xFFL);
        this.l[6] = (byte)(l2 >> 8 & 0xFFL);
        this.l[7] = (byte)(0xFFL & l2);
        this.g.b(this.l, 0, 8);
    }

    @Override
    public void a(String object) throws bv {
        try {
            object = ((String)object).getBytes("UTF-8");
            this.a(((Object)object).length);
            this.g.b((byte[])object, 0, ((Object)object).length);
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new bv("JVM DOES NOT SUPPORT UTF-8");
        }
    }

    @Override
    public void a(ByteBuffer byteBuffer) throws bv {
        int n2 = byteBuffer.limit() - byteBuffer.position();
        this.a(n2);
        this.g.b(byteBuffer.array(), byteBuffer.position() + byteBuffer.arrayOffset(), n2);
    }

    @Override
    public void a(cj cj2) throws bv {
        this.a(cj2.b);
        this.a(cj2.c);
    }

    @Override
    public void a(ck ck2) throws bv {
        this.a(ck2.a);
        this.a(ck2.b);
    }

    @Override
    public void a(cl cl2) throws bv {
        this.a(cl2.a);
        this.a(cl2.b);
        this.a(cl2.c);
    }

    @Override
    public void a(cm cm2) throws bv {
        if (this.d) {
            this.a(0x80010000 | cm2.b);
            this.a(cm2.a);
            this.a(cm2.c);
            return;
        }
        this.a(cm2.a);
        this.a(cm2.b);
        this.a(cm2.c);
    }

    @Override
    public void a(cs cs2) throws bv {
        this.a(cs2.a);
        this.a(cs2.b);
    }

    @Override
    public void a(ct ct2) {
    }

    @Override
    public void a(short s2) throws bv {
        this.j[0] = (byte)(s2 >> 8 & 0xFF);
        this.j[1] = (byte)(s2 & 0xFF);
        this.g.b(this.j, 0, 2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void a(boolean bl2) throws bv {
        byte by2 = bl2 ? (byte)1 : 0;
        this.a(by2);
    }

    public String b(int n2) throws bv {
        try {
            this.d(n2);
            Object object = new byte[n2];
            this.g.d((byte[])object, 0, n2);
            object = new String((byte[])object, "UTF-8");
            return object;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new bv("JVM DOES NOT SUPPORT UTF-8");
        }
    }

    @Override
    public void b() {
    }

    @Override
    public void c() {
    }

    public void c(int n2) {
        this.e = n2;
        this.f = true;
    }

    @Override
    public void d() throws bv {
        this.a((byte)0);
    }

    protected void d(int n2) throws bv {
        if (n2 < 0) {
            throw new cp("Negative length: " + n2);
        }
        if (this.f) {
            this.e -= n2;
            if (this.e < 0) {
                throw new cp("Message length exceeded: " + n2);
            }
        }
    }

    @Override
    public void e() {
    }

    @Override
    public void f() {
    }

    @Override
    public void g() {
    }

    @Override
    public cm h() throws bv {
        int n2 = this.w();
        if (n2 < 0) {
            if ((0xFFFF0000 & n2) != -2147418112) {
                throw new cp(4, "Bad version in readMessageBegin");
            }
            return new cm(this.z(), (byte)(n2 & 0xFF), this.w());
        }
        if (this.c) {
            throw new cp(4, "Missing version in readMessageBegin, old client?");
        }
        return new cm(this.b(n2), this.u(), this.w());
    }

    @Override
    public void i() {
    }

    @Override
    public ct j() {
        return h;
    }

    @Override
    public void k() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public cj l() throws bv {
        short s2;
        byte by2 = this.u();
        if (by2 == 0) {
            s2 = 0;
            return new cj("", by2, s2);
        }
        s2 = this.v();
        return new cj("", by2, s2);
    }

    @Override
    public void m() {
    }

    @Override
    public cl n() throws bv {
        return new cl(this.u(), this.u(), this.w());
    }

    @Override
    public void o() {
    }

    @Override
    public ck p() throws bv {
        return new ck(this.u(), this.w());
    }

    @Override
    public void q() {
    }

    @Override
    public cs r() throws bv {
        return new cs(this.u(), this.w());
    }

    @Override
    public void s() {
    }

    @Override
    public boolean t() throws bv {
        return this.u() == 1;
    }

    @Override
    public byte u() throws bv {
        if (this.g.h() >= 1) {
            byte by2 = this.g.f()[this.g.g()];
            this.g.a(1);
            return by2;
        }
        this.a(this.m, 0, 1);
        return this.m[0];
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public short v() throws bv {
        int n2 = 0;
        byte[] byArray = this.n;
        if (this.g.h() >= 2) {
            byArray = this.g.f();
            n2 = this.g.g();
            this.g.a(2);
        } else {
            this.a(this.n, 0, 2);
        }
        byte by2 = byArray[n2];
        return (short)(byArray[n2 + 1] & 0xFF | (by2 & 0xFF) << 8);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int w() throws bv {
        int n2 = 0;
        byte[] byArray = this.o;
        if (this.g.h() >= 4) {
            byArray = this.g.f();
            n2 = this.g.g();
            this.g.a(4);
        } else {
            this.a(this.o, 0, 4);
        }
        byte by2 = byArray[n2];
        byte by3 = byArray[n2 + 1];
        byte by4 = byArray[n2 + 2];
        return byArray[n2 + 3] & 0xFF | ((by2 & 0xFF) << 24 | (by3 & 0xFF) << 16 | (by4 & 0xFF) << 8);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public long x() throws bv {
        int n2 = 0;
        byte[] byArray = this.p;
        if (this.g.h() >= 8) {
            byArray = this.g.f();
            n2 = this.g.g();
            this.g.a(8);
        } else {
            this.a(this.p, 0, 8);
        }
        long l2 = byArray[n2] & 0xFF;
        long l3 = byArray[n2 + 1] & 0xFF;
        long l4 = byArray[n2 + 2] & 0xFF;
        long l5 = byArray[n2 + 3] & 0xFF;
        long l6 = byArray[n2 + 4] & 0xFF;
        long l7 = byArray[n2 + 5] & 0xFF;
        long l8 = byArray[n2 + 6] & 0xFF;
        return (long)(byArray[n2 + 7] & 0xFF) | (l2 << 56 | l3 << 48 | l4 << 40 | l5 << 32 | l6 << 24 | l7 << 16 | l8 << 8);
    }

    @Override
    public double y() throws bv {
        return Double.longBitsToDouble(this.x());
    }

    @Override
    public String z() throws bv {
        int n2 = this.w();
        if (this.g.h() >= n2) {
            try {
                String string2 = new String(this.g.f(), this.g.g(), n2, "UTF-8");
                this.g.a(n2);
                return string2;
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                throw new bv("JVM DOES NOT SUPPORT UTF-8");
            }
        }
        return this.b(n2);
    }

    public static class a
    implements cq {
        protected boolean a = false;
        protected boolean b = true;
        protected int c;

        public a() {
            this(false, true);
        }

        public a(boolean bl2, boolean bl3) {
            this(bl2, bl3, 0);
        }

        public a(boolean bl2, boolean bl3, int n2) {
            this.a = bl2;
            this.b = bl3;
            this.c = n2;
        }

        @Override
        public co a(dc object) {
            object = new ch((dc)object, this.a, this.b);
            if (this.c != 0) {
                ((ch)object).c(this.c);
            }
            return object;
        }
    }
}

