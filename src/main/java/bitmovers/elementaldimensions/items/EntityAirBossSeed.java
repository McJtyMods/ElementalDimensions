package bitmovers.elementaldimensions.items;

import bitmovers.elementaldimensions.dimensions.Dimensions;
import bitmovers.elementaldimensions.mobs.EntityGhostBoss;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityAirBossSeed extends EntityItem {

    private int countdown = 200;

    public EntityAirBossSeed(World worldIn) {
        super(worldIn);
        setNoGravity(true);
        setSize(3, 3);
    }

    public EntityAirBossSeed(World worldIn, double x, double y, double z, ItemStack stack) {
        super(worldIn, x, y, z, stack);
        setNoGravity(true);
        setSize(3, 3);
    }

    @Override
    public boolean isImmuneToExplosions() {
        return true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!worldObj.isRemote && worldObj.provider.getDimension() == Dimensions.AIR.getDimensionID()) {
            countdown--;

            if (countdown == 50) {
                worldObj.createExplosion(this, posX, posY, posZ, 8, true);
            }

            if (countdown <= 0) {
                setDead();

                EntityGhostBoss boss = new EntityGhostBoss(worldObj);
                boss.setPosition(posX, posY, posZ);
                worldObj.spawnEntityInWorld(boss);
            }
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        countdown = compound.getInteger("countdown");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("countdown", countdown);
    }
}
