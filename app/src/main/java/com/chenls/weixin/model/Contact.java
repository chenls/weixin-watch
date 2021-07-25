package com.chenls.weixin.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.chenls.weixin.util.StringUtil;
import com.chenls.weixin.util.WxHome;

import java.util.List;

public class Contact implements Parcelable, Comparable<Contact> {
    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        public Contact createFromParcel(Parcel parcel) {
            return new Contact(parcel);
        }

        public Contact[] newArray(int i) {
            return new Contact[i];
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
        if (!WxHome.isGroupUserName(this.UserName) || this.MemberList == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < 3 && i < this.MemberList.size()) {
            sb.append(this.MemberList.get(i).getShowName());
            if (i < this.MemberList.size() - 1) {
                if (i == 2) {
                    sb.append("...");
                } else {
                    sb.append("、");
                }
            }
            i++;
        }
        return sb.toString();
    }

    public boolean isPublic() {
        return !WxHome.isGroupUserName(this.UserName) && (this.VerifyFlag & 8) != 0;
    }

    public boolean isMuted() {
        boolean ret = (WxHome.isGroupUserName(this.UserName) && this.Statues == 0) || (this.ContactFlag & 512) == 1;
        Log.d("Contact", "isMuted = " + ret + ", NickName=" + this.NickName + " Statues=" + this.Statues + " ContactFlag=" + this.ContactFlag);
        return ret;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.UserName);
        parcel.writeString(this.NickName);
        parcel.writeString(this.HeadImgUrl);
        parcel.writeInt(this.ContactFlag);
        parcel.writeString(this.RemarkName);
        parcel.writeString(this.DisplayName);
        parcel.writeInt(this.Statues);
    }

    public int compareTo(Contact another) {
        if (this.ContactFlag != another.ContactFlag) {
            return this.ContactFlag - another.ContactFlag;
        }
        String displayPY0 = this.RemarkPYQuanPin.toLowerCase();
        if (StringUtil.isNullOrEmpty(this.RemarkPYQuanPin)) {
            displayPY0 = this.PYQuanPin.toLowerCase();
        }
        String displayPY1 = another.RemarkPYQuanPin.toLowerCase();
        if (StringUtil.isNullOrEmpty(another.RemarkPYQuanPin)) {
            displayPY1 = another.PYQuanPin.toLowerCase();
        }
        return displayPY0.compareTo(displayPY1);
    }
}
