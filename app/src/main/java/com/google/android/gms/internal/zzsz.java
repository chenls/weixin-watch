/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzsm;
import com.google.android.gms.internal.zzsn;
import com.google.android.gms.internal.zzso;
import com.google.android.gms.internal.zzss;
import com.google.android.gms.internal.zzsu;
import com.google.android.gms.internal.zzsx;
import java.io.IOException;
import java.util.Arrays;

public interface zzsz {

    public static final class zza
    extends zzso<zza> {
        public String[] zzbuI;
        public String[] zzbuJ;
        public int[] zzbuK;
        public long[] zzbuL;

        public zza() {
            this.zzJC();
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
            if (!(object instanceof zza)) return bl3;
            object = (zza)object;
            bl3 = bl2;
            if (!zzss.equals(this.zzbuI, ((zza)object).zzbuI)) return bl3;
            bl3 = bl2;
            if (!zzss.equals(this.zzbuJ, ((zza)object).zzbuJ)) return bl3;
            bl3 = bl2;
            if (!zzss.equals(this.zzbuK, ((zza)object).zzbuK)) return bl3;
            bl3 = bl2;
            if (!zzss.equals(this.zzbuL, ((zza)object).zzbuL)) return bl3;
            if (this.zzbuj != null) {
                if (!this.zzbuj.isEmpty()) return this.zzbuj.equals(((zza)object).zzbuj);
            }
            if (((zza)object).zzbuj == null) return true;
            bl3 = bl2;
            if (!((zza)object).zzbuj.isEmpty()) return bl3;
            return true;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public int hashCode() {
            int n2;
            int n3 = this.getClass().getName().hashCode();
            int n4 = zzss.hashCode(this.zzbuI);
            int n5 = zzss.hashCode(this.zzbuJ);
            int n6 = zzss.hashCode(this.zzbuK);
            int n7 = zzss.hashCode(this.zzbuL);
            if (this.zzbuj == null || this.zzbuj.isEmpty()) {
                n2 = 0;
                return n2 + (((((n3 + 527) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31;
            }
            n2 = this.zzbuj.hashCode();
            return n2 + (((((n3 + 527) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31;
        }

        @Override
        public /* synthetic */ zzsu mergeFrom(zzsm zzsm2) throws IOException {
            return this.zzS(zzsm2);
        }

        @Override
        public void writeTo(zzsn zzsn2) throws IOException {
            String string2;
            int n2;
            int n3 = 0;
            if (this.zzbuI != null && this.zzbuI.length > 0) {
                for (n2 = 0; n2 < this.zzbuI.length; ++n2) {
                    string2 = this.zzbuI[n2];
                    if (string2 == null) continue;
                    zzsn2.zzn(1, string2);
                }
            }
            if (this.zzbuJ != null && this.zzbuJ.length > 0) {
                for (n2 = 0; n2 < this.zzbuJ.length; ++n2) {
                    string2 = this.zzbuJ[n2];
                    if (string2 == null) continue;
                    zzsn2.zzn(2, string2);
                }
            }
            if (this.zzbuK != null && this.zzbuK.length > 0) {
                for (n2 = 0; n2 < this.zzbuK.length; ++n2) {
                    zzsn2.zzA(3, this.zzbuK[n2]);
                }
            }
            if (this.zzbuL != null && this.zzbuL.length > 0) {
                for (n2 = n3; n2 < this.zzbuL.length; ++n2) {
                    zzsn2.zzb(4, this.zzbuL[n2]);
                }
            }
            super.writeTo(zzsn2);
        }

        public zza zzJC() {
            this.zzbuI = zzsx.zzbuB;
            this.zzbuJ = zzsx.zzbuB;
            this.zzbuK = zzsx.zzbuw;
            this.zzbuL = zzsx.zzbux;
            this.zzbuj = null;
            this.zzbuu = -1;
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public zza zzS(zzsm zzsm2) throws IOException {
            block9: while (true) {
                int n2;
                int n3;
                int n4 = zzsm2.zzIX();
                switch (n4) {
                    default: {
                        if (this.zza(zzsm2, n4)) continue block9;
                    }
                    case 0: {
                        return this;
                    }
                    case 10: {
                        n3 = zzsx.zzc(zzsm2, 10);
                        n4 = this.zzbuI == null ? 0 : this.zzbuI.length;
                        String[] stringArray = new String[n3 + n4];
                        n3 = n4;
                        if (n4 != 0) {
                            System.arraycopy(this.zzbuI, 0, stringArray, 0, n4);
                            n3 = n4;
                        }
                        while (n3 < stringArray.length - 1) {
                            stringArray[n3] = zzsm2.readString();
                            zzsm2.zzIX();
                            ++n3;
                        }
                        stringArray[n3] = zzsm2.readString();
                        this.zzbuI = stringArray;
                        continue block9;
                    }
                    case 18: {
                        n3 = zzsx.zzc(zzsm2, 18);
                        n4 = this.zzbuJ == null ? 0 : this.zzbuJ.length;
                        String[] stringArray = new String[n3 + n4];
                        n3 = n4;
                        if (n4 != 0) {
                            System.arraycopy(this.zzbuJ, 0, stringArray, 0, n4);
                            n3 = n4;
                        }
                        while (n3 < stringArray.length - 1) {
                            stringArray[n3] = zzsm2.readString();
                            zzsm2.zzIX();
                            ++n3;
                        }
                        stringArray[n3] = zzsm2.readString();
                        this.zzbuJ = stringArray;
                        continue block9;
                    }
                    case 24: {
                        n3 = zzsx.zzc(zzsm2, 24);
                        n4 = this.zzbuK == null ? 0 : this.zzbuK.length;
                        int[] nArray = new int[n3 + n4];
                        n3 = n4;
                        if (n4 != 0) {
                            System.arraycopy(this.zzbuK, 0, nArray, 0, n4);
                            n3 = n4;
                        }
                        while (n3 < nArray.length - 1) {
                            nArray[n3] = zzsm2.zzJb();
                            zzsm2.zzIX();
                            ++n3;
                        }
                        nArray[n3] = zzsm2.zzJb();
                        this.zzbuK = nArray;
                        continue block9;
                    }
                    case 26: {
                        n2 = zzsm2.zzmq(zzsm2.zzJf());
                        n4 = zzsm2.getPosition();
                        n3 = 0;
                        while (zzsm2.zzJk() > 0) {
                            zzsm2.zzJb();
                            ++n3;
                        }
                        zzsm2.zzms(n4);
                        n4 = this.zzbuK == null ? 0 : this.zzbuK.length;
                        int[] nArray = new int[n3 + n4];
                        n3 = n4;
                        if (n4 != 0) {
                            System.arraycopy(this.zzbuK, 0, nArray, 0, n4);
                            n3 = n4;
                        }
                        while (n3 < nArray.length) {
                            nArray[n3] = zzsm2.zzJb();
                            ++n3;
                        }
                        this.zzbuK = nArray;
                        zzsm2.zzmr(n2);
                        continue block9;
                    }
                    case 32: {
                        n3 = zzsx.zzc(zzsm2, 32);
                        n4 = this.zzbuL == null ? 0 : this.zzbuL.length;
                        long[] lArray = new long[n3 + n4];
                        n3 = n4;
                        if (n4 != 0) {
                            System.arraycopy(this.zzbuL, 0, lArray, 0, n4);
                            n3 = n4;
                        }
                        while (n3 < lArray.length - 1) {
                            lArray[n3] = zzsm2.zzJa();
                            zzsm2.zzIX();
                            ++n3;
                        }
                        lArray[n3] = zzsm2.zzJa();
                        this.zzbuL = lArray;
                        continue block9;
                    }
                    case 34: 
                }
                n2 = zzsm2.zzmq(zzsm2.zzJf());
                n4 = zzsm2.getPosition();
                n3 = 0;
                while (zzsm2.zzJk() > 0) {
                    zzsm2.zzJa();
                    ++n3;
                }
                zzsm2.zzms(n4);
                n4 = this.zzbuL == null ? 0 : this.zzbuL.length;
                long[] lArray = new long[n3 + n4];
                n3 = n4;
                if (n4 != 0) {
                    System.arraycopy(this.zzbuL, 0, lArray, 0, n4);
                    n3 = n4;
                }
                while (n3 < lArray.length) {
                    lArray[n3] = zzsm2.zzJa();
                    ++n3;
                }
                this.zzbuL = lArray;
                zzsm2.zzmr(n2);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        protected int zzz() {
            int n2;
            int n3;
            String string2;
            int n4;
            int n5;
            int n6;
            int n7 = 0;
            int n8 = super.zzz();
            if (this.zzbuI != null && this.zzbuI.length > 0) {
                n6 = 0;
                n5 = 0;
                for (n4 = 0; n4 < this.zzbuI.length; ++n4) {
                    string2 = this.zzbuI[n4];
                    n3 = n6;
                    n2 = n5;
                    if (string2 != null) {
                        n2 = n5 + 1;
                        n3 = n6 + zzsn.zzgO(string2);
                    }
                    n6 = n3;
                    n5 = n2;
                }
                n4 = n8 + n6 + n5 * 1;
            } else {
                n4 = n8;
            }
            n6 = n4;
            if (this.zzbuJ != null) {
                n6 = n4;
                if (this.zzbuJ.length > 0) {
                    n5 = 0;
                    n2 = 0;
                    for (n6 = 0; n6 < this.zzbuJ.length; ++n6) {
                        string2 = this.zzbuJ[n6];
                        n8 = n5;
                        n3 = n2;
                        if (string2 != null) {
                            n3 = n2 + 1;
                            n8 = n5 + zzsn.zzgO(string2);
                        }
                        n5 = n8;
                        n2 = n3;
                    }
                    n6 = n4 + n5 + n2 * 1;
                }
            }
            n4 = n6;
            if (this.zzbuK != null) {
                n4 = n6;
                if (this.zzbuK.length > 0) {
                    n5 = 0;
                    for (n4 = 0; n4 < this.zzbuK.length; n5 += zzsn.zzmx(this.zzbuK[n4]), ++n4) {
                    }
                    n4 = n6 + n5 + this.zzbuK.length * 1;
                }
            }
            n6 = n4;
            if (this.zzbuL == null) return n6;
            n6 = n4;
            if (this.zzbuL.length <= 0) return n6;
            n5 = 0;
            n6 = n7;
            while (n6 < this.zzbuL.length) {
                n5 += zzsn.zzas(this.zzbuL[n6]);
                ++n6;
            }
            return n4 + n5 + this.zzbuL.length * 1;
        }
    }

    public static final class zzb
    extends zzso<zzb> {
        public String version;
        public int zzbuM;
        public String zzbuN;

        public zzb() {
            this.zzJD();
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
            if (!(object instanceof zzb)) return bl3;
            object = (zzb)object;
            bl3 = bl2;
            if (this.zzbuM != ((zzb)object).zzbuM) return bl3;
            if (this.zzbuN == null) {
                bl3 = bl2;
                if (((zzb)object).zzbuN != null) return bl3;
            } else if (!this.zzbuN.equals(((zzb)object).zzbuN)) {
                return false;
            }
            if (this.version == null) {
                bl3 = bl2;
                if (((zzb)object).version != null) return bl3;
            } else if (!this.version.equals(((zzb)object).version)) {
                return false;
            }
            if (this.zzbuj != null && !this.zzbuj.isEmpty()) {
                return this.zzbuj.equals(((zzb)object).zzbuj);
            }
            if (((zzb)object).zzbuj == null) return true;
            bl3 = bl2;
            if (!((zzb)object).zzbuj.isEmpty()) return bl3;
            return true;
        }

        /*
         * Enabled aggressive block sorting
         */
        public int hashCode() {
            int n2 = 0;
            int n3 = this.getClass().getName().hashCode();
            int n4 = this.zzbuM;
            int n5 = this.zzbuN == null ? 0 : this.zzbuN.hashCode();
            int n6 = this.version == null ? 0 : this.version.hashCode();
            int n7 = n2;
            if (this.zzbuj == null) return (n6 + (n5 + ((n3 + 527) * 31 + n4) * 31) * 31) * 31 + n7;
            if (this.zzbuj.isEmpty()) {
                n7 = n2;
                return (n6 + (n5 + ((n3 + 527) * 31 + n4) * 31) * 31) * 31 + n7;
            }
            n7 = this.zzbuj.hashCode();
            return (n6 + (n5 + ((n3 + 527) * 31 + n4) * 31) * 31) * 31 + n7;
        }

        @Override
        public /* synthetic */ zzsu mergeFrom(zzsm zzsm2) throws IOException {
            return this.zzT(zzsm2);
        }

        @Override
        public void writeTo(zzsn zzsn2) throws IOException {
            if (this.zzbuM != 0) {
                zzsn2.zzA(1, this.zzbuM);
            }
            if (!this.zzbuN.equals("")) {
                zzsn2.zzn(2, this.zzbuN);
            }
            if (!this.version.equals("")) {
                zzsn2.zzn(3, this.version);
            }
            super.writeTo(zzsn2);
        }

        public zzb zzJD() {
            this.zzbuM = 0;
            this.zzbuN = "";
            this.version = "";
            this.zzbuj = null;
            this.zzbuu = -1;
            return this;
        }

        public zzb zzT(zzsm zzsm2) throws IOException {
            block9: while (true) {
                int n2 = zzsm2.zzIX();
                switch (n2) {
                    default: {
                        if (this.zza(zzsm2, n2)) continue block9;
                    }
                    case 0: {
                        return this;
                    }
                    case 8: {
                        n2 = zzsm2.zzJb();
                        switch (n2) {
                            default: {
                                continue block9;
                            }
                            case 0: 
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
                            case 16: 
                            case 17: 
                            case 18: 
                            case 19: 
                            case 20: 
                            case 21: 
                            case 22: 
                            case 23: 
                            case 24: 
                            case 25: 
                            case 26: 
                        }
                        this.zzbuM = n2;
                        continue block9;
                    }
                    case 18: {
                        this.zzbuN = zzsm2.readString();
                        continue block9;
                    }
                    case 26: 
                }
                this.version = zzsm2.readString();
            }
        }

        @Override
        protected int zzz() {
            int n2;
            int n3 = n2 = super.zzz();
            if (this.zzbuM != 0) {
                n3 = n2 + zzsn.zzC(1, this.zzbuM);
            }
            n2 = n3;
            if (!this.zzbuN.equals("")) {
                n2 = n3 + zzsn.zzo(2, this.zzbuN);
            }
            n3 = n2;
            if (!this.version.equals("")) {
                n3 = n2 + zzsn.zzo(3, this.version);
            }
            return n3;
        }
    }

    public static final class zzc
    extends zzso<zzc> {
        public byte[] zzbuO;
        public byte[][] zzbuP;
        public boolean zzbuQ;

        public zzc() {
            this.zzJE();
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
            if (!(object instanceof zzc)) return bl3;
            object = (zzc)object;
            bl3 = bl2;
            if (!Arrays.equals(this.zzbuO, ((zzc)object).zzbuO)) return bl3;
            bl3 = bl2;
            if (!zzss.zza(this.zzbuP, ((zzc)object).zzbuP)) return bl3;
            bl3 = bl2;
            if (this.zzbuQ != ((zzc)object).zzbuQ) return bl3;
            if (this.zzbuj != null) {
                if (!this.zzbuj.isEmpty()) return this.zzbuj.equals(((zzc)object).zzbuj);
            }
            if (((zzc)object).zzbuj == null) return true;
            bl3 = bl2;
            if (!((zzc)object).zzbuj.isEmpty()) return bl3;
            return true;
        }

        /*
         * Enabled aggressive block sorting
         */
        public int hashCode() {
            int n2;
            int n3 = this.getClass().getName().hashCode();
            int n4 = Arrays.hashCode(this.zzbuO);
            int n5 = zzss.zza(this.zzbuP);
            int n6 = this.zzbuQ ? 1231 : 1237;
            if (this.zzbuj != null && !this.zzbuj.isEmpty()) {
                n2 = this.zzbuj.hashCode();
                return n2 + (n6 + (((n3 + 527) * 31 + n4) * 31 + n5) * 31) * 31;
            }
            n2 = 0;
            return n2 + (n6 + (((n3 + 527) * 31 + n4) * 31 + n5) * 31) * 31;
        }

        @Override
        public /* synthetic */ zzsu mergeFrom(zzsm zzsm2) throws IOException {
            return this.zzU(zzsm2);
        }

        @Override
        public void writeTo(zzsn zzsn2) throws IOException {
            if (!Arrays.equals(this.zzbuO, zzsx.zzbuD)) {
                zzsn2.zza(1, this.zzbuO);
            }
            if (this.zzbuP != null && this.zzbuP.length > 0) {
                for (int i2 = 0; i2 < this.zzbuP.length; ++i2) {
                    byte[] byArray = this.zzbuP[i2];
                    if (byArray == null) continue;
                    zzsn2.zza(2, byArray);
                }
            }
            if (this.zzbuQ) {
                zzsn2.zze(3, this.zzbuQ);
            }
            super.writeTo(zzsn2);
        }

        public zzc zzJE() {
            this.zzbuO = zzsx.zzbuD;
            this.zzbuP = zzsx.zzbuC;
            this.zzbuQ = false;
            this.zzbuj = null;
            this.zzbuu = -1;
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public zzc zzU(zzsm zzsm2) throws IOException {
            block6: while (true) {
                int n2 = zzsm2.zzIX();
                switch (n2) {
                    default: {
                        if (this.zza(zzsm2, n2)) continue block6;
                    }
                    case 0: {
                        return this;
                    }
                    case 10: {
                        this.zzbuO = zzsm2.readBytes();
                        continue block6;
                    }
                    case 18: {
                        int n3 = zzsx.zzc(zzsm2, 18);
                        n2 = this.zzbuP == null ? 0 : this.zzbuP.length;
                        byte[][] byArrayArray = new byte[n3 + n2][];
                        n3 = n2;
                        if (n2 != 0) {
                            System.arraycopy(this.zzbuP, 0, byArrayArray, 0, n2);
                            n3 = n2;
                        }
                        while (n3 < byArrayArray.length - 1) {
                            byArrayArray[n3] = zzsm2.readBytes();
                            zzsm2.zzIX();
                            ++n3;
                        }
                        byArrayArray[n3] = zzsm2.readBytes();
                        this.zzbuP = byArrayArray;
                        continue block6;
                    }
                    case 24: 
                }
                this.zzbuQ = zzsm2.zzJc();
            }
        }

        @Override
        protected int zzz() {
            int n2;
            int n3 = 0;
            int n4 = n2 = super.zzz();
            if (!Arrays.equals(this.zzbuO, zzsx.zzbuD)) {
                n4 = n2 + zzsn.zzb(1, this.zzbuO);
            }
            n2 = n4;
            if (this.zzbuP != null) {
                n2 = n4;
                if (this.zzbuP.length > 0) {
                    int n5 = 0;
                    int n6 = 0;
                    for (n2 = n3; n2 < this.zzbuP.length; ++n2) {
                        byte[] byArray = this.zzbuP[n2];
                        int n7 = n5;
                        n3 = n6;
                        if (byArray != null) {
                            n3 = n6 + 1;
                            n7 = n5 + zzsn.zzG(byArray);
                        }
                        n5 = n7;
                        n6 = n3;
                    }
                    n2 = n4 + n5 + n6 * 1;
                }
            }
            n4 = n2;
            if (this.zzbuQ) {
                n4 = n2 + zzsn.zzf(3, this.zzbuQ);
            }
            return n4;
        }
    }

    public static final class zzd
    extends zzso<zzd> {
        public String tag;
        public long zzbuR;
        public long zzbuS;
        public long zzbuT;
        public int zzbuU;
        public boolean zzbuV;
        public zze[] zzbuW;
        public zzb zzbuX;
        public byte[] zzbuY;
        public byte[] zzbuZ;
        public byte[] zzbva;
        public zza zzbvb;
        public String zzbvc;
        public long zzbvd;
        public zzc zzbve;
        public byte[] zzbvf;
        public int zzbvg;
        public int[] zzbvh;
        public long zzbvi;
        public int zzob;

        public zzd() {
            this.zzJF();
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
            if (!(object instanceof zzd)) return bl3;
            object = (zzd)object;
            bl3 = bl2;
            if (this.zzbuR != ((zzd)object).zzbuR) return bl3;
            bl3 = bl2;
            if (this.zzbuS != ((zzd)object).zzbuS) return bl3;
            bl3 = bl2;
            if (this.zzbuT != ((zzd)object).zzbuT) return bl3;
            if (this.tag == null) {
                bl3 = bl2;
                if (((zzd)object).tag != null) return bl3;
            } else if (!this.tag.equals(((zzd)object).tag)) {
                return false;
            }
            bl3 = bl2;
            if (this.zzbuU != ((zzd)object).zzbuU) return bl3;
            bl3 = bl2;
            if (this.zzob != ((zzd)object).zzob) return bl3;
            bl3 = bl2;
            if (this.zzbuV != ((zzd)object).zzbuV) return bl3;
            bl3 = bl2;
            if (!zzss.equals(this.zzbuW, ((zzd)object).zzbuW)) return bl3;
            if (this.zzbuX == null) {
                bl3 = bl2;
                if (((zzd)object).zzbuX != null) return bl3;
            } else if (!this.zzbuX.equals(((zzd)object).zzbuX)) {
                return false;
            }
            bl3 = bl2;
            if (!Arrays.equals(this.zzbuY, ((zzd)object).zzbuY)) return bl3;
            bl3 = bl2;
            if (!Arrays.equals(this.zzbuZ, ((zzd)object).zzbuZ)) return bl3;
            bl3 = bl2;
            if (!Arrays.equals(this.zzbva, ((zzd)object).zzbva)) return bl3;
            if (this.zzbvb == null) {
                bl3 = bl2;
                if (((zzd)object).zzbvb != null) return bl3;
            } else if (!this.zzbvb.equals(((zzd)object).zzbvb)) {
                return false;
            }
            if (this.zzbvc == null) {
                bl3 = bl2;
                if (((zzd)object).zzbvc != null) return bl3;
            } else if (!this.zzbvc.equals(((zzd)object).zzbvc)) {
                return false;
            }
            bl3 = bl2;
            if (this.zzbvd != ((zzd)object).zzbvd) return bl3;
            if (this.zzbve == null) {
                bl3 = bl2;
                if (((zzd)object).zzbve != null) return bl3;
            } else if (!this.zzbve.equals(((zzd)object).zzbve)) {
                return false;
            }
            bl3 = bl2;
            if (!Arrays.equals(this.zzbvf, ((zzd)object).zzbvf)) return bl3;
            bl3 = bl2;
            if (this.zzbvg != ((zzd)object).zzbvg) return bl3;
            bl3 = bl2;
            if (!zzss.equals(this.zzbvh, ((zzd)object).zzbvh)) return bl3;
            bl3 = bl2;
            if (this.zzbvi != ((zzd)object).zzbvi) return bl3;
            if (this.zzbuj != null && !this.zzbuj.isEmpty()) {
                return this.zzbuj.equals(((zzd)object).zzbuj);
            }
            if (((zzd)object).zzbuj == null) return true;
            bl3 = bl2;
            if (!((zzd)object).zzbuj.isEmpty()) return bl3;
            return true;
        }

        /*
         * Enabled aggressive block sorting
         */
        public int hashCode() {
            int n2 = 0;
            int n3 = this.getClass().getName().hashCode();
            int n4 = (int)(this.zzbuR ^ this.zzbuR >>> 32);
            int n5 = (int)(this.zzbuS ^ this.zzbuS >>> 32);
            int n6 = (int)(this.zzbuT ^ this.zzbuT >>> 32);
            int n7 = this.tag == null ? 0 : this.tag.hashCode();
            int n8 = this.zzbuU;
            int n9 = this.zzob;
            int n10 = this.zzbuV ? 1231 : 1237;
            int n11 = zzss.hashCode(this.zzbuW);
            int n12 = this.zzbuX == null ? 0 : this.zzbuX.hashCode();
            int n13 = Arrays.hashCode(this.zzbuY);
            int n14 = Arrays.hashCode(this.zzbuZ);
            int n15 = Arrays.hashCode(this.zzbva);
            int n16 = this.zzbvb == null ? 0 : this.zzbvb.hashCode();
            int n17 = this.zzbvc == null ? 0 : this.zzbvc.hashCode();
            int n18 = (int)(this.zzbvd ^ this.zzbvd >>> 32);
            int n19 = this.zzbve == null ? 0 : this.zzbve.hashCode();
            int n20 = Arrays.hashCode(this.zzbvf);
            int n21 = this.zzbvg;
            int n22 = zzss.hashCode(this.zzbvh);
            int n23 = (int)(this.zzbvi ^ this.zzbvi >>> 32);
            int n24 = n2;
            if (this.zzbuj == null) return (((((n19 + ((n17 + (n16 + ((((n12 + ((n10 + (((n7 + ((((n3 + 527) * 31 + n4) * 31 + n5) * 31 + n6) * 31) * 31 + n8) * 31 + n9) * 31) * 31 + n11) * 31) * 31 + n13) * 31 + n14) * 31 + n15) * 31) * 31) * 31 + n18) * 31) * 31 + n20) * 31 + n21) * 31 + n22) * 31 + n23) * 31 + n24;
            if (this.zzbuj.isEmpty()) {
                n24 = n2;
                return (((((n19 + ((n17 + (n16 + ((((n12 + ((n10 + (((n7 + ((((n3 + 527) * 31 + n4) * 31 + n5) * 31 + n6) * 31) * 31 + n8) * 31 + n9) * 31) * 31 + n11) * 31) * 31 + n13) * 31 + n14) * 31 + n15) * 31) * 31) * 31 + n18) * 31) * 31 + n20) * 31 + n21) * 31 + n22) * 31 + n23) * 31 + n24;
            }
            n24 = this.zzbuj.hashCode();
            return (((((n19 + ((n17 + (n16 + ((((n12 + ((n10 + (((n7 + ((((n3 + 527) * 31 + n4) * 31 + n5) * 31 + n6) * 31) * 31 + n8) * 31 + n9) * 31) * 31 + n11) * 31) * 31 + n13) * 31 + n14) * 31 + n15) * 31) * 31) * 31 + n18) * 31) * 31 + n20) * 31 + n21) * 31 + n22) * 31 + n23) * 31 + n24;
        }

        @Override
        public /* synthetic */ zzsu mergeFrom(zzsm zzsm2) throws IOException {
            return this.zzV(zzsm2);
        }

        @Override
        public void writeTo(zzsn zzsn2) throws IOException {
            int n2;
            int n3 = 0;
            if (this.zzbuR != 0L) {
                zzsn2.zzb(1, this.zzbuR);
            }
            if (!this.tag.equals("")) {
                zzsn2.zzn(2, this.tag);
            }
            if (this.zzbuW != null && this.zzbuW.length > 0) {
                for (n2 = 0; n2 < this.zzbuW.length; ++n2) {
                    zze zze2 = this.zzbuW[n2];
                    if (zze2 == null) continue;
                    zzsn2.zza(3, zze2);
                }
            }
            if (!Arrays.equals(this.zzbuY, zzsx.zzbuD)) {
                zzsn2.zza(6, this.zzbuY);
            }
            if (this.zzbvb != null) {
                zzsn2.zza(7, this.zzbvb);
            }
            if (!Arrays.equals(this.zzbuZ, zzsx.zzbuD)) {
                zzsn2.zza(8, this.zzbuZ);
            }
            if (this.zzbuX != null) {
                zzsn2.zza(9, this.zzbuX);
            }
            if (this.zzbuV) {
                zzsn2.zze(10, this.zzbuV);
            }
            if (this.zzbuU != 0) {
                zzsn2.zzA(11, this.zzbuU);
            }
            if (this.zzob != 0) {
                zzsn2.zzA(12, this.zzob);
            }
            if (!Arrays.equals(this.zzbva, zzsx.zzbuD)) {
                zzsn2.zza(13, this.zzbva);
            }
            if (!this.zzbvc.equals("")) {
                zzsn2.zzn(14, this.zzbvc);
            }
            if (this.zzbvd != 180000L) {
                zzsn2.zzc(15, this.zzbvd);
            }
            if (this.zzbve != null) {
                zzsn2.zza(16, this.zzbve);
            }
            if (this.zzbuS != 0L) {
                zzsn2.zzb(17, this.zzbuS);
            }
            if (!Arrays.equals(this.zzbvf, zzsx.zzbuD)) {
                zzsn2.zza(18, this.zzbvf);
            }
            if (this.zzbvg != 0) {
                zzsn2.zzA(19, this.zzbvg);
            }
            if (this.zzbvh != null && this.zzbvh.length > 0) {
                for (n2 = n3; n2 < this.zzbvh.length; ++n2) {
                    zzsn2.zzA(20, this.zzbvh[n2]);
                }
            }
            if (this.zzbuT != 0L) {
                zzsn2.zzb(21, this.zzbuT);
            }
            if (this.zzbvi != 0L) {
                zzsn2.zzb(22, this.zzbvi);
            }
            super.writeTo(zzsn2);
        }

        public zzd zzJF() {
            this.zzbuR = 0L;
            this.zzbuS = 0L;
            this.zzbuT = 0L;
            this.tag = "";
            this.zzbuU = 0;
            this.zzob = 0;
            this.zzbuV = false;
            this.zzbuW = zze.zzJG();
            this.zzbuX = null;
            this.zzbuY = zzsx.zzbuD;
            this.zzbuZ = zzsx.zzbuD;
            this.zzbva = zzsx.zzbuD;
            this.zzbvb = null;
            this.zzbvc = "";
            this.zzbvd = 180000L;
            this.zzbve = null;
            this.zzbvf = zzsx.zzbuD;
            this.zzbvg = 0;
            this.zzbvh = zzsx.zzbuw;
            this.zzbvi = 0L;
            this.zzbuj = null;
            this.zzbuu = -1;
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public zzd zzV(zzsm zzsm2) throws IOException {
            block27: while (true) {
                int n2 = zzsm2.zzIX();
                switch (n2) {
                    default: {
                        if (this.zza(zzsm2, n2)) continue block27;
                    }
                    case 0: {
                        return this;
                    }
                    case 8: {
                        this.zzbuR = zzsm2.zzJa();
                        continue block27;
                    }
                    case 18: {
                        this.tag = zzsm2.readString();
                        continue block27;
                    }
                    case 26: {
                        int n3 = zzsx.zzc(zzsm2, 26);
                        n2 = this.zzbuW == null ? 0 : this.zzbuW.length;
                        zze[] zzeArray = new zze[n3 + n2];
                        n3 = n2;
                        if (n2 != 0) {
                            System.arraycopy(this.zzbuW, 0, zzeArray, 0, n2);
                            n3 = n2;
                        }
                        while (n3 < zzeArray.length - 1) {
                            zzeArray[n3] = new zze();
                            zzsm2.zza(zzeArray[n3]);
                            zzsm2.zzIX();
                            ++n3;
                        }
                        zzeArray[n3] = new zze();
                        zzsm2.zza(zzeArray[n3]);
                        this.zzbuW = zzeArray;
                        continue block27;
                    }
                    case 50: {
                        this.zzbuY = zzsm2.readBytes();
                        continue block27;
                    }
                    case 58: {
                        if (this.zzbvb == null) {
                            this.zzbvb = new zza();
                        }
                        zzsm2.zza(this.zzbvb);
                        continue block27;
                    }
                    case 66: {
                        this.zzbuZ = zzsm2.readBytes();
                        continue block27;
                    }
                    case 74: {
                        if (this.zzbuX == null) {
                            this.zzbuX = new zzb();
                        }
                        zzsm2.zza(this.zzbuX);
                        continue block27;
                    }
                    case 80: {
                        this.zzbuV = zzsm2.zzJc();
                        continue block27;
                    }
                    case 88: {
                        this.zzbuU = zzsm2.zzJb();
                        continue block27;
                    }
                    case 96: {
                        this.zzob = zzsm2.zzJb();
                        continue block27;
                    }
                    case 106: {
                        this.zzbva = zzsm2.readBytes();
                        continue block27;
                    }
                    case 114: {
                        this.zzbvc = zzsm2.readString();
                        continue block27;
                    }
                    case 120: {
                        this.zzbvd = zzsm2.zzJe();
                        continue block27;
                    }
                    case 130: {
                        if (this.zzbve == null) {
                            this.zzbve = new zzc();
                        }
                        zzsm2.zza(this.zzbve);
                        continue block27;
                    }
                    case 136: {
                        this.zzbuS = zzsm2.zzJa();
                        continue block27;
                    }
                    case 146: {
                        this.zzbvf = zzsm2.readBytes();
                        continue block27;
                    }
                    case 152: {
                        n2 = zzsm2.zzJb();
                        switch (n2) {
                            default: {
                                continue block27;
                            }
                            case 0: 
                            case 1: 
                            case 2: 
                        }
                        this.zzbvg = n2;
                        continue block27;
                    }
                    case 160: {
                        int n3 = zzsx.zzc(zzsm2, 160);
                        n2 = this.zzbvh == null ? 0 : this.zzbvh.length;
                        int[] nArray = new int[n3 + n2];
                        n3 = n2;
                        if (n2 != 0) {
                            System.arraycopy(this.zzbvh, 0, nArray, 0, n2);
                            n3 = n2;
                        }
                        while (n3 < nArray.length - 1) {
                            nArray[n3] = zzsm2.zzJb();
                            zzsm2.zzIX();
                            ++n3;
                        }
                        nArray[n3] = zzsm2.zzJb();
                        this.zzbvh = nArray;
                        continue block27;
                    }
                    case 162: {
                        int n4 = zzsm2.zzmq(zzsm2.zzJf());
                        n2 = zzsm2.getPosition();
                        int n3 = 0;
                        while (zzsm2.zzJk() > 0) {
                            zzsm2.zzJb();
                            ++n3;
                        }
                        zzsm2.zzms(n2);
                        n2 = this.zzbvh == null ? 0 : this.zzbvh.length;
                        int[] nArray = new int[n3 + n2];
                        n3 = n2;
                        if (n2 != 0) {
                            System.arraycopy(this.zzbvh, 0, nArray, 0, n2);
                            n3 = n2;
                        }
                        while (n3 < nArray.length) {
                            nArray[n3] = zzsm2.zzJb();
                            ++n3;
                        }
                        this.zzbvh = nArray;
                        zzsm2.zzmr(n4);
                        continue block27;
                    }
                    case 168: {
                        this.zzbuT = zzsm2.zzJa();
                        continue block27;
                    }
                    case 176: 
                }
                this.zzbvi = zzsm2.zzJa();
            }
        }

        @Override
        protected int zzz() {
            int n2;
            int n3;
            int n4 = 0;
            int n5 = n3 = super.zzz();
            if (this.zzbuR != 0L) {
                n5 = n3 + zzsn.zzd(1, this.zzbuR);
            }
            n3 = n5;
            if (!this.tag.equals("")) {
                n3 = n5 + zzsn.zzo(2, this.tag);
            }
            n5 = n3;
            if (this.zzbuW != null) {
                n5 = n3;
                if (this.zzbuW.length > 0) {
                    for (n5 = 0; n5 < this.zzbuW.length; ++n5) {
                        zze zze2 = this.zzbuW[n5];
                        n2 = n3;
                        if (zze2 != null) {
                            n2 = n3 + zzsn.zzc(3, zze2);
                        }
                        n3 = n2;
                    }
                    n5 = n3;
                }
            }
            n3 = n5;
            if (!Arrays.equals(this.zzbuY, zzsx.zzbuD)) {
                n3 = n5 + zzsn.zzb(6, this.zzbuY);
            }
            n5 = n3;
            if (this.zzbvb != null) {
                n5 = n3 + zzsn.zzc(7, this.zzbvb);
            }
            n3 = n5;
            if (!Arrays.equals(this.zzbuZ, zzsx.zzbuD)) {
                n3 = n5 + zzsn.zzb(8, this.zzbuZ);
            }
            n5 = n3;
            if (this.zzbuX != null) {
                n5 = n3 + zzsn.zzc(9, this.zzbuX);
            }
            n3 = n5;
            if (this.zzbuV) {
                n3 = n5 + zzsn.zzf(10, this.zzbuV);
            }
            n5 = n3;
            if (this.zzbuU != 0) {
                n5 = n3 + zzsn.zzC(11, this.zzbuU);
            }
            n3 = n5;
            if (this.zzob != 0) {
                n3 = n5 + zzsn.zzC(12, this.zzob);
            }
            n5 = n3;
            if (!Arrays.equals(this.zzbva, zzsx.zzbuD)) {
                n5 = n3 + zzsn.zzb(13, this.zzbva);
            }
            n3 = n5;
            if (!this.zzbvc.equals("")) {
                n3 = n5 + zzsn.zzo(14, this.zzbvc);
            }
            n5 = n3;
            if (this.zzbvd != 180000L) {
                n5 = n3 + zzsn.zze(15, this.zzbvd);
            }
            n3 = n5;
            if (this.zzbve != null) {
                n3 = n5 + zzsn.zzc(16, this.zzbve);
            }
            n5 = n3;
            if (this.zzbuS != 0L) {
                n5 = n3 + zzsn.zzd(17, this.zzbuS);
            }
            n2 = n5;
            if (!Arrays.equals(this.zzbvf, zzsx.zzbuD)) {
                n2 = n5 + zzsn.zzb(18, this.zzbvf);
            }
            n3 = n2;
            if (this.zzbvg != 0) {
                n3 = n2 + zzsn.zzC(19, this.zzbvg);
            }
            n5 = n3;
            if (this.zzbvh != null) {
                n5 = n3;
                if (this.zzbvh.length > 0) {
                    n2 = 0;
                    for (n5 = n4; n5 < this.zzbvh.length; ++n5) {
                        n2 += zzsn.zzmx(this.zzbvh[n5]);
                    }
                    n5 = n3 + n2 + this.zzbvh.length * 2;
                }
            }
            n3 = n5;
            if (this.zzbuT != 0L) {
                n3 = n5 + zzsn.zzd(21, this.zzbuT);
            }
            n5 = n3;
            if (this.zzbvi != 0L) {
                n5 = n3 + zzsn.zzd(22, this.zzbvi);
            }
            return n5;
        }
    }

    public static final class zze
    extends zzso<zze> {
        private static volatile zze[] zzbvj;
        public String key;
        public String value;

        public zze() {
            this.zzJH();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static zze[] zzJG() {
            if (zzbvj == null) {
                Object object = zzss.zzbut;
                synchronized (object) {
                    if (zzbvj == null) {
                        zzbvj = new zze[0];
                    }
                }
            }
            return zzbvj;
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
            if (!(object instanceof zze)) return bl3;
            object = (zze)object;
            if (this.key == null) {
                bl3 = bl2;
                if (((zze)object).key != null) return bl3;
            } else if (!this.key.equals(((zze)object).key)) {
                return false;
            }
            if (this.value == null) {
                bl3 = bl2;
                if (((zze)object).value != null) return bl3;
            } else if (!this.value.equals(((zze)object).value)) {
                return false;
            }
            if (this.zzbuj != null && !this.zzbuj.isEmpty()) {
                return this.zzbuj.equals(((zze)object).zzbuj);
            }
            if (((zze)object).zzbuj == null) return true;
            bl3 = bl2;
            if (!((zze)object).zzbuj.isEmpty()) return bl3;
            return true;
        }

        /*
         * Enabled aggressive block sorting
         */
        public int hashCode() {
            int n2 = 0;
            int n3 = this.getClass().getName().hashCode();
            int n4 = this.key == null ? 0 : this.key.hashCode();
            int n5 = this.value == null ? 0 : this.value.hashCode();
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
            return this.zzW(zzsm2);
        }

        @Override
        public void writeTo(zzsn zzsn2) throws IOException {
            if (!this.key.equals("")) {
                zzsn2.zzn(1, this.key);
            }
            if (!this.value.equals("")) {
                zzsn2.zzn(2, this.value);
            }
            super.writeTo(zzsn2);
        }

        public zze zzJH() {
            this.key = "";
            this.value = "";
            this.zzbuj = null;
            this.zzbuu = -1;
            return this;
        }

        public zze zzW(zzsm zzsm2) throws IOException {
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
                        this.key = zzsm2.readString();
                        continue block5;
                    }
                    case 18: 
                }
                this.value = zzsm2.readString();
            }
        }

        @Override
        protected int zzz() {
            int n2;
            int n3 = n2 = super.zzz();
            if (!this.key.equals("")) {
                n3 = n2 + zzsn.zzo(1, this.key);
            }
            n2 = n3;
            if (!this.value.equals("")) {
                n2 = n3 + zzsn.zzo(2, this.value);
            }
            return n2;
        }
    }
}

