/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.Log
 */
package com.riyuxihe.weixinqingliao.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.riyuxihe.weixinqingliao.util.StringUtil;
import com.riyuxihe.weixinqingliao.util.WxHome;
import java.util.List;

public class Contact
implements Parcelable,
Comparable<Contact> {
    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>(){

        public Contact createFromParcel(Parcel parcel) {
            return new Contact(parcel);
        }

        public Contact[] newArray(int n2) {
            return new Contact[n2];
        }
    };
    public int ContactFlag;
    public String DisplayName;
    public String HeadImgUrl;
    public int MemberCount;
    public List<Contact> MemberList;
    public String NickName;
    public String PYQuanPin = "";
    public String RemarkName;
    public String RemarkPYQuanPin = "";
    public int Statues;
    public String UserName;
    public int VerifyFlag;

    public Contact() {
    }

    public Contact(Parcel parcel) {
        this.UserName = parcel.readString();
        this.NickName = parcel.readString();
        this.HeadImgUrl = parcel.readString();
        this.ContactFlag = parcel.readInt();
        this.RemarkName = parcel.readString();
        this.DisplayName = parcel.readString();
        this.Statues = parcel.readInt();
    }

    @Override
    public int compareTo(Contact contact) {
        if (this.ContactFlag != contact.ContactFlag) {
            return this.ContactFlag - contact.ContactFlag;
        }
        String string2 = this.RemarkPYQuanPin.toLowerCase();
        if (StringUtil.isNullOrEmpty(this.RemarkPYQuanPin)) {
            string2 = this.PYQuanPin.toLowerCase();
        }
        String string3 = contact.RemarkPYQuanPin.toLowerCase();
        if (StringUtil.isNullOrEmpty(contact.RemarkPYQuanPin)) {
            string3 = contact.PYQuanPin.toLowerCase();
        }
        return string2.compareTo(string3);
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String getShowName() {
        if (StringUtil.notNullOrEmpty(this.DisplayName)) {
            return this.DisplayName;
        }
        if (StringUtil.notNullOrEmpty(this.RemarkName)) {
            return this.RemarkName;
        }
        if (StringUtil.notNullOrEmpty(this.NickName)) {
            return this.NickName;
        }
        if (!WxHome.isGroupUserName(this.UserName)) {
            return "";
        }
        if (this.MemberList == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i2 = 0; i2 < 3 && i2 < this.MemberList.size(); ++i2) {
            stringBuilder.append(this.MemberList.get(i2).getShowName());
            if (i2 >= this.MemberList.size() - 1) continue;
            if (i2 == 2) {
                stringBuilder.append("...");
                continue;
            }
            stringBuilder.append("\u3001");
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean isMuted() {
        Log.d((String)"Contact", (String)("isMuted, NickName=" + this.NickName + " Statues=" + this.Statues + " ContactFlag=" + this.ContactFlag));
        return WxHome.isGroupUserName(this.UserName) && this.Statues == 0 || (this.ContactFlag & 0x200) == 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean isPublic() {
        return !WxHome.isGroupUserName(this.UserName) && (this.VerifyFlag & 8) != 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        parcel.writeString(this.UserName);
        parcel.writeString(this.NickName);
        parcel.writeString(this.HeadImgUrl);
        parcel.writeInt(this.ContactFlag);
        parcel.writeString(this.RemarkName);
        parcel.writeString(this.DisplayName);
        parcel.writeInt(this.Statues);
    }
}

