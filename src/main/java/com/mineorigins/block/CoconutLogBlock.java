package com.mineorigins.block;

import com.mineorigins.Item.ModItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CoconutLogBlock extends RotatedPillarBlock {

    public CoconutLogBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void attack(BlockState state, Level level, BlockPos pos, Player player) {
        super.attack(state, level, pos, player);

        // Punching the trunk has a 6% chance to knock down a fresh coconut
        if (!level.isClientSide()) {
            RandomSource random = level.getRandom();
            if (random.nextFloat() < 0.06f) {
                // Drop near player or base
                double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.6;
                double y = pos.getY() + 0.8;
                double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.6;
                ItemEntity coconutEntity = new ItemEntity(level, x, y, z, new ItemStack(ModItems.COCONUT, 1));
                coconutEntity.setDefaultPickUpDelay();
                level.addFreshEntity(coconutEntity);
                level.playSound(null, pos, SoundEvents.WOOD_HIT, SoundSource.BLOCKS, 1.0f, 0.8f);
            }
        }
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        // When cut down, drop extra coconut fibers (1-3 fibers)
        if (!level.isClientSide() && !player.isCreative()) {
            RandomSource random = level.getRandom();
            int fiberCount = 1 + random.nextInt(3);
            popResource(level, pos, new ItemStack(ModItems.COCONUT_FIBER, fiberCount));
        }
        return super.playerWillDestroy(level, pos, state, player);
    }
}
