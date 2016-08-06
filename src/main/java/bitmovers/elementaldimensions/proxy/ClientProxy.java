package bitmovers.elementaldimensions.proxy;

import bitmovers.elementaldimensions.blocks.GenericBlock;
import bitmovers.elementaldimensions.mobs.EntityDirtZombie;
import bitmovers.elementaldimensions.mobs.EntityGuard;
import bitmovers.elementaldimensions.mobs.RenderDirtZombie;
import bitmovers.elementaldimensions.mobs.RenderGuard;
import bitmovers.elementaldimensions.ncLayer.overworldTweaks.client.ClientBlockHandler;
import elec332.core.util.RegistryHelper;
import net.minecraft.block.Block;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        registerEntityRenderers();
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        elec332.core.client.model.RenderingRegistry.instance().registerLoader(ClientBlockHandler.INSTANCE.setFields());
        for (Block block : RegistryHelper.getBlockRegistry().getValues()){
            if (block instanceof GenericBlock){
                ((GenericBlock) block).initClient();
            }
        }
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
        registerTESRs();
    }

    private void registerEntityRenderers(){
        RenderingRegistry.registerEntityRenderingHandler(EntityDirtZombie.class, RenderDirtZombie.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityGuard.class, RenderGuard.FACTORY);
    }

    private void registerTESRs(){

    }

}
