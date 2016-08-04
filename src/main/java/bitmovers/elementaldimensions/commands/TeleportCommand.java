package bitmovers.elementaldimensions.commands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static bitmovers.elementaldimensions.tools.CustomTeleporter.teleportToDimension;

public class TeleportCommand implements ICommand {

    @Override
    public String getCommandName() {
        return "ed_tp";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return getCommandName() + " <dimid> [<x> <y> <z>]";
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.emptyList();
    }

    private static int fetchInt(ICommandSender sender, String[] args, int index, int defaultValue) {
        int value;
        try {
            value = Integer.parseInt(args[index]);
        } catch (NumberFormatException e) {
            value = 0;
            sender.addChatMessage(new TextComponentString(TextFormatting.RED + "Parameter is not a valid integer!"));
        } catch (ArrayIndexOutOfBoundsException e) {
            return defaultValue;
        }
        return value;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        int dim = fetchInt(sender, args, 0, 0);
        int x = fetchInt(sender, args, 1, 0);
        int y = fetchInt(sender, args, 2, 100);
        int z = fetchInt(sender, args, 3, 0);

        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;

            int currentId = player.worldObj.provider.getDimension();
            if (currentId != dim) {
                teleportToDimension(player, dim, x, y, z);
            } else {
                player.setPositionAndUpdate(x, y, z);
            }
        }

    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        return Collections.emptyList();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return getCommandName().compareTo(o.getCommandName());
    }
}
