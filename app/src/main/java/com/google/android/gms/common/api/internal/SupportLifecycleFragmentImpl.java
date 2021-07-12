/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 */
package com.google.android.gms.common.api.internal;

import android.app.Dialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.api.internal.zzw;
import com.google.android.gms.common.zzc;

@KeepName
public class SupportLifecycleFragmentImpl
extends zzw {
    @Override
    protected void zzb(int n2, ConnectionResult connectionResult) {
        GooglePlayServicesUtil.showErrorDialogFragment(connectionResult.getErrorCode(), this.getActivity(), this, 2, this);
    }

    @Override
    protected void zzc(int n2, ConnectionResult connectionResult) {
        connectionResult = this.zzpS().zza(this.getActivity(), this);
        this.zzaiD = zzn.zza(this.getActivity().getApplicationContext(), new zzn((Dialog)connectionResult){
            final /* synthetic */ Dialog zzaiL;
            {
                this.zzaiL = dialog;
            }

            @Override
            protected void zzpJ() {
                SupportLifecycleFragmentImpl.this.zzpP();
                this.zzaiL.dismiss();
            }
        });
    }

    @Override
    protected /* synthetic */ zzc zzpQ() {
        return this.zzpS();
    }

    protected GoogleApiAvailability zzpS() {
        return GoogleApiAvailability.getInstance();
    }
}

