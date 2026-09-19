package com.mineorigins.Item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class DreamcatcherWandItem extends Item {

    public DreamcatcherWandItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0f, 1.4f);

        // Grant Slow Falling and Levitation burst or aura shield
        player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 200, 0));
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1));

        Vec3 look = player.getLookAngle();
        player.setDeltaMovement(player.getDeltaMovement().add(look.x * 0.5, 0.4, look.z * 0.5));
        player.hurtMarked = true;

        if (level instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 20; i++) {
                double px = player.getX() + (level.getRandom().nextDouble() - 0.5) * 2.0;
                double py = player.getY() + level.getRandom().nextDouble() * 2.0;
                double pz = player.getZ() + (level.getRandom().nextDouble() - 0.5) * 2.0;
                serverLevel.sendParticles(ParticleTypes.GLOW, px, py, pz, 1, 0, 0.05, 0, 0.02);
            }
        }

        player.getCooldowns().addCooldown(stack, 60);
        if (!player.getAbilities().instabuild) {
            stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack));
        }

        return InteractionResult.SUCCESS;
    }
}
