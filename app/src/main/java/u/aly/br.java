/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import java.io.ByteArrayOutputStream;

public class br
extends ByteArrayOutputStream {
    public br() {
    }

    public br(int n2) {
        super(n2);
    }

    public byte[] a() {
        return this.buf;
    }

    public int b() {
        return this.count;
    }
}

