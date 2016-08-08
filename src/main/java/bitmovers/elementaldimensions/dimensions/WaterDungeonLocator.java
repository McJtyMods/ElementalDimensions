package bitmovers.elementaldimensions.dimensions;

import bitmovers.elementaldimensions.util.Config;
import net.minecraft.world.World;

import java.util.Random;

public class WaterDungeonLocator {

    public static boolean isWaterDungeonChunk(World world, int chunkX, int chunkZ) {
        Random random = new Random((world.getSeed() + chunkX) * 349 + chunkZ * 13 + 241);
        random.nextFloat();
        return random.nextFloat() < Config.Dimensions.waterDungeonChance;
    }
}
