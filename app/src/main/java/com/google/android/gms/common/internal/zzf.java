/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.content.Context
 *  android.view.View
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.view.View;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzro;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class zzf {
    private final Account zzTI;
    private final String zzUW;
    private final Set<Scope> zzagh;
    private final int zzagj;
    private final View zzagk;
    private final String zzagl;
    private final Set<Scope> zzalb;
    private final Map<Api<?>, zza> zzalc;
    private final zzro zzald;
    private Integer zzale;

    /*
     * Enabled aggressive block sorting
     */
    public zzf(Account object, Set<Scope> iterator, Map<Api<?>, zza> map, int n2, View view, String string2, String string3, zzro zzro2) {
        this.zzTI = object;
        object = iterator == null ? Collections.EMPTY_SET : Collections.unmodifiableSet(iterator);
        this.zzagh = object;
        object = map;
        if (map == null) {
            object = Collections.EMPTY_MAP;
        }
        this.zzalc = object;
        this.zzagk = view;
        this.zzagj = n2;
        this.zzUW = string2;
        this.zzagl = string3;
        this.zzald = zzro2;
        object = new HashSet<Scope>(this.zzagh);
        iterator = this.zzalc.values().iterator();
        while (true) {
            if (!iterator.hasNext()) {
                this.zzalb = Collections.unmodifiableSet(object);
                return;
            }
            object.addAll(iterator.next().zzXf);
        }
    }

    public static zzf zzat(Context context) {
        return new GoogleApiClient.Builder(context).zzoY();
    }

    public Account getAccount() {
        return this.zzTI;
    }

    @Deprecated
    public String getAccountName() {
        if (this.zzTI != null) {
            return this.zzTI.name;
        }
        return null;
    }

    public void zza(Integer n2) {
        this.zzale = n2;
    }

    public Set<Scope> zzb(Api<?> object) {
        if ((object = this.zzalc.get(object)) == null || ((zza)object).zzXf.isEmpty()) {
            return this.zzagh;
        }
        HashSet<Scope> hashSet = new HashSet<Scope>(this.zzagh);
        hashSet.addAll(((zza)object).zzXf);
        return hashSet;
    }

    public Account zzqq() {
        if (this.zzTI != null) {
            return this.zzTI;
        }
        return new Account("<<default account>>", "com.google");
    }

    public int zzqr() {
        return this.zzagj;
    }

    public Set<Scope> zzqs() {
        return this.zzagh;
    }

    public Set<Scope> zzqt() {
        return this.zzalb;
    }

    public Map<Api<?>, zza> zzqu() {
        return this.zzalc;
    }

    public String zzqv() {
        return this.zzUW;
    }

    public String zzqw() {
        return this.zzagl;
    }

    public View zzqx() {
        return this.zzagk;
    }

    public zzro zzqy() {
        return this.zzald;
    }

    public Integer zzqz() {
        return this.zzale;
    }

    public static final class zza {
        public final Set<Scope> zzXf;
        public final boolean zzalf;

        public zza(Set<Scope> set, boolean bl2) {
            zzx.zzz(set);
            this.zzXf = Collections.unmodifiableSet(set);
            this.zzalf = bl2;
        }
    }
}

