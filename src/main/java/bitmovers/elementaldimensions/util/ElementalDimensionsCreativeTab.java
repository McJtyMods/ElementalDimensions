package bitmovers.elementaldimensions.util;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.init.ItemRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;

/**
 * Created by Elec332 on 4-8-2016.
 */
public class ElementalDimensionsCreativeTab extends CreativeTabs {

    public ElementalDimensionsCreativeTab() {
        super(ElementalDimensions.MODID);
    }

    @Override
    @Nonnull
    public Item getTabIconItem() {
        return ItemRegister.elementalWand;
    }

}
