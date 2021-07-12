/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.ParcelFileDescriptor
 */
package com.google.android.gms.internal;

import android.os.ParcelFileDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class zzna {
    public static long zza(InputStream inputStream, OutputStream outputStream, boolean bl2) throws IOException {
        return zzna.zza(inputStream, outputStream, bl2, 1024);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static long zza(InputStream inputStream, OutputStream outputStream, boolean bl2, int n2) throws IOException {
        byte[] byArray = new byte[n2];
        long l2 = 0L;
        try {
            while ((n2 = inputStream.read(byArray, 0, byArray.length)) != -1) {
                l2 += (long)n2;
                outputStream.write(byArray, 0, n2);
            }
            return l2;
        }
        finally {
            if (bl2) {
                zzna.zzb(inputStream);
                zzna.zzb(outputStream);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void zza(ParcelFileDescriptor parcelFileDescriptor) {
        if (parcelFileDescriptor == null) return;
        try {
            parcelFileDescriptor.close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public static byte[] zza(InputStream inputStream, boolean bl2) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        zzna.zza(inputStream, byteArrayOutputStream, bl2);
        return byteArrayOutputStream.toByteArray();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void zzb(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public static byte[] zzk(InputStream inputStream) throws IOException {
        return zzna.zza(inputStream, true);
    }
}

