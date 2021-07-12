/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 */
package com.android.volley.toolbox;

import android.os.SystemClock;
import com.android.volley.Cache;
import com.android.volley.VolleyLog;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class DiskBasedCache
implements Cache {
    private static final int CACHE_MAGIC = 538247942;
    private static final int DEFAULT_DISK_USAGE_BYTES = 0x500000;
    private static final float HYSTERESIS_FACTOR = 0.9f;
    private final Map<String, CacheHeader> mEntries = new LinkedHashMap<String, CacheHeader>(16, 0.75f, true);
    private final int mMaxCacheSizeInBytes;
    private final File mRootDirectory;
    private long mTotalSize = 0L;

    public DiskBasedCache(File file) {
        this(file, 0x500000);
    }

    public DiskBasedCache(File file, int n2) {
        this.mRootDirectory = file;
        this.mMaxCacheSizeInBytes = n2;
    }

    private String getFilenameForKey(String string2) {
        int n2 = string2.length() / 2;
        int n3 = string2.substring(0, n2).hashCode();
        return String.valueOf(n3) + String.valueOf(string2.substring(n2).hashCode());
    }

    /*
     * Enabled aggressive block sorting
     */
    private void pruneIfNeeded(int n2) {
        int n3;
        long l2;
        long l3;
        block8: {
            block7: {
                if (this.mTotalSize + (long)n2 < (long)this.mMaxCacheSizeInBytes) break block7;
                if (VolleyLog.DEBUG) {
                    VolleyLog.v("Pruning old cache entries.", new Object[0]);
                }
                l3 = this.mTotalSize;
                int n4 = 0;
                l2 = SystemClock.elapsedRealtime();
                Iterator<Map.Entry<String, CacheHeader>> iterator = this.mEntries.entrySet().iterator();
                do {
                    n3 = n4;
                    if (!iterator.hasNext()) break;
                    CacheHeader cacheHeader = iterator.next().getValue();
                    if (this.getFileForKey(cacheHeader.key).delete()) {
                        this.mTotalSize -= cacheHeader.size;
                    } else {
                        VolleyLog.d("Could not delete cache entry for key=%s, filename=%s", cacheHeader.key, this.getFilenameForKey(cacheHeader.key));
                    }
                    iterator.remove();
                    n4 = n3 = n4 + 1;
                } while (!((float)(this.mTotalSize + (long)n2) < (float)this.mMaxCacheSizeInBytes * 0.9f));
                if (VolleyLog.DEBUG) break block8;
            }
            return;
        }
        VolleyLog.v("pruned %d files, %d bytes, %d ms", n3, this.mTotalSize - l3, SystemClock.elapsedRealtime() - l2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void putEntry(String string2, CacheHeader cacheHeader) {
        if (!this.mEntries.containsKey(string2)) {
            this.mTotalSize += cacheHeader.size;
        } else {
            CacheHeader cacheHeader2 = this.mEntries.get(string2);
            this.mTotalSize += cacheHeader.size - cacheHeader2.size;
        }
        this.mEntries.put(string2, cacheHeader);
    }

    private static int read(InputStream inputStream) throws IOException {
        int n2 = inputStream.read();
        if (n2 == -1) {
            throw new EOFException();
        }
        return n2;
    }

    static int readInt(InputStream inputStream) throws IOException {
        return 0 | DiskBasedCache.read(inputStream) << 0 | DiskBasedCache.read(inputStream) << 8 | DiskBasedCache.read(inputStream) << 16 | DiskBasedCache.read(inputStream) << 24;
    }

    static long readLong(InputStream inputStream) throws IOException {
        return 0L | ((long)DiskBasedCache.read(inputStream) & 0xFFL) << 0 | ((long)DiskBasedCache.read(inputStream) & 0xFFL) << 8 | ((long)DiskBasedCache.read(inputStream) & 0xFFL) << 16 | ((long)DiskBasedCache.read(inputStream) & 0xFFL) << 24 | ((long)DiskBasedCache.read(inputStream) & 0xFFL) << 32 | ((long)DiskBasedCache.read(inputStream) & 0xFFL) << 40 | ((long)DiskBasedCache.read(inputStream) & 0xFFL) << 48 | ((long)DiskBasedCache.read(inputStream) & 0xFFL) << 56;
    }

    static String readString(InputStream inputStream) throws IOException {
        return new String(DiskBasedCache.streamToBytes(inputStream, (int)DiskBasedCache.readLong(inputStream)), "UTF-8");
    }

    /*
     * Enabled aggressive block sorting
     */
    static Map<String, String> readStringStringMap(InputStream inputStream) throws IOException {
        int n2 = DiskBasedCache.readInt(inputStream);
        Map<String, String> map = n2 == 0 ? Collections.emptyMap() : new HashMap(n2);
        int n3 = 0;
        while (n3 < n2) {
            map.put(DiskBasedCache.readString(inputStream).intern(), DiskBasedCache.readString(inputStream).intern());
            ++n3;
        }
        return map;
    }

    private void removeEntry(String string2) {
        CacheHeader cacheHeader = this.mEntries.get(string2);
        if (cacheHeader != null) {
            this.mTotalSize -= cacheHeader.size;
            this.mEntries.remove(string2);
        }
    }

    private static byte[] streamToBytes(InputStream inputStream, int n2) throws IOException {
        int n3;
        int n4;
        byte[] byArray = new byte[n2];
        for (n3 = 0; n3 < n2 && (n4 = inputStream.read(byArray, n3, n2 - n3)) != -1; n3 += n4) {
        }
        if (n3 != n2) {
            throw new IOException("Expected " + n2 + " bytes, read " + n3 + " bytes");
        }
        return byArray;
    }

    static void writeInt(OutputStream outputStream, int n2) throws IOException {
        outputStream.write(n2 >> 0 & 0xFF);
        outputStream.write(n2 >> 8 & 0xFF);
        outputStream.write(n2 >> 16 & 0xFF);
        outputStream.write(n2 >> 24 & 0xFF);
    }

    static void writeLong(OutputStream outputStream, long l2) throws IOException {
        outputStream.write((byte)(l2 >>> 0));
        outputStream.write((byte)(l2 >>> 8));
        outputStream.write((byte)(l2 >>> 16));
        outputStream.write((byte)(l2 >>> 24));
        outputStream.write((byte)(l2 >>> 32));
        outputStream.write((byte)(l2 >>> 40));
        outputStream.write((byte)(l2 >>> 48));
        outputStream.write((byte)(l2 >>> 56));
    }

    static void writeString(OutputStream outputStream, String object) throws IOException {
        object = ((String)object).getBytes("UTF-8");
        DiskBasedCache.writeLong(outputStream, ((Object)object).length);
        outputStream.write((byte[])object, 0, ((Object)object).length);
    }

    static void writeStringStringMap(Map<String, String> object, OutputStream outputStream) throws IOException {
        if (object != null) {
            DiskBasedCache.writeInt(outputStream, object.size());
            for (Map.Entry entry : object.entrySet()) {
                DiskBasedCache.writeString(outputStream, (String)entry.getKey());
                DiskBasedCache.writeString(outputStream, (String)entry.getValue());
            }
        } else {
            DiskBasedCache.writeInt(outputStream, 0);
        }
    }

    @Override
    public void clear() {
        synchronized (this) {
            block7: {
                File[] fileArray = this.mRootDirectory.listFiles();
                if (fileArray == null) break block7;
                int n2 = fileArray.length;
                for (int i2 = 0; i2 < n2; ++i2) {
                    fileArray[i2].delete();
                    continue;
                }
            }
            this.mEntries.clear();
            this.mTotalSize = 0L;
            VolleyLog.d("Cache cleared.", new Object[0]);
            return;
        }
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public Cache.Entry get(String var1_1) {
        var5_9 = null;
        synchronized (this) {
            block31: {
                block30: {
                    var4_10 = this.mEntries.get(var1_1);
                    if (var4_10 != null) break block30;
                    var1_1 = var5_9;
lbl7:
                    // 10 sources

                    return var1_1;
                }
                var8_17 = this.getFileForKey((String)var1_1);
                var7_18 = null;
                var2_19 = null;
                var6_20 = null;
                var3_21 = new CountingInputStream(new BufferedInputStream(new FileInputStream(var8_17)));
                CacheHeader.readHeader(var3_21);
                var2_19 = var4_10.toCacheEntry(DiskBasedCache.streamToBytes(var3_21, (int)(var8_17.length() - (long)CountingInputStream.access$100((CountingInputStream)var3_21))));
                if (var3_21 == null) break block31;
                try {
                    var3_21.close();
                }
                catch (IOException var1_2) {
                    var1_1 = var5_9;
                    ** GOTO lbl7
                }
            }
            var1_1 = var2_19;
            ** GOTO lbl7
            catch (IOException var4_11) {
                var3_21 = var6_20;
lbl32:
                // 2 sources

                while (true) {
                    var2_19 = var3_21;
                    VolleyLog.d("%s: %s", new Object[]{var8_17.getAbsolutePath(), var4_12.toString()});
                    var2_19 = var3_21;
                    this.remove((String)var1_1);
                    var1_1 = var5_9;
                    if (var3_21 == null) ** GOTO lbl7
                    try {
                        var3_21.close();
                        var1_1 = var5_9;
                        ** GOTO lbl7
                    }
                    catch (IOException var1_3) {
                        var1_1 = var5_9;
                        ** GOTO lbl7
                    }
                    break;
                }
            }
            catch (NegativeArraySizeException var4_13) {
                var3_21 = var7_18;
lbl50:
                // 2 sources

                while (true) {
                    var2_19 = var3_21;
                    VolleyLog.d("%s: %s", new Object[]{var8_17.getAbsolutePath(), var4_14.toString()});
                    var2_19 = var3_21;
                    this.remove((String)var1_1);
                    var1_1 = var5_9;
                    if (var3_21 == null) ** GOTO lbl7
                    try {
                        var3_21.close();
                        var1_1 = var5_9;
                        ** GOTO lbl7
                    }
                    catch (IOException var1_4) {
                        var1_1 = var5_9;
                        ** GOTO lbl7
                    }
                    {
                        catch (Throwable var1_5) lbl-1000:
                        // 2 sources

                        {
                            while (true) {
                                if (var2_19 == null) ** GOTO lbl72
                                var2_19.close();
lbl72:
                                // 2 sources

                                throw var1_1;
                                break;
                            }
                            catch (Throwable var1_6) {
                                throw var1_6;
                            }
                            finally {
                            }
                            {
                                catch (IOException var1_7) {
                                    var1_1 = var5_9;
                                    ** continue;
                                }
                            }
                        }
                    }
                    break;
                }
            }
            catch (Throwable var1_8) {
                var2_19 = var3_21;
                ** continue;
            }
            catch (NegativeArraySizeException var4_15) {
                ** continue;
            }
            catch (IOException var4_16) {
                ** continue;
            }
        }
    }

    public File getFileForKey(String string2) {
        return new File(this.mRootDirectory, this.getFilenameForKey(string2));
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void initialize() {
        synchronized (this) {
            if (!this.mRootDirectory.exists()) {
                if (!this.mRootDirectory.mkdirs()) {
                    VolleyLog.e("Unable to create cache dir %s", new Object[]{this.mRootDirectory.getAbsolutePath()});
                }
lbl6:
                // 5 sources

                return;
            }
            var6_1 = this.mRootDirectory.listFiles();
            if (var6_1 == null) ** GOTO lbl6
            var2_2 = var6_1.length;
            var1_3 = 0;
            block19: while (true) {
                block27: {
                    if (var1_3 < var2_2) ** break;
                    ** continue;
                    var7_15 = var6_1[var1_3];
                    var3_4 = null;
                    var5_13 = null;
                    var4_11 = new BufferedInputStream(new FileInputStream(var7_15));
                    var3_4 = CacheHeader.readHeader((InputStream)var4_11);
                    var3_4.size = var7_15.length();
                    this.putEntry(var3_4.key, (CacheHeader)var3_4);
                    if (var4_11 == null) break block27;
                    var4_11.close();
                }
lbl29:
                // 5 sources

                while (true) {
                    ++var1_3;
                    continue block19;
                    break;
                }
                break;
            }
            catch (IOException var3_5) {
                ** GOTO lbl29
            }
            catch (IOException var3_6) {
                var4_11 = var5_13;
lbl36:
                // 2 sources

                while (true) {
                    if (var7_15 != null) {
                        var3_4 = var4_11;
                        var7_15.delete();
                    }
                    if (var4_11 == null) ** GOTO lbl29
                    try {
                        var4_11.close();
                        ** GOTO lbl29
                    }
                    catch (IOException var3_7) {
                        ** continue;
                    }
                    catch (Throwable var4_12) lbl-1000:
                    // 2 sources

                    {
                        while (true) {
                            if (var3_4 == null) ** GOTO lbl54
                            var3_4.close();
lbl54:
                            // 3 sources

                            throw var4_11;
                            {
                                catch (IOException var3_9) {
                                    ** continue;
                                }
                            }
                            break;
                        }
                    }
                    break;
                }
            }
            catch (Throwable var5_14) {
                var3_4 = var4_11;
                var4_11 = var5_14;
                ** continue;
            }
            catch (IOException var3_10) {
                ** continue;
            }
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void invalidate(String string2, boolean bl2) {
        synchronized (this) {
            Cache.Entry entry = this.get(string2);
            if (entry != null) {
                void var2_2;
                entry.softTtl = 0L;
                if (var2_2 != false) {
                    entry.ttl = 0L;
                }
                this.put(string2, entry);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void put(String string2, Cache.Entry entry) {
        synchronized (this) {
            this.pruneIfNeeded(entry.data.length);
            File file = this.getFileForKey(string2);
            try {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
                CacheHeader cacheHeader = new CacheHeader(string2, entry);
                if (!cacheHeader.writeHeader(bufferedOutputStream)) {
                    bufferedOutputStream.close();
                    VolleyLog.d("Failed to write header for %s", file.getAbsolutePath());
                    throw new IOException();
                }
                bufferedOutputStream.write(entry.data);
                bufferedOutputStream.close();
                this.putEntry(string2, cacheHeader);
            }
            catch (IOException iOException) {
                if (!file.delete()) {
                    VolleyLog.d("Could not clean up file %s", file.getAbsolutePath());
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void remove(String string2) {
        synchronized (this) {
            boolean bl2 = this.getFileForKey(string2).delete();
            this.removeEntry(string2);
            if (!bl2) {
                VolleyLog.d("Could not delete cache entry for key=%s, filename=%s", string2, this.getFilenameForKey(string2));
            }
            return;
        }
    }

    static class CacheHeader {
        public String etag;
        public String key;
        public long lastModified;
        public Map<String, String> responseHeaders;
        public long serverDate;
        public long size;
        public long softTtl;
        public long ttl;

        private CacheHeader() {
        }

        public CacheHeader(String string2, Cache.Entry entry) {
            this.key = string2;
            this.size = entry.data.length;
            this.etag = entry.etag;
            this.serverDate = entry.serverDate;
            this.lastModified = entry.lastModified;
            this.ttl = entry.ttl;
            this.softTtl = entry.softTtl;
            this.responseHeaders = entry.responseHeaders;
        }

        public static CacheHeader readHeader(InputStream inputStream) throws IOException {
            CacheHeader cacheHeader = new CacheHeader();
            if (DiskBasedCache.readInt(inputStream) != 538247942) {
                throw new IOException();
            }
            cacheHeader.key = DiskBasedCache.readString(inputStream);
            cacheHeader.etag = DiskBasedCache.readString(inputStream);
            if (cacheHeader.etag.equals("")) {
                cacheHeader.etag = null;
            }
            cacheHeader.serverDate = DiskBasedCache.readLong(inputStream);
            cacheHeader.lastModified = DiskBasedCache.readLong(inputStream);
            cacheHeader.ttl = DiskBasedCache.readLong(inputStream);
            cacheHeader.softTtl = DiskBasedCache.readLong(inputStream);
            cacheHeader.responseHeaders = DiskBasedCache.readStringStringMap(inputStream);
            return cacheHeader;
        }

        public Cache.Entry toCacheEntry(byte[] byArray) {
            Cache.Entry entry = new Cache.Entry();
            entry.data = byArray;
            entry.etag = this.etag;
            entry.serverDate = this.serverDate;
            entry.lastModified = this.lastModified;
            entry.ttl = this.ttl;
            entry.softTtl = this.softTtl;
            entry.responseHeaders = this.responseHeaders;
            return entry;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public boolean writeHeader(OutputStream outputStream) {
            try {
                DiskBasedCache.writeInt(outputStream, 538247942);
                DiskBasedCache.writeString(outputStream, this.key);
                String string2 = this.etag == null ? "" : this.etag;
                DiskBasedCache.writeString(outputStream, string2);
                DiskBasedCache.writeLong(outputStream, this.serverDate);
                DiskBasedCache.writeLong(outputStream, this.lastModified);
                DiskBasedCache.writeLong(outputStream, this.ttl);
                DiskBasedCache.writeLong(outputStream, this.softTtl);
                DiskBasedCache.writeStringStringMap(this.responseHeaders, outputStream);
                outputStream.flush();
                return true;
            }
            catch (IOException iOException) {
                VolleyLog.d("%s", iOException.toString());
                return false;
            }
        }
    }

    private static class CountingInputStream
    extends FilterInputStream {
        private int bytesRead = 0;

        private CountingInputStream(InputStream inputStream) {
            super(inputStream);
        }

        static /* synthetic */ int access$100(CountingInputStream countingInputStream) {
            return countingInputStream.bytesRead;
        }

        @Override
        public int read() throws IOException {
            int n2 = super.read();
            if (n2 != -1) {
                ++this.bytesRead;
            }
            return n2;
        }

        @Override
        public int read(byte[] byArray, int n2, int n3) throws IOException {
            if ((n2 = super.read(byArray, n2, n3)) != -1) {
                this.bytesRead += n2;
            }
            return n2;
        }
    }
}

