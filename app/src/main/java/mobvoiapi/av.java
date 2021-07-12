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
import com.mobvoi.android.search.OneboxApi;
import com.mobvoi.android.search.OneboxRequest;
import mobvoiapi.aw;
import mobvoiapi.ax;

public class av
implements OneboxApi {
    @Override
    public PendingResult<OneboxApi.OneboxResult> fetchOneboxResult(MobvoiApiClient mobvoiApiClient, final OneboxRequest oneboxRequest) {
        return mobvoiApiClient.setResult(new ax<OneboxApi.OneboxResult>(){

            @Override
            protected /* synthetic */ Result a(Status status) {
                return this.c(status);
            }

            @Override
            protected void a(aw aw2) throws RemoteException {
                aw2.a(this, oneboxRequest);
            }

            protected OneboxApi.OneboxResult c(Status status) {
                return new aw.a(new Status(8), null, "", null);
            }
        });
    }
}

