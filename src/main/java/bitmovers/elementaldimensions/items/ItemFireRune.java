package bitmovers.elementaldimensions.items;

import bitmovers.elementaldimensions.dimensions.Dimensions;
import net.minecraft.item.ItemStack;

public class ItemFireRune extends ItemRune {

    public ItemFireRune() {
        super("firerune");
    }

    @Override
    public Dimensions getDimension(ItemStack stack) {
        return Dimensions.FIRE;
    }


}
