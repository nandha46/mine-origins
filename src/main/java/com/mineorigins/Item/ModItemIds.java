package com.mineorigins.Item;

import com.mineorigins.MineOrigins;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class ModItemIds {

    // Raw Items & Ingots
    public static final ResourceKey<Item> BAUXITE = create("bauxite");
    public static final ResourceKey<Item> RAW_BAUXITE = create("raw_bauxite");
    public static final ResourceKey<Item> ALUMINIUM = create("aluminium");

    public static final ResourceKey<Item> ILMENITE = create("ilmenite");
    public static final ResourceKey<Item> RUTILE = create("rutile");
    public static final ResourceKey<Item> RAW_TITANIUM = create("raw_titanium");
    public static final ResourceKey<Item> TITANIUM = create("titanium");

    // Block Item Keys
    public static final ResourceKey<Item> BAUXITE_ORE = create("bauxite_ore");
    public static final ResourceKey<Item> RAW_BAUXITE_BLOCK = create("raw_bauxite_block");
    public static final ResourceKey<Item> ALUMINIUM_BLOCK = create("aluminium_block");

    public static final ResourceKey<Item> ILMENITE_ORE = create("ilmenite_ore");
    public static final ResourceKey<Item> RUTILE_ORE = create("rutile_ore");
    public static final ResourceKey<Item> RAW_TITANIUM_BLOCK = create("raw_titanium_block");
    public static final ResourceKey<Item> TITANIUM_BLOCK = create("titanium_block");

    // Coconut & Palm items
    public static final ResourceKey<Item> COCONUT = create("coconut");
    public static final ResourceKey<Item> COCONUT_FIBER = create("coconut_fiber");
    public static final ResourceKey<Item> ROPE = create("rope");

    // Palm block items
    public static final ResourceKey<Item> COCONUT_LOG = create("coconut_log");
    public static final ResourceKey<Item> COCONUT_WOOD = create("coconut_wood");
    public static final ResourceKey<Item> COCONUT_LEAVES = create("coconut_leaves");
    public static final ResourceKey<Item> COCONUT_SPROUT = create("coconut_sprout");

    public static ResourceKey<Item> create(String name) {
        return ResourceKey.create(Registries.ITEM, MineOrigins.id(name));
    }
}
