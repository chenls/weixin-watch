/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

public final class cm {
    public final String a;
    public final byte b;
    public final int c;

    public cm() {
        this("", 0, 0);
    }

    public cm(String string2, byte by2, int n2) {
        this.a = string2;
        this.b = by2;
        this.c = n2;
    }

    public boolean a(cm cm2) {
        return this.a.equals(cm2.a) && this.b == cm2.b && this.c == cm2.c;
    }

    public boolean equals(Object object) {
        if (object instanceof cm) {
            return this.a((cm)object);
        }
        return false;
    }

    public String toString() {
        return "<TMessage name:'" + this.a + "' type: " + this.b + " seqid:" + this.c + ">";
    }
}

