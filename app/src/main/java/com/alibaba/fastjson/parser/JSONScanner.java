/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.SymbolTable;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.IOUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.TimeZone;

public final class JSONScanner
extends JSONLexerBase {
    public static final int ISO8601_LEN_0 = "0000-00-00".length();
    public static final int ISO8601_LEN_1 = "0000-00-00T00:00:00".length();
    public static final int ISO8601_LEN_2 = "0000-00-00T00:00:00.000".length();
    private final int len;
    private final String text;

    public JSONScanner(String string2) {
        this(string2, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONScanner(String string2, int n2) {
        super(n2);
        this.text = string2;
        this.len = this.text.length();
        this.bp = -1;
        this.next();
        if (this.ch == '\ufeff') {
            this.next();
        }
    }

    public JSONScanner(char[] cArray, int n2) {
        this(cArray, n2, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONScanner(char[] cArray, int n2, int n3) {
        this(new String(cArray, 0, n2), n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    static boolean charArrayCompare(String string2, int n2, char[] cArray) {
        int n3 = cArray.length;
        if (n3 + n2 <= string2.length()) {
            int n4 = 0;
            while (true) {
                if (n4 >= n3) {
                    return true;
                }
                if (cArray[n4] != string2.charAt(n2 + n4)) break;
                ++n4;
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    static boolean checkDate(char c2, char c3, char c4, char c5, char c6, char c7, int n2, int n3) {
        if (c2 != '1' && c2 != '2' || c3 < '0' || c3 > '9' || c4 < '0' || c4 > '9' || c5 < '0' || c5 > '9') return false;
        if (c6 == '0') {
            if (c7 < '1' || c7 > '9') return false;
        } else {
            if (c6 != '1') return false;
            if (c7 != '0' && c7 != '1' && c7 != '2') {
                return false;
            }
        }
        if (n2 == 48) {
            if (n3 < 49 || n3 > 57) return false;
            return true;
        }
        if (n2 == 49 || n2 == 50) {
            if (n3 < 48) return false;
            if (n3 <= 57) return true;
            return false;
        }
        if (n2 != 51) {
            return false;
        }
        if (n3 != 48 && n3 != 49) return false;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean checkTime(char c2, char c3, char c4, char c5, char c6, char c7) {
        if (c2 == '0') {
            if (c3 < '0' || c3 > '9') {
                return false;
            }
        } else if (c2 == '1') {
            if (c3 < '0' || c3 > '9') return false;
        } else {
            if (c2 != '2' || c3 < '0') return false;
            if (c3 > '4') {
                return false;
            }
        }
        if (c4 >= '0' && c4 <= '5') {
            if (c5 < '0' || c5 > '9') return false;
        } else {
            if (c4 != '6') return false;
            if (c5 != '0') {
                return false;
            }
        }
        if (c6 >= '0' && c6 <= '5') {
            if (c7 < '0' || c7 > '9') return false;
            return true;
        }
        if (c6 != '6') {
            return false;
        }
        if (c7 != '0') return false;
        return true;
    }

    private void setCalendar(char c2, char c3, char c4, char c5, char c6, char c7, char c8, char c9) {
        this.calendar = Calendar.getInstance(this.timeZone, this.locale);
        this.calendar.set(1, (c2 - 48) * 1000 + (c3 - 48) * 100 + (c4 - 48) * 10 + (c5 - 48));
        this.calendar.set(2, (c6 - 48) * 10 + (c7 - 48) - 1);
        this.calendar.set(5, (c8 - 48) * 10 + (c9 - 48));
    }

    @Override
    public final String addSymbol(int n2, int n3, int n4, SymbolTable symbolTable) {
        return symbolTable.addSymbol(this.text, n2, n3, n4);
    }

    @Override
    protected final void arrayCopy(int n2, char[] cArray, int n3, int n4) {
        this.text.getChars(n2, n2 + n4, cArray, n3);
    }

    @Override
    public byte[] bytesValue() {
        return IOUtils.decodeBase64(this.text, this.np + 1, this.sp);
    }

    @Override
    public final boolean charArrayCompare(char[] cArray) {
        return JSONScanner.charArrayCompare(this.text, this.bp, cArray);
    }

    @Override
    public final char charAt(int n2) {
        if (n2 >= this.len) {
            return '\u001a';
        }
        return this.text.charAt(n2);
    }

    @Override
    protected final void copyTo(int n2, int n3, char[] cArray) {
        this.text.getChars(n2, n2 + n3, cArray, 0);
    }

    @Override
    public final int indexOf(char c2, int n2) {
        return this.text.indexOf(c2, n2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public String info() {
        String string2;
        StringBuilder stringBuilder = new StringBuilder().append("pos ").append(this.bp).append(", json : ");
        if (this.text.length() < 65536) {
            string2 = this.text;
            return stringBuilder.append(string2).toString();
        }
        string2 = this.text.substring(0, 65536);
        return stringBuilder.append(string2).toString();
    }

    @Override
    public boolean isEOF() {
        return this.bp == this.len || this.ch == '\u001a' && this.bp + 1 == this.len;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final char next() {
        int n2;
        this.bp = n2 = this.bp + 1;
        char c2 = n2 >= this.len ? (char)'\u001a' : (char)this.text.charAt(n2);
        this.ch = c2;
        return c2;
    }

    @Override
    public final String numberString() {
        int n2;
        block3: {
            int n3;
            block2: {
                char c2 = this.charAt(this.np + this.sp - 1);
                n3 = this.sp;
                if (c2 == 'L' || c2 == 'S' || c2 == 'B' || c2 == 'F') break block2;
                n2 = n3;
                if (c2 != 'D') break block3;
            }
            n2 = n3 - 1;
        }
        return this.subString(this.np, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean scanFieldBoolean(char[] cArray) {
        boolean bl2;
        this.matchStat = 0;
        if (!JSONScanner.charArrayCompare(this.text, this.bp, cArray)) {
            this.matchStat = -2;
            return false;
        }
        int n2 = this.bp + cArray.length;
        int n3 = n2 + 1;
        if ((n2 = (int)this.charAt(n2)) == 116) {
            n2 = n3 + 1;
            if (this.charAt(n3) != 'r') {
                this.matchStat = -1;
                return false;
            }
            n3 = n2 + 1;
            if (this.charAt(n2) != 'u') {
                this.matchStat = -1;
                return false;
            }
            if (this.charAt(n3) != 'e') {
                this.matchStat = -1;
                return false;
            }
            this.bp = n3 + 1;
            n3 = this.charAt(this.bp);
            bl2 = true;
        } else {
            if (n2 != 102) {
                this.matchStat = -1;
                return false;
            }
            n2 = n3 + 1;
            if (this.charAt(n3) != 'a') {
                this.matchStat = -1;
                return false;
            }
            n3 = n2 + 1;
            if (this.charAt(n2) != 'l') {
                this.matchStat = -1;
                return false;
            }
            n2 = n3 + 1;
            if (this.charAt(n3) != 's') {
                this.matchStat = -1;
                return false;
            }
            if (this.charAt(n2) != 'e') {
                this.matchStat = -1;
                return false;
            }
            this.bp = n2 + 1;
            n3 = this.charAt(this.bp);
            bl2 = false;
        }
        if (n3 == 44) {
            this.bp = n3 = this.bp + 1;
            this.ch = this.charAt(n3);
            this.matchStat = 3;
            this.token = 16;
            return bl2;
        }
        if (n3 != 125) {
            this.matchStat = -1;
            return false;
        }
        this.bp = n3 = this.bp + 1;
        if ((n3 = (int)this.charAt(n3)) == 44) {
            this.token = 16;
            this.bp = n3 = this.bp + 1;
            this.ch = this.charAt(n3);
        } else if (n3 == 93) {
            this.token = 15;
            this.bp = n3 = this.bp + 1;
            this.ch = this.charAt(n3);
        } else if (n3 == 125) {
            this.token = 13;
            this.bp = n3 = this.bp + 1;
            this.ch = this.charAt(n3);
        } else {
            if (n3 != 26) {
                this.matchStat = -1;
                return false;
            }
            this.token = 20;
        }
        this.matchStat = 4;
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int scanFieldInt(char[] cArray) {
        int n2;
        this.matchStat = 0;
        int n3 = this.bp;
        char c2 = this.ch;
        if (!JSONScanner.charArrayCompare(this.text, this.bp, cArray)) {
            this.matchStat = -2;
            return 0;
        }
        int n4 = this.bp + cArray.length;
        int n5 = n4 + 1;
        n4 = this.charAt(n4);
        boolean bl2 = false;
        if (n4 == 45) {
            n4 = n5 + 1;
            n2 = this.charAt(n5);
            bl2 = true;
            n5 = n4;
            n4 = n2;
        }
        if (n4 >= 48 && n4 <= 57) {
            n4 -= 48;
            while (true) {
                n2 = n5 + 1;
                if ((n5 = (int)this.charAt(n5)) < 48 || n5 > 57) break;
                n4 = n4 * 10 + (n5 - 48);
                n5 = n2;
            }
            if (n5 == 46) {
                this.matchStat = -1;
                return 0;
            }
            if (n4 < 0) {
                this.matchStat = -1;
                return 0;
            }
            if (n5 == 44 || n5 == 125) {
                this.bp = n2 - 1;
            }
            if (n5 == 44) {
                this.bp = n5 = this.bp + 1;
                this.ch = this.charAt(n5);
                this.matchStat = 3;
                this.token = 16;
                n5 = n4;
                if (!bl2) return n5;
                return -n4;
            }
        } else {
            this.matchStat = -1;
            return 0;
        }
        if (n5 == 125) {
            this.bp = n5 = this.bp + 1;
            if ((n5 = (int)this.charAt(n5)) == 44) {
                this.token = 16;
                this.bp = n5 = this.bp + 1;
                this.ch = this.charAt(n5);
            } else if (n5 == 93) {
                this.token = 15;
                this.bp = n5 = this.bp + 1;
                this.ch = this.charAt(n5);
            } else if (n5 == 125) {
                this.token = 13;
                this.bp = n5 = this.bp + 1;
                this.ch = this.charAt(n5);
            } else {
                if (n5 != 26) {
                    this.bp = n3;
                    this.ch = c2;
                    this.matchStat = -1;
                    return 0;
                }
                this.token = 20;
            }
            this.matchStat = 4;
        }
        n5 = n4;
        if (!bl2) return n5;
        return -n4;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public long scanFieldLong(char[] cArray) {
        long l2;
        long l3;
        this.matchStat = 0;
        int n2 = this.bp;
        char c2 = this.ch;
        if (!JSONScanner.charArrayCompare(this.text, this.bp, cArray)) {
            this.matchStat = -2;
            return 0L;
        }
        int n3 = this.bp + cArray.length;
        int n4 = n3 + 1;
        int n5 = this.charAt(n3);
        n3 = 0;
        if (n5 == 45) {
            n5 = n4 + 1;
            char c3 = this.charAt(n4);
            n3 = 1;
            n4 = n5;
            n5 = c3;
        }
        if (n5 >= 48 && n5 <= 57) {
            l3 = n5 - 48;
            while (true) {
                n5 = n4 + 1;
                if ((n4 = (int)this.charAt(n4)) < 48 || n4 > 57) break;
                l3 = 10L * l3 + (long)(n4 - 48);
                n4 = n5;
            }
            if (n4 == 46) {
                this.matchStat = -1;
                return 0L;
            }
            if (n4 == 44 || n4 == 125) {
                this.bp = n5 - 1;
            }
            if (l3 < 0L) {
                this.bp = n2;
                this.ch = c2;
                this.matchStat = -1;
                return 0L;
            }
        } else {
            this.bp = n2;
            this.ch = c2;
            this.matchStat = -1;
            return 0L;
        }
        if (n4 == 44) {
            this.bp = n4 = this.bp + 1;
            this.ch = this.charAt(n4);
            this.matchStat = 3;
            this.token = 16;
            l2 = l3;
            if (n3 == 0) return l2;
            return -l3;
        }
        if (n4 != 125) {
            this.matchStat = -1;
            return 0L;
        }
        this.bp = n4 = this.bp + 1;
        if ((n4 = (int)this.charAt(n4)) == 44) {
            this.token = 16;
            this.bp = n4 = this.bp + 1;
            this.ch = this.charAt(n4);
        } else if (n4 == 93) {
            this.token = 15;
            this.bp = n4 = this.bp + 1;
            this.ch = this.charAt(n4);
        } else if (n4 == 125) {
            this.token = 13;
            this.bp = n4 = this.bp + 1;
            this.ch = this.charAt(n4);
        } else {
            if (n4 != 26) {
                this.bp = n2;
                this.ch = c2;
                this.matchStat = -1;
                return 0L;
            }
            this.token = 20;
        }
        this.matchStat = 4;
        l2 = l3;
        if (n3 == 0) return l2;
        return -l3;
    }

    /*
     * Handled impossible loop by adding 'first' condition
     * Enabled aggressive block sorting
     */
    @Override
    public String scanFieldString(char[] cArray) {
        char c2;
        this.matchStat = 0;
        int n2 = this.bp;
        char c3 = this.ch;
        if (!JSONScanner.charArrayCompare(this.text, this.bp, cArray)) {
            this.matchStat = -2;
            return this.stringDefaultValue();
        }
        int n3 = this.bp + cArray.length;
        int n4 = n3 + 1;
        if (this.charAt(n3) != '\"') {
            this.matchStat = -1;
            return this.stringDefaultValue();
        }
        n3 = this.indexOf('\"', n4);
        if (n3 == -1) {
            throw new JSONException("unclosed str");
        }
        String string2 = this.subString(n4, n3 - n4);
        n4 = n3;
        String string3 = string2;
        boolean bl2 = true;
        while (true) {
            block18: {
                block17: {
                    if (bl2 && !(bl2 = false) && string2.indexOf(92) == -1) break block17;
                    int n5 = 0;
                    for (n4 = n3 - 1; n4 >= 0 && this.charAt(n4) == '\\'; ++n5, --n4) {
                    }
                    if (n5 % 2 != 0) break block18;
                    n4 = n3 - (this.bp + cArray.length + 1);
                    string3 = JSONScanner.readString(this.sub_chars(this.bp + cArray.length + 1, n4), n4);
                    n4 = n3;
                }
                if ((c2 = this.charAt(n4 + 1)) != ',' && c2 != '}') {
                    this.matchStat = -1;
                    return this.stringDefaultValue();
                }
                this.bp = n4 + 1;
                this.ch = c2;
                if (c2 != ',') break;
                this.bp = n3 = this.bp + 1;
                this.ch = this.charAt(n3);
                this.matchStat = 3;
                return string3;
            }
            n3 = this.indexOf('\"', n3 + 1);
        }
        if (c2 != '}') {
            this.matchStat = -1;
            return this.stringDefaultValue();
        }
        this.bp = n3 = this.bp + 1;
        if ((n3 = (int)this.charAt(n3)) == 44) {
            this.token = 16;
            this.bp = n3 = this.bp + 1;
            this.ch = this.charAt(n3);
        } else if (n3 == 93) {
            this.token = 15;
            this.bp = n3 = this.bp + 1;
            this.ch = this.charAt(n3);
        } else if (n3 == 125) {
            this.token = 13;
            this.bp = n3 = this.bp + 1;
            this.ch = this.charAt(n3);
        } else {
            if (n3 != 26) {
                this.bp = n2;
                this.ch = c3;
                this.matchStat = -1;
                return this.stringDefaultValue();
            }
            this.token = 20;
        }
        this.matchStat = 4;
        return string3;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Collection<String> scanFieldStringArray(char[] var1_1, Class<?> var2_3) {
        block28: {
            block29: {
                block27: {
                    block31: {
                        this.matchStat = 0;
                        if (!JSONScanner.charArrayCompare(this.text, this.bp, var1_1 /* !! */ )) {
                            this.matchStat = -2;
                            return null;
                        }
                        if (var2_3.isAssignableFrom(HashSet.class)) {
                            var2_3 = new HashSet<E>();
                        } else if (var2_3.isAssignableFrom(ArrayList.class)) {
                            var2_3 = new ArrayList<E>();
                        } else {
                            try {
                                var2_3 = (Collection)var2_3.newInstance();
                            }
                            catch (Exception var1_2) {
                                throw new JSONException(var1_2.getMessage(), var1_2);
                            }
                        }
                        var4_4 = this.bp + var1_1 /* !! */ .length;
                        var5_5 = var4_4 + 1;
                        if (this.charAt(var4_4) != '[') break block29;
                        var4_4 = this.charAt(var5_5);
                        ++var5_5;
                        block2: while (var4_4 == 34) {
                            var4_4 = this.indexOf('\"', var5_5);
                            if (var4_4 == -1) {
                                throw new JSONException("unclosed str");
                            }
                            var8_8 = this.subString(var5_5, var4_4 - var5_5);
                            var6_6 = var4_4;
                            var1_1 /* !! */  = (char[])var8_8;
                            if (var8_8.indexOf(92) == -1) ** GOTO lbl37
                            while (true) {
                                block30: {
                                    var7_7 = 0;
                                    for (var6_6 = var4_4 - 1; var6_6 >= 0 && this.charAt(var6_6) == '\\'; ++var7_7, --var6_6) {
                                    }
                                    if (var7_7 % 2 != 0) break block30;
                                    var6_6 = var4_4 - var5_5;
                                    var1_1 /* !! */  = (char[])JSONScanner.readString(this.sub_chars(var5_5, var6_6), var6_6);
                                    var6_6 = var4_4;
lbl37:
                                    // 2 sources

                                    var5_5 = var6_6 + 1;
                                    var4_4 = var5_5 + 1;
                                    var5_5 = this.charAt(var5_5);
                                    var2_3.add(var1_1 /* !! */ );
lbl42:
                                    // 2 sources

                                    while (var5_5 == 44) {
                                        var5_5 = this.charAt(var4_4);
                                        var6_6 = var4_4 + 1;
                                        var4_4 = var5_5;
                                        var5_5 = var6_6;
                                        continue block2;
                                    }
                                    break block27;
                                }
                                var4_4 = this.indexOf('\"', var4_4 + 1);
                            }
                        }
                        if (var4_4 != 110 || !this.text.startsWith("ull", var5_5)) break block31;
                        var4_4 = (var5_5 += 3) + 1;
                        var5_5 = this.charAt(var5_5);
                        var2_3.add(null);
                        ** GOTO lbl42
                    }
                    if (var4_4 != 93 || var2_3.size() != 0) {
                        this.matchStat = -1;
                        return null;
                    }
                    var4_4 = var5_5 + 1;
                    var6_6 = this.charAt(var5_5);
                    var1_1 /* !! */  = (char[])var2_3;
lbl65:
                    // 3 sources

                    while (true) {
                        this.bp = var4_4;
                        if (var6_6 == 44) {
                            this.ch = this.charAt(this.bp);
                            this.matchStat = 3;
                            return var1_1 /* !! */ ;
                        }
                        break block28;
                        break;
                    }
                }
                if (var5_5 != 93) {
                    this.matchStat = -1;
                    return null;
                }
                var5_5 = var4_4 + 1;
                var3_9 = this.charAt(var4_4);
                while (true) {
                    var6_6 = var3_9;
                    var4_4 = ++var5_5;
                    var1_1 /* !! */  = (char[])var2_3;
                    if (!JSONScanner.isWhitespace(var3_9)) ** GOTO lbl65
                    var3_9 = this.charAt(var5_5);
                }
            }
            if (!this.text.startsWith("ull", var5_5)) {
                this.matchStat = -1;
                return null;
            }
            var4_4 = var5_5 + 3;
            var6_6 = this.charAt(var4_4);
            var1_1 /* !! */  = null;
            ++var4_4;
            ** while (true)
        }
        if (var6_6 != 125) {
            this.matchStat = -1;
            return null;
        }
        var3_9 = this.charAt(this.bp);
        do {
            block33: {
                block32: {
                    if (var3_9 == ',') {
                        this.token = 16;
                        this.bp = var4_4 = this.bp + 1;
                        this.ch = this.charAt(var4_4);
lbl104:
                        // 4 sources

                        while (true) {
                            this.matchStat = 4;
                            return var1_1 /* !! */ ;
                        }
                    }
                    if (var3_9 != ']') break block32;
                    this.token = 15;
                    this.bp = var4_4 = this.bp + 1;
                    this.ch = this.charAt(var4_4);
                    ** GOTO lbl104
                }
                if (var3_9 != '}') break block33;
                this.token = 13;
                this.bp = var4_4 = this.bp + 1;
                this.ch = this.charAt(var4_4);
                ** GOTO lbl104
            }
            if (var3_9 == '\u001a') {
                this.token = 20;
                this.ch = var3_9;
                ** continue;
            }
            var5_5 = 0;
            while (JSONScanner.isWhitespace(var3_9)) {
                var6_6 = var4_4 + 1;
                var3_9 = this.charAt(var4_4);
                this.bp = var6_6;
                var5_5 = 1;
                var4_4 = var6_6;
            }
        } while (var5_5 != 0);
        this.matchStat = -1;
        return null;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public String scanFieldSymbol(char[] object, SymbolTable symbolTable) {
        this.matchStat = 0;
        if (!JSONScanner.charArrayCompare(this.text, this.bp, object)) {
            this.matchStat = -2;
            return null;
        }
        int n2 = this.bp + ((char[])object).length;
        int n3 = n2 + 1;
        if (this.charAt(n2) != '\"') {
            this.matchStat = -1;
            return null;
        }
        n2 = 0;
        int n4 = n3;
        while (true) {
            int n5 = n4 + 1;
            if ((n4 = (int)this.charAt(n4)) != 34) {
                n2 = n2 * 31 + n4;
                if (n4 == 92) {
                    this.matchStat = -1;
                    return null;
                }
            } else {
                void var2_3;
                char c2;
                this.bp = n5;
                this.ch = c2 = this.charAt(this.bp);
                String string2 = var2_3.addSymbol(this.text, n3, n5 - n3 - 1, n2);
                if (c2 == ',') {
                    this.bp = n2 = this.bp + 1;
                    this.ch = this.charAt(n2);
                    this.matchStat = 3;
                    return string2;
                }
                if (c2 != '}') {
                    this.matchStat = -1;
                    return null;
                }
                this.bp = n2 = this.bp + 1;
                if ((n2 = (int)this.charAt(n2)) == 44) {
                    this.token = 16;
                    this.bp = n2 = this.bp + 1;
                    this.ch = this.charAt(n2);
                } else if (n2 == 93) {
                    this.token = 15;
                    this.bp = n2 = this.bp + 1;
                    this.ch = this.charAt(n2);
                } else if (n2 == 125) {
                    this.token = 13;
                    this.bp = n2 = this.bp + 1;
                    this.ch = this.charAt(n2);
                } else {
                    if (n2 != 26) {
                        this.matchStat = -1;
                        return null;
                    }
                    this.token = 20;
                }
                this.matchStat = 4;
                return string2;
            }
            n4 = n5;
        }
    }

    public boolean scanISO8601DateIfMatch() {
        return this.scanISO8601DateIfMatch(true);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public boolean scanISO8601DateIfMatch(boolean var1_1) {
        block49: {
            block50: {
                block51: {
                    var13_2 = this.len - this.bp;
                    if (!var1_1 && var13_2 > 13) {
                        var10_3 = this.charAt(this.bp);
                        var11_4 = this.charAt(this.bp + 1);
                        var12_5 = this.charAt(this.bp + 2);
                        var14_6 = this.charAt(this.bp + 3);
                        var15_7 = this.charAt(this.bp + 4);
                        var16_8 = this.charAt(this.bp + 5);
                        var17_9 = this.charAt(this.bp + var13_2 - 1);
                        var18_10 = this.charAt(this.bp + var13_2 - 2);
                        if (var10_3 == 47 && var11_4 == 68 && var12_5 == 97 && var14_6 == 116 && var15_7 == 'e' && var16_8 == '(' && var17_9 == '/' && var18_10 == ')') {
                            var11_4 = -1;
                            block0: for (var10_3 = 6; var10_3 < var13_2; ++var10_3) {
                                var14_6 = this.charAt(this.bp + var10_3);
                                if (var14_6 == 43) {
                                    var12_5 = var10_3;
                                    while (true) {
                                        var11_4 = var12_5;
                                        continue block0;
                                        break;
                                    }
                                }
                                if (var14_6 < 48) break;
                                var12_5 = var11_4;
                                if (var14_6 <= 57) ** continue;
                            }
                            if (var11_4 == -1) {
                                return false;
                            }
                            var10_3 = this.bp + 6;
                            var19_11 = Long.parseLong(this.subString(var10_3, var11_4 - var10_3));
                            this.calendar = Calendar.getInstance(this.timeZone, this.locale);
                            this.calendar.setTimeInMillis(var19_11);
                            this.token = 5;
                            return true;
                        }
                    }
                    if (var13_2 == 8 || var13_2 == 14 || var13_2 == 17) {
                        if (var1_1) {
                            return false;
                        }
                        var2_12 = this.charAt(this.bp);
                        if (!JSONScanner.checkDate(var2_12, var3_14 = this.charAt(this.bp + 1), var4_16 = this.charAt(this.bp + 2), var5_18 = this.charAt(this.bp + 3), var6_20 = this.charAt(this.bp + 4), var7_22 = this.charAt(this.bp + 5), var8_24 = this.charAt(this.bp + 6), var9_26 = this.charAt(this.bp + 7))) {
                            return false;
                        }
                        this.setCalendar(var2_12, var3_14, var4_16, var5_18, var6_20, var7_22, var8_24, var9_26);
                        if (var13_2 != 8) {
                            var2_12 = this.charAt(this.bp + 8);
                            if (!this.checkTime(var2_12, var3_14 = this.charAt(this.bp + 9), var4_16 = this.charAt(this.bp + 10), var5_18 = this.charAt(this.bp + 11), var6_20 = this.charAt(this.bp + 12), var7_22 = this.charAt(this.bp + 13))) {
                                return false;
                            }
                            if (var13_2 == 17) {
                                var10_3 = this.charAt(this.bp + 14);
                                var11_4 = this.charAt(this.bp + 15);
                                var12_5 = this.charAt(this.bp + 16);
                                if (var10_3 < 48 || var10_3 > 57) {
                                    return false;
                                }
                                if (var11_4 < 48 || var11_4 > 57) {
                                    return false;
                                }
                                if (var12_5 < 48 || var12_5 > 57) {
                                    return false;
                                }
                                var10_3 = (var10_3 - 48) * 100 + (var11_4 - 48) * 10 + (var12_5 - 48);
lbl53:
                                // 2 sources

                                while (true) {
                                    var14_6 = (var2_12 - 48) * 10 + (var3_14 - 48);
                                    var12_5 = (var4_16 - 48) * 10 + (var5_18 - 48);
                                    var13_2 = (var6_20 - 48) * 10 + (var7_22 - 48);
                                    var11_4 = var10_3;
                                    var10_3 = var14_6;
lbl59:
                                    // 2 sources

                                    while (true) {
                                        this.calendar.set(11, var10_3);
                                        this.calendar.set(12, var12_5);
                                        this.calendar.set(13, var13_2);
                                        this.calendar.set(14, var11_4);
                                        this.token = 5;
                                        return true;
                                    }
                                    break;
                                }
                            }
                            var10_3 = 0;
                            ** continue;
                        }
                        var10_3 = 0;
                        var12_5 = 0;
                        var13_2 = 0;
                        var11_4 = 0;
                        ** continue;
                    }
                    if (var13_2 < JSONScanner.ISO8601_LEN_0) {
                        return false;
                    }
                    if (this.charAt(this.bp + 4) != '-') {
                        return false;
                    }
                    if (this.charAt(this.bp + 7) != '-') {
                        return false;
                    }
                    var2_13 = this.charAt(this.bp);
                    if (!JSONScanner.checkDate(var2_13, var3_15 = this.charAt(this.bp + 1), var4_17 = this.charAt(this.bp + 2), var5_19 = this.charAt(this.bp + 3), var6_21 = this.charAt(this.bp + 5), var7_23 = this.charAt(this.bp + 6), var8_25 = this.charAt(this.bp + 8), var9_27 = this.charAt(this.bp + 9))) {
                        return false;
                    }
                    this.setCalendar(var2_13, var3_15, var4_17, var5_19, var6_21, var7_23, var8_25, var9_27);
                    var2_13 = this.charAt(this.bp + 10);
                    if (var2_13 == 'T' || var2_13 == ' ' && !var1_1) {
                        if (var13_2 < JSONScanner.ISO8601_LEN_1) {
                            return false;
                        }
                    } else {
                        if (var2_13 == '\"' || var2_13 == '\u001a') {
                            this.calendar.set(11, 0);
                            this.calendar.set(12, 0);
                            this.calendar.set(13, 0);
                            this.calendar.set(14, 0);
                            this.bp = var10_3 = this.bp + 10;
                            this.ch = this.charAt(var10_3);
                            this.token = 5;
                            return true;
                        }
                        if (var2_13 == '+' || var2_13 == '-') {
                            if (this.len == 16) {
                                if (this.charAt(this.bp + 13) != ':' || this.charAt(this.bp + 14) != '0' || this.charAt(this.bp + 15) != '0') {
                                    return false;
                                }
                                this.setTime('0', '0', '0', '0', '0', '0');
                                this.calendar.set(14, 0);
                                this.setTimeZone(var2_13, this.charAt(this.bp + 11), this.charAt(this.bp + 12));
                                return true;
                            }
                            return false;
                        }
                        return false;
                    }
                    if (this.charAt(this.bp + 13) != ':') {
                        return false;
                    }
                    if (this.charAt(this.bp + 16) != ':') {
                        return false;
                    }
                    var2_13 = this.charAt(this.bp + 11);
                    if (!this.checkTime(var2_13, var3_15 = this.charAt(this.bp + 12), var4_17 = this.charAt(this.bp + 14), var5_19 = this.charAt(this.bp + 15), var6_21 = this.charAt(this.bp + 17), var7_23 = this.charAt(this.bp + 18))) {
                        return false;
                    }
                    this.setTime(var2_13, var3_15, var4_17, var5_19, var6_21, var7_23);
                    var10_3 = this.charAt(this.bp + 19);
                    if (var10_3 == 46) {
                        if (var13_2 < JSONScanner.ISO8601_LEN_2) {
                            return false;
                        }
                    } else {
                        this.calendar.set(14, 0);
                        this.bp = var11_4 = this.bp + 19;
                        this.ch = this.charAt(var11_4);
                        this.token = 5;
                        if (var10_3 == 90 && this.calendar.getTimeZone().getRawOffset() != 0 && (var21_28 /* !! */  = TimeZone.getAvailableIDs(0)).length > 0) {
                            var21_28 /* !! */  = TimeZone.getTimeZone(var21_28 /* !! */ [0]);
                            this.calendar.setTimeZone((TimeZone)var21_28 /* !! */ );
                        }
                        return true;
                    }
                    var10_3 = this.charAt(this.bp + 20);
                    if (var10_3 < 48 || var10_3 > 57) {
                        return false;
                    }
                    var12_5 = var10_3 - 48;
                    var13_2 = 1;
                    var14_6 = this.charAt(this.bp + 21);
                    var11_4 = var12_5;
                    var10_3 = var13_2;
                    if (var14_6 >= 48) {
                        var11_4 = var12_5;
                        var10_3 = var13_2;
                        if (var14_6 <= 57) {
                            var11_4 = var12_5 * 10 + (var14_6 - 48);
                            var10_3 = 2;
                        }
                    }
                    var13_2 = var11_4;
                    var12_5 = var10_3;
                    if (var10_3 == 2) {
                        var14_6 = this.charAt(this.bp + 22);
                        var13_2 = var11_4;
                        var12_5 = var10_3;
                        if (var14_6 >= 48) {
                            var13_2 = var11_4;
                            var12_5 = var10_3;
                            if (var14_6 <= 57) {
                                var13_2 = var11_4 * 10 + (var14_6 - 48);
                                var12_5 = 3;
                            }
                        }
                    }
                    this.calendar.set(14, var13_2);
                    var10_3 = 0;
                    var2_13 = this.charAt(this.bp + 20 + var12_5);
                    if (var2_13 != '+' && var2_13 != '-') break block50;
                    var3_15 = this.charAt(this.bp + 20 + var12_5 + 1);
                    if (var3_15 < '0' || var3_15 > '1') {
                        return false;
                    }
                    var4_17 = this.charAt(this.bp + 20 + var12_5 + 2);
                    if (var4_17 < '0' || var4_17 > '9') {
                        return false;
                    }
                    var10_3 = this.charAt(this.bp + 20 + var12_5 + 3);
                    if (var10_3 == 58) {
                        if (this.charAt(this.bp + 20 + var12_5 + 4) != '0') {
                            return false;
                        }
                        if (this.charAt(this.bp + 20 + var12_5 + 5) != '0') {
                            return false;
                        }
                        var10_3 = 6;
lbl171:
                        // 3 sources

                        while (true) {
                            this.setTimeZone(var2_13, var3_15, var4_17);
lbl173:
                            // 5 sources

                            while ((var11_4 = (int)this.charAt(this.bp + (var12_5 + 20 + var10_3))) != 26 && var11_4 != 34) {
                                return false;
                            }
                            break block49;
                            break;
                        }
                    }
                    if (var10_3 != 48) break block51;
                    if (this.charAt(this.bp + 20 + var12_5 + 4) != '0') {
                        return false;
                    }
                    var10_3 = 5;
                    ** GOTO lbl171
                }
                var10_3 = 3;
                ** while (true)
            }
            if (var2_13 != 'Z') ** GOTO lbl173
            var10_3 = var11_4 = 1;
            if (this.calendar.getTimeZone().getRawOffset() == 0) ** GOTO lbl173
            var21_29 /* !! */  = TimeZone.getAvailableIDs(0);
            var10_3 = var11_4;
            if (var21_29 /* !! */ .length <= 0) ** GOTO lbl173
            var21_29 /* !! */  = TimeZone.getTimeZone(var21_29 /* !! */ [0]);
            this.calendar.setTimeZone((TimeZone)var21_29 /* !! */ );
            var10_3 = var11_4;
            ** GOTO lbl173
        }
        this.bp = var10_3 = this.bp + (var12_5 + 20 + var10_3);
        this.ch = this.charAt(var10_3);
        this.token = 5;
        return true;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public final int scanInt(char var1_1) {
        block10: {
            block11: {
                this.matchStat = 0;
                var3_2 = this.bp;
                var4_3 = var3_2 + 1;
                if ((var3_2 = (int)this.charAt(var3_2)) == 45) {
                    var5_4 = true;
lbl6:
                    // 2 sources

                    while (true) {
                        if (var5_4) {
                            var3_2 = var4_3 + 1;
                            var6_5 = this.charAt(var4_3);
                            var4_3 = var3_2;
                            var3_2 = var6_5;
                        }
                        if (var3_2 < 48 || var3_2 > 57) break block10;
                        var3_2 -= 48;
                        while (true) {
                            var6_5 = var4_3 + 1;
                            var2_6 = this.charAt(var4_3);
                            if (var2_6 >= '0' && var2_6 <= '9') {
                                var3_2 = var3_2 * 10 + (var2_6 - 48);
                                var4_3 = var6_5;
                                continue;
                            }
                            break block11;
                            break;
                        }
                        break;
                    }
                }
                var5_4 = false;
                ** while (true)
            }
            if (var2_6 == '.') {
                this.matchStat = -1;
                return 0;
            }
            if (var3_2 < 0) {
                this.matchStat = -1;
                return 0;
            }
            ** GOTO lbl38
        }
        this.matchStat = -1;
        return 0;
        while (JSONScanner.isWhitespace(var2_6)) {
            var2_6 = this.charAt(var6_5);
            ++var6_5;
lbl38:
            // 2 sources

            if (var2_6 != var1_1) continue;
            this.bp = var6_5;
            this.ch = this.charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            var1_1 = (char)var3_2;
            if (var5_4) {
                var1_1 = (char)(-var3_2);
            }
            return var1_1;
        }
        this.matchStat = -1;
        var1_1 = (char)var3_2;
        if (var5_4) {
            var1_1 = (char)(-var3_2);
        }
        return var1_1;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public long scanLong(char var1_1) {
        block9: {
            block10: {
                this.matchStat = 0;
                var4_2 = this.bp;
                var3_3 = var4_2 + 1;
                var5_4 = this.charAt(var4_2);
                if (var5_4 == 45) {
                    var4_2 = 1;
lbl7:
                    // 2 sources

                    while (true) {
                        if (var4_2 != 0) {
                            var5_4 = var3_3 + 1;
                            var6_5 = this.charAt(var3_3);
                            var3_3 = var5_4;
                            var5_4 = var6_5;
                        }
                        if (var5_4 < 48 || var5_4 > 57) break block9;
                        var7_6 = var5_4 - 48;
                        while (true) {
                            var5_4 = var3_3 + 1;
                            var2_7 = this.charAt(var3_3);
                            if (var2_7 >= '0' && var2_7 <= '9') {
                                var7_6 = 10L * var7_6 + (long)(var2_7 - 48);
                                var3_3 = var5_4;
                                continue;
                            }
                            break block10;
                            break;
                        }
                        break;
                    }
                }
                var4_2 = 0;
                ** while (true)
            }
            if (var2_7 == '.') {
                this.matchStat = -1;
                return 0L;
            }
            if (var7_6 < 0L) {
                this.matchStat = -1;
                return 0L;
            }
            ** GOTO lbl39
        }
        this.matchStat = -1;
        return 0L;
        while (JSONScanner.isWhitespace(var2_7)) {
            var2_7 = this.charAt(var5_4);
            ++var5_4;
lbl39:
            // 2 sources

            if (var2_7 != var1_1) continue;
            this.bp = var5_4;
            this.ch = this.charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            var9_8 = var7_6;
            if (var4_2 != 0) {
                var9_8 = -var7_6;
            }
            return var9_8;
        }
        this.matchStat = -1;
        return var7_6;
    }

    protected void setTime(char c2, char c3, char c4, char c5, char c6, char c7) {
        this.calendar.set(11, (c2 - 48) * 10 + (c3 - 48));
        this.calendar.set(12, (c4 - 48) * 10 + (c5 - 48));
        this.calendar.set(13, (c6 - 48) * 10 + (c7 - 48));
    }

    protected void setTimeZone(char c2, char c3, char c4) {
        Object object;
        c3 = c4 = (char)(((c3 - 48) * 10 + (c4 - 48)) * 3600 * 1000);
        if (c2 == '-') {
            c3 = -c4;
        }
        if (this.calendar.getTimeZone().getRawOffset() != c3 && ((String[])(object = TimeZone.getAvailableIDs(c3))).length > 0) {
            object = TimeZone.getTimeZone(object[0]);
            this.calendar.setTimeZone((TimeZone)object);
        }
    }

    @Override
    public final String stringVal() {
        if (!this.hasSpecial) {
            return this.subString(this.np + 1, this.sp);
        }
        return new String(this.sbuf, 0, this.sp);
    }

    @Override
    public final String subString(int n2, int n3) {
        if (ASMUtils.IS_ANDROID) {
            if (n3 < this.sbuf.length) {
                this.text.getChars(n2, n2 + n3, this.sbuf, 0);
                return new String(this.sbuf, 0, n3);
            }
            char[] cArray = new char[n3];
            this.text.getChars(n2, n2 + n3, cArray, 0);
            return new String(cArray);
        }
        return this.text.substring(n2, n2 + n3);
    }

    @Override
    public final char[] sub_chars(int n2, int n3) {
        if (ASMUtils.IS_ANDROID && n3 < this.sbuf.length) {
            this.text.getChars(n2, n2 + n3, this.sbuf, 0);
            return this.sbuf;
        }
        char[] cArray = new char[n3];
        this.text.getChars(n2, n2 + n3, cArray, 0);
        return cArray;
    }
}

