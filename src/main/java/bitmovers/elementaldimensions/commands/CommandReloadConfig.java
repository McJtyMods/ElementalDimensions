package bitmovers.elementaldimensions.commands;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.util.command.AbstractSubCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nonnull;

/**
 * Created by Elec332 on 4-8-2016.
 */
public class CommandReloadConfig extends AbstractSubCommand {

    @Override
    public String getCommandName() {
        return "reloadConfig";
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException {
        ElementalDimensions.config.refresh();
        ITextComponent component = new TextComponentString("Configs reloaded");
        if (sender instanceof EntityPlayer) {
            ((EntityPlayer) sender).sendStatusMessage(component, false);
        } else {
            sender.sendMessage(component);
        }
    }

}
