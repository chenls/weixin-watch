/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSON;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;

public class ASMClassLoader
extends ClassLoader {
    private static ProtectionDomain DOMAIN = (ProtectionDomain)AccessController.doPrivileged(new PrivilegedAction<Object>(){

        @Override
        public Object run() {
            return ASMClassLoader.class.getProtectionDomain();
        }
    });

    public ASMClassLoader() {
        super(ASMClassLoader.getParentClassLoader());
    }

    public ASMClassLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    static ClassLoader getParentClassLoader() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            try {
                classLoader.loadClass(JSON.class.getName());
                return classLoader;
            }
            catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
        }
        return JSON.class.getClassLoader();
    }

    public Class<?> defineClassPublic(String string2, byte[] byArray, int n2, int n3) throws ClassFormatError {
        return this.defineClass(string2, byArray, n2, n3, DOMAIN);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean isExternalClass(Class<?> object) {
        ClassLoader classLoader = ((Class)object).getClassLoader();
        if (classLoader != null) {
            object = this;
            while (true) {
                if (object == null) {
                    return true;
                }
                if (object == classLoader) break;
                object = ((ClassLoader)object).getParent();
            }
        }
        return false;
    }
}

