package bitmovers.elementaldimensions;

import bitmovers.elementaldimensions.commands.CommandTeleport;
import bitmovers.elementaldimensions.init.BlockRegister;
import bitmovers.elementaldimensions.init.DimensionRegister;
import bitmovers.elementaldimensions.init.EntityRegister;
import bitmovers.elementaldimensions.init.ItemRegister;
import bitmovers.elementaldimensions.ncLayer.NCLayerMain;
import bitmovers.elementaldimensions.network.PacketPlayerConnect;
import bitmovers.elementaldimensions.network.PacketPointedEntity;
import bitmovers.elementaldimensions.proxy.CommonProxy;
import bitmovers.elementaldimensions.util.Config;
import bitmovers.elementaldimensions.util.ElementalDimensionsCreativeTab;
import bitmovers.elementaldimensions.util.command.ElementalDimensionsCommand;
import bitmovers.elementaldimensions.util.command.IElementalDimensionsSubCommand;
import elec332.core.config.ConfigWrapper;
import elec332.core.util.LoadTimer;
import net.minecraft.creativetab.CreativeTabs;
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

@Mod(modid = MODID, name = MODNAME, version = VERSION)
public class ElementalDimensions {

    public static final String MODID = "elementaldimensions";
    public static final String MODNAME = "Elemental Dimensions";
    public static final String VERSION = "0.0.3";

    @SidedProxy(clientSide="bitmovers.elementaldimensions.proxy.ClientProxy", serverSide="bitmovers.elementaldimensions.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance(MODID)
    public static ElementalDimensions instance;
    public static Logger logger;
    private static LoadTimer loadTimer;
    public static NetworkHandler networkHandler;
    public static ConfigWrapper config;
    public static CreativeTabs creativeTab;
    public static Random random;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = LogManager.getLogger(MODNAME.replace(" ", ""));
        loadTimer = new LoadTimer(logger, MODNAME);
        loadTimer.startPhase(event);
        networkHandler = new NetworkHandler(MODID);
        networkHandler.registerClientPacket(PacketPlayerConnect.class);
        networkHandler.registerServerPacket(PacketPointedEntity.class);
        config = new ConfigWrapper(new Configuration(event.getSuggestedConfigurationFile())); //We'll move it later
        creativeTab = new ElementalDimensionsCreativeTab();
        random = new Random();
        config.registerConfigWithInnerClasses(new Config());
        config.refresh();
        NCLayerMain.instance.preInit(event);
        proxy.preInit(event);

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

}
