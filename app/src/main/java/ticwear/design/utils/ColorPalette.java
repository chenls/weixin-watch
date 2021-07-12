/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Color
 */
package ticwear.design.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import ticwear.design.R;

public class ColorPalette {
    private static final int[][] colorList;
    private Context context;

    static {
        int[] nArray = new int[]{R.color.tic_basic_light_green_darken, R.color.tic_basic_light_green, R.color.tic_basic_light_green_lighten};
        int n2 = R.color.tic_basic_green_darken;
        int n3 = R.color.tic_basic_green;
        int n4 = R.color.tic_basic_green_lighten;
        int[] nArray2 = new int[]{R.color.tic_basic_cyan_darken, R.color.tic_basic_cyan, R.color.tic_basic_cyan_lighten};
        int[] nArray3 = new int[]{R.color.tic_basic_dark_cyan_darken, R.color.tic_basic_dark_cyan, R.color.tic_basic_dark_cyan_lighten};
        int[] nArray4 = new int[]{R.color.tic_basic_indigo_darken, R.color.tic_basic_indigo, R.color.tic_basic_indigo_lighten};
        int[] nArray5 = new int[]{R.color.tic_basic_deep_purple_darken, R.color.tic_basic_deep_purple, R.color.tic_basic_deep_purple_lighten};
        int n5 = R.color.tic_basic_deep_blue_darken;
        int n6 = R.color.tic_basic_deep_blue;
        int n7 = R.color.tic_basic_deep_blue_lighten;
        int n8 = R.color.tic_basic_gold_grey_darken;
        int n9 = R.color.tic_basic_gold_grey;
        int n10 = R.color.tic_basic_gold_grey_lighten;
        int[] nArray6 = new int[]{R.color.tic_basic_pink_darken, R.color.tic_basic_pink, R.color.tic_basic_pink_lighten};
        int[] nArray7 = new int[]{R.color.tic_basic_red_darken, R.color.tic_basic_red, R.color.tic_basic_red_lighten};
        int n11 = R.color.tic_basic_deep_orange_darken;
        int n12 = R.color.tic_basic_deep_orange;
        int n13 = R.color.tic_basic_deep_orange_lighten;
        int[] nArray8 = new int[]{R.color.tic_basic_orange_darken, R.color.tic_basic_orange, R.color.tic_basic_orange_lighten};
        int n14 = R.color.tic_basic_amber_darken;
        int n15 = R.color.tic_basic_amber;
        int n16 = R.color.tic_basic_amber_lighten;
        int n17 = R.color.tic_basic_yellow_darken;
        int n18 = R.color.tic_basic_yellow;
        int n19 = R.color.tic_basic_yellow_lighten;
        int[] nArray9 = new int[]{R.color.tic_basic_brown_darken, R.color.tic_basic_brown, R.color.tic_basic_brown_lighten};
        int n20 = R.color.tic_basic_warm_grey_darken;
        int n21 = R.color.tic_basic_warm_grey;
        int n22 = R.color.tic_basic_warm_grey_lighten;
        colorList = new int[][]{nArray, {n2, n3, n4}, nArray2, nArray3, nArray4, nArray5, {n5, n6, n7}, {n8, n9, n10}, nArray6, nArray7, {n11, n12, n13}, nArray8, {n14, n15, n16}, {n17, n18, n19}, nArray9, {n20, n21, n22}};
    }

    protected ColorPalette(Context context) {
        this.context = context;
    }

    public static ColorPalette from(Context context) {
        return new ColorPalette(context);
    }

    public Color color(@NonNull ColorName colorName) {
        return new Color(this.context, colorName.ordinal());
    }

    public static class Color {
        private static final int COLORS_COUNT = ColorName.values().length;
        private static final int DECORATES_COUNT = ColorDecorate.values().length;
        private int colorDecIndex;
        private int colorResIndex;
        private Context context;

        static {
            if (COLORS_COUNT != colorList.length) {
                throw new RuntimeException("ColorPalette is broken! Make sure the color names matches the color list.");
            }
            if (colorList.length > 0 && DECORATES_COUNT != colorList[0].length) {
                throw new RuntimeException("ColorPalette is broken! Make sure the color decorates matches the color list.");
            }
        }

        Color(Context context, int n2) {
            this(context, n2, ColorDecorate.values().length / 2);
        }

        Color(Context context, int n2, int n3) {
            this.context = context;
            this.colorResIndex = n2;
            this.colorDecIndex = n3;
        }

        public Color darken() {
            Color color2 = this;
            if (this.colorDecIndex - 1 >= 0) {
                color2 = new Color(this.context, this.colorResIndex, this.colorDecIndex - 1);
            }
            return color2;
        }

        public ColorDecorate decorate() {
            return ColorDecorate.values()[this.colorDecIndex];
        }

        public Color lighten() {
            Color color2 = this;
            if (this.colorDecIndex + 1 < DECORATES_COUNT) {
                color2 = new Color(this.context, this.colorResIndex, this.colorDecIndex + 1);
            }
            return color2;
        }

        public ColorName name() {
            return ColorName.values()[this.colorResIndex];
        }

        public String rgbString() {
            if (android.graphics.Color.alpha((int)this.value()) < 255) {
                return String.format("#%08x", this.value());
            }
            return String.format("#%06x", this.value() & 0xFFFFFF);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public String toString() {
            String string2;
            if (this.decorate() == ColorDecorate.NORMAL) {
                string2 = "";
                return this.rgbString() + " " + this.name().colorName() + string2;
            }
            string2 = " " + this.decorate().decorateName();
            return this.rgbString() + " " + this.name().colorName() + string2;
        }

        public int value() {
            if (this.colorResIndex >= 0 && this.colorResIndex < COLORS_COUNT && this.colorDecIndex >= 0 && this.colorDecIndex < DECORATES_COUNT) {
                return this.context.getResources().getColor(colorList[this.colorResIndex][this.colorDecIndex]);
            }
            return 0;
        }
    }

    public static enum ColorDecorate {
        DARKEN("Darken"),
        NORMAL("Normal"),
        LIGHTEN("Lighten");

        private String decorateName;

        private ColorDecorate(String string3) {
            this.decorateName = string3;
        }

        public String decorateName() {
            return this.decorateName;
        }
    }

    public static enum ColorName {
        LIGHT_GREEN("LightGreen"),
        GREEN("Green"),
        CYAN("Cyan"),
        DARK_CYAN("DarkCyan"),
        INDIGO("Indigo"),
        DEEP_PURPLE("DeepPurple"),
        DARK_BLUE("DarkBlue"),
        COLD_GREY("ColdGrey"),
        PINK("Pink"),
        RED("Red"),
        DEEP_ORANGE("DeepOrange"),
        ORANGE("Orange"),
        AMBER("Amber"),
        YELLOW("Yellow"),
        BROWN("Brown"),
        WARM_GREY("WarmGrey");

        private String colorName;

        private ColorName(String string3) {
            this.colorName = string3;
        }

        public String colorName() {
            return this.colorName;
        }
    }
}

