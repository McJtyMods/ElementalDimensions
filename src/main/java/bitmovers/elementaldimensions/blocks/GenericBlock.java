package bitmovers.elementaldimensions.blocks;

import bitmovers.elementaldimensions.ElementalDimensions;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GenericBlock extends Block {

    public GenericBlock(String name, Material materialIn) {
        super(materialIn);
        setRegistryName(name);
        setUnlocalizedName(ElementalDimensions.MODID + "." + name);
        GameRegistry.register(this.setCreativeTab(ElementalDimensions.creativeTab));
        GameRegistry.register((new ItemBlock(this)).setCreativeTab(ElementalDimensions.creativeTab), getRegistryName());
    }

}
