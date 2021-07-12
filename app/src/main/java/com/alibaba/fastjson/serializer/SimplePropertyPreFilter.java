/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerialContext;
import java.util.HashSet;
import java.util.Set;

public class SimplePropertyPreFilter
implements PropertyPreFilter {
    private final Class<?> clazz;
    private final Set<String> excludes;
    private final Set<String> includes = new HashSet<String>();
    private int maxLevel = 0;

    /*
     * WARNING - void declaration
     */
    public SimplePropertyPreFilter(Class<?> object2, String ... stringArray) {
        void var2_4;
        this.excludes = new HashSet<String>();
        this.clazz = object2;
        for (void var1_3 : var2_4) {
            if (var1_3 == null) continue;
            this.includes.add((String)var1_3);
        }
    }

    public SimplePropertyPreFilter(String ... stringArray) {
        this((Class<?>)null, stringArray);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean apply(JSONSerializer object, Object object2, String string2) {
        block8: {
            block7: {
                if (object2 == null || this.clazz != null && !this.clazz.isInstance(object2)) break block7;
                if (this.excludes.contains(string2)) {
                    return false;
                }
                if (this.maxLevel > 0) {
                    int n2 = 0;
                    object = ((JSONSerializer)object).context;
                    while (object != null) {
                        if (++n2 > this.maxLevel) {
                            return false;
                        }
                        object = ((SerialContext)object).parent;
                    }
                }
                if (this.includes.size() != 0 && !this.includes.contains(string2)) break block8;
            }
            return true;
        }
        return false;
    }

    public Class<?> getClazz() {
        return this.clazz;
    }

    public Set<String> getExcludes() {
        return this.excludes;
    }

    public Set<String> getIncludes() {
        return this.includes;
    }

    public int getMaxLevel() {
        return this.maxLevel;
    }

    public void setMaxLevel(int n2) {
        this.maxLevel = n2;
    }
}

