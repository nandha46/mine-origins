package com.mineorigins.block;

import com.mineorigins.worldgen.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DragonCityPortalBlock extends Block {

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final EnumProperty<DragonCityPortalPart> PART = EnumProperty.create("part", DragonCityPortalPart.class);

    protected static final VoxelShape X_AABB = Block.box(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
    protected static final VoxelShape Z_AABB = Block.box(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);

    public DragonCityPortalBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(AXIS, Direction.Axis.X)
                .setValue(PART, DragonCityPortalPart.CENTER));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, PART);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(AXIS) == Direction.Axis.Z ? Z_AABB : X_AABB;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    protected BlockState updateShape(
            BlockState state,
            LevelReader levelReader,
            ScheduledTickAccess scheduledTickAccess,
            BlockPos pos,
            Direction direction,
            BlockPos neighborPos,
            BlockState neighborState,
            RandomSource random
    ) {
        Direction.Axis axis = state.getValue(AXIS);
        // If a portal block has neither a portal neighbor nor a frame neighbor along its axis/vertical, validate
        return super.updateShape(state, levelReader, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, net.minecraft.world.entity.InsideBlockEffectApplier effectApplier, boolean bl) {
        if (!level.isClientSide() && entity.canUsePortal(false)) {
            if (entity instanceof ServerPlayer player) {
                ServerLevel currentLevel = player.level();
                boolean isInDragonCity = currentLevel.dimension().equals(ModDimensions.DRAGON_CITY_LEVEL);

                ServerLevel targetLevel = isInDragonCity
                        ? currentLevel.getServer().getLevel(Level.OVERWORLD)
                        : currentLevel.getServer().getLevel(ModDimensions.DRAGON_CITY_LEVEL);

                if (targetLevel != null) {
                    BlockPos targetPos;
                    if (isInDragonCity) {
                        targetPos = targetLevel.getRespawnData().pos();
                    } else {
                        // World origin of Dragon City: near (0, 319, 0)
                        targetPos = new BlockPos(0, 319, 0);
                        ensureArrivalPlatform(targetLevel, targetPos);
                    }

                    Vec3 targetVec = Vec3.atBottomCenterOf(targetPos.above());
                    TeleportTransition transition = new TeleportTransition(
                            targetLevel,
                            targetVec,
                            Vec3.ZERO,
                            player.getYRot(),
                            player.getXRot(),
                            TeleportTransition.PLAY_PORTAL_SOUND
                    );
                    player.teleport(transition);
                    player.setPortalCooldown(100);
                }
            }
        }
    }

    /**
     * Builds a safe landing platform in Dragon City at world origin Y=319 with a return portal
     */
    private static void ensureArrivalPlatform(ServerLevel level, BlockPos center) {
        for (int dx = -3; dx <= 3; dx++) {
            for (int dz = -3; dz <= 3; dz++) {
                BlockPos p = center.offset(dx, 0, dz);
                if (Math.abs(dx) == 3 || Math.abs(dz) == 3) {
                    level.setBlock(p, Blocks.POLISHED_BLACKSTONE.defaultBlockState(), 3);
                } else {
                    level.setBlock(p, Blocks.SMOOTH_STONE.defaultBlockState(), 3);
                }

                // Clear vertical clearance above the platform
                for (int dy = 1; dy <= 5; dy++) {
                    BlockPos airPos = center.offset(dx, dy, dz);
                    if (!level.isEmptyBlock(airPos)) {
                        level.removeBlock(airPos, false);
                    }
                }
            }
        }

        // Place a complete 3x3 return portal along X axis
        BlockPos portalBottomOrigin = center.offset(-1, 1, 0);
        buildPortal3x3(level, portalBottomOrigin, Direction.Axis.X);
    }

    /**
     * Places the 3x3 stretched portal blocks with corresponding part states
     */
    public static void buildPortal3x3(Level level, BlockPos bottomOrigin, Direction.Axis axis) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                BlockPos p = (axis == Direction.Axis.X)
                        ? bottomOrigin.offset(col, row, 0)
                        : bottomOrigin.offset(0, row, col);

                DragonCityPortalPart part = DragonCityPortalPart.get(col, row);
                BlockState state = ModBlocks.DRAGON_CITY_PORTAL_BLOCK.defaultBlockState()
                        .setValue(AXIS, axis)
                        .setValue(PART, part);
                level.setBlock(p, state, 3);
            }
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(60) == 0) {
            level.playLocalSound(
                    pos.getX() + 0.5,
                    pos.getY() + 0.5,
                    pos.getZ() + 0.5,
                    SoundEvents.BEACON_AMBIENT,
                    SoundSource.BLOCKS,
                    0.8f,
                    random.nextFloat() * 0.2f + 1.2f,
                    false
            );
        }

        Direction.Axis axis = state.getValue(AXIS);

        // Electric cyan energy vortex particles matching dragon portal.png
        for (int i = 0; i < 3; i++) {
            double u = (random.nextDouble() - 0.5) * 1.0;
            double v = (random.nextDouble() - 0.5) * 1.0;

            double x = pos.getX() + 0.5 + (axis == Direction.Axis.X ? u : (random.nextDouble() - 0.5) * 0.2);
            double y = pos.getY() + 0.5 + v;
            double z = pos.getZ() + 0.5 + (axis == Direction.Axis.Z ? u : (random.nextDouble() - 0.5) * 0.2);

            double speedU = -v * 0.08;
            double speedV = u * 0.08;

            double speedX = axis == Direction.Axis.X ? speedU : 0.0;
            double speedY = speedV;
            double speedZ = axis == Direction.Axis.Z ? speedU : 0.0;

            level.addParticle(ParticleTypes.ELECTRIC_SPARK, x, y, z, speedX, speedY, speedZ);

            if (random.nextBoolean()) {
                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x, y, z, speedX * 0.5, speedY * 0.5, speedZ * 0.5);
            }
            if (random.nextInt(4) == 0) {
                level.addParticle(ParticleTypes.END_ROD, x, y, z, 0.0, 0.02, 0.0);
            }
        }
    }
}
