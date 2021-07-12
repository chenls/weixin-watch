/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.os.Parcel
 *  android.os.ParcelFileDescriptor
 *  android.os.ParcelFileDescriptor$AutoCloseInputStream
 *  android.os.Parcelable$Creator
 *  android.util.Log
 */
package com.google.android.gms.common.data;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.data.zza;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class BitmapTeleporter
implements SafeParcelable {
    public static final Parcelable.Creator<BitmapTeleporter> CREATOR = new zza();
    final int mVersionCode;
    ParcelFileDescriptor zzIq;
    final int zzabB;
    private Bitmap zzaiY;
    private boolean zzaiZ;
    private File zzaja;

    BitmapTeleporter(int n2, ParcelFileDescriptor parcelFileDescriptor, int n3) {
        this.mVersionCode = n2;
        this.zzIq = parcelFileDescriptor;
        this.zzabB = n3;
        this.zzaiY = null;
        this.zzaiZ = false;
    }

    public BitmapTeleporter(Bitmap bitmap) {
        this.mVersionCode = 1;
        this.zzIq = null;
        this.zzabB = 0;
        this.zzaiY = bitmap;
        this.zzaiZ = true;
    }

    private void zza(Closeable closeable) {
        try {
            closeable.close();
            return;
        }
        catch (IOException iOException) {
            Log.w((String)"BitmapTeleporter", (String)"Could not close stream", (Throwable)iOException);
            return;
        }
    }

    private FileOutputStream zzqb() {
        FileOutputStream fileOutputStream;
        File file;
        if (this.zzaja == null) {
            throw new IllegalStateException("setTempDir() must be called before writing this object to a parcel");
        }
        try {
            file = File.createTempFile("teleporter", ".tmp", this.zzaja);
        }
        catch (IOException iOException) {
            throw new IllegalStateException("Could not create temporary file", iOException);
        }
        try {
            fileOutputStream = new FileOutputStream(file);
            this.zzIq = ParcelFileDescriptor.open((File)file, (int)0x10000000);
            file.delete();
        }
        catch (FileNotFoundException fileNotFoundException) {
            throw new IllegalStateException("Temporary file is somehow already deleted");
        }
        return fileOutputStream;
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void release() {
        if (this.zzaiZ) return;
        try {
            this.zzIq.close();
            return;
        }
        catch (IOException iOException) {
            Log.w((String)"BitmapTeleporter", (String)"Could not close PFD", (Throwable)iOException);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void writeToParcel(Parcel parcel, int n2) {
        Object object;
        if (this.zzIq == null) {
            Bitmap bitmap = this.zzaiY;
            object = ByteBuffer.allocate(bitmap.getRowBytes() * bitmap.getHeight());
            bitmap.copyPixelsToBuffer((Buffer)object);
            byte[] byArray = ((ByteBuffer)object).array();
            object = new DataOutputStream(this.zzqb());
            try {
                ((DataOutputStream)object).writeInt(byArray.length);
                ((DataOutputStream)object).writeInt(bitmap.getWidth());
                ((DataOutputStream)object).writeInt(bitmap.getHeight());
                ((DataOutputStream)object).writeUTF(bitmap.getConfig().toString());
                ((FilterOutputStream)object).write(byArray);
            }
            catch (IOException iOException) {
                throw new IllegalStateException("Could not write into unlinked file", iOException);
            }
        }
        zza.zza(this, parcel, n2 | 1);
        this.zzIq = null;
        return;
        finally {
            this.zza((Closeable)object);
        }
    }

    public void zzc(File file) {
        if (file == null) {
            throw new NullPointerException("Cannot set null temp directory");
        }
        this.zzaja = file;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Bitmap zzqa() {
        Bitmap.Config config;
        int n3;
        int n2;
        if (this.zzaiZ) return this.zzaiY;
        Object object = new DataInputStream((InputStream)new ParcelFileDescriptor.AutoCloseInputStream(this.zzIq));
        try {
            byte[] byArray = new byte[((DataInputStream)object).readInt()];
            n2 = ((DataInputStream)object).readInt();
            n3 = ((DataInputStream)object).readInt();
            config = Bitmap.Config.valueOf((String)((DataInputStream)object).readUTF());
            ((DataInputStream)object).read(byArray);
            object = ByteBuffer.wrap(byArray);
        }
        catch (IOException iOException) {
            throw new IllegalStateException("Could not read from parcel file descriptor", iOException);
        }
        finally {
            this.zza((Closeable)object);
        }
        config = Bitmap.createBitmap((int)n2, (int)n3, (Bitmap.Config)config);
        config.copyPixelsFromBuffer((Buffer)object);
        this.zzaiY = config;
        this.zzaiZ = true;
        return this.zzaiY;
    }
}

