package com.mineorigins.worldgen.dimension;

import com.mineorigins.MineOrigins;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class ModDimensions {
    public static final ResourceKey<DimensionType> SKY_DIMENSION_TYPE = ResourceKey.create(
            Registries.DIMENSION_TYPE,
            MineOrigins.id("aetherial_aurora")
    );

    public static final ResourceKey<Level> SKY_DIMENSION_LEVEL = ResourceKey.create(
            Registries.DIMENSION,
            MineOrigins.id("aetherial_aurora")
    );

    public static void initialize() {
        MineOrigins.LOGGER.info("Registered Aetherial Aurora sky dimension keys");
    }
}
