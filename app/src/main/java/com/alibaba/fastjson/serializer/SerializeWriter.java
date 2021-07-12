/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.IOUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.List;

public final class SerializeWriter
extends Writer {
    private static final ThreadLocal<char[]> bufLocal = new ThreadLocal();
    private static final ThreadLocal<byte[]> bytesBufLocal = new ThreadLocal();
    static final int nonDirectFeautres = SerializerFeature.UseSingleQuotes.mask | 0 | SerializerFeature.BrowserSecure.mask | SerializerFeature.BrowserCompatible.mask | SerializerFeature.PrettyFormat.mask | SerializerFeature.WriteEnumUsingToString.mask | SerializerFeature.WriteNonStringValueAsString.mask | SerializerFeature.WriteSlashAsSpecial.mask | SerializerFeature.IgnoreErrorGetter.mask | SerializerFeature.WriteClassName.mask | SerializerFeature.NotWriteDefaultValue.mask;
    protected boolean beanToArray;
    protected char[] buf;
    protected int count;
    protected boolean disableCircularReferenceDetect;
    protected int features;
    protected char keySeperator;
    protected boolean notWriteDefaultValue;
    protected boolean quoteFieldNames;
    protected boolean sortField;
    protected boolean useSingleQuotes;
    protected boolean writeDirect;
    protected boolean writeEnumUsingName;
    protected boolean writeEnumUsingToString;
    protected boolean writeNonStringValueAsString;
    private final Writer writer;

    public SerializeWriter() {
        this((Writer)null);
    }

    public SerializeWriter(int n2) {
        this(null, n2);
    }

    public SerializeWriter(Writer writer) {
        this(writer, JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.EMPTY);
    }

    public SerializeWriter(Writer writer, int n2) {
        this.writer = writer;
        if (n2 <= 0) {
            throw new IllegalArgumentException("Negative initial size: " + n2);
        }
        this.buf = new char[n2];
    }

    /*
     * Enabled aggressive block sorting
     */
    public SerializeWriter(Writer writer, int n2, SerializerFeature ... serializerFeatureArray) {
        this.writer = writer;
        this.buf = bufLocal.get();
        if (this.buf != null) {
            bufLocal.set(null);
        } else {
            this.buf = new char[2048];
        }
        int n3 = n2;
        int n4 = serializerFeatureArray.length;
        n2 = 0;
        while (true) {
            if (n2 >= n4) {
                this.features = n3;
                this.computeFeatures();
                return;
            }
            n3 |= serializerFeatureArray[n2].getMask();
            ++n2;
        }
    }

    public SerializeWriter(Writer writer, SerializerFeature ... serializerFeatureArray) {
        this(writer, 0, serializerFeatureArray);
    }

    public SerializeWriter(SerializerFeature ... serializerFeatureArray) {
        this((Writer)null, serializerFeatureArray);
    }

    private int encodeToUTF8(OutputStream outputStream) throws IOException {
        byte[] byArray;
        int n2 = (int)((double)this.count * 3.0);
        byte[] byArray2 = byArray = bytesBufLocal.get();
        if (byArray == null) {
            byArray2 = new byte[8192];
            bytesBufLocal.set(byArray2);
        }
        byArray = byArray2;
        if (byArray2.length < n2) {
            byArray = new byte[n2];
        }
        n2 = IOUtils.encodeUTF8(this.buf, 0, this.count, byArray);
        outputStream.write(byArray, 0, n2);
        return n2;
    }

    private byte[] encodeToUTF8Bytes() {
        byte[] byArray;
        int n2 = (int)((double)this.count * 3.0);
        byte[] byArray2 = byArray = bytesBufLocal.get();
        if (byArray == null) {
            byArray2 = new byte[8192];
            bytesBufLocal.set(byArray2);
        }
        byArray = byArray2;
        if (byArray2.length < n2) {
            byArray = new byte[n2];
        }
        n2 = IOUtils.encodeUTF8(this.buf, 0, this.count, byArray);
        byArray2 = new byte[n2];
        System.arraycopy(byArray, 0, byArray2, 0, n2);
        return byArray2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static boolean isSpecial(char c2, int n2) {
        boolean bl2 = true;
        if (c2 == ' ') {
            return false;
        }
        if (c2 == '/') {
            if ((SerializerFeature.WriteSlashAsSpecial.mask & n2) == 0) return false;
            return bl2;
        }
        if (c2 > '#') {
            if (c2 != '\\') return false;
        }
        if (c2 <= '\u001f') return true;
        if (c2 == '\\') return true;
        if (c2 != '\"') return false;
        return true;
    }

    private void writeEnumFieldValue(char c2, String string2, String string3) {
        if (this.useSingleQuotes) {
            this.writeFieldValue(c2, string2, string3);
            return;
        }
        this.writeFieldValueStringWithDoubleQuote(c2, string2, string3);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void writeKeyWithSingleQuoteIfHasSpecial(String object) {
        int n2;
        int n3;
        byte[] byArray;
        block20: {
            char c2;
            block19: {
                block17: {
                    block18: {
                        byArray = IOUtils.specicalFlags_singleQuotes;
                        n3 = ((String)object).length();
                        n2 = this.count + n3 + 1;
                        if (n2 <= this.buf.length) break block17;
                        if (this.writer == null) break block18;
                        if (n3 == 0) {
                            this.write(39);
                            this.write(39);
                            this.write(58);
                            return;
                        }
                        break block19;
                    }
                    this.expandCapacity(n2);
                }
                if (n3 == 0) {
                    if (this.count + 3 > this.buf.length) {
                        this.expandCapacity(this.count + 3);
                    }
                    object = this.buf;
                    int n4 = this.count;
                    this.count = n4 + 1;
                    object[n4] = 39;
                    object = this.buf;
                    n4 = this.count;
                    this.count = n4 + 1;
                    object[n4] = 39;
                    object = this.buf;
                    n4 = this.count;
                    this.count = n4 + 1;
                    object[n4] = 58;
                    return;
                }
                break block20;
            }
            char c3 = '\u0000';
            int n5 = 0;
            while (true) {
                block22: {
                    block21: {
                        c2 = c3;
                        if (n5 >= n3) break block21;
                        c2 = ((String)object).charAt(n5);
                        if (c2 >= byArray.length || byArray[c2] == 0) break block22;
                        c2 = '\u0001';
                    }
                    if (c2 != '\u0000') {
                        this.write(39);
                    }
                    break;
                }
                ++n5;
            }
            for (n5 = 0; n5 < n3; ++n5) {
                c3 = ((String)object).charAt(n5);
                if (c3 < byArray.length && byArray[c3] != 0) {
                    this.write(92);
                    this.write(IOUtils.replaceChars[c3]);
                    continue;
                }
                this.write(c3);
            }
            if (c2 != '\u0000') {
                this.write(39);
            }
            this.write(58);
            return;
        }
        int n6 = this.count;
        int n7 = n6 + n3;
        ((String)object).getChars(0, n3, this.buf, n6);
        this.count = n2;
        boolean bl2 = false;
        int n8 = n6;
        while (true) {
            if (n8 >= n7) {
                this.buf[n2 - 1] = 58;
                return;
            }
            char c4 = this.buf[n8];
            int n9 = n7;
            boolean bl3 = bl2;
            n3 = n8;
            int n10 = n2;
            if (c4 < byArray.length) {
                n9 = n7;
                bl3 = bl2;
                n3 = n8;
                n10 = n2;
                if (byArray[c4] != 0) {
                    if (!bl2) {
                        n10 = n2 + 3;
                        if (n10 > this.buf.length) {
                            this.expandCapacity(n10);
                        }
                        this.count = n10;
                        System.arraycopy(this.buf, n8 + 1, this.buf, n8 + 3, n7 - n8 - 1);
                        System.arraycopy(this.buf, 0, this.buf, 1, n8);
                        this.buf[n6] = 39;
                        object = this.buf;
                        object[++n8] = 92;
                        object = this.buf;
                        n3 = n8 + 1;
                        object[n3] = IOUtils.replaceChars[c4];
                        n9 = n7 + 2;
                        this.buf[this.count - 2] = 39;
                        bl3 = true;
                    } else {
                        n10 = n2 + 1;
                        if (n10 > this.buf.length) {
                            this.expandCapacity(n10);
                        }
                        this.count = n10;
                        System.arraycopy(this.buf, n8 + 1, this.buf, n8 + 2, n7 - n8);
                        this.buf[n8] = 92;
                        object = this.buf;
                        n3 = n8 + 1;
                        object[n3] = IOUtils.replaceChars[c4];
                        n9 = n7 + 1;
                        bl3 = bl2;
                    }
                }
            }
            n8 = n3 + 1;
            n7 = n9;
            bl2 = bl3;
            n2 = n10;
        }
    }

    @Override
    public SerializeWriter append(char c2) {
        this.write(c2);
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public SerializeWriter append(CharSequence charSequence) {
        charSequence = charSequence == null ? "null" : charSequence.toString();
        this.write((String)charSequence, 0, ((String)charSequence).length());
        return this;
    }

    @Override
    public SerializeWriter append(CharSequence charSequence, int n2, int n3) {
        CharSequence charSequence2 = charSequence;
        if (charSequence == null) {
            charSequence2 = "null";
        }
        charSequence = charSequence2.subSequence(n2, n3).toString();
        this.write((String)charSequence, 0, ((String)charSequence).length());
        return this;
    }

    @Override
    public void close() {
        if (this.writer != null && this.count > 0) {
            this.flush();
        }
        if (this.buf.length <= 8192) {
            bufLocal.set(this.buf);
        }
        this.buf = null;
    }

    /*
     * Unable to fully structure code
     */
    protected void computeFeatures() {
        block23: {
            block22: {
                block21: {
                    block20: {
                        block19: {
                            block18: {
                                block17: {
                                    block16: {
                                        block15: {
                                            block14: {
                                                var3_1 = true;
                                                if ((this.features & SerializerFeature.QuoteFieldNames.mask) != 0) {
                                                    var2_2 = true;
lbl4:
                                                    // 2 sources

                                                    while (true) {
                                                        this.quoteFieldNames = var2_2;
                                                        if ((this.features & SerializerFeature.UseSingleQuotes.mask) == 0) break block14;
                                                        var2_2 = true;
lbl8:
                                                        // 2 sources

                                                        while (true) {
                                                            this.useSingleQuotes = var2_2;
                                                            if ((this.features & SerializerFeature.SortField.mask) == 0) break block15;
                                                            var2_2 = true;
lbl12:
                                                            // 2 sources

                                                            while (true) {
                                                                this.sortField = var2_2;
                                                                if ((this.features & SerializerFeature.DisableCircularReferenceDetect.mask) == 0) break block16;
                                                                var2_2 = true;
lbl16:
                                                                // 2 sources

                                                                while (true) {
                                                                    this.disableCircularReferenceDetect = var2_2;
                                                                    if ((this.features & SerializerFeature.BeanToArray.mask) == 0) break block17;
                                                                    var2_2 = true;
lbl20:
                                                                    // 2 sources

                                                                    while (true) {
                                                                        this.beanToArray = var2_2;
                                                                        if ((this.features & SerializerFeature.WriteNonStringValueAsString.mask) == 0) break block18;
                                                                        var2_2 = true;
lbl24:
                                                                        // 2 sources

                                                                        while (true) {
                                                                            this.writeNonStringValueAsString = var2_2;
                                                                            if ((this.features & SerializerFeature.NotWriteDefaultValue.mask) == 0) break block19;
                                                                            var2_2 = true;
lbl28:
                                                                            // 2 sources

                                                                            while (true) {
                                                                                this.notWriteDefaultValue = var2_2;
                                                                                if ((this.features & SerializerFeature.WriteEnumUsingName.mask) == 0) break block20;
                                                                                var2_2 = true;
lbl32:
                                                                                // 2 sources

                                                                                while (true) {
                                                                                    this.writeEnumUsingName = var2_2;
                                                                                    if ((this.features & SerializerFeature.WriteEnumUsingToString.mask) == 0) break block21;
                                                                                    var2_2 = true;
lbl36:
                                                                                    // 2 sources

                                                                                    while (true) {
                                                                                        this.writeEnumUsingToString = var2_2;
                                                                                        if (!this.quoteFieldNames || (this.features & SerializeWriter.nonDirectFeautres) != 0) break block22;
                                                                                        var2_2 = var3_1;
                                                                                        if (!this.beanToArray) {
                                                                                            if (!this.writeEnumUsingName) break block22;
                                                                                            var2_2 = var3_1;
                                                                                        }
lbl43:
                                                                                        // 4 sources

                                                                                        while (true) {
                                                                                            this.writeDirect = var2_2;
                                                                                            if (this.useSingleQuotes) {
                                                                                                var1_3 = 39;
lbl47:
                                                                                                // 2 sources

                                                                                                while (true) {
                                                                                                    this.keySeperator = (char)var1_3;
                                                                                                    return;
                                                                                                }
                                                                                            }
                                                                                            break block23;
                                                                                            break;
                                                                                        }
                                                                                        break;
                                                                                    }
                                                                                    break;
                                                                                }
                                                                                break;
                                                                            }
                                                                            break;
                                                                        }
                                                                        break;
                                                                    }
                                                                    break;
                                                                }
                                                                break;
                                                            }
                                                            break;
                                                        }
                                                        break;
                                                    }
                                                }
                                                var2_2 = false;
                                                ** while (true)
                                            }
                                            var2_2 = false;
                                            ** while (true)
                                        }
                                        var2_2 = false;
                                        ** while (true)
                                    }
                                    var2_2 = false;
                                    ** while (true)
                                }
                                var2_2 = false;
                                ** while (true)
                            }
                            var2_2 = false;
                            ** while (true)
                        }
                        var2_2 = false;
                        ** while (true)
                    }
                    var2_2 = false;
                    ** while (true)
                }
                var2_2 = false;
                ** while (true)
            }
            var2_2 = false;
            ** while (true)
        }
        var1_3 = 34;
        ** while (true)
    }

    /*
     * Enabled aggressive block sorting
     */
    public void config(SerializerFeature serializerFeature, boolean bl2) {
        block4: {
            block3: {
                if (!bl2) break block3;
                this.features |= serializerFeature.getMask();
                if (serializerFeature == SerializerFeature.WriteEnumUsingToString) {
                    this.features &= ~SerializerFeature.WriteEnumUsingName.getMask();
                    break block4;
                } else if (serializerFeature == SerializerFeature.WriteEnumUsingName) {
                    this.features &= ~SerializerFeature.WriteEnumUsingToString.getMask();
                }
                break block4;
            }
            this.features &= ~serializerFeature.getMask();
        }
        this.computeFeatures();
    }

    public void expandCapacity(int n2) {
        int n3;
        int n4 = n3 = this.buf.length * 3 / 2 + 1;
        if (n3 < n2) {
            n4 = n2;
        }
        char[] cArray = new char[n4];
        System.arraycopy(this.buf, 0, cArray, 0, this.count);
        this.buf = cArray;
    }

    @Override
    public void flush() {
        if (this.writer == null) {
            return;
        }
        try {
            this.writer.write(this.buf, 0, this.count);
            this.writer.flush();
            this.count = 0;
            return;
        }
        catch (IOException iOException) {
            throw new JSONException(iOException.getMessage(), iOException);
        }
    }

    public int getBufferLength() {
        return this.buf.length;
    }

    public boolean isEnabled(int n2) {
        return (this.features & n2) != 0;
    }

    public boolean isEnabled(SerializerFeature serializerFeature) {
        return (this.features & serializerFeature.mask) != 0;
    }

    public boolean isNotWriteDefaultValue() {
        return this.notWriteDefaultValue;
    }

    public boolean isSortField() {
        return this.sortField;
    }

    public int size() {
        return this.count;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public byte[] toBytes(String object) {
        if (object == null || "UTF-8".equals(object)) {
            object = IOUtils.UTF8;
            return this.toBytes((Charset)object);
        }
        object = Charset.forName((String)object);
        return this.toBytes((Charset)object);
    }

    public byte[] toBytes(Charset charset) {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        }
        if (charset == IOUtils.UTF8) {
            return this.encodeToUTF8Bytes();
        }
        return new String(this.buf, 0, this.count).getBytes(charset);
    }

    public char[] toCharArray() {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        }
        char[] cArray = new char[this.count];
        System.arraycopy(this.buf, 0, cArray, 0, this.count);
        return cArray;
    }

    public String toString() {
        return new String(this.buf, 0, this.count);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void write(int n2) {
        int n3;
        int n4 = n3 = this.count + 1;
        if (n3 > this.buf.length) {
            if (this.writer == null) {
                this.expandCapacity(n3);
                n4 = n3;
            } else {
                this.flush();
                n4 = 1;
            }
        }
        this.buf[this.count] = (char)n2;
        this.count = n4;
    }

    @Override
    public void write(String string2) {
        if (string2 == null) {
            this.writeNull();
            return;
        }
        this.write(string2, 0, string2.length());
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void write(String string2, int n2, int n3) {
        int n4;
        int n5 = n4 = this.count + n3;
        int n6 = n2;
        int n7 = n3;
        if (n4 > this.buf.length) {
            n5 = n2;
            n6 = n3;
            if (this.writer == null) {
                this.expandCapacity(n4);
                n7 = n3;
                n6 = n2;
                n5 = n4;
            } else {
                do {
                    n3 = this.buf.length - this.count;
                    string2.getChars(n5, n5 + n3, this.buf, this.count);
                    this.count = this.buf.length;
                    this.flush();
                    n2 = n6 - n3;
                    n5 = n3 = n5 + n3;
                    n6 = n2;
                } while (n2 > this.buf.length);
                n5 = n2;
                n6 = n3;
                n7 = n2;
            }
        }
        string2.getChars(n6, n6 + n7, this.buf, this.count);
        this.count = n5;
    }

    /*
     * Unable to fully structure code
     */
    public void write(List<String> var1_1) {
        if (var1_1.isEmpty()) {
            this.write("[]");
            return;
        }
        var5_2 = this.count;
        var7_4 = var1_1.size();
        block0: for (var4_3 = 0; var4_3 < var7_4; ++var4_3) {
            block16: {
                block14: {
                    block13: {
                        block15: {
                            var9_9 = var1_1.get(var4_3);
                            var2_5 = 0;
                            if (var9_9 != null) break block15;
                            var3_6 = 1;
lbl11:
                            // 3 sources

                            while (var3_6 != 0) {
                                this.write(91);
                                block2: for (var2_5 = 0; var2_5 < var1_1.size(); ++var2_5) {
                                    var9_9 = var1_1.get(var2_5);
                                    if (var2_5 != 0) {
                                        this.write(44);
                                    }
                                    if (var9_9 == null) {
                                        this.write("null");
lbl19:
                                        // 2 sources

                                        continue block2;
                                    }
                                    break block13;
                                }
                                break block14;
                            }
                            break block16;
                        }
                        var6_7 = 0;
                        var8_8 = var9_9.length();
                        block4: while (true) {
                            var3_6 = var2_5;
                            if (var6_7 >= var8_8) ** GOTO lbl11
                            var2_5 = var9_9.charAt(var6_7);
                            if (var2_5 >= 32 && var2_5 <= 126 && var2_5 != 34 && var2_5 != 92) break;
                            var2_5 = 1;
lbl33:
                            // 2 sources

                            while (true) {
                                var3_6 = var2_5;
                                if (var2_5 != 0) ** GOTO lbl11
                                ++var6_7;
                                continue block4;
                                break;
                            }
                            break;
                        }
                        var2_5 = 0;
                        ** continue;
                    }
                    this.writeStringWithDoubleQuote((String)var9_9, '\u0000');
                    ** continue;
                }
                this.write(93);
                return;
            }
            var2_5 = var3_6 = var9_9.length() + var5_2 + 3;
            if (var4_3 == var1_1.size() - 1) {
                var2_5 = var3_6 + 1;
            }
            if (var2_5 > this.buf.length) {
                this.count = var5_2;
                this.expandCapacity(var2_5);
            }
            if (var4_3 == 0) {
                var10_10 = this.buf;
                var2_5 = var5_2 + 1;
                var10_10[var5_2] = 91;
lbl57:
                // 2 sources

                while (true) {
                    var10_10 = this.buf;
                    var3_6 = var2_5 + 1;
                    var10_10[var2_5] = 34;
                    var9_9.getChars(0, var9_9.length(), this.buf, var3_6);
                    var2_5 = var3_6 + var9_9.length();
                    var9_9 = this.buf;
                    var5_2 = var2_5 + 1;
                    var9_9[var2_5] = 34;
                    continue block0;
                    break;
                }
            }
            var10_10 = this.buf;
            var2_5 = var5_2 + 1;
            var10_10[var5_2] = 44;
            ** continue;
        }
        this.buf[var5_2] = 93;
        this.count = var5_2 + 1;
    }

    public void write(boolean bl2) {
        if (bl2) {
            this.write("true");
            return;
        }
        this.write("false");
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void write(char[] cArray, int n2, int n3) {
        int n4;
        if (n2 < 0 || n2 > cArray.length || n3 < 0 || n2 + n3 > cArray.length || n2 + n3 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (n3 == 0) {
            return;
        }
        int n5 = n4 = this.count + n3;
        int n6 = n2;
        int n7 = n3;
        if (n4 > this.buf.length) {
            n5 = n2;
            n6 = n3;
            if (this.writer == null) {
                this.expandCapacity(n4);
                n7 = n3;
                n6 = n2;
                n5 = n4;
            } else {
                do {
                    n3 = this.buf.length - this.count;
                    System.arraycopy(cArray, n5, this.buf, this.count, n3);
                    this.count = this.buf.length;
                    this.flush();
                    n2 = n6 - n3;
                    n5 = n3 = n5 + n3;
                    n6 = n2;
                } while (n2 > this.buf.length);
                n5 = n2;
                n6 = n3;
                n7 = n2;
            }
        }
        System.arraycopy(cArray, n6, this.buf, this.count, n7);
        this.count = n5;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public void writeByteArray(byte[] object) {
        int n2 = ((byte[])object).length;
        int n3 = this.useSingleQuotes ? 39 : 34;
        if (n2 == 0) {
            void var1_3;
            if (this.useSingleQuotes) {
                String string2 = "''";
            } else {
                String string3 = "\"\"";
            }
            this.write((String)var1_3);
            return;
        }
        char[] cArray = IOUtils.CA;
        int n4 = n2 / 3 * 3;
        int n5 = (n2 - 1) / 3;
        int n6 = this.count;
        int n7 = this.count + (n5 + 1 << 2) + 2;
        if (n7 > this.buf.length) {
            if (this.writer != null) {
                this.write(n3);
                for (n6 = 0; n6 < n4; ++n6) {
                    n5 = n6 + 1;
                    n7 = object[n6];
                    n6 = n5 + 1;
                    n5 = (n7 & 0xFF) << 16 | (object[n5] & 0xFF) << 8 | object[n6] & 0xFF;
                    this.write(cArray[n5 >>> 18 & 0x3F]);
                    this.write(cArray[n5 >>> 12 & 0x3F]);
                    this.write(cArray[n5 >>> 6 & 0x3F]);
                    this.write(cArray[n5 & 0x3F]);
                }
                n5 = n2 - n4;
                if (n5 > 0) {
                    n4 = object[n4];
                    n6 = n5 == 2 ? (object[n2 - 1] & 0xFF) << 2 : 0;
                    n6 = (n4 & 0xFF) << 10 | n6;
                    this.write(cArray[n6 >> 12]);
                    this.write(cArray[n6 >>> 6 & 0x3F]);
                    n6 = n5 == 2 ? cArray[n6 & 0x3F] : 61;
                    this.write(n6);
                    this.write(61);
                }
                this.write(n3);
                return;
            }
            this.expandCapacity(n7);
        }
        this.count = n7;
        this.buf[n6] = n3;
        ++n6;
        for (n5 = 0; n5 < n4; ++n5) {
            int n8 = n5 + 1;
            int n9 = object[n5];
            n5 = n8 + 1;
            n8 = (n9 & 0xFF) << 16 | (object[n8] & 0xFF) << 8 | object[n5] & 0xFF;
            char[] cArray2 = this.buf;
            n9 = n6 + 1;
            cArray2[n6] = cArray[n8 >>> 18 & 0x3F];
            cArray2 = this.buf;
            n6 = n9 + 1;
            cArray2[n9] = cArray[n8 >>> 12 & 0x3F];
            cArray2 = this.buf;
            n9 = n6 + 1;
            cArray2[n6] = cArray[n8 >>> 6 & 0x3F];
            cArray2 = this.buf;
            n6 = n9 + 1;
            cArray2[n9] = cArray[n8 & 0x3F];
        }
        n5 = n2 - n4;
        if (n5 > 0) {
            n4 = object[n4];
            n6 = n5 == 2 ? (object[n2 - 1] & 0xFF) << 2 : 0;
            n6 = (n4 & 0xFF) << 10 | n6;
            this.buf[n7 - 5] = cArray[n6 >> 12];
            this.buf[n7 - 4] = cArray[n6 >>> 6 & 0x3F];
            char[] cArray3 = this.buf;
            int n10 = n5 == 2 ? cArray[n6 & 0x3F] : 61;
            cArray3[n7 - 3] = n10;
            this.buf[n7 - 2] = 61;
        }
        this.buf[n7 - 1] = n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeDouble(double d2, boolean bl2) {
        if (Double.isNaN(d2) || Double.isInfinite(d2)) {
            this.writeNull();
            return;
        } else {
            String string2;
            String string3 = string2 = Double.toString(d2);
            if (string2.endsWith(".0")) {
                string3 = string2.substring(0, string2.length() - 2);
            }
            this.write(string3);
            if (!bl2 || !this.isEnabled(SerializerFeature.WriteClassName)) return;
            this.write(68);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeEnum(Enum<?> enum_) {
        if (enum_ == null) {
            this.writeNull();
            return;
        }
        String string2 = null;
        if (this.writeEnumUsingName && !this.writeEnumUsingToString) {
            string2 = enum_.name();
        } else if (this.writeEnumUsingToString) {
            string2 = enum_.toString();
        }
        if (string2 == null) {
            this.writeInt(enum_.ordinal());
            return;
        }
        int n2 = this.isEnabled(SerializerFeature.UseSingleQuotes) ? 39 : 34;
        this.write(n2);
        this.write(string2);
        this.write(n2);
    }

    public void writeFieldName(String string2) {
        this.writeFieldName(string2, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeFieldName(String string2, boolean bl2) {
        if (string2 == null) {
            this.write("null:");
            return;
        }
        if (this.useSingleQuotes) {
            if (this.quoteFieldNames) {
                this.writeStringWithSingleQuote(string2);
                this.write(58);
                return;
            }
            this.writeKeyWithSingleQuoteIfHasSpecial(string2);
            return;
        }
        if (this.quoteFieldNames) {
            this.writeStringWithDoubleQuote(string2, ':');
            return;
        }
        boolean bl3 = string2.length() == 0;
        int n2 = 0;
        while (true) {
            block9: {
                boolean bl4;
                block8: {
                    bl4 = bl3;
                    if (n2 >= string2.length()) break block8;
                    if (!SerializeWriter.isSpecial(string2.charAt(n2), 0)) break block9;
                    bl4 = true;
                }
                if (!bl4) break;
                this.writeStringWithDoubleQuote(string2, ':');
                return;
            }
            ++n2;
        }
        this.write(string2);
        this.write(58);
    }

    public void writeFieldNameDirect(String string2) {
        int n2 = string2.length();
        int n3 = this.count + n2 + 3;
        if (n3 > this.buf.length) {
            this.expandCapacity(n3);
        }
        int n4 = this.count;
        this.buf[this.count] = 34;
        string2.getChars(0, n2, this.buf, n4 + 1);
        this.count = n3;
        this.buf[this.count - 2] = 34;
        this.buf[this.count - 1] = 58;
    }

    public void writeFieldValue(char c2, String string2, char c3) {
        this.write(c2);
        this.writeFieldName(string2);
        if (c3 == '\u0000') {
            this.writeString("\u0000");
            return;
        }
        this.writeString(Character.toString(c3));
    }

    public void writeFieldValue(char c2, String string2, double d2) {
        this.write(c2);
        this.writeFieldName(string2);
        this.writeDouble(d2, false);
    }

    public void writeFieldValue(char c2, String string2, float f2) {
        this.write(c2);
        this.writeFieldName(string2);
        this.writeFloat(f2, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeFieldValue(char c2, String string2, int n2) {
        if (n2 == Integer.MIN_VALUE || !this.quoteFieldNames) {
            this.write(c2);
            this.writeFieldName(string2);
            this.writeInt(n2);
            return;
        }
        int n3 = n2 < 0 ? IOUtils.stringSize(-n2) + 1 : IOUtils.stringSize(n2);
        int n4 = string2.length();
        int n5 = this.count + n4 + 4 + n3;
        if (n5 > this.buf.length) {
            if (this.writer != null) {
                this.write(c2);
                this.writeFieldName(string2);
                this.writeInt(n2);
                return;
            }
            this.expandCapacity(n5);
        }
        n3 = this.count;
        this.count = n5;
        this.buf[n3] = c2;
        n5 = n3 + n4 + 1;
        this.buf[n3 + 1] = this.keySeperator;
        string2.getChars(0, n4, this.buf, n3 + 2);
        this.buf[n5 + 1] = this.keySeperator;
        this.buf[n5 + 2] = 58;
        IOUtils.getChars(n2, this.count, this.buf);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeFieldValue(char c2, String string2, long l2) {
        if (l2 == Long.MIN_VALUE || !this.quoteFieldNames) {
            this.write(c2);
            this.writeFieldName(string2);
            this.writeLong(l2);
            return;
        }
        int n2 = l2 < 0L ? IOUtils.stringSize(-l2) + 1 : IOUtils.stringSize(l2);
        int n3 = string2.length();
        int n4 = this.count + n3 + 4 + n2;
        if (n4 > this.buf.length) {
            if (this.writer != null) {
                this.write(c2);
                this.writeFieldName(string2);
                this.writeLong(l2);
                return;
            }
            this.expandCapacity(n4);
        }
        n2 = this.count;
        this.count = n4;
        this.buf[n2] = c2;
        n4 = n2 + n3 + 1;
        this.buf[n2 + 1] = this.keySeperator;
        string2.getChars(0, n3, this.buf, n2 + 2);
        this.buf[n4 + 1] = this.keySeperator;
        this.buf[n4 + 2] = 58;
        IOUtils.getChars(l2, this.count, this.buf);
    }

    public void writeFieldValue(char c2, String string2, Enum<?> enum_) {
        if (enum_ == null) {
            this.write(c2);
            this.writeFieldName(string2);
            this.writeNull();
            return;
        }
        if (this.writeEnumUsingName && !this.writeEnumUsingToString) {
            this.writeEnumFieldValue(c2, string2, enum_.name());
            return;
        }
        if (this.writeEnumUsingToString) {
            this.writeEnumFieldValue(c2, string2, enum_.toString());
            return;
        }
        this.writeFieldValue(c2, string2, enum_.ordinal());
    }

    public void writeFieldValue(char c2, String string2, String string3) {
        if (this.quoteFieldNames) {
            if (this.useSingleQuotes) {
                this.write(c2);
                this.writeFieldName(string2);
                if (string3 == null) {
                    this.writeNull();
                    return;
                }
                this.writeString(string3);
                return;
            }
            if (this.isEnabled(SerializerFeature.BrowserSecure)) {
                this.write(c2);
                this.writeStringWithDoubleQuote(string2, ':');
                this.writeStringWithDoubleQuote(string3, '\u0000');
                return;
            }
            if (this.isEnabled(SerializerFeature.BrowserCompatible)) {
                this.write(c2);
                this.writeStringWithDoubleQuote(string2, ':');
                this.writeStringWithDoubleQuote(string3, '\u0000');
                return;
            }
            this.writeFieldValueStringWithDoubleQuoteCheck(c2, string2, string3);
            return;
        }
        this.write(c2);
        this.writeFieldName(string2);
        if (string3 == null) {
            this.writeNull();
            return;
        }
        this.writeString(string3);
    }

    public void writeFieldValue(char c2, String string2, BigDecimal bigDecimal) {
        this.write(c2);
        this.writeFieldName(string2);
        if (bigDecimal == null) {
            this.writeNull();
            return;
        }
        this.write(bigDecimal.toString());
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeFieldValue(char c2, String string2, boolean bl2) {
        int n2 = bl2 ? 4 : 5;
        int n3 = string2.length();
        int n4 = this.count + n3 + 4 + n2;
        if (n4 > this.buf.length) {
            if (this.writer != null) {
                this.write(c2);
                this.writeString(string2);
                this.write(58);
                this.write(bl2);
                return;
            }
            this.expandCapacity(n4);
        }
        n2 = this.count;
        this.count = n4;
        this.buf[n2] = c2;
        n4 = n2 + n3 + 1;
        this.buf[n2 + 1] = this.keySeperator;
        string2.getChars(0, n3, this.buf, n2 + 2);
        this.buf[n4 + 1] = this.keySeperator;
        if (bl2) {
            System.arraycopy(":true".toCharArray(), 0, this.buf, n4 + 2, 5);
            return;
        }
        System.arraycopy(":false".toCharArray(), 0, this.buf, n4 + 2, 6);
    }

    public void writeFieldValueStringWithDoubleQuote(char c2, String object, String string2) {
        int n2 = ((String)object).length();
        int n3 = this.count;
        int n4 = string2.length();
        if ((n3 += n2 + n4 + 6) > this.buf.length) {
            if (this.writer != null) {
                this.write(c2);
                this.writeStringWithDoubleQuote((String)object, ':');
                this.writeStringWithDoubleQuote(string2, '\u0000');
                return;
            }
            this.expandCapacity(n3);
        }
        this.buf[this.count] = c2;
        int n5 = this.count + 2;
        int n6 = n5 + n2;
        this.buf[this.count + 1] = 34;
        ((String)object).getChars(0, n2, this.buf, n5);
        this.count = n3;
        this.buf[n6] = 34;
        n2 = n6 + 1;
        object = this.buf;
        n3 = n2 + 1;
        object[n2] = 58;
        this.buf[n3] = 34;
        string2.getChars(0, n4, this.buf, n3 + 1);
        this.buf[this.count - 1] = 34;
    }

    /*
     * Handled impossible loop by adding 'first' condition
     * Enabled aggressive block sorting
     */
    public void writeFieldValueStringWithDoubleQuoteCheck(char c2, String object, String string2) {
        int n2;
        int n3 = ((String)object).length();
        int n4 = this.count;
        if (string2 == null) {
            n2 = 4;
            n4 += n3 + 8;
        } else {
            n2 = string2.length();
            n4 += n3 + n2 + 6;
        }
        if (n4 > this.buf.length) {
            if (this.writer != null) {
                this.write(c2);
                this.writeStringWithDoubleQuote((String)object, ':');
                this.writeStringWithDoubleQuote(string2, '\u0000');
                return;
            }
            this.expandCapacity(n4);
        }
        this.buf[this.count] = c2;
        int n5 = this.count + 2;
        int n6 = n5 + n3;
        this.buf[this.count + 1] = 34;
        ((String)object).getChars(0, n3, this.buf, n5);
        this.count = n4;
        this.buf[n6] = 34;
        n5 = n6 + 1;
        object = this.buf;
        n3 = n5 + 1;
        object[n5] = 58;
        if (string2 == null) {
            object = this.buf;
            n2 = n3 + 1;
            object[n3] = 110;
            object = this.buf;
            n3 = n2 + 1;
            object[n2] = 117;
            object = this.buf;
            n2 = n3 + 1;
            object[n3] = 108;
            this.buf[n2] = 108;
            return;
        }
        object = this.buf;
        int n7 = n3 + 1;
        object[n3] = 34;
        int n8 = n7 + n2;
        string2.getChars(0, n2, this.buf, n7);
        int n9 = 0;
        int n10 = -1;
        n3 = -1;
        int n11 = 0;
        for (n2 = n7; n2 < n8; ++n2) {
            int n12;
            int n13;
            int n14;
            block32: {
                block31: {
                    block33: {
                        c2 = this.buf[n2];
                        if (c2 < ']') break block31;
                        n14 = n3;
                        n13 = n11;
                        n12 = n10;
                        n6 = n4;
                        n5 = n9;
                        if (c2 < '\u007f') break block32;
                        if (c2 == '\u2028') break block33;
                        n14 = n3;
                        n13 = n11;
                        n12 = n10;
                        n6 = n4;
                        n5 = n9;
                        if (c2 > '\u00a0') break block32;
                    }
                    n14 = n3;
                    if (n3 == -1) {
                        n14 = n2;
                    }
                    n5 = n9 + 1;
                    n12 = n2;
                    n13 = c2;
                    n6 = n4 + 4;
                    break block32;
                }
                n14 = n3;
                n13 = n11;
                n12 = n10;
                n6 = n4;
                n5 = n9;
                if (SerializeWriter.isSpecial(c2, this.features)) {
                    n10 = n9 + 1;
                    n9 = n2;
                    char c3 = c2;
                    n11 = n4;
                    if (c2 < IOUtils.specicalFlags_doubleQuotes.length) {
                        n11 = n4;
                        if (IOUtils.specicalFlags_doubleQuotes[c2] == 4) {
                            n11 = n4 + 4;
                        }
                    }
                    n14 = n3;
                    n13 = c3;
                    n12 = n9;
                    n6 = n11;
                    n5 = n10;
                    if (n3 == -1) {
                        n14 = n2;
                        n13 = c3;
                        n12 = n9;
                        n6 = n11;
                        n5 = n10;
                    }
                }
            }
            n3 = n14;
            n11 = n13;
            n10 = n12;
            n4 = n6;
            n9 = n5;
        }
        boolean bl2 = true;
        while (true) {
            block36: {
                block29: {
                    block34: {
                        block35: {
                            if (!bl2 || (bl2 = false)) break block34;
                            if (n9 <= 0) break block29;
                            n2 = n4 + n9;
                            if (n2 > this.buf.length) {
                                this.expandCapacity(n2);
                            }
                            this.count = n2;
                            if (n9 != 1) break block35;
                            if (n11 == 8232) {
                                System.arraycopy(this.buf, n10 + 1, this.buf, n10 + 6, n8 - n10 - 1);
                                this.buf[n10] = 92;
                                object = this.buf;
                                n2 = n10 + 1;
                                object[n2] = 117;
                                object = this.buf;
                                object[++n2] = 50;
                                object = this.buf;
                                object[++n2] = 48;
                                object = this.buf;
                                object[++n2] = 50;
                                this.buf[n2 + 1] = 56;
                                break block29;
                            } else if (n11 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[n11] == 4) {
                                System.arraycopy(this.buf, n10 + 1, this.buf, n10 + 6, n8 - n10 - 1);
                                object = this.buf;
                                n2 = n10 + 1;
                                object[n10] = 92;
                                object = this.buf;
                                n3 = n2 + 1;
                                object[n2] = 117;
                                object = this.buf;
                                n2 = n3 + 1;
                                object[n3] = IOUtils.DIGITS[n11 >>> 12 & 0xF];
                                object = this.buf;
                                n3 = n2 + 1;
                                object[n2] = IOUtils.DIGITS[n11 >>> 8 & 0xF];
                                object = this.buf;
                                n2 = n3 + 1;
                                object[n3] = IOUtils.DIGITS[n11 >>> 4 & 0xF];
                                this.buf[n2] = IOUtils.DIGITS[n11 & 0xF];
                                break block29;
                            } else {
                                System.arraycopy(this.buf, n10 + 1, this.buf, n10 + 2, n8 - n10 - 1);
                                this.buf[n10] = 92;
                                this.buf[n10 + 1] = IOUtils.replaceChars[n11];
                            }
                            break block29;
                        }
                        if (n9 <= 1) break block29;
                        n2 = n3;
                        n4 = n3 - n7;
                        n3 = n8;
                    }
                    if (n4 < string2.length()) break block36;
                }
                this.buf[this.count - 1] = 34;
                return;
            }
            c2 = string2.charAt(n4);
            if (c2 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[c2] != 0 || c2 == '/' && this.isEnabled(SerializerFeature.WriteSlashAsSpecial)) {
                object = this.buf;
                n5 = n2 + 1;
                object[n2] = 92;
                if (IOUtils.specicalFlags_doubleQuotes[c2] == 4) {
                    object = this.buf;
                    n2 = n5 + 1;
                    object[n5] = 117;
                    object = this.buf;
                    n5 = n2 + 1;
                    object[n2] = IOUtils.DIGITS[c2 >>> 12 & 0xF];
                    object = this.buf;
                    n2 = n5 + 1;
                    object[n5] = IOUtils.DIGITS[c2 >>> 8 & 0xF];
                    object = this.buf;
                    n5 = n2 + 1;
                    object[n2] = IOUtils.DIGITS[c2 >>> 4 & 0xF];
                    object = this.buf;
                    n2 = n5 + 1;
                    object[n5] = IOUtils.DIGITS[c2 & 0xF];
                    n3 += 5;
                } else {
                    object = this.buf;
                    n2 = n5 + 1;
                    object[n5] = IOUtils.replaceChars[c2];
                    ++n3;
                }
            } else if (c2 == '\u2028') {
                object = this.buf;
                n5 = n2 + 1;
                object[n2] = 92;
                object = this.buf;
                n2 = n5 + 1;
                object[n5] = 117;
                object = this.buf;
                n5 = n2 + 1;
                object[n2] = IOUtils.DIGITS[c2 >>> 12 & 0xF];
                object = this.buf;
                n2 = n5 + 1;
                object[n5] = IOUtils.DIGITS[c2 >>> 8 & 0xF];
                object = this.buf;
                n5 = n2 + 1;
                object[n2] = IOUtils.DIGITS[c2 >>> 4 & 0xF];
                object = this.buf;
                n2 = n5 + 1;
                object[n5] = IOUtils.DIGITS[c2 & 0xF];
                n3 += 5;
            } else {
                this.buf[n2] = c2;
                ++n2;
            }
            ++n4;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeFloat(float f2, boolean bl2) {
        if (Float.isNaN(f2) || Float.isInfinite(f2)) {
            this.writeNull();
            return;
        } else {
            String string2;
            String string3 = string2 = Float.toString(f2);
            if (string2.endsWith(".0")) {
                string3 = string2.substring(0, string2.length() - 2);
            }
            this.write(string3);
            if (!bl2 || !this.isEnabled(SerializerFeature.WriteClassName)) return;
            this.write(70);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeInt(int n2) {
        if (n2 == Integer.MIN_VALUE) {
            this.write("-2147483648");
            return;
        }
        int n3 = n2 < 0 ? IOUtils.stringSize(-n2) + 1 : IOUtils.stringSize(n2);
        int n4 = this.count + n3;
        if (n4 > this.buf.length) {
            if (this.writer != null) {
                char[] cArray = new char[n3];
                IOUtils.getChars(n2, n3, cArray);
                this.write(cArray, 0, cArray.length);
                return;
            }
            this.expandCapacity(n4);
        }
        IOUtils.getChars(n2, n4, this.buf);
        this.count = n4;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeLong(long l2) {
        int n2;
        boolean bl2;
        block10: {
            int n3;
            block9: {
                int n4;
                bl2 = this.isEnabled(SerializerFeature.BrowserCompatible) && !this.isEnabled(SerializerFeature.WriteClassName) && (l2 > 0x1FFFFFFFFFFFFFL || l2 < -9007199254740991L);
                if (l2 == Long.MIN_VALUE) {
                    if (bl2) {
                        this.write("\"-9223372036854775808\"");
                        return;
                    }
                    this.write("-9223372036854775808");
                    return;
                }
                n2 = l2 < 0L ? IOUtils.stringSize(-l2) + 1 : IOUtils.stringSize(l2);
                n3 = n4 = this.count + n2;
                if (bl2) {
                    n3 = n4 + 2;
                }
                if (n3 <= this.buf.length) break block9;
                if (this.writer != null) break block10;
                this.expandCapacity(n3);
            }
            if (bl2) {
                this.buf[this.count] = 34;
                IOUtils.getChars(l2, n3 - 1, this.buf);
                this.buf[n3 - 1] = 34;
            } else {
                IOUtils.getChars(l2, n3, this.buf);
            }
            this.count = n3;
            return;
        }
        char[] cArray = new char[n2];
        IOUtils.getChars(l2, n2, cArray);
        if (bl2) {
            this.write(34);
            this.write(cArray, 0, cArray.length);
            this.write(34);
            return;
        }
        this.write(cArray, 0, cArray.length);
    }

    public void writeNull() {
        this.write("null");
    }

    public void writeNull(int n2, int n3) {
        if ((n2 & n3) == 0 && (this.features & n3) == 0) {
            this.writeNull();
            return;
        }
        if (n3 == SerializerFeature.WriteNullListAsEmpty.mask) {
            this.write("[]");
            return;
        }
        if (n3 == SerializerFeature.WriteNullStringAsEmpty.mask) {
            this.writeString("");
            return;
        }
        if (n3 == SerializerFeature.WriteNullBooleanAsFalse.mask) {
            this.write("false");
            return;
        }
        if (n3 == SerializerFeature.WriteNullNumberAsZero.mask) {
            this.write(48);
            return;
        }
        this.writeNull();
    }

    public void writeNull(SerializerFeature serializerFeature) {
        this.writeNull(0, serializerFeature.mask);
    }

    public void writeString(String string2) {
        if (this.useSingleQuotes) {
            this.writeStringWithSingleQuote(string2);
            return;
        }
        this.writeStringWithDoubleQuote(string2, '\u0000');
    }

    public void writeString(String string2, char c2) {
        if (this.useSingleQuotes) {
            this.writeStringWithSingleQuote(string2);
            this.write(c2);
            return;
        }
        this.writeStringWithDoubleQuote(string2, c2);
    }

    /*
     * Unable to fully structure code
     */
    public void writeStringWithDoubleQuote(String var1_1, char var2_2) {
        block73: {
            block71: {
                block74: {
                    block72: {
                        block70: {
                            block64: {
                                block55: {
                                    block47: {
                                        block48: {
                                            if (var1_1 == null) {
                                                this.writeNull();
                                                if (var2_2 != '\u0000') {
                                                    this.write(var2_2);
                                                }
lbl5:
                                                // 4 sources

                                                return;
                                            }
                                            var6_3 = var1_1.length();
                                            var4_5 = var5_4 = this.count + var6_3 + 2;
                                            if (var2_2 != '\u0000') {
                                                var4_5 = var5_4 + 1;
                                            }
                                            if (var4_5 <= this.buf.length) break block47;
                                            if (this.writer == null) break block48;
                                            this.write(34);
                                            block1: for (var4_5 = 0; var4_5 < var1_1.length(); ++var4_5) {
                                                block50: {
                                                    block54: {
                                                        block51: {
                                                            block53: {
                                                                block52: {
                                                                    block49: {
                                                                        var5_4 = var1_1.charAt(var4_5);
                                                                        if (!this.isEnabled(SerializerFeature.BrowserSecure)) break block49;
                                                                        if (!(var5_4 >= 48 && var5_4 <= 57 || var5_4 >= 97 && var5_4 <= 122 || var5_4 >= 65 && var5_4 <= 90 || var5_4 == 44 || var5_4 == 46 || var5_4 == 95)) {
                                                                            this.write(92);
                                                                            this.write(117);
                                                                            this.write(IOUtils.DIGITS[var5_4 >>> 12 & 15]);
                                                                            this.write(IOUtils.DIGITS[var5_4 >>> 8 & 15]);
                                                                            this.write(IOUtils.DIGITS[var5_4 >>> 4 & 15]);
                                                                            this.write(IOUtils.DIGITS[var5_4 & 15]);
lbl24:
                                                                            // 7 sources

                                                                            continue block1;
                                                                        }
                                                                        break block50;
                                                                    }
                                                                    if (!this.isEnabled(SerializerFeature.BrowserCompatible)) break block51;
                                                                    if (var5_4 != 8 && var5_4 != 12 && var5_4 != 10 && var5_4 != 13 && var5_4 != 9 && var5_4 != 34 && var5_4 != 47 && var5_4 != 92) break block52;
                                                                    this.write(92);
                                                                    this.write(IOUtils.replaceChars[var5_4]);
                                                                    ** GOTO lbl24
                                                                }
                                                                if (var5_4 >= 32) break block53;
                                                                this.write(92);
                                                                this.write(117);
                                                                this.write(48);
                                                                this.write(48);
                                                                this.write(IOUtils.ASCII_CHARS[var5_4 * 2]);
                                                                this.write(IOUtils.ASCII_CHARS[var5_4 * 2 + 1]);
                                                                ** GOTO lbl24
                                                            }
                                                            if (var5_4 < 127) break block50;
                                                            this.write(92);
                                                            this.write(117);
                                                            this.write(IOUtils.DIGITS[var5_4 >>> 12 & 15]);
                                                            this.write(IOUtils.DIGITS[var5_4 >>> 8 & 15]);
                                                            this.write(IOUtils.DIGITS[var5_4 >>> 4 & 15]);
                                                            this.write(IOUtils.DIGITS[var5_4 & 15]);
                                                            ** GOTO lbl24
                                                        }
                                                        if ((var5_4 >= IOUtils.specicalFlags_doubleQuotes.length || IOUtils.specicalFlags_doubleQuotes[var5_4] == 0) && (var5_4 != 47 || !this.isEnabled(SerializerFeature.WriteSlashAsSpecial))) break block50;
                                                        this.write(92);
                                                        if (IOUtils.specicalFlags_doubleQuotes[var5_4] != 4) break block54;
                                                        this.write(117);
                                                        this.write(IOUtils.DIGITS[var5_4 >>> 12 & 15]);
                                                        this.write(IOUtils.DIGITS[var5_4 >>> 8 & 15]);
                                                        this.write(IOUtils.DIGITS[var5_4 >>> 4 & 15]);
                                                        this.write(IOUtils.DIGITS[var5_4 & 15]);
                                                        ** GOTO lbl24
                                                    }
                                                    this.write(IOUtils.replaceChars[var5_4]);
                                                    ** GOTO lbl24
                                                }
                                                this.write(var5_4);
                                                ** continue;
                                            }
                                            this.write(34);
                                            ** while (var2_2 == '\u0000')
lbl69:
                                            // 1 sources

                                            this.write(var2_2);
                                            return;
                                        }
                                        this.expandCapacity(var4_5);
                                    }
                                    var11_6 = this.count + 1;
                                    var12_7 = var11_6 + var6_3;
                                    this.buf[this.count] = 34;
                                    var1_1.getChars(0, var6_3, this.buf, var11_6);
                                    this.count = var4_5;
                                    if (!this.isEnabled(SerializerFeature.BrowserSecure)) break block55;
                                    var5_4 = -1;
                                    var7_8 = var11_6;
                                    var6_3 = var4_5;
                                    for (var4_5 = var7_8; var4_5 < var12_7; ++var4_5) {
                                        block57: {
                                            block59: {
                                                block58: {
                                                    block56: {
                                                        var9_14 = this.buf[var4_5];
                                                        if (var9_14 < '0') break block56;
                                                        var8_11 = var5_4;
                                                        var7_8 = var6_3;
                                                        if (var9_14 <= '9') break block57;
                                                    }
                                                    if (var9_14 < 'a') break block58;
                                                    var8_11 = var5_4;
                                                    var7_8 = var6_3;
                                                    if (var9_14 <= 'z') break block57;
                                                }
                                                if (var9_14 < 'A') break block59;
                                                var8_11 = var5_4;
                                                var7_8 = var6_3;
                                                if (var9_14 <= 'Z') break block57;
                                            }
                                            var8_11 = var5_4;
                                            var7_8 = var6_3;
                                            if (var9_14 != ',') {
                                                var8_11 = var5_4;
                                                var7_8 = var6_3;
                                                if (var9_14 != '.') {
                                                    var8_11 = var5_4;
                                                    var7_8 = var6_3;
                                                    if (var9_14 != '_') {
                                                        var8_11 = var4_5;
                                                        var7_8 = var6_3 + 5;
                                                    }
                                                }
                                            }
                                        }
                                        var5_4 = var8_11;
                                        var6_3 = var7_8;
                                    }
                                    if (var6_3 > this.buf.length) {
                                        this.expandCapacity(var6_3);
                                    }
                                    this.count = var6_3;
                                    for (var4_5 = var5_4; var4_5 >= var11_6; --var4_5) {
                                        block61: {
                                            block63: {
                                                block62: {
                                                    block60: {
                                                        var6_3 = this.buf[var4_5];
                                                        if (var6_3 < 48) break block60;
                                                        var5_4 = var12_7;
                                                        if (var6_3 <= 57) break block61;
                                                    }
                                                    if (var6_3 < 97) break block62;
                                                    var5_4 = var12_7;
                                                    if (var6_3 <= 122) break block61;
                                                }
                                                if (var6_3 < 65) break block63;
                                                var5_4 = var12_7;
                                                if (var6_3 <= 90) break block61;
                                            }
                                            var5_4 = var12_7;
                                            if (var6_3 != 44) {
                                                var5_4 = var12_7;
                                                if (var6_3 != 46) {
                                                    var5_4 = var12_7;
                                                    if (var6_3 != 95) {
                                                        System.arraycopy(this.buf, var4_5 + 1, this.buf, var4_5 + 6, var12_7 - var4_5 - 1);
                                                        this.buf[var4_5] = 92;
                                                        this.buf[var4_5 + 1] = 117;
                                                        this.buf[var4_5 + 2] = IOUtils.DIGITS[var6_3 >>> 12 & 15];
                                                        this.buf[var4_5 + 3] = IOUtils.DIGITS[var6_3 >>> 8 & 15];
                                                        this.buf[var4_5 + 4] = IOUtils.DIGITS[var6_3 >>> 4 & 15];
                                                        this.buf[var4_5 + 5] = IOUtils.DIGITS[var6_3 & 15];
                                                        var5_4 = var12_7 + 5;
                                                    }
                                                }
                                            }
                                        }
                                        var12_7 = var5_4;
                                    }
                                    if (var2_2 != '\u0000') {
                                        this.buf[this.count - 2] = 34;
                                        this.buf[this.count - 1] = var2_2;
                                        return;
                                    }
                                    this.buf[this.count - 1] = 34;
                                    return;
                                }
                                if (!this.isEnabled(SerializerFeature.BrowserCompatible)) break block64;
                                var5_4 = -1;
                                var6_3 = var11_6;
                                var7_9 = var4_5;
                                block5: for (var4_5 = var6_3; var4_5 < var12_7; ++var4_5) {
                                    block66: {
                                        block65: {
                                            var8_12 = this.buf[var4_5];
                                            if (var8_12 == '\"' || var8_12 == '/' || var8_12 == '\\') {
                                                var5_4 = var4_5;
                                                var6_3 = var7_9 + 1;
lbl165:
                                                // 5 sources

                                                while (true) {
                                                    var7_9 = var6_3;
                                                    continue block5;
                                                    break;
                                                }
                                            }
                                            if (var8_12 != '\b' && var8_12 != '\f' && var8_12 != '\n' && var8_12 != '\r' && var8_12 != '\t') break block65;
                                            var5_4 = var4_5;
                                            var6_3 = var7_9 + 1;
                                            ** GOTO lbl165
                                        }
                                        if (var8_12 >= ' ') break block66;
                                        var5_4 = var4_5;
                                        var6_3 = var7_9 + 5;
                                        ** GOTO lbl165
                                    }
                                    var6_3 = var7_9;
                                    if (var8_12 < '\u007f') ** GOTO lbl165
                                    var5_4 = var4_5;
                                    var6_3 = var7_9 + 5;
                                    ** continue;
                                }
                                if (var7_9 > this.buf.length) {
                                    this.expandCapacity(var7_9);
                                }
                                this.count = var7_9;
                                block7: while (var5_4 >= var11_6) {
                                    block68: {
                                        block67: {
                                            var3_16 = this.buf[var5_4];
                                            if (var3_16 == '\b' || var3_16 == '\f' || var3_16 == '\n' || var3_16 == '\r' || var3_16 == '\t') {
                                                System.arraycopy(this.buf, var5_4 + 1, this.buf, var5_4 + 2, var12_7 - var5_4 - 1);
                                                this.buf[var5_4] = 92;
                                                this.buf[var5_4 + 1] = IOUtils.replaceChars[var3_16];
                                                var4_5 = var12_7 + 1;
lbl193:
                                                // 5 sources

                                                while (true) {
                                                    --var5_4;
                                                    var12_7 = var4_5;
                                                    continue block7;
                                                    break;
                                                }
                                            }
                                            if (var3_16 != '\"' && var3_16 != '/' && var3_16 != '\\') break block67;
                                            System.arraycopy(this.buf, var5_4 + 1, this.buf, var5_4 + 2, var12_7 - var5_4 - 1);
                                            this.buf[var5_4] = 92;
                                            this.buf[var5_4 + 1] = var3_16;
                                            var4_5 = var12_7 + 1;
                                            ** GOTO lbl193
                                        }
                                        if (var3_16 >= ' ') break block68;
                                        System.arraycopy(this.buf, var5_4 + 1, this.buf, var5_4 + 6, var12_7 - var5_4 - 1);
                                        this.buf[var5_4] = 92;
                                        this.buf[var5_4 + 1] = 117;
                                        this.buf[var5_4 + 2] = 48;
                                        this.buf[var5_4 + 3] = 48;
                                        this.buf[var5_4 + 4] = IOUtils.ASCII_CHARS[var3_16 * 2];
                                        this.buf[var5_4 + 5] = IOUtils.ASCII_CHARS[var3_16 * 2 + 1];
                                        var4_5 = var12_7 + 5;
                                        ** GOTO lbl193
                                    }
                                    var4_5 = var12_7;
                                    if (var3_16 < '\u007f') ** GOTO lbl193
                                    System.arraycopy(this.buf, var5_4 + 1, this.buf, var5_4 + 6, var12_7 - var5_4 - 1);
                                    this.buf[var5_4] = 92;
                                    this.buf[var5_4 + 1] = 117;
                                    this.buf[var5_4 + 2] = IOUtils.DIGITS[var3_16 >>> 12 & 15];
                                    this.buf[var5_4 + 3] = IOUtils.DIGITS[var3_16 >>> 8 & 15];
                                    this.buf[var5_4 + 4] = IOUtils.DIGITS[var3_16 >>> 4 & 15];
                                    this.buf[var5_4 + 5] = IOUtils.DIGITS[var3_16 & 15];
                                    var4_5 = var12_7 + 5;
                                    ** continue;
                                }
                                if (var2_2 != '\u0000') {
                                    this.buf[this.count - 2] = 34;
                                    this.buf[this.count - 1] = var2_2;
                                    return;
                                }
                                this.buf[this.count - 1] = 34;
                                return;
                            }
                            var15_18 = 0;
                            var16_19 = -1;
                            var6_3 = -1;
                            var14_20 = 0;
                            var5_4 = var11_6;
                            var13_21 = var4_5;
                            var4_5 = var5_4;
                            var5_4 = var6_3;
                            block9: while (var4_5 < var12_7) {
                                block69: {
                                    var3_17 = this.buf[var4_5];
                                    if (var3_17 == 8232) {
                                        var14_20 = var4_5;
                                        var16_19 = var3_17;
                                        var6_3 = var5_4;
                                        var7_10 = var16_19;
                                        var8_13 = var14_20;
                                        var9_15 = var13_21 += 4;
                                        var10_22 = ++var15_18;
                                        if (var5_4 == -1) {
                                            var6_3 = var4_5;
                                            var10_22 = var15_18;
                                            var9_15 = var13_21;
                                            var8_13 = var14_20;
                                            var7_10 = var16_19;
                                        }
lbl257:
                                        // 9 sources

                                        while (true) {
                                            ++var4_5;
                                            var5_4 = var6_3;
                                            var14_20 = var7_10;
                                            var16_19 = var8_13;
                                            var13_21 = var9_15;
                                            var15_18 = var10_22;
                                            continue block9;
                                            break;
                                        }
                                    }
                                    if (var3_17 < 93) break block69;
                                    var6_3 = var5_4;
                                    var7_10 = var14_20;
                                    var8_13 = var16_19;
                                    var9_15 = var13_21;
                                    var10_22 = var15_18;
                                    if (var3_17 < 127) ** GOTO lbl257
                                    var6_3 = var5_4;
                                    var7_10 = var14_20;
                                    var8_13 = var16_19;
                                    var9_15 = var13_21;
                                    var10_22 = var15_18;
                                    if (var3_17 > 160) ** GOTO lbl257
                                    var6_3 = var5_4;
                                    if (var5_4 == -1) {
                                        var6_3 = var4_5;
                                    }
                                    var10_22 = var15_18 + 1;
                                    var8_13 = var4_5;
                                    var7_10 = var3_17;
                                    var9_15 = var13_21 + 4;
                                    ** GOTO lbl257
                                }
                                var6_3 = var5_4;
                                var7_10 = var14_20;
                                var8_13 = var16_19;
                                var9_15 = var13_21;
                                var10_22 = var15_18;
                                if (!SerializeWriter.isSpecial((char)var3_17, this.features)) ** GOTO lbl257
                                var16_19 = var15_18 + 1;
                                var15_18 = var4_5;
                                var17_23 = var3_17;
                                var14_20 = var13_21;
                                if (var3_17 < IOUtils.specicalFlags_doubleQuotes.length) {
                                    var14_20 = var13_21;
                                    if (IOUtils.specicalFlags_doubleQuotes[var3_17] == 4) {
                                        var14_20 = var13_21 + 4;
                                    }
                                }
                                var6_3 = var5_4;
                                var7_10 = var17_23;
                                var8_13 = var15_18;
                                var9_15 = var14_20;
                                var10_22 = var16_19;
                                if (var5_4 != -1) ** GOTO lbl257
                                var6_3 = var4_5;
                                var7_10 = var17_23;
                                var8_13 = var15_18;
                                var9_15 = var14_20;
                                var10_22 = var16_19;
                                ** continue;
                            }
                            if (var15_18 <= 0) break block70;
                            var4_5 = var13_21 + var15_18;
                            if (var4_5 > this.buf.length) {
                                this.expandCapacity(var4_5);
                            }
                            this.count = var4_5;
                            if (var15_18 != 1) break block71;
                            if (var14_20 != 8232) break block72;
                            System.arraycopy(this.buf, var16_19 + '\u0001', this.buf, var16_19 + 6, var12_7 - var16_19 - 1);
                            this.buf[var16_19] = 92;
                            var1_1 = this.buf;
                            var4_5 = var16_19 + 1;
                            var1_1[var4_5] = 117;
                            var1_1 = this.buf;
                            var1_1[++var4_5] = 50;
                            var1_1 = this.buf;
                            var1_1[++var4_5] = 48;
                            var1_1 = this.buf;
                            var1_1[++var4_5] = 50;
                            this.buf[var4_5 + 1] = 56;
                        }
lbl333:
                        // 5 sources

                        while (var2_2 != '\u0000') {
                            this.buf[this.count - 2] = 34;
                            this.buf[this.count - 1] = var2_2;
                            return;
                        }
                        break block73;
                    }
                    if (var14_20 >= IOUtils.specicalFlags_doubleQuotes.length || IOUtils.specicalFlags_doubleQuotes[var14_20] != 4) break block74;
                    System.arraycopy(this.buf, var16_19 + 1, this.buf, var16_19 + 6, var12_7 - var16_19 - 1);
                    var1_1 = this.buf;
                    var4_5 = var16_19 + 1;
                    var1_1[var16_19] = 92;
                    var1_1 = this.buf;
                    var5_4 = var4_5 + 1;
                    var1_1[var4_5] = 117;
                    var1_1 = this.buf;
                    var4_5 = var5_4 + 1;
                    var1_1[var5_4] = IOUtils.DIGITS[var14_20 >>> 12 & 15];
                    var1_1 = this.buf;
                    var5_4 = var4_5 + 1;
                    var1_1[var4_5] = IOUtils.DIGITS[var14_20 >>> 8 & 15];
                    var1_1 = this.buf;
                    var4_5 = var5_4 + 1;
                    var1_1[var5_4] = IOUtils.DIGITS[var14_20 >>> 4 & 15];
                    this.buf[var4_5] = IOUtils.DIGITS[var14_20 & 15];
                    ** GOTO lbl333
                }
                System.arraycopy(this.buf, var16_19 + 1, this.buf, var16_19 + 2, var12_7 - var16_19 - 1);
                this.buf[var16_19] = 92;
                this.buf[var16_19 + 1] = IOUtils.replaceChars[var14_20];
                ** GOTO lbl333
            }
            if (var15_18 <= 1) ** GOTO lbl333
            var4_5 = var5_4;
            var6_3 = var5_4 - var11_6;
            var5_4 = var12_7;
            block12: while (var6_3 < var1_1.length()) {
                block76: {
                    block75: {
                        var3_17 = var1_1.charAt(var6_3);
                        if ((var3_17 >= IOUtils.specicalFlags_doubleQuotes.length || IOUtils.specicalFlags_doubleQuotes[var3_17] == 0) && (var3_17 != 47 || !this.isEnabled(SerializerFeature.WriteSlashAsSpecial))) break block75;
                        var18_24 = this.buf;
                        var7_10 = var4_5 + 1;
                        var18_24[var4_5] = 92;
                        if (IOUtils.specicalFlags_doubleQuotes[var3_17] == 4) {
                            var18_24 = this.buf;
                            var4_5 = var7_10 + 1;
                            var18_24[var7_10] = 117;
                            var18_24 = this.buf;
                            var7_10 = var4_5 + 1;
                            var18_24[var4_5] = IOUtils.DIGITS[var3_17 >>> 12 & 15];
                            var18_24 = this.buf;
                            var4_5 = var7_10 + 1;
                            var18_24[var7_10] = IOUtils.DIGITS[var3_17 >>> 8 & 15];
                            var18_24 = this.buf;
                            var7_10 = var4_5 + 1;
                            var18_24[var4_5] = IOUtils.DIGITS[var3_17 >>> 4 & 15];
                            var18_24 = this.buf;
                            var4_5 = var7_10 + 1;
                            var18_24[var7_10] = IOUtils.DIGITS[var3_17 & 15];
                            var5_4 += 5;
lbl391:
                            // 4 sources

                            while (true) {
                                ++var6_3;
                                continue block12;
                                break;
                            }
                        }
                        var18_24 = this.buf;
                        var4_5 = var7_10 + 1;
                        var18_24[var7_10] = IOUtils.replaceChars[var3_17];
                        ++var5_4;
                        ** GOTO lbl391
                    }
                    if (var3_17 != 8232) break block76;
                    var18_24 = this.buf;
                    var7_10 = var4_5 + 1;
                    var18_24[var4_5] = 92;
                    var18_24 = this.buf;
                    var4_5 = var7_10 + 1;
                    var18_24[var7_10] = 117;
                    var18_24 = this.buf;
                    var7_10 = var4_5 + 1;
                    var18_24[var4_5] = IOUtils.DIGITS[var3_17 >>> 12 & 15];
                    var18_24 = this.buf;
                    var4_5 = var7_10 + 1;
                    var18_24[var7_10] = IOUtils.DIGITS[var3_17 >>> 8 & 15];
                    var18_24 = this.buf;
                    var7_10 = var4_5 + 1;
                    var18_24[var4_5] = IOUtils.DIGITS[var3_17 >>> 4 & 15];
                    var18_24 = this.buf;
                    var4_5 = var7_10 + 1;
                    var18_24[var7_10] = IOUtils.DIGITS[var3_17 & 15];
                    var5_4 += 5;
                    ** GOTO lbl391
                }
                this.buf[var4_5] = var3_17;
                ++var4_5;
                ** continue;
            }
            ** GOTO lbl333
        }
        this.buf[this.count - 1] = 34;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void writeStringWithSingleQuote(String object) {
        int n2;
        block16: {
            block20: {
                int n3;
                int n4;
                int n5;
                int n6;
                int n7;
                block19: {
                    int n8;
                    block15: {
                        if (object == null) {
                            int n9 = this.count + 4;
                            if (n9 > this.buf.length) {
                                this.expandCapacity(n9);
                            }
                            "null".getChars(0, 4, this.buf, this.count);
                            this.count = n9;
                            return;
                        }
                        n2 = ((String)object).length();
                        n8 = this.count + n2 + 2;
                        if (n8 <= this.buf.length) break block15;
                        if (this.writer != null) break block16;
                        this.expandCapacity(n8);
                    }
                    n7 = this.count + 1;
                    n6 = n7 + n2;
                    this.buf[this.count] = 39;
                    ((String)object).getChars(0, n2, this.buf, n7);
                    this.count = n8;
                    n5 = 0;
                    n4 = -1;
                    n3 = 0;
                    for (n2 = n7; n2 < n6; ++n2) {
                        int n10;
                        int n11;
                        int n12;
                        block18: {
                            int n13;
                            block17: {
                                n13 = this.buf[n2];
                                if (n13 <= 13 || n13 == 92 || n13 == 39) break block17;
                                n12 = n3;
                                n11 = n4;
                                n10 = n5;
                                if (n13 != 47) break block18;
                                n12 = n3;
                                n11 = n4;
                                n10 = n5;
                                if (!this.isEnabled(SerializerFeature.WriteSlashAsSpecial)) break block18;
                            }
                            n10 = n5 + 1;
                            n11 = n2;
                            n12 = n13;
                        }
                        n3 = n12;
                        n4 = n11;
                        n5 = n10;
                    }
                    n2 = n8 + n5;
                    if (n2 > this.buf.length) {
                        this.expandCapacity(n2);
                    }
                    this.count = n2;
                    if (n5 != 1) break block19;
                    System.arraycopy(this.buf, n4 + 1, this.buf, n4 + 2, n6 - n4 - 1);
                    this.buf[n4] = 92;
                    this.buf[n4 + 1] = IOUtils.replaceChars[n3];
                    break block20;
                }
                if (n5 <= 1) break block20;
                System.arraycopy(this.buf, n4 + 1, this.buf, n4 + 2, n6 - n4 - 1);
                this.buf[n4] = 92;
                object = this.buf;
                n2 = n4 + 1;
                object[n2] = IOUtils.replaceChars[n3];
                n5 = n6 + 1;
                n2 -= 2;
                while (n2 >= n7) {
                    block22: {
                        block21: {
                            n4 = this.buf[n2];
                            if (n4 <= 13 || n4 == 92 || n4 == 39) break block21;
                            n3 = n5;
                            if (n4 != 47) break block22;
                            n3 = n5;
                            if (!this.isEnabled(SerializerFeature.WriteSlashAsSpecial)) break block22;
                        }
                        System.arraycopy(this.buf, n2 + 1, this.buf, n2 + 2, n5 - n2 - 1);
                        this.buf[n2] = 92;
                        this.buf[n2 + 1] = IOUtils.replaceChars[n4];
                        n3 = n5 + 1;
                    }
                    --n2;
                    n5 = n3;
                }
            }
            this.buf[this.count - 1] = 39;
            return;
        }
        this.write(39);
        n2 = 0;
        while (true) {
            if (n2 >= ((String)object).length()) {
                this.write(39);
                return;
            }
            char c2 = ((String)object).charAt(n2);
            if (c2 <= '\r' || c2 == '\\' || c2 == '\'' || c2 == '/' && this.isEnabled(SerializerFeature.WriteSlashAsSpecial)) {
                this.write(92);
                this.write(IOUtils.replaceChars[c2]);
            } else {
                this.write(c2);
            }
            ++n2;
        }
    }

    public void writeTo(OutputStream outputStream, String string2) throws IOException {
        this.writeTo(outputStream, Charset.forName(string2));
    }

    public void writeTo(OutputStream outputStream, Charset charset) throws IOException {
        this.writeToEx(outputStream, charset);
    }

    public void writeTo(Writer writer) throws IOException {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        }
        writer.write(this.buf, 0, this.count);
    }

    public int writeToEx(OutputStream outputStream, Charset object) throws IOException {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        }
        if (object == IOUtils.UTF8) {
            return this.encodeToUTF8(outputStream);
        }
        object = new String(this.buf, 0, this.count).getBytes((Charset)object);
        outputStream.write((byte[])object);
        return ((Object)object).length;
    }
}

