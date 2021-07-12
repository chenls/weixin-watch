/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Fragment
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.View
 */
package com.google.android.gms.dynamic;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.gms.dynamic.zzc;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;

@SuppressLint(value={"NewApi"})
public final class zzb
extends zzc.zza {
    private Fragment zzavH;

    private zzb(Fragment fragment) {
        this.zzavH = fragment;
    }

    public static zzb zza(Fragment fragment) {
        if (fragment != null) {
            return new zzb(fragment);
        }
        return null;
    }

    @Override
    public Bundle getArguments() {
        return this.zzavH.getArguments();
    }

    @Override
    public int getId() {
        return this.zzavH.getId();
    }

    @Override
    public boolean getRetainInstance() {
        return this.zzavH.getRetainInstance();
    }

    @Override
    public String getTag() {
        return this.zzavH.getTag();
    }

    @Override
    public int getTargetRequestCode() {
        return this.zzavH.getTargetRequestCode();
    }

    @Override
    public boolean getUserVisibleHint() {
        return this.zzavH.getUserVisibleHint();
    }

    @Override
    public zzd getView() {
        return zze.zzC(this.zzavH.getView());
    }

    @Override
    public boolean isAdded() {
        return this.zzavH.isAdded();
    }

    @Override
    public boolean isDetached() {
        return this.zzavH.isDetached();
    }

    @Override
    public boolean isHidden() {
        return this.zzavH.isHidden();
    }

    @Override
    public boolean isInLayout() {
        return this.zzavH.isInLayout();
    }

    @Override
    public boolean isRemoving() {
        return this.zzavH.isRemoving();
    }

    @Override
    public boolean isResumed() {
        return this.zzavH.isResumed();
    }

    @Override
    public boolean isVisible() {
        return this.zzavH.isVisible();
    }

    @Override
    public void setHasOptionsMenu(boolean bl2) {
        this.zzavH.setHasOptionsMenu(bl2);
    }

    @Override
    public void setMenuVisibility(boolean bl2) {
        this.zzavH.setMenuVisibility(bl2);
    }

    @Override
    public void setRetainInstance(boolean bl2) {
        this.zzavH.setRetainInstance(bl2);
    }

    @Override
    public void setUserVisibleHint(boolean bl2) {
        this.zzavH.setUserVisibleHint(bl2);
    }

    @Override
    public void startActivity(Intent intent) {
        this.zzavH.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int n2) {
        this.zzavH.startActivityForResult(intent, n2);
    }

    @Override
    public void zzn(zzd zzd2) {
        zzd2 = (View)zze.zzp(zzd2);
        this.zzavH.registerForContextMenu((View)zzd2);
    }

    @Override
    public void zzo(zzd zzd2) {
        zzd2 = (View)zze.zzp(zzd2);
        this.zzavH.unregisterForContextMenu((View)zzd2);
    }

    @Override
    public zzd zztV() {
        return zze.zzC(this.zzavH.getActivity());
    }

    @Override
    public zzc zztW() {
        return zzb.zza(this.zzavH.getParentFragment());
    }

    @Override
    public zzd zztX() {
        return zze.zzC(this.zzavH.getResources());
    }

    @Override
    public zzc zztY() {
        return zzb.zza(this.zzavH.getTargetFragment());
    }
}

