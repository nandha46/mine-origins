package com.mineorigins.entity;

import com.mineorigins.MineOrigins;
import com.mineorigins.Item.ModItems;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.levelgen.Heightmap;

public class ModEntities {

    public static final ResourceKey<EntityType<?>> EAGLE_KEY = ResourceKey.create(
            Registries.ENTITY_TYPE,
            MineOrigins.id("eagle")
    );

    public static final EntityType<EagleEntity> EAGLE = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            EAGLE_KEY,
            EntityType.Builder.of(EagleEntity::new, MobCategory.CREATURE)
                    .sized(1.1f, 0.9f)
                    .eyeHeight(0.7f)
                    .clientTrackingRange(10)
                    .build(EAGLE_KEY)
    );

    public static final ResourceKey<Item> EAGLE_SPAWN_EGG_KEY = ResourceKey.create(
            Registries.ITEM,
            MineOrigins.id("eagle_spawn_egg")
    );

    public static final Item EAGLE_SPAWN_EGG = Registry.register(
            BuiltInRegistries.ITEM,
            EAGLE_SPAWN_EGG_KEY,
            new SpawnEggItem(new Item.Properties().setId(EAGLE_SPAWN_EGG_KEY).spawnEgg(EAGLE))
    );

    public static void initialize() {
        FabricDefaultAttributeRegistry.register(EAGLE, EagleEntity.createAttributes());

        SpawnPlacements.register(
                EAGLE,
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                EagleEntity::checkEagleSpawnRules
        );

        BiomeModifications.addSpawn(
                BiomeSelectors.tag(BiomeTags.IS_MOUNTAIN).or(BiomeSelectors.tag(BiomeTags.IS_BEACH)),
                MobCategory.CREATURE,
                EAGLE,
                15,
                1,
                2
        );

        MineOrigins.LOGGER.info("Registered Eagle entity, spawn egg, and natural spawn rules");
    }
}
