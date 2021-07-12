/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.asm;

import com.alibaba.fastjson.asm.ByteVector;
import com.alibaba.fastjson.asm.FieldWriter;
import com.alibaba.fastjson.asm.Item;
import com.alibaba.fastjson.asm.MethodWriter;
import com.alibaba.fastjson.asm.Type;

public class ClassWriter {
    private int access;
    FieldWriter firstField;
    MethodWriter firstMethod;
    int index = 1;
    private int interfaceCount;
    private int[] interfaces;
    Item[] items;
    final Item key;
    final Item key2;
    final Item key3;
    FieldWriter lastField;
    MethodWriter lastMethod;
    private int name;
    final ByteVector pool = new ByteVector();
    private int superName;
    String thisName;
    int threshold;
    Item[] typeTable;
    int version;

    public ClassWriter() {
        this(0);
    }

    private ClassWriter(int n2) {
        this.items = new Item[256];
        this.threshold = (int)(0.75 * (double)this.items.length);
        this.key = new Item();
        this.key2 = new Item();
        this.key3 = new Item();
    }

    private Item get(Item item) {
        Item item2 = this.items[item.hashCode % this.items.length];
        while (!(item2 == null || item2.type == item.type && item.isEqualTo(item2))) {
            item2 = item2.next;
        }
        return item2;
    }

    private Item newString(String string2) {
        Item item;
        this.key2.set(8, string2, null, null);
        Item item2 = item = this.get(this.key2);
        if (item == null) {
            this.pool.put12(8, this.newUTF8(string2));
            int n2 = this.index;
            this.index = n2 + 1;
            item2 = new Item(n2, this.key2);
            this.put(item2);
        }
        return item2;
    }

    private void put(Item item) {
        int n2;
        if (this.index > this.threshold) {
            n2 = this.items.length;
            int n3 = n2 * 2 + 1;
            Item[] itemArray = new Item[n3];
            --n2;
            while (n2 >= 0) {
                Item item2 = this.items[n2];
                while (item2 != null) {
                    int n4 = item2.hashCode % itemArray.length;
                    Item item3 = item2.next;
                    item2.next = itemArray[n4];
                    itemArray[n4] = item2;
                    item2 = item3;
                }
                --n2;
            }
            this.items = itemArray;
            this.threshold = (int)((double)n3 * 0.75);
        }
        n2 = item.hashCode % this.items.length;
        item.next = this.items[n2];
        this.items[n2] = item;
    }

