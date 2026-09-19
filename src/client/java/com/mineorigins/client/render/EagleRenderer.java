package com.mineorigins.client.render;

import com.mineorigins.MineOrigins;
import com.mineorigins.entity.EagleEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class EagleRenderer extends MobRenderer<EagleEntity, EagleRenderState, EagleModel> {

    public static final Identifier TEXTURE = MineOrigins.id("textures/entity/eagle.png");

    public EagleRenderer(EntityRendererProvider.Context context) {
        super(context, new EagleModel(context.bakeLayer(EagleModel.EAGLE_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTextureLocation(EagleRenderState state) {
        return TEXTURE;
    }

    @Override
    public EagleRenderState createRenderState() {
        return new EagleRenderState();
    }

    @Override
    public void extractRenderState(EagleEntity eagle, EagleRenderState state, float partialTick) {
        super.extractRenderState(eagle, state, partialTick);
        state.flap = eagle.flap;
        state.flapSpeed = eagle.flapSpeed;
        state.isOnGround = eagle.onGround();
        state.isDiving = eagle.getTarget() != null && !eagle.onGround();
    }
}
