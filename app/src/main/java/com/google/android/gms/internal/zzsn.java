/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzsu;
import com.google.android.gms.internal.zzsx;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;

public final class zzsn {
    private final ByteBuffer zzbui;

    private zzsn(ByteBuffer byteBuffer) {
        this.zzbui = byteBuffer;
        this.zzbui.order(ByteOrder.LITTLE_ENDIAN);
    }

    private zzsn(byte[] byArray, int n2, int n3) {
        this(ByteBuffer.wrap(byArray, n2, n3));
    }

    public static int zzC(int n2, int n3) {
        return zzsn.zzmA(n2) + zzsn.zzmx(n3);
    }

    public static int zzD(int n2, int n3) {
        return zzsn.zzmA(n2) + zzsn.zzmy(n3);
    }

    public static zzsn zzE(byte[] byArray) {
        return zzsn.zzb(byArray, 0, byArray.length);
    }

    public static int zzG(byte[] byArray) {
        return zzsn.zzmC(byArray.length) + byArray.length;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int zza(CharSequence charSequence, int n2) {
        int n3 = charSequence.length();
        int n4 = 0;
        while (n2 < n3) {
            int n5;
            char c2 = charSequence.charAt(n2);
            if (c2 < '\u0800') {
                n4 += 127 - c2 >>> 31;
                n5 = n2;
            } else {
                int n6 = n4 + 2;
                n5 = n2;
                n4 = n6;
                if ('\ud800' <= c2) {
                    n5 = n2;
                    n4 = n6;
                    if (c2 <= '\udfff') {
                        if (Character.codePointAt(charSequence, n2) < 65536) {
                            throw new IllegalArgumentException("Unpaired surrogate at index " + n2);
                        }
                        n5 = n2 + 1;
                        n4 = n6;
                    }
                }
            }
            n2 = n5 + 1;
        }
        return n4;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int zza(CharSequence charSequence, byte[] byArray, int n2, int n3) {
        char c2;
        int n4 = charSequence.length();
        int n5 = 0;
        int n6 = n2 + n3;
        for (n3 = n5; n3 < n4 && n3 + n2 < n6 && (n5 = (int)charSequence.charAt(n3)) < 128; ++n3) {
            byArray[n2 + n3] = (byte)n5;
        }
        if (n3 == n4) {
            return n2 + n4;
        }
        n2 += n3;
        while (true) {
            block9: {
                char c3;
                int n7;
                block13: {
                    block12: {
                        block11: {
                            block10: {
                                block8: {
                                    if (n3 >= n4) {
                                        return n2;
                                    }
                                    c2 = charSequence.charAt(n3);
                                    if (c2 >= '\u0080' || n2 >= n6) break block8;
                                    n5 = n2 + 1;
                                    byArray[n2] = (byte)c2;
                                    n2 = n5;
                                    break block9;
                                }
                                if (c2 >= '\u0800' || n2 > n6 - 2) break block10;
                                n5 = n2 + 1;
                                byArray[n2] = (byte)(c2 >>> 6 | 0x3C0);
                                n2 = n5 + 1;
                                byArray[n5] = (byte)(c2 & 0x3F | 0x80);
                                break block9;
                            }
                            if (c2 >= '\ud800' && '\udfff' >= c2 || n2 > n6 - 3) break block11;
                            n5 = n2 + 1;
                            byArray[n2] = (byte)(c2 >>> 12 | 0x1E0);
                            n7 = n5 + 1;
                            byArray[n5] = (byte)(c2 >>> 6 & 0x3F | 0x80);
                            n2 = n7 + 1;
                            byArray[n7] = (byte)(c2 & 0x3F | 0x80);
                            break block9;
                        }
                        if (n2 > n6 - 4) break;
                        n5 = n3;
                        if (n3 + 1 == charSequence.length()) break block12;
                        if (Character.isSurrogatePair(c2, c3 = charSequence.charAt(++n3))) break block13;
                        n5 = n3;
                    }
                    throw new IllegalArgumentException("Unpaired surrogate at index " + (n5 - 1));
                }
                n5 = Character.toCodePoint(c2, c3);
                n7 = n2 + 1;
                byArray[n2] = (byte)(n5 >>> 18 | 0xF0);
                n2 = n7 + 1;
                byArray[n7] = (byte)(n5 >>> 12 & 0x3F | 0x80);
                n7 = n2 + 1;
                byArray[n2] = (byte)(n5 >>> 6 & 0x3F | 0x80);
                n2 = n7 + 1;
                byArray[n7] = (byte)(n5 & 0x3F | 0x80);
            }
            ++n3;
        }
        if (!('\ud800' > c2 || c2 > '\udfff' || n3 + 1 != charSequence.length() && Character.isSurrogatePair(c2, charSequence.charAt(n3 + 1)))) {
            throw new IllegalArgumentException("Unpaired surrogate at index " + n3);
        }
        throw new ArrayIndexOutOfBoundsException("Failed writing " + c2 + " at index " + n2);
    }

    private static void zza(CharSequence charSequence, ByteBuffer object) {
        if (((Buffer)object).isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
        if (((ByteBuffer)object).hasArray()) {
            try {
                ((Buffer)object).position(zzsn.zza(charSequence, ((ByteBuffer)object).array(), ((ByteBuffer)object).arrayOffset() + ((Buffer)object).position(), ((Buffer)object).remaining()) - ((ByteBuffer)object).arrayOffset());
                return;
            }
            catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                object = new BufferOverflowException();
                ((Throwable)object).initCause(arrayIndexOutOfBoundsException);
                throw object;
            }
        }
        zzsn.zzb(charSequence, (ByteBuffer)object);
    }

    public static int zzaA(boolean bl2) {
        return 1;
    }

    public static int zzar(long l2) {
        return zzsn.zzav(l2);
    }

    public static int zzas(long l2) {
        return zzsn.zzav(l2);
    }

    public static int zzat(long l2) {
        return zzsn.zzav(zzsn.zzax(l2));
    }

    public static int zzav(long l2) {
        if ((0xFFFFFFFFFFFFFF80L & l2) == 0L) {
            return 1;
        }
        if ((0xFFFFFFFFFFFFC000L & l2) == 0L) {
            return 2;
        }
        if ((0xFFFFFFFFFFE00000L & l2) == 0L) {
            return 3;
        }
        if ((0xFFFFFFFFF0000000L & l2) == 0L) {
            return 4;
        }
        if ((0xFFFFFFF800000000L & l2) == 0L) {
            return 5;
        }
        if ((0xFFFFFC0000000000L & l2) == 0L) {
            return 6;
        }
        if ((0xFFFE000000000000L & l2) == 0L) {
            return 7;
        }
        if ((0xFF00000000000000L & l2) == 0L) {
            return 8;
        }
        if ((Long.MIN_VALUE & l2) == 0L) {
            return 9;
        }
        return 10;
    }

    public static long zzax(long l2) {
        return l2 << 1 ^ l2 >> 63;
    }

    public static int zzb(int n2, double d2) {
        return zzsn.zzmA(n2) + zzsn.zzl(d2);
    }

    public static int zzb(int n2, zzsu zzsu2) {
        return zzsn.zzmA(n2) * 2 + zzsn.zzd(zzsu2);
    }

    public static int zzb(int n2, byte[] byArray) {
        return zzsn.zzmA(n2) + zzsn.zzG(byArray);
    }

    public static zzsn zzb(byte[] byArray, int n2, int n3) {
        return new zzsn(byArray, n2, n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void zzb(CharSequence charSequence, ByteBuffer byteBuffer) {
        int n2 = charSequence.length();
        int n3 = 0;
        while (true) {
            block6: {
                char c2;
                int n4;
                char c3;
                block10: {
                    block9: {
                        block8: {
                            block7: {
                                block5: {
                                    if (n3 >= n2) {
                                        return;
                                    }
                                    c3 = charSequence.charAt(n3);
                                    if (c3 >= '\u0080') break block5;
                                    byteBuffer.put((byte)c3);
                                    break block6;
                                }
                                if (c3 >= '\u0800') break block7;
                                byteBuffer.put((byte)(c3 >>> 6 | 0x3C0));
                                byteBuffer.put((byte)(c3 & 0x3F | 0x80));
                                break block6;
                            }
                            if (c3 >= '\ud800' && '\udfff' >= c3) break block8;
                            byteBuffer.put((byte)(c3 >>> 12 | 0x1E0));
                            byteBuffer.put((byte)(c3 >>> 6 & 0x3F | 0x80));
                            byteBuffer.put((byte)(c3 & 0x3F | 0x80));
                            break block6;
                        }
                        n4 = n3;
                        if (n3 + 1 == charSequence.length()) break block9;
                        if (Character.isSurrogatePair(c3, c2 = charSequence.charAt(++n3))) break block10;
                        n4 = n3;
                    }
                    throw new IllegalArgumentException("Unpaired surrogate at index " + (n4 - 1));
                }
                n4 = Character.toCodePoint(c3, c2);
                byteBuffer.put((byte)(n4 >>> 18 | 0xF0));
                byteBuffer.put((byte)(n4 >>> 12 & 0x3F | 0x80));
                byteBuffer.put((byte)(n4 >>> 6 & 0x3F | 0x80));
                byteBuffer.put((byte)(n4 & 0x3F | 0x80));
            }
            ++n3;
        }
    }

    public static int zzc(int n2, float f2) {
        return zzsn.zzmA(n2) + zzsn.zzk(f2);
    }

    public static int zzc(int n2, zzsu zzsu2) {
        return zzsn.zzmA(n2) + zzsn.zze(zzsu2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int zzc(CharSequence charSequence) {
        int n2;
        int n3;
        block3: {
            int n4;
            n3 = charSequence.length();
            for (n4 = 0; n4 < n3 && charSequence.charAt(n4) < '\u0080'; ++n4) {
            }
            int n5 = n4;
            n4 = n3;
            while (true) {
                n2 = n4;
                if (n5 >= n3) break block3;
                n2 = charSequence.charAt(n5);
                if (n2 >= 2048) break;
                ++n5;
                n4 = (127 - n2 >>> 31) + n4;
            }
            n2 = n4 + zzsn.zza(charSequence, n5);
        }
        if (n2 < n3) {
            throw new IllegalArgumentException("UTF-8 length does not fit in int: " + ((long)n2 + 0x100000000L));
        }
        return n2;
    }

    public static int zzd(int n2, long l2) {
        return zzsn.zzmA(n2) + zzsn.zzas(l2);
    }

    public static int zzd(zzsu zzsu2) {
        return zzsu2.getSerializedSize();
    }

    public static int zze(int n2, long l2) {
        return zzsn.zzmA(n2) + zzsn.zzat(l2);
    }

    public static int zze(zzsu zzsu2) {
        int n2 = zzsu2.getSerializedSize();
        return n2 + zzsn.zzmC(n2);
    }

    public static int zzf(int n2, boolean bl2) {
        return zzsn.zzmA(n2) + zzsn.zzaA(bl2);
    }

    public static int zzgO(String string2) {
        int n2 = zzsn.zzc(string2);
        return n2 + zzsn.zzmC(n2);
    }

    public static int zzk(float f2) {
        return 4;
    }

    public static int zzl(double d2) {
        return 8;
    }

    public static int zzmA(int n2) {
        return zzsn.zzmC(zzsx.zzF(n2, 0));
    }

    public static int zzmC(int n2) {
        if ((n2 & 0xFFFFFF80) == 0) {
            return 1;
        }
        if ((n2 & 0xFFFFC000) == 0) {
            return 2;
        }
        if ((0xFFE00000 & n2) == 0) {
            return 3;
        }
        if ((0xF0000000 & n2) == 0) {
            return 4;
        }
        return 5;
    }

    public static int zzmE(int n2) {
        return n2 << 1 ^ n2 >> 31;
    }

    public static int zzmx(int n2) {
        if (n2 >= 0) {
            return zzsn.zzmC(n2);
        }
        return 10;
    }

    public static int zzmy(int n2) {
        return zzsn.zzmC(zzsn.zzmE(n2));
    }

    public static int zzo(int n2, String string2) {
        return zzsn.zzmA(n2) + zzsn.zzgO(string2);
    }

    public void zzA(int n2, int n3) throws IOException {
        this.zzE(n2, 0);
        this.zzmv(n3);
    }

    public void zzB(int n2, int n3) throws IOException {
        this.zzE(n2, 0);
        this.zzmw(n3);
    }

    public void zzE(int n2, int n3) throws IOException {
        this.zzmB(zzsx.zzF(n2, n3));
    }

    public void zzF(byte[] byArray) throws IOException {
        this.zzmB(byArray.length);
        this.zzH(byArray);
    }

    public void zzH(byte[] byArray) throws IOException {
        this.zzc(byArray, 0, byArray.length);
    }

    public int zzJn() {
        return this.zzbui.remaining();
    }

    public void zzJo() {
        if (this.zzJn() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    public void zza(int n2, double d2) throws IOException {
        this.zzE(n2, 1);
        this.zzk(d2);
    }

    public void zza(int n2, long l2) throws IOException {
        this.zzE(n2, 0);
        this.zzao(l2);
    }

    public void zza(int n2, zzsu zzsu2) throws IOException {
        this.zzE(n2, 2);
        this.zzc(zzsu2);
    }

    public void zza(int n2, byte[] byArray) throws IOException {
        this.zzE(n2, 2);
        this.zzF(byArray);
    }

    public void zzao(long l2) throws IOException {
        this.zzau(l2);
    }

    public void zzap(long l2) throws IOException {
        this.zzau(l2);
    }

    public void zzaq(long l2) throws IOException {
        this.zzau(zzsn.zzax(l2));
    }

    public void zzau(long l2) throws IOException {
        while (true) {
            if ((0xFFFFFFFFFFFFFF80L & l2) == 0L) {
                this.zzmz((int)l2);
                return;
            }
            this.zzmz((int)l2 & 0x7F | 0x80);
            l2 >>>= 7;
        }
    }

    public void zzaw(long l2) throws IOException {
        if (this.zzbui.remaining() < 8) {
            throw new zza(this.zzbui.position(), this.zzbui.limit());
        }
        this.zzbui.putLong(l2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void zzaz(boolean bl2) throws IOException {
        int n2 = bl2 ? 1 : 0;
        this.zzmz(n2);
    }

    public void zzb(byte by2) throws IOException {
        if (!this.zzbui.hasRemaining()) {
            throw new zza(this.zzbui.position(), this.zzbui.limit());
        }
        this.zzbui.put(by2);
    }

    public void zzb(int n2, float f2) throws IOException {
        this.zzE(n2, 5);
        this.zzj(f2);
    }

    public void zzb(int n2, long l2) throws IOException {
        this.zzE(n2, 0);
        this.zzap(l2);
    }

    public void zzb(zzsu zzsu2) throws IOException {
        zzsu2.writeTo(this);
    }

    public void zzc(int n2, long l2) throws IOException {
        this.zzE(n2, 0);
        this.zzaq(l2);
    }

    public void zzc(zzsu zzsu2) throws IOException {
        this.zzmB(zzsu2.getCachedSize());
        zzsu2.writeTo(this);
    }

    public void zzc(byte[] byArray, int n2, int n3) throws IOException {
        if (this.zzbui.remaining() >= n3) {
            this.zzbui.put(byArray, n2, n3);
            return;
        }
        throw new zza(this.zzbui.position(), this.zzbui.limit());
    }

    public void zze(int n2, boolean bl2) throws IOException {
        this.zzE(n2, 0);
        this.zzaz(bl2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void zzgN(String string2) throws IOException {
        try {
            int n2 = zzsn.zzmC(string2.length());
            if (n2 == zzsn.zzmC(string2.length() * 3)) {
                int n3 = this.zzbui.position();
                if (this.zzbui.remaining() < n2) {
                    throw new zza(n2 + n3, this.zzbui.limit());
                }
                this.zzbui.position(n3 + n2);
                zzsn.zza((CharSequence)string2, this.zzbui);
                int n4 = this.zzbui.position();
                this.zzbui.position(n3);
                this.zzmB(n4 - n3 - n2);
                this.zzbui.position(n4);
                return;
            }
        }
        catch (BufferOverflowException bufferOverflowException) {
            zza zza2 = new zza(this.zzbui.position(), this.zzbui.limit());
            zza2.initCause(bufferOverflowException);
            throw zza2;
        }
        this.zzmB(zzsn.zzc(string2));
        zzsn.zza((CharSequence)string2, this.zzbui);
    }

    public void zzj(float f2) throws IOException {
        this.zzmD(Float.floatToIntBits(f2));
    }

    public void zzk(double d2) throws IOException {
        this.zzaw(Double.doubleToLongBits(d2));
    }

    public void zzmB(int n2) throws IOException {
        while (true) {
            if ((n2 & 0xFFFFFF80) == 0) {
                this.zzmz(n2);
                return;
            }
            this.zzmz(n2 & 0x7F | 0x80);
            n2 >>>= 7;
        }
    }

    public void zzmD(int n2) throws IOException {
        if (this.zzbui.remaining() < 4) {
            throw new zza(this.zzbui.position(), this.zzbui.limit());
        }
        this.zzbui.putInt(n2);
    }

    public void zzmv(int n2) throws IOException {
        if (n2 >= 0) {
            this.zzmB(n2);
            return;
        }
        this.zzau(n2);
    }

    public void zzmw(int n2) throws IOException {
        this.zzmB(zzsn.zzmE(n2));
    }

    public void zzmz(int n2) throws IOException {
        this.zzb((byte)n2);
    }

    public void zzn(int n2, String string2) throws IOException {
        this.zzE(n2, 2);
        this.zzgN(string2);
    }

    public static class zza
    extends IOException {
        zza(int n2, int n3) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space (pos " + n2 + " limit " + n3 + ").");
        }
    }
}

