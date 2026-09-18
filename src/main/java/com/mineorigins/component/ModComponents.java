package com.mineorigins.component;

import com.mineorigins.MineOrigins;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModComponents {
    public static void initialize() {
        MineOrigins.LOGGER.info("Registering {} components", MineOrigins.MOD_ID);
    }


    public static final DataComponentType<AdvancedCustomComponent> ADVANCED_CUSTOM_COMPONENT = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            MineOrigins.id("custom"),
            DataComponentType.<AdvancedCustomComponent>builder().persistent(AdvancedCustomComponent.CODEC)
                    .networkSynchronized(AdvancedCustomComponent.STREAM_CODEC).build());
}