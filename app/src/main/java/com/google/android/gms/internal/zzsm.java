/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzst;
import com.google.android.gms.internal.zzsu;
import com.google.android.gms.internal.zzsx;
import java.io.IOException;

public final class zzsm {
    private final byte[] buffer;
    private int zzbtZ;
    private int zzbua;
    private int zzbub;
    private int zzbuc;
    private int zzbud;
    private int zzbue = Integer.MAX_VALUE;
    private int zzbuf;
    private int zzbug = 64;
    private int zzbuh = 0x4000000;

    private zzsm(byte[] byArray, int n2, int n3) {
        this.buffer = byArray;
        this.zzbtZ = n2;
        this.zzbua = n2 + n3;
        this.zzbuc = n2;
    }

    public static zzsm zzD(byte[] byArray) {
        return zzsm.zza(byArray, 0, byArray.length);
    }

    private void zzJj() {
        this.zzbua += this.zzbub;
        int n2 = this.zzbua;
        if (n2 > this.zzbue) {
            this.zzbub = n2 - this.zzbue;
            this.zzbua -= this.zzbub;
            return;
        }
        this.zzbub = 0;
    }

    public static zzsm zza(byte[] byArray, int n2, int n3) {
        return new zzsm(byArray, n2, n3);
    }

    public static long zzan(long l2) {
        return l2 >>> 1 ^ -(1L & l2);
    }

    public static int zzmp(int n2) {
        return n2 >>> 1 ^ -(n2 & 1);
    }

    public int getPosition() {
        return this.zzbuc - this.zzbtZ;
    }

    public byte[] readBytes() throws IOException {
        int n2 = this.zzJf();
        if (n2 <= this.zzbua - this.zzbuc && n2 > 0) {
            byte[] byArray = new byte[n2];
            System.arraycopy(this.buffer, this.zzbuc, byArray, 0, n2);
            this.zzbuc = n2 + this.zzbuc;
            return byArray;
        }
        if (n2 == 0) {
            return zzsx.zzbuD;
        }
        return this.zzmt(n2);
    }

    public double readDouble() throws IOException {
        return Double.longBitsToDouble(this.zzJi());
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(this.zzJh());
    }

    public String readString() throws IOException {
        int n2 = this.zzJf();
        if (n2 <= this.zzbua - this.zzbuc && n2 > 0) {
            String string2 = new String(this.buffer, this.zzbuc, n2, "UTF-8");
            this.zzbuc = n2 + this.zzbuc;
            return string2;
        }
        return new String(this.zzmt(n2), "UTF-8");
    }

    public int zzIX() throws IOException {
        if (this.zzJl()) {
            this.zzbud = 0;
            return 0;
        }
        this.zzbud = this.zzJf();
        if (this.zzbud == 0) {
            throw zzst.zzJv();
        }
        return this.zzbud;
    }

    public void zzIY() throws IOException {
        int n2;
        while ((n2 = this.zzIX()) != 0 && this.zzmo(n2)) {
        }
    }

    public long zzIZ() throws IOException {
        return this.zzJg();
    }

    public long zzJa() throws IOException {
        return this.zzJg();
    }

    public int zzJb() throws IOException {
        return this.zzJf();
    }

    public boolean zzJc() throws IOException {
        return this.zzJf() != 0;
    }

    public int zzJd() throws IOException {
        return zzsm.zzmp(this.zzJf());
    }

    public long zzJe() throws IOException {
        return zzsm.zzan(this.zzJg());
    }

    /*
     * Enabled aggressive block sorting
     */
    public int zzJf() throws IOException {
        int n2 = this.zzJm();
        if (n2 < 0) {
            n2 &= 0x7F;
            int n3 = this.zzJm();
            if (n3 >= 0) {
                return n2 | n3 << 7;
            }
            n2 |= (n3 & 0x7F) << 7;
            n3 = this.zzJm();
            if (n3 >= 0) {
                return n2 | n3 << 14;
            }
            n2 |= (n3 & 0x7F) << 14;
            int n4 = this.zzJm();
            if (n4 >= 0) {
                return n2 | n4 << 21;
            }
            n3 = this.zzJm();
            n2 = n4 = n2 | (n4 & 0x7F) << 21 | n3 << 28;
            if (n3 < 0) {
                n3 = 0;
                while (true) {
                    if (n3 >= 5) {
                        throw zzst.zzJu();
                    }
                    n2 = n4;
                    if (this.zzJm() >= 0) break;
                    ++n3;
                }
            }
        }
        return n2;
    }

    public long zzJg() throws IOException {
        long l2 = 0L;
        for (int i2 = 0; i2 < 64; i2 += 7) {
            byte by2 = this.zzJm();
            l2 |= (long)(by2 & 0x7F) << i2;
            if ((by2 & 0x80) != 0) continue;
            return l2;
        }
        throw zzst.zzJu();
    }

    public int zzJh() throws IOException {
        return this.zzJm() & 0xFF | (this.zzJm() & 0xFF) << 8 | (this.zzJm() & 0xFF) << 16 | (this.zzJm() & 0xFF) << 24;
    }

