package com.mineorigins.Item;

import com.mineorigins.MineOrigins;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class ModItemIds {

    public static final ResourceKey<Item> BAUXITE = create("bauxite");
    public static final ResourceKey<Item> ALUMINIUM = create("aluminium");

    public static final ResourceKey<Item> ILMENITE = create("ilmenite");
    public static final ResourceKey<Item> RUTILE = create("rutile");
    public static final ResourceKey<Item> TITANIUM = create("titanium");

    public static ResourceKey<Item> create(String name) {
        // Create the item key.
        return ResourceKey.create(Registries.ITEM, MineOrigins.id(name));
    }
}
