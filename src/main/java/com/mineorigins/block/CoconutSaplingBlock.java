package com.mineorigins.block;

import com.mineorigins.worldgen.tree.CoconutTreeGenerator;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CoconutSaplingBlock extends BushBlock implements BonemealableBlock {

    // 0 = small sprout, 1 = growing palm shoot, 2 = mature sapling ready to form tree
    public static final IntegerProperty STAGE = BlockStateProperties.AGE_2;
    private static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);

    public CoconutSaplingBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, 0));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        // Can be planted on sand, red sand, dirt, coarse dirt, and grass
        return state.is(Blocks.SAND) || state.is(Blocks.RED_SAND) ||
               state.is(Blocks.DIRT) || state.is(Blocks.GRASS_BLOCK) ||
               state.is(Blocks.COARSE_DIRT);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos below = pos.below();
        return this.mayPlaceOn(level.getBlockState(below), level, below);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
        if (level.getMaxLocalRawBrightness(pos.above()) >= 9 && random.nextInt(7) == 0) {
            this.advanceGrowth(level, pos, state, random);
        }
    }

    public void advanceGrowth(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        int stage = state.getValue(STAGE);
        if (stage < 2) {
            level.setBlock(pos, state.setValue(STAGE, stage + 1), 3);
        } else {
            // Remove sapling and generate tree
            level.removeBlock(pos, false);
            if (!CoconutTreeGenerator.generate(level, pos, random)) {
                // Restore if failed
                level.setBlock(pos, state, 3);
            }
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return 0.45f > random.nextFloat();
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        this.advanceGrowth(level, pos, state, random);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }
}