    public long zzJi() throws IOException {
        byte by2 = this.zzJm();
        byte by3 = this.zzJm();
        byte by4 = this.zzJm();
        byte by5 = this.zzJm();
        byte by6 = this.zzJm();
        byte by7 = this.zzJm();
        byte by8 = this.zzJm();
        byte by9 = this.zzJm();
        long l2 = by2;
        return ((long)by3 & 0xFFL) << 8 | l2 & 0xFFL | ((long)by4 & 0xFFL) << 16 | ((long)by5 & 0xFFL) << 24 | ((long)by6 & 0xFFL) << 32 | ((long)by7 & 0xFFL) << 40 | ((long)by8 & 0xFFL) << 48 | ((long)by9 & 0xFFL) << 56;
    }

    public int zzJk() {
        if (this.zzbue == Integer.MAX_VALUE) {
            return -1;
        }
        int n2 = this.zzbuc;
        return this.zzbue - n2;
    }

    public boolean zzJl() {
        return this.zzbuc == this.zzbua;
    }

    public byte zzJm() throws IOException {
        if (this.zzbuc == this.zzbua) {
            throw zzst.zzJs();
        }
        byte[] byArray = this.buffer;
        int n2 = this.zzbuc;
        this.zzbuc = n2 + 1;
        return byArray[n2];
    }

    public void zza(zzsu zzsu2) throws IOException {
        int n2 = this.zzJf();
        if (this.zzbuf >= this.zzbug) {
            throw zzst.zzJy();
        }
        n2 = this.zzmq(n2);
        ++this.zzbuf;
        zzsu2.mergeFrom(this);
        this.zzmn(0);
        --this.zzbuf;
        this.zzmr(n2);
    }

    public void zza(zzsu zzsu2, int n2) throws IOException {
        if (this.zzbuf >= this.zzbug) {
            throw zzst.zzJy();
        }
        ++this.zzbuf;
        zzsu2.mergeFrom(this);
        this.zzmn(zzsx.zzF(n2, 4));
        --this.zzbuf;
    }

    public void zzmn(int n2) throws zzst {
        if (this.zzbud != n2) {
            throw zzst.zzJw();
        }
    }

    public boolean zzmo(int n2) throws IOException {
        switch (zzsx.zzmI(n2)) {
            default: {
                throw zzst.zzJx();
            }
            case 0: {
                this.zzJb();
                return true;
            }
            case 1: {
                this.zzJi();
                return true;
            }
            case 2: {
                this.zzmu(this.zzJf());
                return true;
            }
            case 3: {
                this.zzIY();
                this.zzmn(zzsx.zzF(zzsx.zzmJ(n2), 4));
                return true;
            }
            case 4: {
                return false;
            }
            case 5: 
        }
        this.zzJh();
        return true;
    }

    public int zzmq(int n2) throws zzst {
        if (n2 < 0) {
            throw zzst.zzJt();
        }
        int n3 = this.zzbue;
        if ((n2 = this.zzbuc + n2) > n3) {
            throw zzst.zzJs();
        }
        this.zzbue = n2;
        this.zzJj();
        return n3;
    }

    public void zzmr(int n2) {
        this.zzbue = n2;
        this.zzJj();
    }

    public void zzms(int n2) {
        if (n2 > this.zzbuc - this.zzbtZ) {
            throw new IllegalArgumentException("Position " + n2 + " is beyond current " + (this.zzbuc - this.zzbtZ));
        }
        if (n2 < 0) {
            throw new IllegalArgumentException("Bad position " + n2);
        }
        this.zzbuc = this.zzbtZ + n2;
    }

    public byte[] zzmt(int n2) throws IOException {
        if (n2 < 0) {
            throw zzst.zzJt();
        }
        if (this.zzbuc + n2 > this.zzbue) {
            this.zzmu(this.zzbue - this.zzbuc);
            throw zzst.zzJs();
        }
        if (n2 <= this.zzbua - this.zzbuc) {
            byte[] byArray = new byte[n2];
            System.arraycopy(this.buffer, this.zzbuc, byArray, 0, n2);
            this.zzbuc += n2;
            return byArray;
        }
        throw zzst.zzJs();
    }

    public void zzmu(int n2) throws IOException {
        if (n2 < 0) {
            throw zzst.zzJt();
        }
        if (this.zzbuc + n2 > this.zzbue) {
            this.zzmu(this.zzbue - this.zzbuc);
            throw zzst.zzJs();
        }
        if (n2 <= this.zzbua - this.zzbuc) {
            this.zzbuc += n2;
            return;
        }
        throw zzst.zzJs();
    }

    public byte[] zzz(int n2, int n3) {
        if (n3 == 0) {
            return zzsx.zzbuD;
        }
        byte[] byArray = new byte[n3];
        int n4 = this.zzbtZ;
        System.arraycopy(this.buffer, n4 + n2, byArray, 0, n3);
        return byArray;
    }
}

