package bitmovers.elementaldimensions.ncLayer;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.util.command.AbstractSubCommand;
import bitmovers.elementaldimensions.util.command.ElementalDimensionsCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import javax.annotation.Nonnull;

/**
 * Created by Elec332 on 4-8-2016.
 */
public class NCLayerMain {

    public static NCLayerMain instance;

    public void preInit(FMLPreInitializationEvent event){
        SchematicLoader.INSTANCE.reloadCache();
    }

    public void init(FMLInitializationEvent event){
        ElementalDimensions.registerCommand(new AbstractSubCommand() {

            @Override
            public String getCommandName() {
                return "reloadSchematics";
            }

            @Override
            public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException {
                sender.addChatMessage(new TextComponentString("Schematics reloaded"));
                SchematicLoader.INSTANCE.reloadCache();
            }

        });
        ElementalDimensions.registerCommand(new AbstractSubCommand() {

            @Override
            public String getCommandName() {
                return "ping";
            }

            @Override
            public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException {
                sender.addChatMessage(new TextComponentString("pong"));
            }

        });
    }

    public void postInit(FMLPostInitializationEvent event){

    }

    public void serverStarting(FMLServerStartingEvent event){
        event.registerServerCommand(new ElementalDimensionsCommand());
    }

    static {
        instance = new NCLayerMain();
    }

}
