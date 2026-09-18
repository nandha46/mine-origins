package com.mineorigins.Item;

import java.util.function.Function;

import com.mineorigins.MineOrigins;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModItems {

    public static final Item BAUXITE = register(ModItemIds.BAUXITE, Item::new, new Item.Properties());
    public static final Item ILMENITE = register(ModItemIds.ILMENITE, Item::new, new Item.Properties());
    public static final Item RUTILE = register(ModItemIds.RUTILE, Item::new, new Item.Properties());
    public static final Item TITANIUM = register(ModItemIds.TITANIUM, Item::new, new Item.Properties());
    public static final Item ALUMINIUM = register(ModItemIds.ALUMINIUM, Item::new, new Item.Properties());

    public static final ResourceKey<CreativeModeTab> MINE_ORIGINS_CREATIVE_TAB_KEY = ResourceKey.create(
            BuiltInRegistries.CREATIVE_MODE_TAB.key(), MineOrigins.id("creative_tab"));
    public static final CreativeModeTab MINE_ORIGINS_CREATIVE_TAB = FabricCreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.BAUXITE))
            .title(Component.translatable("creativeTab.mine-origins"))
            .displayItems((params, output) -> {
                output.accept(ModItems.BAUXITE);
                output.accept(ModItems.ILMENITE);
                output.accept(ModItems.RUTILE);
                output.accept(ModItems.TITANIUM);
                output.accept(ModItems.ALUMINIUM);

            })
            .build();

    public static Item register(ResourceKey<Item> itemKey, Function<Item.Properties, Item> itemFactory,
            Item.Properties settings) {
        // Create the item instance.
        Item item = itemFactory.apply(settings.setId(itemKey));

        // Register the item.
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