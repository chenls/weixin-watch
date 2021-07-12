/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import java.util.BitSet;
import u.aly.bv;
import u.aly.ci;
import u.aly.co;
import u.aly.cq;
import u.aly.cw;
import u.aly.cz;
import u.aly.dc;

public final class cu
extends ci {
    public cu(dc dc2) {
        super(dc2);
    }

    public static BitSet a(byte[] byArray) {
        BitSet bitSet = new BitSet();
        for (int i2 = 0; i2 < byArray.length * 8; ++i2) {
            if ((byArray[byArray.length - i2 / 8 - 1] & 1 << i2 % 8) <= 0) continue;
            bitSet.set(i2);
        }
        return bitSet;
    }

    public static byte[] b(BitSet bitSet, int n2) {
        byte[] byArray = new byte[(int)Math.ceil((double)n2 / 8.0)];
        for (n2 = 0; n2 < bitSet.length(); ++n2) {
            if (!bitSet.get(n2)) continue;
            int n3 = byArray.length - n2 / 8 - 1;
            byArray[n3] = (byte)(byArray[n3] | 1 << n2 % 8);
        }
        return byArray;
    }

    @Override
    public Class<? extends cw> D() {
        return cz.class;
    }

    public void a(BitSet object, int n2) throws bv {
        object = cu.b((BitSet)object, n2);
        int n3 = ((Object)object).length;
        for (n2 = 0; n2 < n3; ++n2) {
            this.a((byte)object[n2]);
        }
    }

    public BitSet b(int n2) throws bv {
        int n3 = (int)Math.ceil((double)n2 / 8.0);
        byte[] byArray = new byte[n3];
        for (n2 = 0; n2 < n3; ++n2) {
            byArray[n2] = this.u();
        }
        return cu.a(byArray);
    }

    public static class a
    implements cq {
        @Override
        public co a(dc dc2) {
            return new cu(dc2);
        }
    }
}

