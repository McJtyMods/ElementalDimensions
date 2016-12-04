package bitmovers.elementaldimensions.items;

import bitmovers.elementaldimensions.ElementalDimensions;
import elec332.core.item.*;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GenericItem extends elec332.core.item.AbstractItem {

    public GenericItem(String name) {
        setUnlocalizedName(ElementalDimensions.MODID + "." + name);
        setRegistryName(name);
        setCreativeTab(ElementalDimensions.creativeTab);
        GameRegistry.register(this);
        setMaxStackSize(1);
    }

}
