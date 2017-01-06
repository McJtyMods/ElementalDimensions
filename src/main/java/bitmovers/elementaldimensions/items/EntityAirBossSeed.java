package bitmovers.elementaldimensions.items;

import bitmovers.elementaldimensions.dimensions.Dimensions;
import bitmovers.elementaldimensions.mobs.EntityGhostBoss;
import elec332.core.world.WorldHelper;
import mcjty.lib.tools.WorldTools;
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

        if (!getEntityWorld().isRemote && getEntityWorld().provider.getDimension() == Dimensions.AIR.getDimensionID()) {
            countdown--;

            if (countdown == 50) {
                getEntityWorld().createExplosion(this, posX, posY, posZ, 8, true);
            }

            if (countdown <= 0) {
                setDead();

                EntityGhostBoss boss = new EntityGhostBoss(getEntityWorld());
                boss.setPosition(posX, posY, posZ);
                WorldHelper.spawnEntityInWorld(getEntityWorld(), boss);
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
