/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;

public final class GeoParsedResult
extends ParsedResult {
    private final double altitude;
    private final double latitude;
    private final double longitude;
    private final String query;

    GeoParsedResult(double d2, double d3, double d4, String string2) {
        super(ParsedResultType.GEO);
        this.latitude = d2;
        this.longitude = d3;
        this.altitude = d4;
        this.query = string2;
    }

    public double getAltitude() {
        return this.altitude;
    }

    @Override
    public String getDisplayResult() {
        StringBuilder stringBuilder = new StringBuilder(20);
        stringBuilder.append(this.latitude);
        stringBuilder.append(", ");
        stringBuilder.append(this.longitude);
        if (this.altitude > 0.0) {
            stringBuilder.append(", ");
            stringBuilder.append(this.altitude);
            stringBuilder.append('m');
        }
        if (this.query != null) {
            stringBuilder.append(" (");
            stringBuilder.append(this.query);
            stringBuilder.append(')');
        }
        return stringBuilder.toString();
    }

    public String getGeoURI() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("geo:");
        stringBuilder.append(this.latitude);
        stringBuilder.append(',');
        stringBuilder.append(this.longitude);
        if (this.altitude > 0.0) {
            stringBuilder.append(',');
            stringBuilder.append(this.altitude);
        }
        if (this.query != null) {
            stringBuilder.append('?');
            stringBuilder.append(this.query);
        }
        return stringBuilder.toString();
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public String getQuery() {
        return this.query;
    }
}