    public Item newClassItem(String string2) {
        Item item;
        this.key2.set(7, string2, null, null);
        Item item2 = item = this.get(this.key2);
        if (item == null) {
            this.pool.put12(7, this.newUTF8(string2));
            int n2 = this.index;
            this.index = n2 + 1;
            item2 = new Item(n2, this.key2);
            this.put(item2);
        }
        return item2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    Item newConstItem(Object object) {
        if (object instanceof Integer) {
            int n2 = (Integer)object;
            this.key.set(n2);
            Item item = this.get(this.key);
            object = item;
            if (item != null) return object;
            this.pool.putByte(3).putInt(n2);
            n2 = this.index;
            this.index = n2 + 1;
            object = new Item(n2, this.key);
            this.put((Item)object);
            return object;
        }
        if (object instanceof String) {
            return this.newString((String)object);
        }
        if (!(object instanceof Type)) throw new IllegalArgumentException("value " + object);
        object = (Type)object;
        if (((Type)object).sort == 10) {
            object = ((Type)object).getInternalName();
            return this.newClassItem((String)object);
        }
        object = ((Type)object).getDescriptor();
        return this.newClassItem((String)object);
    }

    Item newFieldItem(String string2, String string3, String string4) {
        Item item;
        this.key3.set(9, string2, string3, string4);
        Item item2 = item = this.get(this.key3);
        if (item == null) {
            int n2 = this.newClassItem((String)string2).index;
            int n3 = this.newNameTypeItem((String)string3, (String)string4).index;
            this.pool.put12(9, n2).putShort(n3);
            n2 = this.index;
            this.index = n2 + 1;
            item2 = new Item(n2, this.key3);
            this.put(item2);
        }
        return item2;
    }

    /*
     * Enabled aggressive block sorting
     */
    Item newMethodItem(String string2, String string3, String string4, boolean bl2) {
        Item item;
        int n2 = bl2 ? 11 : 10;
        this.key3.set(n2, string2, string3, string4);
        Item item2 = item = this.get(this.key3);
        if (item == null) {
            int n3 = this.newClassItem((String)string2).index;
            int n4 = this.newNameTypeItem((String)string3, (String)string4).index;
            this.pool.put12(n2, n3).putShort(n4);
            n2 = this.index;
            this.index = n2 + 1;
            item2 = new Item(n2, this.key3);
            this.put(item2);
        }
        return item2;
    }

    public Item newNameTypeItem(String string2, String string3) {
        Item item;
        this.key2.set(12, string2, string3, null);
        Item item2 = item = this.get(this.key2);
        if (item == null) {
            int n2 = this.newUTF8(string2);
            int n3 = this.newUTF8(string3);
            this.pool.put12(12, n2).putShort(n3);
            n2 = this.index;
            this.index = n2 + 1;
            item2 = new Item(n2, this.key2);
            this.put(item2);
        }
        return item2;
    }

    public int newUTF8(String string2) {
        Item item;
        this.key.set(1, string2, null, null);
        Item item2 = item = this.get(this.key);
        if (item == null) {
            this.pool.putByte(1).putUTF8(string2);
            int n2 = this.index;
            this.index = n2 + 1;
            item2 = new Item(n2, this.key);
            this.put(item2);
        }
        return item2.index;
    }

    public byte[] toByteArray() {
        int n2 = this.interfaceCount * 2 + 24;
        int n3 = 0;
        Object object = this.firstField;
        while (object != null) {
            ++n3;
            n2 += ((FieldWriter)object).getSize();
            object = ((FieldWriter)object).next;
        }
        int n4 = 0;
        object = this.firstMethod;
        int n5 = n2;
        n2 = n4;
        while (object != null) {
            ++n2;
            n5 += ((MethodWriter)object).getSize();
            object = ((MethodWriter)object).next;
        }
        ByteVector byteVector = new ByteVector(n5 + this.pool.length);
        byteVector.putInt(-889275714).putInt(this.version);
        byteVector.putShort(this.index).putByteArray(this.pool.data, 0, this.pool.length);
        byteVector.putShort(this.access & 0xFFF9FFFF).putShort(this.name).putShort(this.superName);
        byteVector.putShort(this.interfaceCount);
        for (n5 = 0; n5 < this.interfaceCount; ++n5) {
            byteVector.putShort(this.interfaces[n5]);
        }
        byteVector.putShort(n3);
        object = this.firstField;
        while (object != null) {
            ((FieldWriter)object).put(byteVector);
            object = ((FieldWriter)object).next;
        }
        byteVector.putShort(n2);
        object = this.firstMethod;
        while (object != null) {
            ((MethodWriter)object).put(byteVector);
            object = ((MethodWriter)object).next;
        }
        byteVector.putShort(0);
        return byteVector.data;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void visit(int n2, int n3, String string2, String string3, String[] stringArray) {
        this.version = n2;
        this.access = n3;
        this.name = this.newClassItem((String)string2).index;
        this.thisName = string2;
        n2 = string3 == null ? 0 : this.newClassItem((String)string3).index;
        this.superName = n2;
        if (stringArray != null && stringArray.length > 0) {
            this.interfaceCount = stringArray.length;
            this.interfaces = new int[this.interfaceCount];
            for (n2 = 0; n2 < this.interfaceCount; ++n2) {
                this.interfaces[n2] = this.newClassItem((String)stringArray[n2]).index;
            }
        }
    }
}

