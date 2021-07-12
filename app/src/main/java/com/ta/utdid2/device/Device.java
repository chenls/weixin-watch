/*
 * Decompiled with CFR 0.151.
 */
package com.ta.utdid2.device;

public class Device {
    private String deviceId = "";
    private String imei = "";
    private String imsi = "";
    private long mCheckSum = 0L;
    private long mCreateTimestamp = 0L;
    private String utdid = "";

    long getCheckSum() {
        return this.mCheckSum;
    }

    long getCreateTimestamp() {
        return this.mCreateTimestamp;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getImei() {
        return this.imei;
    }

    public String getImsi() {
        return this.imsi;
    }

    public String getUtdid() {
        return this.utdid;
    }

    void setCheckSum(long l2) {
        this.mCheckSum = l2;
    }

    void setCreateTimestamp(long l2) {
        this.mCreateTimestamp = l2;
    }

    void setDeviceId(String string2) {
        this.deviceId = string2;
    }

    void setImei(String string2) {
        this.imei = string2;
    }

    void setImsi(String string2) {
        this.imsi = string2;
    }

    void setUtdid(String string2) {
        this.utdid = string2;
    }
}

