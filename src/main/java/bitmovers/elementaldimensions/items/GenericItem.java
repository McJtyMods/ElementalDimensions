package bitmovers.elementaldimensions.items;

import bitmovers.elementaldimensions.ElementalDimensions;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GenericItem extends Item {

    public GenericItem(String name) {
        setUnlocalizedName(ElementalDimensions.MODID + "." + name);
        setRegistryName(name);
        setCreativeTab(ElementalDimensions.creativeTab);
        GameRegistry.register(this);
        setMaxStackSize(1);
    }

}
