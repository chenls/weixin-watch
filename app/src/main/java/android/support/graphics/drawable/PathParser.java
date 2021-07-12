/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.graphics.Path
 *  android.util.Log
 */
package android.support.graphics.drawable;

import android.graphics.Path;
import android.util.Log;
import java.util.ArrayList;

class PathParser {
    private static final String LOGTAG = "PathParser";

    PathParser() {
    }

    private static void addNode(ArrayList<PathDataNode> arrayList, char c2, float[] fArray) {
        arrayList.add(new PathDataNode(c2, fArray));
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean canMorph(PathDataNode[] pathDataNodeArray, PathDataNode[] pathDataNodeArray2) {
        if (pathDataNodeArray != null && pathDataNodeArray2 != null && pathDataNodeArray.length == pathDataNodeArray2.length) {
            int n2 = 0;
            while (true) {
                if (n2 >= pathDataNodeArray.length) {
                    return true;
                }
                if (pathDataNodeArray[n2].type != pathDataNodeArray2[n2].type || pathDataNodeArray[n2].params.length != pathDataNodeArray2[n2].params.length) break;
                ++n2;
            }
        }
        return false;
    }

    private static float[] copyOfRange(float[] fArray, int n2, int n3) {
        if (n2 > n3) {
            throw new IllegalArgumentException();
        }
        int n4 = fArray.length;
        if (n2 < 0 || n2 > n4) {
            throw new ArrayIndexOutOfBoundsException();
        }
        n4 = Math.min(n3 -= n2, n4 - n2);
        float[] fArray2 = new float[n3];
        System.arraycopy(fArray, n2, fArray2, 0, n4);
        return fArray2;
    }

    public static PathDataNode[] createNodesFromPathData(String string2) {
        if (string2 == null) {
            return null;
        }
        int n2 = 0;
        int n3 = 1;
        ArrayList<PathDataNode> arrayList = new ArrayList<PathDataNode>();
        while (n3 < string2.length()) {
            String string3 = string2.substring(n2, n3 = PathParser.nextStart(string2, n3)).trim();
            if (string3.length() > 0) {
                float[] fArray = PathParser.getFloats(string3);
                PathParser.addNode(arrayList, string3.charAt(0), fArray);
            }
            n2 = n3++;
        }
        if (n3 - n2 == 1 && n2 < string2.length()) {
            PathParser.addNode(arrayList, string2.charAt(n2), new float[0]);
        }
        return arrayList.toArray(new PathDataNode[arrayList.size()]);
    }

    public static Path createPathFromPathData(String string2) {
        Path path = new Path();
        PathDataNode[] pathDataNodeArray = PathParser.createNodesFromPathData(string2);
        if (pathDataNodeArray != null) {
            try {
                PathDataNode.nodesToPath(pathDataNodeArray, path);
                return path;
            }
            catch (RuntimeException runtimeException) {
                throw new RuntimeException("Error in parsing " + string2, runtimeException);
            }
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static PathDataNode[] deepCopyNodes(PathDataNode[] pathDataNodeArray) {
        if (pathDataNodeArray == null) {
            return null;
        }
        PathDataNode[] pathDataNodeArray2 = new PathDataNode[pathDataNodeArray.length];
        int n2 = 0;
        while (true) {
            PathDataNode[] pathDataNodeArray3 = pathDataNodeArray2;
            if (n2 >= pathDataNodeArray.length) return pathDataNodeArray3;
            pathDataNodeArray2[n2] = new PathDataNode(pathDataNodeArray[n2]);
            ++n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void extract(String string2, int n2, ExtractFloatResult extractFloatResult) {
        int n3;
        boolean bl2 = false;
        extractFloatResult.mEndWithNegOrDot = false;
        boolean bl3 = false;
        boolean bl4 = false;
        for (n3 = n2; n3 < string2.length(); ++n3) {
            boolean bl5;
            boolean bl6;
            boolean bl7;
            boolean bl8 = false;
            switch (string2.charAt(n3)) {
                default: {
                    bl7 = bl3;
                    bl6 = bl8;
                    bl5 = bl2;
                    break;
                }
                case ' ': 
                case ',': {
                    bl5 = true;
                    bl6 = bl8;
                    bl7 = bl3;
                    break;
                }
                case '-': {
                    bl5 = bl2;
                    bl6 = bl8;
                    bl7 = bl3;
                    if (n3 == n2) break;
                    bl5 = bl2;
                    bl6 = bl8;
                    bl7 = bl3;
                    if (bl4) break;
                    bl5 = true;
                    extractFloatResult.mEndWithNegOrDot = true;
                    bl6 = bl8;
                    bl7 = bl3;
                    break;
                }
                case '.': {
                    if (!bl3) {
                        bl7 = true;
                        bl5 = bl2;
                        bl6 = bl8;
                        break;
                    }
                    bl5 = true;
                    extractFloatResult.mEndWithNegOrDot = true;
                    bl6 = bl8;
                    bl7 = bl3;
                    break;
                }
                case 'E': 
                case 'e': {
                    bl6 = true;
                    bl5 = bl2;
                    bl7 = bl3;
                }
            }
            if (bl5) break;
            bl2 = bl5;
            bl4 = bl6;
            bl3 = bl7;
        }
        extractFloatResult.mEndPosition = n3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static float[] getFloats(String string2) {
        int n2;
        ExtractFloatResult extractFloatResult;
        float[] fArray;
        int n3 = 1;
        int n4 = string2.charAt(0) == 'z' ? 1 : 0;
        if (string2.charAt(0) != 'Z') {
            n3 = 0;
        }
        if (n4 | n3) {
            return new float[0];
        }
        try {
            fArray = new float[string2.length()];
            n3 = 1;
            extractFloatResult = new ExtractFloatResult();
            n2 = string2.length();
            n4 = 0;
        }
        catch (NumberFormatException numberFormatException) {
            throw new RuntimeException("error in parsing \"" + string2 + "\"", numberFormatException);
        }
        while (n3 < n2) {
            PathParser.extract(string2, n3, extractFloatResult);
            int n5 = extractFloatResult.mEndPosition;
            if (n3 < n5) {
                int n6 = n4 + 1;
                fArray[n4] = Float.parseFloat(string2.substring(n3, n5));
                n4 = n6;
            }
            if (extractFloatResult.mEndWithNegOrDot) {
                n3 = n5;
                continue;
            }
            n3 = n5 + 1;
        }
        return PathParser.copyOfRange(fArray, 0, n4);
    }

    private static int nextStart(String string2, int n2) {
        char c2;
        while (n2 < string2.length() && (((c2 = string2.charAt(n2)) - 65) * (c2 - 90) > 0 && (c2 - 97) * (c2 - 122) > 0 || c2 == 'e' || c2 == 'E')) {
            ++n2;
        }
        return n2;
    }

    public static void updateNodes(PathDataNode[] pathDataNodeArray, PathDataNode[] pathDataNodeArray2) {
        for (int i2 = 0; i2 < pathDataNodeArray2.length; ++i2) {
            pathDataNodeArray[i2].type = pathDataNodeArray2[i2].type;
            for (int i3 = 0; i3 < pathDataNodeArray2[i2].params.length; ++i3) {
                pathDataNodeArray[i2].params[i3] = pathDataNodeArray2[i2].params[i3];
            }
        }
    }

    private static class ExtractFloatResult {
        int mEndPosition;
        boolean mEndWithNegOrDot;

        private ExtractFloatResult() {
        }
    }

    public static class PathDataNode {
        float[] params;
        char type;

        private PathDataNode(char c2, float[] fArray) {
            this.type = c2;
            this.params = fArray;
        }

        private PathDataNode(PathDataNode pathDataNode) {
            this.type = pathDataNode.type;
            this.params = PathParser.copyOfRange(pathDataNode.params, 0, pathDataNode.params.length);
        }

        /*
         * Enabled aggressive block sorting
         */
        private static void addCommand(Path path, float[] fArray, char c2, char c3, float[] fArray2) {
            int n2 = 2;
            float f2 = fArray[0];
            float f3 = fArray[1];
            float f4 = fArray[2];
            float f5 = fArray[3];
            float f6 = fArray[4];
            float f7 = fArray[5];
            switch (c3) {
                case 'Z': 
                case 'z': {
                    path.close();
                    f2 = f6;
                    f3 = f7;
                    f4 = f6;
                    f5 = f7;
                    path.moveTo(f2, f3);
                    break;
                }
                case 'L': 
                case 'M': 
                case 'T': 
                case 'l': 
                case 'm': 
                case 't': {
                    n2 = 2;
                    break;
                }
                case 'H': 
                case 'V': 
                case 'h': 
                case 'v': {
                    n2 = 1;
                    break;
                }
                case 'C': 
                case 'c': {
                    n2 = 6;
                    break;
                }
                case 'Q': 
                case 'S': 
                case 'q': 
                case 's': {
                    n2 = 4;
                    break;
                }
                case 'A': 
                case 'a': {
                    n2 = 7;
                    break;
                }
            }
            char c4 = '\u0000';
            char c5 = c2;
            c2 = c4;
            float f8 = f7;
            f7 = f6;
            float f9 = f5;
            float f10 = f4;
            while (c2 < fArray2.length) {
                switch (c3) {
                    default: {
                        f5 = f8;
                        f4 = f9;
                        f6 = f10;
                        break;
                    }
                    case 'm': {
                        f2 += fArray2[c2 + '\u0000'];
                        f3 += fArray2[c2 + '\u0001'];
                        if (c2 > '\u0000') {
                            path.rLineTo(fArray2[c2 + '\u0000'], fArray2[c2 + '\u0001']);
                            f6 = f10;
                            f4 = f9;
                            f5 = f8;
                            break;
                        }
                        path.rMoveTo(fArray2[c2 + '\u0000'], fArray2[c2 + '\u0001']);
                        f7 = f2;
                        f5 = f3;
                        f6 = f10;
                        f4 = f9;
                        break;
                    }
                    case 'M': {
                        f2 = fArray2[c2 + '\u0000'];
                        f3 = fArray2[c2 + '\u0001'];
                        if (c2 > '\u0000') {
                            path.lineTo(fArray2[c2 + '\u0000'], fArray2[c2 + '\u0001']);
                            f6 = f10;
                            f4 = f9;
                            f5 = f8;
                            break;
                        }
                        path.moveTo(fArray2[c2 + '\u0000'], fArray2[c2 + '\u0001']);
                        f7 = f2;
                        f5 = f3;
                        f6 = f10;
                        f4 = f9;
                        break;
                    }
                    case 'l': {
                        path.rLineTo(fArray2[c2 + '\u0000'], fArray2[c2 + '\u0001']);
                        f2 += fArray2[c2 + '\u0000'];
                        f3 += fArray2[c2 + '\u0001'];
                        f6 = f10;
                        f4 = f9;
                        f5 = f8;
                        break;
                    }
                    case 'L': {
                        path.lineTo(fArray2[c2 + '\u0000'], fArray2[c2 + '\u0001']);
                        f2 = fArray2[c2 + '\u0000'];
                        f3 = fArray2[c2 + '\u0001'];
                        f6 = f10;
                        f4 = f9;
                        f5 = f8;
                        break;
                    }
                    case 'h': {
                        path.rLineTo(fArray2[c2 + '\u0000'], 0.0f);
                        f2 += fArray2[c2 + '\u0000'];
                        f6 = f10;
                        f4 = f9;
                        f5 = f8;
                        break;
                    }
                    case 'H': {
                        path.lineTo(fArray2[c2 + '\u0000'], f3);
                        f2 = fArray2[c2 + '\u0000'];
                        f6 = f10;
                        f4 = f9;
                        f5 = f8;
                        break;
                    }
                    case 'v': {
                        path.rLineTo(0.0f, fArray2[c2 + '\u0000']);
                        f3 += fArray2[c2 + '\u0000'];
                        f6 = f10;
                        f4 = f9;
                        f5 = f8;
                        break;
                    }
                    case 'V': {
                        path.lineTo(f2, fArray2[c2 + '\u0000']);
                        f3 = fArray2[c2 + '\u0000'];
                        f6 = f10;
                        f4 = f9;
                        f5 = f8;
                        break;
                    }
                    case 'c': {
                        path.rCubicTo(fArray2[c2 + '\u0000'], fArray2[c2 + '\u0001'], fArray2[c2 + 2], fArray2[c2 + 3], fArray2[c2 + 4], fArray2[c2 + 5]);
                        f6 = f2 + fArray2[c2 + 2];
                        f4 = f3 + fArray2[c2 + 3];
                        f2 += fArray2[c2 + 4];
                        f3 += fArray2[c2 + 5];
                        f5 = f8;
                        break;
                    }
                    case 'C': {
                        path.cubicTo(fArray2[c2 + '\u0000'], fArray2[c2 + '\u0001'], fArray2[c2 + 2], fArray2[c2 + 3], fArray2[c2 + 4], fArray2[c2 + 5]);
                        f2 = fArray2[c2 + 4];
                        f3 = fArray2[c2 + 5];
                        f6 = fArray2[c2 + 2];
                        f4 = fArray2[c2 + 3];
                        f5 = f8;
                        break;
                    }
                    case 's': {
                        f6 = 0.0f;
                        f4 = 0.0f;
                        if (c5 == 'c' || c5 == 's' || c5 == 'C' || c5 == 'S') {
                            f6 = f2 - f10;
                            f4 = f3 - f9;
                        }
                        path.rCubicTo(f6, f4, fArray2[c2 + '\u0000'], fArray2[c2 + '\u0001'], fArray2[c2 + 2], fArray2[c2 + 3]);
                        f6 = f2 + fArray2[c2 + '\u0000'];
                        f4 = f3 + fArray2[c2 + '\u0001'];
                        f2 += fArray2[c2 + 2];
                        f3 += fArray2[c2 + 3];
                        f5 = f8;
                        break;
                    }
                    case 'S': {
                        f4 = f2;
                        f6 = f3;
                        if (c5 == 'c' || c5 == 's' || c5 == 'C' || c5 == 'S') {
                            f4 = 2.0f * f2 - f10;
                            f6 = 2.0f * f3 - f9;
                        }
                        path.cubicTo(f4, f6, fArray2[c2 + '\u0000'], fArray2[c2 + '\u0001'], fArray2[c2 + 2], fArray2[c2 + 3]);
                        f6 = fArray2[c2 + '\u0000'];
                        f4 = fArray2[c2 + '\u0001'];
                        f2 = fArray2[c2 + 2];
                        f3 = fArray2[c2 + 3];
                        f5 = f8;
                        break;
                    }
                    case 'q': {
                        path.rQuadTo(fArray2[c2 + '\u0000'], fArray2[c2 + '\u0001'], fArray2[c2 + 2], fArray2[c2 + 3]);
                        f6 = f2 + fArray2[c2 + '\u0000'];
                        f4 = f3 + fArray2[c2 + '\u0001'];
                        f2 += fArray2[c2 + 2];
                        f3 += fArray2[c2 + 3];
                        f5 = f8;
                        break;
                    }
                    case 'Q': {
                        path.quadTo(fArray2[c2 + '\u0000'], fArray2[c2 + '\u0001'], fArray2[c2 + 2], fArray2[c2 + 3]);
                        f6 = fArray2[c2 + '\u0000'];
                        f4 = fArray2[c2 + '\u0001'];
                        f2 = fArray2[c2 + 2];
                        f3 = fArray2[c2 + 3];
                        f5 = f8;
                        break;
                    }
                    case 't': {
                        f4 = 0.0f;
                        f6 = 0.0f;
                        if (c5 == 'q' || c5 == 't' || c5 == 'Q' || c5 == 'T') {
                            f4 = f2 - f10;
                            f6 = f3 - f9;
                        }
                        path.rQuadTo(f4, f6, fArray2[c2 + '\u0000'], fArray2[c2 + '\u0001']);
                        f4 = f2 + f4;
                        f5 = f3 + f6;
                        f2 += fArray2[c2 + '\u0000'];
                        f3 += fArray2[c2 + '\u0001'];
                        f6 = f4;
                        f4 = f5;
                        f5 = f8;
                        break;
                    }
                    case 'T': {
                        f4 = f2;
                        f6 = f3;
                        if (c5 == 'q' || c5 == 't' || c5 == 'Q' || c5 == 'T') {
                            f4 = 2.0f * f2 - f10;
                            f6 = 2.0f * f3 - f9;
                        }
                        path.quadTo(f4, f6, fArray2[c2 + '\u0000'], fArray2[c2 + '\u0001']);
                        f2 = f6;
                        f9 = fArray2[c2 + '\u0000'];
                        f3 = fArray2[c2 + '\u0001'];
                        f6 = f4;
                        f4 = f2;
                        f5 = f8;
                        f2 = f9;
                        break;
                    }
                    case 'a': {
                        f6 = fArray2[c2 + 5];
                        f4 = fArray2[c2 + 6];
                        f5 = fArray2[c2 + '\u0000'];
                        f9 = fArray2[c2 + '\u0001'];
                        f10 = fArray2[c2 + 2];
                        boolean bl2 = fArray2[c2 + 3] != 0.0f;
                        boolean bl3 = fArray2[c2 + 4] != 0.0f;
                        PathDataNode.drawArc(path, f2, f3, f6 + f2, f4 + f3, f5, f9, f10, bl2, bl3);
                        f6 = f2 += fArray2[c2 + 5];
                        f4 = f3 += fArray2[c2 + 6];
                        f5 = f8;
                        break;
                    }
                    case 'A': {
                        f6 = fArray2[c2 + 5];
                        f4 = fArray2[c2 + 6];
                        f5 = fArray2[c2 + '\u0000'];
                        f9 = fArray2[c2 + '\u0001'];
                        f10 = fArray2[c2 + 2];
                        boolean bl2 = fArray2[c2 + 3] != 0.0f;
                        boolean bl3 = fArray2[c2 + 4] != 0.0f;
                        PathDataNode.drawArc(path, f2, f3, f6, f4, f5, f9, f10, bl2, bl3);
                        f2 = fArray2[c2 + 5];
                        f3 = fArray2[c2 + 6];
                        f6 = f2;
                        f4 = f3;
                        f5 = f8;
                    }
                }
                c5 = c3;
                c2 = (char)(c2 + n2);
                f10 = f6;
                f9 = f4;
                f8 = f5;
            }
            fArray[0] = f2;
            fArray[1] = f3;
            fArray[2] = f10;
            fArray[3] = f9;
            fArray[4] = f7;
            fArray[5] = f8;
        }

        private static void arcToBezier(Path path, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, double d10) {
            int n2 = (int)Math.ceil(Math.abs(4.0 * d10 / Math.PI));
            double d11 = d9;
            double d12 = Math.cos(d8);
            double d13 = Math.sin(d8);
            d8 = Math.cos(d11);
            d9 = Math.sin(d11);
            double d14 = -d4 * d12 * d9 - d5 * d13 * d8;
            double d15 = -d4 * d13 * d9 + d5 * d12 * d8;
            double d16 = d10 / (double)n2;
            d9 = d7;
            d8 = d6;
            d10 = d11;
            d7 = d15;
            d6 = d14;
            for (int i2 = 0; i2 < n2; ++i2) {
                double d17 = d10 + d16;
                d15 = Math.sin(d17);
                double d18 = Math.cos(d17);
                double d19 = d4 * d12 * d18 + d2 - d5 * d13 * d15;
                d14 = d4 * d13 * d18 + d3 + d5 * d12 * d15;
                d11 = -d4 * d12 * d15 - d5 * d13 * d18;
                d15 = -d4 * d13 * d15 + d5 * d12 * d18;
                d18 = Math.tan((d17 - d10) / 2.0);
                d10 = Math.sin(d17 - d10) * (Math.sqrt(4.0 + 3.0 * d18 * d18) - 1.0) / 3.0;
                path.cubicTo((float)(d8 + d10 * d6), (float)(d9 + d10 * d7), (float)(d19 - d10 * d11), (float)(d14 - d10 * d15), (float)d19, (float)d14);
                d10 = d17;
                d8 = d19;
                d9 = d14;
                d6 = d11;
                d7 = d15;
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private static void drawArc(Path path, float f2, float f3, float f4, float f5, float f6, float f7, float f8, boolean bl2, boolean bl3) {
            double d2 = Math.toRadians(f8);
            double d3 = Math.cos(d2);
            double d4 = Math.sin(d2);
            double d5 = ((double)f2 * d3 + (double)f3 * d4) / (double)f6;
            double d6 = ((double)(-f2) * d4 + (double)f3 * d3) / (double)f7;
            double d7 = ((double)f4 * d3 + (double)f5 * d4) / (double)f6;
            double d8 = ((double)(-f4) * d4 + (double)f5 * d3) / (double)f7;
            double d9 = d5 - d7;
            double d10 = d6 - d8;
            double d11 = (d5 + d7) / 2.0;
            double d12 = (d6 + d8) / 2.0;
            double d13 = d9 * d9 + d10 * d10;
            if (d13 == 0.0) {
                Log.w((String)PathParser.LOGTAG, (String)" Points are coincident");
                return;
            }
            double d14 = 1.0 / d13 - 0.25;
            if (d14 < 0.0) {
                Log.w((String)PathParser.LOGTAG, (String)("Points are too far apart " + d13));
                float f9 = (float)(Math.sqrt(d13) / 1.99999);
                PathDataNode.drawArc(path, f2, f3, f4, f5, f6 * f9, f7 * f9, f8, bl2, bl3);
                return;
            }
            d13 = Math.sqrt(d14);
            d9 = d13 * d9;
            d10 = d13 * d10;
            if (bl2 == bl3) {
                d11 -= d10;
                d12 += d9;
            } else {
                d11 += d10;
                d12 -= d9;
            }
            d5 = Math.atan2(d6 - d12, d5 - d11);
            bl2 = (d8 = Math.atan2(d8 - d12, d7 - d11) - d5) >= 0.0;
            d7 = d8;
            if (bl3 != bl2) {
                d7 = d8 > 0.0 ? d8 - Math.PI * 2 : d8 + Math.PI * 2;
            }
            PathDataNode.arcToBezier(path, (d11 *= (double)f6) * d3 - (d12 *= (double)f7) * d4, d11 * d4 + d12 * d3, f6, f7, f2, f3, d2, d5, d7);
        }

        public static void nodesToPath(PathDataNode[] pathDataNodeArray, Path path) {
            float[] fArray = new float[6];
            char c2 = 'm';
            for (int i2 = 0; i2 < pathDataNodeArray.length; ++i2) {
                PathDataNode.addCommand(path, fArray, c2, pathDataNodeArray[i2].type, pathDataNodeArray[i2].params);
                c2 = pathDataNodeArray[i2].type;
            }
        }

        public void interpolatePathDataNode(PathDataNode pathDataNode, PathDataNode pathDataNode2, float f2) {
            for (int i2 = 0; i2 < pathDataNode.params.length; ++i2) {
                this.params[i2] = pathDataNode.params[i2] * (1.0f - f2) + pathDataNode2.params[i2] * f2;
            }
        }
    }
}

