package bitmovers.elementaldimensions.util.command;

import com.google.common.collect.ImmutableList;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Elec332 on 4-8-2016.
 */
public abstract class AbstractSubCommand implements IElementalDimensionsSubCommand {

    @Override
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        return ImmutableList.of();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

}
