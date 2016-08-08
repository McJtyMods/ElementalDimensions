package bitmovers.elementaldimensions.dimensions;

import bitmovers.elementaldimensions.util.Config;
import net.minecraft.world.World;

import java.util.Random;

public class SpiritDungeonLocator {

    public static boolean isSpiritDungeonChunk(World world, int chunkX, int chunkZ) {
        Random random = new Random((world.getSeed() + chunkX) * 241 + chunkZ * 27 + 349);
        random.nextFloat();
        return random.nextFloat() < Config.Dimensions.spiritDungeonChance;
    }
}
