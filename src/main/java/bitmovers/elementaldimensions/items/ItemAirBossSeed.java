package bitmovers.elementaldimensions.items;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAirBossSeed extends GenericItem {

    public ItemAirBossSeed() {
        super("airbossseed");
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Override
    public Entity createEntity(World world, Entity old, ItemStack itemstack) {
        EntityAirBossSeed seed = new EntityAirBossSeed(world, old.posX, old.posY, old.posZ, itemstack);
        seed.setPickupDelay(40);
        seed.motionX = old.motionX;
        seed.motionY = old.motionY;
        seed.motionZ = old.motionZ;
        return seed;
    }
}
