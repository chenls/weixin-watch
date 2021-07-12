/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.mobvoi.android.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.mobvoi.android.common.internal.safeparcel.SafeParcelable;
import com.mobvoi.android.wearable.Node;
import com.mobvoi.android.wearable.internal.NodeHolder;

public class GetLocalNodeResponse
implements SafeParcelable {
    public static final Parcelable.Creator<GetLocalNodeResponse> CREATOR = new Parcelable.Creator<GetLocalNodeResponse>(){

        public GetLocalNodeResponse a(Parcel parcel) {
            return new GetLocalNodeResponse(parcel);
        }

        public GetLocalNodeResponse[] a(int n2) {
            return new GetLocalNodeResponse[n2];
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.a(parcel);
        }

        public /* synthetic */ Object[] newArray(int n2) {
            return this.a(n2);
        }
    };
    private Node a;

    public GetLocalNodeResponse(Parcel parcel) {
        this.a = new Node((NodeHolder)parcel.readParcelable(NodeHolder.class.getClassLoader())){
            final /* synthetic */ NodeHolder a;
            {
                this.a = nodeHolder;
            }

            @Override
            public String getDisplayName() {
                return this.a.getDisplayName();
            }

            @Override
            public String getId() {
                return this.a.getId();
            }

            @Override
            public boolean isNearby() {
                return this.a.isNearby();
            }
        };
    }

    public Node a() {
        return this.a;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        parcel.writeParcelable((Parcelable)new NodeHolder(this.a.getId(), this.a.getDisplayName(), this.a.isNearby()), 0);
    }
}

