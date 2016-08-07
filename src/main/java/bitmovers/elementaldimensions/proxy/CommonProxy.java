package bitmovers.elementaldimensions.proxy;

import bitmovers.elementaldimensions.dimensions.DimensionTicker;
import bitmovers.elementaldimensions.init.BlockRegister;
import bitmovers.elementaldimensions.init.DimensionRegister;
import bitmovers.elementaldimensions.init.EntityRegister;
import bitmovers.elementaldimensions.init.ItemRegister;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        BlockRegister.init();
        ItemRegister.init();
        DimensionRegister.init();
        EntityRegister.init();
    }

    public void init(FMLInitializationEvent e) {
        ItemRegister.initCrafting();
        MinecraftForge.EVENT_BUS.register(new DimensionTicker());
    }

    public void postInit(FMLPostInitializationEvent e) {
    }

}
