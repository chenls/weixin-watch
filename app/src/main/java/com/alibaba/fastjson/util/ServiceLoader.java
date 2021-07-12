/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.util;

import com.alibaba.fastjson.util.IOUtils;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ServiceLoader {
    private static final String PREFIX = "META-INF/services/";
    private static final Set<String> loadedUrls = new HashSet<String>();

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static <T> Set<T> load(Class<T> object, ClassLoader classLoader) {
        if (classLoader == null) {
            return Collections.emptySet();
        }
        HashSet hashSet = new HashSet();
        object = ((Class)object).getName();
        Iterator iterator = PREFIX + (String)object;
        object = new HashSet();
        try {
            iterator = classLoader.getResources((String)((Object)iterator));
            while (iterator.hasMoreElements()) {
                URL uRL = (URL)iterator.nextElement();
                if (loadedUrls.contains(uRL.toString())) continue;
                ServiceLoader.load(uRL, (Set<String>)object);
                loadedUrls.add(uRL.toString());
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        iterator = object.iterator();
        while (true) {
            object = hashSet;
            if (!iterator.hasNext()) return object;
            object = (String)iterator.next();
            try {
                hashSet.add(classLoader.loadClass((String)object).newInstance());
            }
            catch (Exception exception) {
            }
        }
    }

    /*
     * Unable to fully structure code
     */
    public static void load(URL var0, Set<String> var1_1) throws IOException {
        var3_2 = null;
        var4_4 = null;
        var3_2 = var0 = var0.openStream();
        try {
            var5_5 = new BufferedReader(new InputStreamReader((InputStream)var0, "utf-8"));
        }
        catch (Throwable var5_6) {
            var0 = var3_2;
            var1_1 = var4_4;
            var3_2 = var5_6;
            ** continue;
        }
        while (true) {
            var3_2 = var5_5.readLine();
            if (var3_2 != null) break block9;
            break;
        }
        catch (Throwable var3_3) {
            var1_1 = var5_5;
lbl13:
            // 2 sources

            while (true) {
                IOUtils.close(var1_1);
                IOUtils.close((Closeable)var0);
                throw var3_2;
            }
        }
        {
            block9: {
                IOUtils.close(var5_5);
                IOUtils.close((Closeable)var0);
                return;
            }
            var2_7 = var3_2.indexOf(35);
            var4_4 = var3_2;
            if (var2_7 < 0) ** GOTO lbl27
            var4_4 = var3_2.substring(0, var2_7);
lbl27:
            // 2 sources

            if ((var3_2 = var4_4.trim()).length() == 0) continue;
            var1_1.add(var3_2);
            continue;
        }
    }
}

