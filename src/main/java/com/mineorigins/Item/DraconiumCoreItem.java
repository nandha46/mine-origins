package com.mineorigins.Item;

import com.mineorigins.block.DragonCityPortalBlock;
import com.mineorigins.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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

public class DraconiumCoreItem extends Item {

    public DraconiumCoreItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        Player player = context.getPlayer();
        BlockState clickedState = level.getBlockState(clickedPos);

        // When used on Smooth Stone, checks or ignites a 3x3 portal
        if (clickedState.is(Blocks.SMOOTH_STONE)) {
            BlockPos targetPos = clickedPos.relative(context.getClickedFace());
            if (level.isEmptyBlock(targetPos)) {
                Direction.Axis clickedAxis = context.getClickedFace().getAxis();
                // Check if inside a 3x3 frame or ignite 3x3 portal
                boolean ignited = tryIgnite3x3Frame(level, targetPos, clickedAxis);
                if (!ignited) {
                    // Directly construct 3x3 portal centered horizontally on targetPos
                    Direction.Axis portalAxis = (clickedAxis == Direction.Axis.Z) ? Direction.Axis.X : Direction.Axis.Z;
                    BlockPos bottomOrigin = (portalAxis == Direction.Axis.X)
                            ? targetPos.offset(-1, 0, 0)
                            : targetPos.offset(0, 0, -1);
                    DragonCityPortalBlock.buildPortal3x3(level, bottomOrigin, portalAxis);
                }

                level.playSound(player, targetPos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, 1.0f, 1.2f);
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK, targetPos.getX() + 0.5, targetPos.getY() + 1.5, targetPos.getZ() + 0.5, 60, 1.0, 1.0, 1.0, 0.15);
                    serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, targetPos.getX() + 0.5, targetPos.getY() + 1.5, targetPos.getZ() + 0.5, 30, 0.8, 0.8, 0.8, 0.05);
                    serverLevel.sendParticles(ParticleTypes.END_ROD, targetPos.getX() + 0.5, targetPos.getY() + 1.5, targetPos.getZ() + 0.5, 25, 0.5, 0.5, 0.5, 0.05);
                }

                if (player != null && !player.getAbilities().instabuild) {
                    context.getItemInHand().hurtAndBreak(1, player, player.getEquipmentSlotForItem(context.getItemInHand()));
                }
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    /**
     * Checks if targetPos is part of an enclosed 3x3 smooth stone frame opening
     * (Frame outer size: 5x5, inner opening: 3x3).
     */
    private boolean tryIgnite3x3Frame(Level level, BlockPos insidePos, Direction.Axis clickedAxis) {
        // Test both X and Z orientations
        Direction.Axis[] axes = (clickedAxis == Direction.Axis.X)
                ? new Direction.Axis[]{Direction.Axis.Z, Direction.Axis.X}
                : new Direction.Axis[]{Direction.Axis.X, Direction.Axis.Z};

        for (Direction.Axis axis : axes) {
            // Find possible bottom-left origin of 3x3 inner area
            for (int colOffset = -2; colOffset <= 0; colOffset++) {
                for (int rowOffset = -2; rowOffset <= 0; rowOffset++) {
                    BlockPos candidateBottomLeft = (axis == Direction.Axis.X)
                            ? insidePos.offset(colOffset, rowOffset, 0)
                            : insidePos.offset(0, rowOffset, colOffset);

                    if (isValid3x3Frame(level, candidateBottomLeft, axis)) {
                        DragonCityPortalBlock.buildPortal3x3(level, candidateBottomLeft, axis);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isValid3x3Frame(Level level, BlockPos bottomLeft, Direction.Axis axis) {
        // Check frame border (surrounding 3x3 opening: col from -1 to 3, row from -1 to 3)
        for (int c = -1; c <= 3; c++) {
            for (int r = -1; r <= 3; r++) {
                boolean isBorder = (c == -1 || c == 3 || r == -1 || r == 3);
                BlockPos p = (axis == Direction.Axis.X)
                        ? bottomLeft.offset(c, r, 0)
                        : bottomLeft.offset(0, r, c);

                if (isBorder) {
                    if (!level.getBlockState(p).is(Blocks.SMOOTH_STONE)) {
                        return false;
                    }
                } else {
                    BlockState s = level.getBlockState(p);
                    if (!s.isAir() && !s.is(ModBlocks.DRAGON_CITY_PORTAL_BLOCK)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
