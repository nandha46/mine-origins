package com.mineorigins.entity;

import java.util.EnumSet;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.rabbit.Rabbit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EagleEntity extends Animal {

    public float flap;
    public float flapSpeed;
    public float oFlapSpeed;
    public float oFlap;
    private float flapping = 1.0f;
    private float nextFlap = 1.0f;

    public EagleEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl<>(this, 15, false);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation nav = new FlyingPathNavigation(this, level);
        nav.setCanOpenDoors(false);
        nav.setCanFloat(true);
        return nav;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH, 14.0)
                .add(Attributes.FLYING_SPEED, 0.7)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.FOLLOW_RANGE, 48.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new EagleDiveAttackGoal(this));
        this.goalSelector.addGoal(2, new EagleCircleGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 16.0f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Rabbit.class, 10, true, false, null));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Chicken.class, 15, true, false, null));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Frog.class, 20, true, false, null));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.calculateFlapping();
    }

    private void calculateFlapping() {
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed += (float) (this.onGround() ? -1 : 4) * 0.3f;
        this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0f, 1.0f);
        if (!this.onGround() && this.flapping < 1.0f) {
            this.flapping = 1.0f;
        }

        this.flapping *= 0.9f;
        Vec3 delta = this.getDeltaMovement();
        if (!this.onGround() && delta.y < 0.0) {
            this.setDeltaMovement(delta.multiply(1.0, 0.6, 1.0));
        }

        this.flap += this.flapping * 2.0f;
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(Items.RABBIT) || itemStack.is(Items.CHICKEN);
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return ModEntities.EAGLE.create(serverLevel, EntitySpawnReason.BREEDING);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PARROT_FLY;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.PARROT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PARROT_DEATH;
    }

    @Override
    protected void checkFallDamage(double d, boolean bl, BlockState blockState, BlockPos blockPos) {
    }

    public static boolean checkEagleSpawnRules(EntityType<EagleEntity> type, LevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        return pos.getY() >= level.getSeaLevel() + 5 && level.getRawBrightness(pos, 0) > 8;
    }

    // Goal: Eagle high-altitude circle
    static class EagleCircleGoal extends Goal {
        private final EagleEntity eagle;
        private double anchorX;
        private double anchorY;
        private double anchorZ;
        private float angle;

        public EagleCircleGoal(EagleEntity eagle) {
            this.eagle = eagle;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return this.eagle.getTarget() == null || !this.eagle.getTarget().isAlive();
        }

        @Override
        public void start() {
            Player nearby = this.eagle.level().getNearestPlayer(this.eagle, 64.0);
            if (nearby != null) {
                this.anchorX = nearby.getX();
                this.anchorY = nearby.getY() + 28.0 + this.eagle.getRandom().nextDouble() * 10.0;
                this.anchorZ = nearby.getZ();
            } else {
                this.anchorX = this.eagle.getX();
                this.anchorY = Math.max(this.eagle.getY(), 95.0);
                this.anchorZ = this.eagle.getZ();
            }
            this.angle = this.eagle.getRandom().nextFloat() * ((float) Math.PI * 2f);
        }

        @Override
        public void tick() {
            this.angle += 0.04f;
            double radius = 18.0;
            double targetX = this.anchorX + Math.cos(this.angle) * radius;
            double targetZ = this.anchorZ + Math.sin(this.angle) * radius;
            double targetY = this.anchorY + Math.sin(this.angle * 0.5f) * 2.0;

            Vec3 dir = new Vec3(targetX - this.eagle.getX(), targetY - this.eagle.getY(), targetZ - this.eagle.getZ());
            double dist = dir.length();
            if (dist > 0.1) {
                dir = dir.normalize();
                this.eagle.setDeltaMovement(this.eagle.getDeltaMovement().scale(0.85).add(dir.scale(0.08)));
                this.eagle.setYRot((float) (Mth.atan2(dir.z, dir.x) * (180f / Math.PI)) - 90f);
            }
        }
    }

    // Goal: Eagle high-speed dive and swoop attack on small prey
    static class EagleDiveAttackGoal extends Goal {
        private final EagleEntity eagle;
        private int attackCooldown = 0;

        public EagleDiveAttackGoal(EagleEntity eagle) {
            this.eagle = eagle;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.eagle.getTarget();
            return target != null && target.isAlive();
        }

        @Override
        public void tick() {
            LivingEntity target = this.eagle.getTarget();
            if (target == null) return;

            Vec3 diff = target.position().subtract(this.eagle.position());
            double distSq = diff.lengthSqr();

            this.eagle.getLookControl().setLookAt(target, 30.0f, 30.0f);

            // Fast swoop downward dive
            Vec3 diveMotion = diff.normalize().scale(0.45);
            this.eagle.setDeltaMovement(this.eagle.getDeltaMovement().scale(0.7).add(diveMotion));

            if (distSq < 4.0 && this.attackCooldown <= 0) {
                this.eagle.doHurtTarget(this.getServerLevel(this.eagle.level()), target);
                this.attackCooldown = 20;
                // Ascend immediately after swoop hit
                this.eagle.setDeltaMovement(this.eagle.getDeltaMovement().add(0, 0.6, 0));
            }

            if (this.attackCooldown > 0) {
                this.attackCooldown--;
            }
        }
    }
}
