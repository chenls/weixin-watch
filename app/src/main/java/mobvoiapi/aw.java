/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.RemoteException
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package mobvoiapi;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.common.api.Status;
import com.mobvoi.android.search.OneboxApi;
import com.mobvoi.android.search.OneboxRequest;
import com.mobvoi.android.search.internal.OneboxSearchResponse;
import mobvoiapi.at;
import mobvoiapi.au;
import mobvoiapi.ax;
import mobvoiapi.bp;
import mobvoiapi.d;
import mobvoiapi.e;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class aw
extends e<au> {
    public aw(Context context, Looper looper, MobvoiApiClient.ConnectionCallbacks connectionCallbacks, MobvoiApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, new String[0]);
    }

    @Override
    protected /* synthetic */ IInterface a(IBinder iBinder) {
        return this.c(iBinder);
    }

    @Override
    public void a(final ax<OneboxApi.OneboxResult> ax2, OneboxRequest oneboxRequest) throws RemoteException {
        ((au)this.d()).a(new at.a(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void a(OneboxSearchResponse oneboxSearchResponse) throws RemoteException {
                try {
                    ax ax22 = ax2;
                    Status status = new Status(oneboxSearchResponse.a);
                    JSONArray jSONArray = new JSONArray(oneboxSearchResponse.b);
                    String string2 = oneboxSearchResponse.c;
                    oneboxSearchResponse = oneboxSearchResponse.d != null ? new JSONObject(oneboxSearchResponse.d) : null;
                    ax22.a(new a(status, jSONArray, string2, (JSONObject)oneboxSearchResponse));
                    return;
                }
                catch (JSONException jSONException) {
                    bp.b("SearchAdapter", "Fail to decode the onebox search response", jSONException);
                    ax2.a(new a(new Status(8), null, "", null));
                    return;
                }
            }
        }, oneboxRequest);
    }

    @Override
    protected void a(d d2, e.c c2) throws RemoteException {
        d2.c(c2, 0, this.e().getPackageName());
    }

    protected au c(IBinder iBinder) {
        return au.a.a(iBinder);
    }

    @Override
    protected String f() {
        return "com.mobvoi.android.search.internal.ISearchService";
    }

    @Override
    protected String g() {
        return "com.mobvoi.android.search.BIND";
    }

    public static class a
    implements OneboxApi.OneboxResult {
        private Status a;
        private JSONArray b;
        private String c;
        private JSONObject d;

        public a(Status status, JSONArray jSONArray, String string2, JSONObject jSONObject) {
            this.a = status;
            this.b = jSONArray;
            this.c = string2;
            this.d = jSONObject;
        }

        @Override
        public String getMsgId() {
            return this.c;
        }

        @Override
        public JSONArray getResponse() {
            return this.b;
        }

        @Override
        public JSONObject getSemantic() {
            return this.d;
        }

        @Override
        public Status getStatus() {
            return this.a;
        }
    }
}

