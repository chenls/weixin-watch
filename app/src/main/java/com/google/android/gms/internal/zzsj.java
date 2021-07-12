/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzsm;
import com.google.android.gms.internal.zzsn;
import com.google.android.gms.internal.zzso;
import com.google.android.gms.internal.zzss;
import com.google.android.gms.internal.zzst;
import com.google.android.gms.internal.zzsu;
import com.google.android.gms.internal.zzsx;
import java.io.IOException;
import java.util.Arrays;

public final class zzsj
extends zzso<zzsj> {
    public zza[] zzbtA;

    public zzsj() {
        this.zzIQ();
    }

    public static zzsj zzA(byte[] byArray) throws zzst {
        return zzsu.mergeFrom(new zzsj(), byArray);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object == this) {
            return true;
        }
        boolean bl3 = bl2;
        if (!(object instanceof zzsj)) return bl3;
        object = (zzsj)object;
        bl3 = bl2;
        if (!zzss.equals(this.zzbtA, ((zzsj)object).zzbtA)) return bl3;
        if (this.zzbuj != null) {
            if (!this.zzbuj.isEmpty()) return this.zzbuj.equals(((zzsj)object).zzbuj);
        }
        if (((zzsj)object).zzbuj == null) return true;
        bl3 = bl2;
        if (!((zzsj)object).zzbuj.isEmpty()) return bl3;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int hashCode() {
        int n2;
        int n3 = this.getClass().getName().hashCode();
        int n4 = zzss.hashCode(this.zzbtA);
        if (this.zzbuj == null || this.zzbuj.isEmpty()) {
            n2 = 0;
            return n2 + ((n3 + 527) * 31 + n4) * 31;
        }
        n2 = this.zzbuj.hashCode();
        return n2 + ((n3 + 527) * 31 + n4) * 31;
    }

    @Override
    public /* synthetic */ zzsu mergeFrom(zzsm zzsm2) throws IOException {
        return this.zzK(zzsm2);
    }

    @Override
    public void writeTo(zzsn zzsn2) throws IOException {
        if (this.zzbtA != null && this.zzbtA.length > 0) {
            for (int i2 = 0; i2 < this.zzbtA.length; ++i2) {
                zza zza2 = this.zzbtA[i2];
                if (zza2 == null) continue;
                zzsn2.zza(1, zza2);
            }
        }
        super.writeTo(zzsn2);
    }

    public zzsj zzIQ() {
        this.zzbtA = zza.zzIR();
        this.zzbuj = null;
        this.zzbuu = -1;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public zzsj zzK(zzsm zzsm2) throws IOException {
        block4: while (true) {
            int n2 = zzsm2.zzIX();
            switch (n2) {
                default: {
                    if (this.zza(zzsm2, n2)) continue block4;
                }
                case 0: {
                    return this;
                }
                case 10: 
            }
            int n3 = zzsx.zzc(zzsm2, 10);
            n2 = this.zzbtA == null ? 0 : this.zzbtA.length;
            zza[] zzaArray = new zza[n3 + n2];
            n3 = n2;
            if (n2 != 0) {
                System.arraycopy(this.zzbtA, 0, zzaArray, 0, n2);
                n3 = n2;
            }
            while (n3 < zzaArray.length - 1) {
                zzaArray[n3] = new zza();
                zzsm2.zza(zzaArray[n3]);
                zzsm2.zzIX();
                ++n3;
            }
            zzaArray[n3] = new zza();
            zzsm2.zza(zzaArray[n3]);
            this.zzbtA = zzaArray;
        }
    }

    @Override
    protected int zzz() {
        int n2;
        int n3 = n2 = super.zzz();
        if (this.zzbtA != null) {
            n3 = n2;
            if (this.zzbtA.length > 0) {
                int n4 = 0;
                while (true) {
                    n3 = n2;
                    if (n4 >= this.zzbtA.length) break;
                    zza zza2 = this.zzbtA[n4];
                    n3 = n2;
                    if (zza2 != null) {
                        n3 = n2 + zzsn.zzc(1, zza2);
                    }
                    ++n4;
                    n2 = n3;
                }
            }
        }
        return n3;
    }

    public static final class com.google.android.gms.internal.zzsj$zza
    extends zzso<com.google.android.gms.internal.zzsj$zza> {
        private static volatile com.google.android.gms.internal.zzsj$zza[] zzbtB;
        public String name;
        public zza zzbtC;

        public com.google.android.gms.internal.zzsj$zza() {
            this.zzIS();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static com.google.android.gms.internal.zzsj$zza[] zzIR() {
            if (zzbtB == null) {
                Object object = zzss.zzbut;
                synchronized (object) {
                    if (zzbtB == null) {
                        zzbtB = new com.google.android.gms.internal.zzsj$zza[0];
                    }
                }
            }
            return zzbtB;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            boolean bl2 = false;
            if (object == this) {
                return true;
            }
            boolean bl3 = bl2;
            if (!(object instanceof com.google.android.gms.internal.zzsj$zza)) return bl3;
            object = (com.google.android.gms.internal.zzsj$zza)object;
            if (this.name == null) {
                bl3 = bl2;
                if (((com.google.android.gms.internal.zzsj$zza)object).name != null) return bl3;
            } else if (!this.name.equals(((com.google.android.gms.internal.zzsj$zza)object).name)) {
                return false;
            }
            if (this.zzbtC == null) {
                bl3 = bl2;
                if (((com.google.android.gms.internal.zzsj$zza)object).zzbtC != null) return bl3;
            } else if (!this.zzbtC.equals(((com.google.android.gms.internal.zzsj$zza)object).zzbtC)) {
                return false;
            }
            if (this.zzbuj != null && !this.zzbuj.isEmpty()) {
                return this.zzbuj.equals(((com.google.android.gms.internal.zzsj$zza)object).zzbuj);
            }
            if (((com.google.android.gms.internal.zzsj$zza)object).zzbuj == null) return true;
            bl3 = bl2;
            if (!((com.google.android.gms.internal.zzsj$zza)object).zzbuj.isEmpty()) return bl3;
            return true;
        }

        /*
         * Enabled aggressive block sorting
         */
        public int hashCode() {
            int n2 = 0;
            int n3 = this.getClass().getName().hashCode();
            int n4 = this.name == null ? 0 : this.name.hashCode();
            int n5 = this.zzbtC == null ? 0 : this.zzbtC.hashCode();
            int n6 = n2;
            if (this.zzbuj == null) return (n5 + (n4 + (n3 + 527) * 31) * 31) * 31 + n6;
            if (this.zzbuj.isEmpty()) {
                n6 = n2;
                return (n5 + (n4 + (n3 + 527) * 31) * 31) * 31 + n6;
            }
            n6 = this.zzbuj.hashCode();
            return (n5 + (n4 + (n3 + 527) * 31) * 31) * 31 + n6;
        }

        @Override
        public /* synthetic */ zzsu mergeFrom(zzsm zzsm2) throws IOException {
            return this.zzL(zzsm2);
        }

        @Override
        public void writeTo(zzsn zzsn2) throws IOException {
            zzsn2.zzn(1, this.name);
            if (this.zzbtC != null) {
                zzsn2.zza(2, this.zzbtC);
            }
            super.writeTo(zzsn2);
        }

        public com.google.android.gms.internal.zzsj$zza zzIS() {
            this.name = "";
            this.zzbtC = null;
            this.zzbuj = null;
            this.zzbuu = -1;
            return this;
        }

        public com.google.android.gms.internal.zzsj$zza zzL(zzsm zzsm2) throws IOException {
            block5: while (true) {
                int n2 = zzsm2.zzIX();
                switch (n2) {
                    default: {
                        if (this.zza(zzsm2, n2)) continue block5;
                    }
                    case 0: {
                        return this;
                    }
                    case 10: {
                        this.name = zzsm2.readString();
                        continue block5;
                    }
                    case 18: 
                }
                if (this.zzbtC == null) {
                    this.zzbtC = new zza();
                }
                zzsm2.zza(this.zzbtC);
            }
        }

        @Override
        protected int zzz() {
            int n2;
            int n3 = n2 = super.zzz() + zzsn.zzo(1, this.name);
            if (this.zzbtC != null) {
                n3 = n2 + zzsn.zzc(2, this.zzbtC);
            }
            return n3;
        }

        public static final class com.google.android.gms.internal.zzsj$zza$zza
        extends zzso<com.google.android.gms.internal.zzsj$zza$zza> {
            private static volatile com.google.android.gms.internal.zzsj$zza$zza[] zzbtD;
            public int type;
            public zza zzbtE;

            public com.google.android.gms.internal.zzsj$zza$zza() {
                this.zzIU();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static com.google.android.gms.internal.zzsj$zza$zza[] zzIT() {
                if (zzbtD == null) {
                    Object object = zzss.zzbut;
                    synchronized (object) {
                        if (zzbtD == null) {
                            zzbtD = new com.google.android.gms.internal.zzsj$zza$zza[0];
                        }
                    }
                }
                return zzbtD;
            }

            /*
             * Enabled aggressive block sorting
             */
            public boolean equals(Object object) {
                boolean bl2 = false;
                if (object == this) {
                    return true;
                }
                boolean bl3 = bl2;
                if (!(object instanceof com.google.android.gms.internal.zzsj$zza$zza)) return bl3;
                object = (com.google.android.gms.internal.zzsj$zza$zza)object;
                bl3 = bl2;
                if (this.type != ((com.google.android.gms.internal.zzsj$zza$zza)object).type) return bl3;
                if (this.zzbtE == null) {
                    bl3 = bl2;
                    if (((com.google.android.gms.internal.zzsj$zza$zza)object).zzbtE != null) return bl3;
                } else if (!this.zzbtE.equals(((com.google.android.gms.internal.zzsj$zza$zza)object).zzbtE)) {
                    return false;
                }
                if (this.zzbuj != null && !this.zzbuj.isEmpty()) {
                    return this.zzbuj.equals(((com.google.android.gms.internal.zzsj$zza$zza)object).zzbuj);
                }
                if (((com.google.android.gms.internal.zzsj$zza$zza)object).zzbuj == null) return true;
                bl3 = bl2;
                if (!((com.google.android.gms.internal.zzsj$zza$zza)object).zzbuj.isEmpty()) return bl3;
                return true;
            }

            /*
             * Enabled aggressive block sorting
             */
            public int hashCode() {
                int n2 = 0;
                int n3 = this.getClass().getName().hashCode();
                int n4 = this.type;
                int n5 = this.zzbtE == null ? 0 : this.zzbtE.hashCode();
                int n6 = n2;
                if (this.zzbuj == null) return (n5 + ((n3 + 527) * 31 + n4) * 31) * 31 + n6;
                if (this.zzbuj.isEmpty()) {
                    n6 = n2;
                    return (n5 + ((n3 + 527) * 31 + n4) * 31) * 31 + n6;
                }
                n6 = this.zzbuj.hashCode();
                return (n5 + ((n3 + 527) * 31 + n4) * 31) * 31 + n6;
            }

            @Override
            public /* synthetic */ zzsu mergeFrom(zzsm zzsm2) throws IOException {
                return this.zzM(zzsm2);
            }

            @Override
            public void writeTo(zzsn zzsn2) throws IOException {
                zzsn2.zzA(1, this.type);
                if (this.zzbtE != null) {
                    zzsn2.zza(2, this.zzbtE);
                }
                super.writeTo(zzsn2);
            }

            public com.google.android.gms.internal.zzsj$zza$zza zzIU() {
                this.type = 1;
                this.zzbtE = null;
                this.zzbuj = null;
                this.zzbuu = -1;
                return this;
            }

            public com.google.android.gms.internal.zzsj$zza$zza zzM(zzsm zzsm2) throws IOException {
                block8: while (true) {
                    int n2 = zzsm2.zzIX();
                    switch (n2) {
                        default: {
                            if (this.zza(zzsm2, n2)) continue block8;
                        }
                        case 0: {
                            return this;
                        }
                        case 8: {
                            n2 = zzsm2.zzJb();
                            switch (n2) {
                                default: {
                                    continue block8;
                                }
                                case 1: 
                                case 2: 
                                case 3: 
                                case 4: 
                                case 5: 
                                case 6: 
                                case 7: 
                                case 8: 
                                case 9: 
                                case 10: 
                                case 11: 
                                case 12: 
                                case 13: 
                                case 14: 
                                case 15: 
                            }
                            this.type = n2;
                            continue block8;
                        }
                        case 18: 
                    }
                    if (this.zzbtE == null) {
                        this.zzbtE = new zza();
                    }
                    zzsm2.zza(this.zzbtE);
                }
            }

            @Override
            protected int zzz() {
                int n2;
                int n3 = n2 = super.zzz() + zzsn.zzC(1, this.type);
                if (this.zzbtE != null) {
                    n3 = n2 + zzsn.zzc(2, this.zzbtE);
                }
                return n3;
            }

            public static final class zza
            extends zzso<zza> {
                public byte[] zzbtF;
                public String zzbtG;
                public double zzbtH;
                public float zzbtI;
                public long zzbtJ;
                public int zzbtK;
                public int zzbtL;
                public boolean zzbtM;
                public com.google.android.gms.internal.zzsj$zza[] zzbtN;
                public com.google.android.gms.internal.zzsj$zza$zza[] zzbtO;
                public String[] zzbtP;
                public long[] zzbtQ;
                public float[] zzbtR;
                public long zzbtS;

                public zza() {
                    this.zzIV();
                }

                /*
                 * Enabled aggressive block sorting
                 */
                public boolean equals(Object object) {
                    boolean bl2 = false;
                    if (object == this) {
                        return true;
                    }
                    boolean bl3 = bl2;
                    if (!(object instanceof zza)) return bl3;
                    object = (zza)object;
                    bl3 = bl2;
                    if (!Arrays.equals(this.zzbtF, ((zza)object).zzbtF)) return bl3;
                    if (this.zzbtG == null) {
                        bl3 = bl2;
                        if (((zza)object).zzbtG != null) return bl3;
                    } else if (!this.zzbtG.equals(((zza)object).zzbtG)) {
                        return false;
                    }
                    bl3 = bl2;
                    if (Double.doubleToLongBits(this.zzbtH) != Double.doubleToLongBits(((zza)object).zzbtH)) return bl3;
                    bl3 = bl2;
                    if (Float.floatToIntBits(this.zzbtI) != Float.floatToIntBits(((zza)object).zzbtI)) return bl3;
                    bl3 = bl2;
                    if (this.zzbtJ != ((zza)object).zzbtJ) return bl3;
                    bl3 = bl2;
                    if (this.zzbtK != ((zza)object).zzbtK) return bl3;
                    bl3 = bl2;
                    if (this.zzbtL != ((zza)object).zzbtL) return bl3;
                    bl3 = bl2;
                    if (this.zzbtM != ((zza)object).zzbtM) return bl3;
                    bl3 = bl2;
                    if (!zzss.equals(this.zzbtN, ((zza)object).zzbtN)) return bl3;
                    bl3 = bl2;
                    if (!zzss.equals(this.zzbtO, ((zza)object).zzbtO)) return bl3;
                    bl3 = bl2;
                    if (!zzss.equals(this.zzbtP, ((zza)object).zzbtP)) return bl3;
                    bl3 = bl2;
                    if (!zzss.equals(this.zzbtQ, ((zza)object).zzbtQ)) return bl3;
                    bl3 = bl2;
                    if (!zzss.equals(this.zzbtR, ((zza)object).zzbtR)) return bl3;
                    bl3 = bl2;
                    if (this.zzbtS != ((zza)object).zzbtS) return bl3;
                    if (this.zzbuj != null && !this.zzbuj.isEmpty()) {
                        return this.zzbuj.equals(((zza)object).zzbuj);
                    }
                    if (((zza)object).zzbuj == null) return true;
                    bl3 = bl2;
                    if (!((zza)object).zzbuj.isEmpty()) return bl3;
                    return true;
                }

                /*
                 * Enabled aggressive block sorting
                 */
                public int hashCode() {
                    int n2 = 0;
                    int n3 = this.getClass().getName().hashCode();
                    int n4 = Arrays.hashCode(this.zzbtF);
                    int n5 = this.zzbtG == null ? 0 : this.zzbtG.hashCode();
                    long l2 = Double.doubleToLongBits(this.zzbtH);
                    int n6 = (int)(l2 ^ l2 >>> 32);
                    int n7 = Float.floatToIntBits(this.zzbtI);
                    int n8 = (int)(this.zzbtJ ^ this.zzbtJ >>> 32);
                    int n9 = this.zzbtK;
                    int n10 = this.zzbtL;
                    int n11 = this.zzbtM ? 1231 : 1237;
                    int n12 = zzss.hashCode(this.zzbtN);
                    int n13 = zzss.hashCode(this.zzbtO);
                    int n14 = zzss.hashCode(this.zzbtP);
                    int n15 = zzss.hashCode(this.zzbtQ);
                    int n16 = zzss.hashCode(this.zzbtR);
                    int n17 = (int)(this.zzbtS ^ this.zzbtS >>> 32);
                    int n18 = n2;
                    if (this.zzbuj == null) return (((((((n11 + ((((((n5 + ((n3 + 527) * 31 + n4) * 31) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9) * 31 + n10) * 31) * 31 + n12) * 31 + n13) * 31 + n14) * 31 + n15) * 31 + n16) * 31 + n17) * 31 + n18;
                    if (this.zzbuj.isEmpty()) {
                        n18 = n2;
                        return (((((((n11 + ((((((n5 + ((n3 + 527) * 31 + n4) * 31) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9) * 31 + n10) * 31) * 31 + n12) * 31 + n13) * 31 + n14) * 31 + n15) * 31 + n16) * 31 + n17) * 31 + n18;
                    }
                    n18 = this.zzbuj.hashCode();
                    return (((((((n11 + ((((((n5 + ((n3 + 527) * 31 + n4) * 31) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9) * 31 + n10) * 31) * 31 + n12) * 31 + n13) * 31 + n14) * 31 + n15) * 31 + n16) * 31 + n17) * 31 + n18;
                }

                @Override
                public /* synthetic */ zzsu mergeFrom(zzsm zzsm2) throws IOException {
                    return this.zzN(zzsm2);
                }

                @Override
                public void writeTo(zzsn zzsn2) throws IOException {
                    Object object;
                    int n2;
                    int n3 = 0;
                    if (!Arrays.equals(this.zzbtF, zzsx.zzbuD)) {
                        zzsn2.zza(1, this.zzbtF);
                    }
                    if (!this.zzbtG.equals("")) {
                        zzsn2.zzn(2, this.zzbtG);
                    }
                    if (Double.doubleToLongBits(this.zzbtH) != Double.doubleToLongBits(0.0)) {
                        zzsn2.zza(3, this.zzbtH);
                    }
                    if (Float.floatToIntBits(this.zzbtI) != Float.floatToIntBits(0.0f)) {
                        zzsn2.zzb(4, this.zzbtI);
                    }
                    if (this.zzbtJ != 0L) {
                        zzsn2.zzb(5, this.zzbtJ);
                    }
                    if (this.zzbtK != 0) {
                        zzsn2.zzA(6, this.zzbtK);
                    }
                    if (this.zzbtL != 0) {
                        zzsn2.zzB(7, this.zzbtL);
                    }
                    if (this.zzbtM) {
                        zzsn2.zze(8, this.zzbtM);
                    }
                    if (this.zzbtN != null && this.zzbtN.length > 0) {
                        for (n2 = 0; n2 < this.zzbtN.length; ++n2) {
                            object = this.zzbtN[n2];
                            if (object == null) continue;
                            zzsn2.zza(9, (zzsu)object);
                        }
                    }
                    if (this.zzbtO != null && this.zzbtO.length > 0) {
                        for (n2 = 0; n2 < this.zzbtO.length; ++n2) {
                            object = this.zzbtO[n2];
                            if (object == null) continue;
                            zzsn2.zza(10, (zzsu)object);
                        }
                    }
                    if (this.zzbtP != null && this.zzbtP.length > 0) {
                        for (n2 = 0; n2 < this.zzbtP.length; ++n2) {
                            object = this.zzbtP[n2];
                            if (object == null) continue;
                            zzsn2.zzn(11, (String)object);
                        }
                    }
                    if (this.zzbtQ != null && this.zzbtQ.length > 0) {
                        for (n2 = 0; n2 < this.zzbtQ.length; ++n2) {
                            zzsn2.zzb(12, this.zzbtQ[n2]);
                        }
                    }
                    if (this.zzbtS != 0L) {
                        zzsn2.zzb(13, this.zzbtS);
                    }
                    if (this.zzbtR != null && this.zzbtR.length > 0) {
                        for (n2 = n3; n2 < this.zzbtR.length; ++n2) {
                            zzsn2.zzb(14, this.zzbtR[n2]);
                        }
                    }
                    super.writeTo(zzsn2);
                }

                public zza zzIV() {
                    this.zzbtF = zzsx.zzbuD;
                    this.zzbtG = "";
                    this.zzbtH = 0.0;
                    this.zzbtI = 0.0f;
                    this.zzbtJ = 0L;
                    this.zzbtK = 0;
                    this.zzbtL = 0;
                    this.zzbtM = false;
                    this.zzbtN = com.google.android.gms.internal.zzsj$zza.zzIR();
                    this.zzbtO = com.google.android.gms.internal.zzsj$zza$zza.zzIT();
                    this.zzbtP = zzsx.zzbuB;
                    this.zzbtQ = zzsx.zzbux;
                    this.zzbtR = zzsx.zzbuy;
                    this.zzbtS = 0L;
                    this.zzbuj = null;
                    this.zzbuu = -1;
                    return this;
                }

                /*
                 * Enabled aggressive block sorting
                 */
                public zza zzN(zzsm zzsm2) throws IOException {
                    block19: while (true) {
                        int n2;
                        int n3;
                        int n4 = zzsm2.zzIX();
                        switch (n4) {
                            default: {
                                if (this.zza(zzsm2, n4)) continue block19;
                            }
                            case 0: {
                                return this;
                            }
                            case 10: {
                                this.zzbtF = zzsm2.readBytes();
                                continue block19;
                            }
                            case 18: {
                                this.zzbtG = zzsm2.readString();
                                continue block19;
                            }
                            case 25: {
                                this.zzbtH = zzsm2.readDouble();
                                continue block19;
                            }
                            case 37: {
                                this.zzbtI = zzsm2.readFloat();
                                continue block19;
                            }
                            case 40: {
                                this.zzbtJ = zzsm2.zzJa();
                                continue block19;
                            }
                            case 48: {
                                this.zzbtK = zzsm2.zzJb();
                                continue block19;
                            }
                            case 56: {
                                this.zzbtL = zzsm2.zzJd();
                                continue block19;
                            }
                            case 64: {
                                this.zzbtM = zzsm2.zzJc();
                                continue block19;
                            }
                            case 74: {
                                n3 = zzsx.zzc(zzsm2, 74);
                                n4 = this.zzbtN == null ? 0 : this.zzbtN.length;
                                com.google.android.gms.internal.zzsj$zza[] zzaArray = new com.google.android.gms.internal.zzsj$zza[n3 + n4];
                                n3 = n4;
                                if (n4 != 0) {
                                    System.arraycopy(this.zzbtN, 0, zzaArray, 0, n4);
                                    n3 = n4;
                                }
                                while (n3 < zzaArray.length - 1) {
                                    zzaArray[n3] = new com.google.android.gms.internal.zzsj$zza();
                                    zzsm2.zza(zzaArray[n3]);
                                    zzsm2.zzIX();
                                    ++n3;
                                }
                                zzaArray[n3] = new com.google.android.gms.internal.zzsj$zza();
                                zzsm2.zza(zzaArray[n3]);
                                this.zzbtN = zzaArray;
                                continue block19;
                            }
                            case 82: {
                                n3 = zzsx.zzc(zzsm2, 82);
                                n4 = this.zzbtO == null ? 0 : this.zzbtO.length;
                                com.google.android.gms.internal.zzsj$zza$zza[] zzaArray = new com.google.android.gms.internal.zzsj$zza$zza[n3 + n4];
                                n3 = n4;
                                if (n4 != 0) {
                                    System.arraycopy(this.zzbtO, 0, zzaArray, 0, n4);
                                    n3 = n4;
                                }
                                while (n3 < zzaArray.length - 1) {
                                    zzaArray[n3] = new com.google.android.gms.internal.zzsj$zza$zza();
                                    zzsm2.zza(zzaArray[n3]);
                                    zzsm2.zzIX();
                                    ++n3;
                                }
                                zzaArray[n3] = new com.google.android.gms.internal.zzsj$zza$zza();
                                zzsm2.zza(zzaArray[n3]);
                                this.zzbtO = zzaArray;
                                continue block19;
                            }
                            case 90: {
                                n3 = zzsx.zzc(zzsm2, 90);
                                n4 = this.zzbtP == null ? 0 : this.zzbtP.length;
                                String[] stringArray = new String[n3 + n4];
                                n3 = n4;
                                if (n4 != 0) {
                                    System.arraycopy(this.zzbtP, 0, stringArray, 0, n4);
                                    n3 = n4;
                                }
                                while (n3 < stringArray.length - 1) {
                                    stringArray[n3] = zzsm2.readString();
                                    zzsm2.zzIX();
                                    ++n3;
                                }
                                stringArray[n3] = zzsm2.readString();
                                this.zzbtP = stringArray;
                                continue block19;
                            }
                            case 96: {
                                n3 = zzsx.zzc(zzsm2, 96);
                                n4 = this.zzbtQ == null ? 0 : this.zzbtQ.length;
                                long[] lArray = new long[n3 + n4];
                                n3 = n4;
                                if (n4 != 0) {
                                    System.arraycopy(this.zzbtQ, 0, lArray, 0, n4);
                                    n3 = n4;
                                }
                                while (n3 < lArray.length - 1) {
                                    lArray[n3] = zzsm2.zzJa();
                                    zzsm2.zzIX();
                                    ++n3;
                                }
                                lArray[n3] = zzsm2.zzJa();
                                this.zzbtQ = lArray;
                                continue block19;
                            }
                            case 98: {
                                n2 = zzsm2.zzmq(zzsm2.zzJf());
                                n4 = zzsm2.getPosition();
                                n3 = 0;
                                while (zzsm2.zzJk() > 0) {
                                    zzsm2.zzJa();
                                    ++n3;
                                }
                                zzsm2.zzms(n4);
                                n4 = this.zzbtQ == null ? 0 : this.zzbtQ.length;
                                long[] lArray = new long[n3 + n4];
                                n3 = n4;
                                if (n4 != 0) {
                                    System.arraycopy(this.zzbtQ, 0, lArray, 0, n4);
                                    n3 = n4;
                                }
                                while (n3 < lArray.length) {
                                    lArray[n3] = zzsm2.zzJa();
                                    ++n3;
                                }
                                this.zzbtQ = lArray;
                                zzsm2.zzmr(n2);
                                continue block19;
                            }
                            case 104: {
                                this.zzbtS = zzsm2.zzJa();
                                continue block19;
                            }
                            case 117: {
                                n3 = zzsx.zzc(zzsm2, 117);
                                n4 = this.zzbtR == null ? 0 : this.zzbtR.length;
                                float[] fArray = new float[n3 + n4];
                                n3 = n4;
                                if (n4 != 0) {
                                    System.arraycopy(this.zzbtR, 0, fArray, 0, n4);
                                    n3 = n4;
                                }
                                while (n3 < fArray.length - 1) {
                                    fArray[n3] = zzsm2.readFloat();
                                    zzsm2.zzIX();
                                    ++n3;
                                }
                                fArray[n3] = zzsm2.readFloat();
                                this.zzbtR = fArray;
                                continue block19;
                            }
                            case 114: 
                        }
                        n4 = zzsm2.zzJf();
                        n2 = zzsm2.zzmq(n4);
                        n3 = n4 / 4;
                        n4 = this.zzbtR == null ? 0 : this.zzbtR.length;
                        float[] fArray = new float[n3 + n4];
                        n3 = n4;
                        if (n4 != 0) {
                            System.arraycopy(this.zzbtR, 0, fArray, 0, n4);
                            n3 = n4;
                        }
                        while (n3 < fArray.length) {
                            fArray[n3] = zzsm2.readFloat();
                            ++n3;
                        }
                        this.zzbtR = fArray;
                        zzsm2.zzmr(n2);
                    }
                }

                @Override
                protected int zzz() {
                    Object object;
                    int n2;
                    int n3 = 0;
                    int n4 = n2 = super.zzz();
                    if (!Arrays.equals(this.zzbtF, zzsx.zzbuD)) {
                        n4 = n2 + zzsn.zzb(1, this.zzbtF);
                    }
                    n2 = n4;
                    if (!this.zzbtG.equals("")) {
                        n2 = n4 + zzsn.zzo(2, this.zzbtG);
                    }
                    n4 = n2;
                    if (Double.doubleToLongBits(this.zzbtH) != Double.doubleToLongBits(0.0)) {
                        n4 = n2 + zzsn.zzb(3, this.zzbtH);
                    }
                    n2 = n4;
                    if (Float.floatToIntBits(this.zzbtI) != Float.floatToIntBits(0.0f)) {
                        n2 = n4 + zzsn.zzc(4, this.zzbtI);
                    }
                    n4 = n2;
                    if (this.zzbtJ != 0L) {
                        n4 = n2 + zzsn.zzd(5, this.zzbtJ);
                    }
                    n2 = n4;
                    if (this.zzbtK != 0) {
                        n2 = n4 + zzsn.zzC(6, this.zzbtK);
                    }
                    int n5 = n2;
                    if (this.zzbtL != 0) {
                        n5 = n2 + zzsn.zzD(7, this.zzbtL);
                    }
                    n4 = n5;
                    if (this.zzbtM) {
                        n4 = n5 + zzsn.zzf(8, this.zzbtM);
                    }
                    n2 = n4;
                    if (this.zzbtN != null) {
                        n2 = n4;
                        if (this.zzbtN.length > 0) {
                            for (n2 = 0; n2 < this.zzbtN.length; ++n2) {
                                object = this.zzbtN[n2];
                                n5 = n4;
                                if (object != null) {
                                    n5 = n4 + zzsn.zzc(9, (zzsu)object);
                                }
                                n4 = n5;
                            }
                            n2 = n4;
                        }
                    }
                    n4 = n2;
                    if (this.zzbtO != null) {
                        n4 = n2;
                        if (this.zzbtO.length > 0) {
                            n4 = n2;
                            for (n2 = 0; n2 < this.zzbtO.length; ++n2) {
                                object = this.zzbtO[n2];
                                n5 = n4;
                                if (object != null) {
                                    n5 = n4 + zzsn.zzc(10, (zzsu)object);
                                }
                                n4 = n5;
                            }
                        }
                    }
                    n2 = n4;
                    if (this.zzbtP != null) {
                        n2 = n4;
                        if (this.zzbtP.length > 0) {
                            n5 = 0;
                            int n6 = 0;
                            for (n2 = 0; n2 < this.zzbtP.length; ++n2) {
                                object = this.zzbtP[n2];
                                int n7 = n5;
                                int n8 = n6;
                                if (object != null) {
                                    n8 = n6 + 1;
                                    n7 = n5 + zzsn.zzgO((String)object);
                                }
                                n5 = n7;
                                n6 = n8;
                            }
                            n2 = n4 + n5 + n6 * 1;
                        }
                    }
                    n4 = n2;
                    if (this.zzbtQ != null) {
                        n4 = n2;
                        if (this.zzbtQ.length > 0) {
                            n5 = 0;
                            for (n4 = n3; n4 < this.zzbtQ.length; ++n4) {
                                n5 += zzsn.zzas(this.zzbtQ[n4]);
                            }
                            n4 = n2 + n5 + this.zzbtQ.length * 1;
                        }
                    }
                    n2 = n4;
                    if (this.zzbtS != 0L) {
                        n2 = n4 + zzsn.zzd(13, this.zzbtS);
                    }
                    n4 = n2;
                    if (this.zzbtR != null) {
                        n4 = n2;
                        if (this.zzbtR.length > 0) {
                            n4 = n2 + this.zzbtR.length * 4 + this.zzbtR.length * 1;
                        }
                    }
                    return n4;
                }
            }
        }
    }
}

