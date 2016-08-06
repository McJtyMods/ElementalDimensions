package bitmovers.elementaldimensions.items;

import bitmovers.elementaldimensions.dimensions.Dimensions;
import net.minecraft.item.ItemStack;

public class ItemSpiritRune extends ItemRune {

    public ItemSpiritRune() {
        super("spiritrune");
    }

    @Override
    public Dimensions getDimension(ItemStack stack) {
        return Dimensions.SPIRIT;
    }

}
