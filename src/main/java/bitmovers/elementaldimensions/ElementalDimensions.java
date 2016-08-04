package bitmovers.elementaldimensions;

import bitmovers.elementaldimensions.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static bitmovers.elementaldimensions.ElementalDimensions.*;

@Mod(modid = MODID, name = MODNAME, version = VERSION)
public class ElementalDimensions {

    public static final String MODID = "elementaldimensions";
    public static final String MODNAME = "Elemental Dimensions";
    public static final String VERSION = "0.0.1";

    @Mod.Instance(MODID)
    public static ElementalDimensions instance;

    @SidedProxy(clientSide="bitmovers.elementaldimensions.proxy.ClientProxy", serverSide="bitmovers.elementaldimensions.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        proxy.preInit(e);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        this.proxy.postInit(e);
    }

}
