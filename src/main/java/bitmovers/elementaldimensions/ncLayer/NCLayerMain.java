package bitmovers.elementaldimensions.ncLayer;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.commands.CommandReloadConfig;
import bitmovers.elementaldimensions.commands.CommandReloadSchematics;
import bitmovers.elementaldimensions.ncLayer.worldgen.DefaultStructureCreator;
import bitmovers.elementaldimensions.util.EDResourceLocation;
import bitmovers.elementaldimensions.util.command.ElementalDimensionsCommand;
import bitmovers.elementaldimensions.world.WorldGeneratorPortalDungeon;
import elec332.core.api.structure.GenerationType;
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

    }

    public void init(FMLInitializationEvent event){
        mcDir = (File) FMLInjectionData.data()[6];

        for (int i = 1; i < 5; i++) {
            SchematicLoader.INSTANCE.registerSchematic(new EDResourceLocation("schematics/test"+i+".schematic"));
        }
        SchematicLoader.INSTANCE.registerSchematic(WorldGeneratorPortalDungeon.dungeonResource, true);
    }

    public void postInit(FMLPostInitializationEvent event){
        ElementalDimensions.registerCommand(new CommandReloadSchematics());
        ElementalDimensions.registerCommand(new CommandReloadConfig());
        SchematicLoader.INSTANCE.reloadCache();
        for (int i = 1; i < 5; i++) {
            GameRegistry.registerWorldGenerator(new DefaultStructureCreator(new EDResourceLocation("schematics/test"+i+".schematic"), GenerationType.SURFACE), 100 + 1);
        }
    }

    public void serverStarting(FMLServerStartingEvent event){
        event.registerServerCommand(new ElementalDimensionsCommand());
    }

    static {
        instance = new NCLayerMain();
    }

}
