package bitmovers.elementaldimensions.mobs;

import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.monster.EntityZombie;

public class EntityAIDirtZombieAttack extends EntityAIAttackMelee {
    private final EntityDirtZombie zombie;
    private int raiseArmTicks;

    public EntityAIDirtZombieAttack(EntityDirtZombie zombieIn, double speedIn, boolean longMemoryIn) {
        super(zombieIn, speedIn, longMemoryIn);
        this.zombie = zombieIn;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        super.startExecuting();
        this.raiseArmTicks = 0;
    }

    /**
     * Resets the task
     */
    @Override
    public void resetTask() {
        super.resetTask();
        this.zombie.setArmsRaised(false);
    }

    /**
     * Updates the task
     */
    public void updateTask() {
        super.updateTask();
        ++this.raiseArmTicks;

        if (this.raiseArmTicks >= 5 && this.attackTick < 10) {
            this.zombie.setArmsRaised(true);
        } else {
            this.zombie.setArmsRaised(false);
        }
    }
}