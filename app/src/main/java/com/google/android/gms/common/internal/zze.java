/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.common.internal;

import com.google.android.gms.common.internal.zzx;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public abstract class zze {
    public static final zze zzakF = zze.zza("\t\n\u000b\f\r \u0085\u1680\u2028\u2029\u205f\u3000\u00a0\u180e\u202f").zza(zze.zza('\u2000', '\u200a'));
    public static final zze zzakG = zze.zza("\t\n\u000b\f\r \u0085\u1680\u2028\u2029\u205f\u3000").zza(zze.zza('\u2000', '\u2006')).zza(zze.zza('\u2008', '\u200a'));
    public static final zze zzakH = zze.zza('\u0000', '\u007f');
    public static final zze zzakI;
    public static final zze zzakJ;
    public static final zze zzakK;
    public static final zze zzakL;
    public static final zze zzakM;
    public static final zze zzakN;
    public static final zze zzakO;
    public static final zze zzakP;
    public static final zze zzakQ;
    public static final zze zzakR;
    public static final zze zzakS;
    public static final zze zzakT;

    static {
        zze zze2 = zze.zza('0', '9');
        for (char c2 : "\u0660\u06f0\u07c0\u0966\u09e6\u0a66\u0ae6\u0b66\u0be6\u0c66\u0ce6\u0d66\u0e50\u0ed0\u0f20\u1040\u1090\u17e0\u1810\u1946\u19d0\u1b50\u1bb0\u1c40\u1c50\ua620\ua8d0\ua900\uaa50\uff10".toCharArray()) {
            zze2 = zze2.zza(zze.zza(c2, (char)(c2 + 9)));
        }
        zzakI = zze2;
        zzakJ = zze.zza('\t', '\r').zza(zze.zza('\u001c', ' ')).zza(zze.zzc('\u1680')).zza(zze.zzc('\u180e')).zza(zze.zza('\u2000', '\u2006')).zza(zze.zza('\u2008', '\u200b')).zza(zze.zza('\u2028', '\u2029')).zza(zze.zzc('\u205f')).zza(zze.zzc('\u3000'));
        zzakK = new zze(){

            @Override
            public boolean zzd(char c2) {
                return Character.isDigit(c2);
            }
        };
        zzakL = new zze(){

            @Override
            public boolean zzd(char c2) {
                return Character.isLetter(c2);
            }
        };
        zzakM = new zze(){

            @Override
            public boolean zzd(char c2) {
                return Character.isLetterOrDigit(c2);
            }
        };
        zzakN = new zze(){

            @Override
            public boolean zzd(char c2) {
                return Character.isUpperCase(c2);
            }
        };
        zzakO = new zze(){

            @Override
            public boolean zzd(char c2) {
                return Character.isLowerCase(c2);
            }
        };
        zzakP = zze.zza('\u0000', '\u001f').zza(zze.zza('\u007f', '\u009f'));
        zzakQ = zze.zza('\u0000', ' ').zza(zze.zza('\u007f', '\u00a0')).zza(zze.zzc('\u00ad')).zza(zze.zza('\u0600', '\u0603')).zza(zze.zza("\u06dd\u070f\u1680\u17b4\u17b5\u180e")).zza(zze.zza('\u2000', '\u200f')).zza(zze.zza('\u2028', '\u202f')).zza(zze.zza('\u205f', '\u2064')).zza(zze.zza('\u206a', '\u206f')).zza(zze.zzc('\u3000')).zza(zze.zza('\ud800', '\uf8ff')).zza(zze.zza("\ufeff\ufff9\ufffa\ufffb"));
        zzakR = zze.zza('\u0000', '\u04f9').zza(zze.zzc('\u05be')).zza(zze.zza('\u05d0', '\u05ea')).zza(zze.zzc('\u05f3')).zza(zze.zzc('\u05f4')).zza(zze.zza('\u0600', '\u06ff')).zza(zze.zza('\u0750', '\u077f')).zza(zze.zza('\u0e00', '\u0e7f')).zza(zze.zza('\u1e00', '\u20af')).zza(zze.zza('\u2100', '\u213a')).zza(zze.zza('\ufb50', '\ufdff')).zza(zze.zza('\ufe70', '\ufeff')).zza(zze.zza('\uff61', '\uffdc'));
        zzakS = new zze(){

            @Override
            public zze zza(zze zze2) {
                zzx.zzz(zze2);
                return this;
            }

            @Override
            public boolean zzb(CharSequence charSequence) {
                zzx.zzz(charSequence);
                return true;
            }

            @Override
            public boolean zzd(char c2) {
                return true;
            }
        };
        zzakT = new zze(){

            @Override
            public zze zza(zze zze2) {
                return zzx.zzz(zze2);
            }

            @Override
            public boolean zzb(CharSequence charSequence) {
                return charSequence.length() == 0;
            }

            @Override
            public boolean zzd(char c2) {
                return false;
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     */
    public static zze zza(final char c2, final char c3) {
        boolean bl2 = c3 >= c2;
        zzx.zzac(bl2);
        return new zze(){

            @Override
            public boolean zzd(char c22) {
                return c2 <= c22 && c22 <= c3;
            }
        };
    }

    public static zze zza(CharSequence object) {
        switch (object.length()) {
            default: {
                object = object.toString().toCharArray();
                Arrays.sort((char[])object);
                return new zze((char[])object){
                    final /* synthetic */ char[] zzakW;
                    {
                        this.zzakW = cArray;
                    }

                    @Override
                    public boolean zzd(char c2) {
                        return Arrays.binarySearch(this.zzakW, c2) >= 0;
                    }
                };
            }
            case 0: {
                return zzakT;
            }
            case 1: {
                return zze.zzc(object.charAt(0));
            }
            case 2: 
        }
        return new zze(object.charAt(0), object.charAt(1)){
            final /* synthetic */ char zzakU;
            final /* synthetic */ char zzakV;
            {
                this.zzakU = c2;
                this.zzakV = c3;
            }

            @Override
            public boolean zzd(char c2) {
                return c2 == this.zzakU || c2 == this.zzakV;
            }
        };
    }

    public static zze zzc(final char c2) {
        return new zze(){

            @Override
            public zze zza(zze zze2) {
                if (zze2.zzd(c2)) {
                    return zze2;
                }
                return super.zza(zze2);
            }

            @Override
            public boolean zzd(char c22) {
                return c22 == c2;
            }
        };
    }

    public zze zza(zze zze2) {
        return new zza(Arrays.asList(this, zzx.zzz(zze2)));
    }

    public boolean zzb(CharSequence charSequence) {
        for (int i2 = charSequence.length() - 1; i2 >= 0; --i2) {
            if (this.zzd(charSequence.charAt(i2))) continue;
            return false;
        }
        return true;
    }

    public abstract boolean zzd(char var1);

    private static class zza
    extends zze {
        List<zze> zzala;

        zza(List<zze> list) {
            this.zzala = list;
        }

        @Override
        public zze zza(zze zze2) {
            ArrayList<zze> arrayList = new ArrayList<zze>(this.zzala);
            arrayList.add(zzx.zzz(zze2));
            return new zza(arrayList);
        }

        @Override
        public boolean zzd(char c2) {
            Iterator<zze> iterator = this.zzala.iterator();
            while (iterator.hasNext()) {
                if (!iterator.next().zzd(c2)) continue;
                return true;
            }
            return false;
        }
    }
}

