/*
 * Decompiled with CFR 0.151.
 */
package mobvoiapi;

import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.mobvoi.android.wearable.MessageApi;
import mobvoiapi.bp;
import mobvoiapi.z;

public class o
implements MessageApi.MessageListener {
    private MessageApi.MessageListener a;

    public o(MessageApi.MessageListener messageListener) {
        this.a = messageListener;
    }

    public boolean equals(Object object) {
        if (object instanceof o) {
            return this.a.equals(((o)object).a);
        }
        return false;
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        bp.a("MobvoiApiManager", "MessageListenerWrapper#onMessageReceived()");
        this.a.onMessageReceived(z.a(messageEvent));
    }
}

