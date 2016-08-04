package bitmovers.elementaldimensions.commands;

import bitmovers.elementaldimensions.util.command.AbstractSubCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import static bitmovers.elementaldimensions.tools.CustomTeleporter.teleportToDimension;

/**
 * Created by Elec332 on 4-8-2016.
 */
public class CommandTeleport extends AbstractSubCommand {

    @Override
    public String getCommandName() {
        return "tp";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        int dim, x, y, z;
        try {
            dim = Integer.parseInt(args[0]);
            x = Integer.parseInt(args[1]);
            y = Integer.parseInt(args[2]);
            z = Integer.parseInt(args[3]);
        } catch (Exception e){
            sender.addChatMessage(new TextComponentString(TextFormatting.RED+"Failed to parse command parameters!"));
            sender.addChatMessage(new TextComponentString(TextFormatting.RED+"Parameters:"));
            sender.addChatMessage(new TextComponentString(TextFormatting.RED+"<dimid> <x> <y> <z>"));
            return;
        }

        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;

            int currentId = player.worldObj.provider.getDimension();
            if (currentId != dim) {
                try {
                    teleportToDimension(player, dim, x, y, z);
                } catch (IllegalArgumentException e){
                    sender.addChatMessage(new TextComponentString(TextFormatting.RED + e.getMessage()));
                }
            } else {
                player.setPositionAndUpdate(x, y, z);
            }
        }

    }

}
