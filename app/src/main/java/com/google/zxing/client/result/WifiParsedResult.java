/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;

public final class WifiParsedResult
extends ParsedResult {
    private final boolean hidden;
    private final String networkEncryption;
    private final String password;
    private final String ssid;

    public WifiParsedResult(String string2, String string3, String string4) {
        this(string2, string3, string4, false);
    }

    public WifiParsedResult(String string2, String string3, String string4, boolean bl2) {
        super(ParsedResultType.WIFI);
        this.ssid = string3;
        this.networkEncryption = string2;
        this.password = string4;
        this.hidden = bl2;
    }

    @Override
    public String getDisplayResult() {
        StringBuilder stringBuilder = new StringBuilder(80);
        WifiParsedResult.maybeAppend(this.ssid, stringBuilder);
        WifiParsedResult.maybeAppend(this.networkEncryption, stringBuilder);
        WifiParsedResult.maybeAppend(this.password, stringBuilder);
        WifiParsedResult.maybeAppend(Boolean.toString(this.hidden), stringBuilder);
        return stringBuilder.toString();
    }

    public String getNetworkEncryption() {
        return this.networkEncryption;
    }

    public String getPassword() {
        return this.password;
    }

    public String getSsid() {
        return this.ssid;
    }

    public boolean isHidden() {
        return this.hidden;
    }
}

