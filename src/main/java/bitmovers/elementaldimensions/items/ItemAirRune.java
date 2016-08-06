package bitmovers.elementaldimensions.items;

import bitmovers.elementaldimensions.dimensions.Dimensions;
import net.minecraft.item.ItemStack;

public class ItemAirRune extends ItemRune {

    public ItemAirRune() {
        super("airrune");
    }

    @Override
    public Dimensions getDimension(ItemStack stack) {
        return Dimensions.AIR;
    }

}
