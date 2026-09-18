package com.mineorigins.Item;

import java.util.function.Function;

import com.mineorigins.MineOrigins;
import com.mineorigins.block.ModBlocks;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModItems {

    // Legacy / Raw Items & Ingots
    public static final Item BAUXITE = register(ModItemIds.BAUXITE, Item::new, new Item.Properties());
    public static final Item RAW_BAUXITE = register(ModItemIds.RAW_BAUXITE, Item::new, new Item.Properties());
    public static final Item ALUMINIUM = register(ModItemIds.ALUMINIUM, Item::new, new Item.Properties());

    public static final Item ILMENITE = register(ModItemIds.ILMENITE, Item::new, new Item.Properties());
    public static final Item RUTILE = register(ModItemIds.RUTILE, Item::new, new Item.Properties());
    public static final Item RAW_TITANIUM = register(ModItemIds.RAW_TITANIUM, Item::new, new Item.Properties());
    public static final Item TITANIUM = register(ModItemIds.TITANIUM, Item::new, new Item.Properties());

    // Coconut & Palm items
    public static final Item COCONUT = register(
            ModItemIds.COCONUT,
            Item::new,
            new Item.Properties().food(new FoodProperties(4, 0.6f, false))
    );
    public static final Item COCONUT_FIBER = register(ModItemIds.COCONUT_FIBER, Item::new, new Item.Properties());
    public static final Item ROPE = register(ModItemIds.ROPE, Item::new, new Item.Properties());

    public static final ResourceKey<CreativeModeTab> MINE_ORIGINS_CREATIVE_TAB_KEY = ResourceKey.create(
            BuiltInRegistries.CREATIVE_MODE_TAB.key(), MineOrigins.id("creative_tab"));

    public static final CreativeModeTab MINE_ORIGINS_CREATIVE_TAB = FabricCreativeModeTab.builder()
            .icon(() -> new ItemStack(ModBlocks.BAUXITE_ORE))
            .title(Component.translatable("creativeTab.mine-origins"))
            .displayItems((params, output) -> {
                // Aluminium suite
                output.accept(ModBlocks.BAUXITE_ORE);
                output.accept(ModItems.RAW_BAUXITE);
                output.accept(ModBlocks.RAW_BAUXITE_BLOCK);
                output.accept(ModItems.ALUMINIUM);
                output.accept(ModBlocks.ALUMINIUM_BLOCK);

                // Titanium suite
                output.accept(ModBlocks.ILMENITE_ORE);
                output.accept(ModBlocks.RUTILE_ORE);
                output.accept(ModItems.RAW_TITANIUM);
                output.accept(ModBlocks.RAW_TITANIUM_BLOCK);
                output.accept(ModItems.TITANIUM);
                output.accept(ModBlocks.TITANIUM_BLOCK);

                // Coconut & Palm suite
                output.accept(ModBlocks.COCONUT_LOG);
                output.accept(ModBlocks.COCONUT_WOOD);
                output.accept(ModBlocks.COCONUT_LEAVES);
                output.accept(ModBlocks.COCONUT_SPROUT);
                output.accept(ModItems.COCONUT);
                output.accept(ModItems.COCONUT_FIBER);
                output.accept(ModItems.ROPE);

                // Existing/compat ore items
                output.accept(ModItems.BAUXITE);
                output.accept(ModItems.ILMENITE);
                output.accept(ModItems.RUTILE);
            })
            .build();

    public static Item register(ResourceKey<Item> itemKey, Function<Item.Properties, Item> itemFactory,
            Item.Properties settings) {
        Item item = itemFactory.apply(settings.setId(itemKey));
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);
        MineOrigins.LOGGER.info("Registered item: {}", itemKey.identifier());
        return item;
    }

    public static void initialize() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MINE_ORIGINS_CREATIVE_TAB_KEY,
                MINE_ORIGINS_CREATIVE_TAB);
        MineOrigins.LOGGER.info("Registered creative mode tab: {}", MINE_ORIGINS_CREATIVE_TAB_KEY.identifier());
    }
}