/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;

public class SymbolTable {
    private final int indexMask;
    private final String[] symbols;

    public SymbolTable(int n2) {
        this.indexMask = n2 - 1;
        this.symbols = new String[n2];
        this.addSymbol("$ref", 0, 4, "$ref".hashCode());
        this.addSymbol(JSON.DEFAULT_TYPE_KEY, 0, 5, JSON.DEFAULT_TYPE_KEY.hashCode());
    }

    public static int hash(char[] cArray, int n2, int n3) {
        int n4 = 0;
        int n5 = 0;
        while (n5 < n3) {
            n4 = n4 * 31 + cArray[n2];
            ++n5;
            ++n2;
        }
        return n4;
    }

    private static String subString(String string2, int n2, int n3) {
        char[] cArray = new char[n3];
        string2.getChars(n2, n2 + n3, cArray, 0);
        return new String(cArray);
    }

    /*
     * Enabled aggressive block sorting
     */
    public String addSymbol(String string2, int n2, int n3, int n4) {
        int n5 = n4 & this.indexMask;
        String string3 = this.symbols[n5];
        if (string3 != null) {
            if (n4 == string3.hashCode() && n3 == string3.length() && string2.startsWith(string3, n2)) {
                return string3;
            }
            return SymbolTable.subString(string2, n2, n3);
        }
        if (n3 != string2.length()) {
            string2 = SymbolTable.subString(string2, n2, n3);
        }
        this.symbols[n5] = string2 = string2.intern();
        return string2;
    }

    public String addSymbol(char[] cArray, int n2, int n3) {
        return this.addSymbol(cArray, n2, n3, SymbolTable.hash(cArray, n2, n3));
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public String addSymbol(char[] var1_1, int var2_2, int var3_3, int var4_4) {
        block4: {
            block3: {
                block5: {
                    var5_5 = var4_4 & this.indexMask;
                    var7_6 = this.symbols[var5_5];
                    if (var7_6 == null) break block4;
                    var6_7 = 1;
                    if (var4_4 != var7_6.hashCode() || var3_3 != var7_6.length()) break block5;
                    var5_5 = 0;
                    while (true) {
                        block7: {
                            block6: {
                                var4_4 = var6_7;
                                if (var5_5 >= var3_3) break block6;
                                if (var1_1 /* !! */ [var2_2 + var5_5] == var7_6.charAt(var5_5)) break block7;
                                var4_4 = 0;
                            }
lbl13:
                            // 2 sources

                            while (var4_4 != 0) {
                                return var7_6;
                            }
                            break block3;
                        }
                        ++var5_5;
                    }
                }
                var4_4 = 0;
                ** GOTO lbl13
            }
            return new String(var1_1 /* !! */ , var2_2, var3_3);
        }
        var1_1 /* !! */  = (char[])new String(var1_1 /* !! */ , var2_2, var3_3).intern();
        this.symbols[var5_5] = var1_1 /* !! */ ;
        return var1_1 /* !! */ ;
    }
}

