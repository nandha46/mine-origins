package com.mineorigins.block;

import net.minecraft.util.StringRepresentable;

public enum DragonCityPortalPart implements StringRepresentable {
    TOP_LEFT("top_left", 0, 2),
    TOP_CENTER("top_center", 1, 2),
    TOP_RIGHT("top_right", 2, 2),
    MIDDLE_LEFT("middle_left", 0, 1),
    CENTER("center", 1, 1),
    MIDDLE_RIGHT("middle_right", 2, 1),
    BOTTOM_LEFT("bottom_left", 0, 0),
    BOTTOM_CENTER("bottom_center", 1, 0),
    BOTTOM_RIGHT("bottom_right", 2, 0);

    private final String name;
    private final int col; // 0=left, 1=center, 2=right
    private final int row; // 0=bottom, 1=middle, 2=top

    DragonCityPortalPart(String name, int col, int row) {
        this.name = name;
        this.col = col;
        this.row = row;
    }

    public static DragonCityPortalPart get(int col, int row) {
        for (DragonCityPortalPart part : values()) {
            if (part.col == col && part.row == row) {
                return part;
            }
        }
        return CENTER;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
