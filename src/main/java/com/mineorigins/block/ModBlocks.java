package com.mineorigins.block;

import java.util.function.Function;

import com.mineorigins.MineOrigins;
import com.mineorigins.Item.ModItemIds;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class ModBlocks {

    // --- Aluminium Suite ---
    public static final Block BAUXITE_ORE = registerWithItem(
            ModBlockIds.BAUXITE_ORE,
            ModItemIds.BAUXITE_ORE,
            Block::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(3.0f, 3.0f)
                    .sound(SoundType.STONE)
    );

    public static final Block RAW_BAUXITE_BLOCK = registerWithItem(
            ModBlockIds.RAW_BAUXITE_BLOCK,
            ModItemIds.RAW_BAUXITE_BLOCK,
            Block::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_RED)
                    .requiresCorrectToolForDrops()
                    .strength(5.0f, 6.0f)
                    .sound(SoundType.STONE)
    );

    public static final Block ALUMINIUM_BLOCK = registerWithItem(
            ModBlockIds.ALUMINIUM_BLOCK,
            ModItemIds.ALUMINIUM_BLOCK,
            Block::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .requiresCorrectToolForDrops()
                    .strength(5.0f, 6.0f)
                    .sound(SoundType.METAL)
    );

    // --- Titanium Suite ---
    public static final Block ILMENITE_ORE = registerWithItem(
            ModBlockIds.ILMENITE_ORE,
            ModItemIds.ILMENITE_ORE,
            Block::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DEEPSLATE)
                    .requiresCorrectToolForDrops()
                    .strength(4.5f, 3.0f)
                    .sound(SoundType.DEEPSLATE)
    );

    public static final Block RUTILE_ORE = registerWithItem(
            ModBlockIds.RUTILE_ORE,
            ModItemIds.RUTILE_ORE,
            Block::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .requiresCorrectToolForDrops()
                    .strength(4.0f, 3.0f)
                    .sound(SoundType.STONE)
    );

    public static final Block RAW_TITANIUM_BLOCK = registerWithItem(
            ModBlockIds.RAW_TITANIUM_BLOCK,
            ModItemIds.RAW_TITANIUM_BLOCK,
            Block::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .requiresCorrectToolForDrops()
                    .strength(6.0f, 7.0f)
                    .sound(SoundType.DEEPSLATE)
    );

    public static final Block TITANIUM_BLOCK = registerWithItem(
            ModBlockIds.TITANIUM_BLOCK,
            ModItemIds.TITANIUM_BLOCK,
            Block::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DIAMOND)
                    .requiresCorrectToolForDrops()
                    .strength(6.0f, 8.0f)
                    .sound(SoundType.HEAVY_CORE)
    );

    // --- Coconut Palm Tree Blocks ---
    public static final Block COCONUT_LOG = registerWithItem(
            ModBlockIds.COCONUT_LOG,
            ModItemIds.COCONUT_LOG,
            CoconutLogBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .strength(2.0f)
                    .sound(SoundType.WOOD)
    );

    public static final Block COCONUT_WOOD = registerWithItem(
            ModBlockIds.COCONUT_WOOD,
            ModItemIds.COCONUT_WOOD,
            RotatedPillarBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .strength(2.0f)
                    .sound(SoundType.WOOD)
    );

    public static final Block COCONUT_LEAVES = registerWithItem(
            ModBlockIds.COCONUT_LEAVES,
            ModItemIds.COCONUT_LEAVES,
            p -> new TintedParticleLeavesBlock(0.01f, p),
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .strength(0.2f)
                    .randomTicks()
                    .sound(SoundType.GRASS)
                    .noOcclusion()
    );

    public static final Block COCONUT_SPROUT = registerWithItem(
            ModBlockIds.COCONUT_SPROUT,
            ModItemIds.COCONUT_SPROUT,
            CoconutSaplingBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollision()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
    );

    // --- Sky Dimension Blocks ---
    public static final Block SKY_STONE = registerWithItem(
            ModBlockIds.SKY_STONE,
            ModItemIds.SKY_STONE,
            Block::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_BLUE)
                    .requiresCorrectToolForDrops()
                    .strength(2.5f, 6.0f)
                    .sound(SoundType.STONE)
    );

    public static final Block SKY_GRASS_BLOCK = registerWithItem(
            ModBlockIds.SKY_GRASS_BLOCK,
            ModItemIds.SKY_GRASS_BLOCK,
            Block::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_CYAN)
                    .strength(0.6f)
                    .sound(SoundType.GRASS)
    );

    public static final Block SKY_DIRT = registerWithItem(
            ModBlockIds.SKY_DIRT,
            ModItemIds.SKY_DIRT,
            Block::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE)
                    .strength(0.5f)
                    .sound(SoundType.GRAVEL)
    );

    public static final Block GLIMMERSTONE = registerWithItem(
            ModBlockIds.GLIMMERSTONE,
            ModItemIds.GLIMMERSTONE,
            Block::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .lightLevel(state -> 15)
                    .strength(1.2f)
                    .sound(SoundType.GLASS)
    );

    public static final Block SKY_METAL_ORE = registerWithItem(
            ModBlockIds.SKY_METAL_ORE,
            ModItemIds.SKY_METAL_ORE,
            Block::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_BLUE)
                    .requiresCorrectToolForDrops()
                    .strength(4.0f, 4.0f)
                    .sound(SoundType.STONE)
    );

    public static final Block RAW_SKY_METAL_BLOCK = registerWithItem(
            ModBlockIds.RAW_SKY_METAL_BLOCK,
            ModItemIds.RAW_SKY_METAL_BLOCK,
            Block::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_CYAN)
                    .requiresCorrectToolForDrops()
                    .strength(5.0f, 6.0f)
                    .sound(SoundType.METAL)
    );

    public static final Block SKY_METAL_BLOCK = registerWithItem(
            ModBlockIds.SKY_METAL_BLOCK,
            ModItemIds.SKY_METAL_BLOCK,
            Block::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DIAMOND)
                    .requiresCorrectToolForDrops()
                    .strength(5.5f, 7.0f)
                    .sound(SoundType.NETHERITE_BLOCK)
    );

    public static final Block CHROMA_ORE = registerWithItem(
            ModBlockIds.CHROMA_ORE,
            ModItemIds.CHROMA_ORE,
            Block::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_MAGENTA)
                    .lightLevel(state -> 8)
                    .requiresCorrectToolForDrops()
                    .strength(3.5f, 3.5f)
                    .sound(SoundType.AMETHYST)
    );

    public static final Block PRISM_LOG = registerWithItem(
            ModBlockIds.PRISM_LOG,
            ModItemIds.PRISM_LOG,
            RotatedPillarBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE)
                    .strength(2.0f)
                    .sound(SoundType.WOOD)
    );

    public static final Block PRISM_WOOD = registerWithItem(
            ModBlockIds.PRISM_WOOD,
            ModItemIds.PRISM_WOOD,
            RotatedPillarBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE)
                    .strength(2.0f)
                    .sound(SoundType.WOOD)
    );

    public static final Block PRISM_LEAVES = registerWithItem(
            ModBlockIds.PRISM_LEAVES,
            ModItemIds.PRISM_LEAVES,
            p -> new TintedParticleLeavesBlock(0.02f, p),
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PINK)
                    .lightLevel(state -> 4)
                    .strength(0.2f)
                    .sound(SoundType.GRASS)
                    .noOcclusion()
    );

    public static final Block PRISM_PLANKS = registerWithItem(
            ModBlockIds.PRISM_PLANKS,
            ModItemIds.PRISM_PLANKS,
            Block::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PINK)
                    .strength(2.0f, 3.0f)
                    .sound(SoundType.WOOD)
    );

    public static final Block SKY_PORTAL_BLOCK = registerWithItem(
            ModBlockIds.SKY_PORTAL_BLOCK,
            ModItemIds.SKY_PORTAL_BLOCK,
            SkyPortalBlock::new,
            BlockBehaviour.Properties.of()
                    .noCollision()
                    .lightLevel(state -> 12)
                    .strength(-1.0f, 3600000.0f)
                    .sound(SoundType.GLASS)
    );

    public static Block registerWithItem(
            ResourceKey<Block> blockKey,
            ResourceKey<Item> itemKey,
            Function<BlockBehaviour.Properties, Block> blockFactory,
            BlockBehaviour.Properties blockProperties) {
        
        Block block = blockFactory.apply(blockProperties.setId(blockKey));
        Registry.register(BuiltInRegistries.BLOCK, blockKey, block);

        Item.Properties itemProperties = new Item.Properties().useBlockDescriptionPrefix().setId(itemKey);
        BlockItem blockItem = new BlockItem(block, itemProperties);
        Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);

        MineOrigins.LOGGER.info("Registered block and block item: {}", blockKey.identifier());
        return block;
    }

    public static void initialize() {
        MineOrigins.LOGGER.info("Initialized {} blocks", MineOrigins.MOD_ID);
    }
}
