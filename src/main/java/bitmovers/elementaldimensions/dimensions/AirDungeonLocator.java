package bitmovers.elementaldimensions.dimensions;

import bitmovers.elementaldimensions.util.Config;
import net.minecraft.world.World;

import java.util.Random;

public class AirDungeonLocator {

    public static boolean isAirDungeonChunk(World world, int chunkX, int chunkZ) {
        Random random = new Random((world.getSeed() + chunkX) * 163 + chunkZ * 11 + 277);
        random.nextFloat();
        return random.nextFloat() < Config.Dimensions.airDungeonChance;
    }
}
