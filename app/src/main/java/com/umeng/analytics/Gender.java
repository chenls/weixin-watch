/*
 * Decompiled with CFR 0.151.
 */
package com.umeng.analytics;

import java.util.Locale;
import u.aly.az;

public enum Gender {
    Male(1){

        public String toString() {
            return String.format(Locale.US, "Male:%d", this.value);
        }
    }
    ,
    Female(2){

        public String toString() {
            return String.format(Locale.US, "Female:%d", this.value);
        }
    }
    ,
    Unknown(0){

        public String toString() {
            return String.format(Locale.US, "Unknown:%d", this.value);
        }
    };

    public int value;

    private Gender(int n3) {
        this.value = n3;
    }

    public static Gender getGender(int n2) {
        switch (n2) {
            default: {
                return Unknown;
            }
            case 1: {
                return Male;
            }
            case 2: 
        }
        return Female;
    }

    public static az transGender(Gender gender) {
        switch (gender) {
            default: {
                return az.c;
            }
            case Male: {
                return az.a;
            }
            case Female: 
        }
        return az.b;
    }

    public int value() {
        return this.value;
    }
}

