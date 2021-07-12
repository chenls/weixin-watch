/*
 * Decompiled with CFR 0.151.
 */
package mobvoiapi;

import com.mobvoi.android.common.MobvoiApiManager;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.common.api.PendingResult;
import com.mobvoi.android.common.api.Status;
import com.mobvoi.android.wearable.MessageApi;
import mobvoiapi.ab;
import mobvoiapi.bd;
import mobvoiapi.bp;
import mobvoiapi.v;

public class ac
implements MessageApi,
ab {
    private MessageApi a;

    public ac() {
        MobvoiApiManager.getInstance().registerProxy(this);
        this.a();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void a() {
        if (MobvoiApiManager.getInstance().getGroup() == MobvoiApiManager.ApiGroup.MMS) {
            this.a = new bd();
        } else if (MobvoiApiManager.getInstance().getGroup() == MobvoiApiManager.ApiGroup.GMS) {
            this.a = new v();
        }
        bp.a("MobvoiApiManager", "load message api success.");
    }

    @Override
    public PendingResult<Status> addListener(MobvoiApiClient mobvoiApiClient, MessageApi.MessageListener messageListener) {
        bp.a("MobvoiApiManager", "MessageApiProxy#addListener()");
        return this.a.addListener(mobvoiApiClient, messageListener);
    }

    @Override
    public PendingResult<Status> removeListener(MobvoiApiClient mobvoiApiClient, MessageApi.MessageListener messageListener) {
        bp.a("MobvoiApiManager", "MessageApiProxy#removeListener()");
        return this.a.removeListener(mobvoiApiClient, messageListener);
    }

    @Override
    public PendingResult<MessageApi.SendMessageResult> sendMessage(MobvoiApiClient mobvoiApiClient, String string2, String string3, byte[] byArray) {
        bp.a("MobvoiApiManager", "MessageApiProxy#sendMessage()");
        return this.a.sendMessage(mobvoiApiClient, string2, string3, byArray);
    }
}

