package bitmovers.elementaldimensions.proxy;

import bitmovers.elementaldimensions.blocks.ModBlocks;
import bitmovers.elementaldimensions.init.DimensionRegister;
import bitmovers.elementaldimensions.items.ModItems;
import bitmovers.elementaldimensions.init.EntityRegister;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        DimensionRegister.preInit();
        EntityRegister.preInit();
        ModItems.preInit();
        ModBlocks.preInit();
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {
    }
}
