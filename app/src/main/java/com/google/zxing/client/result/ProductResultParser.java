/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.ProductParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.oned.UPCEReader;

public final class ProductResultParser
extends ResultParser {
    /*
     * Enabled aggressive block sorting
     */
    @Override
    public ProductParsedResult parse(Result object) {
        BarcodeFormat barcodeFormat = ((Result)object).getBarcodeFormat();
        if (barcodeFormat != BarcodeFormat.UPC_A && barcodeFormat != BarcodeFormat.UPC_E && barcodeFormat != BarcodeFormat.EAN_8 && barcodeFormat != BarcodeFormat.EAN_13) {
            return null;
        }
        String string2 = ProductResultParser.getMassagedText((Result)object);
        if (!ProductResultParser.isStringOfDigits(string2, string2.length())) return null;
        if (barcodeFormat == BarcodeFormat.UPC_E && string2.length() == 8) {
            object = UPCEReader.convertUPCEtoUPCA(string2);
            return new ProductParsedResult(string2, (String)object);
        }
        object = string2;
        return new ProductParsedResult(string2, (String)object);
    }
}

