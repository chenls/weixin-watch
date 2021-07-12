/*
 * Decompiled with CFR 0.151.
 */
package mobvoiapi;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Wearable;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.common.api.PendingResult;
import com.mobvoi.android.common.api.Status;
import com.mobvoi.android.wearable.MessageApi;
import mobvoiapi.bp;
import mobvoiapi.z;

public class v
implements com.mobvoi.android.wearable.MessageApi {
    private MessageApi a = Wearable.MessageApi;

    @Override
    public PendingResult<Status> addListener(MobvoiApiClient object, MessageApi.MessageListener messageListener) {
        bp.a("MobvoiApiManager", "MessageApiGoogleImpl#addListener()");
        object = z.a((MobvoiApiClient)object);
        return z.a(this.a.addListener((GoogleApiClient)object, z.a(messageListener)));
    }

    @Override
    public PendingResult<Status> removeListener(MobvoiApiClient object, MessageApi.MessageListener messageListener) {
        bp.a("MobvoiApiManager", "MessageApiGoogleImpl#removeListener()");
        object = z.a((MobvoiApiClient)object);
        return z.a(this.a.removeListener((GoogleApiClient)object, z.a(messageListener)));
    }

    @Override
    public PendingResult<MessageApi.SendMessageResult> sendMessage(MobvoiApiClient object, String string2, String string3, byte[] byArray) {
        bp.a("MobvoiApiManager", "MessageApiGoogleImpl#sendMessage()");
        object = z.a((MobvoiApiClient)object);
        return z.a(this.a.sendMessage((GoogleApiClient)object, string2, string3, byArray));
    }
}

