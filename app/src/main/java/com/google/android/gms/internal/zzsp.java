/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzsm;
import com.google.android.gms.internal.zzsn;
import com.google.android.gms.internal.zzso;
import com.google.android.gms.internal.zzsu;
import com.google.android.gms.internal.zzsw;
import com.google.android.gms.internal.zzsx;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class zzsp<M extends zzso<M>, T> {
    public final int tag;
    protected final int type;
    protected final Class<T> zzbuk;
    protected final boolean zzbul;

    private zzsp(int n2, Class<T> clazz, int n3, boolean bl2) {
        this.type = n2;
        this.zzbuk = clazz;
        this.tag = n3;
        this.zzbul = bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private T zzK(List<zzsw> object) {
        int n2;
        int n3 = 0;
        ArrayList<Object> arrayList = new ArrayList<Object>();
        for (n2 = 0; n2 < object.size(); ++n2) {
            zzsw zzsw2 = (zzsw)object.get(n2);
            if (zzsw2.zzbuv.length == 0) continue;
            this.zza(zzsw2, arrayList);
        }
        int n4 = arrayList.size();
        if (n4 == 0) {
            object = null;
            return (T)object;
        } else {
            T t2 = this.zzbuk.cast(Array.newInstance(this.zzbuk.getComponentType(), n4));
            n2 = n3;
            while (true) {
                object = t2;
                if (n2 >= n4) return (T)object;
                Array.set(t2, n2, arrayList.get(n2));
                ++n2;
            }
        }
    }

    private T zzL(List<zzsw> object) {
        if (object.isEmpty()) {
            return null;
        }
        object = object.get(object.size() - 1);
        return this.zzbuk.cast(this.zzP(zzsm.zzD(((zzsw)object).zzbuv)));
    }

    public static <M extends zzso<M>, T extends zzsu> zzsp<M, T> zza(int n2, Class<T> clazz, long l2) {
        return new zzsp<M, T>(n2, clazz, (int)l2, false);
    }

    final T zzJ(List<zzsw> list) {
        if (list == null) {
            return null;
        }
        if (this.zzbul) {
            return this.zzK(list);
        }
        return this.zzL(list);
    }

    /*
     * Exception decompiling
     */
    protected Object zzP(zzsm var1_1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 1[TRYBLOCK] [6, 7, 8 : 107->127)] java.lang.InstantiationException
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.insertExceptionBlocks(Op02WithProcessedDataAndRefs.java:2289)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:414)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1042)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:929)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:73)
         *     at org.benf.cfr.reader.Main.main(Main.java:49)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    int zzY(Object object) {
        if (this.zzbul) {
            return this.zzZ(object);
        }
        return this.zzaa(object);
    }

    protected int zzZ(Object object) {
        int n2 = 0;
        int n3 = Array.getLength(object);
        for (int i2 = 0; i2 < n3; ++i2) {
            int n4 = n2;
            if (Array.get(object, i2) != null) {
                n4 = n2 + this.zzaa(Array.get(object, i2));
            }
            n2 = n4;
        }
        return n2;
    }

    protected void zza(zzsw zzsw2, List<Object> list) {
        list.add(this.zzP(zzsm.zzD(zzsw2.zzbuv)));
    }

    void zza(Object object, zzsn zzsn2) throws IOException {
        if (this.zzbul) {
            this.zzc(object, zzsn2);
            return;
        }
        this.zzb(object, zzsn2);
    }

    protected int zzaa(Object object) {
        int n2 = zzsx.zzmJ(this.tag);
        switch (this.type) {
            default: {
                throw new IllegalArgumentException("Unknown type " + this.type);
            }
            case 10: {
                return zzsn.zzb(n2, (zzsu)object);
            }
            case 11: 
        }
        return zzsn.zzc(n2, (zzsu)object);
    }

    /*
     * Exception decompiling
     */
    protected void zzb(Object var1_1, zzsn var2_3) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 1[TRYBLOCK] [2 : 76->113)] java.io.IOException
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.insertExceptionBlocks(Op02WithProcessedDataAndRefs.java:2289)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:414)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1042)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:929)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:73)
         *     at org.benf.cfr.reader.Main.main(Main.java:49)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    protected void zzc(Object object, zzsn zzsn2) {
        int n2 = Array.getLength(object);
        for (int i2 = 0; i2 < n2; ++i2) {
            Object object2 = Array.get(object, i2);
            if (object2 == null) continue;
            this.zzb(object2, zzsn2);
        }
    }
}

