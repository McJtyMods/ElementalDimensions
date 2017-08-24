package bitmovers.elementaldimensions.blocks;

import bitmovers.elementaldimensions.DelayedRegister;
import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.compat.top.TOPInfoProvider;
import elec332.core.tile.AbstractBlock;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class GenericBlock extends AbstractBlock implements TOPInfoProvider {

    public GenericBlock(String name, Material materialIn) {
        this(name, materialIn, ItemBlock.class);
    }

    public GenericBlock(String name, Material materialIn, Class<? extends ItemBlock> itemBlockClass) {
        super(materialIn);
        setRegistryName(name);
        setUnlocalizedName(ElementalDimensions.MODID + "." + name);
        setCreativeTab(ElementalDimensions.creativeTab);
        DelayedRegister.registerLater(this, itemBlockClass, null);
    }

    @SideOnly(Side.CLIENT)
    public void initClient(){
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
