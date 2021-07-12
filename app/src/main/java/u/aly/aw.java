/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 */
package u.aly;

import android.content.Context;
import android.text.TextUtils;
import u.aly.ao;
import u.aly.av;
import u.aly.bj;
import u.aly.bl;
import u.aly.t;
import u.aly.x;

public class aw
implements ao {
    private static aw h = null;
    private boolean a = false;
    private int b = -1;
    private int c = -1;
    private int d = -1;
    private float e = 0.0f;
    private float f = 0.0f;
    private Context g = null;

    private aw(Context context, String string2, int n2) {
        this.g = context;
        this.a(string2, n2);
    }

    public static aw a(Context object) {
        synchronized (aw.class) {
            if (h == null) {
                x.a a2 = x.a(object).b();
                h = new aw((Context)object, a2.d(null), a2.d(0));
            }
            object = h;
            return object;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean a(String stringArray) {
        block5: {
            block4: {
                int n2;
                int n3;
                if (TextUtils.isEmpty((CharSequence)stringArray) || (stringArray = stringArray.split("\\|")).length != 6) break block4;
                if (stringArray[0].startsWith("SIG7") && stringArray[1].split(",").length == stringArray[5].split(",").length) {
                    return true;
                }
                if (stringArray[0].startsWith("FIXED") && (n3 = stringArray[5].split(",").length) >= (n2 = Integer.parseInt(stringArray[1])) && n2 >= 1) break block5;
            }
            return false;
        }
        return true;
    }

    private float b(String string2, int n2) {
        n2 *= 2;
        if (string2 == null) {
            return 0.0f;
        }
        return (float)Integer.valueOf(string2.substring(n2, n2 + 5), 16).intValue() / 1048576.0f;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void b(String objectArray) {
        void var1_6;
        int n2;
        block22: {
            block21: {
                block20: {
                    if (objectArray == null) break block21;
                    String[] stringArray = objectArray.split("\\|");
                    float f2 = stringArray[2].equals("SIG13") ? Float.valueOf(stringArray[3]).floatValue() : 0.0f;
                    if (this.e > f2) {
                        this.a = false;
                        return;
                    }
                    float[] fArray = null;
                    if (stringArray[0].equals("SIG7")) {
                        String[] stringArray2 = stringArray[1].split(",");
                        fArray = new float[stringArray2.length];
                        for (n2 = 0; n2 < stringArray2.length; ++n2) {
                            fArray[n2] = Float.valueOf(stringArray2[n2]).floatValue();
                        }
                    }
                    int[] nArray = null;
                    Object var1_4 = null;
                    if (stringArray[4].equals("RPT")) {
                        String[] stringArray3 = stringArray[5].split(",");
                        int[] nArray2 = new int[stringArray3.length];
                        for (n2 = 0; n2 < stringArray3.length; ++n2) {
                            nArray2[n2] = Integer.valueOf(stringArray3[n2]);
                        }
                    } else if (stringArray[4].equals("DOM")) {
                        if (bj.q(this.g)) {
                            this.a = false;
                            return;
                        }
                        int[] nArray3 = nArray;
                        try {
                            stringArray = stringArray[5].split(",");
                            int[] nArray4 = nArray;
                            nArray = new int[stringArray.length];
                            n2 = 0;
                            while (true) {
                                int[] nArray5 = nArray;
                                int[] nArray6 = nArray;
                                if (n2 < stringArray.length) {
                                    int[] nArray7 = nArray;
                                    nArray[n2] = Integer.valueOf(stringArray[n2]);
                                    ++n2;
                                    continue;
                                }
                                break;
                            }
                        }
                        catch (Exception exception) {
                            int[] nArray8 = nArray3;
                        }
                    }
                    f2 = 0.0f;
                    for (n2 = 0; n2 < fArray.length; ++n2) {
                        if (!(this.f < (f2 += fArray[n2]))) {
                            continue;
                        }
                        break block20;
                    }
                    n2 = -1;
                }
                if (n2 == -1) {
                    this.a = false;
                    return;
                }
                this.a = true;
                this.d = n2 + 1;
                if (var1_6 != null) break block22;
            }
            return;
        }
        this.b = var1_6[n2];
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void c(String object) {
        int n2;
        block18: {
            block17: {
                if (object == null) break block17;
                String[] stringArray = ((String)object).split("\\|");
                float f2 = 0.0f;
                if (stringArray[2].equals("SIG13")) {
                    f2 = Float.valueOf(stringArray[3]).floatValue();
                }
                if (this.e > f2) {
                    this.a = false;
                    return;
                }
                n2 = stringArray[0].equals("FIXED") ? Integer.valueOf(stringArray[1]) : -1;
                int[] nArray = null;
                object = null;
                if (stringArray[4].equals("RPT")) {
                    String[] stringArray2 = stringArray[5].split(",");
                    object = new int[stringArray2.length];
                    for (int i2 = 0; i2 < stringArray2.length; ++i2) {
                        object[i2] = Integer.valueOf(stringArray2[i2]);
                    }
                } else if (stringArray[4].equals("DOM")) {
                    if (bj.q(this.g)) {
                        this.a = false;
                        return;
                    }
                    int[] nArray2 = nArray;
                    try {
                        stringArray = stringArray[5].split(",");
                        nArray2 = nArray;
                        nArray = new int[stringArray.length];
                        int n3 = 0;
                        while (true) {
                            object = nArray;
                            nArray2 = nArray;
                            if (n3 < stringArray.length) {
                                nArray2 = nArray;
                                nArray[n3] = Integer.valueOf(stringArray[n3]);
                                ++n3;
                                continue;
                            }
                            break;
                        }
                    }
                    catch (Exception exception) {
                        object = nArray2;
                    }
                }
                if (n2 == -1) {
                    this.a = false;
                    return;
                }
                this.a = true;
                this.d = n2;
                if (object != null) break block18;
            }
            return;
        }
        this.b = (int)object[n2 - 1];
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(String string2, int n2) {
        this.c = n2;
        String string3 = t.a(this.g);
        if (TextUtils.isEmpty((CharSequence)string3) || TextUtils.isEmpty((CharSequence)string2)) {
            this.a = false;
            return;
        }
        try {
            this.e = this.b(string3, 12);
            this.f = this.b(string3, 6);
            if (string2.startsWith("SIG7")) {
                this.b(string2);
                return;
            }
            if (!string2.startsWith("FIXED")) return;
            this.c(string2);
            return;
        }
        catch (Exception exception) {
            this.a = false;
            bl.e("v:" + string2, exception);
            return;
        }
    }

    public void a(av av2) {
        if (!this.a) {
            return;
        }
        av2.b.f.put("client_test", this.d);
    }

    @Override
    public void a(x.a a2) {
        this.a(a2.d(null), a2.d(0));
    }

    public boolean a() {
        return this.a;
    }

    public int b() {
        return this.b;
    }

    public int c() {
        return this.c;
    }

    public int d() {
        return this.d;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" p13:");
        stringBuilder.append(this.e);
        stringBuilder.append(" p07:");
        stringBuilder.append(this.f);
        stringBuilder.append(" policy:");
        stringBuilder.append(this.b);
        stringBuilder.append(" interval:");
        stringBuilder.append(this.c);
        return stringBuilder.toString();
    }
}

