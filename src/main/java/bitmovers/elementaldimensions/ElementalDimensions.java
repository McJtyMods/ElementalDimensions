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
import elec332.core.util.AbstractCreativeTab;
import elec332.core.util.LoadTimer;
import net.minecraft.creativetab.CreativeTabs;
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
                "required-after:eleccore@[" + MIN_ELECCORE + ",);" +
                "required-after:compatlayer@[" + ElementalDimensions.COMPATLAYER_VER + ",);" +
                "after:Forge@[" + ElementalDimensions.MIN_FORGE10_VER + ",);" +
                "after:forge@[" + ElementalDimensions.MIN_FORGE11_VER + ",)",
        acceptedMinecraftVersions = "[1.10,1.12)")
public class ElementalDimensions {

    public static final String MODID = "elementaldimensions";
    public static final String MODNAME = "Elemental Dimensions";
    public static final String VERSION = "0.1.0";

    public static final String MIN_ELECCORE = "1.6.358";
    public static final String MIN_FORGE10_VER = "12.18.1.2082";
    public static final String MIN_FORGE11_VER = "13.19.0.2176";
    public static final String COMPATLAYER_VER = "0.1.0";

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
        logger = LogManager.getLogger(MODNAME.replace(" ", ""));
        loadTimer = new LoadTimer(logger, MODNAME);
        loadTimer.startPhase(event);
        networkHandler.registerClientPacket(PacketPlayerConnect.class);
        networkHandler.registerServerPacket(PacketPointedEntity.class);
        config = new ConfigWrapper(new Configuration(event.getSuggestedConfigurationFile())); //We'll move it later
        creativeTab = AbstractCreativeTab.create(ElementalDimensions.MODID, new ItemStack(ItemRegister.elementalWand));
        System.out.println(creativeTab);
        if (creativeTab == null){
            throw new RuntimeException();
        }
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

}
