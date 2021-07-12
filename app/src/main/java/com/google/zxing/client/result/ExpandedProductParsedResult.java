/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;
import java.util.Map;

public final class ExpandedProductParsedResult
extends ParsedResult {
    public static final String KILOGRAM = "KG";
    public static final String POUND = "LB";
    private final String bestBeforeDate;
    private final String expirationDate;
    private final String lotNumber;
    private final String packagingDate;
    private final String price;
    private final String priceCurrency;
    private final String priceIncrement;
    private final String productID;
    private final String productionDate;
    private final String rawText;
    private final String sscc;
    private final Map<String, String> uncommonAIs;
    private final String weight;
    private final String weightIncrement;
    private final String weightType;

    public ExpandedProductParsedResult(String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10, String string11, String string12, String string13, String string14, String string15, Map<String, String> map) {
        super(ParsedResultType.PRODUCT);
        this.rawText = string2;
        this.productID = string3;
        this.sscc = string4;
        this.lotNumber = string5;
        this.productionDate = string6;
        this.packagingDate = string7;
        this.bestBeforeDate = string8;
        this.expirationDate = string9;
        this.weight = string10;
        this.weightType = string11;
        this.weightIncrement = string12;
        this.price = string13;
        this.priceIncrement = string14;
        this.priceCurrency = string15;
        this.uncommonAIs = map;
    }

    private static boolean equalsOrNull(Object object, Object object2) {
        if (object == null) {
            return object2 == null;
        }
        return object.equals(object2);
    }

    private static int hashNotNull(Object object) {
        if (object == null) {
            return 0;
        }
        return object.hashCode();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block3: {
            block2: {
                if (!(object instanceof ExpandedProductParsedResult)) break block2;
                object = (ExpandedProductParsedResult)object;
                if (ExpandedProductParsedResult.equalsOrNull(this.productID, ((ExpandedProductParsedResult)object).productID) && ExpandedProductParsedResult.equalsOrNull(this.sscc, ((ExpandedProductParsedResult)object).sscc) && ExpandedProductParsedResult.equalsOrNull(this.lotNumber, ((ExpandedProductParsedResult)object).lotNumber) && ExpandedProductParsedResult.equalsOrNull(this.productionDate, ((ExpandedProductParsedResult)object).productionDate) && ExpandedProductParsedResult.equalsOrNull(this.bestBeforeDate, ((ExpandedProductParsedResult)object).bestBeforeDate) && ExpandedProductParsedResult.equalsOrNull(this.expirationDate, ((ExpandedProductParsedResult)object).expirationDate) && ExpandedProductParsedResult.equalsOrNull(this.weight, ((ExpandedProductParsedResult)object).weight) && ExpandedProductParsedResult.equalsOrNull(this.weightType, ((ExpandedProductParsedResult)object).weightType) && ExpandedProductParsedResult.equalsOrNull(this.weightIncrement, ((ExpandedProductParsedResult)object).weightIncrement) && ExpandedProductParsedResult.equalsOrNull(this.price, ((ExpandedProductParsedResult)object).price) && ExpandedProductParsedResult.equalsOrNull(this.priceIncrement, ((ExpandedProductParsedResult)object).priceIncrement) && ExpandedProductParsedResult.equalsOrNull(this.priceCurrency, ((ExpandedProductParsedResult)object).priceCurrency) && ExpandedProductParsedResult.equalsOrNull(this.uncommonAIs, ((ExpandedProductParsedResult)object).uncommonAIs)) break block3;
            }
            return false;
        }
        return true;
    }

    public String getBestBeforeDate() {
        return this.bestBeforeDate;
    }

    @Override
    public String getDisplayResult() {
        return String.valueOf(this.rawText);
    }

    public String getExpirationDate() {
        return this.expirationDate;
    }

    public String getLotNumber() {
        return this.lotNumber;
    }

    public String getPackagingDate() {
        return this.packagingDate;
    }

    public String getPrice() {
        return this.price;
    }

    public String getPriceCurrency() {
        return this.priceCurrency;
    }

    public String getPriceIncrement() {
        return this.priceIncrement;
    }

    public String getProductID() {
        return this.productID;
    }

    public String getProductionDate() {
        return this.productionDate;
    }

    public String getRawText() {
        return this.rawText;
    }

    public String getSscc() {
        return this.sscc;
    }

    public Map<String, String> getUncommonAIs() {
        return this.uncommonAIs;
    }

    public String getWeight() {
        return this.weight;
    }

    public String getWeightIncrement() {
        return this.weightIncrement;
    }

    public String getWeightType() {
        return this.weightType;
    }

    public int hashCode() {
        return 0 ^ ExpandedProductParsedResult.hashNotNull(this.productID) ^ ExpandedProductParsedResult.hashNotNull(this.sscc) ^ ExpandedProductParsedResult.hashNotNull(this.lotNumber) ^ ExpandedProductParsedResult.hashNotNull(this.productionDate) ^ ExpandedProductParsedResult.hashNotNull(this.bestBeforeDate) ^ ExpandedProductParsedResult.hashNotNull(this.expirationDate) ^ ExpandedProductParsedResult.hashNotNull(this.weight) ^ ExpandedProductParsedResult.hashNotNull(this.weightType) ^ ExpandedProductParsedResult.hashNotNull(this.weightIncrement) ^ ExpandedProductParsedResult.hashNotNull(this.price) ^ ExpandedProductParsedResult.hashNotNull(this.priceIncrement) ^ ExpandedProductParsedResult.hashNotNull(this.priceCurrency) ^ ExpandedProductParsedResult.hashNotNull(this.uncommonAIs);
    }
}

