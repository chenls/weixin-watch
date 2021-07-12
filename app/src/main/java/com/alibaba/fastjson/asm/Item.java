/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.asm;

final class Item {
    int hashCode;
    int index;
    int intVal;
    long longVal;
    Item next;
    String strVal1;
    String strVal2;
    String strVal3;
    int type;

    Item() {
    }

    Item(int n2, Item item) {
        this.index = n2;
        this.type = item.type;
        this.intVal = item.intVal;
        this.longVal = item.longVal;
        this.strVal1 = item.strVal1;
        this.strVal2 = item.strVal2;
        this.strVal3 = item.strVal3;
        this.hashCode = item.hashCode;
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean isEqualTo(Item item) {
        switch (this.type) {
            default: {
                if (!item.strVal1.equals(this.strVal1) || !item.strVal2.equals(this.strVal2) || !item.strVal3.equals(this.strVal3)) return false;
                return true;
            }
            case 1: 
            case 7: 
            case 8: 
            case 13: {
                return item.strVal1.equals(this.strVal1);
            }
            case 5: 
            case 6: 
            case 15: {
                if (item.longVal == this.longVal) return true;
                return false;
            }
            case 3: 
            case 4: {
                if (item.intVal == this.intVal) return true;
                return false;
            }
            case 12: {
                if (!item.strVal1.equals(this.strVal1) || !item.strVal2.equals(this.strVal2)) return false;
            }
        }
        return true;
    }

    void set(int n2) {
        this.type = 3;
        this.intVal = n2;
        this.hashCode = Integer.MAX_VALUE & this.type + n2;
    }

    void set(int n2, String string2, String string3, String string4) {
        this.type = n2;
        this.strVal1 = string2;
        this.strVal2 = string3;
        this.strVal3 = string4;
        switch (n2) {
            default: {
                this.hashCode = string2.hashCode() * string3.hashCode() * string4.hashCode() + n2 & Integer.MAX_VALUE;
                return;
            }
            case 1: 
            case 7: 
            case 8: 
            case 13: {
                this.hashCode = string2.hashCode() + n2 & Integer.MAX_VALUE;
                return;
            }
            case 12: 
        }
        this.hashCode = string2.hashCode() * string3.hashCode() + n2 & Integer.MAX_VALUE;
    }
}

