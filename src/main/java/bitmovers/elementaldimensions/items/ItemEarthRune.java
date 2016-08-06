package bitmovers.elementaldimensions.items;

import bitmovers.elementaldimensions.dimensions.Dimensions;
import net.minecraft.item.ItemStack;

/**
 * Created by Elec332 on 6-8-2016.
 */
public class ItemEarthRune extends ItemRune {

    public ItemEarthRune() {
        super("earthrune");
    }

    @Override
    public Dimensions getDimension(ItemStack stack) {
        return Dimensions.EARTH;
    }

}
