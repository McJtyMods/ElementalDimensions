package bitmovers.elementaldimensions.mobs;

import bitmovers.elementaldimensions.ElementalDimensions;
import com.google.common.base.Predicate;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityWaterCreep extends EntityMob {

    private static final DataParameter<Byte> STATUS = EntityDataManager.<Byte>createKey(EntityWaterCreep.class, DataSerializers.BYTE);
    private static final DataParameter<Integer> TARGET_ENTITY = EntityDataManager.<Integer>createKey(EntityWaterCreep.class, DataSerializers.VARINT);
    public static final ResourceLocation LOOT = new ResourceLocation(ElementalDimensions.MODID, "entities/water_creep");

    private float clientSideTailAnimation;
    private float clientSideTailAnimationO;
    private float clientSideTailAnimationSpeed;
    private float clientSideSpikesAnimation;
    private float clientSideSpikesAnimationO;
    private EntityLivingBase targetedEntity;
    private int clientSideAttackTime;
    private boolean clientSideTouchedGround;
    private EntityAIWander wander;

    public EntityWaterCreep(World worldIn) {
        super(worldIn);
        this.experienceValue = 20;
        this.setSize(2.5F, 2.5F);
        ;
        this.moveHelper = new WaterCreepMoveHelper(this);
        this.clientSideTailAnimation = this.rand.nextFloat();
        this.clientSideTailAnimationO = this.clientSideTailAnimation;
    }

    protected void initEntityAI() {
        EntityAIMoveTowardsRestriction entityaimovetowardsrestriction = new EntityAIMoveTowardsRestriction(this, 1.0D);
        this.wander = new EntityAIWander(this, 1.0D, 80);
        this.tasks.addTask(4, new EntityWaterCreep.AIGuardianAttack(this));
        this.tasks.addTask(5, entityaimovetowardsrestriction);
        this.tasks.addTask(7, this.wander);
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityWaterCreep.class, 12.0F, 0.01F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.wander.setMutexBits(3);
        entityaimovetowardsrestriction.setMutexBits(3);
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 10, true, false, new WaterCreepTargetSelector(this)));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(9.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
    }

    public static void registerFixesGuardian(DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, "Guardian");
    }

    /**
     * Returns new PathNavigateGround instance
     */
    protected PathNavigate getNewNavigator(World worldIn) {
        return new PathNavigateSwimmer(this, worldIn);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(STATUS, Byte.valueOf((byte) 0));
        this.dataManager.register(TARGET_ENTITY, Integer.valueOf(0));
    }

    /**
     * Returns true if given flag is set
     */
    private boolean isSyncedFlagSet(int flagId) {
        return (((Byte) this.dataManager.get(STATUS)).byteValue() & flagId) != 0;
    }

    /**
     * Sets a flag state "on/off" on both sides (client/server) by using DataWatcher
     */
    private void setSyncedFlag(int flagId, boolean state) {
        byte b0 = ((Byte) this.dataManager.get(STATUS)).byteValue();

        if (state) {
            this.dataManager.set(STATUS, Byte.valueOf((byte) (b0 | flagId)));
        } else {
            this.dataManager.set(STATUS, Byte.valueOf((byte) (b0 & ~flagId)));
        }
    }

    public boolean isMoving() {
        return this.isSyncedFlagSet(2);
    }

    private void setMoving(boolean moving) {
        this.setSyncedFlag(2, moving);
    }

    public int getAttackDuration() {
        return 60;
    }

    private void setTargetedEntity(int entityId) {
        this.dataManager.set(TARGET_ENTITY, Integer.valueOf(entityId));
    }

    public boolean hasTargetedEntity() {
        return ((Integer) this.dataManager.get(TARGET_ENTITY)).intValue() != 0;
    }

    public EntityLivingBase getTargetedEntity() {
        if (!this.hasTargetedEntity()) {
            return null;
        } else if (this.worldObj.isRemote) {
            if (this.targetedEntity != null) {
                return this.targetedEntity;
            } else {
                Entity entity = this.worldObj.getEntityByID(((Integer) this.dataManager.get(TARGET_ENTITY)).intValue());

                if (entity instanceof EntityLivingBase) {
                    this.targetedEntity = (EntityLivingBase) entity;
                    return this.targetedEntity;
                } else {
                    return null;
                }
            }
        } else {
            return this.getAttackTarget();
        }
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        super.notifyDataManagerChange(key);

        if (TARGET_ENTITY.equals(key)) {
            this.clientSideAttackTime = 0;
            this.targetedEntity = null;
        }
    }

    /**
     * Get number of ticks, at least during which the living entity will be silent.
     */
    public int getTalkInterval() {
        return 160;
    }

    protected SoundEvent getAmbientSound() {
        return (this.isInWater() ? SoundEvents.ENTITY_GUARDIAN_AMBIENT : SoundEvents.ENTITY_GUARDIAN_AMBIENT_LAND);
    }

    protected SoundEvent getHurtSound() {
        return (this.isInWater() ? SoundEvents.ENTITY_GUARDIAN_HURT : SoundEvents.ENTITY_GUARDIAN_HURT_LAND);
    }

    protected SoundEvent getDeathSound() {
        return (this.isInWater() ? SoundEvents.ENTITY_GUARDIAN_DEATH : SoundEvents.ENTITY_GUARDIAN_DEATH_LAND);
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking() {
        return false;
    }

    public float getEyeHeight() {
        return this.height * 0.5F;
    }

    public float getBlockPathWeight(BlockPos pos) {
        return this.worldObj.getBlockState(pos).getMaterial() == Material.WATER ? 10.0F + this.worldObj.getLightBrightness(pos) - 0.5F : super.getBlockPathWeight(pos);
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate() {
        if (this.worldObj.isRemote) {
            this.clientSideTailAnimationO = this.clientSideTailAnimation;

            if (!this.isInWater()) {
                this.clientSideTailAnimationSpeed = 2.0F;

                if (this.motionY > 0.0D && this.clientSideTouchedGround && !this.isSilent()) {
                    this.worldObj.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GUARDIAN_FLOP, this.getSoundCategory(), 1.0F, 1.0F, false);
                }

                this.clientSideTouchedGround = this.motionY < 0.0D && this.worldObj.isBlockNormalCube((new BlockPos(this)).down(), false);
            } else if (this.isMoving()) {
                if (this.clientSideTailAnimationSpeed < 0.5F) {
                    this.clientSideTailAnimationSpeed = 4.0F;
                } else {
                    this.clientSideTailAnimationSpeed += (0.5F - this.clientSideTailAnimationSpeed) * 0.1F;
                }
            } else {
                this.clientSideTailAnimationSpeed += (0.125F - this.clientSideTailAnimationSpeed) * 0.2F;
            }

            this.clientSideTailAnimation += this.clientSideTailAnimationSpeed;
            this.clientSideSpikesAnimationO = this.clientSideSpikesAnimation;

            if (!this.isInWater()) {
                this.clientSideSpikesAnimation = this.rand.nextFloat();
            } else if (this.isMoving()) {
                this.clientSideSpikesAnimation += (0.0F - this.clientSideSpikesAnimation) * 0.25F;
            } else {
                this.clientSideSpikesAnimation += (1.0F - this.clientSideSpikesAnimation) * 0.06F;
            }

            if (this.isMoving() && this.isInWater()) {
                Vec3d vec3d = this.getLook(0.0F);

                for (int i = 0; i < 2; ++i) {
                    this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width - vec3d.xCoord * 1.5D, this.posY + this.rand.nextDouble() * (double) this.height - vec3d.yCoord * 1.5D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width - vec3d.zCoord * 1.5D, 0.0D, 0.0D, 0.0D, new int[0]);
                }
            }

            if (this.hasTargetedEntity()) {
                if (this.clientSideAttackTime < this.getAttackDuration()) {
                    ++this.clientSideAttackTime;
                }

                EntityLivingBase entitylivingbase = this.getTargetedEntity();

                if (entitylivingbase != null) {
                    this.getLookHelper().setLookPositionWithEntity(entitylivingbase, 90.0F, 90.0F);
                    this.getLookHelper().onUpdateLook();
                    double d5 = (double) this.getAttackAnimationScale(0.0F);
                    double d0 = entitylivingbase.posX - this.posX;
                    double d1 = entitylivingbase.posY + (double) (entitylivingbase.height * 0.5F) - (this.posY + (double) this.getEyeHeight());
                    double d2 = entitylivingbase.posZ - this.posZ;
                    double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                    d0 = d0 / d3;
                    d1 = d1 / d3;
                    d2 = d2 / d3;
                    double d4 = this.rand.nextDouble();

                    while (d4 < d3) {
                        d4 += 1.8D - d5 + this.rand.nextDouble() * (1.7D - d5);
                        this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + d0 * d4, this.posY + d1 * d4 + (double) this.getEyeHeight(), this.posZ + d2 * d4, 0.0D, 0.0D, 0.0D, new int[0]);
                    }
                }
            }
        }

        if (this.inWater) {
            this.setAir(300);
        } else if (this.onGround) {
            this.motionY += 0.5D;
            this.motionX += (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
            this.motionZ += (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
            this.rotationYaw = this.rand.nextFloat() * 360.0F;
            this.onGround = false;
            this.isAirBorne = true;
        }

        if (this.hasTargetedEntity()) {
            this.rotationYaw = this.rotationYawHead;
        }

        super.onLivingUpdate();
    }

    @SideOnly(Side.CLIENT)
    public float getTailAnimation(float p_175471_1_) {
        return this.clientSideTailAnimationO + (this.clientSideTailAnimation - this.clientSideTailAnimationO) * p_175471_1_;
    }

    @SideOnly(Side.CLIENT)
    public float getSpikesAnimation(float p_175469_1_) {
        return this.clientSideSpikesAnimationO + (this.clientSideSpikesAnimation - this.clientSideSpikesAnimationO) * p_175469_1_;
    }

    public float getAttackAnimationScale(float p_175477_1_) {
        return ((float) this.clientSideAttackTime + p_175477_1_) / (float) this.getAttackDuration();
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return LOOT;
    }

    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean isValidLightLevel() {
        return true;
    }

    /**
     * Checks that the entity is not colliding with any blocks / liquids
     */
    public boolean isNotColliding() {
        return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this) && this.worldObj.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty();
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere() {
        return this.posY > 45.0D && this.posY < (double)this.worldObj.getSeaLevel() && super.getCanSpawnHere();
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (!this.isMoving() && !source.isMagicDamage() && source.getSourceOfDamage() instanceof EntityLivingBase) {
            EntityLivingBase entitylivingbase = (EntityLivingBase) source.getSourceOfDamage();

            if (!source.isExplosion()) {
                entitylivingbase.attackEntityFrom(DamageSource.causeThornsDamage(this), 2.0F);
            }
        }

        if (this.wander != null) {
            this.wander.makeUpdate();
        }

        return super.attackEntityFrom(source, amount);
    }

    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    public int getVerticalFaceSpeed() {
        return 180;
    }

    /**
     * Moves the entity based on the specified heading.
     */
    public void moveEntityWithHeading(float strafe, float forward) {
        if (this.isServerWorld()) {
            if (this.isInWater()) {
                this.moveRelative(strafe, forward, 0.1F);
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                this.motionX *= 0.8999999761581421D;
                this.motionY *= 0.8999999761581421D;
                this.motionZ *= 0.8999999761581421D;

                if (!this.isMoving() && this.getAttackTarget() == null) {
                    this.motionY -= 0.005D;
                }
            } else {
                super.moveEntityWithHeading(strafe, forward);
            }
        } else {
            super.moveEntityWithHeading(strafe, forward);
        }
    }

    static class AIGuardianAttack extends EntityAIBase {
        private final EntityWaterCreep theEntity;
        private int tickCounter;

        public AIGuardianAttack(EntityWaterCreep guardian) {
            this.theEntity = guardian;
            this.setMutexBits(3);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            EntityLivingBase entitylivingbase = this.theEntity.getAttackTarget();
            return entitylivingbase != null && entitylivingbase.isEntityAlive();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean continueExecuting() {
            return super.continueExecuting() && (this.theEntity.getDistanceSqToEntity(this.theEntity.getAttackTarget()) > 9.0D);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.tickCounter = -10;
            this.theEntity.getNavigator().clearPathEntity();
            this.theEntity.getLookHelper().setLookPositionWithEntity(this.theEntity.getAttackTarget(), 90.0F, 90.0F);
            this.theEntity.isAirBorne = true;
        }

        /**
         * Resets the task
         */
        public void resetTask() {
            this.theEntity.setTargetedEntity(0);
            this.theEntity.setAttackTarget((EntityLivingBase) null);
            this.theEntity.wander.makeUpdate();
        }

        /**
         * Updates the task
         */
        public void updateTask() {
            EntityLivingBase entitylivingbase = this.theEntity.getAttackTarget();
            this.theEntity.getNavigator().clearPathEntity();
            this.theEntity.getLookHelper().setLookPositionWithEntity(entitylivingbase, 90.0F, 90.0F);

            if (!this.theEntity.canEntityBeSeen(entitylivingbase)) {
                this.theEntity.setAttackTarget((EntityLivingBase) null);
            } else {
                ++this.tickCounter;

                if (this.tickCounter == 0) {
                    this.theEntity.setTargetedEntity(this.theEntity.getAttackTarget().getEntityId());
                    this.theEntity.worldObj.setEntityState(this.theEntity, (byte) 21);
                } else if (this.tickCounter >= this.theEntity.getAttackDuration()) {
                    float f = 1.0F;

                    if (this.theEntity.worldObj.getDifficulty() == EnumDifficulty.HARD) {
                        f += 2.0F;
                    }

                    entitylivingbase.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this.theEntity, this.theEntity), f);
                    entitylivingbase.attackEntityFrom(DamageSource.causeMobDamage(this.theEntity), (float) this.theEntity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
                    this.theEntity.setAttackTarget(null);
                }

                super.updateTask();
            }
        }
    }

    static class WaterCreepMoveHelper extends EntityMoveHelper {
        private final EntityWaterCreep entityWaterCreep;

        public WaterCreepMoveHelper(EntityWaterCreep guardian) {
            super(guardian);
            this.entityWaterCreep = guardian;
        }

        public void onUpdateMoveHelper() {
            if (this.action == Action.MOVE_TO && !this.entityWaterCreep.getNavigator().noPath()) {
                double d0 = this.posX - this.entityWaterCreep.posX;
                double d1 = this.posY - this.entityWaterCreep.posY;
                double d2 = this.posZ - this.entityWaterCreep.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                d3 = (double) MathHelper.sqrt_double(d3);
                d1 = d1 / d3;
                float f = (float) (MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
                this.entityWaterCreep.rotationYaw = this.limitAngle(this.entityWaterCreep.rotationYaw, f, 90.0F);
                this.entityWaterCreep.renderYawOffset = this.entityWaterCreep.rotationYaw;
                float f1 = (float) (this.speed * this.entityWaterCreep.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
                this.entityWaterCreep.setAIMoveSpeed(this.entityWaterCreep.getAIMoveSpeed() + (f1 - this.entityWaterCreep.getAIMoveSpeed()) * 0.125F);
                double d4 = Math.sin((double) (this.entityWaterCreep.ticksExisted + this.entityWaterCreep.getEntityId()) * 0.5D) * 0.05D;
                double d5 = Math.cos((double) (this.entityWaterCreep.rotationYaw * 0.017453292F));
                double d6 = Math.sin((double) (this.entityWaterCreep.rotationYaw * 0.017453292F));
                this.entityWaterCreep.motionX += d4 * d5;
                this.entityWaterCreep.motionZ += d4 * d6;
                d4 = Math.sin((double) (this.entityWaterCreep.ticksExisted + this.entityWaterCreep.getEntityId()) * 0.75D) * 0.05D;
                this.entityWaterCreep.motionY += d4 * (d6 + d5) * 0.25D;
                this.entityWaterCreep.motionY += (double) this.entityWaterCreep.getAIMoveSpeed() * d1 * 0.1D;
                EntityLookHelper entitylookhelper = this.entityWaterCreep.getLookHelper();
                double d7 = this.entityWaterCreep.posX + d0 / d3 * 2.0D;
                double d8 = (double) this.entityWaterCreep.getEyeHeight() + this.entityWaterCreep.posY + d1 / d3;
                double d9 = this.entityWaterCreep.posZ + d2 / d3 * 2.0D;
                double d10 = entitylookhelper.getLookPosX();
                double d11 = entitylookhelper.getLookPosY();
                double d12 = entitylookhelper.getLookPosZ();

                if (!entitylookhelper.getIsLooking()) {
                    d10 = d7;
                    d11 = d8;
                    d12 = d9;
                }

                this.entityWaterCreep.getLookHelper().setLookPosition(d10 + (d7 - d10) * 0.125D, d11 + (d8 - d11) * 0.125D, d12 + (d9 - d12) * 0.125D, 10.0F, 40.0F);
                this.entityWaterCreep.setMoving(true);
            } else {
                this.entityWaterCreep.setAIMoveSpeed(0.0F);
                this.entityWaterCreep.setMoving(false);
            }
        }
    }

    static class WaterCreepTargetSelector implements Predicate<EntityLivingBase> {
        private final EntityWaterCreep parentEntity;

        public WaterCreepTargetSelector(EntityWaterCreep creep) {
            this.parentEntity = creep;
        }

        public boolean apply(@Nullable EntityLivingBase p_apply_1_) {
            return (p_apply_1_ instanceof EntityPlayer) && p_apply_1_.getDistanceSqToEntity(this.parentEntity) > 9.0D;
        }
    }
}