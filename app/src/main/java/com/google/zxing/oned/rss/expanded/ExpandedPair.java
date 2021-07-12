/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned.rss.expanded;

import com.google.zxing.oned.rss.DataCharacter;
import com.google.zxing.oned.rss.FinderPattern;

final class ExpandedPair {
    private final FinderPattern finderPattern;
    private final DataCharacter leftChar;
    private final boolean mayBeLast;
    private final DataCharacter rightChar;

    ExpandedPair(DataCharacter dataCharacter, DataCharacter dataCharacter2, FinderPattern finderPattern, boolean bl2) {
        this.leftChar = dataCharacter;
        this.rightChar = dataCharacter2;
        this.finderPattern = finderPattern;
        this.mayBeLast = bl2;
    }

    private static boolean equalsOrNull(Object object, Object object2) {
        if (object == null) {
            return object2 == null;
        }
        return object.equals(object2);
    }

    private static int hashNotNull(Object object) {
        if (object == null) {
            return 0;
        }
        return object.hashCode();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block3: {
            block2: {
                if (!(object instanceof ExpandedPair)) break block2;
                object = (ExpandedPair)object;
                if (ExpandedPair.equalsOrNull(this.leftChar, ((ExpandedPair)object).leftChar) && ExpandedPair.equalsOrNull(this.rightChar, ((ExpandedPair)object).rightChar) && ExpandedPair.equalsOrNull(this.finderPattern, ((ExpandedPair)object).finderPattern)) break block3;
            }
            return false;
        }
        return true;
    }

    FinderPattern getFinderPattern() {
        return this.finderPattern;
    }

    DataCharacter getLeftChar() {
        return this.leftChar;
    }

    DataCharacter getRightChar() {
        return this.rightChar;
    }

    public int hashCode() {
        return ExpandedPair.hashNotNull(this.leftChar) ^ ExpandedPair.hashNotNull(this.rightChar) ^ ExpandedPair.hashNotNull(this.finderPattern);
    }

    boolean mayBeLast() {
        return this.mayBeLast;
    }

    public boolean mustBeLast() {
        return this.rightChar == null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String toString() {
        Object object;
        StringBuilder stringBuilder = new StringBuilder().append("[ ").append(this.leftChar).append(" , ").append(this.rightChar).append(" : ");
        if (this.finderPattern == null) {
            object = "null";
            return stringBuilder.append(object).append(" ]").toString();
        }
        object = this.finderPattern.getValue();
        return stringBuilder.append(object).append(" ]").toString();
    }
}

