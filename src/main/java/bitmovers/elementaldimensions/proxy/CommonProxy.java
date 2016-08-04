package bitmovers.elementaldimensions.proxy;

import bitmovers.elementaldimensions.init.DimensionRegister;
import bitmovers.elementaldimensions.items.ModItems;
import bitmovers.elementaldimensions.mobs.ModEntities;
import bitmovers.elementaldimensions.mobs.SpawnEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        DimensionRegister.preInit();
        ModEntities.preInit();
        ModItems.preInit();
    }

    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(new SpawnEventHandler());
    }

    public void postInit(FMLPostInitializationEvent e) {
    }
}
