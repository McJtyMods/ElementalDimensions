package bitmovers.elementaldimensions.blocks;

import bitmovers.elementaldimensions.ElementalDimensions;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opencl.CL;

public class GenericBlock extends Block {

    public GenericBlock(String name, Material materialIn) {
        super(materialIn);
        setRegistryName(name);
        setUnlocalizedName(ElementalDimensions.MODID + "." + name);
        setCreativeTab(ElementalDimensions.creativeTab);
        GameRegistry.register(this);
        GameRegistry.register((new ItemBlock(this)), getRegistryName());
    }

    @SideOnly(Side.CLIENT)
    public void initClient(){
    }

}
