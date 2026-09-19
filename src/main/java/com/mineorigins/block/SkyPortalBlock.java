package com.mineorigins.block;

import com.mineorigins.worldgen.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SkyPortalBlock extends Block {

    public SkyPortalBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, net.minecraft.world.entity.InsideBlockEffectApplier effectApplier, boolean bl) {
        if (!level.isClientSide() && entity.canUsePortal(false)) {
            if (entity instanceof ServerPlayer player) {
                ServerLevel currentLevel = player.level();
                boolean isInSky = currentLevel.dimension().equals(ModDimensions.SKY_DIMENSION_LEVEL);

                ServerLevel targetLevel = isInSky
                        ? currentLevel.getServer().getLevel(Level.OVERWORLD)
                        : currentLevel.getServer().getLevel(ModDimensions.SKY_DIMENSION_LEVEL);

                if (targetLevel != null) {
                    BlockPos targetPos = isInSky
                            ? targetLevel.getRespawnData().pos()
                            : new BlockPos(0, 130, 0);

                    // Ensure safe platform in the sky dimension
                    if (!isInSky) {
                        ensurePlatform(targetLevel, targetPos);
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

    private static void ensurePlatform(ServerLevel level, BlockPos center) {
        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {
                BlockPos p = center.offset(dx, 0, dz);
                if (level.isEmptyBlock(p)) {
                    level.setBlock(p, ModBlocks.SKY_STONE.defaultBlockState(), 3);
                }
                // Clear clearance above
                for (int dy = 1; dy <= 3; dy++) {
                    BlockPos airPos = center.offset(dx, dy, dz);
                    if (!level.isEmptyBlock(airPos)) {
                        level.removeBlock(airPos, false);
                    }
                }
            }
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(100) == 0) {
            level.playLocalSound(
                    pos.getX() + 0.5,
                    pos.getY() + 0.5,
                    pos.getZ() + 0.5,
                    net.minecraft.sounds.SoundEvents.PORTAL_AMBIENT,
                    net.minecraft.sounds.SoundSource.BLOCKS,
                    0.5f,
                    random.nextFloat() * 0.4f + 0.8f,
                    false
            );
        }

        for (int i = 0; i < 3; i++) {
            double x = pos.getX() + random.nextDouble();
            double y = pos.getY() + random.nextDouble();
            double z = pos.getZ() + random.nextDouble();
            level.addParticle(ParticleTypes.END_ROD, x, y, z, 0.0, 0.04, 0.0);
        }
    }
}
