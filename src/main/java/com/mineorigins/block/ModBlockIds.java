package com.mineorigins.block;

import com.mineorigins.MineOrigins;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;

public class ModBlockIds {

    // --- Aluminium & Titanium Blocks ---
    public static final ResourceKey<Block> BAUXITE_ORE = create("bauxite_ore");
    public static final ResourceKey<Block> RAW_BAUXITE_BLOCK = create("raw_bauxite_block");
    public static final ResourceKey<Block> ALUMINIUM_BLOCK = create("aluminium_block");

    public static final ResourceKey<Block> ILMENITE_ORE = create("ilmenite_ore");
    public static final ResourceKey<Block> RUTILE_ORE = create("rutile_ore");
    public static final ResourceKey<Block> RAW_TITANIUM_BLOCK = create("raw_titanium_block");
    public static final ResourceKey<Block> TITANIUM_BLOCK = create("titanium_block");

    // --- Coconut Palm Tree Blocks ---
    public static final ResourceKey<Block> COCONUT_LOG = create("coconut_log");
    public static final ResourceKey<Block> COCONUT_WOOD = create("coconut_wood");
    public static final ResourceKey<Block> COCONUT_LEAVES = create("coconut_leaves");
    public static final ResourceKey<Block> COCONUT_SPROUT = create("coconut_sprout");

    public static ResourceKey<Block> create(String name) {
        return ResourceKey.create(Registries.BLOCK, MineOrigins.id(name));
    }
}
