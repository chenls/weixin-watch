/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.LabelFilter;
import java.util.Arrays;

public class Labels {
    public static LabelFilter excludes(String ... stringArray) {
        return new DefaultLabelFilter(null, stringArray);
    }

    public static LabelFilter includes(String ... stringArray) {
        return new DefaultLabelFilter(stringArray, null);
    }

    private static class DefaultLabelFilter
    implements LabelFilter {
        private String[] excludes;
        private String[] includes;

        public DefaultLabelFilter(String[] stringArray, String[] stringArray2) {
            if (stringArray != null) {
                this.includes = new String[stringArray.length];
                System.arraycopy(stringArray, 0, this.includes, 0, stringArray.length);
                Arrays.sort(this.includes);
            }
            if (stringArray2 != null) {
                this.excludes = new String[stringArray2.length];
                System.arraycopy(stringArray2, 0, this.excludes, 0, stringArray2.length);
                Arrays.sort(this.excludes);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean apply(String string2) {
            if (this.excludes != null) {
                if (Arrays.binarySearch(this.excludes, string2) == -1) return true;
                return false;
            }
            if (this.includes == null || Arrays.binarySearch(this.includes, string2) < 0) return false;
            return true;
        }
    }
}

