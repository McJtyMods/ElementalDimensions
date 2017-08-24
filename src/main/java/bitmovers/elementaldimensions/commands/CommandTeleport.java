package bitmovers.elementaldimensions.commands;

import bitmovers.elementaldimensions.util.command.AbstractSubCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;

import static bitmovers.elementaldimensions.util.CustomTeleporter.teleportToDimension;

/**
 * Created by Elec332 on 4-8-2016.
 */
public class CommandTeleport extends AbstractSubCommand {

    @Override
    public String getCommandName() {
        return "tp";
    }

    private static int fetchInt(ICommandSender sender, String[] args, int index, int defaultValue) {
        int value = defaultValue;
        try {
            value = Integer.parseInt(args[index]);
        } catch (NumberFormatException e) {
            ITextComponent component = new TextComponentString(TextFormatting.RED + "Parameter is not a valid integer!");
            if (sender instanceof EntityPlayer) {
                ((EntityPlayer) sender).sendStatusMessage(component, false);
            } else {
                sender.sendMessage(component);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            //
        }
        return value;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException {
        int dim, x, y, z;
        try {
            dim = Integer.parseInt(args[0]);
            x = fetchInt(sender, args, 1, 0);
            y = fetchInt(sender, args, 2, 100);
            z = fetchInt(sender, args, 3, 0);
        } catch (Exception e){
            ITextComponent component2 = new TextComponentString(TextFormatting.RED+"Failed to parse command parameters!");
            if (sender instanceof EntityPlayer) {
                ((EntityPlayer) sender).sendStatusMessage(component2, false);
            } else {
                sender.sendMessage(component2);
            }
            ITextComponent component1 = new TextComponentString(TextFormatting.RED+"Parameters:");
            if (sender instanceof EntityPlayer) {
                ((EntityPlayer) sender).sendStatusMessage(component1, false);
            } else {
                sender.sendMessage(component1);
            }
            ITextComponent component = new TextComponentString(TextFormatting.RED+"<dimid> <x> <y> <z>");
            if (sender instanceof EntityPlayer) {
                ((EntityPlayer) sender).sendStatusMessage(component, false);
            } else {
                sender.sendMessage(component);
            }
            return;
        }

        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;

            int currentId = player.getEntityWorld().provider.getDimension();
            if (currentId != dim) {
                try {
                    teleportToDimension(player, dim, x, y, z);
                } catch (IllegalArgumentException e){
                    ITextComponent component = new TextComponentString(TextFormatting.RED + e.getMessage());
                    if (sender instanceof EntityPlayer) {
                        ((EntityPlayer) sender).sendStatusMessage(component, false);
                    } else {
                        sender.sendMessage(component);
                    }
                }
            } else {
                player.setPositionAndUpdate(x, y, z);
            }
        }

    }

}
