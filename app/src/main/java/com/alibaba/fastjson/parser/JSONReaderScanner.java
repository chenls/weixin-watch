/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.SymbolTable;
import com.alibaba.fastjson.util.IOUtils;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public final class JSONReaderScanner
extends JSONLexerBase {
    private static final ThreadLocal<char[]> BUF_LOCAL = new ThreadLocal();
    private char[] buf;
    private int bufLength;
    private Reader reader;

    public JSONReaderScanner(Reader reader) {
        this(reader, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONReaderScanner(Reader reader, int n2) {
        super(n2);
        this.reader = reader;
        this.buf = BUF_LOCAL.get();
        if (this.buf != null) {
            BUF_LOCAL.set(null);
        }
        if (this.buf == null) {
            this.buf = new char[8192];
        }
        try {
            this.bufLength = reader.read(this.buf);
            this.bp = -1;
            this.next();
        }
        catch (IOException iOException) {
            throw new JSONException(iOException.getMessage(), iOException);
        }
        if (this.ch == '\ufeff') {
            this.next();
        }
    }

    public JSONReaderScanner(String string2) {
        this(string2, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONReaderScanner(String string2, int n2) {
        this(new StringReader(string2), n2);
    }

    public JSONReaderScanner(char[] cArray, int n2) {
        this(cArray, n2, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONReaderScanner(char[] cArray, int n2, int n3) {
        this(new CharArrayReader(cArray, 0, n2), n3);
    }

    @Override
    public final String addSymbol(int n2, int n3, int n4, SymbolTable symbolTable) {
        return symbolTable.addSymbol(this.buf, n2, n3, n4);
    }

    @Override
    protected final void arrayCopy(int n2, char[] cArray, int n3, int n4) {
        System.arraycopy(this.buf, n2, cArray, n3, n4);
    }

    @Override
    public byte[] bytesValue() {
        return IOUtils.decodeBase64(this.buf, this.np + 1, this.sp);
    }

    @Override
    public final boolean charArrayCompare(char[] cArray) {
        for (int i2 = 0; i2 < cArray.length; ++i2) {
            if (this.charAt(this.bp + i2) == cArray[i2]) continue;
            return false;
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public final char charAt(int n2) {
        char c2 = '\u001a';
        int n3 = n2;
        if (n2 < this.bufLength) return this.buf[n3];
        if (this.bufLength == -1) {
            if (n2 >= this.sp) return c2;
            return this.buf[n2];
        }
        if (this.bp == 0) {
            char[] cArray = new char[this.buf.length * 3 / 2];
            System.arraycopy(this.buf, this.bp, cArray, 0, this.bufLength);
            n3 = cArray.length;
            int n4 = this.bufLength;
            try {
                n3 = this.reader.read(cArray, this.bufLength, n3 - n4);
                this.bufLength += n3;
                this.buf = cArray;
                n3 = n2;
            }
            catch (IOException iOException) {
                throw new JSONException(iOException.getMessage(), iOException);
            }
            return this.buf[n3];
        }
        n3 = this.bufLength - this.bp;
        if (n3 > 0) {
            System.arraycopy(this.buf, this.bp, this.buf, 0, n3);
        }
        try {
            this.bufLength = this.reader.read(this.buf, n3, this.buf.length - n3);
            if (this.bufLength == 0) {
                throw new JSONException("illegal state, textLength is zero");
            }
        }
        catch (IOException iOException) {
            throw new JSONException(iOException.getMessage(), iOException);
        }
        if (this.bufLength == -1) return c2;
        this.bufLength += n3;
        n3 = n2 - this.bp;
        this.np -= this.bp;
        this.bp = 0;
        return this.buf[n3];
    }

    @Override
    public void close() {
        super.close();
        if (this.buf.length <= 32768) {
            BUF_LOCAL.set(this.buf);
        }
        this.buf = null;
        IOUtils.close(this.reader);
    }

    @Override
    protected final void copyTo(int n2, int n3, char[] cArray) {
        System.arraycopy(this.buf, n2, cArray, 0, n3);
    }

    @Override
    public final int indexOf(char c2, int n2) {
        n2 -= this.bp;
        char c3;
        while (c2 != (c3 = this.charAt(this.bp + n2))) {
            if (c3 == '\u001a') {
                return -1;
            }
            ++n2;
        }
        return this.bp + n2;
    }

    @Override
    public boolean isEOF() {
        return this.bufLength == -1 || this.bp == this.buf.length || this.ch == '\u001a' && this.bp + 1 == this.buf.length;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final char next() {
        char c2;
        int n2;
        this.bp = n2 = this.bp + 1;
        int n3 = n2;
        if (n2 >= this.bufLength) {
            if (this.bufLength == -1) {
                return '\u001a';
            }
            if (this.sp > 0) {
                n3 = n2 = this.bufLength - this.sp;
                if (this.ch == '\"') {
                    n3 = n2 - 1;
                }
                System.arraycopy(this.buf, n3, this.buf, 0, this.sp);
            }
            this.np = -1;
            this.bp = n2 = this.sp;
            try {
                int n4;
                int n5 = this.bp;
                n3 = n4 = this.buf.length - n5;
                if (n4 == 0) {
                    char[] cArray = new char[this.buf.length * 2];
                    System.arraycopy(this.buf, 0, cArray, 0, this.buf.length);
                    this.buf = cArray;
                    n3 = this.buf.length - n5;
                }
                this.bufLength = this.reader.read(this.buf, this.bp, n3);
                if (this.bufLength == 0) {
                    throw new JSONException("illegal stat, textLength is zero");
                }
            }
            catch (IOException iOException) {
                throw new JSONException(iOException.getMessage(), iOException);
            }
            if (this.bufLength == -1) {
                this.ch = (char)26;
                return '\u001a';
            }
            this.bufLength += this.bp;
            n3 = n2;
        }
        this.ch = c2 = this.buf[n3];
        return c2;
    }

    @Override
    public final String numberString() {
        int n2;
        int n3;
        block5: {
            int n4;
            block4: {
                n2 = n3 = this.np;
                if (n3 == -1) {
                    n2 = 0;
                }
                char c2 = this.charAt(this.sp + n2 - 1);
                n4 = this.sp;
                if (c2 == 'L' || c2 == 'S' || c2 == 'B' || c2 == 'F') break block4;
                n3 = n4;
                if (c2 != 'D') break block5;
            }
            n3 = n4 - 1;
        }
        return new String(this.buf, n2, n3);
    }

    @Override
    public final String stringVal() {
        if (!this.hasSpecial) {
            int n2 = this.np + 1;
            if (n2 < 0) {
                throw new IllegalStateException();
            }
            if (n2 > this.buf.length - this.sp) {
                throw new IllegalStateException();
            }
            return new String(this.buf, n2, this.sp);
        }
        return new String(this.sbuf, 0, this.sp);
    }

    @Override
    public final String subString(int n2, int n3) {
        if (n3 < 0) {
            throw new StringIndexOutOfBoundsException(n3);
        }
        return new String(this.buf, n2, n3);
    }

    @Override
    public final char[] sub_chars(int n2, int n3) {
        if (n3 < 0) {
            throw new StringIndexOutOfBoundsException(n3);
        }
        if (n2 == 0) {
            return this.buf;
        }
        char[] cArray = new char[n3];
        System.arraycopy(this.buf, n2, cArray, 0, n3);
        return cArray;
    }
}

