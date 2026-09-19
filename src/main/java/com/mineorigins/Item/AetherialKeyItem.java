package com.mineorigins.Item;

import com.mineorigins.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class AetherialKeyItem extends Item {

    public AetherialKeyItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        Player player = context.getPlayer();
        BlockState state = level.getBlockState(clickedPos);

        // Can activate inside or against Quartz blocks, Glowstone, or Prismarine to create a Sky Portal
        if (state.is(Blocks.QUARTZ_BLOCK) || state.is(Blocks.SMOOTH_QUARTZ) || state.is(Blocks.CHISELED_QUARTZ_BLOCK) || state.is(ModBlocks.GLIMMERSTONE)) {
            BlockPos targetPos = clickedPos.relative(context.getClickedFace());
            if (level.isEmptyBlock(targetPos)) {
                level.playSound(player, targetPos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 1.0f, 1.2f);
                level.setBlock(targetPos, ModBlocks.SKY_PORTAL_BLOCK.defaultBlockState(), 3);

                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(
                            ParticleTypes.END_ROD,
                            targetPos.getX() + 0.5,
                            targetPos.getY() + 0.5,
                            targetPos.getZ() + 0.5,
                            25,
                            0.3, 0.3, 0.3, 0.05
                    );
                }

                if (player != null && !player.getAbilities().instabuild) {
                    context.getItemInHand().hurtAndBreak(1, player, player.getEquipmentSlotForItem(context.getItemInHand()));
                }
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }
}
