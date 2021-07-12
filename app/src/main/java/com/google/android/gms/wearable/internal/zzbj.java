/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.ChannelApi;

final class zzbj
implements ChannelApi.ChannelListener {
    private final String zzVo;
    private final ChannelApi.ChannelListener zzbtb;

    zzbj(String string2, ChannelApi.ChannelListener channelListener) {
        this.zzVo = zzx.zzz(string2);
        this.zzbtb = zzx.zzz(channelListener);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (!(object instanceof zzbj)) {
                    return false;
                }
                object = (zzbj)object;
                if (!this.zzbtb.equals(((zzbj)object).zzbtb) || !this.zzVo.equals(((zzbj)object).zzVo)) break block5;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.zzVo.hashCode() * 31 + this.zzbtb.hashCode();
    }

    @Override
    public void onChannelClosed(Channel channel, int n2, int n3) {
        this.zzbtb.onChannelClosed(channel, n2, n3);
    }

    @Override
    public void onChannelOpened(Channel channel) {
        this.zzbtb.onChannelOpened(channel);
    }

    @Override
    public void onInputClosed(Channel channel, int n2, int n3) {
        this.zzbtb.onInputClosed(channel, n2, n3);
    }

    @Override
    public void onOutputClosed(Channel channel, int n2, int n3) {
        this.zzbtb.onOutputClosed(channel, n2, n3);
    }
}

