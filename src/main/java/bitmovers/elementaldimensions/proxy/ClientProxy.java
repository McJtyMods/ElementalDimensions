package bitmovers.elementaldimensions.proxy;

import bitmovers.elementaldimensions.items.ModItems;
import bitmovers.elementaldimensions.mobs.EntityDirtZombie;
import bitmovers.elementaldimensions.mobs.RenderDirtZombie;
import bitmovers.elementaldimensions.ncLayer.overworldTweaks.client.ClientBlockHandler;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        RenderingRegistry.registerEntityRenderingHandler(EntityDirtZombie.class, RenderDirtZombie.FACTORY);
        ModItems.initClient();
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        elec332.core.client.model.RenderingRegistry.instance().registerLoader(ClientBlockHandler.INSTANCE.setFields());
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }
}
