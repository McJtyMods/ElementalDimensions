package bitmovers.elementaldimensions.util.command;

import bitmovers.elementaldimensions.ElementalDimensions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import elec332.core.ElecCore;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * Created by Elec332 on 4-8-2016.
 */
public class ElementalDimensionsCommand extends CommandBase {

    public ElementalDimensionsCommand(){
        aliases = Lists.newArrayList(ElementalDimensions.MODID, "ED", "ed");
    }

    private static final Map<String, IElementalDimensionsSubCommand> subCommands;
    private final List<String> aliases;

    @Override
    public String getName() {
        return ElementalDimensions.MODID;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "command.ed.usage";
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException {
        if (args.length < 1) {
            return;
        }
        String s = args[0];
        IElementalDimensionsSubCommand command = subCommands.get(s);
        if (command == null){
            return;
        }
        command.execute(server, sender, removeFirst(args));
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return ElecCore.developmentEnvironment || super.checkPermission(server, sender);
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        if (args.length == 1){
            return getListOfStringsMatchingLastWord(args, subCommands.keySet());
        } else if (args.length > 1){
            String s = args[0];
            IElementalDimensionsSubCommand command = subCommands.get(s);
            if (command == null){
                return ImmutableList.of();
            }
            return command.getTabCompletionOptions(server, sender, removeFirst(args), pos);
        }
        return ImmutableList.of();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        if (args.length < 1) {
            return false;
        }
        String s = args[0];
        IElementalDimensionsSubCommand command = subCommands.get(s);
        return command != null && command.isUsernameIndex(removeFirst(args), index - 1);
    }

    public static void registerSubCommand(@Nonnull IElementalDimensionsSubCommand command){
        subCommands.put(command.getCommandName(), command);
    }

    private String[] removeFirst(String[] args){
        if (args.length < 2){
            return new String[0];
        }
        String[] nArgs = new String[args.length - 1];
        System.arraycopy(args, 1, nArgs, 0, nArgs.length);
        return nArgs;
    }

    static {
        subCommands = Maps.newHashMap();
    }

}
