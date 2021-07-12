/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import u.aly.bp;
import u.aly.bv;
import u.aly.bw;
import u.aly.ci;
import u.aly.cj;
import u.aly.co;
import u.aly.cq;
import u.aly.cr;
import u.aly.db;

public class bs {
    private final co a;
    private final db b = new db();

    public bs() {
        this(new ci.a());
    }

    public bs(cq cq2) {
        this.a = cq2.a(this.b);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Object a(byte by2, byte[] object, bw bw2, bw ... bwArray) throws bv {
        object = this.j((byte[])object, bw2, bwArray);
        if (object == null) return null;
        switch (by2) {
            default: {
                return null;
            }
            case 2: {
                if (object.b != 2) return null;
                boolean bl2 = this.a.t();
                return bl2;
            }
            case 3: {
                if (object.b != 3) return null;
                byte by3 = this.a.u();
                this.b.e();
                this.a.B();
                return by3;
            }
            case 4: {
                if (object.b != 4) return null;
                double d2 = this.a.y();
                this.b.e();
                this.a.B();
                return d2;
            }
            case 6: {
                if (object.b != 6) return null;
                short s2 = this.a.v();
                this.b.e();
                this.a.B();
                return s2;
            }
            case 8: {
                if (object.b != 8) return null;
                by2 = (byte)this.a.w();
                this.b.e();
                this.a.B();
                return (int)by2;
            }
            case 10: {
                if (object.b != 10) return null;
                long l2 = this.a.x();
                this.b.e();
                this.a.B();
                return l2;
            }
            case 11: {
                if (object.b != 11) return null;
                object = this.a.z();
                this.b.e();
                this.a.B();
                return object;
            }
            case 100: 
        }
        try {
            if (object.b != 11) return null;
            object = this.a.A();
            this.b.e();
            this.a.B();
            return object;
        }
        catch (Exception exception) {
            throw new bv(exception);
        }
        catch (Throwable throwable) {
            throw throwable;
        }
        finally {
            this.b.e();
            this.a.B();
        }
    }

    private cj j(byte[] object, bw object2, bw ... bwArray) throws bv {
        int n2;
        int n3 = 0;
        this.b.a((byte[])object);
        bw[] bwArray2 = new bw[bwArray.length + 1];
        bwArray2[0] = object2;
        for (n2 = 0; n2 < bwArray.length; ++n2) {
            bwArray2[n2 + 1] = bwArray[n2];
        }
        this.a.j();
        object = null;
        n2 = n3;
        while (n2 < bwArray2.length) {
            object2 = this.a.l();
            if (((cj)object2).b == 0 || ((cj)object2).c > bwArray2[n2].a()) {
                return null;
            }
            if (((cj)object2).c != bwArray2[n2].a()) {
                cr.a(this.a, ((cj)object2).b);
                this.a.m();
                object = object2;
                continue;
            }
            n3 = n2 + 1;
            object = object2;
            n2 = n3;
            if (n3 >= bwArray2.length) continue;
            this.a.j();
            object = object2;
            n2 = n3;
        }
        return object;
    }

    public Boolean a(byte[] byArray, bw bw2, bw ... bwArray) throws bv {
        return (Boolean)this.a((byte)2, byArray, bw2, bwArray);
    }

    public void a(bp bp2, String string2) throws bv {
        this.a(bp2, string2.getBytes());
    }

    public void a(bp bp2, String string2, String string3) throws bv {
        try {
            this.a(bp2, string2.getBytes(string3));
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new bv("JVM DOES NOT SUPPORT ENCODING: " + string3);
        }
        finally {
            this.a.B();
        }
    }

    public void a(bp bp2, byte[] byArray) throws bv {
        try {
            this.b.a(byArray);
            bp2.a(this.a);
            return;
        }
        finally {
            this.b.e();
            this.a.B();
        }
    }

    public void a(bp bp2, byte[] byArray, bw bw2, bw ... bwArray) throws bv {
        try {
            if (this.j(byArray, bw2, bwArray) != null) {
                bp2.a(this.a);
            }
            return;
        }
        catch (Exception exception) {
            throw new bv(exception);
        }
        finally {
            this.b.e();
            this.a.B();
        }
    }

    public Byte b(byte[] byArray, bw bw2, bw ... bwArray) throws bv {
        return (Byte)this.a((byte)3, byArray, bw2, bwArray);
    }

    public Double c(byte[] byArray, bw bw2, bw ... bwArray) throws bv {
        return (Double)this.a((byte)4, byArray, bw2, bwArray);
    }

    public Short d(byte[] byArray, bw bw2, bw ... bwArray) throws bv {
        return (Short)this.a((byte)6, byArray, bw2, bwArray);
    }

    public Integer e(byte[] byArray, bw bw2, bw ... bwArray) throws bv {
        return (Integer)this.a((byte)8, byArray, bw2, bwArray);
    }

    public Long f(byte[] byArray, bw bw2, bw ... bwArray) throws bv {
        return (Long)this.a((byte)10, byArray, bw2, bwArray);
    }

    public String g(byte[] byArray, bw bw2, bw ... bwArray) throws bv {
        return (String)this.a((byte)11, byArray, bw2, bwArray);
    }

    public ByteBuffer h(byte[] byArray, bw bw2, bw ... bwArray) throws bv {
        return (ByteBuffer)this.a((byte)100, byArray, bw2, bwArray);
    }

    public Short i(byte[] byArray, bw bw2, bw ... bwArray) throws bv {
        try {
            if (this.j(byArray, bw2, bwArray) != null) {
                this.a.j();
                short s2 = this.a.l().c;
                return s2;
            }
            return null;
        }
        catch (Exception exception) {
            throw new bv(exception);
        }
        finally {
            this.b.e();
            this.a.B();
        }
    }
}

