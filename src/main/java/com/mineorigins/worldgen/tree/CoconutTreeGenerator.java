package com.mineorigins.worldgen.tree;

import com.mineorigins.block.ModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CoconutTreeGenerator {

    public static boolean generate(LevelAccessor level, BlockPos startPos, RandomSource random) {
        // Check ground: must be sand, red sand, grass block, or dirt
        BlockState ground = level.getBlockState(startPos.below());
        if (!ground.is(Blocks.SAND) && !ground.is(Blocks.RED_SAND) && !ground.is(Blocks.GRASS_BLOCK) && !ground.is(Blocks.DIRT)) {
            return false;
        }

        // Height: 6 to 11 blocks tall
        int height = 6 + random.nextInt(6);

        // Trunk curvature: 0 = straight, 1 = slightly curved, 2 = pronounced beach slant
        boolean curved = random.nextFloat() < 0.75f;
        Direction curveDir = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        int curveThreshold = 2 + random.nextInt(2); // begins leaning after 2-3 blocks

        BlockPos.MutableBlockPos current = startPos.mutable();
        BlockState logState = ModBlocks.COCONUT_LOG.defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y);

        // 1. Swollen base:
        // Place central trunk base
        level.setBlock(current, logState, 3);

        // Swollen flare: 50% chance of small root buttress on 1-2 sides at the bottom
        if (random.nextBoolean()) {
            Direction buttressDir = Direction.Plane.HORIZONTAL.getRandomDirection(random);
            BlockPos buttressPos = current.relative(buttressDir);
            if (level.getBlockState(buttressPos).canBeReplaced()) {
                level.setBlock(buttressPos, logState, 3);
            }
        }

        // 2. Trunk growth with potential curve
        int offset = 0;
        for (int y = 1; y < height; y++) {
            current.move(Direction.UP);

            if (curved && y >= curveThreshold && offset < 2) {
                if (random.nextFloat() < 0.6f) {
                    current.move(curveDir);
                    offset++;
                }
            }

            if (!level.getBlockState(current).canBeReplaced()) {
                // If obstructed, abort or finish here
                break;
            }

            level.setBlock(current, logState, 3);
        }

        BlockPos crownCenter = current.immutable();

        // 3. Palm Frond Leaves (radiating canopy with drooping tips)
        BlockState leafState = ModBlocks.COCONUT_LEAVES.defaultBlockState();

        // Crown top layer
        setLeafIfReplaceable(level, crownCenter.above(), leafState);

        // Horizontal fronds radiating out in 4 cardinal and 4 diagonal directions
        Direction[] cardinals = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

        for (Direction dir : cardinals) {
            // Level 0 (center height)
            setLeafIfReplaceable(level, crownCenter.relative(dir), leafState);
            setLeafIfReplaceable(level, crownCenter.relative(dir, 2), leafState);
            // Drooping leaf tip
            setLeafIfReplaceable(level, crownCenter.relative(dir, 2).below(), leafState);

            // Optional extended length for taller trees
            if (height >= 8 && random.nextBoolean()) {
                setLeafIfReplaceable(level, crownCenter.relative(dir, 3).below(), leafState);
            }
        }

        // Diagonal fronds
        for (int dx = -1; dx <= 1; dx += 2) {
            for (int dz = -1; dz <= 1; dz += 2) {
                BlockPos diag1 = crownCenter.offset(dx, 0, dz);
                BlockPos diag2 = crownCenter.offset(dx * 2, -1, dz * 2);
                setLeafIfReplaceable(level, diag1, leafState);
                setLeafIfReplaceable(level, diag2, leafState);
            }
        }

        return true;
    }

    private static void setLeafIfReplaceable(LevelAccessor level, BlockPos pos, BlockState state) {
        if (level.getBlockState(pos).canBeReplaced()) {
            level.setBlock(pos, state, 3);
        }
    }
}
