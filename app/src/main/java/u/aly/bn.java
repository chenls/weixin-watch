/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

public class bn {
    private short[] a;
    private int b = -1;

    public bn(int n2) {
        this.a = new short[n2];
    }

    private void d() {
        short[] sArray = new short[this.a.length * 2];
        System.arraycopy(this.a, 0, sArray, 0, this.a.length);
        this.a = sArray;
    }

    public short a() {
        short[] sArray = this.a;
        int n2 = this.b;
        this.b = n2 - 1;
        return sArray[n2];
    }

    public void a(short s2) {
        int n2;
        if (this.a.length == this.b + 1) {
            this.d();
        }
        short[] sArray = this.a;
        this.b = n2 = this.b + 1;
        sArray[n2] = s2;
    }

    public short b() {
        return this.a[this.b];
    }

    public void c() {
        this.b = -1;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<ShortStack vector:[");
        for (int i2 = 0; i2 < this.a.length; ++i2) {
            if (i2 != 0) {
                stringBuilder.append(" ");
            }
            if (i2 == this.b) {
                stringBuilder.append(">>");
            }
            stringBuilder.append(this.a[i2]);
            if (i2 != this.b) continue;
            stringBuilder.append("<<");
        }
        stringBuilder.append("]>");
        return stringBuilder.toString();
    }
}

