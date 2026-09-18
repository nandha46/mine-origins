package com.mineorigins.worldgen;

import com.mineorigins.MineOrigins;
import com.mineorigins.worldgen.tree.CoconutTreeFeature;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModWorldGen {

    // Ores
    public static final ResourceKey<PlacedFeature> ORE_BAUXITE_PLACED_KEY = ResourceKey.create(
            Registries.PLACED_FEATURE, MineOrigins.id("ore_bauxite"));

    public static final ResourceKey<PlacedFeature> ORE_ILMENITE_PLACED_KEY = ResourceKey.create(
            Registries.PLACED_FEATURE, MineOrigins.id("ore_ilmenite"));

    public static final ResourceKey<PlacedFeature> ORE_RUTILE_PLACED_KEY = ResourceKey.create(
            Registries.PLACED_FEATURE, MineOrigins.id("ore_rutile"));

    // Trees
    public static final Feature<NoneFeatureConfiguration> COCONUT_TREE_FEATURE = Registry.register(
            BuiltInRegistries.FEATURE,
            MineOrigins.id("coconut_tree"),
            new CoconutTreeFeature(NoneFeatureConfiguration.CODEC)
    );

    public static final ResourceKey<PlacedFeature> COCONUT_TREE_PLACED_KEY = ResourceKey.create(
            Registries.PLACED_FEATURE, MineOrigins.id("coconut_tree"));

    public static void initialize() {
        // Add Bauxite, Ilmenite, and Rutile ore generation to Overworld biomes
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                ORE_BAUXITE_PLACED_KEY
        );

        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                ORE_ILMENITE_PLACED_KEY
        );

        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                ORE_RUTILE_PLACED_KEY
        );

        // Add Coconut Trees to beach biomes
        BiomeModifications.addFeature(
                BiomeSelectors.tag(BiomeTags.IS_BEACH),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                COCONUT_TREE_PLACED_KEY
        );

        MineOrigins.LOGGER.info("Registered worldgen ore & coconut tree features for Mine Origins");
    }
}
