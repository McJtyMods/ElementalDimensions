package bitmovers.elementaldimensions.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class ItemFocus extends GenericItem {

    public ItemFocus(String name) {
        super(name);
    }

    public abstract void execute(ItemStack stack, World world, EntityPlayer player);

}
