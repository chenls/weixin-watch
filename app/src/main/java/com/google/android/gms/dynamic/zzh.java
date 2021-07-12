/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.View
 */
package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import com.google.android.gms.dynamic.zzc;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;

public final class zzh
extends zzc.zza {
    private Fragment zzalg;

    private zzh(Fragment fragment) {
        this.zzalg = fragment;
    }

    public static zzh zza(Fragment fragment) {
        if (fragment != null) {
            return new zzh(fragment);
        }
        return null;
    }

    @Override
    public Bundle getArguments() {
        return this.zzalg.getArguments();
    }

    @Override
    public int getId() {
        return this.zzalg.getId();
    }

    @Override
    public boolean getRetainInstance() {
        return this.zzalg.getRetainInstance();
    }

    @Override
    public String getTag() {
        return this.zzalg.getTag();
    }

    @Override
    public int getTargetRequestCode() {
        return this.zzalg.getTargetRequestCode();
    }

    @Override
    public boolean getUserVisibleHint() {
        return this.zzalg.getUserVisibleHint();
    }

    @Override
    public zzd getView() {
        return zze.zzC(this.zzalg.getView());
    }

    @Override
    public boolean isAdded() {
        return this.zzalg.isAdded();
    }

    @Override
    public boolean isDetached() {
        return this.zzalg.isDetached();
    }

    @Override
    public boolean isHidden() {
        return this.zzalg.isHidden();
    }

    @Override
    public boolean isInLayout() {
        return this.zzalg.isInLayout();
    }

    @Override
    public boolean isRemoving() {
        return this.zzalg.isRemoving();
    }

    @Override
    public boolean isResumed() {
        return this.zzalg.isResumed();
    }

    @Override
    public boolean isVisible() {
        return this.zzalg.isVisible();
    }

    @Override
    public void setHasOptionsMenu(boolean bl2) {
        this.zzalg.setHasOptionsMenu(bl2);
    }

    @Override
    public void setMenuVisibility(boolean bl2) {
        this.zzalg.setMenuVisibility(bl2);
    }

    @Override
    public void setRetainInstance(boolean bl2) {
        this.zzalg.setRetainInstance(bl2);
    }

    @Override
    public void setUserVisibleHint(boolean bl2) {
        this.zzalg.setUserVisibleHint(bl2);
    }

    @Override
    public void startActivity(Intent intent) {
        this.zzalg.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int n2) {
        this.zzalg.startActivityForResult(intent, n2);
    }

    @Override
    public void zzn(zzd zzd2) {
        zzd2 = (View)zze.zzp(zzd2);
        this.zzalg.registerForContextMenu((View)zzd2);
    }

    @Override
    public void zzo(zzd zzd2) {
        zzd2 = (View)zze.zzp(zzd2);
        this.zzalg.unregisterForContextMenu((View)zzd2);
    }

    @Override
    public zzd zztV() {
        return zze.zzC(this.zzalg.getActivity());
    }

    @Override
    public zzc zztW() {
        return zzh.zza(this.zzalg.getParentFragment());
    }

    @Override
    public zzd zztX() {
        return zze.zzC(this.zzalg.getResources());
    }

    @Override
    public zzc zztY() {
        return zzh.zza(this.zzalg.getTargetFragment());
    }
}

