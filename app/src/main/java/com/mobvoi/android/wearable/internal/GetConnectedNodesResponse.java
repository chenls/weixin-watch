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
import java.util.ArrayList;
import java.util.List;

public class GetConnectedNodesResponse
implements SafeParcelable {
    public static final Parcelable.Creator<GetConnectedNodesResponse> CREATOR = new Parcelable.Creator<GetConnectedNodesResponse>(){

        public GetConnectedNodesResponse a(Parcel parcel) {
            return new GetConnectedNodesResponse(parcel);
        }

        public GetConnectedNodesResponse[] a(int n2) {
            return new GetConnectedNodesResponse[n2];
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.a(parcel);
        }

        public /* synthetic */ Object[] newArray(int n2) {
            return this.a(n2);
        }
    };
    private List<Node> a;

    public GetConnectedNodesResponse(Parcel parcelableArray) {
        parcelableArray = parcelableArray.readParcelableArray(NodeHolder.class.getClassLoader());
        this.a = new ArrayList<Node>(parcelableArray.length);
        for (int i2 = 0; i2 < parcelableArray.length; ++i2) {
            final NodeHolder nodeHolder = (NodeHolder)parcelableArray[i2];
            this.a.add(new Node(){

                @Override
                public String getDisplayName() {
                    return nodeHolder.getDisplayName();
                }

                @Override
                public String getId() {
                    return nodeHolder.getId();
                }

                @Override
                public boolean isNearby() {
                    return nodeHolder.isNearby();
                }
            });
        }
    }

    public List<Node> a() {
        return this.a;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        Parcelable[] parcelableArray = new NodeHolder[this.a.size()];
        for (n2 = 0; n2 < this.a.size(); ++n2) {
            parcelableArray[n2] = new NodeHolder(this.a.get(n2).getId(), this.a.get(n2).getDisplayName(), this.a.get(n2).isNearby());
        }
        parcel.writeParcelableArray(parcelableArray, 0);
    }
}

