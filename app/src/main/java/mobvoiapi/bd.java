/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package mobvoiapi;

import android.os.RemoteException;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.common.api.PendingResult;
import com.mobvoi.android.common.api.Result;
import com.mobvoi.android.common.api.Status;
import com.mobvoi.android.wearable.MessageApi;
import mobvoiapi.bj;
import mobvoiapi.bm;

public class bd
implements MessageApi {
    @Override
    public PendingResult<Status> addListener(MobvoiApiClient mobvoiApiClient, final MessageApi.MessageListener messageListener) {
        return mobvoiApiClient.setResult(new bm<Status>(){

            @Override
            protected /* synthetic */ Result a(Status status) {
                return this.c(status);
            }

            @Override
            protected void a(bj bj2) throws RemoteException {
                bj2.a((bm<Status>)this, messageListener);
            }

            protected Status c(Status status) {
                return status;
            }
        });
    }

    @Override
    public PendingResult<Status> removeListener(MobvoiApiClient mobvoiApiClient, final MessageApi.MessageListener messageListener) {
        return mobvoiApiClient.setResult(new bm<Status>(){

            @Override
            protected /* synthetic */ Result a(Status status) {
                return this.c(status);
            }

            @Override
            protected void a(bj bj2) throws RemoteException {
                bj2.b((bm<Status>)this, messageListener);
            }

            protected Status c(Status status) {
                return status;
            }
        });
    }

    @Override
    public PendingResult<MessageApi.SendMessageResult> sendMessage(MobvoiApiClient mobvoiApiClient, final String string2, final String string3, final byte[] byArray) {
        return mobvoiApiClient.setResult(new bm<MessageApi.SendMessageResult>(){

            @Override
            protected /* synthetic */ Result a(Status status) {
                return this.c(status);
            }

            @Override
            protected void a(bj bj2) throws RemoteException {
                bj2.a(this, string2, string3, byArray);
            }

            protected MessageApi.SendMessageResult c(Status status) {
                return new a(status, -1);
            }
        });
    }

    public static final class a
    implements MessageApi.SendMessageResult {
        private final int a;
        private final Status b;

        public a(Status status, int n2) {
            this.b = status;
            this.a = n2;
        }

        @Override
        public int getRequestId() {
            return this.a;
        }

        @Override
        public Status getStatus() {
            return this.b;
        }
    }
}

