package bitmovers.elementaldimensions.sound.scapes;

import bitmovers.elementaldimensions.util.EDResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpiritsSoundScape implements SoundScape {
    @Override
    public int getRandomDelay() {
        return 100;
    }

    @Override
    public EDResourceLocation getSoundResource() {
        return new EDResourceLocation("spirits");
    }

    @Override
    public float calculateVolume(World world, BlockPos pos, EntityPlayer player) {
        return 1.0f;
    }

    @Override
    public boolean needsUpdate() {
        return false;
    }
}
