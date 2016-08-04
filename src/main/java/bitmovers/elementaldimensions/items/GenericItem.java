package bitmovers.elementaldimensions.items;

import bitmovers.elementaldimensions.ElementalDimensions;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GenericItem extends Item {

    public GenericItem(String name) {
        setUnlocalizedName(ElementalDimensions.MODID + "." + name);
        setRegistryName(name);
        setCreativeTab(ElementalDimensions.creativeTab);
        GameRegistry.register(this);
        setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

}
