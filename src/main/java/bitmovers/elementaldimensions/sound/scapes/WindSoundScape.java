package bitmovers.elementaldimensions.sound.scapes;

import bitmovers.elementaldimensions.util.EDResourceLocation;
import bitmovers.elementaldimensions.util.worldgen.WorldGenHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WindSoundScape implements SoundScape {
    @Override
    public int getRandomDelay() {
        return 0;
    }

    @Override
    public EDResourceLocation getSoundResource() {
        return new EDResourceLocation("howlingwind");
    }

    @Override
    public float calculateVolume(World world, BlockPos pos, EntityPlayer player) {
        float volume = 1.0f;
        if (!WorldGenHelper.areWeOutside(world, pos)) {
            volume /= 5;
        }
        return volume;
    }

    @Override
    public boolean needsUpdate() {
        return true;
    }
}
