/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlSerializer
 */
package com.ta.utdid2.core.persistent;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import org.xmlpull.v1.XmlSerializer;

class FastXmlSerializer
implements XmlSerializer {
    private static final int BUFFER_LEN = 8192;
    private static final String[] ESCAPE_TABLE = new String[]{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "&quot;", null, null, null, "&amp;", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "&lt;", null, "&gt;", null};
    private ByteBuffer mBytes;
    private CharsetEncoder mCharset;
    private boolean mInTag;
    private OutputStream mOutputStream;
    private int mPos;
    private final char[] mText = new char[8192];
    private Writer mWriter;

    FastXmlSerializer() {
        this.mBytes = ByteBuffer.allocate(8192);
    }

    private void append(char c2) throws IOException {
        int n2;
        int n3 = n2 = this.mPos;
        if (n2 >= 8191) {
            this.flush();
            n3 = this.mPos;
        }
        this.mText[n3] = c2;
        this.mPos = n3 + 1;
    }

    private void append(String string2) throws IOException {
        this.append(string2, 0, string2.length());
    }

    /*
     * Enabled aggressive block sorting
     */
    private void append(String string2, int n2, int n3) throws IOException {
        if (n3 <= 8192) {
            int n4;
            int n5 = n4 = this.mPos;
            if (n4 + n3 > 8192) {
                this.flush();
                n5 = this.mPos;
            }
            string2.getChars(n2, n2 + n3, this.mText, n5);
            this.mPos = n5 + n3;
            return;
        } else {
            int n6 = n2 + n3;
            while (n2 < n6) {
                int n7 = n2 + 8192;
                n3 = n7 < n6 ? 8192 : n6 - n2;
                this.append(string2, n2, n3);
                n2 = n7;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void append(char[] cArray, int n2, int n3) throws IOException {
        if (n3 <= 8192) {
            int n4;
            int n5 = n4 = this.mPos;
            if (n4 + n3 > 8192) {
                this.flush();
                n5 = this.mPos;
            }
            System.arraycopy(cArray, n2, this.mText, n5, n3);
            this.mPos = n5 + n3;
            return;
        } else {
            int n6 = n2 + n3;
            while (n2 < n6) {
                int n7 = n2 + 8192;
                n3 = n7 < n6 ? 8192 : n6 - n2;
                this.append(cArray, n2, n3);
                n2 = n7;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void escapeAndAppendString(String string2) throws IOException {
        int n2;
        int n3 = string2.length();
        char c2 = (char)ESCAPE_TABLE.length;
        String[] stringArray = ESCAPE_TABLE;
        int n4 = 0;
        for (n2 = 0; n2 < n3; ++n2) {
            int n5 = string2.charAt(n2);
            if (n5 >= c2) {
                n5 = n4;
            } else {
                String string3 = stringArray[n5];
                n5 = n4;
                if (string3 != null) {
                    if (n4 < n2) {
                        this.append(string2, n4, n2 - n4);
                    }
                    n5 = n2 + 1;
                    this.append(string3);
                }
            }
            n4 = n5;
        }
        if (n4 < n2) {
            this.append(string2, n4, n2 - n4);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void escapeAndAppendString(char[] cArray, int n2, int n3) throws IOException {
        int n4;
        char c2 = (char)ESCAPE_TABLE.length;
        String[] stringArray = ESCAPE_TABLE;
        int n5 = n2;
        for (n4 = n2; n4 < n2 + n3; ++n4) {
            int n6 = cArray[n4];
            if (n6 >= c2) {
                n6 = n5;
            } else {
                String string2 = stringArray[n6];
                n6 = n5;
                if (string2 != null) {
                    if (n5 < n4) {
                        this.append(cArray, n5, n4 - n5);
                    }
                    n6 = n4 + 1;
                    this.append(string2);
                }
            }
            n5 = n6;
        }
        if (n5 < n4) {
            this.append(cArray, n5, n4 - n5);
        }
    }

    private void flushBytes() throws IOException {
        int n2 = this.mBytes.position();
        if (n2 > 0) {
            this.mBytes.flip();
            this.mOutputStream.write(this.mBytes.array(), 0, n2);
            this.mBytes.clear();
        }
    }

    public XmlSerializer attribute(String string2, String string3, String string4) throws IOException, IllegalArgumentException, IllegalStateException {
        this.append(' ');
        if (string2 != null) {
            this.append(string2);
            this.append(':');
        }
        this.append(string3);
        this.append("=\"");
        this.escapeAndAppendString(string4);
        this.append('\"');
        return this;
    }

    public void cdsect(String string2) throws IOException, IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    public void comment(String string2) throws IOException, IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    public void docdecl(String string2) throws IOException, IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    public void endDocument() throws IOException, IllegalArgumentException, IllegalStateException {
        this.flush();
    }

    /*
     * Enabled aggressive block sorting
     */
    public XmlSerializer endTag(String string2, String string3) throws IOException, IllegalArgumentException, IllegalStateException {
        if (this.mInTag) {
            this.append(" />\n");
        } else {
            this.append("</");
            if (string2 != null) {
                this.append(string2);
                this.append(':');
            }
            this.append(string3);
            this.append(">\n");
        }
        this.mInTag = false;
        return this;
    }

    public void entityRef(String string2) throws IOException, IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void flush() throws IOException {
        if (this.mPos > 0) {
            if (this.mOutputStream == null) {
                this.mWriter.write(this.mText, 0, this.mPos);
                this.mWriter.flush();
            } else {
                CharBuffer charBuffer = CharBuffer.wrap(this.mText, 0, this.mPos);
                CoderResult coderResult = this.mCharset.encode(charBuffer, this.mBytes, true);
                while (true) {
                    if (coderResult.isError()) {
                        throw new IOException(coderResult.toString());
                    }
                    if (!coderResult.isOverflow()) break;
                    this.flushBytes();
                    coderResult = this.mCharset.encode(charBuffer, this.mBytes, true);
                }
                this.flushBytes();
                this.mOutputStream.flush();
            }
            this.mPos = 0;
        }
    }

    public int getDepth() {
        throw new UnsupportedOperationException();
    }

    public boolean getFeature(String string2) {
        throw new UnsupportedOperationException();
    }

    public String getName() {
        throw new UnsupportedOperationException();
    }

    public String getNamespace() {
        throw new UnsupportedOperationException();
    }

    public String getPrefix(String string2, boolean bl2) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    public Object getProperty(String string2) {
        throw new UnsupportedOperationException();
    }

    public void ignorableWhitespace(String string2) throws IOException, IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    public void processingInstruction(String string2) throws IOException, IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    public void setFeature(String string2, boolean bl2) throws IllegalArgumentException, IllegalStateException {
        if (string2.equals("http://xmlpull.org/v1/doc/features.html#indent-output")) {
            return;
        }
        throw new UnsupportedOperationException();
    }

    public void setOutput(OutputStream outputStream, String string2) throws IOException, IllegalArgumentException, IllegalStateException {
        if (outputStream == null) {
            throw new IllegalArgumentException();
        }
        try {
            this.mCharset = Charset.forName(string2).newEncoder();
        }
        catch (IllegalCharsetNameException illegalCharsetNameException) {
            throw (UnsupportedEncodingException)new UnsupportedEncodingException(string2).initCause(illegalCharsetNameException);
        }
        catch (UnsupportedCharsetException unsupportedCharsetException) {
            throw (UnsupportedEncodingException)new UnsupportedEncodingException(string2).initCause(unsupportedCharsetException);
        }
        this.mOutputStream = outputStream;
        return;
    }

    public void setOutput(Writer writer) throws IOException, IllegalArgumentException, IllegalStateException {
        this.mWriter = writer;
    }

    public void setPrefix(String string2, String string3) throws IOException, IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    public void setProperty(String string2, Object object) throws IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void startDocument(String string2, Boolean bl2) throws IOException, IllegalArgumentException, IllegalStateException {
        StringBuilder stringBuilder = new StringBuilder().append("<?xml version='1.0' encoding='utf-8' standalone='");
        string2 = bl2 != false ? "yes" : "no";
        this.append(stringBuilder.append(string2).append("' ?>\n").toString());
    }

    public XmlSerializer startTag(String string2, String string3) throws IOException, IllegalArgumentException, IllegalStateException {
        if (this.mInTag) {
            this.append(">\n");
        }
        this.append('<');
        if (string2 != null) {
            this.append(string2);
            this.append(':');
        }
        this.append(string3);
        this.mInTag = true;
        return this;
    }

    public XmlSerializer text(String string2) throws IOException, IllegalArgumentException, IllegalStateException {
        if (this.mInTag) {
            this.append(">");
            this.mInTag = false;
        }
        this.escapeAndAppendString(string2);
        return this;
    }

    public XmlSerializer text(char[] cArray, int n2, int n3) throws IOException, IllegalArgumentException, IllegalStateException {
        if (this.mInTag) {
            this.append(">");
            this.mInTag = false;
        }
        this.escapeAndAppendString(cArray, n2, n3);
        return this;
    }
}

