/*
 * Decompiled with CFR 0.151.
 */
package mobvoiapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class j {
    public static int a(Object[] objectArray) {
        return Arrays.hashCode(objectArray);
    }

    public static a a(Object object) {
        return new a(object);
    }

    public static boolean a(Object object, Object object2) {
        return object == object2 || object != object2 && object.equals(object2);
    }

    public static final class a {
        private final List<String> a;
        private final Object b;

        private a(Object object) {
            this.b = mobvoiapi.a.a(object);
            this.a = new ArrayList<String>();
        }

        public a a(String string2, Object object) {
            this.a.add(mobvoiapi.a.a(string2) + "=" + String.valueOf(object));
            return this;
        }

        public String toString() {
            int n2;
            StringBuffer stringBuffer = new StringBuffer(120).append(this.b.getClass().getSimpleName()).append('{');
            int n3 = this.a.size();
            for (n2 = 0; n2 < n3 - 1; ++n2) {
                stringBuffer.append(this.a.get(n2));
                stringBuffer.append(", ");
            }
            if (n2 == n3 - 1) {
                stringBuffer.append(this.a.get(n2));
            }
            return stringBuffer.append('}').toString();
        }
    }
}

