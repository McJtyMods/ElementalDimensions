package bitmovers.elementaldimensions;

import bitmovers.elementaldimensions.ncLayer.NCLayerMain;
import bitmovers.elementaldimensions.setup.CommonSetup;
import elec332.core.api.mod.IElecCoreMod;
import elec332.core.api.network.ModNetworkHandler;
import elec332.core.network.IElecNetworkHandler;
import mcjty.lib.base.ModBase;
import mcjty.lib.proxy.IProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import static bitmovers.elementaldimensions.ElementalDimensions.*;

@Mod(modid = MODID, name = MODNAME,
        version = VERSION,
        dependencies =
                "required-after:mcjtylib_ng@[" + ElementalDimensions.MIN_MCJTYLIB_VER + ",);" +
                "required-after:eleccore@[" + MIN_ELECCORE + ",);" +
                "after:forge@[" + ElementalDimensions.MIN_FORGE11_VER + ",)",
        acceptedMinecraftVersions = "[1.12,1.13)")
public class ElementalDimensions implements ModBase, IElecCoreMod {

    public static final String MODID = "elementaldimensions";
    public static final String MODNAME = "Elemental Dimensions";
    public static final String VERSION = "0.3.4";
    public static final String MIN_MCJTYLIB_VER = "3.1.0";

    public static final String MIN_ELECCORE = "1.9.451";
    public static final String MIN_FORGE11_VER = "13.19.0.2176";

    @SidedProxy(clientSide="bitmovers.elementaldimensions.setup.ClientProxy", serverSide="bitmovers.elementaldimensions.setup.ServerProxy")
    public static IProxy proxy;
    public static CommonSetup setup = new CommonSetup();

    @Mod.Instance(MODID)
    public static ElementalDimensions instance;

    @ModNetworkHandler
    public static IElecNetworkHandler networkHandler;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        setup.preInit(event);
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        setup.init(event);
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        setup.postInit(event);
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        NCLayerMain.instance.serverStarting(event);
    }

    @Override
    public String getModId() {
        return MODID;
    }

    @Override
    public void openManual(EntityPlayer player, int bookindex, String page) {
        // @todo
    }
}
