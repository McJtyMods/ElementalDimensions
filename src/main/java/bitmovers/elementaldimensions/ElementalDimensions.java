package bitmovers.elementaldimensions;

import bitmovers.elementaldimensions.commands.CommandTeleport;
import bitmovers.elementaldimensions.compat.MainCompatHandler;
import bitmovers.elementaldimensions.init.ItemRegister;
import bitmovers.elementaldimensions.ncLayer.NCLayerMain;
import bitmovers.elementaldimensions.network.PacketPlayerConnect;
import bitmovers.elementaldimensions.network.PacketPointedEntity;
import bitmovers.elementaldimensions.proxy.CommonProxy;
import bitmovers.elementaldimensions.util.Config;
import bitmovers.elementaldimensions.util.command.ElementalDimensionsCommand;
import bitmovers.elementaldimensions.util.command.IElementalDimensionsSubCommand;
import elec332.core.api.network.ModNetworkHandler;
import elec332.core.config.ConfigWrapper;
import elec332.core.network.IElecNetworkHandler;
import elec332.core.util.LoadTimer;
import mcjty.lib.McJtyLib;
import mcjty.lib.base.ModBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

import static bitmovers.elementaldimensions.ElementalDimensions.*;

@Mod(modid = MODID, name = MODNAME,
        version = VERSION,
        dependencies =
                "required-after:mcjtylib_ng@[" + ElementalDimensions.MIN_MCJTYLIB_VER + ",);" +
                "required-after:eleccore@[" + MIN_ELECCORE + ",);" +
                "after:forge@[" + ElementalDimensions.MIN_FORGE11_VER + ",)",
        acceptedMinecraftVersions = "[1.12,1.13)")
public class ElementalDimensions implements ModBase {

    public static final String MODID = "elementaldimensions";
    public static final String MODNAME = "Elemental Dimensions";
    public static final String VERSION = "0.3.1";
    public static final String MIN_MCJTYLIB_VER = "3.0.0";

    public static final String MIN_ELECCORE = "1.7.409";
    public static final String MIN_FORGE11_VER = "13.19.0.2176";

    @SidedProxy(clientSide="bitmovers.elementaldimensions.proxy.ClientProxy", serverSide="bitmovers.elementaldimensions.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance(MODID)
    public static ElementalDimensions instance;
    @ModNetworkHandler
    public static IElecNetworkHandler networkHandler;
    public static Logger logger;
    private static LoadTimer loadTimer;
    public static ConfigWrapper config;
    public static CreativeTabs creativeTab;
    public static Random random;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        McJtyLib.preInit(event);
        logger = LogManager.getLogger(MODNAME.replace(" ", ""));
        loadTimer = new LoadTimer(logger, MODNAME);
        loadTimer.startPhase(event);
        networkHandler.registerClientPacket(PacketPlayerConnect.class);
        networkHandler.registerServerPacket(PacketPointedEntity.class);
        config = new ConfigWrapper(new Configuration(event.getSuggestedConfigurationFile())); //We'll move it later
        creativeTab = new CreativeTabs(ElementalDimensions.MODID) {
            @Override
            public ItemStack getTabIconItem() {
                return new ItemStack(ItemRegister.elementalWand);
            }
        };
        random = new Random();

        config.registerConfigWithInnerClasses(new Config());
        config.refresh();
        NCLayerMain.instance.preInit(event);
        proxy.preInit(event);

        MainCompatHandler.registerWaila();
        MainCompatHandler.registerTOP();

        loadTimer.endPhase(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        loadTimer.startPhase(event);
        NCLayerMain.instance.init(event);
        proxy.init(event);
        loadTimer.endPhase(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        loadTimer.startPhase(event);
        NCLayerMain.instance.postInit(event);
        registerCommand(new CommandTeleport());
        proxy.postInit(event);
        loadTimer.endPhase(event);
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        NCLayerMain.instance.serverStarting(event);
    }

    public static void registerCommand(IElementalDimensionsSubCommand command){
        ElementalDimensionsCommand.registerSubCommand(command);
    }

    public static void registerLoginHandler(String name, INBTSerializable<?> handler){
        PacketPlayerConnect.registerLoginHandler(name, handler);
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
