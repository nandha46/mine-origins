package com.mineorigins.client;

import com.mineorigins.client.render.EagleModel;
import com.mineorigins.client.render.EagleRenderer;
import com.mineorigins.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;

public class MineOriginsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Register Eagle model layer and renderer
		ModelLayerRegistry.registerModelLayer(EagleModel.EAGLE_LAYER, EagleModel::createBodyLayer);
		EntityRendererRegistry.register(ModEntities.EAGLE, EagleRenderer::new);
	}
}