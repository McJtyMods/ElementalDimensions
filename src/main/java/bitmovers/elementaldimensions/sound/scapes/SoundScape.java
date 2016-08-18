package bitmovers.elementaldimensions.sound.scapes;

import bitmovers.elementaldimensions.util.EDResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface SoundScape {

    /// Get a random delay before restarting the sound. Returns 0 if no delay is needed
    int getRandomDelay();

    EDResourceLocation getSoundResource();

    /// Returns true if this soundscape can change (needs updating)
    boolean needsUpdate();

    float calculateVolume(World world, BlockPos pos, EntityPlayer player);
}
