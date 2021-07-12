/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import u.aly.bp;
import u.aly.bv;
import u.aly.ci;
import u.aly.co;
import u.aly.cq;
import u.aly.da;

public class by {
    private final ByteArrayOutputStream a = new ByteArrayOutputStream();
    private final da b = new da(this.a);
    private co c;

    public by() {
        this(new ci.a());
    }

    public by(cq cq2) {
        this.c = cq2.a(this.b);
    }

    public String a(bp object, String string2) throws bv {
        try {
            object = new String(this.a((bp)object), string2);
            return object;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new bv("JVM DOES NOT SUPPORT ENCODING: " + string2);
        }
    }

    public byte[] a(bp bp2) throws bv {
        this.a.reset();
        bp2.b(this.c);
        return this.a.toByteArray();
    }

    public String b(bp bp2) throws bv {
        return new String(this.a(bp2));
    }
}

