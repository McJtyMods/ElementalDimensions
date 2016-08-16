package bitmovers.elementaldimensions.dimensions;

import bitmovers.elementaldimensions.util.Config;
import net.minecraft.world.World;

import java.util.Random;

public class EarthDungeonLocator {

    public static boolean isEarthDungeonChunk(World world, int chunkX, int chunkZ) {
        Random random = new Random((world.getSeed() + chunkX) * 37 + chunkZ * 5 + 113);
        random.nextFloat();
        return random.nextFloat() < Config.Dimensions.earthDungeonChance;
    }

    public static boolean isEarthTowerChunk(World world, int chunkX, int chunkZ) {
        Random random = new Random((world.getSeed() + chunkX) * 167 + chunkZ * 11 + 347);
        random.nextFloat();
        return random.nextFloat() < Config.Dimensions.earthTowerChance;
    }
}
