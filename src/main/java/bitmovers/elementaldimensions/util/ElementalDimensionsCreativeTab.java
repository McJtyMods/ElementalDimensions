package bitmovers.elementaldimensions.util;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.init.ItemRegister;
import elec332.core.util.AbstractCreativeTab;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Created by Elec332 on 4-8-2016.
 */
public class ElementalDimensionsCreativeTab extends AbstractCreativeTab {

    public ElementalDimensionsCreativeTab() {
        super(ElementalDimensions.MODID);
    }

    @Nonnull
    @Override
    protected ItemStack getDisplayStack() {
        return new ItemStack(ItemRegister.elementalWand);
    }

}
