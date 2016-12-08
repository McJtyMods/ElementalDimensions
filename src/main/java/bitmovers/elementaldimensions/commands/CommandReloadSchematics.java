package bitmovers.elementaldimensions.commands;

import bitmovers.elementaldimensions.ncLayer.SchematicLoader;
import bitmovers.elementaldimensions.util.command.AbstractSubCommand;
import mcjty.lib.tools.ChatTools;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nonnull;

/**
 * Created by Elec332 on 4-8-2016.
 */
public class CommandReloadSchematics extends AbstractSubCommand {

    @Override
    public String getCommandName() {
        return "reloadSchematics";
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException {
        ChatTools.addChatMessage(sender, new TextComponentString("Schematics reloaded"));
        SchematicLoader.INSTANCE.reloadCache();
    }

}
