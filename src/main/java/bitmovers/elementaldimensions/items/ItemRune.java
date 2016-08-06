package bitmovers.elementaldimensions.items;

import bitmovers.elementaldimensions.dimensions.Dimensions;
import net.minecraft.item.ItemStack;

/**
 * Created by Elec332 on 6-8-2016.
 */
public abstract class ItemRune extends GenericItem {

    public ItemRune(String s) {
        super(s);
    }

    public abstract Dimensions getDimension(ItemStack stack);

}
