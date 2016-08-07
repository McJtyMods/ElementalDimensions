package bitmovers.elementaldimensions.items;

import bitmovers.elementaldimensions.dimensions.Dimensions;
import bitmovers.elementaldimensions.mobs.EntityDirtZombieBoss;
import bitmovers.elementaldimensions.mobs.EntityWaterCreepBoss;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityWaterBossSeed extends EntityItem {

    private int countdown = 100;

    public EntityWaterBossSeed(World worldIn) {
        super(worldIn);
    }

    public EntityWaterBossSeed(World worldIn, double x, double y, double z, ItemStack stack) {
        super(worldIn, x, y, z, stack);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!worldObj.isRemote && worldObj.provider.getDimension() == Dimensions.WATER.getDimensionID()) {
            Block block = worldObj.getBlockState(getPosition()).getBlock();
            if (block == Blocks.WATER) {
                countdown--;
                if (countdown <= 0) {
                    setDead();
                    EntityWaterCreepBoss boss = new EntityWaterCreepBoss(worldObj);
                    boss.setPosition(posX, posY, posZ);
                    worldObj.spawnEntityInWorld(boss);
                }
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
