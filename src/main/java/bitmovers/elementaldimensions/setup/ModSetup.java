package bitmovers.elementaldimensions.setup;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.commands.CommandTeleport;
import bitmovers.elementaldimensions.dimensions.DimensionEvents;
import bitmovers.elementaldimensions.init.BlockRegister;
import bitmovers.elementaldimensions.init.DimensionRegister;
import bitmovers.elementaldimensions.init.EntityRegister;
import bitmovers.elementaldimensions.init.ItemRegister;
import bitmovers.elementaldimensions.ncLayer.NCLayerMain;
import bitmovers.elementaldimensions.network.PacketPlayerConnect;
import bitmovers.elementaldimensions.network.PacketPointedEntity;
import bitmovers.elementaldimensions.util.Config;
import bitmovers.elementaldimensions.util.command.ElementalDimensionsCommand;
import bitmovers.elementaldimensions.util.command.IElementalDimensionsSubCommand;
import elec332.core.config.ConfigWrapper;
import mcjty.lib.compat.MainCompatHandler;
import mcjty.lib.setup.DefaultModSetup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.util.Random;

public class ModSetup extends DefaultModSetup {

    public static Random random;
    public static ConfigWrapper config;

    public static void registerCommand(IElementalDimensionsSubCommand command){
        ElementalDimensionsCommand.registerSubCommand(command);
    }

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);

        MinecraftForge.EVENT_BUS.register(new DimensionEvents());

        ElementalDimensions.networkHandler.registerClientPacket(PacketPlayerConnect.class);
        ElementalDimensions.networkHandler.registerServerPacket(PacketPointedEntity.class);
        random = new Random();
        NCLayerMain.instance.preInit(e);
        BlockRegister.init();
        ItemRegister.init();
        DimensionRegister.init();
        EntityRegister.init();
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        NCLayerMain.instance.init(e);
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
        NCLayerMain.instance.postInit(e);
        registerCommand(new CommandTeleport());
    }

    @Override
    protected void setupModCompat() {
        MainCompatHandler.registerWaila();
        MainCompatHandler.registerTOP();
    }

    @Override
    protected void setupConfig() {
        config = new ConfigWrapper(new Configuration(new File(getModConfigDir().getPath(), "elementaldimensions.cfg"))); //We'll move it later
        config.registerConfigWithInnerClasses(new Config());
        config.refresh();
    }

    @Override
    public void createTabs() {
        createTab(ElementalDimensions.MODID, new ItemStack(ItemRegister.elementalWand));
    }
}
