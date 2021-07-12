/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.asm;

import com.alibaba.fastjson.asm.ByteVector;
import com.alibaba.fastjson.asm.ClassWriter;
import com.alibaba.fastjson.asm.Item;
import com.alibaba.fastjson.asm.Label;
import com.alibaba.fastjson.asm.MethodVisitor;
import com.alibaba.fastjson.asm.Type;

public class MethodWriter
implements MethodVisitor {
    private int access;
    private ByteVector code = new ByteVector();
    final ClassWriter cw;
    private final int desc;
    int exceptionCount;
    int[] exceptions;
    private int maxLocals;
    private int maxStack;
    private final int name;
    MethodWriter next;

    /*
     * Enabled aggressive block sorting
     */
    public MethodWriter(ClassWriter classWriter, int n2, String string2, String string3, String string4, String[] stringArray) {
        if (classWriter.firstMethod == null) {
            classWriter.firstMethod = this;
        } else {
            classWriter.lastMethod.next = this;
        }
        classWriter.lastMethod = this;
        this.cw = classWriter;
        this.access = n2;
        this.name = classWriter.newUTF8(string2);
        this.desc = classWriter.newUTF8(string3);
        if (stringArray != null && stringArray.length > 0) {
            this.exceptionCount = stringArray.length;
            this.exceptions = new int[this.exceptionCount];
            for (n2 = 0; n2 < this.exceptionCount; ++n2) {
                this.exceptions[n2] = classWriter.newClassItem((String)stringArray[n2]).index;
            }
        }
    }

    final int getSize() {
        int n2 = 8;
        if (this.code.length > 0) {
            this.cw.newUTF8("Code");
            n2 = 8 + (this.code.length + 18 + 0);
        }
        int n3 = n2;
        if (this.exceptionCount > 0) {
            this.cw.newUTF8("Exceptions");
            n3 = n2 + (this.exceptionCount * 2 + 8);
        }
        return n3;
    }

    final void put(ByteVector byteVector) {
        byteVector.putShort(this.access & 0xFFF9FFFF).putShort(this.name).putShort(this.desc);
        int n2 = 0;
        if (this.code.length > 0) {
            n2 = 0 + 1;
        }
        int n3 = n2;
        if (this.exceptionCount > 0) {
            n3 = n2 + 1;
        }
        byteVector.putShort(n3);
        if (this.code.length > 0) {
            n2 = this.code.length;
            byteVector.putShort(this.cw.newUTF8("Code")).putInt(n2 + 12 + 0);
            byteVector.putShort(this.maxStack).putShort(this.maxLocals);
            byteVector.putInt(this.code.length).putByteArray(this.code.data, 0, this.code.length);
            byteVector.putShort(0);
            byteVector.putShort(0);
        }
        if (this.exceptionCount > 0) {
            byteVector.putShort(this.cw.newUTF8("Exceptions")).putInt(this.exceptionCount * 2 + 2);
            byteVector.putShort(this.exceptionCount);
            for (n2 = 0; n2 < this.exceptionCount; ++n2) {
                byteVector.putShort(this.exceptions[n2]);
            }
        }
    }

    @Override
    public void visitEnd() {
    }

    @Override
    public void visitFieldInsn(int n2, String object, String string2, String string3) {
        object = this.cw.newFieldItem((String)object, string2, string3);
        this.code.put12(n2, ((Item)object).index);
    }

    @Override
    public void visitIincInsn(int n2, int n3) {
        this.code.putByte(132).put11(n2, n3);
    }

    @Override
    public void visitInsn(int n2) {
        this.code.putByte(n2);
    }

    @Override
    public void visitIntInsn(int n2, int n3) {
        this.code.put11(n2, n3);
    }

    @Override
    public void visitJumpInsn(int n2, Label label) {
        if ((label.status & 2) != 0 && label.position - this.code.length < Short.MIN_VALUE) {
            throw new UnsupportedOperationException();
        }
        this.code.putByte(n2);
        label.put(this, this.code, this.code.length - 1);
    }

    @Override
    public void visitLabel(Label label) {
        label.resolve(this, this.code.length, this.code.data);
    }

    @Override
    public void visitLdcInsn(Object object) {
        object = this.cw.newConstItem(object);
        int n2 = ((Item)object).index;
        if (((Item)object).type == 5 || ((Item)object).type == 6) {
            this.code.put12(20, n2);
            return;
        }
        if (n2 >= 256) {
            this.code.put12(19, n2);
            return;
        }
        this.code.put11(18, n2);
    }

    @Override
    public void visitMaxs(int n2, int n3) {
        this.maxStack = n2;
        this.maxLocals = n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void visitMethodInsn(int n2, String object, String string2, String string3) {
        boolean bl2 = n2 == 185;
        object = this.cw.newMethodItem((String)object, string2, string3, bl2);
        int n3 = ((Item)object).intVal;
        if (!bl2) {
            this.code.put12(n2, ((Item)object).index);
            return;
        }
        n2 = n3;
        if (n3 == 0) {
            ((Item)object).intVal = n2 = Type.getArgumentsAndReturnSizes(string3);
        }
        this.code.put12(185, ((Item)object).index).put11(n2 >> 2, 0);
    }

    @Override
    public void visitTypeInsn(int n2, String object) {
        object = this.cw.newClassItem((String)object);
        this.code.put12(n2, ((Item)object).index);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void visitVarInsn(int n2, int n3) {
        if (n3 < 4 && n2 != 169) {
            n2 = n2 < 54 ? (n2 - 21 << 2) + 26 + n3 : (n2 - 54 << 2) + 59 + n3;
            this.code.putByte(n2);
            return;
        }
        if (n3 >= 256) {
            this.code.putByte(196).put12(n2, n3);
            return;
        }
        this.code.put11(n2, n3);
    }
}

