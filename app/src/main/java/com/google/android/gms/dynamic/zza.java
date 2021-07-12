/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.Button
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package com.google.android.gms.dynamic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.zzf;
import java.util.LinkedList;

public abstract class zza<T extends LifecycleDelegate> {
    private final zzf<T> zzavA = new zzf<T>(){

        @Override
        public void zza(T object) {
            zza.zza(zza.this, object);
            object = zza.this.zzavz.iterator();
            while (object.hasNext()) {
                ((zza)object.next()).zzb(zza.this.zzavx);
            }
            zza.this.zzavz.clear();
            zza.zza(zza.this, null);
        }
    };
    private T zzavx;
    private Bundle zzavy;
    private LinkedList<zza> zzavz;

    static /* synthetic */ Bundle zza(zza zza2, Bundle bundle) {
        zza2.zzavy = bundle;
        return bundle;
    }

    static /* synthetic */ LifecycleDelegate zza(zza zza2, LifecycleDelegate lifecycleDelegate) {
        zza2.zzavx = lifecycleDelegate;
        return lifecycleDelegate;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void zza(Bundle bundle, zza zza2) {
        if (this.zzavx != null) {
            zza2.zzb((LifecycleDelegate)this.zzavx);
            return;
        }
        if (this.zzavz == null) {
            this.zzavz = new LinkedList();
        }
        this.zzavz.add(zza2);
        if (bundle != null) {
            if (this.zzavy == null) {
                this.zzavy = (Bundle)bundle.clone();
            } else {
                this.zzavy.putAll(bundle);
            }
        }
        this.zza(this.zzavA);
    }

    public static void zzb(FrameLayout frameLayout) {
        final Context context = frameLayout.getContext();
        final int n2 = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        String string2 = zzg.zzc(context, n2, GooglePlayServicesUtil.zzao(context));
        String string3 = zzg.zzh(context, n2);
        LinearLayout linearLayout = new LinearLayout(frameLayout.getContext());
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -2));
        frameLayout.addView((View)linearLayout);
        frameLayout = new TextView(frameLayout.getContext());
        frameLayout.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -2));
        frameLayout.setText((CharSequence)string2);
        linearLayout.addView((View)frameLayout);
        if (string3 != null) {
            frameLayout = new Button(context);
            frameLayout.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -2));
            frameLayout.setText((CharSequence)string3);
            linearLayout.addView((View)frameLayout);
            frameLayout.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    context.startActivity(GooglePlayServicesUtil.zzbv(n2));
                }
            });
        }
    }

    private void zzeJ(int n2) {
        while (!this.zzavz.isEmpty() && this.zzavz.getLast().getState() >= n2) {
            this.zzavz.removeLast();
        }
    }

    public void onCreate(final Bundle bundle) {
        this.zza(bundle, new zza(){

            @Override
            public int getState() {
                return 1;
            }

            @Override
            public void zzb(LifecycleDelegate lifecycleDelegate) {
                zza.this.zzavx.onCreate(bundle);
            }
        });
    }

    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final FrameLayout frameLayout = new FrameLayout(layoutInflater.getContext());
        this.zza(bundle, new zza(){

            @Override
            public int getState() {
                return 2;
            }

            @Override
            public void zzb(LifecycleDelegate lifecycleDelegate) {
                frameLayout.removeAllViews();
                frameLayout.addView(zza.this.zzavx.onCreateView(layoutInflater, viewGroup, bundle));
            }
        });
        if (this.zzavx == null) {
            this.zza(frameLayout);
        }
        return frameLayout;
    }

    public void onDestroy() {
        if (this.zzavx != null) {
            this.zzavx.onDestroy();
            return;
        }
        this.zzeJ(1);
    }

    public void onDestroyView() {
        if (this.zzavx != null) {
            this.zzavx.onDestroyView();
            return;
        }
        this.zzeJ(2);
    }

    public void onInflate(final Activity activity, final Bundle bundle, final Bundle bundle2) {
        this.zza(bundle2, new zza(){

            @Override
            public int getState() {
                return 0;
            }

            @Override
            public void zzb(LifecycleDelegate lifecycleDelegate) {
                zza.this.zzavx.onInflate(activity, bundle, bundle2);
            }
        });
    }

    public void onLowMemory() {
        if (this.zzavx != null) {
            this.zzavx.onLowMemory();
        }
    }

    public void onPause() {
        if (this.zzavx != null) {
            this.zzavx.onPause();
            return;
        }
        this.zzeJ(5);
    }

    public void onResume() {
        this.zza(null, new zza(){

            @Override
            public int getState() {
                return 5;
            }

            @Override
            public void zzb(LifecycleDelegate lifecycleDelegate) {
                zza.this.zzavx.onResume();
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onSaveInstanceState(Bundle bundle) {
        if (this.zzavx != null) {
            this.zzavx.onSaveInstanceState(bundle);
            return;
        } else {
            if (this.zzavy == null) return;
            bundle.putAll(this.zzavy);
            return;
        }
    }

    public void onStart() {
        this.zza(null, new zza(){

            @Override
            public int getState() {
                return 4;
            }

            @Override
            public void zzb(LifecycleDelegate lifecycleDelegate) {
                zza.this.zzavx.onStart();
            }
        });
    }

    public void onStop() {
        if (this.zzavx != null) {
            this.zzavx.onStop();
            return;
        }
        this.zzeJ(4);
    }

    protected void zza(FrameLayout frameLayout) {
        zza.zzb(frameLayout);
    }

    protected abstract void zza(zzf<T> var1);

    public T zztU() {
        return this.zzavx;
    }

    private static interface zza {
        public int getState();

        public void zzb(LifecycleDelegate var1);
    }
}

