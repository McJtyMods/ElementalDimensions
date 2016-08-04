package bitmovers.elementaldimensions.ncLayer;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.commands.CommandReloadSchematics;
import bitmovers.elementaldimensions.ncLayer.dev.SchematicCreatorItem;
import bitmovers.elementaldimensions.util.EDResourceLocation;
import bitmovers.elementaldimensions.util.command.ElementalDimensionsCommand;
import elec332.core.main.ElecCore;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.FMLInjectionData;

import java.io.File;

/**
 * Created by Elec332 on 4-8-2016.
 */
public class NCLayerMain {

    public static NCLayerMain instance;
    public static File mcDir;

    public void preInit(FMLPreInitializationEvent event){
        SchematicLoader.INSTANCE.reloadCache();
    }

    public void init(FMLInitializationEvent event){
        mcDir = (File) FMLInjectionData.data()[6];
        if (ElecCore.developmentEnvironment){
            GameRegistry.register(new SchematicCreatorItem(), new EDResourceLocation("schematicCreator_DEV"));
        }
    }

    public void postInit(FMLPostInitializationEvent event){
        ElementalDimensions.registerCommand(new CommandReloadSchematics());
    }

    public void serverStarting(FMLServerStartingEvent event){
        event.registerServerCommand(new ElementalDimensionsCommand());
    }

    static {
        instance = new NCLayerMain();
    }

}
