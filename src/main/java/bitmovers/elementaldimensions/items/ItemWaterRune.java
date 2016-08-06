package bitmovers.elementaldimensions.items;

import bitmovers.elementaldimensions.dimensions.Dimensions;
import net.minecraft.item.ItemStack;

public class ItemWaterRune extends ItemRune {

    public ItemWaterRune() {
        super("waterrune");
    }

    @Override
    public Dimensions getDimension(ItemStack stack) {
        return Dimensions.WATER;
    }

}
