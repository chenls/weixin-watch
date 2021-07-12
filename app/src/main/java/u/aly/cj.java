/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

public class cj {
    public final String a;
    public final byte b;
    public final short c;

    public cj() {
        this("", 0, 0);
    }

    public cj(String string2, byte by2, short s2) {
        this.a = string2;
        this.b = by2;
        this.c = s2;
    }

    public boolean a(cj cj2) {
        return this.b == cj2.b && this.c == cj2.c;
    }

    public String toString() {
        return "<TField name:'" + this.a + "' type:" + this.b + " field-id:" + this.c + ">";
    }
}

