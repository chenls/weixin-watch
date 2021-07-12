/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.mobvoi.android.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;
import com.mobvoi.android.common.internal.safeparcel.SafeParcelable;
import com.mobvoi.android.wearable.Node;

@Keep
public class NodeHolder
implements SafeParcelable,
Node {
    @Keep
    public static final Parcelable.Creator<NodeHolder> CREATOR = new Parcelable.Creator<NodeHolder>(){

        public NodeHolder a(Parcel parcel) {
            return new NodeHolder(parcel);
        }

        public NodeHolder[] a(int n2) {
            return new NodeHolder[n2];
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.a(parcel);
        }

        public /* synthetic */ Object[] newArray(int n2) {
            return this.a(n2);
        }
    };
    private String mDisplayName;
    private String mId;
    private boolean mIsNearby;

    /*
     * Enabled aggressive block sorting
     */
    private NodeHolder(Parcel parcel) {
        this.mId = parcel.readString();
        this.mDisplayName = parcel.readString();
        boolean bl2 = parcel.readInt() != 0;
        this.mIsNearby = bl2;
    }

    public NodeHolder(String string2, String string3, boolean bl2) {
        this.mId = string2;
        this.mDisplayName = string3;
        this.mIsNearby = bl2;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (object instanceof NodeHolder) {
            object = (NodeHolder)object;
            return this.mId.equals(((NodeHolder)object).getId());
        }
        return false;
    }

    @Override
    public String getDisplayName() {
        return this.mDisplayName;
    }

    @Override
    public String getId() {
        return this.mId;
    }

    public int hashCode() {
        return this.mId.hashCode();
    }

    @Override
    public boolean isNearby() {
        return this.mIsNearby;
    }

    public String toString() {
        return this.mId + "," + this.mDisplayName + ",isNearby:" + this.mIsNearby;
    }

    public void updateNodeId(String string2) {
        this.mId = string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeToParcel(Parcel parcel, int n2) {
        parcel.writeString(this.mId);
        parcel.writeString(this.mDisplayName);
        n2 = this.mIsNearby ? 1 : 0;
        parcel.writeInt(n2);
    }
}

