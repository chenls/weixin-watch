/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzsj;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataMap;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public final class zzsi {
    /*
     * Enabled aggressive block sorting
     */
    private static int zza(String string2, zzsj.zza.zza[] zzaArray) {
        int n2 = zzaArray.length;
        int n3 = 0;
        int n4 = 14;
        while (n3 < n2) {
            int n5;
            zzsj.zza.zza zza2 = zzaArray[n3];
            if (n4 == 14) {
                if (zza2.type == 9 || zza2.type == 2 || zza2.type == 6) {
                    n5 = zza2.type;
                } else {
                    n5 = n4;
                    if (zza2.type != 14) {
                        throw new IllegalArgumentException("Unexpected TypedValue type: " + zza2.type + " for key " + string2);
                    }
                }
            } else {
                n5 = n4;
                if (zza2.type != n4) {
                    throw new IllegalArgumentException("The ArrayList elements should all be the same type, but ArrayList with key " + string2 + " contains items of type " + n4 + " and " + zza2.type);
                }
            }
            ++n3;
            n4 = n5;
        }
        return n4;
    }

    static int zza(List<Asset> list, Asset asset) {
        list.add(asset);
        return list.size() - 1;
    }

    public static zza zza(DataMap dataMap) {
        zzsj zzsj2 = new zzsj();
        ArrayList<Asset> arrayList = new ArrayList<Asset>();
        zzsj2.zzbtA = zzsi.zza(dataMap, arrayList);
        return new zza(zzsj2, arrayList);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static zzsj.zza.zza zza(List<Asset> list, Object object) {
        zzsj.zza.zza zza2 = new zzsj.zza.zza();
        if (object == null) {
            zza2.type = 14;
            return zza2;
        }
        zza2.zzbtE = new zzsj.zza.zza.zza();
        if (object instanceof String) {
            zza2.type = 2;
            zza2.zzbtE.zzbtG = (String)object;
            return zza2;
        }
        if (object instanceof Integer) {
            zza2.type = 6;
            zza2.zzbtE.zzbtK = (Integer)object;
            return zza2;
        }
        if (object instanceof Long) {
            zza2.type = 5;
            zza2.zzbtE.zzbtJ = (Long)object;
            return zza2;
        }
        if (object instanceof Double) {
            zza2.type = 3;
            zza2.zzbtE.zzbtH = (Double)object;
            return zza2;
        }
        if (object instanceof Float) {
            zza2.type = 4;
            zza2.zzbtE.zzbtI = ((Float)object).floatValue();
            return zza2;
        }
        if (object instanceof Boolean) {
            zza2.type = 8;
            zza2.zzbtE.zzbtM = (Boolean)object;
            return zza2;
        }
        if (object instanceof Byte) {
            zza2.type = 7;
            zza2.zzbtE.zzbtL = ((Byte)object).byteValue();
            return zza2;
        }
        if (object instanceof byte[]) {
            zza2.type = 1;
            zza2.zzbtE.zzbtF = (byte[])object;
            return zza2;
        }
        if (object instanceof String[]) {
            zza2.type = 11;
            zza2.zzbtE.zzbtP = (String[])object;
            return zza2;
        }
        if (object instanceof long[]) {
            zza2.type = 12;
            zza2.zzbtE.zzbtQ = (long[])object;
            return zza2;
        }
        if (object instanceof float[]) {
            zza2.type = 15;
            zza2.zzbtE.zzbtR = (float[])object;
            return zza2;
        }
        if (object instanceof Asset) {
            zza2.type = 13;
            zza2.zzbtE.zzbtS = zzsi.zza(list, (Asset)object);
            return zza2;
        }
        if (object instanceof DataMap) {
            zza2.type = 9;
            object = (DataMap)object;
            Object object2 = new TreeSet<String>(((DataMap)object).keySet());
            zzsj.zza[] zzaArray = new zzsj.zza[((TreeSet)object2).size()];
            object2 = ((TreeSet)object2).iterator();
            int n2 = 0;
            while (true) {
                if (!object2.hasNext()) {
                    zza2.zzbtE.zzbtN = zzaArray;
                    return zza2;
                }
                String string2 = (String)object2.next();
                zzaArray[n2] = new zzsj.zza();
                zzaArray[n2].name = string2;
                zzaArray[n2].zzbtC = zzsi.zza(list, ((DataMap)object).get(string2));
                ++n2;
            }
        }
        if (!(object instanceof ArrayList)) throw new RuntimeException("newFieldValueFromValue: unexpected value " + object.getClass().getSimpleName());
        zza2.type = 10;
        ArrayList arrayList = (ArrayList)object;
        zzsj.zza.zza[] zzaArray = new zzsj.zza.zza[arrayList.size()];
        object = null;
        int n3 = arrayList.size();
        int n4 = 0;
        int n5 = 14;
        while (true) {
            if (n4 >= n3) {
                zza2.zzbtE.zzbtO = zzaArray;
                return zza2;
            }
            Object e2 = arrayList.get(n4);
            zzsj.zza.zza zza3 = zzsi.zza(list, e2);
            if (zza3.type != 14 && zza3.type != 2 && zza3.type != 6 && zza3.type != 9) {
                throw new IllegalArgumentException("The only ArrayList element types supported by DataBundleUtil are String, Integer, Bundle, and null, but this ArrayList contains a " + e2.getClass());
            }
            if (n5 == 14 && zza3.type != 14) {
                n5 = zza3.type;
                object = e2;
            } else if (zza3.type != n5) {
                throw new IllegalArgumentException("ArrayList elements must all be of the sameclass, but this one contains a " + object.getClass() + " and a " + e2.getClass());
            }
            zzaArray[n4] = zza3;
            ++n4;
        }
    }

    public static DataMap zza(zza zza2) {
        DataMap dataMap = new DataMap();
        for (zzsj.zza zza3 : zza2.zzbty.zzbtA) {
            zzsi.zza(zza2.zzbtz, dataMap, zza3.name, zza3.zzbtC);
        }
        return dataMap;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static ArrayList zza(List<Asset> list, zzsj.zza.zza.zza zzaArray, int n2) {
        ArrayList<Object> arrayList = new ArrayList<Object>(zzaArray.zzbtO.length);
        zzaArray = zzaArray.zzbtO;
        int n3 = zzaArray.length;
        int n4 = 0;
        while (n4 < n3) {
            zzsj.zza.zza zza2 = zzaArray[n4];
            if (zza2.type == 14) {
                arrayList.add(null);
            } else if (n2 == 9) {
                DataMap dataMap = new DataMap();
                for (zzsj.zza zza3 : zza2.zzbtE.zzbtN) {
                    zzsi.zza(list, dataMap, zza3.name, zza3.zzbtC);
                }
                arrayList.add(dataMap);
            } else if (n2 == 2) {
                arrayList.add(zza2.zzbtE.zzbtG);
            } else {
                if (n2 != 6) {
                    throw new IllegalArgumentException("Unexpected typeOfArrayList: " + n2);
                }
                arrayList.add(zza2.zzbtE.zzbtK);
            }
            ++n4;
        }
        return arrayList;
    }

    private static void zza(List<Asset> arrayList, DataMap dataMap, String string2, zzsj.zza.zza object) {
        int n2 = ((zzsj.zza.zza)object).type;
        if (n2 == 14) {
            dataMap.putString(string2, null);
            return;
        }
        zzsj.zza[] zzaArray = ((zzsj.zza.zza)object).zzbtE;
        if (n2 == 1) {
            dataMap.putByteArray(string2, zzaArray.zzbtF);
            return;
        }
        if (n2 == 11) {
            dataMap.putStringArray(string2, zzaArray.zzbtP);
            return;
        }
        if (n2 == 12) {
            dataMap.putLongArray(string2, zzaArray.zzbtQ);
            return;
        }
        if (n2 == 15) {
            dataMap.putFloatArray(string2, zzaArray.zzbtR);
            return;
        }
        if (n2 == 2) {
            dataMap.putString(string2, zzaArray.zzbtG);
            return;
        }
        if (n2 == 3) {
            dataMap.putDouble(string2, zzaArray.zzbtH);
            return;
        }
        if (n2 == 4) {
            dataMap.putFloat(string2, zzaArray.zzbtI);
            return;
        }
        if (n2 == 5) {
            dataMap.putLong(string2, zzaArray.zzbtJ);
            return;
        }
        if (n2 == 6) {
            dataMap.putInt(string2, zzaArray.zzbtK);
            return;
        }
        if (n2 == 7) {
            dataMap.putByte(string2, (byte)zzaArray.zzbtL);
            return;
        }
        if (n2 == 8) {
            dataMap.putBoolean(string2, zzaArray.zzbtM);
            return;
        }
        if (n2 == 13) {
            if (arrayList == null) {
                throw new RuntimeException("populateBundle: unexpected type for: " + string2);
            }
            dataMap.putAsset(string2, arrayList.get((int)zzaArray.zzbtS));
            return;
        }
        if (n2 == 9) {
            object = new DataMap();
            for (zzsj.zza zza2 : zzaArray.zzbtN) {
                zzsi.zza(arrayList, (DataMap)object, zza2.name, zza2.zzbtC);
            }
            dataMap.putDataMap(string2, (DataMap)object);
            return;
        }
        if (n2 == 10) {
            n2 = zzsi.zza(string2, zzaArray.zzbtO);
            arrayList = zzsi.zza(arrayList, (zzsj.zza.zza.zza)zzaArray, n2);
            if (n2 == 14) {
                dataMap.putStringArrayList(string2, arrayList);
                return;
            }
            if (n2 == 9) {
                dataMap.putDataMapArrayList(string2, arrayList);
                return;
            }
            if (n2 == 2) {
                dataMap.putStringArrayList(string2, arrayList);
                return;
            }
            if (n2 == 6) {
                dataMap.putIntegerArrayList(string2, arrayList);
                return;
            }
            throw new IllegalStateException("Unexpected typeOfArrayList: " + n2);
        }
        throw new RuntimeException("populateBundle: unexpected type " + n2);
    }

    private static zzsj.zza[] zza(DataMap dataMap, List<Asset> list) {
        Object object = new TreeSet<String>(dataMap.keySet());
        zzsj.zza[] zzaArray = new zzsj.zza[((TreeSet)object).size()];
        object = ((TreeSet)object).iterator();
        int n2 = 0;
        while (object.hasNext()) {
            String string2 = (String)object.next();
            Object t2 = dataMap.get(string2);
            zzaArray[n2] = new zzsj.zza();
            zzaArray[n2].name = string2;
            zzaArray[n2].zzbtC = zzsi.zza(list, t2);
            ++n2;
        }
        return zzaArray;
    }

    public static class zza {
        public final zzsj zzbty;
        public final List<Asset> zzbtz;

        public zza(zzsj zzsj2, List<Asset> list) {
            this.zzbty = zzsj2;
            this.zzbtz = list;
        }
    }
}

