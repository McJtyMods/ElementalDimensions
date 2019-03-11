package bitmovers.elementaldimensions.items;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.varia.DelayedRegister;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GenericItem extends Item {

    public GenericItem(String name) {
        setUnlocalizedName(ElementalDimensions.MODID + "." + name);
        setRegistryName(name);
        setCreativeTab(ElementalDimensions.setup.getTab());
        setMaxStackSize(1);
        DelayedRegister.registerLater(this);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

}
