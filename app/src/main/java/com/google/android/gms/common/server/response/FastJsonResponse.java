/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.common.server.response;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.server.converter.ConverterWrapper;
import com.google.android.gms.common.server.response.FieldMappingDictionary;
import com.google.android.gms.common.server.response.SafeParcelResponse;
import com.google.android.gms.internal.zzmo;
import com.google.android.gms.internal.zznb;
import com.google.android.gms.internal.zznc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class FastJsonResponse {
    private void zza(StringBuilder stringBuilder, Field field, Object object) {
        if (field.zzrj() == 11) {
            stringBuilder.append(field.zzrt().cast(object).toString());
            return;
        }
        if (field.zzrj() == 7) {
            stringBuilder.append("\"");
            stringBuilder.append(zznb.zzcU((String)object));
            stringBuilder.append("\"");
            return;
        }
        stringBuilder.append(object);
    }

    private void zza(StringBuilder stringBuilder, Field field, ArrayList<Object> arrayList) {
        stringBuilder.append("[");
        int n2 = arrayList.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            Object object;
            if (i2 > 0) {
                stringBuilder.append(",");
            }
            if ((object = arrayList.get(i2)) == null) continue;
            this.zza(stringBuilder, field, object);
        }
        stringBuilder.append("]");
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        Map<String, Field<?, ?>> map = this.zzrl();
        StringBuilder stringBuilder = new StringBuilder(100);
        block5: for (String string2 : map.keySet()) {
            Field<?, ?> field = map.get(string2);
            if (!this.zza(field)) continue;
            Object obj = this.zza(field, this.zzb(field));
            if (stringBuilder.length() == 0) {
                stringBuilder.append("{");
            } else {
                stringBuilder.append(",");
            }
            stringBuilder.append("\"").append(string2).append("\":");
            if (obj == null) {
                stringBuilder.append("null");
                continue;
            }
            switch (field.zzrk()) {
                default: {
                    if (!field.zzrp()) break;
                    this.zza(stringBuilder, field, (ArrayList)obj);
                    continue block5;
                }
                case 8: {
                    stringBuilder.append("\"").append(zzmo.zzj((byte[])obj)).append("\"");
                    continue block5;
                }
                case 9: {
                    stringBuilder.append("\"").append(zzmo.zzk((byte[])obj)).append("\"");
                    continue block5;
                }
                case 10: {
                    zznc.zza(stringBuilder, (HashMap)obj);
                    continue block5;
                }
            }
            this.zza(stringBuilder, field, obj);
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
        stringBuilder.append("{}");
        return stringBuilder.toString();
    }

    protected <O, I> I zza(Field<I, O> field, Object object) {
        Object object2 = object;
        if (((Field)field).zzamU != null) {
            object2 = field.convertBack(object);
        }
        return (I)object2;
    }

    protected boolean zza(Field field) {
        if (field.zzrk() == 11) {
            if (field.zzrq()) {
                return this.zzcQ(field.zzrr());
            }
            return this.zzcP(field.zzrr());
        }
        return this.zzcO(field.zzrr());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Object zzb(Field hashMap) {
        String string2 = ((Field)((Object)hashMap)).zzrr();
        if (((Field)((Object)hashMap)).zzrt() == null) {
            return this.zzcN(((Field)((Object)hashMap)).zzrr());
        }
        boolean bl2 = this.zzcN(((Field)((Object)hashMap)).zzrr()) == null;
        zzx.zza(bl2, "Concrete field shouldn't be value object: %s", ((Field)((Object)hashMap)).zzrr());
        hashMap = ((Field)((Object)hashMap)).zzrq() ? this.zzrn() : this.zzrm();
        if (hashMap != null) {
            return hashMap.get(string2);
        }
        try {
            hashMap = "get" + Character.toUpperCase(string2.charAt(0)) + string2.substring(1);
            return this.getClass().getMethod((String)((Object)hashMap), new Class[0]).invoke(this, new Object[0]);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    protected abstract Object zzcN(String var1);

    protected abstract boolean zzcO(String var1);

    protected boolean zzcP(String string2) {
        throw new UnsupportedOperationException("Concrete types not supported");
    }

    protected boolean zzcQ(String string2) {
        throw new UnsupportedOperationException("Concrete type arrays not supported");
    }

    public abstract Map<String, Field<?, ?>> zzrl();

    public HashMap<String, Object> zzrm() {
        return null;
    }

    public HashMap<String, Object> zzrn() {
        return null;
    }

    public static class Field<I, O>
    implements SafeParcelable {
        public static final com.google.android.gms.common.server.response.zza CREATOR = new com.google.android.gms.common.server.response.zza();
        private final int mVersionCode;
        protected final int zzamL;
        protected final boolean zzamM;
        protected final int zzamN;
        protected final boolean zzamO;
        protected final String zzamP;
        protected final int zzamQ;
        protected final Class<? extends FastJsonResponse> zzamR;
        protected final String zzamS;
        private FieldMappingDictionary zzamT;
        private zza<I, O> zzamU;

        /*
         * Enabled aggressive block sorting
         */
        Field(int n2, int n3, boolean bl2, int n4, boolean bl3, String string2, int n5, String string3, ConverterWrapper converterWrapper) {
            this.mVersionCode = n2;
            this.zzamL = n3;
            this.zzamM = bl2;
            this.zzamN = n4;
            this.zzamO = bl3;
            this.zzamP = string2;
            this.zzamQ = n5;
            if (string3 == null) {
                this.zzamR = null;
                this.zzamS = null;
            } else {
                this.zzamR = SafeParcelResponse.class;
                this.zzamS = string3;
            }
            if (converterWrapper == null) {
                this.zzamU = null;
                return;
            }
            this.zzamU = converterWrapper.zzrh();
        }

        /*
         * Enabled aggressive block sorting
         */
        protected Field(int n2, boolean bl2, int n3, boolean bl3, String string2, int n4, Class<? extends FastJsonResponse> clazz, zza<I, O> zza2) {
            this.mVersionCode = 1;
            this.zzamL = n2;
            this.zzamM = bl2;
            this.zzamN = n3;
            this.zzamO = bl3;
            this.zzamP = string2;
            this.zzamQ = n4;
            this.zzamR = clazz;
            this.zzamS = clazz == null ? null : clazz.getCanonicalName();
            this.zzamU = zza2;
        }

        public static Field zza(String string2, int n2, zza<?, ?> zza2, boolean bl2) {
            return new Field(zza2.zzrj(), bl2, zza2.zzrk(), false, string2, n2, null, zza2);
        }

        public static <T extends FastJsonResponse> Field<T, T> zza(String string2, int n2, Class<T> clazz) {
            return new Field(11, false, 11, false, string2, n2, clazz, null);
        }

        public static <T extends FastJsonResponse> Field<ArrayList<T>, ArrayList<T>> zzb(String string2, int n2, Class<T> clazz) {
            return new Field<ArrayList<T>, ArrayList<T>>(11, true, 11, true, string2, n2, clazz, null);
        }

        public static Field<Integer, Integer> zzi(String string2, int n2) {
            return new Field<Integer, Integer>(0, false, 0, false, string2, n2, null, null);
        }

        public static Field<Double, Double> zzj(String string2, int n2) {
            return new Field<Double, Double>(4, false, 4, false, string2, n2, null, null);
        }

        public static Field<Boolean, Boolean> zzk(String string2, int n2) {
            return new Field<Boolean, Boolean>(6, false, 6, false, string2, n2, null, null);
        }

        public static Field<String, String> zzl(String string2, int n2) {
            return new Field<String, String>(7, false, 7, false, string2, n2, null, null);
        }

        public static Field<ArrayList<String>, ArrayList<String>> zzm(String string2, int n2) {
            return new Field<ArrayList<String>, ArrayList<String>>(7, true, 7, true, string2, n2, null, null);
        }

        public I convertBack(O o2) {
            return this.zzamU.convertBack(o2);
        }

        public int describeContents() {
            com.google.android.gms.common.server.response.zza zza2 = CREATOR;
            return 0;
        }

        public int getVersionCode() {
            return this.mVersionCode;
        }

        /*
         * Enabled aggressive block sorting
         */
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Field\n");
            stringBuilder.append("            versionCode=").append(this.mVersionCode).append('\n');
            stringBuilder.append("                 typeIn=").append(this.zzamL).append('\n');
            stringBuilder.append("            typeInArray=").append(this.zzamM).append('\n');
            stringBuilder.append("                typeOut=").append(this.zzamN).append('\n');
            stringBuilder.append("           typeOutArray=").append(this.zzamO).append('\n');
            stringBuilder.append("        outputFieldName=").append(this.zzamP).append('\n');
            stringBuilder.append("      safeParcelFieldId=").append(this.zzamQ).append('\n');
            stringBuilder.append("       concreteTypeName=").append(this.zzru()).append('\n');
            if (this.zzrt() != null) {
                stringBuilder.append("     concreteType.class=").append(this.zzrt().getCanonicalName()).append('\n');
            }
            StringBuilder stringBuilder2 = stringBuilder.append("          converterName=");
            String string2 = this.zzamU == null ? "null" : this.zzamU.getClass().getCanonicalName();
            stringBuilder2.append(string2).append('\n');
            return stringBuilder.toString();
        }

        public void writeToParcel(Parcel parcel, int n2) {
            com.google.android.gms.common.server.response.zza zza2 = CREATOR;
            com.google.android.gms.common.server.response.zza.zza(this, parcel, n2);
        }

        public void zza(FieldMappingDictionary fieldMappingDictionary) {
            this.zzamT = fieldMappingDictionary;
        }

        public int zzrj() {
            return this.zzamL;
        }

        public int zzrk() {
            return this.zzamN;
        }

        public Field<I, O> zzro() {
            return new Field<I, O>(this.mVersionCode, this.zzamL, this.zzamM, this.zzamN, this.zzamO, this.zzamP, this.zzamQ, this.zzamS, this.zzrw());
        }

        public boolean zzrp() {
            return this.zzamM;
        }

        public boolean zzrq() {
            return this.zzamO;
        }

        public String zzrr() {
            return this.zzamP;
        }

        public int zzrs() {
            return this.zzamQ;
        }

        public Class<? extends FastJsonResponse> zzrt() {
            return this.zzamR;
        }

        String zzru() {
            if (this.zzamS == null) {
                return null;
            }
            return this.zzamS;
        }

        public boolean zzrv() {
            return this.zzamU != null;
        }

        ConverterWrapper zzrw() {
            if (this.zzamU == null) {
                return null;
            }
            return ConverterWrapper.zza(this.zzamU);
        }

        public Map<String, Field<?, ?>> zzrx() {
            zzx.zzz(this.zzamS);
            zzx.zzz(this.zzamT);
            return this.zzamT.zzcR(this.zzamS);
        }
    }

    public static interface zza<I, O> {
        public I convertBack(O var1);

        public int zzrj();

        public int zzrk();
    }
}

