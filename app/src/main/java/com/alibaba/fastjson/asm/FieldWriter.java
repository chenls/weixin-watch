/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.asm;

import com.alibaba.fastjson.asm.ByteVector;
import com.alibaba.fastjson.asm.ClassWriter;

public final class FieldWriter {
    private final int access;
    private final int desc;
    private final int name;
    FieldWriter next;

    /*
     * Enabled aggressive block sorting
     */
    public FieldWriter(ClassWriter classWriter, int n2, String string2, String string3) {
        if (classWriter.firstField == null) {
            classWriter.firstField = this;
        } else {
            classWriter.lastField.next = this;
        }
        classWriter.lastField = this;
        this.access = n2;
        this.name = classWriter.newUTF8(string2);
        this.desc = classWriter.newUTF8(string3);
    }

    int getSize() {
        return 8;
    }

    void put(ByteVector byteVector) {
        byteVector.putShort(this.access & 0xFFF9FFFF).putShort(this.name).putShort(this.desc);
        byteVector.putShort(0);
    }

    public void visitEnd() {
    }
}

