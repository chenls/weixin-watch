/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.mobvoi.android.search;

import android.os.Parcel;
import android.os.Parcelable;
import com.mobvoi.android.common.internal.safeparcel.SafeParcelable;
import com.mobvoi.android.search.ParamItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OneboxRequest
implements SafeParcelable,
Serializable {
    public static final Parcelable.Creator<OneboxRequest> CREATOR = new OneboxRequestCreator();
    public static final String DETAIL_SEARCH_TYPE = "detail";
    public static final String LIST_SEARCH_TYPE = "pc";
    public static final int VERSION_CODE = 40000;
    private String a;
    private List<ParamItem> b = new ArrayList<ParamItem>();
    private String c = "pc";
    private String d;
    private String e;
    private int f = 40000;

    private OneboxRequest() {
    }

    public OneboxRequest(String string2) {
        if (string2 == null) {
            throw new NullPointerException("task can not be null.");
        }
        this.a = string2;
    }

    static /* synthetic */ int a(OneboxRequest oneboxRequest, int n2) {
        oneboxRequest.f = n2;
        return n2;
    }

    static /* synthetic */ String a(OneboxRequest oneboxRequest, String string2) {
        oneboxRequest.c = string2;
        return string2;
    }

    static /* synthetic */ List a(OneboxRequest oneboxRequest, List list) {
        oneboxRequest.b = list;
        return list;
    }

    static /* synthetic */ String b(OneboxRequest oneboxRequest, String string2) {
        oneboxRequest.d = string2;
        return string2;
    }

    static /* synthetic */ String c(OneboxRequest oneboxRequest, String string2) {
        oneboxRequest.e = string2;
        return string2;
    }

    public void addParamItem(ParamItem paramItem) {
        this.b.add(paramItem);
    }

    public void addParamItems(Collection<ParamItem> collection) {
        this.b.addAll(collection);
    }

    public int describeContents() {
        return 0;
    }

    public String getAppKey() {
        return this.d;
    }

    public String getLocation() {
        return this.e;
    }

    public List<ParamItem> getParams() {
        return this.b;
    }

    public String getSearchType() {
        return this.c;
    }

    public String getTask() {
        return this.a;
    }

    public int getVersionCode() {
        return this.f;
    }

    public void setAppkey(String string2) {
        this.d = string2;
    }

    public void setLocation(String string2) {
        this.e = string2;
    }

    public void setSearchType(String string2) {
        this.c = string2;
    }

    public void setTask(String string2) {
        this.a = string2;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        parcel.writeString(this.a);
        parcel.writeList(this.b);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
        parcel.writeInt(this.f);
        parcel.writeString(this.e);
    }

    public static class OneboxRequestCreator
    implements Parcelable.Creator<OneboxRequest> {
        public OneboxRequest createFromParcel(Parcel parcel) {
            OneboxRequest oneboxRequest = new OneboxRequest(parcel.readString());
            OneboxRequest.a(oneboxRequest, parcel.readArrayList(ParamItem.class.getClassLoader()));
            OneboxRequest.a(oneboxRequest, parcel.readString());
            OneboxRequest.b(oneboxRequest, parcel.readString());
            OneboxRequest.a(oneboxRequest, parcel.readInt());
            OneboxRequest.c(oneboxRequest, parcel.readString());
            return oneboxRequest;
        }

        public OneboxRequest[] newArray(int n2) {
            return new OneboxRequest[n2];
        }
    }
}

