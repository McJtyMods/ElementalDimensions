package bitmovers.elementaldimensions.blocks;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.varia.DelayedRegister;
import mcjty.lib.compat.theoneprobe.TOPInfoProvider;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class GenericBlock extends Block implements TOPInfoProvider {

    public GenericBlock(String name, Material materialIn) {
        this(name, materialIn, ItemBlock.class);
    }

    public GenericBlock(String name, Material materialIn, Class<? extends ItemBlock> itemBlockClass) {
        super(materialIn);
        setRegistryName(name);
        setUnlocalizedName(ElementalDimensions.MODID + "." + name);
        setCreativeTab(ElementalDimensions.setup.getTab());
        DelayedRegister.registerLater(this, itemBlockClass, null);
    }

    @SideOnly(Side.CLIENT)
    public void initClient(){
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {

    }

    private ItemBlock createItemBlock(Class<? extends ItemBlock> itemBlockClass) {
        try {
            Class<?>[] ctorArgClasses = new Class<?>[1];
            ctorArgClasses[0] = Block.class;
            Constructor<? extends ItemBlock> itemCtor = itemBlockClass.getConstructor(ctorArgClasses);
            return itemCtor.newInstance(this);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
