package com.mineorigins;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.ItemComponentTooltipProviderRegistry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mineorigins.Item.ModItems;
import com.mineorigins.block.ModBlocks;
import com.mineorigins.component.ModComponents;
import com.mineorigins.worldgen.ModWorldGen;

public class MineOrigins implements ModInitializer {
	public static final String MOD_ID = "mine-origins";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Mine Origins...");

		ModBlocks.initialize();
		LOGGER.info("Registered ModBlocks");

		ModItems.initialize();
		LOGGER.info("Registered ModItems and Creative Mode Tab");

		ModComponents.initialize();
		LOGGER.info("Registered ModComponents");

		ItemComponentTooltipProviderRegistry.addAfter(DataComponents.DAMAGE, ModComponents.ADVANCED_CUSTOM_COMPONENT);
		LOGGER.info("Registered Tooltip Provider for ADVANCED_CUSTOM_COMPONENT");

		ModWorldGen.initialize();
		LOGGER.info("Registered Worldgen");

		LOGGER.info("Mine Origins successfully initialized!");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
