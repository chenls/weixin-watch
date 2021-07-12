/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import java.io.Serializable;
import u.aly.bv;
import u.aly.bw;
import u.aly.co;

public interface bp<T extends bp<?, ?>, F extends bw>
extends Serializable {
    public void a(co var1) throws bv;

    public F b(int var1);

    public void b();

    public void b(co var1) throws bv;

    public bp<T, F> p();
}

