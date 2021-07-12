/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 */
package com.google.android.gms.common.server.response;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.common.server.response.FieldMappingDictionary;
import com.google.android.gms.common.server.response.zze;
import com.google.android.gms.internal.zzmn;
import com.google.android.gms.internal.zzmo;
import com.google.android.gms.internal.zznb;
import com.google.android.gms.internal.zznc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SafeParcelResponse
extends FastJsonResponse
implements SafeParcelable {
    public static final zze CREATOR = new zze();
    private final String mClassName;
    private final int mVersionCode;
    private final FieldMappingDictionary zzamT;
    private final Parcel zzana;
    private final int zzanb;
    private int zzanc;
    private int zzand;

    /*
     * Enabled aggressive block sorting
     */
    SafeParcelResponse(int n2, Parcel parcel, FieldMappingDictionary fieldMappingDictionary) {
        this.mVersionCode = n2;
        this.zzana = zzx.zzz(parcel);
        this.zzanb = 2;
        this.zzamT = fieldMappingDictionary;
        this.mClassName = this.zzamT == null ? null : this.zzamT.zzrB();
        this.zzanc = 2;
    }

    private SafeParcelResponse(SafeParcelable safeParcelable, FieldMappingDictionary fieldMappingDictionary, String string2) {
        this.mVersionCode = 1;
        this.zzana = Parcel.obtain();
        safeParcelable.writeToParcel(this.zzana, 0);
        this.zzanb = 1;
        this.zzamT = zzx.zzz(fieldMappingDictionary);
        this.mClassName = zzx.zzz(string2);
        this.zzanc = 2;
    }

    private static HashMap<Integer, Map.Entry<String, FastJsonResponse.Field<?, ?>>> zzN(Map<String, FastJsonResponse.Field<?, ?>> object) {
        HashMap hashMap = new HashMap();
        for (Map.Entry entry : object.entrySet()) {
            hashMap.put(((FastJsonResponse.Field)entry.getValue()).zzrs(), entry);
        }
        return hashMap;
    }

    public static <T extends FastJsonResponse> SafeParcelResponse zza(T t2) {
        String string2 = t2.getClass().getCanonicalName();
        FieldMappingDictionary fieldMappingDictionary = SafeParcelResponse.zzb(t2);
        return new SafeParcelResponse((SafeParcelable)((Object)t2), fieldMappingDictionary, string2);
    }

    private static void zza(FieldMappingDictionary fieldMappingDictionary, FastJsonResponse field) {
        Class<?> clazz = field.getClass();
        if (!fieldMappingDictionary.zzb(clazz)) {
            Map<String, FastJsonResponse.Field<?, ?>> map = ((FastJsonResponse)((Object)field)).zzrl();
            fieldMappingDictionary.zza(clazz, map);
            clazz = map.keySet().iterator();
            while (clazz.hasNext()) {
                field = map.get((String)clazz.next());
                Class<FastJsonResponse> clazz2 = field.zzrt();
                if (clazz2 == null) continue;
                try {
                    SafeParcelResponse.zza(fieldMappingDictionary, clazz2.newInstance());
                }
                catch (InstantiationException instantiationException) {
                    throw new IllegalStateException("Could not instantiate an object of type " + field.zzrt().getCanonicalName(), instantiationException);
                }
                catch (IllegalAccessException illegalAccessException) {
                    throw new IllegalStateException("Could not access object of type " + field.zzrt().getCanonicalName(), illegalAccessException);
                }
            }
        }
    }

    private void zza(StringBuilder stringBuilder, int n2, Object object) {
        switch (n2) {
            default: {
                throw new IllegalArgumentException("Unknown type = " + n2);
            }
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: {
                stringBuilder.append(object);
                return;
            }
            case 7: {
                stringBuilder.append("\"").append(zznb.zzcU(object.toString())).append("\"");
                return;
            }
            case 8: {
                stringBuilder.append("\"").append(zzmo.zzj((byte[])object)).append("\"");
                return;
            }
            case 9: {
                stringBuilder.append("\"").append(zzmo.zzk((byte[])object));
                stringBuilder.append("\"");
                return;
            }
            case 10: {
                zznc.zza(stringBuilder, (HashMap)object);
                return;
            }
            case 11: 
        }
        throw new IllegalArgumentException("Method does not accept concrete type.");
    }

    private void zza(StringBuilder stringBuilder, FastJsonResponse.Field<?, ?> field, Parcel parcel, int n2) {
        switch (field.zzrk()) {
            default: {
                throw new IllegalArgumentException("Unknown field out type = " + field.zzrk());
            }
            case 0: {
                this.zzb(stringBuilder, field, this.zza(field, zza.zzg(parcel, n2)));
                return;
            }
            case 1: {
                this.zzb(stringBuilder, field, this.zza(field, zza.zzk(parcel, n2)));
                return;
            }
            case 2: {
                this.zzb(stringBuilder, field, this.zza(field, zza.zzi(parcel, n2)));
                return;
            }
            case 3: {
                this.zzb(stringBuilder, field, this.zza(field, Float.valueOf(zza.zzl(parcel, n2))));
                return;
            }
            case 4: {
                this.zzb(stringBuilder, field, this.zza(field, zza.zzn(parcel, n2)));
                return;
            }
            case 5: {
                this.zzb(stringBuilder, field, this.zza(field, zza.zzo(parcel, n2)));
                return;
            }
            case 6: {
                this.zzb(stringBuilder, field, this.zza(field, zza.zzc(parcel, n2)));
                return;
            }
            case 7: {
                this.zzb(stringBuilder, field, this.zza(field, zza.zzp(parcel, n2)));
                return;
            }
            case 8: 
            case 9: {
                this.zzb(stringBuilder, field, this.zza(field, zza.zzs(parcel, n2)));
                return;
            }
            case 10: {
                this.zzb(stringBuilder, field, this.zza(field, SafeParcelResponse.zzl(zza.zzr(parcel, n2))));
                return;
            }
            case 11: 
        }
        throw new IllegalArgumentException("Method does not accept concrete type.");
    }

    private void zza(StringBuilder stringBuilder, String string2, FastJsonResponse.Field<?, ?> field, Parcel parcel, int n2) {
        stringBuilder.append("\"").append(string2).append("\":");
        if (field.zzrv()) {
            this.zza(stringBuilder, field, parcel, n2);
            return;
        }
        this.zzb(stringBuilder, field, parcel, n2);
    }

    private void zza(StringBuilder stringBuilder, Map<String, FastJsonResponse.Field<?, ?>> map, Parcel parcel) {
        map = SafeParcelResponse.zzN(map);
        stringBuilder.append('{');
        int n2 = zza.zzau(parcel);
        boolean bl2 = false;
        while (parcel.dataPosition() < n2) {
            int n3 = zza.zzat(parcel);
            Map.Entry entry = (Map.Entry)((HashMap)map).get(zza.zzca(n3));
            if (entry == null) continue;
            if (bl2) {
                stringBuilder.append(",");
            }
            this.zza(stringBuilder, (String)entry.getKey(), (FastJsonResponse.Field)entry.getValue(), parcel, n3);
            bl2 = true;
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        stringBuilder.append('}');
    }

    private static FieldMappingDictionary zzb(FastJsonResponse fastJsonResponse) {
        FieldMappingDictionary fieldMappingDictionary = new FieldMappingDictionary(fastJsonResponse.getClass());
        SafeParcelResponse.zza(fieldMappingDictionary, fastJsonResponse);
        fieldMappingDictionary.zzrz();
        fieldMappingDictionary.zzry();
        return fieldMappingDictionary;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void zzb(StringBuilder stringBuilder, FastJsonResponse.Field<?, ?> object, Parcel object2, int n2) {
        if (((FastJsonResponse.Field)object).zzrq()) {
            stringBuilder.append("[");
            switch (((FastJsonResponse.Field)object).zzrk()) {
                default: {
                    throw new IllegalStateException("Unknown field type out.");
                }
                case 0: {
                    zzmn.zza(stringBuilder, zza.zzv((Parcel)object2, n2));
                    break;
                }
                case 1: {
                    zzmn.zza(stringBuilder, zza.zzx((Parcel)object2, n2));
                    break;
                }
                case 2: {
                    zzmn.zza(stringBuilder, zza.zzw((Parcel)object2, n2));
                    break;
                }
                case 3: {
                    zzmn.zza(stringBuilder, zza.zzy((Parcel)object2, n2));
                    break;
                }
                case 4: {
                    zzmn.zza(stringBuilder, zza.zzz((Parcel)object2, n2));
                    break;
                }
                case 5: {
                    zzmn.zza(stringBuilder, zza.zzA((Parcel)object2, n2));
                    break;
                }
                case 6: {
                    zzmn.zza(stringBuilder, zza.zzu((Parcel)object2, n2));
                    break;
                }
                case 7: {
                    zzmn.zza(stringBuilder, zza.zzB((Parcel)object2, n2));
                    break;
                }
                case 8: 
                case 9: 
                case 10: {
                    throw new UnsupportedOperationException("List of type BASE64, BASE64_URL_SAFE, or STRING_MAP is not supported");
                }
                case 11: {
                    object2 = zza.zzF((Parcel)object2, n2);
                    int n3 = ((Object)object2).length;
                    for (n2 = 0; n2 < n3; ++n2) {
                        if (n2 > 0) {
                            stringBuilder.append(",");
                        }
                        object2[n2].setDataPosition(0);
                        this.zza(stringBuilder, ((FastJsonResponse.Field)object).zzrx(), (Parcel)object2[n2]);
                    }
                }
            }
            stringBuilder.append("]");
            return;
        }
        switch (((FastJsonResponse.Field)object).zzrk()) {
            default: {
                throw new IllegalStateException("Unknown field type out");
            }
            case 0: {
                stringBuilder.append(zza.zzg((Parcel)object2, n2));
                return;
            }
            case 1: {
                stringBuilder.append(zza.zzk((Parcel)object2, n2));
                return;
            }
            case 2: {
                stringBuilder.append(zza.zzi((Parcel)object2, n2));
                return;
            }
            case 3: {
                stringBuilder.append(zza.zzl((Parcel)object2, n2));
                return;
            }
            case 4: {
                stringBuilder.append(zza.zzn((Parcel)object2, n2));
                return;
            }
            case 5: {
                stringBuilder.append(zza.zzo((Parcel)object2, n2));
                return;
            }
            case 6: {
                stringBuilder.append(zza.zzc((Parcel)object2, n2));
                return;
            }
            case 7: {
                object = zza.zzp((Parcel)object2, n2);
                stringBuilder.append("\"").append(zznb.zzcU((String)object)).append("\"");
                return;
            }
            case 8: {
                object = zza.zzs((Parcel)object2, n2);
                stringBuilder.append("\"").append(zzmo.zzj((byte[])object)).append("\"");
                return;
            }
            case 9: {
                object = zza.zzs((Parcel)object2, n2);
                stringBuilder.append("\"").append(zzmo.zzk((byte[])object));
                stringBuilder.append("\"");
                return;
            }
            case 10: {
                object = zza.zzr((Parcel)object2, n2);
                object2 = object.keySet();
                object2.size();
                stringBuilder.append("{");
                object2 = object2.iterator();
                n2 = 1;
                while (true) {
                    if (!object2.hasNext()) {
                        stringBuilder.append("}");
                        return;
                    }
                    String string2 = (String)object2.next();
                    if (n2 == 0) {
                        stringBuilder.append(",");
                    }
                    stringBuilder.append("\"").append(string2).append("\"");
                    stringBuilder.append(":");
                    stringBuilder.append("\"").append(zznb.zzcU(object.getString(string2))).append("\"");
                    n2 = 0;
                }
            }
            case 11: 
        }
        object2 = zza.zzE((Parcel)object2, n2);
        object2.setDataPosition(0);
        this.zza(stringBuilder, ((FastJsonResponse.Field)object).zzrx(), (Parcel)object2);
    }

    private void zzb(StringBuilder stringBuilder, FastJsonResponse.Field<?, ?> field, Object object) {
        if (field.zzrp()) {
            this.zzb(stringBuilder, field, (ArrayList)object);
            return;
        }
        this.zza(stringBuilder, field.zzrj(), object);
    }

    private void zzb(StringBuilder stringBuilder, FastJsonResponse.Field<?, ?> field, ArrayList<?> arrayList) {
        stringBuilder.append("[");
        int n2 = arrayList.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            if (i2 != 0) {
                stringBuilder.append(",");
            }
            this.zza(stringBuilder, field.zzrj(), arrayList.get(i2));
        }
        stringBuilder.append("]");
    }

    public static HashMap<String, String> zzl(Bundle bundle) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        for (String string2 : bundle.keySet()) {
            hashMap.put(string2, bundle.getString(string2));
        }
        return hashMap;
    }

    public int describeContents() {
        zze zze2 = CREATOR;
        return 0;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    @Override
    public String toString() {
        zzx.zzb(this.zzamT, (Object)"Cannot convert to JSON on client side.");
        Parcel parcel = this.zzrD();
        parcel.setDataPosition(0);
        StringBuilder stringBuilder = new StringBuilder(100);
        this.zza(stringBuilder, this.zzamT.zzcR(this.mClassName), parcel);
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zze zze2 = CREATOR;
        zze.zza(this, parcel, n2);
    }

    @Override
    protected Object zzcN(String string2) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    @Override
    protected boolean zzcO(String string2) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    /*
     * Enabled aggressive block sorting
     */
    public Parcel zzrD() {
        switch (this.zzanc) {
            case 0: {
                this.zzand = zzb.zzav(this.zzana);
                zzb.zzI(this.zzana, this.zzand);
                this.zzanc = 2;
            }
            default: {
                return this.zzana;
            }
            case 1: 
        }
        zzb.zzI(this.zzana, this.zzand);
        this.zzanc = 2;
        return this.zzana;
    }

    FieldMappingDictionary zzrE() {
        switch (this.zzanb) {
            default: {
                throw new IllegalStateException("Invalid creation type: " + this.zzanb);
            }
            case 0: {
                return null;
            }
            case 1: {
                return this.zzamT;
            }
            case 2: 
        }
        return this.zzamT;
    }

    @Override
    public Map<String, FastJsonResponse.Field<?, ?>> zzrl() {
        if (this.zzamT == null) {
            return null;
        }
        return this.zzamT.zzcR(this.mClassName);
    }
}

