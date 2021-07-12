/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.SymbolTable;
import com.alibaba.fastjson.util.IOUtils;
import java.io.Closeable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.TimeZone;

public abstract class JSONLexerBase
implements JSONLexer,
Closeable {
    protected static final int INT_MULTMIN_RADIX_TEN = -214748364;
    protected static final long MULTMIN_RADIX_TEN = -922337203685477580L;
    private static final ThreadLocal<char[]> SBUF_LOCAL;
    protected static final int[] digits;
    protected static final char[] typeFieldName;
    protected int bp;
    protected Calendar calendar = null;
    protected char ch;
    protected int eofPos;
    protected int features;
    protected boolean hasSpecial;
    protected Locale locale;
    public int matchStat = 0;
    protected int np;
    protected int pos;
    protected char[] sbuf;
    protected int sp;
    protected String stringDefaultValue = null;
    protected TimeZone timeZone = JSON.defaultTimeZone;
    protected int token;

    static {
        int n2;
        SBUF_LOCAL = new ThreadLocal();
        typeFieldName = ("\"" + JSON.DEFAULT_TYPE_KEY + "\":\"").toCharArray();
        digits = new int[103];
        for (n2 = 48; n2 <= 57; ++n2) {
            JSONLexerBase.digits[n2] = n2 - 48;
        }
        for (n2 = 97; n2 <= 102; ++n2) {
            JSONLexerBase.digits[n2] = n2 - 97 + 10;
        }
        for (n2 = 65; n2 <= 70; ++n2) {
            JSONLexerBase.digits[n2] = n2 - 65 + 10;
        }
    }

    public JSONLexerBase(int n2) {
        this.locale = JSON.defaultLocale;
        this.features = n2;
        if ((Feature.InitStringFieldAsEmpty.mask & n2) != 0) {
            this.stringDefaultValue = "";
        }
        this.sbuf = SBUF_LOCAL.get();
        if (this.sbuf == null) {
            this.sbuf = new char[512];
        }
    }

    public static boolean isWhitespace(char c2) {
        return c2 <= ' ' && (c2 == ' ' || c2 == '\n' || c2 == '\r' || c2 == '\t' || c2 == '\f' || c2 == '\b');
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String readString(char[] cArray, int n2) {
        char[] cArray2 = new char[n2];
        int n3 = 0;
        int n4 = 0;
        while (n3 < n2) {
            int n5;
            char c2 = cArray[n3];
            if (c2 != '\\') {
                n5 = n4 + 1;
                cArray2[n4] = c2;
                n4 = n5;
            } else {
                switch (cArray[++n3]) {
                    default: {
                        throw new JSONException("unclosed.str.lit");
                    }
                    case '0': {
                        n5 = n4 + 1;
                        cArray2[n4] = '\u0000';
                        n4 = n5;
                        break;
                    }
                    case '1': {
                        n5 = n4 + 1;
                        cArray2[n4] = '\u0001';
                        n4 = n5;
                        break;
                    }
                    case '2': {
                        n5 = n4 + 1;
                        cArray2[n4] = 2;
                        n4 = n5;
                        break;
                    }
                    case '3': {
                        n5 = n4 + 1;
                        cArray2[n4] = 3;
                        n4 = n5;
                        break;
                    }
                    case '4': {
                        n5 = n4 + 1;
                        cArray2[n4] = 4;
                        n4 = n5;
                        break;
                    }
                    case '5': {
                        n5 = n4 + 1;
                        cArray2[n4] = 5;
                        n4 = n5;
                        break;
                    }
                    case '6': {
                        n5 = n4 + 1;
                        cArray2[n4] = 6;
                        n4 = n5;
                        break;
                    }
                    case '7': {
                        n5 = n4 + 1;
                        cArray2[n4] = 7;
                        n4 = n5;
                        break;
                    }
                    case 'b': {
                        n5 = n4 + 1;
                        cArray2[n4] = 8;
                        n4 = n5;
                        break;
                    }
                    case 't': {
                        n5 = n4 + 1;
                        cArray2[n4] = 9;
                        n4 = n5;
                        break;
                    }
                    case 'n': {
                        n5 = n4 + 1;
                        cArray2[n4] = 10;
                        n4 = n5;
                        break;
                    }
                    case 'v': {
                        n5 = n4 + 1;
                        cArray2[n4] = 11;
                        n4 = n5;
                        break;
                    }
                    case 'F': 
                    case 'f': {
                        n5 = n4 + 1;
                        cArray2[n4] = 12;
                        n4 = n5;
                        break;
                    }
                    case 'r': {
                        n5 = n4 + 1;
                        cArray2[n4] = 13;
                        n4 = n5;
                        break;
                    }
                    case '\"': {
                        n5 = n4 + 1;
                        cArray2[n4] = 34;
                        n4 = n5;
                        break;
                    }
                    case '\'': {
                        n5 = n4 + 1;
                        cArray2[n4] = 39;
                        n4 = n5;
                        break;
                    }
                    case '/': {
                        n5 = n4 + 1;
                        cArray2[n4] = 47;
                        n4 = n5;
                        break;
                    }
                    case '\\': {
                        n5 = n4 + 1;
                        cArray2[n4] = 92;
                        n4 = n5;
                        break;
                    }
                    case 'x': {
                        n5 = n4 + 1;
                        int[] nArray = digits;
                        int n6 = nArray[cArray[++n3]];
                        nArray = digits;
                        cArray2[n4] = (char)(n6 * 16 + nArray[cArray[++n3]]);
                        n4 = n5;
                        break;
                    }
                    case 'u': {
                        n5 = n4 + 1;
                        c2 = cArray[++n3];
                        char c3 = cArray[++n3];
                        char c4 = cArray[++n3];
                        cArray2[n4] = (char)Integer.parseInt(new String(new char[]{c2, c3, c4, cArray[++n3]}), 16);
                        n4 = n5;
                    }
                }
            }
            ++n3;
        }
        return new String(cArray2, 0, n4);
    }

    private void scanStringSingleQuote() {
        this.np = this.bp;
        this.hasSpecial = false;
        block22: while (true) {
            char[] cArray;
            char c2;
            if ((c2 = this.next()) == '\'') {
                this.token = 4;
                this.next();
                return;
            }
            if (c2 == '\u001a') {
                if (!this.isEOF()) {
                    this.putChar('\u001a');
                    continue;
                }
                throw new JSONException("unclosed single-quote string");
            }
            if (c2 == '\\') {
                if (!this.hasSpecial) {
                    this.hasSpecial = true;
                    if (this.sp > this.sbuf.length) {
                        cArray = new char[this.sp * 2];
                        System.arraycopy(this.sbuf, 0, cArray, 0, this.sbuf.length);
                        this.sbuf = cArray;
                    }
                    this.copyTo(this.np + 1, this.sp, this.sbuf);
                }
                c2 = this.next();
                switch (c2) {
                    default: {
                        this.ch = c2;
                        throw new JSONException("unclosed single-quote string");
                    }
                    case '0': {
                        this.putChar('\u0000');
                        continue block22;
                    }
                    case '1': {
                        this.putChar('\u0001');
                        continue block22;
                    }
                    case '2': {
                        this.putChar('\u0002');
                        continue block22;
                    }
                    case '3': {
                        this.putChar('\u0003');
                        continue block22;
                    }
                    case '4': {
                        this.putChar('\u0004');
                        continue block22;
                    }
                    case '5': {
                        this.putChar('\u0005');
                        continue block22;
                    }
                    case '6': {
                        this.putChar('\u0006');
                        continue block22;
                    }
                    case '7': {
                        this.putChar('\u0007');
                        continue block22;
                    }
                    case 'b': {
                        this.putChar('\b');
                        continue block22;
                    }
                    case 't': {
                        this.putChar('\t');
                        continue block22;
                    }
                    case 'n': {
                        this.putChar('\n');
                        continue block22;
                    }
                    case 'v': {
                        this.putChar('\u000b');
                        continue block22;
                    }
                    case 'F': 
                    case 'f': {
                        this.putChar('\f');
                        continue block22;
                    }
                    case 'r': {
                        this.putChar('\r');
                        continue block22;
                    }
                    case '\"': {
                        this.putChar('\"');
                        continue block22;
                    }
                    case '\'': {
                        this.putChar('\'');
                        continue block22;
                    }
                    case '/': {
                        this.putChar('/');
                        continue block22;
                    }
                    case '\\': {
                        this.putChar('\\');
                        continue block22;
                    }
                    case 'x': {
                        this.putChar((char)(digits[this.next()] * 16 + digits[this.next()]));
                        continue block22;
                    }
                    case 'u': 
                }
                this.putChar((char)Integer.parseInt(new String(new char[]{this.next(), this.next(), this.next(), this.next()}), 16));
                continue;
            }
            if (!this.hasSpecial) {
                ++this.sp;
                continue;
            }
            if (this.sp == this.sbuf.length) {
                this.putChar(c2);
                continue;
            }
            cArray = this.sbuf;
            int n2 = this.sp;
            this.sp = n2 + 1;
            cArray[n2] = c2;
        }
    }

    public abstract String addSymbol(int var1, int var2, int var3, SymbolTable var4);

    protected abstract void arrayCopy(int var1, char[] var2, int var3, int var4);

    @Override
    public abstract byte[] bytesValue();

    protected abstract boolean charArrayCompare(char[] var1);

    public abstract char charAt(int var1);

    @Override
    public void close() {
        if (this.sbuf.length <= 8192) {
            SBUF_LOCAL.set(this.sbuf);
        }
        this.sbuf = null;
    }

    @Override
    public void config(Feature feature, boolean bl2) {
        this.features = Feature.config(this.features, feature, bl2);
        if ((this.features & Feature.InitStringFieldAsEmpty.mask) != 0) {
            this.stringDefaultValue = "";
        }
    }

    protected abstract void copyTo(int var1, int var2, char[] var3);

    /*
     * Unable to fully structure code
     */
    @Override
    public final Number decimalValue(boolean var1_1) {
        var4_2 = this.charAt(this.np + this.sp - 1);
        if (var4_2 == 'F') {
            return Float.valueOf(Float.parseFloat(this.numberString()));
        }
        if (var4_2 == 'D') {
            return Double.parseDouble(this.numberString());
        }
        if (!var1_1) ** GOTO lbl11
        try {
            return this.decimalValue();
lbl11:
            // 1 sources

            var2_3 = this.doubleValue();
        }
        catch (NumberFormatException var5_4) {
            throw new JSONException(var5_4.getMessage() + ", " + this.info());
        }
        return var2_3;
    }

    @Override
    public final BigDecimal decimalValue() {
        return new BigDecimal(this.numberString());
    }

    public double doubleValue() {
        return Double.parseDouble(this.numberString());
    }

    @Override
    public float floatValue() {
        return Float.parseFloat(this.numberString());
    }

    public Calendar getCalendar() {
        return this.calendar;
    }

    @Override
    public final char getCurrent() {
        return this.ch;
    }

    @Override
    public Locale getLocale() {
        return this.locale;
    }

    @Override
    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    public abstract int indexOf(char var1, int var2);

    @Override
    public String info() {
        return "";
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final int intValue() {
        int n2;
        if (this.np == -1) {
            this.np = 0;
        }
        int n3 = 0;
        boolean bl2 = false;
        int n4 = this.np;
        int n5 = this.np + this.sp;
        if (this.charAt(this.np) == '-') {
            bl2 = true;
            n2 = Integer.MIN_VALUE;
            ++n4;
        } else {
            n2 = -2147483647;
        }
        int n6 = n4;
        if (n4 < n5) {
            n3 = -(this.charAt(n4) - 48);
            n6 = n4 + 1;
        }
        while (true) {
            char c2;
            block12: {
                block11: {
                    if (n6 >= n5) break block11;
                    n4 = n6 + 1;
                    c2 = this.charAt(n6);
                    n6 = n4;
                    if (c2 == 'L') break block11;
                    n6 = n4;
                    if (c2 == 'S') break block11;
                    if (c2 != 'B') break block12;
                    n6 = n4;
                }
                if (!bl2) {
                    return -n3;
                }
                if (n6 <= this.np + 1) break;
                return n3;
            }
            n6 = c2 - 48;
            if ((long)n3 < -214748364L) {
                throw new NumberFormatException(this.numberString());
            }
            if ((n3 *= 10) < n2 + n6) {
                throw new NumberFormatException(this.numberString());
            }
            n3 -= n6;
            n6 = n4;
        }
        throw new NumberFormatException(this.numberString());
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final Number integerValue() throws NumberFormatException {
        long l2;
        long l3 = 0L;
        boolean bl2 = false;
        if (this.np == -1) {
            this.np = 0;
        }
        int n2 = this.np;
        int n3 = this.np + this.sp;
        int n4 = 32;
        switch (this.charAt(n3 - 1)) {
            case 'L': {
                --n3;
                n4 = 76;
                break;
            }
            case 'S': {
                --n3;
                n4 = 83;
                break;
            }
            case 'B': {
                --n3;
                n4 = 66;
            }
        }
        if (this.charAt(this.np) == '-') {
            bl2 = true;
            l2 = Long.MIN_VALUE;
            ++n2;
        } else {
            l2 = -9223372036854775807L;
        }
        int n5 = n2;
        if (n2 < n3) {
            l3 = -(this.charAt(n2) - 48);
            n5 = n2 + 1;
        }
        while (n5 < n3) {
            n2 = this.charAt(n5) - 48;
            if (l3 < -922337203685477580L) {
                return new BigInteger(this.numberString());
            }
            if ((l3 *= 10L) < (long)n2 + l2) {
                return new BigInteger(this.numberString());
            }
            l3 -= (long)n2;
            ++n5;
        }
        if (bl2) {
            if (n5 <= this.np + 1) {
                throw new NumberFormatException(this.numberString());
            }
            if (l3 >= Integer.MIN_VALUE && n4 != 76) {
                if (n4 == 83) {
                    return (short)l3;
                }
                if (n4 == 66) {
                    return (byte)l3;
                }
                return (int)l3;
            }
            return l3;
        }
        if ((l3 = -l3) <= Integer.MAX_VALUE && n4 != 76) {
            if (n4 == 83) {
                return (short)l3;
            }
            if (n4 == 66) {
                return (byte)l3;
            }
            return (int)l3;
        }
        return l3;
    }

    @Override
    public final boolean isBlankInput() {
        int n2 = 0;
        char c2;
        while ((c2 = this.charAt(n2)) != '\u001a') {
            if (!JSONLexerBase.isWhitespace(c2)) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    public abstract boolean isEOF();

    @Override
    public final boolean isEnabled(int n2) {
        return (this.features & n2) != 0;
    }

    @Override
    public final boolean isEnabled(Feature feature) {
        return this.isEnabled(feature.mask);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final boolean isRef() {
        return this.sp == 4 && this.charAt(this.np + 1) == '$' && this.charAt(this.np + 2) == 'r' && this.charAt(this.np + 3) == 'e' && this.charAt(this.np + 4) == 'f';
    }

    protected void lexError(String string2, Object ... objectArray) {
        this.token = 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final long longValue() throws NumberFormatException {
        long l2;
        long l3 = 0L;
        boolean bl2 = false;
        if (this.np == -1) {
            this.np = 0;
        }
        int n2 = this.np;
        int n3 = this.np + this.sp;
        if (this.charAt(this.np) == '-') {
            bl2 = true;
            l2 = Long.MIN_VALUE;
            ++n2;
        } else {
            l2 = -9223372036854775807L;
        }
        int n4 = n2;
        if (n2 < n3) {
            l3 = -(this.charAt(n2) - 48);
            n4 = n2 + 1;
        }
        while (true) {
            char c2;
            block12: {
                block11: {
                    if (n4 >= n3) break block11;
                    n2 = n4 + 1;
                    c2 = this.charAt(n4);
                    n4 = n2;
                    if (c2 == 'L') break block11;
                    n4 = n2;
                    if (c2 == 'S') break block11;
                    if (c2 != 'B') break block12;
                    n4 = n2;
                }
                if (!bl2) {
                    return -l3;
                }
                if (n4 <= this.np + 1) break;
                return l3;
            }
            n4 = c2 - 48;
            if (l3 < -922337203685477580L) {
                throw new NumberFormatException(this.numberString());
            }
            if ((l3 *= 10L) < (long)n4 + l2) {
                throw new NumberFormatException(this.numberString());
            }
            l3 -= (long)n4;
            n4 = n2;
        }
        throw new NumberFormatException(this.numberString());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean matchField(char[] cArray) {
        if (!this.charArrayCompare(cArray)) {
            return false;
        }
        this.bp += cArray.length;
        this.ch = this.charAt(this.bp);
        if (this.ch == '{') {
            this.next();
            this.token = 12;
            return true;
        }
        if (this.ch == '[') {
            this.next();
            this.token = 14;
            return true;
        }
        if (this.ch == 'S' && this.charAt(this.bp + 1) == 'e' && this.charAt(this.bp + 2) == 't' && this.charAt(this.bp + 3) == '[') {
            this.bp += 3;
            this.ch = this.charAt(this.bp);
            this.token = 21;
            return true;
        }
        this.nextToken();
        return true;
    }

    public final int matchStat() {
        return this.matchStat;
    }

    @Override
    public abstract char next();

    public final void nextIdent() {
        while (JSONLexerBase.isWhitespace(this.ch)) {
            this.next();
        }
        if (this.ch == '_' || Character.isLetter(this.ch)) {
            this.scanIdent();
            return;
        }
        this.nextToken();
    }

    @Override
    public final void nextToken() {
        this.sp = 0;
        block15: while (true) {
            block23: {
                int n2;
                this.pos = this.bp;
                if (this.ch == '/') {
                    this.skipComment();
                    continue;
                }
                if (this.ch == '\"') {
                    this.scanString();
                    return;
                }
                if (this.ch == ',') {
                    this.next();
                    this.token = 16;
                    return;
                }
                if (this.ch >= '0' && this.ch <= '9') {
                    this.scanNumber();
                    return;
                }
                if (this.ch == '-') {
                    this.scanNumber();
                    return;
                }
                switch (this.ch) {
                    default: {
                        if (this.isEOF()) {
                            if (this.token != 20) break;
                            throw new JSONException("EOF error");
                        }
                        break block23;
                    }
                    case '\'': {
                        if (!this.isEnabled(Feature.AllowSingleQuotes)) {
                            throw new JSONException("Feature.AllowSingleQuotes is false");
                        }
                        this.scanStringSingleQuote();
                        return;
                    }
                    case '\b': 
                    case '\t': 
                    case '\n': 
                    case '\f': 
                    case '\r': 
                    case ' ': {
                        this.next();
                        continue block15;
                    }
                    case 't': {
                        this.scanTrue();
                        return;
                    }
                    case 'f': {
                        this.scanFalse();
                        return;
                    }
                    case 'n': {
                        this.scanNullOrNew();
                        return;
                    }
                    case 'N': 
                    case 'S': 
                    case 'T': 
                    case 'u': {
                        this.scanIdent();
                        return;
                    }
                    case '(': {
                        this.next();
                        this.token = 10;
                        return;
                    }
                    case ')': {
                        this.next();
                        this.token = 11;
                        return;
                    }
                    case '[': {
                        this.next();
                        this.token = 14;
                        return;
                    }
                    case ']': {
                        this.next();
                        this.token = 15;
                        return;
                    }
                    case '{': {
                        this.next();
                        this.token = 12;
                        return;
                    }
                    case '}': {
                        this.next();
                        this.token = 13;
                        return;
                    }
                    case ':': {
                        this.next();
                        this.token = 17;
                        return;
                    }
                }
                this.token = 20;
                this.bp = n2 = this.eofPos;
                this.pos = n2;
                return;
            }
            if (this.ch > '\u001f' && this.ch != '\u007f') break;
            this.next();
        }
        this.lexError("illegal.char", String.valueOf((int)this.ch));
        this.next();
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public final void nextToken(int var1_1) {
        this.sp = 0;
        block10: while (true) {
            switch (var1_1) lbl-1000:
            // 7 sources

            {
                default: {
                    if (this.ch != ' ' && this.ch != '\n' && this.ch != '\r' && this.ch != '\t' && this.ch != '\f' && this.ch != '\b') break block10;
                    this.next();
                    continue block10;
                }
                case 12: {
                    if (this.ch == '{') {
                        this.token = 12;
                        this.next();
                        return;
                    }
                    if (this.ch != '[') ** GOTO lbl-1000
                    this.token = 14;
                    this.next();
                    return;
                }
                case 16: {
                    if (this.ch == ',') {
                        this.token = 16;
                        this.next();
                        return;
                    }
                    if (this.ch == '}') {
                        this.token = 13;
                        this.next();
                        return;
                    }
                    if (this.ch == ']') {
                        this.token = 15;
                        this.next();
                        return;
                    }
                    if (this.ch != '\u001a') ** GOTO lbl-1000
                    this.token = 20;
                    return;
                }
                case 2: {
                    if (this.ch >= '0' && this.ch <= '9') {
                        this.pos = this.bp;
                        this.scanNumber();
                        return;
                    }
                    if (this.ch == '\"') {
                        this.pos = this.bp;
                        this.scanString();
                        return;
                    }
                    if (this.ch == '[') {
                        this.token = 14;
                        this.next();
                        return;
                    }
                    if (this.ch != '{') ** GOTO lbl-1000
                    this.token = 12;
                    this.next();
                    return;
                }
                case 4: {
                    if (this.ch == '\"') {
                        this.pos = this.bp;
                        this.scanString();
                        return;
                    }
                    if (this.ch >= '0' && this.ch <= '9') {
                        this.pos = this.bp;
                        this.scanNumber();
                        return;
                    }
                    if (this.ch == '[') {
                        this.token = 14;
                        this.next();
                        return;
                    }
                    if (this.ch != '{') ** GOTO lbl-1000
                    this.token = 12;
                    this.next();
                    return;
                }
                case 14: {
                    if (this.ch == '[') {
                        this.token = 14;
                        this.next();
                        return;
                    }
                    if (this.ch != '{') ** GOTO lbl-1000
                    this.token = 12;
                    this.next();
                    return;
                }
                case 15: {
                    if (this.ch == ']') {
                        this.token = 15;
                        this.next();
                        return;
                    }
                }
                case 20: {
                    if (this.ch != '\u001a') ** GOTO lbl-1000
                    this.token = 20;
                    return;
                }
                case 18: {
                    this.nextIdent();
                    return;
                }
            }
            break;
        }
        this.nextToken();
    }

    public final void nextTokenWithChar(char c2) {
        this.sp = 0;
        while (true) {
            if (this.ch == c2) {
                this.next();
                this.nextToken();
                return;
            }
            if (this.ch != ' ' && this.ch != '\n' && this.ch != '\r' && this.ch != '\t' && this.ch != '\f' && this.ch != '\b') break;
            this.next();
        }
        throw new JSONException("not match " + c2 + " - " + this.ch);
    }

    @Override
    public final void nextTokenWithColon() {
        this.nextTokenWithChar(':');
    }

    @Override
    public final void nextTokenWithColon(int n2) {
        this.nextTokenWithChar(':');
    }

    @Override
    public abstract String numberString();

    @Override
    public final int pos() {
        return this.pos;
    }

    protected final void putChar(char c2) {
        char[] cArray;
        if (this.sp == this.sbuf.length) {
            cArray = new char[this.sbuf.length * 2];
            System.arraycopy(this.sbuf, 0, cArray, 0, this.sbuf.length);
            this.sbuf = cArray;
        }
        cArray = this.sbuf;
        int n2 = this.sp;
        this.sp = n2 + 1;
        cArray[n2] = c2;
    }

    @Override
    public final void resetStringPosition() {
        this.sp = 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean scanBoolean(char c2) {
        char c3;
        boolean bl2;
        int n2;
        block12: {
            this.matchStat = 0;
            n2 = this.bp;
            int n3 = 0 + 1;
            char c4 = this.charAt(n2 + 0);
            bl2 = false;
            if (c4 == 't') {
                if (this.charAt(this.bp + 1) == 'r' && this.charAt(this.bp + 1 + 1) == 'u' && this.charAt(this.bp + 1 + 2) == 'e') {
                    c3 = this.charAt(this.bp + 4);
                    bl2 = true;
                    n2 = n3 + 3 + 1;
                    break block12;
                } else {
                    this.matchStat = -1;
                    return false;
                }
            }
            if (c4 == 'f') {
                if (this.charAt(this.bp + 1) == 'a' && this.charAt(this.bp + 1 + 1) == 'l' && this.charAt(this.bp + 1 + 2) == 's' && this.charAt(this.bp + 1 + 3) == 'e') {
                    c3 = this.charAt(this.bp + 5);
                    bl2 = false;
                    n2 = n3 + 4 + 1;
                    break block12;
                } else {
                    this.matchStat = -1;
                    return false;
                }
            }
            if (c4 == '1') {
                c3 = this.charAt(this.bp + 1);
                bl2 = true;
                n2 = n3 + 1;
            } else {
                c3 = c4;
                n2 = n3;
                if (c4 == '0') {
                    c3 = this.charAt(this.bp + 1);
                    bl2 = false;
                    n2 = n3 + 1;
                }
            }
        }
        while (true) {
            if (c3 == c2) {
                this.bp += n2;
                this.ch = this.charAt(this.bp);
                this.matchStat = 3;
                return bl2;
            }
            if (!JSONLexerBase.isWhitespace(c3)) {
                this.matchStat = -1;
                return bl2;
            }
            c3 = this.charAt(this.bp + n2);
            ++n2;
        }
    }

    @Override
    public final double scanDouble(char c2) {
        double d2;
        this.matchStat = 0;
        int n2 = this.charAt(this.bp + 0);
        if (n2 >= 48 && n2 <= 57) {
            char c3;
            int n3;
            int n4 = 0 + 1;
            while (true) {
                n3 = this.bp;
                n2 = n4 + 1;
                c3 = this.charAt(n3 + n4);
                if (c3 < '0' || c3 > '9') break;
                n4 = n2;
            }
            n4 = c3;
            n3 = n2;
            if (c3 == '.') {
                n3 = this.bp;
                n4 = n2 + 1;
                if ((n2 = (int)this.charAt(n3 + n2)) >= 48 && n2 <= 57) {
                    while (true) {
                        n3 = this.bp;
                        n2 = n4 + 1;
                        c3 = this.charAt(n3 + n4);
                        n4 = c3;
                        n3 = n2;
                        if (c3 >= '0') {
                            n4 = c3;
                            n3 = n2;
                            if (c3 <= '9') {
                                n4 = n2;
                                continue;
                            }
                        }
                        break;
                    }
                } else {
                    this.matchStat = -1;
                    return 0.0;
                }
            }
            n2 = this.bp;
            d2 = Double.parseDouble(this.subString(n2, this.bp + n3 - n2 - 1));
            if (n4 == c2) {
                this.bp += n3;
                this.ch = this.charAt(this.bp);
                this.matchStat = 3;
                this.token = 16;
                return d2;
            }
        } else {
            this.matchStat = -1;
            return 0.0;
        }
        this.matchStat = -1;
        return d2;
    }

    @Override
    public Enum<?> scanEnum(Class<?> clazz, SymbolTable object, char c2) {
        if ((object = this.scanSymbolWithSeperator((SymbolTable)object, c2)) == null) {
            return null;
        }
        return Enum.valueOf(clazz, (String)object);
    }

    public final void scanFalse() {
        if (this.ch != 'f') {
            throw new JSONException("error parse false");
        }
        this.next();
        if (this.ch != 'a') {
            throw new JSONException("error parse false");
        }
        this.next();
        if (this.ch != 'l') {
            throw new JSONException("error parse false");
        }
        this.next();
        if (this.ch != 's') {
            throw new JSONException("error parse false");
        }
        this.next();
        if (this.ch != 'e') {
            throw new JSONException("error parse false");
        }
        this.next();
        if (this.ch == ' ' || this.ch == ',' || this.ch == '}' || this.ch == ']' || this.ch == '\n' || this.ch == '\r' || this.ch == '\t' || this.ch == '\u001a' || this.ch == '\f' || this.ch == '\b' || this.ch == ':') {
            this.token = 7;
            return;
        }
        throw new JSONException("scan false error");
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean scanFieldBoolean(char[] cArray) {
        boolean bl2;
        this.matchStat = 0;
        if (!this.charArrayCompare(cArray)) {
            this.matchStat = -2;
            return false;
        }
        int n2 = cArray.length;
        int n3 = this.bp;
        int n4 = n2 + 1;
        if ((n2 = (int)this.charAt(n3 + n2)) == 116) {
            n3 = this.bp;
            n2 = n4 + 1;
            if (this.charAt(n3 + n4) != 'r') {
                this.matchStat = -1;
                return false;
            }
            n4 = this.bp;
            n3 = n2 + 1;
            if (this.charAt(n4 + n2) != 'u') {
                this.matchStat = -1;
                return false;
            }
            n2 = this.bp;
            n4 = n3 + 1;
            if (this.charAt(n2 + n3) != 'e') {
                this.matchStat = -1;
                return false;
            }
            bl2 = true;
        } else {
            if (n2 != 102) {
                this.matchStat = -1;
                return false;
            }
            n3 = this.bp;
            n2 = n4 + 1;
            if (this.charAt(n3 + n4) != 'a') {
                this.matchStat = -1;
                return false;
            }
            n3 = this.bp;
            n4 = n2 + 1;
            if (this.charAt(n3 + n2) != 'l') {
                this.matchStat = -1;
                return false;
            }
            n2 = this.bp;
            n3 = n4 + 1;
            if (this.charAt(n2 + n4) != 's') {
                this.matchStat = -1;
                return false;
            }
            if (this.charAt(this.bp + n3) != 'e') {
                this.matchStat = -1;
                return false;
            }
            bl2 = false;
            n4 = n3 + 1;
        }
        n3 = this.bp;
        n2 = n4 + 1;
        if ((n4 = (int)this.charAt(n3 + n4)) == 44) {
            this.bp += n2;
            this.ch = this.charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            return bl2;
        }
        if (n4 != 125) {
            this.matchStat = -1;
            return false;
        }
        n3 = this.bp;
        n4 = n2 + 1;
        if ((n2 = (int)this.charAt(n3 + n2)) == 44) {
            this.token = 16;
            this.bp += n4;
            this.ch = this.charAt(this.bp);
        } else if (n2 == 93) {
            this.token = 15;
            this.bp += n4;
            this.ch = this.charAt(this.bp);
        } else if (n2 == 125) {
            this.token = 13;
            this.bp += n4;
            this.ch = this.charAt(this.bp);
        } else {
            if (n2 != 26) {
                this.matchStat = -1;
                return false;
            }
            this.token = 20;
            this.bp += n4 - 1;
            this.ch = (char)26;
        }
        this.matchStat = 4;
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final double scanFieldDouble(char[] cArray) {
        double d2;
        int n2;
        int n3;
        int n4;
        int n5;
        block26: {
            block23: {
                block25: {
                    block24: {
                        this.matchStat = 0;
                        if (!this.charArrayCompare(cArray)) {
                            this.matchStat = -2;
                            return 0.0;
                        }
                        n5 = cArray.length;
                        n4 = this.charAt(this.bp + n5);
                        if (n4 < 48 || n4 > 57) break block23;
                        ++n5;
                        while (true) {
                            n4 = this.bp;
                            n3 = n5 + 1;
                            n2 = this.charAt(n4 + n5);
                            if (n2 < 48 || n2 > 57) break;
                            n5 = n3;
                        }
                        n4 = n2;
                        n5 = n3;
                        if (n2 == 46) {
                            n4 = this.bp;
                            n5 = n3 + 1;
                            if ((n4 = (int)this.charAt(n4 + n3)) >= 48 && n4 <= 57) {
                                while (true) {
                                    n4 = this.bp;
                                    n3 = n5 + 1;
                                    n4 = n2 = (int)this.charAt(n4 + n5);
                                    n5 = n3;
                                    if (n2 >= 48) {
                                        n4 = n2;
                                        n5 = n3;
                                        if (n2 <= 57) {
                                            n5 = n3;
                                            continue;
                                        }
                                    }
                                    break;
                                }
                            } else {
                                this.matchStat = -1;
                                return 0.0;
                            }
                        }
                        if (n4 == 101) break block24;
                        n2 = n4;
                        n3 = n5;
                        if (n4 != 69) break block25;
                    }
                    n3 = this.bp;
                    n4 = n5 + 1;
                    if ((n5 = (int)this.charAt(n3 + n5)) == 43 || n5 == 45) {
                        n3 = this.bp;
                        n5 = n4 + 1;
                        n3 = this.charAt(n3 + n4);
                        n4 = n5;
                        n5 = n3;
                    }
                    while (true) {
                        n2 = n5;
                        n3 = n4;
                        if (n5 < 48) break;
                        n2 = n5;
                        n3 = n4;
                        if (n5 > 57) break;
                        n5 = this.charAt(this.bp + n4);
                        ++n4;
                    }
                }
                n5 = this.bp + cArray.length;
                d2 = Double.parseDouble(this.subString(n5, this.bp + n3 - n5 - 1));
                if (n2 == 44) {
                    this.bp += n3;
                    this.ch = this.charAt(this.bp);
                    this.matchStat = 3;
                    this.token = 16;
                    return d2;
                }
                break block26;
            }
            this.matchStat = -1;
            return 0.0;
        }
        if (n2 != 125) {
            this.matchStat = -1;
            return 0.0;
        }
        n4 = this.bp;
        n5 = n3 + 1;
        if ((n4 = (int)this.charAt(n4 + n3)) == 44) {
            this.token = 16;
            this.bp += n5;
            this.ch = this.charAt(this.bp);
        } else if (n4 == 93) {
            this.token = 15;
            this.bp += n5;
            this.ch = this.charAt(this.bp);
        } else if (n4 == 125) {
            this.token = 13;
            this.bp += n5;
            this.ch = this.charAt(this.bp);
        } else {
            if (n4 != 26) {
                this.matchStat = -1;
                return 0.0;
            }
            this.token = 20;
            this.bp += n5 - 1;
            this.ch = (char)26;
        }
        this.matchStat = 4;
        return d2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final float scanFieldFloat(char[] cArray) {
        float f2;
        int n2;
        this.matchStat = 0;
        if (!this.charArrayCompare(cArray)) {
            this.matchStat = -2;
            return 0.0f;
        }
        int n3 = cArray.length;
        int n4 = this.charAt(this.bp + n3);
        if (n4 >= 48 && n4 <= 57) {
            char c2;
            ++n3;
            while (true) {
                n2 = this.bp;
                n4 = n3 + 1;
                c2 = this.charAt(n2 + n3);
                if (c2 < '0' || c2 > '9') break;
                n3 = n4;
            }
            n2 = c2;
            n3 = n4;
            if (c2 == '.') {
                n2 = this.bp;
                n3 = n4 + 1;
                if ((n4 = (int)this.charAt(n2 + n4)) >= 48 && n4 <= 57) {
                    while (true) {
                        n2 = this.bp;
                        n4 = n3 + 1;
                        c2 = this.charAt(n2 + n3);
                        n2 = c2;
                        n3 = n4;
                        if (c2 >= '0') {
                            n2 = c2;
                            n3 = n4;
                            if (c2 <= '9') {
                                n3 = n4;
                                continue;
                            }
                        }
                        break;
                    }
                } else {
                    this.matchStat = -1;
                    return 0.0f;
                }
            }
            n4 = this.bp + cArray.length;
            f2 = Float.parseFloat(this.subString(n4, this.bp + n3 - n4 - 1));
            if (n2 == 44) {
                this.bp += n3;
                this.ch = this.charAt(this.bp);
                this.matchStat = 3;
                this.token = 16;
                return f2;
            }
        } else {
            this.matchStat = -1;
            return 0.0f;
        }
        if (n2 != 125) {
            this.matchStat = -1;
            return 0.0f;
        }
        n2 = this.bp;
        n4 = n3 + 1;
        if ((n3 = (int)this.charAt(n2 + n3)) == 44) {
            this.token = 16;
            this.bp += n4;
            this.ch = this.charAt(this.bp);
        } else if (n3 == 93) {
            this.token = 15;
            this.bp += n4;
            this.ch = this.charAt(this.bp);
        } else if (n3 == 125) {
            this.token = 13;
            this.bp += n4;
            this.ch = this.charAt(this.bp);
        } else {
            if (n3 != 26) {
                this.matchStat = -1;
                return 0.0f;
            }
            this.bp += n4 - 1;
            this.token = 20;
            this.ch = (char)26;
        }
        this.matchStat = 4;
        return f2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int scanFieldInt(char[] cArray) {
        int n2;
        int n3;
        this.matchStat = 0;
        if (!this.charArrayCompare(cArray)) {
            this.matchStat = -2;
            return 0;
        }
        int n4 = cArray.length;
        int n5 = this.bp;
        int n6 = n4 + 1;
        if ((n5 = (n4 = (int)this.charAt(n5 + n4)) == 45 ? 1 : 0) != 0) {
            n3 = this.bp;
            n4 = n6 + 1;
            n3 = this.charAt(n3 + n6);
            n6 = n4;
            n4 = n3;
        }
        if (n4 >= 48 && n4 <= 57) {
            n4 -= 48;
            while (true) {
                n2 = this.bp;
                n3 = n6 + 1;
                if ((n6 = (int)this.charAt(n2 + n6)) < 48 || n6 > 57) break;
                n4 = n4 * 10 + (n6 - 48);
                n6 = n3;
            }
            if (n6 == 46) {
                this.matchStat = -1;
                return 0;
            }
            if (!(n4 >= 0 && n3 <= cArray.length + 14 || n4 == Integer.MIN_VALUE && n3 == 17 && n5 != 0)) {
                this.matchStat = -1;
                return 0;
            }
        } else {
            this.matchStat = -1;
            return 0;
        }
        if (n6 == 44) {
            this.bp += n3;
            this.ch = this.charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            n6 = n4;
            if (n5 == 0) return n6;
            return -n4;
        }
        if (n6 != 125) {
            this.matchStat = -1;
            return 0;
        }
        n2 = this.bp;
        n6 = n3 + 1;
        if ((n3 = (int)this.charAt(n2 + n3)) == 44) {
            this.token = 16;
            this.bp += n6;
            this.ch = this.charAt(this.bp);
        } else if (n3 == 93) {
            this.token = 15;
            this.bp += n6;
            this.ch = this.charAt(this.bp);
        } else if (n3 == 125) {
            this.token = 13;
            this.bp += n6;
            this.ch = this.charAt(this.bp);
        } else {
            if (n3 != 26) {
                this.matchStat = -1;
                return 0;
            }
            this.token = 20;
            this.bp += n6 - 1;
            this.ch = (char)26;
        }
        this.matchStat = 4;
        n6 = n4;
        if (n5 == 0) return n6;
        return -n4;
    }

    /*
     * Enabled aggressive block sorting
     */
    public long scanFieldLong(char[] cArray) {
        long l2;
        long l3;
        int n2;
        this.matchStat = 0;
        if (!this.charArrayCompare(cArray)) {
            this.matchStat = -2;
            return 0L;
        }
        int n3 = cArray.length;
        int n4 = this.bp;
        int n5 = n3 + 1;
        n4 = this.charAt(n4 + n3);
        n3 = 0;
        if (n4 == 45) {
            n4 = this.bp;
            n3 = n5 + 1;
            n4 = this.charAt(n4 + n5);
            n2 = 1;
            n5 = n3;
            n3 = n2;
        }
        if (n4 >= 48 && n4 <= 57) {
            l3 = n4 - 48;
            while (true) {
                n2 = this.bp;
                n4 = n5 + 1;
                if ((n5 = (int)this.charAt(n2 + n5)) < 48 || n5 > 57) break;
                l3 = 10L * l3 + (long)(n5 - 48);
                n5 = n4;
            }
            if (n5 == 46) {
                this.matchStat = -1;
                return 0L;
            }
            if (l3 < 0L || n4 > 21) {
                this.matchStat = -1;
                return 0L;
            }
        } else {
            this.matchStat = -1;
            return 0L;
        }
        if (n5 == 44) {
            this.bp += n4;
            this.ch = this.charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            l2 = l3;
            if (n3 == 0) return l2;
            return -l3;
        }
        if (n5 != 125) {
            this.matchStat = -1;
            return 0L;
        }
        n2 = this.bp;
        n5 = n4 + 1;
        if ((n4 = (int)this.charAt(n2 + n4)) == 44) {
            this.token = 16;
            this.bp += n5;
            this.ch = this.charAt(this.bp);
        } else if (n4 == 93) {
            this.token = 15;
            this.bp += n5;
            this.ch = this.charAt(this.bp);
        } else if (n4 == 125) {
            this.token = 13;
            this.bp += n5;
            this.ch = this.charAt(this.bp);
        } else {
            if (n4 != 26) {
                this.matchStat = -1;
                return 0L;
            }
            this.token = 20;
            this.bp += n5 - 1;
            this.ch = (char)26;
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
    public String scanFieldString(char[] cArray) {
        int n2;
        this.matchStat = 0;
        if (!this.charArrayCompare(cArray)) {
            this.matchStat = -2;
            return this.stringDefaultValue();
        }
        int n3 = cArray.length;
        if (this.charAt(this.bp + n3) != '\"') {
            this.matchStat = -1;
            return this.stringDefaultValue();
        }
        int n4 = this.indexOf('\"', this.bp + cArray.length + 1);
        if (n4 == -1) {
            throw new JSONException("unclosed str");
        }
        int n5 = this.bp + cArray.length + 1;
        String string2 = this.subString(n5, n4 - n5);
        n5 = n4;
        String string3 = string2;
        boolean bl2 = true;
        while (true) {
            block17: {
                block16: {
                    if (bl2 && !(bl2 = false) && string2.indexOf(92) == -1) break block16;
                    n2 = 0;
                    for (n5 = n4 - 1; n5 >= 0 && this.charAt(n5) == '\\'; ++n2, --n5) {
                    }
                    if (n2 % 2 != 0) break block17;
                    n5 = n4 - (this.bp + cArray.length + 1);
                    string3 = JSONLexerBase.readString(this.sub_chars(this.bp + cArray.length + 1, n5), n5);
                    n5 = n4;
                }
                n5 = n3 + 1 + (n5 - (this.bp + cArray.length + 1) + 1);
                n2 = this.bp;
                n4 = n5 + 1;
                if ((n5 = (int)this.charAt(n2 + n5)) != 44) break;
                this.bp += n4;
                this.ch = this.charAt(this.bp);
                this.matchStat = 3;
                return string3;
            }
            n4 = this.indexOf('\"', n4 + 1);
        }
        if (n5 != 125) {
            this.matchStat = -1;
            return this.stringDefaultValue();
        }
        n2 = this.bp;
        n5 = n4 + 1;
        if ((n4 = (int)this.charAt(n2 + n4)) == 44) {
            this.token = 16;
            this.bp += n5;
            this.ch = this.charAt(this.bp);
        } else if (n4 == 93) {
            this.token = 15;
            this.bp += n5;
            this.ch = this.charAt(this.bp);
        } else if (n4 == 125) {
            this.token = 13;
            this.bp += n5;
            this.ch = this.charAt(this.bp);
        } else {
            if (n4 != 26) {
                this.matchStat = -1;
                return this.stringDefaultValue();
            }
            this.token = 20;
            this.bp += n5 - 1;
            this.ch = (char)26;
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
    public Collection<String> scanFieldStringArray(char[] var1_1, Class<?> var2_3) {
        block28: {
            block27: {
                block26: {
                    block23: {
                        block25: {
                            this.matchStat = 0;
                            if (!this.charArrayCompare(var1_1 /* !! */ )) {
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
                            var3_4 = var1_1 /* !! */ .length;
                            var5_5 = this.bp;
                            var4_6 = var3_4 + 1;
                            if (this.charAt(var5_5 + var3_4) != '[') {
                                this.matchStat = -1;
                                return null;
                            }
                            var3_4 = this.charAt(this.bp + var4_6);
                            ++var4_6;
                            block2: while (var3_4 == 34) {
                                var3_4 = this.indexOf('\"', this.bp + var4_6);
                                if (var3_4 == -1) {
                                    throw new JSONException("unclosed str");
                                }
                                var5_5 = this.bp + var4_6;
                                var7_8 = this.subString(var5_5, var3_4 - var5_5);
                                var5_5 = var3_4;
                                var1_1 /* !! */  = (char[])var7_8;
                                if (var7_8.indexOf(92) == -1) ** GOTO lbl41
                                while (true) {
                                    block24: {
                                        var6_7 = 0;
                                        for (var5_5 = var3_4 - 1; var5_5 >= 0 && this.charAt(var5_5) == '\\'; ++var6_7, --var5_5) {
                                        }
                                        if (var6_7 % 2 != 0) break block24;
                                        var5_5 = var3_4 - (this.bp + var4_6);
                                        var1_1 /* !! */  = (char[])JSONLexerBase.readString(this.sub_chars(this.bp + var4_6, var5_5), var5_5);
                                        var5_5 = var3_4;
lbl41:
                                        // 2 sources

                                        var4_6 += var5_5 - (this.bp + var4_6) + 1;
                                        var5_5 = this.bp;
                                        var3_4 = var4_6 + 1;
                                        var4_6 = this.charAt(var5_5 + var4_6);
                                        var2_3.add((String)var1_1 /* !! */ );
lbl47:
                                        // 2 sources

                                        while (var4_6 == 44) {
                                            var4_6 = this.charAt(this.bp + var3_4);
                                            var5_5 = var3_4 + 1;
                                            var3_4 = var4_6;
                                            var4_6 = var5_5;
                                            continue block2;
                                        }
                                        break block23;
                                    }
                                    var3_4 = this.indexOf('\"', var3_4 + 1);
                                }
                            }
                            if (var3_4 != 110 || this.charAt(this.bp + var4_6) != 'u' || this.charAt(this.bp + var4_6 + 1) != 'l' || this.charAt(this.bp + var4_6 + 2) != 'l') break block25;
                            var5_5 = this.bp;
                            var3_4 = (var4_6 += 3) + 1;
                            var4_6 = this.charAt(var5_5 + var4_6);
                            var2_3.add(null);
                            ** GOTO lbl47
                        }
                        if (var3_4 != 93 || var2_3.size() != 0) {
                            throw new JSONException("illega str");
                        }
                        var5_5 = this.charAt(this.bp + var4_6);
                        var3_4 = var4_6 + 1;
                        var4_6 = var5_5;
lbl70:
                        // 2 sources

                        while (var4_6 == 44) {
                            this.bp += var3_4;
                            this.ch = this.charAt(this.bp);
                            this.matchStat = 3;
                            return var2_3;
                        }
                        break block26;
                    }
                    if (var4_6 != 93) {
                        this.matchStat = -1;
                        return null;
                    }
                    var4_6 = this.charAt(this.bp + var3_4);
                    ++var3_4;
                    ** GOTO lbl70
                }
                if (var4_6 != 125) {
                    this.matchStat = -1;
                    return null;
                }
                var5_5 = this.bp;
                var4_6 = var3_4 + 1;
                if ((var3_4 = (int)this.charAt(var5_5 + var3_4)) == 44) {
                    this.token = 16;
                    this.bp += var4_6;
                    this.ch = this.charAt(this.bp);
lbl93:
                    // 4 sources

                    while (true) {
                        this.matchStat = 4;
                        return var2_3;
                    }
                }
                if (var3_4 != 93) break block27;
                this.token = 15;
                this.bp += var4_6;
                this.ch = this.charAt(this.bp);
                ** GOTO lbl93
            }
            if (var3_4 != 125) break block28;
            this.token = 13;
            this.bp += var4_6;
            this.ch = this.charAt(this.bp);
            ** GOTO lbl93
        }
        if (var3_4 != 26) {
            this.matchStat = -1;
            return null;
        }
        this.bp += var4_6 - 1;
        this.token = 20;
        this.ch = (char)26;
        ** while (true)
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public String scanFieldSymbol(char[] object, SymbolTable symbolTable) {
        this.matchStat = 0;
        if (!this.charArrayCompare((char[])object)) {
            this.matchStat = -2;
            return null;
        }
        int n2 = ((char[])object).length;
        if (this.charAt(this.bp + n2) != '\"') {
            this.matchStat = -1;
            return null;
        }
        int n3 = 0;
        ++n2;
        while (true) {
            int n4 = this.bp;
            int n5 = n2 + 1;
            if ((n2 = (int)this.charAt(n4 + n2)) != 34) {
                n3 = n3 * 31 + n2;
                if (n2 == 92) {
                    this.matchStat = -1;
                    return null;
                }
            } else {
                void var2_3;
                n2 = this.bp + ((char[])object).length + 1;
                String string2 = this.addSymbol(n2, this.bp + n5 - n2 - 1, n3, (SymbolTable)var2_3);
                n2 = this.bp;
                n3 = n5 + 1;
                if ((n2 = (int)this.charAt(n2 + n5)) == 44) {
                    this.bp += n3;
                    this.ch = this.charAt(this.bp);
                    this.matchStat = 3;
                    return string2;
                }
                if (n2 != 125) {
                    this.matchStat = -1;
                    return null;
                }
                n5 = this.bp;
                n2 = n3 + 1;
                if ((n3 = (int)this.charAt(n5 + n3)) == 44) {
                    this.token = 16;
                    this.bp += n2;
                    this.ch = this.charAt(this.bp);
                } else if (n3 == 93) {
                    this.token = 15;
                    this.bp += n2;
                    this.ch = this.charAt(this.bp);
                } else if (n3 == 125) {
                    this.token = 13;
                    this.bp += n2;
                    this.ch = this.charAt(this.bp);
                } else {
                    if (n3 != 26) {
                        this.matchStat = -1;
                        return null;
                    }
                    this.token = 20;
                    this.bp += n2 - 1;
                    this.ch = (char)26;
                }
                this.matchStat = 4;
                return string2;
            }
            n2 = n5;
        }
    }

    @Override
    public final float scanFloat(char c2) {
        float f2;
        this.matchStat = 0;
        int n2 = this.charAt(this.bp + 0);
        if (n2 >= 48 && n2 <= 57) {
            char c3;
            int n3;
            int n4 = 0 + 1;
            while (true) {
                n3 = this.bp;
                n2 = n4 + 1;
                c3 = this.charAt(n3 + n4);
                if (c3 < '0' || c3 > '9') break;
                n4 = n2;
            }
            n4 = c3;
            n3 = n2;
            if (c3 == '.') {
                n3 = this.bp;
                n4 = n2 + 1;
                if ((n2 = (int)this.charAt(n3 + n2)) >= 48 && n2 <= 57) {
                    while (true) {
                        n3 = this.bp;
                        n2 = n4 + 1;
                        c3 = this.charAt(n3 + n4);
                        n4 = c3;
                        n3 = n2;
                        if (c3 >= '0') {
                            n4 = c3;
                            n3 = n2;
                            if (c3 <= '9') {
                                n4 = n2;
                                continue;
                            }
                        }
                        break;
                    }
                } else {
                    this.matchStat = -1;
                    return 0.0f;
                }
            }
            n2 = this.bp;
            f2 = Float.parseFloat(this.subString(n2, this.bp + n3 - n2 - 1));
            if (n4 == c2) {
                this.bp += n3;
                this.ch = this.charAt(this.bp);
                this.matchStat = 3;
                this.token = 16;
                return f2;
            }
        } else {
            this.matchStat = -1;
            return 0.0f;
        }
        this.matchStat = -1;
        return f2;
    }

    public final void scanIdent() {
        this.np = this.bp - 1;
        this.hasSpecial = false;
        do {
            ++this.sp;
            this.next();
        } while (Character.isLetterOrDigit(this.ch));
        String string2 = this.stringVal();
        if ("null".equalsIgnoreCase(string2)) {
            this.token = 8;
            return;
        }
        if ("new".equals(string2)) {
            this.token = 9;
            return;
        }
        if ("true".equals(string2)) {
            this.token = 6;
            return;
        }
        if ("false".equals(string2)) {
            this.token = 7;
            return;
        }
        if ("undefined".equals(string2)) {
            this.token = 23;
            return;
        }
        if ("Set".equals(string2)) {
            this.token = 21;
            return;
        }
        if ("TreeSet".equals(string2)) {
            this.token = 22;
            return;
        }
        this.token = 18;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public int scanInt(char var1_1) {
        block10: {
            block11: {
                this.matchStat = 0;
                var3_2 = this.bp;
                var4_3 = 0 + 1;
                if ((var3_2 = (int)this.charAt(var3_2 + 0)) == 45) {
                    var5_4 = true;
lbl6:
                    // 2 sources

                    while (true) {
                        if (var5_4) {
                            var3_2 = this.bp;
                            ++var4_3;
                            var3_2 = this.charAt(var3_2 + 1);
                        }
                        if (var3_2 < 48 || var3_2 > 57) break block10;
                        var3_2 -= 48;
                        while (true) {
                            var7_7 = this.bp;
                            var6_6 = var4_3 + 1;
                            var2_5 = this.charAt(var7_7 + var4_3);
                            if (var2_5 >= '0' && var2_5 <= '9') {
                                var3_2 = var3_2 * 10 + (var2_5 - 48);
                                var4_3 = var6_6;
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
            if (var2_5 == '.') {
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
        while (JSONLexerBase.isWhitespace(var2_5)) {
            var2_5 = this.charAt(this.bp + var6_6);
            ++var6_6;
lbl38:
            // 2 sources

            if (var2_5 != var1_1) continue;
            this.bp += var6_6;
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
                var3_3 = 0 + 1;
                var5_4 = this.charAt(var4_2 + 0);
                if (var5_4 == 45) {
                    var4_2 = 1;
lbl7:
                    // 2 sources

                    while (true) {
                        if (var4_2 != 0) {
                            var5_4 = this.bp;
                            ++var3_3;
                            var5_4 = this.charAt(var5_4 + 1);
                        }
                        if (var5_4 < 48 || var5_4 > 57) break block9;
                        var7_5 = var5_4 - 48;
                        while (true) {
                            var6_7 = this.bp;
                            var5_4 = var3_3 + 1;
                            var2_6 = this.charAt(var6_7 + var3_3);
                            if (var2_6 >= '0' && var2_6 <= '9') {
                                var7_5 = 10L * var7_5 + (long)(var2_6 - 48);
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
            if (var2_6 == '.') {
                this.matchStat = -1;
                return 0L;
            }
            if (var7_5 < 0L) {
                this.matchStat = -1;
                return 0L;
            }
            ** GOTO lbl39
        }
        this.matchStat = -1;
        return 0L;
        while (JSONLexerBase.isWhitespace(var2_6)) {
            var2_6 = this.charAt(this.bp + var5_4);
            ++var5_4;
lbl39:
            // 2 sources

            if (var2_6 != var1_1) continue;
            this.bp += var5_4;
            this.ch = this.charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            var9_8 = var7_5;
            if (var4_2 != 0) {
                var9_8 = -var7_5;
            }
            return var9_8;
        }
        this.matchStat = -1;
        return var7_5;
    }

    public final void scanNullOrNew() {
        if (this.ch != 'n') {
            throw new JSONException("error parse null or new");
        }
        this.next();
        if (this.ch == 'u') {
            this.next();
            if (this.ch != 'l') {
                throw new JSONException("error parse null");
            }
            this.next();
            if (this.ch != 'l') {
                throw new JSONException("error parse null");
            }
            this.next();
            if (this.ch == ' ' || this.ch == ',' || this.ch == '}' || this.ch == ']' || this.ch == '\n' || this.ch == '\r' || this.ch == '\t' || this.ch == '\u001a' || this.ch == '\f' || this.ch == '\b') {
                this.token = 8;
                return;
            }
            throw new JSONException("scan null error");
        }
        if (this.ch != 'e') {
            throw new JSONException("error parse new");
        }
        this.next();
        if (this.ch != 'w') {
            throw new JSONException("error parse new");
        }
        this.next();
        if (this.ch == ' ' || this.ch == ',' || this.ch == '}' || this.ch == ']' || this.ch == '\n' || this.ch == '\r' || this.ch == '\t' || this.ch == '\u001a' || this.ch == '\f' || this.ch == '\b') {
            this.token = 9;
            return;
        }
        throw new JSONException("scan new error");
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void scanNumber() {
        this.np = this.bp;
        if (this.ch == '-') {
            ++this.sp;
            this.next();
        }
        while (this.ch >= '0' && this.ch <= '9') {
            ++this.sp;
            this.next();
        }
        boolean bl2 = false;
        if (this.ch == '.') {
            ++this.sp;
            this.next();
            boolean bl3 = true;
            while (true) {
                bl2 = bl3;
                if (this.ch < '0') break;
                bl2 = bl3;
                if (this.ch > '9') break;
                ++this.sp;
                this.next();
            }
        }
        if (this.ch == 'L') {
            ++this.sp;
            this.next();
        } else if (this.ch == 'S') {
            ++this.sp;
            this.next();
        } else if (this.ch == 'B') {
            ++this.sp;
            this.next();
        } else if (this.ch == 'F') {
            ++this.sp;
            this.next();
            bl2 = true;
        } else if (this.ch == 'D') {
            ++this.sp;
            this.next();
            bl2 = true;
        } else if (this.ch == 'e' || this.ch == 'E') {
            ++this.sp;
            this.next();
            if (this.ch == '+' || this.ch == '-') {
                ++this.sp;
                this.next();
            }
            while (this.ch >= '0' && this.ch <= '9') {
                ++this.sp;
                this.next();
            }
            if (this.ch == 'D' || this.ch == 'F') {
                ++this.sp;
                this.next();
            }
            bl2 = true;
        }
        if (bl2) {
            this.token = 3;
            return;
        }
        this.token = 2;
    }

    /*
     * Handled impossible loop by adding 'first' condition
     * Enabled aggressive block sorting
     */
    @Override
    public String scanString(char c2) {
        this.matchStat = 0;
        int n2 = this.charAt(this.bp + 0);
        if (n2 == 110) {
            if (this.charAt(this.bp + 1) == 'u' && this.charAt(this.bp + 1 + 1) == 'l' && this.charAt(this.bp + 1 + 2) == 'l') {
                if (this.charAt(this.bp + 4) == c2) {
                    this.bp += 5;
                    this.ch = this.charAt(this.bp);
                    this.matchStat = 3;
                    return null;
                }
            } else {
                this.matchStat = -1;
                return null;
            }
            this.matchStat = -1;
            return null;
        }
        if (n2 != 34) {
            this.matchStat = -1;
            return this.stringDefaultValue();
        }
        int n3 = this.bp + 1;
        n2 = this.indexOf('\"', n3);
        if (n2 == -1) {
            throw new JSONException("unclosed str");
        }
        String string2 = this.subString(this.bp + 1, n2 - n3);
        int n4 = n2;
        String string3 = string2;
        boolean bl2 = true;
        while (true) {
            block13: {
                block12: {
                    if (bl2 && !(bl2 = false) && string2.indexOf(92) == -1) break block12;
                    int n5 = 0;
                    for (n4 = n2 - 1; n4 >= 0 && this.charAt(n4) == '\\'; ++n5, --n4) {
                    }
                    if (n5 % 2 != 0) break block13;
                    n4 = n2 - n3;
                    string3 = JSONLexerBase.readString(this.sub_chars(this.bp + 1, n4), n4);
                    n4 = n2;
                }
                if (this.charAt(this.bp + (n2 = n4 - (this.bp + 1) + 1 + 1)) != c2) break;
                this.bp += n2 + 1;
                this.ch = this.charAt(this.bp);
                this.matchStat = 3;
                return string3;
            }
            n2 = this.indexOf('\"', n2 + 1);
        }
        this.matchStat = -1;
        return string3;
    }

    @Override
    public final void scanString() {
        this.np = this.bp;
        this.hasSpecial = false;
        block22: while (true) {
            char[] cArray;
            int n2;
            char c2;
            if ((c2 = this.next()) == '\"') {
                this.token = 4;
                this.ch = this.next();
                return;
            }
            if (c2 == '\u001a') {
                if (!this.isEOF()) {
                    this.putChar('\u001a');
                    continue;
                }
                throw new JSONException("unclosed string : " + c2);
            }
            if (c2 == '\\') {
                int n3;
                if (!this.hasSpecial) {
                    this.hasSpecial = true;
                    if (this.sp >= this.sbuf.length) {
                        n2 = n3 = this.sbuf.length * 2;
                        if (this.sp > n3) {
                            n2 = this.sp;
                        }
                        cArray = new char[n2];
                        System.arraycopy(this.sbuf, 0, cArray, 0, this.sbuf.length);
                        this.sbuf = cArray;
                    }
                    this.copyTo(this.np + 1, this.sp, this.sbuf);
                }
                c2 = this.next();
                switch (c2) {
                    default: {
                        this.ch = c2;
                        throw new JSONException("unclosed string : " + c2);
                    }
                    case '0': {
                        this.putChar('\u0000');
                        continue block22;
                    }
                    case '1': {
                        this.putChar('\u0001');
                        continue block22;
                    }
                    case '2': {
                        this.putChar('\u0002');
                        continue block22;
                    }
                    case '3': {
                        this.putChar('\u0003');
                        continue block22;
                    }
                    case '4': {
                        this.putChar('\u0004');
                        continue block22;
                    }
                    case '5': {
                        this.putChar('\u0005');
                        continue block22;
                    }
                    case '6': {
                        this.putChar('\u0006');
                        continue block22;
                    }
                    case '7': {
                        this.putChar('\u0007');
                        continue block22;
                    }
                    case 'b': {
                        this.putChar('\b');
                        continue block22;
                    }
                    case 't': {
                        this.putChar('\t');
                        continue block22;
                    }
                    case 'n': {
                        this.putChar('\n');
                        continue block22;
                    }
                    case 'v': {
                        this.putChar('\u000b');
                        continue block22;
                    }
                    case 'F': 
                    case 'f': {
                        this.putChar('\f');
                        continue block22;
                    }
                    case 'r': {
                        this.putChar('\r');
                        continue block22;
                    }
                    case '\"': {
                        this.putChar('\"');
                        continue block22;
                    }
                    case '\'': {
                        this.putChar('\'');
                        continue block22;
                    }
                    case '/': {
                        this.putChar('/');
                        continue block22;
                    }
                    case '\\': {
                        this.putChar('\\');
                        continue block22;
                    }
                    case 'x': {
                        n2 = this.next();
                        n3 = this.next();
                        this.putChar((char)(digits[n2] * 16 + digits[n3]));
                        continue block22;
                    }
                    case 'u': 
                }
                this.putChar((char)Integer.parseInt(new String(new char[]{this.next(), this.next(), this.next(), this.next()}), 16));
                continue;
            }
            if (!this.hasSpecial) {
                ++this.sp;
                continue;
            }
            if (this.sp == this.sbuf.length) {
                this.putChar(c2);
                continue;
            }
            cArray = this.sbuf;
            n2 = this.sp;
            this.sp = n2 + 1;
            cArray[n2] = c2;
        }
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void scanStringArray(Collection<String> var1_1, char var2_2) {
        block11: {
            block13: {
                block9: {
                    block10: {
                        this.matchStat = 0;
                        var3_3 = this.charAt(this.bp + 0);
                        if (var3_3 == 110 && this.charAt(this.bp + 1) == 'u' && this.charAt(this.bp + 1 + 1) == 'l' && this.charAt(this.bp + 1 + 2) == 'l' && this.charAt(this.bp + 1 + 3) == var2_2) {
                            this.bp += 5;
                            this.ch = this.charAt(this.bp);
                            this.matchStat = 5;
                            return;
                        }
                        if (var3_3 != 91) {
                            this.matchStat = -1;
                            return;
                        }
                        var3_3 = this.charAt(this.bp + 1);
                        var4_4 = 0 + 1 + 1;
                        block0: while (var3_3 == 110 && this.charAt(this.bp + var4_4) == 'u' && this.charAt(this.bp + var4_4 + 1) == 'l' && this.charAt(this.bp + var4_4 + 2) == 'l') {
                            var5_5 = this.bp;
                            var3_3 = (var4_4 += 3) + 1;
                            var4_4 = this.charAt(var5_5 + var4_4);
                            var1_1.add(null);
lbl19:
                            // 2 sources

                            while (var4_4 == 44) {
                                var4_4 = this.charAt(this.bp + var3_3);
                                var5_5 = var3_3 + 1;
                                var3_3 = var4_4;
                                var4_4 = var5_5;
                                continue block0;
                            }
                            break block9;
                        }
                        if (var3_3 != 93 || var1_1.size() != 0) break block10;
                        var5_5 = this.bp;
                        var3_3 = var4_4 + 1;
                        var5_5 = this.charAt(var5_5 + var4_4);
                        var4_4 = var3_3;
                        var3_3 = var5_5;
lbl32:
                        // 2 sources

                        while (var3_3 == var2_2) {
                            this.bp += var4_4;
                            this.ch = this.charAt(this.bp);
                            this.matchStat = 3;
                            return;
                        }
                        break block11;
                    }
                    if (var3_3 != 34) {
                        this.matchStat = -1;
                        return;
                    }
                    var7_7 = this.bp + var4_4;
                    var3_3 = this.indexOf('\"', var7_7);
                    if (var3_3 == -1) {
                        throw new JSONException("unclosed str");
                    }
                    var9_9 = this.subString(this.bp + var4_4, var3_3 - var7_7);
                    var5_5 = var3_3;
                    var8_8 = var9_9;
                    if (var9_9.indexOf(92) == -1) ** GOTO lbl59
                    while (true) {
                        block12: {
                            var6_6 = 0;
                            for (var5_5 = var3_3 - 1; var5_5 >= 0 && this.charAt(var5_5) == '\\'; --var5_5) {
                                ++var6_6;
                            }
                            if (var6_6 % 2 != 0) break block12;
                            var5_5 = var3_3 - var7_7;
                            var8_8 = JSONLexerBase.readString(this.sub_chars(this.bp + var4_4, var5_5), var5_5);
                            var5_5 = var3_3;
lbl59:
                            // 2 sources

                            var4_4 += var5_5 - (this.bp + var4_4) + 1;
                            var5_5 = this.bp;
                            var3_3 = var4_4 + 1;
                            var4_4 = this.charAt(var5_5 + var4_4);
                            var1_1.add(var8_8);
                            ** GOTO lbl19
                        }
                        var3_3 = this.indexOf('\"', var3_3 + 1);
                    }
                }
                if (var4_4 != 93) break block13;
                var5_5 = this.bp;
                var4_4 = var3_3 + 1;
                var3_3 = this.charAt(var5_5 + var3_3);
                ** GOTO lbl32
            }
            this.matchStat = -1;
            return;
        }
        this.matchStat = -1;
    }

    @Override
    public final String scanSymbol(SymbolTable symbolTable) {
        this.skipWhitespace();
        if (this.ch == '\"') {
            return this.scanSymbol(symbolTable, '\"');
        }
        if (this.ch == '\'') {
            if (!this.isEnabled(Feature.AllowSingleQuotes)) {
                throw new JSONException("syntax error");
            }
            return this.scanSymbol(symbolTable, '\'');
        }
        if (this.ch == '}') {
            this.next();
            this.token = 13;
            return null;
        }
        if (this.ch == ',') {
            this.next();
            this.token = 16;
            return null;
        }
        if (this.ch == '\u001a') {
            this.token = 20;
            return null;
        }
        if (!this.isEnabled(Feature.AllowUnQuotedFieldNames)) {
            throw new JSONException("syntax error");
        }
        return this.scanSymbolUnQuoted(symbolTable);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final String scanSymbol(SymbolTable object, char c2) {
        block31: {
            int n2 = 0;
            this.np = this.bp;
            this.sp = 0;
            int n3 = 0;
            block22: while (true) {
                char[] cArray;
                int n4;
                char c3;
                if ((c3 = this.next()) == c2) {
                    this.token = 4;
                    if (n3 != 0) break;
                    c2 = this.np == -1 ? (char)'\u0000' : (char)(this.np + 1);
                    object = this.addSymbol(c2, this.sp, n2, (SymbolTable)object);
                    break block31;
                }
                if (c3 == '\u001a') {
                    throw new JSONException("unclosed.str");
                }
                if (c3 == '\\') {
                    n4 = n3;
                    if (n3 == 0) {
                        n4 = 1;
                        if (this.sp >= this.sbuf.length) {
                            int n5;
                            n3 = n5 = this.sbuf.length * 2;
                            if (this.sp > n5) {
                                n3 = this.sp;
                            }
                            cArray = new char[n3];
                            System.arraycopy(this.sbuf, 0, cArray, 0, this.sbuf.length);
                            this.sbuf = cArray;
                        }
                        this.arrayCopy(this.np + 1, this.sbuf, 0, this.sp);
                    }
                    c3 = this.next();
                    switch (c3) {
                        default: {
                            this.ch = c3;
                            throw new JSONException("unclosed.str.lit");
                        }
                        case '0': {
                            n2 = n2 * 31 + c3;
                            this.putChar('\u0000');
                            n3 = n4;
                            continue block22;
                        }
                        case '1': {
                            n2 = n2 * 31 + c3;
                            this.putChar('\u0001');
                            n3 = n4;
                            continue block22;
                        }
                        case '2': {
                            n2 = n2 * 31 + c3;
                            this.putChar('\u0002');
                            n3 = n4;
                            continue block22;
                        }
                        case '3': {
                            n2 = n2 * 31 + c3;
                            this.putChar('\u0003');
                            n3 = n4;
                            continue block22;
                        }
                        case '4': {
                            n2 = n2 * 31 + c3;
                            this.putChar('\u0004');
                            n3 = n4;
                            continue block22;
                        }
                        case '5': {
                            n2 = n2 * 31 + c3;
                            this.putChar('\u0005');
                            n3 = n4;
                            continue block22;
                        }
                        case '6': {
                            n2 = n2 * 31 + c3;
                            this.putChar('\u0006');
                            n3 = n4;
                            continue block22;
                        }
                        case '7': {
                            n2 = n2 * 31 + c3;
                            this.putChar('\u0007');
                            n3 = n4;
                            continue block22;
                        }
                        case 'b': {
                            n2 = n2 * 31 + 8;
                            this.putChar('\b');
                            n3 = n4;
                            continue block22;
                        }
                        case 't': {
                            n2 = n2 * 31 + 9;
                            this.putChar('\t');
                            n3 = n4;
                            continue block22;
                        }
                        case 'n': {
                            n2 = n2 * 31 + 10;
                            this.putChar('\n');
                            n3 = n4;
                            continue block22;
                        }
                        case 'v': {
                            n2 = n2 * 31 + 11;
                            this.putChar('\u000b');
                            n3 = n4;
                            continue block22;
                        }
                        case 'F': 
                        case 'f': {
                            n2 = n2 * 31 + 12;
                            this.putChar('\f');
                            n3 = n4;
                            continue block22;
                        }
                        case 'r': {
                            n2 = n2 * 31 + 13;
                            this.putChar('\r');
                            n3 = n4;
                            continue block22;
                        }
                        case '\"': {
                            n2 = n2 * 31 + 34;
                            this.putChar('\"');
                            n3 = n4;
                            continue block22;
                        }
                        case '\'': {
                            n2 = n2 * 31 + 39;
                            this.putChar('\'');
                            n3 = n4;
                            continue block22;
                        }
                        case '/': {
                            n2 = n2 * 31 + 47;
                            this.putChar('/');
                            n3 = n4;
                            continue block22;
                        }
                        case '\\': {
                            n2 = n2 * 31 + 92;
                            this.putChar('\\');
                            n3 = n4;
                            continue block22;
                        }
                        case 'x': {
                            char c4;
                            this.ch = c3 = this.next();
                            this.ch = c4 = this.next();
                            c3 = (char)(digits[c3] * 16 + digits[c4]);
                            n2 = n2 * 31 + c3;
                            this.putChar(c3);
                            n3 = n4;
                            continue block22;
                        }
                        case 'u': 
                    }
                    n3 = Integer.parseInt(new String(new char[]{this.next(), this.next(), this.next(), this.next()}), 16);
                    n2 = n2 * 31 + n3;
                    this.putChar((char)n3);
                    n3 = n4;
                    continue;
                }
                n2 = n2 * 31 + c3;
                if (n3 == 0) {
                    ++this.sp;
                    continue;
                }
                if (this.sp == this.sbuf.length) {
                    this.putChar(c3);
                    continue;
                }
                cArray = this.sbuf;
                n4 = this.sp;
                this.sp = n4 + 1;
                cArray[n4] = c3;
            }
            object = ((SymbolTable)object).addSymbol(this.sbuf, 0, this.sp, n2);
        }
        this.sp = 0;
        this.next();
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final String scanSymbolUnQuoted(SymbolTable symbolTable) {
        boolean[] blArray = IOUtils.firstIdentifierFlags;
        int n2 = this.ch;
        int n3 = this.ch >= blArray.length || blArray[n2] ? 1 : 0;
        if (n3 == 0) {
            throw new JSONException("illegal identifier : " + this.ch + this.info());
        }
        blArray = IOUtils.identifierFlags;
        n3 = n2;
        this.np = this.bp;
        this.sp = 1;
        while (true) {
            if ((n2 = this.next()) < blArray.length && !blArray[n2]) {
                this.ch = this.charAt(this.bp);
                this.token = 18;
                if (this.sp != 4 || n3 != 3392903 || this.charAt(this.np) != 'n' || this.charAt(this.np + 1) != 'u' || this.charAt(this.np + 2) != 'l' || this.charAt(this.np + 3) != 'l') break;
                return null;
            }
            n3 = n3 * 31 + n2;
            ++this.sp;
        }
        return this.addSymbol(this.np, this.sp, n3, symbolTable);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public String scanSymbolWithSeperator(SymbolTable object, char c2) {
        int n2;
        this.matchStat = 0;
        int n3 = this.bp;
        int n4 = 0 + 1;
        if ((n3 = (int)this.charAt(n3 + 0)) == 110) {
            if (this.charAt(this.bp + 1) == 'u' && this.charAt(this.bp + 1 + 1) == 'l' && this.charAt(this.bp + 1 + 2) == 'l') {
                if (this.charAt(this.bp + 4) == c2) {
                    this.bp += 5;
                    this.ch = this.charAt(this.bp);
                    this.matchStat = 3;
                    return null;
                }
            } else {
                this.matchStat = -1;
                return null;
            }
            this.matchStat = -1;
            return null;
        }
        if (n3 != 34) {
            this.matchStat = -1;
            return null;
        }
        n3 = 0;
        while (true) {
            int n5 = this.bp;
            n2 = n4 + 1;
            if ((n4 = (int)this.charAt(n5 + n4)) == 34) break;
            n3 = n3 * 31 + n4;
            if (n4 == 92) {
                this.matchStat = -1;
                return null;
            }
            n4 = n2;
        }
        n4 = this.bp + 0 + 1;
        object = this.addSymbol(n4, this.bp + n2 - n4 - 1, n3, (SymbolTable)object);
        char c3 = this.charAt(this.bp + n2);
        n3 = n2 + 1;
        while (true) {
            if (c3 == c2) {
                this.bp += n3;
                this.ch = this.charAt(this.bp);
                this.matchStat = 3;
                return object;
            }
            if (!JSONLexerBase.isWhitespace(c3)) {
                this.matchStat = -1;
                return object;
            }
            c3 = this.charAt(this.bp + n3);
            ++n3;
        }
    }

    public final void scanTrue() {
        if (this.ch != 't') {
            throw new JSONException("error parse true");
        }
        this.next();
        if (this.ch != 'r') {
            throw new JSONException("error parse true");
        }
        this.next();
        if (this.ch != 'u') {
            throw new JSONException("error parse true");
        }
        this.next();
        if (this.ch != 'e') {
            throw new JSONException("error parse true");
        }
        this.next();
        if (this.ch == ' ' || this.ch == ',' || this.ch == '}' || this.ch == ']' || this.ch == '\n' || this.ch == '\r' || this.ch == '\t' || this.ch == '\u001a' || this.ch == '\f' || this.ch == '\b' || this.ch == ':') {
            this.token = 6;
            return;
        }
        throw new JSONException("scan true error");
    }

    /*
     * Enabled aggressive block sorting
     */
    public final int scanType(String string2) {
        int n2;
        int n3;
        int n4 = -1;
        this.matchStat = 0;
        if (!this.charArrayCompare(typeFieldName)) {
            return -2;
        }
        int n5 = this.bp + typeFieldName.length;
        int n6 = string2.length();
        for (n3 = 0; n3 < n6; ++n3) {
            n2 = n4;
            if (string2.charAt(n3) != this.charAt(n5 + n3)) return n2;
        }
        n3 = n5 + n6;
        n2 = n4;
        if (this.charAt(n3) != '\"') return n2;
        this.ch = this.charAt(++n3);
        if (this.ch == ',') {
            n2 = n3 + 1;
            this.ch = this.charAt(n2);
            this.bp = n2;
            this.token = 16;
            return 3;
        }
        n2 = n3++;
        if (this.ch == '}') {
            this.ch = this.charAt(n3);
            if (this.ch == ',') {
                this.token = 16;
                n2 = n3 + 1;
                this.ch = this.charAt(n2);
            } else if (this.ch == ']') {
                this.token = 15;
                n2 = n3 + 1;
                this.ch = this.charAt(n2);
            } else if (this.ch == '}') {
                this.token = 13;
                n2 = n3 + 1;
                this.ch = this.charAt(n2);
            } else {
                n2 = n4;
                if (this.ch != '\u001a') return n2;
                this.token = 20;
                n2 = n3;
            }
            this.matchStat = 4;
        }
        this.bp = n2;
        return this.matchStat;
    }

    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public void setToken(int n2) {
        this.token = n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void skipComment() {
        this.next();
        if (this.ch == '/') {
            do {
                this.next();
            } while (this.ch != '\n');
            this.next();
            return;
        }
        if (this.ch != '*') {
            throw new JSONException("invalid comment");
        }
        this.next();
        while (this.ch != '\u001a') {
            if (this.ch == '*') {
                this.next();
                if (this.ch != '/') continue;
                this.next();
                return;
            }
            this.next();
        }
        return;
    }

    @Override
    public final void skipWhitespace() {
        while (this.ch <= '/') {
            if (this.ch == ' ' || this.ch == '\r' || this.ch == '\n' || this.ch == '\t' || this.ch == '\f' || this.ch == '\b') {
                this.next();
                continue;
            }
            if (this.ch != '/') break;
            this.skipComment();
        }
    }

    public final String stringDefaultValue() {
        return this.stringDefaultValue;
    }

    @Override
    public abstract String stringVal();

    public abstract String subString(int var1, int var2);

    protected abstract char[] sub_chars(int var1, int var2);

    @Override
    public final int token() {
        return this.token;
    }

    @Override
    public final String tokenName() {
        return JSONToken.name(this.token);
    }
}

